package fa.edu.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "[INJECTION_RESULT]")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InjectionResult {

	@Id
	@GeneratedValue(generator = "result")
	@GenericGenerator(name = "result", strategy = "fa.edu.generator.InjectionResult")
	@Column(name = "INJECTION_RESULT_ID", columnDefinition = "VARCHAR(255)")
	private String injectionResultId;

	@Column(name = "INJECTION_DATE")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date injectionDate;
	
	@NotNull(message = "Result Not Null")
	@Column(name = "Result", columnDefinition = "NVARCHAR(100)", nullable = false)
	private String resultInjection;

	@Column(name = "STATUS", columnDefinition = "BIT DEFAULT 1", nullable = false)
	private int status;
	
	@NotNull(message = "Employee Not Null!")
	@ManyToOne
	@JoinColumn(name="EMPLOYEE_ID")
	private Employee employee;
	
	@OneToOne(cascade =CascadeType.ALL)
	@JoinColumn(name="INJECTION_SCHEDULE_ID")
	private InjectionSchedule injectionSchedule;
	
	

}
