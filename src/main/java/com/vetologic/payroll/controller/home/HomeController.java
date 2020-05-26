package com.vetologic.payroll.controller.home;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.vetologic.payroll.entity.ContactUsEntity;
import com.vetologic.payroll.entity.EmployeeEntity;
import com.vetologic.payroll.entity.LeaveApplyEntity;
import com.vetologic.payroll.entity.LeaveManagementEntity;
import com.vetologic.payroll.entity.LeavesSortByName;
import com.vetologic.payroll.entity.ResponseVT;
import com.vetologic.payroll.entity.SalaryEntity;
import com.vetologic.payroll.service.leave.LeaveManagementService;
import com.vetologic.payroll.service.user.EmployeService;
import com.vetologic.payroll.service.user.EmployeeService;
import com.vetologic.payroll.util.AppUtil;
import com.vetologic.payroll.util.MailUtil;
import com.vetologic.payroll.util.PasswordUtil;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class HomeController {
	private static Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	LeaveManagementService leaveManagementService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	MailUtil mailUtil;
	
	@Autowired
	EmployeService employeService;
	
	
	
	@RequestMapping(value = "/home")
	public ResponseVT adminHome(HttpServletRequest httpServletRequest)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		ResponseVT responseVT = new ResponseVT();
		int userId = 0;
		String username=null;
		try {
			userId = (int) httpServletRequest.getSession().getAttribute("userId");
			username = (String) httpServletRequest.getSession().getAttribute("userName");
			List<LeaveManagementEntity> userLeaveList = leaveManagementService.getLeaveDetailsByUserId(userId);
			Collections.sort(userLeaveList, new LeavesSortByName());
			map.put("userLeaveList",userLeaveList);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setUserId(userId);
		employeeEntity.setUsername(username);
		List<LeaveApplyEntity> approvedLeaveApply = leaveManagementService.getLeaveStatusOfEmployees(employeeEntity);
		List<LeaveApplyEntity> rejectedLeaveApply = leaveManagementService.getRejectedLeaveStatusOfEmployees(employeeEntity);
		map.put("approvedLeaveApplySize", approvedLeaveApply.size());
		map.put("rejectedLeaveApplySize", rejectedLeaveApply.size());
		
		List<SalaryEntity> approvedSalary = employeeService.getApprovedSalary();
		List<SalaryEntity> rejectedSalary = employeeService.getRejectedSalary();
		map.put("approvedSalarySize", approvedSalary.size());
		map.put("rejectedSalarySize", rejectedSalary.size());
		
//		String userName = null;
//		try {
//			userName = (String) httpServletRequest.getSession().getAttribute("username");
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//		List<LeaveApplyEntity> leaveApply = null;
//		if(userName != null){
//		leaveApply = leaveManagementService.getLeaveAppliesForApproveOrReject(userName);
//		}
//		try {
//		map.put("leaveApplySize", 3);
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
		
		EmployeeEntity employeeDetail = employeeService.getEmployeeDetail(employeeEntity);
		if (Objects.nonNull(employeeDetail)) {
			map.put("userId", employeeDetail.getUserId());
			map.put("userName", employeeDetail.getUsername());
			map.put("designation", employeeDetail.getDesignationEntity().getDesignationName());
			map.put("displayName", employeeDetail.getDisplayName());
			map.put("email", employeeDetail.getEmailId());
			map.put("number", employeeDetail.getMobile());
			map.put("registrationDate", employeeDetail.getUserCreated());
			List<LeaveApplyEntity> leaveApply1 = leaveManagementService.getLeaveAppliesForApproveOrReject(employeeDetail.getUsername());
			List<SalaryEntity> emp = employeeService.getSalaryAppliesForApproveOrReject(employeeDetail.getUsername());
			map.put("leaveApplySize", leaveApply1.size());
			map.put("salaryApplySize", emp.size());
		} 
		responseVT.setObject(map);
		
		return responseVT;
	}
	
	@GetMapping("/forgotPassword/{email}")
	public ResponseVT  forgotPassword(@PathVariable (value = "email") String forgotPasswordEmail, EmployeeEntity user) {
		ResponseVT response = new ResponseVT();
		if(employeeService.checkEmail(forgotPasswordEmail)) {
			PasswordUtil util = new PasswordUtil();
			String newPassword = util.generatePassword();
			 BCryptPasswordEncoder en = new BCryptPasswordEncoder();
			try {
				mailUtil.sendForgotPasswordEmail(forgotPasswordEmail, newPassword);
				if(employeeService.changeForgotPassword(forgotPasswordEmail,en.encode(newPassword))) {
					response.setSuccess(true);
					response.setMessage("New Password sent to your Email");
				} else {
					response.setSuccess(false);
					response.setMessage("Reset Password Not Successfully");
				}
			 } catch (Exception e) {
				e.printStackTrace();
				response.setSuccess(false);
				response.setMessage("Reset Password UnSuccessfully. Try Again..!");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("Email id doesn't exist");
		}
		return response;		
	}
	
	@RequestMapping(value="/saveContactUs")
	public  ResponseVT contactCompany(@ModelAttribute ContactUsEntity contactUS) {
		ResponseVT response = new ResponseVT();
		contactUS.setContactDate(AppUtil.currentDate());
		boolean result = employeeService.saveContactUsDetails(contactUS);
		if(result){
		try {
			mailUtil.sendMailToCompany(contactUS);
			response.setSuccess(true);
			response.setMessage("Your Msg is send to Company Successfully !\nPlease Wait for the Response.");
			 return response;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			response.setSuccess(false);
			response.setMessage("Fails to Send Mail.");
			 return response;
		}
		}else{
			response.setSuccess(false);
			response.setMessage("Something Went Wrong...please try again.");
			 return response;
		}
	}
	
	@PutMapping(value = "/changePassword")
	public ResponseVT changePassword(@RequestBody EmployeeEntity userEntity,
			@RequestParam(value = "currentPassword") String currentPassword) {
		ResponseVT response = new ResponseVT();
		try {
			EmployeeEntity exUser = employeService.findById(userEntity.getUserId()).get();
			if (Objects.nonNull(exUser)) {
				BCryptPasswordEncoder en = new BCryptPasswordEncoder();
				boolean isPasswordMatches = en.matches(currentPassword, exUser.getPassword());
				if (isPasswordMatches) {
					exUser.setPassword(en.encode(userEntity.getPassword()));
					EmployeeEntity savedUser = employeService.save(exUser);
					if (Objects.nonNull(savedUser)) {
						response.setSuccess(true);
						response.setMessage("Password changed successfully");
					} else {
						response.setSuccess(false);
						response.setMessage("Password changed Unsuccessfully");
					}
				} else {
					response.setSuccess(false);
					response.setStatusCode(400);
					// response.setMessage("The Current Password Which you have entered is
					// invalid..please re-enter correct password");
				}
			} else {
				response.setSuccess(false);
				response.setMessage("invalid user.");
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			response.setSuccess(false);
			response.setMessage("Something went wrong please try again after some times.");
		}

		return response;
	}

	
}
