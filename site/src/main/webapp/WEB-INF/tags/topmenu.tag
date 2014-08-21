<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="menuItem" type="org.hippoecm.hst.core.sitemenu.EditableMenuItem" rtexprvalue="true" required="true"%>

<ul class="sub-menu">
 
  <c:choose>
    <c:when test="${tag:getSitemenuConfigParameter(menuItem, 'levelOneOnly') eq true}">
      <c:forEach items="${menuItem.childMenuItems}" var="item">
        <opw:menuitem siteMenuItem="${item}" depth="0"
          expandedClass="current arrow-down"
          selectedClass="active arrow-down"
          unexpandedClass="unexpanded arrow-side" leafClass="arrow-side" 
          recurseOnlyExpanded="false"/>
      </c:forEach>
    </c:when>
    <c:otherwise>
      <c:forEach items="${menuItem.childMenuItems}" var="item">
        <opw:menuitem siteMenuItem="${item}" depth="1"
          expandedClass="current arrow-down"
          selectedClass="active arrow-down"
          unexpandedClass="unexpanded arrow-side" leafClass="arrow-side" 
          recurseOnlyExpanded="false"/>
      </c:forEach>
    </c:otherwise>
  </c:choose> 

</ul>
