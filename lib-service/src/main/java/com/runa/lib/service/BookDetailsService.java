package com.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runa.lib.api.dao.IBookDetailsDao;
import com.runa.lib.api.dto.BookDetailsDto;
import com.runa.lib.api.service.IBookDetailsService;
import com.runa.lib.entities.BookDetails;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class BookDetailsService implements IBookDetailsService {

	@Autowired
	private IBookDetailsDao bookDetailsDao;

	@Override
	public List<BookDetailsDto> getAllBookDetails() {
		return BookDetailsDto.convertList(bookDetailsDao.getAll());
	}

	@Override
	public BookDetailsDto addBookDetails(BookDetailsDto bookDetailsDto) {
		BookDetails bookDetails = new BookDetails();
		bookDetails.setName(bookDetailsDto.getName());
		bookDetails.setAuthor(bookDetailsDto.getAuthor());
		bookDetails.setDiscription(bookDetailsDto.getDiscription());
		return BookDetailsDto.entityToDto(bookDetailsDao.create(bookDetails));
	}

	@Override
	public BookDetailsDto getBookDetailsById(Long id) {
		return Optional.ofNullable(BookDetailsDto.entityToDto(bookDetailsDao.get(id))).orElse(new BookDetailsDto());
	}

	@Override
	public void deleteBookDetailsById(Long id) {
		bookDetailsDao.delete(bookDetailsDao.get(id));
		log.info("BookDetails successfully deleted");
	}

	@Override
	public void updateBookDetails(Long id, BookDetailsDto bookDetailsDto) {
		BookDetails existingBookDetails = Optional.ofNullable(bookDetailsDao.get(id)).orElse(new BookDetails());
		existingBookDetails.setDiscription(bookDetailsDto.getDiscription());
		existingBookDetails.setName(bookDetailsDto.getName());
		existingBookDetails.setAuthor(bookDetailsDto.getAuthor());
		bookDetailsDao.update(existingBookDetails);
		log.info("BookDetails successfully updated");

	}
}