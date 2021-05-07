package com.wjy.filter;

import com.wjy.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "ManagerFilter",urlPatterns = {"/pages/manager/*", "/manager/bookServlet"})
public class ManagerFilter implements Filter {
    public void destroy() {
        System.out.println("过滤器 destroy()");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        System.out.println("doFilter方法");
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        User user = (User) httpServletRequest.getSession().getAttribute("user");

        if (user == null) {
            httpServletRequest.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        } else {
            chain.doFilter(req,resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("过滤器init)方法");
    }

}
