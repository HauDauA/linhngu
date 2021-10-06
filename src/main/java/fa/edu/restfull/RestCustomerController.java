package fa.edu.restfull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fa.edu.model.Customer;
import fa.edu.model.Vaccine;
import fa.edu.model.WorkPlace;
import fa.edu.repository.CustomerRepository;
import fa.edu.repository.InjectionScheduleRepository;
import fa.edu.repository.PlaceRepository;
import fa.edu.service.CustomerService;
import fa.edu.service.VaccineService;

@RestController
public class RestCustomerController {
	@Autowired
	private CustomerService cuService;

	@Autowired
	private CustomerRepository cuRepo;

	@GetMapping("/customers")
	public List<Customer> customers() {
		return cuService.findAll();
	}

	@GetMapping("/customers/{id}")
	private Customer customer(@PathVariable("id") String id) {
		return cuService.findById(id);
	}

	@PostMapping("/customer/save")
	private Customer add(@RequestBody Customer customer) {
		return cuRepo.save(customer);
	}

}
