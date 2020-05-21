package by.runa.lib.service;

import by.runa.lib.api.dao.IAGenericDao;
import by.runa.lib.api.dao.IBookDao;
import by.runa.lib.api.dao.IDepartmentDao;
import by.runa.lib.api.dto.BookDetailsDto;
import by.runa.lib.api.dto.BookDto;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.service.IBookDetailsService;
import by.runa.lib.api.service.IBookService;
import by.runa.lib.entities.Book;
import by.runa.lib.entities.BookDetails;
import by.runa.lib.entities.Department;
import by.runa.lib.utils.mailsender.EmailSender;
import by.runa.lib.utils.mappers.AMapper;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public IAGenericDao<Book> getBookDao() {
        return bookDao;
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookMapper.toListDto(getBookDao().getAll());
    }

    @Override
    public BookDto createBook(BookDto dto, DepartmentDto departmentDto) {
        cleanIsbn(dto);
        Department dep = getDepartmentByName(departmentDto);
        if (getBookbyIsbn(dto) != null) {
            incrementQuantity(dto);
            getBookbyIsbn(dto).getDepartments().add(dep);
            return bookMapper.toDto(getBookbyIsbn(dto));
        } else {
            Book book = writeBook(dto, dep);
            bookMapper.toDto(getBookDao().create(book));
            try {
                emailSender.sendEmailsFromAdminAboutNewBook(book);
            } catch (MessagingException e) {
                log.info("Mail not sent!");
            }
            return bookMapper.toDto(book);
        }
    }

    @Override
    public BookDto getBookById(Long id) throws EntityNotFoundException {
        return Optional.ofNullable(bookMapper.toDto(getBookDao().get(id)))
                .orElseThrow(() -> new EntityNotFoundException("Book"));
    }

    @Override
    public void deleteBookById(Long id, DepartmentDto departmentDto) {
        Book book = getBookDao().get(id);
        if (book.getQuantityInLibrary() == 1) {
            getBookDao().delete(book);
        } else {
            removeOneBook(departmentDto, book);
        }
    }

    @Override
    public BookDto updateBook(BookDto bookDto, MultipartFile file) throws EntityNotFoundException {
        Book existingBook = Optional.ofNullable(getBookDao().get(bookDto.getId()))
                .orElseThrow(() -> new EntityNotFoundException("Book"));
        updateBookDetails(bookDto, file, existingBook);
        getBookDao().update(existingBook);
        return bookMapper.toDto(existingBook);
    }

    private void updateBookDetails(BookDto bookDto, MultipartFile file, Book existingBook) {
        if (bookDto.getBookDetailsDto() != null) {
            bookDetailsService.updateBookDetails(existingBook.getBookDetails(), bookDto.getBookDetailsDto(), file);
        }
    }

    private void removeOneBook(DepartmentDto departmentDto, Book book) {
        book.setQuantityInLibrary(book.getQuantityInLibrary() - 1)
                .setQuantityAvailable(book.getQuantityAvailable() - 1);
        removeOneDepartmentFromList(departmentDto, book);
        getBookDao().update(getBookDao().get(book.getId()));
    }

    private void removeOneDepartmentFromList(DepartmentDto departmentDto, Book book) {
        for (Department depart : book.getDepartments()) {
            if (depart.getName().equals(departmentDto.getName())) {
                book.getDepartments().remove(depart);
                break;
            }
        }
    }

    private Book writeBook(BookDto dto, Department department) {
        return new Book().setQuantityInLibrary(1).setQuantityAvailable(1).setIsbn(dto.getIsbn()).setRating(null)
                .setDepartments(Collections.singletonList(department)).setBookDetails(createBookDetails(dto));
    }

    private void cleanIsbn(BookDto dto) {
        dto.setIsbn(RegExUtils.replaceAll(dto.getIsbn(), "-", StringUtils.EMPTY).trim());
    }

    private Department getDepartmentByName(DepartmentDto departmentDto) {
        return departmentDao.getByName(departmentDto.getName());
    }

    BookDetails createBookDetails(BookDto dto) {
        return bookDetailsMapper.toEntity(bookDetailsService.createBookDetails(dto.getIsbn()));
    }

    private void incrementQuantity(BookDto dto) {
        getBookbyIsbn(dto).setQuantityAvailable(getBookbyIsbn(dto).getQuantityAvailable() + 1);
        getBookbyIsbn(dto).setQuantityInLibrary(getBookbyIsbn(dto).getQuantityInLibrary() + 1);
    }

    private Book getBookbyIsbn(BookDto dto) {
        return bookDao.getByIsbn(dto.getIsbn());
    }
}