package utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CountFiles {

	public static void main(String[] args) {
		String folderPath = Constants.picturesPath.value;

		try{
			File folder = new File (folderPath);

			if(folder != null && folder.exists() && folder.isDirectory()){

				File[] subfolders = folder.listFiles();

				if(subfolders != null && subfolders.length > 0){

					for(File category : subfolders){

						if(category != null && category.exists() && category.isDirectory()){

							File[] files = category.listFiles();
							if(files != null && files.length > 0){

								System.out.println(category.getName());

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
											System.out.println(value);
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
}