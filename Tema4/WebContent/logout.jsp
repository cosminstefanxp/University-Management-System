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
<title>ACS - Logout</title>
</head>
<%@include file="../include/tools.jsp"%>
<% request.setAttribute("permissions", Tip.ANY);%>
<%@include file="../include/authentify.jsp" %>
	<%
		session.invalidate();
		response.sendRedirect("index.jsp");
	%>
</body>
</html>