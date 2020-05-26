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
@Table(name = "LEAVE_MANAGEMENT")
public class LeaveManagementEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LEAVE_ID")
	private int leaveId;
	
	@Column(name = "ACTIVE_FLAG")
	private int active;
	
	@ManyToOne
	@JoinColumn(name = "LEAVE_TYPE_ID")
	private LeaveTypeMasterEntity leaveType;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private EmployeeEntity employeeEntity;
	
	@Column(name ="TOTAL_NO_OF_LEAVES_THERE")
	private String totalNoOfLeavesThere;
	
	@Column(name ="TOTAL_NO_OF_LEAVES_TAKEN")
	private String totalNoOfLeavesTaken;
	
	@Column(name ="TOTAL_NO_OF_LEAVES_AVAILABLE")
	private String totalNoOfLeavesAvailable;
	
	@Column(name ="START_DATE")
	private String startDate;
	
	@Column(name ="END_DATE")
	private String endDate;
	
	@Column(name ="REASON_FOR_LEAVE")
	private String reasonForLeave;
	
	@Column(name ="NO_OF_LEAVES_APPLIED")
	private String noOfLeavesApplied;
	
	@Column(name ="LEAVE_STATUS")
	private String leaveStatus;
	
	@Column(name ="LEAVE_TYPE_STATUS")
	private String leaveTypeStatus;

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public LeaveTypeMasterEntity getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveTypeMasterEntity leaveType) {
		this.leaveType = leaveType;
	}
	
	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReasonForLeave() {
		return reasonForLeave;
	}

	public void setReasonForLeave(String reasonForLeave) {
		this.reasonForLeave = reasonForLeave;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public String getTotalNoOfLeavesThere() {
		return totalNoOfLeavesThere;
	}

	public void setTotalNoOfLeavesThere(String totalNoOfLeavesThere) {
		this.totalNoOfLeavesThere = totalNoOfLeavesThere;
	}

	public String getTotalNoOfLeavesTaken() {
		return totalNoOfLeavesTaken;
	}

	public void setTotalNoOfLeavesTaken(String totalNoOfLeavesTaken) {
		this.totalNoOfLeavesTaken = totalNoOfLeavesTaken;
	}

	public String getTotalNoOfLeavesAvailable() {
		return totalNoOfLeavesAvailable;
	}

	public void setTotalNoOfLeavesAvailable(String totalNoOfLeavesAvailable) {
		this.totalNoOfLeavesAvailable = totalNoOfLeavesAvailable;
	}

	public String getNoOfLeavesApplied() {
		return noOfLeavesApplied;
	}

	public void setNoOfLeavesApplied(String noOfLeavesApplied) {
		this.noOfLeavesApplied = noOfLeavesApplied;
	}

	@Override
	public String toString() {
		return "LeaveManagementEntity [leaveId=" + leaveId + ", active=" + active + ", leaveType=" + leaveType
				+ ", employeeEntity=" + employeeEntity + ", totalNoOfLeavesThere=" + totalNoOfLeavesThere
				+ ", totalNoOfLeavesTaken=" + totalNoOfLeavesTaken + ", totalNoOfLeavesAvailable="
				+ totalNoOfLeavesAvailable + ", startDate=" + startDate + ", endDate=" + endDate + ", reasonForLeave="
				+ reasonForLeave + ", noOfLeavesApplied=" + noOfLeavesApplied + ", leaveStatus=" + leaveStatus + "]";
	}

	public String getLeaveTypeStatus() {
		return leaveTypeStatus;
	}

	public void setLeaveTypeStatus(String leaveTypeStatus) {
		this.leaveTypeStatus = leaveTypeStatus;
	}
	
}
