package com.mutualfund.backend.config;

import com.mutualfund.backend.entity.MutualFund;
import com.mutualfund.backend.entity.User;
import com.mutualfund.backend.repo.MutualFundRepo;
import com.mutualfund.backend.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final MutualFundRepo mutualFundRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(MutualFundRepo mutualFundRepo, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.mutualFundRepo = mutualFundRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedFunds();
        seedUsers();
    }

    private void seedFunds() {
        createFundIfMissing("Alpha Growth Equity Fund", "Large Cap", MutualFund.RiskLevel.Medium, 45.32, "Rs 5,240 Cr", "1.20%", "Rs 500", 12.5, 15.2, 17.8, "Ravi Sharma");
        createFundIfMissing("Strategic Balanced Fund", "Balanced", MutualFund.RiskLevel.Medium, 38.76, "Rs 3,890 Cr", "1.10%", "Rs 500", 10.8, 12.4, 14.1, "Priya Nair");
        createFundIfMissing("Mid Cap Opportunities Fund", "Mid Cap", MutualFund.RiskLevel.High, 62.15, "Rs 2,150 Cr", "1.45%", "Rs 1000", 18.3, 22.6, 25.4, "Amit Rao");
        createFundIfMissing("Conservative Debt Fund", "Debt", MutualFund.RiskLevel.Low, 28.94, "Rs 4,560 Cr", "0.85%", "Rs 500", 6.2, 6.8, 7.2, "Neha Jain");
        createFundIfMissing("Small Cap Growth Fund", "Small Cap", MutualFund.RiskLevel.High, 54.28, "Rs 1,680 Cr", "1.60%", "Rs 1000", 22.4, 28.5, 31.2, "Karan Mehta");
        createFundIfMissing("Tax Saver Equity Fund", "ELSS", MutualFund.RiskLevel.Medium, 41.56, "Rs 6,200 Cr", "1.25%", "Rs 500", 13.7, 16.8, 19.3, "Sana Iyer");
        createFundIfMissing("Corporate Bond Fund", "Debt", MutualFund.RiskLevel.Low, 32.18, "Rs 3,420 Cr", "0.75%", "Rs 500", 7.1, 7.6, 8.0, "Rahul Verma");
        createFundIfMissing("Dividend Yield Fund", "Large Cap", MutualFund.RiskLevel.Medium, 49.82, "Rs 4,780 Cr", "1.15%", "Rs 500", 11.5, 13.9, 15.5, "Meera Das");
    }

    private void createFundIfMissing(
            String name,
            String category,
            MutualFund.RiskLevel risk,
            double nav,
            String aum,
            String expenseRatio,
            String minInvest,
            double returns1yr,
            double returns3yr,
            double returns5yr,
            String fundManager
    ) {
        if (mutualFundRepo.existsByName(name)) {
            return;
        }

        MutualFund fund = new MutualFund();
        fund.setName(name);
        fund.setCategory(category);
        fund.setRisk(risk);
        fund.setNav(nav);
        fund.setAum(aum);
        fund.setExpenseRatio(expenseRatio);
        fund.setMinInvest(minInvest);
        fund.setReturns1yr(returns1yr);
        fund.setReturns3yr(returns3yr);
        fund.setReturns5yr(returns5yr);
        fund.setDescription("Seeded sample fund for local full-stack testing.");
        fund.setFundManager(fundManager);

        mutualFundRepo.save(fund);
    }

    private void seedUsers() {
        createUserIfMissing("Admin User", "admin@mfpro.com", "admin123", User.Role.ADMIN);
        createUserIfMissing("Advisor User", "advisor@mfpro.com", "advisor123", User.Role.FINANCIAL_ADVISOR);
        createUserIfMissing("Analyst User", "analyst@mfpro.com", "analyst123", User.Role.DATA_ANALYST);
    }

    private void createUserIfMissing(String name, String email, String password, User.Role role) {
        if (userRepo.existsByEmail(email)) {
            return;
        }

        User user = new User();
        user.setFullName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setStatus(User.Status.ACTIVE);

        userRepo.save(user);
    }
}
