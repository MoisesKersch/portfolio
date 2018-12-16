
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
								<h5 class="breadcrumbs-title">${title}</h5>
								<ol class="breadcrumb">
									<li><a href="home">Home</a></li>
									<li class="active">${subTitle}</li>
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
							<h4 class="header">${tableTitle}</h4>
							<div class="row">
								<div class="col s12 ">
									<table id="${tableId}" class=" hoverable centered striped ">
										<thead>
											<tr>
												<th>ID</th>
												<th>Nome</th>
												<th>Data de Nascimento</th>
												<th>CPF</th>
												<th>Funcionário</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
						</div>

						<input id="editing" class="hide" name="editing" type="text" value="false">

						<div id="${modalId}" class="modal">
							<div class="modal-content">
								<h4>${modalTitle}</h4>
								<div class="row">
									<form class="col s12" id="${formId}">
										<input type="hidden" name="id" id="id">
										<div class="row modal-form-row">
											<div class="input-field col s12">
												<input id="nome" type="text" class="required" name="nome"> <label for="nome">Nome</label>
											</div>
										</div>

										<div class="row modal-form-row">
											<div class="input-field col s12">
												<input id="dataNascimento" type="text" class="required" name="dataNascimento"> <label for="dataNascimento">Data de Nascimento</label>
											</div>
										</div>

										<div class="row modal-form-row">
											<div class="input-field col s12">
												<input id="cpf" type="text" name="cpf"> <label for="cpf">CPF</label>
											</div>
										</div>

										<div class="row">
											<div class="col s12">
												<input type="checkbox" class="checkbox" id="funcionario" name="funcionario"> <label for="funcionario">Funcionário</label>
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