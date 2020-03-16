package com.runa.lib.dao;

import org.springframework.stereotype.Repository;

import com.runa.lib.entities.Book;

@Repository
public class BookDao extends AGenericDao<Book> {

	public BookDao(Class<Book> clazz) {
		super(Book.class);
	}
}