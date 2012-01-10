<%@page import="aii.Utilizator"%>
<%@page import="aii.Utilizator.Tip"%>
<%
	//After import, the logged in user, if any will be available
	Tip authenticationType=(Tip)request.getAttribute("permissions");
	Utilizator utilizator=(Utilizator)session.getAttribute("user");
	
	log("Verificam autentificarea pentru "+utilizator+" pentru "+authenticationType);
	if(authenticationType!=null)
	{
		//The user is not logged in
		if(utilizator==null)
		{
			response.sendRedirect("/AII-Tema4/index.jsp");
			log("[ERROR] Lipsa utilizator in sesiune!");
			return;
		}
		//The user doesn't have the required permissions
		if(authenticationType!=Tip.ANY)
		{
			boolean fail=false;
			if(authenticationType==Tip.ADMIN && (utilizator.tip!=Tip.ADMIN && utilizator.tip!=Tip.SUPER_ADMIN) )
			{
				fail=true;
			}
			else if(authenticationType==Tip.CADRU_DIDACTIC && (utilizator.tip!=Tip.CADRU_DIDACTIC && utilizator.tip!=Tip.SEF_CATEDRA) )
			{
				fail=true;
			}
			else if(authenticationType==Tip.STUDENT && utilizator.tip!=Tip.STUDENT)
			{
				fail=true;
			}
			else if(authenticationType==Tip.SECRETAR && utilizator.tip!=Tip.SECRETAR)
			{
				fail=true;
			}
			else if(authenticationType==Tip.SEF_CATEDRA && utilizator.tip!=Tip.SEF_CATEDRA)
			{
				fail=true;
			}
			
			//The type was not the required one
			if(fail)
			{
				response.sendRedirect("index.jsp?error='Lipsa Permisiuni'");
				log("[ERROR] Utilizatorul nu are permisiunile necesare!");
				return;
			}
		}
	}
		
%>