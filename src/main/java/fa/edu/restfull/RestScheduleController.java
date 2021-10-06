package fa.edu.restfull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fa.edu.model.InjectionSchedule;
import fa.edu.repository.InjectionScheduleRepository;

@RestController
public class RestScheduleController {
	@Autowired
	private InjectionScheduleRepository scheduleRepository;
	
	@PostMapping("/schedule/save")
	private InjectionSchedule add(@RequestBody InjectionSchedule schedule) {
		return scheduleRepository.save(schedule);
	}

}
