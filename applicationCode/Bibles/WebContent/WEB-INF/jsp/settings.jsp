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

function onLoadSteps() {

	try {
		
		if('${settings.saveMessageSettings}' === 'true'){
			
			localStorage.setItem('messagesEncapsulation', JSON.stringify('${settings.messagesEncapsulation}'));
			localStorage.setItem('userEmail',             JSON.stringify('${settings.userEmail}'));
			localStorage.setItem('emailFrom',             JSON.stringify('${settings.emailFrom}'));
			localStorage.setItem('automatSendMessage',    JSON.stringify('${settings.automatSendMessage}'));
		}
		
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
<body onload="onLoadSteps();">

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

<div style = "position: absolute; left: 50%;">

	<div align="center">
		<c:if test="${not empty settings.error}">
			<font color="red">${settings.error}</font>
			<br><br>
		</c:if>
	</div>
	
	<div align="center">
					<c:if test="${not empty settings.ok}">
						<b><font color="green">${settings.ok}</font></b>
					</c:if>
	</div>
	
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
		
		<form:input path="selectedMessageId" 
		    		type="text"
					class="selectedMessageId" 
				    hidden="true"
		/>

		<form:input path="messagesEncapsulation" 
		    		type="text"
					class="messagesEncapsulation" 
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
				<td colspan='1'><c:out value="${settings.displayReferencesLabel}" /></td>
				<td align="left" colspan='1'>
					<form:checkbox path="displayReference"
									cssStyle = "cursor: pointer"
									onchange="$('.eventId').val(14);this.form.submit();" 
					/>
				</td>

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
				<td colspan='1'><c:out value="${settings.displayEntireChapterLabel}" /></td>
				<td align="left" colspan='1'>
				    <form:checkbox path="displayEntireChapter"
								   cssStyle = "cursor: pointer"
								   onchange="$('.eventId').val(16);this.form.submit();" 
					/>
				</td>
				
				<td align="left" colspan='1'><c:out value="${settings.selectLanguage}" /></td>

				<td align="left" colspan='1'>

						<form:select path="selectedLanguage"
									 items="${settings.languages}"
									 onchange="$('.eventId').val(3);this.form.submit();" 
									 cssStyle = "cursor: pointer"
						/>

				</td>
			</tr>
			
			<tr>
			
				<td colspan='1'><c:out value="${settings.automatMessage}" /></td>
				
				
				<td align="left" colspan='1'>
					 <form:checkbox path="automatSendMessage"
					                class="automatSendMessage"
									cssStyle = "cursor: pointer"
									onchange="$('.eventId').val(23);this.form.submit();" 
					/>
				</td>
			
			</tr>
			
		</table>
		
		<br>
		
		
		<table>
		
		<tr>
		
			<td align="right" colspan='3' >
				<span style="font-weight: bold; "> ${settings.message}: </span>
			</td >
		
		</tr>
		
		<tr><td colspan='3' ><br></td><tr>
		
		<tr>
			
				<td align="left" colspan='3' >
			    <div align="center">
			    	<form:radiobuttons path="emailFrom"
			    	                   class="emailFrom"
									   cssStyle = "cursor: pointer" 
									   items="${settings.emailFromMap}"
									   delimiter = "&nbsp;&nbsp;"
									   onchange="$('.eventId').val(24);$('#settingsForm').submit();" 
					/>
					</div>
			    </td>
			
				<c:if test="${settings.emailFrom == 2}">
					<td align="left" colspan='2'>
			    		<form:input path="userEmail" 
			    		            class="userEmail" 
			    					cssStyle = "cursor: pointer"
									type="text"
									size="35%"
									placeholder='${settings.userServerEmailPlaceholder}'
									onkeydown="$('.eventId').val(25);if(event.keyCode==13){$('#settingsForm').submit();};"
						/>
					</td>
					
					<td align="left" colspan='1'>
						<img
							src="<c:url 
  							value="/resources/images/add.jpg"/>"
							style = "cursor: pointer"
							onclick="$('.eventId').val(25);$('#settingsForm').submit();"
						/>
					</td>
			    </c:if>
			</tr>
			    
			    <c:if test="${settings.emailFrom == 2}">
			    	<tr>
			    		<td align="left" colspan='3'>
							<img src="<c:url value="/resources/images/instructions.gif"/>" />
						</td>
			         	
						<td align="left" colspan='2'>
			     
							<form:password path="userPassword"
								   	   	   cssStyle = "cursor: pointer"
								   	   	   size="36%"
								   	   	   placeholder='${settings.userServerPasswordPlaceholder}'
								   	   	   onkeydown="$('.eventId').val(26);if(event.keyCode==13){$('#settingsForm').submit();};"
							/>
			    		</td>
			    		
			    		<td align="left" colspan='1'>
					<img
						src="<c:url 
  						value="/resources/images/add.jpg"/>"
						style = "cursor: pointer"
						onclick="$('.eventId').val(26);$('#settingsForm').submit();"
					/>
				</td>
			    	
			    	</tr>
				</c:if>

				<c:choose>
						<c:when test="${'undefined' != settings.messages && not empty settings.messages}">

				        	<tr>
				        	
				        		<td colspan='4' ></td>
				        		<td colspan='2' > <span>&nbsp;&nbsp;</span></td>
				        		
				        		<td colspan='2' align="center"> 

									<form:checkbox path="selelctAll"
												   style = "cursor: pointer ; position: relative; top: 7px;"
										   	   	   value=""
								   	       	   	   onchange="$('.eventId').val(33);$('#settingsForm').submit();"
									/>

								</td>
								
				        		<td colspan='1' > <span>${settings.email}&nbsp;&nbsp;</span></td>
				        		<td colspan='1' > <span>${settings.phone}&nbsp;&nbsp;</span></td>
				        	<tr>

						</c:when>
						<c:otherwise>
									
							<tr><td colspan='3' ><br></td><tr>

						</c:otherwise>
				</c:choose>

			<tr>
				<td align="left" colspan='1'><c:out value="${settings.addMessage}" /></td>

				<td align="left" colspan='1'>

					<form:input path="newAddress"
						        type="text"
								cssStyle = "cursor: pointer"
								size="55%"
								placeholder="${settings.addPlaceholder}"
								onkeydown="$('.eventId').val(20);if(event.keyCode==13){$('#settingsForm').submit();};"
					/>
				</td>
				<td align="left" colspan='1'>
					<img
						src="<c:url 
  						value="/resources/images/add.jpg"/>"
						style = "cursor: pointer"
						onclick="$('.eventId').val(20);$('#settingsForm').submit();"
					/>
				</td>

			</tr>
			
			<tr>
				<td align="left" colspan='1'><c:out value="${settings.name}" /></td>

				<td align="left" colspan='1'>

					<form:input path="nameValue"
						        type="text"
								cssStyle = "cursor: pointer"
								size="55%"
								placeholder="${settings.namePlaceholder}"
								onkeydown="$('.eventId').val(30);if(event.keyCode==13){$('#settingsForm').submit();};"
					/>
				</td>
				
				<td align="left" colspan='1'>
					<img
						src="<c:url 
  						value="/resources/images/add.jpg"/>"
						style = "cursor: pointer"
						onclick="$('.eventId').val(30);$('#settingsForm').submit();"
					/>
				</td>
			</tr>
			
			<tr>
				
				<td align="left" colspan='1'>
					<c:out value="${settings.messageTitleLabel}" />
				</td>

				<td align="left" colspan='1'>
		
				        	<form:input path="messageTitleValue" 
										type="text"
										size="55%"
										placeholder="${settings.titlePlaceholder}"
										onkeydown="$('.eventId').val(27);if(event.keyCode==13){$('#settingsForm').submit();};"
					 		/>

				</td>
				
				<td align="left" colspan='1'>
						<img
							src="<c:url 
  							value="/resources/images/add.jpg"/>"
							style = "cursor: pointer"
							onclick="$('.eventId').val(27);$('#settingsForm').submit();"
						/>
				</td>
			</tr>
			
			<tr>
			
				<td align="left" colspan='1' >
					<c:out value="${settings.messageContentLabel}" />
				</td>

				<td align="left" colspan='1'>	
				     <form:textarea path="messageContentValue" placeholder="${settings.contentPlaceholder}"                                       cssStyle = "background: transparent; font-size: 16px; width: 100%; max-width: 100%; height: 200px; max-height: 200px; word-wrap: break-word; text-align: left;"/>
				</td>
				
				<td align="left" colspan='1'>
					<img
						src="<c:url 
  						value="/resources/images/add.jpg"/>"
						style = "cursor: pointer"
						onclick="$('.eventId').val(28);$('#settingsForm').submit();"
					/>
				</td>
			
			</tr>
	
				<c:forEach var="message" items="${settings.messages}" varStatus="messagesStatus">
					<tr>
						<td align="left" colspan='1'></td>
			    		<td align="left" colspan='1'></td>
			    		<td align="left" colspan='1'></td>
			    		<td align="left" colspan='1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			    		<td align="left" colspan='1'>
							
		    		 	    <c:choose>
								<c:when test="${settings.selectedMessageId eq message.address}">
									
				                		
									<form:label path="selectedMessage"
    	 	            		 	    		cssStyle = "cursor: pointer ; position: relative; top: -298px; color: green ; font-weight: bold"
    	 	            		 	    		onclick="$('.selectedMessageId').val('${message.address}');$('.eventId').val(29);$('#settingsForm').submit();"  
		    		 	    		>
		    		     	     		${message.name}
		    		 	    		</form:label>
								
									
								</c:when>
								<c:otherwise>
									
									<form:label path="selectedMessage"
    	 	            		 	    		cssStyle = "cursor: pointer ; position: relative; top: -298px;"
    	 	            		 	    		onclick="$('.selectedMessageId').val('${message.address}');$('.eventId').val(29);$('#settingsForm').submit();"  
		    		 	    		>
		    		     	     		${message.name}
		    		 	    		</form:label>

								</c:otherwise>
							</c:choose>
		    		     	
						</td>
						<td align="left" colspan='1'>
							
		    		 	    <c:choose>
								<c:when test="${settings.selectedMessageId eq message.address}">
									
				                		
									<form:label path="selectedMessage"
    	 	            		 	    		cssStyle = "cursor: pointer ; position: relative; top: -298px; color: green ; font-weight: bold"
    	 	            		 	    		onclick="$('.selectedMessageId').val('${message.address}');$('.eventId').val(29);$('#settingsForm').submit();"  
		    		 	    		>
		    		     	     		${message.address}
		    		 	    		</form:label>
								
									
								</c:when>
								<c:otherwise>
									
									<form:label path="selectedMessage"
    	 	            		 	    		cssStyle = "cursor: pointer ; position: relative; top: -298px;"
    	 	            		 	    		onclick="$('.selectedMessageId').val('${message.address}');$('.eventId').val(29);$('#settingsForm').submit();"  
		    		 	    		>
		    		     	     		${message.address}
		    		 	    		</form:label>

								</c:otherwise>
							</c:choose>
		    		     	
						</td>
						<td align="center" colspan='2'>
							
							<form:checkbox path="messages[${messagesStatus.index}].selected"
										   value=""
								   	   	   cssStyle = "cursor: pointer ; position: relative; top: -298px;"
								   	       onchange="$('.selectedMessageId').val('${message.address}');$('.eventId').val(21);$('#settingsForm').submit();"
							/>
						</td>
	
						<td align="center" colspan='1'>
							<c:if test="${message.email eq true}">
								<form:checkbox path="messages[${messagesStatus.index}].sendEmail"
										   	   value=""
								   	   	   	   cssStyle = "cursor: pointer ; position: relative; top: -298px;"
								   	           onchange="$('.selectedMessageId').val('${message.address}');$('.eventId').val(31);$('#settingsForm').submit();"
								/>
							</c:if>
						</td>
						
						<td align="center" colspan='1'>
							<c:if test="${message.phone eq true}">
								<form:checkbox path="messages[${messagesStatus.index}].sendPhone"
										   	   value=""
								   	   	       cssStyle = "cursor: pointer ; position: relative; top: -298px;"
								   	       	   onchange="$('.selectedMessageId').val('${message.address}');$('.eventId').val(32);$('#settingsForm').submit();"
								/>
							</c:if>
						</td>
						
					
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						<td align="center" colspan='1'>	
							<img src="<c:url  value="/resources/images/delete.gif" />"
							     style = "cursor: pointer ; position: relative; top: -299px;"
							     onclick="$('.selectedMessageId').val('${message.address}');$('.eventId').val(22);$('#settingsForm').submit();" 
							/>
							 
						</td>
					</tr>  	
				</c:forEach>
		
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