package dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import beans.Language;
import beans.Version;

@Repository
@Transactional
public class DAOImplTool implements DAOTool {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean insertTranslation(Language language){

		boolean saved = false;

		try{

			if(language != null){

				org.hibernate.Session session = sessionFactory.getCurrentSession();

				if(session != null){
					session.saveOrUpdate(language);
					session.flush();
					session.clear(); 
					saved = true;
				}
			}
		}
		catch(Exception e){
			saved = false;
			e.printStackTrace();
		}

		return saved;
	}

	@Override
	public boolean isSaveOrUpdateVersion(Version version) throws Throwable{
		sessionFactory.getCurrentSession().saveOrUpdate(version);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Version getVersion(String versionName) {

		List<Version> versionList = null;

		try{

			String versionQuery = "from " + Version.class.getName();

			if(versionName != null && !versionName.trim().isEmpty()){
				versionQuery += " where value = :value ";
			}

			Query<Version> query = sessionFactory.getCurrentSession().createQuery(versionQuery);

			if(versionName != null && !versionName.trim().isEmpty()){
				query.setParameter("value", versionName.trim());
			}

			versionList = query.getResultList();

			if(versionList != null){
				for(Version version : versionList){
					if(version != null){
						return version;
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Language getLanguage(String languageName){

		try{
			org.hibernate.Session session = sessionFactory.getCurrentSession();

			if(session != null && languageName != null && !languageName.trim().isEmpty()){

				String languageQuery = "from " + Language.class.getName();

				languageQuery += " where value = :val ";

				Query<Language> query = session.createQuery(languageQuery);

				query.setParameter("val", languageName.trim());

				List<Language> results = query.getResultList();

				session.flush();
				session.clear(); 

				if(results != null && !results.isEmpty()){
					for(Language language : results){
						if(language != null && language.getValue().trim().equals(languageName.trim())){}
						return language;
					}
				}
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}
}