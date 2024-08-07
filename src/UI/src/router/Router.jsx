import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Layout from './Layout'
import Home from '../components/home/Home'
import Login from '../components/authentication/Login'
import TestSuccessLogin from '../components/authentication/TestSuccessLogin'
import BlogPosts from '../components/BlogPosts/BlogPosts'

const Router = () => {
  return (
    <BrowserRouter>
        <Routes>
            <Route path='/' element={<Layout />} >
                <Route index element={<Home />} />
                <Route path='/login' element={<Login />}/>
                <Route path='/home' element={<TestSuccessLogin/>} />  
                <Route path='/blogposts' element={<BlogPosts/>}/>
            </Route>
        </Routes>
    
    
    </BrowserRouter>

  )
}

export default Router