package beans;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import utilities.VerseDetails;

public final class Bible{

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

	private final transient List<VerseDetails> verseValue =  new LinkedList<>();
	private transient Map<String, String> historyMap = new LinkedHashMap<>();
	private transient LinkedHashSet<String> versions;
	private transient LinkedHashSet<String> books;
	private transient LinkedHashSet<String> chapters;
	private transient LinkedHashSet<String> verses;
	private transient TreeSet<String> profiles = new TreeSet<>(Arrays.asList("1", "2", "3"));
	private final transient Set<String> email = new LinkedHashSet<>();

	private transient String eventId;
	private transient String selectedProfile = "1";
	private transient String selectedOldProfile;
	private transient String selectedVersion;
	private transient String selectedBook;
	private transient String selectedChapter;
	private transient String selectedVerse;
	private transient String selectVersion;
	private transient String selectBook;
	private transient String selectChapter;
	private transient String selectVerse;
	private transient String noVersion;
	private transient String noBook;
	private transient String noChapter;
	private transient String noVerse;
	private transient String searchVerse;
	private transient String searchText;
	private transient String noNextVerse;
	private transient String noBackVerse;
	private transient String noVerseSelected;
	private transient String references;
	private transient String noResult;
	private transient String formFontSelected = "4";
	private transient String unHighlightedText;
	private transient String nonColorSearchedText;
	private transient String history;
	private transient String searchHistory;
	private transient String error;
	private transient String ok;
	private transient String popupInformation;
	private transient String popupId;
	private transient String curentDisplay;
	private transient String identifiedWords = "";
	private transient String languageCode;
	private transient String theSpokenWords;
	private transient String searchByReference;
	private transient String searchByText;
	private transient String placeholderSuggestion;
	private transient String placeholderReference = "mat 2 5";
	private transient String okEmail;
	private transient String emailNotExist = "Email not exist. Please add a valid email address!";
	private transient String phoneNotExist = "Phone not exist. Please add a valid phone number!";
	private transient String emailNotSelected = "Please select an email address from this list:";
	private transient String phoneNotSelected = "Please select a phone number from this list:";
	private transient String selectBibleText = "Please select bible text!";
	private transient String okPhone = "The phone message was sent at:";

	private transient boolean wordWrap;
	private transient boolean selectAll;
	private transient boolean openPopup;
	private transient boolean usingVoice;
	private transient boolean displayReference;
	private transient boolean searchTextAvailable;

	// load email settings

	private String messagesEncapsulation;
	private String userEmail;
	private String automatSendMessage;
	private int emailFrom = 1;
	private boolean firstAccess = true;


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

	public String getSelectVersion() {
		return selectVersion;
	}

	public void setSelectVersion(String selectVersion) {
		this.selectVersion = selectVersion;
	}

	public String getSelectBook() {
		return selectBook;
	}

	public void setSelectBook(String selectBook) {
		this.selectBook = selectBook;
	}

	public String getSelectChapter() {
		return selectChapter;
	}

	public void setSelectChapter(String selectChapter) {
		this.selectChapter = selectChapter;
	}

	public String getSelectVerse() {
		return selectVerse;
	}

	public void setSelectVerse(String selectVerse) {
		this.selectVerse = selectVerse;
	}

	public String getNoVersion() {
		return noVersion;
	}

	public void setNoVersion(String noVersion) {
		this.noVersion = noVersion;
	}

	public String getNoBook() {
		return noBook;
	}

	public void setNoBook(String noBook) {
		this.noBook = noBook;
	}

	public String getNoChapter() {
		return noChapter;
	}

	public void setNoChapter(String noChapter) {
		this.noChapter = noChapter;
	}

	public String getNoVerse() {
		return noVerse;
	}

	public void setNoVerse(String noVerse) {
		this.noVerse = noVerse;
	}

	public String getSelectedVersion() {
		return selectedVersion;
	}

	public void setSelectedVersion(String selectedVersion) {
		this.selectedVersion = selectedVersion;
	}

	public String getSelectedBook() {
		return selectedBook;
	}

	public void setSelectedBook(String selectedBook) {
		this.selectedBook = selectedBook;
	}

	public String getSelectedChapter() {
		return selectedChapter;
	}

	public void setSelectedChapter(String selectedChapter) {
		this.selectedChapter = selectedChapter;
	}

	public String getSelectedVerse() {
		return selectedVerse;
	}

