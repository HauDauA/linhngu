package fa.edu.service.implement;

import java.util.List;

import fa.edu.model.Customer;
import fa.edu.security.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fa.edu.model.Employee;
import fa.edu.repository.EmployeeRepository;
import fa.edu.service.EmployeeService;

import javax.transaction.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository employRepo;

	@Override
	public Employee getEmplByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean findByEmail(String email) {
		return employRepo.findByEmail(email).isPresent();
	}

	@Override
	public boolean findByPhone(String phone) {
		return employRepo.findByPhone(phone).isPresent();
	}

	@Override
	public boolean findByUserName(String userName) {
		return employRepo.findByUsername(userName).isPresent();
	}

	@Override
	public boolean save(Employee employee) {
		if (employee != null) {
			employRepo.save(employee);
			return true;
		}
		return false;
	}

	@Override
	public void deleteById(String id) {
		employRepo.deleteEmployee(id);
	}

	@Override
	public List<Employee> lisEmpls() {
		return employRepo.listEmpls();
	}

	@Override
	public Page<Employee> listEmpls(int pageNumber, String keyword) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		if (keyword != null) {
			return employRepo.listEmps(keyword, pageable);
		}
		return employRepo.listEmpsNoSearch(pageable);
	}

	@Override
	public void updateResetPassword(String token, String email) throws CustomerNotFoundException {
		Employee employee= employRepo.findEmail(email)	;
		if (employee != null){
			employee.setResetPasswordToken(token);
			employRepo.save(employee);
		}else {
			throw  new CustomerNotFoundException("Could not find any employee with email "+email);
		}
	}

	public Employee getByResetPasswordToken(String resetPasswordToken){
		return  employRepo.findByResetPasswordToken(resetPasswordToken);
	}

	public void updatePassword(Employee employee , String newPassword){
		BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
		String encodePassword=cryptPasswordEncoder.encode(newPassword);
		employee.setPassword(encodePassword);
		employee.setResetPasswordToken(null);
		employRepo.save(employee);
	}

	@Override
	public List<Employee> top5Emp() {
		Pageable pageable = PageRequest.of( 1, 5);
		return employRepo.top5Emp(pageable);
	}

}
