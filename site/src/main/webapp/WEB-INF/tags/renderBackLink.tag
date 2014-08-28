<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="sitemapRefId" type="java.lang.String" rtexprvalue="true" required="true"%>

<hst:setBundle basename="nl.hsleiden.widget.Messages" />

<c:if test="${not empty sitemapRefId}">
  <div class="container">
    <div class="row">
      <c:set var="refId" value="${sitemapRefId}"/>
      <hst:link siteMapItemRefId="${refId}" var="overviewLink"/>
      <a class="btn back" href="${overviewLink}" title="<fmt:message key="back.overview.${refId}" />">
        <fmt:message key="back.overview.${refId}"/>
      </a>
    </div>
  </div>
</c:if>


