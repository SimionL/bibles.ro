package beans;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.web.multipart.MultipartFile;

import dbBeans.ChurchTable;
import dbBeans.Participant;

public final class Church {

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

	private final List<Participant> participantsList = new LinkedList<>();
	private final Set<Integer> participantsOrderList = new TreeSet<>();
	private final List<String> participantDuration = new LinkedList<>();
	private final List<String> duration = new LinkedList<>();
	private final List<String> events = new LinkedList<>();
	private final List<String> churches = new LinkedList<>();
	private List<MultipartFile> participantResources;

	private String username;
	private String password;
	private String churchEmail;
	private String churchEmailPassword;
	private String displayChurch="event";
	private String error;
	private String eventDescription;
	private String successful;
	private String eventDate;
	private String eventHour;
	private String selectedEvent = "";
	private String selectedChurch = "";
	private String addUserButton;
	private String selectedDuration;
	private String eventPasswordValue;
	private String participantNameValue;
	private String participantForenameValue;
	private String participantDescriptionValue;
	private String participantPhoneValue;
	private String participantEmailValue;
	private String participantDurationValue;
	private String eventNameValue = "";
	private String formFontSelected = "3";
	private String invitationEmailValue;
	private String notOkEmail;
	private String okEmail;
	private transient String identifiedWords = "";
	private transient String languageCode;

	private String invitationEmailLabel;
	private String emailTitleLabel;
	private String emailContentLabel;
	private String eventDescriptionLabel;
	private String emailContentValue;
	private String emailTitleValue; 
	private int totalParticipantTime;
	private long participantId;

	private ChurchTable  user;

	private transient boolean openPopup;
	private transient boolean usingVoice;

	private String churchEmailLabel;
	private String churchEmailPasswordLabel;
	private String usernameLabel;
	private String passwordLabel;
	private String event;
	private String account;
	private String login;
	private String createAccount;
	private String deleteAccount;
	private String updateAccount;
	private String logout;
	private String successfullLogout;
	private String failureLogin;
	private String successfullDelete;
	private String errorDelete;
	private String successfullUpdated;
	private String errorUpdated;
	private String createEvent;
	private String updateEvent;
	private String deleteEvent;
	private String eventNameLabel;
	private String eventDateLabel;
	private String successfullEventCreated;
	private String eventCreationError;
	private String eventsLabel;
	private String churchesLabel;
	private String noEventLabel;
	private String noChurchLabel;
	private String failurEventUpdate;
	private String successfullEventUpdate;
	private String failurEventDelete;
	private String successfullEventDelete;
	private String eventDuration;
	private String minutes;
	private String participantName;
	private String participantForename;
	private String durationParticipantLabel;
	private String resources;
	private String participantPhone;
	private String participantEmail;
	private String eventPasswordLabel;
	private String okParticipantAdded;
	private String errorParticipantAdded;
	private String errorParticipantDeleted;
	private String okParticipantDeleted;
	private String wrongPassword;
	private String wrongFileSize;
	private String participantMessage;
	private String order;
	private String totalParticipantTimeLabel;
	private String downloadAttachments;
	private String sendInvitation;
	private String accessInvitation;
	private String successfulLogin;
	private String usernameError;
	private String existEmailError;
	private String invalidEmailFormat;
	private String successfulInsertion;

