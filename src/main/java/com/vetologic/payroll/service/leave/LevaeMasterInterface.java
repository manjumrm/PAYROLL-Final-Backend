package com.vetologic.payroll.service.leave;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vetologic.payroll.entity.LeaveTypeMasterEntity;

public interface LevaeMasterInterface extends JpaRepository<LeaveTypeMasterEntity, Integer>{

}
