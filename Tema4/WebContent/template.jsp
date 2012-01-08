<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ACS - Login</title>
<link rel="stylesheet" href="style/main.css" type="text/css" />
</head>
<%@include file="include/tools.jsp"%>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Panou de autentificare" name="title"/>
	</jsp:include>
	
	<!-- Continutul principal al paginii -->
	<div id="content">
		Bine ai venit pe consola de administrare! Introdu-ti datele mai jos pentru autentificare:
		<form name="login_form" action="index.jsp" method="POST" >
			<table>
				<tr>
					<td>Utilizator:</td>
					<td><input type="text" name="user"></td>
				</tr>
				<tr>
					<td>Parola:</td>
					<td><input type="password" name="pass"></td>
				</tr>
			</table>
			<input type="submit" name="submit" class="button" value="Autentifica">
		</form>
	</div>
	
	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>