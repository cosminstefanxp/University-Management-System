<%@page import="aii.Utilizator.Taxa"%>
<%@page import="aii.Utilizator.Finantare"%>
<%@page import="aii.database.UtilizatorWrapper"%>
<%@page import="aii.Utilizator.Tip"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ACS - Student</title>
<link rel="stylesheet" href="../style/main.css" type="text/css" />
</head>
<%@include file="../include/tools.jsp"%>
<%
	request.setAttribute("permissions", Tip.SECRETAR);
%>
<%@include file="../include/authentify.jsp"%>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Panou de Editare Studenti" name="title" />
	</jsp:include>

	<!-- Continutul principal al paginii -->
	<div id="content">
		<%-- Analizam datele din formular, pentru cazul unui submit. --%>
		<%
			Utilizator user = new UtilizatorWrapper().getUtilizator(request.getParameter("CNP"));
			if (request.getParameter("submitted") != null) {
				log("Avem parametrii pentru setarea proprietatilor: " + request.getParameterMap());

				try{
					
				//Copy the old user
				Utilizator utilizatorCopy = user.clone();

				//Create the new user
				user.titlu_grupa=request.getParameter("grupa");
				user.finantare=Finantare.valueOf(request.getParameter("finantare"));
				user.taxa=Taxa.valueOf(request.getParameter("taxa"));
				if(user.finantare==Finantare.Buget)
					user.taxa=Taxa.Achitata;
				
				System.out.println("Utilizator existent -> modificat in " + user);
				if (!new UtilizatorWrapper().UpdateUtilizator(utilizatorCopy, user))
					error("A fost intampinata o problema la introducerea intrarii in baza de date! Aceasta deja exista!",
							out);
				else
					notify("Utilizatorul " + user + " a fost actualizat.", out);

				utilizator=user;
				} catch (Exception ex) {
					error(ex.getMessage(), out);
				}
			
			}
		%>

		Mai jos poti sa iti editezi datele referitoare grupa sau starea taxei pentru un student:
		<form name="edit_user" action="edit_student.jsp?CNP=<%=user.CNP%>" method="POST">
			<table>
				<tr>
					<td>Nume:</td>
					<td><input type="text" disabled="disabled" name="nume" value="<%=user.nume%>"></td>
				</tr>
				<tr>
					<td>Prenume:</td>
					<td><input type="text" disabled="disabled" name="prenume" value="<%=user.prenume%>"></td>
				</tr>
				<tr>
					<td>Grupa:</td>
					<td><input type="text" name="grupa" value="<%=user.titlu_grupa%>"></td>
				</tr>
				<tr>
					<td>Finantare:</td>
					<td><select name="finantare">
						<option value="<%=Finantare.Buget%>" <% if(user.finantare==Finantare.Buget) out.write("selected=''"); %>>Buget</option>
						<option value="<%=Finantare.Taxa%>" <% if(user.finantare==Finantare.Taxa) out.write("selected=''"); %>>Taxa</option>
					</select> </td>
				</tr>
				<tr>
					<td>Taxa:*</td>
					<td><select name="taxa">
						<option value="<%=Taxa.Achitata%>" <% if(user.taxa==Taxa.Achitata) out.write("selected=''"); %>>Achitata</option>
						<option value="<%=Taxa.Neachitata%>" <% if(user.taxa==Taxa.Neachitata) out.write("selected=''"); %>>Neachitata</option>
					</select> </td>
				</tr>
				<tr>
					<td colspan="2"><i>*Daca studentul este la buget, taxa se va seta automat ca fiind achitata.</i></td>
				</tr>
				
				
			</table>
			<input type="hidden" name="submitted" value="true" /> <input type="submit" name="submit" class="button"
				value="Salveaza">
		</form>

		<jsp:include page="/include/content_footer.jsp">
			<jsp:param value="studenti.jsp" name="back_link"/>
		</jsp:include>
	</div>

	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>