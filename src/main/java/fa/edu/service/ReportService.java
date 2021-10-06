package fa.edu.service;

import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import fa.edu.model.Customer;
import fa.edu.model.InjectionResult;
import fa.edu.model.Vaccine;



public interface ReportService {
	
	Map<Month, Integer> numberInjectionByMonth(Integer year, String typeName);

	public List<Integer> listYear();

	Page<InjectionResult> filterPaginated(InjectionResult injectionResult, int pageNo, int pageSize);

	List<InjectionResult> findAll();

	Page<Customer> findPaginatedCus(int pageNo, int pageSize);

	Page<Customer> searchCustomerReport(int pageNo, int pageSize, Date fromDate, Date toDate, String fullName,
			String address);

}
