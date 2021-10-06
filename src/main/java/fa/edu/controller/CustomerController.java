package fa.edu.controller;

import fa.edu.model.Customer;
import fa.edu.repository.CustomerRepository;
import fa.edu.service.CustomerService;
import fa.edu.service.implement.CustomerServiceImpl;
import fa.edu.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerServiceImpl customerServiceImpl;

	@Autowired
	private CustomerRepository customerRepo;

	@GetMapping("/list")
	public String showList(Model model) {
		String keyword = null;
		return listByPage(model, 1, keyword);
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("keyword") String keyword) {
		Page<Customer> page = customerService.listCuss(currentPage, keyword);
		int totalItems = (int) page.getTotalElements();
		int totalPages = page.getTotalPages();
		long startCount = (currentPage - 1) * Constant.NUMBER_PER_PAGE + 1;
		long endCount = startCount + Constant.NUMBER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		List<Customer> listCus = page.getContent();
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listCus", listCus);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("keyword", keyword);
		return "list-customer";
	}

	@GetMapping("/create")
	public String showFormCreate(Model model) {
		model.addAttribute("customer", new Customer());
		return "create-customer";
	}

	@PostMapping("/save")
	public String save(@Valid Customer customer, BindingResult result, HttpServletRequest request,
			RedirectAttributes redirect, Model model) {
		
		if (customer.getDateOfBirth() != null) {
			java.util.Date dob = customer.getDateOfBirth();
			Date date = new Date();
			if (dob.after(date)) {
				result.addError(new FieldError("customer", "dateOfBirth", "Birthday Not Found"));

			}
			if (date.getYear() - dob.getYear() > 120) {
				result.addError(new FieldError("customer", "dateOfBirth", "Birthday greater 0 and little 120 "));
			}
		}
		if (customer.getEmail() != null) {
			if (customerServiceImpl.userEmail(customer.getEmail())) {
				result.addError(new FieldError("customer", "email", "Email already in use!"));
			}
		}
		if (customer.getPhone() != null) {
			if (customerServiceImpl.userPhone(customer.getPhone())) {
				result.addError(new FieldError("customer", "phone", "Phone already in use!"));
			}
		}
		if (customer.getIdentityCard() != null) {
			if (customerServiceImpl.userCard(customer.getIdentityCard())) {
				result.addError(new FieldError("customer", "identityCard", "Identity Card already in use!"));
			}
		}
		if (result.hasErrors()) {
			return "create-customer";
		}
		redirect.addFlashAttribute("message", "Success! your registration is complete");
		customer.setStatus(1);
		customerServiceImpl.save(customer);
		return "redirect:/admin/customer/list";
	}

	@PostMapping("/saveUpdate")
	public String saveUpdate(@Valid Customer customer, BindingResult result, HttpServletRequest request,
			RedirectAttributes redirect, Model model) {
		if (customer.getDateOfBirth() != null) {
			Date dob = customer.getDateOfBirth();
			Date date = new Date();
			if (dob.after(date)) {
				result.addError(new FieldError("customer", "dateOfBirth", "Birthday Not Found"));

			}
			if (date.getYear() - dob.getYear() > 120) {
				result.addError(new FieldError("customer", "dateOfBirth", "Birthday greater 0 and little 120 "));
			}
		}
		if (result.hasErrors()) {
			return "update-customer";
		}
		redirect.addFlashAttribute("message", "Success! update account  is complete");
		customer.setStatus(1);
		customerServiceImpl.save(customer);
		return "redirect:/admin/customer/list";
	}

	@GetMapping("/update/{id}")
	public String ShowFormUpdateEmpl(@PathVariable(name = "id") String id, Model model) {
		Customer customer = customerService.findById(id);
		model.addAttribute("customer", customer);
		return "update-customer";
	}

	@PostMapping(value = "/delete")
	public String deleteCuss(@RequestParam(name = "idChecked", required = false) List<String> ids, Model model,
			RedirectAttributes redirectAttributes) {
		if (ids == null) {
			redirectAttributes.addFlashAttribute("error", "No selected to delete");
		} else {
			for (String idStr : ids) {
				customerService.deleteById(idStr);
			}
			redirectAttributes.addFlashAttribute("success", "Delete Success");
		}
		return "redirect:/admin/customer/list";
	}

}
