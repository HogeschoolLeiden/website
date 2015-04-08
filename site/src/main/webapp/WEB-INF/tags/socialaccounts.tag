<%@ tag language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="document" rtexprvalue="true" required="true" type="org.hippoecm.hst.content.beans.standard.HippoDocument" %>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.blog.Messages"/>

<ul class="social clearfix">
  <c:forEach var="account" items="${document.accounts}" varStatus="zebra">
    
    <c:set var="accountClass"><c:out value=""/></c:set>
    <c:set var="accountClass">
      <c:choose>
        <c:when test="${account.type eq 'twitter'}"><c:out value="fa fa-twitter"/></c:when>
        <c:when test="${account.type eq 'linkedin'}"><c:out value="fa fa-linkedin"/></c:when>
        <c:when test="${account.type eq 'facebook'}"><c:out value="fa fa-facebook"/></c:when>
        <c:when test="${account.type eq 'googleplus'}"><c:out value="fa fa-google-plus"/></c:when>
      </c:choose>
    </c:set>
    
    <c:if test="${not empty accountClass}">
      <li>
        <a href="${account.link}" class="${accountClass}" target="_blank" rel="nofollow"
           title="<fmt:message key="follow.author"/> ${account.type}">
           <span class="hidden"><fmt:message key="follow.author"/> ${account.type}</span></a></li>
    </c:if>
  </c:forEach>
</ul>