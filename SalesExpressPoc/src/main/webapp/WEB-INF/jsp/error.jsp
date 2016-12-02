<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<!-- Note: Please format this error page and message later -->
<body>
	<div style="color: red; font-size: 120%;">Error has occurred while processing request</div>
	<div style="color: red; font-size: 100%;">Error URL: ${ url }</div>
	<div style="color: red; font-size: 100%;">Error Stack: ${ exception }</div>
</body>
</html>