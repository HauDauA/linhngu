package fa.edu.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fa.edu.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

	@Query("Select r FROM Role r Where r.role_name= ?1")
	public Role findByName(String name);
}
