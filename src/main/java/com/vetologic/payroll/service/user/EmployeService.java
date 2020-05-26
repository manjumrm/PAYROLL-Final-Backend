package com.vetologic.payroll.service.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vetologic.payroll.entity.EmployeeEntity;

public interface EmployeService extends JpaRepository<EmployeeEntity, Integer>{

}
