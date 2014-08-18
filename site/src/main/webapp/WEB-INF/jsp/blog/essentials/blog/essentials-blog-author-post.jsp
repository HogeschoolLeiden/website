<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
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

<%--@elvariable id="item" type="nl.hsleiden.beans.Blogpost"--%>
<%--@elvariable id="author" type="nl.hsleiden.beans.Author"--%>
<%--@elvariable id="pageable" type="org.onehippo.cms7.essentials.components.paging.Pageable"--%>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.blog.Messages"/>

<div class="other blogs author">
<div class="panel panel-default">
  <c:if test="${(pageable ne null)}">
    <div class="panel-heading">
      <h3 class="panel-title"><fmt:message key="more.by"/> ${author.fullName}</h3>
    </div>
    <c:choose>
      <c:when test="${pageable.total gt 0}">
        <div class="panel-body">
          <c:forEach var="item" items="${pageable.items}" varStatus="status">
            <hst:link var="link" hippobean="${item}"/>
            <p><a href="${link}"><c:out value="${item.title}"/></a></p>
          </c:forEach>
        </div>
      </c:when>
      <c:otherwise>
        <div class="panel-body">
          <p><fmt:message key="no.other.blogs"/> </p>
        </div>
      </c:otherwise>
    </c:choose>
  </c:if>
</div>
</div>