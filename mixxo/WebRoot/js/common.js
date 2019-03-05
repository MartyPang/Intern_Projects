// JavaScript Document
$(function($) {		
	logoWidthAuto();
	$('.pop').click(function(e) {
        if($('#share').css('display') == 'block'){
			$(this).hide();
		}
    });
	var type = getQueryString('type');
	
	switch(type){
		case 'md':
			$('.dhk').addClass('md');
			$('.dhk').find('p').html('明洞是韩国魅力的缩影，是购物者的天堂。从高级百货店、大型购物商场到独具特色的各类小店，追求时尚的韩国人和世界各地游客在这里邂逅，是一个让人乐不思蜀的地方。');
			$('.bottom').find('img').attr('src','images/md_bg.png');
			$('#person').find('img').attr('src','images/women1.gif');
			break;
		case 'hd':
			$('.dhk').addClass('hd');
			$('.dhk').find('p').html('弘益大学是韩国无数艺术生向往的最高艺术学府，代表年轻人们的青春与激情。街边的潮人乐队，风格独特的墙画和涂鸦，充满艺术情调的小店，弘大的魅力与创意相关，与自由为伴。');
			$('.bottom').find('img').attr('src','images/hd_bg.png');
			$('#person').find('img').attr('src','images/women2.gif');
			break;
		case 'ly':
			$('.dhk').addClass('ly');
			$('.dhk').find('p').html('林荫路它繁华不张扬，总是给人轻松、舒适的感觉。林立的咖啡厅与精品商店都有属于林荫路自己的风格与味道。仿佛在这里品尝一杯咖啡的时间，就像是度过了一个最惬意的周末。');
			$('.bottom').find('img').attr('src','images/ly_bg.png');
			break;
		case 'qtd':
			$('.dhk').addClass('qtd');
			$('.dhk').find('p').html('清潭洞代表时尚与奢华。众多高端品牌和画廊，使这里成为一个可以同时享受购物与文化的综合空间，是众女性梦寐以求想要融入的一个时尚与奢华的地方。');
			$('.bottom').find('img').attr('src','images/qtd_bg.png');
			$('#person').find('img').attr('src','images/women1.gif');
			break;
	}
	
	$('.tip_con').click(function(e) {
		if($('.tip').css('display')=='none'){
			$('#arr').attr('src','images/arr_u.gif');
			$('.tip').show();
		}else{
			$('#arr').attr('src','images/arr_d.gif');
			$('.tip').hide();
		}
    });
	
	$('.pop_bg').click(function(e) {
		$('.pop').hide();
		$(this).hide();
    });
});

$(window).resize(function(){
	logoWidthAuto();
	setTimeout(function(){$('.pop').height($('body').height());},1000);
});

function logoWidthAuto (){
	var pageWidth = $(window).width();
	var pageHeight = $(window).height();
	
	$('.page').css('min-height',pageHeight);
	
	$('.puzzle').height(pageWidth);
	$('.puzzle_bar').height(pageWidth);
	$('.puzzle_lock').height(pageWidth);
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
};

function ClosePopbox(id){
	var boxName = $('#'+id);
    $('.pop').hide();
	boxName.hide();
}

function OpenPopbox(id){
	var boxName = $('#'+id);
	$('body').css('overflow','hidden');
	$('.pop_bg').height($(document).height());
	$('.pop_bg').show();
    $('.pop').show();
	$('.pop').css('top',($(window).height()-boxName.height())/2 + window.scrollY + 30);
	if(id == "share"){
		$('.pop').css('top','0px');
		$('.pop_box').hide();
	}
	boxName.css('display','inline-block');
}

function register()
{
	var serial = $("#serial_number").val();
	var tel = $("#telephone").val();
	var code = $("#validate_code").val();
	var imageID = images.serverId[0];
	if(serial == "" || tel == "" || code == "" || typeof(imageID) =="undefined"){
		alert("请输入完整信息！");
		return;
	}
	
	$.ajax({
        url: '${ctx}/submit',
        type: 'POST',
        data:{
        	serial_number:serial,
            telephone:tel,
            validate_code:code,
            media_id:imageID
        },

        dataType: 'text',
        success: function(resp){
        	//跳回页面顶部
        	document.getElementsByTagName('BODY')[0].scrollTop=0;
        	
          	//alert(resp);
        	if(resp=="serialerror"){
        		alert("小票号格式无效！");
        		return;
        	}
        	if(resp=="codeerror"){
        		alert("验证码错误！");
        		return;
        	}
        	var tt = resp.substring(0,3);
        	if(tt=='102'){
        		alert("上传失败！请重新上传！");
        		return;
        	}else if(tt=='103'){
        		alert("小票号重复！");
        		return;
        	}else if(tt=='104'){
        		alert("今日已上传过小票！");
        		return;
        	}else if(tt=='105'){
			    var label = document.getElementById("num1");  
                label.innerText="0"; 
				label = document.getElementById("num2");
				label.innerText=resp.charAt(4);
				$('#yz4').find('em').addClass('an');
				setTimeout(function(){
					$('#yz4').find('img').attr('src','images/sign_qtd_check.png');
					setTimeout(function(){OpenPopbox('complete');},500);
				},300);
				return;
				//$("#sign_qtd").attr("src","images/sign_qtd_check.png");
        	}else{
        		var label = document.getElementById("num1");  
                label.innerText=4-resp.charAt(3); 
				label = document.getElementById("num2");
				label.innerText=resp.charAt(4);
				if(resp.charAt(3)=='1'){
					$('#yz1').find('em').addClass('an');
		    		setTimeout(function(){
		    			$('#yz1').find('img').attr('src','images/sign_md_check.png');
		    			setTimeout(function(){OpenPopbox('submit');},500);
		    		},300);
					//$("#sign_md").attr("src","images/sign_md_check.png");
				}
				else if(resp.charAt(3)=='2'){
					$('#yz2').find('em').addClass('an');
		    		setTimeout(function(){
		    			$('#yz2').find('img').attr('src','images/sign_hd_check.png');
		    			setTimeout(function(){OpenPopbox('submit');},500);
		    		},300);
					//$("#sign_hd").attr("src","images/sign_hd_check.png");
				}
				else if(resp.charAt(3)=='3'){
					$('#yz3').find('em').addClass('an');
		    		setTimeout(function(){
		    			$('#yz3').find('img').attr('src','images/sign_lyl_check.png');
		    			setTimeout(function(){OpenPopbox('submit');},500);
		    		},300);
					//$("#sign_lyl").attr("src","images/sign_lyl_check.png");
				}
				return;
        	}
        	return;
        },
        error:function(resp){
        	alert(resp);
        }
        });
}