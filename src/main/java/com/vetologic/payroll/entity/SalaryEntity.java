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
@Table(name = "SALARY")
public class SalaryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SALARY_ID")
	private int salaryId;

	@Column(name = "ACTIVE_FLAG")
	private int active;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private EmployeeEntity employeeEntity;

	@Column(name = "BASIC_SALARY")
	private String basicSalary;
	
	@Column(name = "HRA_SALARY")
	private String hraSalary;

	@Column(name = "SPECIAL_ALLOWANCE")
	private String specialAllowance;

	@Column(name = "OTHERS_SALARY")
	private String othersSalary;

	@Column(name = "CONVEYANCE_ALLOWANCE")
	private String conveyanceAllowance;
	
	@Column(name = "MEDICAL_ALLOWANCE")
	private String medicalAllowance;
	
	@Column(name = "BONUS_OR_INCENTIVE")
	private String bonusOrIncentive;
	
	@Column(name = "DEARNESS_ALLOWANCE")
	private String dearnessAllowance;
	
	@Column(name = "TOTAL_EARNINGS")
	private String totalEarnings;

	@Column(name = "PT")
	private String pt;

	@Column(name = "TDS")
	private String tds;

	@JoinColumn(name = "PF")
	private String pf;

	@Column(name = "ESIC")
	private String esic;
	
	@Column(name = "ADVANCE_SALARY")
	private String advanceSalary;

	@Column(name = "TOTAL_DEDUCTION")
	private String totalDeduction;

	@Column(name = "GROSS_SALARY")
	private String grossSalary;
	
	@Column(name = "NET_SALARY")
	private String netSalary;
	
	@Column(name ="SALARY_STATUS")
	private String salaryStatus;
	
	@Column(name ="REASON_FOR_SALARY_REJECTION")
	private String reasonForSalaryRejection;

	public int getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(int salaryId) {
		this.salaryId = salaryId;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}

	public String getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(String basicSalary) {
		this.basicSalary = basicSalary;
	}

	public String getHraSalary() {
		return hraSalary;
	}

	public void setHraSalary(String hraSalary) {
		this.hraSalary = hraSalary;
	}

	public String getSpecialAllowance() {
		return specialAllowance;
	}

	public void setSpecialAllowance(String specialAllowance) {
		this.specialAllowance = specialAllowance;
	}

	public String getOthersSalary() {
		return othersSalary;
	}

	public void setOthersSalary(String othersSalary) {
		this.othersSalary = othersSalary;
	}

	public String getConveyanceAllowance() {
		return conveyanceAllowance;
	}

	public void setConveyanceAllowance(String conveyanceAllowance) {
		this.conveyanceAllowance = conveyanceAllowance;
	}

	public String getMedicalAllowance() {
		return medicalAllowance;
	}

	public void setMedicalAllowance(String medicalAllowance) {
		this.medicalAllowance = medicalAllowance;
	}

	public String getBonusOrIncentive() {
		return bonusOrIncentive;
	}

	public void setBonusOrIncentive(String bonusOrIncentive) {
		this.bonusOrIncentive = bonusOrIncentive;
	}

	public String getDearnessAllowance() {
		return dearnessAllowance;
	}

	public void setDearnessAllowance(String dearnessAllowance) {
		this.dearnessAllowance = dearnessAllowance;
	}

	public String getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(String totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	public String getTds() {
		return tds;
	}

	public void setTds(String tds) {
		this.tds = tds;
	}

	public String getPf() {
		return pf;
	}

	public void setPf(String pf) {
		this.pf = pf;
	}

	public String getEsic() {
		return esic;
	}

	public void setEsic(String esic) {
		this.esic = esic;
	}

	public String getAdvanceSalary() {
		return advanceSalary;
	}

	public void setAdvanceSalary(String advanceSalary) {
		this.advanceSalary = advanceSalary;
	}

	public String getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(String totalDeduction) {
		this.totalDeduction = totalDeduction;
	}

	public String getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(String grossSalary) {
		this.grossSalary = grossSalary;
	}

	public String getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(String netSalary) {
		this.netSalary = netSalary;
	}

	public String getSalaryStatus() {
		return salaryStatus;
	}

	public void setSalaryStatus(String salaryStatus) {
		this.salaryStatus = salaryStatus;
	}

	public String getReasonForSalaryRejection() {
		return reasonForSalaryRejection;
	}

	public void setReasonForSalaryRejection(String reasonForSalaryRejection) {
		this.reasonForSalaryRejection = reasonForSalaryRejection;
	}


}
