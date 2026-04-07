package com.mutualfund.backend.repo;

import com.mutualfund.backend.entity.MutualFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

//Repository interface for User entity
// Provides CRUD operations using Spring Data JPA
@Repository
public interface MutualFundRepo extends JpaRepository<MutualFund, Long> {
    List<MutualFund> findByCategory(String category);
    List<MutualFund> findByRisk(MutualFund.RiskLevel risk);
    List<MutualFund> findByCategoryAndRisk(String category, MutualFund.RiskLevel risk);
    boolean existsByName(String name);

    @Query("SELECT f FROM MutualFund f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<MutualFund> searchByName(@Param("name") String name);
}