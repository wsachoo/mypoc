<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<sec:authorize access="isAuthenticated()">
	    <sec:authentication property="principal.username" var="username" />
	</sec:authorize>
	<h2>Dear <strong>${username}</strong>,</h2>
	<h3>You are not authorized to access this page
	<a href="<c:url value="/logout" />">Logout</a></h3>
</body>
</html>