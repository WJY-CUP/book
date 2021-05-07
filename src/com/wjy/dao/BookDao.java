package com.wjy.dao;

import com.wjy.pojo.Book;
import com.wjy.pojo.Page;

import java.util.List;

public interface BookDao {

    int addBook(Book book);

    int deleteBookById(Integer id);

    int updateBook(Book book);

    Book queryBookById(Integer id);

    List<Book> queryBooks();

    int queryForPageTotalCount();

    List<Book> queryForPageItems(Integer begin, Integer pageSize);

    List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max);

    Integer queryForPageTotalCountByPrice(int min, int max);

}
