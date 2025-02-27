package com.tinubu.insurance.repository;

import com.tinubu.insurance.model.InsurancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author r.benabdesselam
 * @Date 27 févr. 2025
 *
 */

@Repository
@Transactional // Gère les transactions pour CREATE, UPDATE, DELETE
public interface InsurancePolicyCommandRepository extends JpaRepository<InsurancePolicy, Integer> {
}
