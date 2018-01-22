package dbBeans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Entity(name = "users")
@Table(name = "users")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class ChurchTable implements Serializable {

	private static final long serialVersionUID = 2390922197447392644L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "user_id", nullable = false)
	private long userId;

	@Column(name = "username")
	private String username;

	@Column(name = "user_password")
	private String password;

	@Column(name = "church_email")
	private String churchEmail;

	@Column(name = "church_email_password")
	private String churchEmailPassword;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "user_id", nullable=true, insertable = false, updatable = false)
	private List<Event> events;

	public String getUsername(){
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getChurchEmail() {
		return churchEmail;
	}

	public void setChurchEmail(String churchEmail) {
		this.churchEmail = churchEmail;
	}

	public String getChurchEmailPassword() {
		return churchEmailPassword;
	}

	public void setChurchEmailPassword(String churchEmailPassword) {
		this.churchEmailPassword = churchEmailPassword;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public boolean equals(Object o) {

		try {
			if (o != null) {

				try{
					return ((ChurchTable) o).getUserId() == this.getUserId();
				}
				catch(Exception e){
					//new Utilities().writeFile(e);
					return false;
				}
			}
		} catch (Exception e) {
			//new Utilities().writeFile(e);
		}
		return false;
	}

	public int hashCode() {
		return new Long(userId).intValue();
	}
}