	public void setSelectedVerse(String selectedVerse) {
		this.selectedVerse = selectedVerse;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public LinkedHashSet<String> getVersions() {
		return versions;
	}

	public void setVersions(LinkedHashSet<String> versions) {
		this.versions = versions;
	}

	public LinkedHashSet<String> getBooks() {
		return books;
	}

	public void setBooks(LinkedHashSet<String> books) {
		this.books = books;
	}

	public LinkedHashSet<String> getChapters() {
		return chapters;
	}

	public void setChapters(LinkedHashSet<String> chapters) {
		this.chapters = chapters;
	}

	public LinkedHashSet<String> getVerses() {
		return verses;
	}

	public void setVerses(LinkedHashSet<String> verses) {
		this.verses = verses;
	}

	public List<VerseDetails> getVerseValue() {
		return verseValue;
	}

	public String getSearchVerse() {
		return searchVerse;
	}

	public void setSearchVerse(String searchVerse) {
		this.searchVerse = searchVerse;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getNoNextVerse() {
		return noNextVerse;
	}

	public void setNoNextVerse(String noNextVerse) {
		this.noNextVerse = noNextVerse;
	}

	public String getNoBackVerse() {
		return noBackVerse;
	}

	public void setNoBackVerse(String noBackVerse) {
		this.noBackVerse = noBackVerse;
	}

	public String getNoVerseSelected() {
		return noVerseSelected;
	}

	public void setNoVerseSelected(String noVerseSelected) {
		this.noVerseSelected = noVerseSelected;
	}

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public String getNoResult() {
		return noResult;
	}

	public void setNoResult(String noResult) {
		this.noResult = noResult;
	}

	public boolean isWordWrap() {
		return wordWrap;
	}

	public void setWordWrap(boolean wordWrap) {
		this.wordWrap = wordWrap;
	}

	public String getFormFontSelected() {
		return formFontSelected;
	}

	public void setFormFontSelected(String formFontSelected) {
		this.formFontSelected = formFontSelected;
	}

	public String getUnHighlightedText() {
		return unHighlightedText;
	}

	public void setUnHighlightedText(String unHighlightedText) {
		this.unHighlightedText = unHighlightedText;
	}

	public String getNonColorSearchedText() {
		return nonColorSearchedText;
	}

	public void setNonColorSearchedText(String nonColorSearchedText) {
		this.nonColorSearchedText = nonColorSearchedText;
	}

	public boolean isSearchTextAvailable() {
		return searchTextAvailable;
	}

	public void setSearchTextAvailable(boolean searchTextAvailable) {
		this.searchTextAvailable = searchTextAvailable;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public Map<String, String> getHistoryMap() {
		return historyMap;
	}

	public void setHistoryMap(Map<String, String> historyMap) {
		this.historyMap = historyMap;
	}

	public String getSearchHistory() {
		return searchHistory;
	}

	public void setSearchHistory(String searchHistory) {
		this.searchHistory = searchHistory;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getPopupInformation() {
		return popupInformation;
	}

	public void setPopupInformation(String popupInformation) {
		this.popupInformation = popupInformation;
	}

	public boolean isOpenPopup() {
		return openPopup;
	}

	public void setOpenPopup(boolean openPopup) {
		this.openPopup = openPopup;
	}

	public String getPopupId() {
		return popupId;
	}

	public void setPopupId(String popupId) {
		this.popupId = popupId;
	}

	public String getCurentDisplay() {
		return curentDisplay;
	}

	public void setCurentDisplay(String curentDisplay) {
		this.curentDisplay = curentDisplay;
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

	public String getTheSpokenWords() {
		return theSpokenWords;
	}

	public void setTheSpokenWords(String theSpokenWords) {
		this.theSpokenWords = theSpokenWords;
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

	public String getSearchByReference() {
		return searchByReference;
	}

	public void setSearchByReference(String searchByReference) {
		this.searchByReference = searchByReference;
	}

	public String getSearchByText() {
		return searchByText;
	}

	public void setSearchByText(String searchByText) {
		this.searchByText = searchByText;
	}

	public String getPlaceholderSuggestion() {
		return placeholderSuggestion;
	}

	public void setPlaceholderSuggestion(String placeholderSuggestion) {
		this.placeholderSuggestion = placeholderSuggestion;
	}

	public String getPlaceholderReference() {
		return placeholderReference;
	}

	public void setPlaceholderReference(String placeholderReference) {
		this.placeholderReference = placeholderReference;
	}

	public String getOkEmail() {
		return okEmail;
	}

	public void setOkEmail(String okEmail) {
		this.okEmail = okEmail;
	}

	public int getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(int emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getMessagesEncapsulation() {
		return messagesEncapsulation;
	}

	public void setMessagesEncapsulation(String messagesEncapsulation) {
		this.messagesEncapsulation = messagesEncapsulation;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getAutomatSendMessage() {
		return automatSendMessage;
	}

	public void setAutomatSendMessage(String automatSendMessage) {
		this.automatSendMessage = automatSendMessage;
	}

	public boolean isFirstAccess() {
		return firstAccess;
	}

	public void setFirstAccess(boolean firstAccess) {
		this.firstAccess = firstAccess;
	}

	public String getEmailNotSelected() {
		return emailNotSelected;
	}

	public void setEmailNotSelected(String emailNotSelected) {
		this.emailNotSelected = emailNotSelected;
	}

	public String getEmailNotExist() {
		return emailNotExist;
	}

	public void setEmailNotExist(String emailNotExist) {
		this.emailNotExist = emailNotExist;
	}

	public String getSelectBibleText() {
		return selectBibleText;
	}

	public void setSelectBibleText(String selectBibleText) {
		this.selectBibleText = selectBibleText;
	}

	public String getPhoneNotExist() {
		return phoneNotExist;
	}

	public void setPhoneNotExist(String phoneNotExist) {
		this.phoneNotExist = phoneNotExist;
	}

	public String getPhoneNotSelected() {
		return phoneNotSelected;
	}

	public void setPhoneNotSelected(String phoneNotSelected) {
		this.phoneNotSelected = phoneNotSelected;
	}

	public String getOkPhone() {
		return okPhone;
	}

	public void setOkPhone(String okPhone) {
		this.okPhone = okPhone;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public Set<String> getEmail() {
		return email;
	}

	public TreeSet<String> getProfiles() {
		return profiles;
	}

	public String getSelectedProfile() {
		return selectedProfile;
	}

	public void setSelectedProfile(String selectedProfile) {
		this.selectedProfile = selectedProfile;
	}

	public String getSelectedOldProfile() {
		return selectedOldProfile;
	}

	public void setSelectedOldProfile(String selectedOldProfile) {
		this.selectedOldProfile = selectedOldProfile;
	}
}