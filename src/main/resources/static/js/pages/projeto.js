var table;
var descTempt;

$(document).ready(function() {
	
	openTable();
	
	
	$("#projeto-form").submit(function(event) {
		event.preventDefault();
		save();
	})
	
	$('#dataInicio').mask('00/00/0000', {reverse: true});
	$('#dataPrevisaoFim').mask('00/00/0000', {reverse: true});
	$('#dataFim').mask('00/00/0000', {reverse: true});
	$('.money').mask("#.##0,00", {reverse: true});
});

 function toFloat(value)
 {
    value = value.toString();
     if (value.indexOf('.') !== -1 && value.indexOf(',') !== -1) {
         if (value.indexOf('.') <  value.indexOf(',')) {
            //inglês
            return parseFloat(value.replace(/,/gi,''));
         } else {
           //português
            return parseFloat(value.replace(/./gi,'').replace(/,/gi,'.'));
         }      
     } else {
        return parseFloat(value);
     }
}

function dropDown()
{
	$.ajax({
		url: "getpessoasfromprojeto",
		success: function (obj)
		{
			var next_id = $("#idGerente");
			$(next_id).empty();
			$(next_id).append($("<option></option>").attr("value", "o").text("Gerente Responsável"));
			$.each(obj, function(key, value) {
				$(next_id).append($("<option></option>").attr("value", value.id).text(value.nome));
			});
			$(next_id).material_select();
		}
	})
}

function openTable() {
	$
			.ajax({
				url : "getprojetos",
				success : function(obj) {
					console.log(obj)
					table = $('#projeto-table')
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
													{ data: "idGerente",
									    				"mRender": function(data, type, full)
									    				{
									    					return data.nome;
									    				}
									    			},
													{data : "dataInicio"},
													{data : "dataPrevisaoFim"},
													{data : "dataFim"},
													{data : "descricao"},
													{data : "status"},
													{data : "orcamento"},
													{data : "risco"}
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
	if ($("#projeto-form").valid()) 
	{
		$('#orcamento').val( toFloat($('#orcamento').val()) );
		$
				.ajax({
					type : "POST",
					data : $("#projeto-form").serializeObject(),
					url : "projeto",
					success : function(obj) 
					{
						table.clear().rows.add(obj).draw();
						
						if (obj != null && obj != undefined) 
						{
							$('#projeto-modal').closeModal();
							
							if ($("#editing").val() == "false") 
							{
								swal("Sucesso!", "O registro foi salvo!",
										"success");
								$("#editing").val("false");
							} 
							else 
							{
								$("#editing").val("false");
								swal("Sucesso!", "O registro foi atualizado!",
										"success");
							}
						} 
						else 
						{
							swal("Cancelado",
									"O registro não pode ser salvo no sistema",
									"error");
						}
					}
				})
	}
	
	return false;
}

function projetoRemove(id)
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
				url: "projetoremove",
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
		"Por favor entre um formato de data válido DD/MM/YYYY"
		);

$("#projeto-form").validate({

	rules : {
		dataInicio: {
			validDate: true
	    },
	    dataPrevisaoFim: {
	    	validDate: true
	    },
	    dataFim: {
	    	validDate: true
	    }
	},
	messages: {
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