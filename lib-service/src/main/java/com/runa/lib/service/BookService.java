package com.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runa.lib.api.dao.IBookDao;
import com.runa.lib.api.dto.BookDetailsDto;
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
		return BookDto.convertList(bookDao.getAll());
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
			return BookDto.entityToDto(getBookbyIsbn(dto));
		} else {
			Book book = new Book();
			book.setId(dto.getId());
			book.setQuantity(1);
			book.setIsbn(dto.getIsbn());
			book.setOccupied(false);
			book.setRating(null);
			book.setDepartments(departmentConverter.convertToEntityAttribute(dto.getDepartment()));
			book.setBookDetails(BookDetailsDto.dtoToEntity(bookDetailsService.createBookDetails(dto.getIsbn())));
			return BookDto.entityToDto(bookDao.create(book));
		}
	}

	private Book getBookbyIsbn(BookDto dto) {
		return bookDao.getByIsbn(dto.getIsbn());
	}

	@Override
	public BookDto getBookById(Long id) {
		return Optional.ofNullable(BookDto.entityToDto(bookDao.get(id))).orElse(new BookDto());
	}

	@Override
	public void deleteBookById(Long id) {
		bookDao.delete(bookDao.get(id));
		log.info("Book successfully deleted");
	}

	@Override
	public void updateBook(Long id, BookDto bookDto) {
		Book existingBook = Optional.ofNullable(bookDao.get(id)).orElse(new Book());
		existingBook.setQuantity(bookDto.getQuantity());
		existingBook.setIsbn(bookDto.getIsbn());
		existingBook.setOccupied(bookDto.isOccupied());
		existingBook.setRating(bookDto.getRating());
//		existingBook.setDepartments(bookDto.getDepartments());
		bookDao.update(existingBook);
		log.info("Book successfully updated");

	}
}