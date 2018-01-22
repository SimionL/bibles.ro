package beans;

import java.util.LinkedList;

import utilities.ApplicationElement;

public class ApplicationCode {

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

	private transient String error;
	private transient String eventId;
	private transient String popupInformation;
	private transient String popupId;
	private transient String fileName;
	private transient String identifiedWords = "";
	private transient String languageCode;

	private transient boolean openPopup;
	private transient boolean usingVoice;

	private int selectedFileId;
	private int selectedFileValue;

	private final transient LinkedList<ApplicationElement> folder = new LinkedList<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getScreensaver() {
		return screensaver;
	}

	public void setScreensaver(String screensaver) {
		this.screensaver = screensaver;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public LinkedList<ApplicationElement> getFolder() {
		return folder;
	}

	public int getSelectedFileId() {
		return selectedFileId;
	}

	public void setSelectedFileId(int selectedFileId) {
		this.selectedFileId = selectedFileId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public int getSelectedFileValue() {
		return selectedFileValue;
	}

	public void setSelectedFileValue(int selectedFileValue) {
		this.selectedFileValue = selectedFileValue;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getIdentifiedWords() {
		return identifiedWords;
	}

	public void setIdentifiedWords(String identifiedWords) {
		this.identifiedWords = identifiedWords;
	}

	public boolean isUsingVoice() {
		return usingVoice;
	}

	public void setUsingVoice(boolean usingVoice) {
		this.usingVoice = usingVoice;
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