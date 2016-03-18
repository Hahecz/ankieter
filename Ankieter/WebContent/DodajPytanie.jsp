<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"
import="Package1.Polecenia" %>
<% request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Elektroniczny Ankieter</title>
		<link rel="icon" href="//java.mitrosz.com/Ankiety/images/redmine-logo.ico">
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script src="JavaScript/bootstrap.min.js"></script>
		<!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	</head>
	<%
		String login = (String)session.getAttribute("Login");
		if(login==null){
			response.sendRedirect("Zaloguj.jsp");
		} else {
		int id_ankiety = Integer.parseInt((String) request.getParameter("id_ankiety"));
	%>
	<body>
		<%@include file="Header.jsp" %>
	    <div class="container">
			<div class="row">
				<div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1 col-xs-12">
					<div class="well bs-component">
						<form action=DodajPytanie?id_ankiety=<%=id_ankiety%> method="post" class="form-horizontal">
							<fieldset>
								<legend><b>Dodajesz pytanie do ankiety:  </b><%out.println(Polecenia.podajAnkiete(id_ankiety)); %></legend>
								<div class="row form-group">
									<div class="col-md-3 col-sm-3 col-xs-12">
										<label for="inputLogin control-label">Pytanie:</label>
									</div>
									<div class="col-md-9 col-sm-9 col-xs-12">
										<input TYPE="TEXT" NAME="pytanie" style="width: 100%;"/>
									</div>
								</div>	
								<div class="row form-group">
									<div class="col-md-3 col-sm-5 col-xs-12">
										<label for="inputLogin control-label">Rodzaj odpowiedzi:</label>
									</div>
									<div class="col-md-4 col-sm-7 col-xs-12" style="padding-bottom: 10px;">
										<select id="rodzaj_odpowiedzi" name="rodzaj_odpowiedzi" style="width: 100%;">
											<option name="jednokrotnego wyboru" value="jednokrotnego wyboru">jednokrotnego wyboru</option>
											<option name="wielokrotnego wyboru" value="wielokrotnego wyboru">wielokrotnego wyboru</option>
											<option name="tekstowa" value="tekstowa">tekstowa</option>
											<option name="lista rozwijalna" value="lista rozwijalna">lista rozwijalna</option>
										</select>
									</div>								
									<div id="label" class="col-md-3 col-sm-5 col-xs-12">
										<label for="inputLogin control-label">Ilość odpowiedzi:</label>
									</div>
									<div id="label_dlugosc" class="col-md-3 col-sm-5 col-xs-12" style="display: none;">
										<label for="inputLogin control-label">Maksymalna długość odpowiedzi:</label>
									</div>
									<div id="ilosc" class="col-md-2 col-sm-7 col-xs-12">
										<input TYPE="TEXT" id="ilosc_odpowiedzi_na_pytanie" name="ilosc_odpowiedzi_na_pytanie" style="width: 100%;"/>
									</div>
									<div id="dlugosc" class="col-md-2 col-sm-7 col-xs-12" style="display: none;">
										<input TYPE="TEXT" id="dlugosc_odpowiedzi" name="dlugosc_odpowiedzi" style="width: 100%;"/>
									</div>
									<script type="text/javascript">
										$(function() {
										    $('#rodzaj_odpowiedzi').change(function(){
										        if($('#rodzaj_odpowiedzi').val() == 'tekstowa') {
										            $('#ilosc').hide(); 
										            $('#label').hide();
										            $('#dlugosc').show(); 
										            $('#label_dlugosc').show(); 
										        } else {
										            $('#ilosc').show(); 
										            $('#label').show();
										            $('#dlugosc').hide(); 
										            $('#label_dlugosc').hide();
										        } 
										    });
										});
									</script>
								</div>
								<div class="row form-group">
									<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
										<input TYPE="submit" value="Dodaj" class="btn btn-primary btn-md" style="width: 100%;">
									</div>
									<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
										<a href="Edytuj.jsp?id_ankiety=<%=id_ankiety%>" class="btn btn-primary btn-md" style="width: 100%;">Anuluj</a>
									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
<%
	String komunikat = (String) session.getAttribute("komunikatDlaDodajPytanie");
	if(komunikat!=null){
		%>
				<script type="text/javascript">
					alert('<%=komunikat%>');
				</script>
		<%
		session.setAttribute("komunikatDlaDodajPytanie", null);
	}
} %>