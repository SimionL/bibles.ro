package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import beans.Book;
import beans.Chapter;
import beans.Version;
import dao.DAOTool;
import utilities.BooksMaps;
import utilities.Constants;

@Controller
public class InsertController{

	@Autowired
	private DAOTool dao;

	private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	//private final String dbPath = "C:\\BiblesDB\\";
	private final String dbPath = "D:\\bazaDePrelucrat\\test\\";
	private final String platformType = "\\";
	//private final String CornilescuFilePath = "C:\\Users\\Laurentiu\\Desktop\\versiuni\\Romanian-Biblia_Traducerea_Dumitru_Cornilescu_1925\\Romanian-Biblia_Traducerea_Dumitru_Cornilescu_1925-ctco\\Biblia-TDC1925-ctco\\carti\\";


	//		private final String dbPath = "/opt/tomcat/BiblesDB/";
	//		private final String platformType = "/";
	//		private final String CornilescuFilePath = "/opt/tomcat/surse/carti/";

	@RequestMapping(value = "/insertVersion")
	public void fillDatabase(HttpServletRequest request, HttpServletResponse response){

		Set<String> problem = new HashSet<>();

		try {

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			final String allBibles = "D:\\bazaDePrelucrat\\individual\\";


			//insertCornilescu(CornilescuFilePath, problem);
			CreareCapitoleMultiple(allBibles, problem);
			//scriptul_2(allBibles, problem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE!!!");

		if(problem != null && !problem.isEmpty()){
			System.out.println("Problematic versions are: ");
			for(String ver : problem)System.out.println(ver);
		}
	}

	private void insertCornilescu(String CornilescuFilePath, Set<String> problem){
		try{
			if(CornilescuFilePath != null && !CornilescuFilePath.isEmpty()){
				File director = new File(CornilescuFilePath);
				if (director != null && director.isDirectory()){

					Version version = new Version();

					version.setValue("Cornilescu");

					List<Book> books = new ArrayList<>();
					version.setBooks(books);

					Chapter chapter = null;
					String entireChapter = "";

					BooksMaps booksMaps = new BooksMaps();
					Map<Integer, String> CornilescuBooksMap = booksMaps.getCornilescuBooksMapByNumber();
					Map<String, Integer> statistics =  booksMaps.getCornilescuBooksStatistics();

					String start = "<SUP>";
					String end = "</SUP>";

					for(File file : director.listFiles()){

						if(file != null && file.exists() && file.isFile()){

							try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF8_CHARSET))){

								if(reader != null && reader.ready()){

									String bookFullName = file.getName();

									if(bookFullName != null && bookFullName.contains(".") && !bookFullName.trim().isEmpty()){

										String bookNumber = bookFullName.substring(0, bookFullName.indexOf("."));

										if(bookNumber != null && bookNumber.matches("\\d+") && !bookNumber.trim().isEmpty()){

											int bN = Integer.parseInt(bookNumber);

											String bookDescription = "";

											Book book = new Book();

											List<Chapter> chapters = new ArrayList<>();

											String line = null;
											String verseN = null;

											while ((line = reader.readLine()) != null) {

												line = new String(line.getBytes(UTF8_CHARSET), UTF8_CHARSET);

												if(line.contains("<H3>") || line.contains("<H4>")){

													bookDescription += line;
												}
												else if(line.contains("<H2>")){

													line = line.replaceAll("\\<.*?>","");

													if(line != null && line.matches("\\d+")){

														if(entireChapter != null && !entireChapter.trim().isEmpty()){
															chapter.setEntireChapter(entireChapter);
														}

														entireChapter = "";

														chapter = new Chapter();
														chapter.setBook(book);
														chapter.setValue(Integer.parseInt(line.trim()));
														chapters.add(chapter);
													}
												}
												else if(line.contains("<EM>")){

													String chapterDescription = line.replaceAll("\\<.*?>","");

													chapterDescription = chapterDescription.replaceAll("&nbsp", "");

													if(chapterDescription != null && !chapterDescription.trim().isEmpty()){

														entireChapter += "<br>" + chapterDescription.trim() + "<br><br>";
													}
												}
												else if(line.contains(start) && line.contains(end)){

													verseN = line.substring(line.indexOf(start) + start.length(), line.indexOf(end));

													if(verseN != null && !verseN.trim().isEmpty()){

														verseN = verseN.trim();
														verseN = verseN.replaceAll("&nbsp", "");

														if(verseN.matches("\\d+")){

															String verseText = line.substring(line.indexOf(end) + end.length());

															if(verseText != null && !verseText.trim().isEmpty()){

																verseText = verseText.replaceAll("\\<.*?>","");

																verseText = verseText.replaceAll("&nbsp", "");

																if(verseN.length() == 1){
																	entireChapter += verseN + ".&nbsp;&nbsp;&nbsp;" + verseText + "<br>";
																}
																else if(verseN.length() == 2){
																	entireChapter += verseN + ". " + verseText + "<br>";
																}
																else if(verseN.length() > 2){
																	entireChapter += verseN + "." + verseText + "<br>";
																}
															}
														}
													}
												}
												else if(line.contains("<P class=Q><A href=")){

													String allVerseReferences = line.replaceAll("\\<.*?>","");

													allVerseReferences = allVerseReferences.replaceAll("&nbsp", "");

													if(allVerseReferences != null && verseN != null && !allVerseReferences.trim().isEmpty()){

														allVerseReferences = allVerseReferences.trim();

														entireChapter += Constants.references.value.trim() + allVerseReferences.trim() + "<br>";
													}
												}
											}

											if(entireChapter != null && !entireChapter.trim().isEmpty()){
												chapter.setEntireChapter(entireChapter);
											}

											if(CornilescuBooksMap.containsKey(bN)){

												String bookName = CornilescuBooksMap.get(bN);

												if(bookName != null && !bookName.trim().isEmpty()){

													bookName = bookName.trim();

													book.setVersion(version);
													book.setValue(bookName);

													book.setBookNo(bN);

													if(bookDescription != null && !bookDescription.trim().isEmpty()){
														book.setDescription(bookDescription);	
													}

													book.setChapters(chapters);

													books.add(book);

													int chaptersN = statistics.get(bookName);
													int currentChapterN = chapters.size();

													if(chaptersN != currentChapterN){
														System.out.println(bookName + " contain " + currentChapterN + " should contain " + chaptersN);
													}
												}
											}
										}
									}
								}
							}
							catch(Exception e){
								problem.add("Dumitru Cornilescu ------===" );
							}
						}
					}
					if(dao.isSaveOrUpdateVersion(version)){
						createDB(version);
					}
				}
			}
		}
		catch(Throwable e){
			problem.add("Dumitru Cornilescu ------===" );
		}
	}

	private String checkFont(String line, String version, Set<String> problem){

		try{
			if (line.contains("<span class=\"smallcaps\">") && line.contains("</span>")) line = scrieFontLitereMari(line, "<span class=\"smallcaps\">", "</span>");		
			if (line.contains("\"smallcaps\">") && line.contains("</span>"))             line = scrieFontLitereMari(line, "\"smallcaps\">", "</span>");
			if (line.contains("\"smallcaps\">") && line.contains(" "))                   line = scrieFontLitereMari(line, "\"smallcaps\">", " ");
		}
		catch(Exception e){
			problem.add(version);
		}
		return line;
	}

	private void CreareCapitoleMultiple(String allBibles, Set<String> problem){

		final File biblii = new File(allBibles);

		for (File caleBiblie : biblii.listFiles()){
			if ((caleBiblie != null) && (caleBiblie.isDirectory())){

				Version version = new Version();

				version.setValue(caleBiblie.getName().trim());

				List<Book> books = new ArrayList<>();
				version.setBooks(books);

				BooksMaps booksMaps = new BooksMaps();
				Map<Integer, String> genericBooksMap = booksMaps.getGenericBooksMapByNumber();

				final File biblia = new File(((caleBiblie.getAbsolutePath()).toString()).trim());
				File [] cartiBiblie = biblia.listFiles();
				if (cartiBiblie != null && cartiBiblie.length > 0) {
					for (File carte : cartiBiblie){
						if (carte.isDirectory()){

							String arr = carte.getPath().trim().replace("\\", "-_-");
							String[] elemente = arr.split("-_-");

							String bookName = elemente[elemente.length-1].replaceFirst(" ", "").replaceFirst("	", "");

							int bookN = 0;
							for(Integer bN : genericBooksMap.keySet()){
								if(genericBooksMap.get(bN).equals(bookName)){
									bookN = bN;
									break;
								}
							}

							Book book = new Book();

							List<Chapter> chapters = new ArrayList<>();

							book.setVersion(version);
							book.setValue(bookName);
							book.setBookNo(bookN);
							book.setChapters(chapters);
							books.add(book);

							for (File capitol : carte.listFiles()){
								if (capitol.isFile()){
									String caleFisier = capitol.getPath().trim();

									try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(caleFisier), StandardCharsets.UTF_8))){

										if(reader != null && reader.ready()){

											String originalStrLine;
											StringBuilder continut = new StringBuilder();
											Chapter chapter = new Chapter();
											chapter.setBook(book);
											chapter.setValue(Integer.parseInt(caleFisier.trim().substring(caleFisier.trim().lastIndexOf("\\") + 1, caleFisier.trim().lastIndexOf("."))));

											chapters.add(chapter);

											while ((originalStrLine = reader.readLine()) != null){

												String strLine = new String(originalStrLine.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

												if (!strLine.trim().isEmpty() && (strLine.contains("title") || strLine.contains("<span class=") || strLine.contains("reftext"))){

													if(strLine.contains("<html><head><title>")){
														String chapterTitle = cautaTitlul(strLine);
														if (!chapterTitle.trim().isEmpty()){
															chapterTitle = chapterTitle.replaceAll("\\<.*?>","");
															continut.append(chapterTitle + "<br>");
														}
													}

													String verseText = null;
													if (strLine.contains("<b>")){

														verseText = strLine.substring(strLine.indexOf("<b>"));
														if (strLine.contains("<hr size=")){
															verseText = pastrezPanaLa("<hr size=", verseText);
														}
														verseText = checkFont(verseText, version.getValue(), problem);
														verseText = verseText.replaceAll("</b></a></span>", "</b></a></span>. ").replaceAll("\\<.*?>","");

														if (verseText != null && !verseText.trim().isEmpty()) {

															verseText = cleanLine(verseText);

															if (verseText != null && !verseText.trim().isEmpty()){

																String verseN = null;

																if(verseText.contains(".")){
																	verseN = verseText.substring(0, verseText.indexOf("."));
																}

																if(verseN == null || verseN.trim().isEmpty() || !verseN.trim().matches("\\d+")){
																	verseN = getVerseN(verseText);
																}

																if(verseN != null && !verseN.trim().isEmpty() && verseN.trim().matches("\\d+")){
																	continut.append(verseText + "<br>");
																}
																else{
																	//System.out.println(caleFisier);
																	//System.out.println(verseText);
																	//problem.add(version.getValue());
																	//System.out.println("version problems");	
																}
															}
														}
													}
												}
											}
											if(continut != null && continut.length() > 0){
												chapter.setEntireChapter(continut.toString());
											}
											else {
												System.out.println(caleFisier);
												System.out.println("chapter problems");
											}
										}
									}catch (Exception exception){
										exception.printStackTrace();
										problem.add(caleFisier);
										problem.add(version.getValue());
										System.out.println("version problems");																				System.err.println("Error: " + exception.getCause());
									}
								}
							}
						}			
					}
				}
				if(version != null){
					try{
						dao.isSaveOrUpdateVersion(version);
						createDB(version);
					}
					catch(Throwable e){
						problem.add(version.getValue());
						//e.printStackTrace();
					}
				}
			}
		}
	}
	private String getVerseN(String verse){

		try{

			if(verse != null && !verse.trim().isEmpty() && verse.length() > 3){

				verse = verse.trim();

				int index = 0;
				String letter = verse.substring(0, 1);

				while(letter.matches("\\d+") && index < 3){
					letter = verse.substring(++index, index + 1);
				}

				return verse.substring(0, index);
			}
		}
		catch(Exception e){
			System.out.println(verse);
			e.printStackTrace();
			return null;
		}
		return null;
	}
	private String cautaTitlul(String primaLinie){

		String titlulGasit = primaLinie.trim();
		if ((titlulGasit.contains("p class=\"hdg\"")) && (titlulGasit.contains("</p>"))) {
			titlulGasit = pastrezDeLaPanaLa("\"hdg\">", "<", titlulGasit);
			return titlulGasit.trim();
		}

		else if ((titlulGasit.contains("p class=\"hdg\"")) && (titlulGasit.contains("<br />"))) {
			titlulGasit = pastrezDeLaPanaLa("\"hdg\">", "<", titlulGasit);
			return titlulGasit.trim();
		}

		else if ((titlulGasit.contains("<span class=\"heading\">")) && (titlulGasit.contains("</span>"))){
			titlulGasit = pastrezDeLaPanaLa("<span class=\"heading\">", "<", titlulGasit);
			return titlulGasit.trim();
		}

		else if ((titlulGasit.contains("<p class=\"heading\">")) && (titlulGasit.contains("</p>"))){
			titlulGasit = pastrezDeLaPanaLa("<p class=\"heading\">", "<", titlulGasit);
			return titlulGasit.trim();
		}

		else if ((titlulGasit.contains("<p class=\"subhead\">")) && (titlulGasit.contains("</p>"))){
			titlulGasit = pastrezDeLaPanaLa("<p class=\"subhead\">", "<", titlulGasit);
			return titlulGasit.trim();
		}
		else if ((titlulGasit.contains("<title>")) && (titlulGasit.contains("</title>"))){
			titlulGasit = pastrezDeLaPanaLa("<title>", "</title>", titlulGasit);
			return titlulGasit.trim();
		}

		return "";
	}

	private String pastrezDeLaPanaLa(String text1, String text2, String totTextul){

		if (totTextul.contains(text1) && totTextul.contains(text2)){

			int index1 = totTextul.indexOf(text1);
			String restul = totTextul.substring(index1 + text1.length(), totTextul.length());
			int index2 = index1 + text1.length() + restul.indexOf(text2);

			if((index1 >= 0) && (index2 > 0) && (index1 < index2)) {
				totTextul  = totTextul.substring(index1+text1.length(),index2);
				return totTextul;
			}
		}
		return "";
	}

	private String curataText(String textul, String intrusul){

		if (textul.contains(intrusul)) {

			String stringCuratit = "";
			try{

				String[] bucati = textul.split(intrusul);

				for(int i = 0 ; i < bucati.length ; i++){

					stringCuratit += bucati[i];
				}

				if (stringCuratit.contains(intrusul)) {

					while(stringCuratit.contains(intrusul)){

						int stergDeLa = stringCuratit.indexOf(intrusul);
						int stergPanaLa = stringCuratit.indexOf(intrusul) + intrusul.length();

						String textul1 = stringCuratit.substring(stergDeLa, stergPanaLa);

						String textul2 = stringCuratit.substring(stergPanaLa, stringCuratit.length());

						stringCuratit = textul1 + textul2;	 
					}		
				}
			}
			catch(Throwable q){
				System.out.println(q.getCause());
				System.out.println(q.getMessage());
				System.err.print(22);
			}
			return stringCuratit;
		}
		else return textul;
	}

	private String pastrezPanaLa(String text1, String totTextul){

		if (totTextul.contains(text1))  {

			int index1 = totTextul.indexOf(text1);	

			totTextul  = totTextul.substring(0,index1);

		}
		return totTextul.trim();
	}
	private String cleanLine(String entireLine){

		String localLine = entireLine;

		try{
			if (localLine != null && !localLine.trim().isEmpty()){

				localLine = curataText(localLine, "</td>").trim();
				localLine = (localLine.replace("</td>", "")).trim();
				localLine = curataText(localLine, "<td>").trim();
				localLine = (localLine.replace("<td>", "")).trim();
				localLine = curataText(localLine, "<td align=\"right\">").trim();
				localLine = (localLine.replace("<td align=\"right\">", "")).trim();
				localLine = curataText(localLine, "</tr>").trim();
				localLine = (localLine.replace("</tr>", "")).trim();
				localLine = curataText(localLine, "<tr>").trim();
				localLine = (localLine.replace("<tr>", "")).trim();
				localLine = curataText(localLine, "<td\"right\">").trim();
				localLine = (localLine.replace("<td\"right\">", "")).trim();
				localLine = localLine.replace("</b></a></span>", "");
				localLine = curataText(localLine, "<b>");
				localLine = curataText(localLine, "<head"); //System.out.println(1);
				localLine = curataText(localLine, "<br>");//System.out.println(2);
				localLine = curataText(localLine, "<a href=");//System.out.println(3);
				localLine = curataText(localLine, "</div>");//System.out.println(4);
				localLine = curataText(localLine, "</ul>");//System.out.println(5);
				localLine = curataText(localLine, "<!--");//System.out.println(6);
				localLine = curataText(localLine, "Sitemap");//System.out.println(7);
				localLine = curataText(localLine, "@ 2012");//System.out.println(8);
				localLine = curataText(localLine, "footer");//System.out.println(9);
				localLine = curataText(localLine, "http:");//System.out.println(10);
				localLine = curataText(localLine, "-->");//System.out.println(11);
				localLine = curataText(localLine, "#FFFFFF");//System.out.println(12);
				localLine = curataText(localLine, "#");//System.out.println(13);
				localLine = curataText(localLine, "</script>");//System.out.println(14);
				localLine = curataText(localLine, "<script>");//System.out.println(15);
				localLine = curataText(localLine, "<li>");//System.out.println(16);
				localLine = curataText(localLine, "cellspacing=");//System.out.println(17);
				localLine = curataText(localLine, "<!-- /rbcontent -->");//System.out.println(18);
				localLine = curataText(localLine, "sidebar");//System.out.println(19);
				localLine = curataText(localLine, "<li class=");//System.out.println(20);
				localLine = curataText(localLine, "page_item");//System.out.println(21);
				localLine = curataText(localLine, "><a title");//System.out.println(22);
				localLine = curataText(localLine, "</a></li>");//System.out.println(23);
				localLine = curataText(localLine, "<td valign=");//System.out.println(24);
				localLine = curataText(localLine, " align=");//System.out.println(25);
				localLine = curataText(localLine, "width");//System.out.println(26);
				localLine = curataText(localLine, "<p>");//System.out.println(27);
				localLine = curataText(localLine, "<!--");//System.out.println(29);
				localLine = curataText(localLine, "topbar");//System.out.println(30);
				localLine = curataText(localLine, "<div id=");//System.out.println(31);
				localLine = curataText(localLine, "footer");//System.out.println(32);
				localLine = curataText(localLine, "</head>");//System.out.println(33);
				localLine = curataText(localLine, "<head>");//System.out.println(34);
				localLine = curataText(localLine, "</body>");//System.out.println(35);
				localLine = curataText(localLine, "<!--");//System.out.println(36);
				localLine = curataText(localLine, "if IE 6]>");//System.out.println(361);
				localLine = curataText(localLine, "xfn");//System.out.println(37);
				localLine = curataText(localLine, "gmpg.org");//System.out.println(38);
				localLine = curataText(localLine, "profile=");//System.out.println(39);
				localLine = curataText(localLine, "xhtml");//System.out.println(40);
				localLine = curataText(localLine, "www.w3.org");//System.out.println(41);
				localLine = curataText(localLine, "http:");//System.out.println(42);
				localLine = curataText(localLine, "<html xmlns");//System.out.println(43);
				localLine = curataText(localLine, "W3C ");//System.out.println(44);
				localLine = curataText(localLine, "<DTD XHTML 1.0 Transitional");//System.out.println(45);
				localLine = curataText(localLine, "<http:");//System.out.println(46);
				localLine = curataText(localLine, "www.w3.org");//System.out.println(47);
				localLine = curataText(localLine, "<xhtml1");//System.out.println(48);
				localLine = curataText(localLine, "xhtml1-transitional.dtd");//System.out.println(49);
				localLine = curataText(localLine, "<p>");//System.out.println(51);
				localLine = curataText(localLine, "</ul>");//System.out.println(52);
				localLine = curataText(localLine, "<div id=");//System.out.println(53);
				localLine = curataText(localLine, "narrowcolumn");//System.out.println(54);
				localLine = curataText(localLine, "<div class=");//System.out.println(55);
				localLine = curataText(localLine, "post-87");//System.out.println(56);
				localLine = curataText(localLine, "entrytext");//System.out.println(57);
				localLine = curataText(localLine, "www.gtcentralrotary.org");//System.out.println(58);
				localLine = curataText(localLine, "dimensiune font titlu");//System.out.println(59);
				localLine = curataText(localLine, "fundalul la fereastra cu stiri");//System.out.println(60);
				localLine = curataText(localLine, "culoare font titlu");//System.out.println(61);
				localLine = curataText(localLine, "culoare font stiri");//System.out.println(62);
				localLine = curataText(localLine, "dimensiune font titlu");//System.out.println(63);
				localLine = curataText(localLine, "dimensiune font stiri");//System.out.println(64);
				localLine = curataText(localLine, "<br /");//System.out.println(65);
				localLine = curataText(localLine, "<ul>");//System.out.println(66);
				localLine = curataText(localLine, "Cuprins general - Biblia ortodoxa");//System.out.println(67);
				localLine = curataText(localLine, "<table");//System.out.println(68);
				localLine = curataText(localLine, "<tr>");//System.out.println(69);
				localLine = curataText(localLine, "reftext").trim();
				localLine = curataText(localLine, ".htm").trim();
				localLine = curataText(localLine, "<i>").trim();
				localLine = curataText(localLine, "</i>").trim();
				localLine = curataText(localLine, ".htm").trim();
				localLine = curataText(localLine, "\"btext\">").trim();
				localLine = curataText(localLine, "\"parenth\">").trim();
				localLine = curataText(localLine, "\"black\">").trim();
				localLine = curataText(localLine, "\"red\">").trim();
				localLine = curataText(localLine, "\"none\">").trim();
				localLine = (localLine.replace("\"black\">", "")).trim();
				localLine = (localLine.replace("\"red\">", "")).trim();
				localLine = (localLine.replace("\"none\">", "")).trim();
				localLine = curataText(localLine, "</p>");
				localLine = curataText(localLine, "<br />");
				localLine = curataText(localLine, "<p class=\"body-fl-sp\">");
				localLine = (localLine.replace("<p class=\"body-fl-sp\">", "")).trim();
				localLine = pastrezPanaLa("<hr size=",localLine);
				localLine = (localLine.replace("</b></a>&nbsp;", ".")).trim();
				localLine = curataText(localLine, "<I>").trim();
				localLine = curataText(localLine, "</I>").trim();
				localLine = curataText(localLine, "</i").trim();
				localLine = curataText(localLine, "<p class=\"list-sp\">").trim();
				localLine = curataText(localLine, "<p class=\"body1\">").trim();
				localLine = curataText(localLine, "<p class=\"ext-body-fl-sp\">").trim();
				localLine = curataText(localLine, "<p class=\"poet\">").trim();
				localLine = curataText(localLine, "<p class=\"poet0\">").trim();
				localLine = curataText(localLine, "<p class=\"poet1\">").trim();
				localLine = curataText(localLine, "<p class=\"poet2\">").trim();
				localLine = curataText(localLine, "<p class=\"poet3\">").trim();
				localLine = curataText(localLine, "<p class=\"poet4\">").trim();
				localLine = curataText(localLine, "<p class=\"poet5\">").trim();
				localLine = curataText(localLine, "<p class=\"poet6\">").trim();
				localLine = curataText(localLine, "<p class=\"poet7\">").trim();
				localLine = curataText(localLine, "<p class=\"poet8\">").trim();
				localLine = curataText(localLine, "<p class=\"poet9\">").trim();
				localLine = curataText(localLine, "<p class=\"poet10\">").trim();
				localLine = curataText(localLine, "<p class=\"poet11\">").trim();
				localLine = curataText(localLine, "\"nl:<p class=\"poet1-sp\">").trim();
				localLine = (localLine.replace("\"nl:<p class=\"poet1-sp\">", "")).trim();
				localLine = curataText(localLine, "\"nl:<p class=\"poet0-sp\">").trim();
				localLine = (localLine.replace("\"nl:<p class=\"poet0-sp\">", "")).trim();
				localLine = curataText(localLine, "\"nl:<p class=\"poet2-sp\">").trim();
				localLine = (localLine.replace("\"nl:<p class=\"poet2-sp\">", "")).trim();
				localLine = curataText(localLine, "\"nl:<p class=\"poet3-sp\">").trim();
				localLine = (localLine.replace("\"nl:<p class=\"poet3-sp\">", "")).trim();
				localLine = curataText(localLine, "\"nl:<p class=\"poet4-sp\">").trim();
				localLine = (localLine.replace("\"nl:<p class=\"poet4-sp\">", "")).trim();
				localLine = curataText(localLine, "\"nl:<p class=\"poet5-sp\">").trim();
				localLine = (localLine.replace("\"nl:<p class=\"poet5-sp\">", "")).trim();
				localLine = curataText(localLine, "\"nl:<p class=\"poet6-sp\">").trim();
				localLine = (localLine.replace("\"nl:<p class=\"poet6-sp\">", "")).trim();
				localLine = curataText(localLine, "<span class=\"bracket\">").trim();
				localLine = (localLine.replace("<span class=\"bracket\">", "")).trim();

				if (localLine != null)localLine = localLine.trim();

				String etapaIntermediara = "";

				for (String line : (localLine.trim()).split("\\n")) {

					if ((line != null) && (!line.isEmpty()) && (!(line.trim()).isEmpty()) && (((line.trim()).length()>0))){
						String nouaLinie = line.trim();

						String[]  caractere1 = {"footnote" , "</sup>"};
						nouaLinie = _eliminTextul(caractere1, nouaLinie).trim();				

						String[]  caractere2 = {"<p class=" , "regular", ">"};
						nouaLinie = _eliminTextul(caractere2, nouaLinie).trim();

						String[]  caractere3 = {"block-indent" , ">"};
						nouaLinie = _eliminTextul(caractere3, nouaLinie).trim();

						String[]  caractere4 = {"p class" , "line-group", ">"};
						nouaLinie = _eliminTextul(caractere4, nouaLinie).trim();

						String[]  caractere5 = {"line", ">"};
						nouaLinie = _eliminTextul(caractere5, nouaLinie).trim();

						String[]  caractere6 = {"<p class=", "same-paragraph", ">"};
						nouaLinie = _eliminTextul(caractere6, nouaLinie).trim();

						String[]  caractere7 = {"ln-indent", ">"};
						nouaLinie = _eliminTextul(caractere7, nouaLinie).trim();

						String[]  caractere8 = {"woc", ">"};
						nouaLinie = _eliminTextul(caractere8, nouaLinie).trim();

						String[]  caractere9 = {"BLOCK-INDENT", "P class="};
						nouaLinie = _eliminTextul(caractere9, nouaLinie).trim();

						if (nouaLinie.contains("&nbsp;") || nouaLinie.contains("&nbsp;".toUpperCase())|| nouaLinie.contains("&nbsp;".toLowerCase())) nouaLinie = (nouaLinie.replace("&nbsp;", "")).trim();

						String[]  caractere10 = {"p class=", "LISTD", ">"};
						nouaLinie = _eliminTextul(caractere10, nouaLinie).trim();

						String[]  caractere11 = {"p class=", "NPSTHALF", ">"};
						nouaLinie = _eliminTextul(caractere11, nouaLinie).trim();

						String[]  caractere12 = {"p class=", "TXTTWO", ">"};
						nouaLinie = _eliminTextul(caractere12, nouaLinie).trim();

						String[]  caractere14 = {"p class=" , "acrostic", ">"};
						nouaLinie = _eliminTextul(caractere14, nouaLinie).trim();

						String[]  caractere15 = {"\"\">" , "\">"};
						nouaLinie = _eliminTextul(caractere15, nouaLinie).trim();

						nouaLinie = respectaFontul(nouaLinie).trim();

						etapaIntermediara = etapaIntermediara + nouaLinie.trim() + "\n";
					}
				}

				etapaIntermediara = curataText(etapaIntermediara, "<span class=").trim();
				etapaIntermediara = (etapaIntermediara.replace("</span>", "")).trim();
				etapaIntermediara = curataText(etapaIntermediara, "</SPAN>").trim();
				etapaIntermediara = (etapaIntermediara.replace("</SPAN>", "")).trim();
				etapaIntermediara = curataText(etapaIntermediara, "<SPAN>").trim();
				etapaIntermediara = (etapaIntermediara.replace("<SPAN>", "")).trim();
				etapaIntermediara = curataText(etapaIntermediara, "<span>").trim();
				etapaIntermediara = (etapaIntermediara.replace("<span>", "")).trim();
				etapaIntermediara = curataText(etapaIntermediara, "</span>").trim();
				etapaIntermediara = (etapaIntermediara.replace("</span>", "")).trim();
				etapaIntermediara = curataText(etapaIntermediara, "</span").trim();
				etapaIntermediara = (etapaIntermediara.replace("</span", "")).trim();
				etapaIntermediara = curataText(etapaIntermediara, "<SPAN class=").trim();
				etapaIntermediara = (etapaIntermediara.replace("<SPAN class=", "")).trim();
				etapaIntermediara = (etapaIntermediara.replace("<<", "{")).trim();
				etapaIntermediara = (etapaIntermediara.replace(">>>", "}")).trim();
				etapaIntermediara = (etapaIntermediara.replace("}>", "}")).trim();
				etapaIntermediara = (etapaIntermediara.replace(">>", "}")).trim();

				etapaIntermediara = (etapaIntermediara.replace("<span class", "\n")).trim(); 

				etapaIntermediara = (etapaIntermediara.replace("&8212;", "â€”")).trim(); 
				etapaIntermediara = (etapaIntermediara.replace("&#8217;", "â€™")).trim(); 
				etapaIntermediara = (etapaIntermediara.replace("&8217;", "â€™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&8216;", "â€�")).trim(); 
				etapaIntermediara = (etapaIntermediara.replace("&#8216;", "â€�")).trim(); 
				etapaIntermediara = (etapaIntermediara.replace("&#8221;", "â€ť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#8220;", "“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#8221;", "”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&8220;", "\"")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+00AB", "Â«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+00BB", "Â»")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+2018", "â€�")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+2019", "â€™")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+201A", "â€š")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&sbquo;", "â€š")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+201B", "â€›")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#8219;", "â€›")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+201C", "\"")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+201D", "â€ť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+201E", "â€ž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&bdquo;", "â€ž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+201F", "â€ź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#8223;", "â€ź")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+2039", "â€ą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&lsaquo;", "â€ą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+203A", "â€ş")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&rsaquo;", "â€ş")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+300C", "ă€Ś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#12300;", "ă€Ś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+300D", "ă€Ť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#12301;", "ă€Ť")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+300E", "ă€Ž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#12302;", "ă€Ž")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+300F", "ă€Ź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#12303;", "ă€Ź")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+301D", "ă€ť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#12317;", "ă€ť")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+301E", "ă€ž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#12318;", "ă€ž")).trim();		 
				etapaIntermediara = (etapaIntermediara.replace("U+301F", "ă€ź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#12319;", "ă€ź")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+FE41", "ďą�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#65089;", "ďą�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+FE42", "ďą‚")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#65090;", "ďą‚")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+FE43", "ďą�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#65091;", "ďą�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+FE44", "ďą„")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#65092;", "ďą„")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+FF02", "ďĽ‚")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#65282;", "ďĽ‚")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0022", "ďĽ‚")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+FF07", "ďĽ‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#65287;", "ďĽ‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0027", "ďĽ‡")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+FF62", "ď˝˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#65378;", "ď˝˘")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+FF63", "ď˝Ł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#65379;", "ď˝Ł")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("&#09;", "	")).trim();			
				etapaIntermediara = curataText(etapaIntermediara, "&#13;").trim();
				etapaIntermediara = (etapaIntermediara.replace("&#32;", " ")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#33;", "!")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#34;", "\"")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&quot;", "\"")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#35;", "#")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#36;", "$")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#37;", "%")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#38;", "&")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&amp;", "&")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("&#39;", "'")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#40;", "(")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#41;", ")")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#42;", "*")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#43;", "+")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#44;", ",")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#45;", "-")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#46;", ".")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#47;", "/")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&frasl;", "/")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#48;", "0")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#49;", "1")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#50;", "2")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#51;", "3")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#52;", "4")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#53;", "5")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#54;", "6")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#55;", "7")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#56;", "8")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#57;", "9")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#58;", ":")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#59;", ";")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("&#60;", "<")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&lt;", "<")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#61;", "=")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#62;", ">")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&gt;", ">")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#63;", "?")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#64;", "@")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#65;", "A")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#66;", "B")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#67;", "C")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#68;", "D")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#69;", "E")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#70;", "F")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#71;", "G")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#72;", "H")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#73;", "I")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#74;", "J")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#75;", "K")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#76;", "L")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#77;", "M")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#78;", "N")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#79;", "O")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#80;", "P")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#81;", "Q")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#82;", "R")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#83;", "S")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#84;", "T")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#85;", "U")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#86;", "V")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#87;", "W")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#88;", "X")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#89;", "Y")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#90;", "Z")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#91;", "[")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#92;", "\\")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#93;", "]")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#94;", "^")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#95;", "_")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#96;", "`")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#97;", "a")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#98;", "b")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#99;", "c")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#100;", "d")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#101;", "e")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#102;", "f")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#103;", "g")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#104;", "h")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#105;", "i")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#106;", "j")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#107;", "k")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#108;", "l")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#109;", "m")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#110;", "n")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#111;", "o")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#112;", "p")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#113;", "q")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#114;", "r")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#115;", "s")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#116;", "t")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#117;", "u")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#118;", "v")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#119;", "w")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#120;", "x")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#121;", "y")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#122;", "z")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#123;", "{")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#124;", "|")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#125;", "}")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#126;", "~")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#127;", "")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#128;", "â‚¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&euro;", "â‚¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#130;", "â€š")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#131;", "Ć’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#132;", "â€ž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#133;", "â€¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#134;", "â€ ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&dagger;", "â€ ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#135;", "â€ˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Dagger;", "â€ˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#136;", "Ë†")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#137;", "â€°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&permil;", "â€°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#138;", "Ĺ ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#139;", "â€ą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#140;", "Ĺ’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#145;", "â€�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&lsquo;", "â€�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#146;", "â€™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&rsquo;", "â€™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#147;", "\"")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&ldquo;", "\"")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#148;", "â€ť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&rdquo;", "â€ť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#149;", "â€˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&bull;", "â€˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#150;", "â€“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&ndash;", "â€“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#151;", "â€”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&mdash;", "â€”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#152;", "Ëś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#153;", "â„˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#154;", "Ĺˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#155;", "â€ş")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#156;", "Ĺ“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#159;", "Ĺ¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#160;", " ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&nbsp;", " ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#161;", "Âˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&iexcl;", "Âˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#162;", "Â˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&cent;", "Â˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#163;", "ÂŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&pound;", "ÂŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#164;", "Â¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&curren;", "Â¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#165;", "ÂĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&yen;", "ÂĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#166;", "Â¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&brvbar;", "Â¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&brkbar;", "Â¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#167;", "Â§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&sect;", "Â§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#168;", "Â¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&uml;", "Â¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&die;", "Â¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#169;", "Â©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&copy;", "Â©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#170;", "ÂŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&ordf;", "ÂŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#171;", "Â«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&laquo;", "Â«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#172;", "Â¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&not;", "Â¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&shy;", "")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#173;", "")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#174;", "Â®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&reg;", "Â®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#175;", "ÂŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&macr;", "ÂŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&hibar;", "ÂŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#176;", "Â°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&deg;", "Â°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#177;", "Â±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&plusmn;", "Â±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#178;", "Â˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&sup2;", "Â˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#179;", "Âł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&sup3;", "Âł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#180;", "Â´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&acute;", "Â´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#181;", "Âµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&micro;", "Âµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#182;", "Â¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&para;", "Â¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#183;", "Â·")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&middot;", "Â·")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#184;", "Â¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&cedil;", "Â¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#185;", "Âą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&sup1;", "Âą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#186;", "Âş")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&ordm;", "Âş")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#187;", "Â»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&raquo;", "Â»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#188;", "ÂĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&frac14;", "ÂĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#189;", "Â˝")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&frac12;", "Â˝")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#190;", "Âľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&frac34;", "Âľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#191;", "Âż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&iquest;", "Âż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#192;", "Ă€")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Agrave;", "Ă€")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#193;", "Ă�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Aacute;", "Ă�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#194;", "Ă‚")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Acirc;", "Ă‚")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#195;", "Ă�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Atilde;", "Ă�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#196;", "Ă„")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Auml;", "Ă„")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#197;", "Ă…")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Aring;", "Ă…")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#198;", "Ă†")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Aelig;", "Ă†")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#199;", "Ă‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Ccedil;", "Ă‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#200;", "Ă�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Egrave;", "Ă�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#201;", "Ă‰")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Eacute;", "Ă‰")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#202;", "ĂŠ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Ecicr;", "ĂŠ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#203;", "Ă‹")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Euml;", "Ă‹")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#204;", "ĂŚ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Igrave;", "ĂŚ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#205;", "ĂŤ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Iacute;", "ĂŤ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#206;", "ĂŽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Icirc;", "ĂŽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#207;", "ĂŹ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Iuml;", "ĂŹ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#208;", "Ă�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&ETH;", "Ă�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Dstrok;", "Ă�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#209;", "Ă‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Ntilde;", "Ă‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#210;", "Ă’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Ograve;", "Ă’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#211;", "Ă“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Oacute;", "Ă“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#212;", "Ă”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Ocirc;", "Ă”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#213;", "Ă•")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Otilde;", "Ă•")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#214;", "Ă–")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Ouml;", "Ă–")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#215;", "Ă—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&times;", "Ă—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#216;", "Ă�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Oslash;", "Ă�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#217;", "Ă™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Ugrave;", "Ă™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#218;", "Ăš")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Uacute;", "Ăš")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#219;", "Ă›")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Ucirc;", "Ă›")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#220;", "Ăś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Uuml;", "Ăś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#221;", "Ăť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&Yacute;", "Ăť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#222;", "Ăž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&THORN;", "Ăž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#223;", "Ăź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&szlig;", "Ăź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#224;", "Ă ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&agrave;", "Ă ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#225;", "Ăˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&aacute;", "Ăˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#226;", "Ă˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&acirc;", "Ă˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#227;", "ĂŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&atilde;", "ĂŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#228;", "Ă¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&auml;", "Ă¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#229;", "ĂĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&aring;", "ĂĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#230;", "Ă¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&aelig;", "Ă¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#231;", "Ă§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&ccedil;", "Ă§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#232;", "Ă¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&egrave;", "Ă¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#233;", "Ă©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&eacute;", "Ă©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#234;", "ĂŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&ecirc;", "ĂŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#235;", "Ă«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&euml;", "Ă«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#236;", "Ă¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&igrave;", "Ă¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#237;", "Ă­")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&iacute;", "Ă­")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#238;", "Ă®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&icirc;", "Ă®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#239;", "ĂŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&iuml;", "ĂŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#240;", "Ă°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&eth;", "Ă°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#241;", "Ă±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&ntilde", "Ă±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#242;", "Ă˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&ograve;", "Ă˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#243;", "Ăł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&oacute;", "Ăł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#244;", "Ă´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&ocirc;", "Ă´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#245;", "Ăµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&otilde;", "Ăµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#246;", "Ă¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&ouml;", "Ă¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#247;", "Ă·")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&divide;", "Ă·")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#248;", "Ă¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&oslash;", "Ă¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#249;", "Ăą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&ugrave;", "Ăą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#250;", "Ăş")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&uacute;", "Ăş")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#251;", "Ă»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&ucirc;", "Ă»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#252;", "ĂĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&uuml;", "ĂĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#253;", "Ă˝")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&yacute;", "Ă˝")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#254;", "Ăľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&thorn;", "Ăľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#255;", "Ăż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&yuml;", "Ăż")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("&#1488;", "×�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#1489;", "×‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&1488;", "")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&1489;", "")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0100", "Ä€")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#256;", "Ä€")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 80", "Ä€")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0101", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 81", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#257;", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0102", "Ä‚")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 82", "Ä‚")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#258;", "Ä‚")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0103", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 83", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#259;", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0104", "Ä„")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 84", "Ä„")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#260;", "Ä„")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0105", "Ä…")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 85", "Ä…")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#261;", "Ä…")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0106", "Ä†")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 86", "Ä†")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#262;", "Ä†")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0107", "Ä‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 87", "Ä‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#263;", "Ä‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0108", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 88", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#264;", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0109", "Ä‰")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 89", "Ä‰")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#265;", "Ä‰")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+010A", "ÄŠ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 8a", "ÄŠ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#266;", "ÄŠ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+010B", "Ä‹")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 8b", "Ä‹")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#267;", "Ä‹")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+010C", "ÄŚ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 8c", "ÄŚ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#268;", "ÄŚ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+010D", "ÄŤ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 8d", "ÄŤ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#269;", "ÄŤ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+010E", "ÄŽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 8e", "ÄŽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#270;", "ÄŽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+010F", "ÄŹ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 8f", "ÄŹ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#271;", "ÄŹ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0110", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 90", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#272;", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0111", "Ä‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 91", "Ä‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#273;", "Ä‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0112", "Ä’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 92", "Ä’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#274;", "Ä’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0113", "Ä“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 93", "Ä“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#275;", "Ä“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0114", "Ä”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 94", "Ä”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#276;", "Ä”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0115", "Ä•")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 95", "Ä•")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#277;", "Ä•")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0116", "Ä–")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 96", "Ä–")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#278;", "Ä–")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0117", "Ä—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 97", "Ä—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#279;", "Ä—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0118", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 98", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#280;", "Ä�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0119", "Ä™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 99", "Ä™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#281;", "Ä™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+011A", "Äš")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 9a", "Äš")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#282;", "Äš")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+011B", "Ä›")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 9b", "Ä›")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#283;", "Ä›")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+011C", "Äś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 9c", "Äś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#284;", "Äś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+011D", "Äť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 9d", "Äť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#285;", "Äť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+011E", "Äž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 9e", "Äž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#286;", "Äž")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+011F", "Äź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 9f", "Äź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#287;", "Äź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0120", "Ä ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 a0", "Ä ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#288;", "Ä ")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+0121", "Äˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 a1", "Äˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#289;", "Äˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0122", "Ä˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 a2", "Ä˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#290;", "Ä˘")).trim();						
				etapaIntermediara = (etapaIntermediara.replace("U+0123", "ÄŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 a3", "ÄŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#291;", "ÄŁ")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+0124", "Ä¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 a4", "Ä¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#292;", "Ä¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0125", "ÄĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 a5", "ÄĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#293;", "ÄĄ")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0126", "Ä¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 a6", "Ä¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#294;", "Ä¦")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+0127", "Ä§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 a7", "Ä§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#295;", "Ä§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0128", "Ä¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 a8", "Ä¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#296;", "Ä¨")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+0129", "Ä©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 a9", "Ä©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#297;", "Ä©")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+012A", "ÄŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 aa", "ÄŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#298;", "ÄŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+012B", "Ä«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 ab", "Ä«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#299;", "Ä«")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+012C", "Ä¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 ac", "Ä¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#300;", "Ä¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+012D", "Ä­")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 ad", "Ä­")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#301;", "Ä­")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+012E", "Ä®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 ae", "Ä®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#302;", "Ä®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+012F", "ÄŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#303;", "ÄŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 af", "ÄŻ")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0130", "Ä°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 b0", "Ä°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#304;", "Ä°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0131", "Ä±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 b1", "Ä±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#305;", "Ä±")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0132", "Ä˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 b2", "Ä˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#306;", "Ä˛")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0133", "Äł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 b3", "Äł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#307;", "Äł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0134", "Ä´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 b4", "Ä´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#308;", "Ä´")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+0135", "Äµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 b5", "Äµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#309;", "Äµ")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0136", "Ä¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 b6", "Ä¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#310;", "Ä¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0137", "Ä·")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 b7", "Ä·")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#311;", "Ä·")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+0138", "Ä¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 b8", "Ä¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#312;", "Ä¸")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0139", "Äą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 b9", "Äą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#313;", "Äą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+013A", "Äş")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 ba", "Äş")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#314;", "Äş")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+013B", "Ä»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 bb", "Ä»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#315;", "Ä»")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+013C", "ÄĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 bc", "ÄĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#316;", "ÄĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+013D", "Ä˝")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 bd", "Ä˝")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#317;", "Ä˝")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+013E", "Äľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 be", "Äľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#318;", "Äľ")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+013F", "Äż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c4 bf", "Äż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#319;", "Äż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0140", "Ĺ€")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 80", "Ĺ€")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#320;", "Ĺ€")).trim();						
				etapaIntermediara = (etapaIntermediara.replace("U+0141", "Ĺ�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 81", "Ĺ�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#321;", "Ĺ�")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0142", "Ĺ‚")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 82", "Ĺ‚")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#322;", "Ĺ‚")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+0143", "Ĺ�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 83", "Ĺ�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#323;", "Ĺ�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0144", "Ĺ„")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 84", "Ĺ„")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#324;", "Ĺ„")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0145", "Ĺ…")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 85", "Ĺ…")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#325;", "Ĺ…")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0146", "Ĺ†")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 86", "Ĺ†")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#326;", "Ĺ†")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0147", "Ĺ‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 87", "Ĺ‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#327;", "Ĺ‡")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0148", "Ĺ�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 88", "Ĺ�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#328;", "Ĺ�")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+0149", "Ĺ‰")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 89", "Ĺ‰")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#329;", "Ĺ‰")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+014A", "ĹŠ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 8a", "ĹŠ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#330;", "ĹŠ")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+014B", "Ĺ‹")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 8b", "Ĺ‹")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#331;", "Ĺ‹")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+014C", "ĹŚ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 8c", "ĹŚ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#332;", "ĹŚ")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+014D", "ĹŤ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 8d", "ĹŤ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#333;", "ĹŤ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+014E", "ĹŽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 8e", "ĹŽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#334;", "ĹŽ")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+014F", "ĹŹ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 8f", "ĹŹ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#335;", "ĹŹ")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+0150", "Ĺ�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 90", "Ĺ�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#336;", "Ĺ�")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+0151", "Ĺ‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 91", "Ĺ‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#337;", "Ĺ‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0152", "Ĺ’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 92", "Ĺ’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#338;", "Ĺ’")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0153", "Ĺ“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 93", "Ĺ“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#339;", "Ĺ“")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+0154", "Ĺ”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 94", "Ĺ”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#340;", "Ĺ”")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+0155", "Ĺ•")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 95", "Ĺ•")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#341;", "Ĺ•")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0156", "Ĺ–")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 96", "Ĺ–")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#342;", "Ĺ–")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0157", "Ĺ—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 97", "Ĺ—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#343;", "Ĺ—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0158", "Ĺ�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 98", "Ĺ�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#344;", "Ĺ�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0159", "Ĺ™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 99", "Ĺ™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#345;", "Ĺ™")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+015A", "Ĺš")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 9a", "Ĺš")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#346;", "Ĺš")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+015B", "Ĺ›")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 9b", "Ĺ›")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#347;", "Ĺ›")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+015C", "Ĺś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 9c", "Ĺś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#348;", "Ĺś")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+015D", "Ĺť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 9d", "Ĺť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#349;", "Ĺť")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+015E", "Ĺž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 9e", "Ĺž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#350;", "Ĺž")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+015F", "Ĺź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 9f", "Ĺź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#351;", "Ĺź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0160", "Ĺ ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 a0", "Ĺ ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#352;", "Ĺ ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0161", "Ĺˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 a1", "Ĺˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#353;", "Ĺˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0162", "Ĺ˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 a2", "Ĺ˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#354;", "Ĺ˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0163", "ĹŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 a3", "ĹŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#355;", "ĹŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0164", "Ĺ¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 a4", "Ĺ¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#356;", "Ĺ¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0165", "ĹĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 a5", "ĹĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#357;", "ĹĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0166", "Ĺ¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 a6", "Ĺ¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#358;", "Ĺ¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0167", "Ĺ§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 a7", "Ĺ§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#359;", "Ĺ§")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0168", "Ĺ¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 a8", "Ĺ¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#360;", "Ĺ¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0169", "Ĺ©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 a9", "Ĺ©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#361;", "Ĺ©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+016A", "ĹŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 aa", "ĹŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#362;", "ĹŞ")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+016B", "Ĺ«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 ab", "Ĺ«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#363;", "Ĺ«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+016C", "Ĺ¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 ac", "Ĺ¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#364;", "Ĺ¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+016D", "Ĺ­")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 ad", "Ĺ­")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#365;", "Ĺ­")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+016E", "Ĺ®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 ae", "Ĺ®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#366;", "Ĺ®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+016F", "ĹŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 af", "ĹŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#367;", "ĹŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0170", "Ĺ°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 b0", "Ĺ°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#368;", "Ĺ°")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+0171", "Ĺ±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 b1", "Ĺ±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#369;", "Ĺ±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0172", "Ĺ˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 b2", "Ĺ˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#370;", "Ĺ˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0173", "Ĺł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 b3", "Ĺł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#371;", "Ĺł")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+0174", "Ĺ´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 b4", "Ĺ´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#372;", "Ĺ´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0175", "Ĺµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 b5", "Ĺµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#373;", "Ĺµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0176", "Ĺ¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 b6", "Ĺ¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#374;", "Ĺ¶")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0177", "Ĺ·")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 b7", "Ĺ·")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#375;", "Ĺ·")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+0178", "Ĺ¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 b8", "Ĺ¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#376;", "Ĺ¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0179", "Ĺą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 b9", "Ĺą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#377;", "Ĺą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+017A", "Ĺş")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 ba", "Ĺş")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#378;", "Ĺş")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+017B", "Ĺ»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 bb", "Ĺ»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#379;", "Ĺ»")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+017C", "ĹĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 bc", "ĹĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#380;", "ĹĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+017D", "Ĺ˝")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 bd", "Ĺ˝")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#381;", "Ĺ˝")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+017E", "Ĺľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 be", "Ĺľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#382;", "Ĺľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+017F", "Ĺż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c5 bf", "Ĺż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#383;", "Ĺż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0180", "Ć€")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 80", "Ć€")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#384;", "Ć€")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0181", "Ć� ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 81", "Ć� ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#385;", "Ć� ")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+0182", "Ć‚ ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 82", "Ć‚ ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#386;", "Ć‚ ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0183", "Ć� ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 83", "Ć� ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#387;", "Ć� ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0184", "Ć„ ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 84", "Ć„ ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#388;", "Ć„ ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0185", "Ć… ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 85", "Ć… ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#389;", "Ć… ")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0186", "Ć† ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 86", "Ć† ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#390;", "Ć† ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0187", "Ć‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 87", "Ć‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#391;", "Ć‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0188", "Ć� ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 88", "Ć� ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#392;", "Ć� ")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+0189", "Ć‰")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 89", "Ć‰")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#393;", "Ć‰")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+018A", "ĆŠ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 8a", "ĆŠ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#394;", "ĆŠ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+018B", "Ć‹")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 8b", "Ć‹")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#395;", "Ć‹")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+018C", "ĆŚ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 8c", "ĆŚ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#396;", "ĆŚ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+018D", "ĆŤ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 8d", "ĆŤ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#397;", "ĆŤ")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+018E", "ĆŽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 8e", "ĆŽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#398;", "ĆŽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+018F", "ĆŹ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 8f", "ĆŹ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#399;", "ĆŹ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0190", "Ć�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 90", "Ć�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#400;", "Ć�")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+0191", "Ć‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 91", "Ć‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#401;", "Ć‘")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0192", "Ć’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 92", "Ć’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#402;", "Ć’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0193", "Ć“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 93", "Ć“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#403;", "Ć“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0194", "Ć”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 94", "Ć”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#404;", "Ć”")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0195", "Ć•")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 95", "Ć•")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#405;", "Ć•")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0196", "Ć–")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#406;", "Ć–")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 96", "Ć–")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0197", "Ć—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 97", "Ć—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#407;", "Ć—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+0198", "Ć�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 98", "Ć�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#408;", "Ć�")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+0199", "Ć™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 99", "Ć™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#409;", "Ć™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+019A", "Ćš")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 9a", "Ćš")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#410;", "Ćš")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+019B", "Ć›")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 9b", "Ć›")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#411;", "Ć›")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+019C", "Ćś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 9c", "Ćś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#412;", "Ćś")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+019D", "Ćť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 9d", "Ćť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#413;", "Ćť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+019E", "Ćž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 9e", "Ćž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#414;", "Ćž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+019F", "Ćź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 9f", "Ćź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#415;", "Ćź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01A0", "Ć ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 a0", "Ć ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#416;", "Ć ")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+01A1", "Ćˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 a1", "Ćˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#417;", "Ćˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01A2", "Ć˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 a2", "Ć˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#418;", "Ć˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01A3", "ĆŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 a3", "ĆŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#419;", "ĆŁ")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+01A4", "Ć¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 a4", "Ć¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#420;", "Ć¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01A5", "ĆĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 a5", "ĆĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#421;", "ĆĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01A6", "Ć¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 a6", "Ć¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#422;", "Ć¦")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+01A7", "Ć§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 a7", "Ć§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#423;", "Ć§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01A8", "Ć¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 a8", "Ć¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#424;", "Ć¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01A9", "Ć©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 a9", "Ć©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#425;", "Ć©")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+01AA", "ĆŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 aa", "ĆŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#426;", "ĆŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01AB", "Ć«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 ab", "Ć«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#427;", "Ć«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01AC", "Ć¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 ac", "Ć¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#428;", "Ć¬")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+01AD", "Ć­")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 ad", "Ć­")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#429;", "Ć­")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01AE", "Ć®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 ae", "Ć®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#430;", "Ć®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01AF", "ĆŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 af", "ĆŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#431;", "ĆŻ")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+01B0", "Ć°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 b0", "Ć°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#432;", "Ć°")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+01B1", "Ć±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 b1", "Ć±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#433;", "Ć±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01B2", "Ć˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 b2", "Ć˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#434;", "Ć˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01B3", "Ćł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 b3", "Ćł")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("&#435;", "Ćł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01B4", "Ć´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 b4", "Ć´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#436;", "Ć´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01B5", "Ćµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 b5", "Ćµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#437;", "Ćµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01B6", "Ć¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 b6", "Ć¶")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("&#438;", "Ć¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01B7", "Ć·")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("c6 b7", "Ć·")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#439;", "Ć·")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01B8", "Ć¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 b8", "Ć¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#440;", "Ć¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01B9", "Ćą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 b9", "Ćą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#441;", "Ćą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01BA", "Ćş")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("c6 ba", "Ćş")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("&#442;", "Ćş")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01BB", "Ć»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 bb", "Ć»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#443;", "Ć»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01BC", "ĆĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 bc", "ĆĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#444;", "ĆĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01BD", "Ć˝")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 bd", "Ć˝")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("&#445;", "Ć˝")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+01BE", "Ćľ")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("c6 be", "Ćľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#446;", "Ćľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01BF", "Ćż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c6 bf", "Ćż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#447;", "Ćż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01C0", "Ç€")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 80", "Ç€")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#448;", "Ç€")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01C1", "Ç�")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("c7 81", "Ç�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#449;", "Ç�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01C2", "Ç‚")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 82", "Ç‚")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#450;", "Ç‚")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01C3", "Ç�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 83", "Ç�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#451;", "Ç�")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+01C4", "Ç„")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 84", "Ç„")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#452;", "Ç„")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01C5", "Ç…")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 85", "Ç…")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#453;", "Ç…")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01C6", "Ç†")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("c7 86", "Ç†")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#454;", "Ç†")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01C7", "Ç‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 87", "Ç‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#455;", "Ç‡")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01C8", "Ç�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 88", "Ç�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#456;", "Ç�")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+01C9", "Ç‰")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 89", "Ç‰")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#457;", "Ç‰")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01CA", "ÇŠ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 8a", "ÇŠ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#458;", "ÇŠ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01CB", "Ç‹")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 8b", "Ç‹")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#459;", "Ç‹")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01CC", "ÇŚ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 8c", "ÇŚ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#460;", "ÇŚ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01CD", "ÇŤ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 8d", "ÇŤ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#461;", "ÇŤ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01CE", "ÇŽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 8e", "ÇŽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#462;", "ÇŽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01CF", "ÇŹ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 8f", "ÇŹ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#463;", "ÇŹ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01D0", "Ç�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 90", "Ç�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#464;", "Ç�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01D1", "Ç‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 91", "Ç‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#465;", "Ç‘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01D2", "Ç’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 92", "Ç’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#466;", "Ç’")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01D3", "Ç“")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("c7 93", "Ç“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#467;", "Ç“")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01D4", "Ç”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 94", "Ç”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#468;", "Ç”")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01D5", "Ç•")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 95", "Ç•")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#469;", "Ç•")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01D6", "Ç–")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 96", "Ç–")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("&#470;", "Ç–")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01D7", "Ç—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 97", "Ç—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#471;", "Ç—")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01D8", "Ç�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 98", "Ç�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#472;", "Ç�")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01D9", "Ç™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 99", "Ç™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#473;", "Ç™")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01DA", "Çš")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 9a", "Çš")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#474;", "Çš")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01DB", "Ç›")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 9b", "Ç›")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#475;", "Ç›")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+01DC", "Çś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 9c", "Çś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#476;", "Çś")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01DD", "Çť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 9d", "Çť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#477;", "Çť")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01DE", "Çž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 9e", "Çž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#478;", "Çž")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01DF", "Çź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 9f", "Çź")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#479;", "Çź")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+01E0", "Ç ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 a0", "Ç ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#480;", "Ç ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01E1", "Çˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 a1", "Çˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#481;", "Çˇ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01E2", "Ç˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 a2", "Ç˘")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#482;", "Ç˘")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+01E3", "ÇŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 a3", "ÇŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#483;", "ÇŁ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01E4", "Ç¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 a4", "Ç¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#484;", "Ç¤")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01E5", "ÇĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 a5", "ÇĄ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#485;", "ÇĄ")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+01E6", "Ç¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 a6", "Ç¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#486;", "Ç¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01E7", "Ç§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 a7", "Ç§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#487;", "Ç§")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01E8", "Ç¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 a8", "Ç¨")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#488;", "Ç¨")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+01E9", "Ç©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 a9", "Ç©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#489;", "Ç©")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01EA", "ÇŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 aa", "ÇŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#490;", "ÇŞ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01EB", "Ç«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 ab", "Ç«")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#491;", "Ç«")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+01EC", "Ç¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 ac", "Ç¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#492;", "Ç¬")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01ED", "Ç­")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 ad", "Ç­")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#493;", "Ç­")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+01EE", "Ç®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 ae", "Ç®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#494;", "Ç®")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01EF", "ÇŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 af", "ÇŻ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#495;", "ÇŻ")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+01F0", "Ç°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 b0", "Ç°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#496;", "Ç°")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01F1", "Ç±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 b1", "Ç±")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#497;", "Ç±")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+01F2", "Ç˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 b2", "Ç˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#498;", "Ç˛")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01F3", "Çł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 b3", "Çł")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#499;", "Çł")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+01F4", "Ç´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 b4", "Ç´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#500;", "Ç´")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01F5", "Çµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 b5", "Çµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#501;", "Çµ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01F6", "Ç¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 b6", "Ç¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#502;", "Ç¶")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01F7", "Ç·")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 b7", "Ç·")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#503;", "Ç·")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+01F8", "Ç¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 b8", "Ç¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#504;", "Ç¸")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01F9", "Çą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 b9", "Çą")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#505;", "Çą")).trim();			
				etapaIntermediara = (etapaIntermediara.replace("U+01FA", "Çş")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 ba", "Çş")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#506;", "Çş")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+01FB", "Ç»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 bb", "Ç»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#507;", "Ç»")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01FC", "ÇĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 bc", "ÇĽ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#508;", "ÇĽ")).trim();	
				etapaIntermediara = (etapaIntermediara.replace("U+01FD", "Ç˝")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 bd", "Ç˝")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#509;", "Ç˝")).trim();		
				etapaIntermediara = (etapaIntermediara.replace("U+01FE", "Çľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 be", "Çľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#510;", "Çľ")).trim();
				etapaIntermediara = (etapaIntermediara.replace("U+01FF", "Çż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("c7 bf", "Çż")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#511;", "Çż")).trim();


				etapaIntermediara = (etapaIntermediara.replace("U+1FE6", "áż¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#8166;", "áż¦")).trim();
				etapaIntermediara = (etapaIntermediara.replace("&#x1FE6;", "áż¦")).trim();



				String finalLine = etapaIntermediara.trim();

				if (finalLine != null)finalLine = finalLine.trim();

				if (finalLine.endsWith("\n")){
					int index = finalLine.lastIndexOf("\n");	
					finalLine  = finalLine.substring(0,index);
				}

				if (finalLine != null)finalLine = finalLine.trim();

				if (finalLine.startsWith("\n")){
					int index = finalLine.indexOf("\n") + "\n".length();	
					finalLine  = finalLine.substring(index,finalLine.length());
				}

				if (finalLine != null)finalLine = finalLine.trim();

				return finalLine;
			}
		}catch (Exception exception){
			exception.printStackTrace();
			System.err.println("Error: " + exception.getStackTrace());
			System.err.println("Error: " + exception.fillInStackTrace());
			System.err.println("Error: " + exception.getLocalizedMessage());
			System.err.println("Error: " + exception.getMessage());
			System.err.println("Error: " + exception.getCause());

			return null;
		}
		return null;
	}
	private String _eliminTextul(String a[], String textul){

		String textReturnat = textul;

		boolean apelez = true;
		for(String initial : a) if (!(textReturnat.contains(initial)))apelez = false;
		if(apelez) textReturnat = (eliminTextulDintreCeleDouaTexteMinusPrimulCaracterGhilimea(a[0], a[a.length-1], textReturnat)).trim();

		apelez = true;
		for(String litereMici : a) if (!(textReturnat.contains(litereMici.toLowerCase())))apelez = false;
		if(apelez) textReturnat = (eliminTextulDintreCeleDouaTexteMinusPrimulCaracterGhilimea(a[0].toLowerCase(), a[a.length-1].toLowerCase(), textReturnat)).trim();


		apelez = true;
		for(String litereMari : a) if (!(textReturnat.contains(litereMari.toUpperCase())))apelez = false;
		if(apelez) textReturnat = (eliminTextulDintreCeleDouaTexteMinusPrimulCaracterGhilimea(a[0].toUpperCase(), a[a.length-1].toUpperCase(), textReturnat)).trim();

		return textReturnat.trim();
	}
	private String eliminTextulDintreCeleDouaTexteMinusPrimulCaracterGhilimea(String text1, String text2, String totTextul){

		String textReturnat = totTextul;
		if (textReturnat.contains(text1) && textReturnat.contains(text2)){

			while (textReturnat.contains(text1) && textReturnat.contains(text2)){

				int index1 = textReturnat.indexOf(text1);

				String restul = textReturnat.substring(index1 + text1.length(), textReturnat.length());
				int index2 = index1 + text1.length() + restul.indexOf(text2) + text2.length();
				if((index1 > 0) && (index2 > 0) && (index1 < index2)) {

					String primuaParte = textReturnat.substring(0, index1 - 1);
					String aDouaParte = textReturnat.substring(index2, textReturnat.length());
					textReturnat = primuaParte + aDouaParte;
				}
			}
		}
		return textReturnat;
	}
	private  String respectaFontul(String text){
		String textReturnat = text;
		if ((textReturnat.contains("divine-name")) && (textReturnat.contains(">"))){

			while ((textReturnat.contains("divine-name")) && (textReturnat.contains(">"))){

				int index1 = textReturnat.indexOf("divine-name");

				String primuaParte = textReturnat.substring(0, index1 - 1);
				String aDouaParte = textReturnat.substring(index1 + "divine-name".length() + 2, textReturnat.length());

				int indexBlanc = 0;

				if (aDouaParte.contains(" ")) 
				{
					indexBlanc = aDouaParte.indexOf(" ");
					String aDouaParte1 = aDouaParte.substring(0, indexBlanc);
					aDouaParte1 = aDouaParte1.toUpperCase();
					String aDouaParte2 = aDouaParte.substring(indexBlanc, aDouaParte.length());
					aDouaParte = aDouaParte1 + aDouaParte2;
				}
				else aDouaParte = aDouaParte.toUpperCase();

				textReturnat = primuaParte + aDouaParte;
			}
		}
		return textReturnat;
	}
	private String scrieFontLitereMari(String totTextul, String fontul1, String fontul2){

		String textul = totTextul;
		while (textul.contains(fontul1) && textul.contains(fontul2)){

			int index1 = textul.indexOf(fontul1);
			String restul = textul.substring(index1 + fontul1.length(), textul.length());
			int noulIndex = 0;
			if (restul.contains(fontul2)){
				noulIndex = restul.indexOf(fontul2);
				int index2 = index1 + fontul1.length() + noulIndex;

				if((index1 >= 0) && (index2 > 0) && (index1 < index2)){

					String primulString = textul.substring(0, index1);
					String alDoileaString = textul.substring(index1 + fontul1.length(), index2);
					String alTreileaString = textul.substring(index2 + fontul2.length(), textul.length());

					textul = primulString + alDoileaString.toUpperCase() + alTreileaString;
					scrieFontLitereMari(textul, fontul1, fontul2);
				}
			}
			else {
				noulIndex = restul.length();
				int index2 = index1 + fontul1.length() + noulIndex;

				if((index1 >= 0) && (index2 > 0) && (index1 < index2)){

					String primulString = textul.substring(0, index1);
					String alDoileaString = textul.substring(index1 + fontul1.length(), index2);
					String alTreileaString = textul.substring(index2 , textul.length());

					textul = primulString + alDoileaString.toUpperCase() + alTreileaString;
					scrieFontLitereMari(textul, fontul1, fontul2);
				}
			}
		}
		return textul;
	}
	private void scriptul_2(String allBibles_2, Set<String> problem){

		try{

			final File biblii = new File(allBibles_2);

			for (File caleBiblie : biblii.listFiles()){
				if (caleBiblie != null && caleBiblie.isDirectory()){

					final File biblia = new File(((caleBiblie.getAbsolutePath()).toString()).trim());
					File [] cartiBiblie = biblia.listFiles();
					if (cartiBiblie != null && cartiBiblie.length > 0) {
						String versionName = caleBiblie.getName().trim();

						if(versionName != null && !versionName.trim().isEmpty()){

							versionName = versionName.trim();

							Version version = new Version();
							version.setValue(versionName);

							List<Book> books = new ArrayList<>();
							version.setBooks(books);

							Map<String, String> mapCarte = new HashMap<>();
							for (File index : cartiBiblie){
								if (index.isFile() && index.getName().trim().equalsIgnoreCase("index.htm")){	
									citesteIndex((index.getPath()).trim(), versionName, mapCarte, problem);
								}
							}

							if(mapCarte != null && !mapCarte.isEmpty()){

								for (File carte : cartiBiblie){

									if(carte != null){

										String bookNo = carte.getName().trim();

										if (carte.isDirectory() && bookNo.matches("\\d+") && mapCarte.containsKey(bookNo)){

											Book book = new Book();

											List<Chapter> chapters = new ArrayList<>();

											book.setVersion(version);
											book.setValue(mapCarte.get(bookNo).trim());
											book.setBookNo(Integer.parseInt(bookNo));
											book.setChapters(chapters);
											books.add(book);

											for (File capitol : carte.listFiles()){
												if (capitol.isFile() && (daNumarCapitol((capitol.getPath()).trim()).trim()).matches("\\d+")){

													String filePath = capitol.getPath().trim();

													String chapterNo = curataNumar(daNumarCapitol(filePath).trim()).trim();
													String entireChapter = citestePrelucreazaContinutFisier(filePath, versionName, problem, mapCarte);

													Chapter chapter = new Chapter();
													chapter.setBook(book);
													chapter.setValue(Integer.parseInt(chapterNo));
													chapters.add(chapter);

													if(entireChapter != null && !entireChapter.trim().isEmpty()){

														chapter.setEntireChapter(entireChapter);
													}
													else{
														System.out.println(filePath);
														System.out.println("end entire chapter");
													}
												}
											}
										}
									}
								}
								if(version != null){
									try{
										dao.isSaveOrUpdateVersion(version);
										createDB(version);
									}
									catch(Throwable e){
										problem.add(version.getValue());
										//e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e){

		}
	}
	private String citestePrelucreazaContinutFisier(String caleFisier, String versionName, Set<String> problem, Map<String, String> mapCarte){
		String ttt = null;
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(caleFisier), StandardCharsets.UTF_8));){

			String strLine;
			String[]  caractereEliminate   = {"p><SPAN class", "id=", ">"};
			String[]  caractereEliminate2  = {"br><SPAN class", "id=", ">"};
			String[]  caractereEliminate3  = {"p align=", "dir=", ">"};
			String[]  caractereEliminate4  = {"span class=", "id=", ">"};
			String[]  caractereEliminate5  = {"span class=", "person", ">"};
			String[]  caractereEliminate6  = {"br", "><span class=", "id=",">"};
			String[]  caractereEliminate7  = {"SPAN class=", "id=",">"};
			String[]  caractereEliminate8  = {"a name=",">"};
			String[]  caractereEliminate9  = {"span class=", "place", ">"};
			String[]  caractereEliminate10 = {"SPAN class=", "person", ">"};
			String[]  caractereEliminate11 = {"SPAN class=", "place", ">"};
			String[]  caractereEliminate12 = {"div class=", "bd", ">"};
			String[]  caractereEliminate13 = {"DIV class=", ">"};
			String[]  caractereEliminate14 = {"FONT COLOR=", ">"};
			String[]  caractereEliminate15 = {"/dl", "compact></p", ">"};

			StringBuilder continut = new StringBuilder();

			boolean copyText = false;

			while ((strLine = br.readLine()) != null){ttt = strLine;

			if(strLine.contains("<!--... sharper than any twoedged sword... -->")){
				copyText = false;
				break;
			}

			if (!strLine.isEmpty() && copyText && !strLine.equalsIgnoreCase("</p>") && !strLine.equalsIgnoreCase("<\n>") && !strLine.equalsIgnoreCase("<br>") && !strLine.equalsIgnoreCase("</a></p>") && strLine.contains("class")){

				if (strLine.contains("<span class=\"smallcaps\">") && strLine.contains("</span>")) strLine = scrieFontLitereMari(strLine, "<span class=\"smallcaps\">", "</span>");		
				if (strLine.contains("\"smallcaps\">") && strLine.contains("</span>"))             strLine = scrieFontLitereMari(strLine, "\"smallcaps\">", "</span>");
				if (strLine.contains("\"smallcaps\">") && strLine.contains(" "))                   strLine = scrieFontLitereMari(strLine, "\"smallcaps\">", " ");
				if (strLine.contains(" </SPAN> ") && strLine.substring(strLine.indexOf(" </SPAN> ") - 1, strLine.indexOf(" </SPAN> ")).matches("\\d+")) strLine = (strLine.replace(" </SPAN> ", ". "));
				if (strLine.contains("  </span>") && (strLine.indexOf("  </span>") > 0) && strLine.substring(strLine.indexOf("  </span>") - 1, strLine.indexOf("  </span>")).matches("\\d+")) strLine = (strLine.replace("  </span>", ". "));
				if (strLine.contains(" </span>")  && strLine.substring(strLine.indexOf(" </span>") - 1, strLine.indexOf(" </span>")).matches("\\d+"))   strLine = (strLine.replace(" </span>", ". "));
				if (strLine.contains(" </SPAN>")  && (strLine.indexOf(" </SPAN>") > 0) && strLine.substring(strLine.indexOf(" </SPAN>") - 1, strLine.indexOf(" </SPAN>")).matches("\\d+"))   strLine = (strLine.replace(" </SPAN>", ". "));
				if (strLine.contains("</SPAN>")   && strLine.substring(strLine.indexOf("</SPAN>") - 1, strLine.indexOf("</SPAN>")).matches("\\d+"))     strLine = (strLine.replace("</SPAN>", "."));
				if (strLine.contains("</span>")   && (strLine.indexOf("</span>") > 0) && strLine.substring(strLine.indexOf("</span>") - 1, strLine.indexOf("</span>")).matches("\\d+"))     strLine = (strLine.replace("</span>", "."));
				if (strLine.contains("&nbsp;")) strLine = strLine.replace("&nbsp;", "");
				if (strLine.contains("&nbsp;")) strLine = curataText(strLine, "&nbsp;");

				strLine = _eliminTextul(caractereEliminate11, strLine, "\n").trim();
				strLine = _eliminTextul(caractereEliminate10, strLine, "\n").trim();
				strLine = _eliminTextul(caractereEliminate, strLine, "\n").trim();
				strLine = _eliminTextul(caractereEliminate2,strLine, "\n").trim();
				strLine = _eliminTextul(caractereEliminate3,strLine, "\n").trim();
				strLine = _eliminTextul(caractereEliminate4,strLine, "\n").trim();
				strLine = _eliminTextul(caractereEliminate5,strLine, "\n").trim();
				strLine = _eliminTextul(caractereEliminate6,strLine, "\n").trim();
				strLine = _eliminTextul(caractereEliminate7,strLine, "\n").trim();
				strLine = _eliminTextul(caractereEliminate8,strLine, "\n").trim();
				strLine = _eliminTextul(caractereEliminate9,strLine, "\n").trim();
				strLine = _eliminTextul(caractereEliminate12,strLine, "\n").trim();
				strLine = _eliminTextul(caractereEliminate13,strLine, "\n").trim();					

				strLine = _eliminTextul(caractereEliminate14,strLine, null).trim();
				strLine = _eliminTextul(caractereEliminate15,strLine, null).trim();
				strLine = (strLine.replace("</a><br>", "")).trim();	
				strLine = curataText(strLine, "</a><br>").trim();
				strLine = (strLine.replace("<br/>", "")).trim();	
				strLine = curataText(strLine, "<br/>").trim();
				strLine = (strLine.replace("	", " ")).trim();
				strLine = (strLine.replace("<br>", "")).trim();	
				strLine = curataText(strLine, "<br>").trim();
				strLine = (strLine.replace("<p>", "")).trim();	
				strLine = curataText(strLine, "<p>").trim();
				strLine = (strLine.replace("</a>", "")).trim();	
				strLine = curataText(strLine, "</a>").trim();
				strLine = (strLine.replace("<br />", "")).trim();	
				strLine = curataText(strLine, "<br />").trim();


				if (strLine != null && !strLine.trim().isEmpty() && !strLine.trim().equalsIgnoreCase("\n")&& !strLine.trim().equalsIgnoreCase("<br>")){

					try{

						String no = strLine.substring(0, strLine.indexOf(".")).trim();

						if(no.matches("\\d+")){
							continut.append(no + ".  " + strLine.substring(strLine.indexOf(".") + 1).trim() + "\n");
						}
						else {
							System.out.println(caleFisier);
							System.out.println("end index error");
						}

					} catch(Exception exc){
						System.out.println(caleFisier);
						System.out.println("end substring error");
					}
				}
			}
			if(strLine.contains("<!--... the Word of God:-->")){
				copyText = true;
			}
			}
			String ramas1 = continut.toString().trim();

			if (ramas1 != null) ramas1 = eliminaCaractere( ramas1).trim();

			String etapaIntermediara = "";
			String liniaAnterioara = "";

			boolean primaLinie = true;
			int numarLiniile = 0; 

			String [] buferTemporarCapitol = (ramas1.trim()).split("\\n");

			for (String line : buferTemporarCapitol) {
				++numarLiniile;

				if ((line != null) && (!line.isEmpty()) && (!(line.trim()).isEmpty()) && (((line.trim()).length()>0))){
					String nouaLinie = line.trim();

					String[]  caractere1 = {"footnote" , "</sup>"};
					nouaLinie = _eliminTextul(caractere1, nouaLinie, null).trim();				

					String[]  caractere2 = {"<p class=" , "regular", ">"};
					nouaLinie = _eliminTextul(caractere2, nouaLinie, null).trim();

					String[]  caractere3 = {"block-indent" , ">"};
					nouaLinie = _eliminTextul(caractere3, nouaLinie, null).trim();

					String[]  caractere4 = {"p class" , "line-group", ">"};
					nouaLinie = _eliminTextul(caractere4, nouaLinie, null).trim();

					String[]  caractere5 = {"line", ">"};
					nouaLinie = _eliminTextul(caractere5, nouaLinie, null).trim();

					String[]  caractere6 = {"<p class=", "same-paragraph", ">"};
					nouaLinie = _eliminTextul(caractere6, nouaLinie, null).trim();

					String[]  caractere7 = {"ln-indent", ">"};
					nouaLinie = _eliminTextul(caractere7, nouaLinie, null).trim();

					String[]  caractere8 = {"woc", ">"};
					nouaLinie = _eliminTextul(caractere8, nouaLinie, null).trim();

					String[]  caractere9 = {"BLOCK-INDENT", "P class="};
					nouaLinie = _eliminTextul(caractere9, nouaLinie, null).trim();

					if (nouaLinie.contains("&nbsp;") || nouaLinie.contains("&nbsp;".toUpperCase())|| nouaLinie.contains("&nbsp;".toLowerCase())) nouaLinie = (nouaLinie.replace("&nbsp;", "")).trim();

					String[]  caractere10 = {"p class=", "LISTD", ">"};
					nouaLinie = _eliminTextul(caractere10, nouaLinie, null).trim();

					String[]  caractere11 = {"p class=", "NPSTHALF", ">"};
					nouaLinie = _eliminTextul(caractere11, nouaLinie, null).trim();

					String[]  caractere12 = {"p class=", "TXTTWO", ">"};
					nouaLinie = _eliminTextul(caractere12, nouaLinie, null).trim();

					String[]  caractere14 = {"p class=" , "acrostic", ">"};
					nouaLinie = _eliminTextul(caractere14, nouaLinie, null).trim();

					String[]  caractere15 = {"\"\">" , "\">"};
					nouaLinie = _eliminTextul(caractere15, nouaLinie, null).trim();

					nouaLinie = respectaFontul(nouaLinie).trim();

					if (!primaLinie) {
						if ((nouaLinie != null) && (!nouaLinie.isEmpty()) && (!(nouaLinie.trim()).isEmpty())){
							if ((liniaAnterioara != null) && (!liniaAnterioara.isEmpty()) && (!(liniaAnterioara.trim()).isEmpty())){
								if (!nouaLinie.contains(".")) liniaAnterioara = liniaAnterioara.trim() + " " + nouaLinie.trim();
								else if (nouaLinie.contains(".") && !((nouaLinie.trim()).substring(0, (nouaLinie.trim()).indexOf("."))).matches("\\d+")) liniaAnterioara = liniaAnterioara.trim() + " " + nouaLinie.trim();
								else {
									if (numarLiniile == buferTemporarCapitol.length){
										etapaIntermediara = etapaIntermediara.trim() + liniaAnterioara.trim();
										if (nouaLinie.contains(".") && ((nouaLinie.trim()).substring(0, (nouaLinie.trim()).indexOf("."))).matches("\\d+")) etapaIntermediara = etapaIntermediara.trim() + "\n" + nouaLinie.trim();
										break;
									}
									else {
										etapaIntermediara = etapaIntermediara.trim() + liniaAnterioara.trim() + "\n" + "===+++===" + "\r";
										liniaAnterioara = nouaLinie.trim();
										continue;
									}
								}
							}
							else {
								liniaAnterioara = nouaLinie.trim();
								continue;
							}
						}
						else {
							if (numarLiniile == buferTemporarCapitol.length){
								if ((liniaAnterioara != null) && (!liniaAnterioara.isEmpty()) && (!(liniaAnterioara.trim()).isEmpty())) etapaIntermediara = etapaIntermediara.trim() + liniaAnterioara.trim();
								break;	
							}
							else continue;
						}
					}
					else{
						if (numarLiniile == buferTemporarCapitol.length){
							if ((nouaLinie != null) && (!nouaLinie.isEmpty()) && (!(nouaLinie.trim()).isEmpty())) etapaIntermediara = etapaIntermediara.trim() + nouaLinie.trim();
							break;	
						}
						else {
							liniaAnterioara = nouaLinie.trim();
							primaLinie = false;
							continue;
						}
					}
					if (numarLiniile == buferTemporarCapitol.length){
						if ((liniaAnterioara != null) && (!liniaAnterioara.isEmpty()) && (!(liniaAnterioara.trim()).isEmpty())) etapaIntermediara = etapaIntermediara.trim() + liniaAnterioara.trim();
						break;	
					}
				}
			}

			String fisierFinal = corecturiComplexe(etapaIntermediara).trim();

			if (fisierFinal != null)fisierFinal = fisierFinal.trim();

			if (fisierFinal.endsWith("\n")){
				int index = fisierFinal.lastIndexOf("\n");	
				fisierFinal  = fisierFinal.substring(0,index);
			}

			if (fisierFinal != null)fisierFinal = fisierFinal.trim();

			if (fisierFinal.startsWith("\n")){
				int index = fisierFinal.indexOf("\n") + "\n".length();	
				fisierFinal  = fisierFinal.substring(index,fisierFinal.length());
			}

			if (fisierFinal != null)fisierFinal = fisierFinal.trim();

			return eliminaGhilimele(fisierFinal.trim()).trim().replaceAll("\\<.*?>","").trim().replace("\n", "<br>").trim();

		}catch (Exception exception){
			problem.add(versionName);
			System.out.println(caleFisier);		
			System.out.println(ttt);
			exception.printStackTrace();
			System.err.println("Error: " + exception.getStackTrace());
			System.err.println("Error: " + exception.fillInStackTrace());
			System.err.println("Error: " + exception.getLocalizedMessage());
			System.err.println("Error: " + exception.getMessage());
			System.err.println("Error: " + exception.getCause());
		}
		return null;
	}
	private  String eliminaGhilimele (String ramas1){

		if (ramas1.contains("&8220;"))ramas1 = ramas1.replace("&8220;", "\"");
		if (ramas1.contains("&8221;"))ramas1 = ramas1.replace("&8221;", "\"");	

		return ramas1;
	}

	private String curataNumar(String numarCapitol){

		String numar = (numarCapitol.toString()).trim();
		String buffer = "";
		for (int index = 0 ; index < numar.length() ; index++) {
			if ((numar.substring(index, index+1)).matches(".*[0-9].*"))	buffer += numar.charAt(index);
		}
		numar = buffer;
		while (numar.contains(" ")){
			numar = curataText(numar, " ");		
		}
		return numar.trim();
	}

	private String daNumarCapitol(String path){

		String Bufer = path.replace("\\", "-_-");
		String[] elemente = Bufer.split("-_-");
		String[] capitolSiExtensie = (elemente[elemente.length-1]).split("\\.");
		return capitolSiExtensie[0].trim();
	}
	private  void citesteIndex(String caleIndex, String numeVersiune, Map<String, String> mapCarte, Set<String> problem){

		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(caleIndex), StandardCharsets.UTF_8));
			String strLine;	
			String[]  caractereEliminate   = {"div class=", ">"};
			String[]  caractereEliminate0   = {"DIV class=", ">"};
			String[]  caractereEliminate2  = {"h3>", "<span class=", "</span></h3", ">"};
			String[]  caractereEliminate3  = {"ul class=", ">"};
			String[]  caractereEliminate4  = {"h3>", "</h3", ">"};
			StringBuilder continut = new StringBuilder();
			while ((strLine = br.readLine()) != null){
				if ((!strLine.isEmpty()) && (strLine.length() > 0)){
					strLine = _eliminTextul(caractereEliminate, strLine, "\n").trim();
					strLine = _eliminTextul(caractereEliminate0, strLine, "\n").trim();
					strLine = _eliminTextul(caractereEliminate2,strLine, "\n").trim();
					strLine = _eliminTextul(caractereEliminate3,strLine, null).trim();
					strLine = _eliminTextul(caractereEliminate4,strLine, null).trim();
					strLine = (strLine.replace("</ul>", "")).trim();
					strLine = curataText(strLine, "</ul>");
					strLine = (strLine.replace("</div>", "")).trim();
					strLine = curataText(strLine, "</div>");

					if ((strLine != null) && !strLine.isEmpty() && !strLine.trim().isEmpty() && !strLine.trim().equalsIgnoreCase("\n")){	
						continut.append(strLine + "\n");
					}
				}
			}
			String ramas1 = pastrezDeLaPanaLa("<!-- chapter list -->", "<!-- end chapter list -->", continut.toString().trim());
			try{
				int numarCarte = 0;
				for (String line : (ramas1.trim()).split("\\n")) {
					if ((line != null) && (!line.isEmpty()) && (!(line.trim()).isEmpty()) && (((line.trim()).length()>0)) && line.contains("[") && line.contains("]")){
						String indexCarte = pastrezDeLaPanaLa("[", "]", line.toString().trim());
						String numeCarte = "";
						if (line.contains("<a href=") && line.indexOf("[") < line.indexOf("<a href=")){
							line = line.substring(line.indexOf(indexCarte), line.length());
							numeCarte = line.substring(line.indexOf(">") + 1, line.indexOf("<i class=")).trim();
						}
						else if (line.contains("<A href=") && line.indexOf("[") < line.indexOf("<A href=")){
							line = line.substring(line.indexOf(indexCarte), line.length());
							numeCarte = line.substring(line.indexOf(">") + 1, line.indexOf("<i class=")).trim();
						}
						else numeCarte = line.substring(line.indexOf("]") + 1, line.indexOf("<i class=")).trim();

						if (numeCarte.contains("/")){
							numeCarte = (numeCarte.replace("/", "")).trim();
							numeCarte = curataText(numeCarte, "/");
						}
						if (indexCarte.length() == 1) indexCarte = "0" + indexCarte.trim();

						if (numeCarte.contains(".")) {
							numeCarte = numeCarte.replace(".", "");
							numeCarte = curataText(numeCarte, ".");
						}
						numeCarte = numeCarte.trim();
						if (!mapCarte.containsValue(numeCarte))mapCarte.put(indexCarte,numeCarte);
						else {
							while (mapCarte.containsValue(numeCarte)){
								numeCarte =	numeCarte + "_";
								numeCarte = numeCarte.trim();
							}
							mapCarte.put(indexCarte,numeCarte);
						}
					}
					else if (numeVersiune.equalsIgnoreCase("הקודש במקרא - עברית - Hebrew") || numeVersiune.equalsIgnoreCase("الكتاب المقدس") || numeVersiune.equalsIgnoreCase("انجیل مقدس") || numeVersiune.equalsIgnoreCase("کتاب مقدس Farsi Bible - Persian - Tarjumeh-ye Ghadeem 1896") || numeVersiune.equalsIgnoreCase("ಪವಿತ್ರ ಬೈಬಲ್ - Kannada Bible") || numeVersiune.equalsIgnoreCase("성경 Korean Bible") || numeVersiune.equalsIgnoreCase("Βίβλος - Greek Bible") || numeVersiune.equalsIgnoreCase("पवित्र बाइबिल - Hindi Holy Bible") || numeVersiune.equalsIgnoreCase("પવિત્ર બાઇબલ - Gujarati Holy Bible")){
						++numarCarte;
						String indexCarte = Integer.toString(numarCarte);
						if (indexCarte.length() == 1) indexCarte = "0" + indexCarte.trim();
						mapCarte.put(indexCarte,line.trim());
					}
				}
			}
			catch (Throwable e){
				problem.add(numeVersiune);
			}
			br.close();
		}catch (Exception exception){
			problem.add(numeVersiune);
			//			exception.printStackTrace();
			//			System.err.println("Error: " + exception.getStackTrace());
			//			System.err.println("Error: " + exception.fillInStackTrace());
			//			System.err.println("Error: " + exception.getLocalizedMessage());
			//			System.err.println("Error: " + exception.getMessage());
			//			System.err.println("Error: " + exception.getCause());
		}
	}
	private  String _eliminTextul(String a[], String textul, String caracterAdaugat){
		boolean contineTot = true;		
		if ((textul != null) && (!textul.isEmpty()) && (!(textul.trim()).isEmpty())){
			for (String elimina : a){
				if(!textul.toLowerCase().contains(elimina.toLowerCase())) {
					contineTot = false;
					break;
				}
			}
		}
		else contineTot = false;

		String textReturnat = textul;
		if (contineTot){
			boolean apelez = true;
			for(String initial : a) if (!(textReturnat.contains(initial)))apelez = false;
			if(apelez) textReturnat = (eliminTextulDintreCeleDouaTexteMinusPrimulCaracterGhilimea(a[0], a[a.length-1], textReturnat, caracterAdaugat)).trim();

			apelez = true;
			for(String litereMici : a) if (!(textReturnat.contains(litereMici.toLowerCase())))apelez = false;
			if(apelez) textReturnat = (eliminTextulDintreCeleDouaTexteMinusPrimulCaracterGhilimea(a[0].toLowerCase(), a[a.length-1].toLowerCase(), textReturnat, caracterAdaugat)).trim();

			apelez = true;
			for(String litereMari : a) if (!(textReturnat.contains(litereMari.toUpperCase())))apelez = false;
			if(apelez) textReturnat = (eliminTextulDintreCeleDouaTexteMinusPrimulCaracterGhilimea(a[0].toUpperCase(), a[a.length-1].toUpperCase(), textReturnat, caracterAdaugat)).trim();
			textReturnat = textReturnat.trim();
		}
		return textReturnat;
	}
	private String eliminTextulDintreCeleDouaTexteMinusPrimulCaracterGhilimea(String text1, String text2, String totTextul, String caracterAdaugat){

		String textReturnat = totTextul;
		if (textReturnat.contains(text1) && textReturnat.contains(text2)){

			while (textReturnat.contains(text1) && textReturnat.contains(text2)){

				int index1 = textReturnat.indexOf(text1);

				String restul = textReturnat.substring(index1 + text1.length(), textReturnat.length());
				int index2 = index1 + text1.length() + restul.indexOf(text2) + text2.length();

				if((index1 > 0) && (index2 > 0) && (index1 < index2)) {
					String primaParte = textReturnat.substring(0, index1 - 1);
					String aDouaParte = textReturnat.substring(index2, textReturnat.length());
					if (caracterAdaugat != null) textReturnat = primaParte + " " + caracterAdaugat + " " + aDouaParte;
					else textReturnat = primaParte + aDouaParte;
				}
				else if((index1 == 0) && (index2 > 0)) {
					String aDouaParte = textReturnat.substring(index2, textReturnat.length());
					if (caracterAdaugat != null) textReturnat = " " + caracterAdaugat + " " + aDouaParte;
					else textReturnat = aDouaParte;
				}
			}
		}
		return textReturnat;
	}
	private String eliminaCaractere(String ramas1){
		ramas1 = curataText(ramas1, "</td>").trim();
		ramas1 = (ramas1.replace("</td>", "")).trim();
		ramas1 = curataText(ramas1, "<td>").trim();
		ramas1 = (ramas1.replace("<td>", "")).trim();
		ramas1 = curataText(ramas1, "<td align=\"right\">").trim();
		ramas1 = (ramas1.replace("<td align=\"right\">", "")).trim();
		ramas1 = curataText(ramas1, "</tr>").trim();
		ramas1 = (ramas1.replace("</tr>", "")).trim();
		ramas1 = curataText(ramas1, "<tr>").trim();
		ramas1 = (ramas1.replace("<tr>", "")).trim();
		ramas1 = curataText(ramas1, "<td\"right\">").trim();
		ramas1 = (ramas1.replace("<td\"right\">", "")).trim();
		ramas1 = ramas1.replace("</b></a></span>", ". ");
		ramas1 = curataText(ramas1, "<b>");
		ramas1 = curataText(ramas1, "<head"); //System.out.println(1);
		ramas1 = curataText(ramas1, "<br>");//System.out.println(2);
		ramas1 = curataText(ramas1, "<a href=");//System.out.println(3);
		ramas1 = curataText(ramas1, "</div>");//System.out.println(4);
		ramas1 = curataText(ramas1, "</ul>");//System.out.println(5);
		ramas1 = curataText(ramas1, "<!--");//System.out.println(6);
		ramas1 = curataText(ramas1, "Sitemap");//System.out.println(7);
		ramas1 = curataText(ramas1, "@ 2012");//System.out.println(8);
		ramas1 = curataText(ramas1, "footer");//System.out.println(9);
		ramas1 = curataText(ramas1, "http:");//System.out.println(10);
		ramas1 = curataText(ramas1, "-->");//System.out.println(11);
		ramas1 = curataText(ramas1, "#FFFFFF");//System.out.println(12);
		ramas1 = curataText(ramas1, "#");//System.out.println(13);
		ramas1 = curataText(ramas1, "</script>");//System.out.println(14);
		ramas1 = curataText(ramas1, "<script>");//System.out.println(15);
		ramas1 = curataText(ramas1, "<li>");//System.out.println(16);
		ramas1 = curataText(ramas1, "cellspacing=");//System.out.println(17);
		ramas1 = curataText(ramas1, "<!-- /rbcontent -->");//System.out.println(18);
		ramas1 = curataText(ramas1, "sidebar");//System.out.println(19);
		ramas1 = curataText(ramas1, "<li class=");//System.out.println(20);
		ramas1 = curataText(ramas1, "page_item");//System.out.println(21);
		ramas1 = curataText(ramas1, "><a title");//System.out.println(22);
		ramas1 = curataText(ramas1, "</a></li>");//System.out.println(23);
		ramas1 = curataText(ramas1, "<td valign=");//System.out.println(24);
		ramas1 = curataText(ramas1, " align=");//System.out.println(25);
		ramas1 = curataText(ramas1, "width");//System.out.println(26);
		ramas1 = curataText(ramas1, "<p>");//System.out.println(27);
		ramas1 = curataText(ramas1, "<!--");//System.out.println(29);
		ramas1 = curataText(ramas1, "topbar");//System.out.println(30);
		ramas1 = curataText(ramas1, "<div id=");//System.out.println(31);
		ramas1 = curataText(ramas1, "footer");//System.out.println(32);
		ramas1 = curataText(ramas1, "</head>");//System.out.println(33);
		ramas1 = curataText(ramas1, "<head>");//System.out.println(34);
		ramas1 = curataText(ramas1, "</body>");//System.out.println(35);
		ramas1 = curataText(ramas1, "<!--");//System.out.println(36);
		ramas1 = curataText(ramas1, "if IE 6]>");//System.out.println(361);
		ramas1 = curataText(ramas1, "xfn");//System.out.println(37);
		ramas1 = curataText(ramas1, "gmpg.org");//System.out.println(38);
		ramas1 = curataText(ramas1, "profile=");//System.out.println(39);
		ramas1 = curataText(ramas1, "xhtml");//System.out.println(40);
		ramas1 = curataText(ramas1, "www.w3.org");//System.out.println(41);
		ramas1 = curataText(ramas1, "http:");//System.out.println(42);
		ramas1 = curataText(ramas1, "<html xmlns");//System.out.println(43);
		ramas1 = curataText(ramas1, "W3C ");//System.out.println(44);
		ramas1 = curataText(ramas1, "<DTD XHTML 1.0 Transitional");//System.out.println(45);
		ramas1 = curataText(ramas1, "<http:");//System.out.println(46);
		ramas1 = curataText(ramas1, "www.w3.org");//System.out.println(47);
		ramas1 = curataText(ramas1, "<xhtml1");//System.out.println(48);
		ramas1 = curataText(ramas1, "xhtml1-transitional.dtd");//System.out.println(49);
		ramas1 = curataText(ramas1, "<p>");//System.out.println(51);
		ramas1 = curataText(ramas1, "</ul>");//System.out.println(52);
		ramas1 = curataText(ramas1, "<div id=");//System.out.println(53);
		ramas1 = curataText(ramas1, "narrowcolumn");//System.out.println(54);
		ramas1 = curataText(ramas1, "<div class=");//System.out.println(55);
		ramas1 = curataText(ramas1, "post-87");//System.out.println(56);
		ramas1 = curataText(ramas1, "entrytext");//System.out.println(57);
		ramas1 = curataText(ramas1, "www.gtcentralrotary.org");//System.out.println(58);
		ramas1 = curataText(ramas1, "dimensiune font titlu");//System.out.println(59);
		ramas1 = curataText(ramas1, "fundalul la fereastra cu stiri");//System.out.println(60);
		ramas1 = curataText(ramas1, "culoare font titlu");//System.out.println(61);
		ramas1 = curataText(ramas1, "culoare font stiri");//System.out.println(62);
		ramas1 = curataText(ramas1, "dimensiune font titlu");//System.out.println(63);
		ramas1 = curataText(ramas1, "dimensiune font stiri");//System.out.println(64);
		ramas1 = curataText(ramas1, "<br /");//System.out.println(65);
		ramas1 = curataText(ramas1, "<ul>");//System.out.println(66);
		ramas1 = curataText(ramas1, "Cuprins general - Biblia ortodoxa");//System.out.println(67);
		ramas1 = curataText(ramas1, "<table");//System.out.println(68);
		ramas1 = curataText(ramas1, "<tr>");//System.out.println(69);
		ramas1 = curataText(ramas1, "reftext").trim();
		ramas1 = curataText(ramas1, ".htm").trim();
		ramas1 = curataText(ramas1, "<i>").trim();
		ramas1 = curataText(ramas1, "</i>").trim();
		ramas1 = curataText(ramas1, ".htm").trim();
		ramas1 = curataText(ramas1, "\"btext\">").trim();
		ramas1 = curataText(ramas1, "\"parenth\">").trim();
		ramas1 = curataText(ramas1, "\"black\">").trim();
		ramas1 = curataText(ramas1, "\"red\">").trim();
		ramas1 = curataText(ramas1, "\"none\">").trim();
		ramas1 = (ramas1.replace("\"black\">", "")).trim();
		ramas1 = (ramas1.replace("\"red\">", "")).trim();
		ramas1 = (ramas1.replace("\"none\">", "")).trim();
		ramas1 = curataText(ramas1, "</p>");
		ramas1 = curataText(ramas1, "<br />");
		ramas1 = curataText(ramas1, "<p class=\"body-fl-sp\">");
		ramas1 = (ramas1.replace("<p class=\"body-fl-sp\">", "")).trim();
		ramas1 = pastrezPanaLa("<hr size=",ramas1);
		ramas1 = (ramas1.replace("</b></a>&nbsp;", ".")).trim();
		ramas1 = curataText(ramas1, "<I>").trim();
		ramas1 = curataText(ramas1, "</I>").trim();
		ramas1 = curataText(ramas1, "</i").trim();
		ramas1 = curataText(ramas1, "<p class=\"list-sp\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"body1\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"ext-body-fl-sp\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"poet\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"poet0\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"poet1\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"poet2\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"poet3\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"poet4\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"poet5\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"poet6\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"poet7\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"poet8\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"poet9\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"poet10\">").trim();
		ramas1 = curataText(ramas1, "<p class=\"poet11\">").trim();
		ramas1 = curataText(ramas1, "\"nl:<p class=\"poet1-sp\">").trim();
		ramas1 = (ramas1.replace("\"nl:<p class=\"poet1-sp\">", "")).trim();
		ramas1 = curataText(ramas1, "\"nl:<p class=\"poet0-sp\">").trim();
		ramas1 = (ramas1.replace("\"nl:<p class=\"poet0-sp\">", "")).trim();
		ramas1 = curataText(ramas1, "\"nl:<p class=\"poet2-sp\">").trim();
		ramas1 = (ramas1.replace("\"nl:<p class=\"poet2-sp\">", "")).trim();
		ramas1 = curataText(ramas1, "\"nl:<p class=\"poet3-sp\">").trim();
		ramas1 = (ramas1.replace("\"nl:<p class=\"poet3-sp\">", "")).trim();
		ramas1 = curataText(ramas1, "\"nl:<p class=\"poet4-sp\">").trim();
		ramas1 = (ramas1.replace("\"nl:<p class=\"poet4-sp\">", "")).trim();
		ramas1 = curataText(ramas1, "\"nl:<p class=\"poet5-sp\">").trim();
		ramas1 = (ramas1.replace("\"nl:<p class=\"poet5-sp\">", "")).trim();
		ramas1 = curataText(ramas1, "\"nl:<p class=\"poet6-sp\">").trim();
		ramas1 = (ramas1.replace("\"nl:<p class=\"poet6-sp\">", "")).trim();
		ramas1 = curataText(ramas1, "<span class=\"bracket\">").trim();
		ramas1 = (ramas1.replace("<span class=\"bracket\">", "")).trim();
		ramas1 = (ramas1.replace("</FONT>", "")).trim();
		ramas1 = curataText(ramas1, "</FONT>").trim();

		return ramas1;
	}
	private String corecturiComplexe(String etapaIntermediara){
		etapaIntermediara = (etapaIntermediara.replace("===+++===", "")).trim();
		etapaIntermediara = curataText(etapaIntermediara, "===+++===").trim();
		etapaIntermediara = curataText(etapaIntermediara, "<span class=").trim();
		etapaIntermediara = (etapaIntermediara.replace("</span>", "")).trim();
		etapaIntermediara = curataText(etapaIntermediara, "</SPAN>").trim();
		etapaIntermediara = (etapaIntermediara.replace("</SPAN>", "")).trim();
		etapaIntermediara = curataText(etapaIntermediara, "<SPAN>").trim();
		etapaIntermediara = (etapaIntermediara.replace("<SPAN>", "")).trim();
		etapaIntermediara = curataText(etapaIntermediara, "<span>").trim();
		etapaIntermediara = (etapaIntermediara.replace("<span>", "")).trim();
		etapaIntermediara = curataText(etapaIntermediara, "</span>").trim();
		etapaIntermediara = (etapaIntermediara.replace("</span>", "")).trim();
		etapaIntermediara = curataText(etapaIntermediara, "</span").trim();
		etapaIntermediara = (etapaIntermediara.replace("</span", "")).trim();
		etapaIntermediara = curataText(etapaIntermediara, "<SPAN class=").trim();
		etapaIntermediara = (etapaIntermediara.replace("<SPAN class=", "")).trim();
		etapaIntermediara = (etapaIntermediara.replace("<<", "{")).trim();
		etapaIntermediara = (etapaIntermediara.replace(">>>", "}")).trim();
		etapaIntermediara = (etapaIntermediara.replace("}>", "}")).trim();
		etapaIntermediara = (etapaIntermediara.replace(">>", "}")).trim();

		etapaIntermediara = (etapaIntermediara.replace("<span class", "\n")).trim(); 

		etapaIntermediara = (etapaIntermediara.replace("&8212;", "â€”")).trim(); 
		etapaIntermediara = (etapaIntermediara.replace("&#8217;", "â€™")).trim(); 
		etapaIntermediara = (etapaIntermediara.replace("&8217;", "â€™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&8216;", "â€�")).trim(); 
		etapaIntermediara = (etapaIntermediara.replace("&#8216;", "â€�")).trim(); 
		etapaIntermediara = (etapaIntermediara.replace("&#8221;", "â€ť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#8220;", "“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#8221;", "”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&8220;", "â€ś")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+00AB", "Â«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+00BB", "Â»")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+2018", "â€�")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+2019", "â€™")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+201A", "â€š")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&sbquo;", "â€š")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+201B", "â€›")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#8219;", "â€›")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+201C", "â€ś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+201D", "â€ť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+201E", "â€ž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&bdquo;", "â€ž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+201F", "â€ź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#8223;", "â€ź")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+2039", "â€ą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&lsaquo;", "â€ą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+203A", "â€ş")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&rsaquo;", "â€ş")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+300C", "ă€Ś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#12300;", "ă€Ś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+300D", "ă€Ť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#12301;", "ă€Ť")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+300E", "ă€Ž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#12302;", "ă€Ž")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+300F", "ă€Ź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#12303;", "ă€Ź")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+301D", "ă€ť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#12317;", "ă€ť")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+301E", "ă€ž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#12318;", "ă€ž")).trim();		 
		etapaIntermediara = (etapaIntermediara.replace("U+301F", "ă€ź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#12319;", "ă€ź")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+FE41", "ďą�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#65089;", "ďą�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+FE42", "ďą‚")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#65090;", "ďą‚")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+FE43", "ďą�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#65091;", "ďą�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+FE44", "ďą„")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#65092;", "ďą„")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+FF02", "ďĽ‚")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#65282;", "ďĽ‚")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0022", "ďĽ‚")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+FF07", "ďĽ‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#65287;", "ďĽ‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0027", "ďĽ‡")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+FF62", "ď˝˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#65378;", "ď˝˘")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+FF63", "ď˝Ł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#65379;", "ď˝Ł")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("&#09;", "	")).trim();			
		etapaIntermediara = curataText(etapaIntermediara, "&#13;").trim();
		etapaIntermediara = (etapaIntermediara.replace("&#32;", " ")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#33;", "!")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#34;", "\"")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&quot;", "\"")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#35;", "#")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#36;", "$")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#37;", "%")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#38;", "&")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&amp;", "&")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("&#39;", "'")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#40;", "(")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#41;", ")")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#42;", "*")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#43;", "+")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#44;", ",")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#45;", "-")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#46;", ".")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#47;", "/")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&frasl;", "/")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#48;", "0")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#49;", "1")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#50;", "2")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#51;", "3")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#52;", "4")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#53;", "5")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#54;", "6")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#55;", "7")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#56;", "8")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#57;", "9")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#58;", ":")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#59;", ";")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("&#60;", "<")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&lt;", "<")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#61;", "=")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#62;", ">")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&gt;", ">")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#63;", "?")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#64;", "@")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#65;", "A")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#66;", "B")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#67;", "C")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#68;", "D")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#69;", "E")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#70;", "F")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#71;", "G")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#72;", "H")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#73;", "I")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#74;", "J")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#75;", "K")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#76;", "L")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#77;", "M")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#78;", "N")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#79;", "O")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#80;", "P")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#81;", "Q")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#82;", "R")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#83;", "S")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#84;", "T")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#85;", "U")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#86;", "V")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#87;", "W")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#88;", "X")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#89;", "Y")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#90;", "Z")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#91;", "[")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#92;", "\\")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#93;", "]")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#94;", "^")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#95;", "_")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#96;", "`")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#97;", "a")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#98;", "b")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#99;", "c")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#100;", "d")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#101;", "e")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#102;", "f")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#103;", "g")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#104;", "h")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#105;", "i")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#106;", "j")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#107;", "k")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#108;", "l")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#109;", "m")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#110;", "n")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#111;", "o")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#112;", "p")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#113;", "q")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#114;", "r")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#115;", "s")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#116;", "t")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#117;", "u")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#118;", "v")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#119;", "w")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#120;", "x")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#121;", "y")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#122;", "z")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#123;", "{")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#124;", "|")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#125;", "}")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#126;", "~")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#127;", "")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#128;", "â‚¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&euro;", "â‚¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#130;", "â€š")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#131;", "Ć’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#132;", "â€ž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#133;", "â€¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#134;", "â€ ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&dagger;", "â€ ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#135;", "â€ˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Dagger;", "â€ˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#136;", "Ë†")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#137;", "â€°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&permil;", "â€°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#138;", "Ĺ ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#139;", "â€ą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#140;", "Ĺ’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#145;", "â€�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&lsquo;", "â€�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#146;", "â€™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&rsquo;", "â€™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#147;", "â€ś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&ldquo;", "â€ś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#148;", "â€ť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&rdquo;", "â€ť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#149;", "â€˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&bull;", "â€˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#150;", "â€“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&ndash;", "â€“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#151;", "â€”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&mdash;", "â€”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#152;", "Ëś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#153;", "â„˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#154;", "Ĺˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#155;", "â€ş")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#156;", "Ĺ“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#159;", "Ĺ¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#160;", " ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&nbsp;", " ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#161;", "Âˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&iexcl;", "Âˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#162;", "Â˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&cent;", "Â˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#163;", "ÂŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&pound;", "ÂŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#164;", "Â¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&curren;", "Â¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#165;", "ÂĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&yen;", "ÂĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#166;", "Â¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&brvbar;", "Â¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&brkbar;", "Â¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#167;", "Â§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&sect;", "Â§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#168;", "Â¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&uml;", "Â¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&die;", "Â¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#169;", "Â©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&copy;", "Â©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#170;", "ÂŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&ordf;", "ÂŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#171;", "Â«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&laquo;", "Â«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#172;", "Â¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&not;", "Â¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&shy;", "")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#173;", "")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#174;", "Â®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&reg;", "Â®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#175;", "ÂŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&macr;", "ÂŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&hibar;", "ÂŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#176;", "Â°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&deg;", "Â°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#177;", "Â±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&plusmn;", "Â±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#178;", "Â˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&sup2;", "Â˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#179;", "Âł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&sup3;", "Âł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#180;", "Â´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&acute;", "Â´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#181;", "Âµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&micro;", "Âµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#182;", "Â¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&para;", "Â¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#183;", "Â·")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&middot;", "Â·")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#184;", "Â¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&cedil;", "Â¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#185;", "Âą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&sup1;", "Âą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#186;", "Âş")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&ordm;", "Âş")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#187;", "Â»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&raquo;", "Â»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#188;", "ÂĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&frac14;", "ÂĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#189;", "Â˝")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&frac12;", "Â˝")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#190;", "Âľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&frac34;", "Âľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#191;", "Âż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&iquest;", "Âż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#192;", "Ă€")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Agrave;", "Ă€")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#193;", "Ă�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Aacute;", "Ă�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#194;", "Ă‚")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Acirc;", "Ă‚")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#195;", "Ă�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Atilde;", "Ă�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#196;", "Ă„")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Auml;", "Ă„")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#197;", "Ă…")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Aring;", "Ă…")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#198;", "Ă†")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Aelig;", "Ă†")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#199;", "Ă‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Ccedil;", "Ă‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#200;", "Ă�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Egrave;", "Ă�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#201;", "Ă‰")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Eacute;", "Ă‰")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#202;", "ĂŠ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Ecicr;", "ĂŠ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#203;", "Ă‹")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Euml;", "Ă‹")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#204;", "ĂŚ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Igrave;", "ĂŚ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#205;", "ĂŤ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Iacute;", "ĂŤ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#206;", "ĂŽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Icirc;", "ĂŽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#207;", "ĂŹ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Iuml;", "ĂŹ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#208;", "Ă�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&ETH;", "Ă�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Dstrok;", "Ă�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#209;", "Ă‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Ntilde;", "Ă‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#210;", "Ă’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Ograve;", "Ă’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#211;", "Ă“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Oacute;", "Ă“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#212;", "Ă”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Ocirc;", "Ă”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#213;", "Ă•")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Otilde;", "Ă•")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#214;", "Ă–")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Ouml;", "Ă–")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#215;", "Ă—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&times;", "Ă—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#216;", "Ă�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Oslash;", "Ă�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#217;", "Ă™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Ugrave;", "Ă™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#218;", "Ăš")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Uacute;", "Ăš")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#219;", "Ă›")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Ucirc;", "Ă›")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#220;", "Ăś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Uuml;", "Ăś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#221;", "Ăť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&Yacute;", "Ăť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#222;", "Ăž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&THORN;", "Ăž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#223;", "Ăź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&szlig;", "Ăź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#224;", "Ă ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&agrave;", "Ă ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#225;", "Ăˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&aacute;", "Ăˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#226;", "Ă˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&acirc;", "Ă˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#227;", "ĂŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&atilde;", "ĂŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#228;", "Ă¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&auml;", "Ă¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#229;", "ĂĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&aring;", "ĂĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#230;", "Ă¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&aelig;", "Ă¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#231;", "Ă§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&ccedil;", "Ă§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#232;", "Ă¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&egrave;", "Ă¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#233;", "Ă©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&eacute;", "Ă©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#234;", "ĂŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&ecirc;", "ĂŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#235;", "Ă«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&euml;", "Ă«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#236;", "Ă¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&igrave;", "Ă¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#237;", "Ă­")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&iacute;", "Ă­")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#238;", "Ă®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&icirc;", "Ă®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#239;", "ĂŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&iuml;", "ĂŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#240;", "Ă°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&eth;", "Ă°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#241;", "Ă±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&ntilde", "Ă±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#242;", "Ă˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&ograve;", "Ă˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#243;", "Ăł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&oacute;", "Ăł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#244;", "Ă´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&ocirc;", "Ă´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#245;", "Ăµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&otilde;", "Ăµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#246;", "Ă¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&ouml;", "Ă¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#247;", "Ă·")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&divide;", "Ă·")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#248;", "Ă¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&oslash;", "Ă¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#249;", "Ăą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&ugrave;", "Ăą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#250;", "Ăş")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&uacute;", "Ăş")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#251;", "Ă»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&ucirc;", "Ă»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#252;", "ĂĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&uuml;", "ĂĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#253;", "Ă˝")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&yacute;", "Ă˝")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#254;", "Ăľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&thorn;", "Ăľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#255;", "Ăż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&yuml;", "Ăż")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("&#1488;", "×�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#1489;", "×‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&1488;", "")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&1489;", "")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0100", "Ä€")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#256;", "Ä€")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 80", "Ä€")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0101", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 81", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#257;", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0102", "Ä‚")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 82", "Ä‚")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#258;", "Ä‚")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0103", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 83", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#259;", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0104", "Ä„")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 84", "Ä„")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#260;", "Ä„")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0105", "Ä…")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 85", "Ä…")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#261;", "Ä…")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0106", "Ä†")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 86", "Ä†")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#262;", "Ä†")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0107", "Ä‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 87", "Ä‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#263;", "Ä‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0108", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 88", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#264;", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0109", "Ä‰")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 89", "Ä‰")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#265;", "Ä‰")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+010A", "ÄŠ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 8a", "ÄŠ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#266;", "ÄŠ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+010B", "Ä‹")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 8b", "Ä‹")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#267;", "Ä‹")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+010C", "ÄŚ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 8c", "ÄŚ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#268;", "ÄŚ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+010D", "ÄŤ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 8d", "ÄŤ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#269;", "ÄŤ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+010E", "ÄŽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 8e", "ÄŽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#270;", "ÄŽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+010F", "ÄŹ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 8f", "ÄŹ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#271;", "ÄŹ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0110", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 90", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#272;", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0111", "Ä‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 91", "Ä‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#273;", "Ä‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0112", "Ä’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 92", "Ä’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#274;", "Ä’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0113", "Ä“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 93", "Ä“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#275;", "Ä“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0114", "Ä”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 94", "Ä”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#276;", "Ä”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0115", "Ä•")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 95", "Ä•")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#277;", "Ä•")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0116", "Ä–")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 96", "Ä–")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#278;", "Ä–")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0117", "Ä—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 97", "Ä—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#279;", "Ä—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0118", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 98", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#280;", "Ä�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0119", "Ä™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 99", "Ä™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#281;", "Ä™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+011A", "Äš")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 9a", "Äš")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#282;", "Äš")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+011B", "Ä›")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 9b", "Ä›")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#283;", "Ä›")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+011C", "Äś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 9c", "Äś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#284;", "Äś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+011D", "Äť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 9d", "Äť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#285;", "Äť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+011E", "Äž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 9e", "Äž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#286;", "Äž")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+011F", "Äź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 9f", "Äź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#287;", "Äź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0120", "Ä ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 a0", "Ä ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#288;", "Ä ")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+0121", "Äˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 a1", "Äˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#289;", "Äˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0122", "Ä˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 a2", "Ä˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#290;", "Ä˘")).trim();						
		etapaIntermediara = (etapaIntermediara.replace("U+0123", "ÄŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 a3", "ÄŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#291;", "ÄŁ")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+0124", "Ä¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 a4", "Ä¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#292;", "Ä¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0125", "ÄĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 a5", "ÄĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#293;", "ÄĄ")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0126", "Ä¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 a6", "Ä¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#294;", "Ä¦")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+0127", "Ä§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 a7", "Ä§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#295;", "Ä§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0128", "Ä¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 a8", "Ä¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#296;", "Ä¨")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+0129", "Ä©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 a9", "Ä©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#297;", "Ä©")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+012A", "ÄŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 aa", "ÄŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#298;", "ÄŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+012B", "Ä«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 ab", "Ä«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#299;", "Ä«")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+012C", "Ä¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 ac", "Ä¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#300;", "Ä¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+012D", "Ä­")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 ad", "Ä­")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#301;", "Ä­")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+012E", "Ä®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 ae", "Ä®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#302;", "Ä®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+012F", "ÄŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#303;", "ÄŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 af", "ÄŻ")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0130", "Ä°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 b0", "Ä°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#304;", "Ä°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0131", "Ä±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 b1", "Ä±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#305;", "Ä±")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0132", "Ä˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 b2", "Ä˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#306;", "Ä˛")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0133", "Äł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 b3", "Äł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#307;", "Äł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0134", "Ä´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 b4", "Ä´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#308;", "Ä´")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+0135", "Äµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 b5", "Äµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#309;", "Äµ")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0136", "Ä¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 b6", "Ä¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#310;", "Ä¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0137", "Ä·")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 b7", "Ä·")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#311;", "Ä·")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+0138", "Ä¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 b8", "Ä¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#312;", "Ä¸")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0139", "Äą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 b9", "Äą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#313;", "Äą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+013A", "Äş")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 ba", "Äş")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#314;", "Äş")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+013B", "Ä»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 bb", "Ä»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#315;", "Ä»")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+013C", "ÄĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 bc", "ÄĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#316;", "ÄĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+013D", "Ä˝")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 bd", "Ä˝")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#317;", "Ä˝")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+013E", "Äľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 be", "Äľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#318;", "Äľ")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+013F", "Äż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c4 bf", "Äż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#319;", "Äż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0140", "Ĺ€")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 80", "Ĺ€")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#320;", "Ĺ€")).trim();						
		etapaIntermediara = (etapaIntermediara.replace("U+0141", "Ĺ�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 81", "Ĺ�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#321;", "Ĺ�")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0142", "Ĺ‚")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 82", "Ĺ‚")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#322;", "Ĺ‚")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+0143", "Ĺ�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 83", "Ĺ�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#323;", "Ĺ�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0144", "Ĺ„")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 84", "Ĺ„")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#324;", "Ĺ„")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0145", "Ĺ…")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 85", "Ĺ…")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#325;", "Ĺ…")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0146", "Ĺ†")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 86", "Ĺ†")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#326;", "Ĺ†")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0147", "Ĺ‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 87", "Ĺ‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#327;", "Ĺ‡")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0148", "Ĺ�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 88", "Ĺ�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#328;", "Ĺ�")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+0149", "Ĺ‰")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 89", "Ĺ‰")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#329;", "Ĺ‰")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+014A", "ĹŠ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 8a", "ĹŠ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#330;", "ĹŠ")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+014B", "Ĺ‹")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 8b", "Ĺ‹")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#331;", "Ĺ‹")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+014C", "ĹŚ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 8c", "ĹŚ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#332;", "ĹŚ")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+014D", "ĹŤ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 8d", "ĹŤ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#333;", "ĹŤ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+014E", "ĹŽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 8e", "ĹŽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#334;", "ĹŽ")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+014F", "ĹŹ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 8f", "ĹŹ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#335;", "ĹŹ")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+0150", "Ĺ�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 90", "Ĺ�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#336;", "Ĺ�")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+0151", "Ĺ‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 91", "Ĺ‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#337;", "Ĺ‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0152", "Ĺ’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 92", "Ĺ’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#338;", "Ĺ’")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0153", "Ĺ“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 93", "Ĺ“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#339;", "Ĺ“")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+0154", "Ĺ”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 94", "Ĺ”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#340;", "Ĺ”")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+0155", "Ĺ•")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 95", "Ĺ•")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#341;", "Ĺ•")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0156", "Ĺ–")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 96", "Ĺ–")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#342;", "Ĺ–")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0157", "Ĺ—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 97", "Ĺ—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#343;", "Ĺ—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0158", "Ĺ�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 98", "Ĺ�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#344;", "Ĺ�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0159", "Ĺ™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 99", "Ĺ™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#345;", "Ĺ™")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+015A", "Ĺš")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 9a", "Ĺš")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#346;", "Ĺš")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+015B", "Ĺ›")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 9b", "Ĺ›")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#347;", "Ĺ›")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+015C", "Ĺś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 9c", "Ĺś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#348;", "Ĺś")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+015D", "Ĺť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 9d", "Ĺť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#349;", "Ĺť")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+015E", "Ĺž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 9e", "Ĺž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#350;", "Ĺž")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+015F", "Ĺź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 9f", "Ĺź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#351;", "Ĺź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0160", "Ĺ ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 a0", "Ĺ ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#352;", "Ĺ ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0161", "Ĺˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 a1", "Ĺˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#353;", "Ĺˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0162", "Ĺ˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 a2", "Ĺ˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#354;", "Ĺ˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0163", "ĹŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 a3", "ĹŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#355;", "ĹŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0164", "Ĺ¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 a4", "Ĺ¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#356;", "Ĺ¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0165", "ĹĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 a5", "ĹĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#357;", "ĹĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0166", "Ĺ¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 a6", "Ĺ¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#358;", "Ĺ¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0167", "Ĺ§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 a7", "Ĺ§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#359;", "Ĺ§")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0168", "Ĺ¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 a8", "Ĺ¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#360;", "Ĺ¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0169", "Ĺ©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 a9", "Ĺ©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#361;", "Ĺ©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+016A", "ĹŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 aa", "ĹŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#362;", "ĹŞ")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+016B", "Ĺ«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 ab", "Ĺ«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#363;", "Ĺ«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+016C", "Ĺ¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 ac", "Ĺ¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#364;", "Ĺ¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+016D", "Ĺ­")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 ad", "Ĺ­")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#365;", "Ĺ­")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+016E", "Ĺ®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 ae", "Ĺ®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#366;", "Ĺ®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+016F", "ĹŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 af", "ĹŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#367;", "ĹŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0170", "Ĺ°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 b0", "Ĺ°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#368;", "Ĺ°")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+0171", "Ĺ±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 b1", "Ĺ±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#369;", "Ĺ±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0172", "Ĺ˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 b2", "Ĺ˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#370;", "Ĺ˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0173", "Ĺł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 b3", "Ĺł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#371;", "Ĺł")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+0174", "Ĺ´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 b4", "Ĺ´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#372;", "Ĺ´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0175", "Ĺµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 b5", "Ĺµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#373;", "Ĺµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0176", "Ĺ¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 b6", "Ĺ¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#374;", "Ĺ¶")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0177", "Ĺ·")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 b7", "Ĺ·")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#375;", "Ĺ·")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+0178", "Ĺ¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 b8", "Ĺ¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#376;", "Ĺ¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0179", "Ĺą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 b9", "Ĺą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#377;", "Ĺą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+017A", "Ĺş")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 ba", "Ĺş")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#378;", "Ĺş")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+017B", "Ĺ»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 bb", "Ĺ»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#379;", "Ĺ»")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+017C", "ĹĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 bc", "ĹĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#380;", "ĹĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+017D", "Ĺ˝")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 bd", "Ĺ˝")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#381;", "Ĺ˝")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+017E", "Ĺľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 be", "Ĺľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#382;", "Ĺľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+017F", "Ĺż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c5 bf", "Ĺż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#383;", "Ĺż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0180", "Ć€")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 80", "Ć€")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#384;", "Ć€")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0181", "Ć� ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 81", "Ć� ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#385;", "Ć� ")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+0182", "Ć‚ ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 82", "Ć‚ ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#386;", "Ć‚ ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0183", "Ć� ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 83", "Ć� ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#387;", "Ć� ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0184", "Ć„ ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 84", "Ć„ ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#388;", "Ć„ ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0185", "Ć… ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 85", "Ć… ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#389;", "Ć… ")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0186", "Ć† ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 86", "Ć† ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#390;", "Ć† ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0187", "Ć‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 87", "Ć‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#391;", "Ć‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0188", "Ć� ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 88", "Ć� ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#392;", "Ć� ")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+0189", "Ć‰")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 89", "Ć‰")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#393;", "Ć‰")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+018A", "ĆŠ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 8a", "ĆŠ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#394;", "ĆŠ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+018B", "Ć‹")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 8b", "Ć‹")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#395;", "Ć‹")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+018C", "ĆŚ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 8c", "ĆŚ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#396;", "ĆŚ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+018D", "ĆŤ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 8d", "ĆŤ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#397;", "ĆŤ")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+018E", "ĆŽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 8e", "ĆŽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#398;", "ĆŽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+018F", "ĆŹ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 8f", "ĆŹ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#399;", "ĆŹ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0190", "Ć�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 90", "Ć�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#400;", "Ć�")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+0191", "Ć‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 91", "Ć‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#401;", "Ć‘")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0192", "Ć’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 92", "Ć’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#402;", "Ć’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0193", "Ć“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 93", "Ć“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#403;", "Ć“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0194", "Ć”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 94", "Ć”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#404;", "Ć”")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0195", "Ć•")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 95", "Ć•")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#405;", "Ć•")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0196", "Ć–")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#406;", "Ć–")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 96", "Ć–")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0197", "Ć—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 97", "Ć—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#407;", "Ć—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+0198", "Ć�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 98", "Ć�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#408;", "Ć�")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+0199", "Ć™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 99", "Ć™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#409;", "Ć™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+019A", "Ćš")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 9a", "Ćš")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#410;", "Ćš")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+019B", "Ć›")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 9b", "Ć›")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#411;", "Ć›")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+019C", "Ćś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 9c", "Ćś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#412;", "Ćś")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+019D", "Ćť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 9d", "Ćť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#413;", "Ćť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+019E", "Ćž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 9e", "Ćž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#414;", "Ćž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+019F", "Ćź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 9f", "Ćź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#415;", "Ćź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01A0", "Ć ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 a0", "Ć ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#416;", "Ć ")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+01A1", "Ćˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 a1", "Ćˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#417;", "Ćˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01A2", "Ć˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 a2", "Ć˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#418;", "Ć˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01A3", "ĆŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 a3", "ĆŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#419;", "ĆŁ")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+01A4", "Ć¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 a4", "Ć¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#420;", "Ć¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01A5", "ĆĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 a5", "ĆĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#421;", "ĆĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01A6", "Ć¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 a6", "Ć¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#422;", "Ć¦")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+01A7", "Ć§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 a7", "Ć§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#423;", "Ć§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01A8", "Ć¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 a8", "Ć¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#424;", "Ć¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01A9", "Ć©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 a9", "Ć©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#425;", "Ć©")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+01AA", "ĆŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 aa", "ĆŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#426;", "ĆŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01AB", "Ć«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 ab", "Ć«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#427;", "Ć«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01AC", "Ć¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 ac", "Ć¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#428;", "Ć¬")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+01AD", "Ć­")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 ad", "Ć­")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#429;", "Ć­")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01AE", "Ć®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 ae", "Ć®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#430;", "Ć®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01AF", "ĆŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 af", "ĆŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#431;", "ĆŻ")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+01B0", "Ć°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 b0", "Ć°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#432;", "Ć°")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+01B1", "Ć±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 b1", "Ć±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#433;", "Ć±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01B2", "Ć˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 b2", "Ć˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#434;", "Ć˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01B3", "Ćł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 b3", "Ćł")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("&#435;", "Ćł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01B4", "Ć´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 b4", "Ć´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#436;", "Ć´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01B5", "Ćµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 b5", "Ćµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#437;", "Ćµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01B6", "Ć¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 b6", "Ć¶")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("&#438;", "Ć¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01B7", "Ć·")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("c6 b7", "Ć·")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#439;", "Ć·")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01B8", "Ć¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 b8", "Ć¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#440;", "Ć¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01B9", "Ćą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 b9", "Ćą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#441;", "Ćą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01BA", "Ćş")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("c6 ba", "Ćş")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("&#442;", "Ćş")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01BB", "Ć»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 bb", "Ć»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#443;", "Ć»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01BC", "ĆĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 bc", "ĆĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#444;", "ĆĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01BD", "Ć˝")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 bd", "Ć˝")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("&#445;", "Ć˝")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+01BE", "Ćľ")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("c6 be", "Ćľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#446;", "Ćľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01BF", "Ćż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c6 bf", "Ćż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#447;", "Ćż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01C0", "Ç€")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 80", "Ç€")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#448;", "Ç€")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01C1", "Ç�")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("c7 81", "Ç�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#449;", "Ç�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01C2", "Ç‚")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 82", "Ç‚")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#450;", "Ç‚")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01C3", "Ç�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 83", "Ç�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#451;", "Ç�")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+01C4", "Ç„")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 84", "Ç„")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#452;", "Ç„")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01C5", "Ç…")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 85", "Ç…")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#453;", "Ç…")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01C6", "Ç†")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("c7 86", "Ç†")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#454;", "Ç†")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01C7", "Ç‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 87", "Ç‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#455;", "Ç‡")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01C8", "Ç�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 88", "Ç�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#456;", "Ç�")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+01C9", "Ç‰")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 89", "Ç‰")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#457;", "Ç‰")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01CA", "ÇŠ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 8a", "ÇŠ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#458;", "ÇŠ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01CB", "Ç‹")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 8b", "Ç‹")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#459;", "Ç‹")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01CC", "ÇŚ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 8c", "ÇŚ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#460;", "ÇŚ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01CD", "ÇŤ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 8d", "ÇŤ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#461;", "ÇŤ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01CE", "ÇŽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 8e", "ÇŽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#462;", "ÇŽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01CF", "ÇŹ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 8f", "ÇŹ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#463;", "ÇŹ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01D0", "Ç�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 90", "Ç�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#464;", "Ç�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01D1", "Ç‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 91", "Ç‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#465;", "Ç‘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01D2", "Ç’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 92", "Ç’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#466;", "Ç’")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01D3", "Ç“")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("c7 93", "Ç“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#467;", "Ç“")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01D4", "Ç”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 94", "Ç”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#468;", "Ç”")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01D5", "Ç•")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 95", "Ç•")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#469;", "Ç•")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01D6", "Ç–")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 96", "Ç–")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("&#470;", "Ç–")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01D7", "Ç—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 97", "Ç—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#471;", "Ç—")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01D8", "Ç�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 98", "Ç�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#472;", "Ç�")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01D9", "Ç™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 99", "Ç™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#473;", "Ç™")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01DA", "Çš")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 9a", "Çš")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#474;", "Çš")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01DB", "Ç›")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 9b", "Ç›")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#475;", "Ç›")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+01DC", "Çś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 9c", "Çś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#476;", "Çś")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01DD", "Çť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 9d", "Çť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#477;", "Çť")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01DE", "Çž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 9e", "Çž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#478;", "Çž")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01DF", "Çź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 9f", "Çź")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#479;", "Çź")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+01E0", "Ç ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 a0", "Ç ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#480;", "Ç ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01E1", "Çˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 a1", "Çˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#481;", "Çˇ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01E2", "Ç˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 a2", "Ç˘")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#482;", "Ç˘")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+01E3", "ÇŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 a3", "ÇŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#483;", "ÇŁ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01E4", "Ç¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 a4", "Ç¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#484;", "Ç¤")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01E5", "ÇĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 a5", "ÇĄ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#485;", "ÇĄ")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+01E6", "Ç¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 a6", "Ç¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#486;", "Ç¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01E7", "Ç§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 a7", "Ç§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#487;", "Ç§")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01E8", "Ç¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 a8", "Ç¨")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#488;", "Ç¨")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+01E9", "Ç©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 a9", "Ç©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#489;", "Ç©")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01EA", "ÇŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 aa", "ÇŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#490;", "ÇŞ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01EB", "Ç«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 ab", "Ç«")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#491;", "Ç«")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+01EC", "Ç¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 ac", "Ç¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#492;", "Ç¬")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01ED", "Ç­")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 ad", "Ç­")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#493;", "Ç­")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+01EE", "Ç®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 ae", "Ç®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#494;", "Ç®")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01EF", "ÇŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 af", "ÇŻ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#495;", "ÇŻ")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+01F0", "Ç°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 b0", "Ç°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#496;", "Ç°")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01F1", "Ç±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 b1", "Ç±")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#497;", "Ç±")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+01F2", "Ç˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 b2", "Ç˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#498;", "Ç˛")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01F3", "Çł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 b3", "Çł")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#499;", "Çł")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+01F4", "Ç´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 b4", "Ç´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#500;", "Ç´")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01F5", "Çµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 b5", "Çµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#501;", "Çµ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01F6", "Ç¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 b6", "Ç¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#502;", "Ç¶")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01F7", "Ç·")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 b7", "Ç·")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#503;", "Ç·")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+01F8", "Ç¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 b8", "Ç¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#504;", "Ç¸")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01F9", "Çą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 b9", "Çą")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#505;", "Çą")).trim();			
		etapaIntermediara = (etapaIntermediara.replace("U+01FA", "Çş")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 ba", "Çş")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#506;", "Çş")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+01FB", "Ç»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 bb", "Ç»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#507;", "Ç»")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01FC", "ÇĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 bc", "ÇĽ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#508;", "ÇĽ")).trim();	
		etapaIntermediara = (etapaIntermediara.replace("U+01FD", "Ç˝")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 bd", "Ç˝")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#509;", "Ç˝")).trim();		
		etapaIntermediara = (etapaIntermediara.replace("U+01FE", "Çľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 be", "Çľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#510;", "Çľ")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+01FF", "Çż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("c7 bf", "Çż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#511;", "Çż")).trim();
		etapaIntermediara = (etapaIntermediara.replace("U+1FE6", "áż¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#8166;", "áż¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("&#x1FE6;", "áż¦")).trim();
		etapaIntermediara = (etapaIntermediara.replace("<", "")).trim();
		etapaIntermediara = curataText(etapaIntermediara, "<").trim();
		etapaIntermediara = (etapaIntermediara.replace(">", "")).trim();
		etapaIntermediara = curataText(etapaIntermediara, ">").trim();
		return etapaIntermediara;
	}
	private void createDB(Version version){

		try{
			if(version != null && dbPath != null && !dbPath.trim().isEmpty()){
				String versionName = version.getValue();
				File checkDbPath = new File(dbPath);
				if(checkDbPath.isDirectory() && checkDbPath.canWrite()){

					boolean existVersion = false;

					File[] files = checkDbPath.listFiles();

					if(files != null && files.length > 0){
						for(File file : files){
							if(file != null && file.isDirectory()){
								String name = file.getName();
								if(name != null && name.equalsIgnoreCase(versionName)){
									existVersion = true;
									break;
								}
							}
						}
					}
					if(!existVersion){
						String versionPath = dbPath + platformType + versionName;
						if(createdDirector(versionPath)){
							List<Book> booksList = version.getBooks();
							if(booksList != null && !booksList.isEmpty()){
								for(Book book : booksList){
									if(book != null){
										String bookName = book.getValue();
										if(bookName != null && !bookName.trim().isEmpty()){
											String bookPath = versionPath + platformType + book.getBookNo() + "_" + bookName;
											if(createdDirector(bookPath)){
												String bookDescription = book.getDescription();
												if(bookDescription != null && !bookDescription.trim().isEmpty()){
													writeFile(bookPath + platformType + "0", bookDescription.trim());
												}
												List<Chapter> chaptersList = book.getChapters();
												if(chaptersList != null && !chaptersList.isEmpty()){
													for(Chapter chapter : chaptersList){
														if(chapter != null){
															int chapterName = chapter.getValue();
															String chapterContent = chapter.getEntireChapter();
															if(chapterName > 0 && chapterContent != null && !chapterContent.trim().isEmpty()){
																writeFile(bookPath + platformType + chapterName, chapterContent);
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
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void writeFile(String path, String fileContent){

		try(BufferedWriter out = Files.newBufferedWriter(Paths.get(path.trim() + ".txt".trim()), StandardCharsets.UTF_8)){
			if(path != null && !path.trim().isEmpty() && fileContent != null && !fileContent.trim().isEmpty()){

				//Files.write(Paths.get(path.trim() + ".txt".trim()), fileContent.getBytes());

				out.write(fileContent.trim());
			}
		}
		catch (Throwable e){
			e.printStackTrace();
			System.err.println("Error: " + e.getMessage());
			System.err.println("Error: " + e.getCause());			
			System.err.println("Error: " + e.getStackTrace());
			System.err.println("Error: " + e.fillInStackTrace());
			System.err.println("Error: " + e.getLocalizedMessage());
		}
	}

	private boolean createdDirector(String path){

		File director = new File(path);
		if (!director.exists()){
			try{
				if (!director.isDirectory()) {
					director.mkdir();
					director.setReadable(true);
					director.setWritable(true);
					return true;
				}
			}
			catch(Throwable a){
				a.printStackTrace();
			}
		}
		return false;
	}
}