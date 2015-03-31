var doc = document.documentElement;
doc.setAttribute('data-useragent', navigator.userAgent);

$(document).ready(function() {

	/* help button */
	$(".hulp").click(function() {
		if ($(this).hasClass("open")) {
			$(".hulp").animate({
				"right" : "-=170px"
			}, "fast");
			$(".hulp").removeClass("open");
		} else {
			$(".hulp").animate({
				"right" : "+=170px"
			}, "fast");
			$(".hulp").addClass("open");
		}
	});

	manageCookies();

	hideFilterButton();

	hideFilteringColumn();

	expandOrCollapseFaq();

	renderMail();

});

function renderMail() {
	var mlink = jQuery(".nospam");
	var hlink = jQuery(".helpsms");

	if (mlink) {
		var n = mlink.attr('data-n');
		var d = mlink.attr('data-d');
		var s = mlink.attr('data-s');
		generateMailToLink(n, d, s, mlink);
	}

	if (hlink) {
		var n = hlink.attr('data-n');
		var d = hlink.attr('data-d');
		generateHelpMailToLink(n, d, hlink);
	}
}

function generateHelpMailToLink(name, domain, mlink) {
	if (name) {
		if (domain) {

			var aElem = "<a href=\"mailto:" + name + "@" + domain + "\">"
					+ name + "@" + domain + "<\/a>";

			mlink.parent().append(aElem);
			mlink.remove();
		}
	}
}

function generateMailToLink(name, domain, subject, mlink) {
	if (name) {
		if (domain) {

			var aElem = "<a href=\"mailto:" + name + "@" + domain;
			if (subject) {
				aElem = aElem + "?subject=" + subject + "\">" + name + "@"
						+ domain + "<\/a>";
			} else {
				aElem = aElem + "\">" + name + "@" + domain + "<\/a>";
			}
			mlink.children().first().remove();
			mlink.append(aElem);
		}
	}
}

function manageCookies() {
	if (getCookie('allowCookies') || getCookie('refuseCookies')) {
		$("#row-disclaimer").addClass("hidden");
	}
}

function setCookieAllowRemoveMessage() {
	$("#row-disclaimer").addClass("hidden");
	addCookie('allowCookies', true, 365);
}

function setCookieRefuseRemoveMessage() {
	$("#row-disclaimer").addClass("hidden");
	addCookie('refuseCookies', true, 7);
}

function addCookie(name, value, days) {
	var expires;
	if (days) {
		var date = new Date();
		date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
		expires = "; expires=" + date.toGMTString();
	} else {
		expires = "";
	}
	document.cookie = name + "=" + value + expires + "; path=/";
}

function getCookie(c_name) {
	if (document.cookie.length > 0) {
		var c_start = document.cookie.indexOf(c_name + "=");
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1;
			var c_end = document.cookie.indexOf(";", c_start);
			if (c_end == -1) {
				c_end = document.cookie.length;
			}
			return unescape(document.cookie.substring(c_start, c_end));
		}
	}
	return "";
}

/**
 * invoked from the header button for switching the display of the filtering on
 * and off.
 * 
 * In mobile version the filtering column is never displayed, so every click on
 * the button triggers the addition/removal of "hidden" from the css class of
 * the filtering column
 * 
 * In tablet version the filtering is displayed for every overview page, but not
 * for the deep linking detail pages.
 */
function switchFiltering() {
	if (window.innerWidth < 768) {
		var filteringColumn = jQuery(".algemeenfilter");
		var studieFilter = jQuery(".studiefilter");

		hideOrShow(filteringColumn);
		hideOrShow(studieFilter);
	} else {
		if (window.innerWidth < 992) {
			var deepLinkingFilter = jQuery(".deepLinking");
			hideOrShow(deepLinkingFilter);
		}
	}
}

function expandOrCollapseFaq() {
	jQuery(".expOrColl").click(function() {
		hideOrShow($(this).parent().next());
		changeImage($(this));
	});
}

function changeImage(link) {

	var img = link.children().first();
	var imgSrcRight = contextPath + '/images/faq-arrow-right.png'
	var imgSrcDown = contextPath + '/images/faq-arrow-down.png'

	if ((img.attr("src")).match(/right.png$/)) {
		$(img).attr("src", imgSrcDown);
		$(img).attr("alt", 'collapse');
		$(img).attr("title", 'collapse');
		$(link).attr("title", 'collapse');
	} else {
		$(img).attr("src", imgSrcRight);
		$(img).attr("alt", 'expand');
		$(img).attr("title", 'expand');
		$(link).attr("title", 'expand');
	}
}

function hideOrShow(section) {
	if (section.hasClass("hidden")) {
		section.removeClass("hidden");
	} else {
		section.addClass("hidden");
	}
}

/**
 * hide filtering section (left column) for mobile for tablets, the filtering
 * section is displayed for overview pages and is hidden only for deep linking
 * detail pages
 */

function hideFilteringColumn() {
	if (window.innerWidth < 768) {
		var leftColumn = jQuery(".algemeenfilter");
		leftColumn.addClass("hidden");
	} else {
		if (window.innerWidth < 992) {
			var leftColumn = jQuery(".deepLinking");
			leftColumn.addClass("hidden");
		}
	}
}

/**
 * hide filtering button from the header if there is no filtering to be applied
 * 
 * it tablet view (768 - 991) filtering can be applied only for deep linking
 * detail pages, because the overview pages display it already.
 */
function hideFilterButton() {
	if (window.innerWidth < 768) {

		var filterButton = jQuery(".toggle-filter-button");
		var filterColumnPresence = jQuery(".algemeenfilter").length;
		var studieFilterPresence = jQuery(".studiefilter").length;

		if (filterColumnPresence + studieFilterPresence >= 1) {
			if (filterColumnPresence >= 1) {
				manageNormalFiltering(filterButton)
			} else if (studieFilterPresence >= 1) {
				manageBachelorsFiltering(filterButton)
			}
		} else {
			if (!filterButton.hasClass("hidden")) {
				filterButton.addClass("hidden");
			}
		}
	} else if (window.innerWidth < 992) {

		var filterButton = jQuery(".toggle-filter-button");
		var deepLinkingColumnPresence = jQuery(".deepLinking").length;

		if (deepLinkingColumnPresence <= 0) {
			filterButton.addClass("hidden");
		}
	}
}

function manageBachelorsFiltering(filterButton) {
	if (jQuery(".noQueryResults").length > 0) {
		filterButton.addClass("hidden");
	}
}

function manageNormalFiltering(filterButton) {
	var doNotDisplay = false;
	if (jQuery("[class='filtergroup col-md-3']").length <= 0) {
		var firstDiv = jQuery(".algemeenfilter > div");
		if (firstDiv.height() <= 0) {
			var doNotDisplay = true;
		}
	}

	if (doNotDisplay) {
		filterButton.addClass("hidden");
	}
}
