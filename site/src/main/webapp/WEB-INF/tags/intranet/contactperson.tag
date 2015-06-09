<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<%@ attribute name="data" type="nl.hinttech.hsleiden.pi.beans.ContactPerson" rtexprvalue="true"
  required="true" %>
<%@ attribute name="label" type="java.lang.String" rtexprvalue="true"
  required="true" %>

<%-- Tag for rendering a ContactPerson item on a Minor detail page --%>

<c:if test="${not empty data and data.isFilledIn}">
    <tr>
        <td class="col1">${label}</td>
        <td class="col2">
            <c:if test="${not empty data.name}">
                ${fn:escapeXml(data.name)}<br />
            </c:if>
            <c:if test="${not empty data.emailAddress}">
                <em>E-mail: </em><a href="MAILTO:${data.emailAddress}">${fn:escapeXml(data.emailAddress)}</a><br />
            </c:if>
            <c:if test="${not empty data.telephoneNumber}">
                <em>Telefoon: </em>${fn:escapeXml(data.telephoneNumber)}<br />
            </c:if>
        </td>
    </tr>
</c:if>