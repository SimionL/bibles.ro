package beans;

import java.util.TreeSet;

public class Voice {

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

	private transient String useVoiceLabel;

	private transient String eventId;
	private transient String error;
	private transient String popupInformation;
	private transient String popupId;
	private transient String identifiedWords = "";
	private transient String languageCode;
	private transient String theSpokenWords;
	private transient String selectedLanguage_1;
	private transient String selectedLanguage_2;
	private transient String voiceCode;
	private transient String selected_1_Text;
	private transient String selected_2_Text;

	private final TreeSet<String> languages_1 = new TreeSet<>();
	private final TreeSet<String> languages_2 = new TreeSet<>();

	private transient boolean usingVoice;
	private transient boolean openPopup;
	private transient boolean voice_1;
	private transient boolean enableSpeaking;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getUseVoiceLabel() {
		return useVoiceLabel;
	}

	public void setUseVoiceLabel(String useVoiceLabel) {
		this.useVoiceLabel = useVoiceLabel;
	}

	public boolean isUsingVoice() {
		return usingVoice;
	}

	public void setUsingVoice(boolean usingVoice) {
		this.usingVoice = usingVoice;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPopupInformation() {
		return popupInformation;
	}

	public void setPopupInformation(String popupInformation) {
		this.popupInformation = popupInformation;
	}

	public String getPopupId() {
		return popupId;
	}

	public void setPopupId(String popupId) {
		this.popupId = popupId;
	}

	public String getIdentifiedWords() {
		return identifiedWords;
	}

	public void setIdentifiedWords(String identifiedWords) {
		this.identifiedWords = identifiedWords;
	}

	public boolean isOpenPopup() {
		return openPopup;
	}

	public void setOpenPopup(boolean openPopup) {
		this.openPopup = openPopup;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getTheSpokenWords() {
		return theSpokenWords;
	}

	public void setTheSpokenWords(String theSpokenWords) {
		this.theSpokenWords = theSpokenWords;
	}

	public String getSelectedLanguage_1() {
		return selectedLanguage_1;
	}

	public void setSelectedLanguage_1(String selectedLanguage_1) {
		this.selectedLanguage_1 = selectedLanguage_1;
	}

	public String getSelectedLanguage_2() {
		return selectedLanguage_2;
	}

	public void setSelectedLanguage_2(String selectedLanguage_2) {
		this.selectedLanguage_2 = selectedLanguage_2;
	}

	public TreeSet<String> getLanguages_1() {
		return languages_1;
	}

	public TreeSet<String> getLanguages_2() {
		return languages_2;
	}

	public String getSelected_1_Text() {
		return selected_1_Text;
	}

	public void setSelected_1_Text(String selected_1_Text) {
		this.selected_1_Text = selected_1_Text;
	}

	public String getSelected_2_Text() {
		return selected_2_Text;
	}

	public void setSelected_2_Text(String selected_2_Text) {
		this.selected_2_Text = selected_2_Text;
	}

	public String getVoiceCode() {
		return voiceCode;
	}

	public void setVoiceCode(String voiceCode) {
		this.voiceCode = voiceCode;
	}

	public boolean isVoice_1() {
		return voice_1;
	}

	public void setVoice_1(boolean voice_1) {
		this.voice_1 = voice_1;
	}

	public boolean isEnableSpeaking() {
		return enableSpeaking;
	}

	public void setEnableSpeaking(boolean enableSpeaking) {
		this.enableSpeaking = enableSpeaking;
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