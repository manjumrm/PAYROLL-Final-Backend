package com.vetologic.payroll.entity;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "EMPLOYEE", uniqueConstraints = @UniqueConstraint(columnNames = { "EMPLOYEE_NAME", "EMAIL_ID" }))
public class EmployeeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private int userId;

	@Column(name = "EMPLOYEE_NAME")
	@NotEmpty
	@NotNull
	@Length(min = 1, max = 50)
	private String username;

	@Column(name = "EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name = "EMPLOYEE_NAME_AS_PER_AADHAR")
	private String employeeNameAsPerAadhar;
	
	@Column(name = "SUPERVISOR_NAME")
	private String supervisorName;
	
	@ManyToOne
	@JoinColumn(name = "DESIGNATION_ID")
	private DesignationEntity designationEntity;
	
	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID")
	private DepartmentEntity departmentEntity;

	@Column(name = "ACTIVE_FLAG")
	private int active;

	@Column(name = "DISPLAY_NAME")
	@NotEmpty
	@NotNull
	private String displayName;

	@Column(name = "PASSWORD")
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIMESTAMP")
	private Date userCreated;
	
	@Column(name = "EMAIL_ID")
	@Email
	@NotEmpty
	@NotNull
	private String emailId;

	@Column(name = "MOBILE_NO")
	@NotEmpty
	@NotNull
	private String mobile;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="ACCOUNT_NUMBER")
	private String accountNumber;
	
	@Column(name="IFSC_NUMBER")
	private String ifscNumber;
	
	@Column(name="BANK_NAME")
	private String bankName;
	
	@Column(name="BRANCH_NAME")
	private String bankBranchName;
	
	@Column(name="BRANCH_ADDRESS")
	private String bankBranchAddress;
	
	@Column(name="DATE_OF_BIRTH")
	private String dateOfBirth;
	
	@Column(name="AGE")
	private int age;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="PAN_NO")
	private String panNo;
	
	@Column(name="ADDHAR_NO")
	private String addharNo;
	
    @Lob
	@Column(name="IMAGE")
	private byte[] image;
    
    @Column(name="DATE_OF_JOINING")
	private String dateOfJoining;
    
    @Column(name="DATE_OF_RESIGNATION")
	private String dateOfResignation;
    
    @Column(name="DATE_OF_LEAVING")
	private String dateOfLeaving;
    
    @Column(name="USER_TYPE")
	private String userType;
    
    @Column(name ="USER_STATUS")
	private String userStatus;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeNameAsPerAadhar() {
		return employeeNameAsPerAadhar;
	}

	public void setEmployeeNameAsPerAadhar(String employeeNameAsPerAadhar) {
		this.employeeNameAsPerAadhar = employeeNameAsPerAadhar;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public DesignationEntity getDesignationEntity() {
		return designationEntity;
	}

	public void setDesignationEntity(DesignationEntity designationEntity) {
		this.designationEntity = designationEntity;
	}

	public DepartmentEntity getDepartmentEntity() {
		return departmentEntity;
	}

	public void setDepartmentEntity(DepartmentEntity departmentEntity) {
		this.departmentEntity = departmentEntity;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(Date userCreated) {
		this.userCreated = userCreated;
	}

	

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscNumber() {
		return ifscNumber;
	}

	public void setIfscNumber(String ifscNumber) {
		this.ifscNumber = ifscNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getBankBranchAddress() {
		return bankBranchAddress;
	}

	public void setBankBranchAddress(String bankBranchAddress) {
		this.bankBranchAddress = bankBranchAddress;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getAddharNo() {
		return addharNo;
	}

	public void setAddharNo(String addharNo) {
		this.addharNo = addharNo;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getDateOfResignation() {
		return dateOfResignation;
	}

	public void setDateOfResignation(String dateOfResignation) {
		this.dateOfResignation = dateOfResignation;
	}

	public String getDateOfLeaving() {
		return dateOfLeaving;
	}

	public void setDateOfLeaving(String dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	
	@Override
	public String toString() {
		return "EmployeeEntity [userId=" + userId + ", username=" + username + ", employeeId=" + employeeId
				+ ", employeeNameAsPerAadhar=" + employeeNameAsPerAadhar + ", supervisorName=" + supervisorName
				+ ", designationEntity=" + designationEntity + ", departmentEntity=" + departmentEntity + ", active="
				+ active + ", displayName=" + displayName + ", password=" + password + ", userCreated=" + userCreated
				+ ", emailId=" + emailId + ", mobile=" + mobile + ", address=" + address + ", city=" + city
				+ ", accountNumber=" + accountNumber + ", ifscNumber=" + ifscNumber + ", bankName=" + bankName
				+ ", bankBranchName=" + bankBranchName + ", bankBranchAddress=" + bankBranchAddress + ", dateOfBirth="
				+ dateOfBirth + ", age=" + age + ", gender=" + gender + ", panNo=" + panNo + ", addharNo=" + addharNo
				+ ", image=" + Arrays.toString(image) + ", dateOfJoining=" + dateOfJoining + ", dateOfResignation="
				+ dateOfResignation + ", dateOfLeaving=" + dateOfLeaving + ", userType=" + userType + ", userStatus="
				+ userStatus + "]";
	}
		
}
