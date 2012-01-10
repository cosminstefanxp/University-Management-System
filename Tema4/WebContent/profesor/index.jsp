<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ACS - Profesor</title>
<link rel="stylesheet" href="../style/main.css" type="text/css" />
</head>
<%@include file="../include/tools.jsp"%>
<% request.setAttribute("permissions", Tip.CADRU_DIDACTIC);%>
<%@include file="../include/authentify.jsp" %>

<body>
		<%
		String denumire="";
		if(utilizator.tip==Tip.CADRU_DIDACTIC) 
			denumire="Consola Cadru Didactic";
		else
			denumire="Consola Sef de Catedra";
		%>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
			<jsp:param value="<%=denumire%>" name="title"/>
	</jsp:include>
	<!-- Continutul principal al paginii -->
	<div id="content">
		Bine ai venit pe consola cadrelor didactice ale facultatii! Alege de mai jos una dintre optiunile dorite:
		<div class="link-box">
			<a href="../user_settings.jsp" class="button">Editare setari personale</a>
			<a href="catalog.jsp" class="button">Vizualizare catalog</a>
			<% if(utilizator.tip==Tip.SEF_CATEDRA) { %>
			<a href="discipline.jsp" class="button">Administrare discipline</a>
			<a href="activitati.jsp" class="button">Administrare activitati didactice</a>
			<%} %>
			<div class="clear"></div>
		</div>
	</div>
	
	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>