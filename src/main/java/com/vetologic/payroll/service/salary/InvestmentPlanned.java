package com.vetologic.payroll.service.salary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vetologic.payroll.entity.InvestmentDeclarationPlannedEntity;

public interface InvestmentPlanned extends JpaRepository<InvestmentDeclarationPlannedEntity, Integer>{

}
