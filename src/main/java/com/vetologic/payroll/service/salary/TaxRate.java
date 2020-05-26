package com.vetologic.payroll.service.salary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vetologic.payroll.entity.TaxRateEntity;

public interface TaxRate extends JpaRepository<TaxRateEntity, Integer>{

}
