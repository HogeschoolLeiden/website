<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<header class="bar">
    <div class="wrapper" id="wrapper-logo" >
        <hst:link var="homeUrl" path="/" />
        <span id="popup">
            <img src="<hst:link path="/img/intranet/logo.png"/>" class="logo" />
        </span>
        <div id="popup-menu">
          <hst:link var="spriteBackground" path="/img/intranet/apps_sprite.png"></hst:link>
          <c:forEach items="${appbar.siteMenuItems}" varStatus="status" var="menuItem">
          <div class="${status.index eq 0 ? 'digital' : 'external' }">
            <h2><c:out value="${menuItem.name}"/></h2>
            <ul>
              <c:forEach items="${menuItem.childMenuItems}" var="subitem">
                <c:choose>
                  <c:when test="${not empty myMenuItem.hstLink }">
                    <hst:link var="menuLink" link="${subitem.hstLink}" />
                    <c:set var="ext" value="false"/>
                  </c:when>
                  <c:otherwise>
                    <c:set var="menuLink" value="${subitem.externalLink}" />
                    <c:set var="ext" value="true"/>
                  </c:otherwise>
                </c:choose>
                <li>
                  <a href="${menuLink}" style="background: url('${spriteBackground}') -${67 + (70 * subitem.parameters['imageIndex'])}px 95px;" title="${fn:escapeXml(subitem.name)}" ${ext ? 'target="_blank"' : '' }></a>
                </li>
              </c:forEach>
            </ul>
          </div>
          </c:forEach>
    	</div>
    </div>
</header>

<header>
    <div class="wrapper clearfix" style="margin-top: 65px;">
        <section class="page-title">
            <span class="title-header">Praktische informatie</span>
        </section>
    </div>
    <section id="search" class="search">
        <hst:link var="searchUrl" path="/zoeken" />
        <form class="search-form" action="${searchUrl}" method="GET">
            <input type="search" class="search-input" name="query" placeholder="Zoeken in praktische informatie">
            <img class="search-magnify" src="<hst:link path="/img/intranet/search.png"/>"search.png">
        </form>
    </section>
</header>


<div class="wrapper clearfix">
    <section class="breadcrums">
		    
        <c:choose>
            <c:when test="${fn:length(breadcrumb.items) gt 1}">

                        <ul class="breadcrums clearfix">

			                <c:forEach var="item" items="${breadcrumb.items}" varStatus="status">
			                    <li>
			                      <c:choose>
			                            <c:when test="${status.last}">
			                                <span>${item.title}</span>
			                            </c:when>
			                            <c:otherwise>
			                                <hst:link var="itemUrl" path="${item.relativeUrl}" />
			                                <a href="${itemUrl}">${item.title}</a>
			                            </c:otherwise>
			                        </c:choose>
			                    </li>
			                </c:forEach>
			                
                        </ul>
                       
            </c:when>
            <c:otherwise>
		         
		              <ul class="breadcrums clearfix">
		                  <li>&nbsp;</li>
		              </ul>
		              
            </c:otherwise>
        </c:choose>

    </section>
</div>

