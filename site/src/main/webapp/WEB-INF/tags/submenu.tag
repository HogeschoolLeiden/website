<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="menuItem" type="org.hippoecm.hst.core.sitemenu.EditableMenuItem" rtexprvalue="true" required="true"%>
<%@ attribute name="columNr" type="java.lang.Integer" rtexprvalue="true" required="true" %>

<li>
    <ul>
      
      <c:choose>
        <c:when test="${tag:getSitemenuConfigParameter(menuItem.childMenuItems[columNr], 'invisible') eq true}">
          <li class="h2"><h2>&nbsp;</h2></li>
        </c:when>
        <c:otherwise>
          <c:choose>
          <c:when test="${tag:getSitemenuConfigParameter(menuItem.childMenuItems[columNr], 'disabled') eq true}">
            <li class="h2"><h2><c:out value=" ${menuItem.childMenuItems[columNr].name}"/></h2></li>
          </c:when>
          <c:otherwise>
            <hst:link var="link" link="${menuItem.childMenuItems[columNr].hstLink}"/>
            <li class="h2"><h2><a href="${link}"><c:out value=" ${menuItem.childMenuItems[columNr].name}"/></a></h2></li>
          </c:otherwise>
          </c:choose> 
        </c:otherwise>
      </c:choose>
 
      <c:forEach items="${menuItem.childMenuItems[columNr].childMenuItems}" var="item">
        <opw:menuitem siteMenuItem="${item}" depth="0"
          expandedClass="current arrow-down"
          selectedClass="active arrow-down"
          unexpandedClass="unexpanded arrow-side" leafClass="arrow-side" 
          recurseOnlyExpanded="false"/>
      </c:forEach>
    
    </ul>
    </li>

