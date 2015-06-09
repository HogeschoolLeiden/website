<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<%@ attribute name="data" type="java.lang.String" rtexprvalue="true"
  required="true" %>
<%@ attribute name="label" type="java.lang.String" rtexprvalue="true"
  required="true" %>

<%-- Tag for rendering a String value item on a Minor detail page --%>

<c:if test="${not empty data}">
    <tr>
        <td class="col1">${label}</td>
        <td class="col2"><tag:normalizedtext data="${data}" /></td>
    </tr>
</c:if>