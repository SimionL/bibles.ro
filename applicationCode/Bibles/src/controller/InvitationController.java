package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import utilities.Utilities;

@Controller
@Transactional
@Scope("prototype")
public class InvitationController {

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

	@RequestMapping(value = "/invitation")
	public ModelAndView first(@RequestParam("church") String selectedChurch, @RequestParam("event") String event, @RequestParam("language") String language, ModelMap model, HttpServletRequest request, HttpServletResponse response){

		try{
			if(request != null && response != null && dao != null){
				new Utilities().setFirstAcces(model, request, response, dao, selectedChurch, event, language, bible, settings, popup, reference, church, screensaver, code, voice, app, thankYou, feedback);
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
		return new ModelAndView("church", model);
	}
}