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
		<jsp:param value="Discipline - Sef de Catedra " name="title"/>
	</jsp:include>
	<!-- Continutul principal al paginii -->
	<div id="content">
		Mai jos se pot vizualiza disciplinele din sistem:
		<div class="table-box">
			<%
			//Prepare the data for the table model 
			ArrayList<Disciplina> objects;
			ObjectTableGenerator<Disciplina> tableModel=null;
			DisciplinaWrapper dao=new DisciplinaWrapper();
			//Get the objects		
			try {
				objects=dao.getDiscipline("true");
				tableModel=new ObjectTableGenerator<Disciplina>(Disciplina.class,
						objects,
						Constants.ADMIN_DISCIPLINA_COLUMN_FIELD_MATCH[1],
						Constants.ADMIN_DISCIPLINA_COLUMN_FIELD_MATCH[0],
						new int[] {0},
						new String[] {"cod"});
			} catch (Exception e) {
				error(e.getMessage(), out);
				e.printStackTrace();
			}			
			%>
			<!-- Afisare date in tabel -->
			<table>
				<%=tableModel.getHTMLTableRepresentation("edit_disciplina.jsp","delete_disciplina.jsp") %>
			</table>
			<br/>
			<a href="add_disciplina.jsp" class="button">Adauga</a>


		</div>
		
		<!-- Link catre pagina principala a utilizatorului -->
		<jsp:include page="../include/content_footer.jsp"></jsp:include>
	</div>
	
	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>