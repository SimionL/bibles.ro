package dao;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import beans.Church;
import dbBeans.ChurchTable;
import dbBeans.Event;
import dbBeans.Language;
import dbBeans.MessageTable;
import dbBeans.Participant;
import dbBeans.ScreensaverTable;
import utilities.Constant;


@Transactional
@Repository("dao")
@Scope("prototype")
public class DAOImpl implements DAO {

	@Autowired
	private SessionFactory sessionFactory;

	private final String bibleLog = Constant.bibleLog;
	private final String platformType = Constant.platformType;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ChurchTable getUser(long userId){

		try{

			String userQuery = "from " + ChurchTable.class.getName() + " where userId = :userId ";

			Query<ChurchTable> query = sessionFactory.getCurrentSession().createQuery(userQuery);

			query.setParameter("userId", userId);

			List<ChurchTable> usersList = query.getResultList();

			if(usersList != null){
				for(ChurchTable user : usersList){
					if(user != null){
						return user;
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ChurchTable getUserByUsername(String username){

		try{

			if(username != null && !username.trim().isEmpty()) {

				String userQuery = "from " + ChurchTable.class.getName() + " where username = :username ";

				Query<ChurchTable> query = sessionFactory.getCurrentSession().createQuery(userQuery);

				query.setParameter("username", username.trim());

				List<ChurchTable> usersList = query.getResultList();

				if(usersList != null){
					for(ChurchTable user : usersList){
						if(user != null){
							return user;
						}
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public TreeMap<Integer, ScreensaverTable> getScreensaversMap(final int category){

		try{

			String seectedCategory = null;

			if(category > 1){

				switch(category){
				case 2 : {
					seectedCategory = "Christ";
					break;
				}
				case 3 : {
					seectedCategory = "cross";
					break;
				}
				case 4 : {
					seectedCategory = "bible";
					break;
				}
				case 5 : {
					seectedCategory = "angels";
					break;
				}
				case 6 : {
					seectedCategory = "clouds";
					break;
				}
				case 7 : {
					seectedCategory = "pigeons";
					break;
				}
				case 8 : {
					seectedCategory = "others";
					break;
				}
				case 9 : {
					seectedCategory = "the knowledge of God";
					break;
				}
				case 10 : {
					seectedCategory = "how is God";
					break;
				}
				case 11 : {
					seectedCategory = "without fear";
					break;
				}
				case 12 : {
					seectedCategory = "listening in prayer";
					break;
				}
				case 13 : {
					seectedCategory = "how to find God wish";
					break;
				}
				case 14 : {
					seectedCategory = "God plan for man";
					break;
				}
				case 15 : {
					seectedCategory = "God answers";
					break;
				}
				}
			}

			String userQuery = "from " + ScreensaverTable.class.getName();

			if(seectedCategory != null && !seectedCategory.trim().isEmpty()){
				userQuery += " where category = :category ";
			}

			Query<ScreensaverTable> query = sessionFactory.getCurrentSession().createQuery(userQuery);

			if(seectedCategory != null && !seectedCategory.trim().isEmpty()){
				query.setParameter("category", seectedCategory);	
			}

			TreeMap<Integer, ScreensaverTable> result = new TreeMap<>();

			List<ScreensaverTable> screensaverList = query.getResultList();

			if(screensaverList != null && result != null && !screensaverList.isEmpty()){
				if(category > 1){
					for(ScreensaverTable screensaver : screensaverList){
						if(screensaver != null){
							String pictureName = screensaver.getPictureName();
							if(pictureName != null && !pictureName.trim().isEmpty() && pictureName.contains(".")){
								String pictureNumber = pictureName.substring(0, pictureName.indexOf("."));
								if(pictureNumber != null && pictureNumber.matches("\\d+")){
									result.put(Integer.valueOf(pictureNumber), screensaver);
								}
							}
						}
					}
				}
				else if(category == 1){

					TreeMap<Integer, ScreensaverTable> mapChrist =  new TreeMap<>();
					TreeMap<Integer, ScreensaverTable> mapCross =   new TreeMap<>();
					TreeMap<Integer, ScreensaverTable> mapBible =   new TreeMap<>();
					TreeMap<Integer, ScreensaverTable> mapAngels =  new TreeMap<>();
					TreeMap<Integer, ScreensaverTable> mapClouds =  new TreeMap<>();
					TreeMap<Integer, ScreensaverTable> mapPigeons = new TreeMap<>();
					TreeMap<Integer, ScreensaverTable> mapOthers =  new TreeMap<>();
					TreeMap<Integer, ScreensaverTable> mapKnowledgeOfGod =  new TreeMap<>();
					TreeMap<Integer, ScreensaverTable> mapHowIsGod =  new TreeMap<>();
					TreeMap<Integer, ScreensaverTable> mapWithoutFear =  new TreeMap<>();
					TreeMap<Integer, ScreensaverTable> mapListeningInPrayer =  new TreeMap<>();
					TreeMap<Integer, ScreensaverTable> mapHowToFindGodWish =  new TreeMap<>();
					TreeMap<Integer, ScreensaverTable> mapGodPlanForMan =  new TreeMap<>();
					TreeMap<Integer, ScreensaverTable> mapGodAnswers =  new TreeMap<>();
					
					int maxSize = 0;
					for(ScreensaverTable screensaver : screensaverList){
						if(screensaver != null){
							String pictureName = screensaver.getPictureName();
							if(pictureName != null && !pictureName.trim().isEmpty() && pictureName.contains(".")){
								String pictureNumber = pictureName.substring(0, pictureName.indexOf("."));
								if(pictureNumber != null && pictureNumber.matches("\\d+")){
									switch(screensaver.getCategory()){
									case "Christ" : {
										mapChrist.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapChrist.size()){
											maxSize = mapChrist.size();
										}
										break;
									}
									case "cross" : {
										mapCross.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapCross.size()){
											maxSize = mapCross.size();
										}
										break;
									}
									case "bible" : {
										mapBible.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapBible.size()){
											maxSize = mapBible.size();
										}
										break;
									}
									case "angels" : {
										mapAngels.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapAngels.size()){
											maxSize = mapAngels.size();
										}
										break;
									}
									case "clouds" : {
										mapClouds.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapClouds.size()){
											maxSize = mapClouds.size();
										}
										break;
									}
									case "pigeons" : {
										mapPigeons.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapPigeons.size()){
											maxSize = mapPigeons.size();
										}
										break;
									}
									case "others" : {
										mapOthers.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapOthers.size()){
											maxSize = mapOthers.size();
										}
										break;
									}
									case "the knowledge of God" : {
										mapKnowledgeOfGod.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapKnowledgeOfGod.size()){
											maxSize = mapKnowledgeOfGod.size();
										}
										break;
									}
									case "how is God" : {
										mapHowIsGod.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapHowIsGod.size()){
											maxSize = mapHowIsGod.size();
										}
										break;
									}
									case "without fear" : {
										mapWithoutFear.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapWithoutFear.size()){
											maxSize = mapWithoutFear.size();
										}
										break;
									}
									case "listening in prayer" : {
										mapListeningInPrayer.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapListeningInPrayer.size()){
											maxSize = mapListeningInPrayer.size();
										}
										break;
									}
									case "how to find God wish" : {
										mapHowToFindGodWish.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapHowToFindGodWish.size()){
											maxSize = mapHowToFindGodWish.size();
										}
										break;
									}
									case "God plan for man" : {
										mapGodPlanForMan.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapGodPlanForMan.size()){
											maxSize = mapGodPlanForMan.size();
										}
										break;
									}
									case "God answers" : {
										mapGodAnswers.put(Integer.valueOf(pictureNumber), screensaver);
										if(maxSize < mapGodAnswers.size()){
											maxSize = mapGodAnswers.size();
										}
										break;
									}
									}
								}
							}
						}
					}
					if(maxSize > 0){
						for(int pictueN = 1 ; pictueN <= maxSize ; pictueN++){
							if(mapChrist.containsKey(pictueN)){
								result.put(result.size() + 1, mapChrist.get(pictueN));
							}
							if(mapCross.containsKey(pictueN)){
								result.put(result.size() + 1, mapCross.get(pictueN));
							}
							if(mapBible.containsKey(pictueN)){
								result.put(result.size() + 1, mapBible.get(pictueN));
							}
							if(mapAngels.containsKey(pictueN)){
								result.put(result.size() + 1, mapAngels.get(pictueN));
							}
							if(mapClouds.containsKey(pictueN)){
								result.put(result.size() + 1, mapClouds.get(pictueN));
							}
							if(mapPigeons.containsKey(pictueN)){
								result.put(result.size() + 1, mapPigeons.get(pictueN));
							}
							if(mapOthers.containsKey(pictueN)){
								result.put(result.size() + 1, mapOthers.get(pictueN));
							}
							if(mapKnowledgeOfGod.containsKey(pictueN)){
								result.put(result.size() + 1, mapKnowledgeOfGod.get(pictueN));
							}
							if(mapHowIsGod.containsKey(pictueN)){
								result.put(result.size() + 1, mapHowIsGod.get(pictueN));
							}	
							if(mapWithoutFear.containsKey(pictueN)){
								result.put(result.size() + 1, mapWithoutFear.get(pictueN));
							}
							if(mapListeningInPrayer.containsKey(pictueN)){
								result.put(result.size() + 1, mapListeningInPrayer.get(pictueN));
							}
							if(mapHowToFindGodWish.containsKey(pictueN)){
								result.put(result.size() + 1, mapHowToFindGodWish.get(pictueN));
							}
							if(mapGodPlanForMan.containsKey(pictueN)){
								result.put(result.size() + 1, mapGodPlanForMan.get(pictueN));
							}
							if(mapGodAnswers.containsKey(pictueN)){
								result.put(result.size() + 1, mapGodAnswers.get(pictueN));
							}
						}
					}
				}
			}

			return result;

		}
		catch(Exception e){
			writeFile(e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Participant getParticipantById(long participantId){

		try{

			String userQuery = "from " + Participant.class.getName() + " where participantId = :participantId ";

			Query<Participant> query = sessionFactory.getCurrentSession().createQuery(userQuery);

			query.setParameter("participantId", participantId);

			List<Participant> participantList = query.getResultList();

			if(participantList != null){
				for(Participant participant : participantList){
					if(participant != null){
						return participant;
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Event getEvent(String eventName){

		try{

			String eventQuery = "from " + Event.class.getName() + " where eventName = :eventName ";

			Query<Event> query = sessionFactory.getCurrentSession().createQuery(eventQuery);

			query.setParameter("eventName", eventName);

			List<Event> eventList = query.getResultList();

			if(eventList != null){
				for(Event event : eventList){
					if(event != null){
						return event;
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean isEventDeleted(String eventName){

		try{

			String eventQuery = "delete from " + Event.class.getName() + " where eventName = :eventName ";

			Query<Event> query = sessionFactory.getCurrentSession().createQuery(eventQuery);

			query.setParameter("eventName", eventName);

			int result = query.executeUpdate();

			if (result > 0) {
				return true;
			}
			else{
				return false;
			}
		}
		catch(Exception e){
			writeFile(e);
			return false;
		}
	}
	@Override
	@Transactional
	public boolean isSaveOrUpdateUser(ChurchTable user){

		try{
			if(user != null){	

				if(user.getUserId() < 0){
					return false;
				}
				else{
					sessionFactory.getCurrentSession().saveOrUpdate(user);
					return true;
				}
			}
		}
		catch(Exception e){
			writeFile(e);
			return false;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean isSaveOrUpdateMessage(MessageTable message){

		try{
			if(message != null){	

				if(message.getMessageId() < 0){
					return false;
				}
				else{
					sessionFactory.getCurrentSession().saveOrUpdate(message);
					return true;
				}
			}
		}
		catch(Exception e){
			writeFile(e);
			return false;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<MessageTable> getAllMessages(String userName) {

		try{

			String messageQuery = "from " + MessageTable.class.getName();

			if(userName != null && !userName.trim().isEmpty()){
				messageQuery += " where user_name = :val ";
			}

			Query<MessageTable> query = sessionFactory.getCurrentSession().createQuery(messageQuery);

			if(userName != null && !userName.trim().isEmpty()){
				query.setParameter("val", userName.trim());
			}

			return query.getResultList();	
		}
		catch(Exception e){
			writeFile(e);
		}

		return null;
	}

	@Override
	@Transactional
	public boolean isSaveOrUpdateEvent(Event event){

		try{
			if(event != null){	
				if(event.getEventId() < 0){
					return false;
				}
				else{
					sessionFactory.getCurrentSession().saveOrUpdate(event);
					return true;
				}
			}
		}
		catch(Exception e){
			writeFile(e);
			return false;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean deleteUser(ChurchTable user){

		try{
			if(user != null){
				sessionFactory.getCurrentSession().delete(user);
				return true;
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return false;
	}

	@Override
	@Transactional
	public boolean deleteParticipant(Participant participant){

		try{
			if(participant != null){
				org.hibernate.Session session = sessionFactory.getCurrentSession();
				session.remove(participant);
				session.flush();
				return true;
			}
		}
		catch(Exception e){
			writeFile(e);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Language> getAllLanguages(String languageName) {

		try{

			String languageQuery = "from " + Language.class.getName();

			if(languageName != null && !languageName.trim().isEmpty()){
				languageQuery += " where value = :val ";
			}

			Query<Language> query = sessionFactory.getCurrentSession().createQuery(languageQuery);

			if(languageName != null && !languageName.trim().isEmpty()){
				query.setParameter("val", languageName.trim());
			}

			return query.getResultList();

		}
		catch(Exception e){
			writeFile(e);
		}

		return null;
	}	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Set<String> getColumn(String query) {

		Set<String> results = null;

		try{
			results = new TreeSet<>(sessionFactory.getCurrentSession().createQuery(query).getResultList());
		}
		catch(Exception e){
			writeFile(e);
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean existChurchUser(Church church, Church churchForm){

		try{

			if(church != null && churchForm != null){

				Query<ChurchTable> query = sessionFactory.getCurrentSession().createQuery("from " + ChurchTable.class.getName() + " where username = :username AND password = :password ");

				if(query != null){

					String username = churchForm.getUsername();
					String password = churchForm.getPassword();

					if(username != null && !username.trim().isEmpty()){
						query.setParameter("username", username.trim());
					}

					if(password != null && !password.trim().isEmpty()){
						query.setParameter("password", password.trim());
					}

					List<ChurchTable> usersList = query.getResultList();

					if(usersList != null && usersList.size() == 1){
						for(ChurchTable user : usersList){
							if(user != null){
								church.setUser(user);
								return true;
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			writeFile(e);
		}

		if(church != null)church.setUser(null);

		return false;
	}

	public void writeFile(Exception exception) {

		exception.printStackTrace();

		String path = bibleLog + platformType + "log.txt";

		File file = new File(path.trim());
		try{
			if (file != null && !file.exists()){
				file.createNewFile();
				file.setReadable(true);
				file.setWritable(true);
			}
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {

			StackTraceElement[] stackTrace = exception.getStackTrace();

			String stackTraceInfo = "\n\n" + (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date()) + " " + "\n\n";

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

			writer.write(stackTraceInfo + "\n" + exception.getMessage() + "\n" + exception.getCause() + "\n" + exception.getLocalizedMessage() + "\n\n" + "==========================================================\n");
			writer.flush();
		} catch (Exception ex) {
			//System.out.println(ex.getMessage());
		}
	}
}