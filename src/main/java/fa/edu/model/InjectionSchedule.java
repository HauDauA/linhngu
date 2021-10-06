package fa.edu.model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "INJECTION_SCHEDULE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InjectionSchedule {

	@Id
	@GeneratedValue(generator = "schedule")
	@GenericGenerator(name = "schedule", strategy = "fa.edu.generator.InjectionSchedule")
	@Column(name = "INJECTIONSCHEDULE_ID", nullable = false)
	private String injectionScheduleId;

	@Column(name = "DESCRIPTION", columnDefinition = "VARCHAR(200)")
	private String description;

	@Column(name = "DATE", columnDefinition = "DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	@Column(name = "NUMBER_OF_INJECTION", nullable = false)
	private String numberOfInjection;

	private String fever;

	private String cough;

	private String shortnessOfBreath;

	private String soreThroat;

	private String nausea;

	private String diarrhea;

	private String skinBleeding;

	private String skinRash;

	@Column(name = "STATUS_CUSTOMER", columnDefinition = "NVARCHAR(200)")
	private String statusCustomer;

	@Column(name = "STATUS", columnDefinition = "BIT DEFAULT 1", nullable = false)
	private int status;


	@NotNull(message = "Customer  Not Null")
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	@JsonIgnore
	private Customer customer;

	@NotNull(message = "Vaccine must be madatory!")
	@ManyToOne
	@JoinColumn(name = "VACCINE_ID")
	@JsonIgnore
	private Vaccine vaccine;

	@Column(name = "RESULT", columnDefinition = "BIT DEFAULT 1", nullable = false)
	private int result;

	@NotNull(message = "Place must be madatory!")
	@ManyToOne
	@JoinColumn(name = "INJECTION_PLACE")
	@JsonIgnore
	private WorkPlace place;

	@OneToOne(mappedBy = "injectionSchedule")
	private InjectionResult resultInjection;

}
