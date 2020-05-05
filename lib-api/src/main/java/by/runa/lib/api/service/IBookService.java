package by.runa.lib.api.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import by.runa.lib.api.dto.BookDto;
import by.runa.lib.api.dto.DepartmentDto;

public interface IBookService {

	List<BookDto> getAllBooks();

	BookDto getBookById(Long id) throws Exception;

	BookDto createBook(BookDto bookDto, DepartmentDto departmentDto);

	BookDto updateBook(BookDto bookDto, MultipartFile file) throws Exception;

	void deleteBookById(Long id, DepartmentDto departmentDto);
}
