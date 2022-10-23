<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" buffer="256kb" %>
<html>
<head>
<title>${screensaver.screensaver}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

	<link   rel="stylesheet"       href="<c:url value="/resources/css/style.css" />"        />

<script type="text/javascript" charset="utf-8">

var audioSong;
var recognizer = null;

function playAudio(playSong) {

	try{

		var melodie = '${screensaver.melodieSelected}';
	
		if(playSong == 'true' && melodie != null && melodie !== null && melodie != 'null' && melodie != 'undefined' && melodie != '' && !(melodie.trim() === "") && melodie.trim().length > 0){

		 	if(recognizer != null && recognizer !== null && recognizer != 'null' && recognizer != 'undefined'){
			 	recognizer.abort();
		  	 	recognizer.stop();
		  	 	recognizer = null;
	   		}

 			document.getElementById('pauseSongId').setAttribute("hidden", true);
 			document.getElementById('playSongId').removeAttribute("hidden", true);

 			$('#usingVoiceId').val(false);
 			$('#playId').val(true);

 			if(audioSong != null && audioSong !== null && audioSong != 'null' && audioSong != 'undefined'){
 				audioSong.pause();	
 			}
 			else{
 				audioSong = document.createElement('audio');	
 			}
 
 			if(audioSong != null && audioSong !== null && audioSong != 'null' && audioSong != 'undefined'){

 				audioSong.volume = 1.0;
 	 			//audioSong.type = ' audio/mp3; codecs="theora, vorbis" ';
 	 			audioSong.src = "https://bibles.ro/songs/" + melodie + ".mp3";
 	 			
 	 			audioSong.addEventListener("ended", function() {
 	 				$("#eventId").val(9);
		       		$("#screensaverForm").submit();
 	 			}, true);
 	 			
 	 			audioSong.play(); 
  			}
		}
		else{
			pauseAudio();
		}
	}
	catch(ex){
		pauseAudio();
	}
}

function pauseAudio() {

	try{
	
		document.getElementById('playSongId').setAttribute("hidden", true);
		document.getElementById('pauseSongId').removeAttribute("hidden", true);
		$('#playId').val(false);

		if(audioSong != null && audioSong !== null && audioSong != 'null' && audioSong != 'undefined'){
			audioSong.pause();
			audioSong.close();
		}
	}
	catch(ex){
		
	}
}

