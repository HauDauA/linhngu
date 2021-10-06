package fa.edu.repository;

import org.springframework.data.repository.CrudRepository;

import fa.edu.model.WorkPlace;

public interface PlaceRepository extends CrudRepository<WorkPlace, Integer> {

}
