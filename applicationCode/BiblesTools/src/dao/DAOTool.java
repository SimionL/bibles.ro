package dao;

import beans.Language;
import beans.Version;

public interface DAOTool {
	public boolean isSaveOrUpdateVersion(Version version) throws Throwable;
	public Language getLanguage(String languageName);
	public Version getVersion(String versionName);
	public boolean insertTranslation(Language language);
}
