// JavaScript Document
$(function($) {
	
	//$(document).bind("contextmenu",function(){return false;});
	//$(document).bind("selectstart",function(){return false;});
	//$(document).keydown(function(){return key(arguments[0])});
	
	var num = 1;
	var angle = 0;
	var barHeight = 0;
	var dis = $(window).width()-200;
	var barRound;
	var dxRound;
	var boxRight;
	var barDis;
	var goDis;
	var score;
	var longOrshot = true;
	
	var randomDis = Math.ceil(Math.random()*dis);
	
	$('#start').show();
	$('#start').click(function(e) {
		$('#start').hide();
        gameInit();
    });
	
	$('.again').click(function(e) {
        $('#gameover').hide();
		gameAgain();
    });
	
	/*function gameInit(){
		$('aside em').css('bottom','15px');
		$('.box').append('<article id='+num+'><img src="images/house.png" width="115" /></article>');
		randomDis = Math.ceil(Math.random()*dis);
		$('#'+num).animate({right:randomDis+'px'},500);
		setTimeout(function(){canTouch = true;},500);
		document.addEventListener('touchstart', touchSatrtFunc, false); 
		document.addEventListener('mousedown', touchSatrtFunc, false); 
	}*/
	
	function touchSatrtFunc(evt){
		evt.preventDefault();
		barRound = setInterval(barLong,10);
		document.addEventListener('touchend', touchEndFunc, false);  
		document.addEventListener('mouseup', touchEndFunc, false);  
		document.removeEventListener('touchstart', touchSatrtFunc, false);  
		document.removeEventListener('mousedown', touchSatrtFunc, false); 
	};
	
	function touchEndFunc(evt){
		clearInterval(barRound);
		dxRound = setInterval(daoXia,10);
		boxRight = $('#'+num).css('right');
		barDis = ($(window).width()-45)-(randomDis+100);
		document.removeEventListener('touchend', touchEndFunc, false);  
		document.removeEventListener('mouseup', touchEndFunc, false);  
	};
	
	/*function checkGame(){
		if(barHeight>=barDis && barHeight<=barDis+50){
			//this round win!!
			goDis = barDis+55;
			$('aside').animate({left:'+='+goDis});
			$('aside img').attr('src','images/santa_m.gif');
			setTimeout(function(){
				$('aside img').attr('src','images/santa_game.png');
				$('aside em').animate({bottom:'-50px'});
			},700);
			setTimeout(function(){
				$('.box').animate({marginLeft:'-='+goDis});
				setTimeout(gameGoon,1000);
			},1000);
			
		}else{
			//lose
			score = num-1;
			goDis = barHeight+25;
			$('aside').animate({left:'+='+goDis},500);
			$('aside img').attr('src','images/santa_m.gif');
			setTimeout(function(){
				$('aside img').attr('src','images/santa_game.png');
				dxRound = setInterval(diaoXia,10);
				$('aside').animate({bottom:'-40px'},200);
			},1000);
		}
	}*/
	
	function gameAgain (){
		gameOver();
		$('#score').html('当前成绩：0');
		$('aside').css('left','20px');
		$('aside').css('bottom','85px');
		$('article').remove();
		$('.box').append('<article id="0" class="init"><img src="images/house.png" width="115" /></article>');
		num = 1;
		gameInit();
	}
	
	function gameGoon(){
		if(num==1){
			$('#0').remove();
		}else{
			$('#'+num).remove();
		}
		$('.box').css('margin-left','0px');
		gameOver();
		$('#score').html('当前成绩：'+num);
		num++;
		gameInit();
	}
	
	function gameOver(){
		barHeight = 0;
		angle = 0;
		$('.bar').height(barHeight);
		$('.bar').rotate(angle);
		$('aside').css('left','20px');
		$('#'+num).css('right','0px');
		$('#'+num).addClass('init');
	}
	
	function daoXia(){
		angle+=2;
		$('.bar').rotate(angle);
		if(angle>=90){
			clearInterval(dxRound);
			setTimeout(checkGame,300);
		}
	}
	
	function diaoXia(){
		angle+=5;
		$('.bar').rotate(angle);
		if(angle>=180){
			$('#gameover').show();
			clearInterval(dxRound);
		}
	}
	
	function barLong(){
		if(longOrshot){
			barHeight++;
		}else{
			barHeight--;
		}
		if(barHeight>=$(window).width()-45){
			longOrshot = false;
		}
		if(barHeight<0){
			longOrshot = true;
		}
		$('.bar').css('height',barHeight);
	}
});

function gameAgain(){
	location.reload();
}