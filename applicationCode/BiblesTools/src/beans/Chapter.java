package beans;

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
@Entity(name = "chapter")
@Table(name = "chapter")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class Chapter implements Serializable {

	private static final long serialVersionUID = -3640108135222496482L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "chapter_id")
	private long chapterId;

	@Column(name = "value")
	private int value;

	@Column(name = "entirechapter")
	private String entireChapter;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "book_id", nullable=true)
	private Book book;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getEntireChapter() {
		return entireChapter;
	}

	public void setEntireChapter(String entireChapter) {
		this.entireChapter = entireChapter;
	}

	public long getChapterId() {
		return chapterId;
	}

	public void setChapterId(long chapterId) {
		this.chapterId = chapterId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public boolean equals(Object o) {

		try {
			if (o != null) {

				try{

					Chapter chapter = ((Chapter) o);

					return chapter.getChapterId() == this.getChapterId() && chapter.getValue() == this.getValue();
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
		return new Long(chapterId).intValue();
	}
}