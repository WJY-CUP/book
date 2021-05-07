package com.wjy.test.daoTest;

import com.wjy.dao.BookDao;
import com.wjy.dao.impl.BookDaoImpl;
import com.wjy.pojo.Book;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class BookDaoTest {

    private BookDao bookDao = new BookDaoImpl();

    @Test
    public void addBook() {
        bookDao.addBook(new Book(null, "围城", "钱钟书", new BigDecimal(100), 10000, 20, null));
    }

    @Test
    public void deleteBookById() {
        bookDao.deleteBookById(6);
    }

    @Test
    public void updateBook() {
        bookDao.updateBook(new Book(21, "《围城》", "钱钟书", new BigDecimal(100), 10000, 20, null));
    }

    @Test
    public void queryBookById() {
        System.out.println(bookDao.queryBookById(21));
    }

    @Test
    public void queryBooks() {
        bookDao.queryBooks().forEach(System.out::println);
    }

    @Test
    public void queryForPageTotalCount() {
        System.out.println(bookDao.queryForPageTotalCount());
    }

    @Test
    public void queryForPageItems() {
        List<Book> books = bookDao.queryForPageItems(0, 5);
        books.forEach(System.out::println);
    }

    @Test
    public void queryForPageTotalCountByPrice() {
        System.out.println(bookDao.queryForPageTotalCountByPrice(10, 50));
    }


    @Test
    public void queryForPageItemsByPrice() {
        List<Book> books = bookDao.queryForPageItemsByPrice(0, 5, 50, 100);
        books.forEach(System.out::println);
    }


}