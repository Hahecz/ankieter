<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
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
    <body>
		<div class="navbar navbar-default navbar-fixed-top navbar-inverse">
			<div class="container">
		        <div class="navbar-header">
		          	<a href="Zalogowano.jsp" class="navbar-brand" title="Przejdź do strony głównej..."><span class="glyphicon glyphicon-home" aria-hidden="true"></span> Ankieter</a>
		          	<button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
		            	<span class="icon-bar"></span>
		            	<span class="icon-bar"></span>
		            	<span class="icon-bar"></span>
		        	</button>
		        </div>
		    	<div class="navbar-collapse collapse" id="navbar-main">
		    		<ul class="nav navbar-nav navbar-right">
			        	<li><a href="Rejestracja.jsp"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Rejestracja</a></li>
			        </ul>	
		    	</div>
			</div>
		</div>
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3 col-xs-12">
					<div class="well bs-component">
						<form method="post" class="form-horizontal" action=<%out.print("Logowanie?id_ankiety="+request.getParameter("id_ankiety"));%>>
							<fieldset>
							<legend>Prosimy o zalogowanie się:</legend>
							<div class="row form-group">
								<div class="col-md-2">
									<label for="inputLogin control-label">Login:</label>
								</div>
								<div class="col-md-10">
									<input id="inputLogin" type="text" name="Login" value="" class="form-control">
								</div>
							</div>
							<div class="row form-group">
								<div class="col-md-2">
									<label>Hasło:</label>
								</div>
								<div class="col-md-10">
									<input type="password" name="Haslo" value="" class="form-control">
								</div>
							</div>
							<div class="row form-group">
								<div class="col-md-10 col-md-offset-2">
									<input type="submit" class="btn btn-primary form-control" value="Zaloguj"/>
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
String komunikat = (String) session.getAttribute("logowanieKomunikat");	
if(komunikat!=null){
	%>
		<script type="text/javascript">
			alert('<%=komunikat%>');
		</script>
	<%
}
session.setAttribute("logowanieKomunikat", null);
session.invalidate();
%>