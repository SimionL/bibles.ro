package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import beans.Language;
import dao.DAOTool;
import utilities.Constants;
import utilities.Translator;

@Controller
public class TranslateController{

	@Autowired
	private DAOTool dao;

	private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	@RequestMapping(value = "/translate")
	public ModelAndView testDatabase(ModelMap model, HttpServletRequest request, HttpServletResponse response){

		try {

			if(request != null){

				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");

				HttpSession session = request.getSession();
				if(session != null){
					session.setMaxInactiveInterval(999999999);
				}
			}

			try{

				Language language = dao.getLanguage(Constants.english.value.trim());

				if(language != null){

					Translator http = new Translator();

					Map<String, String> allAvailableLanguages = new TreeMap<>();

					allAvailableLanguages.put("am", "Amharic");
					allAvailableLanguages.put("af", "Afrikaans");
					allAvailableLanguages.put("sq", "Albanian");
					allAvailableLanguages.put("ar", "Arabic");
					allAvailableLanguages.put("hy", "Armenian");
					allAvailableLanguages.put("az", "Azerbaijani");
					allAvailableLanguages.put("eu", "Basque");
					allAvailableLanguages.put("bn", "Bengali");
					allAvailableLanguages.put("be", "Belarusian");
					allAvailableLanguages.put("bs", "Bosnian");
					allAvailableLanguages.put("bg", "Bulgarian");
					allAvailableLanguages.put("km", "Cambodian");
					allAvailableLanguages.put("ca", "Catalan");
					allAvailableLanguages.put("ny", "Chichewa");
					allAvailableLanguages.put("zh-CN", "Chinese Simplified");
					allAvailableLanguages.put("zh-TW", "Chinese Traditional");
					allAvailableLanguages.put("zh-HK", "Chinese Hong Kong");
					allAvailableLanguages.put("co", "Corsican");
					allAvailableLanguages.put("hr", "Croatian");
					allAvailableLanguages.put("cs", "Czech");
					allAvailableLanguages.put("da", "Danish");
					allAvailableLanguages.put("nl", "Dutch");
					allAvailableLanguages.put("eo", "Esperanto");
					allAvailableLanguages.put("et", "Estonian");
					allAvailableLanguages.put("en-US", "English US");
					allAvailableLanguages.put("tl", "Filipino");
					allAvailableLanguages.put("fi", "Finnish");
					allAvailableLanguages.put("fr", "French");
					allAvailableLanguages.put("fr-CA", "French Canadian");
					allAvailableLanguages.put("fy", "Frisian");
					allAvailableLanguages.put("gl", "Galician");
					allAvailableLanguages.put("ka", "Georgian");
					allAvailableLanguages.put("de", "German");
					allAvailableLanguages.put("el", "Greek");
					allAvailableLanguages.put("gu", "Gujarati");
					allAvailableLanguages.put("ht", "Haitian Creole");
					allAvailableLanguages.put("ha", "Hausa");
					allAvailableLanguages.put("haw", "Hawaiian");
					allAvailableLanguages.put("iw", "Hebrew");
					allAvailableLanguages.put("hi", "Hindi");
					allAvailableLanguages.put("hu", "Hungarian");
					allAvailableLanguages.put("is", "Icelandic");
					allAvailableLanguages.put("ig", "Igbo");
					allAvailableLanguages.put("id", "Indonesian");
					allAvailableLanguages.put("ga", "Irish");
					allAvailableLanguages.put("it", "Italian");
					allAvailableLanguages.put("ja", "Japanese");
					allAvailableLanguages.put("jw", "Javanese");
					allAvailableLanguages.put("kn", "Kannada");
					allAvailableLanguages.put("kk", "Kazakh");
					allAvailableLanguages.put("ky", "Kyrgyz");
					allAvailableLanguages.put("ko", "Korean");
					allAvailableLanguages.put("ku", "Kurdish");
					allAvailableLanguages.put("lo", "Laothian");
					allAvailableLanguages.put("la", "Latin");
					allAvailableLanguages.put("lv", "Latvian");
					allAvailableLanguages.put("lt", "Lithuanian");
					allAvailableLanguages.put("mk", "Macedonian");
					allAvailableLanguages.put("mg", "Malagasy");
					allAvailableLanguages.put("ms", "Malay");
					allAvailableLanguages.put("ml", "Malayalam");
					allAvailableLanguages.put("mt", "Maltese");
					allAvailableLanguages.put("mi", "Maori");
					allAvailableLanguages.put("mr", "Marathi");
					allAvailableLanguages.put("mo", "Moldavian");
					allAvailableLanguages.put("mn", "Mongolian");
					allAvailableLanguages.put("sr-ME", "Montenegrin");
					allAvailableLanguages.put("ne", "Nepali");
					allAvailableLanguages.put("no", "Norwegian");
					allAvailableLanguages.put("nn", "Norwegian Nynorsk");
					allAvailableLanguages.put("ps", "Pashto");
					allAvailableLanguages.put("fa", "Persian");
					allAvailableLanguages.put("pl", "Polish");
					allAvailableLanguages.put("pt", "Portuguese");
					allAvailableLanguages.put("pt-BR", "Portuguese Brazil");
					allAvailableLanguages.put("pt-PT", "Portuguese Portugal");
					allAvailableLanguages.put("pa", "Punjabi");
					allAvailableLanguages.put("ru", "Russian");
					allAvailableLanguages.put("gd", "Scots Gaelic");
					allAvailableLanguages.put("sr", "Serbian");
					allAvailableLanguages.put("sh", "Serbo-Croatian");
					allAvailableLanguages.put("st", "Sesotho");
					allAvailableLanguages.put("sn", "Shona");
					allAvailableLanguages.put("sd", "Sindhi");
					allAvailableLanguages.put("si", "Sinhalese");
					allAvailableLanguages.put("sk", "Slovak");
					allAvailableLanguages.put("sl", "Slovenian");
					allAvailableLanguages.put("so", "Somali");
					allAvailableLanguages.put("es", "Spanish");
					allAvailableLanguages.put("es-419", "Spanish Latin America");
					allAvailableLanguages.put("su", "Sundanese");
					allAvailableLanguages.put("sw", "Swahili");
					allAvailableLanguages.put("sv", "Swedish");
					allAvailableLanguages.put("tg", "Tajik");
					allAvailableLanguages.put("ta", "Tamil");
					allAvailableLanguages.put("te", "Telugu");
					allAvailableLanguages.put("th", "Thai");     
					allAvailableLanguages.put("tr", "Turkish");
					allAvailableLanguages.put("uk", "Ukrainian");
					allAvailableLanguages.put("ur", "Urdu");
					allAvailableLanguages.put("uz", "Uzbek");
					allAvailableLanguages.put("vi", "Vietnamese");
					allAvailableLanguages.put("cy", "Welsh");
					allAvailableLanguages.put("xh", "Xhosa");
					allAvailableLanguages.put("yi", "Yiddish");
					allAvailableLanguages.put("yo", "Yoruba");
					allAvailableLanguages.put("zu", "Zulu");

					if(allAvailableLanguages != null && !allAvailableLanguages.isEmpty()){
						for(String languageCode : allAvailableLanguages.keySet()){
							if(languageCode != null && !languageCode.trim().isEmpty()){

								try{

									String languageName = allAvailableLanguages.get(languageCode);

									if(languageName != null){

										Language newLanguage = null;

										languageName = getEncodedString(http.callUrlAndParseResult("en",          languageCode, languageName));

										if(!languageName.trim().isEmpty()){

											languageName = languageName.trim().toLowerCase();

											newLanguage = dao.getLanguage(languageName);

											if(newLanguage == null){
												newLanguage = new Language();
											}
											if(newLanguage.getLanguageCode() == null){
												newLanguage.setLanguageCode(languageCode.trim());	
											}
											if(newLanguage.getValue() == null){
												newLanguage.setValue(languageName.toLowerCase());	
											}
											if(newLanguage.getBible() == null){
												newLanguage.setBible(getEncodedString(http.callUrlAndParseResult("en",          languageCode, language.getBible())));	
											}
											if(newLanguage.getChurch() == null){
												newLanguage.setChurch(getEncodedString(http.callUrlAndParseResult("en",         languageCode, language.getChurch())));
											}
											if(newLanguage.getNoBackVerse() == null){
												newLanguage.setNoBackVerse(getEncodedString(http.callUrlAndParseResult("en",    languageCode, language.getNoBackVerse())));
											}
											if(newLanguage.getNoBook() == null){
												newLanguage.setNoBook(getEncodedString(http.callUrlAndParseResult("en",         languageCode, language.getNoBook())));
											}
											if(newLanguage.getNoChapter() == null){
												newLanguage.setNoChapter(getEncodedString(http.callUrlAndParseResult("en",      languageCode, language.getNoChapter())));
											}
											if(newLanguage.getNoNextVerse() == null){
												newLanguage.setNoNextVerse(getEncodedString(http.callUrlAndParseResult("en",    languageCode, language.getNoNextVerse())));
											}
											if(newLanguage.getNoVerse() == null){
												newLanguage.setNoVerse(getEncodedString(http.callUrlAndParseResult("en",        languageCode, language.getNoVerse())));
											}
											if(newLanguage.getNoVerseSelected() == null){
												newLanguage.setNoVerseSelected(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getNoVerseSelected())));
											}
											if(newLanguage.getNoVersion() == null){
												newLanguage.setNoVersion(getEncodedString(http.callUrlAndParseResult("en",      languageCode, language.getNoVersion())));
											}
											if(newLanguage.getSelectBook() == null){
												newLanguage.setSelectBook(getEncodedString(http.callUrlAndParseResult("en",     languageCode, language.getSelectBook())));
											}
											if(newLanguage.getSelectChapter() == null){
												newLanguage.setSelectChapter(getEncodedString(http.callUrlAndParseResult("en",  languageCode, language.getSelectChapter())));
											}
											if(newLanguage.getSelectLanguage() == null){
												newLanguage.setSelectLanguage(getEncodedString(http.callUrlAndParseResult("en", languageCode, language.getSelectLanguage())));
											}
											if(newLanguage.getSelectVerse() == null){
												newLanguage.setSelectVerse(getEncodedString(http.callUrlAndParseResult("en",    languageCode, language.getSelectVerse())));
											}
											if(newLanguage.getSelectVersion() == null){
												newLanguage.setSelectVersion(getEncodedString(http.callUrlAndParseResult("en",  languageCode, language.getSelectVersion())));
											}
											if(newLanguage.getSettings() == null){
												newLanguage.setSettings(getEncodedString(http.callUrlAndParseResult("en",       languageCode, language.getSettings())));
											}
											if(newLanguage.getReferences() == null){
												newLanguage.setReferences(getEncodedString(http.callUrlAndParseResult("en",      languageCode, language.getReferences())));
											}
											if(newLanguage.getDisplayReferences() == null){
												newLanguage.setDisplayReferences(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getDisplayReferences())));
											}
											if(newLanguage.getNoResult() == null){
												newLanguage.setNoResult(getEncodedString(http.callUrlAndParseResult("en",         languageCode, language.getNoResult())));
											}
											if(newLanguage.getWordWrap() == null){
												newLanguage.setWordWrap(getEncodedString(http.callUrlAndParseResult("en",         languageCode, language.getWordWrap())));
											}
											if(newLanguage.getDisplayEntireChapter() == null){
												newLanguage.setDisplayEntireChapter(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getDisplayEntireChapter())));
											}
											if(newLanguage.getHighlightsText() == null){
												newLanguage.setHighlightsText(getEncodedString(http.callUrlAndParseResult("en",		 languageCode, language.getHighlightsText())));
											}
											if(newLanguage.getSearchBlockLength() == null){
												newLanguage.setSearchBlockLength(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSearchBlockLength())));
											}
											if(newLanguage.getInexactColors() == null){
												newLanguage.setInexactColors(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getInexactColors())));
											}

