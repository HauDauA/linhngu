package fa.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="[USER]")
public class User {
	@Id
	private Integer id;
	
	@Column(name="Username")
	private String username;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Address")
	private String address;
	
	@Column(name="Phone")
	private String phone;
	
	@Column(name="Email")
	private String email;	
	
	@Column(name="Note")
	private String note;
}
