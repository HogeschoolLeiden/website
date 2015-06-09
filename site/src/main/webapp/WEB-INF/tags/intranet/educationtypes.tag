<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<%@ attribute name="data" type="java.util.List" rtexprvalue="true"
  required="true" %>
<%@ attribute name="label" type="java.lang.String" rtexprvalue="true"
  required="true" %>
<%@ attribute name="otherValue" type="java.lang.String" rtexprvalue="true"
  required="true" %>

<%-- Tag for rendering the education types on a Minor detail page --%>

<c:if test="${not empty data}">
    <tr>
        <td class="col1">${label}</td>
        <td class="col2">
            
            <c:forEach var="value" items="${data}" varStatus="status">
                
                <c:choose>                    
	                <c:when test="${value eq 'anders'}">
	                   ${(!status.first)?', ':''}${fn:escapeXml(otherValue)}	                    
	                </c:when>
	                <c:otherwise>
	                   ${(!status.first)?', ':''}${onderwijstypes[value]}
	                </c:otherwise>
                </c:choose>
            </c:forEach>
        </td>
    </tr>
</c:if>