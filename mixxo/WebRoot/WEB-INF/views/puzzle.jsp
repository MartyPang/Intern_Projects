<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>集拼图，赢大奖！</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Cache-Control" content="max-age=0"/>
<meta name="HandheldFriendly" content="true" />
<meta name="viewport" content="width=320.1, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link href="css/common.css" rel="stylesheet" type="text/css" />
    <script language="javascript" type="text/javascript" src="http://libs.baidu.com/jquery/2.0.3/jquery.min.js"></script>
    <script language="javascript" type="text/javascript" src="js/common.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
</head>
<body>
<div id='wx_pic' style='margin:0 auto;display:none;'>
     <img src='${ctx}/images/share.jpg' />
</div>
<input type="hidden" value="${drawNum}" id="count" />
<script type="text/javascript">
$(document).ready(function(){
	//设置拼图解锁数量
	var tt = document.getElementById("count").value;
	var css = "puzzle_lock s"+tt;
	if(tt=='9'){
	    css = 'dn';
	    ClosePopbox('share');
		OpenPopbox('success');
		$("#des1").css('display','none'); 
	}
	$("#puzzle_lock").attr("class",css);
	
	//设置活动到期时间为当前年当前月的最后一天
	var date = new Date();
	document.getElementById("year").innerHTML=date.getFullYear();
	document.getElementById("month").innerHTML=date.getMonth()+1;
	
	var  day = new Date(date.getFullYear(),date.getMonth()+1,0); 
	document.getElementById("day").innerHTML = day.getDate();
	
	document.getElementById("num3").innerHTML=(9-tt);
	
	wx.config( {
		debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : '${appid}', // 必填，公众号的唯一标识
		timestamp : '${timestamp}', // 必填，生成签名的时间戳
		nonceStr : '${noncestr}', // 必填，生成签名的随机串
		signature : '${signature}',// 必填，签名，见附录1
		jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.ready(function(){
	    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		//分享给朋友	*******************************************************************************************************
		wx.onMenuShareAppMessage({
		    title: '', // 分享标题
		    desc: '快帮我解锁~MIXXO送你双飞机票，玩转首尔！', // 分享描述
		    link: '${shareURL}', // 分享链接
		    imgUrl: '${qr}/images/shareImg.jpg', // 分享图标
		    type: '', // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () { 
		    },
		    cancel: function () { 
		    }
		});
		
		//分享到朋友圈*****************************************************************************************************
		wx.onMenuShareTimeline({
		    title: '快帮我解锁~MIXXO送你双飞机票，玩转首尔！', // 分享标题
		    desc: '快帮我解锁~MIXXO送你双飞机票，玩转首尔！', // 分享描述
		    link: '${shareURL}', // 分享链接
		    imgUrl: '${qr}/images/shareImg.jpg', // 分享图标
		    success: function () { 
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
		
		//分享到QQ*****************************************************************************************************
		wx.onMenuShareQQ({
		    title: '', // 分享标题
		    desc: 'MIXXO集章活动', // 分享描述
		    link: '${shareURL}', // 分享链接
		    imgUrl: '', // 分享图标
		    success: function () { 
		    },
		    cancel: function () { 
		       // 用户取消分享后执行的回调函数
		    }
		});
		
		
		//分享到QQ空间*****************************************************************************************************
		wx.onMenuShareQZone({
		    title: '', // 分享标题
		    desc: 'MIXXO集章活动', // 分享描述
		    link: '${shareURL}', // 分享链接
		    imgUrl: '', // 分享图标
		    success: function () { 
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
     });
	 wx.error(function(res){
     alert('wx.error: '+JSON.stringify(res));
     });
});
</script>
<div class="page">
	<div class="logo"></div>
    <div><img src="images/text.gif" width="50%" /></div>
    <div class="puzzle_shadow">
        <div class="puzzle">
            <div class="puzzle_bar">
            	<!-- puzzle_lock 后面不带其他class为全锁，解锁class为s1~s8,dn为全解锁 -->
            	<div class="puzzle_lock s0" id="puzzle_lock"></div>
           	</div>            
        </div>
    </div>
    <div class="my_btns">
        <div id="des1">
    	<h1>就差<label id="num3"></label>块拼图了，加油!</h1>
        <span>每邀请一个闺蜜，可以帮你解锁一块拼图哟</span>
        </div>
        <div class="ygm_btn mb10" onclick="OpenPopbox('share')">
        	<img src="images/ygm_btn.png" width="180" />
        </div>
        <div class="wygz_btn mb10 dn">
        	<img src="images/wygz_btn.png" width="110" />
        </div>
        <p>本期活动有效期至<label id="year"></label>年<label id="month"></label>月<label id="day"></label>日</p>
    </div>
</div>
<div class="pop_bg"></div>
<div class="pop">

    <div id="share" style="color:#FFF; width:100px; text-align:center; position:absolute; top:40px; right:10px; display:none;">
    	<img src="images/share.png" width="36" /><br/>
    	还要加把劲哦，和小伙伴一起赢大奖吧~
    </div>
	<div class="pop_box" id="success">
    	<div class="top_img"><img src="images/pop_top.gif" width="90%" /></div>
        <div class="pop_tit_img"><img src="images/popbox_jscg.png" width="99%" /></div>
        <div class="pop_txt">
			您已成功解锁9块拼图<br/>
			五星级酒店住宿一晚<br/>
			我们将于10月1日公布中奖结果<br/>
			敬请期待
        </div>
        <div class="mb20" onclick="window.location.href='myBadge.jsp'"><img src="images/bakindex_btn.png" width="110" /></div>
    </div>
    <div class="pop_box" id="metoo">
    	<div class="top_img"><img src="images/pop_top.gif" width="90%" /></div>
        <div class="pop_tit_img"><img src="images/popbox_wyyw.png" width="99%" /></div>
        <div class="pop_txt">
			关注MIXXO女装<br/>
            参与更多有奖活动
        </div>
        <div class="qr_box"><img src="images/qr.jpg" width="100%" /></div>
        <div class="mb20" onclick="ClosePopbox('metoo')"><img src="images/bak_btn.png" width="110" /></div>
    </div>
</div>
</body>
</html>
