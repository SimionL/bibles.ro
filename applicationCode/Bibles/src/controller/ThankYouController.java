package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
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
import utilities.Utilities;

@Controller
@Scope("prototype")
public class ThankYouController {

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

	@RequestMapping(value = "/thankYou", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ModelAndView thankYou(ThankYou thankYouForm, ModelMap model, HttpServletRequest request, HttpServletResponse response){

		try{

			if(request != null){

				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");

				if(bible != null && settings != null && church != null && thankYou != null){
					Utilities utilities = new Utilities();

					new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, thankYouForm.isOpenPopup());
					utilities.emptySelection(app);

					String eventId = thankYouForm.getEventId();

					if(eventId != null && !eventId.trim().isEmpty()){

						eventId = eventId.trim();

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

							String pageName = new Utilities().goToSettingsPage(model, settings, popup, reference, voice, app, null);

							if(pageName != null && !pageName.trim().isEmpty()) {
								return new ModelAndView(pageName, model);
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
							utilities.setModel(model, null, null, null, null, null, null, null, voice, null, null);
							return new ModelAndView("voice", model);
						}
						case "7" : {
							utilities.setModel(model, null, null, null, null, null, null, null, null, null, feedback);
							return new ModelAndView("feedback", model);
						}
						}

						thankYouForm.setEventId(null);
						thankYou.setEventId(null);
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		new Utilities().setModel(model, null, null, null, null, null, null, null, null, thankYou, null);
		return new ModelAndView("thankYou", model);
	}
}