package com.runa.lib.api.dao;

import java.util.List;

import com.runa.lib.entities.BookDetails;

public interface IBookDetailsDao  {

	Class<BookDetails> getGenericClass();

	BookDetails create(BookDetails entity);

	BookDetails get(Long id);

	void update(BookDetails entity);

	void delete(BookDetails entity);

	List<BookDetails> getAll();

}
