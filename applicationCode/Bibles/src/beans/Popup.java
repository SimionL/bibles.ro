package beans;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;

import org.springframework.web.multipart.MultipartFile;

public final class Popup{

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

	private transient String eventId;
	private final LinkedHashSet<String> popupFontList = new LinkedHashSet<>();
	private final LinkedHashSet<String> popupMarginTopList = new LinkedHashSet<>();
	private final LinkedHashSet<String> popupMarginBottomList = new LinkedHashSet<>();
	private final LinkedHashSet<String> popupMarginRightList = new LinkedHashSet<>();
	private final LinkedHashSet<String> popupMarginLeftList = new LinkedHashSet<>();
	private final LinkedHashMap<String, String> popupTextAlignMap = new LinkedHashMap<>();
	private final TreeSet<Integer> scriptureTextOpacityList = new TreeSet<>();
	private final TreeSet<Integer> pictureOpacityList = new TreeSet<>();
	private transient List<MultipartFile> popupPictureSelected;
	private final TreeSet<String> fontFamilyPopupList = new TreeSet<>();
	private final TreeSet<String> fontStylePopupList = new TreeSet<>();
	private final TreeSet<Integer> letterSpacingPopupList = new TreeSet<>();
	private final TreeSet<Integer> lineHeightPopupList = new TreeSet<>();
	private final TreeSet<Integer> wordSpacingPopupList = new TreeSet<>();

	private transient String popupFontSelected = "20";
	private transient String popupTextAlignSelected = "justify";
	private transient String popupBackgroundColorPaletteSelected = "#ffffff";
	private transient String popupUserMessageSelected = "";
	private transient String popupTextColorPaletteSelected = "#000000";
	private transient String fontFamilyPopupSelected = "Times New Roman";
	private transient String fontStylePopupSelected = "normal";

	private transient String popupMarginTopLabel;
	private transient String popupMarginBottomLabel;
	private transient String popupMarginRightLabel;
	private transient String popupMarginLeftLabel;
	private transient String popupTextColorLabel;
	private transient String popupBackgroundColorLabel;
	private transient String popupTextAlignLabel;
	private transient String popupUserMessageLabel;
	private transient String popupPictureLabel;
	private transient String checkBoxScriptureTextLabel;
	private transient String checkBoxPictureLabel;
	private transient String checkBoxUserMessageLabel;
	private transient String fontFamilyPopupLabel;
	private transient String fontStylePopupLabel;
	private transient String letterSpacingPopupLabel;
	private transient String lineHeightPopupLabel;
	private transient String wordSpacingPopupLabel;
	private transient String boldLabel;
	private transient String fontLabel;
	private transient String imageOpacityLabel;
	private transient String textOpacityLabel;
	private transient String identifiedWords = "";
	private transient String languageCode;

	private transient String wrongFileSize;
	private transient String wrongFormat;
	private transient String wrongFiles;
	private transient String popupInformation;
	private transient String popupId;
	private transient String error;
	private transient String defaultPopupImageLabel;

	private transient boolean displayScriptureText;
	private transient boolean openPopup;
	private transient boolean displayPicture;
	private transient boolean displayUserMessage;
	private transient boolean popupBoldSelected = true;
	private transient boolean defaultPopupImageSelected;
	private transient boolean usingVoice;

	private transient int wordSpacingPopupSelected = 0;
	private transient int letterSpacingPopupSelected = 0;
	private transient int lineHeightPopupSelected = 1;
	private transient int imageOpacitySelected = 10;
	private transient int scriptureTextOpacitySelected = 10;

	private transient int popupMarginTopSelected = 5;
	private transient int popupMarginBottomSelected = 5;
	private transient int popupMarginRightSelected = 70;
	private transient int popupMarginLeftSelected = 70;


	public String getBible() {
		return bible;
	}

