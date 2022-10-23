package beans;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import utilities.Message;

public final class Settings{

	private String bible;
	private String settings;
	private String popup;
	private String reference;
	private String church;
	private String screensaver;
	private String code;
	private String voice;
	private String feedback;
	private String thankYou;

	private String eventId;
	private final TreeSet<String> languages = new TreeSet<>();
	private final List<Message> messages = new LinkedList<>();
	private final LinkedHashSet<String> formFontList = new LinkedHashSet<>();
	private final LinkedHashSet<String> searchBlockLengthSelectionList = new LinkedHashSet<>();
	private final LinkedHashMap<Integer, String> searchAreaOptions = new LinkedHashMap<>();
	private final LinkedHashMap<Integer, String> emailFromMap = new LinkedHashMap<>();

	private String displayReferencesLabel;
	private String wordWrapLabel;
	private String displayEntireChapterLabel;
	private String highlightsTextLabel;
	private String formFontLabel;
	private String selectedLanguage;
	private String selectLanguage;
	private String formFontSelected = "3";
	private String searchBlockLength;
	private String searchBlockLengthSelection = "1";
	private String inexactColorsLabel;
	private String exactColorsLabel;
	private String exactColorPaletteSelected = "#00ca33";
	private String inexactColorPaletteSelected = "#d20000";
	private String searchLevel;
	private String error;
	private String ok;
	private String identifiedWords = "";
	private String languageCode;

	private String automatMessage = "Automat send message";
	private String addMessage = "Add";
	private String addPlaceholder = "email or phone are mandatory";
	private String namePlaceholder = "name or alias are optional";
	private String titlePlaceholder = "message title is optional";
	private String contentPlaceholder = "message content is optional";
	private String passwordSaved = "Password saved!";
	private String emailSaved = "Email saved!";
	private String added = "Message was successfully added!";
	private String userServerEmailPlaceholder = "user gmail server address";
	private String userServerPasswordPlaceholder = "user server password";
	private String invalidFormat = "Invalid format!";
	private String messageTitleAdded = "Message title added!";
	private String messageContentAdded = "Message content added!";
	private String defaultMessageTitle = "message from www.bibles.ro";
	private String defaultMessageContent = "A www.bibles.ro user is sending you this verse:";
	private String messageTitleLabel = "Title";
	private String messageContentLabel = "Content";
	private String phone = "Phone";
	private String email = "Email";
	private String name = "Name";
	private String nameChanged = "Name changed!";


	private String message;
	private String newAddress;
	private String selectedMessageId;
	private String messageTitleValue;
	private String messageContentValue;
	private String selectedMessage;
	private String userEmail;
	private String userPassword;
	private String messagesEncapsulation;
	private String nameValue;

	private boolean openPopup;
	private boolean usingVoice;
	private boolean wordWrap;
	private boolean highlights;
	private boolean displayReference;
	private boolean displayEntireChapter = true;
	private boolean automatSendMessage;
	private boolean saveMessageSettings;
	private boolean selelctAll = true;

	private int searchLevelSelected = 1;
	private int emailFrom = 1;

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

	public List<Message> getMessages() {
		return messages;
	}

	public String getSelectedMessageId() {
		return selectedMessageId;
	}

	public void setSelectedMessageId(String selectedMessageId) {
		this.selectedMessageId = selectedMessageId;
	}

	public String getAutomatMessage() {
		return automatMessage;
	}

	public void setAutomatMessage(String automatMessage) {
		this.automatMessage = automatMessage;
	}

	public boolean isAutomatSendMessage() {
		return automatSendMessage;
	}

	public void setAutomatSendMessage(boolean automatSendMessage) {
		this.automatSendMessage = automatSendMessage;
	}

	public String getMessageTitleLabel() {
		return messageTitleLabel;
	}

	public void setMessageTitleLabel(String messageTitleLabel) {
		this.messageTitleLabel = messageTitleLabel;
	}

	public String getMessageContentLabel() {
		return messageContentLabel;
	}

	public void setMessageContentLabel(String messageContentLabel) {
		this.messageContentLabel = messageContentLabel;
	}

	public String getAddMessage() {
		return addMessage;
	}

	public void setAddMessage(String addMessage) {
		this.addMessage = addMessage;
	}

	public String getSelectedMessage() {
		return selectedMessage;
	}

	public void setSelectedMessage(String selectedMessage) {
		this.selectedMessage = selectedMessage;
	}

