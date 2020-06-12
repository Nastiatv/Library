package by.runa.lib.dao;

import by.runa.lib.api.dao.IBookDetailsDao;
import by.runa.lib.entities.BookDetails;

import org.springframework.stereotype.Repository;

@Repository
public class BookDetailsDao extends AGenericDao<BookDetails> implements IBookDetailsDao {

    public BookDetailsDao() {
        super(BookDetails.class);
    }
}