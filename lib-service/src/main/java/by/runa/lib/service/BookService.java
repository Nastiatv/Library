package by.runa.lib.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.runa.lib.api.dao.IBookDao;
import by.runa.lib.api.dao.IDepartmentDao;
import by.runa.lib.api.dto.BookDetailsDto;
import by.runa.lib.api.dto.BookDto;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.mappers.AMapper;
import by.runa.lib.api.service.IBookDetailsService;
import by.runa.lib.api.service.IBookService;
import by.runa.lib.entities.Book;
import by.runa.lib.entities.BookDetails;
import by.runa.lib.entities.Department;
import by.runa.lib.utils.mailsender.EmailSender;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class BookService implements IBookService {

	@Autowired
	private IBookDao bookDao;

	@Autowired
	private IDepartmentDao departmentDao;

	@Autowired
	private AMapper<Book, BookDto> bookMapper;

	@Autowired
	private AMapper<BookDetails, BookDetailsDto> bookDetailsMapper;

	@Autowired
	private IBookDetailsService bookDetailsService;

	@Autowired
	private EmailSender emailSender;

	@Override
	public List<BookDto> getAllBooks() {
		return bookMapper.toListEntities(bookDao.getAll());
	}

	@Override
	public BookDto createBook(BookDto dto, DepartmentDto departmentDto) {
		Department department = departmentDao.getByName(departmentDto.getName());
		if (getBookbyIsbn(dto) != null) {
			if (getBookbyIsbn(dto).getDepartments().contains(department)) {
				getBookbyIsbn(dto).setQuantity(getBookbyIsbn(dto).getQuantity() + 1);
			} else {
				getBookbyIsbn(dto).setQuantity(getBookbyIsbn(dto).getQuantity() + 1);
				getBookbyIsbn(dto).getDepartments().add(department);
				try {
					emailSender.sendEmailsFromAdmin(dto);
				} catch (MessagingException e) {
					log.info("Mail not sent!");
				}
			}
			return bookMapper.toDto(getBookbyIsbn(dto));
		} else {
			Book book = new Book();
			book.setQuantity(1);
			dto.setIsbn(RegExUtils.replaceAll(dto.getIsbn(), "-", StringUtils.EMPTY).trim());
			book.setIsbn(dto.getIsbn());
			book.setOccupied(false);
			book.setRating(null);
			List<Department> departmentInList = new ArrayList<>();
			departmentInList.add(department);
			book.setDepartments(departmentInList);
			BookDetails bd=bookDetailsMapper.toEntity(bookDetailsService.createBookDetails(dto.getIsbn()));
			book.setBookDetails(bd);
			try {
				emailSender.sendEmailsFromAdmin(bookMapper.toDto(book));
			} catch (MessagingException e) {
				log.info("Mail not sent!");
			}
			return bookMapper.toDto(bookDao.create(book));
		}
	}

	private Book getBookbyIsbn(BookDto dto) {
		return bookDao.getByIsbn(dto.getIsbn().trim());
	}

	@Override
	public BookDto getBookById(Long id) throws Exception {
		return Optional.ofNullable(bookMapper.toDto(bookDao.get(id))).orElseThrow(Exception::new);
	}

	@Override
	public void deleteBookById(Long id) {
		bookDao.delete(bookDao.get(id));
		log.info("Book successfully deleted");
	}

	@Override
	public BookDto updateBook(Long id, BookDto bookDto) {
		Book existingBook = Optional.ofNullable(bookDao.get(id)).orElse(new Book());
		existingBook.setQuantity(bookDto.getQuantity());
		existingBook.setOccupied(bookDto.isOccupied());
		existingBook.setDepartments(bookDto.getDepartments());
		bookDao.update(existingBook);
		return bookMapper.toDto(existingBook);

	}
}