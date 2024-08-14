import React, { useEffect } from 'react'
import { isUserAuthenticated } from '../services/authentication/authenticationService'
import { useNavigate } from 'react-router-dom'

const ProtectedRoute = ({element:Component,...rest} ) => {

    const navigate = useNavigate()
    const authenticated = isUserAuthenticated()

        useEffect(() => {
            if (!authenticated) {
              navigate('/login', {replace:true});
            }
          }, [authenticated, navigate]);
    
    return authenticated ? <Component {...rest} /> :null

}

export default ProtectedRoute