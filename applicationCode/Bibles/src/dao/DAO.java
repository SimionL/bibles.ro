package dao;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

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

@Transactional
@Repository("dao")
@Scope("prototype")
public interface DAO {
	public List<Language> getAllLanguages(String languageName);
	public boolean isSaveOrUpdateUser(ChurchTable user);
	public Set<String> getColumn(String query);
	public boolean existChurchUser(Church church, Church churchForm);
	public boolean deleteUser(ChurchTable user);
	public boolean deleteParticipant(Participant participant);
	public ChurchTable getUser(long userId);
	public Event getEvent(String eventName);
	public boolean isSaveOrUpdateEvent(Event event);
	public boolean isSaveOrUpdateMessage(MessageTable message);
	public List<MessageTable> getAllMessages(String userName);
	public boolean isEventDeleted(String eventName);
	public ChurchTable getUserByUsername(String username);
	public Participant getParticipantById(long participantId);
	public TreeMap<Integer, ScreensaverTable> getScreensaversMap(int category);
}