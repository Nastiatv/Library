package com.runa.lib.dao;

import org.springframework.stereotype.Repository;

import com.runa.lib.entities.BookDetails;

@Repository
public class BookDetailsDao extends AGenericDao<BookDetails> {

	public BookDetailsDao(Class<BookDetails> clazz) {
		super(BookDetails.class);
	}
}