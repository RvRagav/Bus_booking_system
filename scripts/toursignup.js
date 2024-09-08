const form =document.querySelector('#form');
const username =document.querySelector('#uname');
const email =document.querySelector('#email');
const password =document.querySelector('#password');
const cpassword =document.querySelector('#cpassword');

form.addEventListener('submit',(e)=>{
    
    if(!validateInputs()){
        e.preventDefault();
    }
})
function validateInputs(){
    const usernameval=username.value.trim();
    const emailval=email.value.trim();
    const passwordval=password.value.trim();
    const cpasswordVal=cpassword.value.trim();
    let success=true;
    if(usernameval==''){
        success=false;
        setError(username,"Please enter the Username ");
    }
    else{
        setSuccess(username);
    }
    if(emailval==''){
        success=false;
        setError(email,"Email is Required")
    }
    else if(!(ValidateEmail(emailval))){
        success=false;
        setError(email,"Enter the valid Email");
    }
    else{
        setSuccess(email);
    }
    if(passwordval==''){
        success=false;
        setError(password,"Enter the password");
    }
    else if(passwordval.length < 8){
        success=false;
        setError(password,"Password must contain 8 Characters");
    }
   
    else {
        setSuccess(password);
    }

    if(cpasswordVal==''){
        success=false;
        setError(cpassword,"Enter the confirm password");
    }
    else if(cpasswordVal !== passwordval)
    {   success=false;
        setError(cpassword,"Confirm Password is not matched");
    }
    else{
        setSuccess(cpassword);
    }
    return success;
}

function setError(element,message){
    const inputGroup=element.parentElement;
    const errorElement=inputGroup.querySelector('.error');
    errorElement.innerHTML =message;
    inputGroup.classList.add('error');
    inputGroup.classList.remove('success');
}
function setSuccess(element){
    const inputGroup=element.parentElement
    const errorElement=inputGroup.querySelector('.error')
    errorElement.innerText ="";
    inputGroup.classList.add('success');
    inputGroup.classList.remove('error');
}
function ValidateEmail(_mail) 
{
 if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(form.email.value))
  {
    return (true)
  }
    alert("You have entered an invalid email address!")
    return (false)
}