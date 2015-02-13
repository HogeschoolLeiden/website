<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>


<div id="breadcrumbs" itemscope itemtype="http://data-vocabulary.org/Breadcrumb">
  <div class="container">
    <div class="row">
      <div class="col-md-9 col-md-offset-3">

        <c:if test="${not(pageContext.request.requestContext.contentBean['class'].name == 'hslbeans.HomePage' 
                   or pageContext.request.requestContext.contentBean['class'].name == 'hslbeans.Lectorat')}">
          <ol class="breadcrumb">
            <hst:link path="/" var="home" mount="hsl" />
            <li>
              <a itemprop="url" href="${home }"> 
                <span itemprop="title"><c:out value="Home " /></span>
              </a>
            </li>

            <c:forEach var="item" items="${model}" varStatus="status">
              <li>
                <c:choose>
                  <c:when test="${status.last}">
                      <span itemprop="title"><c:out value="${item.name}" /></span>
                  </c:when>
                  <c:otherwise>
                    <a itemprop="url" href="<hst:link link="${item.link}"/>"> 
                      <span itemprop="title"><c:out value="${item.name}" /></span>
                    </a>
                  </c:otherwise>
                </c:choose>
              </li>
            </c:forEach>
          </ol>
        </c:if>

      </div>
    </div>
  </div>
</div>