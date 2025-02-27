package com.tinubu.insurance.service;

import java.util.List;
import java.util.Optional;
import com.tinubu.insurance.model.InsurancePolicy;

/**
 * 
 * @author r.benabdesselam
 * @Date 27 févr. 2025
 *
 */
public interface InsurancePolicyService {

    List<InsurancePolicy> getAllPolicies();

    Optional<InsurancePolicy> getPolicyById(int id);

    InsurancePolicy createPolicy(InsurancePolicy policy);

    InsurancePolicy updatePolicy(int id, InsurancePolicy updatedPolicy);

    void deletePolicy(int id);
}
