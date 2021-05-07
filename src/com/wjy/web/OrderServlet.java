package com.wjy.web;

import com.wjy.pojo.*;
import com.wjy.service.OrderService;
import com.wjy.service.impl.OrderServiceImpl;
import com.wjy.utils.JdbcUtils;
import com.wjy.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderServlet", urlPatterns = "/orderServlet")
public class OrderServlet extends BaseServlet {

    // private OrderService orderService = new OrderServiceImpl();
    private OrderService orderService = WebUtils.getBean(OrderService.class);


    // 生成订单
    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 先获取 Cart 购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 获取 Userid
        User loginUser = (User) request.getSession().getAttribute("user");
        if (loginUser == null) {
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
            return;
        }
        Integer userId = loginUser.getId();
        String orderId = orderService.createOrder(cart, userId);

//        防止数据不一致性，以便底层的jbdcUtils进行事务回滚
//        但是使用此方法太笨拙，要想给所有servlet加上try-catch太，故使用TransactionFilter过滤器进行处理
//        try {
//            // 调用 orderService.createOrder(Cart,Userid); 生成订单
//            orderId = orderService.createOrder(cart, userId);
////            如果提交订单中没有异常，正常进行提交并且关闭连接
//            JdbcUtils.commitAndClose();
//        } catch (Exception e) {
////            如果提交订单中出现常，进行事务回滚保持数据一致性并且关闭连接
//            JdbcUtils.rollbackAndClose();
//            e.printStackTrace();
//        }

        // req.setAttribute("orderId", orderId);
        // 请求转发到 /pages/cart/checkout.jsp
        // req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req, resp);
        request.getSession().setAttribute("orderId", orderId);
//        此处不能请求转发，因为刷新页面会提交空订单
//        request.getRequestDispatcher("/pages/cart/checkout.jsp").forward(request,response);
        response.sendRedirect(request.getContextPath() + "/pages/cart/checkout.jsp");
    }

    // 用户查询订单
    protected void showMyOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取 Userid
        User loginUser = (User) request.getSession().getAttribute("user");
        List<Order> orders = orderService.showMyOrders(loginUser.getId());
        request.setAttribute("orders", orders);
        //        此处不能重定向，因为使用request域提交订单数据给浏览器，否则会丢失
        request.getRequestDispatcher("/pages/order/order.jsp").forward(request, response);
    }

    // 用户签收订单
    protected void receiveOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        orderService.receiveOrder(orderId);
        // 使用请求转发会暴露用户id，不推荐
//        request.getRequestDispatcher("/orderServlet?action=showMyOrders").forward(request,response);
        response.sendRedirect(request.getContextPath()+"/orderServlet?action=showMyOrders");
    }


        // 用户查询订单详情
    protected void showOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        List<OrderItem> orderItems = orderService.showOrderDetail(orderId);
        request.setAttribute("orderItems", orderItems);
        request.getRequestDispatcher("/pages/order/order_items.jsp").forward(request, response);
    }


    // 管理员查看所有订单
    protected void showAllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orders = orderService.showAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(request, response);
    }

    // 管理员发货
    protected void sendOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        int i = orderService.sendOrder(orderId);
//        request.getRequestDispatcher("orderServlet?action=showAllOrders").forward(request, response);
        response.sendRedirect(request.getContextPath()+"/orderServlet?action=showAllOrders");
    }

}
