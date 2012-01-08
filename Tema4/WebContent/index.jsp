<%@page import="aii.database.UtilizatorWrapper"%>
<%@page import="aii.Utilizator"%>
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
		<jsp:param value="Panou de autentificare" name="title" />
	</jsp:include>

	<!-- Continutul principal al paginii -->
	<div id="content">
		Bine ai venit pe consola de administrare! Introdu-ti datele mai jos pentru autentificare:
		<form name="login_form" action="index.jsp" method="POST">
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

		<%--Verificari daca este submit de formular --%>
		<%
			String utilizatorS = request.getParameter("user");
			String parolaS = request.getParameter("pass");

			if (utilizatorS != null && parolaS != null) {
				log("Trying authentication for: " + utilizatorS);
				session.removeAttribute("user");
				//Get the user
				UtilizatorWrapper utilizatorDAO = new UtilizatorWrapper();
				Utilizator utilizator = utilizatorDAO.getUtilizator(utilizatorS);

				if (utilizator == null) {
					error("Nu s-a gasit in baza de date un utilizator "
							+ "cu CNP-ul introdus. Va rugam incercati din nou!", out);
				}
				//Authentify the user
				else
				if (!utilizator.parola.equals(parolaS)) {
					error("Combinatia cnp/parola introdusa nu este corecta. " + "Va rugam incercati din nou!", out);
				} else {
					log("Utilizator " + utilizator + " autentificat.");
					session.setAttribute("user", utilizator);
					
					switch (utilizator.tip) {
					case ADMIN:
					case SUPER_ADMIN:
						response.sendRedirect("admin.jsp");
						break;
					case CADRU_DIDACTIC:
					case SEF_CATEDRA:
						response.sendRedirect("cadru.jsp");
						break;
					case SECRETAR:
						response.sendRedirect("secretar.jsp");
						break;
					case STUDENT:
						response.sendRedirect("student.jsp");
						break;
					default:
						notify("Tipul utilizatorului necunoscut", out);
					}
				}
			}
		%>
	</div>

	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>