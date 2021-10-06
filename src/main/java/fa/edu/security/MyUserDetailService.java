package fa.edu.security;

import fa.edu.model.Employee;
import fa.edu.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private EmployeeRepository  repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee emp=repository.getEmplByUsername(username);
        if (emp != null) {
            return new MyUserDetails(emp);
        }
        throw  new UsernameNotFoundException("Could not find user with username"+ username);
    }
}
