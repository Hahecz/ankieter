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
	%>
	<body>
		<%@include file="Header.jsp" %>
	    <div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="well bs-component">
						<h2>Witaj '<%out.print(login);%>' w elektronicznym systemie obsługi ankiet !</h2>
						<p>Prosimy wybrać jedną z opcji w menu.</p>
					</div>
				</div>
			</div>
		</div>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script src="JavaScript/bootstrap.min.js"></script>
	</body>
</html>
<%}
String komunikat = (String) session.getAttribute("zalogowanoKomunikat");	
if(komunikat!=null){
	%>
		<script type="text/javascript">
			alert('<%=komunikat%>');
		</script>
	<%
}
session.setAttribute("zalogowanoKomunikat", null);
%>