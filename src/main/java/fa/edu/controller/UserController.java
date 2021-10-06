package fa.edu.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fa.edu.model.Customer;
import fa.edu.model.InjectionSchedule;
import fa.edu.model.Vaccine;
import fa.edu.model.WorkPlace;
import fa.edu.repository.CustomerRepository;
import fa.edu.repository.InjectionScheduleRepository;
import fa.edu.repository.PlaceRepository;
import fa.edu.service.InjectionScheduleService;
import fa.edu.service.PhoneverificationService;
import fa.edu.service.VaccineService;
import fa.edu.service.implement.CustomerServiceImpl;
import fa.edu.utils.ValidatePhoneNumber;

@Controller
public class UserController {

	@Autowired
	private VaccineService vaccineService;

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private InjectionScheduleService schedule;

	@Autowired
	private InjectionScheduleRepository scheduleRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private PhoneverificationService phonesmsservice;

	@GetMapping("/")
	public String viewUser() {
		return "index";
	}

//	@PostMapping("/sendotp")
//	public ResponseEntity<String> sendotp(@RequestParam("phone") String phone) {
//		VerificationResult result = phonesmsservice.startVerification(phone);
//		if (result.isValid()) {
//			return new ResponseEntity<>("Otp Sent..", HttpStatus.OK);
//		}
//		return new ResponseEntity<>("Otp failed to sent..", HttpStatus.BAD_REQUEST);
//	}
//
//	@PostMapping("/verifyotp")
//	public String sendotp(@RequestParam("phone") String phone, @RequestParam("otp") String otp) {
//		VerificationResult result = phonesmsservice.checkverification(phone, otp);
//		if (result.isValid()) {
//			return "redirect:/success";
//		}
//		return "redirect:/fail";
//	}

//	@PostMapping("/verifyotp")
//	public String sendotp(@RequestParam("phone") String phone, @RequestParam("otp") String otp, Model model,
//			RedirectAttributes redirect) {
//		System.out.println(phone);
//		VerificationResult result = phonesmsservice.checkverification(phone, otp);
//		if (result.isValid()) {
//			Customer customer = customerRepo.findPhone(phone);
//			if (customer == null) {
//				redirect.addFlashAttribute("message",
//						"Welcome to AntiCovid.Vui lòng điền đầy đủ thông tin trước khi đăng kí tiêm");
//				redirect.addFlashAttribute("phone", phone);
//				return "redirect:/create";
//
//			} else {
//				redirect.addFlashAttribute("message",
//						"Số điện thoại đã được đăng kí. Vui lòng update lại thông tin trước khi đăng kí tiêm.");
//				redirect.addFlashAttribute("customer", customer);
//				return "redirect:/update";
//
//			}
//		}
//		redirect.addFlashAttribute("error", "OTP error.Please enter repeat...!!");
//		return "redirect:/";
//	}

	@Autowired
	private CustomerServiceImpl customerServiceImpl;

	@PostMapping("/")
	public String checkPhoneNumber(HttpServletRequest request, Model model, RedirectAttributes redirect) {
		String phone = request.getParameter("phone");
		Customer customer = customerRepo.findPhone(phone);
		if (customer == null) {
			if (ValidatePhoneNumber.validatePhoneNumber(phone)) {
				redirect.addFlashAttribute("message",
						"Welcome to AntiCovid.Vui lòng điền đầy đủ thông tin trước khi đăng kí tiêm");
				redirect.addFlashAttribute("phone", phone);
				return "redirect:/create";
			} else {
				model.addAttribute("error", "Định dạng number phone error");
				return "index";
			}
		} else {
			redirect.addFlashAttribute("message",
					"Số điện thoại đã được đăng kí. Vui lòng update lại thông tin trước khi đăng kí tiêm.");
			redirect.addFlashAttribute("customer", customer);
			return "redirect:/update";

		}
	}

	@GetMapping("/create")
	public String viewCreateUser(Model model) {
		model.addAttribute("customer", new Customer());
		return "create";
	}

	@GetMapping("/update")
	public String viewUpdateUser(Model model) {

		return "update";
	}

