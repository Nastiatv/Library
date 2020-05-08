package by.runa.lib.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import by.runa.lib.api.dto.BookDto;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.exceptions.NoBookWithThisIdException;

public interface IBookService {

	List<BookDto> getAllBooks();

	BookDto getBookById(Long id) throws NoBookWithThisIdException;

	BookDto createBook(BookDto bookDto, DepartmentDto departmentDto);

	BookDto updateBook(BookDto bookDto, MultipartFile file) throws NoBookWithThisIdException;

	void deleteBookById(Long id, DepartmentDto departmentDto);
}
