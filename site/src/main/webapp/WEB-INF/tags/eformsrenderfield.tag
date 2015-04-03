<%@ tag description="render form field item" pageEncoding="UTF-8" %>
<%@ attribute name="field" type="com.onehippo.cms7.eforms.hst.model.AbstractField" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<c:choose>

  <c:when test="${field.type eq 'simpletextfield'}">
    <div class="eforms-text" name="${field.formRelativeUniqueName}">
      <div class="${field.styleClass}"><c:out value='${field.label}'/></div>
    </div>
  </c:when>

  <c:when test="${field.type eq 'textfield'}">
    <div class="eforms-field">
      <label><c:out value='${field.label}'/><span class="eforms-req"><c:out value='${field.requiredMarker}'/></span></label>
      <input type="text" name="${field.formRelativeUniqueName}" class="${field.styleClass}" value="${field.value}"
             <c:if test='${field.length gt 0}'>size="${field.length}"</c:if> <c:if test='${field.maxLength gt 0}'>maxlength="${field.maxLength}"</c:if> />
      <span class="eforms-hint"><c:out value='${field.hint}'/></span>
    </div>
  </c:when>

  <c:when test="${field.type eq 'passwordfield'}">
    <div class="eforms-field">
      <label><c:out value='${field.label}'/><span class="eforms-req"><c:out value='${field.requiredMarker}'/></span></label>
      <input type="password" name="${field.formRelativeUniqueName}" class="${field.styleClass}"
             <c:if test='${field.length gt 0}'>size="${field.length}"</c:if> <c:if test='${field.maxLength gt 0}'>maxlength="${field.maxLength}"</c:if> />
      <span class="eforms-hint"><c:out value='${field.hint}'/></span>
    </div>
  </c:when>

  <c:when test="${field.type eq 'textarea'}">
    <div class="eforms-field">
      <label><c:out value='${field.label}'/><span class="eforms-req"><c:out value='${field.requiredMarker}'/></span></label>
      <textarea name="${field.formRelativeUniqueName}" class="${field.styleClass}"
                cols="${field.cols}" rows="${field.rows}"
                <c:if test='${field.minLength gt 0}'>minlength="${field.minLength}"</c:if> <c:if test='${field.maxLength gt 0}'>maxlength="${field.maxLength}"</c:if>>${field.value}</textarea>
      <span class="eforms-hint"><c:out value='${field.hint}'/></span>
    </div>
  </c:when>

  <c:when test="${field.type eq 'dropdown'}">
    <div class="eforms-field">
      <label><c:out value='${field.label}'/><span class="eforms-req"><c:out value='${field.requiredMarker}'/></span></label>
      <select name="${field.formRelativeUniqueName}" class="${field.styleClass}">
        <c:forEach var="option" items="${field.options}">
          <option value="<c:out value='${option.value}'/>" <c:if test='${option.selected}'>selected="selected"</c:if>><c:out value="${option.text}"/></option>
        </c:forEach>
      </select>
      <span class="eforms-hint"><c:out value='${field.hint}'/></span>
    </div>
  </c:when>

  <c:when test="${field.type eq 'fileuploadfield'}">
    <div class="eforms-field">
      <label><c:out value='${field.label}'/><span class="eforms-req"><c:out value='${field.requiredMarker}'/></span></label>
      <input type="file" name="${field.formRelativeUniqueName}" class="${field.styleClass}" />
      <span class="eforms-hint"><c:out value='${field.hint}'/></span>
    </div>
  </c:when>

  <c:when test="${field.type eq 'datefield'}">
    <div class="eforms-field">
      <label>
        <c:out value='${field.label}'/>
        <span class="eforms-req"><c:out value='${field.requiredMarker}'/></span>
      </label>
      <input type="text" name="${field.formRelativeUniqueName}" class="date ${field.styleClass}" value="${field.value}" />
      <span class="eforms-hint"><c:out value='${field.hint}'/></span>
    </div>
    <hst:headContribution category="scripts">
    <script>
      $(document).ready(function() {
        $(function() {
          $('input[class*="date"][name="${field.formRelativeUniqueName}"]').datepicker({
            showOn : "button",
            buttonImage : "<hst:link path='/images/calendar2.gif'/>",
            buttonImageOnly : true,
            dateFormat : '${field.dateFormat}'.replace(/yyyy/g, 'yy').replace(/M/g, 'm'), // because java date format is different from jquery-ui date format
            autoSize : true
          });
        });
      });
    </script>
    </hst:headContribution>
  </c:when>

  <c:when test="${field.type eq 'radiogroup'}">
    <div class="eforms-field radiobuttons">
      <label><c:out value='${field.label}'/><span class="eforms-req"><c:out value='${field.requiredMarker}'/></span></label>
      <ul class="radiogroup">
        <c:forEach var="radio" items="${field.fields}" varStatus="step">
          <li>
            <input type="radio" name="${field.formRelativeUniqueName}" id="${field.formRelativeUniqueName}${step.index}"
                   class="${radio.styleClass}" value="<c:out value='${radio.value}'/>"
                   <c:if test='${radio.checked}'>checked="true"</c:if> />
            <label class="instant-label" for="${field.formRelativeUniqueName}${step.index}"><c:out value='${radio.label}'/></label>
          </li>
        </c:forEach>
        <c:if test="${field.allowOther}">
          <li>
            <input type="radio" name="${field.formRelativeUniqueName}" class="${field.styleClass}" value="-other"
              <c:if test='${field.otherValue}'>checked="true"</c:if> />
            Other:
            <span>
              <input type="text" name="${field.formRelativeUniqueName}-other" class="textfield-other"
                     <c:if test='${field.length gt 0}'>size="${field.length}"</c:if> <c:if test='${field.maxLength gt 0}'>maxlength="${field.maxLength}"</c:if> />
            </span>
          </li>
        </c:if>
      </ul>
      <span class="eforms-hint"><c:out value='${field.hint}'/></span>
    </div>
  </c:when>

  <c:when test="${field.type eq 'checkboxgroup'}">
    <div class="eforms-field checkboxes">
      <label><c:out value='${field.label}'/><span class="eforms-req"><c:out value='${field.requiredMarker}'/></span></label>
      <c:forEach var="checkbox" items="${field.fields}" varStatus="step">
        <p>
          <input type="checkbox" name="${checkbox.formRelativeUniqueName}" 
                 class="${checkbox.styleClass}" value="<c:out value='${checkbox.value}'/>"
                 id="${checkbox.formRelativeUniqueName}${step.index}"
                 <c:if test='${checkbox.checked}'>checked="true"</c:if> />
          <label class="instant-label" for="${checkbox.formRelativeUniqueName}${step.index}"> <c:out value='${checkbox.label}'/> </label>
        </p>
      </c:forEach>
      <c:if test="${field.allowOther}">
        <input type="checkbox" name="${field.formRelativeUniqueName}" class="${field.styleClass}" value="-other"
          <c:if test='${field.otherValue}'>checked="true"</c:if> />
        Other:
        <span>
          <input type="text" name="${field.formRelativeUniqueName}-other" class="textfield-other"
                 <c:if test='${field.length gt 0}'>size="${field.length}"</c:if> <c:if test='${field.maxLength gt 0}'>maxlength="${field.maxLength}"</c:if> />
        </span>
      </c:if>
      <span class="eforms-hint"><c:out value='${field.hint}'/></span>
    </div>
  </c:when>

  <c:when test="${field.type eq 'likert'}">
    <div class="eforms-field">
      <label><c:out value='${field.label}'/><span class="eforms-req"><c:out value='${field.requiredMarker}'/></span></label>
      <table class="eforms-likert-table">
        <tr>
          <td>&nbsp;</td>
          <c:forEach var="option" items="${field.options}">
            <td>
              ${option}
            </td>
          </c:forEach>
        </tr>
        <c:forEach var="item" items="${field.optionsMap}">
          <tr>
            <td>${item.key.label}</td>
            <c:forEach var="radio" items="${item.value}">
              <td>
                <input type="radio" name="${radio.formRelativeUniqueName}" class="${radio.styleClass}" value="${radio.value}"
                       <c:if test='${radio.checked}'>checked="true"</c:if> />
              </td>
            </c:forEach>
          </tr>
        </c:forEach>
      </table>
      <span class="eforms-hint"><c:out value='${field.hint}'/></span>
    </div>
  </c:when>

</c:choose>
