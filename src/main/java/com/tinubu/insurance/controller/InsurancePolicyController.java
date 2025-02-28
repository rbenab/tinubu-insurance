package com.tinubu.insurance.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinubu.insurance.model.InsurancePolicy;
import com.tinubu.insurance.service.InsurancePolicyService;

/**
 * 
 * @author r.benabdesselam
 * @Date 27 févr. 2025
 *
 */
@RestController
@RequestMapping("/policies")
public class InsurancePolicyController {

    private final InsurancePolicyService insurancePolicyService;

    @Autowired
    public InsurancePolicyController(InsurancePolicyService insurancePolicyService) {
        this.insurancePolicyService = insurancePolicyService;
    }

    // Lister toutes les polices
    @GetMapping
    public List<InsurancePolicy> getAllPolicies() {
        return insurancePolicyService.getAllPolicies();
    }
    
    // Lire une police par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPolicyById(@PathVariable int id) {
        Optional<InsurancePolicy> policy = insurancePolicyService.getPolicyById(id);
        if (policy.isPresent()) {
            return ResponseEntity.ok(policy);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insurance policy not found with ID: " + id);
        }
    }
    
    
    // Créer une nouvelle police
    @PostMapping
    public ResponseEntity<?> createPolicy(@Valid @RequestBody InsurancePolicy policy) {
        try {
            InsurancePolicy savedPolicy = insurancePolicyService.createPolicy(policy);
            return ResponseEntity.ok(savedPolicy);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Mettre à jour une police
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePolicy(
            @PathVariable int id, @RequestBody InsurancePolicy updatedPolicy) {
        try {
            InsurancePolicy policy = insurancePolicyService.updatePolicy(id, updatedPolicy);
            return ResponseEntity.ok(policy);
        } catch (IllegalArgumentException e) {
            // Si l'objet est incomplet, on renvoie une erreur détaillée
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            // Si l'entité n'est pas trouvée, on renvoie un message 404 avec l'erreur
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

 // Supprimer une police
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePolicy(@PathVariable int id) {
        try {
            insurancePolicyService.deletePolicy(id);
            return ResponseEntity.ok("Insurance policy deleted successfully."); // Message de succès
        } catch (EmptyResultDataAccessException e) {
            // Retourner un message "Not Found" avec le code 404 si la police n'existe pas
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insurance policy not found with ID: " + id);
        }
    }
}
