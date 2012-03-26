<%@page import="aii.SituatieScolara"%>
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
		<jsp:param value="Situatie Scolara - Student " name="title"/>
	</jsp:include>
	<!-- Continutul principal al paginii -->
	<div id="content">
		Mai jos se poate vizualiza situatia scolara a studentului. Valorile 0 pot indica, de exemplu, faptul ca nota nu a fost inca trecuta in catalog sau faptul ca semestrul nu s-a incheiat.
		<div class="table-box">
			<%
			//Construim situatiile scolare pentru fiecare an
			ArrayList<SituatieScolara> situatii=new ArrayList<SituatieScolara>();
			for (int i=0;i<4;i++)
				situatii.add(SituatieScolara.obtineSituatieScolara(utilizator.CNP, i+1));
			System.out.println("Situatii scolare: "+situatii);
			
			ObjectTableGenerator<SituatieScolara> tableModel=new ObjectTableGenerator<SituatieScolara>(SituatieScolara.class,
					situatii, 
					Constants.VIEW_SITUATIE_SCOLARA_COLUMN_FIELD_MATCH[1], 
					Constants.VIEW_SITUATIE_SCOLARA_COLUMN_FIELD_MATCH[0],
					new int[]{},new String[]{});
					
				
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