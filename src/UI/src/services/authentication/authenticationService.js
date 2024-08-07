import { ACCESS_TOKEN_NAME, BASE_URL, REFRESH_TOKEN_NAME, REFRESH_TOKEN_HEADER } from "../api_constans";
import { setItemWithExpiration } from "./LocalStorageManager";
import { ACCESS_TOKEN_EXPIRATION, REFRESH_TOKEN_EXPIRATION } from "../api_constans";
import apiClient from "./interceptor";
import { jwtDecode } from "jwt-decode";


const AUTH_URL = "auth"

export const authenticate = async (payload) =>{
    const url = BASE_URL+AUTH_URL+"/login"

    try{
        const response = await apiClient.post(url, payload );

        localStorage.setItem(ACCESS_TOKEN_NAME,response.data.accessToken)
        localStorage.setItem(REFRESH_TOKEN_NAME,response.data.refreshToken)
        return response;
        }catch(error){
            console.error(error)
            throw error
        }
}

export const logout = async () =>{

    const response = await apiClient.post(
      BASE_URL+"auth/logout", 
      null, 
      { headers:
        {
          [REFRESH_TOKEN_HEADER]: localStorage.getItem(REFRESH_TOKEN_NAME)
        }
      }
    );
    localStorage.removeItem(ACCESS_TOKEN_NAME);
    localStorage.removeItem(REFRESH_TOKEN_NAME);
 
  }

export const isTokenExpired = (token) =>{
    const decodedToken = jwtDecode(token);
    const currentTime = Date.now()/1000;
    return decodedToken.exp < currentTime
}

// export const getRefreshedAccessToken = async ( token )=>{
//     const url = BASE_URL + AUTH_URL + "/refresh";

//     const response = await axios.put(url,{
//         headers:{
//             'X-Refresh-Token': token,
//     }});


// }

//TODO
/*
    ogarnac refreshowanie - albo zostaje jak jest - czyli po errorze refreshujemy, albo robimy sprawdzanie czasu
    Routery




*/