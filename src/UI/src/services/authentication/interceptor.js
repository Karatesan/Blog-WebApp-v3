import axios from "axios";
import { ACCESS_TOKEN_NAME, BASE_URL, REFRESH_TOKEN_NAME } from "../api_constans";
import { isTokenExpired } from "./authenticationService";

const apiClient = axios.create({
    baseURL:BASE_URL,
    timeout:100000,
})

apiClient.interceptors.request.use(
    async config =>{
        const token = localStorage.getItem(ACCESS_TOKEN_NAME);
        console.log("W interceptorze ", token)
        if(token) 
            config.headers['Authorization'] = `Bearer ${token}`;
        return config;
    },
    error =>{
        return Promise.reject(error)
    }
)

//to teraz probuje refreshowac gdy api zwroci error, nawet gdy po prostu nie mamy privilegea do danej strony
apiClient.interceptors.response.use(
    response => {
        return response
    },
    async error =>{
        console.log(error)
        if(error.response && error.response.status === 401){
     
            const token = localStorage.getItem(ACCESS_TOKEN_NAME);
            if(token){
            const isAccessTokenExpired = isTokenExpired(token)
            if(isAccessTokenExpired){
                const refreshToken = localStorage.getItem(REFRESH_TOKEN_NAME)
                if(refreshToken ){
                    try{
                        const response = await axios.put(BASE_URL + "auth/refresh",null,{
                            headers:{ 'X-Refresh-Token': refreshToken}
                        })
                        localStorage.setItem("access_token", response.data.accessToken);
                        error.config.headers['Authorization'] = `Bearer ${response.data.accessToken}`;
                        return axios(error.config)
                    }catch(refreshError){

                        //check refresh token validity
                        console.log("Token refresh error",refreshError)
                    }
                }
                   // Logout or redirect to login page if refresh token fails
                   localStorage.removeItem(ACCESS_TOKEN_NAME);
                   localStorage.removeItem(REFRESH_TOKEN_NAME);
                   window.location.href ='/login'

                   console.error('Token is invalid or expired');
            }
        }
        }
        return Promise.reject(error);
    }
)

export default apiClient;