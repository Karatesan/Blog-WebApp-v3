//package com.karatesan.WebAppApi.model.security.filter;
//
//import com.karatesan.WebAppApi.model.security.securityConstants.SecurityConstants;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//public class jwtTokenFilter2 extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String header = request.getHeader(SecurityConstants.JWT_HEADER);
//        //sprawdzamy czy request ma header, jezeli nie to moze to byc request do loginu, wiec puszczamy dalej
//        if(header.isEmpty() || !header.startsWith("Bearer")){
//            filterChain.doFilter(request,response);
//            return;
//        }
//
//        //geet token and validate
//        final String token = header.split(" ")[1].trim();
//        if(!jwtTokenUtil.validate(token)){
//            filterChain.doFilter(request,response);
//            return;
//        }
//    }
//}
