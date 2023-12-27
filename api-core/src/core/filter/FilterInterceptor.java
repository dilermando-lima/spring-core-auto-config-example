package core.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import core.exception.ExceptionRest;
import core.mvc.MvcConfiguration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterInterceptor  implements HandlerInterceptor {

    @Autowired
    private SessionRequest sessionRequest;

    @java.lang.annotation.Inherited
    @java.lang.annotation.Target(java.lang.annotation.ElementType.METHOD)
    @java.lang.annotation.Retention( java.lang.annotation.RetentionPolicy.RUNTIME )
    public @interface PublicEndpoint {}

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod handlerMethod && !handlerMethod.hasMethodAnnotation(PublicEndpoint.class)) {
           
            ExceptionRest.throwForbiddenIF(
            "All non-public endpoint must recieve header %s ".formatted(MvcConfiguration.HEADER_NAME_X_ANY_KEY_AUTH_ID),
            request.getHeader(MvcConfiguration.HEADER_NAME_X_ANY_KEY_AUTH_ID) == null || request.getHeader(MvcConfiguration.HEADER_NAME_X_ANY_KEY_AUTH_ID).trim().isBlank());

            sessionRequest.setAnyKeyAuthId(request.getHeader(MvcConfiguration.HEADER_NAME_X_ANY_KEY_AUTH_ID));

        }
        return true;
    }

    
}
