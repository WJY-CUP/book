package com.wjy.test.serviceTest;

import com.wjy.pojo.Book;
import com.wjy.pojo.Page;
import com.wjy.service.BookService;
import com.wjy.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BookServiceTest {

    private BookService bookService = new BookServiceImpl();

    @Test
    public void addBook() {
        bookService.addBook(new Book(null, "悲惨世界", "雨果", new BigDecimal(200), 10000, 10, null));
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(22);
    }

    @Test
    public void updateBook() {
        bookService.updateBook(new Book(22, "《悲惨世界》", "雨果", new BigDecimal(200), 10000, 10, null));
    }

    @Test
    public void queryBookById() {
        System.out.println(bookService.queryBookById(22));
    }

    @Test
    public void queryBooks() {
        System.out.println(bookService.queryBooks());
    }

    @Test
    public void page() {
        System.out.println(bookService.page(1, 4));
    }

    @Test
    public void pageByPrice() {
        Page<Book> pageByPrice = bookService.pageByPrice(1, 10, 50, 100);
        pageByPrice.getItems().forEach(System.out::println);
    }
}