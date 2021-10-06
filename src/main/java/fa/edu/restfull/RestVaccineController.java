package fa.edu.restfull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fa.edu.model.Vaccine;
import fa.edu.service.VaccineService;

@RestController
public class RestVaccineController {
	@Autowired
	private VaccineService vcService;
	
	@GetMapping("/vaccines")
	public List<Vaccine> vaccines(){
		return  vcService.findAll();
	}
	

}
