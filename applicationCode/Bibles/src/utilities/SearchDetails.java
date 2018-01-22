package utilities;

import java.util.LinkedList;

public class SearchDetails {

	String version;
	String book;
	String chapter;
	final LinkedList<String> verseList = new LinkedList<>();

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public String getChapter() {
		return chapter;
	}
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	public LinkedList<String> getVerseList() {
		return verseList;
	}
}