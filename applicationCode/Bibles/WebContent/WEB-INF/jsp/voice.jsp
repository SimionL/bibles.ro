<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" buffer="256kb" %>

<html>
<head>
<title>${voice.voice}</title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<link   rel="stylesheet"       href="<c:url value="/resources/css/style.css" />"        />

<script type="text/javascript" charset="utf-8">

function startListening() {

	var recognizer = null;
	var langCode = '${voice.languageCode}';

	try {

		if('${voice.usingVoice}' == 'true' && langCode != null && langCode != 'null' && langCode != 'undefined' && langCode != ''){

	        if (typeof speechRecognition !== 'undefined') {
	        	recognizer = new speechRecognition();
	        } 
	        else if (typeof webkitSpeechRecognition !== 'undefined') {
	        	recognizer = new webkitSpeechRecognition();
	        }
	        else if (typeof msSpeechRecognition !== 'undefined') {
	        	recognizer = new msSpeechRecognition();
	        } 
	        else if (typeof mozSpeechRecognition !== 'undefined') {
	        	 recognizer = new mozSpeechRecognition();
	        }

	        if(recognizer !== null && recognizer !== 'undefined'){

	          	recognizer.lang            = langCode;
	          	recognizer.continuous      = true;
	          	recognizer.maxAlternatives = 0;
	          	recognizer.interimResults  = false;

	          	recognizer.onresult = function(event) {

	          		var words = '';
	          		for (var i = event.resultIndex; i < event.results.length; i++) {
	        	  
	        	  		var wordResult = event.results[i][0].transcript;
	        	  
	        	  		if(wordResult != null && wordResult != 'null' && wordResult != 'undefined' && wordResult != '' && !(wordResult.trim() === "") && wordResult.trim().length > 0){
	        	   
	        		  		words += ((wordResult.trim()).normalize("NFKD")).replace (/[\u0300-\u036F]/g, "") + ';';
	             		}
	          		}

	          		if(words != null && words != 'null' && words != 'undefined' && words != '' && !(words.trim() === "") && words.trim().length > 0){
	          			
	        	  		recognizer.abort();
				  		recognizer.stop();
				  		recognizer = null;
	          			
				  		$("#identifiedWordsId").val(words.trim());
			      		$("#eventId").val(7);
				  		$("#voiceForm").submit();
	          		}
	      		 };

	 	       recognizer.onend = function () {
	 	    	  if(recognizer != null && recognizer !== null && recognizer != 'null' && recognizer != 'undefined'){
	 	    	  	 recognizer.start();
	 	    	  }
	 	       }

	 	       recognizer.onerror = function () {
	 	    	   if(recognizer != null && recognizer !== null && recognizer != 'null' && recognizer != 'undefined'){
			 	      recognizer.start();
			 	   }
	 		   }

	 	       recognizer.onnomatch = function () {
	 	    	   if(recognizer != null && recognizer !== null && recognizer != 'null' && recognizer != 'undefined'){
			 	      recognizer.start();
			 	   } 
	 		   }

	 	       recognizer.start();

	      	   }
			}
		}
	     catch(ex) {
	        if(recognizer != null && recognizer !== null && recognizer != 'null' && recognizer != 'undefined'){
			   recognizer.start();
		    }
	     }
}

function prepareAudioFacilities(){

 	try{

 		if('${voice.usingVoice}' == 'true'){

 			var enableSpeaking = "${voice.enableSpeaking}";
 			var audioText = "${voice.theSpokenWords}";

 			if('speechSynthesis' in window && audioText != null && audioText != 'null' && audioText != 'undefined' && audioText != '' && enableSpeaking != null && enableSpeaking != 'null' && enableSpeaking != 'undefined' && enableSpeaking != '' && enableSpeaking == 'true'){
 		
 				window.speechSynthesis.onvoiceschanged = function() {
 				
				var talks = new SpeechSynthesisUtterance();

				if(talks !== null && talks != null && talks !== 'undefined'){

					talks.text = audioText;
					talks.volume = 1;
					talks.rate   = 1;
					talks.pitch  = 1;

 					var newLang;
 					var voiceLang = '${voice.voiceCode}';
 					var voices = window.speechSynthesis.getVoices();

					if(voices != null && voices != 'null' && voices != 'undefined' && voices != '' && voices.length > 0){
						for (var i in voices) {
						    newLang = voices[i];
							if(newLang != null && newLang != 'null' && newLang != 'undefined' && newLang != ''){
								var localVoicelang = newLang.lang;
								if(localVoicelang != null && localVoicelang != 'null' && localVoicelang != 'undefined' && localVoicelang != '' && localVoicelang.startsWith('${voice.voiceCode}')){

									voiceLang = localVoicelang;
									talks.voiceURI = newLang.voiceURI;
									talks.voice = newLang;
									break;
								}
							}
						}
					}
					
					talks.lang = voiceLang;

					talks.onend = function(event) {

						window.speechSynthesis.cancel();
						talks = null;

						startListening();
					}

					talks.onerror = function(event) {

						window.speechSynthesis.cancel();
						talks = null;
						
						startListening();	
					}

					window.speechSynthesis.speak(talks);
				}
				else{
					 startListening();
	 			}
			  }; 
 			}
 			else{
 				 startListening();
 			}
 		}
	} catch(ex) {
		if('${voice.usingVoice}' == 'true'){
			startListening();
		}
	}
}

