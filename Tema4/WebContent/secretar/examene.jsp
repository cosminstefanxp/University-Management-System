<%@page import="aii.Examen"%>
<%@page import="aii.database.ExamenWrapper"%>
<%@page import="aii.OrarComplet"%>
<%@page import="aii.database.OrarCompletWrapper"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="sun.util.calendar.Gregorian"%>
<%@page import="java.util.Calendar"%>
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
<title>ACS - Secretar</title>
<link rel="stylesheet" href="../style/main.css" type="text/css" />
</head>
<%@include file="../include/tools.jsp"%>
<% request.setAttribute("permissions", Tip.SECRETAR);%>
<%@include file="../include/authentify.jsp" %>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Programare Examene - Secretar " name="title"/>
	</jsp:include>
	<!-- Continutul principal al paginii -->
	<div id="content">
		Mai jos se poate vizualiza programarea examenelor:
		<div class="table-box">
			<%			
			//Get the objects and prepare the table models
			ExamenWrapper exameneDAO=new ExamenWrapper();
			ArrayList<Examen> objects;
			DisciplinaWrapper disciplineDAO=new DisciplinaWrapper();
			ObjectTableGenerator<Examen> tableModel=null;
			try {
	
				//Table model for "Examene"
				exameneDAO.setNameMatch(Constants.EXAMEN_FIELD_MATCH_FULL);
				objects=exameneDAO.getExameneJoined("e.data, e.ora, d.denumire, e.sala, e.grupa, e.cod_disciplina",
					"disciplina d, examen e",
					"d.cod=e.cod_disciplina");
				tableModel=new ObjectTableGenerator<Examen>(Examen.class,
						objects,
						Constants.ADMIN_EXAMEN_COLUMN_FIELD_MATCH[1],
						Constants.ADMIN_EXAMEN_COLUMN_FIELD_MATCH[0],
						new int[]{0,1,3},
						new String[] {"grupa","cod_disciplina","data"}
						);
			} catch (Exception e) {
				e.printStackTrace();
			}
			%>
			<!-- Afisare date in tabel -->
			<table style="width:100%">
				<%=tableModel.getHTMLTableRepresentation(null,null) %>
			</table>
			<br/>
		

		</div>
		
		<!-- Link catre pagina principala a utilizatorului -->
		<jsp:include page="../include/content_footer.jsp"></jsp:include>
	</div>
	
	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>