<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta charset="utf-8">
	<title>Tools</title>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

</head>
<body>

	<br>
		<form method="POST" 
			  action="translate" 
			  enctype="application/x-www-form-urlencoded"
			  accept-charset="UTF-8"
		>
	
			<input type="submit" value="Translate" class="button" />

		</form> 
<%-- 
   <br>
   
		<form method="POST" 
			  action="insertVersion" 
			  enctype="application/x-www-form-urlencoded"
			  accept-charset="UTF-8"
		>
	
			<input type="submit" value="Insert database" class="button" />

		</form>
   <br>
 
		<form method="POST" 
			  action="testVersion" 
			  enctype="application/x-www-form-urlencoded"
			  accept-charset="UTF-8"
		>
	
			<input type="submit" value="Test database" class="button" />

		</form> 
		
		 <br>
		<form method="POST" 
			  action="screensaver" 
			  enctype="application/x-www-form-urlencoded"
			  accept-charset="UTF-8"
		>
	
			<input type="submit" value="count screensaver pictures" class="button" />

		</form> 
		<font color="green">${screensaver}</font> --%>
</body>
</html>