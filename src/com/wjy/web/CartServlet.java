package com.wjy.web;

import com.google.gson.Gson;
import com.wjy.pojo.Book;
import com.wjy.pojo.Cart;
import com.wjy.pojo.CartItem;
import com.wjy.pojo.User;
import com.wjy.service.BookService;
import com.wjy.service.impl.BookServiceImpl;
import com.wjy.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CartServlet", urlPatterns = "/cartServlet")
public class CartServlet extends BaseServlet {

    // private BookService bookService = new BookServiceImpl();
    private BookService bookService = WebUtils.getBean(BookService.class);

    protected void ajaxAddItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 加入购物车没有登陆，就跳转到登陆界面
        User loginUser = (User) request.getSession().getAttribute("user");
        if (loginUser == null) {
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request,response);
            return;
        }

//        // 获取商品所在页面地址
//        System.out.println("请求头地址：" + request.getHeader("Referer"));
        // 获取商品id
        Integer id = WebUtils.parseInt(request.getParameter("id"), 1);
        // 根据id查询图书
        Book book = bookService.queryBookById(id);
        System.out.println("加入购物车：" + book.toString());
        // 将Book对象转化为CartItem对象
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());

        // cart不能定义为全局变量,因为多个用户会乱
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }
//        商品添加到购物车
        cart.addItem(cartItem);

//        因为重定向不会共享request域的信息，所以需要保存在session,用于首页展示刚刚添加了什么
        request.getSession().setAttribute("lastName", cartItem.getName());

//        使用ajax异步更新页面内容
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("lastName", cartItem.getName());
        resultMap.put("totalCount", cart.getTotalCount());
        Gson gson = new Gson();
//        将Map对象转化为Json字符串对象
        String s = gson.toJson(resultMap);
        response.getWriter().write(s);
    }

//    protected void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        // 加入购物车没有登陆，就跳转到登陆界面
//        User loginUser = (User) request.getSession().getAttribute("user");
//        if (loginUser == null) {
//            request.getRequestDispatcher("/pages/user/login.jsp").forward(request,response);
//            return;
//        }
//
//        // 获取商品所在页面地址
//        String referer = request.getHeader("Referer");
//        Integer id = WebUtils.parseInt(request.getParameter("id"), 1);
//        Book book = bookService.queryBookById(id);
//        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
//
//        // cart不能定义为全局变量,因为多个用户会乱
//        Cart cart = (Cart) request.getSession().getAttribute("cart");
//        if (cart == null) {
//            cart = new Cart();
//            request.getSession().setAttribute("cart", cart);
//        }
////        商品添加到购物车
//        cart.addItem(cartItem);
//        System.out.println(cart);
//
////        因为重定向不会共享request域的信息，所以需要保存在session,用于首页展示刚刚添加了什么
//        request.getSession().setAttribute("lastItemName", cartItem.getName());
//
//        response.sendRedirect(referer);
////        // 返回商品初始主页面，不合乎用户体验，也不符合商场需求
////        response.sendRedirect(request.getContextPath());
//
//    }

    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = WebUtils.parseInt(request.getParameter("id"), 1);
        //        cart不能定义为全局变量,因为多个用户会乱
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            cart.deleteItem(id);
        }
        response.sendRedirect(request.getHeader("Referer"));
    }

    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        int count = WebUtils.parseInt(request.getParameter("count"), 1);

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            cart.updateCount(id,count);
        }
        response.sendRedirect(request.getHeader("Referer"));
    }


    /**
     * @Description: 清空购物车
     * @param: [request, response]
     * @return: void
     */
    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            cart.clear();
        }
        response.sendRedirect(request.getHeader("Referer"));
    }

}
