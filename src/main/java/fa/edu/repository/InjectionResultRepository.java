package fa.edu.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fa.edu.model.Customer;
import fa.edu.model.InjectionResult;
@Repository
public interface InjectionResultRepository extends PagingAndSortingRepository<InjectionResult, String> {
	@Query("SELECT r FROM InjectionResult r INNER JOIN r.injectionSchedule s INNER JOIN Customer c on (s.customer.customerId= c.customerId) "
			+ "INNER JOIN Vaccine v on (v.vaccineId=s.vaccine.vaccineId) INNER JOIN s.place p"
            + "	Where CONCAT(c.customerId,c.fullName,p.placeName,v.vaccineName,r.resultInjection,v.vaccineId)"
            + " LIKE %?1% AND v.status =1 AND c.status =1 AND s.status=1 AND r.status=1" )
    public Page<InjectionResult> listResult(String keyword, Pageable pageable);
	
	@Query("SELECT r FROM InjectionResult r INNER JOIN r.injectionSchedule s INNER JOIN Customer c on (s.customer.customerId= c.customerId) "
			+ "INNER JOIN Vaccine v on (v.vaccineId=s.vaccine.vaccineId) INNER JOIN s.place p "
            + "Where  v.status =1 AND c.status =1 AND s.status=1 AND r.status=1" )
    public Page<InjectionResult> listResultNoSearch( Pageable pageable);
	
	
	@Query("SELECT r FROM InjectionResult r INNER JOIN r.injectionSchedule s INNER JOIN Customer c on (s.customer.customerId= c.customerId) "
			+ "INNER JOIN Vaccine v on (v.vaccineId=s.vaccine.vaccineId) INNER JOIN s.place p "
            + "Where  v.status =1 AND c.status =1 AND s.status=1 AND r.status=1" )
    public List<InjectionResult> top5( Pageable pageable);
	
	
	@Transactional
    @Modifying
    @Query("Update InjectionResult  i Set i.status = 0 Where i.injectionResultId = :id")
     void deleteResult(@Param("id") String id);
	
	@Query("SELECT r FROM InjectionResult r WHERE r.status=1")
	public List<InjectionResult> results();
	
	
	@Query("Select Count(*) From InjectionResult ir WHERE ir.status=1")
	public Integer countResult();
}
