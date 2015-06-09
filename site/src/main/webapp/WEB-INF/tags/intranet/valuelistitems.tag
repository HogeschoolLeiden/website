<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<%@ attribute name="data" type="java.util.List" rtexprvalue="true"
  required="true" %>
<%@ attribute name="label" type="java.lang.String" rtexprvalue="true"
  required="true" %>
<%@ attribute name="valuelist" type="java.util.Map" rtexprvalue="true"
  required="true" %>

<%-- Tag for rendering an List of Valuelist items on a Minor detail page --%>

<c:if test="${not empty data}">
    <tr>
        <td class="col1">${label}</td>
        <td class="col2">
            <c:forEach var="value" items="${data}" varStatus="status">
                ${(!status.first)?', ':''}${valuelist[value]}
            </c:forEach>
        </td>
    </tr>
</c:if>