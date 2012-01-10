<%@page import="com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aii.database.Constants"%>
<%@page import="aii.database.ActivitateWrapper"%>
<%@page import="aii.Activitate.TipActivitate"%>
<%@page import="aii.Activitate"%>
<%@page import="aii.Disciplina.Examinare"%>
<%@page import="aii.Disciplina.TipDisciplina"%>
<%@page import="aii.Disciplina"%>
<%@page import="aii.database.DisciplinaWrapper"%>
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
	request.setAttribute("permissions", Tip.SEF_CATEDRA);
%>
<%@include file="../include/authentify.jsp"%>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Panou de Adaugare Activitati de Predare" name="title" />
	</jsp:include>

	<!-- Continutul principal al paginii -->
	<div id="content">
		<%-- Analizam datele din formular, pentru cazul unui submit. --%>
		<%
			Activitate activitate=new ActivitateWrapper().getActivitate(request.getParameter("id"));
			if (request.getParameter("submitted") != null) {
				log("Avem parametrii pentru setarea proprietatilor: " + request.getParameterMap());

				//Check parameters
				if (request.getParameter("cnp_cadru_didactic").isEmpty() || request.getParameter("cod_disciplina").isEmpty()
						|| request.getParameter("tip").isEmpty()) {
					error("Completati toti parametrii necesari!", out);
				} else {
					try {

						//Creaza noul obiect
						Activitate object=new Activitate();
						object.cnpCadruDidactic=request.getParameter("cnp_cadru_didactic");
						object.codDisciplina=Integer.parseInt(request.getParameter("cod_disciplina"));
						object.tip=TipActivitate.valueOf(request.getParameter("tip"));
						object.id=activitate.id;

						//Actualizeaza noul obiect in baza de date
						System.out.println("Activitate modificata: " + object);
						if (!new ActivitateWrapper().updateActivitate(activitate, object)) {
							error("A fost intampinata o problema la introducerea intrarii in baza de date! Aceasta deja exista!",
									out);
						} else
							notify("Activitatea " + object + " a fost actualizata.", out);
						//Copiem noua activitate
						activitate=object;
						
					} catch (Exception ex) {
						error(ex.getMessage(), out);
					}
				}
			}
		%>

		Mai jos poti sa editezi datele referitoare la disciplina:
		<%
		//Pregatim datele referitoare la discipline si la profesori
		ArrayList<Disciplina> discipline=new DisciplinaWrapper().getObjects(Constants.DISCIPLINA_TABLE,"cod=cod");
		ArrayList<Utilizator> utilizatori=new UtilizatorWrapper().getObjects(Constants.USER_TABLE,"tip=\'CADRU_DIDACTIC\' OR tip=\'SEF_CATEDRA\'");%>
		<form name="add_activitate" action="edit_activitate.jsp?<%=request.getQueryString() %>" method="POST">
			<table>
				<tr>
					<td>Disciplina:</td>
					<td><select name="cod_disciplina">
						<% for(Disciplina d:discipline) {%>
						<option value="<%=d.cod%>" <% if(activitate.codDisciplina==d.cod) out.write("selected='selected'"); %>>
							<%=d.cod+" - "+d.denumire %>
						</option>
						<%} %>
					</select></td>
				</tr>
				<tr>
					<td>Cadru Didactic:</td>
					<td><select name="cnp_cadru_didactic">
						<% for(Utilizator u:utilizatori) {%>
						<option value="<%=u.CNP%>"  <% if(activitate.cnpCadruDidactic.equals(u.CNP)) out.write("selected='selected'"); %>>
							<%=u.nume+" "+u.prenume+" - "+u.CNP %>
						</option>
						<%} %>
					</select></td>
				</tr>
				<tr>
					<td>Tip:</td>
					<td>
						<select name="tip">
							<option value="<%=TipActivitate.Curs%>"  <% if(activitate.tip==TipActivitate.Curs) out.write("selected='selected'"); %>>Curs</option>
							<option value="<%=TipActivitate.Laborator%>" <% if(activitate.tip==TipActivitate.Laborator) out.write("selected='selected'"); %>>Laborator</option>
							<option value="<%=TipActivitate.Seminar%>" <% if(activitate.tip==TipActivitate.Seminar) out.write("selected='selected'"); %>>Seminar</option>
							<option value="<%=TipActivitate.Proiect%>" <% if(activitate.tip==TipActivitate.Proiect) out.write("selected='selected'"); %>>Proiect</option>
						</select>
					</td>
				</tr>
				
				
			</table>
			<input type="hidden" name="submitted" value="true" /> <input type="submit" name="submit" class="button"
				value="Salveaza">
		</form>

		<jsp:include page="/include/content_footer.jsp">
			<jsp:param value="activitati.jsp" name="back_link"/>
		</jsp:include>
	</div>

	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>