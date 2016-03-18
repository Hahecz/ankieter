<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
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
	String login = (String) session.getAttribute("Login");	
 	String nazwa_ankiety = "";	
	int ilosc_pytan = 0;
	if(login==null){
		response.sendRedirect("Zaloguj.jsp");
	} else {
		String ip =  (String) session.getAttribute("ilosc_pytan");
		nazwa_ankiety = (String) session.getAttribute("nazwa_ankiety");
		String czyAktywna = (String) session.getAttribute("czy_aktywna");
		if(nazwa_ankiety.length()<=150){
			if(!nazwa_ankiety.equals("")){
				if(!ip.equals("")){
					try {
						ilosc_pytan = Integer.parseInt(ip);
						if(ilosc_pytan<=0){
							session.setAttribute("komunikatDlaNowa_ankieta", "Minimalna ilość pytań to 1 !");
							response.sendRedirect("Nowa_ankieta.jsp");
						}
					} catch (NumberFormatException e) {
						session.setAttribute("komunikatDlaNowa_ankieta", "W polu z ilością pytań nie wpisałeś poprawnej liczby !");
						response.sendRedirect("Nowa_ankieta.jsp");
					}
				}
				else{
					session.setAttribute("komunikatDlaNowa_ankieta", "W polu z ilośią pytań nie wpisałeś żadnej liczby !");
					response.sendRedirect("Nowa_ankieta.jsp");
				}
			}
			else {
				session.setAttribute("komunikatDlaNowa_ankieta", "W polu z nazwą ankiety nic nie wpisałeś !");
				response.sendRedirect("Nowa_ankieta.jsp");
			}
		}
		else {
			session.setAttribute("komunikatDlaNowa_ankieta", "Nazwa ankiety jest zbyt długa ! \\nMaksymalna ilość znaków to 150.");
			response.sendRedirect("Nowa_ankieta.jsp");
		}
	%>
	<body>
		<%@include file="Header.jsp" %>
	    <div class="container">
			<div class="row">
				<div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1 col-xs-12">
					<div class="well bs-component">
						<form action=DodajAnkieteCz2 method="post" name="form1" class="form-horizontal">
							<fieldset>
								<legend><b>Tworzysz ankietę o nazwie:  </b><%out.println(nazwa_ankiety); %></legend>
								<% for(int i=1;i <= ilosc_pytan; i++){%>
								<div class="row form-group">
									<div class="col-md-3 col-sm-3 col-xs-12">
										<label for="inputLogin control-label"><%out.println("Pytanie "+i); %>:</label>
									</div>
									<div class="col-md-9 col-sm-9 col-xs-12">
										<input TYPE="TEXT" NAME="<%out.print("pytanie"+i);%>" style="width: 100%;"/>
									</div>
								</div>	
								<div class="row form-group">
									<div class="col-md-3 col-md-offset-0 col-sm-5 col-sm-offset-1 col-xs-11 col-xs-offset-1">
										<label for="inputLogin control-label">Rodzaj odpowiedzi:</label>
									</div>
									<div class="col-md-4 col-md-offset-0 col-sm-6 col-sm-offset-0 col-xs-11 col-xs-offset-1" style="padding-bottom: 10px;">
										<select id="rodzaj_odpowiedzi_<%=i%>" name="rodzaj_odpowiedzi_<%=i%>" style="width: 100%;">
											<option name="jednokrotnego wyboru" value="jednokrotnego wyboru">jednokrotnego wyboru</option>
											<option name="wielokrotnego wyboru" value="wielokrotnego wyboru">wielokrotnego wyboru</option>
											<option name="tekstowa" value="tekstowa">tekstowa</option>
											<option name="lista rozwijalna" value="lista rozwijalna">lista rozwijalna</option>
										</select>
									</div>								
									<div id="label<%=i%>" class="col-md-3 col-md-offset-0 col-sm-5 col-sm-offset-1 col-xs-11 col-xs-offset-1">
										<label for="inputLogin control-label">Ilość odpowiedzi:</label>
									</div>
									<div id="label_dlugosc<%=i%>" class="col-md-3 col-md-offset-0 col-sm-5 col-sm-offset-1 col-xs-11 col-xs-offset-1" style="display: none;">
										<label for="inputLogin control-label">Maksymalna długość odpowiedzi:</label>
									</div>
									<div id="ilosc<%=i%>" class="col-md-2 col-md-offset-0 col-sm-6 col-sm-offset-0 col-xs-11 col-xs-offset-1">
										<input TYPE="TEXT" id="ilosc_odpowiedzi_na_pytanie<%=i%>" name="ilosc_odpowiedzi_na_pytanie<%=i%>" style="width: 100%;"/>
									</div>
									<div id="dlugosc<%=i%>" class="col-md-2 col-md-offset-0 col-sm-6 col-sm-offset-0 col-xs-11 col-xs-offset-1" style="display: none;">
										<input TYPE="TEXT" id="dlugosc_odpowiedzi_na_pytanie<%=i%>" name="dlugosc_odpowiedzi_na_pytanie<%=i%>" style="width: 100%;"/>
									</div>
									<script type="text/javascript">
										$(function() {
										    $('#rodzaj_odpowiedzi_<%=i%>').change(function(){
										        if($('#rodzaj_odpowiedzi_<%=i%>').val() == 'tekstowa') {
										            $('#ilosc<%=i%>').hide(); 
										            $('#label<%=i%>').hide();
										            $('#dlugosc<%=i%>').show(); 
										            $('#label_dlugosc<%=i%>').show(); 
										        } else {
										            $('#ilosc<%=i%>').show(); 
										            $('#label<%=i%>').show();
										            $('#dlugosc<%=i%>').hide(); 
										            $('#label_dlugosc<%=i%>').hide();
										        } 
										    });
										});
									</script>
								</div>											
								<% }%>
								<div class="row form-group">
									<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
										<input TYPE="submit" value="Dalej" class="btn btn-primary btn-md" style="width: 100%;">
									</div>
									<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
										<a href="Nowa_ankieta.jsp?kom=blad" class="btn btn-primary btn-md" style="width: 100%;">Powrót</a>
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
String komunikat = (String) session.getAttribute("komunikatDlaTworzenie_ankiety");	
if(komunikat!=null){
%>
	<script type="text/javascript">
		alert('<%=komunikat%>');
	</script>
<%
}	
session.setAttribute("komunikatDlaTworzenie_ankiety", null);
}
%>