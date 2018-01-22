package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import beans.Book;
import beans.Chapter;
import beans.Version;
import dao.DAOTool;

@Controller
@Transactional
@Scope("prototype")
public class TestController{

	@Autowired
	private DAOTool dao;

	@RequestMapping(value = "/testVersion")
	public ModelAndView testDatabase(ModelMap model, HttpServletRequest request, HttpServletResponse response){

		try {

			if(request != null){
				
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				
				HttpSession session = request.getSession();
				if(session != null){
					session.setMaxInactiveInterval(999999999);
				}
			}



			//			search.setDao(dao);
			//			taskExecutor.execute(search);

			String displayBible = "";
			String newLine = "<br>";

			Version version = dao.getVersion("Cornilescu");
			if(version != null){

				//System.out.println(version.getValue());
				displayBible += version.getValue() + newLine;
				List<Book> books = version.getBooks();
				if(books != null){

					for(Book book : books){
						if(book != null){
							//System.out.println(book.getValue());
							displayBible += book.getValue() + newLine;

							List<Chapter> chapters = book.getChapters();
							if(chapters != null){
								for(Chapter chapter : chapters){
									if(chapter != null){
										//System.out.println(chapter.getValue());
										displayBible += chapter.getValue() + newLine;

//										List<Verse> verses = chapter.getVerses();
//										if(verses != null){
//											for(Verse verse : verses){
//												if(verse != null){
//													//System.out.println(verse.getVerseNo() + " " + verse.getValue());
//													displayBible += verse.getVerseNo() + " " + verse.getValue() + newLine;
//												}
//											}
//										}
									}
								}
							}
						}
					}
				}
			}
			model.addAttribute("tools", displayBible);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("tools", model);
	}
}