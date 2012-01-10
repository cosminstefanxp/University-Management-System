
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
		response.sendRedirect("profesor");
		break;
	case SECRETAR:
		response.sendRedirect("secretar");
		break;
	case STUDENT:
		response.sendRedirect("student");
		break;
	default:
		notify("Tipul utilizatorului necunoscut", out);
	}
%>