	private String eventId;

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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsernameLabel() {
		return usernameLabel;
	}
	public void setUsernameLabel(String usernameLabel) {
		this.usernameLabel = usernameLabel;
	}
	public String getPasswordLabel() {
		return passwordLabel;
	}
	public void setPasswordLabel(String passwordLabel) {
		this.passwordLabel = passwordLabel;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSelectedEvent() {
		return selectedEvent;
	}
	public void setSelectedEvent(String selectedEvent) {
		this.selectedEvent = selectedEvent;
	}
	public List<String> getEvents() {
		return events;
	}
	public List<String> getChurches() {
		return churches;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCreateAccount() {
		return createAccount;
	}
	public void setCreateAccount(String createAccount) {
		this.createAccount = createAccount;
	}
	public String getDeleteAccount() {
		return deleteAccount;
	}
	public void setDeleteAccount(String deleteAccount) {
		this.deleteAccount = deleteAccount;
	}
	public String getUpdateAccount() {
		return updateAccount;
	}
	public void setUpdateAccount(String updateAccount) {
		this.updateAccount = updateAccount;
	}
	public String getLogout() {
		return logout;
	}
	public void setLogout(String logout) {
		this.logout = logout;
	}
	public String getDisplayChurch() {
		return displayChurch;
	}
	public void setDisplayChurch(String displayChurch) {
		this.displayChurch = displayChurch;
	}
	public String getChurchEmail() {
		return churchEmail;
	}
	public void setChurchEmail(String churchEmail) {
		this.churchEmail = churchEmail;
	}
	public String getChurchEmailPassword() {
		return churchEmailPassword;
	}
	public void setChurchEmailPassword(String churchEmailPassword) {
		this.churchEmailPassword = churchEmailPassword;
	}
	public String getChurchEmailLabel() {
		return churchEmailLabel;
	}
	public void setChurchEmailLabel(String churchEmailLabel) {
		this.churchEmailLabel = churchEmailLabel;
	}
	public String getChurchEmailPasswordLabel() {
		return churchEmailPasswordLabel;
	}
	public void setChurchEmailPasswordLabel(String churchEmailPasswordLabel) {
		this.churchEmailPasswordLabel = churchEmailPasswordLabel;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getSuccessful() {
		return successful;
	}
	public void setSuccessful(String successful) {
		this.successful = successful;
	}
	public String getFailureLogin() {
		return failureLogin;
	}
	public void setFailureLogin(String failureLogin) {
		this.failureLogin = failureLogin;
	}
	public ChurchTable getUser() {
		return user;
	}
	public void setUser(ChurchTable user) {
		this.user = user;
	}
	public String getSuccessfullLogout() {
		return successfullLogout;
	}
	public void setSuccessfullLogout(String successfullLogout) {
		this.successfullLogout = successfullLogout;
	}
	public String getSuccessfullDelete() {
		return successfullDelete;
	}
	public void setSuccessfullDelete(String successfullDelete) {
		this.successfullDelete = successfullDelete;
	}
	public String getErrorDelete() {
		return errorDelete;
	}
	public void setErrorDelete(String errorDelete) {
		this.errorDelete = errorDelete;
	}
	public String getSuccessfullUpdated() {
		return successfullUpdated;
	}
	public void setSuccessfullUpdated(String successfullUpdated) {
		this.successfullUpdated = successfullUpdated;
	}
	public String getErrorUpdated() {
		return errorUpdated;
	}
	public void setErrorUpdated(String errorUpdated) {
		this.errorUpdated = errorUpdated;
	}
	public String getCreateEvent() {
		return createEvent;
	}
	public void setCreateEvent(String createEvent) {
		this.createEvent = createEvent;
	}

	public String getEventNameLabel() {
		return eventNameLabel;
	}
	public void setEventNameLabel(String eventNameLabel) {
		this.eventNameLabel = eventNameLabel;
	}
	public String getEventNameValue() {
		return eventNameValue;
	}
	public void setEventNameValue(String eventNameValue) {
		this.eventNameValue = eventNameValue;
	}
	public String getSuccessfullEventCreated() {
		return successfullEventCreated;
	}
	public void setSuccessfullEventCreated(String successfullEventCreated) {
		this.successfullEventCreated = successfullEventCreated;
	}
	public String getEventCreationError() {
		return eventCreationError;
	}
	public void setEventCreationError(String eventCreationError) {
		this.eventCreationError = eventCreationError;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public String getEventsLabel() {
		return eventsLabel;
	}
	public void setEventsLabel(String eventsLabel) {
		this.eventsLabel = eventsLabel;
	}
	public String getUpdateEvent() {
		return updateEvent;
	}
	public void setUpdateEvent(String updateEvent) {
		this.updateEvent = updateEvent;
	}
	public String getDeleteEvent() {
		return deleteEvent;
	}
	public void setDeleteEvent(String deleteEvent) {
		this.deleteEvent = deleteEvent;
	}
	public String getFailurEventUpdate() {
		return failurEventUpdate;
	}
	public void setFailurEventUpdate(String failurEventUpdate) {
		this.failurEventUpdate = failurEventUpdate;
	}
	public String getSuccessfullEventUpdate() {
		return successfullEventUpdate;
	}
	public void setSuccessfullEventUpdate(String successfullEventUpdate) {
		this.successfullEventUpdate = successfullEventUpdate;
	}
	public String getFailurEventDelete() {
		return failurEventDelete;
	}
	public void setFailurEventDelete(String failurEventDelete) {
		this.failurEventDelete = failurEventDelete;
	}
	public String getSuccessfullEventDelete() {
		return successfullEventDelete;
	}
	public void setSuccessfullEventDelete(String successfullEventDelete) {
		this.successfullEventDelete = successfullEventDelete;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getEventDateLabel() {
		return eventDateLabel;
	}
	public void setEventDateLabel(String eventDateLabel) {
		this.eventDateLabel = eventDateLabel;
	}
	public String getEventHour() {
		return eventHour;
	}
	public void setEventHour(String eventHour) {
		this.eventHour = eventHour;
	}
	public String getSelectedChurch() {
		return selectedChurch;
	}
	public void setSelectedChurch(String selectedChurch) {
		this.selectedChurch = selectedChurch;
	}
	public String getChurchesLabel() {
		return churchesLabel;
	}
	public void setChurchesLabel(String churchesLabel) {
		this.churchesLabel = churchesLabel;
	}
	public String getNoEventLabel() {
		return noEventLabel;
	}
	public void setNoEventLabel(String noEventLabel) {
		this.noEventLabel = noEventLabel;
	}
	public String getNoChurchLabel() {
		return noChurchLabel;
	}
	public void setNoChurchLabel(String noChurchLabel) {
		this.noChurchLabel = noChurchLabel;
	}
	public String getAddUserButton() {
		return addUserButton;
	}
	public void setAddUserButton(String addUserButton) {
		this.addUserButton = addUserButton;
	}
	public String getSelectedDuration() {
		return selectedDuration;
	}
	public void setSelectedDuration(String selectedDuration) {
		this.selectedDuration = selectedDuration;
	}
	public List<String> getDuration() {
		return duration;
	}
	public String getEventDuration() {
		return eventDuration;
	}
	public void setEventDuration(String eventDuration) {
		this.eventDuration = eventDuration;
	}
	public String getMinutes() {
		return minutes;
	}
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	public String getParticipantName() {
		return participantName;
	}
	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}
	public String getParticipantForename() {
		return participantForename;
	}
	public void setParticipantForename(String participantForename) {
		this.participantForename = participantForename;
	}
	public String getDurationParticipantLabel() {
		return durationParticipantLabel;
	}
	public void setDurationParticipantLabel(String durationParticipantLabel) {
		this.durationParticipantLabel = durationParticipantLabel;
	}
	public String getResources() {
		return resources;
	}
	public void setResources(String resources) {
		this.resources = resources;
	}
	public String getParticipantPhone() {
		return participantPhone;
	}
	public void setParticipantPhone(String participantPhone) {
		this.participantPhone = participantPhone;
	}
	public String getParticipantEmail() {
		return participantEmail;
	}
	public void setParticipantEmail(String participantEmail) {
		this.participantEmail = participantEmail;
	}
	public String getParticipantNameValue() {
		return participantNameValue;
	}
	public void setParticipantNameValue(String participantNameValue) {
		this.participantNameValue = participantNameValue;
	}
	public String getParticipantForenameValue() {
		return participantForenameValue;
	}
	public void setParticipantForenameValue(String participantForenameValue) {
		this.participantForenameValue = participantForenameValue;
	}
	public List<String> getParticipantDuration() {
		return participantDuration;
	}

	public List<MultipartFile> getParticipantResources() {
		return participantResources;
	}
	public void setParticipantResources(List<MultipartFile> participantResources) {
		this.participantResources = participantResources;
	}
	public String getParticipantDescriptionValue() {
		return participantDescriptionValue;
	}
	public void setParticipantDescriptionValue(String participantDescriptionValue) {
		this.participantDescriptionValue = participantDescriptionValue;
	}
	public String getParticipantPhoneValue() {
		return participantPhoneValue;
	}
	public void setParticipantPhoneValue(String participantPhoneValue) {
		this.participantPhoneValue = participantPhoneValue;
	}
	public String getParticipantEmailValue() {
		return participantEmailValue;
	}
	public void setParticipantEmailValue(String participantEmailValue) {
		this.participantEmailValue = participantEmailValue;
	}
	public String getParticipantDurationValue() {
		return participantDurationValue;
	}
	public void setParticipantDurationValue(String participantDurationValue) {
		this.participantDurationValue = participantDurationValue;
	}
	public String getEventPasswordValue() {
		return eventPasswordValue;
	}
	public void setEventPasswordValue(String eventPasswordValue) {
		this.eventPasswordValue = eventPasswordValue;
	}
	public String getEventPasswordLabel() {
		return eventPasswordLabel;
	}
	public void setEventPasswordLabel(String eventPasswordLabel) {
		this.eventPasswordLabel = eventPasswordLabel;
	}
	public String getOkParticipantAdded() {
		return okParticipantAdded;
	}
	public void setOkParticipantAdded(String okParticipantAdded) {
		this.okParticipantAdded = okParticipantAdded;
	}
	public String getErrorParticipantAdded() {
		return errorParticipantAdded;
	}
	public void setErrorParticipantAdded(String errorParticipantAdded) {
		this.errorParticipantAdded = errorParticipantAdded;
	}
	public String getWrongPassword() {
		return wrongPassword;
	}
	public void setWrongPassword(String wrongPassword) {
		this.wrongPassword = wrongPassword;
	}
	public String getWrongFileSize() {
		return wrongFileSize;
	}
	public void setWrongFileSize(String wrongFileSize) {
		this.wrongFileSize = wrongFileSize;
	}
	public List<Participant> getParticipantsList() {
		return participantsList;
	}
	public String getParticipantMessage() {
		return participantMessage;
	}
	public void setParticipantMessage(String participantMessage) {
		this.participantMessage = participantMessage;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public int getTotalParticipantTime() {
		return totalParticipantTime;
	}
	public void setTotalParticipantTime(int totalParticipantTime) {
		this.totalParticipantTime = totalParticipantTime;
	}
	public String getTotalParticipantTimeLabel() {
		return totalParticipantTimeLabel;
	}
	public void setTotalParticipantTimeLabel(String totalParticipantTimeLabel) {
		this.totalParticipantTimeLabel = totalParticipantTimeLabel;
	}

	public Set<Integer> getParticipantsOrderList() {
		return participantsOrderList;
	}
	public long getParticipantId() {
		return participantId;
	}
	public void setParticipantId(long participantId) {
		this.participantId = participantId;
	}
	public String getFormFontSelected() {
		return formFontSelected;
	}
	public void setFormFontSelected(String formFontSelected) {
		this.formFontSelected = formFontSelected;
	}
	public String getErrorParticipantDeleted() {
		return errorParticipantDeleted;
	}
	public void setErrorParticipantDeleted(String errorParticipantDeleted) {
		this.errorParticipantDeleted = errorParticipantDeleted;
	}
	public String getOkParticipantDeleted() {
		return okParticipantDeleted;
	}
	public void setOkParticipantDeleted(String okParticipantDeleted) {
		this.okParticipantDeleted = okParticipantDeleted;
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
	public boolean isOpenPopup() {
		return openPopup;
	}
	public void setOpenPopup(boolean openPopup) {
		this.openPopup = openPopup;
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
	public String getDownloadAttachments() {
		return downloadAttachments;
	}
	public void setDownloadAttachments(String downloadAttachments) {
		this.downloadAttachments = downloadAttachments;
	}
	public String getSendInvitation() {
		return sendInvitation;
	}
	public void setSendInvitation(String sendInvitation) {
		this.sendInvitation = sendInvitation;
	}
	public String getInvitationEmailValue() {
		return invitationEmailValue;
	}
	public void setInvitationEmailValue(String invitationEmailValue) {
		this.invitationEmailValue = invitationEmailValue;
	}
	public String getEmailTitleValue() {
		return emailTitleValue;
	}
	public void setEmailTitleValue(String emailTitleValue) {
		this.emailTitleValue = emailTitleValue;
	}
	public String getEmailContentValue() {
		return emailContentValue;
	}
	public void setEmailContentValue(String emailContentValue) {
		this.emailContentValue = emailContentValue;
	}
	public String getInvitationEmailLabel() {
		return invitationEmailLabel;
	}
	public void setInvitationEmailLabel(String invitationEmailLabel) {
		this.invitationEmailLabel = invitationEmailLabel;
	}
	public String getEmailTitleLabel() {
		return emailTitleLabel;
	}
	public void setEmailTitleLabel(String emailTitleLabel) {
		this.emailTitleLabel = emailTitleLabel;
	}
	public String getEmailContentLabel() {
		return emailContentLabel;
	}
	public void setEmailContentLabel(String emailContentLabel) {
		this.emailContentLabel = emailContentLabel;
	}
	public String getEventDescriptionLabel() {
		return eventDescriptionLabel;
	}
	public void setEventDescriptionLabel(String eventDescriptionLabel) {
		this.eventDescriptionLabel = eventDescriptionLabel;
	}
	public String getNotOkEmail() {
		return notOkEmail;
	}
	public void setNotOkEmail(String notOkEmail) {
		this.notOkEmail = notOkEmail;
	}
	public String getOkEmail() {
		return okEmail;
	}
	public void setOkEmail(String okEmail) {
		this.okEmail = okEmail;
	}
	public String getAccessInvitation() {
		return accessInvitation;
	}
	public void setAccessInvitation(String accessInvitation) {
		this.accessInvitation = accessInvitation;
	}
	public String getSuccessfulLogin() {
		return successfulLogin;
	}
	public void setSuccessfulLogin(String successfulLogin) {
		this.successfulLogin = successfulLogin;
	}
	public String getUsernameError() {
		return usernameError;
	}
	public void setUsernameError(String usernameError) {
		this.usernameError = usernameError;
	}
	public String getExistEmailError() {
		return existEmailError;
	}
	public void setExistEmailError(String existEmailError) {
		this.existEmailError = existEmailError;
	}
	public String getInvalidEmailFormat() {
		return invalidEmailFormat;
	}
	public void setInvalidEmailFormat(String invalidEmailFormat) {
		this.invalidEmailFormat = invalidEmailFormat;
	}
	public String getSuccessfulInsertion() {
		return successfulInsertion;
	}
	public void setSuccessfulInsertion(String successfulInsertion) {
		this.successfulInsertion = successfulInsertion;
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