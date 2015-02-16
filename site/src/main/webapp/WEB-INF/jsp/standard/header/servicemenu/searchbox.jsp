<%@ page contentType="text/html; charset=UTF-8" language="java"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<hst:setBundle basename="nl.hsleiden.general.Messages" />


<hst:resourceURL var="resourceUrl"/>
<hst:link path="/js/typeahead.bundle.js" var="typeaheadLink" />
<hst:headContribution category="scripts" keyHint="typeahead.bundle">
	<script type="text/javascript" src="${typeaheadLink}"></script>
</hst:headContribution>

<hst:headContribution category="scripts" keyHint="typeahead.config">
	<script type="text/javascript">
			
			
			$('.typeahead').typeahead({
			  hint: true,
			  highlight: true,
			  minLength: 1
			},
			{
			  name: 'Search',
			  displayKey: 'value',
			  source: function(q, cb) {
				    var matches, substrRegex;
					 
				    matches = [];
				    var url = '${resourceUrl}';
				    if (url.indexOf('?') > 0) {
						url = url.substring(0, url.indexOf('?')) + '?q=' + q;
				    } else {
				    	url = url + '?q=' + q;
				    }
				    
				 	var strs;
					$.ajax({
						  dataType: "json",
						  url: url,
						  async: false,
						  success: function(data) {
							  strs = data;
						  }
					});
				    substrRegex = new RegExp(q, 'i');
				 
				    $.each(strs, function(i, str) {
				      if (substrRegex.test(str)) {
				        matches.push({ value: str });
				      }
				    });
				 
				    cb(matches);
				  }
			});
	</script>
</hst:headContribution>


<fmt:message var="submitText" key="search.submit.text" />
<hst:link var="link" siteMapItemRefId="search" mount="hsl"/>
<form role="form" class="navbar-form" action="${link}" method="get">
  <div class="form-group">
	<input type="text" name="q"
		   class="search-query input-xlarge typeahead form-control"
		   placeholder="${fn:escapeXml(submitText)}" required="required"
		   ${not empty model.query? 'value=\"' : ''} ${model.query} ${not empty model.query? '\"' : ''} />
  </div>
  <button class="btn fa fa-search no-radius btn-primary inline" type="submit" value="${submitText}"> </button>
</form>
