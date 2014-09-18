<%@tag trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="item" rtexprvalue="true" required="true" type="nl.hsleiden.beans.OverviewBean"%>

<hst:setBundle basename="nl.hsleiden.sharebox.Messages" />

<section class="col-sm-6 col-md-3 area">
  <h1><span class="truncate"><c:out value="${item.overviewBean.title}"/></span></h1>
  <hst:link var="link" hippobean="${item.highLighted}" />
  <article class="article">
    <a href="${link}" title="${item.highLighted.title}">
       <h2><c:out value="${item.highLighted.title}"/></h2>
        
       <tag:renderDate document="${item.highLighted}" dateClass="datum start large"></tag:renderDate>
        
       <figure>
         <img  class="img-responsive"
               src="<hst:link hippobean="${item.highLighted.headerImage.paragraphImage }" />" 
               alt="<c:out value="${item.highLighted.title }" escapeXml="true" />" 
               title="<c:out value="${item.highLighted.title }" escapeXml="true" />" />
       </figure>
        
       <p><c:out value="${item.highLighted.introduction }"/></p>
    </a>
  </article>
          
  <ul>
     <c:forEach items="${item.menuItem.childMenuItems}" var="item">
        <opw:menuitem siteMenuItem="${item}" depth="0"
          expandedClass="current arrow-down"
          selectedClass="active arrow-down"
          unexpandedClass="unexpanded arrow-side" leafClass="arrow-side" 
          recurseOnlyExpanded="false"/>
     </c:forEach>
  </ul>
</section>

