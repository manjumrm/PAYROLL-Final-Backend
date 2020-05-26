package com.vetologic.payroll.service.leave;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vetologic.payroll.entity.LeaveManagementEntity;

public interface LeaveManagementInterface extends JpaRepository<LeaveManagementEntity,Integer>{

}
