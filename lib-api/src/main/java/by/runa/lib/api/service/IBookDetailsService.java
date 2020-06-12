package by.runa.lib.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import by.runa.lib.api.dto.BookDetailsDto;
import by.runa.lib.api.exceptions.NoSuchBookException;
import by.runa.lib.entities.Book;

public interface IBookDetailsService {

    List<BookDetailsDto> getAllBookDetails();

    BookDetailsDto getBookDetailsById(Long id);

    void deleteBookDetailsById(Long id);

    BookDetailsDto createBookDetails(String isbn) throws NoSuchBookException;

    void updateBookDetails(Book existingBook, BookDetailsDto newbookDetailsDto, MultipartFile file);
}