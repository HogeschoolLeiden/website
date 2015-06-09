<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<%@ attribute name="data" type="nl.hinttech.hsleiden.pi.beans.Accessibility" rtexprvalue="true"
  required="true" %>
<%@ attribute name="label" type="java.lang.String" rtexprvalue="true"
  required="true" %>

<%-- Tag for rendering an Accessibility item on a Minor detail page --%>

<c:if test="${not empty data and data.isFilledIn}">
    
    <tr>
        <td class="col1">${label}</td>
        <td class="col2">
            <c:if test="${not empty data.educations}">
                <em>Studenten van opleiding(en): </em><br />
                <tag:normalizedtext data="${data.educations}" />
                <br />
                <br />
            </c:if>
            <c:if test="${fn:length(data.educationTypes) gt 0}">
                <em>Opleidingsvorm(en): </em> <br />
	            <c:forEach var="value" items="${data.educationTypes}" varStatus="status">
	                ${(!status.first)?', ':''}${opleidingtypes[value]}
	            </c:forEach>
	            <br />
            </c:if>
            <br />
            <c:if test="${not empty data.requirements}">
                <em>Fase opleiding: </em> <br />
                <tag:normalizedtext data="${data.requirements}" />
            </c:if>
        </td>
    </tr>
</c:if>