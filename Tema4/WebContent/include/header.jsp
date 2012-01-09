
<%@page import="aii.Utilizator"%>
<div id="header">
	<div class="header-content">
		<center><image src="/AII-Tema4/images/upb.jpg"/ style="align:'middle'" height="30px"></image></center>
		<center><h2>Facultatea de Automatica si Calculatoare</h2></center>
		<center><h4><%=request.getParameter("title") %></h3></center>
		<%
		Utilizator utilizator=(Utilizator)session.getAttribute("user");
		if(utilizator!=null) {%>
		<div class="user">
			Welcome, <%=((Utilizator)session.getAttribute("user")).prenume %>!
			<a href="/AII-Tema4/logout.jsp" class="button">Logout</a>
		</div>
		<%} %>
	</div>
</div>