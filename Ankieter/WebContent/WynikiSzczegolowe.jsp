<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
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
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script src="JavaScript/bootstrap.min.js"></script>
		<script src="JavaScript/Chart.js"></script>
		<style type="text/css">
		.chart-legend ul li span{
			display: inline-block;
			width: 12px;
			height: 12px;
			margin-right: 5px;
		}
		ul{
			list-style-type: none;
			padding-left: 0px;
		}
		</style>
		<!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	</head>
	<%
		String login = (String)session.getAttribute("Login");
		int id_ankiety = 0;
		if(login==null){
			response.sendRedirect("Zaloguj.jsp");
		} else {
			id_ankiety = Integer.parseInt(request.getParameter("id_ankiety"));
	%>
	<body>
		<%@include file="Header.jsp" %>
	    <div class="container">
			<div class="row">
				<div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1 col-xs-12">
					<div class="well bs-component">
						<legend><b>Podgląd wyników ankiety o nazwie: </b><%=Polecenia.podajAnkiete(id_ankiety)%></legend>
						<div class="row form-group">
							<div class="col-md-12 col-sm-12 col-xs-12 table-responsive">
								<table class="table table-striped table-bordered" cellspacing="0">
									<% ArrayList<Integer> pytania = Polecenia.idPytan(id_ankiety);
									String wynik;
									for(int i=0; i< pytania.size(); i++){%>
									<thead>
										<tr>
											<th align="center" colspan="2">
												<%=Polecenia.podajPytanie(pytania.get(i)) %>:
											</th>
										</tr>
									</thead>
									<tbody>
										<%if(Polecenia.podajRodzajPytania((int) pytania.get(i))==1 || Polecenia.podajRodzajPytania((int) pytania.get(i))==4){%>
										<% 	ArrayList<Object> lista = Polecenia.procentOdpNaPytanieJednokrotnegoWyboru((int) pytania.get(i)); 
											for(int j=0; j<lista.size(); j=j+2){ %>
											<tr>
												<td style="padding-right: 5px;">
													<%=lista.get(j)%>
												</td>
												<td>
													<%=lista.get(j+1)+"%"%>
												</td>
											</tr>
										<%	}%>
											<tr>
												<td colspan="2">
													<div class="col-md-6 col-sm-6 col-sm-12"><canvas id="wykres<%=i%>" width="200" height="200"></canvas></div>
													<div class="col-md-6 col-sm-6 col-sm-12">
														<label>Legenda:</label>
														<div id="legenda<%=i%>" class="chart-legend"></div>
													</div>
													<script type="text/javascript">
														function getRandomColor() {
														    var letters = '0123456789ABCDEF'.split('');
														    var color = '#';
														    for (var i = 0; i < 6; i++ ) {
														        color += letters[Math.floor(Math.random() * 16)];
														    }
														    return color;
														}
														var dane<%=i%> = [
														        <%for(int j=0; j<lista.size(); j=j+2){ %>
													            	{
													                	value: <%=lista.get(j+1)%>,
													                	color: getRandomColor(),
													                	label: '<%=lista.get(j)%>'
													            	}<%if(j<lista.size()-2){%>,<%}
														          }%>
													        ];
														
														var opcje<%=i%> = {
															    segmentShowStroke: false,
															    animateRotate: true,
															    animateScale: false
															}
														var wykres<%=i%> = document.getElementById("wykres<%=i%>").getContext("2d");
														var myChart = new Chart(wykres<%=i%>).Pie(dane<%=i%>, opcje<%=i%>);
														document.getElementById("legenda<%=i%>").innerHTML = myChart.generateLegend();
													</script>
												</td>
											</tr>
										<%} else if(Polecenia.podajRodzajPytania((int) pytania.get(i))==2){ %>
										<% 	ArrayList<Object> lista = Polecenia.procentOdpNaPytanieWielokrotnegoWyboru((int) pytania.get(i)); 
											for(int j=0; j<lista.size(); j=j+2){ %>
											<tr>
												<td style="padding-right: 5px;">
													<%=lista.get(j)%>
												</td>
												<td>
													<%=lista.get(j+1)+"%"%>
												</td>
											</tr>
										<%	} %>
											<tr>
												<td colspan="2">
													<div class="col-md-12 col-sm-12 col-sm-12"><canvas id="wykres<%=i%>" height="100" style="width: 100%;"></canvas></div>
													<script type="text/javascript">
														var dane<%=i%> = {
															labels: [<%for(int j=0;j<lista.size();j=j+2){ %>'<%=lista.get(j)%>'<%if(j<lista.size()-2){%>,<%}} %>],
															datasets: [
																{
																	label: "Procent",
																    fillColor: "rgba(151,187,205,0.5)",
																    strokeColor: "rgba(151,187,205,0.8)",
																    highlightFill: "rgba(151,187,205,0.75)",
																    highlightStroke: "rgba(151,187,205,1)",
																    data: [<%for(int j=1;j<lista.size();j=j+2){ %><%=lista.get(j)%><%if(j<lista.size()-2){%>,<%}} %>]
																}
															]
														};
														
														var opcje<%=i%> = {
															    segmentShowStroke: false,
															    animateRotate: true,
															    animateScale: false
															}
														var wykres<%=i%> = document.getElementById("wykres<%=i%>").getContext("2d");
														new Chart(wykres<%=i%>).Bar(dane<%=i%>, opcje<%=i%>);
													</script>
												</td>
											</tr>	
										<%} else if(Polecenia.podajRodzajPytania((int) pytania.get(i))==3){ %>
											<tr>
												<td style="padding-right: 5px;" colspan="2">
													Odpowiedź typu tekstowego (Aby zobaczyć odpowiedzi przejdź do wyników szczegółowych).
												</td>
											</tr>
										<%} %>											
									</tbody>
									<%} %>
								</table>
							</div>
						</div>
						<div class="row form-group">
							<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
								<a href="Wyniki.jsp?id_ankiety=<%=id_ankiety%>" class="btn btn-primary btn-md" style="width: 100%;">Mniej szczegółów...</a>
							</div>
							<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
								<a href="Profil.jsp" class="btn btn-primary btn-md" style="width: 100%;">Powrót</a>
							</div>
						</div>
						<div class="row form-group">
							<div class="col-md-12 col-sm-12 col-xs-12 table-responsive">
								<table class="table table-striped table-bordered" cellspacing="0">
									<thead>
										<tr>
											<th>
												Pytanie: 
											</th>
											<th>
												Odpowiedź:
											</th>
										</tr>
									</thead>
									<tbody>
										<%if(!Polecenia.wyswietlWyniki(id_ankiety).equals("")){ %>
										<%=Polecenia.wyswietlWyniki(id_ankiety)%>
										<%} else { %>
										<tr>
											<td colspan="3">
												Żaden użytkownik nie brał jeszcze udziału w tej ankiecie.
											</td>
										</tr>
										<%} %>
									</tbody>
								</table>
							</div>
						</div>
						<div class="row form-group">
							<div class="col-md-12 col-sm-12 col-xs-12 table-responsive">
								<table class="table table-striped table-bordered" cellspacing="0">
									<thead>
										<tr>
											<th>
												Użytkownik: 
											</th>
											<th>
												Odpowiedzi:
											</th>
										</tr>
									</thead>
									<tbody>
										<%HashMap<String, String> uzWyniki = Polecenia.pobierzWynikiUzytkownikow(id_ankiety);
										Set klucze = uzWyniki.entrySet();
										Iterator iter = klucze.iterator();
										String klucz = "";
										String wartosc = "";
										Entry poleMapy;
										while(iter.hasNext()) {
											poleMapy = (Entry) iter.next();
											klucz = (String) poleMapy.getKey();
										    wartosc = uzWyniki.get(klucz);%>
										    <tr>
										    	<td>
										    		<%=klucz %>
										    	</td>
										    	<td>
										    		<%=wartosc %>
										    	</td>
										    </tr>
										<%} %>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
<%}%>