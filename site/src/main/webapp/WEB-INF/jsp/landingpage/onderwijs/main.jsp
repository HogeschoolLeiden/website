<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<div class="container-fluid">
  <div class="row-fluid">
    <div class="header span8">
      
      <hst:include ref="contentTop" />
      
      <hst:cmseditlink hippobean="${document}" />
      
      <div class="fourFourth">
        <h1><c:out value="${document.title}"/></h1>
        <hst:include ref="content" />
      </div>
      
      <hst:include ref="contentBottom" />
      
      <tag:toolbox document="${document }"/>
    </div>
  </div>
</div>
