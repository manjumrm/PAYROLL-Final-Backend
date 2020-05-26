package com.vetologic.payroll.repository.leave;

import java.util.List;

import com.vetologic.payroll.entity.EmployeeEntity;
import com.vetologic.payroll.entity.LeaveApplyEntity;
import com.vetologic.payroll.entity.LeaveManagementEntity;

public interface LeaveManagementRepository {

	List<LeaveManagementEntity> getLeaveDetailsByUserId(int userId);

	List<LeaveApplyEntity> getLeaveStatusOfEmployees(EmployeeEntity employeeEntity);

	List<LeaveApplyEntity> getRejectedLeaveStatusOfEmployees(EmployeeEntity employeeEntity);

	List<LeaveApplyEntity> getLeaveAppliesForApproveOrReject(String userName);

	boolean checkLeaveTypeAlreadyexitForUser(int empId, int empIdleaveMasterId);

	List<LeaveManagementEntity> getEmpLeaveList(int empid);

	List<LeaveManagementEntity> getLeaveDetailsByUserIdAndLeaveTypeId(int empId, int leaveTypeId);

	List<LeaveApplyEntity> getLeaveApplyDetailsByUserIdAndLeaveTypeId(LeaveManagementEntity leaveManagementEntity);

	LeaveManagementEntity getLeaveDetailsForUser(int empId, int empIdleaveMasterId);

	List<LeaveApplyEntity> getUserApprovedLeaveStatus(int userId);

	List<LeaveApplyEntity> getUserRejectedLeaveStatus(int userId);

}
