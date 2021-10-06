package fa.edu.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "[VACCINE]")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine {

	@Id
	@GeneratedValue(generator = "vaccine")
	@GenericGenerator(name = "vaccine", strategy = "fa.edu.generator.Vaccine")
	private String vaccineId;

	@NotNull(message = "Contraindication Not Null!!")
	@Length(min = 5,message = "Value Not little 10!!")
	@Column(name = "CONTRAINDICATION", columnDefinition = "NVARCHAR(600)")
	private String contraindication;

	@NotNull(message = "Indication Not Null!!")
	@Length(min = 5,message = "Value Not little 10!!")
	@Column(name = "INDICATION", columnDefinition = "NVARCHAR(600)")
	private String indication;

	@NotNull(message = "Number Of Injection Not Null!")
	@Column(name = "NUMBER_OF_INJECTION",nullable = false)
	private int numberOfInjection;


	@NotNull(message = "Origin Not Null!!")
	@Length(min = 5,message = "Value Not little 10!!")
	@Column(name = "ORIGIN", columnDefinition = "NVARCHAR(100)")
	private String origin;

	@NotNull(message = "Enter time vaccine begin")
	@Column(name = "Time_Begin", columnDefinition = "DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date timeBegin;

	@NotNull(message = "Enter time vaccine end")
	@Column(name = "Time_End", columnDefinition = "DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date timeEnd;

	@NotNull(message = "Usage Not Null!!")
	@Length(min = 5,message = "Value Not little 10!!")
	@Column(name = "USAGE", columnDefinition = "NVARCHAR(600)")
	private String usage;

	@NotNull(message = "Vaccine Name not null")
	@Length(min = 5, message = "Length Vaccine Name greate or equals 5")
	@Column(name = "VACCINE_NAME", columnDefinition = "NVARCHAR(100)", nullable = false)
	private String vaccineName;

	@Column(name = "Status", columnDefinition = "BIT DEFAULT 1", nullable = false)
	private int  status;


	@OneToMany(mappedBy = "vaccine")
	@JsonIgnore
	private List<InjectionSchedule> injectionSchedules;


}
