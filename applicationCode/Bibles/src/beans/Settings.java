package beans;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public final class Settings{

	private transient String bible;
	private transient String settings;
	private transient String popup;
	private transient String reference;
	private transient String church;
	private transient String screensaver;
	private transient String code;
	private transient String voice;
	private transient String feedback;
	private transient String thankYou;

	private transient String eventId;
	private final TreeSet<String> languages = new TreeSet<>();
	private final LinkedHashSet<String> formFontList = new LinkedHashSet<>();
	private final LinkedHashSet<String> searchBlockLengthSelectionList = new LinkedHashSet<>();
	private transient final LinkedHashMap<Integer, String> searchAreaOptions =  new LinkedHashMap<>();

	private transient String displayReferencesLabel;
	private transient String wordWrapLabel;
	private transient String displayEntireChapterLabel;
	private transient String highlightsTextLabel;
	private transient String formFontLabel;
	private transient String selectedLanguage;
	private transient String selectLanguage;
	private transient String formFontSelected = "3";
	private transient String searchBlockLength;
	private transient String searchBlockLengthSelection = "1";
	private transient String inexactColorsLabel;
	private transient String exactColorsLabel;
	private transient String exactColorPaletteSelected = "#00ca33";
	private transient String inexactColorPaletteSelected = "#d20000";
	private transient String searchLevel;
	private transient String error;
	private transient String identifiedWords = "";
	private transient String languageCode;

	private transient boolean openPopup;
	private transient boolean usingVoice;
	private transient boolean wordWrap;
	private transient boolean highlights;
	private transient boolean displayReference;
	private transient boolean displayEntireChapter;

	private transient int searchLevelSelected = 1;

	public boolean isDisplayReference() {
		return displayReference;
	}

	public void setDisplayReference(boolean displayReference) {
		this.displayReference = displayReference;
	}

	public String getBible() {
		return bible;
	}

	public void setBible(String bible) {
		this.bible = bible;
	}

	public String getSettings() {
		return settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

	public String getChurch() {
		return church;
	}

	public void setChurch(String church) {
		this.church = church;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getSelectedLanguage() {
		return selectedLanguage;
	}

	public void setSelectedLanguage(String selectedLanguage) {
		this.selectedLanguage = selectedLanguage;
	}

	public String getSelectLanguage() {
		return selectLanguage;
	}

	public void setSelectLanguage(String selectLanguage) {
		this.selectLanguage = selectLanguage;
	}

	public String getDisplayReferencesLabel() {
		return displayReferencesLabel;
	}

	public void setDisplayReferencesLabel(String displayReferencesLabel) {
		this.displayReferencesLabel = displayReferencesLabel;
	}

	public TreeSet<String> getLanguages() {
		return languages;
	}

	public boolean isWordWrap() {
		return wordWrap;
	}

	public void setWordWrap(boolean wordWrap) {
		this.wordWrap = wordWrap;
	}

	public boolean isDisplayEntireChapter() {
		return displayEntireChapter;
	}

	public void setDisplayEntireChapter(boolean displayEntireChapter) {
		this.displayEntireChapter = displayEntireChapter;
	}

	public String getWordWrapLabel() {
		return wordWrapLabel;
	}

	public void setWordWrapLabel(String wordWrapLabel) {
		this.wordWrapLabel = wordWrapLabel;
	}

	public String getDisplayEntireChapterLabel() {
		return displayEntireChapterLabel;
	}

	public void setDisplayEntireChapterLabel(String displayEntireChapterLabel) {
		this.displayEntireChapterLabel = displayEntireChapterLabel;
	}

	public String getHighlightsTextLabel() {
		return highlightsTextLabel;
	}

	public void setHighlightsTextLabel(String highlightsTextLabel) {
		this.highlightsTextLabel = highlightsTextLabel;
	}

	public boolean isHighlights() {
		return highlights;
	}

	public void setHighlights(boolean highlights) {
		this.highlights = highlights;
	}

	public String getFormFontLabel() {
		return formFontLabel;
	}

	public void setFormFontLabel(String formFontLabel) {
		this.formFontLabel = formFontLabel;
	}

	public LinkedHashSet<String> getFormFontList() {
		return formFontList;
	}

	public LinkedHashSet<String> getSearchBlockLengthSelectionList() {
		return searchBlockLengthSelectionList;
	}

	public String getFormFontSelected() {
		return formFontSelected;
	}

	public void setFormFontSelected(String formFontSelected) {
		this.formFontSelected = formFontSelected;
	}

	public String getSearchBlockLength() {
		return searchBlockLength;
	}

	public void setSearchBlockLength(String searchBlockLength) {
		this.searchBlockLength = searchBlockLength;
	}

	public String getSearchBlockLengthSelection() {
		return searchBlockLengthSelection;
	}

	public void setSearchBlockLengthSelection(String searchBlockLengthSelection) {
		this.searchBlockLengthSelection = searchBlockLengthSelection;
	}

	public String getInexactColorsLabel() {
		return inexactColorsLabel;
	}

	public void setInexactColorsLabel(String inexactColorsLabel) {
		this.inexactColorsLabel = inexactColorsLabel;
	}

	public String getExactColorsLabel() {
		return exactColorsLabel;
	}

	public void setExactColorsLabel(String exactColorsLabel) {
		this.exactColorsLabel = exactColorsLabel;
	}

	public String getExactColorPaletteSelected() {
		return exactColorPaletteSelected;
	}

	public void setExactColorPaletteSelected(String exactColorPaletteSelected) {
		this.exactColorPaletteSelected = exactColorPaletteSelected;
	}

	public String getInexactColorPaletteSelected() {
		return inexactColorPaletteSelected;
	}

	public void setInexactColorPaletteSelected(String inexactColorPaletteSelected) {
		this.inexactColorPaletteSelected = inexactColorPaletteSelected;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getScreensaver() {
		return screensaver;
	}

	public void setScreensaver(String screensaver) {
		this.screensaver = screensaver;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPopup() {
		return popup;
	}

	public void setPopup(String popup) {
		this.popup = popup;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public boolean isOpenPopup() {
		return openPopup;
	}

	public void setOpenPopup(boolean openPopup) {
		this.openPopup = openPopup;
	}

	public String getSearchLevel() {
		return searchLevel;
	}

	public void setSearchLevel(String searchLevel) {
		this.searchLevel = searchLevel;
	}

	public LinkedHashMap<Integer, String> getSearchAreaOptions() {
		return searchAreaOptions;
	}

	public int getSearchLevelSelected() {
		return searchLevelSelected;
	}

	public void setSearchLevelSelected(int searchLevelSelected) {
		this.searchLevelSelected = searchLevelSelected;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public boolean isUsingVoice() {
		return usingVoice;
	}

	public void setUsingVoice(boolean usingVoice) {
		this.usingVoice = usingVoice;
	}

	public String getIdentifiedWords() {
		return identifiedWords;
	}

	public void setIdentifiedWords(String identifiedWords) {
		this.identifiedWords = identifiedWords;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getThankYou() {
		return thankYou;
	}

	public void setThankYou(String thankYou) {
		this.thankYou = thankYou;
	}

}