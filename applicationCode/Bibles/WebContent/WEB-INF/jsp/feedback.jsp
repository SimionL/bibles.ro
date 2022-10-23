<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" buffer="256kb" %>
<html>
<head>
<title>${feedback.feedback}</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<link   rel="stylesheet"       href="<c:url value="/resources/css/style.css" />"        />

<script type="text/javascript" charset="utf-8">

function validateMessage() {

	var elems = [ document.getElementById('myNameId'),
				  document.getElementById('passwordId'),
			      document.getElementById('messageValueId')
                ];

	return arrayValidation(elems);
}

function arrayValidation(elems) {
	var isValid = true;
	if (elems != 'undefined' && elems.length > 0) {
		for (var i = 0; i < elems.length; i++) {

			if (elems[i] == 'undefined' ||
				elems[i] == null        ||
				elems[i] == 'null'      ||
				elems[i].value == null  ||
				elems[i].value == 'null'||
				elems[i].value == '0'   ||
				elems[i].value == ''    ||
				elems[i].value == "") {
				elems[i].style.borderColor = "red";

				isValid = false;
			} else {
				elems[i].style.borderColor = "";
			}
		}
	}
	return isValid;
}

</script>

<style>

.feedbackClass{
    	background: transparent;
    	font-size: 20px;
		width: 100%;
		max-width: 100%;
		height: 300px;
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
<body>

<div class="tab">
  <button id="bibleTabId">${feedback.bible}</button>
  <button id="churchTabId">${feedback.church}</button>
  <button id="settingsTabId">${feedback.settings}</button>
  <button id="screensaverTabId">${feedback.screensaver}</button>
  <button id="codeTabId">${feedback.code}</button>
  <button id="voiceTabId">${feedback.voice}</button>
  <button class="active">${feedback.feedback}</button>
  <button id="thankYouTabId">${feedback.thankYou}</button>
</div>
    <br>
	<div align="center">
		<c:if test="${not empty feedback.error}">
			<b><font color="red">${feedback.error}</font></b>
			<br><br>
		</c:if>
	</div>

	<form:form action="feedback" 
			   modelAttribute="feedback"
			   enctype="multipart/form-data" 
			   id="feedbackForm"
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

		<div class="position">
    		
    		<table class="tableClass" style="width: 100%;" cellspacing="12">

 					<tr>
						<td style="width: 1%;">
							<c:out value="${feedback.messageTypeLabel}" />
						</td>
						
						<td>
						
							<form:radiobuttons path="messageTypeValue"
									   	   	   cssStyle = "cursor: pointer" 
									   	   	   items="${feedback.typeRadioMap}"
									   	   	   delimiter = "<br>"
									   	   	   id="messageTypeId"
							/>
						</td>
 					</tr>
 					<tr>
						<td>
							<c:out value="${feedback.myNameLabel}" />
						</td>
						
						<td>
							
							<form:input path="myNameValue"
						        		type="text"
										cssStyle = "cursor: pointer"
										size="45%"
										id="myNameId"
							/>
						&nbsp;&nbsp;&nbsp;&nbsp;
				 			<img
								src="<c:url 
  								value="/resources/images/add.jpg"/>"
								id="addMessageId"
								style = "cursor: pointer"
							/>
						</td>
 					</tr>
 					<tr>
 						<td>
							<c:out value="${feedback.userPasswordLabel}" />
						</td>
						<td>
							<form:password path="userPasswordValue"
								 		   cssStyle = "cursor: pointer"
								 		   id="passwordId"
								 		   size="58%"
							/>
						</td>
 					</tr>
 					<tr>
						<td>
							<c:out value="${feedback.messageLabel}" />
						</td>
						
						<td>
							
							<form:textarea path="messageValue" 
								    	   type="text"
								    	   class="feedbackClass"
								    	   cssStyle = "cursor: pointer"
								    	   id="messageValueId"
					 		/>

						</td>
 					</tr>
    		</table>
	
		</div>
	</form:form>

			<c:if test="${not empty feedback.messageBugMap}">

				<br><center><b><c:out value="${feedback.bugsLabel}" /></b></center><br>

           		<c:forEach items="${feedback.messageBugMap}" var="message">

					<c:if test="${not empty message}">

						<br> <span style="word-wrap: break-word;"> ${message.value.userName}&nbsp;&nbsp;${message.value.messageStringDate} <br> ${message.value.messageValue} </span> <br><br>
		
					</c:if>

				</c:forEach>

			</c:if>
			<br>
			<c:if test="${not empty feedback.messageQuestionMap}">

				<br><center><b><c:out value="${feedback.questionsLabel}" /></b></center><br>

           		<c:forEach items="${feedback.messageQuestionMap}" var="message">
				
					<c:if test="${not empty message}">

						<br> <span style="word-wrap: break-word;"> ${message.value.userName}&nbsp;&nbsp;${message.value.messageStringDate} <br> ${message.value.messageValue} </span> <br><br>
						
					</c:if>

				</c:forEach>

			</c:if>
			<br>
			<c:if test="${not empty feedback.messageSuggestionMap}">

				<br><center><b><c:out value="${feedback.suggestionsLabel}" /></b></center><br>

           		<c:forEach items="${feedback.messageSuggestionMap}" var="message">
				
					<c:if test="${not empty message}">

						<br> <span style="word-wrap: break-word;"> ${message.value.userName}&nbsp;&nbsp;${message.value.messageStringDate} <br> ${message.value.messageValue} </span> <br><br>

					</c:if>

				</c:forEach>

			</c:if>
			<br>
			<c:if test="${not empty feedback.messageIWantToJoinMap}">

				<br><center><b><c:out value="${feedback.iWantToJoinLabel}" /></b></center><br>

           		<c:forEach items="${feedback.messageIWantToJoinMap}" var="message">
				
					<c:if test="${not empty message}">

						<c:out value="${message.value.userName}" />&nbsp;&nbsp;<c:out value="${message.value.messageStringDate}" /><br>
						
						<br> <span style="word-wrap: break-word;"> ${message.value.userName}&nbsp;&nbsp;${message.value.messageStringDate} <br> ${message.value.messageValue} </span> <br><br>

					</c:if>

				</c:forEach>

			</c:if>
			<br>
			<c:if test="${not empty feedback.messageOthersMap}">

				<br><center><b><c:out value="${feedback.othersLabel}" /></b></center><br>

           		<c:forEach items="${feedback.messageOthersMap}" var="message">
				
					<c:if test="${not empty message}">

						<br> <span style="word-wrap: break-word;"> ${message.value.userName}&nbsp;&nbsp;${message.value.messageStringDate} <br> ${message.value.messageValue} </span> <br><br>

					</c:if>

				</c:forEach>

			</c:if>
			<br>

		<script type="text/javascript" charset="utf-8">
			$("#bibleTabId").click(function() {
			$("#eventId").val(1);
			$("#feedbackForm").submit();
			});
		
			$("#churchTabId").click(function() {
			$("#eventId").val(2);
			$("#feedbackForm").submit();
			});
  		
			$("#settingsTabId").click(function() {
			$("#eventId").val(3);
			$("#feedbackForm").submit();
			});
		
			$("#screensaverTabId").click(function() {
			$("#eventId").val(4);
			$("#feedbackForm").submit();
			});
		
			$("#codeTabId").click(function() {
			$("#eventId").val(5);
			$("#feedbackForm").submit();
			});
		
			$("#voiceTabId").click(function() {
			$("#eventId").val(6);
			$("#feedbackForm").submit();
			});
			
			$("#thankYouTabId").click(function() {
			$("#eventId").val(7);
			$("#feedbackForm").submit();
			});
			
			$("#addMessageId").click(function() {
				if(validateMessage()){
					$("#eventId").val(8);
					$("#feedbackForm").submit();
				}
			});
		</script>
</body>
</html>