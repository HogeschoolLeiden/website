<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix='hst' uri="http://www.hippoecm.org/jsp/hst/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.widget.Messages"/>

<c:if test="${not empty form}">
<c:if test="${not empty form.title}">
  <h2><c:out value="${form.title}" /></h2>
</c:if>

<c:if test="${not empty formIntro}">
  <p><c:out value="${formIntro}" /></p>
</c:if>

<c:set var="style">
  <c:if test="${empty eforms_errors}">display:none;</c:if>
</c:set>
<div id="feedbackPanel" class="nojs-error" style="${style}">
  <ul>
    <c:forEach items="${eforms_errors}" var="error">
      <li><c:out value="${error.value.localizedMessage}"/></li>
    </c:forEach>
  </ul>
</div>

<c:if test="${maxFormSubmissionsReached}">
  <c:choose>
    <c:when test="${not empty maxFormSubmissionsReachedText}">
      <p><c:out value="${maxFormSubmissionsReachedText}" /></p>
    </c:when>
    <c:otherwise>
      <p><fmt:message key="max.submission.nr.message"/></p>
    </c:otherwise>
  </c:choose>
</c:if>

<c:if test="${not maxFormSubmissionsReached}">

  <form class="form" action="<hst:actionURL />" method="post" name="${form.name}" <c:if test="${form.multipart}">enctype="multipart/form-data"</c:if>>

    <c:set var="formPages" value="${form.pages}" />

    <c:if test="${fn:length(formPages) gt 1}">
      <ul id="pagesTab" class="eforms-pagetab" style="DISPLAY: none">
        <c:forEach var="page" items="${formPages}" varStatus="status">
          <c:choose>
            <c:when test="${status.index eq 0}">
              <li class="conditionally-visible selected"><c:out value="${page.label}" /></li>
            </c:when>
            <c:otherwise>
              <li class="conditionally-visible"><c:out value="${page.label}" /></li>
            </c:otherwise>
          </c:choose>
        </c:forEach>
      </ul>
    </c:if>

    <c:forEach var="page" items="${formPages}" varStatus="status">

      <div id="page${status.index}" class="eforms-page conditionally-visible">

        <c:forEach var="fieldItem" items="${page.fields}">
          <c:choose>
            <c:when test="${fieldItem.type eq 'fieldgroup'}">
              <c:set var="groupCssClassName" value="eforms-fieldgroup" />
              <c:if test="${fieldItem.oneline}">
                <c:set var="groupCssClassName" value="eforms-fieldgroup oneline" />
              </c:if>
              <fieldset name="${fieldItem.fieldNamePrefix}" class="${groupCssClassName}">
                <c:if test="${not empty fieldItem.label}">
                  <legend class="eforms-fieldgroupname"><c:out value="${fieldItem.label}"/></legend>
                </c:if>
                <c:forEach var="fieldItemInGroup" items="${fieldItem.fields}">
                  <tag:eformsrenderfield field="${fieldItemInGroup}"/>
                </c:forEach>
                <span class="eforms-hint"><c:out value="${fieldItem.hint}"/></span>
              </fieldset>
            </c:when>
            <c:otherwise>
              <tag:eformsrenderfield field="${fieldItem}"/>
            </c:otherwise>
          </c:choose>

        </c:forEach>

      </div>

    </c:forEach>

    <div class="eforms-buttons">
      <c:forEach var="button" items="${form.buttons}">
        <c:choose>
          <c:when test="${button.type eq 'resetbutton'}">
            <input type="reset" name="${button.formRelativeUniqueName}" class="btn ${button.styleClass}"
                   value="<c:choose><c:when test='${empty button.value}'><c:out value='${button.name}'/></c:when><c:otherwise><c:out value='${button.value}'/></c:otherwise></c:choose>" />
          </c:when>
          <c:when test="${button.type eq 'submitbutton'}">
            <input type="submit" name="${button.formRelativeUniqueName}" class="btn ${button.styleClass}"
                   value="<c:choose><c:when test='${empty button.value}'><c:out value='${button.name}'/></c:when><c:otherwise><c:out value='${button.value}'/></c:otherwise></c:choose>" />
          </c:when>
          <c:when test="${button.type eq 'nextbutton'}">
            <input id="nextPageButton" type="button" name="${button.formRelativeUniqueName}" class="btn ${button.styleClass}" style="DISPLAY: none"
                   value="<c:choose><c:when test='${empty button.value}'><c:out value='${button.name}'/></c:when><c:otherwise><c:out value='${button.value}'/></c:otherwise></c:choose>" />
          </c:when>
          <c:when test="${button.type eq 'previousbutton'}">
            <input id="previousPageButton" type="button" name="${button.formRelativeUniqueName}" class="btn ${button.styleClass}" style="DISPLAY: none"
                   value="<c:choose><c:when test='${empty button.value}'><c:out value='${button.name}'/></c:when><c:otherwise><c:out value='${button.value}'/></c:otherwise></c:choose>" />
          </c:when>
          <c:otherwise>
            <input type="button" name="${button.formRelativeUniqueName}" class="btn ${button.styleClass}"
                   value="<c:choose><c:when test='${empty button.value}'><c:out value='${button.name}'/></c:when><c:otherwise><c:out value='${button.value}'/></c:otherwise></c:choose>" />
          </c:otherwise>
        </c:choose>
      </c:forEach>
    </div>

  </form>

