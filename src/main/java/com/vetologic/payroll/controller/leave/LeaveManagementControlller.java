package com.vetologic.payroll.controller.leave;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vetologic.payroll.entity.EmployeeEntity;
import com.vetologic.payroll.entity.LeaveApplyEntity;
import com.vetologic.payroll.entity.LeaveManagementEntity;
import com.vetologic.payroll.entity.LeaveTypeMasterEntity;
import com.vetologic.payroll.entity.LeavesSortByName;
import com.vetologic.payroll.service.leave.LeaveInterface;
import com.vetologic.payroll.service.leave.LeaveManagementInterface;
import com.vetologic.payroll.service.leave.LeaveManagementService;
import com.vetologic.payroll.service.leave.LevaeMasterInterface;
import com.vetologic.payroll.service.salary.InvestmentActual;
import com.vetologic.payroll.service.salary.InvestmentPlanned;
import com.vetologic.payroll.service.salary.SalaryInterface;
import com.vetologic.payroll.service.salary.TaxRate;
import com.vetologic.payroll.service.user.EmployeService;
import com.vetologic.payroll.service.user.EmployeeService;
import com.vetologic.payroll.util.MailUtil;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LeaveManagementControlller {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	LeaveManagementService leaveManagementService;
	
	@Autowired
	EmployeService employeService;
	
	@Autowired
	InvestmentPlanned investmentPlanned;
	
	@Autowired
	InvestmentActual investmentActual;
	
	@Autowired
	TaxRate taxRate;
	
	@Autowired
	SalaryInterface salaryInterface;
	
	@Autowired
	MailUtil mailUtil;
	
	@Autowired
	LevaeMasterInterface leaveMasterInterface;
	
	@Autowired
	LeaveManagementInterface leaveManagementInterface;
	
	@Autowired
	LeaveInterface leaveInterface;
	
	@GetMapping(value = "/getLeaveMasterData")
	public List<LeaveTypeMasterEntity> getLeaveMasterData(){
		return leaveMasterInterface.findAll();
	}
	
	@GetMapping(value = "/checkLeaveTypeAlreadyexitForUser/{empId}/{empIdleaveMasterId}")
	public Object checkLeaveTypeAlreadyexitForUser(@PathVariable int empId,@PathVariable int empIdleaveMasterId) {
		Map<String, Object> map = new HashMap<String, Object>();
		LeaveManagementEntity leaveManagement1 = leaveManagementService.getLeaveDetailsForUser(empId,empIdleaveMasterId);
		if(Objects.nonNull(leaveManagement1)) {
			map.put("leaveId", leaveManagement1.getLeaveId());
			map.put("noOfLeaveThere", leaveManagement1.getTotalNoOfLeavesThere());
			map.put("noOfLeavesTaken", leaveManagement1.getTotalNoOfLeavesTaken());
			map.put("noOfLeaveAvailable", leaveManagement1.getTotalNoOfLeavesAvailable());
		}
		if(leaveManagementService.checkLeaveTypeAlreadyexitForUser(empId,empIdleaveMasterId)) {
			map.put("success", true);
		}else {
			map.put("success", false);
		}
		return map;
	}
	
	@PostMapping(value = "/saveLeaveManagementDetails/{empId}/{leaveMasterId}")
	public Object saveLeaveManagementDetails(@PathVariable int empId,@PathVariable int leaveMasterId,@RequestBody LeaveManagementEntity leaveManagementEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setUserId(empId);
		LeaveTypeMasterEntity leaveTypeMasterEntity = new LeaveTypeMasterEntity();
		leaveTypeMasterEntity.setLeaveTypeId(leaveMasterId);
		leaveManagementEntity.setEmployeeEntity(employeeEntity);
		leaveManagementEntity.setLeaveType(leaveTypeMasterEntity);
		leaveManagementEntity.setActive(1);
		leaveManagementEntity.setTotalNoOfLeavesTaken("0");
		leaveManagementEntity.setTotalNoOfLeavesAvailable(leaveManagementEntity.getTotalNoOfLeavesThere());
		LeaveManagementEntity leaveManagementEntity2 = leaveManagementInterface.save(leaveManagementEntity);
		if(Objects.nonNull(leaveManagementEntity2)) {
			map.put("success", true);
		}else {
			map.put("success", false);
		}
		return map;
	}
	
	@GetMapping(value = "/getEmpLeaveList/{empid}")
	public Object getEmpLeaveList(@PathVariable int empid){
		Map<String, Object> map = new HashMap<String, Object>();
		List<LeaveManagementEntity> leaveManagementEntities = leaveManagementService.getEmpLeaveList(empid);
		Collections.sort(leaveManagementEntities, new LeavesSortByName());
		map.put("size", leaveManagementEntities.size());
		map.put("leaveManagementEntities", leaveManagementEntities);
		return map;
	}
	
	@GetMapping(value = "/getLeaveManagementDetailById/{leaveId}")
	public LeaveManagementEntity getLeaveManagementDetailById(@PathVariable int leaveId) {
		return leaveManagementInterface.findById(leaveId).get();
	}
	
	
	@PutMapping(value = "/updateLeaveManagementToDb/{leaveId}/{leavetype}")
	public Object updateLeaveManagementToDb(@PathVariable int leaveId,@PathVariable int leavetype,@RequestBody LeaveManagementEntity leaveManagementEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
		LeaveManagementEntity exEntity = leaveManagementInterface.findById(leaveId).get();
		if(Objects.nonNull(exEntity)) {
			LeaveTypeMasterEntity entity = new LeaveTypeMasterEntity();
			entity.setLeaveTypeId(leavetype);
			exEntity.setLeaveType(entity);
			exEntity.setTotalNoOfLeavesThere(leaveManagementEntity.getTotalNoOfLeavesThere());
			exEntity.setTotalNoOfLeavesAvailable(String.valueOf(Double.parseDouble(leaveManagementEntity.getTotalNoOfLeavesThere())-Double.parseDouble(exEntity.getTotalNoOfLeavesTaken())));
			LeaveManagementEntity savedEntity = leaveManagementInterface.save(exEntity);
			if(Objects.nonNull(savedEntity)){
				map.put("message", "Leave Updated Successfully");
				map.put("success", true);
			}else{
				map.put("success", false);
				map.put("message", "Leave Updated UnSuccessfully");
			}
		}else {
			map.put("success", false);
			map.put("message", "Leave Updated UnSuccessfully");
		}
		return map;
		}catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("message", "Leave Updated UnSuccessfully");
			return map;
		}
	}
	
	
	@GetMapping(value = "/showLeavePopup/{leaveTypeId}/{empId}")
	public Object showLeavePopup(@PathVariable int leaveTypeId,@PathVariable int empId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<LeaveManagementEntity> leaveManagement = leaveManagementService.getLeaveDetailsByUserIdAndLeaveTypeId(empId,leaveTypeId);
		List<LeaveApplyEntity> leaveApplies = leaveManagementService.getLeaveApplyDetailsByUserIdAndLeaveTypeId(leaveManagement.get(0));
		map.put("userLeaveDetail", leaveApplies);
		return map;
	}
	
	@DeleteMapping(value = "/deleteLeave/{leaveId}")
	public Object deleteLeave(@PathVariable int leaveId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			leaveManagementInterface.deleteById(leaveId);
			map.put("success", true);
		}catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		return map;
		
	}
	
	@GetMapping(value = "/getLeaveManagementDetailByEmpId/{empId}")
	public  List<LeaveManagementEntity> getLeaveManagementDetailByEmpId(@PathVariable int empId){
		List<LeaveManagementEntity> userLeaveList = leaveManagementService.getLeaveDetailsByUserId(empId);
		Collections.sort(userLeaveList, new LeavesSortByName());
		return userLeaveList;
	}
	
	@PutMapping(value = "/updateLeaveToDbS")
	public Object updateLeaveToDbS(@RequestBody LeaveManagementEntity leaveManagement,LeaveApplyEntity leaveApply) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println("leaveManagement "+leaveManagement.toString());
		LeaveManagementEntity leaveManagementDetail = leaveManagementInterface.findById(leaveManagement.getLeaveId()).get();
		leaveManagementDetail.setStartDate(leaveManagement.getStartDate());
		leaveManagementDetail.setEndDate(leaveManagement.getEndDate());
		
		leaveApply.setLeaveManagementEntity(leaveManagementDetail);
		leaveApply.setTotalNoOfLeavesTaken(leaveManagementDetail.getNoOfLeavesApplied());
		leaveApply.setTotalNoOfLeavesThere(leaveManagementDetail.getTotalNoOfLeavesThere());
		leaveApply.setStartDate(leaveManagement.getStartDate());
		leaveApply.setEndDate(leaveManagement.getEndDate());
		leaveApply.setNoOfLeavesApplied(leaveManagement.getNoOfLeavesApplied());
		leaveApply.setReasonForLeave(leaveManagement.getReasonForLeave());
		leaveApply.setActive(1);
		leaveApply.setLeaveTypeStatus(leaveManagement.getLeaveTypeStatus());
		leaveApply.setLeaveStatus("Pending");
		LeaveApplyEntity savedApplyEntity = leaveInterface.save(leaveApply);
		LeaveManagementEntity savLeaveManagementEntity = leaveManagementInterface.save(leaveManagementDetail);
		if(Objects.nonNull(savedApplyEntity) && Objects.nonNull(savLeaveManagementEntity)){
			map.put("message", "Leave Applied Successfully and Wait for the response From the Superviser");
			map.put("success", true);
		}else{
			map.put("success", false);
			map.put("message", "Leave Applied NotSuccessfully");
		}
		return map;
		}catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("message", "Leave Applied NotSuccessfully");
			return map;
		}
	}
	
	@GetMapping(value = "/getLeaveAppliesForApproveOrReject/{supName}")
	public List<LeaveApplyEntity> getLeaveAppliesForApproveOrReject(@PathVariable String supName){
		List<LeaveApplyEntity> leaveApply = null;
		leaveApply = leaveManagementService.getLeaveAppliesForApproveOrReject(supName);
		return leaveApply;
	}
	
	@GetMapping(value = "/getLeaveApplyById/{leaveApplyId}")
	public LeaveApplyEntity getLeaveApplyById(@PathVariable int leaveApplyId) {
		return leaveInterface.findById(leaveApplyId).get();
	}
	
	
	@GetMapping(value = "/approveLeave/{leaveApplyId}/{leaveId}")
	public Object approveLeave(@PathVariable int leaveApplyId,@PathVariable int leaveId) {
		System.out.println("leaveManagement.getLeaveId() "+leaveId);
		System.out.println("leaveApply.getLeaveApplyId() "+leaveApplyId);
		Map<String, Object> map = new HashMap<String, Object>();
		LeaveManagementEntity leaveManagementDetail = leaveManagementInterface.findById(leaveId).get();
		LeaveApplyEntity leaveApplyDetail = leaveInterface.findById(leaveApplyId).get();		
		if(leaveManagementDetail.getNoOfLeavesApplied() != null){
		leaveManagementDetail.setNoOfLeavesApplied(String.valueOf(Double.parseDouble(leaveManagementDetail.getNoOfLeavesApplied())+Double.parseDouble(leaveApplyDetail.getNoOfLeavesApplied())));
		}
		else{
			leaveManagementDetail.setNoOfLeavesApplied(String.valueOf(Math.round(Double.parseDouble("0")+Double.parseDouble(leaveApplyDetail.getNoOfLeavesApplied()))));
		}
		leaveManagementDetail.setReasonForLeave(leaveApplyDetail.getReasonForLeave());
		if(leaveManagementDetail.getTotalNoOfLeavesTaken() != null && leaveApplyDetail.getNoOfLeavesApplied() != null){
		leaveManagementDetail.setTotalNoOfLeavesTaken(String.valueOf(Double.parseDouble(leaveManagementDetail.getTotalNoOfLeavesTaken())+Double.parseDouble(leaveApplyDetail.getNoOfLeavesApplied())));
		}else{
			leaveManagementDetail.setTotalNoOfLeavesTaken(String.valueOf(Math.round(Double.parseDouble("0")+Double.parseDouble("0"))));
		}
		if(leaveManagementDetail.getNoOfLeavesApplied() != null){
		leaveManagementDetail.setTotalNoOfLeavesAvailable(String.valueOf(Double.parseDouble(leaveManagementDetail.getTotalNoOfLeavesThere()) - Double.parseDouble(leaveManagementDetail.getNoOfLeavesApplied())));
		}
		else{
			leaveManagementDetail.setTotalNoOfLeavesAvailable(String.valueOf(Math.round(Double.parseDouble(leaveManagementDetail.getTotalNoOfLeavesThere()) - Double.parseDouble("0"))));
		}
		if(leaveManagementDetail.getNoOfLeavesApplied() != null){
			leaveApplyDetail.setTotalNoOfLeavesAvailable(String.valueOf(Double.parseDouble(leaveManagementDetail.getTotalNoOfLeavesThere()) - Double.parseDouble(leaveManagementDetail.getNoOfLeavesApplied())));
		}else{
			leaveApplyDetail.setTotalNoOfLeavesAvailable(String.valueOf(Math.round(Double.parseDouble(leaveManagementDetail.getTotalNoOfLeavesThere()) - Double.parseDouble("0"))));
		}
		leaveApplyDetail.setLeaveStatus("Approved");
		LeaveApplyEntity savedApplyEntity = leaveInterface.save(leaveApplyDetail);
		LeaveManagementEntity savLeaveManagementEntity = leaveManagementInterface.save(leaveManagementDetail);
		if(Objects.nonNull(savedApplyEntity) && Objects.nonNull(savLeaveManagementEntity)){
			map.put("success", true);
		}else{
			map.put("success", false);
		}
		return map;
	}
	
	
	@GetMapping(value = "/rejectLeave/{leaveApplyId}/{leaveId}/{reason}")
	public Object rejectLeave(@PathVariable int leaveApplyId,@PathVariable int leaveId,@PathVariable String reason) {
		Map<String, Object> map = new HashMap<String, Object>();
		LeaveApplyEntity leaveApplyDetail = leaveInterface.findById(leaveApplyId).get();
		leaveApplyDetail.setLeaveStatus("Reject");
		leaveApplyDetail.setReasonForLeaveRejection(reason);
		LeaveApplyEntity savedApplyEntity = leaveInterface.save(leaveApplyDetail);
		if(Objects.nonNull(savedApplyEntity)){
			map.put("success", true);
		}else{
			map.put("success", false);
		}
		return map;
	}
	
	
	@GetMapping(value = "/getUserApprovedLeaveStatus/{userId}")
	public List<LeaveApplyEntity> getUserApprovedLeaveStatus(@PathVariable int userId){
		return leaveManagementService.getUserApprovedLeaveStatus(userId);
	}
	
	@GetMapping(value = "/getUserRejectedLeaveStatus/{userId}")
	public List<LeaveApplyEntity> getUserRejectedLeaveStatus(@PathVariable int userId){
		return leaveManagementService.getUserRejectedLeaveStatus(userId);
	}
	
	
	
	
	
	
	

}
