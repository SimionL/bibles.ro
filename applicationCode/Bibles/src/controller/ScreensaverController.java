package controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import dbBeans.ScreensaverTable;
import utilities.AppBean;
import utilities.Beans;
import utilities.Constants;
import utilities.Utilities;

@Controller
@Transactional
@Scope("prototype")
public class ScreensaverController {

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

	private final String dbPath = Constants.dbPath.value;
	private final String platformType = Constants.platformType.value;
	private final String picturesPath = Constants.picturesPath.value;

	@RequestMapping(value = "/ajaxCall.htm", method = RequestMethod.POST, headers = "Accept=*/*", produces = "text/html;charset=UTF-8")
	public @ResponseBody String ajaxRequest(@RequestParam("param") String param, HttpServletRequest request, HttpServletResponse response){

		try{

			if(request != null){

				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");

				screensaver.setPopupInformation(null);

				switch(param){
				case "1" : {

					setNextScreensaverPopup();
					break;
				}
				case "2" : {

					if(popup != null && app != null && bible != null){

						int screensaverRadioSelected = screensaver.getScreensaverRadioSelected();

						if(screensaverRadioSelected == 3 || screensaverRadioSelected == 4){
							new Utilities().setDefaultPopupImageColor(popup);
						}

						if(screensaver.getScreensaverRadioSelected() == 4){
							setNextScreensaverBibleSelection();
						}
						else{
							setNextScreensaverPopup();	
						}
					}
					break;
				}
				case "3" : {

					if(popup != null && app != null && bible != null){

						int screensaverRadioSelected = screensaver.getScreensaverRadioSelected();

						if(screensaverRadioSelected == 3 || screensaverRadioSelected == 4){
							new Utilities().setDefaultPopupImageColor(popup);
						}

						if(screensaver.getScreensaverRadioSelected() == 4){
							setBackScreensaverBibleSelection();
						}
						else{
							setBackScreensaverPopup();
						}
					}
					break;
				}
				}

				String result = screensaver.getPopupInformation();

				if(result == null){
					screensaver.setOpenPopup(false);
					clean();
				}
				else{
					screensaver.setOpenPopup(true);
				}

				return result;
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}

		return "";
	}

	@RequestMapping(value = "/screensaver", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ModelAndView screensaver(ModelMap model, Screensaver screensaverForm, HttpServletRequest request, HttpServletResponse response){

		try{

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			screensaver.setPlaySong(screensaverForm.isPlaySong());
			screensaver.setMelodieSelected(screensaverForm.getMelodieSelected());

			String eventId = screensaverForm.getEventId();

			if(eventId != null && !eventId.trim().isEmpty()){

				clean(); 

				eventId = eventId.trim();
				Utilities utilities = new Utilities();
				switch(eventId){
				case "1" : {

					boolean openPopup = screensaver.isOpenPopup();

					stopScreensaver();
					new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, openPopup);

					utilities.setModel(model, bible, null, null, null, null, null, null, null, null, null);

					return new ModelAndView("bible", model);
				}
				case "2" : {

					stopScreensaver();
					utilities.setModel(model, null, church, null, null,null, null, null, null, null, null);

					return new ModelAndView("church", model);
				}
				case "3" : {

					stopScreensaver();

					String pageName = new Utilities().goToSettingsPage(model, settings, popup, reference, voice, app, null);

					if(pageName != null && !pageName.trim().isEmpty()) {
						return new ModelAndView(pageName, model);
					}

					break;
				}
				case "4" : {

					stopScreensaver();
					utilities.setModel(model, null, null, null, null, null,null, code, null, null, null);

					return new ModelAndView("code", model);
				}
				case "5" : {

					stopScreensaver();
					utilities.setModel(model, null, null, null, null, null,null, null, voice, null, null);

					return new ModelAndView("voice", model);
				}
				case "6" : {
					ModelAndView result = executtingVoiceCommand(screensaverForm, model);
					if(result != null){
						return result;
					}
					break;
				}
				case "7" : {

					screensaver.setPlaySong(true);
					screensaver.setMelodieSelected(screensaverForm.getMelodieSelected());

					break;
				}
				case "8" : {

					if(popup != null && bible != null){

						new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, true);

						startScreensaver();
					}

					break;
				}
				case "9" : {

					setNextMelody(screensaverForm);

					break;
				}
				case "10" : {

					screensaver.setOpenPopup(false);
					clean(); 
					break;
				}
				case "11" : {
					utilities.setModel(model, null, null, null, null, null, null, null, null, null, feedback);
					return new ModelAndView("feedback", model);
				}
				case "12" : {
					utilities.setModel(model, null, null, null, null, null, null, null, null, thankYou, null);
					return new ModelAndView("thankYou", model);
				}

				case "31" : {

					if(screensaver != null && screensaverForm != null && bible != null){
						setScreensaverTime(screensaverForm.getScreensaverTimeSelected());
					}

					utilities.setModel(model, null, null, null, null,null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}

				case "32" : {

					if(popup != null && screensaverForm != null && bible != null){
						setScreensaverCategorie(screensaverForm.getScreensaverCategoriesSelected());
					}
					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}

				case "34" : {

					if(screensaver != null && popup != null && bible != null){
						stopScreensaver();
					}
					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}

				case "35" : {

					if(popup != null && bible != null){
						setNextScreensaverPopup();
					}
					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}

				case "36" : {

					if(popup != null && screensaverForm != null && bible != null){
						setScreensaverRadio(screensaverForm.getScreensaverRadioSelected());
					}
					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}

				case "37" : {

					if(popup != null && screensaverForm != null){
						setScreensaverTypeSelected(screensaverForm.getScreensaverTypeSelected());
					}
					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}

				case "62" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverParamSelected_1(screensaverForm.getScreensaverParamSelected_1());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "63" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverParamSelected_2(screensaverForm.getScreensaverParamSelected_2());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "64" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverParamSelected_3(screensaverForm.getScreensaverParamSelected_3());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}					
				case "65" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverParamSelected_4(screensaverForm.getScreensaverParamSelected_4());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "66" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverParamSelected_5(screensaverForm.getScreensaverParamSelected_5());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "67" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverParamSelected_6(screensaverForm.getScreensaverParamSelected_6());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "68" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverColorPaletteSelected_1(screensaverForm.getScreensaverColorPaletteSelected_1());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "69" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverColorPaletteSelected_2(screensaverForm.getScreensaverColorPaletteSelected_2());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "72" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverParamSelected_7(screensaverForm.getScreensaverParamSelected_7());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "73" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverParamSelected_8(screensaverForm.getScreensaverParamSelected_8());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "74" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverParamSelected_9(screensaverForm.getScreensaverParamSelected_9());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "75" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverParamSelected_10(screensaverForm.getScreensaverParamSelected_10());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "76" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverParamSelected_11(screensaverForm.getScreensaverParamSelected_11());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}					
				case "77" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverParamSelected_12(screensaverForm.getScreensaverParamSelected_12());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "78" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverColorPaletteSelected_3(screensaverForm.getScreensaverColorPaletteSelected_3());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "79" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverColorPaletteSelected_4(screensaverForm.getScreensaverColorPaletteSelected_4());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "82" : {

					if(popup != null && screensaverForm != null){
						screensaver.setScreensaverColorPaletteSelected_5(screensaverForm.getScreensaverColorPaletteSelected_5());
					}

					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}

		new Utilities().setModel(model, null, null, null, null,null, screensaver, null, null, null, null);
		return new ModelAndView("screensaver", model);
	}

	private ModelAndView executtingVoiceCommand(Screensaver screensaverForm, ModelMap model){

		try{

			String theSpokenWords = screensaverForm.getIdentifiedWords();

			if(theSpokenWords != null && !theSpokenWords.trim().isEmpty()){

				if(!theSpokenWords.contains(" ") && !theSpokenWords.contains("	")){

					return new Utilities().navigateByVoice(voice, bible, settings, popup, reference, church, screensaver, code, app, model, theSpokenWords);
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
		return null;
	}

	private void setScreensaverCategorie(int screensaverCategorieSelected){

		try{
			if(app != null && popup != null){

				screensaver.setScreensaverCategoriesSelected(screensaverCategorieSelected);
				app.setCurrentScreensaver(0);

				final TreeMap<Integer, ScreensaverTable> screensaverMap = app.getScreensaverMap();
				final Map<String, String> selectedImagesMap = app.getSelectedImagesMap();

				if(screensaverMap != null){
					screensaverMap.clear();
				}

				if(selectedImagesMap != null){
					selectedImagesMap.clear();
				}

				new Utilities().loadResources(app, screensaver, dao);
				if(screensaver.isOpenPopup() && screensaver.getScreensaverTypeSelected() == 1){
					startScreensaver();	
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setNextMelody(Screensaver screensaverForm){

		try{

			screensaver.setMelodieSelected(null);

			String melodieSelected = screensaverForm.getMelodieSelected();

			if(melodieSelected != null && !melodieSelected.trim().isEmpty()){
				final TreeSet<String> melodiesList = screensaver.getMelodiesList();
				if(melodiesList != null && !melodiesList.isEmpty()){

					String firstMelody = null;
					String lastMelody = null;

					for(String melody : melodiesList){
						if(melody != null && !melody.trim().isEmpty()){

							if(firstMelody == null){
								firstMelody = melody;
							}

							if(lastMelody != null && melodieSelected.trim().equals(lastMelody.trim())){
								screensaver.setMelodieSelected(melody);
								break;
							}
							lastMelody = melody;		
						}
					}
					if(screensaver.getMelodieSelected() == null){
						screensaver.setMelodieSelected(firstMelody);
					}
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setScreensaverTime(int screensaverTimeSelected){

		try{
			if(bible != null && screensaver != null){
				screensaver.setScreensaverTimeSelected(screensaverTimeSelected);

				if(screensaver.isOpenPopup()){
					startScreensaver();	
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void startScreensaver(){

		try{
			if(bible != null && popup != null && app != null){

				int screensaverRadioSelected = screensaver.getScreensaverRadioSelected();

				if(screensaverRadioSelected == 3 || screensaverRadioSelected == 4){
					new Utilities().setDefaultPopupImageColor(popup);
				}

				final TreeMap<Integer, ScreensaverTable> screensaverMap = app.getScreensaverMap();

				if(screensaverMap != null){

					if(screensaverMap.isEmpty()){
						screensaverMap.putAll(dao.getScreensaversMap(screensaver.getScreensaverCategoriesSelected()));
					}

					if(!screensaverMap.isEmpty()){
						setNextScreensaverPopup();
					}
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void stopScreensaver(){

		try{
			if(app != null && screensaver != null && bible != null){

				screensaver.setOpenPopup(false);
				app.setCurrentScreensaver(0);

				final Map<String, String> selectedImagesMap = app.getSelectedImagesMap();

				if(selectedImagesMap != null){
					selectedImagesMap.clear();
				}
				new Utilities().emptySelection(app);
				new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, false);
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setScreensaverTypeSelected(final int screensaverTypeSelected){

		try{
			screensaver.setScreensaverTypeSelected(screensaverTypeSelected);
			if(screensaverTypeSelected == 1){
				startScreensaver();
			}

		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setScreensaverRadio(int screensaverRadioSelected){

		try{
			if(popup != null){

				screensaver.setScreensaverRadioSelected(screensaverRadioSelected);

				if(screensaverRadioSelected == 3 || screensaverRadioSelected == 4){
					new Utilities().setDefaultPopupImageColor(popup);
				}

				if(screensaver.isOpenPopup() && screensaver.getScreensaverTypeSelected() == 1){
					startScreensaver();	
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setScreensaverImage(boolean addPicture){

		try{
			if(popup != null && app != null){

				final TreeMap<Integer, ScreensaverTable> screensaverMap = app.getScreensaverMap();
				int currentScreensaver = app.getCurrentScreensaver();
				if(screensaverMap != null && !screensaverMap.isEmpty() && screensaverMap.containsKey(currentScreensaver)){
					Map<String, String> selectedImagesMap = app.getSelectedImagesMap();
					if(selectedImagesMap != null){
						selectedImagesMap.clear();
						ScreensaverTable screensaverObject = screensaverMap.get(currentScreensaver);
						if(screensaverObject != null){

							String fileSelected = picturesPath + platformType + screensaverObject.getCategory()+ platformType + screensaverObject.getPictureName();

							if(fileSelected != null && !fileSelected.trim().isEmpty() && fileSelected.contains(".") && Files.exists(Paths.get(fileSelected))){

								String format = fileSelected.substring(fileSelected.lastIndexOf(".") + 1);

								if(format != null && !format.trim().isEmpty() &&(
										format.equals("JPEG") ||
										format.equals("jpeg") ||
										format.equals("JPG")  ||
										format.equals("jpg")  ||
										format.equals("BMP")  ||
										format.equals("bmp")  ||
										format.equals("PNG")  ||
										format.equals("png")  ||
										format.equals("GIF")  ||
										format.equals("gif")
										)){

									File picture = new File(fileSelected);

									if(picture != null && picture.exists() && picture.isFile()){

										byte[] imageBytes = Files.readAllBytes(Paths.get(fileSelected));

										if(imageBytes != null && imageBytes.length > 0){

											String imageText = Base64.getEncoder().encodeToString(imageBytes);

											if(imageText != null && !imageText.trim().isEmpty()){
												selectedImagesMap.put(fileSelected.trim(), imageText);
												screensaver.setOpenPopup(true);
												if(addPicture){
													new Utilities().addPicture(bible, church, settings, popup, reference, screensaver, code, Beans.screensaver, app);
												}
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
			new Utilities().writeFile(e);
		}
	}

	private void setNextScreensaverPopup(){

		try{

			if(popup != null && bible != null && app != null){

				app.setNext(true);

				final TreeMap<Integer, ScreensaverTable> screensaverMap = app.getScreensaverMap();

				if(screensaverMap != null && !screensaverMap.isEmpty()){

					setNextScreensaverIndex();

					setScreensaverPopupVariants();
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setNextScreensaverIndex(){

		try{

			final TreeMap<Integer, ScreensaverTable> screensaverMap = app.getScreensaverMap();

			if(screensaverMap != null && !screensaverMap.isEmpty()){

				int currentScreensaver = app.getCurrentScreensaver() + 1;

				if(screensaverMap.containsKey(currentScreensaver)){
					app.setCurrentScreensaver(currentScreensaver);
				}
				else{
					app.setCurrentScreensaver(0);
				}
			}

		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setScreensaverPopupVariants(){

		try{

			switch(screensaver.getScreensaverRadioSelected()){
			case 1 : {
				setScreensaverBoth();
				break;
			}
			case 2 : {
				setScreensaverImage(true);
				break;
			}
			case 3 : {

				new Utilities().setDefaultPopupImageColor(popup);

				isSetScreensaverVerse();
				new Utilities().addMultipleScripturePopupText(church, settings, code, popup, reference, bible, screensaver, app, Beans.screensaver);

				break;
			}
			case 4 : {
				new Utilities().setDefaultPopupImageColor(popup);
				setNextScreensaverBibleSelection();
				break;
			}
			}

		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setBackScreensaverBibleSelection(){

		try{
			String selectedVersion = bible.getSelectedVersion();
			String selectedBook    = bible.getSelectedBook();
			String selectedChapter = bible.getSelectedChapter();
			String selectedVerse   = bible.getSelectedVerse();

			if(new Utilities().isRealVersion(selectedVersion, bible)){

				if(selectedVerse != null && selectedVerse.trim().matches("\\d+")){

					if(isSetPreviousVerse(selectedVersion, selectedBook, selectedChapter, selectedVerse)){
						return;
					}
					else if(isSetPreviousUnknownVerse(selectedVersion, selectedBook, selectedChapter)){
						return;
					}
				}
				else if(isSetPreviousUnknownVerse(selectedVersion, selectedBook, selectedChapter)){
					return;
				}
			}
			else{
				stopScreensaver();
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private boolean isSetPreviousUnknownVerse(String version, String book, String chapter){

		try{
			if(chapter != null && chapter.trim().matches("\\d+") && isSetPreviousChapter(version, book, chapter)){
				return true;
			}
			else if (new Utilities().isRealBook(book, bible) && isSetPreviousBook(version, book)){
				return true;
			}
			else if(isSetLastVersionVerse(version)){
				return true;
			}
			else {
				stopScreensaver();
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
		return false;
	}

	private boolean isSetPreviousVerse(String version, String book, String chapter, String verse){

		try{
			int previousVerse = Integer.parseInt(verse) - 1;

			if(previousVerse > 0 && isSetNewVerse(version, book, chapter, previousVerse + "")){
				return true;
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
		return false;
	}

	private boolean isSetPreviousChapter(String version, String book, String chapter){

		try{
			int previousChapter = Integer.parseInt(chapter) - 1;

			if(previousChapter > 0 && isSetNewVerse(version, book, previousChapter + "", getLastChapterVerse(version, book, previousChapter + ""))){
				return true;
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
		return false;
	}

	private boolean isSetPreviousBook(String version, String book){

		try{
			String previousBook = getPreviousBook(version, book);
			String lastBookChapter = getLastBookChapter(version, previousBook);
			String lastChapterVerse = getLastChapterVerse(version, previousBook, lastBookChapter);

			if(isSetNewVerse(version, previousBook, lastBookChapter, lastChapterVerse)){
				return true;
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
		return false;
	}

	private boolean isSetLastVersionVerse(String version){

		try{
			String lastVersionBook =    getLastVersionBook(version);
			String lastVersionChapter = getLastBookChapter(version, lastVersionBook);
			String lastVersionVerse =   getLastChapterVerse(version, lastVersionBook, lastVersionChapter);

			if(isSetNewVerse(version, lastVersionBook, lastVersionChapter, lastVersionVerse)){
				return true;
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
		return false;
	}

	private String getPreviousBook(String version, String book){

		try{
			if(version != null){
				File versionFile = new File(dbPath + platformType + version);
				if(versionFile != null && versionFile.exists() && versionFile.canRead() && versionFile.isDirectory()){
					File[] bookFiles = versionFile.listFiles();
					if(bookFiles != null && bookFiles.length > 0){
						Map<Integer, String> bookMap = new HashMap<>();
						int previousBook = 0;
						for(File bookFile : bookFiles){
							if(bookFile != null && bookFile.isDirectory()){
								String bookName = bookFile.getName();
								if(bookName != null && !bookName.trim().isEmpty() && bookName.trim().contains("_") && !bookName.trim().startsWith("0")){
									String bookN = bookName.substring(0, bookName.indexOf("_"));
									if(bookN != null && bookN.trim().matches("\\d+")){
										int bookIntNumber = Integer.parseInt(bookN.trim());
										if(bookName.substring(bookName.indexOf("_") + 1, bookName.length()).equals(book)){
											previousBook = bookIntNumber - 1;
										}
										bookMap.put(bookIntNumber, bookN);
									}
								}
							}
						}
						if(bookMap.containsKey(previousBook)){
							return bookMap.get(previousBook);
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		return null;
	}

	private String getLastVersionBook(String version){

		int maxBook = 0;

		try{
			if(version != null){
				File versionFile = new File(dbPath + platformType + version);
				if(versionFile != null && versionFile.exists() && versionFile.canRead() && versionFile.isDirectory()){

					File[] bookFiles = versionFile.listFiles();
					if(bookFiles != null && bookFiles.length > 0){
						for(File bookFile : bookFiles){
							if(bookFile != null && bookFile.isDirectory()){
								String bookName = bookFile.getName();
								if(bookName != null && !bookName.trim().isEmpty() && bookName.trim().contains("_") && !bookName.trim().startsWith("0")){
									String bookN = bookName.substring(0, bookName.indexOf("_"));
									if(bookN != null && bookN.trim().matches("\\d+")){
										int bookIntNumber = Integer.parseInt(bookN.trim());
										if(maxBook < bookIntNumber){
											maxBook = bookIntNumber;
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
			new Utilities().writeFile(e);
		}

		return maxBook + "";
	}

	private String getLastBookChapter(String version, String book){

		int maxChapter = 0;

		try{
			if(version != null && book != null){
				File versionFile = new File(dbPath + platformType + version);
				if(versionFile != null && versionFile.exists() && versionFile.canRead() && versionFile.isDirectory()){
					File[] bookFiles = versionFile.listFiles();
					if(bookFiles != null && bookFiles.length > 0){
						for(File bookFile : bookFiles){
							if(bookFile != null && bookFile.isDirectory()){
								String bookName = bookFile.getName();
								if(bookName != null && !bookName.trim().isEmpty() && bookName.trim().contains("_") && !bookName.trim().startsWith("0")){
									String bibleBookName = bookName.substring(bookName.indexOf("_") + 1, bookName.length());
									String bookN = bookName.substring(0, bookName.indexOf("_"));
									if(bookN != null && bookN.trim().matches("\\d+") && (bookN.equals(book) || bibleBookName.equals(book))){
										File chapterFile = new File(bookFile.getAbsolutePath());
										if(chapterFile != null && chapterFile.exists() && chapterFile.canRead() && chapterFile.isDirectory()){
											File[] chapterFiles = chapterFile.listFiles();
											if(chapterFiles != null && chapterFiles.length > 0){
												for(File chapterF : chapterFiles){
													if(chapterF != null && chapterF.isFile()){
														String chapterName = chapterF.getName();
														if(chapterName != null && !chapterName.trim().isEmpty() && chapterName.contains(".")){
															String chapterNumber = chapterName.substring(0, chapterName.indexOf("."));
															if (chapterNumber != null && chapterNumber.trim().matches("\\d+")){
																int chapterIntNumber = Integer.parseInt(chapterNumber.trim());
																if(maxChapter < chapterIntNumber){
																	maxChapter = chapterIntNumber;
																}
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
			new Utilities().writeFile(e);
		}

		return maxChapter + "";
	}

	private String getLastChapterVerse(String version, String book, String chapter){

		int maxVerse = 0;

		try{
			if(version != null && book != null && chapter != null && chapter.trim().matches("\\d+")){
				File versionFile = new File(dbPath + platformType + version);
				if(versionFile != null && versionFile.exists() && versionFile.canRead() && versionFile.isDirectory()){
					File[] bookFiles = versionFile.listFiles();
					if(bookFiles != null && bookFiles.length > 0){
						for(File bookFile : bookFiles){
							if(bookFile != null && bookFile.isDirectory()){
								String bookName = bookFile.getName();
								if(bookName != null && !bookName.trim().isEmpty() && bookName.trim().contains("_") && !bookName.trim().startsWith("0")){
									String bibleBookName = bookName.substring(bookName.indexOf("_") + 1, bookName.length());
									String bookN = bookName.substring(0, bookName.indexOf("_"));
									if(bookN != null && bookN.trim().matches("\\d+") && (bookN.equals(book) || bibleBookName.equals(book))){
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
																		if(verseText != null && !verseText.trim().isEmpty() && verseText.contains(".")){
																			String verse = verseText.substring(0, verseText.indexOf("."));
																			if (verse != null && verse.trim().matches("\\d+")){
																				int verseNumber = Integer.parseInt(verse.trim());
																				if(maxVerse < verseNumber){
																					maxVerse = verseNumber;
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
			new Utilities().writeFile(e);
		}

		return maxVerse + "";
	}

	private void setNextScreensaverBibleSelection(){

		try{
			String selectedVersion = bible.getSelectedVersion();
			String selectedBook    = bible.getSelectedBook();
			String selectedChapter = bible.getSelectedChapter();
			String selectedVerse   = bible.getSelectedVerse();
			Utilities utilities = new Utilities();
			if(utilities.isRealVersion(selectedVersion, bible)){

				if(selectedVerse != null && selectedVerse.trim().matches("\\d+")){

					if(isSetNewVerse(selectedVersion, selectedBook, selectedChapter, (Integer.parseInt(selectedVerse) + 1) + "")){
						return;
					}
					else if(isSetNewVerse(selectedVersion, selectedBook, (Integer.parseInt(selectedChapter) + 1) + "", "1")){
						return;
					}
					else if(isSetNewVerse(selectedVersion, getNextBook(selectedVersion, selectedBook), "1", "1")){
						return;
					}
					else if(isSetNewVerse(selectedVersion, "1", "1", "1")){
						return;
					}
					else {
						stopScreensaver();
					}
				}
				else if(selectedChapter != null && selectedChapter.trim().matches("\\d+") && isSetNewVerse(selectedVersion, selectedBook, selectedChapter, "1")){
					return;
				}
				else if (utilities.isRealBook(selectedBook, bible) && isSetNewVerse(selectedVersion, selectedBook, "1", "1")){
					return;
				}
				else if(isSetNewVerse(selectedVersion, "1", "1", "1")){
					return;
				}
				else {
					stopScreensaver();
				}
			}
			else{
				stopScreensaver();
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private String getNextBook(String version, String book){

		try{
			if(book != null && !book.trim().isEmpty()){
				File versionFile = new File(dbPath + platformType + version);
				if(versionFile != null && versionFile.exists() && versionFile.canRead() && versionFile.isDirectory()){

					File[] bookFiles = versionFile.listFiles();

					if(bookFiles != null && bookFiles.length > 0){
						for(File bookFile : bookFiles){
							if(bookFile != null && bookFile.isDirectory()){
								String bookName = bookFile.getName();
								if(bookName != null && !bookName.trim().isEmpty() && bookName.trim().contains("_") && !bookName.trim().startsWith("0")){
									String bookNumber = bookName.substring(0, bookName.indexOf("_"));
									String shortName = bookName.substring(bookName.indexOf("_") + 1);
									if(shortName != null && bookNumber != null && bookNumber.trim().matches("\\d+") && shortName.equals(book)){
										return (Integer.parseInt(bookNumber) + 1) + "";
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}

		return null;
	}

	private boolean isSetNewVerse(String version, String book, String chapter, String verse){

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
									String bookNumber = bookName.substring(0, bookName.indexOf("_"));
									String shortName = bookName.substring(bookName.indexOf("_") + 1);
									Utilities utilities = new Utilities();
									if(shortName != null && 
											bookNumber != null && 
											bookNumber.trim().matches("\\d+") && 
											(shortName.equals(book) || bookNumber.equals(book)) && 
											utilities.isBibleVerse(app, bible, version, bookNumber, chapter, verse)){

										screensaver.setOpenPopup(true);
										utilities.addMultipleScripturePopupText(church, settings, code, popup, reference, bible, screensaver, app, Beans.screensaver);

										Map<String, Map<String, String>> verseValue = bible.getVerseValue();

										if(verseValue != null){
											verseValue.clear();
											verseValue.put(app.getVerseValue(), null);
										}
										return true;
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}

		return false;
	}

	private void setBackScreensaverPopup(){

		try{
			if(popup != null && bible != null && app != null){

				app.setNext(false);

				final TreeMap<Integer, ScreensaverTable> screensaverMap = app.getScreensaverMap();

				if(screensaverMap != null && !screensaverMap.isEmpty()){

					setBackScreensaverIndex();

					setScreensaverPopupVariants();
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setBackScreensaverIndex(){

		try{
			final TreeMap<Integer, ScreensaverTable> screensaverMap = app.getScreensaverMap();

			if(screensaverMap != null && !screensaverMap.isEmpty()){

				int currentScreensaver = app.getCurrentScreensaver() - 1;

				if(screensaverMap.containsKey(currentScreensaver)){
					app.setCurrentScreensaver(currentScreensaver);
				}
				else{
					app.setCurrentScreensaver(screensaverMap.size());
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private String getScreensaverColor(final int colorN){

		try{
			if(screensaver != null){

				switch(colorN){

				case 1 : {
					return screensaver.getScreensaverColorPaletteSelected_1();
				}
				case 2 : {
					return screensaver.getScreensaverColorPaletteSelected_2();
				}
				case 3 : {
					return screensaver.getScreensaverColorPaletteSelected_3();
				}
				case 4 : {
					return screensaver.getScreensaverColorPaletteSelected_4();
				}
				case 5 : {
					return screensaver.getScreensaverColorPaletteSelected_5();
				}	
				}	
			}
		} catch (Exception e) {
			new Utilities().writeFile(e);
		}

		return "";
	}

	private void setScreensaverBoth(){

		try{

			Utilities utilities = new Utilities();
			if(popup != null && 
					bible != null && 
					app != null &&
					isSetScreensaverVerse()
					){

				String verseReferences = utilities.getReferenceText(reference, bible, bible.getSelectedVersion(), bible.getSelectedBook(), bible.getSelectedChapter(), bible.getSelectedVerse());

				setScreensaverImage(false);

				String result = " <html> <head> <style> "                                                                      +
						" .imgDiv { "		                                                                                  +
						" width: \"100%\"; "                                                                                  +
						" height:\"100%\"; "                                                                                  +
						" } "                                                                                                 +
						" .scriptureTextDiv { "                                                                               +
						" position: absolute; "                                                                               +
						" color: " + getScreensaverColor(5) + "     ; "                                                                                     +
						" top: 0; "                                                                                           +
						" left: 0; "                                                                                          +
						" width: \"100%\"; "                                                                                  +
						" font-size: " + popup.getPopupFontSelected() + "px; "                                             +
						utilities.addMargins(popup)                                                                        +
						" text-align: " + popup.getPopupTextAlignSelected()+ "; "                                          +
						" font-weight: bold; "                                                                                +
						" text-shadow: " + screensaver.getScreensaverParamSelected_1() + "px " + screensaver.getScreensaverParamSelected_2() + "px " + screensaver.getScreensaverParamSelected_3() + "px " + getScreensaverColor(1) + " , " + 
						screensaver.getScreensaverParamSelected_4() + "px " + screensaver.getScreensaverParamSelected_5() + "px " + screensaver.getScreensaverParamSelected_6() + "px " + getScreensaverColor(2) + " , " + 
						screensaver.getScreensaverParamSelected_7() + "px " + screensaver.getScreensaverParamSelected_8() + "px " + screensaver.getScreensaverParamSelected_9() + "px " + getScreensaverColor(3) + " , " + 
						screensaver.getScreensaverParamSelected_10() +"px " + screensaver.getScreensaverParamSelected_11() +"px " + screensaver.getScreensaverParamSelected_12() +"px " + getScreensaverColor(4) + " ; " +
						" } "                                                                                                 +
						" </style> </head> <body bgcolor=\"" + popup.getPopupBackgroundColorPaletteSelected() + "\">"      +
						utilities.getPictureString(app)                                                                   +
						" <div class=\"scriptureTextDiv\"> " + verseReferences + "<br>" + app.getVerseValue() + " </div> "+
						" </body></html>";
				screensaver.setPopupInformation(result);
				screensaver.setOpenPopup(true);
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private boolean isSetScreensaverVerse(){

		try{
			if(popup != null && bible != null && app != null){

				TreeMap<Integer, ScreensaverTable> screensaverMap = app.getScreensaverMap();

				if(screensaverMap != null && !screensaverMap.isEmpty()){

					int count = 0;
					boolean search = true;
					while(count < 1000 && search){

						++count;
						search = foundScreensaverVerse();

						if(!search){
							if(app.isNext()){
								setNextScreensaverIndex();
							}
							else{
								setBackScreensaverIndex();
							}
						}
						else{
							return true;
						}
					}
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
		return false;
	}

	private boolean foundScreensaverVerse(){

		try{
			if(popup != null && bible != null && app != null){
				TreeMap<Integer, ScreensaverTable> screensaverMap = app.getScreensaverMap();
				if(screensaverMap != null && !screensaverMap.isEmpty()){
					int currentScreensaverVerse = app.getCurrentScreensaver();
					if(screensaverMap.containsKey(currentScreensaverVerse)){
						ScreensaverTable screensaverObject = screensaverMap.get(currentScreensaverVerse);
						if(screensaverObject != null){
							String version = bible.getSelectedVersion();
							Utilities utilities = new Utilities();
							if(utilities.isRealVersion(version, bible)){

								String book = screensaverObject.getBook() + "";
								String chapter = screensaverObject.getChapter() + "";
								String verse = screensaverObject.getVerse() + "";

								if(utilities.isBibleVerse(app, bible, version, book, chapter, verse)){

									screensaver.setOpenPopup(true);

									return true;
								}
							}
							else{
								screensaver.setError(screensaver.getNoVersionSelected());
								stopScreensaver();
							}
						}
					}
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
		return false;
	}

	private void clean(){

		try{
			if(screensaver != null){

				screensaver.setError(null);
				screensaver.setEventId(null);
				screensaver.setIdentifiedWords(null);
			}

			new Utilities().emptySelection(app);
			new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, screensaver.isOpenPopup());

		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}
}