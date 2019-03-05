<%@ page language="java" import="java.util.*, com.sinocontact.util.PropertyUtils" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <script language="javascript" type="text/javascript" src="http://libs.baidu.com/jquery/2.0.3/jquery.min.js"></script>
  </head>
  
  <body>
   <script type="text/javascript">
    $(document).ready(function(){
    	    var tt_url = "<%=PropertyUtils.getProperty("url") %>";
			var appid="<%=PropertyUtils.getProperty("appid") %>";
			var url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+tt_url+"/preStart&response_type=code&scope=snsapi_userinfo&state=aa#wechat_redirect";
   			location.href=url;
     });
    </script>
  </body>
</html>
