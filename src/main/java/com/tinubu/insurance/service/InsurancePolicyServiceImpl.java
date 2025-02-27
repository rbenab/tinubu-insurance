package com.tinubu.insurance.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
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

	public InsurancePolicyServiceImpl(InsurancePolicyReadRepository readRepository,
			InsurancePolicyCommandRepository commandRepository) {
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
		   if (policy.getStartDate() != null && policy.getEndDate() != null) {
		        // Vérification que la date de fin est après la date de début et dans le futur
		        if (!policy.getEndDate().isAfter(policy.getStartDate()) || policy.getStartDate().isBefore(LocalDate.now())) {
		            throw new IllegalArgumentException("La date de fin doit être après la date de début et dans le futur.");
		        }
		    }

		return commandRepository.save(policy);
	}

	@Override
	public InsurancePolicy updatePolicy(int id, InsurancePolicy updatedPolicy) {
		return readRepository.findById(id).map(existingPolicy -> {
			
	        // Vérification de la date dans l'update, si les dates sont présentes
	        if (updatedPolicy.getStartDate() != null && updatedPolicy.getEndDate() != null) {
	            if (!updatedPolicy.getEndDate().isAfter(updatedPolicy.getStartDate())) {
	                throw new IllegalArgumentException("La date de fin doit être après la date de début et dans le futur.");
	            }
	        }

			// Mettre à jour les champs avec les valeurs envoyées
			if (updatedPolicy.getPolicyName() != null) {
				existingPolicy.setPolicyName(updatedPolicy.getPolicyName());
			}
			if (updatedPolicy.getPolicyStatus() != null) {
				existingPolicy.setPolicyStatus(updatedPolicy.getPolicyStatus());
			}
			if (updatedPolicy.getStartDate() != null) {
				existingPolicy.setStartDate(updatedPolicy.getStartDate());
			}
			if (updatedPolicy.getEndDate() != null) {
				existingPolicy.setEndDate(updatedPolicy.getEndDate());
			}

			// Enregistrer et renvoyer l'objet mis à jour
			return commandRepository.save(existingPolicy);
		}).orElseThrow(() -> new RuntimeException("Policy not found"));
	}

	@Override
	public void deletePolicy(int id) {
	    try {
	        // Essayer de supprimer l'entité
	        commandRepository.deleteById(id);
	    } catch (EmptyResultDataAccessException e) {
	        // Cette exception est levée lorsque l'entité à supprimer n'existe pas
	        throw new EmptyResultDataAccessException("No class com.tinubu.insurance.model.InsurancePolicy entity with id " + id + " exists!", 1);
	    }
	}
}
