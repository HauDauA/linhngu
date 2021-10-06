package fa.edu.service.implement;

import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import fa.edu.model.Customer;
import fa.edu.model.InjectionResult;
import fa.edu.service.ReportService;

public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private fa.edu.repository.ReportResultRepository reportResult;
	 
	@Override
	public Map<Month, Integer> numberInjectionByMonth(Integer year, String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> listYear() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<InjectionResult> filterPaginated(InjectionResult injectionResult, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InjectionResult> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Customer> findPaginatedCus(int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Customer> searchCustomerReport(int pageNo, int pageSize, Date fromDate, Date toDate, String fullName,
			String address) {
		// TODO Auto-generated method stub
		return null;
	}

}
