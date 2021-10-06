package fa.edu.controller;

import fa.edu.model.Customer;
import fa.edu.model.InjectionSchedule;
import fa.edu.model.Vaccine;
import fa.edu.model.WorkPlace;
import fa.edu.repository.InjectionScheduleRepository;
import fa.edu.repository.PlaceRepository;
import fa.edu.repository.VaccineRepository;
import fa.edu.service.CustomerService;
import fa.edu.service.InjectionScheduleService;
import fa.edu.service.VaccineService;
import fa.edu.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/schedule")
public class InjectionScheduleController {
	@Autowired
	private InjectionScheduleService scheduleService;

	@Autowired
	private VaccineService vaccineService;

	@Autowired
	private VaccineRepository vaccineRepo;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private InjectionScheduleService schedule;

	@Autowired
	private InjectionScheduleRepository scheduleRepo;

	@GetMapping("/list")
	public String showList(Model model) {
		String keyword = null;
		return listByPage(model, 1, keyword);
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("keyword") String keyword) {
		Page<InjectionSchedule> page = scheduleService.list(currentPage, keyword);
		int totalItems = (int) page.getTotalElements();
		int totalPages = page.getTotalPages();
		long startCount = (currentPage - 1) * Constant.NUMBER_PER_PAGE + 1;
		long endCount = startCount + Constant.NUMBER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		List<InjectionSchedule> schedules = page.getContent();
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("schedules", schedules);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("keyword", keyword);

		return "list-schedule";
	}

