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
<title>ACS - Student</title>
<link rel="stylesheet" href="../style/main.css" type="text/css" />
</head>
<%@include file="../include/tools.jsp"%>
<% request.setAttribute("permissions", Tip.STUDENT);%>
<%@include file="../include/authentify.jsp" %>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Programare Examene - Student " name="title"/>
	</jsp:include>
	<!-- Continutul principal al paginii -->
	<div id="content">
		Mai jos se poate vizualiza programarea examenelor studentului pentru anul curent.
		<div class="table-box">
			<%
			//Prepare the data for the table model 
			//Prepare study year
			if(utilizator.titlu_grupa==null || utilizator.titlu_grupa.isEmpty())
			{
				error("Nu sunteti inregistrat la nici o grupa!",out);
				return;
			}
			if(utilizator.titlu_grupa.equals("licentiat"))
			{
				error("Sunteti licentiat!",out);
				return;
			}
			int studyYear = utilizator.titlu_grupa.charAt(1) - '0';
			System.out.println("Studentul este in anul "+studyYear);
			
			//Get the objects and prepare the table models
			ExamenWrapper exameneDAO=new ExamenWrapper();
			ArrayList<Examen> objects;
			DisciplinaWrapper disciplineDAO=new DisciplinaWrapper();
			ObjectTableGenerator<Examen> tableModel=null;
			try {
				//Disciplinele urmate
				ArrayList<Integer> discipline=disciplineDAO.obtineDisciplineUrmate(utilizator.CNP, studyYear);
				
				//Table model for "Examene"
				exameneDAO.setNameMatch(Constants.EXAMEN_STUDENT_FIELD_MATCH);
				objects=exameneDAO.getExameneParticularizat(discipline, utilizator.titlu_grupa);
				tableModel=new ObjectTableGenerator<Examen>(Examen.class,
						objects,
						Constants.VIEW_EXAMEN_STUDENT_COLUMN_FIELD_MATCH[1],
						Constants.VIEW_EXAMEN_STUDENT_COLUMN_FIELD_MATCH[0],
						new int[]{2},
						new String[] {"data"}
						);
			} catch (Exception e) {
				e.printStackTrace();
			}
			%>
			<!-- Afisare date in tabel -->
			<table>
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