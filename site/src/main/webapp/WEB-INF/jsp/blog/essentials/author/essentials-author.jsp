<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%--
  Copyright 2014 Hippo B.V. (http://www.onehippo.com)

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  --%>

<%--@elvariable id="document" type="nl.hsleiden.beans.Author"--%>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.blog.Messages"/>

<div class="panel panel-default">
  
  <div class="panel-heading">
    <h3 class="panel-title"><fmt:message key="more.about"/><c:out value="${model.author.fullName}"/></h3>
  </div>
  
  <div class="panel-body">
    <c:if test="${not empty model.author.image }">
      <div class="pull-left">
        <hst:link var="image" hippobean="${model.author.image.listImageMedium}"/>
        <img class="media-object" src="${image}" alt="${model.author.fullName}">
      </div>
    </c:if>
    
    <div class="media-body">
      <h3 class="media-heading">
        <c:out value="${model.author.fullName}"/>
      </h3>
      <p><i><c:out value="${model.author.role}"/></i></p>
      
      <c:if test="${fn:length(model.author.accounts)>0}">
        <tag:socialaccounts document="${model.author}"/>
      </c:if>
    
    </div>

    <hst:html hippohtml="${model.author.content}"></hst:html>
    
    
        
  </div>
</div>

