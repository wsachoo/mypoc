<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="_csrf" content="${_csrf.token}" />


<title>Sales App</title>

<link href="${contextPath}/css/bootstrap.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #eee;
}

.form-signin {
	max-width: 330px;
	padding: 15px;
	margin: 0 auto;
}

.form-signin .form-signin-heading, .form-signin .checkbox {
	margin-bottom: 10px;
}

.form-heading {
	color: #337ab7;
	margin-bottom: 20px;
}

.form-signin .checkbox {
	font-weight: normal;
}

.form-signin .form-control {
	position: relative;
	height: auto;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	padding: 10px;
	font-size: 16px;
}

.form-signin .form-control:focus {
	z-index: 2;
}

.form-signin input {
	margin-top: 10px;
	border-bottom-right-radius: 0;
	border-bottom-left-radius: 0;
}

.form-signin button {
	margin-top: 10px;
}

.has-error {
	color: red
}
</style>
</head>

<body>
	<div class="container">
		<form method="POST" action="${contextPath}/login" class="form-signin">
			<h2 class="form-heading">Sales App Login</h2>
			<div
				class="form-group ${SPRING_SECURITY_LAST_EXCEPTION.message != null ? 'has-error' : ''}">
				<input name="username" type="text" class="form-control"
					placeholder="Username" autofocus /> <input name="password"
					type="password" class="form-control" placeholder="Password" />
					<span>${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

				<button class="btn btn-lg btn-primary btn-block" type="submit">Log
					In</button>
			</div>
		</form>

	</div>
	<script type="text/javascript"
		src="${contextPath}/js/lib/jquery-1.12.4.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/lib/bootstrap.min.js"></script>
</body>
</html>