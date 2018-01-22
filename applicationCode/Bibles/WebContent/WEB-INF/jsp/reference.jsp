<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" buffer="2048kb" %>
<html>
<head>
<title>${reference.reference}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

	<link   rel="stylesheet"       href="<c:url value="/resources/css/style.css" />"        />

<script type="text/javascript" charset="utf-8">

function startListening() {

	try {
		
		if('${reference.usingVoice}' == 'true'){

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

	            recognizer.lang = '${reference.languageCode}';
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
			       $("#eventId").val(6);
			       $("#referenceForm").submit();
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
		  if(openPopup  === "true"){
			  
			 var myWindow = window.open("", '${reference.popupId}', "width=800,height=400,menubar=no,titlebar=no,toolbar=no,location=no,status=no,fullscreen=yes,channelmode=yes,scrollbars=yes");
 
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
<body  onload="startListening();setPopup('${reference.openPopup}', '${reference.bible}');">

<div class="tab">
  <button id="bibleTabId">${reference.bible}</button>
  <button id="churchTabId">${reference.church}</button>
  <button class="active">${reference.settings}</button>
  <button id="screensaverTabId">${reference.screensaver}</button>
  <button id="codeTabId">${reference.code}</button>
  <button id="voiceTabId">${reference.voice}</button>
  <button id="feedbackTabId">${reference.feedback}</button>
  <button id="thankYouTabId">${reference.thankYou}</button>
</div>

<br><br>
<div class="tab">
  <button id="BibleSettingsId" >${reference.bible}</button>
  <button id="popupSettingsId" >${reference.popup}</button>
  <button class="active"       >${reference.reference}</button>
</div>

	<div align="center">
		<c:if test="${not empty reference.error}">
			<font color="red">${reference.error}</font>
			
		</c:if>
	</div>

	<form:form action="reference" 
			   modelAttribute="reference"
			   enctype="multipart/form-data" 
			   id="referenceForm"
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
		
		<form:input path="popupInformation" 
					type="text" 
					value="${reference.popupInformation}"
					id="popupInformationId" 
				    hidden="true"
		/>  

		<form:input path="eventId" type="text" class="eventId" hidden="true" />

		<table style="width: 20%" class="tableClass" >

			<tr> 
			
				<td colspan='1'><c:out value="${reference.boldLabel}" /></td>
				<td colspan='1'>
						<form:checkbox path="referencePopupBoldSelected" 
								   	   value="" 
								   	   cssStyle = "cursor: pointer"
								   	   onchange="$('.eventId').val(41);this.form.submit();" 
						/>
				</td>
				
				<td colspan='1'>&nbsp;&nbsp;<c:out value="${reference.enterUpPopupLabel}" /></td>
			
				<td colspan='1'>
								<form:select path="referenceEnterUpPopupSelected"
						 	 	 	 		 onchange="$('.eventId').val(42);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${reference.referenceEnterUpPopupList}"
						 		 />
				</td>
			</tr> 
			
			<tr> 
			
				<td colspan='1'><c:out value="${reference.displayVersionLabel}" /></td>

				<td colspan='1'>
						<form:checkbox path="referenceDisplayVersionSelected" 
								       value="" 
								       cssStyle = "cursor: pointer"
								       onchange="$('.eventId').val(40);this.form.submit();" 
					    />
				</td>

				<td colspan='1'>&nbsp;&nbsp;<c:out value="${reference.enterDownPopupLabel}" /></td>
					
				<td colspan='1'>
								<form:select path="referenceEnterDownPopupSelected"
						 	 	 			 onchange="$('.eventId').val(42);this.form.submit();"
						 	 	 			 selected="true"
						 	 	 			 cssStyle = "cursor: pointer"
						 	 	 			 items="${reference.referenceEnterDownPopupList}"
								/>
				</td>
			</tr>
			
			<tr> 
			
				<td colspan='1'><c:out value="${reference.displayBookLabel}" /></td>
				
				<td colspan='1'>
						 <form:checkbox path="referenceDisplayBookSelected" 
								   		value="" 
								   		cssStyle = "cursor: pointer"
								   		onchange="$('.eventId').val(40);this.form.submit();" 
						 />
				</td>

				<td colspan='1'>&nbsp;&nbsp;<c:out value="${reference.fontLabel}" /></td>
				
				<td colspan='1'>
								<form:select path="referenceSizePopupSelected"
						 	 	 			 onchange="$('.eventId').val(46);this.form.submit();"
						 	 	 			 selected="true"
						 	 	 			 cssStyle = "cursor: pointer"
						 	 	 			 items="${reference.referenceSizePopupList}"
								/>
				</td>

			</tr>
			
			<tr> 
			
				<td colspan='1'><c:out value="${reference.displayChapterLabel}" /></td>
				
				<td colspan='1'>
					<form:checkbox path="referenceDisplayChapterSelected" 
								   value="" 
								   cssStyle = "cursor: pointer"
								   onchange="$('.eventId').val(40);this.form.submit();" 
					 	 />
				</td>
	
				<td colspan='1'>&nbsp;&nbsp;<c:out value="${reference.referenceLetterSpacingPopupLabel}" /></td>

				<td colspan='1'>
								<form:select path="referenceLetterSpacingPopupSelected"
						 	 	 	 		 onchange="$('.eventId').val(59);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${reference.referenceLetterSpacingPopupList}"
						 		 />
				</td>

			</tr>

			<tr>

				<td colspan='1'><c:out value="${reference.displayVerseLabel}" /></td>
				
				<td colspan='1'>
						 <form:checkbox path="referenceDisplayVerseSelected" 
								   		value="" 
								   		cssStyle = "cursor: pointer"
								   		onchange="$('.eventId').val(40);this.form.submit();" 
					 	 />
				</td>

				<td colspan='1'>&nbsp;&nbsp;<c:out value="${reference.referenceAlignmentLabel}" /></td>
							
					
				<td colspan='1'>
								<form:select path="referenceAlignPopupSelected"
						 	 	 			 onchange="$('.eventId').val(45);this.form.submit();"
						 	 	 			 selected="true"
						 	 	 			 cssStyle = "cursor: pointer"
						 	 	 			 items="${reference.referenceAlignPopupMap}"
								/>
				</td>

			</tr>
			
			
			<tr>
				<td rowspan='4'><c:out value="${reference.referenceWordsSpacePopupLabel}" /></td>
				<td colspan='1'>
						<form:select path="referenceVersionSpaceSelected"
						 	 	 	 onchange="$('.eventId').val(43);this.form.submit();"
						 	 	 	 selected="true"
						 	 	 	 cssStyle = "cursor: pointer"
						 	 	 	 items="${reference.referenceVersionSpaceList}"
					/>
				</td>
				
				<td colspan='1'>&nbsp;&nbsp;<c:out value="${reference.referenceFontStylePopupLabel}" /></td>

				<td colspan='1'>
								<form:select path="referenceFontStylePopupSelected"
						 	 	 			 onchange="$('.eventId').val(58);this.form.submit();"
						 	 	 			 selected="true"
						 	 	 			 cssStyle = "cursor: pointer"
						 	 	 			 items="${reference.referenceFontStylePopupList}"
								/>
				</td>
			</tr>
			
			
			<tr>
				
				<td colspan='1'>
					<form:select path="referenceBookSpaceSelected"
						 	 	 onchange="$('.eventId').val(43);this.form.submit();"
						 	 	 selected="true"
						 	 	 cssStyle = "cursor: pointer"
						 	 	 items="${reference.referenceBookSpaceList}"
					/>
				</td>
				
				<td colspan='1'>&nbsp;&nbsp;<c:out value="${reference.referenceFontDecorationPopupLabel}" /></td>
			
				<td colspan='1'>
								<form:select path="referenceFontDecorationPopupSelected"
						 	 	 	 		 onchange="$('.eventId').val(61);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${reference.referenceFontDecorationPopupList}"
						 		 />
				</td>
			</tr>
			
			<tr>
				
				<td colspan='1'>
					<form:select path="referenceChapterSpaceSelected"
						 	 	 onchange="$('.eventId').val(43);this.form.submit();"
						 	 	 selected="true"
						 	 	 cssStyle = "cursor: pointer"
						 	 	 items="${reference.referenceChapterSpaceList}"
					/>
				</td>
				
				<td colspan='1'>&nbsp;&nbsp;<c:out value="${reference.referenceFontFamilyPopupLabel}" /></td>
						
				<td colspan='1'>
								<form:select path="referenceFontFamilyPopupSelected"
						 	 	 	 		 onchange="$('.eventId').val(60);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${reference.referenceFontFamilyPopupList}"
						 		 />
				</td>
			</tr>
			
			<tr>
				
				<td colspan='1'>
					<form:select path="referenceDotsSpaceSelected"
						 	 	 onchange="$('.eventId').val(43);this.form.submit();"
						 	 	 selected="true"
						 	 	 cssStyle = "cursor: pointer"
						 	 	 items="${reference.referenceDotsSpaceList}"
					/>
				</td>
			</tr>
			
			<tr>
				<td style="font-size: 19px;" align="center" colspan="4"><c:out value="${reference.schemaLabel}" /></td>
			</tr>
				
			<tr>
						<td align="center">
							<form:input path="referenceColorPopupPaletteSelected_1" 
										type="color"
										value="${reference.referenceColorPopupPaletteSelected_1}"
						                onchange="$('.eventId').val(44);this.form.submit();"
							/>
						</td>

							<td colspan='1'>
									<form:select path="referenceParamSelected_1"
						 	 	 				onchange="$('.eventId').val(48);this.form.submit();"
						 	 	 				selected="true"
						 	 	 				cssStyle = "cursor: pointer"
						 	 	 				items="${reference.referenceParamList_1}"
									/>
							</td>
							<td colspan='1' align="center">
									<form:select path="referenceParamSelected_2"
						 	 	 				onchange="$('.eventId').val(48);this.form.submit();"
						 	 	 				selected="true"
						 	 	 				cssStyle = "cursor: pointer"
						 	 	 				items="${reference.referenceParamList_2}"
									/>
							</td>
			        		<td colspan='1'>
									<form:select path="referenceParamSelected_3"
						 	 	 				onchange="$('.eventId').val(48);this.form.submit();"
						 	 	 				selected="true"
						 	 	 				cssStyle = "cursor: pointer"
						 	 	 				items="${reference.referenceParamList_3}"
									/>
							</td>

			</tr>
			
 			<tr>
 			
 						<td align="center">
							<form:input path="referenceColorPopupPaletteSelected_2" 
										type="color"
										value="${reference.referenceColorPopupPaletteSelected_2}"
						                onchange="$('.eventId').val(44);this.form.submit();"
							/>
						</td>

 						<td colspan='1'>
								   <form:select path="referenceParamSelected_4"
						 	 	 				onchange="$('.eventId').val(48);this.form.submit();"
						 	 	 				selected="true"
						 	 	 				cssStyle = "cursor: pointer"
						 	 	 				items="${reference.referenceParamList_4}"
									/>
						</td>
 			
			        	<td colspan='1' align="center">
									<form:select path="referenceParamSelected_5"
						 	 	 				onchange="$('.eventId').val(48);this.form.submit();"
						 	 	 				selected="true"
						 	 	 				cssStyle = "cursor: pointer"
						 	 	 				items="${reference.referenceParamList_5}"
									/>
						</td>
						<td colspan='1'>
									<form:select path="referenceParamSelected_6"
						 	 	 				onchange="$('.eventId').val(48);this.form.submit();"
						 	 	 				selected="true"
						 	 	 				cssStyle = "cursor: pointer"
						 	 	 				items="${reference.referenceParamList_6}"
									/>
						</td>
			</tr>	

		</table>

	</form:form>

		<script type="text/javascript" charset="utf-8">
			$("#bibleTabId").click(function() {
			$("#eventId").val(1);
			$("#referenceForm").submit();
			});

			$("#churchTabId").click(function() {
			$("#eventId").val(2);
			$("#referenceForm").submit();
			});

			$("#screensaverTabId").click(function() {
			$("#eventId").val(4);
			$("#referenceForm").submit();
			});

			$("#codeTabId").click(function() {
			$("#eventId").val(5);
			$("#referenceForm").submit();
			});

			$("#voiceTabId").click(function() {
			$("#eventId").val(3);
			$("#referenceForm").submit();
			});

			$("#BibleSettingsId").click(function() {
			$("#eventId").val(7);
			$("#referenceForm").submit();
			});

			$("#popupSettingsId").click(function() {
			$("#eventId").val(8);
			$("#referenceForm").submit();
			});

			$("#feedbackTabId").click(function() {
			$("#eventId").val(9);
			$("#referenceForm").submit();
			});

			$("#thankYouTabId").click(function() {
			$("#eventId").val(10);
			$("#referenceForm").submit();
			});
		</script>
</body>
</html>