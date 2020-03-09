package com.runa.lib.dao;

import com.runa.lib.entities.Book;

public class BookDao extends AGenericDao<Book> {

	public BookDao(Class<Book> clazz) {
		super(Book.class);
	}

}
