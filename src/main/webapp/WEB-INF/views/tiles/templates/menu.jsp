<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<aside id="left-sidebar-nav">
	<ul id="slide-out" class="side-nav fixed leftside-navigation">
		<li class="user-details cyan darken-2">
			<div class="row">
				<div class="col col s4 m4 l4">
					<img src="resources/images/avatar.jpg" alt="" class="circle responsive-img valign profile-image">
				</div>
				<div class="col col s8 m8 l8">
					<ul id="profile-dropdown" class="dropdown-content">
						<li><a href="/logout"><i class="mdi-hardware-keyboard-tab"></i> Sair</a></li>
					</ul>
					<a class="btn-flat dropdown-button waves-effect waves-light white-text profile-btn" href="#" data-activates="profile-dropdown">${login}<i class="mdi-navigation-arrow-drop-down right"></i></a>
					<p class="user-roal">Teste</p>
				</div>
			</div>
		</li>
		<li class="bold"><a href="pessoa" class="waves-effect waves-cyan"><i class="mdi-social-person-add"></i> Cadastro de Pessoas </a></li>
		<li class="bold"><a href="projeto" class="waves-effect waves-cyan"><i class="mdi-action-shop-two"></i> Cadastro de Projetos </a></li>
	</ul>
	<a href="#" data-activates="slide-out" class="sidebar-collapse btn-floating btn-medium waves-effect waves-light hide-on-large-only darken-2"><i class="mdi-navigation-menu"></i></a>
</aside>