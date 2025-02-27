package com.tinubu.insurance.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tinubu.insurance.model.InsurancePolicy;
import com.tinubu.insurance.repository.InsurancePolicyCommandRepository;
import com.tinubu.insurance.repository.InsurancePolicyReadRepository;

/**
 * 
 * @author r.benabdesselam
 * @Date 27 févr. 2025
 *
 */

@Service
public class InsurancePolicyServiceImpl implements InsurancePolicyService {

    private final InsurancePolicyReadRepository readRepository;
    private final InsurancePolicyCommandRepository commandRepository;

    private static final Logger LOGGER = Logger.getLogger(InsurancePolicyServiceImpl.class);
    
    public InsurancePolicyServiceImpl(InsurancePolicyReadRepository readRepository, InsurancePolicyCommandRepository commandRepository) {
        this.readRepository = readRepository;
        this.commandRepository = commandRepository;
    }

    @Override
    public List<InsurancePolicy> getAllPolicies() {
        return readRepository.findAll();
    }

    @Override
    public Optional<InsurancePolicy> getPolicyById(int id) {
        return readRepository.findById(id);
    }

    @Override
    public InsurancePolicy createPolicy(InsurancePolicy policy) {
        return commandRepository.save(policy);
    }

    @Override
    public InsurancePolicy updatePolicy(int id, InsurancePolicy updatedPolicy) {
        return readRepository.findById(id)
                .map(existingPolicy -> {
                    existingPolicy.setPolicyName(updatedPolicy.getPolicyName());
                    existingPolicy.setPolicyStatus(updatedPolicy.getPolicyStatus());
                    existingPolicy.setStartDate(updatedPolicy.getStartDate());
                    existingPolicy.setEndDate(updatedPolicy.getEndDate());
                    return commandRepository.save(existingPolicy);
                })
                .orElseThrow(() -> new RuntimeException("Policy not found"));
    }

    @Override
    public void deletePolicy(int id) {
        commandRepository.deleteById(id);
    }
}
