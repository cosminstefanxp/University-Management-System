<%@page import="aii.database.UtilizatorWrapper"%>
<%@page import="aii.database.OptiuneContractWrapper"%>
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
			<jsp:param value="Consola Student" name="title"/>
	</jsp:include>
	<!-- Continutul principal al paginii -->
	<div id="content">
		

		Ai ales urmatoarele discipline:
		<% 
		ArrayList<String> discipline=new ArrayList<String>();
		
		//Adaugam disciplinele optionale
		int nr=Integer.parseInt(request.getParameter("optionale"));
		for(int i=0;i<nr;i++)
			if(request.getParameter("optionale_"+i)!=null)
				discipline.add(request.getParameter("optionale_"+i));
			else
			{
				error("Nu ai completat optionalul "+i,out);
				return;
			}
		
		//Adaugam disciplinele facultative + obligatorii
		if(request.getParameterMap().get("materie")!=null)
			for(String str:request.getParameterMap().get("materie"))
				discipline.add(str);
		
		
		//Adaugare optiuni
		int studyYear = utilizator.titlu_grupa.charAt(1) - '0';
		OptiuneContractWrapper optiuniDAO=new OptiuneContractWrapper();
		for(String str:discipline)
		{
			out.write(str+" ");
			optiuniDAO.insertOptiune(str, utilizator.CNP, studyYear);			
		}
		//Salvam utilizatorul
		utilizator.contractCompletat=true;
		new UtilizatorWrapper().UpdateUtilizator(utilizator, utilizator);
			
		
		
		%>
		<% notify("Contractul tau a fost salvat cu success.", out); %>
		
		<jsp:include page="/include/content_footer.jsp">
			<jsp:param value="contract_studii.jsp" name="back_link" />
		</jsp:include>
	</div>
	
	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>