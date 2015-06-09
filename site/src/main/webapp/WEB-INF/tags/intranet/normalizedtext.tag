<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<%@ attribute name="data" type="java.lang.String" rtexprvalue="true"
  required="true" %>

<%-- Tag for rendering a flat text value; newlines will be replaced by <br /> elements.  --%>

<c:if test="${not empty data}">
    <c:set var="newline" value="<%= \"\n\" %>" />
    <c:set var="text" value="${fn:escapeXml(data)}" />
    <c:set var="text" value="${fn:replace(text,newline,'<br />')}" />
    ${text}
</c:if>