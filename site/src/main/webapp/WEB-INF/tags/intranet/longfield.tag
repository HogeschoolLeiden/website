<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<%@ attribute name="data" type="java.lang.Long" rtexprvalue="true"
  required="true" %>
<%@ attribute name="label" type="java.lang.String" rtexprvalue="true"
  required="true" %>
<%@ attribute name="hideWhenZero" type="java.lang.Boolean" rtexprvalue="true"
  required="false" %>

<%-- Tag for rendering an Long value on a Minor detail page --%>

<c:if test="${not empty data}">
    <c:set var="show" value="${(hideWhenZero and (data gt 0)) or !hideWhenZero}" />
    <c:if test="${show}">
	    <tr>
	        <td class="col1">${label}</td>
	        <td class="col2">${data}</td>
	    </tr>
    </c:if>
</c:if>