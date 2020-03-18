package com.runa.lib.dao;

import org.springframework.stereotype.Repository;

import com.runa.lib.api.dao.IBookDetailsDao;
import com.runa.lib.entities.BookDetails;

@Repository
public class BookDetailsDao extends AGenericDao<BookDetails> implements IBookDetailsDao {

	public BookDetailsDao() {
		super(BookDetails.class);
	}
}