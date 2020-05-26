package com.vetologic.payroll.entity;

import java.util.Comparator;

public class LeavesSortByName  implements Comparator<LeaveManagementEntity>{

	@Override
	public int compare(LeaveManagementEntity o1, LeaveManagementEntity o2) {
		return o1.getLeaveType().getLeaveTypeName().compareTo(o2.getLeaveType().getLeaveTypeName()) ;
	}

}
