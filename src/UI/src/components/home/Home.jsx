import React from 'react'
import { Link } from 'react-router-dom'
import { ACCESS_TOKEN_NAME, BASE_URL, REFRESH_TOKEN_NAME, ACCESS_TOKEN_HEADER, REFRESH_TOKEN_HEADER } from '../../services/api_constans';
import axios from 'axios';
import apiClient from '../../services/authentication/interceptor';
import { logout } from '../../services/authentication/authenticationService';

const Home = () => {

  return (
    <div>
      Home
    <Link to={'login'}>Login</Link>
    </div>
    
  )
}

export default Home