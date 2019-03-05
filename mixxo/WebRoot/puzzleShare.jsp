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
<script type="text/javascript">
var from_id="<%=request.getAttribute("from_id").toString() %>";
var draw_num="<%=request.getAttribute("draw_num").toString() %>";
var friend_id="<%=request.getAttribute("friend_openid").toString() %>";
$(document).ready(function(){
	var tt = draw_num;
	//alert(tt);
	var css = "puzzle_lock s"+tt;
	if(tt=='9'){
	    css = 'dn';
	    $("#ywc_btn").attr("class","ywc_btn mb10");
	}
	$("#puzzle_lock").attr("class",css);
	if(tt < '9'){
	$.ajax({
		url: '${ctx}/checkUnlockable',
		type: 'POST',
		data:{
			from_id:from_id,
			friend_id:friend_id
		},
		dataType: 'text',
		success: function(resp){
		    //alert(resp);
			if(resp=='0'){
				//$("#btjs_btn").attr("class","btjs_btn dn mb10");
				$("#ybgm_btn").attr("class","ybgm_btn mb10");
				//$("#wcxp_btn").attr("class","wcxp_btn mb10");
			}else if(resp=='2'){
			    if(tt=='0'){
			           $("#wcxp_btn").attr("class","wcxp_btn mb10");
			    }
			    else{
			           $("#ygm_btn").attr("class","ygm_btn mb10");
			    }
				//$("#btjs_btn").attr("class","btjs_btn dn mb10");
				//$("#ybgm_btn").attr("class","ybgm_btn dn mb10");
				//$("#wcxp_btn").attr("class","wcxp_btn dn mb10");
			}else{
			    if(tt=='0'){
	                 //$("#ywc_btn").attr("class","wcxp_btn dn mb10");
		             //$("#btjs_btn").attr("class","btjs_btn dn mb10");
		             //$("#ybgm_btn").attr("class","ybgm_btn dn mb10");
		               $("#wcxp_btn").attr("class","wcxp_btn mb10");
		               }
		        if(tt>0&&tt<9){
		               $("#btjs_btn").attr("class","btjs_btn mb10");
		               }
			}
		}
	});
	}
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
		    desc: '快帮我解锁~MIXXO送你五星酒店免费入住！', // 分享描述
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
		    title: '快帮我解锁~MIXXO送你五星酒店免费入住！', // 分享标题
		    desc: '快帮我解锁~MIXXO送你五星酒店免费入住！', // 分享描述
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

function unlock(){
	$.ajax({
        url: '${ctx}/unlockOnePiece',
        type: 'POST',
        data:{
        	from_id:from_id,
        	friend_id:friend_id
        },
        dataType: 'text',
        success: function(resp){
        	var tt = parseInt(draw_num)+1;
        	var css = "puzzle_lock s"+tt;
        	$("#btjs_btn").attr("class","btjs_btn dn mb10");
        	if(tt=='9'){
        		css = 'dn';
        		$("#puzzle_lock").attr("class",css);
        		$("#ywc_btn").attr("class","ywc_btn mb10");
        		
        	}else{
        	    $("#puzzle_lock").attr("class",css);
        	    $("#ybgm_btn").attr("class","ybgm_btn mb10");
        	}
        	//alert(resp);
        	OpenPopbox('nine');
        	
        }
	});
}
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
    	<div class="btjs_btn dn mb10" id="btjs_btn" onclick="unlock()">
        	<img src="images/btjs_btn.png" width="180" />
        </div>
        <div class="ybgm_btn dn mb10" id="ybgm_btn">
        	<img src="images/ybgm_btn.png" width="180" />
        </div>
        <div class="wcxp_btn dn mb10" id="wcxp_btn">
        	<img src="images/wcxp_btn.png" width="180" />
        </div>
        <div class="ywc_btn dn mb10" id="ywc_btn">
        	<img src="images/ywc_btn.png" width="180" />
        </div>
        <div class="ygm_btn dn mb10" id="ygm_btn" onclick="OpenPopbox('share')">
        	<img src="images/ygm_btn.png" width="180" />
        </div>
        <div class="wyyw_btn" onclick="OpenPopbox('metoo')">
        	<img src="images/wyyw_btn.png" width="180" />
        </div>
    </div>
</div>
<div class="pop_bg"></div>
<div class="pop">
    <div id="share" style="color:#FFF; width:100px; text-align:center; position:absolute; top:40px; right:10px; display:none;">
    	<img src="images/share.png" width="36" /><br/>
    	还要加把劲哦，和小伙伴一起赢大奖吧~
    </div>
	<div class="pop_box" id="nine">
    	<div class="top_img"><img src="images/pop_top.gif" width="90%" /></div>
        <div class="pop_tit_img"><img src="images/popbox_jscg.png" width="99%" /></div>
        <div class="pop_txt">
			您已成功帮助好友解锁1块拼图<br/>
        </div>
        <div class="mb20" onclick="ClosePopbox('nine')"><img src="images/bak_btn.png" width="110" /></div>
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
