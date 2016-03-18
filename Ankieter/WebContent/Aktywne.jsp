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
						<center><h3 style="padding-bottom: 10px;">Aktywne ankiety:</h3></center>
						<% ArrayList<Integer> ankiety = Polecenia.idAktywnychAnkiet();
						   if(ankiety.size()>0){ %>
						<ul id="expList">
							<% for(int j=0; j<ankiety.size();j++){
							
							ArrayList<Integer> pytania = Polecenia.idPytan(ankiety.get(j));%>
							<li>
								<strong>
									<a href="http://<%=request.getServerName()+request.getContextPath()%>/Ankieta.jsp?id_ankiety=<%=ankiety.get(j)%>"> <%out.print(Polecenia.podajAnkiete(ankiety.get(j)));%></a></strong>
								
							</li>												
							<%}%>
						</ul>
						<%} else {%>
							<center><br /><h5>W tym momencie nie ma aktywnych ankiet.</h5></center>
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