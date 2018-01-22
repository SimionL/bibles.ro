package utilities;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS, value="session")
public enum Constants implements Serializable {

	dburl(----------------------------------),
	username(-------------------------------),
	driver("org.postgresql.Driver"),

	//LOCAL---------------------------------------------------------------------------------

	melodiesPath("C:\\Users\\Laurentiu\\Desktop\\bible\\songs"),
	dbPath("C:\\BiblesDB\\"),
	eventsPath("C:\\events"),
	bibleLog("C:\\BibleLog"),
	platformType("\\"),
	password(-------------------------------),
	picturesPath("C:\\Users\\Laurentiu\\Desktop\\bible\\pictures\\"),
	codePath("C:\\Users\\Laurentiu\\Desktop\\bible\\istoric\\105\\"),
	appLink(" <a &nbsp; href='http://localhost:8080/Bibles/invitation?");

	//SERVER---------------------------------------------------------------------------------

//		melodiesPath("/opt/tomcat/webapps/ROOT/songs"),
//		dbPath("/opt/tomcat/BiblesDB/"),
//		eventsPath("/opt/tomcat/events"),
//		bibleLog("/opt/tomcat/BibleLog"),
//		platformType("/"),
//		password(------------------------------),
//		picturesPath("/opt/tomcat/screensaver"),
//		codePath("/opt/tomcat/applicationCode/"),
//		appLink(" <a &nbsp; href='http://www.bibles.ro/invitation?");

	private Constants(String value){
		this.value = value;
	}
	public final String value;
}