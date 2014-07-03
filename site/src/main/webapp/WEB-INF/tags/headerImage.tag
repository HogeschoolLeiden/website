<%@tag description="display image in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="document" rtexprvalue="true" required="true" type="hslbeans.WebPage" %>

<c:if test="${hst:isReadable(document, 'headerImage')}">
  <c:if test="${not empty document.headerImage }">
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="header image span8">
          <hst:link var="img" hippobean="${document.headerImage.allWidthImage}"/>
          <c:if test="${not empty img }">
            <figure>
              <img src="${img}" title="${fn:escapeXml(document.headerImage.alt)}"
              alt="${fn:escapeXml(document.headerImage.alt)}"/>
            </figure>
          </c:if>
        </div>
      </div>
    </div>       
  </c:if>
</c:if>