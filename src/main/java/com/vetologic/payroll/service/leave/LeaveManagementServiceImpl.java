package com.vetologic.payroll.service.leave;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetologic.payroll.entity.EmployeeEntity;
import com.vetologic.payroll.entity.LeaveApplyEntity;
import com.vetologic.payroll.entity.LeaveManagementEntity;
import com.vetologic.payroll.repository.leave.LeaveManagementRepository;

@Service
public class LeaveManagementServiceImpl implements LeaveManagementService{
	
	@Autowired
	LeaveManagementRepository leaveManagementRepository;

	@Override
	public List<LeaveManagementEntity> getLeaveDetailsByUserId(int userId) {
		return leaveManagementRepository.getLeaveDetailsByUserId(userId);
	}

	@Override
	public List<LeaveApplyEntity> getLeaveStatusOfEmployees(EmployeeEntity employeeEntity) {
		return leaveManagementRepository.getLeaveStatusOfEmployees(employeeEntity);
	}

	@Override
	public List<LeaveApplyEntity> getRejectedLeaveStatusOfEmployees(EmployeeEntity employeeEntity) {
		return leaveManagementRepository.getRejectedLeaveStatusOfEmployees(employeeEntity);
	}

	@Override
	public List<LeaveApplyEntity> getLeaveAppliesForApproveOrReject(String userName) {
		return leaveManagementRepository.getLeaveAppliesForApproveOrReject(userName);
	}

	@Override
	public boolean checkLeaveTypeAlreadyexitForUser(int empId, int empIdleaveMasterId) {
		return leaveManagementRepository.checkLeaveTypeAlreadyexitForUser(empId,empIdleaveMasterId);
	}

	@Override
	public List<LeaveManagementEntity> getEmpLeaveList(int empid) {
		return leaveManagementRepository.getEmpLeaveList(empid);
	}

	@Override
	public List<LeaveManagementEntity> getLeaveDetailsByUserIdAndLeaveTypeId(int empId, int leaveTypeId) {
		return leaveManagementRepository.getLeaveDetailsByUserIdAndLeaveTypeId(empId,leaveTypeId);
	}

	@Override
	public List<LeaveApplyEntity> getLeaveApplyDetailsByUserIdAndLeaveTypeId(
			LeaveManagementEntity leaveManagementEntity) {
		return leaveManagementRepository.getLeaveApplyDetailsByUserIdAndLeaveTypeId(leaveManagementEntity);
	}

	@Override
	public LeaveManagementEntity getLeaveDetailsForUser(int empId, int empIdleaveMasterId) {
		return leaveManagementRepository.getLeaveDetailsForUser(empId,empIdleaveMasterId);
	}

	@Override
	public List<LeaveApplyEntity> getUserApprovedLeaveStatus(int userId) {
		return leaveManagementRepository.getUserApprovedLeaveStatus(userId);
	}

	@Override
	public List<LeaveApplyEntity> getUserRejectedLeaveStatus(int userId) {
		return leaveManagementRepository.getUserRejectedLeaveStatus(userId);
	}

}
