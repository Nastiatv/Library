package com.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runa.lib.api.dao.IBookDao;
import com.runa.lib.api.dto.BookDto;
import com.runa.lib.api.service.IBookService;
import com.runa.lib.entities.Book;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class BookService implements IBookService {

	@Autowired
	private IBookDao bookDao;

	@Override
	public List<BookDto> getAllBooks() {
		return BookDto.convertList(bookDao.getAll());
	}

	@Override
	public BookDto addBook(BookDto bookDto) {
		Book book = new Book();
		book.setIsbn(bookDto.getIsbn());
		book.setOccupied(bookDto.isOccupied());
		book.setQuantity(bookDto.getQuantity());
		book.setRating(bookDto.getRating());
		book.setDepartment(bookDto.getDepartment());
		return BookDto.entityToDto(bookDao.create(book));
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
		existingBook.setDepartment(bookDto.getDepartment());
		bookDao.update(existingBook);
		log.info("Book successfully updated");

	}
}