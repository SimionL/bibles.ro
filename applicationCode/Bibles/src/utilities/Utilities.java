package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import beans.ApplicationCode;
import beans.Bible;
import beans.Church;
import beans.Feedback;
import beans.Popup;
import beans.Reference;
import beans.Screensaver;
import beans.Settings;
import beans.ThankYou;
import beans.Voice;
import dao.DAO;
import dbBeans.ChurchTable;
import dbBeans.Event;
import dbBeans.Language;
import dbBeans.MessageTable;
import dbBeans.Participant;
import dbBeans.ScreensaverTable;

public class Utilities {

	private final String bibleLog = Constants.bibleLog.value;
	private final String platformType = Constants.platformType.value;	
	private final String dbPath = Constants.dbPath.value;
	private final String picturesPath = Constants.picturesPath.value;

	public void setModel(ModelMap model, Bible bible, Church church, Settings settings, Popup popup, Reference reference, Screensaver screensaver, ApplicationCode code, Voice voice, ThankYou thankYou, Feedback feedback){

		try{

			if(model != null){

				if (model.containsAttribute("bible")) {
					model.remove("bible");
				}

				if (model.containsAttribute("settings")) {
					model.remove("settings");
				}

				if (model.containsAttribute("church")) {
					model.remove("church");
				}

				if (model.containsAttribute("screensaver")) {
					model.remove("screensaver");
				}

				if (model.containsAttribute("code")) {
					model.remove("code");
				}

				if (model.containsAttribute("popup")) {
					model.remove("popup");
				}

				if (model.containsAttribute("reference")) {
					model.remove("reference");
				}

				if (model.containsAttribute("voice")) {
					model.remove("voice");
				}

				if (model.containsAttribute("thankYou")) {
					model.remove("thankYou");
				}

				if (model.containsAttribute("feedback")) {
					model.remove("feedback");
				}

				if(bible != null){

					model.addAttribute("bible", bible);
				}

				if(settings != null){

					model.addAttribute("settings", settings);
				}

				if(popup != null){

					model.addAttribute("popup", popup);
				}

				if(reference != null){

					model.addAttribute("reference", reference);
				}

				if(church != null){

					model.addAttribute("church", church);
				}

				if(screensaver != null){

					model.addAttribute("screensaver", screensaver);
				}

				if(code != null){

					model.addAttribute("code", code);
				}

				if(voice != null){

					model.addAttribute("voice", voice);
				}

				if(feedback != null){

					model.addAttribute("feedback", feedback);
				}

				if(thankYou != null){

					model.addAttribute("thankYou", thankYou);
				}
			}
		} catch (Exception e) {
			writeFile(e);
		}
	}

	public LinkedHashSet<String> getList(String firstElement){

		LinkedHashSet<String> list = new LinkedHashSet<>();
		list.add(firstElement);

		return list;
	}
	private void setBibleLabels(Language language, Bible bible, Settings settings){

		try{
			if(language != null && bible != null){

				bible.setSelectVersion(language.getSelectVersion());
				bible.setSelectBook(language.getSelectBook());
				bible.setSelectChapter(language.getSelectChapter());
				bible.setSelectVerse(language.getSelectVerse());
				settings.setSelectLanguage(language.getSelectLanguage());

				bible.setNoVersion(language.getNoVersion());
				bible.setNoBook(language.getNoBook());
				bible.setNoChapter(language.getNoChapter());
				bible.setNoVerse(language.getNoVerse());
				bible.setNoResult(language.getNoResult());
				bible.setReferences(language.getReferences());
			}

		} catch (Exception e) {
			writeFile(e);
		}
	}

