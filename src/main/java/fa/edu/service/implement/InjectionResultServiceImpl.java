package fa.edu.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fa.edu.model.InjectionResult;
import fa.edu.repository.InjectionResultRepository;
import fa.edu.service.InjectionResultService;
@Service
public class InjectionResultServiceImpl implements InjectionResultService {

	@Autowired
	private InjectionResultRepository resultRepo;
	
	@Override
	public Page<InjectionResult> list(int pageNumber, String keyword) {
		 Pageable pageable = PageRequest.of(pageNumber - 1, 5);
	        if (keyword != null) {
	            return resultRepo.listResult(keyword, pageable);
	        }
	        return resultRepo.listResultNoSearch(pageable);
	}

	@Override
	public boolean save(InjectionResult injectionResult) {
		if (injectionResult != null) {
			resultRepo.save(injectionResult);
            return true;
        }
        return false;
	}

	@Override
	public void deleteById(String id) {
		resultRepo.deleteResult(id);
		
	}
	
	@Override
	public List<InjectionResult> results(){
		return resultRepo.results();
	}

	@Override
	public List<InjectionResult> top5() {
		Pageable pageable = PageRequest.of(1, 5);
		return resultRepo.top5(pageable);
	}

}
