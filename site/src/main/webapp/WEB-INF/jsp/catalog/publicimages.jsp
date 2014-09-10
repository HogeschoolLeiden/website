<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.widget.Messages"/>

<c:if test="${not empty model.items and fn:length(model.items) > 0 }">
  <hst:headContribution keyHint="ppinit">
    <script type="text/javascript" src="<hst:link path="/js/pretty-photo-inizialization.js"/>" charset="utf-8"></script>
  </hst:headContribution>

  <hst:headContribution keyHint="jquery">
    <script type="text/javascript" src="<hst:link path="/js/jquery-1.9.1.min.js"/>" charset="utf-8"></script>
  </hst:headContribution>
  
  <hst:headContribution keyHint="jqueryUICss">
    <link rel="stylesheet" href="<hst:link path="/css/prettyPhoto.css"/>" type="text/css" media="screen" />
  </hst:headContribution>
  
  <hst:headContribution keyHint="prettyPhoto">
    <script type="text/javascript" src="<hst:link path="/js/jquery.prettyPhoto.js"/>" charset="utf-8"></script>
  </hst:headContribution>

<div class="container">
<div class="row">
<article class="col-md-9 detail-content">  
  <div class="images${model.info.imagesPerRow}">
  
    <c:if test="${not empty model.hasParentFolder and model.hasParentFolder }">
        <hst:renderURL var="parentFolderUrl">
          <hst:param name="folder" value="${model.parentFolder}"/>
          <hst:param name="page" value="1"/>
        </hst:renderURL>
        <a class="backToParentFolder" href="${parentFolderUrl }" title="<fmt:message key="back.parent.images.folder" />">
          <span><fmt:message key="back.parent.images.folder" /></span>
        </a>
    </c:if>
    
    <c:forEach items="${model.items}" var="image" varStatus="loop">
      
      <div class="singleImage">
        <div class="singleImagePadding">

          <figure class="fexibleblock image">
          
            <c:choose>
              <c:when test="${fn:contains(image.name, '/') }">
                
                <hst:renderURL var="url">
                  <hst:param name="folder" value="${image.name}"/>
                </hst:renderURL>
                
                <hst:link fullyQualified="true" hippobean="${model.imageFolderBeanPath.wideImage }" var="folderImageLink"></hst:link>
                     
                <a href="${url }" title="${image.localizedName}">
                    <img src="${folderImageLink}"
                         alt="${fn:escapeXml(image.localizedName)}"
                         title="${fn:escapeXml(image.localizedName)}" />
                </a> 

                <span class="images subfolder">
                  <c:out value="${image.localizedName}"></c:out>
                </span> 
              </c:when>
              <c:otherwise>
                 <hst:link fullyQualified="true" hippobean="${image.wideImage }" var="imageLink"></hst:link>
                 
                 <a href="${imageLink }" data-atr="prettyPhoto" class="pin-it-button">
                   <img  src="${imageLink}" alt="${fn:escapeXml(image.alt) }" title="${fn:escapeXml(image.alt) }"/>
                 </a>
              </c:otherwise>
            </c:choose>
                      
          </figure>
       
        </div>        
      </div>
      
    </c:forEach>
        
  </div>
   
  <div class="pager-wrapper">
      <c:choose>
        <c:when test="${tag:hasParameter(pageContext.request, 'folder') }">
          <tag:publicImagesPaginator paginator="${model.paginator}" folder="${tag:getParameter(pageContext.request, 'folder')}"/>
        </c:when>
        <c:otherwise>
          <tag:publicImagesPaginator paginator="${model.paginator}"/>
        </c:otherwise>
      </c:choose>
  </div>

  </article>
</div>
</div>
  
</c:if>