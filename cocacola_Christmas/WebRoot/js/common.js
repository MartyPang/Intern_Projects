// JavaScript Document
$(function($) {
	logoWidthAuto();
	
	$('#zxkf').click(function(e) {
        $('#zx').show();
    });
	
	$('#zx').click(function(e) {
		$('#zx').hide();
    });
});

$(window).resize(function(){
	logoWidthAuto();
});

function logoWidthAuto (){
	var pageWidth = $(window).width();
	var pageHeight = $(window).height();
	$('.box').css('min-width',pageWidth);
	
	//$('.snow').css('min-height',pageHeight);
	$('.page.game').height(pageHeight);
	
	$('.pop').width(pageWidth);
	$('.pop').height(pageHeight);
}