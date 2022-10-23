package controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StreamUtils;
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
import dao.DAO;
import dbBeans.ChurchTable;
import dbBeans.Event;
import dbBeans.Participant;
import utilities.AppBean;
import utilities.Constant;
import utilities.Utilities;

@Controller
@Transactional
@Scope("prototype")
public class ChurchController {

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

	private final String eventsPath = Constant.eventsPath;
	private final String platformType = Constant.platformType;

	@RequestMapping(value = "/church", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ModelAndView church(ModelMap model, Church churchForm, HttpServletRequest request, HttpServletResponse response){

		try{

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			if(church != null && churchForm != null){
				church.setDisplayChurch(churchForm.getDisplayChurch());
				church.setError("");
				church.setSuccessful("");
				church.setIdentifiedWords(null);
				churchForm.setError("");
				churchForm.setSuccessful("");
			}

			new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, churchForm.isOpenPopup());

			String eventId = churchForm.getEventId();

			if(eventId != null && !eventId.trim().isEmpty()){

				eventId = eventId.trim();
				Utilities utilities = new Utilities();
				switch(eventId){
				case "1" : {
					utilities.setModel(model, bible, null, null, null, null, null, null, null, null, null);
					return new ModelAndView("bible", model);
				}
				case "2" : {

					String pageName = new Utilities().goToSettingsPage(model, settings, popup, reference, voice, app, null);

					if(pageName != null && !pageName.trim().isEmpty()) {
						return new ModelAndView(pageName, model);
					}

					break;
				}
				case "3" : {
					handleChurch(churchForm);
					utilities.setModel(model, null, church, null, null, null, null, null, null, null, null);
					return new ModelAndView("church", model);
				}
				case "4" : {
					handleEvent(churchForm);
					utilities.setModel(model, null, church, null, null, null, null, null, null, null, null);
					return new ModelAndView("church", model);
				}
				case "5" : {
					login(churchForm);
					break;
				}
				case "6" : {
					logout();
					break;
				}
				case "7" : {
					createAccount(churchForm);
					break;
				}
				case "8" : {
					deleteAccount();
					break;
				}
				case "9" : {
					updateAccount(churchForm);
					break;
				}
				case "10" : {
					createEvent(churchForm);
					break;
				}
				case "11" : {
					updateEvent(churchForm);
					break;
				}
				case "12" : {
					deleteEvent(churchForm);
					break;
				}
				case "13" : {
					addParticipant(churchForm);
					break;
				}
				case "14" : {
					setParticipantOrder(churchForm);
					break;
				}
				case "15" : {
					deleteParticipant(churchForm);
					break;
				}
				case "16" : {
					utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
					return new ModelAndView("screensaver", model);
				}
				case "17" : {
					utilities.setModel(model, null, null, null, null, null, null, code, null, null, null);
					return new ModelAndView("code", model);
				}
				case "18" : {
					downloadAttachments(response);
					break;
				}
				case "19" : {
					prepareEmail(churchForm);
					break;
				}
				case "20" : {
					utilities.setModel(model, null, null, null, null, null, null, null, voice, null, null);
					return new ModelAndView("voice", model);
				}
				case "21" : {
					ModelAndView result = executtingVoiceCommand(churchForm, model);
					if(result != null){
						return result;
					}
					break;
				}
				case "22" : {
					utilities.setModel(model, null, null, null, null, null, null, null, null, null, feedback);
					return new ModelAndView("feedback", model);
				}
				case "23" : {
					utilities.setModel(model, null, null, null, null, null, null, null, null, thankYou, null);
					return new ModelAndView("thankYou", model);
				}
				}
				churchForm.setEventId(null);
				church.setEventId(null);
				new Utilities().setAllChurchAndEvents(church, null, null, dao);
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}

		new Utilities().setModel(model, null, church, null, null, null, null, null, null, null, null);
		return new ModelAndView("church", model);
	}

	private ModelAndView executtingVoiceCommand(Church churchForm, ModelMap model){

		try{

			String theSpokenWords = churchForm.getIdentifiedWords();

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

	private void setParticipantOrder(Church churchForm){

		try{
			String selectedEvent = churchForm.getSelectedEvent();

			if(selectedEvent != null && !selectedEvent.trim().isEmpty() && 
					!selectedEvent.trim().equalsIgnoreCase(church.getEventsLabel()) &&
					!selectedEvent.trim().equalsIgnoreCase(church.getNoEventLabel())
					){

				Event event = dao.getEvent(selectedEvent);

				if(event != null){

					List<Integer> participantsOrder = new ArrayList<>();
					List<Participant> participantsList = churchForm.getParticipantsList();
					int participantOrder = 0;

					if(participantsList != null && !participantsList.isEmpty()){

						for(Participant participant : participantsList){

							participantOrder = participant.getParticipantOrder();

							if(participantsOrder.contains(participantOrder)){
								break;
							}
							else{
								participantsOrder.add(participantOrder);
							}
							participantOrder = 0;
						}
					}
					if(participantOrder > 0){
						List<Participant> dbParticipantsList = event.getParticipantsList();
						if(dbParticipantsList != null && !dbParticipantsList.isEmpty()){
							Participant participant_1 = null;
							Participant participant_2 = null;
							long participantId = churchForm.getParticipantId();
							for(Participant participant : dbParticipantsList){
								if(participant != null){
									if(participant_1 == null && participant.getParticipantId() == participantId){
										participant_1 = participant;
									}

									if(participant_2 == null && participant.getParticipantOrder() == participantOrder && participant.getParticipantId() != participantId){
										participant_2 = participant;
									}
								}
								if(participant_1 != null && participant_2 != null){
									break;
								}
							}
							if(participant_1 != null && participant_2 != null){
								int order_1 = participant_1.getParticipantOrder();
								int order_2 = participant_2.getParticipantOrder();
								participant_1.setParticipantOrder(order_2);
								participant_2.setParticipantOrder(order_1);
								if(dao.isSaveOrUpdateEvent(event)){
									setParticipantsEventList(event);
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

	private void deleteParticipant(Church churchForm){

		try{
			if(church != null && churchForm != null){

				Participant participant = dao.getParticipantById(churchForm.getParticipantId());

				if(participant != null){

					int deletedParticipantOrder = participant.getParticipantOrder();
					String participantResourcesPath = participant.getResourcesPath();
					participant.setEvent(null);

					String selectedEvent = churchForm.getSelectedEvent();

					if(selectedEvent != null && !selectedEvent.trim().isEmpty()){

						Event event = dao.getEvent(selectedEvent);

						if(event != null){

							List<Participant> participantsList = event.getParticipantsList();

							if(participantsList != null){

								if(participantsList.contains(participant)) {
									participantsList.remove(participant);
								}

								Set<Integer> participantsOrderList = church.getParticipantsOrderList();
								participantsOrderList.clear();
								Map<Integer, Participant> participantOrderMap = new TreeMap<>();
								if(!participantsList.isEmpty() && participantOrderMap != null){
									int partO = 1;
									for(Participant p : participantsList){
										if(p != null){
											participantsOrderList.add(partO ++);
											if(p.getParticipantOrder() > deletedParticipantOrder){
												int newEventOrder = p.getParticipantOrder() - 1;
												p.setParticipantOrder(newEventOrder);
												participantOrderMap.put(newEventOrder, p);
											}
											else{
												participantOrderMap.put(p.getParticipantOrder(), p);
											}
										}
									}
								}

								List<Participant> beanParticipantsList = church.getParticipantsList();

								if(beanParticipantsList != null){
									beanParticipantsList.clear();
									if(participantOrderMap != null && !participantOrderMap.isEmpty()){
										for(Integer participantOrder : participantOrderMap.keySet()){
											if(participantOrder >0){
												beanParticipantsList.add(participantOrderMap.get(participantOrder));	
											}
										}
									}
								}
								new Utilities().setTotalTime(church, event);
								church.setSuccessful(church.getOkParticipantDeleted());
							}
							else{
								church.setError(church.getErrorParticipantDeleted());
							}
							dao.isSaveOrUpdateEvent(event);
						}
						else{
							church.setError(church.getErrorParticipantDeleted());
						}
					}
					else{
						church.setError(church.getErrorParticipantDeleted());
					}

					if(dao.deleteParticipant(participant)){
						removeFolder(participantResourcesPath);
					}
					else{
						church.setError(church.getErrorParticipantDeleted());
					}
				}
				else{
					church.setError(church.getErrorParticipantDeleted());
				}
			}
			else{
				church.setError(church.getErrorParticipantDeleted());
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void addParticipant(Church churchForm){

		try{

			String selectedEvent = churchForm.getSelectedEvent();

			if(selectedEvent != null && !selectedEvent.trim().isEmpty() && !selectedEvent.trim().equalsIgnoreCase(church.getEventsLabel())){

				Event event = dao.getEvent(selectedEvent);

				if(event != null){

					String eventPassword = event.getEventPassword();

					if(eventPassword != null && !eventPassword.isEmpty()){
						String participantPassword = churchForm.getEventPasswordValue();
						if(participantPassword != null && participantPassword.equals(eventPassword)){
							insertParticipant(churchForm, event);
						}
						else{
							church.setError(church.getErrorParticipantAdded() + "<br><br>" + church.getWrongPassword());
						}
					}
					else{
						insertParticipant(churchForm, event);
					}
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void insertParticipant(Church churchForm, Event event){

		try{
			String participantName = churchForm.getParticipantNameValue();
			String participantForename = churchForm.getParticipantForenameValue();
			String participantDuration = churchForm.getParticipantDurationValue();
			String participantDescription = churchForm.getParticipantDescriptionValue();
			String participantPhone = churchForm.getParticipantPhoneValue();
			String participantEmail = churchForm.getParticipantEmailValue();

			String participantResourcesFolder = event.getFolderPath() + platformType + participantName + "_" +  participantForename;
			List<MultipartFile> participantResources = churchForm.getParticipantResources();

			Participant participant = new Participant();

			if(participantResources != null){
				createAndWriteFolder(participantResourcesFolder, participantResources, participant);	
			}

			participant.setName(participantName);
			participant.setForename(participantForename);
			participant.setDuration(Integer.parseInt(participantDuration));
			participant.setResourcesPath(participantResourcesFolder);
			participant.setDescription(participantDescription);
			participant.setPhone(participantPhone);
			participant.setEmail(participantEmail);
			participant.setEvent(event);

			List<Participant> participantsList = event.getParticipantsList();
			if(participantsList != null){
				participant.setParticipantOrder(participantsList.size() + 1);
				participantsList.add(participant);
			}

			if(dao.isSaveOrUpdateEvent(event)){
				church.setSuccessful(church.getOkParticipantAdded());
				church.setParticipantNameValue(participantName);
				church.setParticipantForenameValue(participantForename);
				church.setParticipantPhoneValue(participantPhone);
				setParticipantsEventList(event);
				new Utilities().setTotalTime(church, event);

				Set<Integer> participantsOrderList = church.getParticipantsOrderList();

				if(participantsOrderList != null){

					participantsOrderList.add(participantsOrderList.size() + 1);
				}
			}
			else{
				church.setError(church.getErrorParticipantAdded());
			}

		}catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void updateEvent(Church churchForm){

		try{
			if(church != null && churchForm != null){
				church.setDisplayChurch("event");
				String selectedEvent = churchForm.getSelectedEvent();

				if(selectedEvent != null && !selectedEvent.trim().isEmpty() && !selectedEvent.trim().equalsIgnoreCase(church.getEventsLabel())){

					Event event = dao.getEvent(selectedEvent);

					if(event != null){

						String eventDescription = churchForm.getEventDescription();
						String eventDate = churchForm.getEventDate();
						String eventHour = churchForm.getEventHour();
						String eventDuration = churchForm.getSelectedDuration();

						event.setEventDate(eventDate);
						event.setEventHour(eventHour);
						event.setEventDuration(eventDuration);
						event.setEventPassword(churchForm.getEventPasswordValue());
						if(eventDescription != null){
							if(eventDescription.contains("\r\n")){
								eventDescription = eventDescription.replaceAll("\r\n", "<br>");
							}
							event.setEventDescription(eventDescription);
						}
						else{
							event.setEventDescription(null);
						}


						church.setEventNameValue(selectedEvent);
						church.setEventDate(eventDate);
						church.setEventHour(eventHour);
						church.setSelectedDuration(eventDuration);
						church.setEventDescription(churchForm.getEventDescription());
						church.setSelectedEvent(selectedEvent);
						if(dao.isSaveOrUpdateEvent(event)){
							church.setSuccessful(church.getSuccessfullEventUpdate());
							new Utilities().setTotalTime(church, event);
						}
						else{
							church.setError(church.getFailurEventUpdate());
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void deleteEvent(Church churchForm){

		try{
			if(church != null && churchForm != null){
				church.setDisplayChurch("event");
				String selectedEvent = churchForm.getSelectedEvent();
				List<String> eventsListLabels = church.getEvents();

				if(selectedEvent != null && 
						!selectedEvent.trim().isEmpty() && 
						!selectedEvent.trim().equalsIgnoreCase(church.getEventsLabel())&&
						eventsListLabels.contains(selectedEvent)
						){

					if(dao.isEventDeleted(selectedEvent)){
						church.setSuccessful(church.getSuccessfullEventDelete());
						eventsListLabels.remove(selectedEvent);
						cleanEvent();
						church.setTotalParticipantTime(0);
					}
					else{
						church.setError(church.getFailurEventDelete());
					}
				}
				else{
					church.setError(church.getFailurEventDelete());
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void createEvent(Church churchForm){

		try{

			ChurchTable existentUser = church.getUser();

			if(existentUser != null){

				ChurchTable user = dao.getUser(existentUser.getUserId());

				if(user != null && church != null && churchForm != null){

					String newEventName = churchForm.getEventNameValue();
					String eventDate = churchForm.getEventDate();
					String eventHour = churchForm.getEventHour();
					String eventDuration = churchForm.getSelectedDuration();
					String eventDescription = churchForm.getEventDescription();

					if(newEventName != null && !newEventName.trim().isEmpty() &&
							eventDate != null && !eventDate.trim().isEmpty() &&	
							eventHour != null && !eventHour.trim().isEmpty() &&
							eventDuration != null && !eventDuration.trim().isEmpty() && eventDuration.trim().matches("\\d+")
							){

						newEventName = newEventName.trim();

						List<Event> churchEvents = user.getEvents();

						List<String> eventsListLabels = church.getEvents();

						if(eventsListLabels != null && churchEvents != null){
							Set<String> allEvents = new TreeSet<>();
							if(!churchEvents.isEmpty()){
								for(Event ev : churchEvents){
									if(ev != null){
										String eventName = ev.getEventName();
										if(eventName != null && !eventName.trim().isEmpty()){
											allEvents.add(eventName.trim());
										}
									}
								}
							}

							eventsListLabels.clear();
							eventsListLabels.add(church.getEventsLabel());
							String eventFolderPath = eventsPath + platformType + newEventName;


							if(allEvents.contains(newEventName) ||  new File(eventFolderPath).exists()){
								church.setError(church.getEventCreationError());
							}
							else {
								Event event = new Event();
								event.setUser(user);
								event.setEventName(newEventName);
								event.setEventDate(eventDate.trim());
								event.setEventHour(eventHour.trim());
								event.setEventDescription(eventDescription);
								event.setEventDuration(eventDuration);
								event.setEventPassword(churchForm.getEventPasswordValue());
								event.setFolderPath(eventFolderPath);

								churchEvents.add(event);
								if(dao.isSaveOrUpdateUser(user)){
									church.setSuccessful(church.getSuccessfullEventCreated());
									allEvents.add(newEventName);
									church.setSelectedEvent(newEventName);
									church.setEventNameValue(newEventName);
									church.setEventDate(eventDate.trim());
									church.setEventHour(eventHour.trim());
									church.setSelectedDuration(eventDuration);
									church.setEventDescription(eventDescription);

									createFolder(eventFolderPath);
									church.setTotalParticipantTime(0);
								}
							}
							eventsListLabels.addAll(allEvents);
						}
					}
				}
			}
			church.setDisplayChurch("event");
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void createFolder(String path){

		try{
			if(path != null && !path.trim().isEmpty()){
				path = path.trim();
				File folder = new File(path);
				if (folder != null){
					if (!folder.exists()) {
						folder.mkdirs();
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void removeFolder(String folderPath){

		try{

			if(folderPath != null && !folderPath.trim().isEmpty()){

				Path directory = Paths.get(folderPath.trim());

				if (Files.exists(directory)) {
					Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {

						@Override
						public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
							Files.deleteIfExists(path);
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult postVisitDirectory(Path directory, IOException ioException) throws IOException {
							Files.delete(directory);
							return FileVisitResult.CONTINUE;
						}
					});
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void createAndWriteFolder(String path, List<MultipartFile> resources, Participant participant){

		try{
			createFolder(path);

			if(resources != null){

				String participantResourcesName = "";

				for(MultipartFile participantResource : resources){

					if(participantResource != null && participantResource.getSize() > 0){

						String fileSelected = participantResource.getOriginalFilename();

						if(fileSelected != null && !fileSelected.trim().isEmpty()){

							participantResourcesName += fileSelected + "\r\n";

							File newFile = new File(path + platformType + participantResource.getOriginalFilename());

							if(newFile != null && !newFile.exists()){

								newFile.createNewFile();

								try(OutputStream outputStream = new FileOutputStream(newFile)){
									outputStream.write(participantResource.getBytes());
								} catch (Exception e) {
									new Utilities().writeFile(e);
								}
							}
						}
					}
				}
				participant.setResources(participantResourcesName);
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void login(Church churchForm){

		try{
			cleanEvent();
			cleanParticipantsEventList();
			if(church != null && churchForm != null && dao.existChurchUser(church, churchForm)){
				church.setSuccessful(church.getSuccessfulLogin());
				church.setUsername(churchForm.getUsername().trim());

				ChurchTable churchTable = church.getUser();

				if(churchTable != null){

					church.setChurchEmail(churchTable.getChurchEmail());
					church.setSelectedChurch(churchTable.getUsername());

					List<Event> churchEvents = churchTable.getEvents();
					List<String> eventsListLabels = church.getEvents();

					if(eventsListLabels != null && churchEvents != null){
						Set<String> allEvents = new TreeSet<>();
						if(!churchEvents.isEmpty()){
							for(Event ev : churchEvents){
								if(ev != null){
									String eventName = ev.getEventName();
									if(eventName != null && !eventName.trim().isEmpty()){
										allEvents.add(eventName.trim());
									}
								}
							}
						}

						eventsListLabels.clear();
						if(!allEvents.isEmpty()){
							eventsListLabels.add(church.getEventsLabel());
							eventsListLabels.addAll(allEvents);
						}
					}
				}
			}
			else{
				church.setError(church.getFailureLogin());
			}

			church.setDisplayChurch("account");
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void logout(){

		try{
			if(church != null){
				church.setUser(null);
				church.setUsername(null);
				church.setSuccessful(church.getSuccessfullLogout());
				cleanEvent();
			}

			church.setDisplayChurch("account");
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void createAccount(Church churchForm){

		try{

			if(church != null && churchForm != null){

				cleanEvent();

				String username= churchForm.getUsername();
				String password= churchForm.getPassword();
				String churchEmail= churchForm.getChurchEmail();
				String churchEmailPassword= churchForm.getChurchEmailPassword();

				boolean validAccount = true;
				String error = "";

				if(username != null && !username.trim().isEmpty()){
					church.setUsername(username.trim());
					if(existRecord("select distinct username from " + ChurchTable.class.getName(), username)){
						error += "<br>" + church.getUsernameError();
						validAccount = false;
					}
				}
				else{
					validAccount = false;
				}

				if(password != null && !password.trim().isEmpty()){
					church.setPassword(password);
				}
				else{
					validAccount = false;
				}

				if(churchEmail != null && !churchEmail.trim().isEmpty()){
					church.setChurchEmail(churchEmail.trim());
					if(existRecord("select distinct churchEmail from " + ChurchTable.class.getName(), churchEmail.trim())){
						error += "<br>" + church.getExistEmailError();
						validAccount = false;
					}
					if(!new Utilities().isValidEmailAddress(churchEmail.trim())){
						error += "<br>" + church.getInvalidEmailFormat();
						validAccount = false;
					}
				}
				else{
					validAccount = false;
				}

				if(churchEmailPassword != null && !churchEmailPassword.trim().isEmpty()){
					church.setChurchEmailPassword(churchEmailPassword.trim());
				}
				else{
					validAccount = false;
				}

				if(validAccount && error.isEmpty()){

					church.setUsername(username.trim());
					church.setChurchEmail(churchEmail.trim());

					final ChurchTable user = new ChurchTable();

					user.setUsername(username.trim());
					user.setPassword(password);
					user.setChurchEmail(churchEmail.trim());
					user.setChurchEmailPassword(churchEmailPassword);
					List<Event> event = new ArrayList<>();
					user.setEvents(event);

					if(dao.isSaveOrUpdateUser(user)){
						church.setUser(user);
						church.setSuccessful(church.getSuccessfulInsertion());
					}
				}
				else if(!error.isEmpty()){
					church.setError(error);
				}

				church.setDisplayChurch("account");
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private boolean existRecord(String query, String record){

		try{
			if(query != null && !query.trim().isEmpty() && record != null && !record.trim().isEmpty()){
				Set<String> allEmails = dao.getColumn(query.trim());
				if(allEmails != null && allEmails.contains(record.trim())) return true;	
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
		return false;
	}

	private void deleteAccount(){

		if(church != null){

			try{

				ChurchTable user = church.getUser();

				if(user != null){
					deleteUserFromBean(user);

					if(dao.deleteUser(user)){
						church.setSuccessful(church.getSuccessfullDelete());
					}
					else{
						church.setError(church.getErrorDelete());
					}
				}
				else{
					church.setError(church.getErrorDelete());
				}
			}
			catch(Exception e){
				new Utilities().writeFile(e);
			}
			church.setDisplayChurch("account");
		}
	}

	private void deleteUserFromBean(ChurchTable user){

		try{
			List<String> currentChurches = church.getChurches();
			String username = user.getUsername();

			if(currentChurches != null && !currentChurches.isEmpty() && username != null && currentChurches.contains(username.trim())){
				currentChurches.remove(username.trim());
			}

			church.setUser(null);
			church.setUsername(null);
			church.setPassword(null);
			church.setChurchEmail(null);
			church.setChurchEmailPassword(null);
			church.setUser(null);
			church.setParticipantResources(null);
			cleanEvent();

			List<Event> events = user.getEvents();

			if(events != null && !events.isEmpty()){
				for(Event event : events){
					if(event != null){
						String eventFolderPath = event.getFolderPath();
						if(eventFolderPath != null && !eventFolderPath.trim().isEmpty()){
							removeFolder(eventFolderPath.trim());	
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void updateAccount(Church churchForm){
		if(church != null && churchForm != null){
			try{
				ChurchTable user = church.getUser();
				if(user != null){

					String username = churchForm.getUsername();
					String password = churchForm.getPassword();
					String churchEmail = churchForm.getChurchEmail();
					String churchEmailPassword = churchForm.getChurchEmailPassword();

					if(username != null && !username.trim().isEmpty()){
						church.setUsername(username.trim());
						user.setUsername(username.trim());
					}

					if(password != null && !password.trim().isEmpty()){
						church.setPassword(password.trim());
						user.setPassword(password.trim());
					}

					if(churchEmail != null && !churchEmail.trim().isEmpty()){
						church.setChurchEmail(churchEmail.trim());
						user.setChurchEmail(churchEmail.trim());
					}

					if(churchEmailPassword != null && !churchEmailPassword.trim().isEmpty()){
						church.setChurchEmailPassword(churchEmailPassword.trim());
						user.setChurchEmailPassword(churchEmailPassword.trim());
					}

					if(dao.isSaveOrUpdateUser(user)){
						church.setSuccessful(church.getSuccessfullUpdated());
					}
					else{
						church.setError(church.getErrorUpdated());
					}
				}
				else{
					church.setError(church.getErrorUpdated());
				}
			}
			catch(Exception e){
				new Utilities().writeFile(e);
			}
			church.setDisplayChurch("account");
		}
	}

	private void cleanParticipantsEventList(){

		try{
			if(church != null){
				List<Participant> beanParticipantsList = church.getParticipantsList();
				if(beanParticipantsList != null){
					beanParticipantsList.clear();
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setParticipantsEventList(Event event){

		try{
			if(church != null && event != null){
				List<Participant> beanParticipantsList = church.getParticipantsList();
				if(beanParticipantsList != null){
					beanParticipantsList.clear();
					List<Participant> eventParticipantsList = event.getParticipantsList();
					if(eventParticipantsList != null){

						Set<Integer> participantsOrderList = church.getParticipantsOrderList();

						participantsOrderList.clear();

						if(!eventParticipantsList.isEmpty()){
							Map<Integer, Participant> participantMap = new TreeMap<>();
							for(Participant participant : eventParticipantsList){
								if(participant != null){
									participantMap.put(participant.getParticipantOrder(), participant);
									participantsOrderList.add(participant.getParticipantOrder());
								}
							}

							if(participantMap != null && !participantMap.isEmpty()){
								for(Integer participantOrder : participantMap.keySet()){
									if(participantOrder > 0){	
										beanParticipantsList.add(participantMap.get(participantOrder));
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
	}

	private void handleEvent(Church churchForm){

		try{
			if(church != null && churchForm != null){
				church.setDisplayChurch("event");
				String selectedEvent = churchForm.getSelectedEvent();

				if(selectedEvent != null && !selectedEvent.trim().isEmpty() && 
						!selectedEvent.trim().equalsIgnoreCase(church.getEventsLabel()) &&
						!selectedEvent.trim().equalsIgnoreCase(church.getNoEventLabel())
						){
					Event event = dao.getEvent(selectedEvent);
					if(event != null){

						new Utilities().setTotalTime(church, event);
						setParticipantsEventList(event);

						String eventDescription = event.getEventDescription();
						if(eventDescription != null){
							if(eventDescription.contains("<br>")){
								eventDescription = eventDescription.replaceAll("<br>", "\r\n");
							}
							church.setEventDescription(eventDescription);
						}
						else{
							church.setEventDescription(null);
						}

						church.setEventDate(event.getEventDate());
						church.setEventHour(event.getEventHour());
						church.setSelectedDuration(event.getEventDuration());
						church.setEventNameValue(selectedEvent);

						if(church.getUser() == null){
							church.setAddUserButton("true");
						}

						church.setSelectedEvent(selectedEvent);
					}
				}
				else{
					if(church.getUser() == null){
						church.setAddUserButton(null);
					}

					cleanEvent();
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void handleChurch(Church churchForm){

		try{
			if(church != null && churchForm != null){
				church.setDisplayChurch("event");
				String selectedChurch = churchForm.getSelectedChurch();
				if(selectedChurch != null && !selectedChurch.trim().isEmpty()){
					selectedChurch = selectedChurch.trim();
					church.setSelectedChurch(selectedChurch);
					if(!selectedChurch.equalsIgnoreCase(church.getChurchesLabel())){

						ChurchTable user = dao.getUserByUsername(selectedChurch);

						if(user != null){

							cleanEvent();	

							List<Event> churchEvents = user.getEvents();

							List<String> eventsListLabels = church.getEvents();

							if(eventsListLabels != null && churchEvents != null && !churchEvents.isEmpty()){
								Set<String> allEvents = new TreeSet<>();
								if(!churchEvents.isEmpty()){
									for(Event ev : churchEvents){
										if(ev != null){
											String eventName = ev.getEventName();
											if(eventName != null && !eventName.trim().isEmpty()){
												allEvents.add(eventName.trim());
											}
										}
									}
								}

								eventsListLabels.clear();
								eventsListLabels.add(church.getEventsLabel());
								eventsListLabels.addAll(allEvents);
							}
							else{
								setEmptyEventsList();
							}
						}
					}
					else{
						setEmptyEventsList();
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}
	private void setEmptyEventsList(){

		try{
			if(church != null){
				cleanEvent();
				List<String> eventsListLabels = church.getEvents();
				if(eventsListLabels != null){
					eventsListLabels.clear();
					eventsListLabels.add(church.getNoEventLabel());
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void cleanEvent(){

		try{
			if(church != null){
				List<String> events = church.getEvents();
				if(events != null){
					events.clear();
				}
				church.setEventDescription(null);
				church.setSelectedEvent(church.getNoEventLabel());
				church.setEventNameValue(null);
				church.setEventDate(null);
				church.setEventHour(null);
				church.setSelectedDuration(null);
				church.setAddUserButton(null);
				cleanParticipantsEventList();
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void downloadAttachments(HttpServletResponse response) {

		try(OutputStream stream = response.getOutputStream();
				ByteArrayOutputStream fos = new ByteArrayOutputStream();
				ZipOutputStream zos = new ZipOutputStream(fos);) {

			response.reset();
			String selectedEvent = church.getSelectedEvent();

			if(selectedEvent != null && !selectedEvent.trim().isEmpty()) {
				Event event = dao.getEvent(selectedEvent);
				if(event != null) {
					String resourcesEventPath = event.getFolderPath();

					File resourcesFolder = new File(resourcesEventPath.trim());

					if(resourcesFolder != null && resourcesFolder.exists() && 
							resourcesFolder.canRead() && resourcesFolder.isDirectory()){

						Set<String> allFiles = new HashSet<>();

						File[] participants = resourcesFolder.listFiles();
						if(participants != null && participants.length > 0){
							for(File participant : participants){
								if(participant != null){
									File[] participantResources = participant.listFiles();
									if(participantResources != null && participantResources.length > 0){
										for(File downloadFile : participantResources){
											if(downloadFile != null && downloadFile.exists() && 
													downloadFile.canRead() && downloadFile.isFile()){

												String fileName = downloadFile.getName();
												String name = fileName.substring(0, fileName.lastIndexOf("."));
												String format = fileName.substring(fileName.lastIndexOf("."));

												int version = 1;
												while(allFiles.contains(fileName)){
													++version;
													fileName = name + "_" + version + format;
												}
												allFiles.add(fileName);

												FileInputStream fis = new FileInputStream(downloadFile);

												zos.putNextEntry(new ZipEntry(fileName));

												byte[] bytes = new byte[1024];
												int length;
												while ((length = fis.read(bytes)) >= 0) {
													zos.write(bytes, 0, length);
												}
												zos.closeEntry();
												fis.close();
											}
										}
									}
								}
							}
						}

						zos.finish();
						zos.flush();
						fos.flush();
						zos.close();
						fos.close();

						response.setContentType(MediaType.ALL_VALUE);

						String resultName = cleanString(event.getEventName()) + ".zip";

						response.setHeader("Content-Disposition", "attachment; filename=" + resultName);
						response.addHeader("Content-Disposition", "attachment; filename=" + resultName);

						StreamUtils.copy(fos.toByteArray(), stream);
					}
				}
			}
		} catch (Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private String cleanString(String text){

		try{
			text = Normalizer.normalize(text, Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "").replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
			text = text.replaceAll("\\p{M}", "");
			text = text.replace("?", "");
			text = text.replace("!", "");
			text = text.replace("@", "");
			text = text.replace("%", "");
			text = text.replace("-", "");
			text = text.replace("#", "");
			text = text.replace("$", "");
			text = text.replace("^", "");
			text = text.replace("&", "");
			text = text.replace("*", "");
			text = text.replace("+", "");
			text = text.replace("=", "");
			text = text.replace(":", "");
			text = text.replace("'", "");
			text = text.replace("\"","");
			text = text.replace(";", "");
			text = text.replace(",", "");
			text = text.replace(".", "");
			text = text.replace("/", "");
			text = text.replace("\\", "");
			text = text.replace("|", "");

		}catch (Exception e) {
			new Utilities().writeFile(e);
		}
		return text;
	}

	private void prepareEmail(Church churchForm) {

		try{

			String emailTo = churchForm.getInvitationEmailValue();
			String title = cleanString(churchForm.getEmailTitleValue());

			String content = churchForm.getEmailContentValue();

			church.setInvitationEmailValue(emailTo);

			Set<String> emailsTo = new HashSet<>();

			if(emailTo != null && !emailTo.trim().isEmpty()){
				String[] splitEmails = emailTo.trim().split(";");
				if(splitEmails != null && splitEmails.length > 0){
					for(String email : splitEmails){
						if(email != null && !email.trim().isEmpty()){
							emailsTo.add(email.trim());
						}
					}
				}
			}

			String allEmailsSended = "";
			String allEmailsNotSended = "";

			ChurchTable user = church.getUser();

			if(user != null && emailsTo != null && !emailsTo.isEmpty()){

				if(content.contains("---")) {

					String selectedEvent = churchForm.getSelectedEvent();

					if(selectedEvent != null && !selectedEvent.trim().isEmpty() && 
							!selectedEvent.trim().equalsIgnoreCase(church.getEventsLabel()) &&
							!selectedEvent.trim().equalsIgnoreCase(church.getNoEventLabel())
							){
						Event event = dao.getEvent(selectedEvent);
						if(event != null) {
							content = content.replace("---", "<b>" + event.getEventPassword() + "</b>");
						}
					}
				}

				if(content.contains("\r\n")) {
					content = content.replace("\r\n", "<br>");
				}

				for(String email : emailsTo){
					if(email != null && !email.trim().isEmpty()){

						if(new Utilities().isValidEmailAddress(email)){

							new Utilities().sendEmail(user.getChurchEmail(), user.getChurchEmailPassword(), title, getEmailContent(content), email.trim());

							allEmailsSended += "<br>" + email;
						}
						else{
							allEmailsNotSended += "<br>" + email;
						}
					}
				}
			}

			if(allEmailsSended != null && !allEmailsSended.trim().isEmpty()){
				church.setSuccessful(church.getOkEmail() + "<br>" + allEmailsSended);
			}

			if(allEmailsNotSended != null && !allEmailsNotSended.trim().isEmpty()){
				church.setError(church.getNotOkEmail() + "<br>" + allEmailsNotSended);
			}
		} catch (Exception e) {
			new Utilities().writeFile(e);
		}
	}

	private String getEmailContent(String content){

		String result = "";

		try{

			result = new Utilities().getFirstDiv()
					+ " </style> </head> <body> "
					+ " <div class=\"overlay\"> <br>"
					+ content
					+ "<br><br><br>"
					+ Constant.appLink 
					+ "church=" + church.getSelectedChurch()
					+ "&event=" + church.getSelectedEvent()
					+ "&language=" + settings.getSelectedLanguage()
					+ " 'style='font-size:18px;text-align:center; "
					+ " height: 50px; width: 170px; "
					+ " background-color: #39D951; "
					+ " border-radius: 100px; "
					+ " font-size: 15px; "
					+ " cursor: pointer; "
					+ " padding: 20px; "
					+ " font-weight: bold; "
					+ " ' > "
					+ church.getAccessInvitation()
					+ "</a><br>"
					+ "</div> </body> </html> ";
		}
		catch (Exception e) {
			new Utilities().writeFile(e);
		}
		return result;
	}
}