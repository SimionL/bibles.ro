package dbBeans;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Entity(name = "screensaver")
@Table(name = "screensaver")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class ScreensaverTable implements Serializable {

	private static final long serialVersionUID = -3218645710403830295L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "screensaver_id", nullable = false)
	private long screensaverId;

	@Column(name = "category")
	private String category;

	@Column(name = "book")
	private int book;

	@Column(name = "chapter")
	private int chapter;

	@Column(name = "verse")
	private int verse;

	@Column(name = "picture_name")
	private String pictureName;

	@Column(name = "picture_opacity")
	private int pictureOpacity;

	@Column(name = "verse_opacity")
	private int verseOpacity;

	public long getScreensaverId() {
		return screensaverId;
	}

	public void setScreensaverId(long screensaverId) {
		this.screensaverId = screensaverId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getBook() {
		return book;
	}

	public void setBook(int book) {
		this.book = book;
	}

	public int getChapter() {
		return chapter;
	}

	public void setChapter(int chapter) {
		this.chapter = chapter;
	}

	public int getVerse() {
		return verse;
	}

	public void setVerse(int verse) {
		this.verse = verse;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public int getPictureOpacity() {
		return pictureOpacity;
	}

	public void setPictureOpacity(int pictureOpacity) {
		this.pictureOpacity = pictureOpacity;
	}

	public int getVerseOpacity() {
		return verseOpacity;
	}

	public void setVerseOpacity(int verseOpacity) {
		this.verseOpacity = verseOpacity;
	}

	public boolean equals(Object o) {

		try {
			if (o != null) {

				try{
					return ((ScreensaverTable) o).getScreensaverId() == this.getScreensaverId();
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
		return new Long(screensaverId).intValue();
	}
}