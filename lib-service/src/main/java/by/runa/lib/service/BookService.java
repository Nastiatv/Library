package by.runa.lib.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
			getBookbyIsbn(dto).setQuantity(getBookbyIsbn(dto).getQuantity() + 1);
			getBookbyIsbn(dto).getDepartments().add(department);
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
			BookDetails bd = bookDetailsMapper.toEntity(bookDetailsService.createBookDetails(dto.getIsbn()));
			book.setBookDetails(bd);
			bookMapper.toDto(bookDao.create(book));
			try {
				emailSender.sendEmailsFromAdmin(book);
			} catch (MessagingException e) {
				log.info("Mail not sent!");
			}
			return bookMapper.toDto(book);
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
	public void deleteBookById(Long id, DepartmentDto departmentDto) {
		if (bookDao.get(id).getQuantity() == 1) {
			bookDao.delete(bookDao.get(id));
		} else {
			bookDao.get(id).setQuantity(bookDao.get(id).getQuantity() - 1);
			Department dep = departmentDao.getByName(departmentDto.getName());
			if (bookDao.get(id).getDepartments().contains(dep)) {
				List<Department> list = bookDao.get(id).getDepartments();
				for (Department depart : list) {
					if (depart.getName().equals(departmentDto.getName())) {
						bookDao.get(id).getDepartments().remove(depart);
						break;
					}
				}
				bookDao.update(bookDao.get(id));
			}
		}
	}

	@Override
	public BookDto updateBook(BookDto bookDto, DepartmentDto departmentDto) throws Exception {
		Book existingBook = Optional.ofNullable(bookDao.get(bookDto.getId())).orElseThrow(Exception::new);
		BookDetails ebd = existingBook.getBookDetails();
		if (departmentDto.getName() != null) {
			existingBook.setDepartments(
					Stream.of(departmentDao.getByName(departmentDto.getName())).collect(Collectors.toList()));
		}
		if (bookDto.getBookDetailsDto() != null) {
			bookDetailsService.updateBookDetails(ebd, bookDto.getBookDetailsDto());
		}
		bookDao.update(existingBook);
		return bookMapper.toDto(existingBook);

	}
}