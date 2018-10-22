$(document).ready(
		
		

		function() {

			// SUBMIT FORM
			$("#IndexForm").submit(function(event) {
				// Prevent the form from submitting via the browser.
				event.preventDefault();
				ajaxPost();
			});

			function ajaxPost() {

				// PREPARE FORM DATA
				var formData = {
					code : $("#code").val(),
					
				}

				// DO POST
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : "execute",
					data : JSON.stringify(formData),
					dataType : 'json',
					success : function(data){
						var output = data.result;
						$("output").html(output);
					},
				});

			}

		})