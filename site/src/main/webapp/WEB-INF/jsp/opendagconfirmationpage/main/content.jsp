<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<hst:setBundle basename="nl.hsleiden.opendag.Messages"/>

<c:choose>
  <c:when test="${empty model.status}">
    <tag:pagenotfound/>
  </c:when>
  <c:otherwise>

    <article class="well well-large">
      <header>
      	<c:choose>
      		<c:when test="${model.status eq 'ALREADY_REGISTERED'}">
          		<h1><fmt:message key="opendag.confirmation.already.registered"/></h1>
      		</c:when>
      		<c:when test="${model.status eq 'SUCCESS'}">
          		<h1><fmt:message key="opendag.confirmation.success"/></h1>
      		</c:when>
      		<c:when test="${model.status eq 'REGISTRATION_NOT_FOUND'}">
          		<h1><fmt:message key="opendag.confirmation.not.found"/></h1>
      		</c:when>
      		<c:when test="${model.status eq 'REGISTRATION_FAILED'}">
          		<h1><fmt:message key="opendag.confirmation.failure"/></h1>
      		</c:when>
      	</c:choose>

      </header>
      
      <tag:toolbox document="${document }" />
      
    </article>

  </c:otherwise>
</c:choose>