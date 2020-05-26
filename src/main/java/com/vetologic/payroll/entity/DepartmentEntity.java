package com.vetologic.payroll.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DEPARTMENT_NAMES")
public class DepartmentEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DEPARTMENT_ID")
	private int departmentId;
	
	@Column(name="DEPARTMENT_NAME")
	private String departmentName;

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
