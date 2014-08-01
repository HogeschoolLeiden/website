<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="menuItem" type="org.hippoecm.hst.core.sitemenu.EditableMenuItemImpl" rtexprvalue="true" required="true"%>
<%@ attribute name="columNr" type="java.lang.Integer" rtexprvalue="true" required="true" %>

<hst:link var="link" link="${menuItem.childMenuItems[columNr].hstLink}"/>
<h3><a href="${link}"><c:out value=" ${menuItem.childMenuItems[columNr].name}"/></a></h3> 

<ul class="sub-menu">
 
  <c:forEach items="${menuItem.childMenuItems[columNr].childMenuItems}" var="item">
    <opw:menuitem siteMenuItem="${item}" depth="1"
      expandedClass="current arrow-down"
      selectedClass="active arrow-down"
      unexpandedClass="unexpanded arrow-side" leafClass="arrow-side" 
      recurseOnlyExpanded="false"/>
  </c:forEach>

</ul>