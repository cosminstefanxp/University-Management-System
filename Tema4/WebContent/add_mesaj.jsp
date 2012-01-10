<%@page import="aii.Mesaj"%>
<%@page import="aii.database.Constants"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aii.Utilizator.Finantare"%>
<%@page import="aii.database.UtilizatorWrapper"%>
<%@page import="aii.Utilizator.Tip"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ACS - Mesaje </title>
<link rel="stylesheet" href="style/main.css" type="text/css" />
</head>
<%@include file="include/tools.jsp"%>
<%request.setAttribute("permissions", Tip.ANY);%>
<%@include file="include/authentify.jsp"%>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Panou de Trimitere Mesaj" name="title" />
	</jsp:include>

	<!-- Continutul principal al paginii -->
	<div id="content">
		<%-- Analizam datele din formular, pentru cazul unui submit. --%>
		<%
			if (request.getParameter("submitted") != null) {
				log("Avem parametrii pentru setarea proprietatilor: " + request.getParameterMap());

				
				//Verificare parametrii
				if(request.getParameter("cnp_destinatie")==null ||
						request.getParameter("subiect")==null ||
						request.getParameter("mesaj")==null )
				{
					error("Unul din campurile principale necesare nu a fost completat.",out);
				}
				else
				{
					Mesaj mesaj=new Mesaj();
	
					//Create the new user
					mesaj.subiect=request.getParameter("subiect");
					mesaj.cnpDestinatie=request.getParameter("cnp_destinatie");
					mesaj.mesaj=request.getParameter("mesaj");
					mesaj.cnpSursa=utilizator.CNP;
					
					System.out.println("Mesaj nou creat: " + user);
					if (!new UtilizatorWrapper().insertUtilizator(user))
					{
						error("O eroare a avut loc in momentul crearii utilizatorului in baza de date.",out);
						return;
					}
	
					notify("Utilizatorul " + user + " a fost creat.", out);
					}
			}
		%>

		Mai jos poti sa setezi datele:
		<form name="add_mesaj" action="add_mesaj.jsp" method="POST">
		<%
			ArrayList<Utilizator> utilizatori=new UtilizatorWrapper().getObjects(Constants.USER_TABLE,"tip=\'CADRU_DIDACTIC\' OR tip=\'SEF_CATEDRA\' OR tip=\'STUDENT\'");
		%>
			<table>
				<tr>
				<tr>
					<td>Destinatar:</td>
					<td><select name="cnp_destinatie">
						<% for(Utilizator u:utilizatori) {%>
						<option value="<%=u.CNP%>" >
							<%=u.nume+" "+u.prenume+" - "+u.CNP %>
						</option>
						<%} %>
					</select></td>
				</tr>
				<tr>
					<td>Subiect:</td>
					<td><input type="text" name="subiect"></td>
				</tr>
				<tr>
					<td>Mesaj:</td>
					<td><textarea name="mesaj"></textarea></td>
				</tr>

			</table>
			<input type="hidden" name="submitted" value="true" /> <input type="submit" name="submit" class="button"
				value="Salveaza">
		</form>

		<jsp:include page="/include/content_footer.jsp">
			<jsp:param value="mesaje.jsp" name="back_link"/>
		</jsp:include>
	</div>

	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>