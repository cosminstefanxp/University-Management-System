<%@page import="aii.database.MesajWrapper"%>
<%@page import="aii.Mesaj"%>
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
<title>ACS - Mesaje</title>
<link rel="stylesheet" href="style/main.css" type="text/css" />
</head>
<%@include file="include/tools.jsp"%>
<%request.setAttribute("permissions", Tip.ANY);%>
<%@include file="include/authentify.jsp"%>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Vizualizare mesaje" name="title"/>
	</jsp:include>
	<!-- Continutul principal al paginii -->
	<div id="content">
		Mai jos se pot vizualiza mesajele tale:
		<div class="table-box">
			<%
			//Prepare the data for the table model 
			ArrayList<Mesaj> objects;
			ObjectTableGenerator<Mesaj> tableModel=null;
			MesajWrapper dao=new MesajWrapper();
			//Get the objects		
			try {
				objects=dao.getMesaje("cnp_destinatie='"+utilizator.CNP+"'");
				tableModel=new ObjectTableGenerator<Mesaj>(Mesaj.class,
						objects,
						Constants.ADMIN_MESAJ_COLUMN_FIELD_MATCH[1],
						Constants.ADMIN_MESAJ_COLUMN_FIELD_MATCH[0],
						new int[] {0},
						new String[] {"id"});
			} catch (Exception e) {
				error(e.getMessage(), out);
				e.printStackTrace();
			}			
			%>
			<!-- Afisare date in tabel -->
			<table>
				<%=tableModel.getHTMLTableRepresentation("read_mesaj.jsp",null) %>
			</table>
			<br/>
			<a href="add_mesaj.jsp" class="button">Trimite un nou mesaj</a>


		</div>
		
		<!-- Link catre pagina principala a utilizatorului -->
		<jsp:include page="/include/content_footer.jsp"></jsp:include>
	</div>
	
	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>