var table;
var descTempt;

$(document).ready(function() {
	$("#dica-form").submit(function(event) {
		event.preventDefault();
		saveAnimal();
	})

	openTableAnimal()
	dicaMask()
});

function dicaMask() {
	$('#peso').mask("#0,000", {
		reverse : true
	});
	$('#dataNascimento').mask('00/00/0000');
}

function openTableAnimal() {
	$
			.ajax({
				url : "getdicas",
				success : function(obj) {
					console.log(obj)
					table = $('#dica-table')
							.DataTable(
									{
										"sPaginationType" : "full_numbers",
										data : obj,
										"language" : {
											"url" : "/resources/js/plugins/data-tables/json/Portuguese-Brasil.json"
										},
										deferRender: true,
									    scrollY:     300,
									    scroller:    true,
										columns : [ {
											data : "id"
										}, {
											data : "titulo"
										}, {
											data : "descricao"
										},
										 {
											data : "url"
										}],
										dom : 'Bfrtip', // Needs button
										// container
										select : 'single',
										responsive : true,
										altEditor : true, // Enable altEditor
										buttons : [ {
											text : 'Adicionar',
											name : 'add' // do not change
										// name
										}, {
											extend : 'selected', // Bind to
											// Selected
											// row
											text : 'Editar',
											name : 'edit' // do not change
										// name
										}, {
											extend : 'selected', // Bind to
											// Selected
											// row
											text : 'Remover',
											name : 'delete' // do not change
										// name
										} ],
										"columnDefs" : [ {
											"targets" : [ 0 ],
											"visible" : false
										}, ]
									})
				}
			})
}

function saveAnimal() {
	if ($("#dica-form").valid()) {
		$
				.ajax({
					type : "POST",
					data : $("#dica-form").serializeObject(),
					url : "dica",
					success : function(obj) {
						console.log(obj)
						if (obj != null && obj != undefined) {
							$('#dica-form-modal').closeModal();
							if ($("#editing").val() == "false") 
							{
								table.row.add({
									"id" : obj.id,
									"titulo" : obj.titulo,
									"descricao" : obj.descricao,
									"url" : obj.url
								}).draw();
								swal("Sucesso!", "O registro foi salvo!",
										"success");
								$("#editing").val("false");
							} else {
								table.row({
									selected : true
								}).data({
									"id" : obj.id,
									"titulo" : obj.titulo,
									"descricao" : obj.descricao,
									"url" : obj.url
								});
								$("#editing").val("false");
								swal("Sucesso!", "O registro foi atualizado!",
										"success");
							}
						} else {
							swal("Cancelado",
									"O serviço não pode ser salvo no sistema",
									"error");
						}
					}
				})
	}
}

function dicaRemove(id)
{
	swal({
		title: "Você tem certeza que deseja cancelar?",
		text: "Não será possível recuperar esse serviço após o cancelamento!",
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: '#DD6B55',
		confirmButtonText: 'Sim!',
		cancelButtonText: "Não!",
		closeOnConfirm: false,
		closeOnCancel: false
	},
	function(isConfirm)
	{
		if (isConfirm)
		{
			$.ajax({
				type: "POST",
				data:  {"id": id},
				url: "dicaremove",
				success: function(obj)
				{
					if (obj)
					{
						 table.row({
			                 selected : true
			               }).remove();
						 table.draw();
						swal("Removido!", "O registro foi cancelado!", "success");
					} 
					else 
					{
						swal("Cancelado", "O registro não pode ser removido!", "error");
					} 
				}})
		}
		else
			swal("Cancelado", "O registro não foi removido!", "error");
	});
}

$("#dica-form").validate({
	rules : {
		sexo : "required"
	},
	errorElement : 'div',
	errorPlacement : function(error, element) {
		var placement = $(element).data('error');
		if (placement) {
			$(placement).append(error)
		} else {
			error.insertAfter(element);
		}
	}
});