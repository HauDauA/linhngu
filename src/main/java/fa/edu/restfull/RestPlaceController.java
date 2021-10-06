package fa.edu.restfull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fa.edu.model.WorkPlace;
import fa.edu.repository.PlaceRepository;

@RestController
public class RestPlaceController {
	@Autowired
	private PlaceRepository place;
	
	@GetMapping("/places")
	public List<WorkPlace> places(){
		return (List<WorkPlace>) place.findAll();
	}

}
