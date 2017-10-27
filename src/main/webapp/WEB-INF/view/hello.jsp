<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello</title>
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.js"></script>
<script type="text/javascript">
	
	//
	function test() {
		var message = document.getElementById("message").value;
		alert(message);
		//alert(dd);
		//$("#i1").attr("innerhtml","<html><body><input type='button' id='btn1' value='click'/></body></html>");
		//$("#i1").attr("src","https://www.baidu.com/");
		//alert($("#i1").attr("innerhtml"));
		//var frame = window.frames['i1']; 
		//frame.document.clear();
		//frame.document.write("<html><body><input type='button' id='btn1' value='click'/></body></html>");
	}
	
</script>
</head>
<body>
	hello <input type="text" value="${people}"/> !
	<input type="hidden" id="message" value="${message}"/>
	<input type="hidden" id="h1" name="h1" value="hidden"/>
	<form action="${ctx}/hello1"><input type="submit" id="btn2" value="click2" /> </form>
	<input type="button" id="btn1" value="click" onclick="test()"/>
	<textarea id="resLogText"   name="resLogText" rows="20" style="display:none" >ddd</textarea>
	<iframe id="i1" name="i1" src="www.baidu.com" style="overflow:visible;" scrolling="yes"  width="100%" height="400"></iframe>
</body>
</html>