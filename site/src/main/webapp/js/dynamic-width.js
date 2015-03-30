
/*sets the width of the div with class ("fb-post") to 100% of its parent width (class "catalog facebook posts") */
jQuery(document).ready(function(){
	
	var parentWidth = jQuery(".catalog.facebook.posts").width();
	
	jQuery('.fb-post').each(function(){
		jQuery(this).attr("data-width", parentWidth);
	});
});
