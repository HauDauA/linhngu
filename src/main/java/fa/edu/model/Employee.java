package fa.edu.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "[EMPLOYEE]")
public class Employee {

	@Id
	@GeneratedValue(generator = "employee")
	@GenericGenerator(name = "employee", strategy = "fa.edu.generator.Employee")
	@Column(name = "EMPLOYEE_ID", nullable = false)
	private String employeeId;

	@Column(name = "EMPLOYEE_NAME", nullable = false, columnDefinition = "VARCHAR(255)")
	@NotBlank(message = "Enter your fullname")
	@Length(max = 50, message = "Length of fullname <=50")
	private String fullName;

	@Column(name = "EMAIL", nullable = false, columnDefinition = "VARCHAR(100)", unique = true)
	@Email(message = "Email format is not correct")
	@NotBlank(message = "Enter your email")
	private String email;

	@Column(name = "ADDRESS", nullable = false, columnDefinition = "NVARCHAR(255)")
	@NotBlank(message = "Enter your Address")
	@Length(max = 255, message = "The lenght <= 255 ")
	private String address;

	@NotNull(message = "Enter your birthday")
	@Column(name = "DATE_OF_BIRTH", columnDefinition = "DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	@Column(name = "GENDER", nullable = false, columnDefinition = "VARCHAR(10)")
	@NotNull(message = "Please Select Gender !")
	private String gender;

	@NotBlank(message = "Enter your password")
	@Size(min = 6, message = "Length of password more 6 or little  20")
	@Column(name = "PASSWORD", columnDefinition = "VARCHAR(255)")
	private String password;

	@NotBlank(message = "Enter your phone")
	@Length(min = 10, max = 11, message = "Length of phone little or equals 11")
	@Column(name = "PHONE", nullable = false, columnDefinition = "VARCHAR(20)")
	private String phone;

	@NotBlank(message = "Enter your username")
	@Length(max = 10, message = "Length of username = 10")
	@Column(name = "USERNAME", columnDefinition = "VARCHAR(255)")
	private String username;

	private int idOfRole;

	@ManyToOne
	@JoinColumn(name = "INJECTION_PLACE")
	private WorkPlace place;


	@Column(name="reset_password_token")
	private  String resetPasswordToken;

	@Transient
	private String confirmPassword;

	@Column(name = "STATUS", columnDefinition = "BIT DEFAULT 1", nullable = false)
	private int status;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "employee_role", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@OneToMany(mappedBy = "employee")
	private List<InjectionResult> injectionResults;
	
	
	
	
}
