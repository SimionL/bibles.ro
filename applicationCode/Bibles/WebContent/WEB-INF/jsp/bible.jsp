<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" buffer="256kb" %>
<html>
<head>
<title>${bible.bible}</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<link   rel="stylesheet"       href="<c:url value="/resources/css/style.css" />"        />

<script type="text/javascript" charset="utf-8">

function startListening() {

	try {
		
		if('${bible.usingVoice}' == 'true'){

	        var recognizer = null;

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

	          recognizer.lang = '${bible.languageCode}';
	          recognizer.continuous = true;
	          recognizer.maxAlternatives = 0;
	          recognizer.interimResults = false;

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
			           $("#eventId").val(15);
			   		   $("#bibleForm").submit();
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
	  } catch(ex) {
		
	  }
}

	$(document).ready(function(){
    	$("form").submit(function(){

    		try{
    			if('speechSynthesis' in window){
    				window.speechSynthesis.cancel();
    			}
    		}
    		catch(ex){
    		}
    	});
	});

function onLoadSteps(){

 	try{

		if('${bible.firstAccess}' === 'true'){
			
			var selectedProfile          = JSON.parse(localStorage.getItem('selectedProfile'));
			var messagesEncapsulation    = JSON.parse(localStorage.getItem('messagesEncapsulation'));
			var userEmail                = JSON.parse(localStorage.getItem('userEmail'));
			var emailFrom                = JSON.parse(localStorage.getItem('emailFrom'));
			var automatSendMessage       = JSON.parse(localStorage.getItem('automatSendMessage'));
			
			if(selectedProfile != null && selectedProfile != 'null' && selectedProfile != 'undefined'){
				$('.selectedOldProfile').val(selectedProfile);
			}

			if(messagesEncapsulation != null && messagesEncapsulation != 'null' && messagesEncapsulation != 'undefined'){
				$('.messagesEncapsulation').val(messagesEncapsulation);
			}
			
			if(userEmail != null && userEmail != 'null' && userEmail != 'undefined'){
				$('.userEmail').val(userEmail);
			}
			
			if(emailFrom != null && emailFrom != 'null' && emailFrom != 'undefined'){
				$('.emailFrom').val(emailFrom);
			}
			
			if(automatSendMessage != null && automatSendMessage != 'null' && automatSendMessage != 'undefined'){
				$('.automatSendMessage').val(automatSendMessage);
			}
			
			$('.eventId').val(19);
			$('#bibleForm').submit();
		}
		
		localStorage.setItem('selectedProfile', JSON.stringify('${bible.selectedProfile}'));

 		if('${bible.usingVoice}' == 'true'){

 			var audioText = "${bible.theSpokenWords}".replace(/[\|&;\$%@"<>\(\)\+,]/g, "");

 			if('speechSynthesis' in window && audioText != null && audioText != 'null' && audioText != 'undefined' && audioText != ''){

 				window.speechSynthesis.onvoiceschanged = function() {

 				var talks = new SpeechSynthesisUtterance();

				if(talks !== null && talks != null && talks !== 'undefined'){

					talks.text = audioText;
					talks.volume = 1;
					talks.rate   = 1;
					talks.pitch  = 1;

 					var newLang;
 					var voiceLang = '${bible.languageCode}';
 					var voices = window.speechSynthesis.getVoices();

					if(voices != null && voices != 'null' && voices != 'undefined' && voices != '' && voices.length > 0){
						for (var i in voices) {
						    newLang = voices[i];
							if(newLang != null && newLang != 'null' && newLang != 'undefined' && newLang != ''){
								var localVoicelang = newLang.lang;
								if(localVoicelang != null && localVoicelang != 'null' && localVoicelang != 'undefined' && localVoicelang != '' && localVoicelang.startsWith('${bible.languageCode}')){

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
		if('${bible.usingVoice}' == 'true'){
			startListening();
		}
	}
}

	function validateSearch(selectVersion) {

		var selectedVersion = document.getElementById('selectedVersionId');
		
		if (selectedVersion != 'undefined' && selectedVersion != null && selectedVersion.value != ''
			&& selectedVersion.value != "" && selectedVersion.value != selectVersion) {
			document.getElementById('selectedVersionId').style.borderColor = "";
			return true;
		}
		else if(selectedVersion == null){
			return true;
		}
		else{
			document.getElementById('selectedVersionId').style.borderColor = "red";
			return false;
		}
	}

	function setPopup(openPopup, popupTitle) {
	  if(openPopup === "true"){
		  var myWindow =window.open("", '${bible.popupId}', "width=800,height=400,menubar=no,titlebar=no,toolbar=no,location=no,status=no,fullscreen=yes,channelmode=yes");
		  var doc = myWindow.document;
			doc.open();
			doc.write(document.getElementById('popupInformationId').value);
			doc.close();
			doc.title = popupTitle;
			
			myWindow.onbeforeunload = function () {
				$('#openPopupId').val(false);
			};
			myWindow.webkitEnterFullscreen();
	    }
	  }

</script>

<style>

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
<body  onload="onLoadSteps();setPopup('${bible.openPopup}', '${bible.bible}');">

<div class="tab">
  <button class="active">${bible.bible}</button>
  <button id="churchTabId">${bible.church}</button>
  <button id="settingsTabId">${bible.settings}</button>
  <button id="screensaverTabId">${bible.screensaver}</button>
  <button id="codeTabId">${bible.code}</button>
  <button id="voiceTabId">${bible.voice}</button>
  <button id="feedbackTabId">${bible.feedback}</button>
  <button id="thankYouTabId">${bible.thankYou}</button>
</div>

<div style = "position: absolute; left: 50%;">
	<table>
			<tr>
				<td>
					<c:if test="${not empty bible.error}">
						<b><font color="red">${bible.error}</font></b>
					</c:if>
				</td>
				<td>
					<c:if test="${not empty bible.ok}">
						<b><font color="green">${bible.ok}</font></b>
					</c:if>
				</td>
			</tr>
		</table>
</div>

	<form:form action="be_blessed" 
			   modelAttribute="bible"
			   enctype="multipart/form-data" 
			   id="bibleForm"
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

		<form:input path="popupInformation" 
					type="text" 
					value="${bible.popupInformation}"
					id="popupInformationId" 
				    hidden="true"
		/>
		
		<form:input path="userEmail" 
		    		type="text"
					class="userEmail" 
				    hidden="true"
		/>

		<form:input path="emailFrom" 
		    		type="text"
					class="emailFrom" 
				    hidden="true"
		/>

		<form:input path="automatSendMessage" 
		    		type="text"
					class="automatSendMessage" 
				    hidden="true"
		/>

		<form:input path="messagesEncapsulation" 
		    		type="text"
					class="messagesEncapsulation" 
				    hidden="true"
		/>

		<form:input path="selectedOldProfile" 
		    		type="text"
					class="selectedOldProfile" 
				    hidden="true"
		/>

		<div class="position">

			<br>

		<table>
			<tr>
					   <td>
						   <img
							  src="<c:url 
  					   	      value="/resources/images/back.jpg"/>"
							  class="backButton" 
							  id="backButton"
							/>
					
						<img
							src="<c:url 
  					        value="/resources/images/next.jpg"/>"
							class="nextButton" 
							id="nextButton"
						/>
						
						<img
							src="<c:url 
  							value="/resources/images/search.jpg"/>"
  							class="searchButton"
  							id="searchId"
						  />
					
						<img
							src="<c:url 
  							value="/resources/images/newWindow.jpg"/>"
  							class="newWindow"
  							id="newWindowId"
						  />
						<img
							src="<c:url 
  							value="/resources/images/email.png"/>"
  							style = "cursor: pointer"
  							onclick="$('.eventId').val(18);$('#bibleForm').submit();"
						  />
						  <form:select path="selectedProfile" 
											 items='${bible.profiles}'
											 value='${bible.selectedProfile}'
											 selected="true"
											 onchange="$('.eventId').val(21);this.form.submit();" 
											 cssStyle = "position: relative ; cursor: pointer ; bottom: 4.5px;"
						  />
					  </td>
				</tr>
			</table>
			<table class="mainTable">
				<tr>
					<td>
						<form:select path="history"
			 			         
					             	 cssStyle = "cursor: pointer;"
					                 onchange="if (typeof(this.selectedIndex) != 'undefined' && $('select').val() != '${bible.searchHistory}'){$('.eventId').val(11);this.form.submit();}" 
						 >
    						<c:forEach items='${bible.historyMap}' var="hm">			
        						<form:option value="${hm.value}">
            					       ${hm.key}
        						</form:option>                    
    						</c:forEach>
    					
						 </form:select>
                    </td>
					<td>
						 
						 <form:input path="searchVerse" 
									 type="text"
									 cssStyle = "cursor: pointer;"
									 placeholder='${bible.placeholderReference}'
									 onkeydown="$('.eventId').val(7);if(event.keyCode==13){this.form.submit();};" 
						 />
					</td>
				</tr>
			</table>

			<table class="mainTable">
			
				<tr>
					<td colspan="1">
					<c:choose>

				<c:when test="${not empty bible.selectedVersion}">

					<form:select path="selectedVersion" 
								 items='${bible.versions}'
								 value="${bible.selectedVersion}" 
								 id="selectedVersionId" 
								 selected="true"
								 onchange="$('.eventId').val(3);this.form.submit();" 
								 cssStyle = "cursor: pointer"
					/>

				</c:when>

				<c:otherwise>

					<form:select path="selectedVersion" 
								 items='${bible.versions}'
						         onchange="$('.eventId').val(3);this.form.submit();" 
						         cssStyle = "cursor: pointer"
					/>

				</c:otherwise>

			</c:choose>
					</td>
					
					<td colspan="1">
					<c:choose>

				<c:when test="${not empty bible.selectedBook}">

					<form:select path="selectedBook" 
								 items='${bible.books}'
								 value='${bible.selectedBook}' 
								 selected="true"
						         onchange="$('.eventId').val(4);this.form.submit();" 
						         cssStyle = "cursor: pointer"
					/>

				</c:when>

				<c:otherwise>

					<form:select path="selectedBook" 
								 items='${bible.books}'
								 onchange="$('.eventId').val(4);this.form.submit();" 
								 cssStyle = "cursor: pointer"
					/>

				</c:otherwise>

			</c:choose>
					</td>
					
					<td colspan="1">
					<c:choose>

				<c:when test="${not empty bible.selectedChapter}">

					<form:select path="selectedChapter" 
							     items='${bible.chapters}'
								 value='${bible.selectedChapter}' 
								 selected="true"
								 onchange="$('.eventId').val(5);this.form.submit();" 
								 cssStyle = "cursor: pointer"
					/>

				</c:when>

				<c:otherwise>

					<form:select path="selectedChapter" 
								 items='${bible.chapters}'
								 onchange="$('.eventId').val(5);this.form.submit();" 
								 cssStyle = "cursor: pointer"
					/>

				</c:otherwise>

			</c:choose>
					</td>
					
					<td colspan="1">
						<c:choose>

							<c:when test="${not empty bible.selectedVerse}">

								<form:select path="selectedVerse" 
											 items='${bible.verses}'
											 value='${bible.selectedVerse}'
											 selected="true"
											 onchange="$('.eventId').val(6);this.form.submit();" 
											 cssStyle = "cursor: pointer"
								/>

							</c:when>

							<c:otherwise>

								<form:select path="selectedVerse" 
										     items='${bible.verses}'
											 onchange="$('.eventId').val(6);this.form.submit();" 
											 cssStyle = "cursor: pointer"
								/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<form:input path="searchText" 
									type="text"
									cssClass = "searchField"
									placeholder='${bible.searchByText.concat(": ").concat(bible.placeholderSuggestion)}'
									onkeydown="if(event.keyCode==13 && validateSearch('${bible.selectVersion}')){$('.eventId').val(10);this.form.submit();};"
						/>
					</td>
				</tr>
			</table>
			
			<c:if test="${not empty bible.verseValue}">
              <font size='${bible.formFontSelected}'>
				<br>
				
				<form:checkbox path="selectAll" 
				               cssStyle = "cursor: pointer" 
				               onchange="$('.eventId').val(20);this.form.submit();"/>
				<br><br>
				<c:forEach var="verse" items='${bible.verseValue}' varStatus="verseStatus">

					<c:if test="${not empty verse}">
					
					
					
							<form:input path="verseValue[${verseStatus.index}].verse"
		    		            		type="text"
				                		hidden="true"
		             		/>
							
							<form:checkbox path="verseValue[${verseStatus.index}].selected"
								   	   	   	   cssStyle = "cursor: pointer"         
							/>
					
						<c:choose>

							<c:when test="${bible.wordWrap == 'true'}">
								<span style="word-wrap: break-word;"> ${verse.verse} </span>
							</c:when>

							<c:otherwise>
								<span style="white-space: nowrap"> ${verse.verse} </span>
							</c:otherwise>
                        </c:choose>
	
					</c:if>
					<br><br>
					<c:if test="${(not empty verse.references) and (bible.displayReference == 'true')}">
			
						  <c:out value='${bible.references}' />
						  <br><br>
			
					      <c:forEach var="references" items="${verse.references}">

							<c:choose>

							 <c:when test="${bible.wordWrap == 'true'}">

									<span style="word-wrap: break-word;">
									
											<c:if test="${not empty references.key}">
				             		       		${references.key}
				             		      		<br>
											</c:if> 
											<c:if test="${not empty references.value}">
				             					${references.value}
				             					<br>
											</c:if>

									</span>
								</c:when>

								<c:otherwise>
									<span style="white-space: nowrap">
									
											<c:if test="${not empty references.key}">
				             		       		${references.key}
				             		      		<br>
											</c:if> 
											<c:if test="${not empty references.value}">
				             					${references.value}
				             					<br>
											</c:if>
									</span>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				            <br><br><br> 
					</c:if>
				</c:forEach>

				<br> 
				</font>
			  </c:if>
		</div>
	</form:form>

		<script type="text/javascript" charset="utf-8">
			$("#backButton").click(function() {
				$("#eventId").val(8);
				$("#bibleForm").submit();
			});

			$("#nextButton").click(function() {
				$("#eventId").val(9);
				$("#bibleForm").submit();
			});

			$("#newWindowId").click(function() {

			 if(document.getElementById('openPopupId').value === "false"){

				$('#openPopupId').val(true);
				
				var myWindow =window.open('', '${bible.popupId}', "width=800,height=400,menubar=no,titlebar=no,toolbar=no,location=no,status=no,fullscreen=yes,channelmode=yes");
				var doc = myWindow.document;
				doc.open();
				doc.write(document.getElementById('popupInformationId').value);
				doc.close();
				doc.title = '${bible.bible}';
				
				myWindow.onbeforeunload = function () {
					$('#openPopupId').val(false);
				};
				myWindow.webkitEnterFullscreen();
			  }
			});

			$("#searchId").click(function() {
				$("#eventId").val(10);
				$("#bibleForm").submit();
			});

			$("#churchTabId").click(function() {
			$("#eventId").val(1);
			$("#bibleForm").submit();
			});

			$("#settingsTabId").click(function() {
			$("#eventId").val(2);
			$("#bibleForm").submit();
			});

			$("#screensaverTabId").click(function() {
			$("#eventId").val(12);
			$("#bibleForm").submit();
			});

			$("#codeTabId").click(function() {
			$("#eventId").val(13);
			$("#bibleForm").submit();
			});

			$("#voiceTabId").click(function() {
			$("#eventId").val(16);
			$("#bibleForm").submit();
			});

			$("#feedbackTabId").click(function() {
			$("#eventId").val(14);
			$("#bibleForm").submit();
			});

			$("#thankYouTabId").click(function() {
			$("#eventId").val(17);
			$("#bibleForm").submit();
			});
		</script>
</body>
</html>