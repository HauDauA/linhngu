package fa.edu.responsitory;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fa.edu.model.User;
@Repository
public interface UserResponsitory extends JpaRepository<User, Integer> {
}
