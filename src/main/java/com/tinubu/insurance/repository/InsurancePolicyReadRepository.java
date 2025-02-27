package com.tinubu.insurance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tinubu.insurance.model.InsurancePolicy;

/**
 * 
 * @author r.benabdesselam
 * @Date 27 févr. 2025
 *
 */

@Repository
@Transactional(readOnly = true) // Lecture seule
public interface InsurancePolicyReadRepository extends JpaRepository<InsurancePolicy, Integer> {

    List<InsurancePolicy> findAll();

    Optional<InsurancePolicy> findById(int id);
}
