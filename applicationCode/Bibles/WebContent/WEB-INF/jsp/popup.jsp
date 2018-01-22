<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" buffer="2048kb" %>
<html>
<head>
<title>${popup.popup}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

	<link   rel="stylesheet"       href="<c:url value="/resources/css/style.css" />"        />

<script type="text/javascript" charset="utf-8">

function startListening() {

	try {
		
		if('${popup.usingVoice}' == 'true'){

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

	            recognizer.lang = '${popup.languageCode}';
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
			       $("#eventId").val(3);
			       $("#popupForm").submit();
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

	function isValidPictureSelection() {

		var sizeError =           document.getElementById('wrongFileSizeId').value;
		var formatError =         document.getElementById('wrongFormatId').value + " JPEG, JPG, BMP, PNG, GIF";
		var wrongFiles = "\r\n" + document.getElementById('wrongFilesId').value + "\r\n";
		var file = document.querySelector("#selectResourcesId");
		var message = "";
		var totalSize = 0;
		if (file != 'undefined') {

			var arrayFiles = file.files;

			if (arrayFiles != 'undefined' && arrayFiles != '' && arrayFiles != null) {

				for (var i = 0; i != arrayFiles.length; ++i) {
					
					var pictureFile = arrayFiles[i];
					totalSize += pictureFile.size;
					
					if (pictureFile == 'undefined' || pictureFile == '' || pictureFile == null) {
						$('#selectResourcesId').val('');
						document.getElementById('selectResourcesId').value = null;
						return false;
					} else {
						if (/\.(JPEG|jpeg|JPG|jpg|BMP|bmp|PNG|png|GIF|gif)$/i.test(pictureFile.name) === false){
							wrongFiles += pictureFile.name + "\r\n";
							if(message.indexOf(formatError) === -1){
								message += formatError + "\r\n";
							}
						}
						if (pictureFile.size > 409715200){
							if(wrongFiles.indexOf(pictureFile.name) === -1){
								wrongFiles += pictureFile.name + "\r\n";
							}
							if(message.indexOf(sizeError) === -1){
								message += sizeError + "\r\n";
							}
						}
					}
				}
			}
		}
		if ((totalSize > 409715200) && (message.indexOf(sizeError) === -1)){
			message += sizeError.replace("---", (Math.floor(totalSize/1000000) + ' MB'));
		}
		if (message == 'undefined' || message == '' || message == null || message == ""){
			return true;
		} else {
			$('#selectResourcesId').val('');
			document.getElementById('selectResourcesId').value = null;
			alert(message + wrongFiles);
			return false;
		}
	}
	
	function setPopup(openPopup, popupTitle) {
		  if(openPopup  === "true"){
			  
			 var myWindow = window.open("", '${popup.popupId}', "width=800,height=400,menubar=no,titlebar=no,toolbar=no,location=no,status=no,fullscreen=yes,channelmode=yes,scrollbars=yes");
 
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
<body  onload="startListening();setPopup('${popup.openPopup}', '${popup.bible}');">

<div class="tab">
  <button id="bibleTabId">${popup.bible}</button>
  <button id="churchTabId">${popup.church}</button>
  <button class="active">${popup.settings}</button>
  <button id="screensaverTabId">${popup.screensaver}</button>
  <button id="codeTabId">${popup.code}</button>
  <button id="voiceTabId">${popup.voice}</button>
  <button id="feedbackTabId">${popup.feedback}</button>
  <button id="thankYouTabId">${popup.thankYou}</button>
</div>

<br><br>
<div class="tab">
  <button id="BibleSettingsId" >${popup.bible}</button>
  <button class="active"       >${popup.popup}</button>
  <button id="referenceSettingsId" >${popup.reference}</button>
</div>

	<div align="center">
		<c:if test="${not empty popup.error}">
			<font color="red">${popup.error}</font>
			
		</c:if>
	</div>

	<form:form action="popup" 
			   modelAttribute="popup"
			   enctype="multipart/form-data" 
			   id="popupForm"
			   accept-charset="UTF-8"
	>

		<form:input path="openPopup" 
					type="text" 
					id="openPopupId" 
					hidden="true" 
		/>

		<form:input path="wrongFileSize"
		    		type="text" 
					id="wrongFileSizeId" 
				    hidden="true"
		/>

		<form:input path="wrongFormat"
		    		type="text" 
					id="wrongFormatId" 
				    hidden="true"
		/>

		<form:input path="wrongFiles"
		    		type="text" 
					id="wrongFilesId" 
				    hidden="true"
		/>

		<form:input path="identifiedWords"
					id="identifiedWordsId" 
				    hidden="true"
		/>
		
		<form:input path="popupInformation" 
					type="text" 
					value="${popup.popupInformation}"
					id="popupInformationId" 
				    hidden="true"
		/>  

		<form:input path="eventId" type="text" class="eventId" hidden="true" />

		<table style="width: 20%" class="tableClass">

			<tr> 
			
				<td colspan='1'><c:out value="${popup.boldLabel}" /></td>
				<td  colspan='1'>
					<form:checkbox path="popupBoldSelected" 
								   value="" 
								   cssStyle = "cursor: pointer"
								   onchange="$('.eventId').val(9);this.form.submit();" 
					/>
				</td>
			
				<td colspan='1'>&nbsp;&nbsp;<c:out value="${popup.popupTextColorLabel}" /></td>
				<td  colspan='1'>

						  	<form:input path="popupTextColorPaletteSelected" 
										type="color"
										value="${popup.popupTextColorPaletteSelected}"
						                onchange="$('.eventId').val(19);this.form.submit();"
							/>

				</td>
			</tr>
			<tr>

				<td colspan='1'><c:out value="${popup.checkBoxPictureLabel}" /></td>
			
				<td  colspan='1'>
							<form:checkbox path="displayPicture" 
								   		   value="" 
								   		   cssStyle = "cursor: pointer"
								   		   onchange="$('.eventId').val(26);this.form.submit();" 
							/>
				</td>

                <td colspan='1'>&nbsp;&nbsp;<c:out value="${popup.popupBackgroundColorLabel}" /></td>
				<td  colspan='1'>
						  	<form:input path="popupBackgroundColorPaletteSelected" 
										type="color"
										value="${popup.popupBackgroundColorPaletteSelected}"
						                onchange="$('.eventId').val(18);this.form.submit();"
							/>

				</td>
			</tr>
			
			<tr>

				<td colspan='1'>
				    <c:out value="${popup.defaultPopupImageLabel}" />
			   </td>
			
			   <td colspan='1'>

					<form:checkbox path="defaultPopupImageSelected" 
								   value="" 
								   cssStyle = "cursor: pointer"
								   onchange="$('.eventId').val(50);this.form.submit();" 
					/>
				</td>
				
				<td colspan='1'>&nbsp;&nbsp;<c:out value="${popup.imageOpacityLabel}" /></td>
				
				<td colspan='1'>
							<form:select path="imageOpacitySelected"
						 		 		 onchange="$('.eventId').val(29);this.form.submit();"
						 		 		 selected="true"
						 		 		 cssStyle = "cursor: pointer"
						 		 		 items="${popup.pictureOpacityList}"
							/>
				</td>
			</tr>

			<tr>

				<td colspan='1'><c:out value="${popup.checkBoxScriptureTextLabel}" /></td>
			
				<td  colspan='1'>
							<form:checkbox path="displayScriptureText" 
								   		   value="" 
								   		   cssStyle = "cursor: pointer"
								   		   onchange="$('.eventId').val(25);this.form.submit();" 
							/>
				</td>
				
				<td colspan='1'>&nbsp;&nbsp;<c:out value="${popup.textOpacityLabel}" /></td>

				<td  colspan='1'>
							<form:select path="scriptureTextOpacitySelected"
						 		 		 onchange="$('.eventId').val(28);this.form.submit();"
						 		 		 selected="true"
						 		 		 cssStyle = "cursor: pointer"
						 		 		 items="${popup.scriptureTextOpacityList}"
							/>
				</td>
			</tr>
			
			<tr>

				<td colspan='1'><c:out value="${popup.checkBoxUserMessageLabel}" /></td>
			
				<td  colspan='1'>
							<form:checkbox path="displayUserMessage" 
								   		   value="" 
								   		   cssStyle = "cursor: pointer"
								   		   onchange="$('.eventId').val(27);this.form.submit();" 
							/>
				</td>
				
			   <td colspan='1'>
				   &nbsp;&nbsp;<c:out value="${popup.fontLabel}" />
               </td>
               
               <td colspan='1'>
				    <form:select path="popupFontSelected" 
	            			   	 items="${popup.popupFontList}"
							 	 value="${popup.popupFontSelected}" 
							 	 selected="true"
							  	 onchange="$('.eventId').val(6);this.form.submit();" 
							  	 cssStyle = "cursor: pointer"
				    />
               </td>

			</tr>

			<tr>
				<td colspan='1'><c:out value="${popup.popupMarginTopLabel}" /></td>
				<td  colspan='1'>
	            	<form:select path="popupMarginTopSelected" 
	            			   	 items="${popup.popupMarginTopList}"
							 	 value="${popup.popupMarginTopSelected}" 
							 	 selected="true"
							  	 onchange="$('.eventId').val(13);this.form.submit();" 
							  	 cssStyle = "cursor: pointer"
				    />
				</td>

				<td  colspan='1'>
			    	&nbsp;&nbsp;<c:out value="${popup.lineHeightPopupLabel}" />
                </td>
                
                <td  colspan='1'>    
                    <form:select path="lineHeightPopupSelected" 
	            			   	 items="${popup.lineHeightPopupList}"
							 	 value="${popup.lineHeightPopupSelected}" 
							  	 onchange="$('.eventId').val(56);this.form.submit();" 
							  	 cssStyle = "cursor: pointer"
				    />
               </td>

			</tr>
			<tr>

				<td colspan='1'><c:out value="${popup.popupMarginBottomLabel}" /></td>
				<td  colspan='1'>
		            
	            	<form:select path="popupMarginBottomSelected" 
	            			   	 items="${popup.popupMarginBottomList}"
							 	 value="${popup.popupMarginBottomSelected}" 
							 	 selected="true"
							  	 onchange="$('.eventId').val(14);this.form.submit();" 
							  	 cssStyle = "cursor: pointer"
				    />
				</td>

				<td colspan='1'>&nbsp;&nbsp;<c:out value="${popup.popupTextAlignLabel}" /></td>
				<td  colspan='1'>
					<form:select path="popupTextAlignSelected"
						     	 onchange="$('.eventId').val(17);this.form.submit();"
						     	 selected="true"
						     	 cssStyle = "cursor: pointer"
					>
    					<c:forEach items="${popup.popupTextAlignMap}" var="textAlign">			
        					<form:option value="${textAlign.key}">
            					       ${textAlign.value}
        					</form:option>                    
    					</c:forEach>
					</form:select>
				</td> 
			</tr>

			<tr>

                <td colspan='1'><c:out value="${popup.popupMarginRightLabel}" /></td>
				<td colspan='1'>

	            	<form:select path="popupMarginRightSelected" 
	            			   	 items="${popup.popupMarginRightList}"
							 	 value="${popup.popupMarginRightSelected}" 
							 	 selected="true"
							  	 onchange="$('.eventId').val(15);this.form.submit();" 
							  	 cssStyle = "cursor: pointer"
				    />
				</td>

				<td colspan='1'>&nbsp;&nbsp;<c:out value="${popup.fontStylePopupLabel}" /></td>
				<td  colspan='1'>
		            
	            	<form:select path="fontStylePopupSelected" 
	            			   	 items="${popup.fontStylePopupList}"
							 	 value="${popup.fontStylePopupSelected}" 
							  	 onchange="$('.eventId').val(53);this.form.submit();" 
							  	 cssStyle = "cursor: pointer"
				    />
				</td>
			</tr>
			<tr>
			
			<td colspan='1'><c:out value="${popup.popupMarginLeftLabel}" /></td>
				<td  colspan='1'>
	            	<form:select path="popupMarginLeftSelected" 
	            			   	 items="${popup.popupMarginLeftList}"
							 	 value="${popup.popupMarginLeftSelected}" 
							 	 selected="true"
							  	 onchange="$('.eventId').val(16);this.form.submit();" 
							  	 cssStyle = "cursor: pointer"
				    />
				</td>
			
 			    <td colspan='1'>&nbsp;&nbsp;<c:out value="${popup.fontFamilyPopupLabel}" /></td>
				<td  colspan='1'>
		            
	            	<form:select path="fontFamilyPopupSelected" 
	            			   	 items="${popup.fontFamilyPopupList}"
							 	 value="${popup.fontFamilyPopupSelected}" 
							  	 onchange="$('.eventId').val(52);this.form.submit();" 
							  	 cssStyle = "cursor: pointer"
				    />
				</td>

			</tr>
			
			<tr>
			
			<td  colspan='1'>
			    <c:out value="${popup.letterSpacingPopupLabel}" />
			</td>
			
			<td  colspan='1'>
			 		<form:select path="letterSpacingPopupSelected" 
	            			   	 items="${popup.letterSpacingPopupList}"
							 	 value="${popup.letterSpacingPopupSelected}" 
							  	 onchange="$('.eventId').val(55);this.form.submit();" 
							  	 cssStyle = "cursor: pointer"
			 		/>
			 </td>

             	<td colspan='1'>&nbsp;&nbsp;<c:out value="${popup.popupPictureLabel}" /></td>
				<td  colspan='1'>
		            
	            	<form:input path="popupPictureSelected"
						        type= "file"
								cssStyle = "cursor: pointer"
								multiple="multiple"
								id="selectResourcesId"
								class="popupImageClass"
					/>
				</td>

              </tr>
			
			  <tr>
              
              <td  colspan='1'>
				<c:out value="${popup.wordSpacingPopupLabel}" />
 			  </td>
 			
 			  <td  colspan='1'>
				<form:select path="wordSpacingPopupSelected" 
	            			   	 items="${popup.wordSpacingPopupList}"
							 	 value="${popup.wordSpacingPopupSelected}" 
							  	 onchange="$('.eventId').val(57);this.form.submit();" 
							  	 cssStyle = "cursor: pointer"
				    />
 			  </td>
 			  
            <td colspan='1'>&nbsp;&nbsp;<c:out value="${popup.popupUserMessageLabel}" /></td>
			
			<td  colspan='1'>
				 	<img
						src="<c:url 
  						value="/resources/images/add.jpg"/>"
						id="addPopupUserMessage"
						style = "cursor: pointer"
					/>
			</td>
 
             </tr>

		</table>
				
	    <form:textarea path="popupUserMessageSelected"
					   type="text"
					   class="userMessageClass"
					   cssStyle = "cursor: pointer"
		/>
	</form:form>
	
		<script type="text/javascript" charset="utf-8">
			$("#bibleTabId").click(function() {
			$("#eventId").val(1);
			$("#popupForm").submit();
			});

			$("#churchTabId").click(function() {
			$("#eventId").val(2);
			$("#popupForm").submit();
			});

			$("#screensaverTabId").click(function() {
			$("#eventId").val(4);
			$("#popupForm").submit();
			});

			$("#codeTabId").click(function() {
			$("#eventId").val(5);
			$("#popupForm").submit();
			});

			$("#voiceTabId").click(function() {
			$("#eventId").val(10);
			$("#popupForm").submit();
			});

			$("#BibleSettingsId").click(function() {
			$("#eventId").val(7);
			$("#popupForm").submit();
			});

			$("#referenceSettingsId").click(function() {
			$("#eventId").val(8);
			$("#popupForm").submit();
			});

			$("#addPopupUserMessage").click(function() {
					$("#eventId").val(22);
					$("#popupForm").submit();
			});

		   $('.popupImageClass').change('change',function(){
				if(isValidPictureSelection()){
					$("#eventId").val(23);
					$("#popupForm").submit();
			}
			});

			$("#feedbackTabId").click(function() {
			$("#eventId").val(11);
			$("#popupForm").submit();
			});
			
			$("#thankYouTabId").click(function() {
			$("#eventId").val(12);
			$("#popupForm").submit();
			});
		</script>

</body>
</html>