package fa.edu.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "[CUSTOMER]")
public class Customer {

	@Id
	@GeneratedValue(generator = "customer")
	@GenericGenerator(name = "customer", strategy = "fa.edu.generator.Customer")
	@Column(name = "CUSTOMER_ID", nullable = false)
	private String customerId;

	@NotBlank(message = "Enter your Address")
	@Column(name = "ADDRESS", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String address;

	@NotNull(message = "Enter your birthday")
	@Column(name = "DATE_OF_BIRTH", columnDefinition = "DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

	@Email(message = "Email format is not correct")
	@NotBlank(message = "Enter your email")
	@Column(name = "EMAIL", nullable = false, columnDefinition = "VARCHAR(100)")
	private String email;

	@NotBlank(message = "Enter your fullname")
	@Length(max = 50, message = "Length of fullname <=50")
	@Column(name = "FULL_NAME", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String fullName;
	
	@NotNull(message = "Enter your gender")
	@Column(name = "GENDER", nullable = false,columnDefinition = "VARCHAR(10)")
	private String gender;

	@NotBlank(message = "Enter your Identity Card")
	@Column(name = "IDENTITY_CARD", nullable = false, columnDefinition = "VARCHAR(12)")
	private String identityCard;


	@NotBlank(message = "Enter your phone")
	@Length(min = 10, max = 10, message = "Length of phone little or equals 10")
	@Column(name = "PHONE", nullable = false, columnDefinition = "VARCHAR(20)")
	private String phone;


	@Column(name = "STATUS", columnDefinition = "BIT DEFAULT 1", nullable = false)
	private int status;

	@OneToMany(mappedBy = "customer")
	@JsonIgnore
	private List<InjectionSchedule> injectionSchedule;
}
