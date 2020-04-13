package by.runa.lib.dao;

import org.springframework.stereotype.Repository;

import by.runa.lib.api.dao.IBookDetailsDao;
import by.runa.lib.entities.BookDetails;

@Repository
public class BookDetailsDao extends AGenericDao<BookDetails> implements IBookDetailsDao {

	public BookDetailsDao() {
		super(BookDetails.class);
	}

	
}