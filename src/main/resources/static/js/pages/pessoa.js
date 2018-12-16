var table;
var descTempt;

$(document).ready(function() {
	
	openTable();
	
	$('#cpf').mask('000.000.000-00', {reverse: true});
	$('#dataNascimento').mask('00/00/0000', {reverse: true});
});

function openTable() {
	$
			.ajax({
				url : "getpessoas",
				success : function(obj) {
					console.log(obj)
					table = $('#pessoa-table')
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
										columns : [	
													{data : "id"}, 
													{data : "nome"}, 
													{data : "dataNascimento"},
													{data : "cpf"},
													{ data: "funcionario",
									    				"mRender": function(data, type, full)
									    				{
									    					if (data != false && data != undefined && data != null)
																return "Sim";
									    					else	
									    					return "Não";
									    				}
									    			}
												],
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

function save() 
{
	if ($("#pessoa-form").valid()) 
	{
		$
				.ajax({
					type : "POST",
					data : $("#pessoa-form").serializeObject(),
					url : "pessoa",
					success : function(obj) 
					{
						table.clear().rows.add(obj).draw();
						
						if (obj != null && obj != undefined) 
						{
							$('#pessoa-modal').closeModal();
							if ($("#editing").val() == "false") 
							{
								swal("Sucesso!", "O registro foi salvo!",
										"success");
								$("#editing").val("false");
								
								return 0;
							} 
							else 
							{
								$("#editing").val("false");
								swal("Sucesso!", "O registro foi atualizado!",
										"success");
								
								return 0;
							}
						} 
						else 
						{
							swal("Cancelado",
									"O registro não pode ser salvo no sistema",
									"error");
							return 0;
						}
					}
				})
	}
	
	return false;
}

function pessoaRemove(id)
{
	swal({
		title: "Você tem certeza que deseja cancelar?",
		text: "Não será possível recuperar esse registro após o cancelamento!",
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
				url: "pessoaremove",
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

jQuery.validator.addMethod(
		"validDate",
		function(value, element) {
		    return value.match(/(?:0[1-9]|[12][0-9]|3[01])\/(?:0[1-9]|1[0-2])\/(?:19|20\d{2})/);
		},
		"Please enter a valid date in the format DD/MM/YYYY"
		);

$("#pessoa-form").validate({
	submitHandler: function(form) 
	{
	    save();
	},
	rules : {
		cpf : {
			required : false,
			remote : {
				url : "public/iscpfcnpjvalido",
				type : "POST",
				data : {
					"entrada" : function() {
						return $("#cpf").val()
					}
				},
				dataFilter : function(response)
				{
					var response = jQuery.parseJSON(response);
					currentMessage = response.Message;
					
					if (response) {
						return true;
					}
					return false;
				}
			}
		},
		dataNascimento: {
			validDate: true
	    }
	},
	messages: {
		cpf: "CPF inválido!"
    },
	errorElement : "div",
	errorPlacement : function(error, element) {
		var er = error.insertAfter(element.next());

		if (er == null)
			er.insertAfter(element.next());

	}
});