<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<div class="catalog navigation">
  <ul class="nav nav-pills nav-stacked">
    <c:forEach items="${menu.menuItems}" var="topMenuItem">
      <c:if test="${topMenuItem.expanded}">
        <c:forEach items="${topMenuItem.childMenuItems}" var="item">
          <opw:menuitem siteMenuItem="${item}" depth="${depth}"
            expandedClass="current arrow-down"
            selectedClass="active arrow-down"
            unexpandedClass="unexpanded arrow-side" leafClass="arrow-side" 
            recurseOnlyExpanded="${recurseOnlyExpanded}"/>
        </c:forEach>
      </c:if>
    </c:forEach>
  </ul>
</div>