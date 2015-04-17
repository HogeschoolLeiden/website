$(".column-box").children('.column-box-header').on('click', function() {
  $this = $(this);
  if ($this.next().is(':hidden')){
    $('.box-content').slideUp(200);
    var imageSource = $('.column-box-header img').attr('src');
    imageSource = imageSource.replace('arrow_up', 'arrow_down');
    $('.column-box-header').removeClass('active').children('span + img').attr('src', imageSource);
    $this.next().slideDown(200);
    imageSource = imageSource.replace('arrow_down', 'arrow_up');
    $this.addClass('active').children('span + img').attr('src', imageSource);
  }
  else {
    $this.next().slideUp(200);
    var imageSource = $(this).children('img').attr('src');
    imageSource = imageSource.replace('arrow_up', 'arrow_down');
    $this.removeClass('active').children('span + img').attr('src', imageSource);
  }
});

$('.search-magnify').on('click', function(){
	$(this).closest('form').submit();
});

$(".filter").change(function() {
	$(this).closest('form').submit();
});

$('#skiplinks a').on('focus', function () {
    $(this).closest('#skiplinks').addClass('active');
});
$('#skiplinks a').on('blur', function () {
    $(this).closest('#skiplinks').removeClass('active');
});