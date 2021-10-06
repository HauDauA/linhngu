package fa.edu.controller;

import fa.edu.model.Employee;
import fa.edu.model.Role;
import fa.edu.model.WorkPlace;
import fa.edu.repository.EmployeeRepository;
import fa.edu.repository.PlaceRepository;
import fa.edu.repository.RoleRepository;
import fa.edu.security.MyUserDetailService;
import fa.edu.security.MyUserDetails;
import fa.edu.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
public class AccountController {

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private PlaceRepository placeRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RoleRepository roleRepo;

	@GetMapping("/admin/profile")
	public String showProfile(Model model) {
		MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String idPrincipal = user.getUsername();
		Employee employee = employeeRepo.getEmplByUsername(idPrincipal);
		model.addAttribute("employee", employee);
		List<WorkPlace> places = (List<WorkPlace>) placeRepo.findAll();
		model.addAttribute("listPlaces", places);
		return "profile";
	}

	public static LocalDate convertToLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	@PostMapping("/admin/update")
	public String saveUpdate(@Valid @ModelAttribute("employee") Employee employee, BindingResult result,
			HttpServletRequest request, RedirectAttributes redirect, Model model) {
		MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String id = user.getId();
		employee.setEmployeeId(id);
		Set<Role> roles = user.getRoles();
		employee.setRoles(roles);
		Integer idOfRole = user.getIdRole();
		employee.setIdOfRole(idOfRole);
//		String username = user.getUsername();
//		employee.setUsername(username);

		List<WorkPlace> places = (List<WorkPlace>) placeRepo.findAll();
		model.addAttribute("listPlaces", places);
		if (employee.getBirthDate() != null) {
			Date dob = employee.getBirthDate();
			Date date = new Date();
			if (dob.after(date)) {
				result.addError(new FieldError("employee", "birthDate", "Birthday Not Found"));

			}
			if (date.getYear() - dob.getYear() > 120) {
				result.addError(new FieldError("employee", "birthDate", "Birthday greater 0 and little 120 "));
			}
		}
		if (employee.getPassword() != null) {
			if (employee.getPassword().length() < 6 && employee.getPassword().length() > 20) {
				result.addError(new FieldError("employee", "password", "Password greater 6 and little 120 "));
			}
		}
		if (result.hasErrors()) {
			return "profile";
		}

		String password = employee.getPassword();
		employee.setPassword(bCryptPasswordEncoder.encode(password));
		employee.setStatus(1);
		employeeRepo.save(employee);
		redirect.addFlashAttribute("message", "Your account details have been updated");
		return "redirect:/admin/profile";
	}
}
