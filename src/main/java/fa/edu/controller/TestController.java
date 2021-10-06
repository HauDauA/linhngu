package fa.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fa.edu.service.VerificationResult;
@Controller
public class TestController {

	@Autowired
	fa.edu.service.PhoneverificationService phonesmsservice;
	    
	@RequestMapping("/")
	public String homepage(ModelAndView model)
	{
		return "khaibao";
	}
	
	@RequestMapping("/success")
	public String homesuccess(ModelAndView model)
	{
		return "success";
	}
	
	@RequestMapping("/fail")
	public String homefail(ModelAndView model)
	{
		return "fail";
	}
	
	@PostMapping("/sendotp")
	public ResponseEntity<String> sendotp(@RequestParam("phone") String phone)
	{
	    VerificationResult result=phonesmsservice.startVerification(phone);
	    if(result.isValid())
	    {
	    	return new ResponseEntity<>("Otp Sent..",HttpStatus.OK);
	    }
		return new ResponseEntity<>("Otp failed to sent..",HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/verifyotp")
	public String sendotp(@RequestParam("phone") String phone, @RequestParam("otp") String otp)
	{
	    VerificationResult result=phonesmsservice.checkverification(phone,otp);
	    if(result.isValid())
	    {
	    	return "redirect:/success";
	    }
	    return "redirect:/fail";
	}

}
