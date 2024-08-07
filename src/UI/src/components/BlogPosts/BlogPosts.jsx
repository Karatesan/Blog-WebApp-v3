import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { BASE_URL } from '../../services/api_constans'
import apiClient from '../../services/authentication/interceptor'
import { logout } from '../../services/authentication/authenticationService'

const BlogPosts = () => {
    const [data, setData] = useState("")
    const [loading,setLoading] = useState(true)
    const [error,setError] = useState()

    useEffect(()=>{

        async function fetch_data() {

try{
            const response = await apiClient.get(BASE_URL+"blogpost");
            setData(response.data);
}catch(error){
    setError(error.response.data)
}
        }
    fetch_data().finally(()=>setLoading(false));
    
    },[])

    if(loading) return (
        <div>Loading...</div>
    )
  return (
    <div>
    <div>{data}</div>
    </div>
  )
}

export default BlogPosts