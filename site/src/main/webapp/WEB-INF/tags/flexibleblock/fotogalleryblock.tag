<%@tag description="display image in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="content" rtexprvalue="true" required="true" type="hslbeans.Fotogallery" %>

<c:if test="${not empty content and not empty content.title and fn:length(content.image)>0 }">

  <c:if test="${not empty content.title}">
    <h2><c:out value="${content.title }"/></h2>
  </c:if>

  <figure class="gallery row">
    <c:forEach items="${content.image}" var="item">
      <hst:link fullyQualified="true" hippobean="${item.wideImage }" var="imageLink"></hst:link>
         
      <a href="${imageLink }" data-atr="prettyPhoto" class="col-xs-6 col-sm-4 col-md-4">
         <img  src="<hst:link hippobean="${item.listImageLarge }" />" alt="${fn:escapeXml(image.alt) }" title="${fn:escapeXml(image.alt) }"/>
         <span class="fa fa-search"></span>
      </a>
    </c:forEach>								
  </figure>
</c:if>
