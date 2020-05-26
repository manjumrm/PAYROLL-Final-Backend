package com.vetologic.payroll.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetologic.payroll.entity.BankEntity;
import com.vetologic.payroll.entity.ContactUsEntity;
import com.vetologic.payroll.entity.DepartmentEntity;
import com.vetologic.payroll.entity.DesignationEntity;
import com.vetologic.payroll.entity.EmployeeEntity;
import com.vetologic.payroll.entity.InvestmentDeclarationActualEntity;
import com.vetologic.payroll.entity.InvestmentDeclarationPlannedEntity;
import com.vetologic.payroll.entity.SalaryEntity;
import com.vetologic.payroll.entity.TaxRateEntity;
import com.vetologic.payroll.repository.user.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public EmployeeEntity getEmployeeDetail(EmployeeEntity employeeEntity) {
		return employeeRepository.getEmployeeDetail(employeeEntity);
	}

	@Override
	public List<SalaryEntity> getApprovedSalary() {
		return employeeRepository.getApprovedSalary();
	}

	@Override
	public List<SalaryEntity> getRejectedSalary() {
		return employeeRepository.getRejectedSalary();
	}

	@Override
	public List<DesignationEntity> getDesignationList() {
		return employeeRepository.getDesignationList();
	}

	@Override
	public List<DepartmentEntity> getDepartmentList() {
		return employeeRepository.getDepartmentList();
	}

	@Override
	public List<BankEntity> getBankList() {
		return employeeRepository.getBankList();
	}

	@Override
	public boolean saveEmployeeDetails(EmployeeEntity employeeEntity) {
		return employeeRepository.saveEmployeeDetails(employeeEntity);
	}

	@Override
	public boolean toCheckEmpNameAlreadyExistOrNot(String empName) {
		return employeeRepository.toCheckEmpNameAlreadyExistOrNot(empName);
	}

	@Override
	public boolean toCheckEmpEmailAlreadyExistOrNot(String empEmail) {
		return employeeRepository.toCheckEmpEmailAlreadyExistOrNot(empEmail);
	}

	@Override
	public boolean toCheckSupNameAlreadyExistOrNot(String supName) {
		return employeeRepository.toCheckSupNameAlreadyExistOrNot(supName);
	}

	@Override
	public List<EmployeeEntity> getAllEmployeeDetails() {
		return employeeRepository.getAllEmployeeDetails();
	}

	@Override
	public boolean deleteEmployee(int eId) {
		return employeeRepository.deleteEmployee(eId);
	}

	@Override
	public InvestmentDeclarationPlannedEntity getEmpInvestmentDecPlanned(int userId) {
		return employeeRepository.getEmpInvestmentDecPlanned(userId);
	}

	@Override
	public InvestmentDeclarationActualEntity getEmpInvestmentDecActual(int userId) {
		return employeeRepository.getEmpInvestmentDecActual(userId);
	}

	@Override
	public List<TaxRateEntity> getAllTaxRateList() {
		return employeeRepository.getAllTaxRateList();
	}

	@Override
	public boolean toCheckInvestmentDeclaration(int userId) {
		return employeeRepository.toCheckInvestmentDeclaration(userId);
	}

	@Override
	public List<SalaryEntity> getAllSalaryDetails() {
		return employeeRepository.getAllSalaryDetails();
	}

	@Override
	public boolean deleteSalary(int salId) {
		return employeeRepository.deleteSalary(salId);
	}

	@Override
	public boolean checkEmail(String forgotPasswordEmail) {
		return employeeRepository.checkEmail(forgotPasswordEmail);
	}

	@Override
	public boolean changeForgotPassword(String forgotPasswordEmail, String newPassword) {
		return employeeRepository.changeForgotPassword(forgotPasswordEmail,newPassword);
	}

	@Override
	public boolean saveContactUsDetails(ContactUsEntity contactUS) {
		return employeeRepository.saveContactUsDetails(contactUS);
	}

	@Override
	public List<SalaryEntity> approveOrRejectSalary(String userName) {
		return employeeRepository.approveOrRejectSalary(userName);
	}

	@Override
	public List<SalaryEntity> getSalaryAppliesForApproveOrReject(String username) {
		return employeeRepository.getSalaryAppliesForApproveOrReject(username);
	}

	@Override
	public List<SalaryEntity> getApprovedSalary(int userId) {
		return employeeRepository.getApprovedSalary(userId);
	}

	@Override
	public List<SalaryEntity> getRejectedSalary(int userId) {
		return employeeRepository.getRejectedSalary(userId);
	}
	

}
