<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" buffer="256kb" %>
<html>
<head>
<title>${code.code}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

	<link   rel="stylesheet"       href="<c:url value="/resources/css/style.css" />"        />

<script type="text/javascript" charset="utf-8">

function startListening() {

	try {
		
		if('${code.usingVoice}' == 'true'){

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

	            recognizer.lang = '${code.languageCode}';
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
			  	  		$("#eventId").val(7);
			   	  		$("#codeForm").submit();
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

	function setPopup(openPopup, popupTitle) {
	  if(openPopup === "true"){
		  var myWindow =window.open("", document.getElementById('popupNameId').value, "width=800,height=400,menubar=no,titlebar=no,toolbar=no,location=no,status=no,fullscreen=yes,channelmode=yes,scrollbars=no");
		  var doc = myWindow.document;
			doc.open();
			doc.write(document.getElementById('popupInformationId').value);
			doc.close();
			doc.title = popupTitle;
			
			myWindow.onbeforeunload = function () {
				$('#openPopupId').val(false);
			};
	    }
	  }

	function submitFileSelection(elementId) {

		$('#selectedId').val(elementId);
		$('#eventId').val(5);
		$("#codeForm").submit();
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
<body  onload="startListening();setPopup('${code.openPopup}', '${code.fileName}');">

<div class="tab">
  <button id="bibleTabId">${code.bible}</button>
  <button id="churchTabId">${code.church}</button>
  <button id="settingsTabId">${code.settings}</button>
  <button id="screensaverTabId">${code.screensaver}</button>
  <button class="active">${code.code}</button>
  <button id="voiceTabId">${code.voice}</button>
  <button id="feedbackTabId">${code.feedback}</button>
  <button id="thankYouTabId">${code.thankYou}</button>
</div>

	<div align="center">
		<c:if test="${not empty code.error}">
			<font color="red">${code.error}</font>
			<br><br>
		</c:if>
	</div>

	<form:form action="code" 
			   modelAttribute="code"
			   enctype="multipart/form-data" 
			   id="codeForm"
			   accept-charset="UTF-8"
	>

		<form:input path="openPopup" 
					type="text" 
					id="openPopupId" 
					hidden="true" 
		/>

		<form:input path="eventId" 
					type="text" 
					class="eventId" 
					hidden="true" 
		/>

		<form:input path="selectedFileValue" 
					id="selectedId" 
					hidden="true" 
		/>
		
		<form:input path="popupInformation" 
					type="text" 
					value="${code.popupInformation}"
					id="popupInformationId" 
				    hidden="true"
		/>  
		
		<form:input path="popupId" 
					type="text"
					id="popupNameId" 
				    hidden="true"
		/>
		
		<form:input path="identifiedWords"
					id="identifiedWordsId" 
				    hidden="true"
		/>

		<div class="position">
		<br>
        <c:forEach items="${code.folder}" var="element"> 
  			<tr>
    			<td>  
                    ${element.margin}

		    	<c:choose>

					<c:when test="${element.file eq true}">

    	 	        	 <form:label path="selectedFileId"
    	 	            		 	 cssStyle = "cursor: pointer"
					    		 	 onclick="submitFileSelection('${element.id}');"
		    		 	 >
		    		     	${element.name}
		    		 	 </form:label>

					</c:when>

					<c:otherwise>
						<img
							src="<c:url 
  					        value="/resources/images/folder.jpg"/>"
						/>${element.name}
					</c:otherwise>

			</c:choose>

    			     <br>
    			</td>    
  			</tr>
		</c:forEach>

		</div>
	</form:form>

		<script>
			$("#bibleTabId").click(function() {
			$("#eventId").val(1);
			$("#codeForm").submit();
			});
			
			$("#churchTabId").click(function() {
			$("#eventId").val(2);
			$("#codeForm").submit();
			});
			
			$("#settingsTabId").click(function() {
			$("#eventId").val(3);
			$("#codeForm").submit();
			});
			
			$("#screensaverTabId").click(function() {
			$("#eventId").val(4);
			$("#codeForm").submit();
			});
			
			$("#voiceTabId").click(function() {
			$("#eventId").val(6);
			$("#codeForm").submit();
			});
			
			$("#feedbackTabId").click(function() {
			$("#eventId").val(8);
			$("#codeForm").submit();
			});
			
			$("#thankYouTabId").click(function() {
			$("#eventId").val(9);
			$("#codeForm").submit();
			});
		</script>
</body>
</html>