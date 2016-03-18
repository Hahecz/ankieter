<% request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");%>
<div class="navbar navbar-default navbar-fixed-top navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<a href="Zalogowano.jsp" class="navbar-brand"
				title="PrzejdÅº do strony gÅÃ³wnej..."><span
				class="glyphicon glyphicon-home" aria-hidden="true"></span> Ankieter</a>
			<button class="navbar-toggle" type="button" data-toggle="collapse"
				data-target="#navbar-main">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="navbar-main">
			<ul class="nav navbar-nav navbar-right">
				<li style="padding: 15px 0 0 15px; color: #909090;"><span
					class="glyphicon glyphicon-user" aria-hidden="true"></span>
					Zalogowano jako: <%out.print(login);%></li>
			</ul>
			<ul class="nav navbar-nav">
				<li style="height: 1px;"><hr></li>
				<li><a href="Profil.jsp"><span
						class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>
						Moje ankiety</a></li>
				<li><a href="Nowa_ankieta.jsp"><span
						class="glyphicon glyphicon-plus" aria-hidden="true"></span> Stw�rz
						now&#261; ankiet&#281;�</a></li>
				<li><a href="Aktywne.jsp"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span> Aktywne ankiety</a></li>
				<li style="height: 1px; padding: 0 0 0 0;"><hr></li>
				<li><a href="Zaloguj.jsp"><span
						class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
						Wyloguj</a></li>
			</ul>
		</div>
	</div>
</div>