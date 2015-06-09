<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<%@ attribute name="data" type="nl.hinttech.hsleiden.pi.beans.Roster" rtexprvalue="true"
  required="true" %>
<%@ attribute name="label" type="java.lang.String" rtexprvalue="true"
  required="true" %>

<%-- Tag for rendering a Roster on a Minor detail page --%>

<c:if test="${not empty data and data.isFilledIn}">
    <tr>
        <td class="col1">${label}</td>
        <td class="col2">
            <c:forEach var="rosterDay" items="${data.rosterDays}" varStatus="status">
                <em>${lesdagen[rosterDay.day]}:&nbsp</em>
                <c:forEach var="dayPart" items="${rosterDay.dayParts}" varStatus="status">
                    ${(!status.first)?', ':''}${dagdelen[dayPart]}
                </c:forEach>
                <br />
            </c:forEach>
            <%-- 
            <br />
            <strong>Het aantal uren aangegeven in de relevante dagdelen</strong><br />
            Ochtend: 8.30 - 13:30<br />
            Middag: 13.30 - 18:30<br />
            Avond: 18.30 - 22:30<br /> --%>
        </td>
    </tr>
</c:if>