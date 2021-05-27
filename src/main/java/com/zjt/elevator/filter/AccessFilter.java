/*
package com.zjt.elevator.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

*/
/**
 * @author zjt.
 * @version 1.0
 * @Date: 2021/4/11 19:06*//*




@Order(1)
@WebFilter(filterName = "piceaFilter", urlPatterns = {"/getElevatorsStatus","/getData","/getElevators"} , initParams = {
        @WebInitParam(name = "any", value = "/getElevatorsStatus,/getData,/getElevators")})
public class AccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(StringUtils.isEmpty(request.getParameter("clientId"))){
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("访问非法！！无ClientId");
        }else{
            chain.doFilter(request,response);
        }
    }
}
*/
