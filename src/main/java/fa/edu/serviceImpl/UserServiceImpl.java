package fa.edu.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fa.edu.model.User;
import fa.edu.responsitory.UserResponsitory;
import fa.edu.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserResponsitory userResponsitory;
	
	@Override
	public User saveUser(User user) {
		return userResponsitory.save(user);
	}

	@Override
	public List<User> users() {
		return userResponsitory.findAll();
	}

	@Override
	public User findById(Integer id) {
		return userResponsitory.findById(id).get();
	}

	@Override
	public void deleteUser(Integer id) {
		userResponsitory.deleteById(id);;
		
	}

	
}
