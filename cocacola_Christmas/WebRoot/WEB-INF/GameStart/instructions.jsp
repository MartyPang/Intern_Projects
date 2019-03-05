<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/common/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<title>可口可乐圣诞大派送~</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-cache"/> 
	<meta http-equiv="Cache-Control" content="max-age=0"/>
	<meta name="HandheldFriendly" content="true" />
	<meta name="viewport" content="width=320.1, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script language="javascript" type="text/javascript" src="${ctx}/js/common.js"></script>
     <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
	<script src="${ctx}/js/WeixinApi.js" type="text/javascript"></script>
	<script src="${ctx}/js/share.js" type="text/javascript"></script>
	<script type="text/javascript">
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
		    desc: '我帮圣诞老人派送了999瓶可口可乐，等你来挑战！~', // 分享描述
		    link: 'http://h5.dakecrm.com/cocacola_Christmas', // 分享链接
		    imgUrl: 'http://h5.dakecrm.com/cocacola_Christmas/images/fengmian.jpg', // 分享图标
		    type: '', // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		         var type = "朋友";
		        sharePage(type);
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
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
<body style="overflow:auto;">
<div class="page">
	<div class="snow">
		<div class="tips_box">
        	<div class="tips_tit"><img src="${ctx}/images/tips_title.png" width="100" /></div>
            <div class="tips_con">
            	一、活动时间<br/>
                2015年12月21日-2016年1月3日<br/>
                <br/>
                二、游戏规则<br/>
                <p><span>1、</span>使用手指长按屏幕，控制梯子伸缩的长度</p>
                <p><span>2、</span>圣诞老人抵达目标烟囱，派送可口可乐成功，即得一分</p>
                <p><span>3、</span>圣诞老人超过或未达到目标烟囱，游戏结束</p>
				<p><span>4、</span>截止至每日24点，游戏最高分的玩家（同分，取时间最早的）为当日优胜</p>
				<p><span>5、</span>每日优胜者可获得纪念奖品一份</p>
				<p><span>6、</span>优胜者需关注微信号[可口可乐乐在上海]领取奖励</p>
				<p><span>7、</span>若发现游戏过程中存在作弊行为，[可口可乐乐在上海]有权取消其活动参与资格及获得的奖品</p>
                <p><span>8、</span>本次活动归[可口可乐乐在上海]所有。</p>
                <br/>
                三、奖品设置<br/>
                每日优胜者将获得由[可口可乐乐在上海]微信公众号提供的可口可乐16k直柄伞一份。
            </div>
            <div class="tips_img">
            	<img src="${ctx}/images/jiangpin.jpg" width="50%" />
            </div>
            <div class="tips_img">
            	<img src="${ctx}/images/qr.jpg" width="40%" />
                <img src="${ctx}/images/zw.png" width="40%" />
                <br/>长按微信二维码图片，关注[可口可乐乐在上海]
            </div>
            <div class="btn_box" onclick="window.location.href='${ctx}/game/gameStart.do'"><img src="${ctx}/images/wx_game_start.png" width="100%" /></div>
            <div class="btn_box" onclick="window.location.href='${ctx}/game/theHighest.do'"><img src="${ctx}/images/check_top.png" width="100%" /></div>
        </div>
        <div class="foot st">
        	<img src="${ctx}/images/bg_foot.png" width="100%" />
        </div>
    </div>
</div>
</body>
</html>
