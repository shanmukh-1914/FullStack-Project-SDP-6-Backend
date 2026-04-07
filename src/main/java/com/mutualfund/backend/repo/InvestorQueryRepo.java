package com.mutualfund.backend.repo;

import com.mutualfund.backend.entity.InvestorQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
//Repository interface for User entity
// Provides CRUD operations using Spring Data JPA
@Repository
public interface InvestorQueryRepo extends JpaRepository<InvestorQuery, Long> {
    List<InvestorQuery> findByInvestorId(Long investorId);
    List<InvestorQuery> findByStatus(InvestorQuery.Status status);
    List<InvestorQuery> findByRepliedById(Long advisorId);
    long countByStatus(InvestorQuery.Status status);
}