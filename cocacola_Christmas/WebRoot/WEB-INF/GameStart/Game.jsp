<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/common/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<title>可口可乐圣诞大派送~</title>
	<META content="IE=10.000" http-equiv="X-UA-Compatible">
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">          
	<META name="Keywords" content="微信小游戏">     
	<META name="description" content="">     
	<META name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no"> 
	<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
    <script language="javascript" type="text/javascript" src="http://libs.baidu.com/jquery/2.0.3/jquery.min.js"></script>
    <script language="javascript" type="text/javascript" src="${ctx}/js/common.js"></script>
    <script language="javascript" type="text/javascript" src="${ctx}/js/jQueryRotate.2.1.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
	<script src="${ctx}/js/WeixinApi.js" type="text/javascript"></script>
	<script src="${ctx}/js/share.js" type="text/javascript"></script>
	<META name="GENERATOR" content="MSHTML 10.00.9200.16521">
	<script type="text/javascript">
	 $(document).ready(function(){
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
	
		function gameInit(){
			$('aside em').css('bottom','15px');
			$('.box').append('<article id='+num+'><img src="${ctx}/images/house.png" width="115" /></article>');
			randomDis = Math.ceil(Math.random()*dis);
			$('#'+num).animate({right:randomDis+'px'},500);
			setTimeout(function(){canTouch = true;},500);
			document.addEventListener('touchstart', touchSatrtFunc, false); 
			document.addEventListener('mousedown', touchSatrtFunc, false); 
		}
	
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
		
		function checkGame(){
			if(barHeight>=barDis && barHeight<=barDis+50){
				//this round win!!
				goDis = barDis+55;
				$('aside').animate({left:'+='+goDis});
				$('aside img').attr('src','${ctx}/images/santa_m.gif');
				setTimeout(function(){
					$('aside img').attr('src','${ctx}/images/santa_game.png');
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
				$('aside img').attr('src','${ctx}/images/santa_m.gif');
				setTimeout(function(){
					$('aside img').attr('src','${ctx}/images/santa_game.png');
					dxRound = setInterval(diaoXia,10);
					$('aside').animate({bottom:'-40px'},200);
				},1000);
			}
		}
	
		function gameAgain (){
			gameOver();
			$('#score').html('当前成绩：0');
			$('aside').css('left','20px');
			$('aside').css('bottom','85px');
			$('article').remove();
			$('.box').append('<article id="0" class="init"><img src="${ctx}/images/house.png" width="115" /></article>');
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
			$.ajax({
		            url: '${ctx}/game/addGameNum.do',
		            type: 'POST',
		            data:{
		                nick_name:'${wechatUserInfo['nickname']}',
		                open_id:'${wechatUserInfo['openid']}',
		                head_imgurl:'${wechatUserInfo['headimgurl']}',
		                game_num:num-1
		            },
		            dataType: 'text',
		            success: function(resp){
		            if(resp=="0"){
		            	alert('分数提交失败，请联系管理员！');
		            }else{
		            	$("#sp").html(num-1);
		            }
		           }
		    });
		    if(num>10){
		    	$("#sp1").html('不错哦，挑战最佳成绩吧！');
		    }else{
		    	$("#sp1").html('加油，为圣诞小任务给劲造！');
		    }
		    $('#gameover').show();
			clearInterval(dxRound);
			}
		}
	
		function barLong(){
			if(longOrshot){
				barHeight+=3;
			}else{
				barHeight-=3;
			}
			if(barHeight>=$(window).width()-45){
				longOrshot = false;
			}
			if(barHeight<0){
				longOrshot = true;
			}
			$('.bar').css('height',barHeight);
		}
		orientationChange();
   		 window.onorientationchange = orientationChange;
		
	});

	function gameAgain(){
		location.reload();
	}
	
	function orientationChange() {
	    switch(window.orientation) {
		    　　case 0: 
		            //addBind();
		            $("body").css('transform','');
		            break;
		    　　case -90: 
		            //clearBind();
		            alert("游戏要竖屏玩哟！！！");
		            $("body").css('transform','rotate(90deg)');
		            break;
		    　　case 90:   
		            //clearBind();
		            alert("游戏要竖屏玩哟！！！");
		            $("body").css('transform','rotate(-90deg)');
		            break;
		    　　case 180:   
		            //addBind();
		            $("body").css('transform','rotate(0deg)');
		        　break;
	    }
}
	
	
	
	
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${appid}', // 必填，公众号的唯一标识
	    timestamp: ${timestamp}, // 必填，生成签名的时间戳
	    nonceStr: '${noncestr}', // 必填，生成签名的随机串
	    signature: '${signature}',// 必填，签名，见附录1
	    jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	wx.ready(function(){
	    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		//分享给朋友	*******************************************************************************************************
		wx.onMenuShareAppMessage({
		    title: '可口可乐圣诞大派送~', // 分享标题
		    desc: '我帮圣诞老人派送了999瓶可口可乐，等你来挑战！~', // 分享描述', // 分享描述
		    link: 'http://h5.dakecrm.com/cocacola_Christmas', // 分享链接
		    imgUrl: 'http://h5.dakecrm.com/cocacola_Christmas/images/fengmian.jpg', // 分享图标
		    type: '', // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () { 
		    	var type = "朋友";
		        sharePage(type);
		    },
		    cancel: function () { 
		    }
		});
		
		//分享到朋友圈*****************************************************************************************************
		wx.onMenuShareTimeline({
		    title: '我帮圣诞老人派送了999瓶可口可乐，等你来挑战！~', // 分享标题
		    link: 'http://h5.dakecrm.com/cocacola_Christmas', // 分享链接
		    imgUrl: 'http://h5.dakecrm.com/cocacola_Christmas/images/fengmian.jpg', // 分享图标
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		        var type = "朋友圈";
		        sharePage(type);
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
		
		//分享到QQ*****************************************************************************************************
		wx.onMenuShareQQ({
		    title: '可口可乐圣诞大派送~', // 分享标题
		    desc: '我帮圣诞老人派送了999瓶可口可乐，等你来挑战！~', // 分享描述
		    link: 'http://h5.dakecrm.com/cocacola_Christmas', // 分享链接
		    imgUrl: 'http://h5.dakecrm.com/cocacola_Christmas/images/fengmian.jpg', // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		        var type = "QQ";
		        sharePage(type);
		    },
		    cancel: function () { 
		       // 用户取消分享后执行的回调函数
		    }
		});
		
		//分享到腾讯微博*****************************************************************************************************
		wx.onMenuShareWeibo({
		    title: '可口可乐圣诞大派送~', // 分享标题
		    desc: '我帮圣诞老人派送了999瓶可口可乐，等你来挑战！~', // 分享描述
		    link: 'http://h5.dakecrm.com/cocacola_Christmas', // 分享链接
		    imgUrl: 'http://h5.dakecrm.com/cocacola_Christmas/images/fengmian.jpg', // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		        var type = "腾讯微博";
		        sharePage(type);
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
		
		//分享到QQ空间*****************************************************************************************************
		wx.onMenuShareQZone({
		    title: '可口可乐圣诞大派送~', // 分享标题
		    desc: '我帮圣诞老人派送了999瓶可口可乐，等你来挑战！~', // 分享描述
		    link: 'http://h5.dakecrm.com/cocacola_Christmas', // 分享链接
		    imgUrl: 'http://h5.dakecrm.com/cocacola_Christmas/images/fengmian.jpg', // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		       var type = "QQ空间";
		        sharePage(type);
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
		
	});
	
	
	function sharePage(type){
		$.ajax({
		            url: '${ctx}/game/addShareLog.do',
		            type: 'POST',
		            data:{
		                nick_name:'${wechatUserInfo['nickname']}',
		                open_id:'${wechatUserInfo['openid']}',
		                head_imgurl:'${wechatUserInfo['headimgurl']}',
		                type:type,
		                url:'${url}'
		            },
		            dataType: 'text',
		            success: function(resp){
		            }
		});
	}
	</script>
</head>

<body style="background:url(${ctx}/images/game_bg.gif) repeat-x;">
<audio src="${ctx}/mp3/bgm.mp3" loop="loop" autoplay="autoplay"></audio>
<div class="page game">
	<div class="score_box">
		<div class="score" id="score">当前成绩：0</div>
        <div class="score top">当日领先成绩：${maxNumMap.game_num}</div>
    </div>
    <div class="trees"></div>
	<div class="box">
    	<aside><img src="${ctx}/images/santa_game.png" height="40" /><em></em></aside>
    	<div class="bar"></div>
    	<article id="0" class="init"><img src="${ctx}/images/house.png" width="115" /></article>
    </div>
    <div class="tudi"></div>
</div>
<div class="pop" id="start">
	<div class="game_info" id="info">
    	<img src="${ctx}/images/game_info.png" width="90" /><br/>
        长按屏幕控制梯子长度<br/>
		帮助圣诞老人抵达烟囱<br/>
		派送可口可乐
    </div>
</div>
<div class="pop" id="gameover">
	<div class="game_info go">
    	<img src="${ctx}/images/game_over.png" width="90" /><br/>
        <p><span id="sp1">加油，为圣诞小任务给劲造！</span><br/>
		帮圣诞老人派送了<span id="sp"></span>瓶【可口可乐】<br/>
		一个人玩，不如奔走相告~</p>
        <a class="again"></a>
        <a class="check" href="${ctx}/game/theHighest.do"></a>
        <a class="yxzc" href="${ctx}/game/instructions.do"></a>
    </div>
</div>
</body>
</html>
