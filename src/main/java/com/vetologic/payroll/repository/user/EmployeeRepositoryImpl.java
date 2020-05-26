package com.vetologic.payroll.repository.user;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vetologic.payroll.entity.BankEntity;
import com.vetologic.payroll.entity.ContactUsEntity;
import com.vetologic.payroll.entity.DepartmentEntity;
import com.vetologic.payroll.entity.DesignationEntity;
import com.vetologic.payroll.entity.EmployeeEntity;
import com.vetologic.payroll.entity.InvestmentDeclarationActualEntity;
import com.vetologic.payroll.entity.InvestmentDeclarationPlannedEntity;
import com.vetologic.payroll.entity.LeaveApplyEntity;
import com.vetologic.payroll.entity.SalaryEntity;
import com.vetologic.payroll.entity.TaxRateEntity;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository{

	@PersistenceUnit
	private EntityManagerFactory factory;
	
	@Override
	public EmployeeEntity getEmployeeDetail(EmployeeEntity employeeEntity) {
		EmployeeEntity employeeEntity2 = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<EmployeeEntity> query = (Query<EmployeeEntity>) entityManager.createQuery("Select e from EmployeeEntity e WHERE e.username=?1");
			query.setParameter(1, employeeEntity.getUsername());
			employeeEntity2 = query.getSingleResult();
			entityTransaction.commit();
			return employeeEntity2;
			}catch (Exception e) {
		    System.err.println(e.getMessage());
		}finally {
			entityManager.close();
		}
		return employeeEntity2;
	}

	@Override
	public List<SalaryEntity> getApprovedSalary() {
		List<SalaryEntity> salaries = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<SalaryEntity> query = (Query<SalaryEntity>) entityManager.createQuery("select s from SalaryEntity s where  s.salaryStatus='Approved'");
			salaries = query.getResultList();
			entityTransaction.commit();
			return salaries;
			}catch (Exception e) {
		    System.err.println(e.getMessage());
		}finally {
			entityManager.close();
		}
		return salaries;
	}

	@Override
	public List<SalaryEntity> getRejectedSalary() {
		List<SalaryEntity> salaries = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<SalaryEntity> query = (Query<SalaryEntity>) entityManager.createQuery("select s from SalaryEntity s where  s.salaryStatus='Rejected'");
			salaries = query.getResultList();
			entityTransaction.commit();
			return salaries;
			}catch (Exception e) {
		    System.err.println(e.getMessage());
		}finally {
			entityManager.close();
		}
		return salaries;
	}

	@Override
	public List<DesignationEntity> getDesignationList() {
		List<DesignationEntity> designationEntities = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<DesignationEntity> query = (Query<DesignationEntity>) entityManager.createQuery("select d from DesignationEntity d");
			designationEntities = query.getResultList();
			entityTransaction.commit();
			return designationEntities;
			}catch (Exception e) {
		    System.err.println(e.getMessage());
		}finally {
			entityManager.close();
		}
		return designationEntities;
	}

	@Override
	public List<DepartmentEntity> getDepartmentList() {
		List<DepartmentEntity> departmentEntities = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<DepartmentEntity> query = (Query<DepartmentEntity>) entityManager.createQuery("select d from DepartmentEntity d");
			departmentEntities = query.getResultList();
			entityTransaction.commit();
			return departmentEntities;
			}catch (Exception e) {
		    System.err.println(e.getMessage());
		}finally {
			entityManager.close();
		}
		return departmentEntities;
	}

	@Override
	public List<BankEntity> getBankList() {
		List<BankEntity> backEntities = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<BankEntity> query = (Query<BankEntity>) entityManager.createQuery("select b from BankEntity b");
			backEntities = query.getResultList();
			entityTransaction.commit();
			return backEntities;
			}catch (Exception e) {
		    System.err.println(e.getMessage());
		}finally {
			entityManager.close();
		}
		return backEntities;
	}

	@Override
	public boolean saveEmployeeDetails(EmployeeEntity employeeEntity) {
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			entityManager.persist(employeeEntity);
			entityTransaction.commit();
			return true;
			}catch (Exception e) {
		    e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return false;
	}

	@Override
	public boolean toCheckEmpNameAlreadyExistOrNot(String empName) {
		EmployeeEntity employeeEntity = null;
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			@SuppressWarnings("unchecked")
			Query<EmployeeEntity> query = (Query<EmployeeEntity>) entityManager
					.createQuery("select e from EmployeeEntity e where e.active=?1 and e.username=?2");
			query.setParameter(1, 1);
			query.setParameter(2, empName);
			employeeEntity = query.setMaxResults(1).uniqueResult();
			entityTransaction.commit();
			if(Objects.nonNull(employeeEntity)){
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			entityManager.close();
		}
		return false;
	}

	@Override
	public boolean toCheckEmpEmailAlreadyExistOrNot(String empEmail) {
		EmployeeEntity employeeEntity = null;
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			@SuppressWarnings("unchecked")
			Query<EmployeeEntity> query = (Query<EmployeeEntity>) entityManager
					.createQuery("select e from EmployeeEntity e where e.active=?1 and e.emailId=?2");
			query.setParameter(1, 1);
			query.setParameter(2, empEmail);
			employeeEntity = query.uniqueResult();
			entityTransaction.commit();
			if(Objects.nonNull(employeeEntity)){
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			entityManager.close();
		}
			return false;
	}

	@Override
	public boolean toCheckSupNameAlreadyExistOrNot(String supName) {
		EmployeeEntity employeeEntity = null;
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			@SuppressWarnings("unchecked")
			Query<EmployeeEntity> query = (Query<EmployeeEntity>) entityManager
					.createQuery("select e from EmployeeEntity e where e.active=?1 and e.username=?2");
			query.setParameter(1, 1);
			query.setParameter(2, supName);
			employeeEntity = query.setMaxResults(1).uniqueResult();
			entityTransaction.commit();
			if(Objects.nonNull(employeeEntity)){
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			entityManager.close();
		}
		return false;
	
	}

	@Override
	public List<EmployeeEntity> getAllEmployeeDetails() {
		List<EmployeeEntity> employeeEntitys = null;
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			@SuppressWarnings("unchecked")
			Query<EmployeeEntity> query = (Query<EmployeeEntity>) entityManager
					.createQuery("select e from EmployeeEntity e where e.active=?1");
			query.setParameter(1, 1);
			employeeEntitys = query.list();
			entityTransaction.commit();
			return employeeEntitys;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			entityManager.close();
		}
	
		return employeeEntitys;
	}

	@Override
	public boolean deleteEmployee(int eId) {
		int result = 0;
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			@SuppressWarnings("unchecked")
			Query<EmployeeEntity> query = (Query<EmployeeEntity>) entityManager
					.createQuery("update EmployeeEntity e set e.active=?1 where e.userId=?2");
			query.setParameter(1, 0);
			query.setParameter(2, eId);
			result = query.executeUpdate();
			entityTransaction.commit();
			if(result != 0)
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			entityManager.close();
		}
		return false;
	}

	@Override
	public InvestmentDeclarationPlannedEntity getEmpInvestmentDecPlanned(int userId) {
		InvestmentDeclarationPlannedEntity InvestmentDeclarationPlannedEntity = null;
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			@SuppressWarnings("unchecked")
			Query<InvestmentDeclarationPlannedEntity> query = (Query<InvestmentDeclarationPlannedEntity>) entityManager
					.createQuery("from InvestmentDeclarationPlannedEntity where employeeEntity.userId=?1");
			query.setParameter(1, userId);
			InvestmentDeclarationPlannedEntity = query.uniqueResult();
			entityTransaction.commit();
			return InvestmentDeclarationPlannedEntity;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			entityManager.close();
		}
		return InvestmentDeclarationPlannedEntity;
	}

	@Override
	public InvestmentDeclarationActualEntity getEmpInvestmentDecActual(int userId) {
		InvestmentDeclarationActualEntity InvestmentDeclarationActualEntity = null;
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			@SuppressWarnings("unchecked")
			Query<InvestmentDeclarationActualEntity> query = (Query<InvestmentDeclarationActualEntity>) entityManager
					.createQuery("from InvestmentDeclarationActualEntity where employeeEntity.userId=?1 ORDER BY id DESC");
			query.setParameter(1, userId);
			InvestmentDeclarationActualEntity = query.uniqueResult();
			entityTransaction.commit();
			return InvestmentDeclarationActualEntity;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			entityManager.close();
		}
		return InvestmentDeclarationActualEntity;
	}

	@Override
	public List<TaxRateEntity> getAllTaxRateList() {
		List<TaxRateEntity> taxRateEntities = null;
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			@SuppressWarnings("unchecked")
			Query<TaxRateEntity> query = (Query<TaxRateEntity>) entityManager
					.createQuery("from TaxRateEntity");
			taxRateEntities = query.getResultList();
			entityTransaction.commit();
			return taxRateEntities;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			entityManager.close();
		}
		return taxRateEntities;
	}

	@Override
	public boolean toCheckInvestmentDeclaration(int userId) {
		InvestmentDeclarationPlannedEntity InvestmentDeclarationPlannedEntity = null;
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			@SuppressWarnings("unchecked")
			Query<InvestmentDeclarationPlannedEntity> query = (Query<InvestmentDeclarationPlannedEntity>) entityManager
					.createQuery("from InvestmentDeclarationPlannedEntity where employeeEntity.userId=?1");
			query.setParameter(1, userId);
			InvestmentDeclarationPlannedEntity = query.uniqueResult();
			entityTransaction.commit();
			if(Objects.nonNull(InvestmentDeclarationPlannedEntity))
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			entityManager.close();
		}
		return false;
	}

	@Override
	public List<SalaryEntity> getAllSalaryDetails() {
		List<SalaryEntity> salaryEntities =  null;
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			@SuppressWarnings("unchecked")
			Query<SalaryEntity> query = (Query<SalaryEntity>) entityManager
					.createQuery("from SalaryEntity where active=?1");
			query.setParameter(1, 1);
			salaryEntities = query.getResultList();
			entityTransaction.commit();
			return salaryEntities;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			entityManager.close();
		}
		return salaryEntities;
	}

	@Override
	public boolean deleteSalary(int salId) {
		int result = 0;
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			@SuppressWarnings("unchecked")
			Query<SalaryEntity> query = (Query<SalaryEntity>) entityManager
					.createQuery("update SalaryEntity e set e.active=?1 where e.salaryId=?2");
			query.setParameter(1, 0);
			query.setParameter(2, salId);
			result = query.executeUpdate();
			entityTransaction.commit();
			if(result != 0)
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			entityManager.close();
		}
		return false;
	}

	@Override
	public boolean checkEmail(String forgotPasswordEmail) {
		EmployeeEntity userList = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<EmployeeEntity> query = (Query<EmployeeEntity>) entityManager.createQuery("from EmployeeEntity WHERE active=?1 and emailId=?2");
			query.setParameter(1, 1);
			query.setParameter(2,forgotPasswordEmail);
			userList = query.uniqueResult();
			entityTransaction.commit();
			if(Objects.nonNull(userList)) {
				return true;
			}else {
				return false;
			}
		}catch (Exception e) {
		    System.err.println(e.getMessage());
		}finally {
			entityManager.close();
		}
		return false;
	}

	@Override
	public boolean changeForgotPassword(String forgotPasswordEmail, String newPassword) {
		int res = 0;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<EmployeeEntity> query = (Query<EmployeeEntity>) entityManager.createQuery("update EmployeeEntity set password=?1 WHERE emailId=?2");
			query.setParameter(1, newPassword);
			query.setParameter(2,forgotPasswordEmail);
			res = query.executeUpdate();
			entityTransaction.commit();
			if(res != 0) {
				return true;
			}else {
				return false;
			}
		}catch (Exception e) {
		    System.err.println(e.getMessage());
		}finally {
			entityManager.close();
		}
		return false;

	}

	@Override
	public boolean saveContactUsDetails(ContactUsEntity contactUS) {
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			entityManager.persist(contactUS);
			entityTransaction.commit();
			return true;
			}catch (Exception e) {
		    System.err.println(e.getMessage());
		}finally {
			entityManager.close();
		}
		  return false;
	}

	@Override
	public List<SalaryEntity> approveOrRejectSalary(String userName) {
		List<SalaryEntity> entities = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<SalaryEntity> query = (Query<SalaryEntity>) entityManager.createQuery("from SalaryEntity where  salaryStatus='Pending' and employeeEntity.userId IN(select userId from EmployeeEntity where supervisorName = ?1)");
			query.setParameter(1, userName);
			entities = query.getResultList();
			entityTransaction.commit();
			return entities;
			}catch (Exception e) {
		    System.err.println(e.getMessage());
		}finally {
			entityManager.close();
		}
		  return entities;
	}

	@Override
	public List<SalaryEntity> getSalaryAppliesForApproveOrReject(String username) {
		List<SalaryEntity> salaryEntities = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<SalaryEntity> query = (Query<SalaryEntity>)entityManager.createQuery("from SalaryEntity where salaryStatus='Pending' and employeeEntity.userId IN(select userId from EmployeeEntity where supervisorName = ?1)");
			query.setParameter(1,username);
			salaryEntities = query.list();
			entityTransaction.commit();
			return salaryEntities;
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return salaryEntities;
	}

	@Override
	public List<SalaryEntity> getApprovedSalary(int userId) {
		List<SalaryEntity> salaryEntities = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<SalaryEntity> query = (Query<SalaryEntity>)entityManager.createQuery("from SalaryEntity where salaryStatus='Approved'");
			salaryEntities = query.list();
			entityTransaction.commit();
			return salaryEntities;
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return salaryEntities;
	}

	@Override
	public List<SalaryEntity> getRejectedSalary(int userId) {
		List<SalaryEntity> salaryEntities = null;
		EntityManager entityManager=factory.createEntityManager();
		EntityTransaction entityTransaction=entityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query<SalaryEntity> query = (Query<SalaryEntity>)entityManager.createQuery("from SalaryEntity where salaryStatus='Rejected'");
			salaryEntities = query.list();
			entityTransaction.commit();
			return salaryEntities;
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return salaryEntities;
	}
	
	

}
