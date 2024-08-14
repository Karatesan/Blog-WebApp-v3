import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { ACCESS_TOKEN_NAME, BASE_URL, REFRESH_TOKEN_NAME, ACCESS_TOKEN_HEADER, REFRESH_TOKEN_HEADER } from '../../services/api_constans';
import axios from 'axios';
import apiClient from '../../services/authentication/interceptor';
import { logout } from '../../services/authentication/authenticationService';

const Home = () => {

  const nav = useNavigate();

  const jakasFunkcja = ()=>{
    nav("/home")
  }

  return (
    <div>
      Home
    <Link to={'login'}>Login</Link>
    <Link to={'login'}> <button>TEST</button></Link>
    </div>
    
  )
}

export default Home