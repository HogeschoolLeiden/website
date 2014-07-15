<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix='hst' uri="http://www.hippoecm.org/jsp/hst/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--@elvariable id="form" type="org.onehippo.forge.easyforms.model.Form"--%>
<%--@elvariable id="likert" type="org.onehippo.forge.easyforms.model.Likert"--%>
<%--@elvariable id="ef_errors" type="java.util.List"--%>
<%--@elvariable id="error" type="org.onehippo.forge.easyforms.model.ErrorMessage"--%>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.widget.Messages"/>

<hst:defineObjects />
<c:set var="isCmsRequest" value="${hstRequest.requestContext.cmsRequest}" />

<c:if test="${fn:length(form.fields)==0 and isCmsRequest}">
  <p class="error-message"><fmt:message key="${webMasterMessage}" ></fmt:message></p>
</c:if>
  
<c:if test="${fn:length(form.fields)>0}">

<c:if test="${not empty form.title}">
  <h2><c:out value="${form.title}"/></h2>
</c:if>
<c:if test="${not empty formIntro}">
  <p><c:out value="${formIntro}"/></p>
</c:if>
<c:choose>
  <c:when test="${processDone}">
    <p>${afterProcessSuccessText}</p>
  </c:when>
  <c:otherwise>
    <c:forEach items="${ef_errors}" var="error">
      ${error.value.localizedMessage}
    </c:forEach>
    <c:if test="${maxFormSubmissionsReached}">
      <c:choose>
        <c:when test="${not empty maxFormSubmissionsReachedText}">
          <p><c:out value="${maxFormSubmissionsReachedText}"/></p>
        </c:when>
        <c:otherwise>
          <p>The maximum number of submission for this form has been reached</p>
        </c:otherwise>
      </c:choose>
    </c:if>
    <c:if test="${!maxFormSubmissionsReached}">
      <c:choose>
        <c:when test="${form.multipart}">
             <c:set var="enctype" value="multipart/form-data"/>
        </c:when>
        <c:otherwise>
          <c:set var="enctype" value="application/x-www-form-urlencoded"/>
        </c:otherwise>
      </c:choose>
      
      <form class="form" action="<hst:actionURL />" method="post" id="${form.id}" name="${form.name}" enctype="${enctype}">
        <c:forEach var="field" items="${form.fields}">
          <c:choose>
            <c:when test="${field.fieldSetTag}">
              ${field.html}
            </c:when>
            <c:when test="${field.simpleText}">
              <div class="ef-text">
                <h2>${field.label}</h2>

                <p>${field.hint}</p>
              </div>
            </c:when>
            <%-- simple types layout--%>
            <c:when test="${field.textField  or field.textArea or field.dropdown or field.radioBox or field.checkBox  or field.fileupload or field.password}">
              <div class="ef-field">
                <label>${field.label}<span class="ef-req">${field.requiredMarker}</span></label>${field.html}<span
                class="ef-hint">${field.hint}</span>
              </div>
            </c:when>
            <c:when test="${field.radioGroup}">
              <div class="ef-field">
                <label>${field.label}<span class="ef-req">${field.requiredMarker}</span></label>
                <c:forEach var="radio" items="${field.fields}">
                  <p>${radio.html} <span>${radio.label}</span></p>
                </c:forEach>
                <c:if test="${field.allowOther}">
                  ${field.otherChoice} Other: <span>${field.other}</span>
                </c:if>
                <span class="ef-hint">${field.hint}</span>
              </div>
            </c:when>
            <c:when test="${field.dateField}">
              <div class="ef-field">
                <label>${field.label}<span class="ef-req">${field.requiredMarker}</span></label>${field.html}
                <span class="ef-hint">${field.hint}</span>
              </div>
              <script>
                $(document).ready(function () {
                  $(function () {
                    $('input[name="${field.name}"]').datepicker({
                      showOn: "button",
                      buttonImage: "<hst:link path="/images/calendar.gif"/>",
                      buttonImageOnly: true,
                      dateFormat: '${field.dateFormat}',
                      autoSize: true
                    });
                  });
                });
              </script>
            </c:when>
            <c:when test="${field.checkBoxGroup}">
              <div class="ef-field">
                <label>${field.label}<span class="ef-req">${field.requiredMarker}</span></label>
                <c:forEach var="box" items="${field.fields}">
                  <p>${box.html} ${box.label}</p>
                </c:forEach>
                <c:if test="${field.allowOther}">
                  ${field.otherChoice} Other: <span>${field.other}</span>
                </c:if>
                <span class="ef-hint">${field.hint}</span>
              </div>
            </c:when>
            <%--  LIKERT--%>
            <c:when test="${field.likert}">
              <div class="ef-field">
                <label>${field.label}<span class="ef-req">${field.requiredMarker}</span></label>
                <table class="ef-likert-table">
                  <tr>
                    <td>&nbsp;</td>
                    <c:forEach var="option" items="${field.options}">
                      <td>${option}</td>
                    </c:forEach>
                  </tr>
                  <c:forEach var="map" items="${field.htmlMap}">
                    <tr>
                      <td>${map.key.label}</td>
                      <c:forEach var="radio" items="${map.value}">
                        <td>${radio.html}</td>
                      </c:forEach>
                    </tr>
                  </c:forEach>
                </table>
              </div>
            </c:when>
          </c:choose>
        </c:forEach>
        <div class="ef-buttons">
          <c:forEach var="button" items="${form.buttons}">
            ${button.html}
          </c:forEach>
        </div>
      </form>
      
    </c:if>
  </c:otherwise>
</c:choose>
<%--
    HERE WE PRINT JAVASCRIPT CALL WHICH WILL VALIDATE OUR FORM
--%>
${form.jsCall}
<%--
    //########################################################################
    //  HEADER CONTRIBUTIONS
    //########################################################################
--%>

<hst:headContribution keyHint="formValidationCss">
  <link rel="stylesheet" href="<hst:link path="/js/formcheck/theme/blue/formcheck.css"/>" type="text/css"/>
</hst:headContribution>
<hst:headContribution keyHint="jqueryUICss">
  <link rel="stylesheet" href="<hst:link path="/css/jquery-ui.min.css"/>" type="text/css"/>
</hst:headContribution>
<hst:headContribution keyHint="jquery-datepicker" category="scripts">
  <script type="text/javascript" src="<hst:link path="/js/jquery-ui.min.js"/>"></script>
</hst:headContribution>
<hst:headContribution keyHint="formJsValidation" category="scripts">
  <script type="text/javascript" src="<hst:link path="/js/jquery.validate.min.js"/>"></script>
</hst:headContribution>
<%--
    easy forms css
--%>
<hst:headContribution keyHint="formCss">
  <link rel="stylesheet" href="<hst:link path="/css/easyforms.css"/>" type="text/css"/>
</hst:headContribution>

</c:if>
