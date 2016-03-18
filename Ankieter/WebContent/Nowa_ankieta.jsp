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
				<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2 col-xs-12">
					<div class="well bs-component">
						<form action=dodajAnkiete method="post" class="form-horizontal">
							<fieldset>
								<legend>Witaj w kreatorze ankiety.</legend>
								<div class="row form-group">
									<div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 5px;">
										<label for="inputLogin control-label">Jak zamierzasz nazwać swoją ankietę ?:</label>
									</div>
									<div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 5px;">
										<input TYPE="TEXT" NAME="nazwa_ankiety" style="width: 100%;">
									</div>
									<div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 5px;">
										<label for="inputLogin control-label">Ile pytań zamierzasz zadać ?:</label>
									</div>
									<div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 30px;">
										<input TYPE="TEXT" NAME="ilosc_pytan" style="width: 100%;">
									</div>
									<div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 5px;">
										<label for="inputLogin control-label">Czy ankieta jest aktywna?:</label>
									</div>
									<div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 30px;">
										<input TYPE="checkbox" NAME="czy_aktywna" value="czy_aktywna" style="width: 100%;">
									</div>
									<div class="row form-group">
										<div class="col-md-12 col-sm-12 col-xs-12">
											<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
												<input TYPE="submit" value="Gotowe" class="btn btn-primary btn-md" style="width: 100%;">
											</div>
											<div class="col-md-6 col-sm-6 col-xs-12" style="padding-bottom: 5px;">
												<a href="Profil.jsp" class="btn btn-primary btn-md" style="width: 100%;">Anuluj</a>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script src="JavaScript/bootstrap.min.js"></script>
	</body>
</html>
<%
String komunikat = (String) session.getAttribute("komunikatDlaNowa_ankieta");	
if(komunikat!=null){
	if(((String) request.getParameter("kom"))==null){
%>
		<script type="text/javascript">
			alert('<%=komunikat%>');
		</script>
<%
	}
	session.setAttribute("komunikatDlaNowa_ankieta", null);
}
}
%>