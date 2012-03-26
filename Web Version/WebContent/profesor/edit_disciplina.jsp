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
		<jsp:param value="Panou de Editare Discipline" name="title" />
	</jsp:include>

	<!-- Continutul principal al paginii -->
	<div id="content">
		<%-- Analizam datele din formular, pentru cazul unui submit. --%>
		<%
			Disciplina disciplina = new DisciplinaWrapper().getDisciplina(request.getParameter("cod"));
			if (request.getParameter("submitted") != null) {
				log("Avem parametrii pentru setarea proprietatilor: " + request.getParameterMap());

				//Check parameters
				if (request.getParameter("denumire").isEmpty() || request.getParameter("tip").isEmpty()
						|| request.getParameter("examinare").isEmpty() || request.getParameter("nr_ore").isEmpty()
						|| request.getParameter("pct_credit").isEmpty() || request.getParameter("semestru").isEmpty()
						|| request.getParameter("an_studiu").isEmpty()) {
					error("Completati toti parametrii necesari!", out);
				} else {
					try {

						//Creaza noua disciplina
						Disciplina object = new Disciplina();
						object.denumire = request.getParameter("denumire");

						object.tip = TipDisciplina.valueOf(request.getParameter("tip"));
						object.examinare = Examinare.valueOf(request.getParameter("examinare"));
						object.anStudiu = Integer.parseInt(request.getParameter("an_studiu"));
						object.nrOre = Integer.parseInt(request.getParameter("nr_ore"));
						object.pctCredit = Integer.parseInt(request.getParameter("pct_credit"));
						object.semestru = Integer.parseInt(request.getParameter("semestru"));
						object.cod = disciplina.cod;
						if (object.tip == TipDisciplina.Optional)
							object.grup = Integer.parseInt(request.getParameter("grup"));
						else
							object.grup = 0;

						//Actualizeaza noua disciplina
						System.out.println("Disciplina existenta -> modificata in " + object);
						if (!new DisciplinaWrapper().updateDisciplina(disciplina, object)) {
							error("A fost intampinata o problema la introducerea intrarii in baza de date! Aceasta deja exista!",
									out);
						} else
							notify("Disciplina " + object + " a fost actualizata.", out);
						disciplina=object;
						
					} catch (Exception ex) {
						error(ex.getMessage(), out);
					}
				}
			}
		%>

		Mai jos poti sa editezi datele referitoare la disciplina:
		<form name="edit_disciplina" action="edit_disciplina.jsp?<%=request.getQueryString() %>" method="POST">
			<table>
				<tr>
					<td>Cod disciplina:</td>
					<td><input type="text" name="new_cod" value="<%=disciplina.cod%>" disabled="disabled"></td>
				</tr>
				<tr>
					<td>Denumire:</td>
					<td><input type="text" name="denumire" value="<%=disciplina.denumire%>"></td>
				</tr>
				<tr>
					<td>Tip:</td>
					<td>
						<select name="tip">
							<option value="<%=TipDisciplina.Obligatoriu%>" <%if (disciplina.tip == TipDisciplina.Obligatoriu)
				out.write("selected='selected'");%>>Obligatoriu</option>
							<option value="<%=TipDisciplina.Optional%>"<%if (disciplina.tip == TipDisciplina.Optional)
				out.write("selected='selected'");%>>Optional</option>
							<option value="<%=TipDisciplina.Facultativ%>"<%if (disciplina.tip == TipDisciplina.Facultativ)
				out.write("selected='selected'");%>>Facultativ</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Examinare:</td>
					<td>
						<select name="examinare">
							<option value="<%=Examinare.Examen%>" <%if (disciplina.examinare == Examinare.Examen)
				out.write("selected='selected'");%>>Examen</option>
							<option value="<%=Examinare.Colocviu%>"<%if (disciplina.examinare == Examinare.Colocviu)
				out.write("selected='selected'");%>>Colocviu</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Numar ore:</td>
					<td><input type="text" name="nr_ore" value="<%=disciplina.nrOre%>"></td>
				</tr>
				<tr>
					<td>Puncte Credit:</td>
					<td><input type="text" name="pct_credit" value="<%=disciplina.pctCredit%>"></td>
				</tr>
				<tr>
					<td>An Studiu:</td>
					<td><input type="text" name="an_studiu" value="<%=disciplina.anStudiu%>"></td>
				</tr>
				<tr>
					<td>Semestru:</td>
					<td><input type="text" name="semestru" value="<%=disciplina.semestru%>"></td>
				</tr>
				<tr>
					<td>Grupul de alegere:*</td>
					<td><input type="text" name="grup" value="<%=disciplina.grup%>"></td>
				</tr>
				<tr>
					<td colspan="2"><i>*De campul grup se va tine cont doar daca tipul selectat este Facultativ sau Optional</i></td>
				</tr>
				
				
			</table>
			<input type="hidden" name="submitted" value="true" /> <input type="submit" name="submit" class="button"
				value="Salveaza">
		</form>

		<jsp:include page="/include/content_footer.jsp">
			<jsp:param value="discipline.jsp" name="back_link"/>
		</jsp:include>
	</div>

	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>