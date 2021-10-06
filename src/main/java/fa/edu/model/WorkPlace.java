package fa.edu.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name="WORK_PLACE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlace {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String placeName;
	
	private String bossName;
	
	private Integer numberTable;
	
	@OneToMany(mappedBy = "place")
	@JsonIgnore
	private List<InjectionSchedule> injectionSchedule;
	
	@OneToMany(mappedBy = "place")
	@JsonIgnore
	private List<Employee> employees;
}
