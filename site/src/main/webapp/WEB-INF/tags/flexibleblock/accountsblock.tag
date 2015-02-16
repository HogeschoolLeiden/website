<%@tag description="display image in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<%@ attribute name="content" rtexprvalue="true" required="true" type="hslbeans.Accounts" %>
<%@ attribute name="name" rtexprvalue="true" required="true" type="java.lang.String" %>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.general.Messages"/>

<fmt:message key="follow.medewerker.facebook"  var="facebook">
  <fmt:param value="${name}"/>
</fmt:message>
<fmt:message key="follow.medewerker.googleplus"  var="googleplus">
  <fmt:param value="${name}"/>
</fmt:message>
<fmt:message key="follow.medewerker.linkedin"  var="linkedin">
  <fmt:param value="${name}"/>
</fmt:message>
<fmt:message key="follow.medewerker.twitter" var="twitter">
  <fmt:param value="${name}"/>
</fmt:message>

<c:if test="${fn:length(content.accounts)>0}">
  <figure class="medewerkers">
    <ul class="social clearfix">
      <c:forEach var="account" items="${content.accounts}" varStatus="zebra">
        
          <c:choose>
            <c:when test="${account.type eq 'twitter'}">
              <li class="twitter">
                <a href="${account.link}" target="_blank" title="${twitter}"></a>
              </li>
            </c:when>
            <c:when test="${account.type eq 'linkedin'}">
              <li class="linkedin">
                <a href="${account.link}" target="_blank" title="${linkedin}"></a>
              </li>
            </c:when>
            <c:when test="${account.type eq 'facebook'}">
               <li class="facebook">
                  <a href="${account.link}" target="_blank" title="${facebook}"></a>
               </li>
            </c:when>
            <c:when test="${account.type eq 'googleplus'}">
                <li class="googleplus">
                  <a href="${account.link}" target="_blank" title="${googleplus}"></a>
                </li>
            </c:when>
          </c:choose>
  
      </c:forEach>
    </ul>
  </figure>
</c:if>