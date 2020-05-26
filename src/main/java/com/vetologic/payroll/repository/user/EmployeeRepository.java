package com.vetologic.payroll.repository.user;

import java.util.List;

import com.vetologic.payroll.entity.BankEntity;
import com.vetologic.payroll.entity.ContactUsEntity;
import com.vetologic.payroll.entity.DepartmentEntity;
import com.vetologic.payroll.entity.DesignationEntity;
import com.vetologic.payroll.entity.EmployeeEntity;
import com.vetologic.payroll.entity.InvestmentDeclarationActualEntity;
import com.vetologic.payroll.entity.InvestmentDeclarationPlannedEntity;
import com.vetologic.payroll.entity.SalaryEntity;
import com.vetologic.payroll.entity.TaxRateEntity;

public interface EmployeeRepository {

	EmployeeEntity getEmployeeDetail(EmployeeEntity employeeEntity);

	List<SalaryEntity> getApprovedSalary();

	List<SalaryEntity> getRejectedSalary();

	List<DesignationEntity> getDesignationList();

	List<DepartmentEntity> getDepartmentList();

	List<BankEntity> getBankList();

	boolean saveEmployeeDetails(EmployeeEntity employeeEntity);

	boolean toCheckEmpNameAlreadyExistOrNot(String empName);

	boolean toCheckEmpEmailAlreadyExistOrNot(String empEmail);

	boolean toCheckSupNameAlreadyExistOrNot(String supName);

	List<EmployeeEntity> getAllEmployeeDetails();

	boolean deleteEmployee(int eId);

	InvestmentDeclarationPlannedEntity getEmpInvestmentDecPlanned(int userId);

	InvestmentDeclarationActualEntity getEmpInvestmentDecActual(int userId);

	List<TaxRateEntity> getAllTaxRateList();

	boolean toCheckInvestmentDeclaration(int userId);

	List<SalaryEntity> getAllSalaryDetails();

	boolean deleteSalary(int salId);

	boolean checkEmail(String forgotPasswordEmail);

	boolean changeForgotPassword(String forgotPasswordEmail, String newPassword);

	boolean saveContactUsDetails(ContactUsEntity contactUS);

	List<SalaryEntity> approveOrRejectSalary(String userName);

	List<SalaryEntity> getSalaryAppliesForApproveOrReject(String username);

	List<SalaryEntity> getApprovedSalary(int userId);

	List<SalaryEntity> getRejectedSalary(int userId);

}
