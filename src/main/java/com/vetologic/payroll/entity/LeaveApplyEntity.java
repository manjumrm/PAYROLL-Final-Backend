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
@Table(name = "LEAVE_APPLY_FOR_EMPLOYEE")
public class LeaveApplyEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LEAVE_APPLY_ID")
	private int leaveApplyId;
	
	@Column(name = "ACTIVE_FLAG")
	private int active;
	
	@ManyToOne
	@JoinColumn(name = "LEAVE_MANAGEMENT_ID")
	private LeaveManagementEntity leaveManagementEntity;
	
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
	
	@Column(name ="REASON_FOR_LEAVE_Rejection")
	private String reasonForLeaveRejection;
	
	@Column(name ="NO_OF_LEAVES_APPLIED")
	private String noOfLeavesApplied;
	
	@Column(name ="LEAVE_STATUS")
	private String leaveStatus;
	
	@Column(name ="LEAVE_TYPE_STATUS")
	private String leaveTypeStatus;

	public int getLeaveApplyId() {
		return leaveApplyId;
	}

	public void setLeaveApplyId(int leaveApplyId) {
		this.leaveApplyId = leaveApplyId;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
	
	public LeaveManagementEntity getLeaveManagementEntity() {
		return leaveManagementEntity;
	}

	public void setLeaveManagementEntity(LeaveManagementEntity leaveManagementEntity) {
		this.leaveManagementEntity = leaveManagementEntity;
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

	public String getReasonForLeaveRejection() {
		return reasonForLeaveRejection;
	}

	public void setReasonForLeaveRejection(String reasonForLeaveRejection) {
		this.reasonForLeaveRejection = reasonForLeaveRejection;
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

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public String getLeaveTypeStatus() {
		return leaveTypeStatus;
	}

	public void setLeaveTypeStatus(String leaveTypeStatus) {
		this.leaveTypeStatus = leaveTypeStatus;
	}

	
}