	public void setLanguage(final Bible bible, final Settings settings, final Church church, final Popup popup, final Reference reference, final Screensaver screensaver, final ApplicationCode code, final Voice voice, final ThankYou thankYou, final Feedback feedback, DAO dao){

		try{
			String selectedLanguage = settings.getSelectedLanguage();

			if(selectedLanguage != null && !selectedLanguage.trim().isEmpty()){

				Language language = null;

				List<Language> availableLanguages = dao.getAllLanguages(selectedLanguage);

				if(availableLanguages != null && !availableLanguages.isEmpty()){

					for(Language lang : availableLanguages){

						if(lang != null){

							String languageName = lang.getValue();

							if(languageName != null && !languageName.trim().isEmpty()){

								languageName = languageName.trim();

								if(languageName.equals(selectedLanguage)){
									language = lang;
									break;
								}
							}
						}
					}
				}

				if(language != null){

					Map<String, Map<String, String>> verseValue = bible.getVerseValue();

					if(verseValue != null && !verseValue.isEmpty()){

						LinkedHashMap<String, Map<String, String>> newValue =  new LinkedHashMap<>();

						newValue.clear();

						for(String verseKey : verseValue.keySet()){

							String verseSelected = verseKey;

							if(verseSelected != null && !verseSelected.trim().isEmpty()){

								verseSelected = verseSelected.trim();

								if(verseSelected.equalsIgnoreCase(bible.getNoBackVerse())){
									newValue.put(language.getNoBackVerse(), verseValue.get(verseKey));
								}
								else if(verseSelected.equalsIgnoreCase(bible.getNoNextVerse())){
									newValue.put(language.getNoNextVerse(), verseValue.get(verseKey));
								}
								else if(verseSelected.equalsIgnoreCase(bible.getNoVerseSelected())){
									newValue.put(language.getNoVerseSelected(), verseValue.get(verseKey));
								}
								else if(verseSelected.equalsIgnoreCase(bible.getNoResult())){
									newValue.put(language.getNoResult(), verseValue.get(verseKey));
								}
								else {
									newValue.put(verseKey, verseValue.get(verseKey));
								}
							}
						}
					}

					String selectedVersion = bible.getSelectedVersion();
					String selectedBook =    bible.getSelectedBook();
					String selectedChapter = bible.getSelectedChapter();
					String selectedVerse =   bible.getSelectedVerse();

					String selectVersion = bible.getSelectVersion();
					String selectBook =    bible.getSelectBook();
					String selectChapter = bible.getSelectChapter();
					String selectVerse =   bible.getSelectVerse();

					String newSelectVersion = language.getSelectVersion();
					String newSelectBook =    language.getSelectBook();
					String newSelectChapter = language.getSelectChapter();
					String newSelectVerse =   language.getSelectVerse();

					String noVersion = bible.getNoVersion();
					String noBook =    bible.getNoBook();
					String noChapter = bible.getNoChapter();
					String noVerse =   bible.getNoVerse();

					String newNoVersion = language.getNoVersion();
					String newNoBook =    language.getNoBook();
					String newNoChapter = language.getNoChapter();
					String newNoVerse =   language.getNoVerse();

					Map<String, String> versionLanguageMap = new HashMap<>();
					Map<String, String> bookLanguageMap = new HashMap<>();
					Map<String, String> chapterLanguageMap = new HashMap<>();
					Map<String, String> verseLanguageMap = new HashMap<>();

					versionLanguageMap.put(noVersion, newNoVersion);
					versionLanguageMap.put(selectVersion, newSelectVersion);

					bookLanguageMap.put(noBook, newNoBook);
					bookLanguageMap.put(selectBook, newSelectBook);

					chapterLanguageMap.put(noChapter, newNoChapter);
					chapterLanguageMap.put(selectChapter, newSelectChapter);

					verseLanguageMap.put(noVerse, newNoVerse);
					verseLanguageMap.put(selectVerse, newSelectVerse);

					if(selectedVersion != null && !selectedVersion.trim().isEmpty()){

						if(selectedVersion.equals(selectVersion)){
							bible.setSelectedVersion(newSelectVersion);
						}
						else if(selectedVersion.equals(noVersion)){
							bible.setSelectedVersion(newNoVersion);
						}
						else{
							bible.setSelectedVersion(selectedVersion);
						}
					}

					if(selectedBook != null && !selectedBook.trim().isEmpty()){

						if(selectedBook.equals(selectBook)){
							bible.setSelectedBook(newSelectBook);
						}
						else if(selectedBook.equals(noBook)){
							bible.setSelectedBook(newNoBook);
						}
						else{
							bible.setSelectedBook(selectedBook);
						}
					}

					if(selectedChapter != null && !selectedChapter.trim().isEmpty()){

						if(selectedChapter.equals(selectChapter)){
							bible.setSelectedChapter(newSelectChapter);
						}
						else if(selectedChapter.equals(noChapter)){
							bible.setSelectedChapter(newNoChapter);
						}
						else{
							bible.setSelectedChapter(selectedChapter);
						}
					}

					if(selectedVerse != null && !selectedVerse.trim().isEmpty()){

						if(selectedVerse.equals(selectVerse)){
							bible.setSelectedVerse(newSelectVerse);
						}
						else if(selectedVerse.equals(noVerse)){
							bible.setSelectedVerse(newNoVerse);
						}
						else{
							bible.setSelectedVerse(selectedVerse);
						}
					}

					bible.setVersions(getNewSet(bible.getVersions(), versionLanguageMap));
					bible.setBooks(getNewSet(bible.getBooks(), bookLanguageMap));
					bible.setChapters(getNewSet(bible.getChapters(), chapterLanguageMap));
					bible.setVerses(getNewSet(bible.getVerses(), verseLanguageMap));
					bible.setSelectVersion(newSelectVersion);
					bible.setSelectBook(newSelectBook);
					bible.setSelectChapter(newSelectChapter);
					bible.setSelectVerse(newSelectVerse);
					bible.setNoVersion(newNoVersion);
					bible.setNoBook(newNoBook);
					bible.setNoChapter(newNoChapter);
					bible.setNoVerse(newNoVerse);
					bible.setNoResult(language.getNoResult());
					bible.setReferences(language.getReferences());
					bible.setNoNextVerse(language.getNoNextVerse());
					bible.setNoBackVerse(language.getNoBackVerse());
					bible.setNoVerseSelected(language.getNoVerseSelected());
					bible.setBible(language.getBible());
					bible.setSettings(language.getSettings());
					bible.setChurch(language.getChurch());
					bible.setScreensaver(language.getScreensaver());
					bible.setCode(language.getCode());
					bible.setPopup(language.getPopup());
					bible.setReference(language.getReference());
					bible.setLanguageCode(language.getLanguageCode());
					bible.setVoice(language.getVoice());
					bible.setFeedback(language.getFeedback());
					bible.setThankYou(language.getThankYou());

					settings.setDisplayReferencesLabel(language.getDisplayReferences());
					settings.setWordWrapLabel(language.getWordWrap());
					settings.setDisplayEntireChapterLabel(language.getDisplayEntireChapter());
					settings.setHighlightsTextLabel(language.getHighlightsText());
					settings.setFormFontLabel(language.getFont());
					settings.setScreensaver(language.getScreensaver());
					settings.setSearchBlockLength(language.getSearchBlockLength());
					settings.setInexactColorsLabel(language.getInexactColors());
					settings.setExactColorsLabel(language.getExactColors());
					settings.setCode(language.getCode());
					settings.setPopup(language.getPopup());
					settings.setBible(language.getBible());
					settings.setSettings(language.getSettings());
					settings.setChurch(language.getChurch());
					settings.setSelectLanguage(language.getSelectLanguage());
					settings.setReference(language.getReference());
					settings.setSearchLevel(language.getSearchLevel());
					settings.setLanguageCode(language.getLanguageCode());
					settings.setVoice(language.getVoice());
					settings.setFeedback(language.getFeedback());
					settings.setThankYou(language.getThankYou());

					popup.setPopupBackgroundColorLabel(language.getPopupBackgroundColor());
					popup.setPopupTextColorLabel(language.getPopupTextColor());
					popup.setWrongFileSize(language.getWrongFileSize());
					popup.setWrongFormat(language.getWrongFormat());
					popup.setWrongFiles(language.getWrongFiles());
					popup.setPopupTextAlignLabel(language.getPopupTextAlign());
					popup.setPopupBackgroundColorLabel(language.getPopupBackgroundColor());
					popup.setPopupPictureLabel(language.getPicture());
					popup.setPopupUserMessageLabel(language.getPopupUserMessage());
					popup.setDefaultPopupImageLabel(language.getDefaultPopupImage());
					popup.setFontFamilyPopupLabel(language.getFontFamilyPopup());
					popup.setFontStylePopupLabel(language.getFontStylePopup());
					popup.setLetterSpacingPopupLabel(language.getLetterSpacingPopup());
					popup.setLineHeightPopupLabel(language.getLineHeightPopup());
					popup.setWordSpacingPopupLabel(language.getWordSpacingPopup());
					popup.setPopupMarginTopLabel(language.getPopupMarginTop());
					popup.setPopupMarginBottomLabel(language.getPopupMarginBottom());
					popup.setPopupMarginRightLabel(language.getPopupMarginRight());
					popup.setPopupMarginLeftLabel(language.getPopupMarginLeft());
					popup.setPopup(language.getPopup());
					popup.setBible(language.getBible());
					popup.setSettings(language.getSettings());
					popup.setChurch(language.getChurch());
					popup.setScreensaver(language.getScreensaver());
					popup.setCode(language.getCode());
					popup.setBoldLabel(language.getBold());
					popup.setFontLabel(language.getFont());
					popup.setReference(language.getReference());
					popup.setImageOpacityLabel(language.getImageOpacity());
					popup.setTextOpacityLabel(language.getTextOpacity());
					popup.setLanguageCode(language.getLanguageCode());
					popup.setVoice(language.getVoice());
					popup.setFeedback(language.getFeedback());
					popup.setThankYou(language.getThankYou());

					reference.setSchemaLabel(language.getSchema());
					reference.setDisplayVersionLabel(language.getDisplayVersionLabel());
					reference.setDisplayBookLabel(language.getDisplayBookLabel());
					reference.setDisplayChapterLabel(language.getDisplayChapterLabel());
					reference.setDisplayVerseLabel(language.getDisplayVerseLabel());
					reference.setEnterUpPopupLabel(language.getEnterUpPopup());
					reference.setEnterDownPopupLabel(language.getEnterDownPopup());
					reference.setBoldLabel(language.getBold());
					reference.setReferenceWordsSpacePopupLabel(language.getReferenceWordsSpacePopup());
					reference.setReferenceAlignmentLabel(language.getAlignment());
					reference.setFontLabel(language.getFont());
					reference.setReferenceLetterSpacingPopupLabel(language.getReferenceLetterSpacingPopup());
					reference.setReferenceFontFamilyPopupLabel(language.getReferenceFontFamilyPopup());
					reference.setReferenceFontStylePopupLabel(language.getReferenceFontStylePopup());
					reference.setReferenceFontDecorationPopupLabel(language.getReferenceFontDecorationPopup());
					reference.setPopup(language.getPopup());
					reference.setBible(language.getBible());
					reference.setSettings(language.getSettings());
					reference.setChurch(language.getChurch());
					reference.setScreensaver(language.getScreensaver());
					reference.setCode(language.getCode());
					reference.setReference(language.getReference());
					reference.setLanguageCode(language.getLanguageCode());
					reference.setVoice(language.getVoice());
					reference.setFeedback(language.getFeedback());
					reference.setThankYou(language.getThankYou());

					screensaver.setScreensaver(language.getScreensaver());
					screensaver.setCode(language.getCode());
					screensaver.setPopup(language.getPopup());
					screensaver.setNoVersionSelected(language.getNoVersionSelected());
					screensaver.setBible(language.getBible());
					screensaver.setSettings(language.getSettings());
					screensaver.setChurch(language.getChurch());
					screensaver.setReference(language.getReference());
					screensaver.setLanguageCode(language.getLanguageCode());
					screensaver.setVoice(language.getVoice());
					screensaver.setFeedback(language.getFeedback());
					screensaver.setThankYou(language.getThankYou());

					church.setScreensaver(language.getScreensaver());
					church.setCode(language.getCode());
					church.setPopup(language.getPopup());
					church.setBible(language.getBible());
					church.setSettings(language.getSettings());
					church.setChurch(language.getChurch());
					church.setReference(language.getReference());
					church.setNotOkEmail(language.getNotOkEmail());
					church.setOkEmail(language.getOkEmail());
					church.setInvitationEmailLabel(language.getInvitationEmail());
					church.setEmailTitleLabel(language.getEmailTitle());
					church.setEmailContentLabel(language.getEmailContent());
					church.setEventDescriptionLabel(language.getEventDescription());
					church.setEmailContentValue(language.getEmailContentValue());
					church.setEmailTitleValue(language.getEmailTitleValue());
					church.setChurchEmailLabel(language.getChurchEmail());
					church.setChurchEmailPasswordLabel(language.getChurchEmailPassword());
					church.setUsernameLabel(language.getUsername());
					church.setPasswordLabel(language.getPassword());
					church.setEvent(language.getEvent());
					church.setAccount(language.getAccount());
					church.setLogin(language.getLogin());
					church.setCreateAccount(language.getCreateAccount());
					church.setDeleteAccount(language.getDeleteAccount());
					church.setUpdateAccount(language.getUpdateAccount());
					church.setLogout(language.getLogout());
					church.setSuccessfullLogout(language.getSuccessfullLogout());
					church.setFailureLogin(language.getFailureLogin());
					church.setSuccessfullDelete(language.getSuccessfullDelete());
					church.setErrorDelete(language.getErrorDelete());
					church.setSuccessfullUpdated(language.getSuccessfullUpdated());
					church.setErrorUpdated(language.getErrorUpdated());
					church.setCreateEvent(language.getCreateEvent());
					church.setUpdateEvent(language.getUpdateEvent());
					church.setDeleteEvent(language.getDeleteEvent());
					church.setEventNameLabel(language.getEventName());
					church.setEventDateLabel(language.getEventDate());
					church.setSuccessfullEventCreated(language.getSuccessfullEventCreated());
					church.setEventCreationError(language.getEventCreationError());
					church.setEventsLabel(language.getEventsLabel());
					church.setChurchesLabel(language.getChurchesLabel());
					church.setNoEventLabel(language.getNoEventLabel());
					church.setNoChurchLabel(language.getNoChurchLabel());
					church.setFailurEventUpdate(language.getFailurEventUpdate());
					church.setSuccessfullEventUpdate(language.getSuccessfullEventUpdate());
					church.setFailurEventDelete(language.getFailurEventDelete());
					church.setSuccessfullEventDelete(language.getSuccessfullEventDelete());
					church.setEventDuration(language.getEventDuration());
					church.setMinutes(language.getMinutes());
					church.setParticipantName(language.getParticipantName());
					church.setParticipantForename(language.getParticipantForename());
					church.setDurationParticipantLabel(language.getDurationParticipant());
					church.setResources(language.getResources());
					church.setParticipantPhone(language.getParticipantPhone());
					church.setParticipantEmail(language.getParticipantEmail());
					church.setEventPasswordLabel(language.getEventPassword());
					church.setOkParticipantAdded(language.getOkParticipantAdded());
					church.setErrorParticipantAdded(language.getErrorParticipantAdded());
					church.setErrorParticipantDeleted(language.getErrorParticipantDeleted());
					church.setOkParticipantDeleted(language.getOkParticipantDeleted());
					church.setWrongPassword(language.getWrongPassword());
					church.setWrongFileSize(language.getWrongFileSize());
					church.setParticipantMessage(language.getMessage());
					church.setOrder(language.getOrder());
					church.setTotalParticipantTimeLabel(language.getTotalParticipantTime());
					church.setDownloadAttachments(language.getDownloadAttachments());
					church.setSendInvitation(language.getSendInvitation());
					church.setAccessInvitation(language.getAccessInvitation());
					church.setSuccessfulLogin(language.getSuccessfulLogin());
					church.setUsernameError(language.getUsernameError());
					church.setExistEmailError(language.getExistEmailError());
					church.setInvalidEmailFormat(language.getInvalidEmailFormat());
					church.setSuccessfulInsertion(language.getSuccessfulChurchCreation());
					church.setLanguageCode(language.getLanguageCode());
					church.setVoice(language.getVoice());
					church.setFeedback(language.getFeedback());
					church.setThankYou(language.getThankYou());

					code.setBible(language.getBible());
					code.setSettings(language.getSettings());
					code.setChurch(language.getChurch());
					code.setScreensaver(language.getScreensaver());
					code.setCode(language.getCode());
					code.setPopup(language.getPopup());
					code.setReference(language.getReference());
					code.setLanguageCode(language.getLanguageCode());
					code.setVoice(language.getVoice());
					code.setFeedback(language.getFeedback());
					code.setThankYou(language.getThankYou());

					voice.setBible(language.getBible());
					voice.setSettings(language.getSettings());
					voice.setChurch(language.getChurch());
					voice.setScreensaver(language.getScreensaver());
					voice.setCode(language.getCode());
					voice.setPopup(language.getPopup());
					voice.setReference(language.getReference());
					voice.setLanguageCode(language.getLanguageCode());
					voice.setVoice(language.getVoice());
					voice.setUseVoiceLabel(language.getUseVoice());
					voice.setFeedback(language.getFeedback());
					voice.setThankYou(language.getThankYou());

					thankYou.setBible(language.getBible());
					thankYou.setSettings(language.getSettings());
					thankYou.setChurch(language.getChurch());
					thankYou.setScreensaver(language.getScreensaver());
					thankYou.setCode(language.getCode());
					thankYou.setPopup(language.getPopup());
					thankYou.setReference(language.getReference());
					thankYou.setVoice(language.getVoice());
					thankYou.setFeedback(language.getFeedback());
					thankYou.setThankYou(language.getThankYou());
					thankYou.setThankYouMessage(language.getThankYouMessage());

					feedback.setBible(language.getBible());
					feedback.setSettings(language.getSettings());
					feedback.setChurch(language.getChurch());
					feedback.setScreensaver(language.getScreensaver());
					feedback.setCode(language.getCode());
					feedback.setPopup(language.getPopup());
					feedback.setReference(language.getReference());
					feedback.setVoice(language.getVoice());
					feedback.setFeedback(language.getFeedback());
					feedback.setThankYou(language.getThankYou());
					feedback.setMessageLabel(language.getMessage());
					feedback.setSuggestionsLabel(language.getSuggestions());
					feedback.setBugsLabel(language.getBugs());
					feedback.setQuestionsLabel(language.getQuestions());
					feedback.setiWantToJoinLabel(language.getiWantToJoinCaps());
					feedback.setOthersLabel(language.getOthersCaps());
					feedback.setMyNameLabel(language.getMyName());
					feedback.setMessageTypeLabel(language.getMessageType());
					feedback.setUserPasswordLabel(language.getMessagePassword());
					feedback.setWrongPasswordMessage(language.getWrongPassword());

					final LinkedHashMap<Integer, String> typeRadioMap = feedback.getTypeRadioMap();

					if(typeRadioMap != null){
						typeRadioMap.clear();
						typeRadioMap.put(1, language.getBug());
						typeRadioMap.put(2, language.getQuestion());
						typeRadioMap.put(3, language.getSuggestion());
						typeRadioMap.put(4, language.getiWantToJoin());
						typeRadioMap.put(5, language.getOthers());
					}

					final LinkedHashMap<Integer, String> screensaverRadioMap = screensaver.getScreensaverRadioMap();

					if(screensaverRadioMap != null){
						screensaverRadioMap.clear();
						screensaverRadioMap.put(1, language.getScreensaverBothRadio());
						screensaverRadioMap.put(2, language.getScreensaverImageRadio());
						screensaverRadioMap.put(3, language.getScreensaverVerseRadio());
						screensaverRadioMap.put(4, language.getScreensaverBibleSelection());
					}

					final TreeMap<Integer, String> screensaverTypeMap = screensaver.getScreensaverTypeMap();

					if(screensaverTypeMap != null){
						screensaverTypeMap.clear();
						screensaverTypeMap.put(1, language.getAutomatScreensaver());
						screensaverTypeMap.put(2, language.getManualScreensaver());
					}

					LinkedHashMap<Integer, String> screensaverCategoriesMap = screensaver.getScreensaverCategoriesMap();

					if(screensaverCategoriesMap != null){
						screensaverCategoriesMap.clear();
						screensaverCategoriesMap.put(1, language.getAllCategories());
						screensaverCategoriesMap.put(2, language.getChrist());
						screensaverCategoriesMap.put(3, language.getCross());
						screensaverCategoriesMap.put(4, language.getBible().toLowerCase());
						screensaverCategoriesMap.put(5, language.getAngels());
						screensaverCategoriesMap.put(6, language.getClouds());
						screensaverCategoriesMap.put(7, language.getPigeons());
						screensaverCategoriesMap.put(8, language.getOthers());
					}

					final LinkedHashMap<Integer, String> searchAreaOptions = settings.getSearchAreaOptions();

					if(searchAreaOptions != null){
						searchAreaOptions.clear();
						searchAreaOptions.put(1, language.getSelectedVersion());
						searchAreaOptions.put(2, language.getSelectedBook());
						searchAreaOptions.put(3, language.getSelectedChapter());
						searchAreaOptions.put(4, language.getCurrentSelection());
					}

					setSearchHistory(bible, language);
					setPopupTextAlignment(popup, reference, language);
					setPopupRadio(popup, language);
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
	}

	private void setSearchHistory(Bible bible, Language language){

		try{
			String searchLabel = bible.getSearchHistory();
			Map<String, String> historyMap = bible.getHistoryMap();
			if(historyMap != null){
				if(historyMap.containsKey(searchLabel)){
					historyMap.remove(searchLabel);
					if(historyMap.isEmpty()){
						historyMap.put(language.getSearchHistory(), null);
					}
					else{
						Map<String, String> bufferMap = new LinkedHashMap<>();
						bufferMap.putAll(historyMap);
						historyMap.clear();
						historyMap.put(language.getSearchHistory(), null);
						historyMap.putAll(bufferMap);
					}
				}
				else{
					historyMap.put(language.getSearchHistory(), null);
				}
				bible.setSearchHistory(language.getSearchHistory());
			}
		}
		catch(Exception e){
			writeFile(e);
		}
	}

	private LinkedHashSet<String> getNewSet(LinkedHashSet<String> set , Map<String, String> languageMap){

		LinkedHashSet<String> newSetList = new LinkedHashSet<>();

		try{

			if (set != null && languageMap != null && !set.isEmpty() && !languageMap.isEmpty()){

				for(String firsLine : languageMap.keySet()){
					if(firsLine != null && !firsLine.trim().isEmpty() && set.contains(firsLine.trim())){
						set.remove(firsLine.trim());
						newSetList.add(languageMap.get(firsLine));
					}
				}

				newSetList.addAll(set);
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return newSetList;
	}

	private void iterateFolder(TreeMap<Integer, ApplicationElement> folderMap, String length, String path){

		try{
			File codeFolder = new File(path);
			if(codeFolder != null && codeFolder.exists() && codeFolder.isDirectory() && codeFolder.canRead()){

				File[] files = codeFolder.listFiles();

				if(files != null && files.length > 0){

					TreeMap<String, ApplicationElement> codeFolders = new TreeMap<>();
					TreeMap<String, ApplicationElement> codeFiles = new TreeMap<>();

					for(File file : files){
						if(file != null && file.exists() && file.canRead()){

							ApplicationElement element = getElement(file, length + length);

							if(element != null){
								if(file.isDirectory()){
									File[] folderContent = file.listFiles();
									if(folderContent != null && folderContent.length > 0){
										element.setFile(false);
										codeFolders.put(file.getName(), element);
									}
								}
								else{
									element.setFile(true);
									codeFiles.put(file.getName(), element);
								}
							}
						}
					}

					if(codeFiles != null && !codeFiles.isEmpty()){
						for(String fileName : codeFiles.keySet()){
							if(fileName != null && !fileName.trim().isEmpty()){
								ApplicationElement fileElement = codeFiles.get(fileName);
								if(fileElement != null){
									int id = folderMap.size() + 1;
									fileElement.setId(id);
									folderMap.put(id, fileElement);
								}
							}
						}
					}

					if(codeFolders != null && !codeFolders.isEmpty()){
						for(String folderName : codeFolders.keySet()){
							if(folderName != null && !folderName.trim().isEmpty()){
								ApplicationElement folderElement = codeFolders.get(folderName);
								if(folderElement != null){
									int id = folderMap.size() + 1;
									folderElement.setId(id);
									folderMap.put(id, folderElement);
									iterateFolder(folderMap, length + length, folderElement.getPath());
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private ApplicationElement getElement(File file, String length){

		ApplicationElement element = new ApplicationElement();

		element.setMargin(length.trim());
		element.setName(file.getName().trim());
		element.setPath(file.getAbsolutePath().trim());

		return element;
	}

	private void addMessageMap(final Map<Long, MessageTable> messageMap, MessageTable message){

		try{
			if(messageMap != null && message != null){

				long millisecondsMessageDate = message.getMessageDate();

				if(millisecondsMessageDate > 0){

					Date date = new Date(millisecondsMessageDate);

					if(date != null){

						String messageStringDate =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

						if(messageStringDate != null && !messageStringDate.trim().isEmpty()){

							message.setMessageStringDate(messageStringDate.trim());

							messageMap.put(message.getMessageDate(), message);
						}
					}
				}
			}
		}catch(Exception e){
			writeFile(e);
		}
	}

	public void addMessages(final List<MessageTable> messagesList, Feedback feedback){

		try{

			if(messagesList != null && !messagesList.isEmpty()){

				for(MessageTable message : messagesList){
					if(message != null){
						switch(message.getMessageType()){
						case 1 : {
							addMessageMap(feedback.getMessageBugMap(), message);
							break;
						}
						case 2 : {
							addMessageMap(feedback.getMessageQuestionMap(), message);
							break;
						}
						case 3 : {
							addMessageMap(feedback.getMessageSuggestionMap(), message);
							break;
						}
						case 4 : {
							addMessageMap(feedback.getMessageIWantToJoinMap(), message);
							break;
						}
						case 5 : {
							addMessageMap(feedback.getMessageOthersMap(), message);
							break;
						}
						}
					}
				}
			}
		}catch(Exception e){
			writeFile(e);
		}
	}

	private void setDefaultOnload(final AppBean appBean, final Popup popup, final Reference reference, final Settings settings, final Bible bible, Screensaver screensaver, final ApplicationCode code, final Feedback feedback, final Language language, DAO dao){

		try{

			String popupId = "" + System.nanoTime() + new Random().nextLong() + System.nanoTime() + new Random().nextLong() + new Random().nextInt();

			if(feedback != null){
				addMessages(dao.getAllMessages(null), feedback);
			}

			if(code != null){

				TreeMap<Integer, ApplicationElement> folderMap = appBean.getFolderMap();

				if(folderMap != null){

					folderMap.clear();

					iterateFolder(folderMap, "&nbsp;&nbsp;", Constants.codePath.value);

					if(folderMap != null && !folderMap.isEmpty()){

						final LinkedList<ApplicationElement> folder = code.getFolder();

						if(folder != null){

							folder.clear();

							for(Integer key : folderMap.keySet()){

								ApplicationElement elem = folderMap.get(key);

								if(elem != null){
									folder.add(elem);
								}
							}
						}
					}
				}
				code.setPopupId(popupId.trim());
			}

			if (popup != null) {
				popup.setPopupId(popupId.trim());
				populateList(popup.getScriptureTextOpacityList(), 1, 10, 1);
				populateList(popup.getPictureOpacityList(), 1, 10, 1);
				populateList(popup.getPopupMarginTopList(), 1, 900, 5);
				populateList(popup.getPopupMarginBottomList(), 1, 900, 5);
				populateList(popup.getPopupMarginRightList(), 1, 900, 5);
				populateList(popup.getPopupMarginLeftList(), 1, 900, 5);
				populateList(popup.getPopupFontList(), 1, 900, 5);
				populateList(popup.getLetterSpacingPopupList(), 0,900, 1);
				populateList(popup.getLineHeightPopupList(),    1,900, 1);
				populateList(popup.getWordSpacingPopupList(),   0,900, 1);
			}

			if (reference != null) {
				reference.setPopupId(popupId.trim());
				populateList(reference.getReferenceEnterUpPopupList(), 0, 15, 1); 
				populateList(reference.getReferenceEnterDownPopupList(), 0, 15, 1);
				populateList(reference.getReferenceVersionSpaceList(), 0, 900, 1);
				populateList(reference.getReferenceBookSpaceList(), 0, 900, 1);
				populateList(reference.getReferenceChapterSpaceList(), 0, 900, 1);
				populateList(reference.getReferenceDotsSpaceList(), 0, 900, 1);
				populateList(reference.getReferenceSizePopupList(), 0, 900, 1);
				populateList(reference.getReferenceParamList_1(), -99, 99, 1);
				populateList(reference.getReferenceParamList_2(), -99, 99, 1);
				populateList(reference.getReferenceParamList_3(),   0, 99, 1);
				populateList(reference.getReferenceParamList_4(), -99, 99, 1);
				populateList(reference.getReferenceParamList_5(), -99, 99, 1);
				populateList(reference.getReferenceParamList_6(),   0, 99, 1);
				populateList(reference.getReferenceLetterSpacingPopupList(), 0,900, 1);
			}

			if(settings != null){

				populateList(settings.getSearchBlockLengthSelectionList(), 1, 20, 1);
				populateList(settings.getFormFontList(), 1, 7, 1);

				if(appBean != null){

					loadResources(appBean, screensaver, dao);
				}

				TreeSet<String> fontFamilyPopupList              = popup.getFontFamilyPopupList();
				TreeSet<String> fontStylePopupList               = popup.getFontStylePopupList();
				TreeSet<String> referenceFontFamilyPopupList     = reference.getReferenceFontFamilyPopupList();
				TreeSet<String> referenceFontStylePopupList      = reference.getReferenceFontStylePopupList();
				TreeSet<String> referenceFontDecorationPopupList = reference.getReferenceFontDecorationPopupList();

				fontFamilyPopupList.add("Arial");
				fontFamilyPopupList.add("Arial Black");
				fontFamilyPopupList.add("Comic Sans MS");
				fontFamilyPopupList.add("Courier New");
				fontFamilyPopupList.add("Lucida Console");
				fontFamilyPopupList.add("Arial Narrow");
				fontFamilyPopupList.add("Impact");
				fontFamilyPopupList.add("Century Gothic");
				fontFamilyPopupList.add("Courier");
				fontFamilyPopupList.add("Cursive");
				fontFamilyPopupList.add("Sans-serif");
				fontFamilyPopupList.add("Verdana");
				fontFamilyPopupList.add("Times New Roman");

				referenceFontFamilyPopupList.add("Arial");
				referenceFontFamilyPopupList.add("Arial Black");
				referenceFontFamilyPopupList.add("Comic Sans MS");
				referenceFontFamilyPopupList.add("Courier New");
				referenceFontFamilyPopupList.add("Lucida Console");
				referenceFontFamilyPopupList.add("Arial Narrow");
				referenceFontFamilyPopupList.add("Impact");
				referenceFontFamilyPopupList.add("Century Gothic");
				referenceFontFamilyPopupList.add("Courier");
				referenceFontFamilyPopupList.add("Cursive");
				referenceFontFamilyPopupList.add("Sans-serif");
				referenceFontFamilyPopupList.add("Verdana");
				referenceFontFamilyPopupList.add("Times New Roman");

				fontStylePopupList.add("italic");
				fontStylePopupList.add("normal");
				fontStylePopupList.add("oblique");

				referenceFontStylePopupList.add("italic");
				referenceFontStylePopupList.add("normal");
				referenceFontStylePopupList.add("oblique");

				referenceFontDecorationPopupList.add("overline");
				referenceFontDecorationPopupList.add("line-through");
				referenceFontDecorationPopupList.add("underline");
				referenceFontDecorationPopupList.add("none");
			}
			if(bible != null){
				bible.setPopupId(popupId.trim());
			}
			if(screensaver != null){
				screensaver.setPopupId(popupId.trim());
				populateList(screensaver.getScreensaverParamList_1(), -99, 99, 1);
				populateList(screensaver.getScreensaverParamList_2(), -99, 99, 1);
				populateList(screensaver.getScreensaverParamList_3(),   0, 99, 1);
				populateList(screensaver.getScreensaverParamList_4(), -99, 99, 1);
				populateList(screensaver.getScreensaverParamList_5(), -99, 99, 1);
				populateList(screensaver.getScreensaverParamList_6(),   0, 99, 1);
				populateList(screensaver.getScreensaverParamList_7(), -99, 99, 1);
				populateList(screensaver.getScreensaverParamList_8(), -99, 99, 1);
				populateList(screensaver.getScreensaverParamList_9(),   0, 99, 1);
				populateList(screensaver.getScreensaverParamList_10(),-99, 99, 1);
				populateList(screensaver.getScreensaverParamList_11(),-99, 99, 1);
				populateList(screensaver.getScreensaverParamList_12(),  0, 99, 1);

				TreeSet<Integer> screensaverTimeList = screensaver.getScreensaverTimeList();

				if(screensaverTimeList != null){
					screensaverTimeList.clear();
					for(int i = 5 ; i <= 500 ; i++){
						screensaverTimeList.add(i);
					}
				}

				final TreeSet<String> melodiesList = screensaver.getMelodiesList();

				if(melodiesList != null){

					melodiesList.clear();

					File songsFolder = getFolder(Constants.melodiesPath.value);

					if(songsFolder != null){
						File[] songsArray = songsFolder.listFiles();
						if(songsArray != null && songsArray.length > 0){
							for(File melody : songsArray){
								if(melody != null){
									String melodyName = melody.getName();
									if(melodyName != null && !melodyName.trim().isEmpty() && melodyName.trim().endsWith(".mp3")){

										String finalMelodyName = melodyName.substring(0, melodyName.indexOf(".mp3"));

										if(finalMelodyName != null && !finalMelodyName.trim().isEmpty()){
											melodiesList.add(finalMelodyName.trim());
										}	
									}
								}
							}
						}
					}

					if(melodiesList != null && !melodiesList.isEmpty()){
						for(String melody : melodiesList){
							if(melody != null && !melody.trim().isEmpty() && screensaver.getMelodieSelected() == null){
								screensaver.setMelodieSelected(melody);
								break;
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
	}

	public TreeMap<Integer, ScreensaverTable> loadResources(AppBean appBean, Screensaver screensaver, DAO dao){

		try{

			if(appBean != null && screensaver != null && dao != null){

				final TreeMap<Integer, ScreensaverTable> screensaverMap = appBean.getScreensaverMap();

				if(screensaverMap != null && screensaverMap.isEmpty()){
					screensaverMap.putAll(dao.getScreensaversMap(screensaver.getScreensaverCategoriesSelected()));
				}

				return screensaverMap;

			}

		}catch(Exception e){
			writeFile(e);
		}
		return null;
	}

	private void setPopupTextAlignment(Popup popup, Reference reference, Language language){

		try{
			if(popup != null){
				LinkedHashMap<String, String> popupTextAlignMap = popup.getPopupTextAlignMap();
				LinkedHashMap<String, String> referenceAlignPopupMap = reference.getReferenceAlignPopupMap();

				if(popupTextAlignMap != null){
					popupTextAlignMap.clear();
					popupTextAlignMap.put("left", language.getLeft());
					popupTextAlignMap.put("right", language.getRight());
					popupTextAlignMap.put("center", language.getCenter());
					popupTextAlignMap.put("justify", language.getJustify());
					popupTextAlignMap.put("initial", language.getInitial());
					popupTextAlignMap.put("inherit", language.getInherit());	
				}

				if(referenceAlignPopupMap != null){
					referenceAlignPopupMap.clear();
					referenceAlignPopupMap.put("left", language.getLeft());
					referenceAlignPopupMap.put("right", language.getRight());
					referenceAlignPopupMap.put("center", language.getCenter());
					referenceAlignPopupMap.put("justify", language.getJustify());
					referenceAlignPopupMap.put("initial", language.getInitial());
					referenceAlignPopupMap.put("inherit", language.getInherit());	
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
	}

	private void setPopupRadio(Popup popup, Language language){

		try{
			if(popup != null){

				popup.setCheckBoxScriptureTextLabel(language.getRadioScriptureText());
				popup.setCheckBoxPictureLabel(language.getPicture());
				popup.setCheckBoxUserMessageLabel(language.getRadioUserMessage());
			}
		}
		catch(Exception e){
			writeFile(e);
		}
	}

	public void populateList(TreeSet<Integer> list, int minValue, int maxValue, int step){

		try{
			if(list != null){
				list.clear();
				int stepBuffer = 0;
				for (int i = minValue ; i <= maxValue ; i++){
					++stepBuffer;
					if(stepBuffer == step){
						stepBuffer = 0;
						list.add(i);
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
	}

	public void populateList(LinkedHashSet<String> list, int minValue, int maxValue, int step){

		try{

			if(list != null){
				list.clear();
				int stepBuffer = 0;
				for (int i = minValue ; i <= maxValue ; i++){
					++stepBuffer;
					if(stepBuffer == step){
						stepBuffer = 0;
						list.add(i + "");
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
	}

	public TreeSet<String> getAllVersionsName(String dbPath){

		TreeSet<String> allVersions = new TreeSet<>();

		try{
			File checkDbPath = new File(dbPath);
			if(checkDbPath != null && checkDbPath.exists() && checkDbPath.isDirectory() && checkDbPath.canRead()){

				File[] files = checkDbPath.listFiles();

				if(files != null && files.length > 0){
					for(File file : files){
						if(file != null && file.isDirectory()){
							String name = file.getName();
							if(name != null && !name.trim().isEmpty()){
								allVersions.add(name.trim());
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return allVersions;
	}

	private void selectDefaultChurchAndEvent(Church church, String selectedChurch){

		try{

			if(church != null){

				List<String> churchListLabels = church.getChurches();

				if(selectedChurch != null && !selectedChurch.trim().isEmpty() && 
						churchListLabels != null && churchListLabels.contains(selectedChurch.trim())){

					church.setSelectedChurch(selectedChurch.trim());
				}
				else if (churchListLabels.size() > 1){
					church.setSelectedChurch(church.getChurchesLabel());
				}
				else{
					church.setSelectedChurch(church.getNoChurchLabel());
				}

				church.setSelectedEvent(church.getNoEventLabel());
			}
		}
		catch(Exception e){
			writeFile(e);
		}
	}

	public void setAllChurchAndEvents(Church church, String selectedChurch, String selectedEvent,DAO dao){

		try{

			if(church != null){

				ChurchTable userChurch = church.getUser();

				if(userChurch != null && (selectedChurch == null || selectedChurch.trim().isEmpty())){

					selectedChurch = userChurch.getUsername();
				}

				if(selectedChurch == null || selectedChurch.trim().isEmpty()){
					selectedChurch = church.getSelectedChurch();
				}

				if(selectedEvent == null || selectedEvent.trim().isEmpty()){
					selectedEvent = church.getSelectedEvent();
				}

				List<String> churchListLabels = church.getChurches();
				List<String> eventsListLabels = church.getEvents();

				if(eventsListLabels != null && churchListLabels != null){

					churchListLabels.clear();
					eventsListLabels.clear();

					selectDefaultChurchAndEvent(church, selectedChurch);

					Set<String> allChurch = dao.getColumn("select distinct username from " + ChurchTable.class.getName());

					if(allChurch != null){

						TreeSet<String> churchesWithEvents = new TreeSet<>();

						if(allChurch.isEmpty()){
							churchListLabels.add(church.getNoChurchLabel());
						}
						else{

							for(String ch : allChurch){
								if(ch != null && !ch.trim().isEmpty()){
									ChurchTable dbCh = dao.getUserByUsername(ch);

									List<Event> dbChEvents = dbCh.getEvents();
									if(dbChEvents != null && !dbChEvents.isEmpty()){
										churchesWithEvents.add(ch);
									}
								}
							}

							if(!churchesWithEvents.isEmpty()){
								churchListLabels.add(church.getChurchesLabel());
								churchListLabels.addAll(churchesWithEvents);
							}
							else{
								churchListLabels.add(church.getNoChurchLabel());
							}

							selectDefaultChurchAndEvent(church, selectedChurch);
						}

						if((selectedChurch == null || selectedChurch.trim().isEmpty()) && 
								churchesWithEvents != null && churchesWithEvents.size() == 1) {

							selectedChurch = churchesWithEvents.first();
						}

						ChurchTable user = dao.getUserByUsername(selectedChurch);

						if(user == null) {
							church.setSelectedEvent(null);
							eventsListLabels.add(church.getNoEventLabel());
						}
						else if(churchListLabels != null &&
								selectedChurch != null &&
								church.getSelectedChurch().equals(selectedChurch) && 
								churchListLabels.contains(selectedChurch)){

							List<Event> events = user.getEvents();

							if(events != null && !events.isEmpty()) {


								Set<String> allChurchEvents = new TreeSet<>();

								Event currentEvent = null;

								for(Event ev : events) {
									if(ev != null) {
										String eventName = ev.getEventName();
										if(eventName != null && !eventName.trim().isEmpty()) {
											allChurchEvents.add(eventName.trim());
											if(selectedEvent != null && 
													!selectedEvent.trim().isEmpty() && 
													eventName.trim().equals(selectedEvent.trim())) {
												church.setSelectedEvent(selectedEvent.trim());
												currentEvent = ev;
											}
										}
									}
								}

								if(allChurchEvents != null && !allChurchEvents.isEmpty()) {
									eventsListLabels.add(church.getEventsLabel());
									eventsListLabels.addAll(allChurchEvents);
								}

								if(currentEvent != null) {

									church.setSelectedEvent(selectedEvent.trim());
									church.setAddUserButton("true");
									List<Participant> participantsList = church.getParticipantsList();
									if(participantsList != null) {
										participantsList.clear();

										List<Participant> eventParticipantsList = currentEvent.getParticipantsList();

										if(eventParticipantsList != null && !eventParticipantsList.isEmpty()) {
											participantsList.addAll(eventParticipantsList);
										}
									}

									setTotalTime(church, currentEvent);

									church.setEventNameValue(currentEvent.getEventName());
									church.setEventDate(currentEvent.getEventDate());
									church.setEventHour(currentEvent.getEventHour());
									church.setSelectedDuration(currentEvent.getEventDuration());
									church.setEventDescription(currentEvent.getEventDescription());
								}
							}
							else {
								church.setSelectedEvent(null);
								eventsListLabels.add(church.getNoEventLabel());
							}
						}
					}
				}
				if(eventsListLabels != null && eventsListLabels.isEmpty()) {
					eventsListLabels.add(church.getNoEventLabel());
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
	}

	public void setTotalTime(Church church, Event event){

		try{

			int totalParticipantTime = 0;

			if(church != null && event != null){
				List<Participant> eventParticipantsList = event.getParticipantsList();
				if(eventParticipantsList != null && !eventParticipantsList.isEmpty()){
					for(Participant participant : eventParticipantsList){
						if(participant != null){
							totalParticipantTime += participant.getDuration();
						}
					}
				}
			}
			church.setTotalParticipantTime(totalParticipantTime);
		}
		catch(Exception e){
			writeFile(e);
		}
	}

	private void setTime(Church church){

		try{
			List<String> duration = church.getDuration();
			List<String> participantDuration = church.getParticipantDuration();

			if(duration != null){
				duration.clear();
				duration.add(church.getMinutes());
				for(int i = 5 ; i <= 10000 ; i++){
					if(i%5 == 0){
						duration.add(i + "");
					}
				}
			}
			if(participantDuration != null){
				participantDuration.clear();
				for(int i = 0 ; i <= 500 ; i++){
					participantDuration.add(i + "");
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}	
	}

	public void writeFile(Exception exception) {

		exception.printStackTrace();

		String path = bibleLog + platformType + "log.txt";

		File file = new File(path.trim());
		try{
			if (file != null && !file.exists()){
				file.createNewFile();
				file.setReadable(true);
				file.setWritable(true);
			}
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {

			StackTraceElement[] stackTrace = exception.getStackTrace();

			String stackTraceInfo = "\n\n" + (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date()) + " " + "\n\n";

			if(stackTrace != null && stackTrace.length > 0){
				for(StackTraceElement sT : stackTrace){
					if(sT != null){
						stackTraceInfo += sT.getClassName()+ "\n";
						stackTraceInfo += sT.getFileName()+ "\n";
						stackTraceInfo += sT.getMethodName()+ "\n";
						stackTraceInfo += sT.getLineNumber()+ "\n";
					}
				}
			}

			writer.write(stackTraceInfo + "\n" + exception.getMessage() + "\n" + exception.getCause() + "\n" + exception.getLocalizedMessage() + "\n\n" + "==========================================================\n");
			writer.flush();
		} catch (Exception ex) {
			//System.out.println(ex.getMessage());
		}
	}

	public void addPicture(Bible bible, final Church church, Settings settings, Popup popup, Reference reference, Screensaver screensaver, ApplicationCode code, Beans bean, AppBean appBean){

		try{

			if(bible != null && popup != null && screensaver != null && appBean != null){

				String pictureString = getPictureString(appBean);

				if(pictureString != null && !pictureString.trim().isEmpty()){

					String result = " <html> <head> <style> "                +
							" .imgDiv { "	                                                                                   +
							" width:  \"100%\"; "                                                                              +
							" height: \"100%\"; "                                                                              +
							" } </style> </head> <body bgcolor=\"" + popup.getPopupBackgroundColorPaletteSelected() + "\">" + pictureString +
							"</body> </html> ";

					setResults(bible, church, settings, popup, reference, screensaver, code, bean, result);
				}
			}
		} catch (Exception e) {
			writeFile(e);
		}
	}

	public String getScriptureImageDiv(Popup popup){

		String result = "";

		try{

			if(popup != null){
				result += " .imgDiv { "		  +
						" width: \"100%\"; "  +
						" height:\"100%\"; "  +
						addOpacity(popup.getImageOpacitySelected())+
						" } ";
			}
		} catch (Exception e) {
			writeFile(e);
		}
		return result;
	}

	private String getScriptureTextDiv(Popup popup){

		String result = "";

		try{

			if(popup != null){

				result = " .scriptureTextDiv {                                              "  +
						" position: absolute;                                               "  +
						" color: " + popup.getPopupTextColorPaletteSelected()+ ";           "  +
						" top: 0;                                                           "  +
						" left: 0;                                                          "  +
						" right: 0;                                                         "  +
						" width: \"100%\";                                                  "  +
						" font-size:       " + popup.getPopupFontSelected()          + "px; "  +
						" margin-top:      " + popup.getPopupMarginTopSelected()     + "px; "  +
						" margin-bottom:   " + popup.getPopupMarginBottomSelected()  + "px; "  +
						" margin-right:    " + popup.getPopupMarginRightSelected()   + "px; "  +
						" margin-left:     " + popup.getPopupMarginLeftSelected()    + "px; "  +
						" text-align:      " + popup.getPopupTextAlignSelected()     + ";   "  +
						" font-family:     " + popup.getFontFamilyPopupSelected()    + ";   "  +
						" font-style:      " + popup.getFontStylePopupSelected()     + ";   "  +
						" letter-spacing:  " + popup.getLetterSpacingPopupSelected() + "px; "  +
						" line-height:     " + popup.getLineHeightPopupSelected()    + ";   "  +
						" word-spacing:    " + popup.getWordSpacingPopupSelected()   + "px; ";

				if(popup.isPopupBoldSelected()){
					result += " font-weight: bold; ";  
				}

				result += addOpacity(popup.getScriptureTextOpacitySelected());

				result += " } ";
			}
		} catch (Exception e) {
			writeFile(e);
		}
		return result;
	}

	private String getScriptureReferenceDiv(Popup popup, Reference reference, AppBean appBean){

		String result = "";

		try{

			if(popup != null && reference != null && appBean != null){

				String verseValue = appBean.getVerseValue().trim();


				if(verseValue != null && !verseValue.trim().isEmpty()){

					result += " .referenceDiv {                                                             " +
							" position: absolute;                                                           " +
							" color: "         + reference.getReferenceColorPopupPaletteSelected_1() + ";   " +
							" top: 0;                                                                       " +
							" left: 0;                                                                      " +
							" right: 0;                                                                     " +
							" width: \"100%\";                                                              " +
							" font-size: "      + reference.getReferenceSizePopupSelected()          + "px; " +
							" margin-top: "     + popup.getPopupMarginTopSelected()                  + "px; " +
							" margin-right: "   + popup.getPopupMarginRightSelected()                + "px; " +
							" margin-left: "    + popup.getPopupMarginLeftSelected()                 + "px; " +
							" text-align: "     + reference.getReferenceAlignPopupSelected()         + ";   " +
							" font-style:   "   + reference.getReferenceFontStylePopupSelected()     + ";   " +
							" letter-spacing: " + reference.getReferenceLetterSpacingPopupSelected() + "px; " +
							" font-family:    " + reference.getReferenceFontFamilyPopupSelected()    + ";   " +
							" text-decoration: "+ reference.getReferenceFontDecorationPopupSelected()+ ";   ";

					result += " text-shadow: "                                + 
							reference.getReferenceParamSelected_1() + "px "   + 
							reference.getReferenceParamSelected_2() + "px "   + 
							reference.getReferenceParamSelected_3() + "px , " +
							reference.getReferenceParamSelected_4() + "px "   + 
							reference.getReferenceParamSelected_5() + "px "   + 
							reference.getReferenceParamSelected_6() + "px "   + 
							reference.getReferenceColorPopupPaletteSelected_2() + " ; ";

					if(reference.isReferencePopupBoldSelected()){
						result += " font-weight: bold; ";  
					}

					result += addOpacity(popup.getScriptureTextOpacitySelected());

					result += " } ";
				}
			}
		} catch (Exception e) {
			writeFile(e);
		}
		return result;
	}

	public boolean addUniqueScripturePopupText(final Church church, final Settings settings, final Popup popup, final Reference reference, final Bible bible, AppBean appBean, final Screensaver screensaver, final ApplicationCode code, final Beans bean){

		try{

			if(popup != null && bible != null && appBean != null && popup.isDisplayScriptureText()){

				String verseValue = appBean.getVerseValue().trim();

				if(verseValue != null && !verseValue.trim().isEmpty()){

					String result = getFirstDiv();

					String pictureString = null;

					if(popup.isDefaultPopupImageSelected()){

						pictureString = getDefaultPopupImage(appBean);

						if(pictureString != null && !pictureString.trim().isEmpty()){

							result += getScriptureImageDiv(popup);
						}
					}

					result += getScriptureReferenceDiv(popup, reference, appBean);
					result += getScriptureTextDiv(popup);

					result += " </style> </head> <body bgcolor=\"" + popup.getPopupBackgroundColorPaletteSelected() + "\">";

					result +=  " <div class=\"overlay\"> ";

					if(pictureString != null && !pictureString.trim().isEmpty() && popup.isDefaultPopupImageSelected()){

						result += pictureString;
					}

					String selectedVerse = bible.getSelectedVerse();
					String verseReferences = getReferenceText(reference, bible, bible.getSelectedVersion(), bible.getSelectedBook(), bible.getSelectedChapter(), bible.getSelectedVerse());

					if(verseReferences != null && 
							!verseReferences.trim().isEmpty() && 
							selectedVerse != null && 
							!selectedVerse.trim().isEmpty() && 
							selectedVerse.matches("\\d+") &&
							!verseValue.contains("referenceDiv")
							){

						result += setDivSettings(popup, reference, "referenceDiv", verseReferences, false);
					}

					if(!verseValue.contains("scriptureTextDiv")){
						result +=  setDivSettings(popup, reference, "scriptureTextDiv", verseValue.trim(), true);
					}

					if(verseValue.contains("referenceDiv") && verseValue.contains("scriptureTextDiv")){
						result += verseValue;
					}

					result +=  " </div> </body> </html>";

					setResults(bible, church, settings, popup, reference, screensaver, code, bean, result);

					return true;
				}
				else{
					emptySelection(appBean);
				}
			}
			else{
				emptySelection(appBean);
			}
		} catch (Exception e) {
			writeFile(e);
		}
		return false;
	}

	public String getFirstDiv(){

		String result = null;

		try{
			result = "<html> <head>  <style type=\"text/css\"> "   +
					" html, body  {                 "   +
					" height: 100%;                 "   +
					" margin: 0px;                  "   +
					" *:fullscreen                  " 	+
					" *:-ms-fullscreen,             "   +
					" *:-webkit-full-screen,        " 	+
					" *:-moz-full-screen {          " 	+
					"    overflow: auto !important; "   +
					" }                             "   +
					" }                             " 	+
					" .overlay{                     " 	+
					" position: absolute;           "   +
					" width:100%;                   " 	+
					" height:100%;                  " 	+
					" top:0px;                      " 	+
					" left:0px;                     " 	+
					" z-index:1000;                 " 	+
					" *:fullscreen                  " 	+
					" *:-ms-fullscreen,             "   +
					" *:-webkit-full-screen,        " 	+
					" *:-moz-full-screen {          " 	+
					"    overflow: auto !important; "   +
					" }                             "   +
					" }                             ";


		} catch (Exception e) {
			writeFile(e);
		}
		return result;
	}

	public boolean addMultipleScripturePopupText(final Church church, final Settings settings, final ApplicationCode code, Popup popup, final Reference reference, Bible bible, Screensaver screensaver, AppBean appBean, Beans bean){

		try{

			if(popup != null && bible != null && appBean != null){

				String result = getFirstDiv();

				String pictureString = null;

				if(popup.isDefaultPopupImageSelected()){

					pictureString = getDefaultPopupImage(appBean);
				}
				else if(popup.isDisplayPicture()){

					pictureString = getPictureString(appBean);
				}

				if(pictureString != null && !pictureString.trim().isEmpty()){
					result += getScriptureImageDiv(popup);
				}

				if(popup.isDisplayScriptureText()){

					result += getScriptureReferenceDiv(popup, reference, appBean);
					result += getScriptureTextDiv(popup);
				}

				String verseValue = appBean.getVerseValue().trim();

				if(popup.isDisplayUserMessage()){

					String userMessage = popup.getPopupUserMessageSelected();

					if(userMessage != null && !userMessage.trim().isEmpty()){

						if(!result.contains(".scriptureTextDiv")){
							result += getScriptureTextDiv(popup);
						}
						if(userMessage.contains("\r\n")){
							userMessage = userMessage.replace("\r\n", "<br>");
						}
						if(verseValue == null || verseValue.trim().isEmpty()){
							verseValue = setDivSettings(popup, reference, "scriptureTextDiv", userMessage, false);
						}
						else if(verseValue.contains("</div>")){
							int index = verseValue.lastIndexOf("</div>");
							verseValue = verseValue.substring(0, index) + userMessage + verseValue.substring(index);
						}
						else{
							verseValue = setDivSettings(popup, reference, "scriptureTextDiv", verseValue.trim() + userMessage, true);
						}
					}
				}

				boolean displayPicture = pictureString != null && !pictureString.trim().isEmpty() && (popup.isDefaultPopupImageSelected() || popup.isDisplayPicture());
				boolean displayText = verseValue != null && !verseValue.trim().isEmpty();

				if(displayText || displayPicture){

					result += " </style> </head> <body bgcolor=\"" + popup.getPopupBackgroundColorPaletteSelected() + "\">";
					result +=  " <div class=\"overlay\"> ";

					if(displayPicture){

						result += pictureString;
					}

					if(displayText){

						String selectedVerse = bible.getSelectedVerse();
						String verseReferences = getReferenceText(reference, bible, bible.getSelectedVersion(), bible.getSelectedBook(), bible.getSelectedChapter(), bible.getSelectedVerse());

						if(verseReferences != null && 
								!verseReferences.trim().isEmpty() && 
								selectedVerse != null && 
								!selectedVerse.trim().isEmpty() && 
								selectedVerse.matches("\\d+") &&
								!verseValue.contains("referenceDiv")
								){

							result += setDivSettings(popup, reference, "referenceDiv", verseReferences, false);
						}

						if(!verseValue.contains("scriptureTextDiv")){
							result +=  setDivSettings(popup, reference, "scriptureTextDiv", verseValue.trim(), true);
						}

						if(verseValue.contains("referenceDiv") || verseValue.contains("scriptureTextDiv")){
							result += verseValue;
						}
					}

					result +=  " </div> </body> </html>";

					setResults(bible, church, settings, popup, reference, screensaver, code, bean, result);
				}
				else{
					emptySelection(appBean);
				}
			}
			else{
				emptySelection(appBean);
			}
		} catch (Exception e) {
			writeFile(e);
		}
		return false;
	}

	private void setResults(Bible bible, Church church, Settings settings, Popup popup, Reference reference, Screensaver screensaver, ApplicationCode code, Beans bean, String result){

		try{
			switch(bean){

			case bible :{

				if(bible != null){
					bible.setPopupInformation(result);
				}

				if(popup != null){
					popup.setPopupInformation(null);	
				}

				if(reference != null){
					reference.setPopupInformation(null);
				}

				if(screensaver != null){
					screensaver.setPopupInformation(null);
				}

				if(code != null){
					code.setPopupInformation(null);
				}

				break;
			}
			case church :{

				if(bible != null){
					bible.setPopupInformation(null);
				}

				if(popup != null){
					popup.setPopupInformation(null);	
				}

				if(reference != null){
					reference.setPopupInformation(null);
				}

				if(screensaver != null){
					screensaver.setPopupInformation(null);
				}

				if(code != null){
					code.setPopupInformation(null);
				}

				break;
			}
			case settings :{

				if(bible != null){
					bible.setPopupInformation(null);
				}

				if(popup != null){
					popup.setPopupInformation(null);	
				}

				if(reference != null){
					reference.setPopupInformation(null);
				}

				if(screensaver != null){
					screensaver.setPopupInformation(null);
				}

				if(code != null){
					code.setPopupInformation(null);
				}

				break;
			}
			case popup :{

				if(bible != null){
					bible.setPopupInformation(null);
				}

				if(popup != null){
					popup.setPopupInformation(result);	
				}

				if(reference != null){
					reference.setPopupInformation(null);
				}

				if(screensaver != null){
					screensaver.setPopupInformation(null);
				}

				if(code != null){
					code.setPopupInformation(null);
				}

				break;
			}
			case reference :{

				if(bible != null){
					bible.setPopupInformation(null);
				}

				if(popup != null){
					popup.setPopupInformation(null);	
				}

				if(reference != null){
					reference.setPopupInformation(result);
				}

				if(screensaver != null){
					screensaver.setPopupInformation(null);
				}

				if(code != null){
					code.setPopupInformation(null);
				}

				break;
			}
			case screensaver :{

				if(bible != null){
					bible.setPopupInformation(null);
				}

				if(popup != null){
					popup.setPopupInformation(null);	
				}

				if(reference != null){
					reference.setPopupInformation(null);
				}

				if(screensaver != null){
					screensaver.setPopupInformation(result);
					screensaver.setOpenPopup(true);
				}

				if(code != null){
					code.setPopupInformation(null);
				}

				break;
			}
			case code :{

				if(bible != null){
					bible.setPopupInformation(null);
				}

				if(popup != null){
					popup.setPopupInformation(null);	
				}

				if(reference != null){
					reference.setPopupInformation(null);
				}

				if(screensaver != null){
					screensaver.setPopupInformation(null);
				}

				if(code != null){
					code.setPopupInformation(result);
				}

				break;
			}
			}	
		} catch (Exception e) {
			writeFile(e);
		}
	}

	public String setDivSettings(Popup popup, Reference reference, String divName, String text, boolean isVerse){

		String result = "";

		try{
			if(text != null && !text.trim().isEmpty() && popup != null){

				int verseFont = 0;
				int referenceFont = reference.getReferenceSizePopupSelected();
				String verseFontSelected = popup.getPopupFontSelected();

				if(verseFontSelected != null && verseFontSelected.trim().matches("\\d+")){
					verseFont = Integer.parseInt(verseFontSelected.trim());
				}

				String localText = text.trim();

				int upN = reference.getReferenceEnterUpPopupSelected();

				if(isVerse){

					if(upN > 0){
						localText = addVerseEnter(localText, upN, verseFont, referenceFont);
					}

					localText = addVerseEnter(localText, 1, verseFont, referenceFont);

					int downN = reference.getReferenceEnterDownPopupSelected();

					if(downN > 0){
						localText = addVerseEnter(localText, downN, verseFont, referenceFont);
					}
				}
				else{
					if(upN > 0){
						localText = addEnter(localText, upN);
					}
				}

				result += " <div class=\"" + divName + "\">" + localText + " </div> ";
			}
		} catch (Exception e) {
			writeFile(e);
		}
		return result;
	}

	private String addVerseEnter(String text, int number, int verseFont, int referenceFont){

		String localText = text;

		try{
			if(verseFont > 0 && verseFont > referenceFont){
				localText = addEnter(localText, number);
			}
			else if(referenceFont > 0 && referenceFont >= verseFont && verseFont > 0){
				localText = addEnter(localText, (referenceFont/verseFont) * number);
			}
		} catch (Exception e) {
			writeFile(e);
		}

		return localText;
	}

	private String addEnter(String text, int number){

		String localText = text;

		try{
			if(number > 0){
				for(int i = 1; i <= number; i++){
					localText = "<br>" + localText;
				}
			}
		} catch (Exception e) {
			writeFile(e);
		}

		return localText;
	}

	public String getReferenceText(Reference reference, Bible bible, String version, String book, String chapter, String verse){

		String result = "";

		try{

			if(reference != null){
				if(reference.isReferenceDisplayVersionSelected() && isRealVersion(version, bible)){
					result += version;
					result += getSpaceReference(reference.getReferenceVersionSpaceSelected());
				}

				if(reference.isReferenceDisplayBookSelected() && isRealBook(book, bible)){
					String bookName = book;
					if(bookName.contains("_")){
						String bN = bookName.substring(0, bookName.indexOf("_"));
						if(bN != null && !bN.trim().isEmpty() && bN.matches("\\d+")){
							bookName = bookName.substring(bookName.indexOf("_") + 1);
						}
					}
					result += bookName;
					result += getSpaceReference(reference.getReferenceBookSpaceSelected());
				}
				boolean existChapter = false;
				if(reference.isReferenceDisplayChapterSelected() && isRealChapter(chapter, bible)){
					result += chapter;
					result += getSpaceReference(reference.getReferenceChapterSpaceSelected());
					existChapter = true;
				}
				if(reference.isReferenceDisplayVerseSelected() && isRealVerse(verse, bible)){
					if(existChapter){
						result += ".";
						result += getSpaceReference(reference.getReferenceDotsSpaceSelected());
					}
					result += verse;
				}
			}
		} catch (Exception e) {
			writeFile(e);
		}
		return result;
	}

	public String getSpaceReference(int spaceN){

		String result = "";

		try{

			if(spaceN > 0){
				for(int i = 1; i <= spaceN; i++){
					result += "&nbsp;";
				}
			}
		} catch (Exception e) {
			writeFile(e);
		}
		return result;
	}

	private void addUserMessage(AppBean appBean, Bible bible, final Church church, final Settings settings, final Reference reference, final Screensaver screensaver, Popup popup, ApplicationCode code, Beans bean){

		try{

			popup.setDefaultPopupImageSelected(false);
			String userMessage = popup.getPopupUserMessageSelected();

			if(userMessage != null && !userMessage.trim().isEmpty()){

				if(userMessage.contains("\r\n")){
					userMessage = userMessage.replace("\r\n", "<br>");
				}

				String result = getFirstDiv();
				result += getScriptureTextDiv(popup);	
				result += " </style> </head> <body bgcolor=\"" + popup.getPopupBackgroundColorPaletteSelected() + "\">";
				result += " <div class=\"overlay\"> ";
				result += setDivSettings(popup, reference, "scriptureTextDiv", userMessage, false);
				result +=  " </div> </body> </html>";

				setResults(bible, church, settings, popup, reference, screensaver, code, bean, result);

				setDefaultPopupImageColor(popup);
			}
		} catch (Exception e) {
			writeFile(e);
		}
	}

	public void cleanPopup(Bible bible, Church church, Settings settings, Popup popup, Reference reference, Screensaver screensaver, final ApplicationCode code, final Voice voice, ThankYou thankYou, Feedback feedback, boolean openPopup){

		try{
			if(bible != null){
				bible.setPopupInformation(null);
				bible.setOpenPopup(openPopup);
			}

			if(church != null){
				church.setOpenPopup(openPopup);
			}

			if(settings != null){
				settings.setOpenPopup(openPopup);
			}

			if(popup != null){
				popup.setPopupInformation(null);
				popup.setOpenPopup(openPopup);
			}

			if(reference != null){
				reference.setPopupInformation(null);
				reference.setOpenPopup(openPopup);
			}

			if(screensaver != null){
				screensaver.setPopupInformation(null);
				screensaver.setOpenPopup(openPopup);
			}

			if(code != null){
				code.setPopupInformation(null);
				code.setOpenPopup(openPopup);
			}

			if(voice != null){
				voice.setPopupInformation(null);
				voice.setOpenPopup(openPopup);
			}

			if(thankYou != null){
				thankYou.setPopupInformation(null);
				thankYou.setOpenPopup(openPopup);
			}

			if(feedback != null){
				feedback.setOpenPopup(openPopup);
			}
		} catch (Exception e) {
			writeFile(e);
		}
	}

	public void emptySelection(AppBean appBean){

		try{
			if(appBean != null){
				appBean.setVerseValue(null);
				LinkedHashMap<String, String> versesMap = appBean.getVersesMap();

				if(versesMap != null){
					versesMap.clear();
				}
			}
		} catch (Exception e) {
			writeFile(e);
		}
	}

	public void setText(Bible bible, Settings settings, Popup popup, AppBean appBean){

		try{
			emptySelection(appBean);

			LinkedHashMap<String, Map<String, String>> verseValue = bible.getVerseValue();

			String text = "";
			if(verseValue != null && !verseValue.isEmpty() && popup.isDisplayScriptureText()){

				LinkedHashMap<String, Map<String, String>> newVerseUnboldValue = new LinkedHashMap<>();

				for(String verse : verseValue.keySet()){
					if(verse != null && !verse.trim().isEmpty()){
						Map<String, String> references = verseValue.get(verse);
						String unBoldVerse = (verse.replace("<b>", "")).replace("</b>", "");

						newVerseUnboldValue.put(unBoldVerse, references);

						text += verse.trim() + "<br><br>";
					}
				}

				bible.setVerseValue(newVerseUnboldValue);

				if(text.contains("<b>")){
					text = text.replace("<b>", "");	
				}
				if(text.contains("</b>")){
					text = text.replace("</b>", "");	
				}
			}

			appBean.setVerseValue(text);

		} catch (Exception e) {
			writeFile(e);
		}
	}

	public void setPopupText(Bible bible, final Church church, Reference reference, Settings settings, Popup popup, Screensaver screensaver, AppBean appBean, final ApplicationCode code, final Beans bean){

		try{

			if(bible != null && popup != null && appBean != null){

				String verseValue = appBean.getVerseValue();

				if(verseValue == null || verseValue.trim().isEmpty()){
					setText(bible, settings, popup, appBean);	
				}

				int multiple = 0;

				if(popup.isDisplayScriptureText()){
					verseValue = appBean.getVerseValue();
					if(verseValue != null && !verseValue.trim().isEmpty()){
						++multiple;	
					}
				}

				if(popup.isDisplayUserMessage()){
					++multiple;
				}

				if(popup.isDisplayPicture() || popup.isDefaultPopupImageSelected()){
					++multiple;
				}

				if(multiple == 1){
					setUniquePopupFacilitie(church, settings, popup, reference, bible, screensaver, appBean, code, bean);
				}
				else if(multiple > 1){
					addMultipleScripturePopupText(church, settings, code, popup, reference, bible, screensaver, appBean, bean);
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
	}

	private void setUniquePopupFacilitie(final Church church, final Settings settings, final Popup popup, final Reference reference, Bible bible, final Screensaver screensaver, final AppBean appBean, final ApplicationCode code, final Beans bean){

		try{

			if(popup.isDisplayPicture()){
				addPicture(bible, church, settings, popup, reference, screensaver, code, bean, appBean);
			}
			else if(popup.isDisplayUserMessage()){
				addUserMessage(appBean, bible, church, settings, reference, screensaver, popup, code, bean);
			}
			else if(popup.isDisplayScriptureText()){
				setDefaultPopupImageColor(popup);
				addUniqueScripturePopupText(church, settings, popup, reference, bible, appBean, screensaver, code, bean);
			}
		}
		catch(Exception e){
			writeFile(e);
		}
	}

	private String addOpacity(int opacity){

		String result = "";

		try{
			if(opacity < 10){
				result += " filter: alpha(opacity=" + opacity +"0); "  +
						" -khtml-opacity: 0." + opacity +"; "          +
						" -moz-opacity: 0." + opacity +"; "            +
						" opacity: 0." + opacity +"; ";
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return result;
	}

	public String addMargins(Popup popup){

		String result = "";

		try{
			if(popup != null){
				result += " margin-top:  " + popup.getPopupMarginTopSelected()    + "px; " +
						" margin-bottom: " + popup.getPopupMarginBottomSelected() + "px; " +
						" margin-right:  " + popup.getPopupMarginRightSelected()  + "px; " +
						" margin-left:   " + popup.getPopupMarginLeftSelected()   + "px; ";
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return result;
	}

	private String getDefaultPopupImage(AppBean appBean){

		String result = "";

		try{

			result += " <div class=\"imgDiv\">";

			String fileSelected = picturesPath + platformType + "bible" + platformType + "38.jpg";
			String format = fileSelected.substring(fileSelected.lastIndexOf(".") + 1);
			String imageText = null;

			File picture = new File(fileSelected);

			if(picture != null && picture.exists() && picture.isFile()){

				byte[] imageBytes = Files.readAllBytes(Paths.get(fileSelected));

				if(imageBytes != null && imageBytes.length > 0){

					imageText = Base64.getEncoder().encodeToString(imageBytes);
				}
			}

			if(imageText != null && !imageText.trim().isEmpty() && format != null && !format.trim().isEmpty()){

				result += "<img src=\"data:image/" + format + ";base64," + imageText + "\" width=\"100%\"\" height=\"100%\"/>";
				result += " </div> ";

				return result;
			}
		}
		catch(Exception e){
			writeFile(e);
		}

		return null;
	}

	public String getPictureString(AppBean appBean){

		String result = "";

		try{

			Map<String, String> selectedImagesMap = appBean.getSelectedImagesMap();

			if(selectedImagesMap != null && !selectedImagesMap.isEmpty()){
				result += " <div class=\"imgDiv\">";
				for(String fileName : selectedImagesMap.keySet()){
					if(fileName != null && !fileName.trim().isEmpty() && fileName.contains(".")){
						String picture = selectedImagesMap.get(fileName);
						String format = fileName.substring(fileName.lastIndexOf(".") + 1);
						if(picture != null && !picture.trim().isEmpty() && format != null && !format.trim().isEmpty()){
							result += "<img src=\"data:image/" + format + ";base64," + picture + "\" width=\"100%\"\" height=\"100%\"/><br><br>";
						}
					}
				}
				result += " </div>";
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return result;
	}

	public boolean isRealVersion(String version, Bible bible){

		try{
			if(version != null && 
					!version.trim().isEmpty() &&
					!version.trim().equalsIgnoreCase(bible.getNoVersion()) &&
					!version.trim().equalsIgnoreCase(bible.getSelectVersion())
					){
				return true;
			}
		}
		catch(Exception e){
			writeFile(e);
			return false;
		}
		return false;
	}

	public boolean isRealBook(String book, Bible bible){

		try{
			if(book != null && 
					!book.trim().isEmpty() &&
					!book.trim().equalsIgnoreCase(bible.getNoBook()) &&
					!book.trim().equalsIgnoreCase(bible.getSelectBook())
					){
				return true;
			}
		}
		catch(Exception e){
			writeFile(e);
			return false;

		}
		return false;
	}

	public boolean isRealChapter(String chapter, Bible bible){

		try{
			if(chapter != null && 
					!chapter.trim().isEmpty() &&
					!chapter.trim().equalsIgnoreCase(bible.getNoChapter()) &&
					!chapter.trim().equalsIgnoreCase(bible.getSelectChapter()) &&
					chapter.matches("\\d+")
					){
				return true;
			}
		}
		catch(Exception e){
			writeFile(e);
			return false;

		}
		return false;
	}

	public boolean isRealVerse(String verse, Bible bible){

		try{
			if(verse != null && 
					!verse.trim().isEmpty() &&
					!verse.trim().equalsIgnoreCase(bible.getNoVerse()) &&
					!verse.trim().equalsIgnoreCase(bible.getSelectVerse()) &&
					verse.matches("\\d+")
					){
				return true;
			}
		}
		catch(Exception e){
			writeFile(e);
			return false;

		}
		return false;
	}

	public boolean isSetSelectedVerse(Bible bible, String version, String realBook, String chapter, String verseN){

		try{
			bible.setSelectedVersion(version);
			bible.setSelectedBook(realBook.substring(realBook.trim().indexOf("_") + 1).trim());
			bible.setSelectedChapter(chapter);
			bible.setSelectedVerse(verseN);

			LinkedHashSet<String> booksSet = getList(bible.getSelectBook());
			LinkedList<String> selectedVersionBooks = getVersionBooks(version, false);

			if(selectedVersionBooks != null && !selectedVersionBooks.isEmpty()){
				for(String identifiedBook : selectedVersionBooks){
					booksSet.add(identifiedBook);
				}
			}

			if(booksSet.size() == 1){
				bible.setBooks(getList(bible.getNoBook()));
				bible.setChapters(getList(bible.getNoChapter()));
				bible.setVerses(getList(bible.getNoVerse()));
			}
			else{
				bible.setBooks(booksSet);	

				LinkedHashSet<String> allChapters = getList(bible.getSelectChapter());
				TreeSet<Integer> chaptersSet = getBookChapters(version, realBook);

				if(allChapters != null){
					for(Integer ch : chaptersSet){
						allChapters.add(ch + "");
					}
				}

				if(allChapters.size() == 1){
					bible.setChapters(getList(bible.getNoChapter()));
					bible.setVerses(getList(bible.getNoVerse()));
				}
				else{
					bible.setChapters(allChapters);

					String chapterPath = dbPath + platformType + version + platformType + realBook + platformType + chapter + ".txt";

					File chapterFile = getFile(chapterPath);
					if(chapterFile != null && chapterFile.isFile()){
						setAllVerses(bible, new String(Files.readAllBytes(Paths.get(chapterPath)), StandardCharsets.UTF_8));
					}
					return true;
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return false;
	}

	public LinkedList<String> getVersionBooks(String version, boolean fullPath){

		LinkedList<String> versionBooks = new LinkedList<>();

		try{
			File checkDbPath = getFile(dbPath + platformType + version);
			if(checkDbPath != null && checkDbPath.exists() && checkDbPath.canRead() && checkDbPath.isDirectory()){

				File[] files = checkDbPath.listFiles();
				TreeMap<Integer, String> bufferSort = new TreeMap<>();
				if(files != null && files.length > 0 && bufferSort != null){

					for(File file : files){
						if(file != null && file.isDirectory()){
							String name = file.getName();
							if(name != null && !name.trim().isEmpty() && name.trim().contains("_") && !name.trim().startsWith("0")){
								String verseN = name.substring(0, name.indexOf("_"));
								if(verseN != null && verseN.trim().matches("\\d+")){
									bufferSort.put(Integer.parseInt(verseN.trim()), name.trim());
								}
							}
						}
					}
					if(!bufferSort.isEmpty()){
						if(fullPath){
							bufferSort.forEach((vN, book) -> versionBooks.add(book));
						}
						else{
							bufferSort.forEach((vN, book) -> versionBooks.add(book.substring(book.indexOf("_") + 1)));
						}
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return versionBooks;
	}

	public TreeSet<Integer> getBookChapters(String version, String bookName){

		try{
			if(version != null && !version.trim().isEmpty() && bookName != null && !bookName.trim().isEmpty()){
				File chaptersPath = getFolder(dbPath + platformType + version + platformType + bookName);
				if(chaptersPath != null){
					File[] chapters = chaptersPath.listFiles();
					if(chapters != null && chapters.length > 0){
						TreeSet<Integer> chaptersList = new TreeSet<>();
						for(File chapter : chapters){
							if(chapter != null){
								String chapterN = chapter.getName();
								if(chapterN != null && !chapterN.trim().isEmpty() && chapterN.contains(".")){

									String cN = chapterN.trim().substring(0, chapterN.indexOf("."));

									if(cN != null && !cN.trim().isEmpty() && cN.trim().matches("\\d+")){
										int chapterValue = Integer.parseInt(cN.trim());
										if(chapterValue > 0){
											chaptersList.add(chapterValue);	
										}
									}
								}
							}
						}
						return chaptersList;
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return null;
	}

	public File getFile(String filePath){

		try{
			if(filePath != null && !filePath.trim().isEmpty()){
				File file = new File(filePath.trim());
				if(file != null && file.exists() && file.canRead()){
					return file;
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return null;
	}

	public void setAllVerses(Bible bible, String chapter){

		try{
			if(bible != null && chapter != null && !chapter.trim().isEmpty()){
				LinkedHashSet<String> allVerses = getList(bible.getSelectVerse());

				String[] verses = chapter.split("<br>");

				if(verses != null && verses.length > 0){
					TreeSet<Integer> versesSet = new TreeSet<>();
					for(String verse : verses){
						if(verse != null && verse.contains(".")){
							String vN = verse.substring(0, verse.indexOf("."));
							if(vN != null && vN.trim().matches("\\d+")){
								int verseN = Integer.parseInt(vN.trim());
								if(verseN > 0){
									versesSet.add(verseN);
								}
							}
						}
					}

					for(Integer verse : versesSet){
						if(verse > 0){
							allVerses.add(verse + "");
						}
					}
				}

				if(allVerses.size() == 1){
					bible.setVerses(getList(bible.getNoVerse()));
				}
				else{
					bible.setVerses(allVerses);
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
	}

	private File getFolder(String filePath){

		try{
			if(filePath != null && !filePath.trim().isEmpty()){
				File folder = new File(filePath.trim());
				if(folder != null && folder.exists() && folder.canRead() && folder.isDirectory()){
					return folder;
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return null;
	}
	public int getBookNumber(String version, String bookName){

		try{
			if(version != null && !version.trim().isEmpty() && bookName != null && !bookName.trim().isEmpty()){
				File versionPath = getFolder(dbPath + platformType + version);
				if(versionPath != null && versionPath.exists() && versionPath.isDirectory()){
					File[] books = versionPath.listFiles();
					if(books != null && books.length > 0){
						for(File book : books){
							if(book != null && book.exists() && book.isDirectory()){
								String name = book.getName();
								if(name != null && !name.trim().isEmpty() && name.contains("_")){

									String bN = name.trim().substring(0, name.indexOf("_"));
									String bookN = name.trim().substring(name.indexOf("_") + 1, name.length());

									if(bN != null                     && 
											bookN != null             && 
											!bN.trim().isEmpty()      && 
											!bookN.trim().isEmpty()   && 
											bN.trim().matches("\\d+") && 
											(name.trim().equals(bookName) || bN.trim().equals(bookName) || bookN.trim().equals(bookName))
											){
										int bookValue = Integer.parseInt(bN.trim());
										if(bookValue > 0){
											return bookValue;	
										}
									}
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return -1;
	}

	public boolean isBibleVerse(AppBean appBean, Bible bible, String version, String book, String chapter, String verse){

		try{
			if(version != null && book != null && chapter != null && verse != null && chapter.trim().matches("\\d+") && verse.trim().matches("\\d+")){
				File versionFile = new File(dbPath + platformType + version);
				if(versionFile != null && versionFile.exists() && versionFile.canRead() && versionFile.isDirectory()){

					File[] bookFiles = versionFile.listFiles();
					if(bookFiles != null && bookFiles.length > 0){
						for(File bookFile : bookFiles){
							if(bookFile != null && bookFile.isDirectory()){
								String bookName = bookFile.getName();
								if(bookName != null && !bookName.trim().isEmpty() && bookName.trim().contains("_") && !bookName.trim().startsWith("0")){
									String bookN = bookName.substring(0, bookName.indexOf("_"));
									if(bookN != null && bookN.trim().matches("\\d+") && bookN.equals(book)){
										File chapterFile = new File(bookFile.getAbsolutePath());
										if(chapterFile != null && chapterFile.exists() && chapterFile.canRead() && chapterFile.isDirectory()){
											File[] chapterFiles = chapterFile.listFiles();
											if(chapterFiles != null && chapterFiles.length > 0){
												for(File chapterF : chapterFiles){
													if(chapterF != null && chapterF.isFile()){
														String chapterName = chapterF.getName();
														if(chapterName != null && !chapterName.trim().isEmpty() && chapterName.contains(".")){
															String chapterNumber = chapterName.substring(0, chapterName.indexOf("."));
															if(chapterNumber.equals(chapter)){
																String text = new String(Files.readAllBytes(Paths.get(chapterF.getAbsolutePath())), StandardCharsets.UTF_8);
																if(text != null && !text.trim().isEmpty()){
																	String[] verseArray = text.split("<br>");
																	for(String verseText : verseArray){
																		if(verseText != null && !verseText.trim().isEmpty() && verseText.trim().startsWith(verse + ".")){
																			String verseValue = verseText.substring(verseText.indexOf(".") + 1, verseText.length());
																			while(verseValue.startsWith("&nbsp;")){
																				verseValue = verseValue.substring(verseValue.indexOf("&nbsp;") + 6, verseValue.length());
																			}
																			if(verseValue != null && !verseValue.trim().isEmpty()){

																				appBean.setVerseValue(verseValue);

																				if(bible != null){
																					isSetSelectedVerse(bible, version, bookName, chapter, verse);
																				}

																				return true;
																			}
																			break;
																		}
																	}
																}
																break;
															}
														}
													}
												}
											}
										}
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}

		return false;
	}

	public String getVerse(String version, String book, String chapter, String verseN, Bible bible){

		try{
			if(version != null && !version.trim().isEmpty() && 
					book != null && !book.trim().isEmpty() &&
					chapter != null && !chapter.trim().isEmpty() && chapter.matches("\\d+") &&
					verseN != null && !verseN.trim().isEmpty() && verseN.matches("\\d+")){

				String chapterPath = dbPath + platformType + version + platformType + book + platformType + chapter + ".txt";

				File chapterFile = getFile(chapterPath);
				if(chapterFile != null && chapterFile.isFile()){

					String text = new String(Files.readAllBytes(Paths.get(chapterPath)), StandardCharsets.UTF_8);
					if(text != null && !text.trim().isEmpty()){
						String[] verseArray = text.split("<br>");
						for(String verse : verseArray){
							if(verse != null && !verse.trim().isEmpty() && verse.trim().startsWith(verseN + ".")){

								String finalVerse = verse.substring(verse.indexOf(".") + 1);

								if(finalVerse != null && !finalVerse.trim().isEmpty() && bible != null){
									setAllVerses(bible, text);
								}

								return finalVerse;
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return null;
	}

	public void setDefaultPopupImageColor(Popup popup){

		try{
			if(popup != null){

				if(!popup.isDefaultPopupImageSelected()){

					String popupBackgroundColorPaletteSelected = popup.getPopupBackgroundColorPaletteSelected();
					String popupTextColorPaletteSelected       = popup.getPopupTextColorPaletteSelected();

					if(popupBackgroundColorPaletteSelected != null && 
							popupTextColorPaletteSelected != null && 
							popupBackgroundColorPaletteSelected.equals(popupTextColorPaletteSelected)
							){
						popup.setPopupBackgroundColorPaletteSelected("#ffffff");
						popup.setPopupTextColorPaletteSelected("#000000");
					}
				}
			}
		}catch(Exception e){
			writeFile(e);
		}
	}

	private String accesSettingsByNo(ModelMap model, Settings settings, Popup popup, Reference reference, AppBean  app){

		try{
			int lastSettings = app.getLastSettings();

			switch(lastSettings) {
			case 1 :{
				setModel(model, null, null, settings, null, null, null, null, null, null, null);
				return "settings";
			}
			case 2 :{
				setModel(model, null, null, null, popup, null, null, null, null, null, null);
				return "popup";
			}
			case 3 :{
				setModel(model, null, null, null, null, reference, null, null, null, null, null);
				return "reference";
			}
			}

		}catch(Exception e){
			writeFile(e);
		}

		return null;
	}

	public String goToSettingsPage(ModelMap model, Settings settings, Popup popup, Reference reference, Voice voice, AppBean  app, String audioRequest) {

		try {

			if(audioRequest == null){
				return accesSettingsByNo(model, settings, popup, reference,  app);
			}
			else if(contain(voice.getSettings(), audioRequest) || 
					contain(audioRequest, voice.getSettings()) || 
					contain("sector", audioRequest)            || 
					contain(audioRequest, "sector")            ||
					contain(audioRequest, "sitting")           ||
					contain(audioRequest, "XV")                ||
					contain(audioRequest, "cities")            ||
					contain("15", audioRequest)){

				return accesSettingsByNo(model, settings, popup, reference,  app);
			}
			else if(contain(voice.getPopup(), audioRequest) ||
					contain(audioRequest, voice.getPopup()) ||
					contain("ferestre", audioRequest)       ||
					contain(audioRequest, "ferestre")){

				app.setLastSettings(2);
				setModel(model, null, null, null, popup, null, null, null, null, null, null);
				return "popup";
			}
			else if(contain(voice.getReference(), audioRequest) ||
					contain(audioRequest, voice.getReference())){

				app.setLastSettings(3);
				setModel(model, null, null, null, null, reference, null, null, null, null, null);
				return "reference";
			}
		}catch(Exception e){
			writeFile(e);
		}
		return null;
	}

	public ModelAndView navigateByVoice(Voice voice, final Bible bible, final Settings settings, final Popup popup, final Reference reference, final Church church, final Screensaver screensaver, ApplicationCode code, final AppBean app, ModelMap model, String theSpokenWords){

		try{

			theSpokenWords = theSpokenWords.replace(";", "").replace(".", "").replace(",", "");

			if(contain(voice.getChurch(), theSpokenWords)       ||
					contain(theSpokenWords, voice.getChurch())  ||
					contain(theSpokenWords, church.getEvent())  ||
					contain(church.getEvent(), theSpokenWords)  ||
					contain(theSpokenWords, church.getAccount())||
					contain(church.getAccount(), theSpokenWords)) {

				if(contain(theSpokenWords, church.getEvent()) || contain(church.getEvent(), theSpokenWords)) {
					church.setDisplayChurch("event");
				}
				else if(contain(theSpokenWords, church.getAccount()) || contain(church.getAccount(), theSpokenWords)) {
					church.setDisplayChurch("account");
				}

				setModel(model, null, church, null, null, null, null, null, null, null, null);

				return new ModelAndView("church", model);
			}
			else if(contain(voice.getSettings(), theSpokenWords)  || 
					contain(theSpokenWords, voice.getSettings())  || 
					contain("sector", theSpokenWords)             || 
					contain(theSpokenWords, "sector")             ||
					contain(voice.getPopup(), theSpokenWords)     || 
					contain(theSpokenWords, voice.getPopup())     || 
					contain(voice.getReference(), theSpokenWords) || 
					contain(theSpokenWords, voice.getReference()) ||
					contain(theSpokenWords, "sitting")            ||
					contain(theSpokenWords, "XV")                 ||
					contain(theSpokenWords, "cities")             ||
					contain(theSpokenWords, "15")) {

				String pageName = goToSettingsPage(model, settings, popup, reference, voice, app, theSpokenWords);

				if(pageName != null && !pageName.trim().isEmpty()) {
					return new ModelAndView(pageName, model);
				}	
			}
			else if(contain(voice.getBible(), theSpokenWords) || 
					contain(theSpokenWords, voice.getBible()) ||
					contain("Delia", theSpokenWords)          || 
					contain(theSpokenWords, "Delia")          ||
					contain("Bia", theSpokenWords)            || 
					contain(theSpokenWords, "Bia")
					) {
				setModel(model, bible, null, null, null, null, null, null, null, null, null);
				return new ModelAndView("bible", model);
			}
			else if(contain(voice.getScreensaver(), theSpokenWords) || contain(theSpokenWords, voice.getScreensaver())) {
				setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
				return new ModelAndView("screensaver", model);
			}
			else if(contain(voice.getCode(), theSpokenWords) || contain(theSpokenWords, voice.getCode())) {
				setModel(model, null, null, null, null, null, null, code, null, null, null);
				return new ModelAndView("code", model);
			}
			else if(contain(voice.getVoice(), theSpokenWords) || contain(theSpokenWords, voice.getVoice())){
				setModel(model, null, null, null, null, null, null, null, voice, null, null);
				return new ModelAndView("voice", model);
			}
		}catch(Exception e){
			writeFile(e);
		}

		return null;
	}

	public void setFirstAcces(ModelMap model, HttpServletRequest request, HttpServletResponse response, DAO dao, String selectedChurch, String selectedEvent, String selectedLanguage, 
			final Bible bible, final Settings settings, final Popup popup, final Reference reference, final Church church, final Screensaver screensaver, final ApplicationCode code, 
			final Voice voice, final AppBean app, final ThankYou thankYou, final Feedback feedback){

		try {

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			settings.setSelectedLanguage("english");

			TreeSet<String> versionsSet = getAllVersionsName(dbPath);

			if(versionsSet != null && !versionsSet.isEmpty()){

				List<Language> availableLanguages = dao.getAllLanguages(null);

				Language language = null;

				final TreeSet<String> languageNameList = settings.getLanguages();
				final TreeSet<String> voiceLanguage_1 = voice.getLanguages_1();
				final TreeSet<String> voiceLanguage_2 = voice.getLanguages_2();
				final Map<String, String> mapCode = app.getMapCode();

				if(availableLanguages != null && !availableLanguages.isEmpty()){

					for(Language lang : availableLanguages){

						if(lang != null){

							String languageName = lang.getValue();

							if(languageName != null && !languageName.trim().isEmpty()){

								languageName = languageName.trim();

								languageNameList.add(languageName);
								voiceLanguage_1.add(languageName);
								voiceLanguage_2.add(languageName);
								mapCode.put(languageName, lang.getLanguageCode());

								if(voice.getSelectedLanguage_1() == null && contain("english", languageName) && contain(languageName, "english")){
									voice.setSelectedLanguage_1(languageName);
									voice.setLanguageCode(lang.getCode());
									voice.setVoice_1(true);
								}

								if(voice.getSelectedLanguage_2() == null && contain("romana", languageName) && contain(languageName, "romana")){
									voice.setSelectedLanguage_2(languageName);
								}

								if(languageName.equals(selectedLanguage)){
									language = lang;
									settings.setSelectedLanguage(selectedLanguage);
								}
							}
						}
					}
				}

				if(language != null){

					setBibleLabels(language, bible, settings);

					LinkedHashSet<String> allVersions = getList(language.getSelectVersion());

					allVersions.addAll(versionsSet);

					bible.setVersions(allVersions);
					bible.setBooks(getList(language.getNoBook()));
					bible.setChapters(getList(language.getNoChapter()));
					bible.setVerses(getList(language.getNoVerse()));

					popup.setDisplayScriptureText(true);
					setLanguage(bible, settings, church, popup, reference, screensaver, code, voice, thankYou, feedback, dao);
					setAllChurchAndEvents(church, selectedChurch, selectedEvent, dao);
					setTime(church);
					setDefaultOnload(app, popup, reference, settings, bible, screensaver, code, feedback, language, dao);
					setSearchHistory(bible, language);
				}
			}

			if(selectedEvent != null && !selectedEvent.trim().isEmpty() && 
					selectedChurch != null && !selectedChurch.trim().isEmpty()) {

				model.addAttribute("church", church);
			}
			else {
				model.addAttribute("bible", bible);
			}
		}catch(Exception e){
			writeFile(e);
		}
	}

	public boolean contain(String entireString, String word){
		try{
			if(entireString != null && !entireString.trim().isEmpty() && word != null && !word.trim().isEmpty()){
				entireString = entireString.trim();
				if(entireString.contains("	")){
					entireString = entireString.replace("	", " ");
				}
				String[] array = entireString.split(" ");
				if(array != null && array.length > 0){
					Collator comparator = Collator.getInstance();
					if(comparator != null){
						String localWord = word.trim();
						comparator.setStrength(Collator.PRIMARY);
						ArrayList<String> history = new ArrayList<>();
						for(String aW : array){
							if(aW != null && !aW.trim().isEmpty()){

								String w = aW.trim();

								if(!history.contains(w)){

									history.add(w);

									int bigWordLength = w.length();
									int smallWordLength = localWord.length();

									if(smallWordLength <= bigWordLength){
										for(int i = 0 ; i < bigWordLength;i++){
											int end = i + smallWordLength;
											if(end <= bigWordLength){
												if(comparator.compare(w.substring(i, end), localWord) == 0){
													return true;
												}
											}
											else{
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			writeFile(e);
			return false;
		}
		return false;
	}
}