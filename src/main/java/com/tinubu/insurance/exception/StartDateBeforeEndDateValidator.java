package com.tinubu.insurance.exception;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tinubu.insurance.model.InsurancePolicy;

/**
 * 
 * @author r.benabdesselam
 * @Date 27 févr. 2025
 *
 */
public class StartDateBeforeEndDateValidator implements ConstraintValidator<StartDateBeforeEndDate, InsurancePolicy> {

    @Override
    public void initialize(StartDateBeforeEndDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(InsurancePolicy policy, ConstraintValidatorContext context) {
        if (policy == null) {
            return true; // La validation sera faite par les annotations comme @NotNull
        }

        LocalDate startDate = policy.getStartDate();
        LocalDate endDate = policy.getEndDate();

        if (startDate == null || endDate == null) {
            return true; // Vous pouvez choisir de valider ce cas ou le gérer autrement
        }

        // Vérifie que la date de fin est après la date de début et dans le futur
        boolean isValid = endDate.isAfter(startDate) && endDate.isAfter(LocalDate.now());

        if (!isValid) {
            // Ajoutez un message d'erreur personnalisé
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La date de fin doit être après la date de début et dans le futur.")
                   .addConstraintViolation();
        }

        return isValid;
    }
}


