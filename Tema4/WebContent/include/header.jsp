
<%@page import="aii.Utilizator"%>
<div id="header">
	<div class="header-content">
		<center><h2>Facultatea de Automatica si Calculatoare</h2></center>
		<center><h4><%=request.getParameter("title") %></h3></center>
		<%
		Utilizator utilizator=(Utilizator)session.getAttribute("user");
		if(utilizator!=null) {%>
		<div class="user">
			Welcome, <%=((Utilizator)session.getAttribute("user")).prenume %>!
		</div>
		<%} %>
	</div>
</div>