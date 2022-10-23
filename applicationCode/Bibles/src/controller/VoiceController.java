package controller;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//import com.google.cloud.translate.Translate;
//import com.google.cloud.translate.Translate.TranslateOption;
//import com.google.cloud.translate.TranslateOptions;
//import com.google.cloud.translate.TranslateOptions.Builder;
//import com.google.cloud.translate.Translation;

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
import utilities.AppBean;
import utilities.Translator;
import utilities.Utilities;

@Controller
@Scope("prototype")
public class VoiceController {

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

	@RequestMapping(value = "/voice", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ModelAndView voice(Voice voiceForm, ModelMap model, HttpServletRequest request, HttpServletResponse response){

		try{

			if(request != null){

				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");

				if(bible != null && settings != null && church != null){

					voice.setIdentifiedWords(null);


					Utilities utilities = new Utilities();
					voice.setError(null);

					new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, voiceForm.isOpenPopup());
					utilities.emptySelection(app);

					String eventId = voiceForm.getEventId();

					if(eventId != null && !eventId.trim().isEmpty() && bible != null){

						eventId = eventId.trim();

						switch(eventId){
						case "1" : {
							utilities.setModel(model, null, church, null, null, null, null, null, null, null, null);
							return new ModelAndView("church", model);
						}
						case "2" : {

							String pageName = new Utilities().goToSettingsPage(model, settings, popup, reference, voice, app, null);

							if(pageName != null && !pageName.trim().isEmpty()) {
								return new ModelAndView(pageName, model);
							}

							break;
						}
						case "3" : {
							utilities.setModel(model, bible, null, null, null, null, null, null, null, null, null);
							return new ModelAndView("bible", model);
						}
						case "4" : {
							utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
							return new ModelAndView("screensaver", model);
						}
						case "5" : {
							utilities.setModel(model, null, null, null, null, null, null, code, null, null, null);
							return new ModelAndView("code", model);
						}
						case "6" : {
							bible.setUsingVoice(voiceForm.isUsingVoice());
							settings.setUsingVoice(voiceForm.isUsingVoice());
							popup.setUsingVoice(voiceForm.isUsingVoice());
							reference.setUsingVoice(voiceForm.isUsingVoice());
							church.setUsingVoice(voiceForm.isUsingVoice());
							screensaver.setUsingVoice(voiceForm.isUsingVoice());
							code.setUsingVoice(voiceForm.isUsingVoice());
							voice.setUsingVoice(voiceForm.isUsingVoice());

							voice.setSelected_1_Text(voiceForm.getSelected_1_Text());
							voice.setSelected_2_Text(voiceForm.getSelected_2_Text());

							break;
						}
						case "7" : {
							ModelAndView result = executtingVoiceCommand(voiceForm, bible, settings, popup, reference, church, screensaver, code, app, model);
							if(result != null){
								return result;
							}
							break;
						}
						case "8" : {
							selectLanguage_1(voiceForm);
							break;
						}
						case "9" : {
							selectLanguage_2(voiceForm);
							break;
						}
						case "10" : {
							translateNext(voiceForm);
							break;
						}
						case "11" : {
							translateBack(voiceForm);
							break;
						}
						case "12" : {
							voice.setVoice_1(false);
							voice.setTheSpokenWords(null);
							updateVoiceCode(voice.getSelectedLanguage_2(), voice.getSelectedLanguage_1());
							break;
						}
						case "13" : {
							voice.setVoice_1(true);
							voice.setTheSpokenWords(null);
							updateVoiceCode(voice.getSelectedLanguage_1(), voice.getSelectedLanguage_2());
							break;
						}
						case "14" : {
							voice.setVoice_1(true);
							voice.setTheSpokenWords(null);
							updateVoiceCode(voice.getSelectedLanguage_1(), voice.getSelectedLanguage_2());
							break;
						}
						case "15" : {
							voice.setVoice_1(false);
							voice.setTheSpokenWords(null);
							updateVoiceCode(voice.getSelectedLanguage_2(), voice.getSelectedLanguage_1());
							break;
						}
						case "16" : {
							voice.setEnableSpeaking(true);
							break;
						}
						case "17" : {
							voice.setEnableSpeaking(false);
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
						}

						voiceForm.setEventId(null);
						bible.setEventId(null);
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		new Utilities().setModel(model, null, null, null, null, null, null, null, voice, null, null);
		return new ModelAndView("voice", model);
	}

	private void updateVoiceCode(final String languageListener, final String languageSpoken) {

		try {

			final Map<String, String> mapCode = app.getMapCode();

			if(mapCode != null && !mapCode.isEmpty() && 
					languageListener != null && !languageListener.trim().isEmpty()) {

				voice.setLanguageCode(mapCode.get(languageListener));
				voice.setVoiceCode(mapCode.get(languageSpoken));
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void translateNext(final Voice voiceForm){

		try{
			voice.setVoice_1(true);
			updateVoiceCode(voice.getSelectedLanguage_1(), voice.getSelectedLanguage_2());
			prepareTranslationProcess(voiceForm.getSelected_1_Text(), getCodeByLanguage(app, voice.getSelectedLanguage_1()), getCodeByLanguage(app, voice.getSelectedLanguage_2()), app);
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void translateBack(final Voice voiceForm){

		try{
			voice.setVoice_1(false);
			updateVoiceCode(voice.getSelectedLanguage_2(), voice.getSelectedLanguage_1());
			prepareTranslationProcess(voiceForm.getSelected_2_Text(), getCodeByLanguage(app, voice.getSelectedLanguage_2()), getCodeByLanguage(app, voice.getSelectedLanguage_1()), app);
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void selectLanguage_1(final Voice voiceForm) {

		try {

			String selectedLanguage = voiceForm.getSelectedLanguage_1();

			if(selectedLanguage != null && !selectedLanguage.trim().isEmpty()) {

				voice.setSelectedLanguage_1(selectedLanguage);

				selectLanguageConfig(voiceForm);
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void selectLanguageConfig(final Voice voiceForm){

		try{
			if(voice.isVoice_1()){
				updateVoiceCode(voice.getSelectedLanguage_1(), voice.getSelectedLanguage_2());
				translateNext(voiceForm);
			}
			else {
				updateVoiceCode(voice.getSelectedLanguage_2(), voice.getSelectedLanguage_1());
				translateBack(voiceForm);
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void selectLanguage_2(final Voice voiceForm) {

		try {

			String selectedLanguage = voiceForm.getSelectedLanguage_2();

			if(selectedLanguage != null && !selectedLanguage.trim().isEmpty()) {

				voice.setSelectedLanguage_2(selectedLanguage);

				selectLanguageConfig(voiceForm);
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private ModelAndView executtingVoiceCommand(Voice voiceForm, final Bible bible, final Settings settings, final Popup popup, final Reference reference, final Church church, final Screensaver screensaver, ApplicationCode code, final AppBean app, ModelMap model){

		try{

			String theSpokenWords = voiceForm.getIdentifiedWords();

			if(theSpokenWords != null && !theSpokenWords.trim().isEmpty()){

				theSpokenWords = theSpokenWords.trim();

				if(!theSpokenWords.contains(" ") && !theSpokenWords.contains("	")){

					return new Utilities().navigateByVoice(voice, bible, settings, popup, reference, church, screensaver, code, app, model, theSpokenWords);
				}

				if(voice.isVoice_1()){
					prepareTranslationProcess(theSpokenWords, getCodeByLanguage(app, voice.getSelectedLanguage_1()), getCodeByLanguage(app, voice.getSelectedLanguage_2()), app);
				}
				else {
					prepareTranslationProcess(theSpokenWords, getCodeByLanguage(app, voice.getSelectedLanguage_2()), getCodeByLanguage(app, voice.getSelectedLanguage_1()), app);
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
		return null;
	}

	private String getCodeByLanguage(final AppBean app, String language) {

		try {

			if(app != null && language != null && !language.trim().isEmpty()) {

				final Map<String, String> mapCode = app.getMapCode();

				if(mapCode != null && !mapCode.isEmpty() && mapCode.containsKey(language)) {

					String code = mapCode.get(language);

					if(code != null && !code.trim().isEmpty()) {
						return code.trim();
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		return null;
	}

	//	final private String translateByApiKey(String text, final String from, final String to) {
	//
	//		try {
	//			if(text != null && !text.trim().isEmpty() &&
	//					from != null && !from.trim().isEmpty() &&	
	//					to != null && !to.trim().isEmpty()
	//					) {
	//				final Builder builder = TranslateOptions.newBuilder();
	//				if(builder != null) {
	//					builder.setApiKey(Constant.translateApiKey);
	//					final TranslateOptions tr = builder.build();
	//					if(tr != null) {
	//						final Translate translate = tr.getService();
	//						if(translate != null) {
	//							final Translation translation = translate.translate(text.trim(), TranslateOption.sourceLanguage(from.trim()), TranslateOption.targetLanguage(to.trim()));
	//							if(translation != null) {
	//								return translation.getTranslatedText();
	//							}    
	//						}
	//					}
	//				}	
	//			}
	//		}
	//		catch(Exception e) {
	//			new Utilities().writeFile(e);
	//		}
	//
	//		return null;
	//	}

	final private String translateByUrl(String text, final String from, final String to) {

		try {
			String result = "";
			String splitSign = "___________________";

			Set<String> endSymbols = new HashSet<>();

			endSymbols.add(".");
			endSymbols.add("!");
			endSymbols.add("?");
			endSymbols.add(";");

			if(endSymbols != null && !endSymbols.isEmpty()){

				for(String symbol : endSymbols){
					if(symbol != null && !symbol.trim().isEmpty() && text.contains(symbol.trim())){
						text = text.replace(symbol.trim(), symbol.trim() + splitSign.trim());
					}
				}
			}

			String[] multipleSentences = text.split(splitSign.trim());

			if(text.contains(splitSign.trim())){
				text = text.replace(splitSign.trim(), "");
			}

			if(multipleSentences != null && multipleSentences.length > 0){
				for(String sentence : multipleSentences){
					if(sentence != null && !sentence.trim().isEmpty()){
						result += getEncodedString(new Translator().callUrlAndParseResult(from, to, sentence));
					}
				}
			}

			if(result != null && !result.trim().isEmpty() && endSymbols != null && !endSymbols.isEmpty()){
				for(String symbol : endSymbols){
					if(symbol != null && !symbol.trim().isEmpty() && result.contains(symbol.trim())){
						result = result.replace(symbol.trim(), symbol.trim() + " ");
					}
				}
			}
			return result;
		}
		catch(Exception e) {
			new Utilities().writeFile(e);
		}
		return null;
	}

	private void prepareTranslationProcess(String text, final String from, final String to, final AppBean app) {

		try {

			if(text != null && !text.trim().isEmpty() && 
					from != null && !from.trim().isEmpty()) {

				text = text.trim();

				String translatedText = null; //translateByApiKey(text, from, to);

				if(translatedText == null || translatedText.trim().isEmpty()) {
					translatedText = translateByUrl(text, from, to);
				}

				String code_1 = getCodeByLanguage(app, voice.getSelectedLanguage_1());

				if(code_1 != null && code_1.equals(from)) {
					voice.setSelected_1_Text(text.trim());
					voice.setSelected_2_Text(translatedText);
				}
				else {
					voice.setSelected_2_Text(text.trim());
					voice.setSelected_1_Text(translatedText);
				}

				voice.setTheSpokenWords(translatedText.trim());
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private final String getEncodedString(String string){

		try{

			if(string != null && !string.trim().isEmpty()){

				final Charset UTF8_CHARSET = Charset.forName("UTF-8");

				return new String(string.trim().getBytes(UTF8_CHARSET), UTF8_CHARSET);
			}
		}
		catch (Exception e){
			new Utilities().writeFile(e);
		}

		return null;
	}
}