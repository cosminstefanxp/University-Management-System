
<%--Realizeaza redirectari in catre pagina principala, in functie de tipul utilizatorului. --%>
<%@include file="include/tools.jsp"%>
<%
	request.setAttribute("permissions", Tip.ANY);
%>
<%@include file="include/authentify.jsp"%>

<%
	switch (utilizator.tip) {
	case ADMIN:
	case SUPER_ADMIN:
		response.sendRedirect("admin");
		break;
	case CADRU_DIDACTIC:
	case SEF_CATEDRA:
		response.sendRedirect("cadru.jsp");
		break;
	case SECRETAR:
		response.sendRedirect("secretar.jsp");
		break;
	case STUDENT:
		response.sendRedirect("student.jsp");
		break;
	default:
		notify("Tipul utilizatorului necunoscut", out);
	}
%>