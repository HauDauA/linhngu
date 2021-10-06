package fa.edu.service;

import org.springframework.data.domain.Page;

import fa.edu.model.InjectionSchedule;

public interface InjectionScheduleService {
    Page<InjectionSchedule> list(int pageNumber, String keyword);

    boolean save(InjectionSchedule injectionSchedule);

    public void deleteById(String id) ;
    
    public void updateById(String id) ;
}
