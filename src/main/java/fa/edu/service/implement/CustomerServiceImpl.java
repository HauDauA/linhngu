package fa.edu.service.implement;

import fa.edu.model.Customer;
import fa.edu.repository.CustomerRepository;
import fa.edu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findCus();
    }


    @Override
    public Page<Customer> listCuss(int pageNumber, String keyword) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        if (keyword != null) {
            return customerRepository.listCuss(keyword, pageable);
        }
        return customerRepository.listCussNoSearch(pageable);
    }

    @Override
    public boolean save(Customer customer) {
        if (customer != null) {
            customerRepository.save(customer);
            return true;
        }

        return false;
    }

    @Override
    public Customer findById(String id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public void deleteById(String id) {
        customerRepository.deleteCustomer(id);
    }

    public boolean userPhone(String phone) {
        return customerRepository.findByPhone(phone).isPresent();
    }

    public boolean userEmail(String email) {
        return customerRepository.findByEmail(email).isPresent();
        
    }
    public boolean userCard(String card) {
        return customerRepository.findByIdentity(card).isPresent();
    }


	@Override
	public Customer findbyPhone(String phone) {
		return customerRepository.findByPhone(phone).get();
	}


	@Override
	public Customer findbyEmail(String email) {
		return customerRepository.findEmail(email).get();
	}


	@Override
	public Customer findbyIdentity(String identity) {
		return customerRepository.findIdentity(identity).get();
	}


	@Override
	public List<Customer> top5Cuss() {
		Pageable pageable = PageRequest.of(1, 5);
		return customerRepository.Top5Cus(pageable);
	}
}
