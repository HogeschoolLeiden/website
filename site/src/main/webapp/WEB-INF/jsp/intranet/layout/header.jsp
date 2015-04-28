<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<span style="display:none;" id="userUID" class="userUID">${userId}</span> 
<%-- <span style="display:none;" id="userUID" class="userUID"></span> --%>

<header class="bar">
    <div class="wrapper" id="wrapper-logo" >
        <hst:link var="homeUrl" path="/" />
        <a href="${homeUrl}">
            <img src="<hst:link path="/img/intranet/logo.png"/>" class="logo" />
        </a>
        <div class="popup-menu">
	        <div class="digital">
	        	<h2>HL Digitaal</h2>
		        <ul>
		        	<hst:link var="spriteBackground" path="/img/intranet/apps_sprite.png"></hst:link>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 0)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 3)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 6)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 2)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 9)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 10)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 7)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 4)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 5)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 1)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 11)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 14)}px 95px;"></a></li>
		  
		        </ul>
	        </div>
	        <div class="external">
	        	<h2>Extern</h2>
		        <ul>
		        	<hst:link var="spriteBackground" path="/img/intranet/apps_sprite.png"></hst:link>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 8)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 12)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 15)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 13)}px 95px;"></a></li>
		        	<li><a href="/" style="background: url('${spriteBackground}') -${67 + (70 * 17)}px 95px;"></a></li>
		        </ul>
	        </div>
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

