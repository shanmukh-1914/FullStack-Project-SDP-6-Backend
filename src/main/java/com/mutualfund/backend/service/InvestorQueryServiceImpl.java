package com.mutualfund.backend.service;

import com.mutualfund.backend.dto.InvestorQueryDTO;
import com.mutualfund.backend.dto.QueryRequest;
import com.mutualfund.backend.dto.ReplyRequest;
import com.mutualfund.backend.entity.InvestorQuery;
import com.mutualfund.backend.entity.User;
import com.mutualfund.backend.exception.ResourceNotFoundException;
import com.mutualfund.backend.repo.InvestorQueryRepo;
import com.mutualfund.backend.repo.UserRepo;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestorQueryServiceImpl implements InvestorQueryService {

    private final InvestorQueryRepo queryRepo;
    private final UserRepo userRepo;

    public InvestorQueryServiceImpl(InvestorQueryRepo queryRepo, UserRepo userRepo) {
        this.queryRepo = queryRepo;
        this.userRepo = userRepo;
    }

    @Override
    public InvestorQueryDTO submitQuery(Long investorId, QueryRequest request) {
        User investor = userRepo.findById(investorId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + investorId));

        InvestorQuery query = new InvestorQuery();
        query.setInvestor(investor);
        query.setQueryText(request.getQueryText());
        query.setStatus(InvestorQuery.Status.PENDING);

        return mapToDTO(queryRepo.save(query));
    }

    @Override
    public InvestorQueryDTO replyToQuery(Long queryId, Long advisorId, ReplyRequest request) {
        InvestorQuery query = queryRepo.findById(queryId)
                .orElseThrow(() -> new ResourceNotFoundException("Query not found: " + queryId));

        User advisor = userRepo.findById(advisorId)
                .orElseThrow(() -> new ResourceNotFoundException("Advisor not found: " + advisorId));

        query.setReplyText(request.getReplyText());
        query.setRepliedBy(advisor);
        query.setStatus(InvestorQuery.Status.ANSWERED);
        query.setRepliedAt(LocalDateTime.now());

        return mapToDTO(queryRepo.save(query));
    }

    @Override
    public List<InvestorQueryDTO> getQueriesByInvestor(Long investorId) {
        return queryRepo.findByInvestorId(investorId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvestorQueryDTO> getAllPendingQueries() {
        return queryRepo.findByStatus(InvestorQuery.Status.PENDING)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvestorQueryDTO> getAllQueries() {
        return queryRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private InvestorQueryDTO mapToDTO(InvestorQuery query) {
        InvestorQueryDTO dto = new InvestorQueryDTO();
        dto.setId(query.getId());
        dto.setInvestorId(query.getInvestor().getId());
        dto.setInvestorName(query.getInvestor().getFullName());
        dto.setInvestorEmail(query.getInvestor().getEmail());
        dto.setQueryText(query.getQueryText());
        dto.setReplyText(query.getReplyText());
        dto.setStatus(query.getStatus().name());
        dto.setCreatedAt(query.getCreatedAt());
        dto.setRepliedAt(query.getRepliedAt());
        if (query.getRepliedBy() != null) {
            dto.setRepliedByName(query.getRepliedBy().getFullName());
        }
        return dto;
    }
}