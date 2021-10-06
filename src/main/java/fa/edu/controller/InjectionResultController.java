package fa.edu.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fa.edu.model.Customer;
import fa.edu.model.Employee;
import fa.edu.model.InjectionResult;
import fa.edu.model.InjectionSchedule;
import fa.edu.model.Vaccine;
import fa.edu.model.WorkPlace;
import fa.edu.repository.InjectionResultRepository;
import fa.edu.repository.InjectionScheduleRepository;
import fa.edu.service.EmployeeService;
import fa.edu.service.InjectionResultService;
import fa.edu.service.implement.InjectionScheduleServiceImpl;
import fa.edu.utils.Constant;

@Controller
@RequestMapping("/admin/result")
public class InjectionResultController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private InjectionScheduleServiceImpl scheduleServiceImpl;
	
	@Autowired
	private InjectionResultService resultService;
	
	@Autowired
	private InjectionResultRepository resultRepo;;
	
	@Autowired
	private InjectionScheduleRepository scheduleRepo;;
	
	
	@GetMapping("/list")
	public String showList(Model model) {
		String keyword = null;
		return listByPage(model, 1, keyword);
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("keyword") String keyword) {
		Page<InjectionResult> page = resultService.list(currentPage, keyword);
		int totalItems = (int) page.getTotalElements();
		int totalPages = page.getTotalPages();
		long startCount = (currentPage - 1) * Constant.NUMBER_PER_PAGE + 1;
		long endCount = startCount + Constant.NUMBER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		List<InjectionResult> results = page.getContent();
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("results", results);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("keyword", keyword);

		return "list-result";
	}
	
	@GetMapping("/create")
	public String showFormCreate(Model model,RedirectAttributes redirect) {
		model.addAttribute("result", new InjectionResult());
		List<InjectionSchedule> schedules = scheduleServiceImpl.schedules();
		model.addAttribute("schedules", schedules);
		List<Employee> employees = employeeService.lisEmpls();
		model.addAttribute("employees", employees);
		if(schedules.size()==0) {
			redirect.addFlashAttribute("message","Đã tiêm hết số người đăng kí.");
			return "redirect:/admin/result/list";
		}
		return "create-result";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("result") InjectionResult injectionResult, BindingResult result,
			HttpServletRequest request, RedirectAttributes redirect, Model model) {
		String scheduId=injectionResult.getInjectionSchedule().getInjectionScheduleId();
		List<Employee> employees = employeeService.lisEmpls();
		List<InjectionSchedule> schedules = scheduleServiceImpl.schedules();
		model.addAttribute("schedules", schedules);
		model.addAttribute("employees", employees);
		if (injectionResult.getResultInjection() != null) {
			if(injectionResult.getResultInjection().equals("1")) {
				injectionResult.setResultInjection("Đã Tiêm");
			}
			if(injectionResult.getResultInjection().equals("2")) {
				injectionResult.setResultInjection("Chưa Tiêm");
			}
		}
		if (result.hasErrors()) {
			return "create-result";
		}
		injectionResult.setStatus(1);
		Date date = new Date();
		injectionResult.setInjectionDate(date);
		resultService.save(injectionResult);
		scheduleServiceImpl.updateById(scheduId);
		redirect.addFlashAttribute("message", "Success! your registration is complete");
		return "redirect:/admin/result/list";
	}
	@GetMapping("/update/{id}")
	public String showFormUpdate(@PathVariable(name = "id") String id, Model model) {
		InjectionResult result = resultRepo.findById(id).get();
		model.addAttribute("result", result);
		List<Employee> employees = employeeService.lisEmpls();
		model.addAttribute("employees", employees);
		return "update-result";
	}
	
	@PostMapping("/saveUpdate")
	public String saveUpdate(@Valid @ModelAttribute("result") InjectionResult injectionResult, BindingResult result,
			HttpServletRequest request, RedirectAttributes redirect, Model model) {
		String scheduId=injectionResult.getInjectionSchedule().getInjectionScheduleId();
		List<InjectionSchedule> schedules = scheduleServiceImpl.schedules();
		model.addAttribute("schedules", schedules);
		List<Employee> employees = employeeService.lisEmpls();
		model.addAttribute("employees", employees);
		if (injectionResult.getResultInjection() != null) {
			if(injectionResult.getResultInjection().equals("1")) {
				injectionResult.setResultInjection("Đã Tiêm");
			}
			if(injectionResult.getResultInjection().equals("2")) {
				injectionResult.setResultInjection("Chưa Tiêm");
			}
		}
		if (result.hasErrors()) {
			return "update-result";
		}
		injectionResult.setStatus(1);
		Date date = new Date();
		injectionResult.setInjectionDate(date);
		resultService.save(injectionResult);
		scheduleServiceImpl.updateById(scheduId);
		redirect.addFlashAttribute("message", "Success! your registration is complete");
		return "redirect:/admin/result/list";
	}
	
	@PostMapping(value = "/delete")
	public String deleteCuss(@RequestParam(name = "idChecked", required = false) List<String> ids, Model model,
			RedirectAttributes redirectAttributes) {
		if (ids == null) {
			redirectAttributes.addFlashAttribute("error", "No selected to delete");
		} else {
			for (String idStr : ids) {
				InjectionResult result=resultRepo.findById(idStr).get();
				String scheduId=result.getInjectionSchedule().getInjectionScheduleId();
				resultService.deleteById(idStr);
				scheduleRepo.updateSchedule1(scheduId);
			}
			redirectAttributes.addFlashAttribute("success", "Delete Success");
			
		}
		return "redirect:/admin/result/list";
	}
}
