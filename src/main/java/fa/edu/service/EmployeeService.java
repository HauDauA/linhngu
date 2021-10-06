package fa.edu.service;

import java.util.List;


import fa.edu.security.CustomerNotFoundException;
import org.springframework.data.domain.Page;

import fa.edu.model.Employee;

public interface EmployeeService {
	public Employee getEmplByUsername(String username);
	
	boolean findByEmail(String email);
	
	boolean findByPhone(String phone);
	
	boolean findByUserName(String userName);
	
	boolean save(Employee employee);
	
	void deleteById(String id);
	
	public List<Employee> lisEmpls();
	
	Page<Employee> listEmpls(int pageNumber,String keyword);
	
	List<Employee> top5Emp();

	public  void updateResetPassword(String token,String email) throws CustomerNotFoundException;
}
