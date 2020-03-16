package com.runa.lib.api.service;

import java.util.List;

import com.runa.lib.api.dto.BookDetailsDto;

public interface IBookDetailsService  {

	List<BookDetailsDto> getAllBookDetails();

	BookDetailsDto addBookDetails(BookDetailsDto dto);

	BookDetailsDto getBookDetailsById(Long id);

	void deleteBookDetailsById(Long id);

	void updateBookDetails(Long id, BookDetailsDto dto);



}