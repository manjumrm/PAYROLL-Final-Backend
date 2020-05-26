package com.vetologic.payroll.repository.leave;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.vetologic.payroll.entity.EmployeeEntity;
import com.vetologic.payroll.entity.LeaveApplyEntity;
import com.vetologic.payroll.entity.LeaveManagementEntity;

@Repository
public class LeaveManagementRepositoryImpl implements LeaveManagementRepository{
	
	@PersistenceUnit
	private EntityManagerFactory factory;

	@Override
	public List<LeaveManagementEntity> getLeaveDetailsByUserId(int userId) {
		List<LeaveManagementEntity> leaveManagementEntities = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<LeaveManagementEntity> query = (Query<LeaveManagementEntity>) entityManager.createQuery("Select l from LeaveManagementEntity l WHERE active=?1 and l.employeeEntity.userId=?2");
			query.setParameter(1, 1);
			query.setParameter(2, userId);
			leaveManagementEntities = query.getResultList();
			entityTransaction.commit();
			return leaveManagementEntities;
			}catch (Exception e) {
		    System.err.println(e.getMessage());
		}finally {
			entityManager.close();
		}
		return leaveManagementEntities;
	}

	@Override
	public List<LeaveApplyEntity> getLeaveStatusOfEmployees(EmployeeEntity employeeEntity) {
		List<LeaveApplyEntity> leaveApplies = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<LeaveApplyEntity> query = (Query<LeaveApplyEntity>)entityManager.createQuery("select l from LeaveApplyEntity l where  l.leaveStatus='Approved' and l.leaveManagementEntity.leaveId IN (select lm.leaveId from LeaveManagementEntity lm where lm.employeeEntity.userId IN(select e.userId from EmployeeEntity e where e.userId=?1))");
			query.setParameter(1,employeeEntity.getUserId());
			leaveApplies = query.getResultList();
			entityTransaction.commit();
			return leaveApplies;
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return leaveApplies;
	}

	@Override
	public List<LeaveApplyEntity> getRejectedLeaveStatusOfEmployees(EmployeeEntity employeeEntity) {
		List<LeaveApplyEntity> leaveApplies = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<LeaveApplyEntity> query = (Query<LeaveApplyEntity>)entityManager.createQuery("select l from LeaveApplyEntity l where  l.leaveStatus= 'Reject' and l.leaveManagementEntity.leaveId IN (select lm.leaveId from LeaveManagementEntity lm where lm.employeeEntity.userId IN(select e.userId from EmployeeEntity e where e.userId = ?1))");
			query.setParameter(1,employeeEntity.getUserId());
			leaveApplies = query.list();
			entityTransaction.commit();
			return leaveApplies;
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return leaveApplies;
	}

	@Override
	public List<LeaveApplyEntity> getLeaveAppliesForApproveOrReject(String userName) {
		List<LeaveApplyEntity> leaveApplies = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<LeaveApplyEntity> query = (Query<LeaveApplyEntity>)entityManager.createQuery("from LeaveApplyEntity where leaveStatus='Pending' and leaveManagementEntity.leaveId IN (select leaveId from LeaveManagementEntity where employeeEntity.userId IN(select userId from EmployeeEntity where supervisorName = ?1))");
			query.setParameter(1,userName);
			leaveApplies = query.list();
			entityTransaction.commit();
			return leaveApplies;
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return leaveApplies;
	}

	@Override
	public boolean checkLeaveTypeAlreadyexitForUser(int empId, int empIdleaveMasterId) {
		LeaveManagementEntity leaveApplies = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<LeaveManagementEntity> query = (Query<LeaveManagementEntity>)entityManager.createQuery("from LeaveManagementEntity  where active=?1 and employeeEntity.userId=?2 and leaveType.leaveTypeId=?3");
			query.setParameter(1,1);
			query.setParameter(2,empId);
			query.setParameter(3,empIdleaveMasterId);
			leaveApplies = query.uniqueResult();
			entityTransaction.commit();
			if(Objects.nonNull(leaveApplies)) {
				return true;
			}
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return false;
	}

	@Override
	public List<LeaveManagementEntity> getEmpLeaveList(int empid) {
		List<LeaveManagementEntity> leaveApplies = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<LeaveManagementEntity> query = (Query<LeaveManagementEntity>)entityManager.createQuery("from LeaveManagementEntity  where active=?1 and employeeEntity.userId=?2");
			query.setParameter(1,1);
			query.setParameter(2,empid);
			leaveApplies = query.getResultList();
			entityTransaction.commit();
				return leaveApplies;
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return leaveApplies;
	}

	@Override
	public List<LeaveManagementEntity> getLeaveDetailsByUserIdAndLeaveTypeId(int empId, int leaveTypeId) {
		List<LeaveManagementEntity> leaveApplies = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<LeaveManagementEntity> query = (Query<LeaveManagementEntity>)entityManager.createQuery("from LeaveManagementEntity  where active=?1 and employeeEntity.userId=?2 and leaveType.leaveTypeId=?3");
			query.setParameter(1,1);
			query.setParameter(2,empId);
			query.setParameter(3,leaveTypeId);
			leaveApplies = query.getResultList();
			entityTransaction.commit();
				return leaveApplies;
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return leaveApplies;
	}

	@Override
	public List<LeaveApplyEntity> getLeaveApplyDetailsByUserIdAndLeaveTypeId(
			LeaveManagementEntity leaveManagementEntity) {
		List<LeaveApplyEntity> leaveApply = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<LeaveApplyEntity> query = (Query<LeaveApplyEntity>) entityManager.createQuery("from LeaveApplyEntity where active=?1 and leaveManagementEntity.leaveId=?2 and leaveStatus='Approved'", LeaveApplyEntity.class);
			query.setParameter(1, 1);
			query.setParameter(2, leaveManagementEntity.getLeaveId());
			leaveApply = query.list();
			entityTransaction.commit();
			return leaveApply;
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}finally {
			entityManager.close();
		}

		return leaveApply;

	}

	@Override
	public LeaveManagementEntity getLeaveDetailsForUser(int empId, int empIdleaveMasterId) {
		LeaveManagementEntity leaveApplies = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<LeaveManagementEntity> query = (Query<LeaveManagementEntity>)entityManager.createQuery("from LeaveManagementEntity  where active=?1 and employeeEntity.userId=?2 and leaveType.leaveTypeId=?3");
			query.setParameter(1,1);
			query.setParameter(2,empId);
			query.setParameter(3,empIdleaveMasterId);
			leaveApplies = query.uniqueResult();
			entityTransaction.commit();
				return leaveApplies;
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return leaveApplies;
	}

	@Override
	public List<LeaveApplyEntity> getUserApprovedLeaveStatus(int userId) {
			List<LeaveApplyEntity> leaveApply = null;
			EntityManager entityManager=factory.createEntityManager();
			EntityTransaction entityTransaction=entityManager.getTransaction();
			try {
				entityTransaction.begin();
				Query<LeaveApplyEntity> query = (Query<LeaveApplyEntity>) entityManager.createQuery("from LeaveApplyEntity where  leaveStatus= 'Approved' and leaveManagementEntity.leaveId IN (select leaveId from LeaveManagementEntity where employeeEntity.userId IN(select userId from EmployeeEntity where userId = ?1))", LeaveApplyEntity.class);
				query.setParameter(1, userId);
				leaveApply = query.list();
				entityTransaction.commit();
				return leaveApply;
			} catch (Exception e) {
				entityTransaction.rollback();
				e.printStackTrace();
			}finally {
				entityManager.close();
			}

			return leaveApply;

	}

	@Override
	public List<LeaveApplyEntity> getUserRejectedLeaveStatus(int userId) {
		List<LeaveApplyEntity> leaveApply = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<LeaveApplyEntity> query = (Query<LeaveApplyEntity>) entityManager.createQuery("from LeaveApplyEntity where  leaveStatus= 'Reject' and leaveManagementEntity.leaveId IN (select leaveId from LeaveManagementEntity where employeeEntity.userId IN(select userId from EmployeeEntity where userId = ?1))", LeaveApplyEntity.class);
			query.setParameter(1, userId);
			leaveApply = query.list();
			entityTransaction.commit();
			return leaveApply;
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}finally {
			entityManager.close();
		}

		return leaveApply;
	}

}
