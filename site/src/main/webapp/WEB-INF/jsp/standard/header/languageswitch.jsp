<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<c:if test="${not noTranslation}">
  <c:forEach items="${translations}" var="translation">
  	<hst:link var="link" link="${translation.link}" fullyQualified="true" />
  	<c:set var="languageName">
  		<fmt:message key="translation.lang"></fmt:message>
  	</c:set>
  	<c:if test="${not (translation.selected)}">
  	   <%-- <li class="active"><a><span>${languageName}</span></a></li> --%>
       <li><a title="${languageName}" href="${link}"><span>${languageName}</span></a></li>
  	</c:if>
  </c:forEach>
</c:if>

