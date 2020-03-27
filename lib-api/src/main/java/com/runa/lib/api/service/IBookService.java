package com.runa.lib.api.service;

import java.util.List;

import com.runa.lib.api.dto.BookDto;

public interface IBookService {

	List<BookDto> getAllBooks();

	BookDto getBookById(Long id);

	void deleteBookById(Long id);

	void updateBook(Long id, BookDto dto);

	BookDto createBook(BookDto dto);

}
