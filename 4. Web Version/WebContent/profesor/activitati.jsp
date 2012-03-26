<%@page import="aii.database.ActivitateWrapper"%>
<%@page import="aii.Activitate"%>
<%@page import="aii.database.DisciplinaWrapper"%>
<%@page import="aii.Disciplina"%>
<%@page import="java.sql.SQLException"%>
<%@page import="aii.database.Constants"%>
<%@page import="aii.gui.ObjectTableGenerator"%>
<%@page import="aii.database.UtilizatorWrapper"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ACS - Cadru Didactic</title>
<link rel="stylesheet" href="../style/main.css" type="text/css" />
</head>
<%@include file="../include/tools.jsp"%>
<% request.setAttribute("permissions", Tip.SEF_CATEDRA);%>
<%@include file="../include/authentify.jsp" %>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Activitati de Predare - Sef de Catedra " name="title"/>
	</jsp:include>
	<!-- Continutul principal al paginii -->
	<div id="content">
		Mai jos se pot vizualiza activitatile din sistem:
		<div class="table-box">
			<%
			//Prepare the data for the table model 
			ArrayList<Activitate> objects;
			ObjectTableGenerator<Activitate> tableModel=null;
			ActivitateWrapper dao=new ActivitateWrapper();
			//Get the objects		
			try {
				//Special Field Match to join to get more details
				dao.setNameMatch(Constants.ACTIVITATE_FIELD_MATCH_FULL);
				objects=dao.getActivitatiJoined("a.id, a.cod_disciplina, a.cnp_cadru_didactic, a.tip, d.denumire, concat(u.nume,concat(\" \",u.prenume)) nume", 
						Constants.ACTIVITATE_TABLE+" a, "+Constants.DISCIPLINA_TABLE+" d, "+Constants.USER_TABLE+" u",
						"a.cod_disciplina=d.cod AND u.cnp=a.cnp_cadru_didactic");
				tableModel=new ObjectTableGenerator<Activitate>(Activitate.class,
						objects,
						Constants.ADMIN_ACTIVITATE_COLUMN_FIELD_MATCH[1],
						Constants.ADMIN_ACTIVITATE_COLUMN_FIELD_MATCH[0],
						new int[] {0},
						new String[] {"id"});
			} catch (Exception e) {
				error(e.getMessage(), out);
				e.printStackTrace();
			}			
			%>
			<!-- Afisare date in tabel -->
			<table>
				<%=tableModel.getHTMLTableRepresentation("edit_activitate.jsp","delete_activitate.jsp") %>
			</table>
			<br/>
			<a href="add_activitate.jsp" class="button">Adauga</a>


		</div>
		
		<!-- Link catre pagina principala a utilizatorului -->
		<jsp:include page="../include/content_footer.jsp"></jsp:include>
	</div>
	
	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>