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

<%--@elvariable id="document" type="nl.hsleiden.beans.Blogpost"--%>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.blog.Messages"/>

<hst:cmseditlink hippobean="${document}" />
<h1> <c:out value="${document.title }" escapeXml="true" /> </h1>
<h2><fmt:message key="before.author.name"/> <c:out value="${document.blogAuthor}"/></h2>

<c:if test="${hst:isReadable(document, 'eventDate.time')}">
  <tag:renderDate document="${document}"/>
</c:if>

<p class="intro">
  <c:out value="${document.introduction }" />
</p>
          
<%-- make blog a webpage, add metadata fields, breadrumb, date this time also with year 

<strong>
  <c:if test="${document.publicationDate ne null}">
    <fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${document.publicationDate.time}"/>
  </c:if>
</strong>
<p><c:out value="${document.introduction}"/></p> --%>

<tag:flexibleblock content="${document.flexibleblock }" />
<tag:toolbox document="${document }" /> 
        

