<%@ tag language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="paginator" type="com.tdclighthouse.prototype.utils.PaginatorWidget" required="true" %>

<c:if test="${not empty paginator}">
 	<c:choose>
	   	<c:when test="${paginator.totalRows eq 0 }">			
	     	<h3>
              <c:set var="message" value="there are "/>
              <c:out value="${message}"/>
              <%-- <properties:property name="search.result.no" documentPath="search"/> --%>
            </h3>
	   	</c:when>
	   	<c:when test="${paginator.totalRows > 1 }">
	     	<h3>
              <span class="important">${model.facetBean.count}</span>&nbsp;
              <c:set var="oneitem" value="items"/>
              <c:out value="${oneitem}"/>
               <%-- <properties:property name="search.result.multiple" documentPath="search"/> --%>
            </h3>
	   	</c:when>
	   	<c:otherwise>
	     	<h3>
              <span class="important">${model.facetBean.count }</span>&nbsp;
              <c:set var="oneitem" value="items"/>
              <c:out value="${oneitem}"/>
              <%--
              <properties:property name="search.result.single" documentPath="search"/>
              --%>
            </h3>
		</c:otherwise>
 	</c:choose>
</c:if>