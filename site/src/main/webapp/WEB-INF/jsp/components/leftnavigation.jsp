<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.widget.Messages" />

<nav class="col-md-3 col-xs-12" role="navigation"/>
  <div class="subnav">
    
    <c:set var="parentItem" value="${tag:getTopMenuItem(menu, paramInfo.level)}"/>
    
    <ul>  
      <tag:renderTopMenuItem menuItem="${parentItem}"/>
      <c:forEach items="${hst:isReadable(parentItem, 'childMenuItems') ? parentItem.childMenuItems : parentItem.menuItems}" var="item">
        <opw:menuitem siteMenuItem="${item}" depth="1"
          expandedClass="current"
          selectedClass="active"
          unexpandedClass="unexpanded arrow-side" leafClass="arrow-side" 
          recurseOnlyExpanded="true"/>
      </c:forEach>
    </ul>
    
  </div>
</nav>

        