	public String getInvalidFormat() {
		return invalidFormat;
	}

	public void setInvalidFormat(String invalidFormat) {
		this.invalidFormat = invalidFormat;
	}

	public int getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(int emailFrom) {
		this.emailFrom = emailFrom;
	}

	public LinkedHashMap<Integer, String> getEmailFromMap() {
		return emailFromMap;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserServerEmailPlaceholder() {
		return userServerEmailPlaceholder;
	}

	public void setUserServerEmailPlaceholder(String userServerEmailPlaceholder) {
		this.userServerEmailPlaceholder = userServerEmailPlaceholder;
	}

	public String getUserServerPasswordPlaceholder() {
		return userServerPasswordPlaceholder;
	}

	public void setUserServerPasswordPlaceholder(String userServerPasswordPlaceholder) {
		this.userServerPasswordPlaceholder = userServerPasswordPlaceholder;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getPasswordSaved() {
		return passwordSaved;
	}

	public void setPasswordSaved(String passwordSaved) {
		this.passwordSaved = passwordSaved;
	}

	public String getEmailSaved() {
		return emailSaved;
	}

	public void setEmailSaved(String emailSaved) {
		this.emailSaved = emailSaved;
	}

	public String getMessageTitleAdded() {
		return messageTitleAdded;
	}

	public void setMessageTitleAdded(String messageTitleAdded) {
		this.messageTitleAdded = messageTitleAdded;
	}

	public String getMessageContentAdded() {
		return messageContentAdded;
	}

	public void setMessageContentAdded(String messageContentAdded) {
		this.messageContentAdded = messageContentAdded;
	}

	public String getDefaultMessageTitle() {
		return defaultMessageTitle;
	}

	public void setDefaultMessageTitle(String defaultMessageTitle) {
		this.defaultMessageTitle = defaultMessageTitle;
	}

	public String getDefaultMessageContent() {
		return defaultMessageContent;
	}

	public void setDefaultMessageContent(String defaultMessageContent) {
		this.defaultMessageContent = defaultMessageContent;
	}

	public String getMessagesEncapsulation() {
		return messagesEncapsulation;
	}

	public void setMessagesEncapsulation(String messagesEncapsulation) {
		this.messagesEncapsulation = messagesEncapsulation;
	}

	public boolean isSaveMessageSettings() {
		return saveMessageSettings;
	}

	public void setSaveMessageSettings(boolean saveMessageSettings) {
		this.saveMessageSettings = saveMessageSettings;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessageTitleValue() {
		return messageTitleValue;
	}

	public void setMessageTitleValue(String messageTitleValue) {
		this.messageTitleValue = messageTitleValue;
	}

	public String getMessageContentValue() {
		return messageContentValue;
	}

	public void setMessageContentValue(String messageContentValue) {
		this.messageContentValue = messageContentValue;
	}

	public String getAdded() {
		return added;
	}

	public void setAdded(String added) {
		this.added = added;
	}

	public String getAddPlaceholder() {
		return addPlaceholder;
	}

	public void setAddPlaceholder(String addPlaceholder) {
		this.addPlaceholder = addPlaceholder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameValue() {
		return nameValue;
	}

	public void setNameValue(String nameValue) {
		this.nameValue = nameValue;
	}

	public String getNameChanged() {
		return nameChanged;
	}

	public void setNameChanged(String nameChanged) {
		this.nameChanged = nameChanged;
	}

	public String getNamePlaceholder() {
		return namePlaceholder;
	}

	public void setNamePlaceholder(String namePlaceholder) {
		this.namePlaceholder = namePlaceholder;
	}

	public String getTitlePlaceholder() {
		return titlePlaceholder;
	}

	public void setTitlePlaceholder(String titlePlaceholder) {
		this.titlePlaceholder = titlePlaceholder;
	}

	public String getContentPlaceholder() {
		return contentPlaceholder;
	}

	public void setContentPlaceholder(String contentPlaceholder) {
		this.contentPlaceholder = contentPlaceholder;
	}

	public String getNewAddress() {
		return newAddress;
	}

	public void setNewAddress(String newAddress) {
		this.newAddress = newAddress;
	}

	public boolean isSelelctAll() {
		return selelctAll;
	}

	public void setSelelctAll(boolean selelctAll) {
		this.selelctAll = selelctAll;
	}
}