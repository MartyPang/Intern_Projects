<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>景点详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Cache-Control" content="max-age=0"/>
<meta name="HandheldFriendly" content="true" />
<meta name="viewport" content="width=320.1, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link href="css/common.css" rel="stylesheet" type="text/css" />
    <script language="javascript" type="text/javascript" src="http://libs.baidu.com/jquery/2.0.3/jquery.min.js"></script>
    <script language="javascript" type="text/javascript" src="js/common.js"></script>
</head>
<body>
<div class="page">
	<div class="logo"></div>
	<div class="women info">
    	<div class="dhk">
        	<p>清潭洞代表时尚与奢华。众多高端品牌和画廊，使这里成为一个可以同时享受购物与文化的综合空间，是众女性梦寐以求想要融入的一个时尚与奢华的地方。</p>
            <a class="btn" href="javascript:history.back();"><img src="images/bak_btn.png" width="110" /></a>
        </div>
        <div style="padding-left:20px; text-align:left;"><img src="images/women.gif" width="40%" /></div>
    </div>
    <div class="bottom"><img src="images/md_bg.png" width="100%" /></div>
</div>
</body>
</html>
