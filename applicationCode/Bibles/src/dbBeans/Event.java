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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Entity(name = "event")
@Table(name = "event")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class Event implements Serializable {

	private static final long serialVersionUID = -7833048304221932661L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "event_id", nullable = false)
	private long eventId;

	@Column(name = "eventname")
	private String eventName;

	@Column(name = "eventdate")
	private String eventDate;

	@Column(name = "eventhour")
	private String eventHour;

	@Column(name = "eventduration")
	private String eventDuration;

	@Column(name = "event_password")
	private String eventPassword;

	@Column(name = "eventdescription")
	private String eventDescription;

	@Column(name = "folderpath")
	private String folderPath;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private ChurchTable user;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "event_id", nullable=true, insertable = false, updatable = false)
	private List<Participant> participantsList;

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public ChurchTable getUser() {
		return user;
	}

	public void setUser(ChurchTable user) {
		this.user = user;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventHour() {
		return eventHour;
	}

	public void setEventHour(String eventHour) {
		this.eventHour = eventHour;
	}

	public List<Participant> getParticipantsList() {
		return participantsList;
	}

	public void setParticipantsList(List<Participant> participantsList) {
		this.participantsList = participantsList;
	}

	public String getEventDuration() {
		return eventDuration;
	}

	public void setEventDuration(String eventDuration) {
		this.eventDuration = eventDuration;
	}

	public String getEventPassword() {
		return eventPassword;
	}

	public void setEventPassword(String eventPassword) {
		this.eventPassword = eventPassword;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public boolean equals(Object o) {

		try {
			if (o != null) {

				try{
					return ((Event) o).getEventId() == this.getEventId();
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
		return new Long(eventId).intValue();
	}
}