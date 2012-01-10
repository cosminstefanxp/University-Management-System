<%@page import="aii.database.OrarWrapper"%>
<%@page import="aii.Orar.Ziua"%>
<%@page import="aii.Activitate"%>
<%@page import="aii.database.Constants"%>
<%@page import="aii.database.ActivitateWrapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aii.Orar.Frecventa"%>
<%@page import="aii.OrarComplet"%>
<%@page import="aii.database.OrarCompletWrapper"%>
<%@page import="aii.Orar"%>
<%@page import="aii.database.ExamenWrapper"%>
<%@page import="aii.Examen"%>
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
<title>ACS - Secretar</title>
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
		<jsp:param value="Panou de Adaugare Orar" name="title" />
	</jsp:include>

	<!-- Continutul principal al paginii -->
	<div id="content">
		<%-- Analizam datele din formular, pentru cazul unui submit. --%>
		<%
			if (request.getParameter("submitted") != null) {
				log("Avem parametrii pentru setarea proprietatilor: " + request.getParameterMap());

				//Check parameters
				if (request.getParameter("id_activitate_new").isEmpty() || request.getParameter("grupa_new").isEmpty()
						|| request.getParameter("sala").isEmpty() || request.getParameter("ziua").isEmpty()
						|| request.getParameter("ora").isEmpty() || request.getParameter("durata").isEmpty()) {
					error("Completati toti parametrii necesari!", out);
				} else {
					try {

						//Creaza noua disciplina
						Orar object=new Orar();

						object.idActivitate=Integer.parseInt(request.getParameter("id_activitate_new"));
						object.grupa=request.getParameter("grupa_new");
						object.sala = request.getParameter("sala");
						object.zi = Ziua.valueOf(request.getParameter("ziua"));
						object.ora = Integer.parseInt(request.getParameter("ora"));
						object.durata = Integer.parseInt(request.getParameter("durata"));
						object.frecventa = Frecventa.valueOf(request.getParameter("frecventa"));

						//Actualizeaza noua disciplina
						System.out.println("Orar nou " + object);
						if (!new OrarWrapper().insertOrar(object)) {
							error("A fost intampinata o problema la introducerea intrarii in baza de date! Aceasta deja exista!",
									out);
						} else
						{
							notify("Orarul " + object + " a fost creat. Faceti refresh pentru noile valori in camp.", out);
						}

					} catch (Exception ex) {
						error(ex.getMessage(), out);
					}
				}
			}
		%>

		Mai jos poti sa editezi datele referitoare la orar:
		<form name="edit_orar" action="add_orar.jsp" method="POST">
			<%
				//Obtinem disciplinele la care preda acest cadru didactic
				ActivitateWrapper activitateDAO = new ActivitateWrapper();
				activitateDAO.setNameMatch(Constants.ACTIVITATE_FIELD_MATCH_MEDIUM);
				ArrayList<Activitate> activitati = activitateDAO.getActivitatiJoined(
						"a.id, a.cod_disciplina, d.denumire, u.nume", Constants.ACTIVITATE_TABLE + " a, "
								+ Constants.USER_TABLE + " u," + Constants.DISCIPLINA_TABLE + " d",
						"d.cod=a.cod_disciplina AND u.cnp=a.cnp_cadru_didactic");
			%>
			<table>
				<tr>
					<td>Activitate:</td>
					<td>
						<select name="id_activitate_new">
						<%
							for (Activitate a : activitati) {
						%>
						<option value="<%=a.id%>"><%=a.id + " - " + a.denumireDisciplina+" - "+a.numeCadruDidactic%>
						</option>
						<%
							}
						%>
						</select>
					</td>
				</tr>
				<tr>
					<td>Grupa:</td>
					<td><input type="text" name="grupa_new"></td>
				</tr>
				<tr>
					<td>Sala:</td>
					<td><input type="text" name="sala" ></td>
				</tr>

				<tr>
					<td>Ziua:</td>
					<td><input type="text" name="ziua" ></td>
				</tr>
				<tr>
					<td>Ora:</td>
					<td><input type="text" name="ora" ></td>
				</tr>
				<tr>
					<td>Durata:</td>
					<td><input type="text" name="durata" ></td>
				</tr>
				<tr>
					<td>Frecventa:</td>
					<td><select name="frecventa">
							<option value="<%=Frecventa.Saptamanal%>">Saptamanal</option>
							<option value="<%=Frecventa.Pare%>" >Pare</option>
							<option value="<%=Frecventa.Impare%>">Impare</option>
					</select></td>
				</tr>

			</table>
			<input type="hidden" name="submitted" value="true" /> <input type="submit" name="submit" class="button"
				value="Salveaza">
		</form>

		<jsp:include page="/include/content_footer.jsp">
			<jsp:param value="orar.jsp" name="back_link" />
		</jsp:include>
	</div>

	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>