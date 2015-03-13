<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld" %>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype" %>

<%@ attribute name="paginator" rtexprvalue="true" required="true" type="com.tdclighthouse.prototype.utils.PaginatorWidget" %>

<c:if test="${tag:hasPrevPage(paginator)}">  
  <c:set var="prevPgUrl">
    <hst:link navigationStateful="true">
      <hst:param name="page" value="${paginator.page - 1}"/>
    </hst:link>
  </c:set>
  <hst:headContribution>
    <link rel="prev" href="${prevPgUrl}"/>
  </hst:headContribution>
</c:if>
<c:if test="${tag:hasNextPage(paginator)}">                
  <c:set var="nextPgUrl">
    <hst:link navigationStateful="true">
      <hst:param name="page" value="${paginator.page + 1}"/>
    </hst:link>
  </c:set>
  <hst:headContribution>
    <link rel="next" href="${nextPgUrl}"/>
  </hst:headContribution>
</c:if>