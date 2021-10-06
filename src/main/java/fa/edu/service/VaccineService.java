package fa.edu.service;

import fa.edu.model.Vaccine;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface VaccineService {
    Page<Vaccine> getPages(int pageNumber, String keyword);

    List<Vaccine> findAll();

    boolean save(Vaccine vaccine);

    boolean findByVaccineName(String name);

    Vaccine findByVaccineId(String id);

    void deleteById(String id);

}
