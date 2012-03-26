<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ACS - Secretar</title>
<link rel="stylesheet" href="../style/main.css" type="text/css" />
</head>
<%@include file="../include/tools.jsp"%>
<% request.setAttribute("permissions", Tip.SECRETAR);%>
<%@include file="../include/authentify.jsp" %>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Consola Secretar" name="title"/>
	</jsp:include>
	<!-- Continutul principal al paginii -->
	<div id="content">
		Bine ai venit pe consola secretarului facultatii! Alege de mai jos una dintre optiunile dorite:
		<div class="link-box">
			<a href="../user_settings.jsp" class="button">Editare setari personale</a>
			<a href="studenti.jsp" class="button">Editare studenti</a>
			<a href="orar.jsp" class="button">Administrare Orar</a>
			<a href="examene.jsp" class="button">Administrare Examene</a>
			<div class="clear"></div>
		</div>
	</div>
	
	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>