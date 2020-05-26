package com.vetologic.payroll.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "INVESTMENT_DECLARATION_PLANNED")
public class InvestmentDeclarationPlannedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private EmployeeEntity employeeEntity;
	
	@Column(name = "EPF_AND_VPF_CONTRIBUTION")
	private String epfAndVpfContribution;
	
	@Column(name = "PUBLIC_PROVIDENR_FUND")
	private String publicProvidentFund;
	
	@Column(name = "SENIOR_CITIZEN_SAVING_SCHEME")
	private String seniorCitizensSavingScheme;
	
	@Column(name = "NSC")
	private String investmentPlusaccruedInterestbeforeMaturityYear;
	
	@Column(name = "TAX_SAVING_FIXED_DEPOSIT")
	private String taxSavingFixedDeposit;
	
	@Column(name = "TAX_SAVING_BONDS")
	private String taxSavingsBonds;
	
	@Column(name = "TAX_SAVING_MUTUAL_FUND")
	private String taxSavingMutualFund;
	
	@Column(name = "LIFE_INSURANCE_PREMIUMS")
	private String lifeInsurancePremiums;
	
	@Column(name = "NEW_PENSION_SCHEME")
	private String newPensionScheme;
	
	@Column(name = "MUTUAL_FUNDS")
	private String pensionPlanfromInsuranceCompaniesOrMutualFunds;
	
	@Column(name = "CCD_CENTRAL_GOVT_EMPLOYEE_PENSION_PLAN")
	private String cCDCentralGovtEmployeePensionPlan;
	
	@Column(name = "HOUSING_LOAN")
	private String housingLoan;
	
	@Column(name = "SUKANYA_SAMRIDDHI_ACCOUNT")
	private String sukanyaSamriddhiAccount;
	
	@Column(name = "STAMP_DUTY_REGISTRATION_CHARGES")
	private String stampDutyAndRegistrationCharges;
	
	@Column(name = "TUTION_FEES_FOR_TWO_CHILDREN")
	private String tuitionFeesforTwochildren;
	
	@Column(name = "80_D_MEDICAL_INSURANCE_PREMIUMS_FOR_SELF")
	private String medicalInsurancePremiumsForSelf;
	
	@Column(name = "80_D_MEDICAL_INSURANCE_PREMIUMS_FOR_PARENTS")
	private String medicalInsurancePremiumsForParents;
	
	@Column(name = "80_E_INTIAL_PAID_ON_EDUCATION_LOAN")
	private String intialPaidOnEducationLoan;
	
	@Column(name = "80_DD_MEDICAL_TREATMENT_OF_HANDICAPPED_DEPENDENT")
	private String medicalTreatmentOfHandicappedDependent;
	
	@Column(name = "80_DDB_EXPENDITURE_ON_SELECTED_MEDICAL_TREATMENT")
	private String  expenditureOnSelectedMedicalTreatmentForSelfOrDependent;
	
	@Column(name = "80G_80GGA_80GGC_DONATION_TO_APPROVED_FUNDS")
	private String  donationToApprovedFunds;
	
	@Column(name = "80GD_FOR_RENT_IN_CASE_OF_NO_HRA_COMPONENT_BUDGET_2016")
	private String  rentInCaseOfNoHRAComponent;

	@Column(name = "TOTAL_AMOUNT")
	private String totalAmount;
	
	@Column(name = "TOTAL_AMOUNT_FOR_80C")
	private String totalAmountFor80C;
	
	@Column(name = "TOTAL_AMOUNT_FOR_80D")
	private String totalAmountFor80D;
	
	@Column(name = "STATUS")
	private String status;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}

	public String getEpfAndVpfContribution() {
		return epfAndVpfContribution;
	}

	public void setEpfAndVpfContribution(String epfAndVpfContribution) {
		this.epfAndVpfContribution = epfAndVpfContribution;
	}

	public String getPublicProvidentFund() {
		return publicProvidentFund;
	}

	public void setPublicProvidentFund(String publicProvidentFund) {
		this.publicProvidentFund = publicProvidentFund;
	}

	public String getSeniorCitizensSavingScheme() {
		return seniorCitizensSavingScheme;
	}

	public void setSeniorCitizensSavingScheme(String seniorCitizensSavingScheme) {
		this.seniorCitizensSavingScheme = seniorCitizensSavingScheme;
	}

	public String getInvestmentPlusaccruedInterestbeforeMaturityYear() {
		return investmentPlusaccruedInterestbeforeMaturityYear;
	}

	public void setInvestmentPlusaccruedInterestbeforeMaturityYear(String investmentPlusaccruedInterestbeforeMaturityYear) {
		this.investmentPlusaccruedInterestbeforeMaturityYear = investmentPlusaccruedInterestbeforeMaturityYear;
	}

	public String getTaxSavingFixedDeposit() {
		return taxSavingFixedDeposit;
	}

	public void setTaxSavingFixedDeposit(String taxSavingFixedDeposit) {
		this.taxSavingFixedDeposit = taxSavingFixedDeposit;
	}

	public String getTaxSavingsBonds() {
		return taxSavingsBonds;
	}

	public void setTaxSavingsBonds(String taxSavingsBonds) {
		this.taxSavingsBonds = taxSavingsBonds;
	}

	public String getTaxSavingMutualFund() {
		return taxSavingMutualFund;
	}

	public void setTaxSavingMutualFund(String taxSavingMutualFund) {
		this.taxSavingMutualFund = taxSavingMutualFund;
	}

	public String getLifeInsurancePremiums() {
		return lifeInsurancePremiums;
	}

	public void setLifeInsurancePremiums(String lifeInsurancePremiums) {
		this.lifeInsurancePremiums = lifeInsurancePremiums;
	}

	public String getNewPensionScheme() {
		return newPensionScheme;
	}

	public void setNewPensionScheme(String newPensionScheme) {
		this.newPensionScheme = newPensionScheme;
	}

	public String getPensionPlanfromInsuranceCompaniesOrMutualFunds() {
		return pensionPlanfromInsuranceCompaniesOrMutualFunds;
	}

	public void setPensionPlanfromInsuranceCompaniesOrMutualFunds(String pensionPlanfromInsuranceCompaniesOrMutualFunds) {
		this.pensionPlanfromInsuranceCompaniesOrMutualFunds = pensionPlanfromInsuranceCompaniesOrMutualFunds;
	}

	public String getcCDCentralGovtEmployeePensionPlan() {
		return cCDCentralGovtEmployeePensionPlan;
	}

	public void setcCDCentralGovtEmployeePensionPlan(String cCDCentralGovtEmployeePensionPlan) {
		this.cCDCentralGovtEmployeePensionPlan = cCDCentralGovtEmployeePensionPlan;
	}

	public String getHousingLoan() {
		return housingLoan;
	}

	public void setHousingLoan(String housingLoan) {
		this.housingLoan = housingLoan;
	}

	public String getSukanyaSamriddhiAccount() {
		return sukanyaSamriddhiAccount;
	}

	public void setSukanyaSamriddhiAccount(String sukanyaSamriddhiAccount) {
		this.sukanyaSamriddhiAccount = sukanyaSamriddhiAccount;
	}

	public String getStampDutyAndRegistrationCharges() {
		return stampDutyAndRegistrationCharges;
	}

	public void setStampDutyAndRegistrationCharges(String stampDutyAndRegistrationCharges) {
		this.stampDutyAndRegistrationCharges = stampDutyAndRegistrationCharges;
	}

	public String getTuitionFeesforTwochildren() {
		return tuitionFeesforTwochildren;
	}

	public void setTuitionFeesforTwochildren(String tuitionFeesforTwochildren) {
		this.tuitionFeesforTwochildren = tuitionFeesforTwochildren;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalAmountFor80C() {
		return totalAmountFor80C;
	}

	public void setTotalAmountFor80C(String totalAmountFor80C) {
		this.totalAmountFor80C = totalAmountFor80C;
	}

	public String getTotalAmountFor80D() {
		return totalAmountFor80D;
	}

	public void setTotalAmountFor80D(String totalAmountFor80D) {
		this.totalAmountFor80D = totalAmountFor80D;
	}

	public String getMedicalInsurancePremiumsForSelf() {
		return medicalInsurancePremiumsForSelf;
	}

	public void setMedicalInsurancePremiumsForSelf(String medicalInsurancePremiumsForSelf) {
		this.medicalInsurancePremiumsForSelf = medicalInsurancePremiumsForSelf;
	}

	public String getMedicalInsurancePremiumsForParents() {
		return medicalInsurancePremiumsForParents;
	}

	public void setMedicalInsurancePremiumsForParents(String medicalInsurancePremiumsForParents) {
		this.medicalInsurancePremiumsForParents = medicalInsurancePremiumsForParents;
	}

	public String getIntialPaidOnEducationLoan() {
		return intialPaidOnEducationLoan;
	}

	public void setIntialPaidOnEducationLoan(String intialPaidOnEducationLoan) {
		this.intialPaidOnEducationLoan = intialPaidOnEducationLoan;
	}

	public String getMedicalTreatmentOfHandicappedDependent() {
		return medicalTreatmentOfHandicappedDependent;
	}

	public void setMedicalTreatmentOfHandicappedDependent(String medicalTreatmentOfHandicappedDependent) {
		this.medicalTreatmentOfHandicappedDependent = medicalTreatmentOfHandicappedDependent;
	}

	public String getExpenditureOnSelectedMedicalTreatmentForSelfOrDependent() {
		return expenditureOnSelectedMedicalTreatmentForSelfOrDependent;
	}

	public void setExpenditureOnSelectedMedicalTreatmentForSelfOrDependent(
			String expenditureOnSelectedMedicalTreatmentForSelfOrDependent) {
		this.expenditureOnSelectedMedicalTreatmentForSelfOrDependent = expenditureOnSelectedMedicalTreatmentForSelfOrDependent;
	}

	public String getDonationToApprovedFunds() {
		return donationToApprovedFunds;
	}

	public void setDonationToApprovedFunds(String donationToApprovedFunds) {
		this.donationToApprovedFunds = donationToApprovedFunds;
	}

	public String getRentInCaseOfNoHRAComponent() {
		return rentInCaseOfNoHRAComponent;
	}

	public void setRentInCaseOfNoHRAComponent(String rentInCaseOfNoHRAComponent) {
		this.rentInCaseOfNoHRAComponent = rentInCaseOfNoHRAComponent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
