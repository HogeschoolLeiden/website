<%@ include file="/WEB-INF/jspf/htmlTags.jspf" %>
<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>



	<nav class="tabs clearfix">
	    <ul>
	        <c:forEach var="menuItem" items="${mainMenu.siteMenuItems}">
	          
	            <c:set var="activeClass">${activeMenu.name eq menuItem.name?'class="active"':''}</c:set>
	            <li ${activeClass}>
	                <hst:link var="menuUrl" link="${menuItem.hstLink}" />
	                <a href="${menuUrl}">${menuItem.name}</a>
	            </li>
	        </c:forEach>
	    </ul>
	</nav>

