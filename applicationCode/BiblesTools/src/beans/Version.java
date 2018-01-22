package beans;

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
import javax.persistence.UniqueConstraint;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Entity(name = "version")
@Table(name = "version", uniqueConstraints = {@UniqueConstraint(columnNames = "value")})
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class Version implements Serializable {

	private static final long serialVersionUID = 8199192577182225030L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "version_id")
	private long versionId;

	@Column(name = "value")
	private String value;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "version_id", nullable=true)
	@Column(name = "version_id")
	private List<Book> books;

	public long getVersionId() {
		return versionId;
	}

	public void setVersionId(long versionId) {
		this.versionId = versionId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public boolean equals(Object o) {

		try {
			if (o != null) {

				try{

					Version version = ((Version) o);

					return version.getVersionId() == this.getVersionId() && version.getValue().trim().equals(this.getValue().trim());
				}
				catch(Exception e){
					e.printStackTrace();
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public int hashCode() {
		return new Long(versionId).intValue();
	}
}