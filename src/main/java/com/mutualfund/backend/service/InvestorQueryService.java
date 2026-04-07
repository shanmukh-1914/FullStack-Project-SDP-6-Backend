package com.mutualfund.backend.service;

import com.mutualfund.backend.dto.InvestorQueryDTO;
import com.mutualfund.backend.dto.QueryRequest;
import com.mutualfund.backend.dto.ReplyRequest;
import java.util.List;

public interface InvestorQueryService {
    InvestorQueryDTO submitQuery(Long investorId, QueryRequest request);
    InvestorQueryDTO replyToQuery(Long queryId, Long advisorId, ReplyRequest request);
    List<InvestorQueryDTO> getQueriesByInvestor(Long investorId);
    List<InvestorQueryDTO> getAllPendingQueries();
    List<InvestorQueryDTO> getAllQueries();
}