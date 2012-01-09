<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Date"%>
<%@page import="com.mysql.jdbc.Util"%>
<%@page import="aii.Activitate"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aii.database.Constants"%>
<%@page import="aii.database.ActivitateWrapper"%>
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
		<jsp:param value="Panou de Adaugare Nota Catalog" name="title" />
	</jsp:include>

	<!-- Continutul principal al paginii -->
	<div id="content">
		<%-- Analizam datele din formular, pentru cazul unui submit. --%>
		<%
			if (request.getParameter("submitted") != null) {
				log("Avem parametrii pentru setarea notei: " + request.getParameterMap());
	
				//Verificari campuri
				if(request.getParameter("cnp_student")==null || 
						request.getParameter("cod_disciplina").isEmpty() ||
						request.getParameter("nota").isEmpty() || 
						request.getParameter("data").isEmpty())
				{
					error("Completati toti parametrii necesari!",out);
				}
				else
				{
					try{
					
					//Create the new nota
					NotaCatalog nota=new NotaCatalog();
					nota.cnpStudent=request.getParameter("cnp_student");
					nota.codDisciplina=Integer.parseInt(request.getParameter("cod_disciplina"));
					SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
					nota.data=new Date(dateFormat.parse(request.getParameter("data")).getTime());
					nota.nota=Integer.parseInt(request.getParameter("nota"));
					if(nota.nota>10)
						nota.nota=10;
					if(nota.nota<1)
						nota.nota=1;
							
					System.out.println("Nota existenta -> modificata in " + nota);
					if (!new NotaCatalogWrapper().insertNotaCatalog(nota))
					{
						error("A fost intampinata o problema la introducerea notei in catalog! Aceasta deja exista!",out);
					}
					else	
						notify("Nota " + nota + " a fost creata.", out);
					}
					catch(Exception ex)
					{
						error(ex.getMessage(),out);
					}
				}
			}
		%>

		Mai jos poti sa editezi datele referitoare la o nota:
		<%  //Obtinem disciplinele la care preda acest cadru didactic
			ActivitateWrapper activitateDAO=new ActivitateWrapper();
			activitateDAO.setNameMatch(Constants.ACTIVITATE_FIELD_MATCH_SHORT);
			ArrayList<Activitate> activitati=activitateDAO.getActivitatiJoined("a.id, a.cod_disciplina, d.denumire",
					Constants.ACTIVITATE_TABLE+" a, "+Constants.DISCIPLINA_TABLE+" d",
					"a.cnp_cadru_didactic='"+utilizator.CNP+"\' AND a.tip=\'"+Activitate.TipActivitate.Curs+"\' AND d.cod=a.cod_disciplina");
			
			//Obtinem studentii care sunt la grupe la activitati asociate disciplinelor la care preda acest cadru didactic
			UtilizatorWrapper utilizatoriDAO=new UtilizatorWrapper();
			utilizatoriDAO.setNameMatch(Constants.USER_FIELD_MATCH_SHORT);
			ArrayList<Utilizator> utilizatori=utilizatoriDAO.getUtilizatoriJoined("u.cnp, u.nume, u.prenume",
					Constants.ACTIVITATE_TABLE+" a, "+Constants.DISCIPLINA_TABLE+" d, "+Constants.USER_TABLE+" u," +Constants.ORAR_TABLE+" o",
					"a.cnp_cadru_didactic='"+utilizator.CNP+"\' AND a.tip=\'"+Activitate.TipActivitate.Curs+"\' AND d.cod=a.cod_disciplina AND "+
					"o.id_activitate=a.id AND o.grupa=u.titlu_grupa");
			
		
		%>
		<form name="edit_nota" action="<%=request.getRequestURI() %>" method="POST">
			<table>
				<tr>
					<td>Disciplina:</td>
					<td>
						<select name="cod_disciplina">
						<% for(Activitate a:activitati) {%>
						<option value="<%=a.codDisciplina%>" >
							<%=a.codDisciplina+" - "+a.denumireDisciplina %>
						</option>
						<%} %>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>Student:</td>
					<td>
						<select name="cnp_student">
						<% for(Utilizator u:utilizatori) {%>
						<option value="<%=u.CNP%>" ><%=u.CNP+" - "+u.prenume+" "+u.nume %></option>
						<%} %>
						</select>
					</td>
				</tr>
				<tr>
					<td>Data (AAAA-LL-ZZ):</td>
					<td><input type="text" name="data"></td>
				</tr>
				<tr>
					<td>Nota:</td>
					<td><input type="text" name="nota"></td>
				</tr>


			</table>
			<input type="hidden" name="submitted" value="true" /> <input type="submit" name="submit" class="button"
				value="Salveaza">
		</form>

		<jsp:include page="/include/content_footer.jsp">
			<jsp:param value="catalog.jsp" name="back_link" />
		</jsp:include>
	</div>

	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>