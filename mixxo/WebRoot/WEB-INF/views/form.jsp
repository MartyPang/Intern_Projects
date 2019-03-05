<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
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

var images = {
		localId : [],
		serverId : []
	};
	
$(document).ready(function(){
		wx.config( {
			debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId : '${appid}', // 必填，公众号的唯一标识
			timestamp : '${timestamp}', // 必填，生成签名的时间戳
			nonceStr : '${noncestr}', // 必填，生成签名的随机串
			signature : '${signature}',// 必填，签名，见附录1
			jsApiList : [ 'chooseImage', 'previewImage',
					'uploadImage', 'downloadImage','onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone' ]
		// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
		wx.ready(function() {
			 // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
			//分享给朋友	*******************************************************************************************************
			wx.onMenuShareAppMessage({
			    title: 'MIXXO我要盖章活动', // 分享标题
			    desc: '上传MIXXO小票，玩转首尔！更有五星酒店免费住~', // 分享描述
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
			    title: '上传MIXXO小票，玩转首尔！更有五星酒店免费住~', // 分享标题
			    desc: '上传MIXXO小票，玩转首尔！更有五星酒店免费住~', // 分享描述
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
			// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
				document.querySelector('#imageDiv').onclick = function() {
					wx.chooseImage( {
						        count:1,
								success : function(res) {
									images.localId = res.localIds;
									$("#divImage").attr("src",images.localId[0]);
									
									setTimeout(function (){ 
										wx.uploadImage({
										localId: images.localId[0], // 需要上传的图片的本地ID，由chooseImage接口获得
										isShowProgressTips: 1,// 默认为1，显示进度提示
										success: function (res){
										images.serverId[0] = res.serverId; // 返回图片的服务器端ID
										}});}, 100);
									//alert('已选择 ' + res.localIds.length + ' 张图片');
								}
					});
				};

			});
		
		
		
	$.ajax({
        url: '${ctx}/checkOpenid',
        type: 'POST',
        data:{
        },
        dataType: 'text',
        success: function(resp){
          	if(resp=='true'){
          		$("#telephone").hide();
          		document.getElementById("telephone").value="111111";
          		$("#sendBtn").hide();
          		$("#validate_code").hide();
          		document.getElementById("validate_code").value="111111";
          		$("#phonebox").hide();
          		$("#codebox").hide();
          		$.ajax({
    				url: '${ctx}/checkBadgeNumber',
    				type: 'POST',
    				data:{
    				},
    				dataType: 'text',
    				success: function(resp){
    				if(resp=='0'){
					return;
    				}
    				else if(resp=='1'){
    				$("#sign_md").attr("src","images/sign_md_check.png");
    				$('#yz1').find('em').addClass('an');
    				}
    				else if(resp=='2'){
    				$("#sign_md").attr("src","images/sign_md_check.png");
    				$('#yz1').find('em').addClass('an');
    				$("#sign_hd").attr("src","images/sign_hd_check.png");
    				$('#yz2').find('em').addClass('an');
    				}
    				else if(resp=='3'){
    				$("#sign_md").attr("src","images/sign_md_check.png");
    				$('#yz1').find('em').addClass('an');
    				$("#sign_hd").attr("src","images/sign_hd_check.png");
    				$('#yz2').find('em').addClass('an');
    				$("#sign_lyl").attr("src","images/sign_lyl_check.png");
    				$('#yz3').find('em').addClass('an');
    				}
    				}
    				
    				});
          		return;
          	}
        }
        });
	}
);

var countdown=60; 
//发送手机验证码
function sendSMScode(obj){
		var mobile="";
		mobile = $("#telephone").val();
		if($("#telephone").val()==""){
			alert("请输入手机号码！");
			return;
		} 
		$.ajax({
	            url: '${ctx}/sendSMScode',
	            type: 'POST',
	            data:{
	                mobile:mobile
	            },
	            dataType: 'text',
	            success: function(resp){
		          	alert(resp);
		          	if(resp=="发送成功！"){
		          		settime(obj);
		          	}
	            }
        });
			
		
 }
 
function settime(obj) { 
    if (countdown == 0) { 
        obj.removeAttribute("disabled");    
        obj.value="获取验证码"; 
        countdown = 60; 
        return;
    } else { 
        obj.setAttribute("disabled", true); 
        obj.value="重新发送(" + countdown + ")"; 
        countdown--; 
    } 
setTimeout(function() { 
    settime(obj); }
    ,1000);
}
 
</script>
<div class="page">
	<div class="logo"></div>
	<div class="sign fm">
    	<a class="sign_box left" id="yz1"><img id="sign_md" src="images/sign_md.png" width="150" /><em></em></a>
        <a class="sign_box right" id="yz2"><img id="sign_hd" src="images/sign_hd.png" width="150" /><em></em></a>
        <a class="sign_box left" id="yz3"><img id="sign_lyl" src="images/sign_lyl.png" width="150" /><em></em></a>
        <a class="sign_box right" id="yz4"><img id="sign_qtd" src="images/sign_qtd.png" width="150" /><em></em></a>
        <div class="input_con">
        	<div class="input_box"><em></em><input type="text" id="serial_number" name="serial_number" maxLength="20" placeholder="填写流水号" /></div>
            <div class="input_box phone" id="phonebox"><em class="phone"></em><input type="text" id="telephone" name="telephone" maxLength="11" placeholder="填写手机号" /></div>
            <input type="button" onclick="sendSMScode(this)" id="sendBtn" value="获取验证码" />
            <div style="clear:both"></div>
            <div class="input_box" id="codebox"><em class="yz"></em><input type="text" id="validate_code" name="validate_code" maxLength="6" placeholder="填写手机验证码" /></div>
           	<div id="imageDiv" class="input_box upload">
            	<img id="divImage" src="images/plus.png" width="55" height="55"/><span>点击上传小票照片<br/>(图片大小请小于5M)</span>
            </div>
        </div>
    </div>
    <div class="submit" onclick="register()">提交</div>
    <div class="tip_con">
        <div class="tip_tit">
            <img src="images/tip_icon.png" width="11" />
            <span>查看活动说明</span>
            <img id="arr" src="images/arr_d.gif" width="10" />
        </div>
        <div class="tip">
        	<p>活动期限：2016年8月1日-2017年8月1日</p>
            <p>活动区域：全国MIXXO门店</p>
            <p>活动须知：本次活动仅在MIXXO微信平台进行，参与者须关注“MIXXO”公众号</p>
            <p>活动方式：只要您在MIXXO门店购物4次，第5次即可享受一件正价商品7折优惠。</p>
            <p>注意事项：</p>
            <p>  1、购物后请及时打开微信活动页面，并上传小票信息，完成一次盖章</p>
            <p>  2、同一用户一天只能成功上传1张小票</p>
            <p>  3、4次盖章完成后，自动生成一张7折优惠券（可在“我的盖章”中查看）</p>
            <p>  4、4次盖章完成后，集章流程将重新启动，您可以再次集章，获取优惠券</p>
            <p>  5、优惠券不可兑换现金，券码唯一，不可重复使用</p>
            <p>  6、优惠券只能用于正价商品</p>

            <p>优惠券有效期：</p>
            <p>  从获得当日起，一个月内有效</p>
        </div>
	</div>
</div>
<div class="pop_bg"></div>
<div class="pop">
	<div class="pop_box" id="submit">
    	<div class="top_img"><img src="images/pop_top.gif" width="90%" /></div>
        <div class="pop_tit_img"><img src="images/popbox_tjwc.png" width="99%" /></div>
        <div class="pop_txt">
        	盖章成功<br/>
            只需再获得<label id="num1"></label>个章，即可兑换7折券。<br/>
            你已解锁<label id="num2"></label>块拼图！<br/>
            邀闺蜜 赢大奖
        </div>
        <div class="mb20" onclick="window.location.href='${ctx}/jump2';"><img src="images/gmjl_btn.png" width="110" /></div>
        <div class="pop_lf_con">
            <p>月度大奖参与规则：</p>
            <div class="pop_tip">
                <p>抽奖资格：参与活动，解锁9宫格，即可参加抽奖活动</p>
                <p>抽奖方式：系统随机抽奖</p>
                <p>奖品内容：五星级酒店住宿一晚，中奖者可选择中国国内任意城市的任意酒店（价值1500元以内即可）</p>
                <p>奖品设置：一名</p>
                <p>抽奖期间：2016年9月1日-2016年9月30日</p>
                <p>获奖公布：2016年10月10日</p>
                <p>公布方式：MIXXO官方微信图文消息</p>
                <p>注意事项：</p>
                <p> 1、机票不可兑现，不可转换为其他奖品，不可转让</p>
                <p> 2、MIXXO品牌会及时与中奖者联系，如于活动结束一周内未联系您，即为未中奖。</p>
                <p> 3、MIXXO可以根据本活动的实际举办情况对活动规则进行变动或调整，相关变动或调整将公布在活动页面上</p>
            </div>
        </div>
    </div>
    <div class="pop_box" id="complete">
    	<div class="top_img"><img src="images/pop_top.gif" width="90%" /></div>
        <div class="pop_tit_img"><img src="images/popbox_tjwc.png" width="99%" /></div>
        <div class="pop_txt">
        	<h1>恭喜！</h1>
            您已集齐4个M印章<br/>
            成功领取<span>[七折券]</span>一张<br/>
            请在<span>[我的奖励]</span>中查看<br/>
            <br/>
            参与接力拼图，赢取<span>[五星级酒店住宿一晚]</span>
        </div>
        <div class="mb20" onclick="window.location.href='${ctx}/jump2';"><img src="images/gmjl_btn.png" width="110" /></div>
    </div>
</div>
</body>
</html>