function startListening() {

	try {
		
		if('${screensaver.usingVoice}' == 'true'){

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

	            recognizer.lang = '${screensaver.languageCode}';
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
			       		$("#screensaverForm").submit();
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

function calculateTimeLeft(remain) {
	
	if (remain > 0) {
	
    var end = new Date(Date.now() + (remain * 1000));
    var timer;

    function showRemaining(){
        var now = new Date();
        var distance = end.getTime()-now.getTime();
        if (distance < 0 ) {
           clearInterval(timer);
           ajaxRequest('1');	
        }
    }

    timer = setInterval(showRemaining, 1);
  }
}

function ajaxRequest(eventId) {
	
	try{
		
		$.ajax({
   	     type: "POST",
   	     url: "ajaxCall.htm",
   	     data: { param: eventId},
   	     scriptCharset: "UTF-8",
   	     encoding:	'UTF-8',
   	     beforeSend : function(xhr) {
   	    	    xhr.overrideMimeType("text/html; charset=UTF-8");
   	            xhr.setRequestHeader('Accept', "text/html; charset=UTF-8");
   	     },
   	     success : function(data) {
   	    	 	   setPopup(data, 'true');
         }
   	})
		
	}
	catch(ex){
		
	}
}

	function setPopup(pictureText, openPopup) {

		try{
		  	 if(openPopup == 'true'){
		  		 
		  		var popupTitle = '${screensaver.bible}';
				var remain = '${screensaver.screensaverTimeSelected}';
			    var screensaverType = '${screensaver.screensaverTypeSelected}';
			 
			 	if(screensaverType === "1"){
				 	calculateTimeLeft(remain);
			 	}

			 	var myWindow = window.open("", '${screensaver.popupId}', "width=800,height=400,menubar=no,titlebar=no,toolbar=no,location=no,status=no,fullscreen=1,scrollbars=0,resizable=yes");

			 	var doc = myWindow.document;
				 	doc.open();
				 	doc.write(pictureText);
				 	doc.close();
				 	doc.title = popupTitle;
				
				 	myWindow.onbeforeunload = function () {
				 		$("#eventId").val(10);
						$('#openPopupId').val(false);
						$("#screensaverForm").submit();
				 	};
				 	myWindow.webkitEnterFullscreen();
			 }
		 }
		 catch(ex){
			
		 }
	}

	function preparePage(){
		
		setPopup('${screensaver.popupInformation}', '${screensaver.openPopup}');
		
		var playSong = '${screensaver.playSong}';
		
		if(playSong == 'true'){
			playAudio(playSong);
		}
		else{
			pauseAudio();
			startListening();
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
<body  onload="preparePage();">

<div class="tab">
  <button id="bibleTabId">${screensaver.bible}</button>
  <button id="churchTabId">${screensaver.church}</button>
  <button id="settingsTabId">${screensaver.settings}</button>
  <button class="active">${screensaver.screensaver}</button>
  <button id="codeTabId">${screensaver.code}</button>
  <button id="voiceTabId">${screensaver.voice}</button>
  <button id="feedbackTabId">${screensaver.feedback}</button>
  <button id="thankYouTabId">${screensaver.thankYou}</button>
</div>

	<div align="center">
		<c:if test="${not empty screensaver.error}">
			<font color="red">${screensaver.error}</font>
			<br><br>
		</c:if>
	</div>

	<form:form action="screensaver" 
			   modelAttribute="screensaver"
			   enctype="multipart/form-data" 
			   id="screensaverForm"
			   name="screensaverFormName"
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

		<form:input path="eventId"    type="text" class="eventId" hidden="true" />
		<form:input path="playSong"   type="text" id="playId" hidden="true" />
		<form:input path="usingVoice" type="text" id="usingVoiceId" hidden="true" />

		<table style="width: 35%" class="tableClass" >

			<tr>
				
			<td align="left" colspan='1'>

               <br><br>

               	<table>
					<tr>
						<td align="left">

							<img
								src="<c:url 
  								value="/resources/images/start.jpg"/>"
								id="startScreensaverId"
								style = "cursor: pointer"
							/>

				 			<img
								src="<c:url 
  								value="/resources/images/stop.png"/>"
								id="stopScreensaverId"
								style = "cursor: pointer"
							/>

						</td>

						<td align="left">

							<img
								src="<c:url 
  								value="/resources/images/speak.png"/>"
								onclick="pauseAudio();"
  					        	onMouseOver="this.style.cursor='pointer'"
  					        	id="playSongId"
							/>

							<img
								src="<c:url 
  					        	value="/resources/images/silence.png"/>"
  					        	onclick="playAudio('true');"
  					        	onMouseOver="this.style.cursor='pointer'"
  					        	id="pauseSongId"
  					        	hidden="true"
							/>

						</td>
					</tr>
				</table>

				<table class="tableClass">

                     <tr>
						<td align="left" colspan='2' style="width: 100%;">
                    		 <form:select path="melodieSelected" 
								           items='${screensaver.melodiesList}'
								           cssStyle = "cursor: pointer"
								           onchange = "$('.eventId').val(7);this.form.submit();"
							  />
                     </tr>

					<tr>
					  <td align="left" colspan='1'>
                        <form:select path="screensaverParamSelected_1"
						 	 	 	 		 onchange="$('.eventId').val(62);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${screensaver.screensaverParamList_1}"
						/>
						<form:select path="screensaverParamSelected_2"
						 	 	 	 		 onchange="$('.eventId').val(63);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${screensaver.screensaverParamList_2}"
						/>
						<form:select path="screensaverParamSelected_3"
						 	 	 	 		 onchange="$('.eventId').val(64);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${screensaver.screensaverParamList_3}"
						/>
					  </td>
					  <td align="left" colspan='1'>
                        <form:select path="screensaverParamSelected_4"
						 	 	 	 		 onchange="$('.eventId').val(65);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${screensaver.screensaverParamList_4}"
						/>
						<form:select path="screensaverParamSelected_5"
						 	 	 	 		 onchange="$('.eventId').val(66);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${screensaver.screensaverParamList_5}"
						/>
						<form:select path="screensaverParamSelected_6"
						 	 	 	 		 onchange="$('.eventId').val(67);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${screensaver.screensaverParamList_6}"
						/>
					  </td>
                    </tr>
					<tr>

							<td align="center" colspan='1'>
						  		<form:input path="screensaverColorPaletteSelected_1" 
										type="color"
										cssStyle="width:100%"
										value="${screensaver.screensaverColorPaletteSelected_1}"
						                onchange="$('.eventId').val(68);this.form.submit();"
								/>
							</td>
							<td align="center" colspan='1'>
						  		<form:input path="screensaverColorPaletteSelected_2" 
										type="color"
										cssStyle="width:100%"
										value="${screensaver.screensaverColorPaletteSelected_2}"
						                onchange="$('.eventId').val(69);this.form.submit();"
								/>
							</td>

                    </tr>

                    <tr>
					  <td align="left" colspan='1'>
                        <form:select path="screensaverParamSelected_7"
						 	 	 	 		 onchange="$('.eventId').val(72);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${screensaver.screensaverParamList_7}"
						/>
						<form:select path="screensaverParamSelected_8"
						 	 	 	 		 onchange="$('.eventId').val(73);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${screensaver.screensaverParamList_8}"
						/>
						<form:select path="screensaverParamSelected_9"
						 	 	 	 		 onchange="$('.eventId').val(74);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${screensaver.screensaverParamList_9}"
						/>
					  </td>
					  <td align="left" colspan='1'>
                        <form:select path="screensaverParamSelected_10"
						 	 	 	 		 onchange="$('.eventId').val(75);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${screensaver.screensaverParamList_10}"
						/>
						<form:select path="screensaverParamSelected_11"
						 	 	 	 		 onchange="$('.eventId').val(76);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${screensaver.screensaverParamList_11}"
						/>
						<form:select path="screensaverParamSelected_12"
						 	 	 	 		 onchange="$('.eventId').val(77);this.form.submit();"
						 	 	 	 		 selected="true"
						 	 	 	 		 cssStyle = "cursor: pointer"
						 	 	 	 		 items="${screensaver.screensaverParamList_12}"
						/>
					  </td>
                    </tr>

					<tr>
					      
							<td align="center" colspan='1'>
						  		<form:input path="screensaverColorPaletteSelected_3" 
										type="color"
										cssStyle="width:100%"
										value="${screensaver.screensaverColorPaletteSelected_3}"
						                onchange="$('.eventId').val(78);this.form.submit();"
								/>
							</td>
							<td align="center" colspan='1'>
						  		<form:input path="screensaverColorPaletteSelected_4" 
										type="color"
										cssStyle="width:100%"
										value="${screensaver.screensaverColorPaletteSelected_4}"
						                onchange="$('.eventId').val(79);this.form.submit();"
								/>
							</td>
                    </tr>
 
                   <tr>
							<td align="center" colspan='2'>
						  		<form:input path="screensaverColorPaletteSelected_5" 
										type="color"
										cssStyle="width:100%"
										value="${screensaver.screensaverColorPaletteSelected_5}"
						                onchange="$('.eventId').val(82);this.form.submit();"
								/>
							</td>

                    </tr>

					</table>

            		<form:radiobuttons path="screensaverRadioSelected"
									   cssStyle = "cursor: pointer" 
									   items="${screensaver.screensaverRadioMap}"
									   delimiter = "<br>"
									   onchange = "$('.eventId').val(36);this.form.submit();"
					/>
					<br>
					<br>
					<form:select path="screensaverCategoriesSelected"
						 	 	 onchange="$('.eventId').val(32);this.form.submit();"
						 	 	 selected="true"
						 	 	 cssStyle = "cursor: pointer"
						 	 	 items="${screensaver.screensaverCategoriesMap}"
					/>

					<br>
					<form:radiobuttons path="screensaverTypeSelected"
									   cssStyle = "cursor: pointer"
									   items="${screensaver.screensaverTypeMap}"
									   delimiter = "<br>"
									   onchange = "$('.eventId').val(37);this.form.submit();"
					/>
					<br>
					<c:choose>
						<c:when test="${screensaver.screensaverTypeSelected eq 1}">
							<form:select path="screensaverTimeSelected"
									 	 onchange="$('.eventId').val(31);this.form.submit();"
									 	 selected="true"
									 	 cssStyle = "cursor: pointer"
									 	 items="${screensaver.screensaverTimeList}"
							/>
						</c:when>
						<c:otherwise>
							<img
								src="<c:url 
  					         	value="/resources/images/back.jpg"/>"
							 	onMouseOver="this.style.cursor='pointer'"
						     	id="backButton"
							/>
							&nbsp;
							<img
								src="<c:url 
  					         	value="/resources/images/next.jpg"/>"
  					         	onMouseOver="this.style.cursor='pointer'"
  					         	id="nextButton"
							/>
						</c:otherwise>
					</c:choose>
            
            </td>
			</tr>
		</table>

	</form:form>

		<script type="text/javascript" charset="utf-8">
			$("#bibleTabId").click(function() {
			$("#eventId").val(1);
			$("#screensaverForm").submit();
			});

			$("#churchTabId").click(function() {
			$("#eventId").val(2);
			$("#screensaverForm").submit();
			});

			$("#settingsTabId").click(function() {
			$("#eventId").val(3);
			$("#screensaverForm").submit();
			});

			$("#codeTabId").click(function() {
			$("#eventId").val(4);
			$("#screensaverForm").submit();
			});

			$("#voiceTabId").click(function() {
			$("#eventId").val(5);
			$("#screensaverForm").submit();
			});

			$("#startScreensaverId").click(function() {
				$("#eventId").val(8);
				$("#screensaverForm").submit();
			});

			$("#stopScreensaverId").click(function() {
					$("#eventId").val(34);
					$("#screensaverForm").submit();
			});

			$("#nextButton").click(function() {
				ajaxRequest('2');
			});

			$("#backButton").click(function() {
				ajaxRequest('3');
			});

			$("#feedbackTabId").click(function() {
			$("#eventId").val(11);
			$("#screensaverForm").submit();
			});
			
			$("#thankYouTabId").click(function() {
			$("#eventId").val(12);
			$("#screensaverForm").submit();
			});
		</script>
</body>
</html>