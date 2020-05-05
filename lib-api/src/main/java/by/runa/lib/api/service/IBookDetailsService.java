package by.runa.lib.api.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import by.runa.lib.api.dto.BookDetailsDto;
import by.runa.lib.entities.BookDetails;

public interface IBookDetailsService  {

	List<BookDetailsDto> getAllBookDetails();

	BookDetailsDto getBookDetailsById(Long id);

	void deleteBookDetailsById(Long id);

	BookDetailsDto createBookDetails(String isbn);

	void updateBookDetails(BookDetails existingBookDetails, BookDetailsDto newbookDetailsDto, MultipartFile file);



}