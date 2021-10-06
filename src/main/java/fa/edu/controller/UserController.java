package fa.edu.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fa.edu.model.User;
import fa.edu.service.UserService;

@Controller
@RequestMapping("/")  

public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String ShowAllEmployees(Model model) {
		model.addAttribute("users", userService.users());
		return "list-user";
	}

	@GetMapping("delete/{id}")
	public String deleteBrand(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		userService.deleteUser(id);
		return "redirect:/";
	}

	@GetMapping("create")
	public String ShowFormCreateEmpl(Model model) {
		model.addAttribute("user", new User());
		return "create-user";
	}

	@PostMapping(value = "save")
	public String saveEmlp(@ModelAttribute("user") User user, Model model) {
		userService.saveUser(user);
		return "redirect:/";
	}

	@GetMapping("update/{id}")
	public ModelAndView ShowFormUpdateEmpl(@PathVariable(name = "id") Integer id, Model model) {
		ModelAndView mav = new ModelAndView("update-user");
		User user = userService.findById(id);
		mav.addObject("user", user);
		return mav;
	}
}
