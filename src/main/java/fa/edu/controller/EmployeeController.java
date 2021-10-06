package fa.edu.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fa.edu.model.Employee;
import fa.edu.model.Role;
import fa.edu.model.WorkPlace;
import fa.edu.repository.EmployeeRepository;
import fa.edu.repository.PlaceRepository;
import fa.edu.repository.RoleRepository;
import fa.edu.service.EmployeeService;
import fa.edu.security.MyUserDetails;
import fa.edu.utils.Constant;

@Controller
@RequestMapping("/admin/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService empService;

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private PlaceRepository placeRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@GetMapping("/list")
	public String showEmpoyee(Model model) {
		String keyword = null;
		return listByPage(model, 1, keyword);
	}



	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("keyword") String keyword) {
		Page<Employee> page = empService.listEmpls(currentPage, keyword);
		int totalItems = (int) page.getTotalElements();
		int totalPages = page.getTotalPages();
		long startCount = (currentPage - 1) * Constant.NUMBER_PER_PAGE + 1;
		long endCount = startCount + Constant.NUMBER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		List<Employee> listEmployees = page.getContent();
		List<WorkPlace> places = (List<WorkPlace>) placeRepo.findAll();
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listEmployees", listEmployees);
		model.addAttribute("listPlaces", places);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("keyword", keyword);
		return "list-employee";
	}

	@GetMapping("/create")
	public String showFormCreate(Model model) {
		model.addAttribute("employee", new Employee());
		List<WorkPlace> places = (List<WorkPlace>) placeRepo.findAll();
		model.addAttribute("listPlaces", places);
		return "create-employee";
	}

	@PostMapping("/save")
	public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, HttpServletRequest request,
							   RedirectAttributes redirect, Model model) {
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

		if (employee.getUsername() != null) {
			if (empService.findByUserName(employee.getUsername())) {
				result.addError(new FieldError("employee", "username", "Username already in use!"));
			}
		}
		if (employee.getEmail() != null) {
			if (empService.findByEmail(employee.getEmail())) {
				result.addError(new FieldError("employee", "email", "Email already in use!"));
			}
		}
		if (employee.getPhone() != null) {
			if (empService.findByPhone(employee.getPhone())) {
				result.addError(new FieldError("employee", "phone", "Phone already in use!"));
			}
		}
		if (employee.getPassword() != null && employee.getConfirmPassword() != null) {
			if (!employee.getPassword().equals(employee.getConfirmPassword())) {
				result.addError(new FieldError("employee", "confirmPassword", "Password must match"));
			}
		}
		employee.setIdOfRole(1);
		HashSet<Role> listRoles = new HashSet<>();
		listRoles.add(roleRepo.findByName("SEN"));
		employee.setRoles(listRoles);
		if (result.hasErrors()) {
			return "create-employee";
		}

		String password = employee.getPassword();
		employee.setPassword(bCryptPasswordEncoder.encode(password));
		employee.setStatus(1);
		empService.save(employee);
		redirect.addFlashAttribute("message", "Success! your registration is complete");
		return "redirect:/admin/employee/list";
	}

	@PostMapping("/saveUpdate")
	public String saveUpdate(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, HttpServletRequest request,
			RedirectAttributes redirect, Model model) {
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

		if (employee.getPassword() != null && employee.getConfirmPassword() != null) {
			if (!employee.getPassword().equals(employee.getConfirmPassword())) {
				result.addError(new FieldError("employee", "confirmPassword", "Password must match"));
			}
		}
		if (result.hasErrors()) {
			return "update-employee";
		}
		employee.setIdOfRole(1);
		redirect.addFlashAttribute("message", "Success!	update information is complete");
		String password = employee.getPassword();
		employee.setPassword(bCryptPasswordEncoder.encode(password));
		employee.setStatus(1);
		empService.save(employee);
		return "redirect:/admin/employee/list";
	}

	@GetMapping("/update/{id}")
	public String ShowFormUpdateEmpl(@PathVariable(name = "id") String id, Model model) {
		Employee employee = employeeRepo.findById(id).get();
		model.addAttribute("employee", employee);
		List<WorkPlace> places = (List<WorkPlace>) placeRepo.findAll();
		model.addAttribute("listPlaces", places);
		return "update-employee";
	}

	@PostMapping(value = "/delete")
	public String deleteCuss(@RequestParam(name = "idChecked", required = false) List<String> ids, Model model,
			RedirectAttributes redirectAttributes) {
		if (ids != null) {
			for (String idStr : ids) {
				empService.deleteById(idStr);
				redirectAttributes.addFlashAttribute("success", "Delete Success");
			}
		} else {
			redirectAttributes.addFlashAttribute("error", "No selected to delete");
		}
		return "redirect:/admin/employee/list";

	}
}
