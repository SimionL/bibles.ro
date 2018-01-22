package controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.TreeMap;

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
import utilities.ApplicationElement;
import utilities.Utilities;

@Controller
@Scope("prototype")
public class ApplicationCodeController {

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

	@RequestMapping(value = "/code", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ModelAndView code(ApplicationCode codeForm, ModelMap model, HttpServletRequest request, HttpServletResponse response){

		try{

			if(request != null){

				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");

				if(bible != null && settings != null && church != null && screensaver != null && code != null){

					new Utilities().cleanPopup(bible, church, settings, popup, reference, screensaver, code, voice, thankYou, feedback, codeForm.isOpenPopup());

					code.setError(null);
					code.setIdentifiedWords(null);

					String eventId = codeForm.getEventId();

					if(eventId != null && !eventId.trim().isEmpty() && bible != null){

						eventId = eventId.trim();

						switch(eventId){
						case "1" : {
							new Utilities().setModel(model, bible, null, null, null, null, null, null, null, null, null);
							return new ModelAndView("bible", model);
						}
						case "2" : {
							new Utilities().setModel(model, null, church, null, null, null, null, null, null, null, null);
							return new ModelAndView("church", model);
						}
						case "3" : {

							String pageName = new Utilities().goToSettingsPage(model, settings, popup, reference, voice, app, null);

							if(pageName != null && !pageName.trim().isEmpty()) {
								return new ModelAndView(pageName, model);
							}

							break;
						}
						case "4" : {
							new Utilities().setModel(model, null, null, null, null, null, screensaver, null, null, null, null);
							return new ModelAndView("screensaver", model);
						}
						case "5" : {
							setFile(codeForm.getSelectedFileValue());
							new Utilities().setModel(model, null, null, null, null, null, null, code, null, null, null);
							return new ModelAndView("code", model);
						}
						case "6" : {
							new Utilities().setModel(model, null, null, null, null, null, null, null, voice, null, null);
							return new ModelAndView("voice", model);
						}
						case "7" : {
							ModelAndView result = executtingVoiceCommand(codeForm, model);
							if(result != null){
								return result;
							}
							break;
						}
						case "8" : {
							new Utilities().setModel(model, null, null, null, null, null, null, null, null, null, feedback);
							return new ModelAndView("feedback", model);
						}
						case "9" : {
							new Utilities().setModel(model, null, null, null, null, null, null, null, null, thankYou, null);
							return new ModelAndView("thankYou", model);
						}
						}
					}
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		new Utilities().setModel(model, null, null, null, null, null, null, code, null, null, null);
		return new ModelAndView("code", model);
	}

	private ModelAndView executtingVoiceCommand(ApplicationCode codeForm, ModelMap model){

		try{

			String theSpokenWords = codeForm.getIdentifiedWords();

			if(theSpokenWords != null && !theSpokenWords.trim().isEmpty()){

				if(!theSpokenWords.contains(" ") && !theSpokenWords.contains("	")){

					return new Utilities().navigateByVoice(voice, bible, settings, popup, reference, church, screensaver, code, app, model, theSpokenWords);
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
		return null;
	}

	private void setFile(int fileId){

		try{
			if(fileId > 0){
				TreeMap<Integer, ApplicationElement> folderMap = app.getFolderMap();

				if(folderMap.containsKey(fileId)){
					ApplicationElement elem = folderMap.get(fileId);
					if(elem != null){
						if(elem.isFile()){
							String path = elem.getPath();

							if(path != null && !path.trim().isEmpty()){

								final String format = getExtension(path);

								if(format != null && !format.trim().isEmpty()){

									switch(format){

									case "jpeg" : {
										setImage(path, format);
										break;
									}
									case "jpg" : {
										setImage(path, format);
										break;
									}
									case "bmp" : {
										setImage(path, format);
										break;
									}
									case "png" : {
										setImage(path, format);
										break;
									}
									case "jsp" : {
										setText(path);
										break;
									}
									case "xml" : {
										setText(path);
										break;
									}
									case "java" : {
										setText(path);
										break;
									}
									case "css" : {
										setText(path);
										break;
									}
									case "js" : {
										setText(path);
										break;
									}
									case "txt" : {
										setText(path);
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
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}

	private String getExtension(String path){

		String extension = "";

		try{

			if(path.contains(".")){

				extension = path.substring(path.lastIndexOf(".") + 1, path.length());

				if(extension != null){
					extension = extension.trim().toLowerCase();
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

		return extension;
	}

	private void setImage(final String path, final String format){

		String result = null;

		try{

			result = "<html> <head>  <style type=\"text/css\"> ";
			result += " .imgDiv { "		        +
					" width: \"11%\"; "         +
					" height:\"11%\"; "         +
					" text-align: center; "     +
					" vertical-align: middle; " +
					" align-items: center; "    +
					" } ";
			result += " </style> </head> <body>";
			result += " <div class=\"imgDiv\">";

			String imageText = null;

			File picture = new File(path);

			if(picture != null && picture.exists() && picture.isFile()){

				byte[] imageBytes = Files.readAllBytes(Paths.get(path));

				if(imageBytes != null && imageBytes.length > 0){

					imageText = Base64.getEncoder().encodeToString(imageBytes);
				}
			}

			if(imageText != null && !imageText.trim().isEmpty() && format != null && !format.trim().isEmpty()){

				result += "<img src=\"data:image/" + format + ";base64," + imageText + "\" width=\"11%\"\" height=\"11%\"/>";
				result += " </div> </body> </html> ";

				code.setPopupInformation(result);
				code.setFileName(picture.getName());
				code.setOpenPopup(true);
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}

	}

	private void setText(final String path){

		try{

			File fileSelected = new File(path);

			if(fileSelected != null && fileSelected.exists() && fileSelected.isFile()){

				String text = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);

				if(text != null && !text.trim().isEmpty()){

					if(text.contains("\r\n")){
						text = text.replace("\r\n", "&#010;");
					}
					if(text.contains("\r")){
						text = text.replace("\r", "&#010;");
					}
					if(text.contains("\n")){
						text = text.replace("\n", "&#010;");
					}
					if(text.contains(" ")){
						text = text.replace(" ", "&nbsp;&nbsp;");
					}
					if(text.contains("\t")){
						text = text.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
					}

					String htmlPage = new Utilities().getFirstDiv()                                   +
							" .descriptionClass{       "                                              +
							" position: absolute;      "                                              +
							" border: 0 none white;    "                                              +
							" width: 100%;             "                                              +
							" max-width: 100%;         "                                              +
							" height: 100%;            "                                              +
							" max-height: 100%;        "                                              +
							" position: absolute;      "                                              +
							" color: " + popup.getPopupTextColorPaletteSelected()   + ";        "  +
							" top: 0;          "                                                      +
							" left: 0;         "                                                      +
							" right: 0;        "                                                      +
							" font-size:       " + popup.getPopupFontSelected()          + "px; "  +
							" text-align:      " + popup.getPopupTextAlignSelected()     + ";   "  +
							" font-family:     " + popup.getFontFamilyPopupSelected()    + ";   "  +
							" font-style:      " + popup.getFontStylePopupSelected()     + ";   "  +
							" letter-spacing:  " + popup.getLetterSpacingPopupSelected() + "px; "  +
							" line-height:     " + popup.getLineHeightPopupSelected()    + ";   "  +
							" word-spacing:    " + popup.getWordSpacingPopupSelected()   + "px; ";
					if(popup.isPopupBoldSelected()){
						htmlPage += " font-weight: bold; ";
					}
					htmlPage += " } </style> </head> <body bgcolor=\"" + popup.getPopupBackgroundColorPaletteSelected() + "\">" +
							" <div class=\"overlay\"> "                                                                        +
							" <textarea class=\"descriptionClass\" readonly> " + text + " </textarea> "                        +
							" </div> </body> </html>";

					code.setPopupInformation(htmlPage);
					code.setFileName(fileSelected.getName());
					code.setOpenPopup(true);
				}
			}
		}
		catch(Exception e){
			new Utilities().writeFile(e);
		}
	}
}