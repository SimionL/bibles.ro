package controller;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import utilities.AppBean;
import utilities.Constant;
import utilities.Message;
import utilities.Utilities;
import utilities.VerseDetails;

@Controller
@Transactional
@Scope("prototype")
public class SettingsController {

	@Autowired
	private DAO dao;

	@Autowired
	private Bible bible;

	@Autowired
	private Settings settings;

	@Autowired
	private Popup popup;

	@Autowired
	private Reference reference;

	@Autowired
	private Church church;

	@Autowired
	private Screensaver screensaver;

	@Autowired
	private ApplicationCode code;

	@Autowired
	private Voice voice;

	@Autowired
	private AppBean app;

	@Autowired
	private ThankYou thankYou;

	@Autowired
	private Feedback feedback;

	@RequestMapping(value = "/blessed", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ModelAndView settings(ModelMap model, Settings settingsForm, HttpServletRequest request, HttpServletResponse response){

		try{

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			before(settingsForm);

			String eventId = settingsForm.getEventId();

			if(eventId != null && !eventId.trim().isEmpty()){

				eventId = eventId.trim();
				Utilities utilities = new Utilities();
				switch(eventId){
				case "1" : {
					after(settingsForm);
					utilities.setModel(model, bible, null, null, null, null, null, null, null, null, null);

					return new ModelAndView("bible", model);
				}
				case "2" : {
					after(settingsForm);
					utilities.setModel(model, null, church, null, null, null, null, null, null, null, null);

					return new ModelAndView("church", model);
				}
				case "3" : {

					if(settingsForm != null){

						settings.setSelectedLanguage(settingsForm.getSelectedLanguage());
						utilities.setLanguage(bible, settings, church, popup, reference, screensaver, code, voice, thankYou, feedback, dao);
					}

					break;
				}
				case "4" : {
					after(settingsForm);
					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "5" : {
					after(settingsForm);
					utilities.setModel(model, null, null, null, null, null, null, code, null, null, null);
					return new ModelAndView("code", model);
				}
				case "6" : {
					after(settingsForm);
					app.setLastSettings(2);
					utilities.setModel(model, null, null, null, popup, null, null, null, null, null, null);
					return new ModelAndView("popup", model);
				}
				case "7" : {
					after(settingsForm);
					app.setLastSettings(3);
					utilities.setModel(model, null, null, null, null, reference, null, null, null, null, null);
					return new ModelAndView("reference", model);
				}
				case "8" : {
					after(settingsForm);
					utilities.setModel(model, null, null, null, null, null, null, null, voice, null, null);

					return new ModelAndView("voice", model);
				}

				case "9" : {

					if(bible != null && settings != null){
						setHighlights(settingsForm);
					}

					break;
				}
				case "10" : {
					if(settings != null && settingsForm != null){
						setSelectedColors(settingsForm.getExactColorPaletteSelected(), settingsForm.getInexactColorPaletteSelected());
					}

					break;
				}
				case "11" : {
					ModelAndView result = executtingVoiceCommand(settingsForm, model);
					if(result != null){
						return result;
					}
					break;
				}
				case "12" : {

					settings.setSearchBlockLengthSelection(settingsForm.getSearchBlockLengthSelection());

					break;
				}
				case "13" : {

					settings.setSearchLevelSelected(settingsForm.getSearchLevelSelected());

					break;
				}
				case "14" : {

					settings.setDisplayReference(settingsForm.isDisplayReference());
					bible.setDisplayReference(settingsForm.isDisplayReference());

					break;
				}
				case "15" : {

					setFormFont(settingsForm);

					break;
				}
				case "16" : {

					settings.setDisplayEntireChapter(settingsForm.isDisplayEntireChapter());

					break;
				}
				case "17" : {

					settings.setWordWrap(settingsForm.isWordWrap());
					bible.setWordWrap(settingsForm.isWordWrap());

					break;
				}
				case "18" : {
					utilities.setModel(model, null, null, null, null, null, null, null, null, null, feedback);
					return new ModelAndView("feedback", model);
				}
				case "19" : {
					utilities.setModel(model, null, null, null, null, null, null, null, null, thankYou, null);
					return new ModelAndView("thankYou", model);
				}
				case "20" : {

					addNewMessage(settingsForm);

					break;
				}
				case "21" : {

					saveMessageAvailability(settingsForm);

					break;
				}

				case "22" : {

					deleteMessageAddress(settingsForm);

					break;
				}
				case "23" : {

					automatSendMessage(settingsForm);

					break;
				}
				case "24" : {

					setEmailFrom(settingsForm);

					break;
				}
				case "25" : {

					addUserServerEmailAddress(settingsForm);

					break;
				}
				case "26" : {

					addUserServerEmailPassword(settingsForm);

					break;
				}

				case "27" : {

					addMessageTitle(settingsForm);

					break;
				}
				case "28" : {

					addMessageContent(settingsForm);

					break;
				}
				case "29" : {

					loadSelectedAddress(settingsForm);

					break;
				}
				case "30" : {

					addName(settingsForm);

					break;
				}
				case "31" : {

					saveMessageEmailAvailability(settingsForm);

					break;
				}
				case "32" : {

					saveMessagePhoneAvailability(settingsForm);

					break;
				}
				case "33" : {

					selectAll(settingsForm);

					break;
				}
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}

		after(settingsForm);
		new Utilities().setModel(model, null, null, settings, null, null, null, null, null, null, null);
		return new ModelAndView("settings", model);
	}

	private void selectAll(Settings settingsForm) {

		try {
			if(settingsForm != null && settings != null) {

				final boolean newSelect = settingsForm.isSelelctAll();

				settings.setSelelctAll(newSelect);
				settings.getMessages().parallelStream().filter(m -> m != null).forEach(m -> m.setSelected(newSelect));
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void saveMessagePhoneAvailability(Settings settingsForm) {

		try {

			if(settingsForm != null && settings != null) {
				String selectedMessage = settingsForm.getSelectedMessageId();
				List<Message> messages = settings.getMessages();

				if(messages != null && !messages.isEmpty() && selectedMessage != null && !selectedMessage.trim().isEmpty()) {

					Message message = getMessageByAddress(selectedMessage.trim());

					if(message != null) {
						message.setSendPhone(!message.isSendPhone());
						settings.setSaveMessageSettings(true);
					}
				}
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private void saveMessageEmailAvailability(Settings settingsForm) {

		try {

			if(settingsForm != null && settings != null) {
				String selectedMessage = settingsForm.getSelectedMessageId();
				List<Message> messages = settings.getMessages();

				if(messages != null && !messages.isEmpty() && selectedMessage != null && !selectedMessage.trim().isEmpty()) {

					Message message = getMessageByAddress(selectedMessage.trim());

					if(message != null) {
						message.setSendEmail(!message.isSendEmail());
						settings.setSaveMessageSettings(true);
					}
				}
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private Message getEditedMessage(Settings settingsForm) {

		try {

			if(settings != null && settingsForm != null) {
				Message message = getMessageByAddress(settings.getSelectedMessageId());
				if(message == null) {
					message = getMessageByAddress(settingsForm.getNewAddress());
				}
				return message;
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}

		return null;
	}

	private Message getMessageByAddress(String messageAdress) {

		try {

			if(settings != null && messageAdress != null && !messageAdress.trim().isEmpty()) {

				List<Message> messages = settings.getMessages();

				if(messages != null && !messages.isEmpty()) {

					return messages.parallelStream()
							.filter(m -> m != null && m.getAddress() != null && m.getAddress().trim().equalsIgnoreCase(messageAdress.trim()))
							.findFirst()
							.orElse(null);
				}
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}

		return null;
	}

	private void loadSelectedAddress(Settings settingsForm) {

		try {

			if(settingsForm != null && settings != null) {

				String newSelectedMessage = settingsForm.getSelectedMessageId();

				if(newSelectedMessage != null && !newSelectedMessage.trim().isEmpty()) {

					String oldSelectedMessage = settings.getSelectedMessageId();

					if(oldSelectedMessage != null && !oldSelectedMessage.trim().isEmpty() && oldSelectedMessage.equalsIgnoreCase(newSelectedMessage)) {
						settings.setSelectedMessageId(null);
					}
					else {
						settings.setSelectedMessageId(newSelectedMessage);

						Message message = getMessageByAddress(newSelectedMessage);

						if(message != null) {
							settings.setNewAddress(message.getAddress());
							settings.setMessageTitleValue(message.getTitle()); 
							settings.setMessageContentValue(message.getMessage());
							settings.setNameValue(message.getName());
						}
					}
				}
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private boolean isSavedEditFields(Message message, Settings settingsForm) {

		try {
			if(message != null && settings != null && settingsForm != null) {

				String address = settingsForm.getNewAddress();

				if(address != null && !address.trim().isEmpty()) {

					message.setAddress(address.trim());
					message.setName(settingsForm.getNameValue());
					message.setTitle(settingsForm.getMessageTitleValue());
					message.setMessage(settingsForm.getMessageContentValue());

					settings.setNewAddress(address.trim());
					settings.setSelectedMessageId(address.trim());
					settings.setNameValue(settingsForm.getNameValue());
					settings.setMessageTitleValue(settingsForm.getMessageTitleValue());
					settings.setMessageContentValue(settingsForm.getMessageContentValue());

					settings.setSaveMessageSettings(true);

					return true;
				}
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
		return false;
	}

	private void addName(Settings settingsForm) {

		try {
			if(settingsForm != null && settings != null) {

				Message message = getEditedMessage(settingsForm);

				if(message != null && isSavedEditFields(message, settingsForm)) {
					settings.setOk(settings.getNameChanged());
				}
				else {
					addNewMessage(settingsForm);
				}
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private void addMessageContent(Settings settingsForm) {

		try {

			if(settingsForm != null && settings != null) {

				Message message = getEditedMessage(settingsForm);

				if(message != null && isSavedEditFields(message, settingsForm)) {
					settings.setOk(settings.getMessageContentAdded());
				}
				else {
					addNewMessage(settingsForm);
				}
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private void addMessageTitle(Settings settingsForm) {

		try {

			if(settingsForm != null && settings != null) {

				Message message = getEditedMessage(settingsForm);

				if(message != null && isSavedEditFields(message, settingsForm)) {
					settings.setOk(settings.getMessageTitleAdded());
				}
				else {
					addNewMessage(settingsForm);
				}
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private void addUserServerEmailPassword(Settings settingsForm) {

		try {
			if(settingsForm != null && settings != null) {
				settings.setUserPassword(settingsForm.getUserPassword());
				settings.setOk(settings.getPasswordSaved());
				settings.setSaveMessageSettings(true);
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private void addUserServerEmailAddress(Settings settingsForm) {

		try {
			if(settingsForm != null && settings != null ) {

				String userServerEmail = settingsForm.getUserEmail();

				if(userServerEmail != null && 
						!userServerEmail.trim().isEmpty() && 
						userServerEmail.trim().endsWith("@gmail.com") &&
						new Utilities().isValidEmailAddress(userServerEmail.trim())
						) {

					settings.setUserEmail(userServerEmail.trim());
					settings.setOk(settings.getEmailSaved());
					settings.setSaveMessageSettings(true);
				}
				else {
					settings.setError(church.getInvalidEmailFormat() + "<br>" + userServerEmail);
				}
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private void setEmailFrom(Settings settingsForm) {

		if(settingsForm != null && settings != null) {
			settings.setEmailFrom(settingsForm.getEmailFrom());
			settings.setSaveMessageSettings(true);
		}
	}

	private void automatSendMessage(Settings settingsForm) {

		try {

			if(settingsForm != null && settings != null && bible != null) {

				settings.setAutomatSendMessage(settingsForm.isAutomatSendMessage());
				settings.setSaveMessageSettings(true);
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private void deleteMessageAddress(Settings settingsForm) {

		try {

			if(settingsForm != null && settings != null) {
				String deletedMessage = settingsForm.getSelectedMessageId();
				List<Message> messages = settings.getMessages();

				if(messages != null && !messages.isEmpty()) {

					List<Message> newMessagesList = messages.stream().filter(e -> e != null && e.getAddress() != null && !e.getAddress().trim().equalsIgnoreCase(deletedMessage.trim())).collect(Collectors.toList());

					messages.clear();
					messages.addAll(newMessagesList);
					settings.setSaveMessageSettings(true);
				}
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private void saveMessageAvailability(Settings settingsForm) {

		try {

			if(settingsForm != null && settings != null) {
				String selectedMessage = settingsForm.getSelectedMessageId();
				List<Message> messages = settings.getMessages();

				if(messages != null && !messages.isEmpty() && selectedMessage != null && !selectedMessage.trim().isEmpty()) {

					Message message = getMessageByAddress(selectedMessage.trim());

					if(message != null) {
						message.setSelected(!message.isSelected());
						settings.setSaveMessageSettings(true);
					}
				}
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private void addNewMessage(Settings settingsForm) {

		try {
			if(settings != null && settingsForm != null) {

				String newAddress = settingsForm.getNewAddress();

				if(newAddress != null && !newAddress.trim().isEmpty()) {

					final List<Message> messages = settings.getMessages();
					if(messages != null) {
						boolean isValidAddress = false;
						boolean isNewMessage = false;

						Message message = getMessageByAddress(newAddress);

						if(message == null) {
							message = new Message();
							isNewMessage = true;
						}

						if(new Utilities().isValidEmailAddress(newAddress)) {
							message.setAddress(newAddress.trim());
							message.setEmail(true);
							isValidAddress = true;
						}

						if(new Utilities().isValidPhoneNumber(newAddress)) {
							message.setAddress(new Utilities().cleanNumber(newAddress.trim()));
							message.setPhone(true);
							isValidAddress = true;
						}

						if(isValidAddress) {

							message.setName(settingsForm.getNameValue());

							String title = settingsForm.getMessageTitleValue();
							String content = settingsForm.getMessageContentValue();

							if(title != null && !title.trim().isEmpty()) {
								message.setTitle(title.trim());
							}

							if(content != null && !content.trim().isEmpty()) {
								message.setMessage(content.trim());
							}

							if(isNewMessage) {
								messages.add(message);
							}

							settings.setOk(settings.getAdded());
							settings.setSaveMessageSettings(true);

							settings.setSelectedMessageId(null);
							settings.setNewAddress(null);
							settings.setNameValue(null);
							settings.setMessageTitleValue(null);
							settings.setMessageContentValue(null);
						}
						else {
							settings.setError(settings.getInvalidFormat() + "<br>" + newAddress + "<br>");
						}
					}
				}
			}
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private ModelAndView executtingVoiceCommand(Settings settingsForm, ModelMap model){

		try{

			String theSpokenWords = settingsForm.getIdentifiedWords();

			if(theSpokenWords != null && !theSpokenWords.trim().isEmpty()){

				if(!theSpokenWords.contains(" ") && !theSpokenWords.contains("	")){

					return new Utilities().navigateByVoice(voice, bible, settings, popup, reference, church, screensaver, code, app, model, theSpokenWords);
				}

				theSpokenWords = theSpokenWords.replace(";", "").trim().toLowerCase();

				final String wordWrap             = Normalizer.normalize(settings.getWordWrapLabel().toLowerCase()            , Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "").trim().toLowerCase();
				final String highlightsText       = Normalizer.normalize(settings.getHighlightsTextLabel().toLowerCase()      , Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "").trim().toLowerCase();
				final String displayReferences    = Normalizer.normalize(settings.getDisplayReferencesLabel().toLowerCase()   , Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "").trim().toLowerCase();
				final String displayEntireChapter = Normalizer.normalize(settings.getDisplayEntireChapterLabel().toLowerCase(), Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "").trim().toLowerCase();
				final String formFont             = Normalizer.normalize(settings.getFormFontLabel().toLowerCase()            , Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "").trim().toLowerCase();
				final String searchBlockLength    = Normalizer.normalize(settings.getSearchBlockLength().toLowerCase()        , Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "").trim().toLowerCase();

				boolean isWordWrap = theSpokenWords.contains(wordWrap) || theSpokenWords.contains("word") || theSpokenWords.contains("world") || theSpokenWords.contains("rap");
				boolean isHighlights = theSpokenWords.contains(highlightsText) || theSpokenWords.contains("evident");
				boolean isDisplayReference = theSpokenWords.contains(displayReferences) || theSpokenWords.contains("trimit");
				boolean isDisplayEntireChapter = theSpokenWords.contains(displayEntireChapter) || theSpokenWords.contains("intreg") || theSpokenWords.contains("capitol");
				boolean isFont = theSpokenWords.contains(formFont);
				boolean isSearchBlockLength = theSpokenWords.contains(searchBlockLength) || theSpokenWords.contains("raz") || 
						theSpokenWords.contains("ras") || theSpokenWords.contains("fraz") || 
						theSpokenWords.contains("bloc") || theSpokenWords.contains("calup") || 
						theSpokenWords.contains("Search") || theSpokenWords.contains("block") || 
						theSpokenWords.contains("length") || theSpokenWords.contains("praz");
				//boolean isSearchLevel = theSpokenWords.contains(searchLevel) || theSpokenWords.contains("level") || theSpokenWords.contains("ivel");


				if(isWordWrap) {
					settings.setWordWrap(!settingsForm.isWordWrap());
					bible.setWordWrap(!settingsForm.isWordWrap());
				}

				if(isHighlights) {

					settingsForm.setHighlights(!settingsForm.isHighlights());
					setHighlights(settingsForm);
				}

				if(isDisplayReference) {

					settings.setDisplayReference(!settingsForm.isDisplayReference());
					bible.setDisplayReference(!settingsForm.isDisplayReference());
				}

				if(isDisplayEntireChapter) {
					settings.setDisplayEntireChapter(!settingsForm.isDisplayEntireChapter());
				}

				if(isFont) {

					settingsForm.setFormFontSelected(getNumberRequestedCommand(theSpokenWords, "3", 1, 7, formFont));
					setFormFont(settingsForm);
				}

				if(isSearchBlockLength) {

					settings.setSearchBlockLengthSelection(getNumberRequestedCommand(theSpokenWords, "1", 1, 20, searchBlockLength, "raz", "ras", "fraz", "praz", "bloc", "calup", "Search", "block", "length"));
				}

				//				if(isSearchLevel) {
				//					settings.setSearchLevelSelected(getStringRequestedCommand(settings, theSpokenWords, searchLevel, "level", "ivel"));
				//				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
		return null;
	}

	private String getNumberRequestedCommand(String command, String defaultValue, int min, int max, String ... fields){

		try {
			if(command != null && !command.trim().isEmpty() &&
					fields != null && fields.length > 0) {

				command = command.replace(";", "").replace("	", "");

				String[] params = command.split(" ");

				String oldParam = null;

				if(params != null && params.length > 0) {

					Utilities util = new Utilities();

					for(String elem : params) {
						if(elem != null && !elem.trim().isEmpty() && 
								oldParam != null && !oldParam.trim().isEmpty() &&
								elem.trim().matches("\\d+")) {

							for(String field : fields) {

								if(field != null && !field.trim().isEmpty() && (util.contain(oldParam, field) || util.contain(field, oldParam))) {

									int selectedFont = Integer.parseInt(elem.trim());

									if(min <= selectedFont && selectedFont <= max) {
										return elem.trim();	
									}
								}
							}
						}
						oldParam = elem;
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		return defaultValue;
	}

	private void after(Settings settingsForm){

		try{
			if(settingsForm != null){
				settingsForm.setEventId(null);
			}
			if(settings != null){
				settings.setEventId(null);
			}

			List<Message> messages = settings.getMessages();

			if(messages != null && !messages.isEmpty()) {

				String messagesEncapsulation = "";

				for(Message message : messages) {
					if(message != null) {
						messagesEncapsulation += message.isSelected()                                                                                         + Constant.splitSign +
								message.isSendEmail()                                                                                                         + Constant.splitSign +
								message.isSendPhone()                                                                                                         + Constant.splitSign +
								((message.getAddress() != null && !message.getAddress().trim().isEmpty()) ? message.getAddress().trim() : Constant.nullSign ) + Constant.splitSign +
								((message.getName()    != null && !message.getName().trim().isEmpty())    ? message.getName().trim()    : Constant.nullSign ) + Constant.splitSign +
								((message.getTitle()   != null && !message.getTitle().trim().isEmpty())   ? message.getTitle().trim()   : Constant.nullSign ) + Constant.splitSign +
								((message.getMessage() != null && !message.getMessage().trim().isEmpty()) ? message.getMessage().trim() : Constant.nullSign ) + Constant.messageSplitSign;
					}
				}
				settings.setMessagesEncapsulation(messagesEncapsulation);
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setHighlights(Settings settingsForm){

		try{

			if(settings != null && settingsForm != null && bible != null){

				settings.setHighlights(settingsForm.isHighlights());

				if(settings.isHighlights()){

					setSelectedColors(null, null);
				}
				else {
					deleteColors();
				}

			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void deleteColors(){

		try{

			if(bible != null && bible.isSearchTextAvailable()){

				List<VerseDetails> verseValue = bible.getVerseValue();

				if(verseValue != null){

					verseValue.clear();

					VerseDetails vd = new VerseDetails();
					vd.setVerse(bible.getNonColorSearchedText());

					verseValue.add(vd);
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setSelectedColors(String exactColorPaletteSelected, String inexactColorPaletteSelected){

		try{
			if(settings != null && bible != null){

				if(exactColorPaletteSelected != null && !exactColorPaletteSelected.trim().isEmpty()){
					settings.setExactColorPaletteSelected(exactColorPaletteSelected);	   
				}

				if(inexactColorPaletteSelected != null && !inexactColorPaletteSelected.trim().isEmpty()){
					settings.setInexactColorPaletteSelected(inexactColorPaletteSelected);	
				}

				if(bible.isSearchTextAvailable()){

					String unHighlightedText = bible.getUnHighlightedText();

					if(unHighlightedText != null && !unHighlightedText.trim().isEmpty()){

						List<VerseDetails> verseValue = bible.getVerseValue();

						if(verseValue != null){

							verseValue.clear();

							String exactColorSelected = settings.getExactColorPaletteSelected();
							String inexactColorSelected = settings.getInexactColorPaletteSelected();

							String highlightedText = unHighlightedText.replaceAll("_@@@@@@_", exactColorSelected).replaceAll("_!!!!!!_", inexactColorSelected);

							final VerseDetails vd = new VerseDetails();
							vd.setVerse(highlightedText);

							verseValue.add(vd);
						}
					}
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setFormFont(Settings settingsForm){

		try{
			if(settingsForm != null){

				if(settings != null){
					settings.setFormFontSelected(settingsForm.getFormFontSelected());
				}

				if(bible != null){
					bible.setFormFontSelected(settingsForm.getFormFontSelected());
				}

				if(church != null){
					church.setFormFontSelected(settingsForm.getFormFontSelected());
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void before(final Settings settingsForm){

		try{

			new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, settingsForm.isOpenPopup());

			new Utilities().emptySelection(app);

			if(settings != null){
				settings.setError(null);
				settings.setOk(null);
				settings.setIdentifiedWords(null);
				settings.setSaveMessageSettings(false);
			}

		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}
}