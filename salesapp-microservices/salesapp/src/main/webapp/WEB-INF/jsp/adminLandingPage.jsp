<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Sales Admin Application</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script>
	var contextPath = "${pageContext.request.contextPath}";
	document
			.write('<link rel="stylesheet" href="${contextPath}/css/bootstrap.css" type="text/css" />');
	document
			.write('<link rel="stylesheet" href="${contextPath}/css/admin-landing-page.css" type="text/css" />');
</script>

</head>

<body style="background-color: #c3c3c3">
	<div class="container">
		<div class="row">
			<div class="col-sm-1 col-sm-offset-11">
				<a href="${contextPath}/logout" style="color: white;font-weight: bold;"> 
					<%-- <img class="img-circle img-responsive img-center" style="width: 75px;height: 75px;" src="${pageContext.request.contextPath}/images/adminIconLogout.png"alt=""> --%>
					<span class="box-ibmorange" style="text-align: right;">LOGOUT</span>
				</a>
			</div>
		</div>
		<div class="row row-centered">
			<div class="col-sm-11">
				<font size="8.75rem;" color="black">Sales Application Administration</font>
			</div>
		</div>

		<div class="row row-centered">
			<div class="col-sm-12 box--high">
			</div>
		</div>
		<div class="row row-centered">
			<div class="col-sm-4">
				<a href="${contextPath}/admin/productConfiguration"> <img class="img-circle img-responsive img-center"
					src="${pageContext.request.contextPath}/images/adminIconProductConfig.png"
					alt="">
				</a>
			</div>
			<div class="col-sm-4">
				<a href="${contextPath}/admin/serviceAndFeaturesConfiguration"> <img class="img-circle img-responsive img-center"
					src="${pageContext.request.contextPath}/images/adminIconSiteConfig.png"
					alt="">
				</a>
			</div>
			<div class="col-sm-4">
				<img class="img-circle img-responsive img-center"
					src="${pageContext.request.contextPath}/images/adminIconOtherConfig.png"
					alt="">
			
<%-- 				<a href=""> <img class="img-circle img-responsive img-center"
					src="${pageContext.request.contextPath}/images/adminIconOtherConfig.png"
					alt="">
				</a>
 --%>			</div>
		</div>
		<div class="row row-centered">
			<div class="col-sm-4">
				<a href="${contextPath}/admin/productConfiguration">
					<font size="5.75rem;" color="black">Product Configuration</font>
				</a>
			</div>
			<div class="col-sm-4">
				<a href="${contextPath}/admin/serviceAndFeaturesConfiguration">
					<font size="5.75rem;" color="black">Add/Remove Services</font>
				</a>
			</div>
			<div class="col-sm-4">
				<font size="5.75rem;" color="black">Misc Configuration</font>
<!-- 				<a href="#">
					<font size="5.75rem;" color="white">Misc Configuration</font>
				</a>
 -->			</div>
		</div>

	</div>
</body>

</html>
