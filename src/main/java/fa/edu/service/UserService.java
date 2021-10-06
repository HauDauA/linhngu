package fa.edu.service;

import java.util.List;
import java.util.Optional;

import fa.edu.model.User;

public interface UserService {
	
	User saveUser(User user);
	
	List<User> users();

	User findById(Integer id);
	
	void deleteUser(Integer id);
}
