package by.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import by.runa.lib.api.dao.IAGenericDao;
import by.runa.lib.api.dao.IBookDetailsDao;
import by.runa.lib.api.dto.BookDetailsDto;
import by.runa.lib.api.mappers.AMapper;
import by.runa.lib.api.service.IBookDetailsService;
import by.runa.lib.entities.BookDetails;
import by.runa.lib.web.WebScraper;

@Service
@Transactional
public class BookDetailsService implements IBookDetailsService {

	@Autowired
	private IBookDetailsDao bookDetailsDao;

	@Autowired
	private AMapper<BookDetails, BookDetailsDto> bookDetailsMapper;

	@Autowired
	private WebScraper webScraper;

	public IAGenericDao<BookDetails> getBookDetailsDao() {
		return bookDetailsDao;
	}
	
	@Override
	public List<BookDetailsDto> getAllBookDetails() {
		return bookDetailsMapper.toListDto(getBookDetailsDao().getAll());
	}

	@Override
	public BookDetailsDto createBookDetails(String isbn) {
		BookDetails bookDetails = webScraper.getBookDetailsFromWeb(isbn);
		return bookDetailsMapper.toDto(bookDetails);
	}

	@Override
	public BookDetailsDto getBookDetailsById(Long id) {
		return Optional.ofNullable(bookDetailsMapper.toDto(getBookDetailsDao().get(id))).orElse(new BookDetailsDto());
	}

	@Override
	public void deleteBookDetailsById(Long id) {
		getBookDetailsDao().delete(getBookDetailsDao().get(id));
	}

	@Override
	public void updateBookDetails(BookDetails existingBookDetails, BookDetailsDto newbookDetailsDto,
			MultipartFile file) {
		if (newbookDetailsDto.getAuthor() != null) {
			existingBookDetails.setAuthor(newbookDetailsDto.getAuthor());
		}
		if (newbookDetailsDto.getDescription() != null) {
			existingBookDetails.setDescription(newbookDetailsDto.getDescription());
		}
		if (newbookDetailsDto.getName() != null) {
			existingBookDetails.setName(newbookDetailsDto.getName());
		}
		if (file.getSize() != 0) {
			existingBookDetails.setPicture("http://localhost:8080/img/" + existingBookDetails.getId() + ".png");
		}
		getBookDetailsDao().update(existingBookDetails);

	}
}