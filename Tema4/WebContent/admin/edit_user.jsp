<%@page import="aii.Utilizator.Finantare"%>
<%@page import="aii.database.UtilizatorWrapper"%>
<%@page import="aii.Utilizator.Tip"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ACS - Administrator</title>
<link rel="stylesheet" href="../style/main.css" type="text/css" />
</head>
<%@include file="../include/tools.jsp"%>
<%
	request.setAttribute("permissions", Tip.ANY);
%>
<%@include file="../include/authentify.jsp"%>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Panou de Editare Utilizatori" name="title" />
	</jsp:include>

	<!-- Continutul principal al paginii -->
	<div id="content">
		<%-- Analizam datele din formular, pentru cazul unui submit. --%>
		<%
			Utilizator user = new UtilizatorWrapper().getUtilizator(request.getParameter("CNP"));
			if (request.getParameter("submitted") != null) {
				log("Avem parametrii pentru setarea proprietatilor: " + request.getParameterMap());

				//Copy the old user
				Utilizator utilizatorCopy = user.clone();

				//Create the new user
				user.tip = Tip.valueOf(request.getParameter("tip"));
				user.CNP = request.getParameter("new_cnp");
				user.nume = request.getParameter("nume");
				user.prenume = request.getParameter("prenume");
				user.parola = request.getParameter("pass");
				user.email = request.getParameter("email");
				user.adresa = request.getParameter("adresa");

				//Specific type attributes
				if(request.getParameter("titlu_grupa")!=null)
					user.titlu_grupa=request.getParameter("titlu_grupa");
				if(request.getParameter("finantare")!=null)
					user.finantare=Finantare.valueOf(request.getParameter("finantare"));
				
				System.out.println("Utilizator existent -> modificat in " + user);
				if (!new UtilizatorWrapper().UpdateUtilizator(utilizatorCopy, user))
					return;

				notify("Utilizatorul " + user.CNP + " a fost actualizat.", out);
			}
		%>

		Mai jos poti sa iti editezi datele referitoare la un utilizator:
		<form name="edit_user" action="edit_user.jsp?CNP=<%=user.CNP%>" method="POST">
			<table>
				<tr>
					<td>Tip Utilizator:</td>
					<td><select name="tip">
							<option value="<%=Tip.ADMIN%>" <%if (user.tip == Tip.ADMIN)
				out.write("selected='selected'");%>>Administrator</option>
							<option value="<%=Tip.SECRETAR%>" <%if (user.tip == Tip.SECRETAR)
				out.write("selected='selected'");%>>Secretar</option>
							<option value="<%=Tip.CADRU_DIDACTIC%>" <%if (user.tip == Tip.CADRU_DIDACTIC)
				out.write("selected='selected'");%>>Cadru
								Didactic</option>
							<option value="<%=Tip.SEF_CATEDRA%>" <%if (user.tip == Tip.SEF_CATEDRA)
				out.write("selected='selected'");%>>Sef
								de Catedra</option>
							<option value="<%=Tip.STUDENT%>" <%if (user.tip == Tip.STUDENT)
				out.write("selected='selected'");%>>Student</option>
					</select></td>
				</tr>
				<tr>
					<td>CNP:</td>
					<td><input type="text" name="new_cnp" value="<%=user.CNP%>"></td>
				</tr>
				<tr>
					<td>Parola:</td>
					<td><input type="password" name="pass" value="<%=user.parola%>"></td>
				</tr>
				<tr>
					<td>Nume:</td>
					<td><input type="text" name="nume" value="<%=user.nume%>"></td>
				</tr>
				<tr>
					<td>Prenume:</td>
					<td><input type="text" name="prenume" value="<%=user.prenume%>"></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><input type="text" name="email" value="<%=user.email%>"></td>
				</tr>
				<tr>
					<td>Adresa:</td>
					<td><input type="text" name="adresa" value="<%=user.adresa%>"></td>
				</tr>
				<tr>
					<td colspan="2"><i>*Pentru modificarea tuturor caracteristicilor unui utilizator, daca este necesara schimbarea
						tipului, atributele caracterstice doar acelui tip sunt accesibile dupa salvarea noului tip.</i></td>
				</tr>
				<%if(user.tip==Tip.STUDENT) {%>
				<tr>
					<td>Grupa:</td>
					<td><input type="text" name="titlu_grupa" value="<%=user.titlu_grupa%>"></td>
				</tr>
				<tr>
					<td>Finantare:</td>
					<td><input type="text" name="finantare" value="<%=user.finantare%>"></td>
				</tr>
				<%}
				if(user.tip==Tip.CADRU_DIDACTIC || user.tip==Tip.SEF_CATEDRA) {%>
				<tr>
					<td>Titlu:</td>
					<td><input type="text" name="titlu_grupa" value="<%=user.titlu_grupa%>"></td>
				</tr>
				<%} %>
				
				


			</table>
			<input type="hidden" name="submitted" value="true" /> <input type="submit" name="submit" class="button"
				value="Salveaza">
		</form>

		<jsp:include page="/include/content_footer.jsp">
			<jsp:param value="users.jsp" name="back_link"/>
		</jsp:include>
	</div>

	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>