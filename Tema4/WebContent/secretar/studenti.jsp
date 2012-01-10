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
		<jsp:param value="Secretar - Pagina editare studenti" name="title"/>
	</jsp:include>
	<!-- Continutul principal al paginii -->
	<div id="content">
		Mai jos se pot vizualiza studentii din sistem si se pot modifica formatiile de studiu si situatia taxelor:
		<div class="table-box">
			<%
			//Prepare the data for the table model 
			ArrayList<Utilizator> objects;
			ObjectTableGenerator<Utilizator> tableModel=null;
			UtilizatorWrapper dao=new UtilizatorWrapper();
			//Get the objects		
			try {
				objects=dao.getUtilizatori("tip='STUDENT'");
				tableModel=new ObjectTableGenerator<Utilizator>(Utilizator.class,
						objects,
						Constants.ADMIN_STUDENT_COLUMN_FIELD_MATCH[1],
						Constants.ADMIN_STUDENT_COLUMN_FIELD_MATCH[0],
						new int[] {0},
						new String[] {"CNP"});
			} catch (Exception e) {
				error(e.getMessage(), out);
				e.printStackTrace();
			}			
			%>
			<!-- Afisare date in tabel -->
			<table style="width:100%;">
				<%=tableModel.getHTMLTableRepresentation("edit_student.jsp",null) %>
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