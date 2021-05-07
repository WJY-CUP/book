package com.wjy.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "BaseServlet")
public abstract class BaseServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("BaseServlet initialize");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("BaseServlet doPost");

        //防止bookServlet add()添加图书 表单中文乱码,提交数据进行utf-8编码
        request.setCharacterEncoding("utf-8");

        // 解决响应中文乱码问题
        // 服务器返回给浏览器的数据采取utf-8编码
        response.setContentType("text/html;charset=UTF-8");

        //jsp表单里面设置隐藏域  <input type="hidden" name="action" value="add"> 即可使用value值反射函数
        String action = request.getParameter("action");

        //使用反射后，如果增加功能，不需要很多if else了
//        if (action.equalsIgnoreCase("login")) {
//            login(request,response);
//        } else if (action.equalsIgnoreCase("regist")) {
//            regist(request,response);
//        }

        try {
            //获取相应的业务方法反射对象
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // 将异常抛给Filter过滤器
            throw new RuntimeException(e);
        }

        System.out.println("------------------------------------------------------");
        System.out.println("Servlet名字: " + getServletName());
        System.out.println("Servlet信息: " + getServletInfo());
        System.out.println("Servlet配置信息: " + getServletConfig());
        System.out.println("Servlet上下文信息: " + getServletContext());
        System.out.println("------------------------------------------------------");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
        System.out.println("BaseServlet doGet() 调用 doPost())");
    }

    @Override
    public void destroy() {
        System.out.println("BaseServlet destroy");
        super.destroy();
    }

}
