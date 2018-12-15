$(document).ready(function(){

	$("#registrationForm").submit(function(event) 
	{
		 send();
	})
})

function send()
{
	if ($("#registrationForm").valid()) 
	{
		$('#registrationForm').submit();
	}
}

$("#registrationForm").validate({
	rules : {
		senha : "required",
		passwordConfirm : {
			equalTo : "#senha"
		},
		login : {
			required : true,
			remote : {
				url : "isusuarioexiste",
				type : "POST",
				data : {
					"entrada" : function() {
						return $("#login").val()
					}
				},
				dataFilter : function(response)
				{
					var response = jQuery.parseJSON(response);
					currentMessage = response.Message;
					
					if (response) {
						return false;
					}
					return true;
				}
			}
		}
	},
	
	messages: {
		login: "Usuário já existente no sistema!"
    },
	errorElement : "div",
	errorPlacement : function(error, element) {
		var er = error.insertAfter(element.next());

		if (er == null)
			er.insertAfter(element.next());

	}
});
