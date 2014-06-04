<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%--@elvariable id="errorComponentWindow" type="org.hippoecm.hst.core.container.HstComponentWindow"--%>

<c:if test="${not empty errorComponentWindow.componentExceptions}">
  <ul>
    <c:forEach var="componentException" items="${errorComponentWindow.componentExceptions}">
      <li>
        <pre>${fn:escapeXml(componentException.message)}</pre>
        <!--
            <c:forEach items="${componentException.stackTrace}" var="line">
              ${fn:escapeXml(line)}<br>
            </c:forEach>
          -->
      </li>
    </c:forEach>
  </ul>
</c:if>