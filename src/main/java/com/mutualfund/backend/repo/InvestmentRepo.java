package com.mutualfund.backend.repo;

import com.mutualfund.backend.entity.Investment;
import com.mutualfund.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvestmentRepo extends JpaRepository<Investment, Long> {
    List<Investment> findByUser(User user);
    List<Investment> findByUserId(Long userId);
    Optional<Investment> findByUserIdAndFundId(Long userId, Long fundId);

    @Query("SELECT SUM(i.amountInvested) FROM Investment i WHERE i.user.id = :userId AND i.status = 'ACTIVE'")
    Double getTotalInvestedByUser(@Param("userId") Long userId);
}