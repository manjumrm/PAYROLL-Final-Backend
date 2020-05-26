package com.vetologic.payroll.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAX_RATE")
public class TaxRateEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "TAX_ID")
	private int taxId;
	
	@Column(name = "STARTING_AMOUNT")
	private String startingAmount;
	
	@Column(name = "ENDING_AMOUNT")
	private String endingAmount;
	
	@Column(name = "TAX_RATE")
	private String taxRateInPercentage;

	public int getTaxId() {
		return taxId;
	}

	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}

	public String getStartingAmount() {
		return startingAmount;
	}

	public void setStartingAmount(String startingAmount) {
		this.startingAmount = startingAmount;
	}

	public String getEndingAmount() {
		return endingAmount;
	}

	public void setEndingAmount(String endingAmount) {
		this.endingAmount = endingAmount;
	}

	public String getTaxRateInPercentage() {
		return taxRateInPercentage;
	}

	public void setTaxRateInPercentage(String taxRateInPercentage) {
		this.taxRateInPercentage = taxRateInPercentage;
	}

	@Override
	public String toString() {
		return "TaxRate [taxId=" + taxId + ", startingAmount=" + startingAmount + ", endingAmount=" + endingAmount
				+ ", taxRateInPercentage=" + taxRateInPercentage + "]";
	}

	
}
