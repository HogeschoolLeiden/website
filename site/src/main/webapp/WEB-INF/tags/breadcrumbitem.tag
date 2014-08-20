<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="parentItem" type="org.hippoecm.hst.core.sitemenu.EditableMenuItem" rtexprvalue="true" required="true"%>
<%@ attribute name="lastItemClass" type="java.lang.String" rtexprvalue="true" required="false"%>

<c:if test="${empty lastItemClass }">
	<c:set var="lastItemClass" value="last"/>
</c:if>

<c:set var="hasExpandedItem" value="${false}"/>

<c:forEach items="${parentItem.childMenuItems}" var="item">
  <c:choose>
    <c:when test="${item.selected}">
	  <li class="${lastItemClass}"> 
        <span itemprop="title"><c:out value="${item.name}"/></span>
      </li>
	  <c:set var="hasExpandedItem" value="${true}"/>
    </c:when>
    <c:when test="${item.expanded}">
      <c:if test="${not tag:getSitemenuConfigParameter(item, 'disabled') eq true}">
	    <li>
          <a itemprop="url" href="<hst:link link="${item.hstLink}"/>">
            <span itemprop="title"><c:out value="${item.name} / "/></span> 
          </a>
        </li>
      </c:if>
	  <tag:breadcrumbitem parentItem="${item}"/>
	  <c:set var="hasExpandedItem" value="${true}"/>
    </c:when>
  </c:choose>
</c:forEach>
