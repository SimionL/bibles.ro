package controller;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Collator;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import utilities.AppBean;
import utilities.Beans;
import utilities.Constants;
import utilities.SearchDetails;
import utilities.Utilities;

@Controller
@Scope("prototype")
public class BibleController {

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

	private final String dbPath = Constants.dbPath.value;
	private final String platformType = Constants.platformType.value;

	@RequestMapping(value = "/be_blessed", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ModelAndView bible(Bible bibleForm, ModelMap model, HttpServletRequest request, HttpServletResponse response){

		try{

			if(request != null){

				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");

				if(bible != null && settings != null && church != null){
					Utilities utilities = new Utilities();
					bible.setError(null);
					bible.setIdentifiedWords(null);

					new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, bibleForm.isOpenPopup());
					utilities.emptySelection(app);

					String eventId = bibleForm.getEventId();

					if(eventId != null && !eventId.trim().isEmpty() && bible != null){

						eventId = eventId.trim();

						switch(eventId){
						case "1" : {
							utilities.setModel(model, null, church, null, null, null, null, null, null, null, null);
							return new ModelAndView("church", model);
						}
						case "2" : {

							String pageName = new Utilities().goToSettingsPage(model, settings, popup, reference, voice, app, null);

							if(pageName != null && !pageName.trim().isEmpty()) {
								return new ModelAndView(pageName, model);
							}

							break;
						}
						case "3" : {
							selectVersionBooks(bibleForm);
							break;
						}
						case "4" : {
							selectBookChapters(bibleForm);
							break;
						}
						case "5" : {
							selectChapterVerses(bibleForm);
							break;
						}
						case "6" : {
							displaySelectedVerse(bibleForm);
							break;
						}
						case "7" : {
							processSearchVerse(bibleForm);
							break;
						}
						case "8" : {
							selectBackVerse(bibleForm);
							break;
						}
						case "9" : {
							selectNextVerse(bibleForm);
							break;
						}
						case "10" : {
							//long a = System.currentTimeMillis();
							processSearchText(bibleForm, null);
							//System.out.println("timp total: " + (System.currentTimeMillis() - a));
							break;
						}
						case "11" : {
							displayHistory(bibleForm);
							break;
						}
						case "12" : {
							utilities.setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
							return new ModelAndView("screensaver", model);
						}
						case "13" : {
							utilities.setModel(model, null, null, null, null, null, null, code, null, null, null);
							return new ModelAndView("code", model);
						}
						case "14" : {
							utilities.setModel(model, null, null, null, null, null, null, null, null, null, feedback);
							return new ModelAndView("feedback", model);
						}
						case "15" : {

							ModelAndView result = automaticOperations(bibleForm, model);

							if(result != null){
								return result;
							}
							break;
						}
						case "16" : {
							utilities.setModel(model, null, null, null, null, null, null, null, voice, null, null);
							return new ModelAndView("voice", model);
						}
						case "17" : {
							utilities.setModel(model, null, null, null, null, null, null, null, null, thankYou, null);
							return new ModelAndView("thankYou", model);
						}
						}

						bibleForm.setEventId(null);
						bible.setEventId(null);
					}

					setHistory();
					setVerseValue();

					String verseValue = app.getVerseValue();

					if(verseValue == null || verseValue.trim().isEmpty()){
						utilities.setText(bible, settings, popup, app);
					}

					utilities.setPopupText(bible, church, reference, settings, popup, screensaver, app, code, Beans.bible);

					final LinkedHashMap<String, Map<String, String>> audioValue = bible.getVerseValue();

					String audio = "";

					if(audioValue != null && !audioValue.isEmpty()) {
						for(String verse : audioValue.keySet()) {
							if(verse != null && !verse.trim().isEmpty()) {
								audio += verse.trim() + " ";
							}
						}
					}

					if(audio != null && !audio.trim().isEmpty()) {

						while (audio.contains("<") && audio.contains(">")){

							audio = audio.substring(0, audio.indexOf("<")) + audio.substring(audio.indexOf(">") + 1, audio.length());
						}

						Set<String> deleteList = new HashSet<>(); 

						deleteList.add("<");
						deleteList.add(">");
						deleteList.add("\"");
						deleteList.add("\r\n");
						deleteList.add("\\r\\n");
						deleteList.add("\r");
						deleteList.add("\\r");
						deleteList.add("\n");
						deleteList.add("\\n");

						for(String del : deleteList){
							if(del != null && !del.isEmpty()){
								audio = audio.replace(del, "");
							}
						}

						bible.setTheSpokenWords(audio);
					}
					else {
						bible.setTheSpokenWords(null);
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		new Utilities().setModel(model, bible, null, null, null, null, null, null, null, null, null);
		return new ModelAndView("bible", model);
	}

	private void automaticSearchByReference(final Bible bibleForm){

		try{
			final String identifiedWords = bibleForm.getIdentifiedWords();

			if(identifiedWords != null && !identifiedWords.trim().isEmpty() && identifiedWords.contains(";")){

				bibleForm.setSearchVerse(identifiedWords.replace(" cu ", " ").replace(";", " "));

				processSearchVerse(bibleForm);
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private boolean isBibleRequest(Bible bibleForm) {

		try{

			if(bible != null && bibleForm != null) {

				String command = bibleForm.getIdentifiedWords();

				if(command != null && !command.trim().isEmpty()) {

					LinkedHashSet<String> versions = bible.getVersions();

					if(versions != null && !versions.isEmpty()) {
						for(String version : versions) {
							if(contain(command, version)) {
								bibleForm.setSelectedVersion(version);
								return true;
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		return false;
	}

	private boolean isBookRequest(Bible bibleForm) {

		try{

			if(bible != null && bibleForm != null) {

				String command = bibleForm.getIdentifiedWords();

				if(command != null && !command.trim().isEmpty()) {

					command = command.replace(";", "").trim();

					String select = Normalizer.normalize(bible.getSelectBook(), Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "");

					if(command.contains(select)){
						command = command.replace(select, "").trim();
					}

					LinkedHashSet<String> books = bible.getBooks();

					if(books != null && !books.isEmpty()) {
						for(String book : books) {
							if(command.equalsIgnoreCase(book)) {
								bibleForm.setSelectedBook(book);
								return true;
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		return false;
	}

	private boolean isChapterRequest(Bible bibleForm) {

		try{

			if(bible != null && bibleForm != null) {

				String command = bibleForm.getIdentifiedWords();

				if(command != null && !command.trim().isEmpty()) {

					command = command.replace(";", "").trim();

					String select = Normalizer.normalize(bible.getSelectChapter(), Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "");

					if(command.contains(select)){
						command = command.replace(select, "").trim();
					}

					LinkedHashSet<String> chapters = bible.getChapters();

					if(chapters != null && !chapters.isEmpty()) {
						for(String chapter : chapters) {
							if(command.equalsIgnoreCase(chapter)) {
								bibleForm.setSelectedChapter(chapter);
								return true;
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		return false;
	}

	private boolean isVerseRequest(Bible bibleForm) {

		try{

			if(bible != null && bibleForm != null) {

				String command = bibleForm.getIdentifiedWords();

				if(command != null && !command.trim().isEmpty()) {

					command = command.replace(";", "").trim();

					String select = Normalizer.normalize(bible.getSelectVerse(), Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "");

					if(command.contains(select)){
						command = command.replace(select, "").trim();
					}

					LinkedHashSet<String> verses = bible.getVerses();

					if(verses != null && !verses.isEmpty()) {
						for(String verse : verses) {
							if(command.equalsIgnoreCase(verse)) {
								bibleForm.setSelectedVerse(verse);
								return true;
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		return false;
	}

	private ModelAndView automaticOperations(final Bible bibleForm, ModelMap model){

		try{

			if(bible.isUsingVoice()){

				String theSpokenWords = bibleForm.getIdentifiedWords();

				if(theSpokenWords != null && !theSpokenWords.trim().isEmpty()){

					if(!theSpokenWords.contains(" ") && !theSpokenWords.contains("	")){

						ModelAndView result = new Utilities().navigateByVoice(voice, bible, settings, popup, reference, church, screensaver, code, app, model, theSpokenWords);

						if(result != null){
							return result;
						}
					}

					if(isBibleRequest(bibleForm)) {
						selectVersionBooks(bibleForm);
						return null;
					}
					else if(isBookRequest(bibleForm)) {
						selectBookChapters(bibleForm);
						return null;
					}
					else if(isChapterRequest(bibleForm)) {
						selectChapterVerses(bibleForm);
						return null;
					}
					else if(isVerseRequest(bibleForm)) {
						displaySelectedVerse(bibleForm);
						return null;
					}
					else {

						Map<String, Map<String, String>> verseValue = bible.getVerseValue();

						if(verseValue != null){

							verseValue.clear();

							automaticSearchByReference(bibleForm);

							if(verseValue.isEmpty() ||
									verseValue.containsKey(bible.getNoResult()) ||	
									verseValue.containsKey(bible.getNoNextVerse()) ||			
									verseValue.containsKey(bible.getNoBackVerse()) ||	
									verseValue.containsKey(bible.getNoVerseSelected())
									){
								automaticSearchByText(bibleForm);	
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		return null;
	}

	private void automaticSearchByText(final Bible bibleForm){

		try{
			List<String> synWordsList = Collections.synchronizedList(new ArrayList<String>());

			if(synWordsList != null){

				final String identifiedWords = bibleForm.getIdentifiedWords();

				if(identifiedWords != null && !identifiedWords.trim().isEmpty() && identifiedWords.contains(";")){

					bibleForm.setSearchText(identifiedWords.replace(";", " "));

					final int finalSize = processSearchText(bibleForm, synWordsList);

					if(finalSize != synWordsList.size() && !synWordsList.isEmpty()){
						processSearchText(bibleForm, synWordsList);
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setVerseValue(){

		try{
			if(app != null){

				LinkedHashMap<String, String> versesMap = app.getVersesMap();

				if(versesMap != null && !versesMap.isEmpty()){

					String results = "";

					if(versesMap.size() == 1){

						for(String referenceText : versesMap.keySet()){
							if(referenceText != null && !referenceText.trim().isEmpty()){
								String verse = versesMap.get(referenceText);
								if(verse != null && !verse.trim().isEmpty()){

									if(referenceText.contains("referenceDiv")){
										results += referenceText;
									}
									else{
										results += new Utilities().setDivSettings(popup, reference, "referenceDiv", referenceText, false);
									}

									if(verse.contains("scriptureTextDiv")){
										results += verse;
									}
									else{
										results += new Utilities().setDivSettings(popup, reference, "scriptureTextDiv", verse.trim(), true);
									}
								}
							}
						}
					}
					else{
						for(String reference : versesMap.keySet()){
							if(reference != null && !reference.trim().isEmpty()){
								String verse = versesMap.get(reference);
								if(verse != null && !verse.trim().isEmpty()){
									results += reference + "<br><br>";
									results += verse + "<br><br>";
								}
							}
						}
					}

					app.setVerseValue(results);
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void displayHistory(Bible bibleForm){

		try{

			String selectedHistory = bibleForm.getHistory();

			if(selectedHistory != null && !selectedHistory.trim().isEmpty() && !selectedHistory.trim().equalsIgnoreCase(bible.getSearchHistory())){

				Map<String, String> historyMap = bible.getHistoryMap();

				if(historyMap != null && historyMap.containsValue(selectedHistory)){

					if(selectedHistory.contains("==;==")){
						String[] arrayHistory = selectedHistory.split("==;==");
						if(arrayHistory != null && arrayHistory.length == 4){
							bibleForm.setSelectedVersion(arrayHistory[0]);
							bibleForm.setSelectedBook(arrayHistory[1]);
							bibleForm.setSelectedChapter(arrayHistory[2]);
							bibleForm.setSelectedVerse(arrayHistory[3]);
							displaySelectedVerse(bibleForm);
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void setHistory(){

		try{
			if(bible != null){

				String selectedVerse = bible.getSelectedVerse();

				if(selectedVerse != null && selectedVerse.trim().matches("\\d+")){
					String selectedVersion = bible.getSelectedVersion();
					String selectedBook = bible.getSelectedBook();
					String selectedChapter = bible.getSelectedChapter();

					Map<String, String> historyMap = bible.getHistoryMap();

					if(historyMap != null){
						String historyMapKey = selectedVersion + " " + selectedBook + " " + selectedChapter + " " + selectedVerse;
						String historyMapValue = selectedVersion + "==;==" + selectedBook + "==;==" + selectedChapter + "==;==" + selectedVerse;

						if(historyMapKey != null && historyMapValue != null){
							historyMap.put(historyMapKey, historyMapValue);
						}
					}
				}
			}
		}

		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private LinkedHashSet<String> getWords(String[] searchedWords){

		LinkedHashSet<String> words = new LinkedHashSet<>();

		try{
			if(searchedWords != null && searchedWords.length > 0){

				HashSet<String> set = new HashSet<>(Arrays.asList(searchedWords));

				String maxWord = null;
				int maxLen = 0;
				while(!set.isEmpty()){
					for(String word : set){
						int wL = word.trim().length();
						if(wL >= maxLen){
							maxWord = word;
							maxLen = wL;
						}
					}
					if(maxWord != null){
						words.add(maxWord.trim());
						set.remove(maxWord);
					}
					maxWord = null;
					maxLen = 0;
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
		return words;
	}

	private int processSearchText(Bible bibleForm, List<String> synWordsList){

		int wordsN = 0;

		try{//long b = System.currentTimeMillis();
			bible.setSearchTextAvailable(true);
			if(bible != null && bibleForm != null){

				String selectedVersion = bibleForm.getSelectedVersion();
				if(new Utilities().isRealVersion(selectedVersion, bible)){

					String searchText = bibleForm.getSearchText();

					if(searchText != null && !searchText.trim().isEmpty()){

						searchText = searchText.trim();

						if(searchText.contains("	")){
							searchText = searchText.replace("	", " ");
						}

						String[] searchedWords = searchText.split(" ");

						if(searchedWords != null && searchedWords.length > 0){

							LinkedHashSet<String> words = getWords(searchedWords);

							if(words != null && !words.isEmpty()){

								if(synWordsList != null){
									if(synWordsList.isEmpty()){
										synWordsList.addAll(words);
										wordsN = synWordsList.size();
									}
									else{
										for(String deleteW : synWordsList){
											words.remove(deleteW);
										}
									}
								}

								if(words != null && !words.isEmpty()){

									int searchBlockLength = Integer.parseInt(settings.getSearchBlockLengthSelection());

									if(searchBlockLength > 0){

										TreeMap<Integer, LinkedList<StringBuilder>> resultsMap = new TreeMap<>();
										TreeMap<Integer, LinkedList<StringBuilder>> colorResultsMap = new TreeMap<>();

										ArrayList<SearchDetails> details =  new ArrayList<SearchDetails>();

										searchVersionText(selectedVersion, resultsMap, searchBlockLength, settings.getSearchLevelSelected(), details, colorResultsMap, words, synWordsList);
										//System.out.println("threadurile au cautat " + (System.currentTimeMillis() - b) + " milisecunde");
										setResults(resultsMap, colorResultsMap, details);
									}
								}
							}
						}
					}
				}
				else{
					bible.setError(bible.getSelectVersion() + "!");
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
		return wordsN;
	}

	private void setResults(TreeMap<Integer, LinkedList<StringBuilder>> resultsMap, TreeMap<Integer, LinkedList<StringBuilder>> colorResultsMap, ArrayList<SearchDetails> searchDetails){

		try{//long g = System.currentTimeMillis();

			if(bible != null){

				Map<String, Map<String, String>> verseValue = bible.getVerseValue();

				if(verseValue != null){
					verseValue.clear();

					StringBuilder formResult = concatResults(resultsMap);

					if(formResult != null && formResult.length() == 0){
						bible.setCurentDisplay(bible.getNoResult());
						verseValue.put(bible.getNoResult(), null);
					}
					else {

						String nonColorSearchedText = formResult.toString();

						bible.setNonColorSearchedText(nonColorSearchedText);
						bible.setCurentDisplay(null);
						String unHighlightedText = concatResults(colorResultsMap).toString();
						bible.setUnHighlightedText(unHighlightedText);

						if(!settings.isHighlights()){
							verseValue.put(nonColorSearchedText, null);
						}
						else if(unHighlightedText != null){

							String exactColorSelected = settings.getExactColorPaletteSelected();
							String inexactColorSelected = settings.getInexactColorPaletteSelected();

							String highlightedText = unHighlightedText.replaceAll("_@@@@@@_", exactColorSelected).replaceAll("_!!!!!!_", inexactColorSelected);
							verseValue.put(highlightedText, null);
						}

						if(Integer.parseInt(settings.getSearchBlockLengthSelection()) == 1 && searchDetails != null && searchDetails.size() == 1){
							Utilities utilities = new Utilities();
							for(SearchDetails sD : searchDetails){
								if(sD != null){
									LinkedList<String> verseList = sD.getVerseList();
									if(verseList != null && verseList.size() == 1){
										String verseN = verseList.getFirst();
										if(utilities.isRealVerse(verseN, bible) && utilities.isSetSelectedVerse(bible, sD.getVersion(), sD.getBook(), sD.getChapter(), verseN)){
											String verseV = utilities.getVerse(sD.getVersion(), sD.getBook(), sD.getChapter(), verseN, bible);
											if(verseV != null && !verseV.trim().isEmpty()){
												while(verseV.startsWith("&nbsp;")){
													verseV = verseV.substring(verseV.indexOf("&nbsp;") + "&nbsp;".length());
												}
												if(verseV != null && !verseV.trim().isEmpty()){
													addVerseIntoMap(bible.getSelectedVersion(), bible.getSelectedBook(), bible.getSelectedChapter(), bible.getSelectedVerse(), verseV);
												}
											}
										}
									}
									break;
								}
							}
						}
					}
				}
			}
			//System.out.println("prelucrarea rezultatelor: " + (System.currentTimeMillis() - g) + " milisecunde");
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void cleanLastSearch(){

		try{
			if(bible != null){
				bible.setUnHighlightedText(null);
				bible.setNonColorSearchedText(null);
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private StringBuilder concatResults(TreeMap<Integer, LinkedList<StringBuilder>> map){

		StringBuilder results = new StringBuilder();

		try{
			if(map != null && !map.isEmpty()){
				for(Integer bookN : map.keySet()){
					if(bookN > 0){
						LinkedList<StringBuilder> list = map.get(bookN);
						if(list != null && !list.isEmpty()){
							for(StringBuilder res : list){
								if(res != null && res.length() > 0){
									results.append(res);	
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
		return results;
	}

	private boolean isBookMapsSet(String version, String selectedBook, LinkedList<StringBuilder> result, LinkedList<StringBuilder> colorResults, TreeMap<Integer, LinkedList<StringBuilder>> resultsMap, TreeMap<Integer, LinkedList<StringBuilder>> colorResultsMap){

		try{
			if(resultsMap != null && colorResultsMap != null && result != null && colorResults != null){

				int bookNumber = new Utilities().getBookNumber(version, selectedBook);

				if(bookNumber > 0){
					resultsMap.put(bookNumber, result);
					colorResultsMap.put(bookNumber, colorResults);
					return true;
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
		return false;
	}

	private void searchVersionText(String version, TreeMap<Integer, LinkedList<StringBuilder>> resultsMap, final int searchBlockLength, final int searchLevel, ArrayList<SearchDetails> details, TreeMap<Integer, LinkedList<StringBuilder>> colorResultsMap, LinkedHashSet<String> words, List<String> synWordsList){

		try{

			if(version != null && bible != null){

				switch(searchLevel) {
				case 1 :{
					searchSelectedVersion(version, resultsMap, colorResultsMap, details,  words, searchBlockLength, synWordsList);
					break;
				}
				case 2 :{
					searchSelectedBook(version, resultsMap, colorResultsMap, details, words, searchBlockLength, synWordsList);
					break;
				}
				case 3 :{
					searchSelectedChapter(version, resultsMap, colorResultsMap, details, words, searchBlockLength, synWordsList);
					break;
				}
				case 4 :{
					searchCurrentSelection(version, resultsMap, colorResultsMap, details, words, searchBlockLength, synWordsList);
					break;
				}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void searchSelectedVersion(String version, TreeMap<Integer, LinkedList<StringBuilder>> resultsMap, TreeMap<Integer, LinkedList<StringBuilder>> colorResultsMap, ArrayList<SearchDetails> details, LinkedHashSet<String> words, final int searchBlockLength, List<String> synWordsList){

		try {
			LinkedList<String> books = new Utilities().getVersionBooks(version, true);
			if(books != null && !books.isEmpty()){
				books.parallelStream().forEach(book-> {
					if(book != null){

						LinkedList<StringBuilder> result = new LinkedList<>(); 
						LinkedList<StringBuilder> colorResults = new LinkedList<>();

						if(isBookMapsSet(version, book, result, colorResults, resultsMap, colorResultsMap)){

							TreeSet<Integer> chapters = new Utilities().getBookChapters(version, book);
							if(chapters != null && !chapters.isEmpty()){
								chapters.forEach(c-> {searchChapterText(version, book, c + "", words, result, searchBlockLength, details, colorResults, synWordsList);});	
							}
						}
					}
				});
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void searchSelectedChapter(String version, TreeMap<Integer, LinkedList<StringBuilder>> resultsMap, TreeMap<Integer, LinkedList<StringBuilder>> colorResultsMap, ArrayList<SearchDetails> details, LinkedHashSet<String> words, final int searchBlockLength, List<String> synWordsList){

		try {
			String selectedBook = bible.getSelectedBook();
			if(new Utilities().isRealBook(selectedBook, bible)){
				String realBook = getRealBook(version, selectedBook);

				if(realBook != null && !realBook.trim().isEmpty()){

					LinkedList<StringBuilder> result = new LinkedList<>(); 
					LinkedList<StringBuilder> colorResults = new LinkedList<>();

					if(isBookMapsSet(version, selectedBook, result, colorResults, resultsMap, colorResultsMap)){

						String selectedChapter = bible.getSelectedChapter();
						if(selectedChapter != null && selectedChapter.trim().matches("\\d+")){
							searchChapterText(version, realBook, selectedChapter, words, result, searchBlockLength, details, colorResults, synWordsList);
						}
						else{
							bible.setError(bible.getSelectChapter() + "!");
						}
					}
				}
				else{
					bible.setError(bible.getSelectBook() + "!");
				}
			}
			else{
				bible.setError(bible.getSelectBook() + "!");
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void searchSelectedBook(String version, TreeMap<Integer, LinkedList<StringBuilder>> resultsMap, TreeMap<Integer, LinkedList<StringBuilder>> colorResultsMap, ArrayList<SearchDetails> details, LinkedHashSet<String> words, final int searchBlockLength, List<String> synWordsList){

		try {
			String selectedBook = bible.getSelectedBook();
			if(new Utilities().isRealBook(selectedBook, bible)){
				String realBook = getRealBook(version, selectedBook);

				if(realBook != null && !realBook.trim().isEmpty()){

					LinkedList<StringBuilder> result = new LinkedList<>(); 
					LinkedList<StringBuilder> colorResults = new LinkedList<>();

					if(isBookMapsSet(version, selectedBook, result, colorResults, resultsMap, colorResultsMap)){
						TreeSet<Integer> chapters = new Utilities().getBookChapters(version, realBook);
						if(chapters != null && !chapters.isEmpty()){
							chapters.forEach(c-> {searchChapterText(version, realBook, c + "", words, result, searchBlockLength, details, colorResults, synWordsList);});	
						}
					}
				}
				else{
					bible.setError(bible.getSelectBook() + "!");
				}
			}
			else{
				bible.setError(bible.getSelectBook() + "!");
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void searchCurrentSelection(String version, TreeMap<Integer, LinkedList<StringBuilder>> resultsMap, TreeMap<Integer, LinkedList<StringBuilder>> colorResultsMap, ArrayList<SearchDetails> details, LinkedHashSet<String> words, final int searchBlockLength, List<String> synWordsList){

		try {
			Utilities utilities = new Utilities();
			String selectedBook = bible.getSelectedBook();
			if(utilities.isRealBook(selectedBook, bible)){
				String realBook = getRealBook(version, selectedBook);

				if(realBook != null && !realBook.trim().isEmpty()){

					LinkedList<StringBuilder> result = new LinkedList<>(); 
					LinkedList<StringBuilder> colorResults = new LinkedList<>();

					if(isBookMapsSet(version, selectedBook, result, colorResults, resultsMap, colorResultsMap)){
						searchBookText(version, realBook, result, searchBlockLength, details, colorResults, words, synWordsList);
					}
				}
			}
			else{
				LinkedList<String> books = utilities.getVersionBooks(version, true);
				if(books != null && !books.isEmpty()){
					books.parallelStream().forEach(b-> {
						if(b != null){

							LinkedList<StringBuilder> result = new LinkedList<>(); 
							LinkedList<StringBuilder> colorResults = new LinkedList<>();

							if(isBookMapsSet(version, b, result, colorResults, resultsMap, colorResultsMap)){
								searchBookText(version, b, result, searchBlockLength, details, colorResults, words, synWordsList);
							}
						}
					});
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void searchBookText(String version, String book, LinkedList<StringBuilder> result, int searchBlockLength, ArrayList<SearchDetails> details, LinkedList<StringBuilder> colorResults, LinkedHashSet<String> words, List<String> synWordsList){

		try{
			if(book != null && bible != null){
				String selectedChapter = bible.getSelectedChapter();
				if(selectedChapter != null && selectedChapter.trim().matches("\\d+")){
					searchChapterText(version, book, selectedChapter, words, result, searchBlockLength, details, colorResults, synWordsList);
				}
				else{
					TreeSet<Integer> chapters = new Utilities().getBookChapters(version, book);
					if(chapters != null && !chapters.isEmpty()){
						chapters.forEach(c-> {searchChapterText(version, book, c + "", words, result, searchBlockLength, details, colorResults, synWordsList);});	
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void searchChapterText(String version, String book, String chapter, LinkedHashSet<String> allWords, LinkedList<StringBuilder> results, int searchBlockLength, ArrayList<SearchDetails> sDetails, LinkedList<StringBuilder> colorResults, List<String> synWordsList){

		try{
			if(version != null && !version.trim().isEmpty() && book != null && !book.trim().isEmpty() && chapter != null && !chapter.trim().isEmpty() && allWords != null && !allWords.isEmpty()){

				LinkedHashSet<String> words = new LinkedHashSet<>();

				for(String word : allWords){
					words.add(word.trim());
				}

				StringBuilder result = new StringBuilder();
				StringBuilder colorResult = new StringBuilder();

				results.add(result);
				colorResults.add(colorResult);

				String filePath = dbPath + version + platformType + book + platformType + chapter + ".txt";
				String title = "\n" + version + " " + book.substring(book.indexOf("_") + 1) + " " + chapter;

				SearchDetails searchDetails = new SearchDetails();

				if(searchBlockLength == 1){
					searchDetails.setVersion(version);
					searchDetails.setBook(book);
					searchDetails.setChapter(chapter);
				}

				if(title != null && !title.trim().isEmpty() && filePath != null && !filePath.trim().isEmpty()){

					String text = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

					if(text != null && !text.trim().isEmpty()){

						boolean containWord = true;

						if(synWordsList == null){
							for(String w : words){
								if(!contain(text, w)){
									containWord = false;
									break;
								}
							}
						}
						else{
							for(String w : words){
								if(!contain(text, w)){
									containWord = false;
								}
								else{
									synWordsList.remove(w);
								}
							}
						}

						if(containWord){
							String[] verses = text.split("<br>");
							if(verses != null && verses.length > 0){
								if(searchBlockLength == 1){
									boolean addPath = true; 
									for(String verse : verses){
										if(verse != null && !verse.trim().isEmpty() && verse.contains(".") && !verse.contains("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp")){
											boolean validVerse = true;
											for(String w : words){
												if(!contain(verse, w)){
													validVerse = false;
													break;
												}
											}

											if(validVerse){

												if(addPath){
													result.append(title + "<br>");
													addPath = false;
												}
												result.append(verse + "<br>");
												if(searchDetails != null){
													searchDetails.getVerseList().add(verse.substring(0, verse.indexOf(".")));
												}
											}
										}
									}
								}
								else{

									Map<Integer, String> chapterMap = new HashMap<Integer, String>();
									int key = 0;
									for(String verse : verses){
										if(verse != null && !verse.trim().isEmpty() && !verse.contains("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp")){

											chapterMap.put(key++, verse);
										}
									}
									if(chapterMap != null && !chapterMap.isEmpty()){

										int radiusCount = 0;
										boolean contain = false;
										boolean	endCondition = true;

										Map<String, String> wordsMap = new HashMap<String, String>();

										words.forEach(wr -> {if(wr != null && !wr.trim().isEmpty())wordsMap.put(wr, "false");});

										boolean addPath = true;
										for (int i = 0 ; i < chapterMap.size(); i++){
											for (int j = i ; j < chapterMap.size(); j++){
												if(endCondition){
													++radiusCount;
													contain = false;
													for (String word : wordsMap.keySet()){
														if(contain(chapterMap.get(j), word)){
															wordsMap.put(word, "true");
															contain = true;
														}
													}
													if (!contain){
														radiusCount = 0;
														for (String w : wordsMap.keySet())wordsMap.put(w, "false");
														break;
													}
													else if(radiusCount == searchBlockLength){
														if (wordsMap.containsValue("false")){
															for (String wor : wordsMap.keySet())wordsMap.put(wor, "false");
															radiusCount = 0;
															break;
														}
														else if (!wordsMap.containsValue("false")){
															for (String wo : wordsMap.keySet())wordsMap.put(wo, "false");
															radiusCount = 0;
															for (int k = i ; k <= j ; k++){

																if(addPath){
																	result.append(title + "<br>");
																	addPath = false;
																}

																String identification = chapterMap.get(k);

																if(identification != null && !identification.trim().isEmpty()){
																	result.append(identification + "<br>");
																}
															}
															break;
														}
													}
													if (j == (chapterMap.size() - 1))endCondition = false;
												}
											}
										}
									}
								}
							}

							if(result != null && result.length() > 0){

								if(searchBlockLength == 1 && searchDetails != null && !searchDetails.getVerseList().isEmpty()){
									sDetails.add(searchDetails);
								}

								if(words != null && !words.isEmpty() && result != null && result.length() > 0){
									colorResult.append(result);

									Map <Integer, String[]> formIndex = new TreeMap<>(Collections.reverseOrder());

									for(String w : words){
										if(w != null && !w.trim().isEmpty()){

											Map<Integer, String[]> occurrences = getFormOccurrences(result, w.trim());

											if(occurrences != null && !occurrences.isEmpty()){
												formIndex.putAll(occurrences);
											}
										}
									}

									if(formIndex != null && !formIndex.isEmpty()){

										Map<Integer, Integer> exactOccurMap = setExactOccurMap(formIndex);

										for(Integer index : formIndex.keySet()){
											if(index > 0){
												String[] details = formIndex.get(index);
												if(details != null && details.length == 2 && details[1] != null){
													if("0".equals(details[0])){
														colorResult.replace(index, index + details[1].length(),"<span style='color: _@@@@@@_ ;'>" + result.subSequence(index, index + details[1].length()) + "</span>");
													}
													else if(canAddOccur(exactOccurMap, index)){
														colorResult.replace(index, index + details[1].length(),"<span style='color: _!!!!!!_ ;'>" + result.subSequence(index, index + details[1].length()) + "</span>");
													}
												}
											}
										}
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

	private Map<Integer, Integer> setExactOccurMap(Map <Integer, String[]> formIndex) {

		Map<Integer, Integer> exactOccurMap = new HashMap<>();

		try {

			if(formIndex != null) {

				for(Integer index : formIndex.keySet()){
					if(index > 0){
						String[] details = formIndex.get(index);
						if(details != null && details.length == 2 && details[1] != null && "0".equals(details[0])){
							exactOccurMap.put(index, index + details[1].length());
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		return exactOccurMap;
	}

	private boolean canAddOccur(Map<Integer, Integer> exactOccurMap, int index) {

		try {
			if(exactOccurMap != null && !exactOccurMap.isEmpty()) {
				for(Integer startIndex : exactOccurMap.keySet()) {
					if(startIndex <= index && index <= exactOccurMap.get(startIndex)) {
						return false;
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
		return true;
	}

	private Map<Integer, String[]> getFormOccurrences(StringBuilder entireText, String word){

		Map<Integer, String[]> formIndex = new HashMap<>();

		try{
			if(entireText != null && entireText.length() > 0 && word != null && !word.trim().isEmpty()){
				Collator comparator = Collator.getInstance();
				if(comparator != null){
					comparator.setStrength(Collator.PRIMARY);
					String localWord = word.trim();
					String localBigString = entireText.toString();

					int localBigWordLength = localBigString.length();

					for(int i = 0 ; i <= localBigWordLength ;i++){
						int end = i + localWord.length();
						if(end <= localBigWordLength){

							if(comparator.compare(localBigString.substring(i, end), localWord) == 0){

								boolean left = false;
								boolean right = false;

								if(i==0){
									left = true;
								}
								else{
									if(localBigString.substring(i - 1, i).chars().allMatch(Character::isLetter)){
										left = false;
									}
									else{
										left = true;
									}
								}

								if(i== localBigWordLength - 1 || localBigWordLength == end){
									right = true;
								}
								else{
									if(localBigString.substring(end, end + 1).chars().allMatch(Character::isLetter)){
										right = false;
									}
									else{
										right = true;
									}
								}

								if(left && right){
									String[] details = {"0", word};
									formIndex.put(i, details);
								}
								else{
									String[] details = {"1", word};
									formIndex.put(i, details);
								}
							}
						}
						else{
							break;
						}
					}
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
		}
		return formIndex;
	}

	private boolean contain(String entireString, String word){
		try{
			if(entireString != null && !entireString.trim().isEmpty() && word != null && !word.trim().isEmpty()){
				entireString = entireString.trim();
				if(entireString.contains("	")){
					entireString = entireString.replace("	", " ");
				}
				String[] array = entireString.split(" ");
				if(array != null && array.length > 0){
					Collator comparator = Collator.getInstance();
					if(comparator != null){
						String localWord = word.trim();
						comparator.setStrength(Collator.PRIMARY);
						ArrayList<String> history = new ArrayList<>();
						for(String aW : array){
							if(aW != null && !aW.trim().isEmpty()){

								String w = aW.trim();

								if(!history.contains(w)){

									history.add(w);

									int bigWordLength = w.length();
									int smallWordLength = localWord.length();

									if(smallWordLength <= bigWordLength){
										for(int i = 0 ; i < bigWordLength;i++){
											int end = i + smallWordLength;
											if(end <= bigWordLength){
												if(comparator.compare(w.substring(i, end), localWord) == 0){
													return true;
												}
											}
											else{
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
			return false;
		}
		return false;
	}

	private void selectBackVerse(Bible bibleForm){
		try{
			bible.setSearchTextAvailable(false);
			if(bible != null){
				cleanLastSearch();
				String selectedVerse = bibleForm.getSelectedVerse();
				if(selectedVerse != null){
					if(selectedVerse.trim().matches("\\d+")){

						int backVerse = Integer.parseInt(selectedVerse) - 1;

						if(backVerse > 0){
							bible.setSelectedVerse(backVerse + "");
							bibleForm.setSelectedVerse(backVerse + "");
							displaySelectedVerse(bibleForm);
						}
						else {

							bible.setSelectedVerse(selectedVerse);
							bibleForm.setSelectedVerse(selectedVerse);

							Map<String, Map<String, String>> verseValue = bible.getVerseValue();

							if(verseValue != null){
								bible.setSelectedVerse(bible.getSelectVerse());
								bibleForm.setSelectedVerse(bible.getSelectVerse());
								verseValue.clear();
								verseValue.put(bible.getNoBackVerse(), null);
								bible.setCurentDisplay(bible.getNoBackVerse());
							}
						}
					}
					else if(selectedVerse.equalsIgnoreCase(bible.getSelectVerse())){
						LinkedHashSet<String> verses = bible.getVerses();
						if(verses != null && !verses.isEmpty()){
							int verseN = verses.size() - 1;
							if(verseN > 0){
								bible.setSelectedVerse(verseN + "");
								bibleForm.setSelectedVerse(verseN + "");
								if(!displaySelectedVerse(bibleForm)){
									bible.setSelectedVerse(bible.getSelectVerse());
									bibleForm.setSelectedVerse(bible.getSelectVerse());
								}
							}
						}
					}
					else{
						Map<String, Map<String, String>> verseValue = bible.getVerseValue();

						if(verseValue != null){
							verseValue.clear();
							verseValue.put(bible.getNoVerseSelected(), null);
							bible.setCurentDisplay(bible.getNoVerseSelected());	
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void selectNextVerse(Bible bibleForm){

		try{
			bible.setSearchTextAvailable(false);
			if(bible != null){
				cleanLastSearch();
				String selectedVerse = bibleForm.getSelectedVerse();
				if(selectedVerse != null){
					selectedVerse = selectedVerse.trim();
					if(selectedVerse.matches("\\d+")){

						int nextVerse = Integer.parseInt(selectedVerse) + 1;

						bible.setSelectedVerse(nextVerse + "");
						bibleForm.setSelectedVerse(nextVerse + "");

						if(!displaySelectedVerse(bibleForm)){

							bible.setSelectedVerse(bible.getSelectVerse());
							bibleForm.setSelectedVerse(bible.getSelectVerse());

							Map<String, Map<String, String>> verseValue = bible.getVerseValue();

							if(verseValue != null){
								verseValue.clear();
								verseValue.put(bible.getNoNextVerse(), null);
								bible.setCurentDisplay(bible.getNoNextVerse());
							}
						}
					}
					else if(selectedVerse.equalsIgnoreCase(bible.getSelectVerse())){
						bible.setSelectedVerse("1");
						bibleForm.setSelectedVerse("1");
						displaySelectedVerse(bibleForm);
					}
					else{
						Map<String, Map<String, String>> verseValue = bible.getVerseValue();

						if(verseValue != null){
							verseValue.clear();
							verseValue.put(bible.getNoVerseSelected(), null);
							bible.setCurentDisplay(bible.getNoVerseSelected());
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void processSearchVerse(Bible bibleForm){

		try{
			bible.setSearchTextAvailable(false);
			if(bible != null && bibleForm != null){
				cleanLastSearch();
				bible.setSearchText(null);
				String selectedVersion = bibleForm.getSelectedVersion();
				String selectedBook =    bibleForm.getSelectedBook();
				String selectedChapter = bibleForm.getSelectedChapter();
				String searchVerse = bibleForm.getSearchVerse();

				boolean uniqueVerse = false;

				if(searchVerse != null && !searchVerse.trim().isEmpty()){

					String[] userRequest = searchVerse.trim().split(" ");

					if(userRequest != null && userRequest.length > 0){

						LinkedList<String> buffList = new LinkedList<String>(); 

						for(String elemReq : userRequest) {
							if(elemReq != null && !elemReq.trim().isEmpty()){
								buffList.add(elemReq.trim());
							}
						}

						userRequest = buffList.stream().toArray(String[]::new);

						if(userRequest.length == 1){

							userRequest = Arrays.copyOf(userRequest, userRequest.length + 2);

							userRequest[userRequest.length - 2]= "1";
							userRequest[userRequest.length - 1]= "1";
						}
						else{
							String lastElement = userRequest[userRequest.length-1];
							String prewElement = userRequest[userRequest.length-2];

							if(prewElement != null && lastElement != null){

								if(!lastElement.trim().matches("\\d+")){

									userRequest = Arrays.copyOf(userRequest, userRequest.length + 2);

									userRequest[userRequest.length - 2]= "1";
									userRequest[userRequest.length - 1]= "1";
								}
								else if(!prewElement.trim().matches("\\d+") && lastElement.trim().matches("\\d+")){

									userRequest = Arrays.copyOf(userRequest, userRequest.length + 1);
									userRequest[userRequest.length - 1]= "1";
								}
							}
						}

						TreeSet<String> versionsSet = new TreeSet<>();

						if(new Utilities().isRealVersion(selectedVersion, bible)){
							versionsSet.add(selectedVersion);
						}
						else{
							versionsSet = getVersions(selectedVersion);
						}

						if(userRequest.length == 1){
							uniqueVerse = searchVerseRequest(versionsSet, getBooks(versionsSet, selectedBook), selectedChapter, userRequest[0]);
						}
						else if(userRequest.length == 2){
							uniqueVerse = searchVerseRequest(versionsSet, getBooks(versionsSet, selectedBook), userRequest[0], userRequest[1]);
						}
						else if(userRequest.length == 3){
							uniqueVerse = searchVerseRequest(versionsSet, getBooks(versionsSet, userRequest[0]), userRequest[1], userRequest[2]);
						}
						else if(userRequest.length == 4){

							String firstRequest = userRequest[0];
							String secondRequest = userRequest[1];
							String thirdRequest = userRequest[2];
							String fourthRequest = userRequest[3];

							if(firstRequest != null && secondRequest != null && thirdRequest != null && fourthRequest != null){

								if(firstRequest.trim().matches("\\d+") && 
										!secondRequest.trim().matches("\\d+") &&
										thirdRequest.trim().matches("\\d+") && 
										fourthRequest.trim().matches("\\d+")
										){
									uniqueVerse = searchVerseRequest(versionsSet, getBooks(versionsSet, firstRequest + " " + secondRequest), thirdRequest, fourthRequest);
								}
								else{
									TreeSet<String> userVersionsSet = getVersions(firstRequest);

									uniqueVerse = searchVerseRequest(userVersionsSet, getBooks(userVersionsSet, secondRequest), thirdRequest, fourthRequest);
								}
							}
						}
					}
				}

				if(!uniqueVerse){
					selectBooks(bibleForm, false);
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private TreeSet<String> getVersions(String smallVersionName){

		TreeSet<String> selectedVersions = new TreeSet<>();

		try{
			TreeSet<String> versions = new Utilities().getAllVersionsName(dbPath);

			if(versions != null && !versions.isEmpty()){
				if(smallVersionName == null || smallVersionName.trim().isEmpty() || smallVersionName.trim().equals(bible.getSelectVersion()) || smallVersionName.trim().equals(bible.getNoVersion())){
					return versions;
				}
				else{
					for(String version : versions){
						if(version != null && !version.trim().isEmpty() && version.trim().toUpperCase().startsWith(smallVersionName.trim().toUpperCase())){
							selectedVersions.add(version.trim());
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
		return selectedVersions;
	}

	private TreeMap<Integer, String> getChapterVersesMap(String chapterPath){

		TreeMap<Integer, String> versesMap = new TreeMap<>();

		try{
			String entireChapter = new String(Files.readAllBytes(Paths.get(chapterPath)), StandardCharsets.UTF_8);
			if(entireChapter != null && !entireChapter.trim().isEmpty()){
				String[] verses = entireChapter.split("<br>");
				if(verses != null && verses.length > 0){
					for(String verse : verses){
						if(verse != null && !verse.trim().isEmpty() && 
								!verse.contains("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp") && 
								verse.contains(".")
								){
							String vN = verse.substring(0, verse.indexOf("."));
							if(vN != null && vN.trim().matches("\\d+")){
								int verseN = Integer.parseInt(vN.trim());
								String verseText = verse.substring(verse.indexOf(".") + 1);
								if(verseN > 0 && verseText != null && !verseText.trim().isEmpty()){
									versesMap.put(verseN, verseText.trim().replace("&nbsp;", "").trim());
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
		return versesMap;
	}

	private String getVerseReferences(String version, String book, String chapter, String verseN){
		try{
			Utilities utilities = new Utilities();
			if(utilities.isRealVersion(version, bible) && 
					book != null && !book.trim().isEmpty() &&
					chapter != null && chapter.trim().matches("\\d+") &&
					verseN != null && verseN.trim().matches("\\d+")){

				String referencePath = dbPath + platformType + version + platformType + book + platformType + chapter + ".txt";
				File referenceFile = utilities.getFile(referencePath);
				if(referenceFile != null && referenceFile.isFile()){
					String entireChapter = new String(Files.readAllBytes(Paths.get(referencePath)), StandardCharsets.UTF_8);
					if(entireChapter != null && !entireChapter.trim().isEmpty() && entireChapter.contains("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp")){
						String[] verses = entireChapter.split("<br>");
						if(verses != null && verses.length > 0){
							String lastVerse = "";
							for (String verse : verses){
								if(verse != null && !verse.trim().isEmpty()){
									if(verse.contains("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp") && lastVerse.trim().equals(verseN.trim())){
										return verse.replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp", "");
									}
									else if(verse.trim().startsWith(verseN.trim() + ".")){
										lastVerse = verseN.trim();
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
		return null;
	}

	private String getRealBook(String version, String bookName){
		try{
			if(bookName != null && !bookName.trim().isEmpty()){
				File checkDbPath = new Utilities().getFile(dbPath + platformType + version);
				if(checkDbPath != null && checkDbPath.isDirectory()){
					File[] files = checkDbPath.listFiles();
					if(files != null && files.length > 0){
						for(File file : files){
							if(file != null && file.isDirectory()){
								String name = file.getName();
								if(name != null && !name.trim().isEmpty() && name.contains("_") && name.substring(name.indexOf("_") + 1, name.length()).equals(bookName)){
									return name;
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
		return null;
	}

	private boolean bookContainChapter(String version, String bookName, String chapterNo){
		try{
			if(version != null && !version.trim().isEmpty() && bookName != null && !bookName.trim().isEmpty() && chapterNo != null && !chapterNo.trim().isEmpty() && chapterNo.trim().matches("\\d+")){
				File bookPath = new Utilities().getFile(dbPath + platformType + version + platformType + bookName);
				if(bookPath != null && bookPath.isDirectory()){
					File[] chapters = bookPath.listFiles();
					if(chapters != null && chapters.length > 0){
						for(File chapter : chapters){
							if(chapter != null){
								String chapterN = chapter.getName();
								if(chapterN != null && !chapterN.trim().isEmpty() && 
										chapterN.contains(".") &&
										chapterN.trim().substring(0, chapterN.trim().indexOf(".")).trim().equals(chapterNo.trim())){
									return true;
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
		return false;
	}

	private Set<String> getBooks(TreeSet<String> versions, String smallBookName){

		Set<String> selectedBooks = new HashSet<>();

		try{

			if(versions != null && !versions.isEmpty() && smallBookName != null && !smallBookName.trim().isEmpty() && bible != null){
				Collator comparator = Collator.getInstance();
				if(comparator != null){
					comparator.setStrength(Collator.PRIMARY);
				}
				for(String versionRequest : versions){
					if(versionRequest != null){
						LinkedList<String> bookRequest = new Utilities().getVersionBooks(versionRequest, true);
						if(bookRequest != null && !bookRequest.isEmpty()){
							for(String bookName: bookRequest){

								if(smallBookName.trim().equals(bible.getSelectBook().trim()) || smallBookName.trim().equals(bible.getNoBook().trim())){
									selectedBooks.add(bookName.trim());
								}
								else{

									String entireBookName = bookName.trim().toUpperCase().substring(bookName.trim().indexOf("_") + 1);
									String userBookName = smallBookName.trim().toUpperCase();

									if(entireBookName != null && !entireBookName.trim().isEmpty() && userBookName != null && !userBookName.trim().isEmpty()){
										entireBookName = entireBookName.trim();
										userBookName = userBookName.trim();
										if(entireBookName.contains("	")){
											entireBookName = entireBookName.replace("	", " ");
										}

										int bigWordLength = entireBookName.length();
										int smallWordLength = userBookName.length();
										if(smallWordLength <= bigWordLength){

											if(comparator.compare(entireBookName.substring(0, smallWordLength), userBookName) == 0){
												selectedBooks.add(bookName.trim());
											}
										}
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
		return selectedBooks;
	}

	private boolean listContain(Set<String> list, String word){

		try{
			if(list != null && !list.isEmpty()){
				ArrayList<String> history = new ArrayList<>();
				for(String bookName : list){
					if(bookName != null && !bookName.trim().isEmpty() && word != null && !word.trim().isEmpty()){
						bookName = bookName.trim();
						if(bookName.contains("	")){
							bookName = bookName.replace("	", " ");
						}
						Collator comparator = Collator.getInstance();
						if(comparator != null){
							String localWord = word.trim();
							comparator.setStrength(Collator.PRIMARY);
							if(!history.contains(bookName)){
								history.add(bookName);
								int bigWordLength = bookName.length();
								int smallWordLength = localWord.length();
								if(smallWordLength <= bigWordLength){
									for(int i = 0 ; i < bigWordLength;i++){
										int end = i + smallWordLength;
										if(end <= bigWordLength){
											if(comparator.compare(bookName.substring(i, end), localWord) == 0){
												return true;
											}
										}
										else{
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			new Utilities().writeFile(e);
			return false;
		}
		return false;
	}

	private boolean searchVerseRequest(Set<String> versions, Set<String> book, String chapter, String verseN){
		boolean uniqueVerse = false;
		try{
			if(bible != null){
				List<String[]> versionVerseReference = new LinkedList<>();

				String uniqueVersion = null;
				String uniqueAvailableBook = null;
				String uniqueOkChapter = null;
				int foundedVerses = 0;

				if(versions != null && !versions.isEmpty() &&
						book != null && !book.isEmpty() &&	
						chapter != null && !chapter.trim().isEmpty() &&	
						verseN != null && !verseN.trim().isEmpty()){
					Utilities utilities = new Utilities();
					for(String version : versions){
						if(version != null){
							LinkedList<String> booksRequest = utilities.getVersionBooks(version, true);
							if(booksRequest != null && !booksRequest.isEmpty()){
								for(String availableBook: booksRequest){
									if(availableBook != null && listContain(book, availableBook.trim())){
										if(bookContainChapter(version, availableBook, chapter)){
											String verse = utilities.getVerse(version, availableBook, chapter, verseN.trim(), null);
											if(verse != null && availableBook.contains("_")){

												String[] array = new String[3];
												String selectedBook = availableBook.substring(availableBook.trim().indexOf("_") + 1).trim();
												String okChapter = chapter.substring(chapter.indexOf("_") + 1);

												array[0] = version;
												array[1] = version + " " + selectedBook + " " + okChapter + "." + verseN.trim() + ":<br>" + verse;
												array[2] = getVerseReferences(version, availableBook, chapter, verseN);

												versionVerseReference.add(array);

												addVerseIntoMap(version, selectedBook, okChapter, verseN, verse);

												++foundedVerses;

												uniqueVersion = version;
												uniqueAvailableBook = availableBook;
												uniqueOkChapter = okChapter;
											}
										}
									}
								}
							}
						}
					}
				}

				if(versions.size() == 1 && foundedVerses == 1 && uniqueOkChapter != null && uniqueOkChapter.matches("\\d+") && verseN != null && verseN.matches("\\d+")){
					uniqueVerse = new Utilities().isSetSelectedVerse(bible, uniqueVersion, uniqueAvailableBook, uniqueOkChapter, verseN);
				}

				if(versionVerseReference != null){

					if(versionVerseReference.isEmpty()){

						Map<String, Map<String, String>> verseValue = bible.getVerseValue();

						if(verseValue != null){
							verseValue.clear();
							verseValue.put(bible.getNoResult(), null);
							bible.setCurentDisplay(bible.getNoResult());
						}
					}
					else{
						setVerseReferences(versionVerseReference);
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
		return uniqueVerse;
	}

	private void selectBooks(Bible bibleForm, boolean clearContent){

		try{
			if(bible != null && bibleForm != null){
				cleanLastSearch();
				Utilities utilities = new Utilities();
				LinkedHashSet<String> booksSet = utilities.getList(bible.getSelectBook());
				LinkedHashSet<String> chaptersSet = utilities.getList(bible.getNoChapter());
				LinkedHashSet<String> versesSet = utilities.getList(bible.getNoVerse());

				String selectedVersion = bibleForm.getSelectedVersion();
				if(selectedVersion != null && !selectedVersion.trim().isEmpty() && 
						(!selectedVersion.trim().equals(bible.getSelectVersion())) &&
						(!selectedVersion.trim().equals(bible.getNoVersion())) 	
						){

					LinkedList<String> versionBooks = utilities.getVersionBooks(selectedVersion.trim(), false);

					if(versionBooks != null && !versionBooks.isEmpty()){
						for(String book : versionBooks){
							booksSet.add(book);
						}
					}
				}

				bible.setSelectedVersion(selectedVersion.trim());
				bible.setSelectedBook(null);
				bible.setSelectedChapter(null);
				bible.setSelectedVerse(null);

				if(booksSet.size() == 1){
					bible.setBooks(utilities.getList(bible.getNoBook()));
				}
				else{
					bible.setBooks(booksSet);
				}

				bible.setChapters(chaptersSet);
				bible.setVerses(versesSet);

				if(clearContent){

					Map<String, Map<String, String>> verseValue = bible.getVerseValue();

					if(verseValue != null){
						verseValue.clear();
						bible.setCurentDisplay("");
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void selectVersionBooks(Bible bibleForm){
		bible.setSearchTextAvailable(false);
		if(!displaySelectedVerse(bibleForm)){
			selectBooks(bibleForm, true);
		}
	}

	private void selectBookChapters(Bible bibleForm){
		bible.setSearchTextAvailable(false);
		if(!displaySelectedVerse(bibleForm)){

			try{
				if(bible != null && bibleForm != null){
					Utilities utilities = new Utilities();
					cleanLastSearch();
					LinkedHashSet<String> chaptersSet = utilities.getList(bible.getSelectChapter());
					LinkedHashSet<String> versesSet = utilities.getList(bible.getNoVerse());

					String selectedVersion = bible.getSelectedVersion().trim();
					String selectedBook = bibleForm.getSelectedBook();

					if(utilities.isRealVersion(selectedVersion, bible) &&
							selectedBook != null && !selectedBook.trim().isEmpty() &&
							(!selectedBook.trim().equals(bible.getSelectBook())) &&
							(!selectedBook.trim().equals(bible.getNoBook()))
							){

						TreeSet<Integer> allChapters = utilities.getBookChapters(selectedVersion, getRealBook(selectedVersion, selectedBook));

						for(Integer ch : allChapters){
							if(ch > 0){
								chaptersSet.add(ch + "");
							}
						}
					}

					bible.setSelectedBook(selectedBook);
					bible.setSelectedChapter(null);
					bible.setSelectedVerse(null);

					if(chaptersSet.size() == 1){
						bible.setChapters(utilities.getList(bible.getNoChapter()));
					}
					else{
						bible.setChapters(chaptersSet);
					}

					bible.setVerses(versesSet);

					Map<String, Map<String, String>> verseValue = bible.getVerseValue();

					if(verseValue != null){
						verseValue.clear();
						bible.setCurentDisplay("");
					}
				}
			}
			catch(Exception e){
				new Utilities().writeFile(e);
			}
		}
	}

	private void selectChapterVerses(Bible bibleForm){
		bible.setSearchTextAvailable(false);
		try{
			if(bible != null && bibleForm != null){

				cleanLastSearch();

				if(!bibleForm.getSelectedVerse().matches("\\d+")){
					displayEntireChapter(bibleForm);
				}
				else{
					displaySelectedVerse(bibleForm);
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void displayEntireChapter(Bible bibleForm){

		try{
			if(bible != null && bibleForm != null){
				String selectedVersion = bibleForm.getSelectedVersion();
				String selectedBook = bibleForm.getSelectedBook();
				String selectedChapter = bibleForm.getSelectedChapter();

				String currentChapter = null;
				Utilities utilities = new Utilities();
				if(utilities.isRealVersion(selectedVersion, bible) &&
						selectedBook != null && 
						!selectedBook.trim().isEmpty() && 
						selectedChapter != null && 
						!selectedChapter.trim().isEmpty()){

					String chapterPath = dbPath + platformType + selectedVersion + platformType + getRealBook(selectedVersion, selectedBook) + platformType + selectedChapter + ".txt";

					File chapterFile = utilities.getFile(chapterPath);
					if(chapterFile != null && chapterFile.isFile()){

						currentChapter = new String(Files.readAllBytes(Paths.get(chapterPath)), StandardCharsets.UTF_8);
					}
				}
				bible.setSelectedChapter(selectedChapter);
				bible.setSelectedVerse(null);

				utilities.setAllVerses(bible, currentChapter);

				Map<String, Map<String, String>> verseValue = bible.getVerseValue();

				if(verseValue != null){

					verseValue.clear();

					if(settings.isDisplayEntireChapter() && bible.getSelectedChapter().matches("\\d+") && currentChapter != null){

						if(!bible.isDisplayReference()){

							String[] chapterArray = currentChapter.split("<br>");

							if(chapterArray != null && chapterArray.length > 0){
								for (String chapterLine : chapterArray){
									if(chapterLine != null && chapterLine.contains("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp")){
										currentChapter = currentChapter.replaceAll(chapterLine + "<br>", "");
									}
								}
							}
						}

						verseValue.put(currentChapter, null);
						bible.setCurentDisplay(currentChapter);
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private void addVerseIntoMap(String version, String book, String chapter, String verseN, String verseValue){

		try{

			if(app != null && bible != null && reference != null){

				LinkedHashMap<String, String> versesMap = app.getVersesMap();

				if(versesMap != null){
					String verseReferences   = new Utilities().getReferenceText(reference, bible, version, book, chapter, verseN.trim());
					versesMap.put(verseReferences, verseValue.trim());
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private boolean displaySelectedVerse(Bible bibleForm){
		bible.setSearchTextAvailable(false);
		boolean verseFound = false;

		try{

			if(bible != null && bibleForm != null){
				cleanLastSearch();
				String selectedVersion = bibleForm.getSelectedVersion();
				String selectedBook = bibleForm.getSelectedBook();
				String selectedChapter = bibleForm.getSelectedChapter();
				String selectedVerse = bibleForm.getSelectedVerse();
				Utilities utilities = new Utilities();
				if(utilities.isRealVersion(selectedVersion, bible) &&
						selectedBook != null    && !selectedBook.trim().isEmpty()    && !selectedBook.equalsIgnoreCase(bible.getSelectBook().trim()) &&	
						selectedChapter != null && selectedChapter.trim().matches("\\d+") &&
						selectedVerse != null   && selectedVerse.trim().matches("\\d+")
						){

					LinkedHashSet<String> allBooks = utilities.getList(bible.getSelectBook());
					LinkedHashSet<String> allChapters = utilities.getList(bible.getSelectChapter());

					String realBook = getRealBook(selectedVersion, selectedBook);
					String verseReferences = null;
					String verseValue = utilities.getVerse(selectedVersion, realBook, selectedChapter, selectedVerse, bible);
					if(verseValue != null && !verseValue.trim().isEmpty()){
						addVerseIntoMap(selectedVersion, selectedBook, selectedChapter, selectedVerse, verseValue);
						verseReferences = getVerseReferences(selectedVersion, realBook, selectedChapter, selectedVerse);
						bible.setSelectedVerse(selectedVerse);
						verseFound = true;
					}

					if(verseFound){
						LinkedList<String> versionBooks = utilities.getVersionBooks(selectedVersion, false);
						TreeSet<Integer> chaptersSet = utilities.getBookChapters(selectedVersion, realBook);

						if(versionBooks != null && chaptersSet != null && versionBooks.contains(selectedBook) && chaptersSet.contains(Integer.parseInt(selectedChapter))){

							if(allBooks != null){
								for(String book : versionBooks){
									allBooks.add(book);
								}
							}
							if(allChapters != null){
								for(Integer ch : chaptersSet){
									allChapters.add(ch + "");
								}
							}

							bible.setSelectedVersion(selectedVersion);
							bible.setSelectedBook(selectedBook);
							bible.setSelectedChapter(selectedChapter);

							bible.setBooks(allBooks);
							bible.setChapters(allChapters);

							List<String[]> versionVerseReference = new LinkedList<>();

							String[] array = new String[3];

							array[0] = selectedVersion;
							array[1] = verseValue;
							array[2] = verseReferences;

							versionVerseReference.add(array);

							setVerseReferences(versionVerseReference);
						}
						else{
							bible.setSelectedVerse(null);	
						}
					}
					else{
						bible.setSelectedVerse(null);	
					}
				}
				else {

					if(selectedVersion != null && selectedVersion.trim().equalsIgnoreCase(bible.getSelectVersion().trim())){

						bible.setSelectedVersion(bible.getSelectVersion().trim());
						bible.setSelectedBook(bible.getNoBook().trim());
						bible.setSelectedChapter(bible.getNoChapter().trim());
						bible.setSelectedVerse(bible.getNoVerse().trim());

						bible.setBooks(utilities.getList(bible.getNoBook().trim()));
						bible.setChapters(utilities.getList(bible.getNoChapter().trim()));
						bible.setVerses(utilities.getList(bible.getNoVerse().trim()));

					}else if(selectedBook != null && selectedBook.trim().equalsIgnoreCase(bible.getSelectBook().trim())){

						bible.setSelectedBook(bible.getSelectBook().trim());
						bible.setSelectedChapter(bible.getNoChapter().trim());
						bible.setSelectedVerse(bible.getNoVerse().trim());

						bible.setChapters(utilities.getList(bible.getNoChapter().trim()));
						bible.setVerses(utilities.getList(bible.getNoVerse().trim()));

					}else if(selectedChapter != null && selectedChapter.trim().equalsIgnoreCase(bible.getSelectChapter().trim())){

						bible.setSelectedChapter(bible.getSelectChapter().trim());
						bible.setSelectedVerse(bible.getNoVerse().trim());

						bible.setVerses(utilities.getList(bible.getNoVerse().trim()));

					}else if(selectedVerse != null && selectedVerse.trim().equalsIgnoreCase(bible.getSelectVerse().trim())){

						bible.setSelectedVerse(bible.getSelectVerse().trim());
						displayEntireChapter(bibleForm);
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		return verseFound;
	}

	private void setVerseReferences(List<String[]> versionVerseReference){

		try{

			if(bible != null && versionVerseReference != null && !versionVerseReference.isEmpty()){

				Map<String, Map<String, String>> verseValue = bible.getVerseValue();

				if(verseValue != null){

					verseValue.clear();

					for(String[] index : versionVerseReference){

						String version = index[0];
						String verseText = index[1]; 
						String verseReferences = index[2];

						if(version != null && !version.trim().isEmpty() && verseText != null && !verseText.trim().isEmpty()){

							Map<String, String> referencesMap = null;

							if(verseReferences != null && !verseReferences.trim().isEmpty()){

								String [] availableReferences = verseReferences.split(";");

								if(availableReferences != null && availableReferences.length > 0){

									referencesMap = new LinkedHashMap<>();

									for(String r : availableReferences){

										if(r != null && !r.trim().isEmpty()){
											referencesMap.putAll(getVerseReferencesMap(version, r.trim()));
										}
									}
								}
							}
							bible.setCurentDisplay(verseText);

							if(referencesMap != null && !referencesMap.isEmpty()){
								verseValue.put(verseText, referencesMap);
							}
							else{
								verseValue.put(verseText, null);
							}
						}
						else{
							verseValue.put(bible.getNoResult(), null);
							bible.setCurentDisplay(bible.getNoResult());
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}
	private Map<String, String> getVerseReferencesMap(String version, String reference){

		Map<String, String> referencesMap = new LinkedHashMap<>();

		try{

			String book = null;
			int chapterValue = -999;
			TreeSet<Integer> verseList = new TreeSet<>();

			if(version != null && !version.trim().isEmpty() && reference != null && !reference.trim().isEmpty()){
				String[] verseArray = reference.split(" ");
				if(verseArray != null && verseArray.length > 0){
					String chapterVerse = null;
					for(String vA : verseArray){
						if(vA != null && !vA.trim().isEmpty()){
							if(vA.contains(".") || vA.contains("-")){
								chapterVerse = vA.trim();
							}
							else{
								book = vA.trim();
							}
						}
					}
					if(book != null && chapterVerse != null && chapterVerse.contains(".")){
						String[] chapterVerseArray = chapterVerse.split("\\.");
						if(chapterVerseArray != null && chapterVerseArray.length == 2){
							String chapter = chapterVerseArray[0];
							String verse = chapterVerseArray[1];
							if(chapter != null && verse != null && chapter.trim().matches("\\d+")){
								chapter = chapter.trim();
								chapterValue = Integer.parseInt(chapter);
								verse = verse.trim();
								if(verse.contains("-")){
									String[] vArray = verse.split("-");
									if(vArray != null && vArray.length == 2){
										String firstVerse = vArray[0];
										String lastVerse = vArray[1];

										if(firstVerse != null && lastVerse != null && firstVerse.trim().matches("\\d+") && lastVerse.trim().matches("\\d+")){
											int first = Integer.parseInt(firstVerse.trim());
											int last = Integer.parseInt(lastVerse.trim());
											int start = 0;
											int end = 0;

											if(first < last){
												start = first;
												end = last;
											}
											else{
												start = last;
												end = first;
											}
											for(int i = start ; i<= end ; i++){
												verseList.add(i);
											}
										}
									}
								}
								else if (verse.matches("\\d+")){
									verseList.add(Integer.parseInt(verse));
								}
							}
						}
					}
				}
			}
			if(version != null && !version.trim().isEmpty() && book != null && !book.trim().isEmpty() && chapterValue > 0 && verseList != null && !verseList.isEmpty()){

				int lastVerseN = verseList.last();
				Utilities utilities = new Utilities();
				LinkedList<String> booksRequest =  utilities.getVersionBooks(version, true);
				if(booksRequest != null && !booksRequest.isEmpty()){
					for(String availableBook: booksRequest){

						if(availableBook != null && availableBook.contains("_")){

							String bookName = availableBook.substring(availableBook.indexOf("_") + 1);

							if(bookName != null && 
									!bookName.trim().isEmpty() && (
											bookName.trim().toUpperCase().startsWith(book.trim().toUpperCase()) || 
											bookName.trim().toUpperCase().equals(book.trim().toUpperCase()) || 
											bookName.replace(" ", "").replace("	", "").trim().toUpperCase().equals(book.trim().toUpperCase()) || 
											bookName.replace(" ", "").replace("	", "").trim().toUpperCase().startsWith(book.trim().toUpperCase())
											)){

								File chaptersPath = utilities.getFile(dbPath + platformType + version + platformType + availableBook);
								if(chaptersPath != null && chaptersPath.isDirectory()){
									File[] chapters = chaptersPath.listFiles();
									if(chapters != null && chapters.length > 0){
										for(File chapter : chapters){
											String chapterName = chapter.getName();
											if(chapter != null && chapterName != null && 
													chapterName.contains(".") && 
													chapterName.trim().substring(0, chapterName.trim().indexOf(".")).equals(chapterValue + "")){
												TreeMap<Integer, String> versesMap = getChapterVersesMap(chapter.getAbsolutePath());
												if(versesMap != null && !versesMap.isEmpty()){
													for(Integer verseN : versesMap.keySet()){
														if(verseList.contains(verseN)){
															referencesMap.put(version + " " + bookName + " " + chapterValue + "." + verseN, versesMap.get(verseN));
															if((verseN.intValue() + "").equals(lastVerseN + "")){
																break;
															}
														}
													}
												}
												break;
											}
										}
									}
								}
								break;
							}
						}
					}
				}

			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		return referencesMap;
	}
}