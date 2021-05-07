package com.wjy.filter;

import com.wjy.utils.JdbcUtils;

import com.wjy.utils.JdbcUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Description: 要想给所有需要数据一致性的操作加上事务处理，在每个servlet中都给service的方法try-catch非常麻烦！！！
 *             chain.doFilter(req, resp)有两个作用：
 *                      1.访问下一个Filter的chain.doFilter(req, resp)方法
 *                      2.访问请求所需的服务器资源。例如html,jsp,jpg,servlet等，相当于间接调用了service的方法
 *                          故给chain.doFilter(req, resp);加上try-catch即为给所有servlet(也即所有service方法)加上了try-catch
 * @param:
 * @return:
 */
@WebFilter(filterName = "TransactionFilter", urlPatterns = "/*")
public class TransactionFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
        try {
            chain.doFilter(req, resp);
            // JdbcUtils.commitAndClose();
        } catch (Exception e) {
            // JdbcUtils.rollbackAndClose();
            e.printStackTrace();
//            把异常抛给tomcat服务器用于展示友好错误页面
            throw new RuntimeException(e);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
