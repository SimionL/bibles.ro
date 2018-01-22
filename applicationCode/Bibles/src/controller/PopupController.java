package controller;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
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
import utilities.AppBean;
import utilities.Beans;
import utilities.Utilities;

@Controller
@Transactional
@Scope("prototype")
public class PopupController {

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

	@RequestMapping(value = "/popup", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ModelAndView popup(ModelMap model, Popup popupForm, HttpServletRequest request, HttpServletResponse response){

		try{

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			if(bible != null && settings != null && popup != null && reference != null && church != null && screensaver != null && code != null && app != null){

				before(popupForm.isOpenPopup()); 

				String eventId = popupForm.getEventId();

				if(eventId != null && !eventId.trim().isEmpty()){

					eventId = eventId.trim();
					Utilities utilities = new Utilities();
					switch(eventId){
					case "1" : {
						utilities.setModel(model, bible, null, null, null, null, null, null, null, null, null);

						return new ModelAndView("bible", model);
					}
					case "2" : {

						utilities.setModel(model, null, church, null, null, null, null, null, null, null, null);

						return new ModelAndView("church", model);
					}

					case "3" : {
						ModelAndView result = executtingVoiceCommand(popupForm, model);
						if(result != null){
							return result;
						}
						break;
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
						popup.setPopupFontSelected(popupForm.getPopupFontSelected());
						break;
					}

					case "7" : {
						app.setLastSettings(1);
						utilities.setModel(model, null, null, settings, null, null, null, null, null, null, null);
						return new ModelAndView("settings", model);
					}
					case "8" : {
						app.setLastSettings(3);
						utilities.setModel(model, null, null, null, null, reference, null, null, null, null, null);
						return new ModelAndView("reference", model);
					}
					case "9" : {
						popup.setPopupBoldSelected(popupForm.isPopupBoldSelected());

						break;
					}
					case "10" : {
						utilities.setModel(model, null, null, null, null, null, null, null, voice, null, null);
						return new ModelAndView("voice", model);
					}
					case "11" : {
						new Utilities().setModel(model, null, null, null, null, null, null, null, null, null, feedback);
						return new ModelAndView("feedback", model);
					}
					case "12" : {
						new Utilities().setModel(model, null, null, null, null, null, null, null, null, thankYou, null);
						return new ModelAndView("thankYou", model);
					}
					case "13" : {
						popup.setPopupMarginTopSelected(popupForm.getPopupMarginTopSelected());
						break;
					}
					case "14" : {
						popup.setPopupMarginBottomSelected(popupForm.getPopupMarginBottomSelected());

						break;
					}
					case "15" : {
						popup.setPopupMarginRightSelected(popupForm.getPopupMarginRightSelected());
						break;
					}
					case "16" : {
						popup.setPopupMarginLeftSelected(popupForm.getPopupMarginLeftSelected());
						break;
					}

					case "17" : {
						popup.setPopupTextAlignSelected(popupForm.getPopupTextAlignSelected());
						break;
					}

					case "18" : {
						popup.setPopupBackgroundColorPaletteSelected(popupForm.getPopupBackgroundColorPaletteSelected());
						break;
					}

					case "19" : {
						popup.setPopupTextColorPaletteSelected(popupForm.getPopupTextColorPaletteSelected());
						break;
					}

					case "22" : {

						new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, true);

						popup.setPopupUserMessageSelected(popupForm.getPopupUserMessageSelected());
						popup.setDisplayUserMessage(true);

						break;
					}

					case "23" : {
						popup.setDefaultPopupImageSelected(false);
						new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, true);
						setPopupPictures(popupForm.getPopupPictureSelected());

						break;
					}

					case "25" : {
						popup.setDisplayScriptureText(popupForm.isDisplayScriptureText());

						break;
					}
					case "26" : {
						popup.setDisplayPicture(popupForm.isDisplayPicture());
						if(popup.isDisplayPicture()){
							popup.setDefaultPopupImageSelected(false);
						}
						break;
					}
					case "27" : {
						popup.setDisplayUserMessage(popupForm.isDisplayUserMessage());

						break;
					}

					case "28" : {
						popup.setScriptureTextOpacitySelected(popupForm.getScriptureTextOpacitySelected());
						break;
					}

					case "29" : {
						popup.setImageOpacitySelected(popupForm.getImageOpacitySelected());
						break;
					}
					case "50" : {
						popup.setDefaultPopupImageSelected(popupForm.isDefaultPopupImageSelected());
						if(popup.isDefaultPopupImageSelected()){
							popup.setDisplayPicture(false);
						}
						new Utilities().setDefaultPopupImageColor(popup);

						break;
					}
					case "52" : {
						popup.setFontFamilyPopupSelected(popupForm.getFontFamilyPopupSelected());

						break;
					}
					case "53" : {
						popup.setFontStylePopupSelected(popupForm.getFontStylePopupSelected());

						break;
					}
					case "55" : {
						popup.setLetterSpacingPopupSelected(popupForm.getLetterSpacingPopupSelected());

						break;
					}
					case "56" : {
						popup.setLineHeightPopupSelected(popupForm.getLineHeightPopupSelected());

						break;
					}
					case "57" : {
						popup.setWordSpacingPopupSelected(popupForm.getWordSpacingPopupSelected());

						break;
					}
					}

					utilities.setPopupText(bible, church, reference, settings, popup, screensaver, app, code, Beans.popup);
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}

		new Utilities().setModel(model, null, null, null, popup, null, null, null, null, null, null);
		return new ModelAndView("popup", model);
	}

	private ModelAndView executtingVoiceCommand(Popup popupForm, ModelMap model){

		try{

			String theSpokenWords = popupForm.getIdentifiedWords();

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

	private void setPopupPictures(List<MultipartFile> popupPictureSelected){

		try{
			if(bible != null && popup != null && popupPictureSelected != null && !popupPictureSelected.isEmpty()){

				Map<String, String> selectedImagesMap = app.getSelectedImagesMap();
				if(selectedImagesMap != null){
					boolean newPicture = true;
					for(MultipartFile popupImage : popupPictureSelected){

						if(popupImage != null && popupImage.getSize() > 0){

							String fileSelected = popupImage.getOriginalFilename();

							if(fileSelected != null && !fileSelected.trim().isEmpty() && fileSelected.contains(".")){

								String format = fileSelected.substring(fileSelected.lastIndexOf(".") + 1);

								if(format != null && !format.trim().isEmpty()){

									byte[] imageBytes = popupImage.getBytes();

									if(imageBytes != null && imageBytes.length > 0){

										String imageText = Base64.getEncoder().encodeToString(imageBytes);

										if(imageText != null && !imageText.trim().isEmpty()){

											if(newPicture){
												newPicture = false;
												selectedImagesMap.clear();
											}

											selectedImagesMap.put(fileSelected.trim(), imageText);
											popup.setDisplayPicture(true);
										}
									}
								}
							}
						}
					}
					popupPictureSelected.clear();
					popupPictureSelected = null;
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void before(final boolean open){

		try{

			new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, open);
			new Utilities().emptySelection(app);

			if(popup != null){
				popup.setError(null);
				popup.setEventId(null);
				popup.setIdentifiedWords(null);
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}
}