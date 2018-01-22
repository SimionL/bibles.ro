package utilities;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS, value="session")
public enum Constants implements Serializable {

	newLine("\n"),
	newLine_2("(\r)"),
	newTextAreaLine_1("(\\\\r\\\\n|\\\\n)"),
	newTextAreaLine_2("(\r\n|\n)"),
	format("utf-8"),
	enter("<br>"),
	space(" "),
	tab("	"),
	comma(","),
	bibleBean("bible"),
	english("english"),
	references("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"),

	dburl(----------------------------------),
	username(-------------------------------),
	driver("org.postgresql.Driver"),

	//LOCAL---------------------------------------------------------------------------------
	
	dbPath("C:\\BiblesDB\\"),
	eventsPath("C:\\events"),
	bibleLog("C:\\BibleLog"),
	platformType("\\"),
	password(--------------------------------),
	picturesPath("C:\\Users\\Laurentiu\\Desktop\\bible\\pictures\\");

	//SERVER---------------------------------------------------------------------------------
	
//	dbPath("/opt/tomcat/BiblesDB/"),
//	eventsPath("/opt/tomcat/events"),
//	bibleLog("/opt/tomcat/BibleLog"),
//	platformType("/"),
//	password(---------------------------------------),
//	picturesPath("/opt/tomcat/screensaver");

	private Constants(String value){
		this.value = value;
	}
	public final String value;
}