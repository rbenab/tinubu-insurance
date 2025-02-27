package com.tinubu.insurance.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinubu.insurance.service.InsurancePolicyService;
import com.tinubu.insurance.service.InsurancePolicyServiceImpl;

/**
 * 
 * @author r.benabdesselam
 * @Date 27 févr. 2025
 *
 */
@RestController
@RequestMapping("/server")
public class serverController {

    private static final Logger LOGGER = Logger.getLogger(InsurancePolicyServiceImpl.class);

    @GetMapping("/status")
    public ResponseEntity<String> checkServerStatus() {
    	LOGGER.debug("Vérification du statut du serveur");
        return new ResponseEntity<>("Server Active", HttpStatus.OK);
    }

}
