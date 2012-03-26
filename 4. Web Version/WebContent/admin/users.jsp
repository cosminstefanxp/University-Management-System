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
<title>ACS - Administrator</title>
<link rel="stylesheet" href="../style/main.css" type="text/css" />
</head>
<%@include file="../include/tools.jsp"%>
<% request.setAttribute("permissions", Tip.ADMIN);%>
<%@include file="../include/authentify.jsp" %>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Consola Administrator" name="title"/>
	</jsp:include>
	<!-- Continutul principal al paginii -->
	<div id="content">
		Mai jos se pot vizualiza utilizatorii din sistem:
		<div class="table-box">
			<%
			//Prepare the data for the table model 
			ArrayList<Utilizator> objects;
			ObjectTableGenerator<Utilizator> tableModel=null;
			UtilizatorWrapper dao=new UtilizatorWrapper();
			//Get the objects		
			try {
				objects=dao.getObjects(Constants.USER_TABLE,"cnp=cnp");
				tableModel=new ObjectTableGenerator<Utilizator>(Utilizator.class,
						objects,
						Constants.ADMIN_USER_COLUMN_FIELD_MATCH[1],
						Constants.ADMIN_USER_COLUMN_FIELD_MATCH[0],
						new int[] {1},
						new String[] {"CNP"});
			} catch (SQLException e) {
				error(e.getMessage(), out);
				e.printStackTrace();
			} catch (Exception e) {
				error(e.getMessage(), out);
				e.printStackTrace();
			}			
			%>
			<!-- Afisare date in tabel -->
			<table>
				<%=tableModel.getHTMLTableRepresentation("edit_user.jsp","delete_user.jsp") %>
			</table>
			<br/>
			<a href="add_user.jsp" class="button">Adauga</a>


		</div>
		
		<!-- Link catre pagina principala a utilizatorului -->
		<jsp:include page="../include/content_footer.jsp"></jsp:include>
	</div>
	
	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>