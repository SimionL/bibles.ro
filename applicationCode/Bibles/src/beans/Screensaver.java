package beans;

import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class Screensaver {

	private transient String bible;
	private transient String settings;
	private transient String popup;
	private transient String reference;
	private transient String church;
	private transient String screensaver;
	private transient String code;
	private transient String voice;
	private transient String feedback;
	private transient String thankYou;

	private transient String screensaverColorPaletteSelected_1 = "#000000";
	private transient String screensaverColorPaletteSelected_2 = "#000000";
	private transient String screensaverColorPaletteSelected_3 = "#000000";
	private transient String screensaverColorPaletteSelected_4 = "#000000";
	private transient String screensaverColorPaletteSelected_5 = "#ffffff";
	private transient String popupInformation;
	private transient String eventId;
	private transient String popupId;
	private transient String error;
	private transient String noVersionSelected;
	private transient String identifiedWords = "";
	private transient String languageCode;
	private transient String melodieSelected;

	private transient boolean openPopup;
	private transient boolean usingVoice;
	private transient boolean playSong;

	private final transient TreeSet<Integer> screensaverParamList_1 = new TreeSet<>();
	private final transient TreeSet<Integer> screensaverParamList_2 = new TreeSet<>();
	private final transient TreeSet<Integer> screensaverParamList_3 = new TreeSet<>();
	private final transient TreeSet<Integer> screensaverParamList_4 = new TreeSet<>();
	private final transient TreeSet<Integer> screensaverParamList_5 = new TreeSet<>();
	private final transient TreeSet<Integer> screensaverParamList_6 = new TreeSet<>();
	private final transient TreeSet<Integer> screensaverParamList_7 = new TreeSet<>();
	private final transient TreeSet<Integer> screensaverParamList_8 = new TreeSet<>();
	private final transient TreeSet<Integer> screensaverParamList_9 = new TreeSet<>();
	private final transient TreeSet<Integer> screensaverParamList_10 = new TreeSet<>();
	private final transient TreeSet<Integer> screensaverParamList_11 = new TreeSet<>();
	private final transient TreeSet<Integer> screensaverParamList_12 = new TreeSet<>();
	private final transient TreeSet<Integer> screensaverTimeList = new TreeSet<>();
	private final transient TreeSet<String> melodiesList = new TreeSet<>();
	private final transient LinkedHashMap<Integer, String> screensaverRadioMap = new LinkedHashMap<>();
	private final transient LinkedHashMap<Integer, String> screensaverCategoriesMap = new LinkedHashMap<>();
	private final transient TreeMap<Integer, String> screensaverTypeMap = new TreeMap<>();

	private transient int screensaverParamSelected_1 = 5;
	private transient int screensaverParamSelected_2;
	private transient int screensaverParamSelected_3;
	private transient int screensaverParamSelected_4 = -5;
	private transient int screensaverParamSelected_5;
	private transient int screensaverParamSelected_6;
	private transient int screensaverParamSelected_7;
	private transient int screensaverParamSelected_8 = 4;
	private transient int screensaverParamSelected_9;
	private transient int screensaverParamSelected_10;
	private transient int screensaverParamSelected_11 = -4;
	private transient int screensaverParamSelected_12;
	private transient int screensaverRadioSelected = 1;
	private transient int screensaverCategoriesSelected = 1;
	private transient int screensaverTimeSelected = 10;
	private transient int screensaverTypeSelected = 1;

	public int getScreensaverTypeSelected() {
		return screensaverTypeSelected;
	}

	public void setScreensaverTypeSelected(int screensaverTypeSelected) {
		this.screensaverTypeSelected = screensaverTypeSelected;
	}

	public int getScreensaverTimeSelected() {
		return screensaverTimeSelected;
	}

	public void setScreensaverTimeSelected(int screensaverTimeSelected) {
		this.screensaverTimeSelected = screensaverTimeSelected;
	}

	public String getBible() {
		return bible;
	}

	public void setBible(String bible) {
		this.bible = bible;
	}

	public boolean isOpenPopup() {
		return openPopup;
	}

	public void setOpenPopup(boolean openPopup) {
		this.openPopup = openPopup;
	}

	public int getScreensaverParamSelected_1() {
		return screensaverParamSelected_1;
	}

	public void setScreensaverParamSelected_1(int screensaverParamSelected_1) {
		this.screensaverParamSelected_1 = screensaverParamSelected_1;
	}

	public int getScreensaverParamSelected_2() {
		return screensaverParamSelected_2;
	}

	public void setScreensaverParamSelected_2(int screensaverParamSelected_2) {
		this.screensaverParamSelected_2 = screensaverParamSelected_2;
	}

	public int getScreensaverParamSelected_3() {
		return screensaverParamSelected_3;
	}

	public void setScreensaverParamSelected_3(int screensaverParamSelected_3) {
		this.screensaverParamSelected_3 = screensaverParamSelected_3;
	}

	public int getScreensaverParamSelected_4() {
		return screensaverParamSelected_4;
	}

	public void setScreensaverParamSelected_4(int screensaverParamSelected_4) {
		this.screensaverParamSelected_4 = screensaverParamSelected_4;
	}

	public int getScreensaverParamSelected_5() {
		return screensaverParamSelected_5;
	}

	public void setScreensaverParamSelected_5(int screensaverParamSelected_5) {
		this.screensaverParamSelected_5 = screensaverParamSelected_5;
	}

	public int getScreensaverParamSelected_6() {
		return screensaverParamSelected_6;
	}

	public void setScreensaverParamSelected_6(int screensaverParamSelected_6) {
		this.screensaverParamSelected_6 = screensaverParamSelected_6;
	}

	public int getScreensaverParamSelected_7() {
		return screensaverParamSelected_7;
	}

	public void setScreensaverParamSelected_7(int screensaverParamSelected_7) {
		this.screensaverParamSelected_7 = screensaverParamSelected_7;
	}

	public int getScreensaverParamSelected_8() {
		return screensaverParamSelected_8;
	}

	public void setScreensaverParamSelected_8(int screensaverParamSelected_8) {
		this.screensaverParamSelected_8 = screensaverParamSelected_8;
	}

	public int getScreensaverParamSelected_9() {
		return screensaverParamSelected_9;
	}

	public void setScreensaverParamSelected_9(int screensaverParamSelected_9) {
		this.screensaverParamSelected_9 = screensaverParamSelected_9;
	}

	public int getScreensaverParamSelected_10() {
		return screensaverParamSelected_10;
	}

	public void setScreensaverParamSelected_10(int screensaverParamSelected_10) {
		this.screensaverParamSelected_10 = screensaverParamSelected_10;
	}

	public int getScreensaverParamSelected_11() {
		return screensaverParamSelected_11;
	}

	public void setScreensaverParamSelected_11(int screensaverParamSelected_11) {
		this.screensaverParamSelected_11 = screensaverParamSelected_11;
	}

	public int getScreensaverParamSelected_12() {
		return screensaverParamSelected_12;
	}

	public void setScreensaverParamSelected_12(int screensaverParamSelected_12) {
		this.screensaverParamSelected_12 = screensaverParamSelected_12;
	}

	public String getScreensaverColorPaletteSelected_1() {
		return screensaverColorPaletteSelected_1;
	}

	public void setScreensaverColorPaletteSelected_1(String screensaverColorPaletteSelected_1) {
		this.screensaverColorPaletteSelected_1 = screensaverColorPaletteSelected_1;
	}

	public String getScreensaverColorPaletteSelected_2() {
		return screensaverColorPaletteSelected_2;
	}

	public void setScreensaverColorPaletteSelected_2(String screensaverColorPaletteSelected_2) {
		this.screensaverColorPaletteSelected_2 = screensaverColorPaletteSelected_2;
	}

	public String getScreensaverColorPaletteSelected_3() {
		return screensaverColorPaletteSelected_3;
	}

	public void setScreensaverColorPaletteSelected_3(String screensaverColorPaletteSelected_3) {
		this.screensaverColorPaletteSelected_3 = screensaverColorPaletteSelected_3;
	}

	public String getScreensaverColorPaletteSelected_4() {
		return screensaverColorPaletteSelected_4;
	}

	public void setScreensaverColorPaletteSelected_4(String screensaverColorPaletteSelected_4) {
		this.screensaverColorPaletteSelected_4 = screensaverColorPaletteSelected_4;
	}

	public String getScreensaverColorPaletteSelected_5() {
		return screensaverColorPaletteSelected_5;
	}

	public void setScreensaverColorPaletteSelected_5(String screensaverColorPaletteSelected_5) {
		this.screensaverColorPaletteSelected_5 = screensaverColorPaletteSelected_5;
	}

	public TreeSet<Integer> getScreensaverParamList_1() {
		return screensaverParamList_1;
	}

	public TreeSet<Integer> getScreensaverParamList_2() {
		return screensaverParamList_2;
	}

	public TreeSet<Integer> getScreensaverParamList_3() {
		return screensaverParamList_3;
	}

	public TreeSet<Integer> getScreensaverParamList_4() {
		return screensaverParamList_4;
	}

	public TreeSet<Integer> getScreensaverParamList_5() {
		return screensaverParamList_5;
	}

	public TreeSet<Integer> getScreensaverParamList_6() {
		return screensaverParamList_6;
	}

	public TreeSet<Integer> getScreensaverParamList_7() {
		return screensaverParamList_7;
	}

	public TreeSet<Integer> getScreensaverParamList_8() {
		return screensaverParamList_8;
	}

	public TreeSet<Integer> getScreensaverParamList_9() {
		return screensaverParamList_9;
	}

	public TreeSet<Integer> getScreensaverParamList_10() {
		return screensaverParamList_10;
	}

	public TreeSet<Integer> getScreensaverParamList_11() {
		return screensaverParamList_11;
	}

	public TreeSet<Integer> getScreensaverParamList_12() {
		return screensaverParamList_12;
	}

	public int getScreensaverRadioSelected() {
		return screensaverRadioSelected;
	}

	public void setScreensaverRadioSelected(int screensaverRadioSelected) {
		this.screensaverRadioSelected = screensaverRadioSelected;
	}

	public int getScreensaverCategoriesSelected() {
		return screensaverCategoriesSelected;
	}

	public void setScreensaverCategoriesSelected(int screensaverCategoriesSelected) {
		this.screensaverCategoriesSelected = screensaverCategoriesSelected;
	}

	public TreeSet<Integer> getScreensaverTimeList() {
		return screensaverTimeList;
	}

	public LinkedHashMap<Integer, String> getScreensaverCategoriesMap() {
		return screensaverCategoriesMap;
	}

	public LinkedHashMap<Integer, String> getScreensaverRadioMap() {
		return screensaverRadioMap;
	}

	public TreeMap<Integer, String> getScreensaverTypeMap() {
		return screensaverTypeMap;
	}

	public String getPopupInformation() {
		return popupInformation;
	}

	public void setPopupInformation(String popupInformation) {
		this.popupInformation = popupInformation;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getScreensaver() {
		return screensaver;
	}

	public void setScreensaver(String screensaver) {
		this.screensaver = screensaver;
	}

	public String getSettings() {
		return settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

	public String getChurch() {
		return church;
	}

	public void setChurch(String church) {
		this.church = church;
	}

	public String getPopupId() {
		return popupId;
	}

	public void setPopupId(String popupId) {
		this.popupId = popupId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNoVersionSelected() {
		return noVersionSelected;
	}

	public void setNoVersionSelected(String noVersionSelected) {
		this.noVersionSelected = noVersionSelected;
	}

	public String getPopup() {
		return popup;
	}

	public void setPopup(String popup) {
		this.popup = popup;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public boolean isUsingVoice() {
		return usingVoice;
	}

	public void setUsingVoice(boolean usingVoice) {
		this.usingVoice = usingVoice;
	}

	public String getIdentifiedWords() {
		return identifiedWords;
	}

	public void setIdentifiedWords(String identifiedWords) {
		this.identifiedWords = identifiedWords;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public boolean isPlaySong() {
		return playSong;
	}

	public void setPlaySong(boolean playSong) {
		this.playSong = playSong;
	}

	public String getMelodieSelected() {
		return melodieSelected;
	}

	public void setMelodieSelected(String melodieSelected) {
		this.melodieSelected = melodieSelected;
	}

	public TreeSet<String> getMelodiesList() {
		return melodiesList;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getThankYou() {
		return thankYou;
	}

	public void setThankYou(String thankYou) {
		this.thankYou = thankYou;
	}

}