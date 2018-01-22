package controller;

import java.util.ArrayList;
import java.util.List;

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
import dao.DAO;
import dbBeans.MessageTable;
import utilities.AppBean;
import utilities.Utilities;

@Controller
@Scope("prototype")
public class FeedbackController {

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

	@RequestMapping(value = "/feedback", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ModelAndView feedback(Feedback feedbackForm, ModelMap model, HttpServletRequest request, HttpServletResponse response){

		try{

			if(request != null){

				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");

				if(bible != null && settings != null && church != null && feedback != null){
					Utilities utilities = new Utilities();

					new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, feedbackForm.isOpenPopup());
					utilities.emptySelection(app);

					String eventId = feedbackForm.getEventId();

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
							utilities.setModel(model, null, null, null, null, null, null, null, null, thankYou, null);
							return new ModelAndView("thankYou", model);
						}
						case "8" : {
							addMessage(feedbackForm);
							break;
						}
						}

						feedbackForm.setEventId(null);
						feedback.setEventId(null);
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		new Utilities().setModel(model, null, null, null, null, null, null, null, null, null, feedback);
		return new ModelAndView("feedback", model);
	}

	private boolean isBadPassword(String myName, String userPassword){

		try{
			final List<MessageTable> userMessagesList = dao.getAllMessages(myName);

			if(userMessagesList == null || userMessagesList.isEmpty()){
				return false;
			}
			else if(!userMessagesList.isEmpty()){
				for(MessageTable mT : userMessagesList){

					String oldPassword = mT.getUserPassword();

					if(oldPassword == null || !oldPassword.equals(userPassword)){
						return true;
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
		return false;
	}

	private void addMessage(Feedback feedbackForm){

		String myName = feedbackForm.getMyNameValue();
		String userPassword = feedbackForm.getUserPasswordValue();
		String message = feedbackForm.getMessageValue();

		int messageType = feedbackForm.getMessageTypeValue();

		feedback.setMessageTypeValue(messageType);
		feedback.setMyNameValue(myName);
		feedback.setMessageValue(message);
		feedback.setUserPasswordValue(userPassword);

		if(isBadPassword(myName, userPassword)){
			feedback.setError(feedback.getWrongPasswordMessage());
		}
		else if(messageType > 0           &&
				messageType < 6           &&
				myName != null            &&
				!myName.trim().isEmpty()  &&
				message != null           &&
				!message.trim().isEmpty() &&
				!userPassword.trim().isEmpty()
				){

			MessageTable newMessage = new MessageTable();
			newMessage.setMessageDate(System.currentTimeMillis());
			newMessage.setMessageType(messageType);
			newMessage.setMessageValue(message);
			newMessage.setUserName(myName.trim());
			newMessage.setUserPassword(userPassword);

			if(dao != null){
				dao.isSaveOrUpdateMessage(newMessage);
			}

			List<MessageTable> messageList= new ArrayList<>();

			messageList.add(newMessage);

			new Utilities().addMessages(messageList, feedback);
		}
	}
}