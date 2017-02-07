<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="nav-side-menu" id="sales_side_bar">
	<div class="menu-list">
	<c:forEach items="${userSitesData.siteAddresses}" var="objUserSite"> 
		<ul id="menu-content" class="menu-content collapse out">
			<li><input type="checkbox" value="${objUserSite.SITE_ID}" data-name="${objUserSite.SITE_NAME}">
			<h4 style="display: inline;">${objUserSite.SITE_NAME}</h4></li>
			<li data-toggle="collapse" data-target="#componet_selection_${objUserSite.SITE_ID}" class="collapsed active">
				<a href="#">
					<span class="sachlefnavheaderspaces"></span>
					<span class="glyphicon glyphicon-plus"></span>
					<span class="sachlefnavheaderspaces">Component Selection</span>
				</a>
			</li>
			<ul class="sub-menu collapse" id="componet_selection_${objUserSite.SITE_ID}">
				<li class="active"><a href="#" id="access_id_${objUserSite.SITE_ID}">Access: (100M Max)</a></li>
				<li><a href="#" id="port_id_${objUserSite.SITE_ID}">Port: (100M Max)</a></li>
				<li><a href="#" id="feature_tag_id_${objUserSite.SITE_ID}">Features: (not applied)</a></li>
			</ul>
			<li data-toggle="collapse" data-target="#available_products_${objUserSite.SITE_ID}" class="collapsed">
					<a href="#">
						<span class="sachlefnavheaderspaces"></span>
						<span class="glyphicon glyphicon-plus"></span>
						<span class="sachlefnavheaderspaces">Available Products</span>
						</a>
			</li>
			<ul class="sub-menu collapse" id="available_products_${objUserSite.SITE_ID}" data-menu_site_id="${objUserSite.SITE_ID}">
				<li>Managed Internet Service</li>
				<li>Standalone AVPN</li>
				<li>VBB Express</li>
			</ul>
		</ul>
		
		<hr style="border-top: 1px solid #c5bebe;"></hr>
	</c:forEach>
	</div>
</div>

