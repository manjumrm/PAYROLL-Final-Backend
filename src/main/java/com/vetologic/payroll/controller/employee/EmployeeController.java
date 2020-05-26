package com.vetologic.payroll.controller.employee;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vetologic.payroll.entity.BankEntity;
import com.vetologic.payroll.entity.DepartmentEntity;
import com.vetologic.payroll.entity.DesignationEntity;
import com.vetologic.payroll.entity.EmployeeEntity;
import com.vetologic.payroll.entity.InvestmentDeclarationActualEntity;
import com.vetologic.payroll.entity.InvestmentDeclarationPlannedEntity;
import com.vetologic.payroll.entity.ResponseVT;
import com.vetologic.payroll.entity.SalaryEntity;
import com.vetologic.payroll.entity.TaxRateEntity;
import com.vetologic.payroll.service.leave.LeaveManagementService;
import com.vetologic.payroll.service.salary.InvestmentActual;
import com.vetologic.payroll.service.salary.InvestmentPlanned;
import com.vetologic.payroll.service.salary.SalaryInterface;
import com.vetologic.payroll.service.salary.TaxRate;
import com.vetologic.payroll.service.user.EmployeService;
import com.vetologic.payroll.service.user.EmployeeService;
import com.vetologic.payroll.util.AppUtil;
import com.vetologic.payroll.util.MailUtil;
import com.vetologic.payroll.util.PasswordUtil;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EmployeeController {
	
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
	
	@GetMapping(value = "/getBasicEmployeeDetails")
	public ResponseVT getBasicEmployeeDetails() {
		ResponseVT responseVT = new ResponseVT();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DesignationEntity> desigList = employeeService.getDesignationList();
		map.put("desigList", desigList);
		List<DepartmentEntity> departmentList = employeeService.getDepartmentList();
		map.put("departmentList", departmentList);
		List<BankEntity> bankList =  employeeService.getBankList();
		map.put("bankList", bankList);
		responseVT.setObject(map);
		return responseVT;
	}
	
	@PostMapping(value = "/saveEmployeeDetails/{empDesig}/{empDept}")
	public  ResponseVT saveEmployeeDetails(@PathVariable int empDesig, @PathVariable int empDept,@RequestBody EmployeeEntity employeeEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		ResponseVT responseVT = new ResponseVT();
		DesignationEntity designationEntity = new DesignationEntity();
		DepartmentEntity departmentEntity = new DepartmentEntity();
		designationEntity.setDesignationId(empDesig);
		departmentEntity.setDepartmentId(empDept);
		employeeEntity.setDepartmentEntity(departmentEntity);
		employeeEntity.setDesignationEntity(designationEntity);
		 PasswordUtil util = new PasswordUtil();
		 String genPassword = util.generatePassword();
		 BCryptPasswordEncoder en = new BCryptPasswordEncoder();
		 employeeEntity.setPassword(en.encode(genPassword));
		 employeeEntity.setEmailId(employeeEntity.getEmailId().toLowerCase());
		 employeeEntity.setDisplayName(employeeEntity.getUsername().toUpperCase());
		 employeeEntity.setActive(1);
		 boolean savedEmployeeEntity = employeeService.saveEmployeeDetails(employeeEntity);
		if(savedEmployeeEntity){
			employeeEntity.setPassword(genPassword);
		     try {
				mailUtil.sendUserEmail(employeeEntity);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				map.put("success" ,true);
				map.put("message","Employee Creation is Successful !\nBut Fails to Send Mail.");
				responseVT.setObject(map);
				return responseVT;
			}
			map.put("message", "Employee Created Successfully \n password is sent to your mail");
			map.put("success", true);
		}else{
			map.put("success", false);
			map.put("message", "Employee Created UnSuccessfully");
		}
		responseVT.setObject(map);
		return responseVT;
	}
	
	@GetMapping(value = "/toCheckEmpNameAlreadyExistOrNot/{empName}")
	public ResponseVT toCheckEmpNameAlreadyExistOrNot(@PathVariable String empName){
		ResponseVT responseVT = new ResponseVT();
		boolean result = employeeService.toCheckEmpNameAlreadyExistOrNot(empName);
		if(result)
		{
			responseVT.setSuccess(true);
		}
		
		return responseVT;
	}
	
	@GetMapping(value = "/toCheckEmpEmailAlreadyExistOrNot/{empEmail}")
	public ResponseVT toCheckEmpEmailAlreadyExistOrNot(@PathVariable String empEmail){
		ResponseVT responseVT = new ResponseVT();
		boolean result = employeeService.toCheckEmpEmailAlreadyExistOrNot(empEmail);
		if(result)
		{
			responseVT.setSuccess(true);
		}
		
		return responseVT;
	}
	
	@GetMapping(value = "/toCheckSupNameAlreadyExistOrNot/{supName}")
	public ResponseVT toCheckSupNameAlreadyExistOrNot(@PathVariable String supName){
		ResponseVT responseVT = new ResponseVT();
		boolean result = employeeService.toCheckSupNameAlreadyExistOrNot(supName);
		if(result)
		{
			responseVT.setSuccess(true);
		}
		
		return responseVT;
	}
	
	@GetMapping(value = "/getAllEmployeeDetails")
	public List<EmployeeEntity> getAllEmployeeDetails(){
		return employeeService.getAllEmployeeDetails();
	}
	
	@DeleteMapping(value = "/deleteEmployee/{eId}")
	public ResponseVT deleteEmployee(@PathVariable int eId) {
		ResponseVT responseVT = new ResponseVT();
		boolean result = employeeService.deleteEmployee(eId);
		if(result) {
			responseVT.setSuccess(true);
		}else {
			responseVT.setSuccess(false);
		}
		return responseVT;
	}
	
	@GetMapping(value = "/getEmployeeById/{eId}")
	public EmployeeEntity getEmployeeById(@PathVariable int eId) {
		EmployeeEntity employeeEntity =  employeService.findById(eId).get();
		return employeeEntity;
	}
	
	@PutMapping(value = "/updateEmployeeDetails/{empDesig}/{empDept}/{empId}")
	public ResponseVT updateEmployeeDetails(@PathVariable int empDesig,@PathVariable int empDept,@PathVariable int empId ,@RequestBody EmployeeEntity employeeEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		ResponseVT responseVT = new ResponseVT();
		try {
		EmployeeEntity exEmployeeEntity = employeService.findById(empId).get();
		if(Objects.nonNull(exEmployeeEntity)) {
		exEmployeeEntity.setDisplayName(exEmployeeEntity.getDisplayName().toUpperCase());
		exEmployeeEntity.setUsername(employeeEntity.getUsername());
		exEmployeeEntity.setEmployeeNameAsPerAadhar(employeeEntity.getEmployeeNameAsPerAadhar());
		exEmployeeEntity.setSupervisorName(employeeEntity.getSupervisorName());
		exEmployeeEntity.setEmployeeId(employeeEntity.getEmployeeId());
		DesignationEntity designationEntity = new DesignationEntity();
		designationEntity.setDesignationId(empDesig);
		exEmployeeEntity.setDesignationEntity(designationEntity);
		exEmployeeEntity.setAddress(employeeEntity.getAddress());
		exEmployeeEntity.setCity(employeeEntity.getCity());
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity.setDepartmentId(empDept);
		exEmployeeEntity.setDepartmentEntity(departmentEntity);
		exEmployeeEntity.setPanNo(employeeEntity.getPanNo());
		exEmployeeEntity.setAddharNo(employeeEntity.getAddharNo());
		exEmployeeEntity.setMobile(employeeEntity.getMobile());
		exEmployeeEntity.setEmailId(employeeEntity.getEmailId());
		exEmployeeEntity.setGender(employeeEntity.getGender());
		exEmployeeEntity.setDateOfBirth(employeeEntity.getDateOfBirth());
		exEmployeeEntity.setAge(employeeEntity.getAge());
		exEmployeeEntity.setBankName(employeeEntity.getBankName());
		exEmployeeEntity.setBankBranchName(employeeEntity.getBankBranchName());
		exEmployeeEntity.setBankBranchAddress(employeeEntity.getBankBranchAddress());
		exEmployeeEntity.setIfscNumber(employeeEntity.getIfscNumber());
		exEmployeeEntity.setAccountNumber(employeeEntity.getAccountNumber());
		exEmployeeEntity.setDateOfJoining(employeeEntity.getDateOfJoining());
		exEmployeeEntity.setDateOfResignation(employeeEntity.getDateOfResignation());
		exEmployeeEntity.setDateOfLeaving(employeeEntity.getDateOfLeaving());
		 
		EmployeeEntity savingEmployeeEntity = employeService.save(exEmployeeEntity);
		if(Objects.nonNull(exEmployeeEntity)) {
			map.put("success", true);
			map.put("message", "Employee Updated Successfully");
		}else {
			map.put("success", false);
			map.put("message", "Employee Updated UnSuccessfully");
		}
	 }else {
		 map.put("success", false);
		 map.put("message", "Employee Updated UnSuccessfully");
	 }
	}catch(Exception e) {
		map.put("success", false);
		map.put("message", "Employee Updated UnSuccessfully");
		e.printStackTrace();
		responseVT.setObject(map);
		return responseVT;
	}
		responseVT.setObject(map);
		return responseVT;
	}
	
	
	@GetMapping(value = "/getEmpInvestmentDecPlanned/{userId}")
	public ResponseVT getEmpInvestmentDecPlanned(@PathVariable int userId) {
		ResponseVT responseVT = new ResponseVT();
		Map<String, Object> map = new HashMap<String, Object>();
		InvestmentDeclarationPlannedEntity declarationPlannedEntity = employeeService.getEmpInvestmentDecPlanned(userId);
		if(Objects.nonNull(declarationPlannedEntity)) {
			map.put("planned",declarationPlannedEntity);
		}else {
			map.put("planned", "");
		}
		responseVT.setObject(map);
		return responseVT;
	}

	@PostMapping(value = "/saveEmpInvestmentDecPlanned/{userId}")
	public ResponseVT saveEmpInvestmentDecPlanned(@PathVariable int userId,@RequestBody InvestmentDeclarationPlannedEntity declarationPlannedEntity) {
		ResponseVT responseVT = new ResponseVT();
		Map<String, Object> map = new HashMap<String, Object>();
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setUserId(userId);
		declarationPlannedEntity.setEmployeeEntity(employeeEntity);
		declarationPlannedEntity.setStatus("Planned");
		InvestmentDeclarationPlannedEntity savedDeclarationPlannedEntity = investmentPlanned.save(declarationPlannedEntity);
		if(Objects.nonNull(savedDeclarationPlannedEntity)) {
			map.put("success", true);
		}
		else {
			map.put("success", false);
		}
		responseVT.setObject(map);
		return responseVT;
	}
	
	@GetMapping(value = "/getEmpInvestmentDecActual/{userId}")
	public ResponseVT getEmpInvestmentDecActual(@PathVariable int userId) {
		ResponseVT responseVT = new ResponseVT();
		Map<String, Object> map = new HashMap<String, Object>();
		InvestmentDeclarationPlannedEntity declarationPlannedEntity = employeeService.getEmpInvestmentDecPlanned(userId);
		InvestmentDeclarationActualEntity actualEntity = employeeService.getEmpInvestmentDecActual(userId);
		if(Objects.isNull(actualEntity)) {
			map.put("planned",declarationPlannedEntity);
		}else {
			map.put("planned", actualEntity);
		}
		responseVT.setObject(map);
		return responseVT;
	}
	
	
	@GetMapping(value = "/getEmpInvestmentDecActualData/{userId}")
	public ResponseVT getEmpInvestmentDecActualData(@PathVariable int userId) {
		ResponseVT responseVT = new ResponseVT();
		Map<String, Object> map = new HashMap<String, Object>();
		InvestmentDeclarationPlannedEntity declarationPlannedEntity = employeeService.getEmpInvestmentDecPlanned(userId);
		InvestmentDeclarationActualEntity actualEntity = employeeService.getEmpInvestmentDecActual(userId);
		if(Objects.nonNull(actualEntity)) {
			map.put("planned",declarationPlannedEntity);
		}else {
			map.put("planned", "");
		}
		responseVT.setObject(map);
		return responseVT;
	}
	
	@PostMapping(value = "/saveEmpInvestmentDecActual/{userId}")
	public ResponseVT saveEmpInvestmentDecActual(@PathVariable int userId,@RequestBody InvestmentDeclarationActualEntity actualEntity) {
		ResponseVT responseVT = new ResponseVT();
		Map<String, Object> map = new HashMap<String, Object>();
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setUserId(userId);
		actualEntity.setEmployeeEntity(employeeEntity);
		actualEntity.setStatus("Actual");
		InvestmentDeclarationActualEntity savedActualEntity = investmentActual.save(actualEntity);
		if(Objects.nonNull(savedActualEntity)) {
			map.put("success", true);
		}
		else {
			map.put("success", false);
		}
		responseVT.setObject(map);
		return responseVT;
	}
	
	@GetMapping(value = "/getAllTaxRateList")
	public List<TaxRateEntity> getAllTaxRateList(){
		return employeeService.getAllTaxRateList();
	}
	
	@PutMapping(value = "/updateTaxRateToDb")
	public ResponseVT updateTaxRateToDb(@RequestBody TaxRateEntity taxRateEntity) {
		ResponseVT responseVT = new ResponseVT();
		Map<String, Object> map = new HashMap<String, Object>();
		TaxRateEntity exTaxRateEntity = taxRate.findById(taxRateEntity.getTaxId()).get();
		if(Objects.nonNull(exTaxRateEntity)) {
			exTaxRateEntity.setStartingAmount(taxRateEntity.getStartingAmount().trim());
			exTaxRateEntity.setEndingAmount(taxRateEntity.getEndingAmount().trim());
			exTaxRateEntity.setTaxRateInPercentage(taxRateEntity.getTaxRateInPercentage().trim()+"%");
			TaxRateEntity savedTaxRateEntity = taxRate.save(exTaxRateEntity);
			if(Objects.nonNull(savedTaxRateEntity)) {
				map.put("success",true);
			}else {
				map.put("success",false);
			}
		}else {
			map.put("success",false);
		}
		
		responseVT.setObject(map);
		return responseVT;
	}
	
	@GetMapping(value = "/toCheckInvestmentDeclaration/{userId}")
	public ResponseVT toCheckInvestmentDeclaration(@PathVariable int userId) {
		ResponseVT responseVT = new ResponseVT();
		boolean result = employeeService.toCheckInvestmentDeclaration(userId);
		if(result) {
			responseVT.setSuccess(true);
		}else {
			responseVT.setSuccess(false);
		}
		return responseVT;
	}
	
	@GetMapping(value = "/calculateTDS/{userId}/{totalEarnings}")
	public Object calculateTDS(@PathVariable ("userId") int userId,@PathVariable ("totalEarnings") String totalEarnings) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
		InvestmentDeclarationActualEntity investmentDeclarationActual = new InvestmentDeclarationActualEntity();
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setUserId(userId);
		investmentDeclarationActual.setEmployeeEntity(employeeEntity);
		EmployeeEntity userDetail = employeService.findById(userId).get();
		InvestmentDeclarationPlannedEntity investmentDeclarationPlanned = employeeService.getEmpInvestmentDecPlanned(userId);
		InvestmentDeclarationActualEntity investmentDeclarationActualDetail = employeeService.getEmpInvestmentDecActual(userId);
		//List<TaxRate> taxRateList = leaveManagementService.getTaxRate();
		System.out.println("Total Earnings "+totalEarnings);
		Long grossSalary = Long.parseLong(totalEarnings)*12;
		Long totalDeclarationAmount = Long.parseLong(investmentDeclarationPlanned.getTotalAmount());
		Long totalTaxableIncome  = grossSalary-totalDeclarationAmount;
		Long totalTaxPayable = 0l;
		TaxRateEntity taxRate1 = taxRate.findById(1).get();
		TaxRateEntity taxRate2 = taxRate.findById(2).get();
		TaxRateEntity taxRate3 = taxRate.findById(3).get();
		TaxRateEntity taxRate4 = taxRate.findById(4).get();
		
		if(totalTaxableIncome >=  Long.parseLong(taxRate1.getStartingAmount()) && totalTaxableIncome <= Long.parseLong(taxRate1.getEndingAmount())){
			totalTaxPayable = 0L;
			System.out.println("1");
		}
		else if(totalTaxableIncome >=  Long.parseLong(taxRate2.getStartingAmount()) && totalTaxableIncome <= Long.parseLong(taxRate2.getEndingAmount())){
			  totalTaxPayable = ((totalTaxableIncome - 500000) * Integer.parseInt(taxRate2.getTaxRateInPercentage().substring(0, taxRate2.getTaxRateInPercentage().length()-1))/100);
			  System.out.println("2");
		  }
		else if(totalTaxableIncome >=  Long.parseLong(taxRate3.getStartingAmount()) && totalTaxableIncome <= Long.parseLong(taxRate3.getEndingAmount())){
			totalTaxPayable = ((totalTaxableIncome - 500000) * Integer.parseInt(taxRate3.getTaxRateInPercentage().substring(0, taxRate3.getTaxRateInPercentage().length()-1))/100)+ 12500;
			System.out.println("3");
		 }
		 else {
				totalTaxPayable = ((totalTaxableIncome - 1000000) * Integer.parseInt(taxRate4.getTaxRateInPercentage().substring(0, taxRate4.getTaxRateInPercentage().length()-1))/100) + 100000 + 12500;
				System.out.println("4");
		 }
		
		//Surcharge Amount
		Long surchargeAmount = 0l;
		if(totalTaxableIncome >= 5000000 && totalTaxableIncome <= 10000000){
			surchargeAmount = (totalTaxPayable * 10/100);
		}
		else if(totalTaxableIncome > 10000000){
			surchargeAmount = (totalTaxPayable * 15/100);
		} 
		
		//Education & Health CessHas to be added
		Long cess = 0l;
		cess = (totalTaxPayable+surchargeAmount)*4/100;   
		
		//Total Net Tax Payable by the employee will be
		Long netTaxPayable = (totalTaxPayable+surchargeAmount+cess)/12;
		map.put("success", true);
		map.put("netTaxPayable", Math.abs(netTaxPayable));
		return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			return map;
		}
	}
	
	
	@PostMapping(value = "/saveSalaryDetails/{userId}")
	public ResponseVT saveSalaryDetails(@RequestBody SalaryEntity salaryEntity,@PathVariable int userId) {
		ResponseVT responseVT = new ResponseVT();
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setUserId(userId);
		salaryEntity.setEmployeeEntity(employeeEntity);
		salaryEntity.setActive(1);
		salaryEntity.setSalaryStatus("Pending");
		SalaryEntity savingSalaryEntity = salaryInterface.save(salaryEntity);
		if(Objects.nonNull(savingSalaryEntity)){
			responseVT.setSuccess(true);
		}else {
			responseVT.setSuccess(false);
		}
		return responseVT;
	}
	
	@GetMapping(value = "/getAllSalaryDetails")
	public List<SalaryEntity> getAllSalaryDetails(){
		return employeeService.getAllSalaryDetails();
	}
	
	@DeleteMapping(value = "/deleteSalary/{salId}")
	public ResponseVT deleteSalary(@PathVariable int salId){
		ResponseVT responseVT = new ResponseVT();
		if(employeeService.deleteSalary(salId)) {
			responseVT.setSuccess(true);
		}else {
			responseVT.setSuccess(false);
		}
		return responseVT;
	}
	
	@GetMapping(value = "/getSalaryById/{salId}")
	public SalaryEntity getSalaryById(@PathVariable int salId){
		return salaryInterface.findById(salId).get();
	}
	
	@PutMapping(value = "/updateSalaryDetails/{userId}/{salaryId}")
	public ResponseVT updateSalaryDetails(@PathVariable int userId,@PathVariable int salaryId,@RequestBody SalaryEntity salaryEntity) {
		ResponseVT responseVT = new ResponseVT();
		SalaryEntity exSalaryEntity = salaryInterface.findById(salaryId).get();
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setUserId(userId);
		exSalaryEntity.setEmployeeEntity(employeeEntity);
		exSalaryEntity.setBasicSalary(salaryEntity.getBasicSalary());
		exSalaryEntity.setHraSalary(salaryEntity.getHraSalary());
		exSalaryEntity.setSpecialAllowance(salaryEntity.getSpecialAllowance());
		exSalaryEntity.setMedicalAllowance(salaryEntity.getMedicalAllowance());
		exSalaryEntity.setConveyanceAllowance(salaryEntity.getConveyanceAllowance());
		exSalaryEntity.setBonusOrIncentive(salaryEntity.getBonusOrIncentive());
		exSalaryEntity.setDearnessAllowance(salaryEntity.getDearnessAllowance());
		exSalaryEntity.setEsic(salaryEntity.getEsic());
		exSalaryEntity.setOthersSalary(salaryEntity.getOthersSalary());
		exSalaryEntity.setTotalEarnings(salaryEntity.getTotalEarnings());
		exSalaryEntity.setPt(salaryEntity.getPt());
		exSalaryEntity.setPf(salaryEntity.getPf());
		exSalaryEntity.setTds(salaryEntity.getTds());
		exSalaryEntity.setAdvanceSalary(salaryEntity.getAdvanceSalary());
		exSalaryEntity.setTotalDeduction(salaryEntity.getTotalDeduction());
		exSalaryEntity.setGrossSalary(salaryEntity.getGrossSalary());
		exSalaryEntity.setNetSalary(salaryEntity.getNetSalary());
		
		SalaryEntity savedSalaryEntity = salaryInterface.save(exSalaryEntity);
		if(Objects.nonNull(savedSalaryEntity)) {
			responseVT.setSuccess(true);
		}else {
			responseVT.setSuccess(false);
		}
		return responseVT;
	}
	
	@GetMapping(value = "/approveOrRejectSalary/{userName}")
	public List<SalaryEntity> approveOrRejectSalary(@PathVariable String userName){
		return employeeService.approveOrRejectSalary(userName);
	}
	
	@GetMapping(value = "/approveSalary/{salId}")
	public Object approveSalary(@PathVariable int salId) {
		Map<String, Object> map = new HashMap<String, Object>();
		SalaryEntity exSalaryEntity = salaryInterface.findById(salId).get();
		exSalaryEntity.setSalaryStatus("Approved");
		SalaryEntity savedSalaryEntity = salaryInterface.save(exSalaryEntity);
		if(Objects.nonNull(savedSalaryEntity)) {
			map.put("success", true);
		}else {
			map.put("success", false);
		}
		List<SalaryEntity> emp = employeeService.getSalaryAppliesForApproveOrReject(exSalaryEntity.getEmployeeEntity().getUsername());
		map.put("salaryApplySize", emp.size());
		return map;
	}
	
	@GetMapping(value = "/rejectSalary/{salId}/{reason}")
	public Object rejectSalary(@PathVariable int salId,@PathVariable String reason) {
		Map<String, Object> map = new HashMap<String, Object>();
		SalaryEntity exSalaryEntity = salaryInterface.findById(salId).get();
		exSalaryEntity.setSalaryStatus("Rejected");
		exSalaryEntity.setReasonForSalaryRejection(reason);
		SalaryEntity savedSalaryEntity = salaryInterface.save(exSalaryEntity);
		if(Objects.nonNull(savedSalaryEntity)) {
			map.put("success", true);
		}else {
			map.put("success", false);
		}
		return map;
	}
	
	@GetMapping(value = "/getApprovedSalary/{userId}")
	public List<SalaryEntity> getApprovedSalary(@PathVariable int userId){
		return employeeService.getApprovedSalary(userId);
	}
	
	@GetMapping(value = "/getRejectedSalary/{userId}")
	public List<SalaryEntity> getRejectedSalary(@PathVariable int userId){
		return employeeService.getRejectedSalary(userId);
	}
	
	
	@RequestMapping(value = "/importCreateEmployeeExcel") 
	public  Object importCreateEmployeeExcel(HttpServletRequest request,@ModelAttribute MultipartFile file ) {
		Map<String, Object> map = new HashMap<String, Object>();
		if ((file != null) && (!file.isEmpty())) {
			try {
				byte[] bytes = file.getBytes();
				// Creating the directory to store file
				
				Path rootPath = FileSystems.getDefault().getPath("").toAbsolutePath();
				File dir = new File(rootPath + File.separator + "Uploads"
						+ File.separator + "CreateEmployee" + File.separator + "ImportedAsExcel");

				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());

				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				

				String filePath = rootPath + File.separator + "Uploads" + File.separator+ "CreateEmployee" 
						+ File.separator + "ImportedAsExcel"+ File.separator + file.getOriginalFilename();

				FileInputStream fileInput = new FileInputStream(new File(filePath));
				XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
				XSSFSheet taskTrackingSheet = workbook.getSheet("employee");
				
					 Row taskTrackingRows = taskTrackingSheet.getRow(0);
					if(taskTrackingRows.getCell(0).getStringCellValue().equals("EMPLOYEE_NAME")
							&& taskTrackingRows.getCell(1).getStringCellValue().equals("EMPLOYEE_NAME_AS_PER_AADHAR")
							&& taskTrackingRows.getCell(2).getStringCellValue().equals("EMPLOYEE_ID")
							&& taskTrackingRows.getCell(3).getStringCellValue().equals("SUPERVISOR_NAME")
							&& taskTrackingRows.getCell(4).getStringCellValue().equals("DEPARTMENT")
							&& taskTrackingRows.getCell(5).getStringCellValue().equals("DESIGNATION")
							&& taskTrackingRows.getCell(6).getStringCellValue().equals("EMAIL_ID")
							&& taskTrackingRows.getCell(7).getStringCellValue().equals("MOBILE_NO")
							&& taskTrackingRows.getCell(8).getStringCellValue().equals("DATE_OF_BIRTH")
							&& taskTrackingRows.getCell(9).getStringCellValue().equals("AGE")
							&& taskTrackingRows.getCell(10).getStringCellValue().equals("GENDER")
							&& taskTrackingRows.getCell(11).getStringCellValue().equals("BANK_NAME")
							&& taskTrackingRows.getCell(12).getStringCellValue().equals("BRANCH_NAME")
							&& taskTrackingRows.getCell(13).getStringCellValue().equals("BRANCH_ADDRESS")
							&& taskTrackingRows.getCell(14).getStringCellValue().equals("ACCOUNT_NUMBER")
							&& taskTrackingRows.getCell(15).getStringCellValue().equals("IFSC_NUMBER")
							&& taskTrackingRows.getCell(16).getStringCellValue().equals("PAN_NO")
							&& taskTrackingRows.getCell(17).getStringCellValue().equals("ADDHAR_NO")
							&& taskTrackingRows.getCell(18).getStringCellValue().equals("CITY")
							&& taskTrackingRows.getCell(19).getStringCellValue().equals("ADDRESS")
							&& taskTrackingRows.getCell(20).getStringCellValue().equals("DATE_OF_JOINING")) {
						Iterator<Row> rowIterator = taskTrackingSheet.iterator();
						rowIterator.next();
							
						List<EmployeeEntity> rowList = new ArrayList<EmployeeEntity>();
						List<String> rowPasswordList = new ArrayList<String>();
						int departmentId = 0;
						int designationId = 0;
						while (rowIterator.hasNext()) {
							System.out.println("Jaggu");
							Row row = rowIterator.next();
							Iterator<Cell> cellIterator = row.cellIterator();
							List<Cell> cellList = new ArrayList<Cell>();
							while (cellIterator.hasNext()) {
								Cell cell = cellIterator.next();
								System.out.println("Test: "+cell);
								cellList.add(cell);
							}
							
							EmployeeEntity user = new EmployeeEntity();
							String genPassword = "";
							DepartmentEntity department = new DepartmentEntity();
							DesignationEntity designation = new DesignationEntity();
							
							
							String employeeName = String.valueOf(cellList.get(0)); 
							if(employeeName.isEmpty() || employeeName.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Employee Name nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It");
								return map;
							}
							else
							{
								if(employeeName.matches("^[a-zA-Z ]+$")){
									boolean res1 = employeeService.toCheckEmpNameAlreadyExistOrNot(employeeName);
									if(res1){
										map.put("success", false);
										map.put("message", "\nColumn Name Employee Name nd Row No " +(row.getRowNum()+1) + " is Already Exist.Please Change the Employee Name.");
										return map;
									}else{
										user.setUsername(employeeName);
										user.setDisplayName(employeeName.toUpperCase());
									}
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Employee Name nd Row No " +(row.getRowNum()+1) + " is Allows Only Alphabets.");
									return map;
								}
							}
							
							String employeeNameAsPerAadhar = String.valueOf(cellList.get(1));
							if(employeeNameAsPerAadhar.isEmpty() || employeeNameAsPerAadhar.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Employee Name As Per Aadhar nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It");
								return map;
							}
							else
							{
								if(employeeNameAsPerAadhar.matches("^[a-zA-Z ]+$")){
									user.setEmployeeNameAsPerAadhar(employeeNameAsPerAadhar);
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Employee Name As Per Aadhar nd Row No " +(row.getRowNum()+1) + " is Allows Only Alphabets.");
									return map;
								}
							}
							
							String employeeId = String.valueOf(cellList.get(2));
							if(employeeId.isEmpty() || employeeId.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Employee ID  nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It.");
								return map;
							}
							else
							{
								if(employeeId.matches("^[a-zA-Z0-9 ]+$")){
									user.setEmployeeId(employeeId);
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Employee Id  Row No " +(row.getRowNum()+1) + " is Allows Only AlphaNumeric.");
									return map;
								}
							}
							
							String supervisorName = String.valueOf(cellList.get(3));
							if(supervisorName.isEmpty() || supervisorName.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Supervisor Name nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It.");
								return map;
							}
							else
							{
								if(supervisorName.matches("^[a-zA-Z ]+$")){
									user.setSupervisorName(supervisorName);
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Supervisor Name nd Row No " +(row.getRowNum()+1) + " is Allows Only Alphabets.");
									return map;
								}
							}
							
							String departmentName = String.valueOf(cellList.get(4));
							if(departmentName.isEmpty() || departmentName.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Department Name nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It.");
								return map;
							}
							else
							{
								if(departmentName.equalsIgnoreCase("Accounts") || departmentName.equalsIgnoreCase("Audits") || departmentName.equalsIgnoreCase("Direct Tax") ||
										departmentName.equalsIgnoreCase("Indirect Tax") || departmentName.equalsIgnoreCase("MIS") || departmentName.equalsIgnoreCase("Legal & Complians")){
									if(departmentName.equalsIgnoreCase("Accounts")){
										departmentId = 1;
										department.setDepartmentId(departmentId);
										user.setDepartmentEntity(department);
									}
									if(departmentName.equalsIgnoreCase("Audits")){
										departmentId = 2;
										department.setDepartmentId(departmentId);
										user.setDepartmentEntity(department);
									}
									if(departmentName.equalsIgnoreCase("Direct Tax")){
										departmentId = 3;
										department.setDepartmentId(departmentId);
										user.setDepartmentEntity(department);
									}
									if(departmentName.equalsIgnoreCase("Indirect Tax")){
										departmentId = 4;
										department.setDepartmentId(departmentId);
										user.setDepartmentEntity(department);
									}
									if(departmentName.equalsIgnoreCase("MIS")){
										departmentId = 5;
										department.setDepartmentId(departmentId);
										user.setDepartmentEntity(department);
									}
									if(departmentName.equalsIgnoreCase("Legal & Complians")){
										departmentId = 6;
										department.setDepartmentId(departmentId);
										user.setDepartmentEntity(department);
									}
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Department Name nd Row No " +(row.getRowNum()+1) + " is Invalid Department...Please Check It.");
									return map;
								}
							}
							
							String designationName = String.valueOf(cellList.get(5));
							System.out.println("designationName "+designationName);
							if(designationName.isEmpty() || designationName.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Designation Name nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It.");
								return map;
							}
							else
							{
								if(designationName.equalsIgnoreCase("Trainee") || designationName.equalsIgnoreCase("Junior Team Coordinator") || designationName.equalsIgnoreCase("Team Coordinator") ||
										designationName.equalsIgnoreCase("Assistant Team Lead") || designationName.equalsIgnoreCase("Team Lead") || designationName.equalsIgnoreCase("Sr.Team Lead")
										|| designationName.equalsIgnoreCase("Manager") || designationName.equalsIgnoreCase("CEO")){
									if(designationName.equalsIgnoreCase("Trainee")){
										designationId = 1;
										designation.setDesignationId(designationId);
										user.setDesignationEntity(designation);
									}
									if(designationName.equalsIgnoreCase("Junior Team Coordinator")){
										designationId = 2;
										designation.setDesignationId(designationId);
										user.setDesignationEntity(designation);
									}
									if(designationName.equalsIgnoreCase("Team Coordinator")){
										designationId = 3;
										designation.setDesignationId(designationId);
										user.setDesignationEntity(designation);
									}
									if(designationName.equalsIgnoreCase("Assistant Team Lead")){
										designationId = 4;
										designation.setDesignationId(designationId);
										user.setDesignationEntity(designation);
									}
									if(designationName.equalsIgnoreCase("Team Lead")){
										designationId = 5;
										designation.setDesignationId(designationId);
										user.setDesignationEntity(designation);
									}
									if(designationName.equalsIgnoreCase("Sr.Team Lead")){
										designationId = 6;
										designation.setDesignationId(designationId);
										user.setDesignationEntity(designation);
									}
									if(designationName.equalsIgnoreCase("Manager")){
										designationId = 7;
										designation.setDesignationId(designationId);
										user.setDesignationEntity(designation);
									}
									if(designationName.equalsIgnoreCase("CEO")){
										designationId = 8;
										designation.setDesignationId(designationId);
										user.setDesignationEntity(designation);
									}
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Designation Name nd Row No " +(row.getRowNum()+1) + " is Invalid Designation...Please Check It.");
									return map;
								}
							}
							
							String emailId = String.valueOf(cellList.get(6));
							String emailRegex = "/^([a-zA-Z0-9_.+-])+\\@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$/";
							if(emailId.isEmpty() || emailId.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Email ID nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It.");
								return map;
							}
							else
							{    boolean res2 = employeeService.toCheckEmpEmailAlreadyExistOrNot(emailId.toLowerCase());
							  if(res2){
								  map.put("success", false);
									map.put("message", "\nColumn Name Email ID nd Row No " +(row.getRowNum()+1) + " is Already Exist.Please Change Email ID.");
									return map;
							  }else{
								user.setEmailId(emailId.toLowerCase());
								/*if(emailId.matches(emailRegex)){
									user.setEmail(emailId);
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Email ID nd Row No " +(row.getRowNum()+1) + " is InValid Email Id.Please Enter Valid Email ID.");
									return map;
								}*/
							  }
							}
							
							String mobile = String.valueOf(cellList.get(7));
							if(mobile.isEmpty() || mobile.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Mobile No nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It.");
								return map;
							}
							else
							{
								if(mobile.length() == 9){
									user.setMobile(mobile);
								}
								else{
									map.put("success", false);
									map.put("message", "\nColumn Name Mobile No nd Row No " +(row.getRowNum()+1) + " is InValid Mobile No.Please Enter Valid Mobile No.");
									return map;
								}
								/*if(mobile.matches("/^([0]|\\+91)?[123456789]\\d{9}$/")){
									user.setMobile(mobile);
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Mobile No nd Row No " +(row.getRowNum()+1) + " is InValid Mobile No.Please Enter Valid Mobile No.");
									return map;
								}*/
							}
							
							String dob = String.valueOf(cellList.get(8));
							if(dob.isEmpty() || dob.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Mobile No nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It.");
								return map;
							}
							else
							{
								user.setDateOfBirth(AppUtil.changeDateFormatToClashs(dob));
							}
							
							String age = String.valueOf(cellList.get(9));
							if(age.isEmpty() || age.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Age nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It.");
								return map;
							}
							else
							{
								//user.setAge(Integer.parseInt(age));
								/*if(age.matches("/d+/")){
									user.setAge(Integer.parseInt(age));
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Age nd Row No " +(row.getRowNum()+1) + " is InValid Age.Please Enter Valid Age.");
									return map;
								}*/
							}
							
							String gender = String.valueOf(cellList.get(10));
							if(gender.isEmpty() || gender.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Gender nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It.");
								return map;
							}
							else
							{
								if(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")){
									if(gender.equalsIgnoreCase("Male")){
										user.setGender(gender);
									}if(gender.equalsIgnoreCase("Female")){
										user.setGender(gender);
									}
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Gender nd Row No " +(row.getRowNum()+1) + " is InValid Gender.Please Enter Valid Gender.");
									return map;
								}
							}
							
							String bankName = String.valueOf(cellList.get(11)); 
							if(bankName.isEmpty() || bankName.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Bank Name nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It");
								return map;
							}
							else
							{
								if(bankName.matches("^[a-zA-Z ]+$")){
									user.setBankName(bankName);
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Bank Name nd Row No " +(row.getRowNum()+1) + " is Allows Only Alphabets.");
									return map;
								}
							}
							
							String branchName = String.valueOf(cellList.get(12)); 
							if(branchName.isEmpty() || branchName.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Branch Name nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It");
								return map;
							}
							else
							{
								if(branchName.matches("^[a-zA-Z ]+$")){
									user.setBankBranchName(branchName);
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Branch Name nd Row No " +(row.getRowNum()+1) + " is Allows Only Alphabets.");
									return map;
								}
							}
							
							String branchAddress = String.valueOf(cellList.get(13)); 
							if(branchAddress.isEmpty() || branchAddress.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Branch Name nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It");
								return map;
							}
							else
							{
								if(branchAddress.matches("^[a-zA-Z[0-9] ]+$")){
									user.setBankBranchAddress(branchAddress);
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Branch Address nd Row No " +(row.getRowNum()+1) + " is Allows Only AlphaNumeric.");
									return map;
								}
							}
							
							String accNo = String.valueOf(cellList.get(14)); 
							if(accNo.isEmpty() || accNo.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Account No nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It");
								return map;
							}
							else
							{
								user.setAccountNumber(accNo);
								/*if(accNo.matches("/^[0-9]*$/") && accNo.matches("/^[0-9]*$/")){
									user.setAccountNumber(accNo);
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Branch Address nd Row No " +(row.getRowNum()+1) + " is Allows Only Digits.");
									return map;
								}*/
							}
							
							String ifsc = String.valueOf(cellList.get(15)); 
							if(ifsc.isEmpty() || ifsc.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name IFSC nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It");
								return map;
							}
							else
							{
								user.setIfscNumber(ifsc);
								/*if(ifsc.matches("/^[A-Za-z]{4}0[A-Z0-9a-z]{6}$/")){
									user.setIfscNumber(ifsc);
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name IFSC nd Row No " +(row.getRowNum()+1) + " is Invalid..Please Enter Valid IFSC.");
									return map;
								}*/
							}
							
							String panNo = String.valueOf(cellList.get(16)); 
							if(panNo.isEmpty() || panNo.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Pan No nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It");
								return map;
							}
							else
							{
								user.setPanNo(panNo);
								/*if(panNo.matches("/^[A-Z]{5}\\d{4}[A-Z]{1}$/")){
									user.setPanNo(panNo);
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Pan No nd Row No " +(row.getRowNum()+1) + " is Invalid..Please Enter Valid Pan No.");
									return map;
								}*/
							}
							
							String aadharNo = String.valueOf(cellList.get(17)); 
							if(aadharNo.isEmpty() || aadharNo.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Aadhar No nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It");
								return map;
							}
							else
							{
								user.setAddharNo(aadharNo);
							}
							
							String city = String.valueOf(cellList.get(18)); 
							if(city.isEmpty() || city.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name City nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It");
								return map;
							}
							else
							{
								user.setCity(city);
								/*if(city.matches("^[a-zA-Z[0-9] ]+$")){
									user.setCity(city);
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name City nd Row No " +(row.getRowNum()+1) + " is Allows Only AlphaNumeric.");
									return map;
								}*/
							}
							
							String address = String.valueOf(cellList.get(19)); 
							if(address.isEmpty() || address.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Address nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It");
								return map;
							}
							else
							{
								user.setAddress(address);
								/*if(address.matches("^[a-zA-Z[0-9] ]+$")){
									user.setAddress(address);
								}else{
									map.put("success", false);
									map.put("message", "\nColumn Name Address nd Row No " +(row.getRowNum()+1) + " is Allows Only AlphaNumeric.");
									return map;
								}*/
							}
							
							String dateOfJoining = String.valueOf(cellList.get(20)); 
							if(dateOfJoining.isEmpty() || dateOfJoining.equalsIgnoreCase("null"))
							{
								map.put("success", false);
								map.put("message", "\nColumn Name Address nd Row No " +(row.getRowNum()+1) + " is empty...Please Fill It");
								return map;
							}
							else
							{
								    user.setDateOfJoining(AppUtil.changeDateFormatToClashs(dateOfJoining));
							}
							
							user.setActive(1);
							PasswordUtil util = new PasswordUtil();
							genPassword = util.generatePassword();
							BCryptPasswordEncoder en = new BCryptPasswordEncoder();
							// String password = util.generateMD5(genPassword);
							user.setPassword(en.encode(genPassword));
							
						    rowPasswordList.add(genPassword);
							rowList.add(user);
							cellList.clear();
							departmentId = 0;
							designationId = 0;
					}
						int i = 0;
							for (EmployeeEntity user : rowList) {
								EmployeeEntity savedEmployeeEntity = employeService.save(user);
								if(Objects.nonNull(savedEmployeeEntity)){
									user.setPassword(rowPasswordList.get(i++));
								     try {
										mailUtil.sendUserEmail(user);
									} catch (Exception e) {
										System.err.println(e.getMessage());
										map.put("success" ,true);
										map.put("message","But Fails to Send Mail.");
										return map;
									}
								}
							}
							map.put("success", true);
					}
				else
				{
					map.put("success", false);
					map.put("message", "\nColumn Name Miss Match....! Please Check the Column Name and Re-Enter Valid Column Name In Excel Sheet");
					return map;
				}
					map.put("success", true);
			}
			catch (Exception e) {
				map.put("success", false);
				e.printStackTrace();
			}

		}else{
			map.put("success", false);
		}
		return map;
   }
	
	
	
	
	
}
