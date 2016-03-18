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
		<script src="JavaScript/ZeroClipboard.js"></script>
		<script type="text/javascript" src="JavaScript/listaAnkiet.js"></script>
		<style type="text/css">
			#listContainer{
			  margin-top:15px;
			}
			 
			#expList ul, li {
			    list-style: none;
			    margin:0;
			    padding:0;
			    cursor: pointer;
			}
			#expList p {
			    margin:0;
			    display:block;
			}
			#expList p:hover {
			    background-color:#121212;
			}
			#expList li {
			    line-height:140%;
			    text-indent:0px;
			    background-position: 1px 8px;
			    padding-left: 20px;
			    background-repeat: no-repeat;
			}
			 
			/* Collapsed state for list element */
			#expList .collapsed {
			    background-image: url(images/collapsed.png);
			}
			/* Expanded state for list element
			/* NOTE: This class must be located UNDER the collapsed one */
			#expList .expanded {
			    background-image: url(images/expanded.png);
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
			response.sendRedirect("Zaloguj.jsp");
		}
		else {
			String ia = (String) request.getAttribute("ilosc_ankiet");
	%>
	<body>
		<%@include file="Header.jsp" %>
	    <div class="container">
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div id="listContainer" class="well bs-component">
						<center><h3 style="padding-bottom: 10px;">Twoje ankiety:</h3></center>
						<% ArrayList<Integer> ankiety = Polecenia.idAnkiet(login);
						   if(ankiety.size()>0){ %>
						<ul id="expList">
							<% for(int j=0; j<ankiety.size();j++){
							boolean czyAktywna = Polecenia.czyAnkietaAktywna(ankiety.get(j));
							ArrayList<Integer> pytania = Polecenia.idPytan(ankiety.get(j));%>
							<li><strong><%out.print(Polecenia.podajAnkiete(ankiety.get(j)));%></strong>
								<ul>
									<li>
										<div class="row">
											<div class="col-md-12" style="padding-left: 0px;">
												<div class="col-md-6 col-sm-12 col-xs-12" style="padding-right: 0px; padding-left: 0px;">
												<%if(czyAktywna){ %>
													<div class="col-md-4 col-sm-4 col-xs-12" style="padding-right: 0px; padding-bottom: 5px;">
														<a href="Servlet?akcja=deaktywuj&id_ankiety=<%=ankiety.get(j)%>" class="btn btn-primary btn-xs" style="width: 100%;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> Dezaktywuj</a>
													</div>
												<%} else { %>
													<div class="col-md-4 col-sm-4 col-xs-12" style="padding-right: 0px; padding-bottom: 5px;">
														<a href="Servlet?akcja=aktywuj&id_ankiety=<%=ankiety.get(j)%>" class="btn btn-primary btn-xs" style="width: 100%;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> Aktywuj</a>
													</div>
												<%} %>
													<div class="col-md-4 col-sm-4 col-xs-12" style="padding-right: 0px; padding-bottom: 5px;">
														<a href="Wyniki.jsp?id_ankiety=<%=ankiety.get(j)%>&id_uzytkownika=<%=Polecenia.podajIdUzytkownika(login)%>" class="btn btn-primary btn-xs" style="width: 100%;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> Wyniki</a>
													</div>
													<div class="col-md-4 col-sm-4 col-xs-12" style="padding-right: 0px; padding-bottom: 5px;">
														<a href="Edytuj?id_ankiety=<%=ankiety.get(j)%>&id_uzytkownika=<%=Polecenia.podajIdUzytkownika(login)%>" class="btn btn-primary btn-xs" style="width: 100%;"><span class="glyphicon glyphicon-wrench" aria-hidden="true"></span> Edytuj</a>
													</div>
													<div class="col-md-4 col-sm-4 col-xs-12" style="padding-right: 0px; padding-bottom: 5px;">
														<a onclick="show_confirm<%=j%>()" class="btn btn-primary btn-xs" style="width: 100%;"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Usuń</a>
														<script type="text/javascript">
															function show_confirm<%=j%>() {
																if (window.confirm("Czy napewno chcesz usunąć ankietę: <%=Polecenia.podajAnkiete(ankiety.get(j))%>?")) { 
																	  window.open("Usun?id_ankiety=<%=ankiety.get(j)%>&id_uzytkownika=<%=Polecenia.podajIdUzytkownika(login)%>","_self");
																}
															}
														</script>
													</div>
												</div>
												<div class="col-md-6 col-sm-12 col-xs-12" style="padding-right: 0px; padding-left: 0px;">
													<div class="col-md-1 col-sm-1 col-xs-12" style="padding-right: 0px; padding-bottom: 5px;">
														Link:
													</div>
													<div class="col-md-9 col-sm-9 col-xs-12" style="padding-right: 0px; padding-bottom: 5px;">
														<input type="text" readonly="readonly" value="http://<%=request.getServerName()+request.getContextPath()%>/Ankieta.jsp?id_ankiety=<%=ankiety.get(j)%>" style="width: 100%;">
													</div>
													<div class="col-md-2 col-sm-2 col-xs-12" style="padding-right: 0px; padding-bottom: 5px;">
														<button id="copy-button<%=j%>" data-clipboard-text="http://<%=request.getServerName()+request.getContextPath()%>/Ankieta.jsp?id_ankiety=<%=ankiety.get(j)%>" title="Aby móc skopiować do schowka przyciskiem potrzebujesz przeglądarki, która posiada Flash'a. W przeciwnym przypadku prosimy link skopiować ręcznie..." class="btn btn-primary btn-xs" style="width: 100%;"><span class="glyphicon glyphicon-copy" aria-hidden="true"></span> Kopiuj</button>
														<script type="text/javascript">
															var client = new ZeroClipboard( document.getElementById("copy-button<%=j%>") );
															client.on( "ready", function( readyEvent ) {	
															  client.on( "aftercopy", function( event ) {
															  } );
															} );
													  	</script>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-12 col-sm-12 col-xs-12 table-responsive">						
												<table class="table table-striped table-bordered" cellspacing="0">
													<thead>
													<tr>
														<th>
															<b>Lp.:</b>
														</th>
														<th>
															<b>Pytania:</b>
														</th>
														<th>
															<b>Rodzaj pytania:</b>
														</th>
														<th>
															<b>Odpowiedzi:</b>
														</th>
													</tr>
													</thead>
													<tbody>
														<%  for(int k=0; k< pytania.size(); k++){ 
															ArrayList<Integer> odpowiedzi = Polecenia.idOdpowiedzi(pytania.get(k));%>
														<tr>
															<td rowspan="<%=odpowiedzi.size()%>">
																<%out.print(k+1); %>
															</td>
															<td rowspan="<%=odpowiedzi.size()%>">
																<%out.print(Polecenia.podajPytanie(pytania.get(k))); %>
															</td>
															<td rowspan="<%=odpowiedzi.size()%>">
																<%int rodzaj_pytania= Polecenia.podajRodzajPytania(pytania.get(k));
																  if(rodzaj_pytania==1){out.print("Jednokrotnego wyboru");}
																  else if(rodzaj_pytania==2){out.print("Wielokrotnego wyboru");}
																  else if(rodzaj_pytania==3){out.print("Tekstowe");}
																  else {out.print("Lista rozwijalna");}%>
															</td>
															<%for(int l=0; l<odpowiedzi.size(); l++){ %>
															<td>
																<%out.print(Polecenia.podajOdpowiedz(odpowiedzi.get(l))); %>
															</td>
															
														</tr>	
														<%}}%>
													</tbody>																																			
												</table>
											</div>
										</div>
									</li>
								</ul>
							</li>												
							<%}%>
						</ul>
						<%} else {%>
							<center><br /><h5>Nie stworzyłeś jeszcze żadnej aniety. Możesz to zrobić wybierając z menu "Stwórz nową ankietę".</h5></center>
						<%} %>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
<%
String komunikat = (String) session.getAttribute("komunikatDlaProfil");	
if(komunikat!=null){
%>
		<script type="text/javascript">
			alert('<%=komunikat%>');
		</script>
<%
}
session.setAttribute("komunikatDlaProfil", null);
}%>