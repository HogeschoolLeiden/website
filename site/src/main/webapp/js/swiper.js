$( document ).ready(function() {

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
	    	if($(".img.swiper-slide").length > 3){
				e.preventDefault();
				mySwiper.swipePrev();
	    	}
		});
		
		$('.swipenav.next').on('click', function(e){
			if($(".img.swiper-slide").length > 3){
				e.preventDefault();
				mySwiper.swipeNext();
			}
		}) ;
		
		if($(".img.swiper-slide").length > 3){			
			setInterval(function(){myTimer()},8000);
			
			function myTimer() {
				var d = new Date();
				mySwiper.swipeNext();
			}
		}
	}
});
