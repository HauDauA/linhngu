package fa.edu.repository;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import fa.edu.model.Customer;
import fa.edu.model.Vaccine;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Repository
public interface VaccineRepository extends PagingAndSortingRepository<Vaccine, String> {
    @Query("Select v From Vaccine v "
            + "	Where CONCAT(v.vaccineName,v.origin,v.contraindication)"
            + "	LIKE %?1% AND v.status =1 " + " order by v.vaccineId desc")
    public Page<Vaccine> listVaccines(String keyword, Pageable pageable);

    @Query("Select v From Vaccine v "
            + "	Where  v.status =1 " + " order by v.vaccineId desc")
    public Page<Vaccine> listVaccineNoSearch( Pageable pageable);

    @Query("Select v From Vaccine v "
                  + "	Where  v.status =1 " + " order by v.vaccineId desc")
    public List<Vaccine> findVaccine();

    @Query("Select v From Vaccine v WHERE v.vaccineName= ?1 ")
    public Optional<Vaccine> findByName(String vaccineName);

    @Query("Select v From Vaccine v WHERE v.vaccineId= ?1")
    public Optional<Vaccine> findByVaccineId(String id);

    @Query("Select v From Vaccine v "
            + "	Where  v.status =1 " + " order by v.vaccineId desc")
    public List<Vaccine> Top5( Pageable pageable);
    
    @Transactional
    @Modifying
    @Query("Update Vaccine v Set v.status = 0 Where v.vaccineId = :id")
    void deleteVaccine(@Param("id") String id);
    
    @Query("Select Count(*) From Vaccine v Where v.status=1")
    public Integer countVac();
}
