package com.vetologic.payroll.repository.Jwt;

import org.springframework.data.repository.CrudRepository;

import com.vetologic.payroll.entity.EmployeeEntity;

public interface UserRepository extends CrudRepository<EmployeeEntity, Integer> {
	EmployeeEntity findByUsername(String username);
}
