<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" buffer="256kb" %>

<html>
<head>
<title>${church.church}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

	<link   rel="stylesheet"       href="<c:url value="/resources/css/style.css" />"        />

<script type="text/javascript" src="<c:url value="/resources/clock/jquery.ptTimeSelect.js" />"></script>
<link   rel="stylesheet" type="text/css"      href="<c:url value="/resources/clock/jquery.ptTimeSelect.css" />" />

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
.deleteButtonClass {
    background-color: #ffffff;
    border: none;
    color: transparent;
    text-decoration: none;
    cursor: pointer;
}

.descriptionClass{
    	background: transparent;
    	font-size: 25px;
		border: 0 none white;
		width: 100%;
		max-width: 100%;
		height: 100%;
		max-height: 100%;
		word-wrap: break-word;
  		text-align: center;
      }

 .participantClass{
    	background: transparent;
    	font-size: 16px;
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

.participantResourcesClass{
    	background: transparent;
    	font-size: 16px;
		border: 0 none white;
		width: 100%;
		max-width: 100%;
		height: 100%;
		max-height: 100%;
  		text-align: left;
      }

table, th, td {
    border: 12px solid white;
}
</style>

<script type="text/javascript" charset="utf-8">

function startListening() {

	try {
		
		if('${church.usingVoice}' == 'true'){

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

	            recognizer.lang = '${church.languageCode}';
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
			   		$("#eventId").val(21);
			   		$("#churchForm").submit();
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

jQuery(function($){
	  $('#newClock').ptTimeSelect();
	  var datefield = document.getElementById('newDate');
	  if (datefield != 'undefined' && datefield != null) {
	   	datefield.setAttribute("type", "date");
	   	if (datefield.type!="date"){
		   	$('#newDate').datepicker();
	   	}
	}
});

	function sizeValidation(){

		var file = document.querySelector("#selectResourcesId");

		if (file != 'undefined') {

			var inputFile = file.files[0];

			if (inputFile != 'undefined' && inputFile.size > 409715200) {
				$('#selectResourcesId').val('');
				document.getElementById('selectResourcesId').value = null;
				alert(document.getElementById('wrongFileSizeId').value);
			}
		}
	}

	function enableEvent() {
		var elems = document.getElementsByClassName('eventIdClass');
		if (elems != 'undefined') {
			for (var i = 0; i != elems.length; ++i) {
				elems[i].removeAttribute("hidden", true);
			}
		}
	}
	function enableLogin() {
		var elems = document.getElementsByClassName('loginIdClass');
		if (elems != 'undefined') {
			for (var i = 0; i != elems.length; ++i) {
				elems[i].removeAttribute("hidden", true);
			}
		}
	}
	function disableEvent() {
		var elems = document.getElementsByClassName('eventIdClass');
		if (elems != 'undefined') {
			for (var i = 0; i != elems.length; ++i) {
				elems[i].setAttribute("hidden", true);
			}
		}
	}
	function disableLogin() {
		var elems = document.getElementsByClassName('loginIdClass');
		if (elems != 'undefined') {
			for (var i = 0; i != elems.length; ++i) {
				elems[i].setAttribute("hidden", true);
			}
		}
	}

	function handleFields() {
		
		var displayChurch = '${church.displayChurch}';
		
		if (displayChurch != 'undefined') {
			if (displayChurch == "event") {
				disableLogin();
				enableEvent();
			} else if (displayChurch == "account") {
				disableEvent();
				enableLogin();
			}
		}
	}

	function arrayValidation(elems) {
		var isValid = true;
		if (elems != 'undefined' && elems.length > 0) {
			for (var i = 0; i < elems.length; i++) {

				if (elems[i] != 'undefined' && elems[i].value == "") {
					elems[i].style.borderColor = "red";

					isValid = false;
				} else {
					elems[i].style.borderColor = "";
				}
			}
		}
		return isValid;
	}

	function validateLogin() {

		var elems = [ document.getElementById('usernameId'),
				document.getElementById('passwordId'), ];

		return arrayValidation(elems);
	}

	function validateCreateAccount() {

		var elems = [ document.getElementById('usernameId'),
				document.getElementById('passwordId'),
				document.getElementById('churchEmailId'),
				document.getElementById('churchEmailPasswordId') ];

		return arrayValidation(elems);
	}

	function validateCreateEvent() {

		var elems = [ document.getElementById('eventName'),
				document.getElementById('newDate'),
				document.getElementById('eventPasswordId'),
				document.getElementById('newClock'), ];

		var selectedTime;

		var pattern = /^\d+$/;
		var selectedDuration = document.getElementById('eventDurationId').value;

		if (selectedDuration != 'undefined' && selectedDuration != ''
				&& selectedDuration != "" && pattern.test(selectedDuration)) {
			document.getElementById('eventDurationId').style.borderColor = "";
			selectedTime = true;
		} else {
			document.getElementById('eventDurationId').style.borderColor = "red";
			selectedTime = false;
		}

		return arrayValidation(elems) && selectedTime;
	}

	function validateAddParticipant() {

		var elems = [ document.getElementById('participantNameId'),
				document.getElementById('participantForenameId'),
				document.getElementById('participantPhoneId') ];

		return arrayValidation(elems);
	}
</script>

</head>

<body onload="startListening();handleFields();">

<div class="tab">
  <button id="bibleTabId">${church.bible}</button>
  <button class="active">${church.church}</button>
  <button id="settingsTabId">${church.settings}</button>
  <button id="screensaverTabId">${church.screensaver}</button>
  <button id="codeTabId">${church.code}</button>
  <button id="voiceTabId">${church.voice}</button>
  <button id="feedbackTabId">${church.feedback}</button>
  <button id="thankYouTabId">${church.thankYou}</button>
</div>

<br><br>
<div class="tab">
  <button <c:if test="${church.displayChurch == 'event'}">  class="active"</c:if> id="churchEventId"   onclick="openEvent(event)">${church.event}</button>
  <button <c:if test="${church.displayChurch == 'account'}">class="active"</c:if> id="churchAccountId" onclick="openLogin(event)">${church.account}</button>
</div>

<script>
function openEvent(evt) {

    var currentTab = document.getElementById("churchAccountId");
    currentTab.className = currentTab.className.replace("active", "");
    
	$('.displayChurchClass').val('event');
    enableEvent();
	disableLogin();
	
	evt.currentTarget.className += "active";
}

function openLogin(evt) {

    var currentTab = document.getElementById("churchEventId");
    currentTab.className = currentTab.className.replace("active", "");
	
	$('.displayChurchClass').val('account');
	disableEvent();
	enableLogin();
	
	evt.currentTarget.className += "active";
}

</script>

		 <div align="center">
			<c:if test="${not empty church.error}">
				<font color="red">${church.error}</font>
				<br><br>
			</c:if>
			
			<c:if test="${not empty church.successful}">
				<font color="green">${church.successful}</font>
				<br><br>
			</c:if>
	     </div>
		<form:form action="church"
			   modelAttribute="church" 
			   enctype="multipart/form-data"
			   id="churchForm"
			   accept-charset="UTF-8"
	    >
		<form:input path="openPopup" 
					type="text" 
					id="openPopupId" 
					hidden="true" 
		/>
	    <font size="${church.formFontSelected}">
    	<form:input path="eventId" 
		    		type="text"
					class="eventId" 
				    hidden="true"
		/>
		<form:input path="displayChurch"
		    		type="text"
					class="displayChurchClass" 
				    hidden="true"
		/>
		<form:input path="wrongFileSize"
		    		type="text" 
					id="wrongFileSizeId" 
				    hidden="true"
		/>
    	<form:input path="participantId" 
		    		type="text"
					class="participantId" 
				    hidden="true"
		/>
		<form:input path="identifiedWords"
					id="identifiedWordsId" 
				    hidden="true"
		/>
		<div class ="eventIdClass">
		<br><br>
			&nbsp;&nbsp;
			
			<c:choose>

				<c:when test="${not empty church.user}">
		  			&nbsp;&nbsp;
		  			<c:if test="${not empty church.events}">		
						<form:select path="selectedEvent" 
						 		 	 items="${church.events}"
						 		 	 value="${church.selectedEvent}" 
						 		 	 selected="true"
						 		 	 onchange="$('.eventId').val(4);this.form.submit();" 
						 		 	 cssStyle = "cursor: pointer"
						 		 	 id="selectedAdminEvent"
						/>
					</c:if>
		  		    &nbsp;&nbsp;

		  			<button type="button" 
							style = "cursor: pointer"
							onclick="if(validateCreateEvent()){$('.eventId').val(10);this.form.submit();}">${church.createEvent}
					</button>
					
					 &nbsp;&nbsp;
					 
					<button type="button" 
							style = "cursor: pointer"
							onclick="$('.eventId').val(11);this.form.submit();">${church.updateEvent}
					</button>
					
					 &nbsp;&nbsp;
					 
					<button type="button" 
							style = "cursor: pointer"
							onclick="$('.eventId').val(12);this.form.submit();">${church.deleteEvent}
					</button>
					
					&nbsp;&nbsp;

					<button type="button" 
							style = "cursor: pointer"
							onclick="$('.eventId').val(18);this.form.submit();">${church.downloadAttachments}
					</button>
					
					&nbsp;&nbsp;

					<button type="button" 
							style = "cursor: pointer"
							onclick="$('.eventId').val(19);this.form.submit();">${church.sendInvitation}
					</button>

					<br><br>

		  		&nbsp;&nbsp; &nbsp;
	
         		<table class="tableClass" style="width: 20%;" cellspacing="12">

					<tr>
						<td colspan='1'>
							<c:out value="${church.eventNameLabel}" />
						</td>
						
						<td colspan='2'>
							
							<form:input path="eventNameValue"
					            		id="eventName"
						        		type="text"
										cssStyle = "cursor: pointer"
										size="27%"
							/>
						</td>
 					</tr>
 					
					<tr>
						<td colspan='1'>
							<c:out value="${church.eventDateLabel}" />
						</td>
						<td colspan='0'>
							<form:input path="eventDate" 
				            			type="date" 
				                		id="newDate"
				      					size="9" 
				      		
							/>
						</td>
						<td colspan='0'>
							<div  class="ui-widget-content">
                				<form:input path="eventHour"  
                		            		id="newClock"
                		            		size="9"
                				/>
                			</div>
						</td>

					 </tr>
					 <tr>
					 	<td colspan='1'>
							<c:out value="${church.eventDuration}" />
						</td>

					 	<td colspan='2'>
					           <form:select path="selectedDuration" 
								 			items="${church.duration}"
								 			value="${church.selectedDuration}" 
								 			id="eventDurationId"
								 			selected="true"
								 			cssStyle = "cursor: pointer"
								/>
					 	</td>
					 	
					</tr>
					<tr>
					 	<td colspan='1'>
							<c:out value="${church.eventPasswordLabel}" />
						</td>

					 	<td colspan='2'>
					        <form:password path="eventPasswordValue"
								 		   cssStyle = "cursor: pointer"
								 		   id="eventPasswordId"
							/>
					 	</td>

					 </tr>
					 <tr>
					 	<td colspan='1'>
							<c:out value="${church.invitationEmailLabel}" />
						</td>

					 	<td colspan='2'>
					       	<form:input path="invitationEmailValue" 
										type="text"
							/>
					 	</td>

					 </tr>
					 
					 <tr>
					 	<td colspan='1'>
							<c:out value="${church.emailTitleLabel}" />
						</td>

					 	<td colspan='2'>
					       	<form:input path="emailTitleValue" 
										type="text"
							/>
					 	</td>

					 </tr>
					 </table>

					 <center><c:out value="${church.emailContentLabel}" /></center>
					 &nbsp;&nbsp;
		  			 <form:textarea path="emailContentValue" 
								    style="width :97%; height: 22%;"
					 />
                     <br>
					 <center><c:out value="${church.eventDescriptionLabel}" /></center>
					 &nbsp;&nbsp;
		  			 <form:textarea path="eventDescription" 
								    value="${church.eventDescription}"
								    style="width :97%; height: 22%;"
					 />

					 	<br><br>
	<c:if test="${church.participantsList != 'undefined' && not empty church.participantsList}">
        			 <center>
        			 	<c:out value="${church.totalParticipantTimeLabel}" />
        			 	&nbsp;&nbsp;

        				<c:choose>
							<c:when test="${church.totalParticipantTime gt church.selectedDuration}">
								<b><span style='color: red ;'>${church.totalParticipantTime}</span></b>
							</c:when>
							<c:otherwise>
								<span style='color: green ;'>${church.totalParticipantTime}</span>
							</c:otherwise>
						</c:choose>
 
        			 	&nbsp;&nbsp;
		  			 	<c:out value="${church.minutes}" />

					</center>
			<br>
			<table style="width: 190%;" cellspacing="0" style="border-spacing: 0;">
				<tr>
						<th>
								<c:out value="${church.order}" />
						</th>
						<th>
								<c:out value="${church.participantName}" />
						</th>
						<th>
								<c:out value="${church.participantForename}" />
						</th>
						<th>
								<c:out value="${church.participantPhone}" />
						</th>
						<th>
								<c:out value="${church.participantEmail}" />
						</th>
						<th>
								<center><c:out value="${church.durationParticipantLabel}" /></center>
						</th>
						<th>
								<center><c:out value="${church.resources}" /></center>
						</th>
						<th colspan='2'>
								<center><c:out value="${church.participantMessage}" /></center>
						</th>
						<th colspan='1' style="width: 25%;">
						</th>

					</tr>
					<tr><th><br></th>
				</tr>
				<c:forEach items="${church.participantsList}" var="participant" varStatus="participantStatus">
					<tr>
						<td>
									
						<form:select path="participantsList[${participantStatus.index}].participantOrder"
						 		 	 items="${church.participantsOrderList}"
						 		 	 selected="true"
						 		 	 onchange="$('.participantId').val(${participant.participantId});$('.eventId').val(14);this.form.submit();" 
						 		     cssStyle = "cursor: pointer"
					   />
						</td>
						<td>
								<c:out value="${participant.name}" />
						</td>
						<td>
								<c:out value="${participant.forename}" />
						</td>
						<td>
								<c:out value="${participant.phone}" />
						</td>
						<td>
								<c:out value="${participant.email}" />
						</td>
						<td align="center">
								<c:out value="${participant.duration}" />
						</td>
						<td>
								<textarea class="participantResourcesClass" readonly>${participant.resources}</textarea>
						</td>
						<td colspan='2' align="center">
								<textarea class="participantClass" readonly>${participant.description}</textarea>
						</td>
						
						<td colspan='1' style="width: 25%;">
						
						<form:button path="participantId"
						             class="deleteButtonClass"
								     onclick="$('.participantId').val(${participant.participantId});$('.eventId').val(15);"
						>
						 	<img
								src="<c:url 
  					        		value="/resources/images/closeIcon.jpg"
  					        	 />"
								class="closeIconClass" 
								id="closeIconId"
							/> 
						</form:button>

						</td>
					</tr>
				</c:forEach>
			</table>

		</c:if>
	
				</c:when>

				<c:otherwise>
					<form:select path="selectedChurch" 
						 		 items="${church.churches}"
						 		 onchange="$('.eventId').val(3);this.form.submit();" 
						 		 cssStyle = "cursor: pointer"
					/>
					&nbsp;&nbsp;
					<form:select path="selectedEvent" 
						 		 items="${church.events}"
						 		 onchange="$('.eventId').val(4);this.form.submit();" 
						 		 cssStyle = "cursor: pointer"
					 />

                    <br><br>
                    
                    <c:if test="${not empty church.addUserButton}">	
                    
                     <center>
                     
                     	<c:out value="${church.eventNameValue}" />

		  			 	<br><br>
		  			
		  			 	<c:out value="${church.eventDate}" />
		  			
		  			 	&nbsp;&nbsp;
		  			
		  			 	<c:out value="${church.eventHour}" />
	
        			 	<br><br>
        			 	
        			 	<c:out value="${church.selectedDuration}" />
        			 	
        			 	&nbsp;&nbsp;
		  			
		  			 	<c:out value="${church.minutes}" />
	
        			 	<br><br>
        			 	
        			 	<c:out value="${church.totalParticipantTimeLabel}" />
        			 	&nbsp;&nbsp;

        				<c:choose>
							<c:when test="${church.totalParticipantTime gt church.selectedDuration}">
								<span style='color: red ;'>${church.totalParticipantTime}</span>
							</c:when>
							<c:otherwise>
								<span style='color: green ;'>${church.totalParticipantTime}</span>
							</c:otherwise>
						</c:choose>
 
        			 	&nbsp;&nbsp;
		  			 	<c:out value="${church.minutes}" />

					</center>
					
					<textarea class="descriptionClass" readonly>${church.eventDescription}</textarea>

					<br><br>
		<c:if test="${church.participantsList != 'undefined' && not empty church.participantsList}">
			<table style="width: 100%;" cellspacing="0" style="border-spacing: 0;">
				<tr  style="font-weight:bold ; text-align:center">
						<td>
								<c:out value="${church.order}" />
						</td>
						<td>
								<c:out value="${church.participantName}" />
						</td>
						<td>
								<c:out value="${church.participantForename}" />
						</td>
						<td>
								<c:out value="${church.participantPhone}" />
						</td>
						<td>
								<c:out value="${church.participantEmail}" />
						</td>
						<td>
								<c:out value="${church.durationParticipantLabel}" />
						</td>
						<td colspan='3'>
								<c:out value="${church.resources}" />
						</td>
						<td colspan='3'>
								<c:out value="${church.participantMessage}" />
						</td>

					</tr>
					<tr><th><br></th></tr>
				<c:forEach items="${church.participantsList}" var="participant">
					<tr>
					
						<td style="text-align:center">
								<c:out value="${participant.participantOrder}" />
						</td>
						<td>
								<c:out value="${participant.name}" />
						</td>
						<td>
								<c:out value="${participant.forename}" />
						</td>
						<td>
								<c:out value="${participant.phone}" />
						</td>
						<td>
								<c:out value="${participant.email}" />
						</td>
						<td style="text-align:center">
								<c:out value="${participant.duration}" />
						</td>
						<td colspan='3'>
								<textarea class="participantResourcesClass" readonly>${participant.resources}</textarea>
						</td>
						<td colspan='3'>
								<textarea class="participantClass" readonly>${participant.description}</textarea>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		
 					<br><br>
 					
 					
 					
 		<table class="tableClass" cellspacing="0" style="border-spacing: 0;">

			<tr>
				<td colspan='3'><c:out value="${church.participantName}" /></td>
				<td colspan='3'><c:out value="${church.participantForename}" /></td>
				<td colspan='1'><c:out value="${church.durationParticipantLabel}" /></td>
				<td colspan='1'><c:out value="${church.eventPasswordLabel}" /></td>
			</tr>

			<tr>
			
				<td colspan='3'>
					<form:input path="participantNameValue"
						        type="text"
						        value="${church.participantNameValue}"
								cssStyle = "cursor: pointer"
								id="participantNameId"
					/>
				</td>

				<td colspan='3'>
					<form:input path="participantForenameValue"
						        type="text"
						        value="${church.participantForenameValue}"
								cssStyle = "cursor: pointer"
								id="participantForenameId"
					/>
				</td>

				<td colspan='1'>
					<form:select path="participantDurationValue" 
								 items="${church.participantDuration}"
								 value="${church.participantDurationValue}"
								 cssStyle = "cursor: pointer"
					/>
	            </td>

				 	<td colspan='1'>
				        <form:password path="eventPasswordValue"
							 		   cssStyle = "cursor: pointer"
						/>
				 	</td>
			</tr>
			<tr>
    			<td colspan='3'></td>
    			<td colspan='3'></td>
    			<td colspan='3'></td>
    			<td colspan='1'>
				 	<img
						src="<c:url 
  						value="/resources/images/add.jpg"/>"
						align="left"
						width="35px" height="35px"
						id="addUserButton"
						style = "cursor: pointer"
					/>
				</td>
    			
			</tr>
			<tr>
				<td colspan='3'><c:out value="${church.participantPhone}" /></td>
				<td colspan='3'><c:out value="${church.participantEmail}" /></td>
				<td colspan='3'><c:out value="${church.resources}" /></td>
			</tr>

			<tr>

				<td colspan='3'>
					<form:input path="participantPhoneValue"
					            value="${church.participantPhoneValue}"
						        type="text"
								cssStyle = "cursor: pointer"
								id="participantPhoneId"
					/>
				</td>

				<td colspan='3'>
					<form:input path="participantEmailValue"
						        type="text"
								cssStyle = "cursor: pointer"
					/>
				</td>
				
			 	<td colspan='3'>
					<form:input path="participantResources"
						        type= "file"
								cssStyle = "cursor: pointer"
								multiple="multiple"
								id="selectResourcesId"
								onchange="sizeValidation();"
					/>
				</td>

			</tr>

		</table>	
		
		<form:textarea path="participantDescriptionValue"
					   type="text"
					   class="descriptionClass"
					   cssStyle = "cursor: pointer"
		/>

		</c:if> 

				</c:otherwise>

			</c:choose>
	
		</div>

		<div class ="loginIdClass">

		&nbsp;&nbsp;
		<table class="tableClass" style="width: 20%;" cellspacing="12">

			<tr>
				<td colspan='1'>&nbsp;&nbsp;<c:out value="${church.usernameLabel}" /></td>
				<td align="center" colspan='1'>
					<form:input path="username"
					            id="usernameId"
						        type="text" 
								value="${church.username}" 
								cssStyle = "cursor: pointer"
					/>
				</td>
			</tr>
            
			<tr>
				<td colspan='1'>&nbsp;&nbsp;<c:out value="${church.passwordLabel}" /></td>
				<td align="center" colspan='1'>
					<form:password path="password"
					               id="passwordId"
								   cssStyle = "cursor: pointer"
					/>
				</td>
			</tr>
			
			<tr>
				<td colspan='1'>&nbsp;&nbsp;<c:out value="${church.churchEmailLabel}" /></td>
				<td align="center" colspan='1'>
					<form:input path="churchEmail"
					            id="churchEmailId"
						        type="text" 
								value="${church.churchEmail}" 
								cssStyle = "cursor: pointer"
					/>
				</td>
			</tr>

			<tr>
				<td colspan='1'>&nbsp;&nbsp;<c:out value="${church.churchEmailPasswordLabel}" /></td>
				<td align="center" colspan='1'>
					<form:password path="churchEmailPassword"
					               id="churchEmailPasswordId"
								   cssStyle = "cursor: pointer"
					/>
				</td>
			</tr>

		</table>
					<br><br>
			&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" 
							style = "cursor: pointer"
							onclick="if(validateLogin()){$('.eventId').val(5);this.form.submit();}">${church.login}
					</button>
			&nbsp;&nbsp;
					<button type="button" 
							style = "cursor: pointer"
							onclick="$('.eventId').val(6);this.form.submit();">${church.logout}
					</button>
			&nbsp;&nbsp;
					<button type="button" 
							style = "cursor: pointer"
							onclick="if(validateCreateAccount()){$('.eventId').val(7);this.form.submit();}">${church.createAccount}
					</button>
			&nbsp;&nbsp;
					<button type="button" 
							style = "cursor: pointer"
							onclick="$('.eventId').val(8);this.form.submit();">${church.deleteAccount}
					</button>
			&nbsp;&nbsp;
					<form:input path="updateAccount" type="submit"
								value="${church.updateAccount}" class="button"
								onclick="$('.eventId').val(9);this.form.submit();" 
					/>
		</div>
	</font>
</form:form>
		<script type="text/javascript" charset="utf-8">
			$("#addUserButton").click(function() {
				if(validateAddParticipant()){
					$("#eventId").val(13);
					$("#churchForm").submit();
				}
			});

				$('.descriptionClass').load('insert',function(){
				$(this).css('height','auto');
				$(this).height(this.scrollHeight);
				});

				$('.participantClass').load('insert',function(){
				$(this).css('height','auto');
				$(this).height(this.scrollHeight);
				});

				$('.participantResourcesClass').load('insert',function(){
				$(this).css('height','auto');
				$(this).height(this.scrollHeight);
				});

			$("#bibleTabId").click(function() {
			$("#eventId").val(1);
			$("#churchForm").submit();
			});

			$("#settingsTabId").click(function() {
			$("#eventId").val(2);
			$("#churchForm").submit();
			});

			$("#screensaverTabId").click(function() {
			$("#eventId").val(16);
			$("#churchForm").submit();
			});

			$("#codeTabId").click(function() {
			$("#eventId").val(17);
			$("#churchForm").submit();
			});

			$("#voiceTabId").click(function() {
			$("#eventId").val(20);
			$("#churchForm").submit();
			});

			$("#feedbackTabId").click(function() {
			$("#eventId").val(22);
			$("#churchForm").submit();
			});
			
			$("#thankYouTabId").click(function() {
			$("#eventId").val(23);
			$("#churchForm").submit();
			});
		</script>
</body>
</html>