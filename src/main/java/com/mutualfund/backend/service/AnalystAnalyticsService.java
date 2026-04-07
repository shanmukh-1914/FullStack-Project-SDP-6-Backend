package com.mutualfund.backend.service;

import com.mutualfund.backend.entity.Investment;
import com.mutualfund.backend.entity.MutualFund;
import com.mutualfund.backend.entity.User;
import com.mutualfund.backend.repo.InvestmentRepo;
import com.mutualfund.backend.repo.MutualFundRepo;
import com.mutualfund.backend.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalystAnalyticsService {

    private static final DateTimeFormatter MONTH_FMT = DateTimeFormatter.ofPattern("MMM yy", Locale.ENGLISH);

    private final MutualFundRepo mutualFundRepo;
    private final InvestmentRepo investmentRepo;
    private final UserRepo userRepo;

    public AnalystAnalyticsService(MutualFundRepo mutualFundRepo, InvestmentRepo investmentRepo, UserRepo userRepo) {
        this.mutualFundRepo = mutualFundRepo;
        this.investmentRepo = investmentRepo;
        this.userRepo = userRepo;
    }

    public Map<String, Object> getAnalytics() {
        List<MutualFund> funds = mutualFundRepo.findAll();
        List<Investment> investments = investmentRepo.findAll();
        List<User> investors = userRepo.findByRole(User.Role.INVESTOR);

        Map<String, Object> response = new HashMap<>();
        response.put("summary", buildSummary(funds, investments, investors));
        response.put("riskReturn", buildRiskReturn(funds));
        response.put("fundPerformanceTrend", buildFundPerformanceTrend(funds));
        response.put("sipVsLumpsum", buildSipVsLumpsum(investments));
        response.put("categoryFlow", buildCategoryFlow(investments));
        response.put("ageBehavior", buildAgeBehavior(investors.size()));
        response.put("fundGrowth", buildFundGrowth(investments, investors));
        return response;
    }

    private Map<String, Object> buildSummary(List<MutualFund> funds, List<Investment> investments, List<User> investors) {
        double totalInvestments = investments.stream()
                .filter(i -> i.getStatus() == Investment.Status.ACTIVE)
                .mapToDouble(Investment::getAmountInvested)
                .sum();

        YearMonth now = YearMonth.now();
        YearMonth prev = now.minusMonths(1);
        double thisMonth = sumByMonth(investments, now);
        double prevMonth = sumByMonth(investments, prev);
        double monthGrowth = prevMonth > 0 ? ((thisMonth - prevMonth) / prevMonth) * 100.0 : 0.0;

        double avgReturn1y = funds.stream()
                .filter(f -> f.getReturns1yr() != null)
                .mapToDouble(MutualFund::getReturns1yr)
                .average()
                .orElse(0.0);

        String bestCategory = funds.stream()
                .filter(f -> f.getReturns1yr() != null)
                .collect(Collectors.groupingBy(MutualFund::getCategory,
                        Collectors.averagingDouble(MutualFund::getReturns1yr)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalInvestments", round(totalInvestments));
        summary.put("monthGrowthPercent", round(monthGrowth));
        summary.put("avgReturn1y", round(avgReturn1y));
        summary.put("mostPopularCategory", bestCategory);
        summary.put("activeInvestors", investors.size());
        return summary;
    }

    private List<Map<String, Object>> buildRiskReturn(List<MutualFund> funds) {
        Map<String, List<MutualFund>> byCategory = funds.stream().collect(Collectors.groupingBy(MutualFund::getCategory));
        List<Map<String, Object>> out = new ArrayList<>();

        for (Map.Entry<String, List<MutualFund>> entry : byCategory.entrySet()) {
            String category = entry.getKey();
            List<MutualFund> categoryFunds = entry.getValue();
            double avgReturn = categoryFunds.stream()
                    .filter(f -> f.getReturns1yr() != null)
                    .mapToDouble(MutualFund::getReturns1yr)
                    .average()
                    .orElse(0.0);
            double volatility = categoryFunds.stream()
                    .mapToDouble(f -> riskVolatility(f.getRisk()))
                    .average()
                    .orElse(0.0);

            Map<String, Object> row = new HashMap<>();
            row.put("category", category);
            row.put("returns", round(avgReturn));
            row.put("volatility", round(volatility));
            out.add(row);
        }

        out.sort((a, b) -> String.valueOf(a.get("category")).compareToIgnoreCase(String.valueOf(b.get("category"))));
        return out;
    }

    private List<Map<String, Object>> buildFundPerformanceTrend(List<MutualFund> funds) {
        List<YearMonth> months = lastMonths(6);
        Map<String, Double> baseByCategory = funds.stream()
                .filter(f -> f.getReturns1yr() != null)
                .collect(Collectors.groupingBy(MutualFund::getCategory,
                        Collectors.averagingDouble(MutualFund::getReturns1yr)));

        List<Map<String, Object>> out = new ArrayList<>();
        for (int i = 0; i < months.size(); i++) {
            YearMonth ym = months.get(i);
            double factor = 0.92 + (i * 0.02);
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("month", ym.format(MONTH_FMT));
            row.put("largeCap", round(baseByCategory.getOrDefault("Large Cap", 0.0) * factor));
            row.put("midCap", round(baseByCategory.getOrDefault("Mid Cap", 0.0) * factor));
            row.put("smallCap", round(baseByCategory.getOrDefault("Small Cap", 0.0) * factor));
            row.put("debt", round(baseByCategory.getOrDefault("Debt", 0.0) * factor));
            out.add(row);
        }
        return out;
    }

    private List<Map<String, Object>> buildSipVsLumpsum(List<Investment> investments) {
        List<YearMonth> months = lastMonths(6);
        List<Map<String, Object>> out = new ArrayList<>();

        for (YearMonth ym : months) {
            double sip = investments.stream()
                    .filter(i -> YearMonth.from(i.getInvestmentDate()).equals(ym))
                    .filter(i -> i.getAmountInvested() <= 5000)
                    .mapToDouble(Investment::getAmountInvested)
                    .sum();
            double lumpsum = investments.stream()
                    .filter(i -> YearMonth.from(i.getInvestmentDate()).equals(ym))
                    .filter(i -> i.getAmountInvested() > 5000)
                    .mapToDouble(Investment::getAmountInvested)
                    .sum();

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("month", ym.format(MONTH_FMT));
            row.put("sip", round(sip));
            row.put("lumpsum", round(lumpsum));
            out.add(row);
        }

        return out;
    }

    private List<Map<String, Object>> buildCategoryFlow(List<Investment> investments) {
        List<YearMonth> months = lastMonths(6);
        List<Map<String, Object>> out = new ArrayList<>();

        for (YearMonth ym : months) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("month", ym.format(DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH)));
            row.put("largeCap", round(sumForMonthAndCategory(investments, ym, "Large Cap")));
            row.put("midCap", round(sumForMonthAndCategory(investments, ym, "Mid Cap")));
            row.put("smallCap", round(sumForMonthAndCategory(investments, ym, "Small Cap")));
            row.put("debt", round(sumForMonthAndCategory(investments, ym, "Debt")));
            out.add(row);
        }

        return out;
    }

    private List<Map<String, Object>> buildAgeBehavior(int investorCount) {
        int base = Math.max(investorCount, 10);
        return List.of(
                ageRow("20-30", Math.min(90, 55 + (base % 20))),
                ageRow("31-40", Math.min(90, 60 + (base % 15))),
                ageRow("41-50", Math.max(35, 55 - (base % 10))),
                ageRow("51-60", Math.max(30, 48 - (base % 8)))
        );
    }

    private List<Map<String, Object>> buildFundGrowth(List<Investment> investments, List<User> investors) {
        List<YearMonth> months = lastMonths(6);
        List<Map<String, Object>> out = new ArrayList<>();

        double cumulative = 0.0;
        for (YearMonth ym : months) {
            double monthAmount = investments.stream()
                    .filter(i -> YearMonth.from(i.getInvestmentDate()).equals(ym))
                    .mapToDouble(Investment::getAmountInvested)
                    .sum();
            cumulative += monthAmount;

            long investorCount = investors.stream()
                    .filter(u -> u.getJoinDate() != null)
                    .filter(u -> !YearMonth.from(u.getJoinDate()).isAfter(ym))
                    .count();

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("month", ym.format(MONTH_FMT));
            row.put("aum", round(cumulative));
            row.put("investors", investorCount);
            out.add(row);
        }

        return out;
    }

    private static Map<String, Object> ageRow(String age, int sipPercent) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("age", age);
        row.put("sip", sipPercent);
        row.put("lumpsum", 100 - sipPercent);
        return row;
    }

    private double sumForMonthAndCategory(List<Investment> investments, YearMonth month, String category) {
        return investments.stream()
                .filter(i -> YearMonth.from(i.getInvestmentDate()).equals(month))
                .filter(i -> i.getFund() != null && category.equals(i.getFund().getCategory()))
                .mapToDouble(Investment::getAmountInvested)
                .sum();
    }

    private double sumByMonth(List<Investment> investments, YearMonth month) {
        return investments.stream()
                .filter(i -> i.getInvestmentDate() != null)
                .filter(i -> YearMonth.from(i.getInvestmentDate()).equals(month))
                .mapToDouble(Investment::getAmountInvested)
                .sum();
    }

    private List<YearMonth> lastMonths(int count) {
        List<YearMonth> list = new ArrayList<>();
        YearMonth end = YearMonth.now();
        for (int i = count - 1; i >= 0; i--) {
            list.add(end.minusMonths(i));
        }
        return list;
    }

    private double riskVolatility(MutualFund.RiskLevel risk) {
        if (risk == null) return 0.0;
        return switch (risk) {
            case Low -> 6.0;
            case Medium -> 10.0;
            case High -> 15.0;
        };
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
