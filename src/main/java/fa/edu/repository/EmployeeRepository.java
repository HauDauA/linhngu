package fa.edu.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fa.edu.model.Employee;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, String> {

	@Query("Select e FROM Employee e WHERE e.username = :username AND e.status = 1")
	public Employee getEmplByUsername(@Param("username") String username);

	@Query("Select e From Employee e " + "WHERE CONCAT(e.fullName,e.email,e.phone)"
			+ "	LIKE %?1% AND e.status =1 AND e.idOfRole=1  order by e.employeeId desc")
	public Page<Employee> listEmps(String keyword, Pageable pageable);

	@Query("Select e From Employee e WHERE e.status =1 AND e.idOfRole=1 order by e.employeeId desc")
	public Page<Employee> listEmpsNoSearch(Pageable pageable);
	
	@Query("Select e From Employee e WHERE e.status =1 AND e.idOfRole=1 order by e.employeeId desc")
	public List<Employee> top5Emp(Pageable pageable);

	@Query("Select e From Employee e WHERE e.status =1 order by e.employeeId desc")
	public List<Employee> listEmpls();

	@Query("Select e.username From Employee e WHERE e.username= ?1")
	public Optional<Employee> findByUsername(String username);

	@Query("Select e.phone From Employee e WHERE e.phone= ?1")
	public Optional<Employee> findByPhone(String phone);

	@Query("Select e.email From Employee e WHERE e.email= ?1")
	public Optional<Employee> findByEmail(String email);

	@Query("Select e From Employee e WHERE e.email= ?1")
	public Employee findEmail(String email);
	
	@Query("Select Count(*) FROM Employee e Where e.status=1 AND e.idOfRole=1")
	public Integer countEmp();
	
	@Query("Select Count(*) FROM Employee e Where e.status=1 AND e.idOfRole=2")
	public Integer countBos();
	

	public  Employee findByResetPasswordToken(String token);

	@Transactional
	@Modifying
	@Query("Update Employee e Set e.status = 0 Where e.employeeId = :id")
	void deleteEmployee(@Param("id") String id);

}
