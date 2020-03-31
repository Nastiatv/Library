package com.runa.lib.api.service;

import java.util.List;

import com.runa.lib.api.dto.BookDto;

public interface IBookService {

	List<BookDto> getAllBooks();

	BookDto getBookById(Long id);

	void deleteBookById(Long id);

	BookDto createBook(BookDto dto);

	void updateBook(String isbn, BookDto bookDto);

}
