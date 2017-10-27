<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Json转换</title>
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.js"></script>
<script type="text/javascript">

	$(document).ready(function() {
		var message = $("#message").val();
		if (message != "") {
			alert(message);
		}
	});
	//
	function jsonToExcel() {
		var url = "${ctx}/json/trans/excel";
		$("#jsonTransForm").attr("action", url).submit();
	}
	//
	function jsonToMysql() {
		var url = "${ctx}/json/trans/mysql";
		$("#jsonTransForm").attr("action", url).submit();
	}
</script>
</head>
<body>
	<form id="jsonTransForm" action="" method="post">
		<input type="hidden" id="message"  value="${message}" />
		<table>
			<tr>
				<td><textarea name="jsonContent" rows="20" style="width: 800px">${jsonContent} </textarea></td>
			</tr>
			<tr>
				<td>ListTag:<input type="text" style="width: 600px;"
					name="jsonListTag" value="${jsonListTag}" />
				</td>
			</tr>
			<tr>
				<td>Header:<input type="text" style="width: 600px;"
					name="jsonHeaderTemp" value="${jsonHeaderTemp}" />
				</td>
			</tr>
			<tr>
				<td><input type="button" id="btnJsonToExcel" value="存储为Excel"
					onclick="jsonToExcel()" /></td>
			</tr>
			<tr>
				<td>jdbcURL:<input type="text" style="width: 600px;"
					name="jdbcURL" value="${jdbcURL}" />
				</td>
			</tr>
			<tr>
				<td>jdbcUser:<input type="text" style="width: 100px;"
					name="jdbcUser" value="${jdbcUser}" />
				</td>
			</tr>
			<tr>
				<td>jdbcPassword:<input type="password" style="width: 100px;"
					name="jdbcPassword" value="${jdbcPassword}" />
				</td>
			</tr>
			<tr>
				<td><input type="button" id="btnJsonToMysql" value="存储到MySql"
					onclick="javascript:jsonToMysql();" /></td>
			</tr>
		</table>
	</form>
	
</body>
</html>