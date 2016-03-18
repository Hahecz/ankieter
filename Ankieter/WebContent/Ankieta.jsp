<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.ResultSetMetaData"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
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
		<style type="text/css">
			textarea {
			  	resize: vertical;
			}
		</style>
		<!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	</head>
	<%
	String login = (String)session.getAttribute("Login");
	if(login==null){
		response.sendRedirect("Zaloguj.jsp?id_ankiety="+request.getParameter("id_ankiety"));
	} else {
		int id_ankiety = Integer.parseInt(request.getParameter("id_ankiety"));
		if(!Polecenia.podajAnkiete(id_ankiety).equals("")){
			int id_uzytkownika = Polecenia.podajIdUzytkownika(login);
			Polecenia.connect();		
		    ResultSet resultSet = Polecenia.statement.executeQuery("SELECT ID_uzytkownika FROM odpowiedzi WHERE ID_ankieta = '"+id_ankiety+"';");
		    ResultSetMetaData rsmd = resultSet.getMetaData();
		    while(resultSet.next()) {
		      	int s = Integer.parseInt(resultSet.getString(1));
		       	if(s==id_uzytkownika){
		       		session.setAttribute("komunikatDlaWynikDlaGoscia", "Przepraszamy.\\nTy już zagłosowałeś w tej ankiecie.");
		       		response.sendRedirect("WynikDlaGoscia.jsp?id_ankiety="+id_ankiety);
		       		break;
		       	}
		  	}
		    Polecenia.close();
	%>
	<body>
		<%@include file="Header.jsp" %>
		<%boolean czyAktywna = Polecenia.czyAnkietaAktywna(id_ankiety);
		  if(czyAktywna) {%>
		
	    <div class="container">
			<div class="row">
				<div class="col-md-8 col-md-offset-2 col-sm-8 col-sm-offset-2 col-xs-12">
					<div class="well bs-component">
						<form action=Zaglosuj?id_ankiety=<%=id_ankiety%> method="post" class="form-horizontal">
							<fieldset>
								<legend>Wypełniasz ankietę: <%=Polecenia.podajAnkiete(id_ankiety)%></legend>
								<%	ArrayList<Integer> pytania = Polecenia.idPytan(id_ankiety);
								for(int k=0; k<pytania.size(); k++){ %>
								<div class="row form-group">
									<div class="col-md-12 col-sm-12 col-xs-12">
										<label for="inputLogin control-label"><%out.print((k+1)+". "+Polecenia.podajPytanie(pytania.get(k))); %>:</label>
									</div>
								</div>
								<% ArrayList<Integer> odpowiedzi = Polecenia.idOdpowiedzi(pytania.get(k));
								for(int i=0; i<odpowiedzi.size(); i++){
								int rodzaj_pytania = Polecenia.podajRodzajPytania(pytania.get(k));%>
								<div class="row form-group">
									<div class="col-md-11 col-md-offset-1 col-sm-11 col-sm-offset-1 col-xs-11 col-xs-offset-1">
										<% if(i==0 && rodzaj_pytania==4){ %>
											<select name="pytanie<%=k%>" style="width: 100%; max-width: 300px;">
										<%} %>
										<% if(rodzaj_pytania==1){ %>
											<input checked="<%if(i==0){out.print("checked");} %>" name="pytanie<%=k%>" type="radio" value="<%=Polecenia.podajOdpowiedz(odpowiedzi.get(i))%>"> <%=Polecenia.podajOdpowiedz(odpowiedzi.get(i))%>
										<%} else if(rodzaj_pytania==2){ %>
											<input type="checkbox" name="pytanie<%=k%>" value="<%=Polecenia.podajOdpowiedz(odpowiedzi.get(i))%>"> <%=Polecenia.podajOdpowiedz(odpowiedzi.get(i))%>
										<%} else if(rodzaj_pytania==4){ %>
											<option value="<%=Polecenia.podajOdpowiedz(odpowiedzi.get(i))%>"><%=Polecenia.podajOdpowiedz(odpowiedzi.get(i))%></option>
										<%} else { %>
											<textarea name="pytanie<%=k%>" style="width: 100%; max-width: 410px;" rows="<%=Polecenia.podajDlugoscOdpowiedzi(odpowiedzi.get(i))/50%>" title="Maksymalna długość odpowiedzi: <%=Polecenia.podajDlugoscOdpowiedzi(odpowiedzi.get(i))%> znaków.">Podaj odpowiedź na pytanie...</textarea>
										<%} %>
										<% if(i==(odpowiedzi.size()-1) && rodzaj_pytania==4){ %>
											</select>
										<%} %>
									</div>
								</div>
								<%}} %>
								<div class="row form-group">
									<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
										<input type="submit" value="Gotowe" style="width: 100%;" class="btn btn-primary btn-md">
									</div>
									<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
										<a href="Zalogowano.jsp" style="width: 100%;" class="btn btn-primary btn-md">Anuluj</a>
									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
		<%} else {%>
		<div class="container">
			<div class="row">
				<div class="col-md-8 col-md-offset-2 col-sm-8 col-sm-offset-2 col-xs-12">
					<div class="well bs-component">
						Wybrana ankieta nie jest aktywna
					</div>
				</div>
			</div>
		</div>
			<%}%>
		}
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script src="JavaScript/bootstrap.min.js"></script>
	</body>
</html>
<%} else {
		session.setAttribute("zalogowanoKomunikat", "Przepraszamy, taka ankieta nie istnieje.");
		response.sendRedirect("Zalogowano.jsp");
	}
	String komunikat = (String) session.getAttribute("komunikatDlaAnkieta");	
	if(komunikat!=null){
	%>
		<script type="text/javascript">
			alert('<%=komunikat%>');
		</script>
	<%
	}
	session.setAttribute("komunikatDlaProfil", null);
}%>