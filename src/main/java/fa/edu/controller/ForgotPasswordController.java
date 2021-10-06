package fa.edu.controller;

import fa.edu.model.Employee;

import fa.edu.repository.EmployeeRepository;
import fa.edu.security.CustomerNotFoundException;
import fa.edu.security.Utility;
import fa.edu.service.EmployeeService;
import fa.edu.service.implement.EmployeeServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {

    @Autowired
    private EmployeeRepository employRepo;

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/forgot-password")
    public  String resetPassword(Model model){
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public  String processForgetPassword(HttpServletRequest request, Model model){
        String email = request.getParameter("email");
        System.out.println(email);
        String token = RandomString.make(30);

        try {
            employeeServiceImpl.updateResetPassword(token, email);
            String resetPasswordLink = Utility.getSizeURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (CustomerNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "forgot-password";
    }

    private void sendEmail(String email, String resertPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message) ;
        helper.setFrom("haudt3.fs@gmail.com","AntiCovid");
        helper.setTo(email);
        String subject ="Here is the link to reset your password";
        String content="<p>Hello,</p>"
                        +"<p>You have  requested to reset your password.</p>"
                        +"<p>Click the link below to change password:</p>"
                        +"<p><b><a href=\"" +resertPasswordLink+"\">Change my password</a><b></p>"
                        +"<p>Ignor this email if you do remember your password,or you have not made the request</p>";

        helper.setSubject(subject);
        helper.setText(content,true);
        mailSender.send(message);


    }
    

}
