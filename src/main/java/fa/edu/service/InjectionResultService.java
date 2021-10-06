package fa.edu.service;

import java.util.List;

import org.springframework.data.domain.Page;

import fa.edu.model.InjectionResult;
import fa.edu.model.InjectionSchedule;

public interface InjectionResultService {
	Page<InjectionResult> list(int pageNumber, String keyword);
	
	List<InjectionResult> top5();

    boolean save(InjectionResult injectionResult);

    public void deleteById(String id) ;
    
    public List<InjectionResult> results();
}
