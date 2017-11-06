var isValid = true;


//Validation
$('#sendRegisterButton').click(function () {	
	isValid = true;
	validateRegisterForm()
	if(isValid){
		$('#registerForm').submit();
	}
});
$('#loginButton').click(function(){
	$('#loginForm').submit();
});
$('#emailRegisterInput').focus(function(){
	removeEmailError();
});

$('#passwordRegisterInput').focus(function(){
	removePasswordError();
});
$('#passwordRepeatRegisterInput').focus(function(){
	removePasswordError();
});
$('#vilkarForBruk').change(function() {
    if(this.checked) {
        removeVilkarError();
    }
});


function validateRegisterForm(){
	//var captcha = grecaptcha.getResponse();
	/*if(captcha.length < 10){
		isValid = false;
		$('#recaptchaError').removeClass('sr-only');
	}
*/
	var email = $('#emailRegisterInput').val();
	var password = $('#passwordRegisterInput').val();
	var passwordRepeat = $('#passwordRepeatRegisterInput').val();
	
	
/*	if(!validateEmail(email)){
		$('#emailRegisterError').removeClass('sr-only');
		$('#emailRegisterInput').parent().addClass('has-danger');
		 isValid = false;
	}*/
	if(password.length<6){
		$('#passwordRegisterInput').parent().addClass('has-danger');
		$('#passwordRegisterError').removeClass('sr-only');
		isValid = false;
	}
	if(passwordRepeat != password){
		$('#passwordRepeatRegisterInput').parent().addClass('has-danger');
		$('#passwordRegisterInput').parent().addClass('has-danger');
		$('#passwordRepeatRegisterError').removeClass('sr-only');
		isValid = false;
	}
	if(!$('#vilkarForBruk').is(':checked')){
		$('#vilkarForBruk').closest('div').addClass('has-danger');
		$('#vilkarForBrukError').removeClass('sr-only');
		isValid = false;
	}
	return isValid;
	
}




//Register form validation
function validateEmail(email) {
	  var regex = /^(([^<>()[\]\\.,;:\s@\']+(\.[^<>()[\]\\.,;:\s@\']+)*)|(\'.+\'))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	  return regex.test(email);
}

function removeEmailError(){
	$('#emailRegisterError').addClass('sr-only');
	$('#emailRegisterInput').parent().removeClass('has-danger');
	$('#emailLoginError').addClass('sr-only');
	$('#emailLoginInput').parent().removeClass('has-danger');
}

function removePasswordError(){
	$('#passwordRepeatRegisterInput').parent().removeClass('has-danger');
	$('#passwordRegisterInput').parent().removeClass('has-danger');
	$('#passwordRepeatRegisterError').addClass('sr-only');
	$('#passwordRegisterError').addClass('sr-only');
}

function removeVilkarError(){
	$('#vilkarForBruk').closest('div').removeClass('has-danger');
	$('#vilkarForBrukError').addClass('sr-only');
}