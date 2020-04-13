package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.BookDto;

public interface IBookService {

	List<BookDto> getAllBooks();

	BookDto getBookById(Long id) throws Exception;

	void deleteBookById(Long id);

	BookDto updateBook(Long id, BookDto bookDto);

	BookDto createBook(BookDto dto);

}
