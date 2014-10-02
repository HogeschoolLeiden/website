<%@ tag language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="facet" required="true" type="org.hippoecm.hst.content.beans.standard.HippoFolderBean"%>
<%@ attribute name="labels" required="false" type="java.util.Map"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<h2><c:out value="${facet.name}" escapeXml="true" /></h2>
<ul>
  <c:forEach items="${facet.folders}" var="item">
    <c:if test="${item.count > 0 }">
       <hst:link var="link" hippobean="${item}" navigationStateful="true"/>
       <li>
         <a href="${link}" class="btn">
           <c:out value="${labels[item.name]}" default="${item.name}" escapeXml="true" />
         </a>
       </li>
     </c:if>
  </c:forEach>
</ul>
