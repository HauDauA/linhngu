package fa.edu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fa.edu.model.Employee;
import fa.edu.service.EmployeeService;
import fa.edu.service.implement.EmployeeServiceImpl;

@Controller
public class ResetPasswordController {
	
	@Autowired
	private EmployeeServiceImpl empServiceImpl;
	
	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
	    Employee employee = empServiceImpl.getByResetPasswordToken(token);
	    model.addAttribute("token", token);
	     
	    if (employee == null) {
	        model.addAttribute("message", "Invalid Token");
	        return "message";
	    }
	     
	    return "reset_password";
	}
	
	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model,RedirectAttributes redirect) {
	    String token = request.getParameter("token");
	    String password = request.getParameter("password");
	     
	    Employee employee = empServiceImpl.getByResetPasswordToken(token);
	     
	    if (employee == null) {
	        model.addAttribute("message", "Invalid Token");
	        return "message";
	    } else {           
	        empServiceImpl.updatePassword(employee, password);
	        redirect.addFlashAttribute("message", "You have successfully changed your password.");
	    }
	     
	    return "login";
	}

}