</script>
<style>

.textClass{
    	background: transparent;
    	font-size: 25px;
		border: 0 none white;
		width: 100%;
		max-width: 100%;
		height: 100%;
		max-height: 100%;
		word-wrap: break-word;
  		text-align: left;
      }

div.tab {
    overflow: hidden;
    background-color: #ffffff;
}

div.tab button {
   background-color: inherit;
   float: left;
   border: none;
   outline: none;
   cursor: pointer;
   font-size: 17px;
   border-radius: 55px;
   padding: 20px;
   width:150px;
   height: 60px; 
}

div.tab button:hover {
    background-color: #ddd;
}

div.tab button.active {
    background-color: #39D951;
}
</style>
</head>
<body onload="prepareAudioFacilities();">

<div class="tab">
  <button id="bibleTabId">${voice.bible}</button>
  <button id="churchTabId">${voice.church}</button>
  <button id="settingsTabId">${voice.settings}</button>
  <button id="screensaverTabId">${voice.screensaver}</button>
  <button id="codeTabId">${voice.code}</button>
  <button class="active">${voice.voice}</button>
  <button id="feedbackTabId">${voice.feedback}</button>
  <button id="thankYouTabId">${voice.thankYou}</button>
</div>

	<div align="center">
		<c:if test="${not empty voice.error}">
			<b><font color="red">${voice.error}</font></b>
			<br><br>
		</c:if>
	</div>

	<form:form action="voice" 
			   modelAttribute="voice"
			   enctype="multipart/form-data" 
			   id="voiceForm"
			   accept-charset="UTF-8"
	>

		<form:input path="eventId" 
					type="text" 
					class="eventId" 
					hidden="true" 
		/>
		
		<form:input path="openPopup" 
					type="text" 
					id="openPopupId" 
					hidden="true" 
		/>
		
		<form:input path="identifiedWords"
					id="identifiedWordsId" 
				    hidden="true"
		/>

		<div class="position">

			<br>
			
			<table style="width: 8%" class="tableClass" >

			<tr>
				<td colspan='1'><c:out value="${voice.useVoiceLabel}" /></td>
				<td align="left" colspan='1'>
					<form:checkbox path="usingVoice" 
								   cssStyle = "cursor: pointer"
								   onclick="$('.eventId').val(6);this.form.submit();" 
					/>
				</td>		
			</tr>
			</table>
            <br><br>
			<table style="width: 100%" class="tableClass" >

			<tr>
				<td align="center" colspan='1'>

						<form:select path="selectedLanguage_1"
									 items="${voice.languages_1}"
									 onchange="$('.eventId').val(8);this.form.submit();" 
									 cssStyle = "cursor: pointer"
						/>

			<c:if test="${voice.usingVoice eq true}">
                <br>
				<c:choose>

					<c:when test="${voice.voice_1 eq true}">

						<img
							src="<c:url 
  					        value="/resources/images/on.png"/>"
  					        onMouseOver="this.style.cursor='pointer'"
  					        id="on_1_Id"
						/>

					</c:when>

					<c:otherwise>

						<img
							src="<c:url 
  					        value="/resources/images/off.png"/>"
  					        onMouseOver="this.style.cursor='pointer'"
  					        id="off_1_Id"
						/>

					</c:otherwise>

				</c:choose>
			
			</c:if>	

				</td>
				<td></td>
				<td align="center" colspan='1'>

						<form:select path="selectedLanguage_2"
									 items="${voice.languages_2}"
									 onchange="$('.eventId').val(9);this.form.submit();" 
									 cssStyle = "cursor: pointer"
						/>

			<c:if test="${voice.usingVoice eq true}">	
 				<br>
				<c:choose>

					<c:when test="${voice.voice_1 eq false}">

						<img
							src="<c:url 
  					        value="/resources/images/on.png"/>"
  					        onMouseOver="this.style.cursor='pointer'"
  					        id="on_2_Id"
						/>

					</c:when>

					<c:otherwise>

						<img
							src="<c:url 
  					        value="/resources/images/off.png"/>"
  					        onMouseOver="this.style.cursor='pointer'"
  					        id="off_2_Id"
						/>

					</c:otherwise>

				</c:choose>
			</c:if>
		</td>
		</tr>
			<tr>
				<td height=600px;>
					<form:textarea path="selected_1_Text"
					   type="text"
					   class="textClass"
					   cssStyle = "cursor: pointer"
					/>
				</td>
				
				<td width="80" align="center">

		    			<img
								src="<c:url 
  					         	value="/resources/images/next.jpg"/>"
  					         	onMouseOver="this.style.cursor='pointer'"
  					         	id="nextButton"
						/>

				<br><br>
				
				<c:if test="${voice.usingVoice eq true}">
				
					<c:choose>

						<c:when test="${voice.enableSpeaking eq true}">
						
							<img
								src="<c:url 
  					        	value="/resources/images/speak.png"/>"
  					        	onMouseOver="this.style.cursor='pointer'"
  					        	id="disableSpeakingId"
							/>

						</c:when>

						<c:otherwise>

							<img
								src="<c:url 
  					        	value="/resources/images/silence.png"/>"
  					        	onMouseOver="this.style.cursor='pointer'"
  					        	id="enableSpeakingId"
							/>

						</c:otherwise>

					</c:choose>

				</c:if>

				<br><br>

		    			<img
								src="<c:url 
  					         	value="/resources/images/back.jpg"/>"
							 	onMouseOver="this.style.cursor='pointer'"
						     	id="backButton"
						/>	

		    	</td>
		    		
				<td height=600px;>
					<form:textarea path="selected_2_Text"
					   type="text"
					   class="textClass"
					   cssStyle = "cursor: pointer"
					/>
				</td>
			</tr>
			
			</table>

		</div>
	
	</form:form>

		<script type="text/javascript" charset="utf-8">
			$("#nextButton").click(function() {
				$("#eventId").val(10);
				$("#voiceForm").submit();
			});

			$("#backButton").click(function() {
				$("#eventId").val(11);
				$("#voiceForm").submit();
			});

			$("#churchTabId").click(function() {
			$("#eventId").val(1);
			$("#voiceForm").submit();
			});

			$("#settingsTabId").click(function() {
			$("#eventId").val(2);
			$("#voiceForm").submit();
			});

			$("#screensaverTabId").click(function() {
			$("#eventId").val(4);
			$("#voiceForm").submit();
			});

			$("#codeTabId").click(function() {
			$("#eventId").val(5);
			$("#voiceForm").submit();
			});

			$("#bibleTabId").click(function() {
			$("#eventId").val(3);
			$("#voiceForm").submit();
			});

			$("#on_1_Id").click(function() {
			$("#eventId").val(12);
			$("#voiceForm").submit();
			});

			$("#off_1_Id").click(function() {
			$("#eventId").val(13);
			$("#voiceForm").submit();
			});

			$("#on_2_Id").click(function() {
			$("#eventId").val(14);
			$("#voiceForm").submit();
			});

			$("#off_2_Id").click(function() {
			$("#eventId").val(15);
			$("#voiceForm").submit();
			});

			$("#enableSpeakingId").click(function() {
			$("#eventId").val(16);
			$("#voiceForm").submit();
			});

			$("#disableSpeakingId").click(function() {
			$("#eventId").val(17);
			$("#voiceForm").submit();
			});

			$("#feedbackTabId").click(function() {
			$("#eventId").val(18);
			$("#voiceForm").submit();
			});
			
			$("#thankYouTabId").click(function() {
			$("#eventId").val(19);
			$("#voiceForm").submit();
			});
		</script>

</body>
</html>