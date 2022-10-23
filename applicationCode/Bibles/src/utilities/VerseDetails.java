package utilities;

import java.util.HashMap;
import java.util.Map;

public class VerseDetails {

	String verse;
	boolean selected;
	Map<String, String> references = new HashMap<>();
	

	public Map<String, String> getReferences() {
		return references;
	}
	public void setReferences(Map<String, String> references) {
		this.references = references;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getVerse() {
		return verse;
	}
	public void setVerse(String verse) {
		this.verse = verse;
	}
}