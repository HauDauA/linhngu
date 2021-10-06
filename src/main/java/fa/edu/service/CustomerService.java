package fa.edu.service;

import fa.edu.model.Customer;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> findAll();


    Page<Customer> listCuss(int pageNumber, String keyword);
    
    List<Customer> top5Cuss();

    // save
    boolean save(Customer customer);

    Customer findById(String id);

    void deleteById(String id);
    
    Customer findbyPhone(String phone);
    
    Customer findbyEmail(String email);
    
    Customer findbyIdentity(String identity);
}
