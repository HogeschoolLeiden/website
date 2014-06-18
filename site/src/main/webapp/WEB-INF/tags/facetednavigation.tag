<%@ tag language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="facetnav" required="true" type="org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean"%>
<%@ attribute name="labels" required="false" type="java.util.Map"%>

<%-- <properties:property var="facetnavtitle" name="facet.navigation.title" documentPath="labels"/> --%>
<c:set var="facetnavtitle" value="refine results:"/>

<div id="searchresult-category-container">
	<c:if test="${facetnav.count gt 0}">
		<div id="facets">
			<h4><c:out value="${facetnavtitle}" escapeXml="true"/></h4>
			<div class="form">
				<c:forEach var="facet" items="${facetnav.folders}">
					<c:if test="${facet.resultSet.count > 0}">
						<div class="fieldset">
							<c:choose>
								<c:when test="${facet.leaf}">
									<h5 class="facetName">
										<c:out value="${facet.name}" escapeXml="true" />
									</h5>
								</c:when>
								<c:otherwise>
									<h5 class="facetName">
										<c:out value="${facet.name}" escapeXml="true" />
									</h5>
									<c:if test="${not empty facet.folders}">
										<c:forEach items="${facet.folders}" var="item">
											<c:choose>
												<c:when test="${item.leaf and item.count gt 0}">
													<hst:facetnavigationlink remove="${item}" current="${facetnav}" var="removeLink" />
													<div class="input selected">
														<span> 
															<c:out value="${labels[item.name]}" default="${item.name}" escapeXml="true" />
														</span> 
														<a href="${removeLink}" class="removeLink"></a>
													</div>
												</c:when>
												<c:when test="${item.leaf and item.count eq 0}">
												</c:when>
												<c:otherwise>
													<hst:link var="link" hippobean="${item}" navigationStateful="true" />
													<div class="input">
														<c:choose>
															<c:when test="${query eq null}">
																<a href="${link }"> 
																	<c:out value="${labels[item.name]}" default="${item.name}" escapeXml="true" />
																</a>
                            	  								(${item.count })
                          									</c:when>
															<c:otherwise>
																<a href="${link }"> 
																	<c:out value="${labels[item.name]}" default="${item.name}" escapeXml="true" />
																</a>
						                            	  		(${item.count })
						                          			</c:otherwise>
														</c:choose>
													</div>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:if>
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</c:if>
</div>
