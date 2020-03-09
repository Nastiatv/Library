package com.runa.lib.dao;

import com.runa.lib.entities.BookDetails;

public class BookDetailsDao extends AGenericDao<BookDetails> {

	public BookDetailsDao(Class<BookDetails> clazz) {
		super(BookDetails.class);
	}
}