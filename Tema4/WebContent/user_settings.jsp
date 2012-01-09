<%@page import="aii.database.UtilizatorWrapper"%>
<%@page import="aii.Utilizator.Tip"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ACS - Login</title>
<link rel="stylesheet" href="style/main.css" type="text/css" />
</head>
<%@include file="include/tools.jsp"%>
<%request.setAttribute("permissions", Tip.ANY);%>
<%@include file="include/authentify.jsp"%>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Panou de autentificare" name="title" />
	</jsp:include>

	<!-- Continutul principal al paginii -->
	<div id="content">
		<%-- Analizam datele din formular, pentru cazul unui submit. --%>
		<%
			if (request.getParameter("submitted") != null) {
				log("Avem parametrii pentru setarea proprietatilor: " + request.getParameterMap());

				//Copy the old user
				Utilizator utilizatorCopy = utilizator.clone();

				//Create the new user
				utilizator.CNP = request.getParameter("cnp");
				utilizator.nume = request.getParameter("nume");
				utilizator.prenume = request.getParameter("prenume");
				utilizator.parola = request.getParameter("pass");
				utilizator.email = request.getParameter("email");
				utilizator.adresa = request.getParameter("adresa");

				System.out.println("Utilizator existent -> modificat in " + utilizator);
				if (!new UtilizatorWrapper().UpdateUtilizator(utilizatorCopy, utilizator))
					return;

				notify("Utilizatorul " + utilizator.CNP + " a fost actualizat.", out);
			}
		%>

		Mai jos poti sa iti editezi datele personale:
		<form name="user_settings" action="user_settings.jsp" method="POST">
			<table>
				<tr>
					<td>CNP:</td>
					<td><input type="text" name="cnp" value="<%=utilizator.CNP%>"></td>
				</tr>
				<tr>
					<td>Parola:</td>
					<td><input type="password" name="pass" value="<%=utilizator.parola%>"></td>
				</tr>
				<tr>
					<td>Nume:</td>
					<td><input type="text" name="nume" value="<%=utilizator.nume%>"></td>
				</tr>
				<tr>
					<td>Prenume:</td>
					<td><input type="text" name="prenume" value="<%=utilizator.prenume%>"></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><input type="text" name="email" value="<%=utilizator.email%>"></td>
				</tr>
				<tr>
					<td>Adresa:</td>
					<td><input type="text" name="adresa" value="<%=utilizator.adresa%>"></td>
				</tr>

			</table>
			<input type="hidden" name="submitted" value="true" /> <input type="submit" name="submit" class="button"
				value="Salveaza">
		</form>
		
		<jsp:include page="/include/content_footer.jsp"></jsp:include>
	</div>

	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>