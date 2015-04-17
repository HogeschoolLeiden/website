<%@ include file="/WEB-INF/jspf/htmlTags.jspf" %>
<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
  
<c:if test="${not empty trainings}">
    <select name="opleiding" class="filter filter-opleiding">
        <option value="">Selecteer opleiding</option>
        <c:forEach var="option" items="${trainings.items}">
            <c:set var="selected">${option.key eq selectedTraining?'selected="selected"':''}</c:set>
            <option value="${option.key}" ${selected}>${option.label}</option>
        </c:forEach>
    </select>
</c:if>


<c:if test="${not empty departments}">
    <select name="afdeling" class="filter filter-afdeling">
        <option value="">Selecteer afdeling</option>
        <c:forEach var="option" items="${departments.items}">
            <c:set var="selected">${option.key eq selectedDepartment?'selected="selected"':''}</c:set>
            <option value="${option.key}" ${selected}>${option.label}</option>
        </c:forEach>
    </select>
</c:if>

