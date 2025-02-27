package com.tinubu.insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * @author r.benabdesselam
 * @Date 26 févr. 2025
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = "com.tinubu.insurance**")
@EntityScan(basePackages = "com.tinubu.insurance.model")
public class InsuranceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InsuranceApplication.class, args);
    }
}
