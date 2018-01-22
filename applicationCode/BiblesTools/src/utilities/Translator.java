package utilities;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
public class Translator {
	public String callUrlAndParseResult(String langFrom, String langTo, String text) throws Exception {

		String translatedText = "";
		String splitSign = "___________________";

		Set<String> endSymbols = new HashSet<>();

		endSymbols.add(".");
		endSymbols.add("!");
		endSymbols.add("?");
		endSymbols.add(";");

		if(endSymbols != null && !endSymbols.isEmpty()){

			for(String symbol : endSymbols){
				if(symbol != null && !symbol.trim().isEmpty() && text.contains(symbol.trim())){
					text = text.replace(symbol.trim(), symbol.trim() + splitSign.trim());
				}
			}
		}

		String[] multipleSentences = text.split(splitSign.trim());

		if(multipleSentences != null && multipleSentences.length > 0){
			for(String sentence : multipleSentences){
				if(sentence != null && !sentence.trim().isEmpty()){
					translatedText += translate(langFrom, langTo, sentence);
				}
			}
		}

		return translatedText;
	}

	private String translate(String langFrom, String langTo, String word) throws Exception {

		String url = "https://translate.googleapis.com/translate_a/single?"+
				"client=gtx&"+
				"sl=" + langFrom + 
				"&tl=" + langTo + 
				"&dt=t&q=" + URLEncoder.encode(word, "UTF-8");    

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection(); 
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		con.setRequestProperty("useUnicode", "true");
		con.setRequestProperty("characterEncoding", "UTF-8");
		con.setRequestProperty("charSet", "UTF-8");
		con.setRequestProperty("characterSetResults", "UTF-8");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		String result = parseResult(response.toString());

		if(result == null || result.trim().isEmpty()){
			result = word;
		}

		return result;
	}

	private String parseResult(String inputJson) throws Exception {

		if(inputJson != null){
			JSONArray jsonArray = new JSONArray(inputJson);
			if(jsonArray != null && jsonArray.length() > 0 && !jsonArray.isNull(0)){
				JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
				if(jsonArray2 != null && jsonArray2.length() > 0 && !jsonArray2.isNull(0)){
					JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);
					if(jsonArray3 != null && jsonArray3.length() > 0 && !jsonArray3.isNull(0)){
						return jsonArray3.get(0).toString();
					}
				}
			}
		}

		return null;
	}
}