											if(newLanguage.getSearchHistory() == null){
												newLanguage.setSearchHistory(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSearchHistory())));
											}
											if(newLanguage.getBold() == null){
												newLanguage.setBold(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getBold())));
											}
											if(newLanguage.getPopupMarginTop() == null){
												newLanguage.setPopupMarginTop(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getPopupMarginTop())));
											}

											if(newLanguage.getPopupMarginBottom() == null){
												newLanguage.setPopupMarginBottom(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getPopupMarginBottom())));
											}

											if(newLanguage.getPopupMarginRight() == null){
												newLanguage.setPopupMarginRight(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getPopupMarginRight())));
											}

											if(newLanguage.getPopupMarginLeft() == null){
												newLanguage.setPopupMarginLeft(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getPopupMarginLeft())));
											}

											if(newLanguage.getPopupTextAlign() == null){
												newLanguage.setPopupTextAlign(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getPopupTextAlign())));
											}

											if(newLanguage.getPopupBackgroundColor() == null){
												newLanguage.setPopupBackgroundColor(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getPopupBackgroundColor())));
											}

											if(newLanguage.getPicture() == null){
												newLanguage.setPicture(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getPicture())));
											}

											if(newLanguage.getPopupUserMessage() == null){
												newLanguage.setPopupUserMessage(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getPopupUserMessage())));
											}

											if(newLanguage.getPopupTextColor() == null){
												newLanguage.setPopupTextColor(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getPopupTextColor())));
											}

											if(newLanguage.getRadioScriptureText() == null){
												newLanguage.setRadioScriptureText(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getRadioScriptureText())));
											}

											if(newLanguage.getRadioUserMessage() == null){
												newLanguage.setRadioUserMessage(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getRadioUserMessage())));
											}

											if(newLanguage.getLeft() == null){
												newLanguage.setLeft(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getLeft())));
											}

											if(newLanguage.getRight() == null){
												newLanguage.setRight(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getRight())));
											}

											if(newLanguage.getCenter() == null){
												newLanguage.setCenter(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getCenter())));
											}

											if(newLanguage.getJustify() == null){
												newLanguage.setJustify(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getJustify())));
											}

											if(newLanguage.getInitial() == null){
												newLanguage.setInitial(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getInitial())));
											}

											if(newLanguage.getInherit() == null){
												newLanguage.setInherit(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getInherit())));
											}

											if(newLanguage.getWrongFileSize() == null){
												newLanguage.setWrongFileSize(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getWrongFileSize())));
											}

											if(newLanguage.getWrongFormat() == null){
												newLanguage.setWrongFormat(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getWrongFormat())));
											}

											if(newLanguage.getWrongFiles() == null){
												newLanguage.setWrongFiles(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getWrongFiles())));
											}

											if(newLanguage.getScreensaver() == null){
												newLanguage.setScreensaver(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getScreensaver())));
											}

											if(newLanguage.getAllCategories() == null){
												newLanguage.setAllCategories(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getAllCategories())));
											}

											if(newLanguage.getAngels() == null){
												newLanguage.setAngels(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getAngels())));
											}

											if(newLanguage.getClouds() == null){
												newLanguage.setClouds(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getClouds())));
											}

											if(newLanguage.getCross() == null){
												newLanguage.setCross(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getCross())));
											}

											if(newLanguage.getChrist() == null){
												newLanguage.setChrist(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getChrist())));
											}

											if(newLanguage.getOthers() == null){
												newLanguage.setOthers(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getOthers())));
											}

											if(newLanguage.getPigeons() == null){
												newLanguage.setPigeons(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getPigeons())));
											}

											if(newLanguage.getScreensaverImageRadio() == null){
												newLanguage.setScreensaverImageRadio(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getScreensaverImageRadio())));
											}

											if(newLanguage.getScreensaverVerseRadio() == null){
												newLanguage.setScreensaverVerseRadio(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getScreensaverVerseRadio())));
											}

											if(newLanguage.getScreensaverBothRadio() == null){
												newLanguage.setScreensaverBothRadio(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getScreensaverBothRadio())));
											}

											if(newLanguage.getScreensaverBibleSelection() == null){
												newLanguage.setScreensaverBibleSelection(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getScreensaverBibleSelection())));
											}

											if(newLanguage.getNoVersionSelected() == null){
												newLanguage.setNoVersionSelected(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getNoVersionSelected())));
											}

											if(newLanguage.getAutomatScreensaver() == null){
												newLanguage.setAutomatScreensaver(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getAutomatScreensaver())));
											}

											if(newLanguage.getManualScreensaver() == null){
												newLanguage.setManualScreensaver(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getManualScreensaver())));
											}
											if(newLanguage.getBackgroundColor() == null){
												newLanguage.setBackgroundColor(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getBackgroundColor())));
											}
											if(newLanguage.getFont() == null){
												newLanguage.setFont(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getFont())));
											}
											if(newLanguage.getAlignment() == null){
												newLanguage.setAlignment(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getAlignment())));
											}
											if(newLanguage.getSchema() == null){
												newLanguage.setSchema(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSchema())));
											}
											if(newLanguage.getReferenceWordsSpacePopup() == null){
												newLanguage.setReferenceWordsSpacePopup(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getReferenceWordsSpacePopup())));
											}
											if(newLanguage.getEnterDownPopup() == null){
												newLanguage.setEnterDownPopup(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEnterDownPopup())));
											}
											if(newLanguage.getEnterUpPopup() == null){
												newLanguage.setEnterUpPopup(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEnterUpPopup())));
											}
											if(newLanguage.getDisplayVerseLabel() == null){
												newLanguage.setDisplayVerseLabel(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getDisplayVerseLabel())));
											}
											if(newLanguage.getDisplayChapterLabel() == null){
												newLanguage.setDisplayChapterLabel(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getDisplayChapterLabel())));
											}
											if(newLanguage.getDisplayBookLabel() == null){
												newLanguage.setDisplayBookLabel(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getDisplayBookLabel())));
											}
											if(newLanguage.getDisplayVersionLabel() == null){
												newLanguage.setDisplayVersionLabel(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getDisplayVersionLabel())));
											}
											if(newLanguage.getReference() == null){
												newLanguage.setReference(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getReference())));
											}

											if(newLanguage.getDefaultPopupImage() == null){
												newLanguage.setDefaultPopupImage(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getDefaultPopupImage())));
											}

											if(newLanguage.getFontFamilyPopup() == null){
												newLanguage.setFontFamilyPopup(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getFontFamilyPopup())));
											}

											if(newLanguage.getFontStylePopup() == null){
												newLanguage.setFontStylePopup(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getFontStylePopup())));
											}

											if(newLanguage.getLetterSpacingPopup() == null){
												newLanguage.setLetterSpacingPopup(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getLetterSpacingPopup())));
											}

											if(newLanguage.getLineHeightPopup() == null){
												newLanguage.setLineHeightPopup(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getLineHeightPopup())));
											}

											if(newLanguage.getWordSpacingPopup() == null){
												newLanguage.setWordSpacingPopup(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getWordSpacingPopup())));
											}

											if(newLanguage.getReferenceLetterSpacingPopup() == null){
												newLanguage.setReferenceLetterSpacingPopup(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getReferenceLetterSpacingPopup())));
											}

											if(newLanguage.getReferenceFontFamilyPopup() == null){
												newLanguage.setReferenceFontFamilyPopup(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getReferenceFontFamilyPopup())));
											}

											if(newLanguage.getReferenceFontStylePopup() == null){
												newLanguage.setReferenceFontStylePopup(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getReferenceFontStylePopup())));
											}

											if(newLanguage.getReferenceFontDecorationPopup() == null){
												newLanguage.setReferenceFontDecorationPopup(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getReferenceFontDecorationPopup())));
											}

											if(newLanguage.getCode() == null){
												newLanguage.setCode(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getCode())));
											}

											if(newLanguage.getPopup() == null){
												newLanguage.setPopup(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getPopup())));
											}

											if(newLanguage.getExactColors() == null){
												newLanguage.setExactColors(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getExactColors())));
											}

											if(newLanguage.getImageOpacity() == null){
												newLanguage.setImageOpacity(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getImageOpacity())));
											}

											if(newLanguage.getTextOpacity() == null){
												newLanguage.setTextOpacity(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getTextOpacity())));
											}

											if(newLanguage.getSelectedVersion() == null){
												newLanguage.setSelectedVersion(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSelectedVersion())));
											}

											if(newLanguage.getSelectedBook() == null){
												newLanguage.setSelectedBook(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSelectedBook())));
											}

											if(newLanguage.getSelectedChapter() == null){
												newLanguage.setSelectedChapter(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSelectedChapter())));
											}

											if(newLanguage.getCurrentSelection() == null){
												newLanguage.setCurrentSelection(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getCurrentSelection())));
											}

											if(newLanguage.getSearchLevel() == null){
												newLanguage.setSearchLevel(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSearchLevel())));
											}

											if(newLanguage.getNotOkEmail() == null){
												newLanguage.setNotOkEmail(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getNotOkEmail())));
											}

											if(newLanguage.getOkEmail() == null){
												newLanguage.setOkEmail(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getOkEmail())));
											}

											if(newLanguage.getInvitationEmail() == null){
												newLanguage.setInvitationEmail(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getInvitationEmail())));
											}

											if(newLanguage.getEmailTitle() == null){
												newLanguage.setEmailTitle(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEmailTitle())));
											}

											if(newLanguage.getEmailContent() == null){
												newLanguage.setEmailContent(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEmailContent())));
											}

											if(newLanguage.getEventDescription() == null){
												newLanguage.setEventDescription(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEventDescription())));
											}

											if(newLanguage.getEmailContentValue() == null){
												newLanguage.setEmailContentValue(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEmailContentValue())));
											}

											if(newLanguage.getEmailTitleValue() == null){
												newLanguage.setEmailTitleValue(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEmailTitleValue())));
											}

											if(newLanguage.getChurchEmail() == null){
												newLanguage.setChurchEmail(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getChurchEmail())));
											}

											if(newLanguage.getChurchEmailPassword() == null){
												newLanguage.setChurchEmailPassword(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getChurchEmailPassword())));
											}

											if(newLanguage.getUsername() == null){
												newLanguage.setUsername(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getUsername())));
											}

											if(newLanguage.getPassword() == null){
												newLanguage.setPassword(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getPassword())));
											}

											if(newLanguage.getEvent() == null){
												newLanguage.setEvent(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEvent())));
											}

											if(newLanguage.getAccount() == null){
												newLanguage.setAccount(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getAccount())));
											}

											if(newLanguage.getLogin() == null){
												newLanguage.setLogin(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getLogin())));
											}

											if(newLanguage.getCreateAccount() == null){
												newLanguage.setCreateAccount(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getCreateAccount())));
											}

											if(newLanguage.getDeleteAccount() == null){
												newLanguage.setDeleteAccount(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getDeleteAccount())));
											}

											if(newLanguage.getUpdateAccount() == null){
												newLanguage.setUpdateAccount(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getUpdateAccount())));
											}

											if(newLanguage.getLogout() == null){
												newLanguage.setLogout(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getLogout())));
											}

											if(newLanguage.getSuccessfullLogout() == null){
												newLanguage.setSuccessfullLogout(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSuccessfullLogout())));
											}

											if(newLanguage.getFailureLogin() == null){
												newLanguage.setFailureLogin(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getFailureLogin())));
											}

											if(newLanguage.getSuccessfullDelete() == null){
												newLanguage.setSuccessfullDelete(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSuccessfullDelete())));
											}

											if(newLanguage.getErrorDelete() == null){
												newLanguage.setErrorDelete(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getErrorDelete())));
											}

											if(newLanguage.getSuccessfullUpdated() == null){
												newLanguage.setSuccessfullUpdated(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSuccessfullUpdated())));
											}

											if(newLanguage.getErrorUpdated() == null){
												newLanguage.setErrorUpdated(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getErrorUpdated())));
											}

											if(newLanguage.getCreateEvent() == null){
												newLanguage.setCreateEvent(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getCreateEvent())));
											}

											if(newLanguage.getUpdateEvent() == null){
												newLanguage.setUpdateEvent(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getUpdateEvent())));
											}

											if(newLanguage.getDeleteEvent() == null){
												newLanguage.setDeleteEvent(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getDeleteEvent())));
											}

											if(newLanguage.getEventName() == null){
												newLanguage.setEventName(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEventName())));
											}

											if(newLanguage.getEventDate() == null){
												newLanguage.setEventDate(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEventDate())));
											}

											if(newLanguage.getSuccessfullEventCreated() == null){
												newLanguage.setSuccessfullEventCreated(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSuccessfullEventCreated())));
											}

											if(newLanguage.getEventCreationError() == null){
												newLanguage.setEventCreationError(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEventCreationError())));
											}

											if(newLanguage.getEventsLabel() == null){
												newLanguage.setEventsLabel(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEventsLabel())));
											}

											if(newLanguage.getChurchesLabel() == null){
												newLanguage.setChurchesLabel(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getChurchesLabel())));
											}

											if(newLanguage.getNoEventLabel() == null){
												newLanguage.setNoEventLabel(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getNoEventLabel())));
											}

											if(newLanguage.getNoChurchLabel() == null){
												newLanguage.setNoChurchLabel(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getNoChurchLabel())));
											}

											if(newLanguage.getFailurEventUpdate() == null){
												newLanguage.setFailurEventUpdate(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getFailurEventUpdate())));
											}

											if(newLanguage.getSuccessfullEventUpdate() == null){
												newLanguage.setSuccessfullEventUpdate(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSuccessfullEventUpdate())));
											}

											if(newLanguage.getFailurEventDelete() == null){
												newLanguage.setFailurEventDelete(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getFailurEventDelete())));
											}

											if(newLanguage.getSuccessfullEventDelete() == null){
												newLanguage.setSuccessfullEventDelete(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSuccessfullEventDelete())));
											}

											if(newLanguage.getEventDuration() == null){
												newLanguage.setEventDuration(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEventDuration())));
											}

											if(newLanguage.getMinutes() == null){
												newLanguage.setMinutes(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getMinutes())));
											}

											if(newLanguage.getParticipantName() == null){
												newLanguage.setParticipantName(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getParticipantName())));
											}

											if(newLanguage.getParticipantForename() == null){
												newLanguage.setParticipantForename(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getParticipantForename())));
											}

											if(newLanguage.getDurationParticipant() == null){
												newLanguage.setDurationParticipant(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getDurationParticipant())));
											}

											if(newLanguage.getResources() == null){
												newLanguage.setResources(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getResources())));
											}

											if(newLanguage.getParticipantPhone() == null){
												newLanguage.setParticipantPhone(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getParticipantPhone())));
											}

											if(newLanguage.getParticipantEmail() == null){
												newLanguage.setParticipantEmail(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getParticipantEmail())));
											}

											if(newLanguage.getEventPassword() == null){
												newLanguage.setEventPassword(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getEventPassword())));
											}

											if(newLanguage.getOkParticipantAdded() == null){
												newLanguage.setOkParticipantAdded(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getOkParticipantAdded())));
											}

											if(newLanguage.getErrorParticipantAdded() == null){
												newLanguage.setErrorParticipantAdded(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getErrorParticipantAdded())));
											}

											if(newLanguage.getErrorParticipantDeleted() == null){
												newLanguage.setErrorParticipantDeleted(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getErrorParticipantDeleted())));
											}

											if(newLanguage.getOkParticipantDeleted() == null){
												newLanguage.setOkParticipantDeleted(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getOkParticipantDeleted())));
											}

											if(newLanguage.getWrongPassword() == null){
												newLanguage.setWrongPassword(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getWrongPassword())));
											}

											if(newLanguage.getMessage() == null){
												newLanguage.setMessage(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getMessage())));
											}

											if(newLanguage.getOrder() == null){
												newLanguage.setOrder(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getOrder())));
											}

											if(newLanguage.getTotalParticipantTime() == null){
												newLanguage.setTotalParticipantTime(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getTotalParticipantTime())));
											}

											if(newLanguage.getDownloadAttachments() == null){
												newLanguage.setDownloadAttachments(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getDownloadAttachments())));
											}

											if(newLanguage.getSendInvitation() == null){
												newLanguage.setSendInvitation(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSendInvitation())));
											}

											if(newLanguage.getAccessInvitation() == null){
												newLanguage.setAccessInvitation(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getAccessInvitation())));
											}

											if(newLanguage.getSuccessfulLogin() == null){
												newLanguage.setSuccessfulLogin(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSuccessfulLogin())));
											}

											if(newLanguage.getUsernameError() == null){
												newLanguage.setUsernameError(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getUsernameError())));
											}

											if(newLanguage.getExistEmailError() == null){
												newLanguage.setExistEmailError(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getExistEmailError())));
											}

											if(newLanguage.getInvalidEmailFormat() == null){
												newLanguage.setInvalidEmailFormat(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getInvalidEmailFormat())));
											}

											if(newLanguage.getSuccessfulChurchCreation() == null){
												newLanguage.setSuccessfulChurchCreation(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSuccessfulChurchCreation())));
											}

											if(newLanguage.getVoice() == null){
												newLanguage.setVoice(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getVoice())));
											}

											if(newLanguage.getUseVoice() == null){
												newLanguage.setUseVoice(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getUseVoice())));
											}

											if(newLanguage.getThankYouMessage() == null){
												newLanguage.setThankYouMessage(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getThankYouMessage())));
											}

											if(newLanguage.getThankYou() == null){
												newLanguage.setThankYou(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getThankYou())));
											}

											if(newLanguage.getFeedback() == null){
												newLanguage.setFeedback(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getFeedback())));
											}

											if(newLanguage.getSuggestions() == null){
												newLanguage.setSuggestions(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSuggestions())));
											}

											if(newLanguage.getBugs() == null){
												newLanguage.setBugs(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getBugs())));
											}

											if(newLanguage.getQuestions() == null){
												newLanguage.setQuestions(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getQuestions())));
											}

											if(newLanguage.getSuggestion() == null){
												newLanguage.setSuggestion(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSuggestion())));
											}

											if(newLanguage.getBug() == null){
												newLanguage.setBug(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getBug())));
											}

											if(newLanguage.getQuestion() == null){
												newLanguage.setQuestion(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getQuestion())));
											}

											if(newLanguage.getiWantToJoin() == null){
												newLanguage.setiWantToJoin(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getiWantToJoin())));
											}

											if(newLanguage.getMyName() == null){
												newLanguage.setMyName(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getMyName())));
											}

											if(newLanguage.getMessageType() == null){
												newLanguage.setMessageType(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getMessageType())));
											}

											if(newLanguage.getMessagePassword() == null){
												newLanguage.setMessagePassword(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getMessagePassword())));
											}

											if(newLanguage.getOthersCaps() == null){
												newLanguage.setOthersCaps(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getOthersCaps())));
											}

											if(newLanguage.getiWantToJoinCaps() == null){
												newLanguage.setiWantToJoinCaps(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getiWantToJoinCaps())));
											}

											if(newLanguage.getSearchByReference() == null){
												newLanguage.setSearchByReference(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSearchByReference())));
											}

											if(newLanguage.getSearchByText() == null){
												newLanguage.setSearchByText(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getSearchByText())));
											}

											if(newLanguage.getPlaceholderSuggestion() == null){
												newLanguage.setPlaceholderSuggestion(getEncodedString(http.callUrlAndParseResult("en",languageCode, language.getPlaceholderSuggestion())));
											}

											if(newLanguage != null){
												dao.insertTranslation(newLanguage);
											}
										}
									}
								}
								catch (Exception e){
									writeFile(e, null);
									System.err.println("Error: " + e.getMessage());
									System.err.println("Error: " + e.getCause());			
									System.err.println("Error: " + e.getStackTrace());
									System.err.println("Error: " + e.fillInStackTrace());
									System.err.println("Error: " + e.getLocalizedMessage());
								}	
							}
						}
					}
				}
			}
			catch (Exception e){
				writeFile(e, null);
				System.err.println("Error: " + e.getMessage());
				System.err.println("Error: " + e.getCause());			
				System.err.println("Error: " + e.getStackTrace());
				System.err.println("Error: " + e.fillInStackTrace());
				System.err.println("Error: " + e.getLocalizedMessage());
			}			

			model.addAttribute("bible");
		} catch (Exception e) {
			writeFile(e, null);
		}
		System.out.println("Finish translation");
		writeFile(null, "Finish translation");

		return new ModelAndView("tools", model);
	}

	public void writeFile(Exception exception, String message) {

		String path = Constants.bibleLog.value + Constants.platformType.value + "TestLog.txt";

		File file = new File(path.trim());
		try{
			if (file != null && !file.exists()){
				file.createNewFile();
				file.setReadable(true);
				file.setWritable(true);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {

			String stackTraceInfo = "";

			String entireMessage = "\n\n" + (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date()) + " " + "\n\n";

			if(exception != null){

				exception.printStackTrace();

				StackTraceElement[] stackTrace = exception.getStackTrace();

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

				entireMessage += stackTraceInfo + "\n" + exception.getMessage() + "\n" + exception.getCause() + "\n" + exception.getLocalizedMessage() + "\n\n";
			}

			if(message != null && !message.trim().isEmpty()){
				entireMessage += message.trim();
			}

			entireMessage += "\n\n==========================================================\n\n";

			writer.write(entireMessage);

			writer.flush();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private final String getEncodedString(String string){

		try{

			if(string != null && !string.trim().isEmpty()){

				string = string.trim();

				String result = new String(string.getBytes(UTF8_CHARSET), UTF8_CHARSET);

				return result.trim();

			}
		}
		catch (Exception e){
			writeFile(e, null);
			System.err.println("Error: " + e.getMessage());
			System.err.println("Error: " + e.getCause());			
			System.err.println("Error: " + e.getStackTrace());
			System.err.println("Error: " + e.fillInStackTrace());
			System.err.println("Error: " + e.getLocalizedMessage());
		}

		return "";
	}
}