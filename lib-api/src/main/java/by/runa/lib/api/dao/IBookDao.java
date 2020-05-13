package by.runa.lib.api.dao;

import by.runa.lib.entities.Book;

public interface IBookDao extends IAGenericDao<Book> {

    Book getByIsbn(String isbn);
}
