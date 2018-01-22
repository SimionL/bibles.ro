package controller;

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
import utilities.AppBean;
import utilities.Beans;
import utilities.Utilities;

@Controller
@Transactional
@Scope("prototype")
public class ReferenceController {

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

	@RequestMapping(value = "/reference", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ModelAndView reference(ModelMap model, Reference referenceForm, HttpServletRequest request, HttpServletResponse response){

		try{

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			if(bible != null && settings != null && popup != null && reference != null && church != null && screensaver != null && code != null && app != null){

				before(referenceForm.isOpenPopup()); 

				String eventId = referenceForm.getEventId();

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
						utilities.setModel(model, null, null, null, null, null, null, null, voice, null, null);
						return new ModelAndView("voice", model);
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
						ModelAndView result = executtingVoiceCommand(referenceForm, model);
						if(result != null){
							return result;
						}
						break;
					}

					case "7" : {
						app.setLastSettings(1);
						utilities.setModel(model, null, null, settings, null, null, null, null, null, null, null);
						return new ModelAndView("settings", model);
					}

					case "8" : {
						app.setLastSettings(2);
						utilities.setModel(model, null, null, null, popup, null, null, null, null, null, null);
						return new ModelAndView("popup", model);
					}

					case "9" : {
						utilities.setModel(model, null, null, null, null, null, null, null, null, null, feedback);
						return new ModelAndView("feedback", model);
					}
					case "10" : {
						utilities.setModel(model, null, null, null, null, null, null, null, null, thankYou, null);
						return new ModelAndView("thankYou", model);
					}

					case "40" : {
						reference.setReferenceDisplayVersionSelected(referenceForm.isReferenceDisplayVersionSelected());
						reference.setReferenceDisplayBookSelected(referenceForm.isReferenceDisplayBookSelected());
						reference.setReferenceDisplayChapterSelected(referenceForm.isReferenceDisplayChapterSelected());
						reference.setReferenceDisplayVerseSelected(referenceForm.isReferenceDisplayVerseSelected());
						break;
					}
					case "41" : {

						reference.setReferencePopupBoldSelected(referenceForm.isReferencePopupBoldSelected());

						break;
					}
					case "42" : {
						reference.setReferenceEnterUpPopupSelected(referenceForm.getReferenceEnterUpPopupSelected());
						reference.setReferenceEnterDownPopupSelected(referenceForm.getReferenceEnterDownPopupSelected());

						break;
					}
					case "43" : {
						reference.setReferenceVersionSpaceSelected(referenceForm.getReferenceVersionSpaceSelected());
						reference.setReferenceBookSpaceSelected(referenceForm.getReferenceBookSpaceSelected());
						reference.setReferenceChapterSpaceSelected(referenceForm.getReferenceChapterSpaceSelected());
						reference.setReferenceDotsSpaceSelected(referenceForm.getReferenceDotsSpaceSelected());

						break;
					}
					case "44" : {
						setReferenceShadowPopupSchema(referenceForm);

						break;
					}
					case "45" : {
						reference.setReferenceAlignPopupSelected(referenceForm.getReferenceAlignPopupSelected());

						break;
					}
					case "46" : {
						reference.setReferenceSizePopupSelected(referenceForm.getReferenceSizePopupSelected());

						break;
					}
					case "48" : {
						setReferenceShadowPopupSchema(referenceForm);

						break;
					}

					case "58" : {
						reference.setReferenceFontStylePopupSelected(referenceForm.getReferenceFontStylePopupSelected());

						break;
					}
					case "59" : {
						reference.setReferenceLetterSpacingPopupSelected(referenceForm.getReferenceLetterSpacingPopupSelected());

						break;
					}
					case "60" : {
						reference.setReferenceFontFamilyPopupSelected(referenceForm.getReferenceFontFamilyPopupSelected());

						break;
					}
					case "61" : {
						reference.setReferenceFontDecorationPopupSelected(referenceForm.getReferenceFontDecorationPopupSelected());

						break;
					}
					}

					utilities.setPopupText(bible, church, reference, settings, popup, screensaver, app, code, Beans.reference);
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}

		new Utilities().setModel(model, null, null, null, null, reference, null, null, null, null, null);
		return new ModelAndView("reference", model);
	}

	private ModelAndView executtingVoiceCommand(Reference referenceForm, ModelMap model){

		try{

			String theSpokenWords = referenceForm.getIdentifiedWords();

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

	private void setReferenceShadowPopupSchema(Reference referenceForm){

		try{

			reference.setReferenceColorPopupPaletteSelected_1(referenceForm.getReferenceColorPopupPaletteSelected_1());	
			reference.setReferenceColorPopupPaletteSelected_2(referenceForm.getReferenceColorPopupPaletteSelected_2());

			reference.setReferenceParamSelected_1(referenceForm.getReferenceParamSelected_1());
			reference.setReferenceParamSelected_2(referenceForm.getReferenceParamSelected_2());
			reference.setReferenceParamSelected_3(referenceForm.getReferenceParamSelected_3());
			reference.setReferenceParamSelected_4(referenceForm.getReferenceParamSelected_4());
			reference.setReferenceParamSelected_5(referenceForm.getReferenceParamSelected_5());
			reference.setReferenceParamSelected_6(referenceForm.getReferenceParamSelected_6());

		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void before(final boolean open){

		try{
			reference.setIdentifiedWords(null);

			new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, open);

			new Utilities().emptySelection(app);

			if(popup != null){
				popup.setError(null);
				popup.setEventId(null);
			}

		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}
}