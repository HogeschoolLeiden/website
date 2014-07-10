<%@tag description="display image in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="doc" rtexprvalue="true" required="true" type="hslbeans.WebPage" %>

<c:if test="${tag:isSubclassOfWebPage(doc)}">
  <div class="inner">
    <c:if test="${not empty doc.title }">
      <h1><c:out value="${doc.title}"/></h1>
    </c:if>
    <c:if test="${not empty doc.introduction }">
      <p class="intro">
        <c:out value="${doc.introduction }" />
      </p>
    </c:if>
  </div>
</c:if>
