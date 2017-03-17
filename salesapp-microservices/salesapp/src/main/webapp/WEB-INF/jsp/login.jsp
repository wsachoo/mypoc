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


<title>Sales Application</title>

<%-- <link href="${contextPath}/css/bootstrap.css" rel="stylesheet"> --%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="${contextPath}/css/admin-landing-page.css">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #c3c3c3;
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
	color: black;
	margin-bottom: 20px;
	font-variant: small-caps;
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
<script>
	var contextPath = "${pageContext.request.contextPath}";
</script>
</head>

<body>
	<div class="container">
		<form method="POST" action="${contextPath}/login"
			class="form-horizontal">



				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			<div class="form-group">
				<div class="col-sm-6 col-sm-offset-3" style="text-align: center;">
					<font size="8.75rem;" color="black">Sales Application</font>
				</div>
			</div>

		<div class="row row-centered">
			<div class="col-sm-12">&nbsp;</div>
		</div>

			<div class="form-group ${SPRING_SECURITY_LAST_EXCEPTION.message != null ? 'has-error' : ''}">
				<div class="col-sm-4 col-sm-offset-4" style="text-align: center;">
					<div class="input-group">
						<div class="input-group-addon">
							<span class="glyphicon glyphicon-user"></span>
						</div>
						<input name="username" type="text" class="form-control"
							placeholder="Username" autofocus />
					</div>
				</div>
			</div>
			<div class="form-group ${SPRING_SECURITY_LAST_EXCEPTION.message != null ? 'has-error' : ''}">
				<div class="col-sm-4 col-sm-offset-4" style="text-align: center;">
					<div class="input-group">
						<div class="input-group-addon">
							<span class="glyphicon glyphicon-lock"></span>
						</div>
						<input name="password"
						type="password" class="form-control" placeholder="Password" />
					</div>
				</div>
			</div>
			
			<div class="form-group ${SPRING_SECURITY_LAST_EXCEPTION.message != null ? 'has-error' : ''}">
				<div class="col-sm-4 col-sm-offset-4" style="text-align: center;">
				<span>${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-4 col-sm-offset-4" style="text-align: center;">
							<button class="btn btn-lg btn-primary btn-block" type="submit" style="background-color: #337ab7">Log In</button>
				</div>
			</div>

		</form>

	</div>
	<script type="text/javascript"
		src="${contextPath}/js/lib/jquery-1.12.4.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/lib/bootstrap.min.js"></script>
</body>
</html>