</c:if>

<%--
    //########################################################################
    //  HEADER CONTRIBUTIONS
    //########################################################################
--%>


<hst:headContribution category="scripts">
<script type="text/javascript">
$(document).ready(function() {

  $(&apos;form[name=&quot;${form.name}&quot;]&apos;).validate({errorElement:&apos;div&apos;});

  var resetPagesVisible = function() {
    var allPages = $(&apos;.eforms-page.conditionally-visible&apos;);
    var curPage = $(&apos;.eforms-page.conditionally-visible:visible:first&apos;);
    var curIndex = -1;

    for (var i = 0; i &lt; allPages.length; i++) {
      if (allPages[i].id == curPage.attr(&apos;id&apos;)) {
        curIndex = i;
      }
    }

    if (curIndex &gt; 0) {
      $(&apos;#previousPageButton&apos;).show();
    }

    if (curIndex &lt; allPages.length - 1) {
      $(&apos;#nextPageButton&apos;).show();
      $(&apos;.eforms-buttons input[type=&quot;submit&quot;]&apos;).each(function() {
        $(this).hide();
      });
    } else if (curIndex == allPages.length - 1) {
      $(&apos;#nextPageButton&apos;).hide();
      $(&apos;.eforms-buttons input[type=&quot;submit&quot;]&apos;).each(function() {
        $(this).show();
      });
    }

    $(&apos;#pagesTab li&apos;).hide();
    $(&apos;#pagesTab li.conditionally-visible&apos;).show();
  };


  <%--
    Function used to create parameters object containing form fields name/value pairs.
    The params object can be posted through ajax for validation.
    --%>
  function addFormFieldsToParameters(fields, params) {
    fields.each(function() {
      if ($(this).attr(&apos;type&apos;) == &apos;checkbox&apos;) {
        if (!params[$(this).attr(&apos;name&apos;)]) {
          var checked = $(&apos;.eforms-page.conditionally-visible:visible .eforms-field *:input[name=&quot;&apos; + $(this).attr(&apos;name&apos;) + &apos;&quot;]:checked&apos;);
          if (checked.length &gt; 0) {
            var values = [];
            checked.each(function(index) {
              values[index] = $(this).val();
            });
            params[$(this).attr(&apos;name&apos;)] = values;
          } else {
            params[$(this).attr(&apos;name&apos;)] = &apos;&apos;;
          }
        }
      } else if ($(this).attr(&apos;type&apos;) == &apos;radio&apos;) {
        if (!params[$(this).attr(&apos;name&apos;)]) {
          var checked = $(&apos;.eforms-page.conditionally-visible:visible .eforms-field *:input[name=&quot;&apos; + $(this).attr(&apos;name&apos;) + &apos;&quot;]:checked&apos;);
          if (checked.length &gt; 0) {
            params[$(this).attr(&apos;name&apos;)] = $(&apos;.eforms-page.conditionally-visible:visible .eforms-field *:input[name=&quot;&apos; + $(this).attr(&apos;name&apos;) + &apos;&quot;]:checked&apos;).val();
          } else {
            params[$(this).attr(&apos;name&apos;)] = &apos;&apos;;
          }
        }
      } else {
        params[$(this).attr(&apos;name&apos;)] = $(this).val();
      }
    });

  }



  <%-- real-time ajax-based single field validation --%>
  var fields = $(&apos;.eforms-field *:input&apos;);
  var ajaxValidationUrl = &apos;<hst:resourceURL resourceId="validation"/>&apos;;

  $(&apos;.eforms-field *:input&apos;).blur(function() {
	  
    // on leaving form field, post field name/value for ajax validation
    var params = {};
    params[$(this).attr(&apos;name&apos;)] = $(this).val();
    $.post(ajaxValidationUrl, params,
      function(data) {
        var feedbackPanel = $(&apos;#feedbackPanel&apos;);
        
        var count = 0;
        if (data) {
          for (var fieldName in data) {
            // get the error message
            var errorMessage = data[fieldName];
            // remove previous error messages from feedback panel
            var messagesList = $(&apos;#feedbackPanel &gt; ul&apos;);
            messagesList.empty();
            if (errorMessage) {
              // add error message to feedback panel
              messagesList.append(&apos;&lt;li&gt;&apos; + errorMessage.localizedMessage + &apos;&lt;/li&gt;&apos;);
              count++;
            }
          }
        }
        if (count &gt; 0) {
          // make feedback panel visible
          feedbackPanel.show();
          scrollToErrorMessage();
        } else {
          // make feedback panel invisible
          feedbackPanel.hide();
        }
      }, &quot;json&quot;);
  });


  <%-- Write JSON of field condition infos --%>
  var conditions = ${form.conditionsAsJson};
  var condFieldNames = {};

  if (conditions) {
    var items = [];
    if (conditions[&apos;fields&apos;]) {
      items = items.concat(conditions[&apos;fields&apos;]);
    }
    if (conditions[&apos;pages&apos;]) {
      items = items.concat(conditions[&apos;pages&apos;]);
    }
    for (var i = 0; i &lt; items.length; i++) {
      var item = items[i];
      var condFieldName = item[&apos;condname&apos;];
      if (!condFieldNames[condFieldName]) {
        condFieldNames[condFieldName] = true;
      }
    }
  }

  for (var condFieldName in condFieldNames) {
    var condField = $(&apos;.eforms-field *[name=&quot;&apos; + condFieldName + &apos;&quot;]&apos;);
    if (condField.length == 0) continue;
    var eventType = &apos;change&apos;;

    condField.bind(eventType, function() {
      if (conditions &amp;&amp; conditions[&apos;fields&apos;]) {
        var fields = conditions[&apos;fields&apos;];

        for (var i = 0; i &lt; fields.length; i++) {
          var field = fields[i];
          var condFieldName = field[&apos;condname&apos;];
          if ($(this).attr(&apos;name&apos;) != condFieldName) continue;

          var name = field[&apos;name&apos;];
          var targetField = $(&apos;.eforms-field *[name=&quot;&apos; + name + &apos;&quot;]&apos;);
          if (targetField.length == 0) {
              targetField = $(&apos;.eforms-fieldgroup[name=&quot;&apos; + name + &apos;&quot;]&apos;);
          }
          if (targetField.length == 0) {
              targetField = $(&apos;.eforms-text[name=&quot;&apos; + name + &apos;&quot;]&apos;);
          }
          if (targetField.length == 0) continue;

          var targetContainer = targetField.parents(&apos;.eforms-field&apos;);
          if (targetContainer.length == 0) {
              targetContainer = targetField;
          }

          var type = field[&apos;condtype&apos;];
          var condFieldValue = field[&apos;condvalue&apos;];
          var curSelectedValue = $(this).val();
          if ($(this).is(&apos;input&apos;) &amp;&amp; $(this).attr(&apos;type&apos;) == &apos;radio&apos;) {
            curSelectedValue = $(&apos;.eforms-field *[name=&quot;&apos; + condFieldName + &apos;&quot;]:radio:checked&apos;).val();
          }

          if (type == &apos;visibility&apos;) {
            if (condFieldValue == curSelectedValue) {
              targetContainer.show();
            } else {
              targetContainer.hide();
            }
          }
        }

        var pages = conditions[&apos;pages&apos;];
        for (var i = 0; i &lt; pages.length; i++) {
          var page = pages[i];
          var condFieldName = page[&apos;condname&apos;];
          if ($(this).attr(&apos;name&apos;) != condFieldName) continue;

          var pageIndex = page[&apos;index&apos;];
          var targetPage = $(&apos;#page&apos; + pageIndex);
          var type = page[&apos;condtype&apos;];
          var condFieldValue = page[&apos;condvalue&apos;];
          var curSelectedValue = $(this).val();
          if ($(this).is(&apos;input&apos;) &amp;&amp; $(this).attr(&apos;type&apos;) == &apos;radio&apos;) {
            curSelectedValue = $(&apos;.eforms-field *[name=&quot;&apos; + condFieldName + &apos;&quot;]:radio:checked&apos;).val();
          }

          if (type == &apos;visibility&apos;) {
            if (condFieldValue == curSelectedValue) {
              targetPage.addClass(&apos;conditionally-visible&apos;);
              $(&apos;#pagesTab li:nth-child(&apos; + (pageIndex + 1) + &apos;)&apos;).addClass(&apos;conditionally-visible&apos;);
            } else {
              targetPage.removeClass(&apos;conditionally-visible&apos;);
              $(&apos;#pagesTab li:nth-child(&apos; + (pageIndex + 1) + &apos;)&apos;).removeClass(&apos;conditionally-visible&apos;);
            }
            resetPagesVisible();
          }
        }
      }
    });

    condField.trigger(eventType);
  }

  <%-- In order not to show page tab for script-disabled clients, show the tabs by script if exits. --%>
  if ($(&apos;#pagesTab&apos;)) {
    $(&apos;#pagesTab&apos;).show();
  }
  <%-- Hide all the pages except of the first page --%>
  $(&apos;.eforms-page&apos;).each(function() {
    $(this).hide();
  });
  if ($(&apos;.eforms-page.conditionally-visible&apos;).length) {
    $(&apos;.eforms-page.conditionally-visible:first&apos;).show();
  }

  resetPagesVisible();

  $(&apos;#previousPageButton&apos;).click(function() {
    var curPage = $(&apos;.eforms-page.conditionally-visible:visible&apos;);
    var prevPage = curPage.prevAll(&apos;.eforms-page.conditionally-visible:first&apos;);
    prevPage.show();
    curPage.hide();

    var curIndex = parseInt(curPage.attr(&apos;id&apos;).replace(/^page/, &apos;&apos;));
    var prevIndex = parseInt(prevPage.attr(&apos;id&apos;).replace(/^page/, &apos;&apos;));
    $(&apos;#pagesTab li:nth-child(&apos; + (curIndex + 1) + &apos;)&apos;).removeClass(&apos;selected&apos;);
    $(&apos;#pagesTab li:nth-child(&apos; + (prevIndex + 1) + &apos;)&apos;).addClass(&apos;selected&apos;);

    if (prevPage.prevAll(&apos;.eforms-page.conditionally-visible:first&apos;).length == 0) {
      $(&apos;#previousPageButton&apos;).hide();
    }
    $(&apos;#nextPageButton&apos;).show();
    $(&apos;.eforms-buttons input[type=&quot;submit&quot;]&apos;).each(function() {
        $(this).hide();
      });

    // remove error messages from feedback panel
    var messagesList = $(&apos;#feedbackPanel &gt; ul&apos;);
    messagesList.empty();

    // hide feedbackPanel
    var feedbackPanel = $(&apos;#feedbackPanel&apos;);
    feedbackPanel.hide();

  });

  $(&apos;#nextPageButton&apos;).click(function() {
    var curPage = $(&apos;.eforms-page.conditionally-visible:visible&apos;);

    // ajax based validation
    // validate all fields on current page before going to the next
    var params = {};
    var fieldsOnPage = $(&apos;.eforms-page.conditionally-visible:visible .eforms-field *:input&apos;);
    addFormFieldsToParameters(fieldsOnPage, params);

    // add an empty parameter for any group on the current page
    var groupsOnPage = $(&apos;.eforms-page.conditionally-visible:visible .eforms-fieldgroup&apos;);
    groupsOnPage.each(function() {
      params[$(this).attr(&apos;name&apos;)] = &apos;&apos;;
    });

    // add current page index to parameters
    params[&apos;currentPage&apos;] = curPage.attr(&apos;id&apos;).replace(/^page/, &apos;&apos;);

    $.post(ajaxValidationUrl, params,
      function(data){
    	
        // remove previous error messages from feedback panel
        var messagesList = $(&apos;#feedbackPanel &gt; ul&apos;);
        messagesList.empty();

        var count = 0;
        if (data) {
          for (var fieldName in data) {
            // get the error message
            var errorMessage = data[fieldName];
            if (errorMessage) {
              // add error message to feedback panel
              messagesList.append(&apos;&lt;li&gt;&apos; + errorMessage.localizedMessage + &apos;&lt;/li&gt;&apos;);
              count++;
            }
          }
        }
        var feedbackPanel = $(&apos;#feedbackPanel&apos;);
        if (count &gt; 0) {
            // there are validation errors
            // make feedback panel visible
            feedbackPanel.show();
            scrollToErrorMessage();
        } else {
          // no error messages
          // make feedback panel invisible
          feedbackPanel.hide();

          // go to the next page
          var nextPage = curPage.nextAll(&apos;.eforms-page.conditionally-visible:first&apos;);
          nextPage.show();
          curPage.hide();

          var curIndex = parseInt(curPage.attr(&apos;id&apos;).replace(/^page/, &apos;&apos;));
          var nextIndex = parseInt(nextPage.attr(&apos;id&apos;).replace(/^page/, &apos;&apos;));
          $(&apos;#pagesTab li:nth-child(&apos; + (curIndex + 1) + &apos;)&apos;).removeClass(&apos;selected&apos;);
          $(&apos;#pagesTab li:nth-child(&apos; + (nextIndex + 1) + &apos;)&apos;).addClass(&apos;selected&apos;);

          $(&apos;#previousPageButton&apos;).show();
          if (nextPage.nextAll(&apos;.eforms-page.conditionally-visible:first&apos;).length == 0) {
            $(&apos;#nextPageButton&apos;).hide();
            $(&apos;.eforms-buttons input[type=&quot;submit&quot;]&apos;).each(function() {
              $(this).show();
            });
          }

        }

      }, &quot;json&quot;);


  });


  var valid = false;

  // ajax page validation in case of last (or only) page
  $(&apos;form[name=&quot;${form.name}&quot;]&apos;).submit(function(event) {

    var curPage = $(&apos;.eforms-page.conditionally-visible:visible&apos;);

    // if valid flag is set, page was validated and form can be submitted
    if (valid) {
        return true;
    }

    var params = {};
    var fieldsOnPage = $(&apos;.eforms-page.conditionally-visible:visible .eforms-field:visible *:input&apos;);
    addFormFieldsToParameters(fieldsOnPage, params);

    // add an empty parameter for any visible group on the current page
    var groupsOnPage = $(&apos;.eforms-page.conditionally-visible:visible .eforms-fieldgroup:visible&apos;);
    groupsOnPage.each(function() {
        params[$(this).attr(&apos;name&apos;)] = &apos;&apos;;
    });

    // add current page index to parameters
    params[&apos;currentPage&apos;] = curPage.attr(&apos;id&apos;).replace(/^page/, &apos;&apos;);

    // prevent form submission as we want to do ajax validation first
    event.preventDefault();

    $.post(ajaxValidationUrl, params,
      function(data){

        // remove previous error messages from feedback panel
        var messagesList = $(&apos;#feedbackPanel &gt; ul&apos;);
        messagesList.empty();

        var count = 0;
        if (data) {
          for (var fieldName in data) {
            // get the error message
            var errorMessage = data[fieldName];
            if (errorMessage) {
              // add error message to feedback panel
              messagesList.append(&apos;&lt;li&gt;&apos; + errorMessage.localizedMessage + &apos;&lt;/li&gt;&apos;);
              count++;
            }
          }
        }
        var feedbackPanel = $(&apos;#feedbackPanel&apos;);
        if (count &gt; 0) {
            // there are validation errors
            // make feedback panel visible
            feedbackPanel.show();
            scrollToErrorMessage();

        } else {
          // no error messages
          // make feedback panel invisible
          feedbackPanel.hide();

          // set valid flag and resubmit form
          valid = true;
          $(&apos;form[name=&quot;${form.name}&quot;] input:submit&apos;).click();
        }

      }, &quot;json&quot;);

  });
  
});
function scrollToErrorMessage(){
  scrollTo(&apos;KO&apos;);
  scrollTo(&apos;feedbackPanel&apos;);
  function scrollTo(hash) {
      location.hash = &quot;#&quot; + hash;
  }
}
</script>
</hst:headContribution>
</c:if>