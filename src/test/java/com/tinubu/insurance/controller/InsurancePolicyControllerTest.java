package com.tinubu.insurance.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinubu.insurance.model.InsurancePolicy;
import com.tinubu.insurance.model.PolicyStatus;
import com.tinubu.insurance.repository.InsurancePolicyCommandRepository;
import com.tinubu.insurance.repository.InsurancePolicyReadRepository;

/**
 * 
 * @author r.benabdesselam
 * @Date 27 févr. 2025
 *
 */
@WebMvcTest(InsurancePolicyController.class)  
public class InsurancePolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InsurancePolicyReadRepository readRepository;

    @MockBean
    private InsurancePolicyCommandRepository commandRepository;

    @BeforeEach
    public void setup() {
        // Aucune donnée préalable n'est nécessaire car on utilise des mocks
    }

    @Test
    public void shouldCreateInsurancePolicy() throws Exception {
    	InsurancePolicy policy = createInsurancePolicy();

        when(commandRepository.save(any(InsurancePolicy.class))).thenReturn(policy);  // Simuler l'enregistrement

        mockMvc.perform(post("/policies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.policyName").value("Policy Name"))
                .andExpect(jsonPath("$.startDate").value(LocalDate.now().plusDays(1).toString()))
                .andExpect(jsonPath("$.endDate").value(LocalDate.now().plusDays(2).toString()));

        verify(commandRepository, times(1)).save(any(InsurancePolicy.class));  // Vérifier que save() a été appelé
    }

    @Test
    public void shouldListInsurancePolicies() throws Exception {
      
        InsurancePolicy policy1 = createInsurancePolicy();
        policy1.setPolicyName("Policy 1");
        when(readRepository.findAll()).thenReturn(List.of(policy1));  // Simuler la lecture des polices

        mockMvc.perform(get("/policies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].policyName").value("Policy 1"))
                .andExpect(jsonPath("$").isArray());

        verify(readRepository, times(1)).findAll();  // Vérifier que findAll() a été appelé
    }

    @Test
    public void shouldGetInsurancePolicyById() throws Exception {
    	InsurancePolicy policy = createInsurancePolicy();
        policy.setId(1);

        when(readRepository.findById(1)).thenReturn(Optional.of(policy));  // Simuler la lecture d'une police par ID

        mockMvc.perform(get("/policies/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.policyName").value("Policy Name"))
                .andExpect(jsonPath("$.startDate").value(LocalDate.now().plusDays(1).toString()));

        verify(readRepository, times(1)).findById(1);  // Vérifier que findById() a été appelé
    }

    @Test
    public void shouldReturn404ForNonExistentPolicy() throws Exception {
        when(readRepository.findById(999)).thenReturn(Optional.empty());  // Simuler une police inexistante

        mockMvc.perform(get("/policies/{id}", 999))
                .andExpect(status().isNotFound());

        verify(readRepository, times(1)).findById(999);  // Vérifier que findById() a été appelé
    }

    @Test
    public void shouldUpdateInsurancePolicy() throws Exception {
        InsurancePolicy policy = createInsurancePolicy();
        policy.setId(1);

        when(readRepository.findById(1)).thenReturn(Optional.of(policy));  // Simuler la lecture de la police
        when(commandRepository.save(any(InsurancePolicy.class))).thenReturn(policy);  // Simuler la mise à jour

        policy.setPolicyName("Updated Policy");

        mockMvc.perform(put("/policies/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.policyName").value("Updated Policy"));

        verify(commandRepository, times(1)).save(any(InsurancePolicy.class));  // Vérifier que save() a été appelé pour la mise à jour
    }

    @Test
    public void shouldDeleteInsurancePolicy() throws Exception {
        InsurancePolicy policy = createInsurancePolicy();
        policy.setId(1);

        when(readRepository.findById(1)).thenReturn(Optional.of(policy));  // Simuler la lecture de la police existante

        mockMvc.perform(delete("/policies/{id}", 1))
        .andExpect(status().isOk()); // Attendre un statut 200 OK au lieu de 204 No Content
        

        verify(commandRepository, times(1)).deleteById(1);  // Vérifier que deleteById() a été appelé
    }
    
    //================Support
    private InsurancePolicy createInsurancePolicy() {
        InsurancePolicy policy = new InsurancePolicy();
        
        // Utilisation des setters pour initialiser l'objet
        policy.setPolicyName("Policy Name");
        policy.setStartDate(LocalDate.now().plusDays(1)); // Date de début (aujourd'hui + 1 jour)
        policy.setEndDate(LocalDate.now().plusDays(2));   // Date de fin (aujourd'hui + 2 jours)
        policy.setPolicyStatus(PolicyStatus.ACTIVE); // Utilisation d'une valeur par défaut pour l'exemple
        
        return policy;
    }
}
