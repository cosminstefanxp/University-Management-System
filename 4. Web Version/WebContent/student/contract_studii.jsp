<%@page import="aii.gui.ObjectTableGenerator.ControlType"%>
<%@page import="aii.database.OptiuneContractWrapper"%>
<%@page import="java.util.Hashtable"%>
<%@page import="aii.OrarComplet"%>
<%@page import="aii.database.OrarCompletWrapper"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="sun.util.calendar.Gregorian"%>
<%@page import="java.util.Calendar"%>
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
<title>ACS - Student</title>
<link rel="stylesheet" href="../style/main.css" type="text/css" />
</head>
<%@include file="../include/tools.jsp"%>
<%
	request.setAttribute("permissions", Tip.STUDENT);
%>
<%@include file="../include/authentify.jsp"%>

<body>
	<!-- Headerul paginii -->
	<jsp:include page="/include/header.jsp">
		<jsp:param value="Contract de Studii - Student " name="title" />
	</jsp:include>

	<%!//Variabile globale
	int studyYear;
	private ArrayList<Disciplina> discipline;
	private ArrayList<Disciplina> disciplineObligatorii;
	private Hashtable<Integer, ArrayList<Disciplina>> disciplineOptionale;
	private ArrayList<Disciplina> disciplineFacultative;

	private ObjectTableGenerator<Disciplina> obligatoriiTableModel;
	private ArrayList<ObjectTableGenerator<Disciplina>> optionaleTableModel;
	private ObjectTableGenerator<Disciplina> facultativeTableModel;

	private OptiuneContractWrapper optiuniDAO = new OptiuneContractWrapper();
	private DisciplinaWrapper disciplineDAO = new DisciplinaWrapper();
	private ArrayList<Integer> keysHashtable;

	//Pregatire date
	/**
		Pregateste datele pentru contractul de studii si salveaza in variabile globale. Intoarce true daca totul a decurs ok.
	 */
	private boolean prepareData(Utilizator utilizator, JspWriter out) {
		if (utilizator.titlu_grupa == null || utilizator.titlu_grupa.isEmpty()) {
			error("Nu sunteti inregistrat la nici o grupa!", out);
			return false;
		}
		//Verificare licentiere
		if (utilizator.titlu_grupa.equals("licentiat")) {
			error("Sunteti licentiat!", out);
			return false;
		}
		studyYear = utilizator.titlu_grupa.charAt(1) - '0';
		System.out.println("Studentul este in anul " + studyYear);

		//Get the objects		
		try {
			if (!utilizator.contractCompletat)
				//Get all the discipline
				discipline = disciplineDAO.getObjects(Constants.DISCIPLINA_TABLE, "an_studiu=\'" + studyYear + "\'");
			else
				discipline = optiuniDAO.getOptiuni(utilizator.CNP, studyYear);
		} catch (Exception e) {
			e.printStackTrace();
			error(e.getMessage(), out);
			return false;
		}

		//Prepare arrays
		disciplineObligatorii = new ArrayList<Disciplina>();
		disciplineFacultative = new ArrayList<Disciplina>();
		disciplineOptionale = new Hashtable<Integer, ArrayList<Disciplina>>(10);

		//Iterate through the entries and put the in the proper container
		for (Disciplina disciplina : discipline) {
			if (disciplina.tip.equals(Disciplina.TipDisciplina.Obligatoriu))
				disciplineObligatorii.add(disciplina);
			else if (disciplina.tip.equals(Disciplina.TipDisciplina.Facultativ))
				disciplineFacultative.add(disciplina);
			else {
				if (disciplineOptionale.get(disciplina.grup) == null)
					disciplineOptionale.put(disciplina.grup, new ArrayList<Disciplina>());
				disciplineOptionale.get(disciplina.grup).add(disciplina);
			}
		}

		//Create table models
		try {
			obligatoriiTableModel = new ObjectTableGenerator<Disciplina>(Disciplina.class, disciplineObligatorii,
					Constants.VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH[1],
					Constants.VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH[0], 
					new int[]{0}, new String[]{"cod"});

			facultativeTableModel = new ObjectTableGenerator<Disciplina>(Disciplina.class, disciplineFacultative,
					Constants.VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH[1],
					Constants.VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH[0],
					new int[]{0}, new String[]{"cod"});

			optionaleTableModel = new ArrayList<ObjectTableGenerator<Disciplina>>();
			keysHashtable = new ArrayList<Integer>();
			for (Integer key : disciplineOptionale.keySet()) {
				keysHashtable.add(key);
				optionaleTableModel.add(new ObjectTableGenerator<Disciplina>(Disciplina.class, disciplineOptionale
						.get(key), Constants.VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH[1],
						Constants.VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH[0], new int[]{0}, new String[]{"cod"}));
			}
		} catch (Exception e) {
			e.printStackTrace();
			error(e.getMessage(), out);
			return false;
		}
		return true;
	}%>

	<!-- Continutul principal al paginii -->
	<div id="content">
		<%

			//Verificare completare			
			if (utilizator.contractCompletat)
				out.write("Contractul tau de studii a fost completat si nu mai poate fi modificat!");
			else
				out.write("Completare contract studii. Te rugam selecteaza materiile optionale (cate una pe fiecare grup) si facultative pe care le doresti si apasa Salveaza.");
		%>
		<div class="table-box">
			<i>(Exemplu: Necompletat la userul 999)<br/><br/></i>
			<%
			//Pregatire date
			if(prepareData(utilizator, out))
			{
			%>
		
			<!-- Afisare date in tabel -->
			<h4>Materii obligatorii:</h4><br/>
			<table style="width:100%;">
				<%=obligatoriiTableModel.getHTMLTableRepresentation(null, null)%>
			</table>
			<br/>
			
			<% //Verificam daca a fost completat contractul sau nu
			if(utilizator.contractCompletat==true)
			{
				for(int i=0;i<optionaleTableModel.size();i++)
				{%>
					<h4>Materii optionale grup <%=i %>:</h4><br/>
					<table style="width:100%;">
						<%=optionaleTableModel.get(i).getHTMLTableRepresentation(null, null)%>
					</table>			
					<br/>
				<%} %>
				
				<h4>Materii facultative:</h4><br/>
				<table style="width:100%;">
					<%=facultativeTableModel.getHTMLTableRepresentation(null, null)%>
				</table>
				<br/>
			<%} else { %>
			
				<form name="contract" action="save_contract.jsp" method="POST">
				<%
				//Mai intai discipline obligatorii
				for(Disciplina d:disciplineObligatorii)
				{
					out.write("<input type='checkbox' hidden='hidden'  checked='true' name='materie' value='"+d.cod+"'/>");
				}
				%>
				
			
				
				<%	
				for(int i=0;i<optionaleTableModel.size();i++)
				{%>
					<h4>Materii optionale grup <%=i %>:</h4><br/>
					<table style="width:100%;">
						<%=optionaleTableModel.get(i).getHTMLTableRepresentation(null, null,ControlType.Radio, "optionale_"+i)%>
					</table>			
					<br/>
				<%} %>
				<input type="text" hidden="hidden" name="optionale" value='<%=optionaleTableModel.size() %>'/>
					
				<h4>Materii facultative:</h4><br/>
				<table style="width:100%;">
					<%=facultativeTableModel.getHTMLTableRepresentation(null, null, ControlType.Checkbox, "materie")%>
				</table>
				<br/>
				<input type="hidden" name="submitted" value="true" /> <input type="submit" name="submit" class="button"
				value="Salveaza">
				</form>
				<%} %>
				
			
			<%} %>
			<br />


		</div>

		<!-- Link catre pagina principala a utilizatorului -->
		<jsp:include page="../include/content_footer.jsp"></jsp:include>
	</div>

	<!-- Footerul paginii -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>