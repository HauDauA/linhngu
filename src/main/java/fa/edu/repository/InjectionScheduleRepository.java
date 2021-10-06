package fa.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import fa.edu.model.InjectionSchedule;
import fa.edu.model.Vaccine;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface InjectionScheduleRepository extends PagingAndSortingRepository<InjectionSchedule, String> {
    @Query("SELECT s FROM InjectionSchedule s INNER JOIN s.vaccine v INNER JOIN s.place p INNER JOIN s.customer c"
            + "	Where CONCAT(v.vaccineName,c.fullName,p.placeName,s.statusCustomer)  LIKE %?1% AND v.status =1 AND c.status =1 AND s.status=1" )
    public Page<InjectionSchedule> listSchedules(String keyword, Pageable pageable);

    @Query("SELECT s FROM InjectionSchedule s INNER JOIN s.vaccine v INNER JOIN s.place p INNER JOIN s.customer c"
            + "	Where v.status =1 AND c.status =1 AND s.status=1" )
    public Page<InjectionSchedule> listSchedulesNoSearch( Pageable pageable);
    
    @Query("SELECT s FROM InjectionSchedule s INNER JOIN s.vaccine v INNER JOIN s.place p INNER JOIN s.customer c"
            + "	Where v.status =1 AND c.status =1 AND s.status=1" )
    public List<InjectionSchedule> Top5( Pageable pageable);
    
    @Query("SELECT s FROM InjectionSchedule s WHERE s.status=1 AND s.result=1")
    public List<InjectionSchedule> schedules();

    @Transactional
    @Modifying
    @Query("Update InjectionSchedule  i Set i.status = 0 Where i.injectionScheduleId = :id")
     void deleteSchedule(@Param("id") String id);
    
    @Transactional
    @Modifying
    @Query("Update InjectionSchedule  i Set i.result = 0 Where i.injectionScheduleId = :id")
     void updateSchedule(String id);
   
    
    @Transactional
    @Modifying
    @Query("Update InjectionSchedule  i Set i.result = 1 Where i.injectionScheduleId = :id")
    void updateSchedule1(String id);
    
    @Query("Select Count(*) FROM InjectionSchedule i Where i.status=1")
    public Integer countSchedule();


}
