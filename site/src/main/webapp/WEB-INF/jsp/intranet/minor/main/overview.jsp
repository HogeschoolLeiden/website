<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<%-- This JSP displays the overview of Minor documents --%>

<%@include file="/WEB-INF/jsp/intranet/common/content.jsp" %>

 <section id="minoroverview" class="sections-column wide">
     
	<c:choose>
	   <c:when test="${not empty groupedResult}">
	         
	     <c:forEach var="group" items="${groupedResult.groups}">
	     
	         <h2>Minoren van ${fn:escapeXml(group.category)}</h2>
	         
	         <table class="minor-overview">
	       
              <thead>
                  <tr>
	                  <td>Naam</td>
	                  <td>Periodes</td>
	                  <td>ECT's</td>
                  </tr>
              </thead>
	                 
	          <c:forEach var="minor" items="${group.documents}">
	              <tr>
	                 <hst:link var="detailUrl" hippobean="${minor}"/>
	                 <td class="col1"><a href="${detailUrl}" title="${fn:escapeXml(minor.introduction)}">${fn:escapeXml(minor.title)}</a></td>
	                 <td class="col2">${fn:escapeXml(minor.periods)}</td>
	                 <td class="col3">${fn:escapeXml(minor.ECTs)}</td>
	              </tr>
	          </c:forEach>
	         </table>
	     </c:forEach>
	   </c:when>
	   
	   <c:otherwise>
	     <p>Er zijn geen minoren gevonden</p>
	   </c:otherwise>
	</c:choose>

 </section>


<%@include file="/WEB-INF/jsp/intranet/common/metadata.jsp" %> 