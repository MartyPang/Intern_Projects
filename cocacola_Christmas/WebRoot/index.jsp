<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/common/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${ctx}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
  </head>
  
  <body>
  <script type="text/javascript">
   $(document).ready(function(){
   		if('${from_user}'==""){
   			location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx36343ee0d84a5568&redirect_uri=http://h5.dakecrm.com/cocacola_Christmas/game/preStart.do?from_user=0&response_type=code&scope=snsapi_userinfo&state=aa#wechat_redirect";
   		}else{
   			location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx36343ee0d84a5568&redirect_uri=http://h5.dakecrm.com/cocacola_Christmas/game/preStart.do?from_user="+'${from_user}'+"&response_type=code&scope=snsapi_userinfo&state=aa#wechat_redirect";
   		}
   });
  </script>
  <br>
  </body>
</html>
