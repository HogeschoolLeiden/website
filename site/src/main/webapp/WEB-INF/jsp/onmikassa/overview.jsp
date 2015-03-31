<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix='hst' uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<hst:setBundle basename="nl.hsleiden.general.Messages" />

<table>
  <thead>
    <tr>
      <th colspan="2"><strong><fmt:message key="form.input" /></strong></th>
    </tr>
  </thead>
  <c:forEach items="${form.fields}" var="field">
    <c:choose>
      <c:when test="${field.type eq 'simpletextfield' or field.type eq 'fieldgroup'}">
        <tr>
          <th colspan="2"><c:out value="${field.label}" /></th>
        </tr>
      </c:when>
      <c:otherwise>
        <c:set var="value" value="${formMap.value[field.formRelativeUniqueName].value}" />
        <tr>
          <td><c:out value="${field.label}" /></td>
          <td><c:choose>
              <c:when test="${field['class'].name eq 'org.onehippo.forge.easyforms.model.DropDown'}">
                <c:out value="${field.displayValues[tag:indexOf(field.values, value)]}" />
              </c:when>
              <c:when test="${field['class'].name eq 'org.onehippo.forge.easyforms.model.RadioGroup'}">
                <c:choose>
                  <c:when test="${not empty value}">
                    <c:out value="${value}" />
                  </c:when>
                  <c:otherwise>
                    <fmt:message key="form.input.no" />
                  </c:otherwise>
                </c:choose>
              </c:when>
              <c:otherwise>
                <c:out value="${value}" />
              </c:otherwise>
            </c:choose></td>
        </tr>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</table>
      
<c:choose>
  <c:when test="${price gt 0}">
    <form method="post" action="${omnikassaRequest.url}" class="omnikassa-overview">
      <input class="btn" type="hidden" name="Data" value="${omnikassaRequest.data}"> <input class="btn" type="hidden" name="InterfaceVersion"
        value="${omnikassaRequest.interfaceVersion}"> <input class="btn" type="hidden" name="Seal" value="${omnikassaRequest.seal}">
      <fmt:message key="form.pay" var="pay" />
      <input class="btn" type="submit" value="${pay}" />
    </form>
  </c:when>
  <c:otherwise>
      <div class="omnikassa-overview">
        <hst:link siteMapItemRefId="omnikassaReturnPage" var="confirmationUrl" >
          <hst:param name="order_id" value="${formMap.value['order id'].value}"/>
        </hst:link>
        <a href="${confirmationUrl}" class="btn"><fmt:message key="form.confirm"/></a>
      </div>
  </c:otherwise>
</c:choose>