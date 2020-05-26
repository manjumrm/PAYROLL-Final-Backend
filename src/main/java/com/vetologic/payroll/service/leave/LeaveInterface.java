package com.vetologic.payroll.service.leave;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vetologic.payroll.entity.LeaveApplyEntity;

public interface LeaveInterface extends JpaRepository<LeaveApplyEntity, Integer>{

}
