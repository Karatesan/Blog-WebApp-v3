import React from 'react'
import { getTokenFromLocalStorage } from '../../services/authentication/LocalStorageManager'
import { ACCESS_TOKEN_NAME } from '../../services/api_constans'
import { Link } from 'react-router-dom'
import { logout } from '../../services/authentication/authenticationService'

const TestSuccessLogin = () => {


  const logOut = async =>{
    logout();
  }

    const data = localStorage.getItem(ACCESS_TOKEN_NAME)
  return (
    <div>{data}fasfdsd
    <div><Link to={'/blogposts'}>Blogi</Link></div>
    <button onClick={logOut}>Logout</button>
    </div>
  )
}

export default TestSuccessLogin