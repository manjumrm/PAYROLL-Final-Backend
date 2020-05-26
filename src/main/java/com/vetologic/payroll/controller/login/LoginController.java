package com.vetologic.payroll.controller.login;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.vetologic.payroll.entity.EmployeeEntity;
import com.vetologic.payroll.entity.ResponseVT;
import com.vetologic.payroll.service.user.EmployeeService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LoginController {
	
	@Autowired
	EmployeeService employeeService;
	
	@PostMapping(value = "/index")
	public ModelAndView excuteSecurity(@RequestBody EmployeeEntity employeeEntity,HttpServletRequest httpServletRequest) {
		System.out.println("Principal UserName :" + employeeEntity.getUsername());
		EmployeeEntity employeeDetail = employeeService.getEmployeeDetail(employeeEntity);
		if (Objects.nonNull(employeeDetail)) {
			HttpSession httpSession = httpServletRequest.getSession(true);
			httpSession.setAttribute("userId", employeeDetail.getUserId());
			httpSession.setAttribute("userName", employeeDetail.getUsername());
			httpSession.setAttribute("designation", employeeDetail.getDesignationEntity().getDesignationName());
			httpSession.setAttribute("displayName", employeeDetail.getDisplayName());
			httpSession.setAttribute("email", employeeDetail.getEmailId());
			httpSession.setAttribute("registrationDate", employeeDetail.getUserCreated());
			
			 return new  ModelAndView("redirect:/home");
		} else {
			return new  ModelAndView("redirect:/invalidCredential");
		}
	}

	@RequestMapping(value ="/invalidCredential")
	public ResponseVT invalid(HttpServletRequest httpServletRequest){
		ResponseVT responseVT = new ResponseVT();
		responseVT.setSuccess(true);
		httpServletRequest.getSession().invalidate();
		return responseVT;
	}


}
