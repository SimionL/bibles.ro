package beans;

import java.util.LinkedHashMap;
import java.util.TreeSet;

public class Reference {

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
	private transient String error;
	private transient String referenceAlignmentLabel;
	private transient String enterUpPopupLabel;
	private transient String displayVersionLabel;
	private transient String displayBookLabel;
	private transient String enterDownPopupLabel;
	private transient String displayChapterLabel;
	private transient String fontLabel;
	private transient String displayVerseLabel;
	private transient String boldLabel;
	private transient String referenceFontStylePopupLabel;
	private transient String referenceWordsSpacePopupLabel;
	private transient String referenceLetterSpacingPopupLabel;
	private transient String referenceFontDecorationPopupLabel;
	private transient String referenceFontFamilyPopupLabel;
	private transient String schemaLabel;

	private transient String referenceFontStylePopupSelected = "normal";
	private transient String referenceAlignPopupSelected = "center";
	private transient String referenceFontDecorationPopupSelected = "none";
	private transient String referenceFontFamilyPopupSelected = "Times New Roman";
	private transient String referenceColorPopupPaletteSelected_1 = "#000000";
	private transient String referenceColorPopupPaletteSelected_2 = "#ffffff";
	private transient String popupInformation;
	private transient String popupId;
	private transient String identifiedWords = "";
	private transient String languageCode;

	private transient boolean referencePopupBoldSelected = true;
	private transient boolean referenceDisplayVerseSelected = true;
	private transient boolean referenceDisplayVersionSelected;
	private transient boolean referenceDisplayBookSelected = true;
	private transient boolean referenceDisplayChapterSelected = true;
	private transient boolean openPopup;
	private transient boolean usingVoice;

	private transient int referenceSizePopupSelected = 110;
	private transient int referenceEnterUpPopupSelected = 0;
	private transient int referenceEnterDownPopupSelected = 1;
	private transient int referenceVersionSpaceSelected = 1;
	private transient int referenceLetterSpacingPopupSelected = 0;
	private transient int referenceBookSpaceSelected = 1;
	private transient int referenceChapterSpaceSelected = 0;
	private transient int referenceDotsSpaceSelected = 0;
	private transient int referenceParamSelected_1 = 0;
	private transient int referenceParamSelected_2 = 0;
	private transient int referenceParamSelected_3 = 0;
	private transient int referenceParamSelected_4 = 0;
	private transient int referenceParamSelected_5 = 0;
	private transient int referenceParamSelected_6 = 0;

