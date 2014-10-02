var doc = document.documentElement;
doc.setAttribute('data-useragent', navigator.userAgent);

$( document ).ready(function() {
	
	/* SWIPER SLIDER */	
	if ($(".swiper-container").length > 0){
	 $(window).bind('orientationchange', function(event) {
	 	window.location.reload(false);
	 });
	}
	
	if ($(".swiper-container").length > 0){
		
		var mySwiper = $('.swiper-container').swiper({
		    resizeReInit: true,
		    mode:'horizontal',
		    loop: true,
		    simulateTouch: true,
		    centeredSlides: true,
		    pagination: ".slider-pagination",
		    createPagination: true,
		    paginationClickable: true,
		    progress:true,
		    onProgressChange: function(swiper){
		        for (var i = 0; i < swiper.slides.length; i++){
		          var slide = swiper.slides[i];
		          var progress = slide.progress;
		          var translate = progress*swiper.width;  
		          var opacity = 1 - Math.min(Math.abs(progress),1);
		          slide.style.opacity = opacity;
		          swiper.setTransform(slide,'translate3d('+translate+'px,0,0)');
		     	}
		    },
		    onTouchStart:function(swiper){
		    for (var i = 0; i < swiper.slides.length; i++){
		      swiper.setTransition(swiper.slides[i], 0);
		    }
		  },
		  onSetWrapperTransition: function(swiper, speed) {
		    for (var i = 0; i < swiper.slides.length; i++){
		      swiper.setTransition(swiper.slides[i], speed);
		    }
		  }
		  });
		  
	    $('.swipenav.prev').on('click', function(e){
			e.preventDefault();
			mySwiper.swipePrev();
		});
		
		$('.swipenav.next').on('click', function(e){
			e.preventDefault();
			mySwiper.swipeNext();
		}) ;	
	
	}
	/* // SWIPER SLIDER */	
	
	/* lightbox */
	$(document).delegate('*[data-toggle="lightbox"]', 'click', function(event) { 		event.preventDefault();
		$(this).ekkoLightbox(); 
	});
	/* // lightbox */

	/* form validation */
	if ($(".validate").length > 0){
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
	}
	/* // form validation */
	
	/* open share in new window */
	$('.share a').click(function(){
		window.open($(this).attr('href'),'title', 'width=800, height=700');
        return false;
	});
	/* // open share in new window */
	
	/* help button */
	$(".hulp").click(function() {
		if( $(this).hasClass("open")) {
			$( ".hulp" ).animate({ "right": "-=170px" }, "fast" );
			$( ".hulp" ).removeClass("open");
		} else {
			$( ".hulp" ).animate({ "right": "+=170px" }, "fast" );
			$( ".hulp" ).addClass("open");
		}
	});
	
	
});