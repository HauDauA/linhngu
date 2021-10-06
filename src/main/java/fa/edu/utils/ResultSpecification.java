package fa.edu.utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import fa.edu.model.InjectionResult;

public class ResultSpecification implements Specification<InjectionResult> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6245107715660513072L;
	private InjectionResult filter;

	public ResultSpecification(InjectionResult filter) {
		super();
		this.filter = filter;
	}
	
	
	@Override
	public Predicate toPredicate(Root<InjectionResult> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		Predicate p = cb.and();
		boolean customer=filter.getInjectionSchedule().getCustomer() != null;
		boolean vaccine=filter.getInjectionSchedule().getVaccine() != null;
		
		if(customer && vaccine) {
			p.getExpressions().add(cb.notEqual(root.get("injectionSchedule").get("customer").get("status"), 0));
			p.getExpressions().add(cb.notEqual(root.get("injectionSchedule").get("vaccine").get("status"), 0));
			p.getExpressions().add(cb.notEqual(root.get("injectionSchedule").get("status"), 0));
		}
		if(customer) {
			p.getExpressions().add(cb.like(root.get("injectionSchedule").get("customer").get("fullName"), 
					"%"+filter.getInjectionSchedule().getCustomer().getFullName()+"%"));
		}
		if(customer) {
			p.getExpressions().add(cb.like(root.get("injectionSchedule").get("customer").get("phone"), 
					"%"+filter.getInjectionSchedule().getCustomer().getPhone()+"%"));
		}
		if(customer) {
			p.getExpressions().add(cb.like(root.get("injectionSchedule").get("customer").get("email"), 
					"%"+filter.getInjectionSchedule().getCustomer().getEmail()+"%"));
		}
		if(customer) {
			p.getExpressions().add(cb.like(root.get("injectionSchedule").get("customer").get("identityCard"), 
					"%"+filter.getInjectionSchedule().getCustomer().getIdentityCard()+"%"));
		}
		if(vaccine) {
			p.getExpressions().add(cb.like(root.get("injectionSchedule").get("vaccine").get("vaccineName"),
					"%"+filter.getInjectionSchedule().getVaccine().getVaccineName()+"%"));
			}
		return p;
	}

}
