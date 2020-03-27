package com.runa.lib.api.dao;

import com.runa.lib.entities.Book;

public interface IBookDao extends IAGenericDao<Book> {
	
	Book getByIsbn(String isbn);
}
