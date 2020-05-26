package com.vetologic.payroll.service.salary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vetologic.payroll.entity.InvestmentDeclarationActualEntity;

public interface InvestmentActual extends JpaRepository<InvestmentDeclarationActualEntity, Integer>{

}
