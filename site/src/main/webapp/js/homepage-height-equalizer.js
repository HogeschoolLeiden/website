jQuery(document).ready(function setHomePageElementsHeight(){
	if(window.innerWidth >=991){
		var quickchoice = document.getElementById('quickchoice');
		if(quickchoice){			
			var quickchoiceHeight = quickchoice.clientHeight;
			
			var firstRow = document.getElementsByClassName('row first')[0];
			
			var firstTeaser = firstRow.getElementsByClassName('catalog head teasers')[0].getElementsByClassName('headteaser')[0];
			var secondTeaser = firstRow.getElementsByClassName('catalog head teasers')[1].getElementsByClassName('headteaser')[0];
			
			firstTeaser.setAttribute("style","min-height:" + quickchoiceHeight +"px;");
			secondTeaser.setAttribute("style","min-height:" + quickchoiceHeight +"px;");
		}
	}
});
