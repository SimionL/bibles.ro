package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import utilities.Constants;

@Controller
@Transactional
public class ScreensaverController {

	@RequestMapping(value = "/screensaver")
	public ModelAndView first(ModelMap model, HttpServletRequest request, HttpServletResponse response){

		try{
			if(request != null){

				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");

				String folderPath = Constants.picturesPath.value;
				String result = "";

				File folder = new File (folderPath);

				if(folder != null && folder.exists() && folder.isDirectory()){

					File[] subfolders = folder.listFiles();

					if(subfolders != null && subfolders.length > 0){

						for(File category : subfolders){

							if(category != null && category.exists() && category.isDirectory()){

								File[] files = category.listFiles();
								if(files != null && files.length > 0){

									result += category.getName() + "<br>";

									List<String> allValues = new ArrayList<>();

									for(int i = 1 ; i <= files.length ; i++){
										allValues.add(i + "");
									}
									if(allValues != null){
										for (File file : files){
											if(file != null && file.exists() && file.isFile()){
												String fileName = file.getName();
												if(fileName.contains(".")){

													String fileN = fileName.substring(0, fileName.indexOf("."));

													if(allValues.contains(fileN)){
														allValues.remove(fileN);
													}
												}
											}
										}
										if(!allValues.isEmpty()){
											for(String value : allValues){
												result += value + "<br>";
											}
										}

									}
								}
							}
						}	
					}	
				}
				
				model.addAttribute("screensaver", result);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return new ModelAndView("tools", model);
	}
}
