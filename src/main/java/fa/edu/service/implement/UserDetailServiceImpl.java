package fa.edu.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fa.edu.model.Employee;
import fa.edu.repository.EmployeeRepository;
import fa.edu.security.MyUserDetails;

public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private EmployeeRepository emplRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee empl =emplRepo.getEmplByUsername(username);
		if(empl==null) {
			throw new UsernameNotFoundException("Could not find user!!");
		}
		return new MyUserDetails(empl);
	}



}
