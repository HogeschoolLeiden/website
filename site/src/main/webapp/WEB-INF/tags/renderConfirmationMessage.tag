<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="model" type="java.util.Map" rtexprvalue="true" required="true"%>
<%@ attribute name="message" type="java.lang.String" rtexprvalue="true" required="true"%>

<hst:setBundle basename="nl.hsleiden.opendag.Messages"/>

<c:choose>
    <c:when test="${not empty model.name and not empty model.surname }">
      <fmt:message key="${message}.dynamic" >
        <fmt:param value="${model.name}"/>
        <fmt:param value="${model.surname}"/>
      </fmt:message>
    </c:when>
    <c:otherwise>
      <fmt:message key="${message}"/>
    </c:otherwise>
</c:choose>

 


