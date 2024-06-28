package com.karatesan.WebAppApi.utility;


import com.karatesan.WebAppApi.config.PublicEndpoint;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;


@Component
public class ApiEndpointSecurityInspector {

    private final RequestMappingHandlerMapping requestHandlerMapping;
    @Getter
    private List<String> publicGetEndpoints = new ArrayList<>();
    @Getter
    private List<String> publicPostEndpoints = new ArrayList<>();
    @Getter
    private List<String> publicPutEndpoints = new ArrayList<>();

    public ApiEndpointSecurityInspector(RequestMappingHandlerMapping requestHandlerMapping) {
        this.requestHandlerMapping = requestHandlerMapping;
    }

    /**
     * Initializes the class by gathering public endpoints for various HTTP methods.
     * It identifies designated public endpoints within the application's mappings
     * and adds them to separate lists based on their associated HTTP methods.
     * If OpenAPI is enabled, Swagger endpoints are also considered as public.
     */

//Post Construct to anotacja ktora wywoluje funkcje init po stworzeniu beana tej klasy
    @PostConstruct
    public void init(){
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((requestInfo, handlerMethod)->{
            if(handlerMethod.hasMethodAnnotation(PublicEndpoint.class)){
                final HttpMethod httpMethod = requestInfo.getMethodsCondition().getMethods().iterator().next().asHttpMethod();
                final Set<String> apiPaths = requestInfo.getPathPatternsCondition().getPatternValues();

                if(httpMethod.equals(HttpMethod.GET)){
                    publicGetEndpoints.addAll(apiPaths);
                } else if(httpMethod.equals(HttpMethod.POST)){
                    publicPostEndpoints.addAll(apiPaths);
                } else if(httpMethod.equals(HttpMethod.PUT)){
                    publicPutEndpoints.addAll(apiPaths);
                }
            }
        });
    }
    //@NonNull robi generuje throwa automatycznie, tak jak w wykomentowanym checku nizej
    public boolean isUnsecureRequest(@NonNull final HttpServletRequest request){
        //if(request==null) throw new NullPointerException("request");

        HttpMethod requestHttpMethod = HttpMethod.valueOf(request.getMethod());
        List<String> unsecuredApiPaths = getUnsecuredApiPaths(requestHttpMethod);
        unsecuredApiPaths = Optional.ofNullable(unsecuredApiPaths).orElseGet(ArrayList::new);

        return unsecuredApiPaths.stream().anyMatch(apiPath-> new AntPathMatcher().match(apiPath,request.getRequestURI()));
    }

    private List<String > getUnsecuredApiPaths(@NonNull final HttpMethod httpMethod) {
        switch (httpMethod.toString()){
            case "GET":
                return publicGetEndpoints;
            case "PUT":
                return  publicPutEndpoints;
            case "POST":
                return publicPostEndpoints;
            default:
                return Collections.emptyList();
        }
    }
}
