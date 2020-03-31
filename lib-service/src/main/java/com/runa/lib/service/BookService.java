package com.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runa.lib.api.converters.BookConverter;
import com.runa.lib.api.converters.BookDetailsConverter;
import com.runa.lib.api.dao.IBookDao;
import com.runa.lib.api.dto.BookDto;
import com.runa.lib.api.service.IBookDetailsService;
import com.runa.lib.api.service.IBookService;
import com.runa.lib.converter.DepartmentConverter;
import com.runa.lib.entities.Book;
import com.runa.lib.entities.Department;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class BookService implements IBookService {

	@Autowired
	private IBookDao bookDao;

	@Autowired
	private DepartmentConverter departmentConverter;

	@Autowired
	private IBookDetailsService bookDetailsService;

	@Override
	public List<BookDto> getAllBooks() {
		return BookConverter.convertList(bookDao.getAll());
	}

	@Override
	public BookDto createBook(BookDto dto) {

		if (getBookbyIsbn(dto) != null) {
			for (Department dep : departmentConverter.convertToEntityAttribute(dto.getDepartment())) {
				for (Book book : bookDao.getAll()) {
					if (book.getDepartments().contains(dep)) {
						getBookbyIsbn(dto).setQuantity(getBookbyIsbn(dto).getQuantity() + 1);
					} else {
						getBookbyIsbn(dto).setQuantity(getBookbyIsbn(dto).getQuantity() + 1);
						getBookbyIsbn(dto).getDepartments().add(dep);
					}
				}
			}
			return BookConverter.entityToDto(getBookbyIsbn(dto));
		} else {
			Book book = new Book();
			book.setQuantity(1);
			book.setIsbn(dto.getIsbn());
			book.setOccupied(false);
			book.setRating(null);
			book.setDepartments(departmentConverter.convertToEntityAttribute(dto.getDepartment()));
			book.setBookDetails(BookDetailsConverter.dtoToEntity(bookDetailsService.createBookDetails(dto.getIsbn())));
			return BookConverter.entityToDto(bookDao.create(book));
		}
	}

	private Book getBookbyIsbn(BookDto dto) {
		return bookDao.getByIsbn(dto.getIsbn().trim());
	}

	@Override
	public BookDto getBookById(Long id) {
		return Optional.ofNullable(BookConverter.entityToDto(bookDao.get(id))).orElse(new BookDto());
	}

	@Override
	public void deleteBookById(Long id) {
		bookDao.delete(bookDao.get(id));
		log.info("Book successfully deleted");
	}

	@Override
	public void updateBook(String isbn, BookDto bookDto) {
		Book existingBook = Optional.ofNullable(bookDao.getByIsbn(isbn)).orElse(new Book());
		existingBook.setQuantity(bookDto.getQuantity());
		existingBook.setOccupied(bookDto.isOccupied());
		existingBook.setDepartments(departmentConverter.convertToEntityAttribute(bookDto.getDepartment()));
		bookDao.update(existingBook);
		log.info("Book successfully updated");

	}
}