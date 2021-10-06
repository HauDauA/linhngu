package fa.edu.controller;

import fa.edu.repository.CustomerRepository;
import fa.edu.repository.EmployeeRepository;
import fa.edu.repository.InjectionResultRepository;
import fa.edu.repository.InjectionScheduleRepository;
import fa.edu.repository.VaccineRepository;
import fa.edu.security.CustomerNotFoundException;
import fa.edu.security.Utility;
import fa.edu.service.EmployeeService;
import fa.edu.service.implement.CustomerServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

	@Autowired
	private CustomerRepository cusRepo;
	
	@Autowired
	private VaccineRepository vaccineRepo;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private InjectionScheduleRepository scheduleRepo;
	
	@Autowired
	private InjectionResultRepository resultRepo;
	
	private Pageable pageable = PageRequest.of(0, 5);
	
	
	@GetMapping("/403")
	public String error403() {
		return "403";
	}

	
	@GetMapping("/login")
	public String loginPage() {
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
			return "login"	;
		}
		return "redirect:/admin";
	}

	
	@GetMapping("/admin")
	public String viewHome(Model model) {
		model.addAttribute("countCus",cusRepo.countCus());
		model.addAttribute("countBos",empRepo.countBos());
		model.addAttribute("countEmp",empRepo.countEmp());
		model.addAttribute("ccountVac",vaccineRepo.countVac());
		model.addAttribute("countSchedule",scheduleRepo.countSchedule());
		model.addAttribute("countResult",resultRepo.countResult());
		return "dashboard";
	}
}
