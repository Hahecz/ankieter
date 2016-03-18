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
			ilosc_pytan = Integer.parseInt(ip);
			nazwa_ankiety = (String) session.getAttribute("nazwa_ankiety");
	%>
	<body>
		<%@include file="Header.jsp" %>
	    <div class="container">
			<div class="row">
				<div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1 col-xs-12">
					<div class="well bs-component">
						<form action=Servlet?ilosc_pytan=<%=ilosc_pytan%> method="post" name="form1" class="form-horizontal">
							<fieldset>
								<legend><b>Tworzysz ankietę: </b><%out.println(nazwa_ankiety);%></legend>
								<% try{ 
								for(int i=1; i<=ilosc_pytan; i++){%>
								<div class="row form-group">
									<div class="col-md-2 col-sm-3 col-xs-12">
										<label for="inputLogin control-label">Pytanie <%=i%>:</label>
									</div>
									<div class="col-md-10 col-sm-9 col-xs-12">
										<input type="TEXT" name="<%out.print("pytanie"+i);%>" style="width: 100%;" value="<%=session.getAttribute("pytanie"+i)%>"/>
									</div>
								</div>
								<% if(session.getAttribute("rodzaj_odpowiedzi_"+i).equals("tekstowa")) { %>
								<div class="row form-group">
									<div class="col-md-6 col-md-offset-1 col-sm-6 col-sm-offset-1 col-xs-11 col-xs-offset-1">
										<label for="inputLogin control-label"><i>Maksymalna długość odpowiedzi (ilość znaków):</i></label>
									</div>
									<div class="col-md-3 col-sm-3 col-xs-11 col-xs-offset-1">
										<input type="text" NAME="<%out.print("dlugosc_odpowiedzi_na_pytanie"+i);%>" style="width: 94%;" value="<%=session.getAttribute("dlugosc_odpowiedzi_na_pytanie"+i)%>" title="Maksymalnie 5 cyfr">
									</div>
								</div>
								<div class="row form-group">
									<div class="col-md-3 col-md-offset-1 col-sm-3 col-sm-offset-1 col-xs-11 col-xs-offset-1">
										<label for="inputLogin control-label">Odpowiedź:</label>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-11 col-xs-offset-1">
										<textarea disabled="disabled" rows="5" style="width: 96.5%">Odpowiedź typu tekstowego...</textarea>
									</div>
								</div>
								<%} else if(session.getAttribute("rodzaj_odpowiedzi_"+i).equals("jednokrotnego wyboru")){ %>
								<%	for(int k=1; k <= (Integer) session.getAttribute("ilosc_odpowiedzi_na_pytanie"+i); k++){ %>															
								<div class="row form-group">
									<div class="col-md-3 col-md-offset-1 col-sm-3 col-sm-offset-1 col-xs-11 col-xs-offset-1">
										<label for="inputLogin control-label">Odpowiedź <%=k%>: </label>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-11 col-xs-offset-1">
										<input type="radio" disabled="disabled">
										<input TYPE="text" NAME="pytanie<%=i%>_odpowiedz<%=k%>" style="width: 92%"/>
									</div>
								</div>
								<%}} else if(session.getAttribute("rodzaj_odpowiedzi_"+i).equals("lista rozwijalna")){ %>
								<%	for(int k=1; k <= (Integer) session.getAttribute("ilosc_odpowiedzi_na_pytanie"+i); k++){ %>															
								<div class="row form-group">
									<div class="col-md-3 col-md-offset-1 col-sm-3 col-sm-offset-1 col-xs-11 col-xs-offset-1">
										<label for="inputLogin control-label">Odpowiedź <%=k%>: </label>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-11 col-xs-offset-1">
										<select disabled="disabled" style="width: 12%"></select>
										<input TYPE="text" NAME="pytanie<%=i%>_odpowiedz<%=k%>" style="width: 85%"/>
									</div>
								</div>	
								<%}} else {%>
								<%	for(int k=1; k <= (Integer) session.getAttribute("ilosc_odpowiedzi_na_pytanie"+i); k++){ %>	
								<div class="row form-group">
									<div class="col-md-3 col-md-offset-1 col-sm-3 col-sm-offset-1 col-xs-11 col-xs-offset-1">
										<label for="inputLogin control-label">Odpowiedź <%=k%>: </label>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-11 col-xs-offset-1">
										<input type="checkbox" disabled="disabled">
										<input TYPE="text" NAME="pytanie<%=i%>_odpowiedz<%=k%>" style="width: 92%"/>
									</div>
								</div>
								<%}}}} catch (NumberFormatException e) {
									session.setAttribute("komunikatDlaTworzenie_ankiety", "Nie podałeś poprawnych liczb w polach z ilością odpowiedzi !");
								}%>
								<div class="row form-group">
									<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
										<input TYPE="submit" value="Stwórz" style="width: 100%;" class="btn btn-primary btn-md">
									</div>
									<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
										<a href="Tworzenie_ankiety.jsp" class="btn btn-primary btn-md" style="width: 100%;">Powrót</a>
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
String komunikat = (String) session.getAttribute("komunikatDlaTworzenie_ankiety_cz2");	
if(komunikat!=null){
%>
	<script type="text/javascript">
		alert('<%=komunikat%>');
	</script>
<%
}	
session.setAttribute("komunikatDlaTworzenie_ankiety_cz2", null);
}
%>