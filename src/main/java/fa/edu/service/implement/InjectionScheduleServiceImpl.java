package fa.edu.service.implement;

import fa.edu.model.InjectionSchedule;
import fa.edu.repository.InjectionScheduleRepository;
import fa.edu.service.InjectionScheduleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InjectionScheduleServiceImpl implements InjectionScheduleService {

    @Autowired
    InjectionScheduleRepository scheduleRepository;
    @Override
    public Page<InjectionSchedule> list(int pageNumber, String keyword) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        if (keyword != null) {
            return scheduleRepository.listSchedules(keyword, pageable);
        }
        return scheduleRepository.listSchedulesNoSearch(pageable);
    }

    @Override
    public boolean save(InjectionSchedule injectionSchedule) {
        if (injectionSchedule != null) {
            scheduleRepository.save(injectionSchedule);
            return true;
        }
        return false;
    }

    @Override
    public void deleteById(String id) {
        scheduleRepository.deleteSchedule(id);
    }
    
    public List<InjectionSchedule> schedules(){
    	return scheduleRepository.schedules();
    }

	@Override
	public void updateById(String id) {
		scheduleRepository.updateSchedule(id);
		
	}
}
