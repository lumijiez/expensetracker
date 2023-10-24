<script>
    import * as EmailValidator from 'email-validator';
    import axios from "axios";
    import {onMount} from "svelte";
    import { getCookie, setCookie } from 'svelte-cookie';

    let isErrorVisible = false;
    let username, password;
    let message = ""

    onMount(async () => {
        console.log("Mounted");
        const access_token = getCookie('access_token');
        const refresh_token = getCookie('refresh_token');
        if (access_token && refresh_token) {
            window.location.href = '/dashboard';
        }
    });

    async function submitForm(event) {
        event.preventDefault();
        console.log("Tried to submit!");
        console.log(username);
        console.log(password);
        try {
            const response = await axios.post('http://localhost:8081/api/v1/auth/authenticate', {
                email: username,
                password: password,
            });

            const { access_token, refresh_token } = response.data;

            // Save the tokens in cookies
            setCookie('access_token', access_token);
            setCookie('refresh_token', refresh_token);
            console.log(access_token, refresh_token);
        } catch (error) {
            console.error('Login failed:', error);
        }
    }

    // function validateEmail() {
    //     let valid = EmailValidator.validate(username);
    //     isErrorVisible = !valid;
    //     message = isErrorVisible ? "Invalid e-mail!" : "";
    //     return valid;
    // }
    //
    // function validatePassword() {
    //     let valid = password.value !== '';
    //     isErrorVisible = !valid;
    //     message = isErrorVisible ? "Invalid password!" : "";
    //     return valid;
    // }

</script>

<div class="animated bounceInDown">
    <div class="container">
        {#if isErrorVisible}
            <span class="error animated tada" id="msg">{message}</span>
        {/if}
        <form name="loginForm" class="loginForm" on:submit={submitForm}>
            <h1 id="formTitle">Track<span>.io</span></h1>
            <h5>Sign in to your account.</h5>
            <input id="usernameInput" type="text" name="email" placeholder="Email or Username" autocomplete="off" on:input={
                event => {username = event.target.value}
            }>
            <input id="passwordInput" type="password" name="password" placeholder="Password" autocomplete="off" on:input={
                event => {password = event.target.value}
            }>
            <a href="/auth/recovery" class="recoveryPass">Forgot your password?</a>
            <input type="submit" value="Sign in" class="submitButton">
        </form>
        <a href="/auth/register" class="noAccount">Don't have an account? Sign up</a>
    </div> 
</div>


<style>
    @import url('https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400');

    .container {
        margin: 0;
        top: 50px;
        left: 50%;
        font-family: 'Source Sans Pro', sans-serif;
        position: absolute;
        text-align: center;
        transform: translateX(-50%);
        background-color: rgb(33, 41, 66);
        border-radius: 9px;
        border-top: 10px solid #79a6fe;
        border-bottom: 10px solid #8BD17C;
        width: 400px;
        height: 500px;
        box-shadow: 1px 1px 108.8px 19.2px rgb(25, 31, 53);
    }
    
    #formTitle {
        font-family: 'Source Sans Pro', sans-serif;
        color: #5c6bc0;
        margin-top: 94px;
    }
    
    #formTitle span {
        color: #dfdeee;
        font-weight: lighter;
    }
    
    .loginForm h5 {
        font-family: 'Source Sans Pro', sans-serif;
        font-size: 13px;
        color: #a1a4ad;
        letter-spacing: 1.5px;
        margin-top: -15px;
        margin-bottom: 70px;
    }
    
    .loginForm input[type="text"],
    .loginForm input[type="password"] {
        display: block;
        margin: 20px auto;
        background: #262e49;
        border: 0;
        border-radius: 5px;
        padding: 14px 10px;
        width: 320px;
        outline: none;
        color: #d6d6d6;
        -webkit-transition: all .2s ease-out;
        -moz-transition: all .2s ease-out;
        -ms-transition: all .2s ease-out;
        -o-transition: all .2s ease-out;
        transition: all .2s ease-out;
    }
    
    .loginForm input[type="text"]:focus,
    .loginForm input[type="password"]:focus {
        border: 1px solid #79A6FE;
    }
    
    a {
        color: #5c7fda;
        text-decoration: none;
    }
    
    a:hover {
        text-decoration: underline;
    }
    
    .submitButton {
        border: 0;
        background: #7f5feb;
        color: #dfdeee;
        border-radius: 100px;
        width: 340px;
        height: 49px;
        font-size: 16px;
        position: absolute;
        top: 79%;
        left: 8%;
        transition: 0.3s;
        cursor: pointer;
    }
    
    .submitButton:hover {
        background: #5d33e6;
    }
    
    .recoveryPass {
        position: relative;
        float: right;
        right: 28px;
    }
    
    .noAccount {
        position: absolute;
        top: 92%;
        left: 24%;
    }
    
    .error {
        text-align: center;
        width: 337px;
        height: 20px;
        padding: 2px;
        border: 0;
        border-radius: 5px;
        margin: 10px auto 10px;
        position: absolute;
        top: 31%;
        left: 7.2%;
        color: rgb(190, 67, 29);
    }
</style>