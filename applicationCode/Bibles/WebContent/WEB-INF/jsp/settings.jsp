<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" buffer="2048kb" %>
<html>
<head>
<title>${settings.settings}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

	<link   rel="stylesheet" href="<c:url value="/resources/css/style.css" />"        />

<script type="text/javascript" charset="utf-8">

function startListening() {

	try {
		
		if('${settings.usingVoice}' == 'true'){

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

	            recognizer.lang = '${settings.languageCode}';
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
			        $("#eventId").val(11);
			        $("#settingsForm").submit();
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

</script>

<style type="text/css">
      .userMessageClass{
    	font-size: 25px;
		border: 0 none white;
		width: 100%;
		max-width: 100%;
		height: 100%;
		max-height: 100%;
		word-wrap: break-word;
  		text-align: center;
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
</head>
<body onload="startListening();">

<div class="tab">
  <button id="bibleTabId">${settings.bible}</button>
  <button id="churchTabId">${settings.church}</button>
  <button class="active">${settings.settings}</button>
  <button id="screensaverTabId">${settings.screensaver}</button>
  <button id="codeTabId">${settings.code}</button>
  <button id="voiceTabId">${settings.voice}</button>
  <button id="feedbackTabId">${settings.feedback}</button>
  <button id="thankYouTabId">${settings.thankYou}</button>
</div>

<br><br>
<div class="tab">
 <div class="tab">
  <button class="active"       >${settings.bible}</button>
  <button id="popupSettingsId" >${settings.popup}</button>
  <button id="referenceSettingsId" >${settings.reference}</button>
</div>
</div>

	<div align="center">
		<c:if test="${not empty settings.error}">
			<font color="red">${settings.error}</font>
			<br><br>
		</c:if>
	</div>

	<form:form action="blessed" 
			   modelAttribute="settings"
			   enctype="multipart/form-data" 
			   id="settingsForm"
			   name="settingsFormName"
			   accept-charset="UTF-8"
	>

		<form:input path="openPopup" 
					type="text" 
					id="openPopupId" 
					hidden="true" 
		/>
		
		<form:input path="identifiedWords"
					id="identifiedWordsId" 
				    hidden="true"
		/>

		<form:input path="eventId" type="text" class="eventId" hidden="true" />

		<table style="width: 20%" class="tableClass" >

			<tr>
				<td colspan='1'><c:out value="${settings.wordWrapLabel}" /></td>
				<td align="left" colspan='1'>
					<form:checkbox path="wordWrap"
								   cssStyle = "cursor: pointer"
								   onchange="$('.eventId').val(17);this.form.submit();"
					/>
				</td>
				
			</tr>

			<tr>
 
				<td colspan='1'><c:out value="${settings.highlightsTextLabel}" />
				
				  	<c:if test="${settings.highlights == 'true'}">

							<br><br>&nbsp;&nbsp;&nbsp;&nbsp;
							<c:out value="${settings.exactColorsLabel}" />
 							<br>&nbsp;&nbsp;&nbsp;&nbsp;

						  	<form:input path="exactColorPaletteSelected" 
										type="color"
										value="${settings.exactColorPaletteSelected}"
						                onchange="$('.eventId').val(10);this.form.submit();"
							/>

							<br>&nbsp;&nbsp;&nbsp;&nbsp;
							<c:out value="${settings.inexactColorsLabel}" />
		        			<br>&nbsp;&nbsp;&nbsp;&nbsp;

							<form:input path="inexactColorPaletteSelected" 
										type="color"
										value="${settings.inexactColorPaletteSelected}"
						                onchange="$('.eventId').val(10);this.form.submit();"
							/>

					</c:if>
				</td>
				<td align="left" colspan='1'>
				    <form:checkbox path="highlights" 
								   cssStyle = "cursor: pointer"
								   onchange="$('.eventId').val(9);this.form.submit();" 
					/>
				</td>

			</tr>
			<tr>
				<td colspan='1'><c:out value="${settings.displayReferencesLabel}" /></td>
				<td align="left" colspan='1'>
					<form:checkbox path="displayReference"
									cssStyle = "cursor: pointer"
									onchange="$('.eventId').val(14);this.form.submit();" 
					/>
				</td>

			</tr>

			<tr>
				<td colspan='1'><c:out value="${settings.displayEntireChapterLabel}" /></td>
				<td align="left" colspan='1'>
				    <form:checkbox path="displayEntireChapter"
								   cssStyle = "cursor: pointer"
								   onchange="$('.eventId').val(16);this.form.submit();" 
					/>
				</td>
				
			</tr>
			
			<tr>
			
				<td align="left" colspan='1'><c:out value="${settings.formFontLabel}" /></td>
			
				<td align="left" colspan='1'>

				 	<form:select path="formFontSelected" 
	            			  	 items="${settings.formFontList}"
							  	 value="${settings.formFontSelected}" 
							  	 selected="true"
							 	 onchange="$('.eventId').val(15);this.form.submit();" 
							 	 cssStyle = "cursor: pointer"
				   	/>
				</td>
			</tr>

			<tr>
				<td align="left" colspan='1'><c:out value="${settings.searchBlockLength}"/></td>
				
				<td align="left" colspan='1'>

	            	<form:select path="searchBlockLengthSelection" 
	            			   	 items="${settings.searchBlockLengthSelectionList}"
							 	 value="${settings.searchBlockLengthSelection}" 
							 	 selected="true"
							  	 onchange="$('.eventId').val(12);this.form.submit();" 
							  	 cssStyle = "cursor: pointer"
				    />
				</td>
			</tr>
			
			<tr>
				<td align="left" colspan='1'><c:out value="${settings.searchLevel}"/></td>
				
				<td align="left" colspan='1'>

	            	<form:select path="searchLevelSelected" 
	            			   	 items="${settings.searchAreaOptions}"
							  	 onchange="$('.eventId').val(13);this.form.submit();" 
							  	 cssStyle = "cursor: pointer"
				    />
				</td>
			</tr>

			<tr>
				<td align="left" colspan='1'><c:out value="${settings.selectLanguage}" /></td>

				<td align="left" colspan='1'>

						<form:select path="selectedLanguage"
									 items="${settings.languages}"
									 onchange="$('.eventId').val(3);this.form.submit();" 
									 cssStyle = "cursor: pointer"
						/>

				</td>
			</tr>

		</table>
	</form:form>
	
		<script type="text/javascript" charset="utf-8">
			$("#bibleTabId").click(function() {
			$("#eventId").val(1);
			$("#settingsForm").submit();
			});

			$("#churchTabId").click(function() {
			$("#eventId").val(2);
			$("#settingsForm").submit();
			});

			$("#screensaverTabId").click(function() {
			$("#eventId").val(4);
			$("#settingsForm").submit();
			});

			$("#codeTabId").click(function() {
			$("#eventId").val(5);
			$("#settingsForm").submit();
			});

			$("#voiceTabId").click(function() {
			$("#eventId").val(8);
			$("#settingsForm").submit();
			});

			$("#popupSettingsId").click(function() {
			$("#eventId").val(6);
			$("#settingsForm").submit();
			});

			$("#referenceSettingsId").click(function() {
			$("#eventId").val(7);
			$("#settingsForm").submit();
			});

			$("#feedbackTabId").click(function() {
			$("#eventId").val(18);
			$("#settingsForm").submit();
			});
			
			$("#thankYouTabId").click(function() {
			$("#eventId").val(19);
			$("#settingsForm").submit();
			});
		</script>
</body>
</html>