	private final TreeSet<Integer> referenceEnterUpPopupList = new TreeSet<>();
	private final TreeSet<Integer> referenceEnterDownPopupList = new TreeSet<>();
	private final TreeSet<Integer> referenceSizePopupList = new TreeSet<>();
	private final LinkedHashMap<String, String> referenceAlignPopupMap = new LinkedHashMap<>();
	private final TreeSet<String> referenceFontStylePopupList = new TreeSet<>();
	private final TreeSet<Integer> referenceVersionSpaceList = new TreeSet<>();
	private final TreeSet<Integer> referenceLetterSpacingPopupList = new TreeSet<>();
	private final TreeSet<Integer> referenceBookSpaceList = new TreeSet<>();
	private final TreeSet<String> referenceFontDecorationPopupList = new TreeSet<>();
	private final TreeSet<Integer> referenceChapterSpaceList = new TreeSet<>();
	private final TreeSet<String> referenceFontFamilyPopupList = new TreeSet<>();
	private final TreeSet<Integer> referenceDotsSpaceList = new TreeSet<>();
	private final TreeSet<Integer> referenceParamList_1 = new TreeSet<>();
	private final TreeSet<Integer> referenceParamList_2 = new TreeSet<>();
	private final TreeSet<Integer> referenceParamList_3 = new TreeSet<>();
	private final TreeSet<Integer> referenceParamList_4 = new TreeSet<>();
	private final TreeSet<Integer> referenceParamList_5 = new TreeSet<>();
	private final TreeSet<Integer> referenceParamList_6 = new TreeSet<>();

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
	public String getPopup() {
		return popup;
	}
	public void setPopup(String popup) {
		this.popup = popup;
	}
	public String getChurch() {
		return church;
	}
	public void setChurch(String church) {
		this.church = church;
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
	public String getReferenceAlignmentLabel() {
		return referenceAlignmentLabel;
	}
	public void setReferenceAlignmentLabel(String referenceAlignmentLabel) {
		this.referenceAlignmentLabel = referenceAlignmentLabel;
	}
	public String getEnterUpPopupLabel() {
		return enterUpPopupLabel;
	}
	public void setEnterUpPopupLabel(String enterUpPopupLabel) {
		this.enterUpPopupLabel = enterUpPopupLabel;
	}
	public String getDisplayVersionLabel() {
		return displayVersionLabel;
	}
	public void setDisplayVersionLabel(String displayVersionLabel) {
		this.displayVersionLabel = displayVersionLabel;
	}
	public String getDisplayBookLabel() {
		return displayBookLabel;
	}
	public void setDisplayBookLabel(String displayBookLabel) {
		this.displayBookLabel = displayBookLabel;
	}
	public String getEnterDownPopupLabel() {
		return enterDownPopupLabel;
	}
	public void setEnterDownPopupLabel(String enterDownPopupLabel) {
		this.enterDownPopupLabel = enterDownPopupLabel;
	}
	public String getDisplayChapterLabel() {
		return displayChapterLabel;
	}
	public void setDisplayChapterLabel(String displayChapterLabel) {
		this.displayChapterLabel = displayChapterLabel;
	}
	public String getFontLabel() {
		return fontLabel;
	}
	public void setFontLabel(String fontLabel) {
		this.fontLabel = fontLabel;
	}
	public String getDisplayVerseLabel() {
		return displayVerseLabel;
	}
	public void setDisplayVerseLabel(String displayVerseLabel) {
		this.displayVerseLabel = displayVerseLabel;
	}
	public String getBoldLabel() {
		return boldLabel;
	}
	public void setBoldLabel(String boldLabel) {
		this.boldLabel = boldLabel;
	}
	public String getReferenceFontStylePopupLabel() {
		return referenceFontStylePopupLabel;
	}
	public void setReferenceFontStylePopupLabel(String referenceFontStylePopupLabel) {
		this.referenceFontStylePopupLabel = referenceFontStylePopupLabel;
	}
	public String getReferenceWordsSpacePopupLabel() {
		return referenceWordsSpacePopupLabel;
	}
	public void setReferenceWordsSpacePopupLabel(String referenceWordsSpacePopupLabel) {
		this.referenceWordsSpacePopupLabel = referenceWordsSpacePopupLabel;
	}
	public String getReferenceLetterSpacingPopupLabel() {
		return referenceLetterSpacingPopupLabel;
	}
	public void setReferenceLetterSpacingPopupLabel(String referenceLetterSpacingPopupLabel) {
		this.referenceLetterSpacingPopupLabel = referenceLetterSpacingPopupLabel;
	}
	public String getReferenceFontDecorationPopupLabel() {
		return referenceFontDecorationPopupLabel;
	}
	public void setReferenceFontDecorationPopupLabel(String referenceFontDecorationPopupLabel) {
		this.referenceFontDecorationPopupLabel = referenceFontDecorationPopupLabel;
	}
	public String getReferenceFontFamilyPopupLabel() {
		return referenceFontFamilyPopupLabel;
	}
	public void setReferenceFontFamilyPopupLabel(String referenceFontFamilyPopupLabel) {
		this.referenceFontFamilyPopupLabel = referenceFontFamilyPopupLabel;
	}
	public String getSchemaLabel() {
		return schemaLabel;
	}
	public void setSchemaLabel(String schemaLabel) {
		this.schemaLabel = schemaLabel;
	}
	public String getReferenceFontStylePopupSelected() {
		return referenceFontStylePopupSelected;
	}
	public void setReferenceFontStylePopupSelected(String referenceFontStylePopupSelected) {
		this.referenceFontStylePopupSelected = referenceFontStylePopupSelected;
	}
	public String getReferenceAlignPopupSelected() {
		return referenceAlignPopupSelected;
	}
	public void setReferenceAlignPopupSelected(String referenceAlignPopupSelected) {
		this.referenceAlignPopupSelected = referenceAlignPopupSelected;
	}
	public String getReferenceFontDecorationPopupSelected() {
		return referenceFontDecorationPopupSelected;
	}
	public void setReferenceFontDecorationPopupSelected(String referenceFontDecorationPopupSelected) {
		this.referenceFontDecorationPopupSelected = referenceFontDecorationPopupSelected;
	}
	public String getReferenceFontFamilyPopupSelected() {
		return referenceFontFamilyPopupSelected;
	}
	public void setReferenceFontFamilyPopupSelected(String referenceFontFamilyPopupSelected) {
		this.referenceFontFamilyPopupSelected = referenceFontFamilyPopupSelected;
	}
	public String getReferenceColorPopupPaletteSelected_1() {
		return referenceColorPopupPaletteSelected_1;
	}
	public void setReferenceColorPopupPaletteSelected_1(String referenceColorPopupPaletteSelected_1) {
		this.referenceColorPopupPaletteSelected_1 = referenceColorPopupPaletteSelected_1;
	}
	public String getReferenceColorPopupPaletteSelected_2() {
		return referenceColorPopupPaletteSelected_2;
	}
	public void setReferenceColorPopupPaletteSelected_2(String referenceColorPopupPaletteSelected_2) {
		this.referenceColorPopupPaletteSelected_2 = referenceColorPopupPaletteSelected_2;
	}
	public boolean isReferencePopupBoldSelected() {
		return referencePopupBoldSelected;
	}
	public void setReferencePopupBoldSelected(boolean referencePopupBoldSelected) {
		this.referencePopupBoldSelected = referencePopupBoldSelected;
	}
	public boolean isReferenceDisplayVerseSelected() {
		return referenceDisplayVerseSelected;
	}
	public void setReferenceDisplayVerseSelected(boolean referenceDisplayVerseSelected) {
		this.referenceDisplayVerseSelected = referenceDisplayVerseSelected;
	}
	public boolean isReferenceDisplayVersionSelected() {
		return referenceDisplayVersionSelected;
	}
	public void setReferenceDisplayVersionSelected(boolean referenceDisplayVersionSelected) {
		this.referenceDisplayVersionSelected = referenceDisplayVersionSelected;
	}
	public boolean isReferenceDisplayBookSelected() {
		return referenceDisplayBookSelected;
	}
	public void setReferenceDisplayBookSelected(boolean referenceDisplayBookSelected) {
		this.referenceDisplayBookSelected = referenceDisplayBookSelected;
	}
	public boolean isReferenceDisplayChapterSelected() {
		return referenceDisplayChapterSelected;
	}
	public void setReferenceDisplayChapterSelected(boolean referenceDisplayChapterSelected) {
		this.referenceDisplayChapterSelected = referenceDisplayChapterSelected;
	}
	public int getReferenceSizePopupSelected() {
		return referenceSizePopupSelected;
	}
	public void setReferenceSizePopupSelected(int referenceSizePopupSelected) {
		this.referenceSizePopupSelected = referenceSizePopupSelected;
	}
	public int getReferenceEnterUpPopupSelected() {
		return referenceEnterUpPopupSelected;
	}
	public void setReferenceEnterUpPopupSelected(int referenceEnterUpPopupSelected) {
		this.referenceEnterUpPopupSelected = referenceEnterUpPopupSelected;
	}
	public int getReferenceEnterDownPopupSelected() {
		return referenceEnterDownPopupSelected;
	}
	public void setReferenceEnterDownPopupSelected(int referenceEnterDownPopupSelected) {
		this.referenceEnterDownPopupSelected = referenceEnterDownPopupSelected;
	}
	public int getReferenceVersionSpaceSelected() {
		return referenceVersionSpaceSelected;
	}
	public void setReferenceVersionSpaceSelected(int referenceVersionSpaceSelected) {
		this.referenceVersionSpaceSelected = referenceVersionSpaceSelected;
	}
	public int getReferenceLetterSpacingPopupSelected() {
		return referenceLetterSpacingPopupSelected;
	}
	public void setReferenceLetterSpacingPopupSelected(int referenceLetterSpacingPopupSelected) {
		this.referenceLetterSpacingPopupSelected = referenceLetterSpacingPopupSelected;
	}
	public int getReferenceBookSpaceSelected() {
		return referenceBookSpaceSelected;
	}
	public void setReferenceBookSpaceSelected(int referenceBookSpaceSelected) {
		this.referenceBookSpaceSelected = referenceBookSpaceSelected;
	}
	public int getReferenceChapterSpaceSelected() {
		return referenceChapterSpaceSelected;
	}
	public void setReferenceChapterSpaceSelected(int referenceChapterSpaceSelected) {
		this.referenceChapterSpaceSelected = referenceChapterSpaceSelected;
	}
	public int getReferenceDotsSpaceSelected() {
		return referenceDotsSpaceSelected;
	}
	public void setReferenceDotsSpaceSelected(int referenceDotsSpaceSelected) {
		this.referenceDotsSpaceSelected = referenceDotsSpaceSelected;
	}
	public int getReferenceParamSelected_1() {
		return referenceParamSelected_1;
	}
	public void setReferenceParamSelected_1(int referenceParamSelected_1) {
		this.referenceParamSelected_1 = referenceParamSelected_1;
	}
	public int getReferenceParamSelected_2() {
		return referenceParamSelected_2;
	}
	public void setReferenceParamSelected_2(int referenceParamSelected_2) {
		this.referenceParamSelected_2 = referenceParamSelected_2;
	}
	public int getReferenceParamSelected_3() {
		return referenceParamSelected_3;
	}
	public void setReferenceParamSelected_3(int referenceParamSelected_3) {
		this.referenceParamSelected_3 = referenceParamSelected_3;
	}
	public int getReferenceParamSelected_4() {
		return referenceParamSelected_4;
	}
	public void setReferenceParamSelected_4(int referenceParamSelected_4) {
		this.referenceParamSelected_4 = referenceParamSelected_4;
	}
	public int getReferenceParamSelected_5() {
		return referenceParamSelected_5;
	}
	public void setReferenceParamSelected_5(int referenceParamSelected_5) {
		this.referenceParamSelected_5 = referenceParamSelected_5;
	}
	public int getReferenceParamSelected_6() {
		return referenceParamSelected_6;
	}
	public void setReferenceParamSelected_6(int referenceParamSelected_6) {
		this.referenceParamSelected_6 = referenceParamSelected_6;
	}
	public TreeSet<Integer> getReferenceEnterUpPopupList() {
		return referenceEnterUpPopupList;
	}
	public TreeSet<Integer> getReferenceEnterDownPopupList() {
		return referenceEnterDownPopupList;
	}
	public TreeSet<Integer> getReferenceSizePopupList() {
		return referenceSizePopupList;
	}
	public LinkedHashMap<String, String> getReferenceAlignPopupMap() {
		return referenceAlignPopupMap;
	}
	public TreeSet<String> getReferenceFontStylePopupList() {
		return referenceFontStylePopupList;
	}
	public TreeSet<Integer> getReferenceVersionSpaceList() {
		return referenceVersionSpaceList;
	}
	public TreeSet<Integer> getReferenceLetterSpacingPopupList() {
		return referenceLetterSpacingPopupList;
	}
	public TreeSet<Integer> getReferenceBookSpaceList() {
		return referenceBookSpaceList;
	}
	public TreeSet<String> getReferenceFontDecorationPopupList() {
		return referenceFontDecorationPopupList;
	}
	public TreeSet<Integer> getReferenceChapterSpaceList() {
		return referenceChapterSpaceList;
	}
	public TreeSet<String> getReferenceFontFamilyPopupList() {
		return referenceFontFamilyPopupList;
	}
	public TreeSet<Integer> getReferenceDotsSpaceList() {
		return referenceDotsSpaceList;
	}
	public TreeSet<Integer> getReferenceParamList_1() {
		return referenceParamList_1;
	}
	public TreeSet<Integer> getReferenceParamList_2() {
		return referenceParamList_2;
	}
	public TreeSet<Integer> getReferenceParamList_3() {
		return referenceParamList_3;
	}
	public TreeSet<Integer> getReferenceParamList_4() {
		return referenceParamList_4;
	}
	public TreeSet<Integer> getReferenceParamList_5() {
		return referenceParamList_5;
	}
	public TreeSet<Integer> getReferenceParamList_6() {
		return referenceParamList_6;
	}
	public boolean isOpenPopup() {
		return openPopup;
	}
	public void setOpenPopup(boolean openPopup) {
		this.openPopup = openPopup;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getPopupInformation() {
		return popupInformation;
	}
	public void setPopupInformation(String popupInformation) {
		this.popupInformation = popupInformation;
	}
	public String getPopupId() {
		return popupId;
	}
	public void setPopupId(String popupId) {
		this.popupId = popupId;
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