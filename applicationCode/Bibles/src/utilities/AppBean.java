package utilities;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import dbBeans.ScreensaverTable;

public final class AppBean {

	private final transient TreeMap<Integer, ScreensaverTable> screensaverMap = new TreeMap<>();
	private final transient Map<String, String> selectedImagesMap = new HashMap<>();
	private int currentScreensaver = 0;
	private transient String verseValue;
	private transient int lastSettings = 1;
	private transient boolean next;
	private final transient LinkedHashMap<String, String> versesMap = new LinkedHashMap<>();
	private final TreeMap<Integer, ApplicationElement> folderMap = new TreeMap<>();
	private final Map<String, String> mapCode = new HashMap<>();

	public TreeMap<Integer, ScreensaverTable> getScreensaverMap() {
		return screensaverMap;
	}
	public int getCurrentScreensaver() {
		return currentScreensaver;
	}
	public void setCurrentScreensaver(int currentScreensaver) {
		this.currentScreensaver = currentScreensaver;
	}
	public Map<String, String> getSelectedImagesMap() {
		return selectedImagesMap;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public String getVerseValue() {
		return verseValue;
	}
	public void setVerseValue(String verseValue) {
		this.verseValue = verseValue;
	}
	public LinkedHashMap<String, String> getVersesMap() {
		return versesMap;
	}
	public TreeMap<Integer, ApplicationElement> getFolderMap() {
		return folderMap;
	}
	public int getLastSettings() {
		return lastSettings;
	}
	public void setLastSettings(int lastSettings) {
		this.lastSettings = lastSettings;
	}
	public Map<String, String> getMapCode() {
		return mapCode;
	}
}