package com.vetologic.payroll.service.salary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vetologic.payroll.entity.SalaryEntity;

public interface SalaryInterface extends JpaRepository<SalaryEntity, Integer>{

}
