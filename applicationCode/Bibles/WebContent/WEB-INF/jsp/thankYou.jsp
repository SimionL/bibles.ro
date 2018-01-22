<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" buffer="256kb" %>
<html>
<head>
<title>${thankYou.thankYou}</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<link   rel="stylesheet"       href="<c:url value="/resources/css/style.css" />"        />

<script type="text/javascript" charset="utf-8">

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
<body>

<div class="tab">
  <button id="bibleTabId">${thankYou.bible}</button>
  <button id="churchTabId">${thankYou.church}</button>
  <button id="settingsTabId">${thankYou.settings}</button>
  <button id="screensaverTabId">${thankYou.screensaver}</button>
  <button id="codeTabId">${thankYou.code}</button>
  <button id="voiceTabId">${thankYou.voice}</button>
  <button id="feedbackTabId">${thankYou.feedback}</button>
  <button class="active">${thankYou.thankYou}</button>
</div>

	<div align="center">
		<c:if test="${not empty thankYou.error}">
			<b><font color="red">${thankYou.error}</font></b>
			<br><br>
		</c:if>
	</div>

	<div class="position">
    	<c:if test="${not empty thankYou.thankYouMessage}">
			<b><font size='6'>${thankYou.thankYouMessage}</font></b>
			<br><br>
		</c:if>
	</div>

	<form:form action="thankYou" 
			   modelAttribute="thankYou"
			   enctype="multipart/form-data" 
			   id="thankYouForm"
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

	</form:form>

		<script type="text/javascript" charset="utf-8">
			$("#bibleTabId").click(function() {
			$("#eventId").val(1);
			$("#thankYouForm").submit();
			});
		
			$("#churchTabId").click(function() {
			$("#eventId").val(2);
			$("#thankYouForm").submit();
			});
  		
			$("#settingsTabId").click(function() {
			$("#eventId").val(3);
			$("#thankYouForm").submit();
			});
		
			$("#screensaverTabId").click(function() {
			$("#eventId").val(4);
			$("#thankYouForm").submit();
			});
		
			$("#codeTabId").click(function() {
			$("#eventId").val(5);
			$("#thankYouForm").submit();
			});

			$("#voiceTabId").click(function() {
			$("#eventId").val(6);
			$("#thankYouForm").submit();
			});

			$("#feedbackTabId").click(function() {
			$("#eventId").val(7);
			$("#thankYouForm").submit();
			});
		</script>
</body>
</html>