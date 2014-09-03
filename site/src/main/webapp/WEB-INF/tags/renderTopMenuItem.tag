<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="menuItem" type="org.hippoecm.hst.core.sitemenu.EditableMenuItem" rtexprvalue="true" required="true"%>

<c:choose>
  <c:when test="${tag:getSitemenuConfigParameter(menuItem, 'invisible') eq true}">
    <li><h2>&nbsp;</h2></li>
  </c:when>
  <c:otherwise>
    <c:choose>
      <c:when test="${tag:getSitemenuConfigParameter(menuItem, 'disabled') eq true}">
        <li><h2><c:out value=" ${menuItem.name}"/></h2></li>
      </c:when>
      <c:otherwise>
        <hst:link var="link" link="${menuItem.hstLink}"/>
        <li>
          <h2 ${menuItem.selected ? "class='selectedItem'" : ""}>
            <a href="${link}"><c:out value=" ${menuItem.name}"/></a>
          </h2>
        </li>
      </c:otherwise>
    </c:choose> 
  </c:otherwise>
</c:choose>

