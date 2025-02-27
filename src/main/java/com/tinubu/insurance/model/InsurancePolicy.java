package com.tinubu.insurance.model;

import java.time.LocalDate;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.tinubu.insurance.exception.StartDateBeforeEndDate;


/**
 * 
 * @author r.benabdesselam
 * @Date 27 févr. 2025
 *
 */
@Entity
@Cacheable
@Table(name = "insurance_policies")
public class InsurancePolicy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message = "Le nom de la police est obligatoire")
	@Column(name = "policy_name", nullable = false)
	private String policyName;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Le statut est obligatoire")
	@Column(name = "policy_status", nullable = false)
	private PolicyStatus policyStatus;

	@NotNull(message = "La date de début est obligatoire")
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@NotNull(message = "La date de fin est obligatoire")
	//@StartDateBeforeEndDate(message = "La date de fin doit être après la date de début et dans le futur")
	@Future(message = "La date de fin doit être dans le futur")
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDate createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDate updatedAt;



	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDate.now();
		this.updatedAt = LocalDate.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDate.now();
	}

	// Getters et Setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public PolicyStatus getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(PolicyStatus policyStatus) {
		this.policyStatus = policyStatus;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}
}
