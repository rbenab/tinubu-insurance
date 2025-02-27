package com.tinubu.insurance.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tinubu.insurance.model.InsurancePolicy;
import com.tinubu.insurance.repository.InsurancePolicyCommandRepository;
import com.tinubu.insurance.repository.InsurancePolicyReadRepository;

/**
 * Test unitaires pour la classe InsurancePolicyService.
 * 
 * @author r.benabdesselam
 * @Date 27 février 2025
 */
class InsurancePolicyServiceTest {

    @Mock
    private InsurancePolicyReadRepository insurancePolicyReadRepository;
    @Mock
    private InsurancePolicyCommandRepository insurancePolicyCommandRepository;

    private InsurancePolicyService insurancePolicyService;

    @BeforeEach
    void setUp() {
        // Initialisation des mocks et du service
        MockitoAnnotations.openMocks(this);
        insurancePolicyService = new InsurancePolicyServiceImpl(insurancePolicyReadRepository, insurancePolicyCommandRepository);
    }

    // Test de la méthode getAllPolicies()
    @Test
    void testGetAllPolicies() {
        // Préparation des mocks
        InsurancePolicy policy = createPolicy(1, "Test Policy");
        when(insurancePolicyReadRepository.findAll()).thenReturn(List.of(policy));

        // Exécution de la méthode
        List<InsurancePolicy> result = insurancePolicyService.getAllPolicies();

        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Policy", result.get(0).getPolicyName());
    }

    // Test de la méthode getPolicyById()
    @Test
    void testGetPolicyById() {
        // Préparation des mocks
        InsurancePolicy policy = createPolicy(1, "Test Policy");
        when(insurancePolicyReadRepository.findById(1)).thenReturn(Optional.of(policy));

        // Exécution de la méthode
        Optional<InsurancePolicy> result = insurancePolicyService.getPolicyById(1);

        // Vérifications
        assertTrue(result.isPresent());
        assertEquals("Test Policy", result.get().getPolicyName());
    }

    // Test de la méthode createPolicy()
    @Test
    void testCreatePolicy() {
        // Préparation de l'entité à créer
        InsurancePolicy policy = createPolicy(null, "New Policy");
        
        // Mocking de la méthode save
        when(insurancePolicyCommandRepository.save(any(InsurancePolicy.class))).thenReturn(policy);

        // Exécution de la méthode
        InsurancePolicy createdPolicy = insurancePolicyService.createPolicy(policy);

        // Vérifications
        assertNotNull(createdPolicy);
        assertEquals("New Policy", createdPolicy.getPolicyName());
    }

    // Test de la méthode updatePolicy()   
    @Test
    void testUpdatePolicy() {
        // Création d'une politique existante avant la mise à jour
        InsurancePolicy existingPolicy = new InsurancePolicy();
        existingPolicy.setId(1);
        existingPolicy.setPolicyName("Old Policy");

        // Création de la politique mise à jour avec un paramètre modifié
        InsurancePolicy updatedPolicy = new InsurancePolicy();
        updatedPolicy.setId(1);
        updatedPolicy.setPolicyName("Updated Policy");

        // Mock des méthodes : on simule le retour de l'objet existant et la sauvegarde de la politique mise à jour
        when(insurancePolicyReadRepository.findById(1)).thenReturn(Optional.of(existingPolicy));
        when(insurancePolicyCommandRepository.save(any(InsurancePolicy.class))).thenReturn(updatedPolicy);

        // Exécution de la méthode updatePolicy
        InsurancePolicy result = insurancePolicyService.updatePolicy(1, updatedPolicy);

        // Vérification que la politique a bien été mise à jour
        assertNotNull(result); // Vérifie que le résultat n'est pas null
        assertEquals("Updated Policy", result.getPolicyName()); // Vérifie que le nom de la politique est bien mis à jour
    }

    // Test de la méthode deletePolicy()
    @Test
    void testDeletePolicy() {
        // Préparation des mocks
        InsurancePolicy policy = createPolicy(1, "Test Policy");
        when(insurancePolicyReadRepository.findById(1)).thenReturn(Optional.of(policy));

        // Exécution de la méthode
        insurancePolicyService.deletePolicy(1);

        // Vérification que delete a été appelé une fois
        verify(insurancePolicyCommandRepository, times(1)).deleteById(1);
    }

    //=====SUPPORT
    
    // Méthode utilitaire pour créer une police d'assurance
    private InsurancePolicy createPolicy(Integer id, String policyName) {
        InsurancePolicy policy = new InsurancePolicy();
        policy.setId(id);
        policy.setPolicyName(policyName);
        return policy;
    }
}
