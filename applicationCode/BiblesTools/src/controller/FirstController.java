package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
public class FirstController {

	@RequestMapping(value = "/")
	public ModelAndView first(ModelMap model, HttpServletRequest request, HttpServletResponse response){

		try{
			if(request != null){

				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return new ModelAndView("tools", model);
	}
}