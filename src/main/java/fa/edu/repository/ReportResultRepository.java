package fa.edu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import fa.edu.model.InjectionResult;

public interface ReportResultRepository
		extends PagingAndSortingRepository<InjectionResult, String>, JpaSpecificationExecutor<String> {
}
