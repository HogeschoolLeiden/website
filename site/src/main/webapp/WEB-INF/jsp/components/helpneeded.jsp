<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.general.Messages, nl.hsleiden.widget.Messages"/>
      
<div class="hulp">
  <h2><c:out value="${document.title}"/></h2>
  <ul>
    <c:forEach var="item" items="${document.helpLines }">
      <li class="${item.icon.firstItem.key }">
        <c:choose>
        
          <%-- External link case --%>
          <c:when test="${not empty item.externallink.linkTitle and not empty item.externallink.linkUrl}">
          
            <c:set var="alt">
              <c:choose>
                <c:when test="${not empty item.externallink.alt }">
                  <c:out value="${item.externallink.alt}"></c:out>
                </c:when>
                <c:otherwise>
                  <c:out value="${item.externallink.linkTitle}"></c:out>
                </c:otherwise>
              </c:choose>
            </c:set>
            
            <c:choose>
              <c:when test="${item.icon.firstItem.key eq 'fa fa-phone' }">
                <a href="#" title="${alt}" ${item.externallink.newWindow ? 'class="external link" target="_blank"': '' }>      
                   <c:out value="${item.externallink.linkTitle }" escapeXml="true" />
                </a>
              </c:when>
              <c:otherwise>
                <a href="${item.externallink.linkUrl}" title="${alt}" ${item.externallink.newWindow ? 'class="external link" target="_blank"': '' }>      
                   <c:out value="${item.externallink.linkTitle }" escapeXml="true" />
                </a>
              </c:otherwise>
            </c:choose>
          </c:when>

          <%-- Internal link case --%>
          <c:otherwise>
            <c:set var="link"><hst:link hippobean="${item.internallink.link }" /></c:set>
            <a href="${link}" title="${item.internallink.alt}">
                 <c:out value="${item.internallink.linkTitle }" escapeXml="true" />
            </a>
          </c:otherwise>
  
        </c:choose>
      </li>
    </c:forEach>
  </ul>
</div>

