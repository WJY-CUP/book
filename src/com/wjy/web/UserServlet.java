package com.wjy.web;

import com.google.gson.Gson;
import com.wjy.pojo.User;
import com.wjy.service.UserService;
import com.wjy.service.impl.UserServiceImpl;
import com.wjy.utils.WebUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

@WebServlet(name = "UserServlet",urlPatterns = "/userServlet")
public class UserServlet extends BaseServlet {

    // private UserService userService = new UserServiceImpl();
    private UserService userService = WebUtils.getBean(UserService.class);

    /**
     * @Description: 处理登陆业务
     * @param:
     * @return:
     */
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("处理登录业务");
        //  1、获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //使用BeanUtils快速获取提交参数给对象
        User user = WebUtils.copyParamToBean(request.getParameterMap(), new User());

        // 调用 userService.login()登录处理业务
        // 使用WebUtils.copyParamToBean返回的user对象
        User loginUser = userService.login(user);
        // 如果等于null,说明登录 失败!
        if (loginUser == null) {

            //  错误信息与回显的表单项信息保存到request域中，否则不知道自己到底哪里输入错了，避免重输一遍
            request.setAttribute("msg", "用户名或密码错误");
            request.setAttribute("username", username);
            //   跳回登录页面
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
//            此处不可重定向，因为需要回显错误信息，重定向会导致request域失效！
//            response.sendRedirect(request.getContextPath()+"/pages/user/login.jsp");
//            如果非想用重定向，回显信息设置如下
//            request.getSession().setAttribute("msg", "用户名或密码错误");
//            request.getSession().setAttribute("username", username);
//            login.jsp->${sessionScope.username}


        } else {
            // 登录 成功
            //跳到成功页面login_success.html
//            将登录信息User保存到session域中，这样不同页面都可以显示当前登录用户
            request.getSession().setAttribute("user", loginUser);
            request.getRequestDispatcher("/pages/user/login_success.jsp").forward(request, response);
        }
    }

    /**
     * @Description: 处理注册业务
     * @param:
     * @return:
     */
    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("处理注册业务");
        String token = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        //  1、获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String code = request.getParameter("code");

        //使用BeanUtils快速获取提交参数给对象
        //使用了泛型，否则还需要(User)转换类型
        User user = WebUtils.copyParamToBean(request.getParameterMap(), new User());


//        2、检查 验证码是否正确
        if (token != null && token.equalsIgnoreCase(code)) {
//        3、检查 用户名是否可用
            if (userService.existsUsername(username)) {
                System.out.println("用户名[" + username + "]已存在!");
//        跳回注册页面
                request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
                //回显信息保存到request域中
                request.setAttribute("msg","用户名已存在！！");
                request.setAttribute("email",email);
                request.setAttribute("username",username);

            } else {
                //      可用
//                调用Sservice保存到数据库
                //使用WebUtils.copyParamToBean返回的user对象
                userService.registUser(user);
//
//        跳到注册成功页面 regist_success.jsp
                request.getRequestDispatcher("/pages/user/regist_success.jsp").forward(request, response);
            }
        } else {
            //回显信息保存到request域中
            request.setAttribute("msg","验证码错误！！");
            request.setAttribute("email",email);
            request.setAttribute("username",username);
            request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
        }
    }

    /**
     * @Description: 处理注销业务
     * @param:
     * @return:
     */
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath());
    }

    /**
     * @Description: 处理查询用户名是否已被注册业务
     * @param:
     * @return:
     */
    protected void ajaxExistsUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean existsUsername = userService.existsUsername(username);

        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("existsUsername", existsUsername);
        Gson gson = new Gson();
        String s = gson.toJson(resultMap);
        response.getWriter().write(s);
    }
}



