<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ attribute name="paginator" required="true" type="nl.hinttech.hsleiden.pi.beans.Paginator" rtexprvalue="true" %>
<%@ attribute name="searchText" required="true" type="java.lang.String" rtexprvalue="true" %>
<%@ attribute name="training" required="true" type="java.lang.String" rtexprvalue="true" %>
<%@ attribute name="department" required="true" type="java.lang.String" rtexprvalue="true" %>
                        
<c:if test="${(not empty paginator.totalPages) and paginator.totalPages gt 1}">
    
    <c:url var="baseUrl" value="">
	    <c:if test="${not empty searchText}">
	         <c:param name="query" value="${searchText}"/>
	    </c:if>
	    <c:if test="${not empty department}">
	       <c:param name="afdeling" value="${department}"/>
	    </c:if>
	    <c:if test="${not empty training}">
	       <c:param name="opleiding" value="${training}"/>
	    </c:if>
    </c:url>
                
    <nav class="search-pagination">
        <ul>
            <c:if test="${paginator.currentPage gt 1}">
                <c:url var="pageUrl" value="${baseUrl}">
                    <c:param name="page" value="${1}"/>
                </c:url>
                <li><a href="${pageUrl}" title="eerste pagina">&#10094;&#10094;</a>&nbsp;&nbsp;</li>

                <c:url var="pageUrl" value="${baseUrl}">
                    <c:param name="page" value="${paginator.previousPage}"/>
                </c:url>
                <li><a href="${pageUrl}"title="vorige pagina" >&#10094;</a></li>
            </c:if>
            <c:forEach var="page" items="${paginator.pages}">
                <c:choose>
	           
                    <c:when test="${page eq paginator.currentPage}">
                        <li class="active"><strong>&nbsp; ${page} &nbsp;</strong></li>
                    </c:when>
                    
					<c:otherwise>
						
						<c:url var="pageUrl" value="${baseUrl}">
						    <c:param name="page" value="${page}"/>
						</c:url>
						
						<li>&nbsp;<a href="${pageUrl}" title="pagina ${page}">${page}</a>&nbsp;</li>
					</c:otherwise>

                </c:choose>
            </c:forEach>
            <c:if test="${paginator.currentPage lt paginator.totalPages}">
                
                <c:url var="pageUrl" value="${baseUrl}">
                    <c:param name="page" value="${paginator.nextPage}"/>
                </c:url>
                <li><a href="${pageUrl}" title="volgende pagina">&#10095;</a></li>
                
                <c:url var="pageUrl" value="${baseUrl}">
                    <c:param name="page" value="${paginator.totalPages}"/>
                </c:url>
                <li><a href="${pageUrl}" title="laatste pagina">&nbsp;&nbsp;&#10095;&#10095;</a></li>
                
            </c:if>
        </ul>
    </nav>
</c:if>
