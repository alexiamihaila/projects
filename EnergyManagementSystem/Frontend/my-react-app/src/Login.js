import React from "react"
import { useState } from "react";
import './Login.css';
import { useNavigate } from 'react-router-dom';
import axios from "axios";



const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
  
    async function login(event) {
        event.preventDefault();
        try {
          const response = await axios.post('http://localhost:3003/springg-demo/auth/login', {
            username: username,
            password: password,
          });
    
          const token = response.data.token;
          localStorage.setItem('accessToken', token);
          console.log(token);
    
          const decodedToken = parseJwt(token);
    
          if (decodedToken) {
            const authorities = decodedToken.authorities;
            if (authorities && authorities.length > 0) {
              const role = authorities[0];
              console.log(role);
    
              localStorage.setItem('accessToken', token);
    
              if (role === 'ADMIN') {
                navigate('/admin/adminPage');
              } else if (role === 'CLIENT') {
                navigate('/userPage');
              } else {
                alert('Unknown role in the token.');
              }
            } else {
              alert('No authorities found in the token.');
            }
          } else {
            alert('Invalid token format.');
          }
        } catch (err) {
          console.error(err);
          alert('Error during login.');
        }
      }
    
      function parseJwt(token) {
        try {
          const base64Url = token.split('.')[1];
          const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
          const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
          }).join(''));
    
          return JSON.parse(jsonPayload);
        } catch (error) {
          console.error('Error parsing JWT token:', error);
          return null;
        }
      }

    return(
        <div>
            <div className="login-text">
                Welcome back!
            </div>
            <div className="login-container">
                <div className="login-form">
                    <form>
                        <div>
                            <label htmlFor="username">
                                Username
                            </label>
                            <input type="username" placeholder='Enter username...'
                            
                            value={username}
                            onChange={(event) => {
                                setUsername(event.target.value);
                            }}
                            
                            />
                        </div>
                        <div>
                            <label htmlFor="password">
                                Password
                            </label>
                            <input type="password" placeholder='Enter password...'
                            
                            value={password}
                            onChange={(event) => {
                            setPassword(event.target.value);
                            }}
            
                            
                            />
                        </div>
                        
                        <button type="submit" onClick={login}>
                            Login
                        </button>

                    </form>
                </div>

            </div>
        </div>

    )


} 

export default Login;