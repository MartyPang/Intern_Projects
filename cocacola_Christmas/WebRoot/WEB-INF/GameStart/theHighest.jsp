<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	
	$(document).ready(function(){
		//var list=eval('${list}');
		//alert(list.length);
		/*var html="";
		for(var i=0;i<list.length;i++){
			html += " <li>";
			html += " <span class=\"date\" >"+${li.date_time}.substring(0,11)+"</span> ";
			html += "<span> "+${li.nick_name}+"</span>";
			html += "<span class=\"fs\"><i>"+${li.game_num}+"</i> 分</span>";
			html += " </li>";
		}
		$("#tb").thml(html);*/
	});
	</script>
</head>
<body style="overflow:auto;">
<div class="page">
	<div class="snow">
		<div class="tips_box">
        	<div class="tips_tit"><img src="${ctx}/images/bd_title.png" width="150" /></div>
            <div class="tips_con">
            	<p class="red"><span>*</span>必须关注[可口可乐乐在上海]微信公众号，才能成功领奖哦。</p>
                <div class="con_title">
                	<span class="date">日期</span>
                    <span>昵称</span>
                    <span class="fs">分数</span>
                </div>
                <ul class="bd_list" id="tb" >
                <c:choose>
                	<c:when test="${empty list}">
                		 <li>
                    		<span class="date" ></span>
                    		<span class="name">暂无数据</span>
                    		<span class="fs"><i></i> </span>
                   		 </li> 
                	</c:when>
                	<c:otherwise>
	                	 <c:forEach var="li" items="${list}">
	                		 <li>
	                    		<span class="date" >${fn:substring(li.date_time, 0, 11)}</span>
	                    		<span class="name">${li.nick_name}</span>
	                    		<span class="fs"><i>${li.game_num}</i> 分</span>
	                   		 </li> 
	                     </c:forEach>  
                	</c:otherwise>
                </c:choose>
                </ul>
                <div class="btns">
                	<a href="${ctx}/game/gameStart.do"><img src="${ctx}/images/back.png" width="100" /></a>
                   <a id="zxkf"><img src="${ctx}/images/zxkf.png" width="100" /></a>
                </div>
           	</div>
        </div>
        <div class="foot">
        	<img src="${ctx}/images/bg_foot.png" width="100%" />
        </div>
    </div>
</div>
<div class="pop" id="zx">
	<div class="game_info" id="info">
    	遇到小问题？咨询客服吧~<br/>
		※ 长按二维码关注[可口可乐乐在上海]<br/>
		微信公众号。<br/>
		※ 记得给小可留言吧。<br/>
        <img src="${ctx}/images/qr.jpg" width="90" style="margin-top:10px;" />
    </div>
</div>
</body>
</html>
