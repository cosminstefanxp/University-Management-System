<%@page import="aii.database.NotaCatalogWrapper"%>
<%@page import="aii.NotaCatalog"%>
<%@page import="aii.Utilizator.Finantare"%>
<%@page import="aii.database.UtilizatorWrapper"%>
<%@page import="aii.Utilizator.Tip"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ACS - Cadru Didactic</title>
<link rel="stylesheet" href="../style/main.css" type="text/css" />
</head>
<%@include file="../include/tools.jsp"%>
<%
	request.setAttribute("permissions", Tip.CADRU_DIDACTIC);
%>
<%@include file="../include/authentify.jsp"%>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Panou de Stergere Note" name="title" />
	</jsp:include>

	<!-- Continutul principal al paginii -->
	<div id="content">
		<%-- Analizam datele din formular, pentru cazul unui submit. --%>
		<%
			NotaCatalog nota = new NotaCatalogWrapper().getNotaCatalog(
				Integer.parseInt(request.getParameter("cod_disciplina")), request.getParameter("cnp_student"));
			if(new NotaCatalogWrapper().deleteNotaCatalog(nota))
				notify("Stergerea s-a efectuat cu success",out);
			else
				error("A aparut o problema la stergerea notei "+request.getParameter("cod_disciplina"),out);
		%>

		<jsp:include page="/include/content_footer.jsp">
			<jsp:param value="catalog.jsp" name="back_link"/>
		</jsp:include>
	</div>

	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>