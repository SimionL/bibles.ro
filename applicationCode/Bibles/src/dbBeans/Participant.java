package dbBeans;

import java.io.Serializable;

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
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Entity(name = "participant")
@Table(name = "participant")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class Participant implements Serializable {

	private static final long serialVersionUID = -1776173986706192926L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "participant_id", nullable = false)
	private long participantId;

	@Column(name = "name")
	private String name;

	@Column(name = "forename")
	private String forename;

	@Column(name = "duration")
	private int duration;

	@Column(name = "resourcespath")
	private String resourcesPath;

	@Column(name = "description")
	private String description;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "participantorder")
	private int participantOrder;

	@Column(name = "participantresources")
	private String resources;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "event_id")
	private Event event;

	public long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(long participantId) {
		this.participantId = participantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getResourcesPath() {
		return resourcesPath;
	}

	public void setResourcesPath(String resourcesPath) {
		this.resourcesPath = resourcesPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Event getEvent() {
		return event;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public int getParticipantOrder() {
		return participantOrder;
	}

	public void setParticipantOrder(int participantOrder) {
		this.participantOrder = participantOrder;
	}

	public boolean equals(Object o) {

		try {
			if (o != null) {

				try{
					return ((Participant) o).getParticipantId() == this.getParticipantId();
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
		return new Long(participantId).intValue();
	}
}