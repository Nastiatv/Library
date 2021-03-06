package by.runa.lib.api.dao;

import java.util.List;

import by.runa.lib.entities.Book;

public interface IBookDao extends IAGenericDao<Book> {

    Book getByIsbn(String isbn);

    List<Book> getBooksByDepartmentId(Long id);
}