	@GetMapping("/schedule")
	public String viewSignInjection(Model model, Customer customer) {
		model.addAttribute("schedule", new InjectionSchedule());
		List<Vaccine> vaccines = vaccineService.findAll();
		List<WorkPlace> places = (List<WorkPlace>) placeRepository.findAll();
		model.addAttribute("places", places);
		model.addAttribute("vaccines", vaccines);
		model.addAttribute("customer", customer);
		return "schedule";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("customer") Customer customer, BindingResult result,
			HttpServletRequest request, RedirectAttributes redirect, Model model) {
		String phone = customer.getPhone();
		model.addAttribute("phone", phone);
		if (customer.getDateOfBirth() != null) {
			java.util.Date dob = customer.getDateOfBirth();
			Date date = new Date();
			if (dob.after(date)) {
				result.addError(new FieldError("customer", "dateOfBirth", "Ngày Sinh không hợp lệ"));

			}
			if (date.getYear() - dob.getYear() > 120) {
				result.addError(new FieldError("customer", "dateOfBirth", "Ngày Sinh không hợp lệ"));
			}
		}
		if (customer.getEmail() != null) {
			if (customerServiceImpl.userEmail(customer.getEmail())) {
				result.addError(new FieldError("customer", "email", "Email đã được đăng kí!"));
			}
		}
		if (customer.getPhone() != null) {
			if (customerServiceImpl.userPhone(customer.getPhone())) {
				result.addError(new FieldError("customer", "phone", "Số điện thoại đã được đăng kí"));
			}
		}
		if (customer.getIdentityCard() != null) {
			if (customerServiceImpl.userCard(customer.getIdentityCard()))
				result.addError(new FieldError("customer", "identityCard", "Số CCCD đã được đăng kí!"));
			if (!customer.getIdentityCard().matches("\\d{12}"))
				result.addError(new FieldError("customer", "identityCard", "Sai định dạng CCCD"));
		}

		if (result.hasErrors()) {
			return "create";
		}

		customer.setStatus(1);
		customerServiceImpl.save(customer);
		redirect.addFlashAttribute("message", "Chúc mừng! Đăng kí thành công");
		redirect.addFlashAttribute("customer", customer);
		redirect.addFlashAttribute("cusId", customer.getCustomerId());
		return "redirect:/schedule";
	}

	@PostMapping("/saveUpdate")
	public String saveUpdate(@Valid @ModelAttribute("customer") Customer customer, BindingResult result,
			HttpServletRequest request, RedirectAttributes redirect, Model model) {
		if (customer.getDateOfBirth() != null) {
			Date dob = customer.getDateOfBirth();
			Date date = new Date();
			if (dob.after(date)) {
				result.addError(new FieldError("customer", "dateOfBirth", "Ngày Sinh không hợp lệ"));

			}
			if (date.getYear() - dob.getYear() > 120) {
				result.addError(new FieldError("customer", "dateOfBirth", "Ngày Sinh không hợp lệ"));
			}
		}
		if (result.hasErrors()) {
			return "update";
		}
		customer.setStatus(1);
		customerServiceImpl.save(customer);
		redirect.addFlashAttribute("message", "Chúc mừng! Cập nhật thông tin thành công");
		redirect.addFlashAttribute("customer", customer);
		redirect.addFlashAttribute("cusId", customer.getCustomerId());
		return "redirect:/schedule";
	}

	@PostMapping("/saveSchedule")
	public String save(@Valid @ModelAttribute("schedule") InjectionSchedule injectionSchedule, BindingResult result,
			HttpServletRequest request, RedirectAttributes redirect, Model model) {


//		if (injectionSchedule.getVaccine() != null) {
//			List<InjectionSchedule> schedules = (List<InjectionSchedule>) scheduleRepo.findAll();
//			model.addAttribute("schedules", schedules);
//			for (InjectionSchedule injectionSchedule2 : schedules) {
//				if (injectionSchedule2.getCustomer().getCustomerId().equals(injectionSchedule.getCustomer().getCustomerId())
//						&& injectionSchedule2.getVaccine().getVaccineId().equals(injectionSchedule.getVaccine().getVaccineId())
//						&& injectionSchedule.getNumberOfInjection() <= injectionSchedule2.getNumberOfInjection()) {
//					result.addError(new FieldError("schedule", "vaccine", "Vaccine Error"));
//				}
//			}
//		}
		List<Vaccine> vaccines = vaccineService.findAll();
		List<WorkPlace> places = (List<WorkPlace>) placeRepository.findAll();
		model.addAttribute("places", places);
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
				status.add("Đau họng");
			}
		}
		if (injectionSchedule.getDiarrhea() != null) {
			if (injectionSchedule.getDiarrhea().equals("yes")) {
				status.add("Tiêu chảy");
			}
		}
		if (injectionSchedule.getNausea() != null) {
			if (injectionSchedule.getNausea().equals("yes")) {
				status.add("Buồn nôn");
			}
		}
		if (injectionSchedule.getShortnessOfBreath() != null) {
			if (injectionSchedule.getShortnessOfBreath().equals("yes")) {
				status.add("Khó thở");
			}
		}
		if (injectionSchedule.getSkinBleeding() != null) {
			if (injectionSchedule.getSkinBleeding().equals("yes")) {
				status.add("Xuất huyết ngoài da");
			}
		}
		if (injectionSchedule.getSkinRash() != null) {
			if (injectionSchedule.getSkinRash().equals("yes")) {
				status.add("Nổi ban ngoài gia");
			}
		}
		String statusCus = String.join(",", status);
		injectionSchedule.setStatusCustomer(statusCus);

		List<ObjectError> errors = result.getAllErrors();
		for (ObjectError objectError : errors) {
			System.out.println(objectError);
		}
		if (result.hasErrors()) {
			return "schedule";
		}

		injectionSchedule.setStatus(1);
		Date date = new Date();
		injectionSchedule.setDate(date);
		injectionSchedule.setResult(1);
		schedule.save(injectionSchedule);
		redirect.addFlashAttribute("message", "Đăng kí tiêm thành công");
		return "redirect:/";
	}
}
