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
	request.setAttribute("permissions", Tip.ADMIN);
%>
<%@include file="../include/authentify.jsp"%>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Panou de Adaugare Utilizatori" name="title" />
	</jsp:include>

	<!-- Continutul principal al paginii -->
	<div id="content">
		<%-- Analizam datele din formular, pentru cazul unui submit. --%>
		<%
			if (request.getParameter("submitted") != null) {
				log("Avem parametrii pentru setarea proprietatilor: " + request.getParameterMap());

				
				//Verificare parametrii
				if(request.getParameter("tip")==null ||
						request.getParameter("cnp")==null ||
						request.getParameter("nume")==null ||
						request.getParameter("prenume")==null ||
						request.getParameter("email")==null ||
						request.getParameter("pass")==null)
				{
					error("Unul din campurile principale necesare nu a fost completat.",out);
				}
				else
				{
					Utilizator user=new Utilizator();
	
					//Create the new user
					user.tip = Tip.valueOf(request.getParameter("tip"));
					user.CNP = request.getParameter("cnp");
					user.nume = request.getParameter("nume");
					user.prenume = request.getParameter("prenume");
					user.parola = request.getParameter("pass");
					user.email = request.getParameter("email");
					user.adresa = request.getParameter("adresa");
	
	
					//Specific type attributes
					if(user.tip==Tip.STUDENT || user.tip==Tip.CADRU_DIDACTIC || user.tip==Tip.SEF_CATEDRA)
						user.titlu_grupa=request.getParameter("titlu_grupa");
					else
						user.titlu_grupa="";
					if(user.tip==Tip.STUDENT)
						user.finantare=Finantare.valueOf(request.getParameter("finantare"));
					else
						user.titlu_grupa="";
					
					System.out.println("Utilizator nou creat: " + user);
					if (!new UtilizatorWrapper().insertUtilizator(user))
					{
						error("O eroare a avut loc in momentul crearii utilizatorului in baza de date.",out);
						return;
					}
	
					notify("Utilizatorul " + user + " a fost creat.", out);
					}
			}
		%>

		Mai jos poti sa iti editezi datele referitoare la un utilizator:
		<form name="add_user" action="add_user.jsp" method="POST">
			<table>
				<tr>
					<td>Tip Utilizator:</td>
					<td><select name="tip">
							<option value="<%=Tip.ADMIN%>">Administrator</option>
							<option value="<%=Tip.SECRETAR%>">Secretar</option>
							<option value="<%=Tip.CADRU_DIDACTIC%>">Cadru Didactic</option>
							<option value="<%=Tip.SEF_CATEDRA%>">Sef de Catedra</option>
							<option value="<%=Tip.STUDENT%>">Student</option>
					</select></td>
				</tr>
				<tr>
					<td>CNP:</td>
					<td><input type="text" name="cnp"></td>
				</tr>
				<tr>
					<td>Parola:</td>
					<td><input type="password" name="pass"></td>
				</tr>
				<tr>
					<td>Nume:</td>
					<td><input type="text" name="nume"></td>
				</tr>
				<tr>
					<td>Prenume:</td>
					<td><input type="text" name="prenume"></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><input type="text" name="email"></td>
				</tr>
				<tr>
					<td>Adresa:</td>
					<td><input type="text" name="adresa"></td>
				</tr>
				<tr>
					<td colspan="2"><i>*In cazul in care tipul selectat anterior nu necesita campurile de mai jos, acestea vor fi ignorate.</i><br/></td>
				</tr>
				<tr>
					<td>Titlu (Sef de catedra sau Cadru didactic)/Grupa(Student)*:</td>
					<td><input type="text" name="titlu_grupa"></td>
				</tr>
				<tr>
					<td>Finantare (Student)*:</td>
					<td><select name="finantare">
						<option value="Buget">Buget</option>
						<option value="Taxa">Taxa</option>
					</select></td>
				</tr>

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