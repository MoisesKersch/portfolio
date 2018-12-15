<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="../tiles/templates/css.jsp"></jsp:include>
<jsp:include page="../tiles/templates/header.jsp"></jsp:include>

<link href="http://cdn.datatables.net/1.10.6/css/jquery.dataTables.min.css" type="text/css" rel="stylesheet" media="screen,projection">

</head>
<body>
	<!-- body >> main >> wrapper >> content -->
	<!-- START MAIN -->
	<div id="main">
		<!-- START WRAPPER -->
		<div class="wrapper">
			<!-- START LEFT SIDEBAR NAV  MENU-->
			<jsp:include page="../tiles/templates/menu.jsp"></jsp:include>
			<!-- END LEFT SIDEBAR 	NAV MENU-->
			<!-- //////////////////////////////////////////////////////////////////////////// -->
			<!-- START CONTENT -->
			<section id="content">
				<!--breadcrumbs start-->
				<div id="breadcrumbs-wrapper" class=" grey lighten-3">
					<div class="container">
						<div class="row">
							<div class="col s12 m12 l12">
								<h5 class="breadcrumbs-title">${mainTitle}</h5>
								<ol class="breadcrumb">
									<li><a href="index.html">Dashboard</a></li>
									<li class="active">${secondTitle}</li>
								</ol>
							</div>
						</div>
					</div>
				</div>
				<!--breadcrumbs end-->
				<!--start container-->
				<div class="container">
					<div class="section">
						<p class="caption">${caption}</p>
						<div class="divider"></div>
						<!--DataTables example-->
						<div id="table-datatables">
							<h4 class="header">Tabela de Dicas</h4>
							<div class="row">
								<div class="col s12 ">
									<table id="dica-table" class=" hoverable centered striped ">
										<thead>
											<tr>
												<th>ID</th>
												<th>Título</th>
												<th>Descrição</th>
												<th>Url</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
						</div>

						<input id="editing" class="hide" name="editing" type="text" value="false">

						<div id="dica-form-modal" class="modal">
							<div class="modal-content">
								<h4>Cadastro</h4>
								<div class="row">
									<form class="col s12" id="dica-form">
										<input type="hidden" name="id" id="id">
										<div class="row modal-form-row">
											<div class="input-field col s12">
												<input id="titulo" type="text" class="required" name="titulo"> <label for="titulo">Título</label>
											</div>
										</div>

										<div class="row modal-form-row">
											<div class="input-field col s12">
												<input id="url" type="text" class="required" name="url"> <label for="url">URL</label>
											</div>
										</div>

										<div class="row modal-form-row">
											<div class="input-field col s12">
												<textarea id="descricao" class="materialize-textarea" style="height: 83.2px;" name="descricao"></textarea>
												<label for="descricao" class="active">Descrição</label>
											</div>
										</div>

										<div class="row">
											<div class="input-field col s12">
												<button class="modal-action modal-close btn  waves-effect waves-light left" type="button">
													<i class="mdi-content-undo left"></i>
												</button>
												<button class="btn cyan waves-effect waves-light right" type="submit">
													<i class="mdi-content-send right"></i>
												</button>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--end container-->
			</section>
		</div>
		<!-- END WRAPPER -->
	</div>
	<!-- END MAIN -->
	<!-- FOOTER -->
	<jsp:include page="../tiles/templates/footer.jsp"></jsp:include>
	<!-- SCRIPTS -->
	<jsp:include page="../tiles/templates/js.jsp"></jsp:include>
	<script type="text/javascript" src="/resources/js/pages/${js}"></script>
	<script type="text/javascript" src="/resources/js/pages/${jsEditor}"></script>

</body>
</html>