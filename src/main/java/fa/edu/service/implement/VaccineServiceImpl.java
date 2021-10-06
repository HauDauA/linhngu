package fa.edu.service.implement;

import fa.edu.model.Vaccine;
import fa.edu.repository.VaccineRepository;
import fa.edu.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VaccineServiceImpl implements VaccineService {

    @Autowired
    private VaccineRepository vaccineRepo;

    @Override
    public Page<Vaccine> getPages(int pageNumber, String keyword) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        if (keyword != null) {
            return vaccineRepo.listVaccines(keyword, pageable);
        }
        return vaccineRepo.listVaccineNoSearch(pageable);
    }

    @Override
    public List<Vaccine> findAll() {
        return vaccineRepo.findVaccine();
    }

    @Override
    public boolean save(Vaccine vaccine) {
        if (vaccine != null){
            vaccineRepo.save(vaccine);
            return true;
        }
        return false;
    }

    @Override
    public boolean findByVaccineName(String name) {
        return vaccineRepo.findByName(name).isPresent();
    }

    @Override
    public Vaccine findByVaccineId(String id) {
        return vaccineRepo.findByVaccineId(id).get();
    }

    @Override
    public void deleteById(String id) {
        vaccineRepo.deleteVaccine(id);
    }
}
