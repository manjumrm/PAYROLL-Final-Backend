package com.vetologic.payroll.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BANK_NAMES")
public class BankEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="BANK_ID")
	private  int bankId;
	
	@Column(name="BANK_NAME")
	private String bankName;

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
