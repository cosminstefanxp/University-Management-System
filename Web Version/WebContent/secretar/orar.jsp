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
		<jsp:param value="Administrare Orar - Secretar " name="title"/>
	</jsp:include>
	<!-- Continutul principal al paginii -->
	<div id="content">
		Mai jos se pot vizualiza toate repartizarile activitatilor in sali si intervale orare:
		<div class="table-box">
			<%			
			//Get the objects and prepare the table models	
			OrarCompletWrapper orareDAO=new OrarCompletWrapper();
			DisciplinaWrapper disciplineDAO=new DisciplinaWrapper();
			ArrayList<OrarComplet> objects=null;
			ObjectTableGenerator<OrarComplet> tableModel=null;
			try {
				
				//Table model for "Orare"
				objects=orareDAO.getOrare("true");
				tableModel=new ObjectTableGenerator<OrarComplet>(OrarComplet.class,
						objects,
						Constants.ADMIN_ORAR_COLUMN_FIELD_MATCH[1],
						Constants.ADMIN_ORAR_COLUMN_FIELD_MATCH[0],
						new int[]{0,1},
						new String[] {"grupa","id_activitate"}
						);
			} catch (Exception e) {
				e.printStackTrace();
			}			
			%>
			<!-- Afisare date in tabel -->
			<table>
				<%=tableModel.getHTMLTableRepresentation("edit_orar.jsp","delete_orar.jsp") %>
			</table>
			<br /> <a href="add_orar.jsp" class="button">Adauga</a>
			
		

		</div>
		
		<!-- Link catre pagina principala a utilizatorului -->
		<jsp:include page="../include/content_footer.jsp"></jsp:include>
	</div>
	
	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>