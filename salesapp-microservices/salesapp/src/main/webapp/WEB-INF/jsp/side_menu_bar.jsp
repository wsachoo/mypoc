<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<div id="sidebar-wrapper">
		<div class="nav-side-menu" id="sales_side_bar">
			<div class="menu-list">

				<ul id="menu-content" class="menu-content collapse out">
					<li class="heading1" style="background-color: #337ab7; text-align: center;"><span 
							style="display: inline; color: #fff; font-size: 18px; font-family: sans-serif;">Site Locations</span>

					</li>
				</ul>
				<hr style="margin: 0px; border: -1px solid #ffffff;" />

				<c:forEach items="${userSitesData.siteAddresses}" var="objUserSite">
					<ul id="menu-content" class="menu-content collapse out">
						<li class="heading1" style="background-color: #777">&nbsp; <input
							type="checkbox" value="${objUserSite.SITE_ID}"
							data-name="${objUserSite.SITE_NAME}"> <a
							data-toggle="collapse" data-target="#sub_${objUserSite.SITE_ID}"
							class=""> &nbsp; <span
								class="more-less glyphicon glyphicon-plus"
								style="color: #fff; font-size: 12px;"></span> <span
								class="sachlefnavheaderspaces"
								style="display: inline; color: #fff;font-size: 18px; font-family: sans-serif;">${objUserSite.SITE_NAME}</span>
						</a>

						</li>
						<div id="sub_${objUserSite.SITE_ID}" class="collapse">
							<li data-toggle="collapse"
								data-target="#componet_selection_${objUserSite.SITE_ID}"
								class="collapsed active"><a href="#"> <span
									class="sachlefnavheaderspaces"></span> <span
									class="glyphicon glyphicon-plus"></span> <span
									class="sachlefnavheaderspaces">Component Selection</span>
							</a></li>
							<ul class="sub-menu collapse"
								id="componet_selection_${objUserSite.SITE_ID}">
								<li class="active"><a href="#"
									id="access_id_${objUserSite.SITE_ID}">Access: (100M Max)</a></li>
								<li><a href="#" id="port_id_${objUserSite.SITE_ID}">Port:
										(100M Max)</a></li>
								<li><a href="#" id="feature_tag_id_${objUserSite.SITE_ID}">Features:
										(not applied)</a></li>
							</ul>
							<li data-toggle="collapse"
								data-target="#available_products_${objUserSite.SITE_ID}"
								class="collapsed cls_available_products"><a href="#"> <span
									class="sachlefnavheaderspaces"></span> <span
									class="glyphicon glyphicon-plus"></span> <span
									class="sachlefnavheaderspaces">Available Products</span>
							</a></li>
							<ul class="sub-menu collapse"
								id="available_products_${objUserSite.SITE_ID}"
								data-menu_site_id="${objUserSite.SITE_ID}">
								<li>Managed Internet Service</li>
								<li>Standalone AVPN</li>
								<li>VBB Express</li>
							</ul>
						</div>
					</ul>
					<hr style="margin: 0px; border: -1px solid #ffffff;" />
				</c:forEach>
			</div>
		</div>
</div>