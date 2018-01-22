package beans;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import dbBeans.MessageTable;

public class Feedback {
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
	private transient String error;
	private transient String popupId;

	private String wrongPasswordMessage;

	private transient String messageLabel;
	private transient String suggestionsLabel;
	private transient String bugsLabel;
	private transient String questionsLabel;
	private transient String iWantToJoinLabel;
	private transient String othersLabel;
	private transient String myNameLabel;
	private transient String messageTypeLabel;
	private transient String userPasswordLabel;

	private transient String myNameValue;
	private transient String messageValue;
	private transient String userPasswordValue;

	private transient int messageTypeValue = 5;

	private transient boolean openPopup;

	private final transient LinkedHashMap<Integer, String> typeRadioMap = new LinkedHashMap<>();

	private final transient Map<Long, MessageTable> messageBugMap = new TreeMap<>();
	private final transient Map<Long, MessageTable> messageQuestionMap = new TreeMap<>();
	private final transient Map<Long, MessageTable> messageSuggestionMap = new TreeMap<>();
	private final transient Map<Long, MessageTable> messageIWantToJoinMap = new TreeMap<>();
	private final transient Map<Long, MessageTable> messageOthersMap = new TreeMap<>();

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
	public String getPopupId() {
		return popupId;
	}
	public void setPopupId(String popupId) {
		this.popupId = popupId;
	}
	public boolean isOpenPopup() {
		return openPopup;
	}
	public void setOpenPopup(boolean openPopup) {
		this.openPopup = openPopup;
	}
	public int getMessageTypeValue() {
		return messageTypeValue;
	}
	public void setMessageTypeValue(int messageTypeValue) {
		this.messageTypeValue = messageTypeValue;
	}
	public LinkedHashMap<Integer, String> getTypeRadioMap() {
		return typeRadioMap;
	}
	public String getMessageLabel() {
		return messageLabel;
	}
	public void setMessageLabel(String messageLabel) {
		this.messageLabel = messageLabel;
	}
	public String getSuggestionsLabel() {
		return suggestionsLabel;
	}
	public void setSuggestionsLabel(String suggestionsLabel) {
		this.suggestionsLabel = suggestionsLabel;
	}
	public String getBugsLabel() {
		return bugsLabel;
	}
	public void setBugsLabel(String bugsLabel) {
		this.bugsLabel = bugsLabel;
	}
	public String getQuestionsLabel() {
		return questionsLabel;
	}
	public void setQuestionsLabel(String questionsLabel) {
		this.questionsLabel = questionsLabel;
	}
	public String getiWantToJoinLabel() {
		return iWantToJoinLabel;
	}
	public void setiWantToJoinLabel(String iWantToJoinLabel) {
		this.iWantToJoinLabel = iWantToJoinLabel;
	}
	public String getOthersLabel() {
		return othersLabel;
	}
	public void setOthersLabel(String othersLabel) {
		this.othersLabel = othersLabel;
	}
	public String getMyNameLabel() {
		return myNameLabel;
	}
	public void setMyNameLabel(String myNameLabel) {
		this.myNameLabel = myNameLabel;
	}
	public String getMessageTypeLabel() {
		return messageTypeLabel;
	}
	public void setMessageTypeLabel(String messageTypeLabel) {
		this.messageTypeLabel = messageTypeLabel;
	}
	public String getMyNameValue() {
		return myNameValue;
	}
	public void setMyNameValue(String myNameValue) {
		this.myNameValue = myNameValue;
	}
	public String getMessageValue() {
		return messageValue;
	}
	public void setMessageValue(String messageValue) {
		this.messageValue = messageValue;
	}
	public Map<Long, MessageTable> getMessageBugMap() {
		return messageBugMap;
	}
	public Map<Long, MessageTable> getMessageQuestionMap() {
		return messageQuestionMap;
	}
	public Map<Long, MessageTable> getMessageSuggestionMap() {
		return messageSuggestionMap;
	}
	public Map<Long, MessageTable> getMessageIWantToJoinMap() {
		return messageIWantToJoinMap;
	}
	public Map<Long, MessageTable> getMessageOthersMap() {
		return messageOthersMap;
	}
	public String getUserPasswordLabel() {
		return userPasswordLabel;
	}
	public void setUserPasswordLabel(String userPasswordLabel) {
		this.userPasswordLabel = userPasswordLabel;
	}
	public String getUserPasswordValue() {
		return userPasswordValue;
	}
	public void setUserPasswordValue(String userPasswordValue) {
		this.userPasswordValue = userPasswordValue;
	}
	public String getWrongPasswordMessage() {
		return wrongPasswordMessage;
	}
	public void setWrongPasswordMessage(String wrongPasswordMessage) {
		this.wrongPasswordMessage = wrongPasswordMessage;
	}
}