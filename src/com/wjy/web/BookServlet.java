package com.wjy.web;

import com.wjy.pojo.Book;
import com.wjy.pojo.Page;
import com.wjy.service.BookService;
import com.wjy.service.impl.BookServiceImpl;
import com.wjy.utils.WebUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 9:02 2021/1/7
 * @E-mail: 15611562852@163.com
 */

@WebServlet(name = "BookServlet", urlPatterns = "/manager/bookServlet")
public class BookServlet extends BaseServlet {

    // private BookService bookService = new BookServiceImpl();
    private BookService bookService = WebUtils.getBean(BookService.class);

    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        <td><a href="pages/manager/book_edit.jsp?pageNo=${requestScope.page.pageTotal}">添加图书</a></td>
//        前端传递过来的是最后一页的页码
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 0);
//        如果新增图书后正好新增了一页，需要加1，不新增页码的话也会页码限制跳转到最后一页
        pageNo += 1;

//        BeanUtils jar包
        Book book = WebUtils.copyParamToBean(request.getParameterMap(), new Book());
        bookService.addBook(book);

        /*
            不可使用请求转发，否则刷新页面会重复添加！！！！！！！！！！！！！！！
            Servlet处理完请求以后，直接转发到目标页面，这样整个业务只发送了一次请求，
            那么当你在浏览器中点击刷新会一直都会刷新之前的请求。
         */

        //getRequestDispatcher为工程内请求转发，第一个/为工程名
        //sendRedirect中的第一个/为端口号，所以需要加上request.getContextPath()表示工程名
//        参数加 &pageNo=" + request.getParameter("pageNo") 的原因是新增图书后需要返回到最后一页，方便查询新添加的图书
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo);

    }

    protected void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 0);
        String id = request.getParameter("id");
        bookService.deleteBookById(WebUtils.parseInt(id, 1));
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo);
    }

    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 修改后需要返回修改条目所在的页面，否则返回第一页不人性化
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 0);
        Book book = WebUtils.copyParamToBean(request.getParameterMap(), new Book());
        bookService.updateBook(book);
        /*
            重定向的速度比转发慢，因为浏览器还得发出一个新的请求，所以如果在使用转发和重定向都无所谓的时候建议使用转发；
         */
        request.getRequestDispatcher("/manager/bookServlet?action=page&pageNo=" + pageNo).forward(request,response);
//        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo);
    }

//    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        //1.使用bookService请求数据
//        List<Book> books = bookService.queryBooks();
//        //2.将数据保存到request域中
//        request.setAttribute("books", books);
//        //3.请求转发到pages/manager/book_manager.jsp页面
//        //不可以用重定向，重定向为新的request，保存在初始request中的回显信息会丢失
//        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);
//    }

    /**
     * @Description: 修改图书先读取要修改的图书信息
     * @param:
     * @return:
     */
    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1 获取请求的参数图书编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        //2 调用bookService.queryBookById查询图书
        Book book = bookService.queryBookById(id);
        //3 保存到图书到Request域中
        request.setAttribute("book", book);
        //4 请求转发到。pages/manager/book_edit.jsp页面
//        response.sendRedirect(request.getContextPath() + "/pages/manager/book_edit.jsp");
        //不可以用重定向，重定向为新的request，保存在初始request中的回显信息会丢失
        request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request,response);
    }

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1 获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        //2 调用BookService.page(pageNo，pageSize)：Page对象
        Page<Book> page = bookService.page(pageNo,pageSize);
        page.setUrl("manager/bookServlet?action=page");

        //3 保存Page对象到Request域中
        request.setAttribute("page",page);
        //4 请求转发到pages/manager/book_manager.jsp页面
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);

    }

}