	@GetMapping("/create")
	public String showFormCreate(Model model) {
		model.addAttribute("schedule", new InjectionSchedule());
		List<Vaccine> vaccines = vaccineService.findAll();
		List<Customer> customers = customerService.findAll();
		List<WorkPlace> places = (List<WorkPlace>) placeRepository.findAll();
		model.addAttribute("places", places);
		model.addAttribute("customers", customers);
		model.addAttribute("vaccines", vaccines);
		return "create-schedule";
	}


	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("schedule") InjectionSchedule injectionSchedule, BindingResult result,
			HttpServletRequest request, RedirectAttributes redirect, Model model) {
		List<Vaccine> vaccines = vaccineService.findAll();
		List<Customer> customers = customerService.findAll();
		List<WorkPlace> places = (List<WorkPlace>) placeRepository.findAll();
		model.addAttribute("places", places);
		model.addAttribute("customers", customers);
		model.addAttribute("vaccines", vaccines);
		List<String> status = new ArrayList<>();
		if (injectionSchedule.getCough() != null) {
			if (injectionSchedule.getCough().equals("yes")) {
				status.add("Ho");
			}
		}
		if (injectionSchedule.getFever() != null) {
			if (injectionSchedule.getFever().equals("yes")) {
				status.add("Sốt");
			}
		}
		if (injectionSchedule.getSoreThroat() != null) {
			if (injectionSchedule.getSoreThroat().equals("yes")) {
				status.add("Đau bụng");
			}
		}
		if (injectionSchedule.getDiarrhea() != null) {
			if (injectionSchedule.getDiarrhea().equals("yes")) {
				status.add("No nam");
			}
		}
		if (injectionSchedule.getNausea() != null) {
			if (injectionSchedule.getNausea().equals("yes")) {
				status.add("No name");
			}
		}
		if (injectionSchedule.getShortnessOfBreath() != null) {
			if (injectionSchedule.getShortnessOfBreath().equals("yes")) {
				status.add("No name");
			}
		}
		if (injectionSchedule.getSkinBleeding() != null) {
			if (injectionSchedule.getSkinBleeding().equals("yes")) {
				status.add("No name");
			}
		}
		if (injectionSchedule.getSkinRash() != null) {
			if (injectionSchedule.getSkinRash().equals("yes")) {
				status.add("No name");
			}
		}
		List<ObjectError> errors=result.getAllErrors();
		for (ObjectError objectError : errors) {
			System.out.println(objectError);
		}
		if (result.hasErrors()) {
			return "create-schedule";
		}
		String statusCus = String.join(",", status);
		injectionSchedule.setStatusCustomer(statusCus);
		injectionSchedule.setStatus(1);
		Date date = new Date();
		injectionSchedule.setDate(date);
		injectionSchedule.setResult(1);
		schedule.save(injectionSchedule);
		redirect.addFlashAttribute("message", "Success! your registration is complete");
		return "redirect:/admin/schedule/list";
	}

	@GetMapping("/update/{id}")
	public String showFormUpdate(@PathVariable(name = "id") String id, Model model) {
		InjectionSchedule schedule = scheduleRepo.findById(id).get();
		model.addAttribute("schedule", schedule);
		Customer customer = schedule.getCustomer();
		model.addAttribute("customer", customer);
		List<Vaccine> vaccines = vaccineService.findAll();
		// List<Customer> customers = customerService.findAll();
		List<WorkPlace> places = (List<WorkPlace>) placeRepository.findAll();
		model.addAttribute("places", places);
		// model.addAttribute("customers", customers);
		model.addAttribute("vaccines", vaccines);
		return "update-schedule";
	}

	@SuppressWarnings({ "unlikely-arg-type", "unused" })
	@PostMapping("/saveUpdate")
	public String saveUpdate(@Valid @ModelAttribute("schedule") InjectionSchedule injectionSchedule,
			BindingResult result, HttpServletRequest request, RedirectAttributes redirect, Model model) {
		injectionSchedule.setInjectionScheduleId(injectionSchedule.getInjectionScheduleId());
		System.out.println(injectionSchedule.getInjectionScheduleId());
		Customer customer = injectionSchedule.getCustomer();
		model.addAttribute("customer", customer);
		List<Vaccine> vaccines = vaccineService.findAll();
		// List<Customer> customers = customerService.findAll();
		List<WorkPlace> places = (List<WorkPlace>) placeRepository.findAll();
		model.addAttribute("places", places);
		// model.addAttribute("customers", customers);
		model.addAttribute("vaccines", vaccines);
		List<String> status = new ArrayList<>();
		injectionSchedule.setCustomer(injectionSchedule.getCustomer());
		if (injectionSchedule.getCough() != null) {
			if (injectionSchedule.getCough().equals("yes")) {
				status.add("Ho");
			}
		}
		if (injectionSchedule.getFever() != null) {
			if (injectionSchedule.getFever().equals("yes")) {
				status.add("Sốt");
			}
		}
		if (injectionSchedule.getSoreThroat() != null) {
			if (injectionSchedule.getSoreThroat().equals("yes")) {
				status.add("Đau bụng");
			}
		}
		if (injectionSchedule.getDiarrhea() != null) {
			if (injectionSchedule.getDiarrhea().equals("yes")) {
				status.add("No nam");
			}
		}
		if (injectionSchedule.getNausea() != null) {
			if (injectionSchedule.getNausea().equals("yes")) {
				status.add("No name");
			}
		}
		if (injectionSchedule.getShortnessOfBreath() != null) {
			if (injectionSchedule.getShortnessOfBreath().equals("yes")) {
				status.add("No name");
			}
		}
		if (injectionSchedule.getSkinBleeding() != null) {
			if (injectionSchedule.getSkinBleeding().equals("yes")) {
				status.add("No name");
			}
		}
		if (injectionSchedule.getSkinRash() != null) {
			if (injectionSchedule.getSkinRash().equals("yes")) {
				status.add("No name");
			}
		}
		if (status != null) {
			String statusCus = String.join(",", status);
			injectionSchedule.setStatusCustomer(statusCus);
		} else {
			status.add("No Status");
		}
		if (result.hasErrors()) {
			return "update-schedule";
		}

		injectionSchedule.setStatus(1);
		Date date = new Date();
		injectionSchedule.setDate(date);
		injectionSchedule.setResult(1);
		schedule.save(injectionSchedule);
		redirect.addFlashAttribute("message", "Success! your registration is complete");
		return "redirect:/admin/schedule/list";
	}

	@PostMapping(value = "/delete")
	public String deleteCuss(@RequestParam(name = "idChecked", required = false) List<String> ids, Model model,
			RedirectAttributes redirectAttributes) {
		if (ids == null) {
			redirectAttributes.addFlashAttribute("error", "No selected to delete");
		} else {
			for (String idStr : ids) {
				scheduleService.deleteById(idStr);
			}
			redirectAttributes.addFlashAttribute("success", "Delete Success");
		}
		return "redirect:/admin/schedule/list";
	}

}
