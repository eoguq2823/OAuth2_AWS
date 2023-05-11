import React from 'react';

import { FcGoogle } from 'react-icons/fc';
import { SiNaver } from 'react-icons/si';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const navigate = useNavigate();

    const googleAuthLoginClichHandel = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/google"
    };
    const naverAuthLoginClichHandel = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/naver"
    }

    return (
        <div>
            <input type="text" placeholder='email'/>
            <input type="password" placeholder='password'/>
            <button>Google Login</button>
            <button onClick={googleAuthLoginClichHandel}><FcGoogle/></button>
            <button onClick={naverAuthLoginClichHandel}><SiNaver /></button>
        </div>
    );
};

export default Login;