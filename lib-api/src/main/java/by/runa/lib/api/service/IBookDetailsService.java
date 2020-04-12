package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.BookDetailsDto;

public interface IBookDetailsService  {

	List<BookDetailsDto> getAllBookDetails();

	BookDetailsDto getBookDetailsById(Long id);

	void deleteBookDetailsById(Long id);

	void updateBookDetails(Long id, BookDetailsDto dto);

	BookDetailsDto createBookDetails(String isbn);



}