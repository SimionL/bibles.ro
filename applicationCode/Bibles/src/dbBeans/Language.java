package dbBeans;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Entity(name = "language")
@Table(name = "language", uniqueConstraints = {@UniqueConstraint(columnNames = "value")})
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class Language implements Serializable {

	private static final long serialVersionUID = -4389469588921394716L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "language_id", nullable = false)
	private long languageId;

	@Column(name = "language_code")
	private String languageCode;

	@Column(name = "value")
	private String value;

	@Column(name = "select_language")
	private String selectLanguage;

	@Column(name = "select_version")
	private String selectVersion;

	@Column(name = "select_book")
	private String selectBook;

	@Column(name = "select_chapter")
	private String selectChapter;

	@Column(name = "select_verse")
	private String selectVerse;

	@Column(name = "no_version")
	private String noVersion;

	@Column(name = "no_book")
	private String noBook;

	@Column(name = "no_chapter")
	private String noChapter;

	@Column(name = "no_verse")
	private String noVerse;

	@Column(name = "bible")
	private String bible;

	@Column(name = "settings")
	private String settings;

	@Column(name = "church")
	private String church;

	@Column(name = "no_next_verse")
	private String noNextVerse;

	@Column(name = "no_back_verse")
	private String noBackVerse;

	@Column(name = "no_verse_selected")
	private String noVerseSelected;

	@Column(name = "verse_references")
	private String references;

	@Column(name = "display_references")
	private String displayReferences;

	@Column(name = "no_result")
	private String noResult;

	@Column(name = "word_wrap")
	private String wordWrap;

	@Column(name = "display_entire_chapter")
	private String displayEntireChapter;

	@Column(name = "highlights_text")
	private String highlightsText;

	@Column(name = "search_block_length")
	private String searchBlockLength;

	@Column(name = "inexact_colors")
	private String inexactColors;

	@Column(name = "exact_colors")
	private String exactColors;

	@Column(name = "search_history")
	private String searchHistory;

	@Column(name = "popup_margin_top")
	private String popupMarginTop;

	@Column(name = "popup_margin_bottom")
	private String popupMarginBottom;

	@Column(name = "popup_margin_right")
	private String popupMarginRight;

	@Column(name = "popup_margin_left")
	private String popupMarginLeft;

	@Column(name = "popup_text_align")
	private String popupTextAlign;

	@Column(name = "popup_background_color")
	private String popupBackgroundColor;

	@Column(name = "popup_user_message")
	private String popupUserMessage;

	@Column(name = "popup_text_color")
	private String popupTextColor;

	@Column(name = "scripture_text")
	private String radioScriptureText;

	@Column(name = "picture")
	private String picture;

	@Column(name = "user_message")
	private String radioUserMessage;

	@Column(name = "left_alignment")
	private String left;

	@Column(name = "right_alignment")
	private String right;

	@Column(name = "center_alignment")
	private String center;

	@Column(name = "justify_alignment")
	private String justify;

	@Column(name = "initial_alignment")
	private String initial;

	@Column(name = "inherit_alignment")
	private String inherit;

	@Column(name = "wrong_file_size")
	private String wrongFileSize;

	@Column(name = "wrong_format")
	private String wrongFormat;

	@Column(name = "wrong_files")
	private String wrongFiles;

	@Column(name = "screensaver")
	private String screensaver;

	@Column(name = "all_categories")
	private String allCategories;

	@Column(name = "angels")
	private String angels;

	@Column(name = "clouds")
	private String clouds;

	@Column(name = "crosses")
	private String cross;

	@Column(name = "Christ")
	private String Christ;

	@Column(name = "the_others")
	private String others;

	@Column(name = "pigeons")
	private String pigeons;

	@Column(name = "screensaver_image")
	private String screensaverImageRadio;

	@Column(name = "screensaver_verse")
	private String screensaverVerseRadio;

	@Column(name = "screensaver_both")
	private String screensaverBothRadio;

	@Column(name = "screensaver_bible")
	private String screensaverBibleSelection;

	@Column(name = "no_version_selected")
	private String noVersionSelected;

	@Column(name = "automat_screensaver")
	private String automatScreensaver;

	@Column(name = "manual_screensaver")
	private String manualScreensaver;

	@Column(name = "display_version_label")
	private String displayVersionLabel;

	@Column(name = "display_book_label")
	private String displayBookLabel;

	@Column(name = "display_chapter_label")
	private String displayChapterLabel;

	@Column(name = "display_verse_label")
	private String displayVerseLabel;

	@Column(name = "bold")
	private String bold;

	@Column(name = "enter_up")
	private String enterUpPopup;

	@Column(name = "enter_down")
	private String enterDownPopup;

	@Column(name = "reference_words_space")
	private String referenceWordsSpacePopup;

	@Column(name = "reference_schema")
	private String schema;

	@Column(name = "alignment")
	private String alignment;

	@Column(name = "font")
	private String font;

	@Column(name = "background_color")
	private String backgroundColor;

	@Column(name = "font_family_popup")
	private String fontFamilyPopup;

	@Column(name = "font_style_popup")
	private String fontStylePopup;

	@Column(name = "letter_spacing_popup")
	private String letterSpacingPopup;

	@Column(name = "line_height_popup")
	private String lineHeightPopup;

	@Column(name = "word_spacing_popup")
	private String wordSpacingPopup;

	@Column(name = "default_popup_image")
	private String defaultPopupImage;

	@Column(name = "reference_letter_spacing_popup")
	private String referenceLetterSpacingPopup;

	@Column(name = "reference_font_family_popup")
	private String referenceFontFamilyPopup;

	@Column(name = "reference_font_style_popup")
	private String referenceFontStylePopup;

	@Column(name = "reference_font_decoration_popup")
	private String referenceFontDecorationPopup;

	@Column(name = "application_code")
	private String code;

	@Column(name = "popup")
	private String popup;

	@Column(name = "verse_reference")
	private String reference;

	@Column(name = "image_opacity")
	private String imageOpacity;

	@Column(name = "text_opacity")
	private String textOpacity;

	@Column(name = "selected_version")
	private String selectedVersion;

	@Column(name = "selected_book")
	private String selectedBook;

	@Column(name = "selected_chapter")
	private String selectedChapter;

	@Column(name = "current_selection")
	private String currentSelection;

	@Column(name = "search_level")
	private String searchLevel;

	@Column(name = "not_ok_email")
	private String notOkEmail;

	@Column(name = "ok_email")
	private String okEmail;

	@Column(name = "invitation_email")
	private String invitationEmail;

	@Column(name = "email_title")
	private String emailTitle;

	@Column(name = "email_content")
	private String emailContent;

	@Column(name = "event_description")
	private String eventDescription;

	@Column(name = "email_content_value")
	private String emailContentValue;

	@Column(name = "email_title_value")
	private String emailTitleValue;

	@Column(name = "church_email")
	private String churchEmail;

	@Column(name = "church_email_password")
	private String churchEmailPassword;

	@Column(name = "username")
	private String username;

	@Column(name = "church_password")
	private String password;

	@Column(name = "the_event")
	private String event;

	@Column(name = "account")
	private String account;

	@Column(name = "login")
	private String login;

	@Column(name = "create_account")
	private String createAccount;

	@Column(name = "delete_account")
	private String deleteAccount;

	@Column(name = "update_account")
	private String updateAccount;

	@Column(name = "logout")
	private String logout;

	@Column(name = "successfull_logout")
	private String successfullLogout;

	@Column(name = "failure_login")
	private String failureLogin;

	@Column(name = "successfull_delete")
	private String successfullDelete;

	@Column(name = "error_delete")
	private String errorDelete;

	@Column(name = "successfull_updated")
	private String successfullUpdated;

	@Column(name = "error_updated")
	private String errorUpdated;

	@Column(name = "create_event")
	private String createEvent;

	@Column(name = "update_event")
	private String updateEvent;

	@Column(name = "delete_event")
	private String deleteEvent;

	@Column(name = "event_name")
	private String eventName;

	@Column(name = "event_date")
	private String eventDate;

	@Column(name = "successfull_event_created")
	private String successfullEventCreated;

	@Column(name = "event_creation_error")
	private String eventCreationError;

	@Column(name = "events_label")
	private String eventsLabel;

	@Column(name = "churches_label")
	private String churchesLabel;

	@Column(name = "no_event_label")
	private String noEventLabel;

	@Column(name = "no_church_label")
	private String noChurchLabel;

	@Column(name = "failur_event_update")
	private String failurEventUpdate;

	@Column(name = "successfull_event_update")
	private String successfullEventUpdate;

	@Column(name = "failur_event_delete")
	private String failurEventDelete;

	@Column(name = "successfull_event_delete")
	private String successfullEventDelete;

	@Column(name = "event_duration")
	private String eventDuration;

	@Column(name = "event_minutes")
	private String minutes;

	@Column(name = "participant_name")
	private String participantName;

	@Column(name = "participant_forename")
	private String participantForename;

	@Column(name = "duration_participant")
	private String durationParticipant;

	@Column(name = "resources")
	private String resources;

	@Column(name = "participant_phone")
	private String participantPhone;

	@Column(name = "participant_email")
	private String participantEmail;

	@Column(name = "event_password")
	private String eventPassword;

	@Column(name = "ok_participant_added")
	private String okParticipantAdded;

	@Column(name = "error_participant_added")
	private String errorParticipantAdded;

	@Column(name = "error_participant_deleted")
	private String errorParticipantDeleted;

	@Column(name = "ok_participant_deleted")
	private String okParticipantDeleted;

	@Column(name = "wrong_password")
	private String wrongPassword;

	@Column(name = "message")
	private String message;

	@Column(name = "participant_order")
	private String order;

	@Column(name = "total_participant_time")
	private String totalParticipantTime;

	@Column(name = "download_attachments")
	private String downloadAttachments;

	@Column(name = "send_invitation")
	private String sendInvitation;

	@Column(name = "access_invitation")
	private String accessInvitation;

	@Column(name = "successful_login")
	private String successfulLogin;

	@Column(name = "username_error")
	private String usernameError;

	@Column(name = "exist_email_error")
	private String existEmailError;

	@Column(name = "invalid_email_format")
	private String invalidEmailFormat;

	@Column(name = "successful_insertion")
	private String successfulChurchCreation;

	@Column(name = "voice")
	private String voice;

	@Column(name = "use_voice")
	private String useVoice;

	@Column(name = "thank_you_message")
	private String thankYouMessage;

	@Column(name = "thank_you")
	private String thankYou;

	@Column(name = "feedback")
	private String feedback;

	@Column(name = "suggestions")
	private String suggestions;

	@Column(name = "bugs")
	private String bugs;

	@Column(name = "questions")
	private String questions;

	@Column(name = "suggestion")
	private String suggestion;

	@Column(name = "bug")
	private String bug;

	@Column(name = "question")
	private String question;

	@Column(name = "i_want_to_join")
	private String iWantToJoin;

	@Column(name = "my_name")
	private String myName;

	@Column(name = "message_type")
	private String messageType;

	@Column(name = "others")
	private String othersCaps;

	@Column(name = "message_password")
	private String messagePassword;

	@Column(name = "i_want_to_join_caps")
	private String iWantToJoinCaps;

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

	public long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(long languageId) {
		this.languageId = languageId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSelectLanguage() {
		return selectLanguage;
	}

	public void setSelectLanguage(String selectLanguage) {
		this.selectLanguage = selectLanguage;
	}

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public String getDisplayReferences() {
		return displayReferences;
	}

	public void setDisplayReferences(String displayReferences) {
		this.displayReferences = displayReferences;
	}

	public String getNoResult() {
		return noResult;
	}

	public void setNoResult(String noResult) {
		this.noResult = noResult;
	}

	public String getWordWrap() {
		return wordWrap;
	}

	public void setWordWrap(String wordWrap) {
		this.wordWrap = wordWrap;
	}

	public String getDisplayEntireChapter() {
		return displayEntireChapter;
	}

	public void setDisplayEntireChapter(String displayEntireChapter) {
		this.displayEntireChapter = displayEntireChapter;
	}

	public String getHighlightsText() {
		return highlightsText;
	}

	public void setHighlightsText(String highlightsText) {
		this.highlightsText = highlightsText;
	}

	public String getSearchBlockLength() {
		return searchBlockLength;
	}

	public void setSearchBlockLength(String searchBlockLength) {
		this.searchBlockLength = searchBlockLength;
	}

	public String getInexactColors() {
		return inexactColors;
	}

	public void setInexactColors(String inexactColors) {
		this.inexactColors = inexactColors;
	}

	public String getSearchHistory() {
		return searchHistory;
	}

	public void setSearchHistory(String searchHistory) {
		this.searchHistory = searchHistory;
	}

	public String getPopupMarginTop() {
		return popupMarginTop;
	}

	public void setPopupMarginTop(String popupMarginTop) {
		this.popupMarginTop = popupMarginTop;
	}

	public String getPopupMarginBottom() {
		return popupMarginBottom;
	}

	public void setPopupMarginBottom(String popupMarginBottom) {
		this.popupMarginBottom = popupMarginBottom;
	}

	public String getPopupMarginRight() {
		return popupMarginRight;
	}

	public void setPopupMarginRight(String popupMarginRight) {
		this.popupMarginRight = popupMarginRight;
	}

	public String getPopupMarginLeft() {
		return popupMarginLeft;
	}

	public void setPopupMarginLeft(String popupMarginLeft) {
		this.popupMarginLeft = popupMarginLeft;
	}

	public String getPopupTextAlign() {
		return popupTextAlign;
	}

	public void setPopupTextAlign(String popupTextAlign) {
		this.popupTextAlign = popupTextAlign;
	}

	public String getPopupBackgroundColor() {
		return popupBackgroundColor;
	}

	public void setPopupBackgroundColor(String popupBackgroundColor) {
		this.popupBackgroundColor = popupBackgroundColor;
	}

	public String getPopupUserMessage() {
		return popupUserMessage;
	}

	public void setPopupUserMessage(String popupUserMessage) {
		this.popupUserMessage = popupUserMessage;
	}

	public String getPopupTextColor() {
		return popupTextColor;
	}

	public void setPopupTextColor(String popupTextColor) {
		this.popupTextColor = popupTextColor;
	}

	public String getRadioScriptureText() {
		return radioScriptureText;
	}

	public void setRadioScriptureText(String radioScriptureText) {
		this.radioScriptureText = radioScriptureText;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getRadioUserMessage() {
		return radioUserMessage;
	}

	public void setRadioUserMessage(String radioUserMessage) {
		this.radioUserMessage = radioUserMessage;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getJustify() {
		return justify;
	}

	public void setJustify(String justify) {
		this.justify = justify;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public String getInherit() {
		return inherit;
	}

	public void setInherit(String inherit) {
		this.inherit = inherit;
	}

	public String getWrongFileSize() {
		return wrongFileSize;
	}

	public void setWrongFileSize(String wrongFileSize) {
		this.wrongFileSize = wrongFileSize;
	}

	public String getWrongFormat() {
		return wrongFormat;
	}

	public void setWrongFormat(String wrongFormat) {
		this.wrongFormat = wrongFormat;
	}

	public String getWrongFiles() {
		return wrongFiles;
	}

	public void setWrongFiles(String wrongFiles) {
		this.wrongFiles = wrongFiles;
	}

	public String getScreensaver() {
		return screensaver;
	}

	public void setScreensaver(String screensaver) {
		this.screensaver = screensaver;
	}

	public String getAllCategories() {
		return allCategories;
	}

	public void setAllCategories(String allCategories) {
		this.allCategories = allCategories;
	}

	public String getAngels() {
		return angels;
	}

	public void setAngels(String angels) {
		this.angels = angels;
	}

	public String getClouds() {
		return clouds;
	}

	public void setClouds(String clouds) {
		this.clouds = clouds;
	}

	public String getCross() {
		return cross;
	}

	public void setCross(String cross) {
		this.cross = cross;
	}

	public String getChrist() {
		return Christ;
	}

	public void setChrist(String christ) {
		Christ = christ;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getPigeons() {
		return pigeons;
	}

	public void setPigeons(String pigeons) {
		this.pigeons = pigeons;
	}

	public String getScreensaverImageRadio() {
		return screensaverImageRadio;
	}

	public void setScreensaverImageRadio(String screensaverImageRadio) {
		this.screensaverImageRadio = screensaverImageRadio;
	}

	public String getScreensaverVerseRadio() {
		return screensaverVerseRadio;
	}

	public void setScreensaverVerseRadio(String screensaverVerseRadio) {
		this.screensaverVerseRadio = screensaverVerseRadio;
	}

	public String getScreensaverBothRadio() {
		return screensaverBothRadio;
	}

	public void setScreensaverBothRadio(String screensaverBothRadio) {
		this.screensaverBothRadio = screensaverBothRadio;
	}

	public String getScreensaverBibleSelection() {
		return screensaverBibleSelection;
	}

	public void setScreensaverBibleSelection(String screensaverBibleSelection) {
		this.screensaverBibleSelection = screensaverBibleSelection;
	}

	public String getNoVersionSelected() {
		return noVersionSelected;
	}

	public void setNoVersionSelected(String noVersionSelected) {
		this.noVersionSelected = noVersionSelected;
	}

	public String getAutomatScreensaver() {
		return automatScreensaver;
	}

	public void setAutomatScreensaver(String automatScreensaver) {
		this.automatScreensaver = automatScreensaver;
	}

	public String getManualScreensaver() {
		return manualScreensaver;
	}

	public void setManualScreensaver(String manualScreensaver) {
		this.manualScreensaver = manualScreensaver;
	}

	public String getDisplayVersionLabel() {
		return displayVersionLabel;
	}

	public void setDisplayVersionLabel(String displayVersionLabel) {
		this.displayVersionLabel = displayVersionLabel;
	}

	public String getDisplayBookLabel() {
		return displayBookLabel;
	}

	public void setDisplayBookLabel(String displayBookLabel) {
		this.displayBookLabel = displayBookLabel;
	}

	public String getDisplayChapterLabel() {
		return displayChapterLabel;
	}

	public void setDisplayChapterLabel(String displayChapterLabel) {
		this.displayChapterLabel = displayChapterLabel;
	}

	public String getDisplayVerseLabel() {
		return displayVerseLabel;
	}

	public void setDisplayVerseLabel(String displayVerseLabel) {
		this.displayVerseLabel = displayVerseLabel;
	}

	public String getBold() {
		return bold;
	}

	public void setBold(String bold) {
		this.bold = bold;
	}

	public String getEnterUpPopup() {
		return enterUpPopup;
	}

	public void setEnterUpPopup(String enterUpPopup) {
		this.enterUpPopup = enterUpPopup;
	}

	public String getEnterDownPopup() {
		return enterDownPopup;
	}

	public void setEnterDownPopup(String enterDownPopup) {
		this.enterDownPopup = enterDownPopup;
	}

	public String getReferenceWordsSpacePopup() {
		return referenceWordsSpacePopup;
	}

	public void setReferenceWordsSpacePopup(String referenceWordsSpacePopup) {
		this.referenceWordsSpacePopup = referenceWordsSpacePopup;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getDefaultPopupImage() {
		return defaultPopupImage;
	}

	public void setDefaultPopupImage(String defaultPopupImage) {
		this.defaultPopupImage = defaultPopupImage;
	}

	public String getFontFamilyPopup() {
		return fontFamilyPopup;
	}

	public void setFontFamilyPopup(String fontFamilyPopup) {
		this.fontFamilyPopup = fontFamilyPopup;
	}

	public String getFontStylePopup() {
		return fontStylePopup;
	}

	public void setFontStylePopup(String fontStylePopup) {
		this.fontStylePopup = fontStylePopup;
	}

	public String getLetterSpacingPopup() {
		return letterSpacingPopup;
	}

	public void setLetterSpacingPopup(String letterSpacingPopup) {
		this.letterSpacingPopup = letterSpacingPopup;
	}

	public String getLineHeightPopup() {
		return lineHeightPopup;
	}

	public void setLineHeightPopup(String lineHeightPopup) {
		this.lineHeightPopup = lineHeightPopup;
	}

	public String getWordSpacingPopup() {
		return wordSpacingPopup;
	}

	public void setWordSpacingPopup(String wordSpacingPopup) {
		this.wordSpacingPopup = wordSpacingPopup;
	}

	public String getReferenceLetterSpacingPopup() {
		return referenceLetterSpacingPopup;
	}

	public void setReferenceLetterSpacingPopup(String referenceLetterSpacingPopup) {
		this.referenceLetterSpacingPopup = referenceLetterSpacingPopup;
	}

	public String getReferenceFontFamilyPopup() {
		return referenceFontFamilyPopup;
	}

	public void setReferenceFontFamilyPopup(String referenceFontFamilyPopup) {
		this.referenceFontFamilyPopup = referenceFontFamilyPopup;
	}

	public String getReferenceFontStylePopup() {
		return referenceFontStylePopup;
	}

	public void setReferenceFontStylePopup(String referenceFontStylePopup) {
		this.referenceFontStylePopup = referenceFontStylePopup;
	}

	public String getReferenceFontDecorationPopup() {
		return referenceFontDecorationPopup;
	}

	public void setReferenceFontDecorationPopup(String referenceFontDecorationPopup) {
		this.referenceFontDecorationPopup = referenceFontDecorationPopup;
	}

	public String getPopup() {
		return popup;
	}

	public void setPopup(String popup) {
		this.popup = popup;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExactColors() {
		return exactColors;
	}

	public void setExactColors(String exactColors) {
		this.exactColors = exactColors;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getImageOpacity() {
		return imageOpacity;
	}

	public void setImageOpacity(String imageOpacity) {
		this.imageOpacity = imageOpacity;
	}

	public String getTextOpacity() {
		return textOpacity;
	}

	public void setTextOpacity(String textOpacity) {
		this.textOpacity = textOpacity;
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

	public String getCurrentSelection() {
		return currentSelection;
	}

	public void setCurrentSelection(String currentSelection) {
		this.currentSelection = currentSelection;
	}

	public String getSearchLevel() {
		return searchLevel;
	}

	public void setSearchLevel(String searchLevel) {
		this.searchLevel = searchLevel;
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

	public String getInvitationEmail() {
		return invitationEmail;
	}

	public void setInvitationEmail(String invitationEmail) {
		this.invitationEmail = invitationEmail;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getEmailContentValue() {
		return emailContentValue;
	}

	public void setEmailContentValue(String emailContentValue) {
		this.emailContentValue = emailContentValue;
	}

	public String getEmailTitleValue() {
		return emailTitleValue;
	}

	public void setEmailTitleValue(String emailTitleValue) {
		this.emailTitleValue = emailTitleValue;
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

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public String getSuccessfullLogout() {
		return successfullLogout;
	}

	public void setSuccessfullLogout(String successfullLogout) {
		this.successfullLogout = successfullLogout;
	}

	public String getFailureLogin() {
		return failureLogin;
	}

	public void setFailureLogin(String failureLogin) {
		this.failureLogin = failureLogin;
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

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
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

	public String getEventsLabel() {
		return eventsLabel;
	}

	public void setEventsLabel(String eventsLabel) {
		this.eventsLabel = eventsLabel;
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

	public String getDurationParticipant() {
		return durationParticipant;
	}

	public void setDurationParticipant(String durationParticipant) {
		this.durationParticipant = durationParticipant;
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

	public String getEventPassword() {
		return eventPassword;
	}

	public void setEventPassword(String eventPassword) {
		this.eventPassword = eventPassword;
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

	public String getWrongPassword() {
		return wrongPassword;
	}

	public void setWrongPassword(String wrongPassword) {
		this.wrongPassword = wrongPassword;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getTotalParticipantTime() {
		return totalParticipantTime;
	}

	public void setTotalParticipantTime(String totalParticipantTime) {
		this.totalParticipantTime = totalParticipantTime;
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

	public String getSuccessfulChurchCreation() {
		return successfulChurchCreation;
	}

	public void setSuccessfulChurchCreation(String successfulChurchCreation) {
		this.successfulChurchCreation = successfulChurchCreation;
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

	public String getUseVoice() {
		return useVoice;
	}

	public void setUseVoice(String useVoice) {
		this.useVoice = useVoice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getThankYouMessage() {
		return thankYouMessage;
	}

	public void setThankYouMessage(String thankYouMessage) {
		this.thankYouMessage = thankYouMessage;
	}

	public String getThankYou() {
		return thankYou;
	}

	public void setThankYou(String thankYou) {
		this.thankYou = thankYou;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}

	public String getBugs() {
		return bugs;
	}

	public void setBugs(String bugs) {
		this.bugs = bugs;
	}

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getBug() {
		return bug;
	}

	public void setBug(String bug) {
		this.bug = bug;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getiWantToJoin() {
		return iWantToJoin;
	}

	public void setiWantToJoin(String iWantToJoin) {
		this.iWantToJoin = iWantToJoin;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getOthersCaps() {
		return othersCaps;
	}

	public void setOthersCaps(String othersCaps) {
		this.othersCaps = othersCaps;
	}

	public String getMessagePassword() {
		return messagePassword;
	}

	public void setMessagePassword(String messagePassword) {
		this.messagePassword = messagePassword;
	}

	public String getiWantToJoinCaps() {
		return iWantToJoinCaps;
	}

	public void setiWantToJoinCaps(String iWantToJoinCaps) {
		this.iWantToJoinCaps = iWantToJoinCaps;
	}

	public boolean equals(Object o) {

		try {
			if (o != null) {

				try{

					Language language = ((Language) o);

					return language.getLanguageId() == this.getLanguageId() && language.getValue().trim().equals(this.getValue().trim());
				}
				catch(Exception e){
					//new Utilities().writeFile(e);
					return false;
				}
			}
		} catch (Exception e) {
			//new Utilities().writeFile(e);
		}
		return false;
	}

	public int hashCode() {
		return new Long(languageId).intValue();
	}
}