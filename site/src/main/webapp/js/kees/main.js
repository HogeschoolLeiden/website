
$( document ).ready(function() {
	
	$(document).delegate('*[data-toggle="lightbox"]', 'click', function(event) { 		event.preventDefault();
		$(this).ekkoLightbox(); 
	});

	$(".validate").validate({
		errorElement:'span',
		rules: {
			email: {
				required:true,
				email:true
			}
		}
	});
	jQuery.extend(jQuery.validator.messages, {
	    required: "Verplicht",
	    email: "Incorrect"
	});
	
	// dateCircleHeight ();
// 	
	// function dateCircleHeight () {
		// var datepickerWidth = $(".ui-datepicker-calendar tr td span, .ui-datepicker-calendar tr td a").outerWidth();
	  	// var datepickerHeight = datepickerWidth*0.75;
// 	  
	  	// $(".ui-datepicker-calendar tr td span, .ui-datepicker-calendar tr td a").height(datepickerHeight);
	// }
// 		
	// $(window).resize(function() {
		// dateCircleHeight ();
	// });


});