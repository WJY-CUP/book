package com.wjy.web;

import com.wjy.pojo.Book;
import com.wjy.pojo.Page;
import com.wjy.service.BookService;
import com.wjy.service.impl.BookServiceImpl;
import com.wjy.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "clientBookServlet", urlPatterns = "/client/bookServlet")
public class ClientBookServlet extends BaseServlet {

    // private BookService bookService = new BookServiceImpl();
    private BookService bookService = WebUtils.getBean(BookService.class);

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("经过转发而来");
        //1 获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);

        //2 调用BookService.page(pageNo，pageSize)：Page对象
        Page<Book> page = bookService.page(pageNo,pageSize);
        page.setUrl("client/bookServlet?action=page");

        //3 保存Page对象到Request域中
        request.setAttribute("page",page);
        //4 请求转发到pages/manager/book_manager.jsp页面
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request,response);

    }


    /**
     * @Description: 根据价格区间搜索商品
     * @param:
     * @return:
     */
    protected void pageByPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int max = WebUtils.parseInt(request.getParameter("max"), Integer.MAX_VALUE);
        int min = WebUtils.parseInt(request.getParameter("min"), 0);
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);

        //2 调用BookService.page(pageNo，pageSize)：Page对象
        Page<Book> page = bookService.pageByPrice(pageNo, pageSize, min, max);

        // 3 跳转地址
        StringBuilder sb = new StringBuilder("client/bookServlet?action=pageByPrice");
        // 如果有最小价格的参数,追加到分页条的地址参数中
        if (request.getParameter("min") != null) {
            sb.append("&min=").append(request.getParameter("min"));
        }
        // 如果有最大价格的参数,追加到分页条的地址参数中
        if (request.getParameter("max") != null) {
            sb.append("&max=").append(request.getParameter("max"));
        }
        page.setUrl(sb.toString());

        //4 保存Page对象到Request域中
        request.setAttribute("page",page);

        //5 请求转发到pages/manager/book_manager.jsp页面
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request,response);

    }
}
