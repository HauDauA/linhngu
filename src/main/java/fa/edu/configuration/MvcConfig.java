package fa.edu.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/images/**","/font/**","/css/**","/js/**","/forgot-password","/reset_password","/sendopt","/verifyotp","/admin/**",
                 "/admin/images/**","/admin/font/**","/admin/css/**","/admin/js/**",
                "/admin/employee/css/**","/admin/employee/js/**", "/admin/employee/images/**","/admin/employee/font/**",
                "/admin/employee/update/css/**","/admin/employee/update/js/**", "/admin/employee/update/images/**","/admin/employee/update/font/**",
                "/admin/customer/css/**","/admin/customer/js/**", "/admin/customer/images/**","/admin/customer/font/**",
                "/admin/customer/page/css/**","/admin/customer/page/js/**", "/admin/customer/page/images/**","/admin/customer/page/font/**",
                "/admin/customer/update/css/**","/admin/customer/update/js/**", "/admin/customer/update/images/**","/admin/customer/update/font/**",
                "/admin/vaccine/css/**","/admin/vaccine/js/**", "/admin/vaccine/images/**","/admin/vaccine/font/**",
                "/admin/vaccine/page/css/**","/admin/vaccine/page/js/**", "/admin/vaccine/page/images/**","/admin/vaccine/page/font/**",
                "/admin/vaccine/update/css/**","/admin/vaccine/update/js/**", "/admin/vaccine/update/images/**","/admin/vaccine/update/font/**",
                "/admin/schedule/css/**","/admin/schedule/js/**", "/admin/schedule/images/**","/admin/schedule/font/**",
                "/admin/schedule/page/css/**","/admin/schedule/page/js/**", "/admin/schedule/page/images/**","/admin/schedule/page/font/**",
                "/admin/schedule/update/css/**","/admin/schedule/update/js/**", "/admin/schedule/update/images/**","/admin/schedule/update/font/**",
                "/admin/result/css/**","/admin/result/js/**", "/admin/result/images/**","/admin/result/font/**",
                "/admin/result/update/css/**","/admin/result/update/js/**", "/admin/result/update/images/**","/admin/result/update/font/**",
                "/admin/result/page/css/**","/admin/result/page/js/**", "/admin/result/page/images/**","/admin/result/page/font/**",
                "/admin/employee/page/css/**","/admin/employee/page/js/**", "/admin/employee/page/images/**","/admin/employee/page/font/**"
                
        		)
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/images/",
                        "classpath:/static/css/",
                        "classpath:/static/font/",
                        "classpath:/static/js/");
        
      
    }
}
