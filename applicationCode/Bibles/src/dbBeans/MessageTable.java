package dbBeans;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Entity(name = "message")
@Table(name = "message")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class MessageTable implements Serializable {

	private static final long serialVersionUID = -5366716325898547900L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "message_id", nullable = false)
	private long messageId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "message_value")
	private String messageValue;

	@Column(name = "message_type")
	private int messageType;

	@Column(name = "user_password")
	private String userPassword;

	@Column(name = "message_date")
	private long messageDate;

	@Transient
	private transient String messageStringDate;

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(long messageDate) {
		this.messageDate = messageDate;
	}

	public String getMessageValue() {
		return messageValue;
	}

	public void setMessageValue(String messageValue) {
		this.messageValue = messageValue;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getMessageStringDate() {
		return messageStringDate;
	}

	public void setMessageStringDate(String messageStringDate) {
		this.messageStringDate = messageStringDate;
	}

	public boolean equals(Object o) {

		try {
			if (o != null) {

				try{
					return ((MessageTable) o).getMessageId() == this.getMessageId();
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
		return new Long(messageId).intValue();
	}
}