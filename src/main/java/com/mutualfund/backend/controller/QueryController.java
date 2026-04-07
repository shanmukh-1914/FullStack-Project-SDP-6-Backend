package com.mutualfund.backend.controller;

import com.mutualfund.backend.dto.InvestorQueryDTO;
import com.mutualfund.backend.dto.QueryRequest;
import com.mutualfund.backend.dto.ReplyRequest;
import com.mutualfund.backend.service.EmailService;
import com.mutualfund.backend.service.InvestorQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
// Exposes endpoints for client (frontend) to interact with backend services
// Follows Controller → Service → Repository architecture
@RestController
@RequestMapping("/api")
public class QueryController {

    private final InvestorQueryService queryService;
    private final EmailService emailService;

    public QueryController(InvestorQueryService queryService,
                           EmailService emailService) {
        this.queryService = queryService;
        this.emailService = emailService;
    }

    @PostMapping("/investor/queries/{investorId}")
    public ResponseEntity<InvestorQueryDTO> submitQuery(
            @PathVariable Long investorId,
            @RequestBody QueryRequest request) {
        return ResponseEntity.ok(queryService.submitQuery(investorId, request));
    }

    @GetMapping("/investor/queries/{investorId}")
    public ResponseEntity<List<InvestorQueryDTO>> getMyQueries(@PathVariable Long investorId) {
        return ResponseEntity.ok(queryService.getQueriesByInvestor(investorId));
    }

    @GetMapping("/advisor/queries")
    public ResponseEntity<List<InvestorQueryDTO>> getAllQueries() {
        return ResponseEntity.ok(queryService.getAllQueries());
    }

    @GetMapping("/advisor/queries/pending")
    public ResponseEntity<List<InvestorQueryDTO>> getPendingQueries() {
        return ResponseEntity.ok(queryService.getAllPendingQueries());
    }

    @PutMapping("/advisor/queries/{queryId}/reply/{advisorId}")
    public ResponseEntity<InvestorQueryDTO> replyToQuery(
            @PathVariable Long queryId,
            @PathVariable Long advisorId,
            @RequestBody ReplyRequest request) {
        InvestorQueryDTO replied = queryService.replyToQuery(queryId, advisorId, request);
        try {
            emailService.sendQueryReplyEmail(
                    replied.getInvestorEmail(),
                    replied.getInvestorName(),
                    replied.getReplyText());
        } catch (Exception e) {
            // continue even if email fails
        }
        return ResponseEntity.ok(replied);
    }

    @GetMapping("/admin/queries")
    public ResponseEntity<List<InvestorQueryDTO>> getAllQueriesAdmin() {
        return ResponseEntity.ok(queryService.getAllQueries());
    }
}