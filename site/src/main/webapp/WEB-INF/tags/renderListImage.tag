<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="item" type="hslbeans.WebPage" rtexprvalue="true" required="true"%>

<hst:setBundle basename="nl.hsleiden.widget.Messages" />

<c:choose>
  <c:when test="${not empty tag:getFirstFlexibleBlockImage(item) }">
      <hst:link var="image" hippobean="${tag:getFirstFlexibleBlockImage(item).image.listImageMedium}" />  
  </c:when>
  <c:otherwise>
      <c:if test="${hst:isReadable(item, 'headerImage') and not empty item.headerImage }">
        <hst:link var="image" hippobean="${item.headerImage.listImageMedium}" />
      </c:if>
  </c:otherwise>
</c:choose>

<figure class="media-object pull-left">
  <c:if test="${not empty image}">
    <img alt="${item.title }" title="${item.title }" src="${image }" />
  </c:if>
</figure>