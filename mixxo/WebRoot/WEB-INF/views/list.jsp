<%@ page language="java" import="java.util.*, com.sinocontact.dao.CouponDao, com.sinocontact.dao.UserDao, com.sinocontact.util.CookieManager" pageEncoding="UTF-8"%>
<%@ include file="common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>我的奖励</title>
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
	 //wx.error(function(res){
     //alert('wx.error: '+JSON.stringify(res));
     //});
});
var telephone="<%=request.getAttribute("telephone") %>";
function useCoupon(){
	var c_num=$("#c_num").val();
	//alert(c_num);
	$.ajax({
		url: '${ctx}/useCoupon',
		type: 'POST',
		data:{
			tel:telephone,
			coupon_num:c_num
		},
		dataType: 'text',
		success: function(resp){
			location.href="${ctx}/couponList";
		}
	});
}
</script>
<div class="page">
	<div class="logo"></div>
    <div class="list_box">
    	<img src="images/popbox_yhlb.png" width="100%" />
        <div class="list_con">
        <%CouponDao coupon = new CouponDao(); 
          List<Map<String,Object>> valid = new ArrayList<Map<String,Object>>();
          List<Map<String,Object>> invalid = new ArrayList<Map<String,Object>>();
          List<Map<String,Object>> used = new ArrayList<Map<String,Object>>();
          //CookieManager cookie = new CookieManager();
          String telephone = request.getAttribute("telephone").toString();
          valid = coupon.getCouponList(telephone, 1);
          invalid = coupon.getCouponList(telephone, 0);
          used = coupon.getCouponList(telephone, 2);
          %>
          <%if(valid.size()<=0&&invalid.size()<=0&&used.size()<=0){%>
               <div>您还没有任何优惠券哦，赶紧参与活动吧~~~</div>
          <%} %>
          <%for(int i=0;i<valid.size();i++){ 
              Map<String,Object> map=valid.get(i);%>
              <input type="hidden" value="<%=map.get("coupon_number") %>" id="c_num"/>
        	<div class="list" onclick="OpenPopbox('confirm')">
            	<div class="ticket_name"><h1>七折</h1><span>优惠券</span></div>
                <div class="ticket_date">到期日期：<%=map.get("expire_time").toString().substring(0, 10) %><br/>请在到期前使用</div>
            </div>
            <%} %>
            <%for(int i=0;i<invalid.size();i++){ 
              Map<String,Object> map=invalid.get(i);%>
            <div class="list gq">
            	<div class="ticket_name"><h1>七折</h1><span>优惠券</span></div>
                <div class="ticket_date">到期日期：<%=map.get("expire_time").toString().substring(0, 10) %><br/>已过期</div>
            </div>
            <%} %>
            <%for(int i=0;i<used.size();i++){ 
              Map<String,Object> map=used.get(i);%>
            <div class="list gq">
            	<div class="ticket_name"><h1>七折</h1><span>优惠券</span></div>
                <div class="ticket_date">到期日期：<%=map.get("expire_time").toString().substring(0, 10) %><br/>已使用</div>
            </div>
            <%} %>
        </div>
        <div class="ptjd_btn" onclick="window.location.href='${ctx}/jump2';">
        	<img src="images/ptjd_btn.png" width="150" />
        </div>
    </div>
</div>
<div class="pop_bg"></div>
<div class="pop">
	<div class="pop_box" id="confirm">
    	<div class="top_img"><img src="images/pop_top.gif" width="90%" /></div>
        <div class="pop_tit_img"><img src="images/popbox_qrsy.png" width="99%" /></div>
        <div class="pop_txt">
			请买单时<br/>
			向收银员出示本页面
        </div>
        <div class="mb20">
        	<img src="images/con_btn.png" width="110" style="margin-right:10px;" onclick="useCoupon()" />
        	<img src="images/bak_btn.png" width="110" onclick="ClosePopbox('confirm')" />
        </div>
    </div>
</div>
</body>
</html>