	public void setBible(String bible) {
		this.bible = bible;
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

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getPopupFontSelected() {
		return popupFontSelected;
	}

	public void setPopupFontSelected(String popupFontSelected) {
		this.popupFontSelected = popupFontSelected;
	}

	public LinkedHashSet<String> getPopupFontList() {
		return popupFontList;
	}

	public boolean isPopupBoldSelected() {
		return popupBoldSelected;
	}

	public void setPopupBoldSelected(boolean popupBoldSelected) {
		this.popupBoldSelected = popupBoldSelected;
	}

	public int getPopupMarginTopSelected() {
		return popupMarginTopSelected;
	}

	public void setPopupMarginTopSelected(int popupMarginTopSelected) {
		this.popupMarginTopSelected = popupMarginTopSelected;
	}

	public int getPopupMarginBottomSelected() {
		return popupMarginBottomSelected;
	}

	public void setPopupMarginBottomSelected(int popupMarginBottomSelected) {
		this.popupMarginBottomSelected = popupMarginBottomSelected;
	}

	public int getPopupMarginRightSelected() {
		return popupMarginRightSelected;
	}

	public void setPopupMarginRightSelected(int popupMarginRightSelected) {
		this.popupMarginRightSelected = popupMarginRightSelected;
	}

	public int getPopupMarginLeftSelected() {
		return popupMarginLeftSelected;
	}

	public void setPopupMarginLeftSelected(int popupMarginLeftSelected) {
		this.popupMarginLeftSelected = popupMarginLeftSelected;
	}

	public String getPopupTextAlignSelected() {
		return popupTextAlignSelected;
	}

	public void setPopupTextAlignSelected(String popupTextAlignSelected) {
		this.popupTextAlignSelected = popupTextAlignSelected;
	}

	public String getPopupBackgroundColorPaletteSelected() {
		return popupBackgroundColorPaletteSelected;
	}

	public void setPopupBackgroundColorPaletteSelected(String popupBackgroundColorPaletteSelected) {
		this.popupBackgroundColorPaletteSelected = popupBackgroundColorPaletteSelected;
	}

	public List<MultipartFile> getPopupPictureSelected() {
		return popupPictureSelected;
	}

	public void setPopupPictureSelected(List<MultipartFile> popupPictureSelected) {
		this.popupPictureSelected = popupPictureSelected;
	}

	public String getWrongFileSize() {
		return wrongFileSize;
	}

	public void setWrongFileSize(String wrongFileSize) {
		this.wrongFileSize = wrongFileSize;
	}

	public String getPopupMarginTopLabel() {
		return popupMarginTopLabel;
	}

	public void setPopupMarginTopLabel(String popupMarginTopLabel) {
		this.popupMarginTopLabel = popupMarginTopLabel;
	}

	public String getPopupMarginBottomLabel() {
		return popupMarginBottomLabel;
	}

	public void setPopupMarginBottomLabel(String popupMarginBottomLabel) {
		this.popupMarginBottomLabel = popupMarginBottomLabel;
	}

	public String getPopupMarginRightLabel() {
		return popupMarginRightLabel;
	}

	public void setPopupMarginRightLabel(String popupMarginRightLabel) {
		this.popupMarginRightLabel = popupMarginRightLabel;
	}

	public String getPopupMarginLeftLabel() {
		return popupMarginLeftLabel;
	}

	public void setPopupMarginLeftLabel(String popupMarginLeftLabel) {
		this.popupMarginLeftLabel = popupMarginLeftLabel;
	}

	public String getPopupTextAlignLabel() {
		return popupTextAlignLabel;
	}

	public void setPopupTextAlignLabel(String popupTextAlignLabel) {
		this.popupTextAlignLabel = popupTextAlignLabel;
	}

	public String getPopupBackgroundColorLabel() {
		return popupBackgroundColorLabel;
	}

	public void setPopupBackgroundColorLabel(String popupBackgroundColorLabel) {
		this.popupBackgroundColorLabel = popupBackgroundColorLabel;
	}

	public String getPopupPictureLabel() {
		return popupPictureLabel;
	}

	public void setPopupPictureLabel(String popupPictureLabel) {
		this.popupPictureLabel = popupPictureLabel;
	}

	public LinkedHashSet<String> getPopupMarginTopList() {
		return popupMarginTopList;
	}

	public LinkedHashSet<String> getPopupMarginBottomList() {
		return popupMarginBottomList;
	}

	public LinkedHashSet<String> getPopupMarginRightList() {
		return popupMarginRightList;
	}

	public LinkedHashSet<String> getPopupMarginLeftList() {
		return popupMarginLeftList;
	}

	public LinkedHashMap<String, String> getPopupTextAlignMap() {
		return popupTextAlignMap;
	}

	public String getPopupUserMessageSelected() {
		return popupUserMessageSelected;
	}

	public void setPopupUserMessageSelected(String popupUserMessageSelected) {
		this.popupUserMessageSelected = popupUserMessageSelected;
	}

	public String getPopupUserMessageLabel() {
		return popupUserMessageLabel;
	}

	public void setPopupUserMessageLabel(String popupUserMessageLabel) {
		this.popupUserMessageLabel = popupUserMessageLabel;
	}

	public String getPopupTextColorPaletteSelected() {
		return popupTextColorPaletteSelected;
	}

	public void setPopupTextColorPaletteSelected(String popupTextColorPaletteSelected) {
		this.popupTextColorPaletteSelected = popupTextColorPaletteSelected;
	}

	public String getPopupTextColorLabel() {
		return popupTextColorLabel;
	}

	public void setPopupTextColorLabel(String popupTextColorLabel) {
		this.popupTextColorLabel = popupTextColorLabel;
	}

	public boolean isDisplayScriptureText() {
		return displayScriptureText;
	}

	public void setDisplayScriptureText(boolean displayScriptureText) {
		this.displayScriptureText = displayScriptureText;
	}

	public boolean isDisplayPicture() {
		return displayPicture;
	}

	public void setDisplayPicture(boolean displayPicture) {
		this.displayPicture = displayPicture;
	}

	public boolean isDisplayUserMessage() {
		return displayUserMessage;
	}

	public void setDisplayUserMessage(boolean displayUserMessage) {
		this.displayUserMessage = displayUserMessage;
	}

	public String getWrongFormat() {
		return wrongFormat;
	}

	public void setWrongFormat(String wrongFormat) {
		this.wrongFormat = wrongFormat;
	}

	public String getWrongFiles() {
		return wrongFiles;
	}

	public void setWrongFiles(String wrongFiles) {
		this.wrongFiles = wrongFiles;
	}

	public int getImageOpacitySelected() {
		return imageOpacitySelected;
	}

	public void setImageOpacitySelected(int imageOpacitySelected) {
		this.imageOpacitySelected = imageOpacitySelected;
	}

	public int getScriptureTextOpacitySelected() {
		return scriptureTextOpacitySelected;
	}

	public void setScriptureTextOpacitySelected(int scriptureTextOpacitySelected) {
		this.scriptureTextOpacitySelected = scriptureTextOpacitySelected;
	}

	public String getCheckBoxScriptureTextLabel() {
		return checkBoxScriptureTextLabel;
	}

	public void setCheckBoxScriptureTextLabel(String checkBoxScriptureTextLabel) {
		this.checkBoxScriptureTextLabel = checkBoxScriptureTextLabel;
	}

	public String getCheckBoxPictureLabel() {
		return checkBoxPictureLabel;
	}

	public void setCheckBoxPictureLabel(String checkBoxPictureLabel) {
		this.checkBoxPictureLabel = checkBoxPictureLabel;
	}

	public String getCheckBoxUserMessageLabel() {
		return checkBoxUserMessageLabel;
	}

	public void setCheckBoxUserMessageLabel(String checkBoxUserMessageLabel) {
		this.checkBoxUserMessageLabel = checkBoxUserMessageLabel;
	}

	public TreeSet<Integer> getScriptureTextOpacityList() {
		return scriptureTextOpacityList;
	}

	public TreeSet<Integer> getPictureOpacityList() {
		return pictureOpacityList;
	}

	public String getPopupInformation() {
		return popupInformation;
	}

	public void setPopupInformation(String popupInformation) {
		this.popupInformation = popupInformation;
	}

	public boolean isOpenPopup() {
		return openPopup;
	}

	public void setOpenPopup(boolean openPopup) {
		this.openPopup = openPopup;
	}

	public String getPopupId() {
		return popupId;
	}

	public void setPopupId(String popupId) {
		this.popupId = popupId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getDefaultPopupImageLabel() {
		return defaultPopupImageLabel;
	}

	public void setDefaultPopupImageLabel(String defaultPopupImageLabel) {
		this.defaultPopupImageLabel = defaultPopupImageLabel;
	}

	public boolean isDefaultPopupImageSelected() {
		return defaultPopupImageSelected;
	}

	public void setDefaultPopupImageSelected(boolean defaultPopupImageSelected) {
		this.defaultPopupImageSelected = defaultPopupImageSelected;
	}

	public String getFontFamilyPopupSelected() {
		return fontFamilyPopupSelected;
	}

	public void setFontFamilyPopupSelected(String fontFamilyPopupSelected) {
		this.fontFamilyPopupSelected = fontFamilyPopupSelected;
	}

	public String getFontStylePopupSelected() {
		return fontStylePopupSelected;
	}

	public void setFontStylePopupSelected(String fontStylePopupSelected) {
		this.fontStylePopupSelected = fontStylePopupSelected;
	}

	public TreeSet<String> getFontFamilyPopupList() {
		return fontFamilyPopupList;
	}

	public TreeSet<String> getFontStylePopupList() {
		return fontStylePopupList;
	}

	public TreeSet<Integer> getLetterSpacingPopupList() {
		return letterSpacingPopupList;
	}

	public TreeSet<Integer> getLineHeightPopupList() {
		return lineHeightPopupList;
	}

	public TreeSet<Integer> getWordSpacingPopupList() {
		return wordSpacingPopupList;
	}

	public int getLetterSpacingPopupSelected() {
		return letterSpacingPopupSelected;
	}

	public void setLetterSpacingPopupSelected(int letterSpacingPopupSelected) {
		this.letterSpacingPopupSelected = letterSpacingPopupSelected;
	}

	public int getLineHeightPopupSelected() {
		return lineHeightPopupSelected;
	}

	public void setLineHeightPopupSelected(int lineHeightPopupSelected) {
		this.lineHeightPopupSelected = lineHeightPopupSelected;
	}

	public int getWordSpacingPopupSelected() {
		return wordSpacingPopupSelected;
	}

	public void setWordSpacingPopupSelected(int wordSpacingPopupSelected) {
		this.wordSpacingPopupSelected = wordSpacingPopupSelected;
	}

	public String getFontFamilyPopupLabel() {
		return fontFamilyPopupLabel;
	}

	public void setFontFamilyPopupLabel(String fontFamilyPopupLabel) {
		this.fontFamilyPopupLabel = fontFamilyPopupLabel;
	}

	public String getFontStylePopupLabel() {
		return fontStylePopupLabel;
	}

	public void setFontStylePopupLabel(String fontStylePopupLabel) {
		this.fontStylePopupLabel = fontStylePopupLabel;
	}

	public String getLetterSpacingPopupLabel() {
		return letterSpacingPopupLabel;
	}

	public void setLetterSpacingPopupLabel(String letterSpacingPopupLabel) {
		this.letterSpacingPopupLabel = letterSpacingPopupLabel;
	}

	public String getLineHeightPopupLabel() {
		return lineHeightPopupLabel;
	}

	public void setLineHeightPopupLabel(String lineHeightPopupLabel) {
		this.lineHeightPopupLabel = lineHeightPopupLabel;
	}

	public String getWordSpacingPopupLabel() {
		return wordSpacingPopupLabel;
	}

	public void setWordSpacingPopupLabel(String wordSpacingPopupLabel) {
		this.wordSpacingPopupLabel = wordSpacingPopupLabel;
	}

	public String getScreensaver() {
		return screensaver;
	}

	public void setScreensaver(String screensaver) {
		this.screensaver = screensaver;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getBoldLabel() {
		return boldLabel;
	}

	public void setBoldLabel(String boldLabel) {
		this.boldLabel = boldLabel;
	}

	public String getFontLabel() {
		return fontLabel;
	}

	public void setFontLabel(String fontLabel) {
		this.fontLabel = fontLabel;
	}

	public String getImageOpacityLabel() {
		return imageOpacityLabel;
	}

	public void setImageOpacityLabel(String imageOpacityLabel) {
		this.imageOpacityLabel = imageOpacityLabel;
	}

	public String getTextOpacityLabel() {
		return textOpacityLabel;
	}

	public void setTextOpacityLabel(String textOpacityLabel) {
		this.textOpacityLabel = textOpacityLabel;
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