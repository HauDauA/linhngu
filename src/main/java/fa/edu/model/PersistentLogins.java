package fa.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "persistent_logins")
public class PersistentLogins {
    @Id
    @Column(name="series" , columnDefinition = "VARCHAR(64) " , nullable = false)
    private String series;

    @Column(name = "username",columnDefinition = "VARCHAR(64)",nullable = false)
    private String username;

    @Column(name = "token",columnDefinition = "VARCHAR(64)",nullable = false)
    private  String token;

    @Column(name = "last_used",nullable = false)
    private Date last_used;

}
