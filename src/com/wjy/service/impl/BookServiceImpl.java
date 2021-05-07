package com.wjy.service.impl;

import com.wjy.dao.BookDao;
import com.wjy.dao.impl.BookDaoImpl;
import com.wjy.pojo.Book;
import com.wjy.pojo.Page;
import com.wjy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 16:44 2021/1/2
 * @E-mail: 15611562852@163.com
 */
@Service
public class BookServiceImpl implements BookService {

    // private BookDao bookDao = new BookDaoImpl();
    @Autowired
    private BookDao bookDao;

    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        Page<Book> page = new Page<>();

        // 求总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCount();
        page.setPageTotalCount(pageTotalCount);
        // 求总页码
        Integer pageTotal= pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0) {
            pageTotal++;
        }
        page.setPageTotal(pageTotal);

        // 求当前页码
        page.setPageNo(pageNo);

        // 求书籍列表
        int begin = (pageNo - 1) * pageSize;
        page.setItems(bookDao.queryForPageItems(begin, pageSize));
        return page;
    }

    @Override
    public Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max) {
        Page<Book> page = new Page<>();

        // 求总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCountByPrice(min, max);
        page.setPageTotalCount(pageTotalCount);

        // 求总页码
        Integer pageTotal= pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0) {
            pageTotal++;
        }
        page.setPageTotal(pageTotal);

        // 求当前页码
        page.setPageNo(pageNo);

        // 求书籍列表
        int begin = (pageNo - 1) * pageSize;
        page.setItems(bookDao.queryForPageItemsByPrice(begin, pageSize, min, max));
        return page;
    }
}
