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
		<style type="text/css">
			textarea {
			    resize: none;
			}
		</style>		
		<!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	</head>
	<%
		String login = (String)session.getAttribute("Login");
		int id_ankiety = Integer.parseInt((String) session.getAttribute("id_ankiety"));
		if(login==null){
			response.sendRedirect("Zaloguj.jsp");
		} else {
	%>
	<body>
		<%@include file="Header.jsp" %>
	    <div class="container">
			<div class="row">
				<div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1 col-xs-12">
					<div class="well bs-component">
						<%ArrayList<Integer> pytania = Polecenia.idPytan(id_ankiety); %>
						<form action="AktualizujAnkiete?id_ankiety=<%=id_ankiety%>" method="post" class="form-horizontal">
							<fieldset>
								<legend><b>Edytujesz ankietę o nazwie: </b><%=Polecenia.podajAnkiete(id_ankiety)%></legend>
								<div class="row form-group">
									<div class="col-md-3 col-sm-4 col-xs-12">
										<label for="inputLogin control-label">Nazwa ankiety:</label>
									</div>
									<div class="col-md-6 col-sm-5 col-xs-12" style="padding-bottom: 5px;">
										<input TYPE="TEXT" NAME="nazwa_ankiety" style="width: 100%;" value="<%=Polecenia.podajAnkiete(id_ankiety)%>" title="Maksymalna długość nazwy ankiety to 150 znaków">
									</div>
									<div class="col-md-3 col-sm-3 col-xs-12">
										<a href="DodajPytanie.jsp?id_ankiety=<%=id_ankiety%>"><span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Dodaj pytanie</a>
									</div>
								</div>
								<%for(int k=0; k< pytania.size(); k++){ 
								ArrayList<Integer> odpowiedzi = Polecenia.idOdpowiedzi(pytania.get(k));%>
								<div class="row form-group">
									<div class="col-md-2 col-sm-2 col-xs-11">
										<label for="inputLogin control-label"><%out.println("Pytanie "+(k+1)); %>:</label>
									</div>
									<div class="col-md-10 col-sm-10 col-xs-12">
										<input TYPE="TEXT" NAME="<%out.print("pytanie"+k);%>" style="width: 90%;" value="<%=Polecenia.podajPytanie(pytania.get(k))%>" title="Maksymalna długość pytania to 200 znaków">&nbsp;
										<%if(k>0){ %>
										<a href="ZmienKolejnoscPytan?id_ankiety=<%=id_ankiety%>&id_uzytkownika=<%=Polecenia.podajIdUzytkownika(login)%>&id_pytania=<%=pytania.get(k)%>&gdzie=gora"><span class="glyphicon glyphicon-chevron-up" aria-hidden="true" title="Przesuń pytanie do góry..."></span></a>
										<%} %>
										<%if(k<(pytania.size()-1)){ %>
										<a href="ZmienKolejnoscPytan?id_ankiety=<%=id_ankiety%>&id_uzytkownika=<%=Polecenia.podajIdUzytkownika(login)%>&id_pytania=<%=pytania.get(k)%>&gdzie=dol"><span class="glyphicon glyphicon-chevron-down" aria-hidden="true" title="Przesuń pytanie do dołu..."></span></a>
										<%} %>
										<a onclick="show_confirm<%=k%>()"><span class="glyphicon glyphicon-trash" aria-hidden="true" title="Usuń pytanie..."></span></a>
										<script type="text/javascript">
											function show_confirm<%=k%>() {
												if (window.confirm("Czy napewno chcesz usunąć pytanie nr.<%=(k+1)%> ?")) { 
													window.open("UsunPytanie?id_ankiety=<%=id_ankiety%>&id_pytania=<%=pytania.get(k)%>","_self");
												}
											}
										</script>
									</div>
								</div>
								<%if(Polecenia.podajRodzajPytania(pytania.get(k))==1){ %>
								<%for(int i=0; i<odpowiedzi.size(); i++){ %>
								<div class="row form-group">
									<div class="col-md-3 col-md-offset-1 col-sm-3 col-sm-offset-1 col-xs-11 col-xs-offset-1">
										<label for="inputLogin control-label"><i>Odpowiedź <%=i+1%>:</i></label>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-11 col-xs-offset-1">
										<input type="radio" disabled="disabled">
										<input TYPE="TEXT" NAME="<%out.print("pytanie"+k+"odp"+i);%>" style="width: 92%;" value="<%=Polecenia.podajOdpowiedz(odpowiedzi.get(i))%>" title="Maksymalna długość odpowiedzi to 100 znaków">
									</div>
								</div>
								<%}} else if(Polecenia.podajRodzajPytania(pytania.get(k))==2){ %>
								<%for(int i=0; i<odpowiedzi.size(); i++){ %>
								<div class="row form-group">
									<div class="col-md-3 col-md-offset-1 col-sm-3 col-sm-offset-1 col-xs-11 col-xs-offset-1">
										<label for="inputLogin control-label"><i>Odpowiedź <%=i+1%>:</i></label>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-11 col-xs-offset-1">
										<input type="checkbox" disabled="disabled">
										<input TYPE="TEXT" NAME="<%out.print("pytanie"+k+"odp"+i);%>" style="width: 92%;" value="<%=Polecenia.podajOdpowiedz(odpowiedzi.get(i))%>" title="Maksymalna długość odpowiedzi to 100 znaków">
									</div>
								</div>
								<%}} else if(Polecenia.podajRodzajPytania(pytania.get(k))==4){ %>
								<%for(int i=0; i<odpowiedzi.size(); i++){ %>
								<div class="row form-group">
									<div class="col-md-3 col-md-offset-1 col-sm-3 col-sm-offset-1 col-xs-11 col-xs-offset-1">
										<label for="inputLogin control-label"><i>Odpowiedź <%=i+1%>:</i></label>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-11 col-xs-offset-1">
										<select disabled="disabled" style="width: 12%"></select>
										<input TYPE="TEXT" NAME="<%out.print("pytanie"+k+"odp"+i);%>" style="width: 85%;" value="<%=Polecenia.podajOdpowiedz(odpowiedzi.get(i))%>" title="Maksymalna długość odpowiedzi to 100 znaków">
									</div>
								</div>
								<%}} else {%>
								<div class="row form-group">
									<div class="col-md-6 col-md-offset-1 col-sm-6 col-sm-offset-1 col-xs-11 col-xs-offset-1">
										<label for="inputLogin control-label"><i>Maksymalna długość odpowiedzi (ilość znaków):</i></label>
									</div>
									<div class="col-md-3 col-sm-3 col-xs-11 col-xs-offset-1">
										<input type="text" NAME="<%out.print("pytanie"+k+"dlugosc_odp");%>" style="width: 94%;" value="<%=Polecenia.podajDlugoscOdpowiedzi(odpowiedzi.get(0))%>" title="Maksymalnie 5 cyfr">
									</div>
								</div>
								<div class="row form-group">
									<div class="col-md-3 col-md-offset-1 col-sm-3 col-sm-offset-1 col-xs-11 col-xs-offset-1">
										<label for="inputLogin control-label"><i>Odpowiedź:</i></label>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-11 col-xs-offset-1">
										<textarea disabled="disabled" rows="5" style="width: 96.5%;">Odpowiedź typu tekstowego...</textarea>
									</div>
								</div>
								<%}} %>		
								<div class="row form-group">
									<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
										<input TYPE="submit" value="Zatwierdź zamiany" style="width: 100%;" class="btn btn-primary btn-md">
									</div>
									<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
										<a href="Profil.jsp"><input TYPE="button" value="Anuluj" style="width: 100%;" class="btn btn-primary btn-md"></a>
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
String komunikat = (String) session.getAttribute("komunikatEdytuj");
if(komunikat!=null){
	if(!komunikat.equals("Twoja ankieta została zaktualizowana poprawnie.")){
	%>
			<script type="text/javascript">
				alert('<%=komunikat%>');
			</script>
	<%
	session.setAttribute("komunikatEdytuj", null);
	}
}
}%>