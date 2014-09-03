<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<c:choose>
  <c:when test="${empty document}">
    <tag:pagenotfound />
  </c:when>
  <c:otherwise>

      <div itemscope itemtype="http://data-vocabulary.org/Event">

        <article class="col-md-9 detail-content">
          <hst:cmseditlink hippobean="${document}" />

          <div class="row">
            <div class="col-md-8 col-sm-8 contentarea">
              <div class="content">

                <h1><span itemprop="summary"><c:out value="${document.title }" /></span></h1>
                
                <%-- 
                <h2>
                  <c:out value="${document.subtitle }" escapeXml="true" />
                </h2>
                 --%>
                 
                <c:if test="${hst:isReadable(document, 'eventDate.time')}">
                  <fmt:formatDate value="${document.releaseDate.time}"
                        type="date" pattern="dd" dateStyle="medium"
                        timeStyle="short" var="day" />
                  <fmt:formatDate value="${document.eventDate.time}"
                        type="date" pattern="MMM" dateStyle="medium"
                        timeStyle="short" var="month" />

                  <time datetime="${time}" class="datum">${fn:escapeXml(day)}<span>${fn:escapeXml(month)}</span></time>
                </c:if>

                <p class="intro">
                  <c:out value="${document.introduction }" />
                </p>

                <tag:flexibleblock content="${document.flexibleblock }" />

                <tag:toolbox document="${document }" />

              </div>
            </div>
          </div>

        </article>

      </div>

  </c:otherwise>
</c:choose>
