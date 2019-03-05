<%@ page language="java" import="java.util.*, com.sinocontact.util.PropertyUtils" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<title>MIXXO我要盖章活动</title>
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
<script type="text/javascript">
$(document).ready(function(){	
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
		    title: 'MIXXO我要盖章活动', // 分享标题
		    desc: '上传MIXXO小票，玩转首尔！更有双飞机票等你赢~', // 分享描述
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
		    title: '上传MIXXO小票，玩转首尔！更有双飞机票等你赢~', // 分享标题
		    desc: '上传MIXXO小票，玩转首尔！更有双飞机票等你赢~', // 分享描述
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
	<div class="logo" style="padding-top:26%;"></div>
	<div class="sign">
    	<a href="info.jsp?type=md" class="sign_box left"><img src="images/sign_md.png" width="150" /></a>
        <a href="info.jsp?type=hd" class="sign_box right"><img src="images/sign_hd.png" width="150" /></a>
        <a href="info.jsp?type=ly" class="sign_box left"><img src="images/sign_lyl.png" width="150" /></a>
        <a href="info.jsp?type=qtd" class="sign_box right"><img src="images/sign_qtd.png" width="150" /></a>
    </div>
	<div class="women">
    	<img src="images/women.gif" width="35%" style="margin-right:-55px; margin-top:15px;" />
        <img src="images/women1.gif" width="40%" style="position:relative;" />
        <img src="images/women2.gif" width="35%" style="margin-left:-60px; margin-top:15px;" />
        <img src="images/ilsoul.png" width="15%" style="position:absolute; right:5%; top:10px;" />
    </div>
    <a class="btn" href="${ctx}/jump"><img src="images/index_btn.png" width="150" /></a>
    <div class="bottom"><img src="images/city_bg.png" width="100%" /></div>
</div>
<script type="text/javascript">
if(screen.width < 375){
	$('.sign').find('img').width('120');
	$('.page .sign .sign_box.left img').css('margin-right','80px');
	$('.page .sign .sign_box.right img').css('margin-left','80px');
	$('.w1').css('width','33%');
	$('.w2').css('width','38%');
	$('.w3').css('width','33%');
}else if(screen.width < 400){
	$('.w1').css('width','30%');
	$('.w2').css('width','35%');
	$('.w3').css('width','30%');
}
</script>
</body>
</html>
