package by.runa.lib.service;

import by.runa.lib.api.dao.IAGenericDao;
import by.runa.lib.api.dao.IBookDao;
import by.runa.lib.api.dto.BookDetailsDto;
import by.runa.lib.api.dto.BookDto;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.service.IBookDetailsService;
import by.runa.lib.api.service.IBookService;
import by.runa.lib.api.service.IDepartmentService;
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

@Slf4j
@Service
@Transactional
public class BookService implements IBookService {

    @Autowired
    private IBookDao bookDao;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private AMapper<Book, BookDto> bookMapper;

    @Autowired
    private AMapper<Department, DepartmentDto> departmentMapper;

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
    public BookDto createBook(BookDto dto, DepartmentDto departmentDto) throws EntityNotFoundException {
        cleanIsbn(dto);
        Book existingBook = getBookbyIsbnFromDao(dto);
        Department existingdepartment = getDepartmentByName(departmentDto);
        if (existingBook != null) {
            incrementQuantity(existingBook).getDepartments().add(existingdepartment);
            return bookMapper.toDto(existingBook);
        } else {
            Book book = new Book().setQuantityInLibrary(1).setQuantityAvailable(1).setIsbn(dto.getIsbn())
                    .setRating(null).setDepartments(Collections.singletonList(existingdepartment))
                    .setBookDetails(createBookDetails(dto));
            sendEmailAboutNewBookInLibrary(existingdepartment, book);
            return bookMapper.toDto(getBookDao().create(book));
        }
    }

    @Override
    public BookDto getBookById(Long id) throws EntityNotFoundException {
        return Optional.ofNullable(bookMapper.toDto(getBookDao().get(id)))
                .orElseThrow(() -> new EntityNotFoundException("Book"));
    }

    @Override
    public List<BookDto> getBooksByDepartmentId(Long id) throws EntityNotFoundException {
        return Optional.ofNullable(bookMapper.toListDto(bookDao.getBooksByDepartmentId(id)))
                .orElseThrow(() -> new EntityNotFoundException("Book"));
    }

    @Override
    public void deleteBookById(Long id, DepartmentDto departmentDto) throws EntityNotFoundException {
        BookDto bookDto = getBookById(id);
        if (bookDto.getQuantityInLibrary() == 1) {
            getBookDao().delete(getBookDao().get(id));
        } else {
            removeOneBook(departmentDto, bookDto);
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

    @Override
    public BookDto getBookByIsbn(String isbn) throws EntityNotFoundException {
        return Optional.ofNullable(bookMapper.toDto(bookDao.getByIsbn(isbn)))
                .orElseThrow(() -> new EntityNotFoundException("Book"));
    }

    private void updateBookDetails(BookDto bookDto, MultipartFile file, Book existingBook) {
        if (bookDto.getBookDetailsDto() != null) {
            bookDetailsService.updateBookDetails(existingBook.getBookDetails(), bookDto.getBookDetailsDto(), file);
        }
    }

    private void removeOneBook(DepartmentDto departmentDto, BookDto bookDto) {
        Book book = getBookDao().get(bookDto.getId());
        book.setQuantityInLibrary(bookDto.getQuantityInLibrary() - 1)
                .setQuantityAvailable(bookDto.getQuantityAvailable() - 1);
        removeOneDepartmentFromList(departmentDto, book);
        getBookDao().update(book);
    }

    private void removeOneDepartmentFromList(DepartmentDto departmentDto, Book book) {
        book.getDepartments().removeIf(department -> department.getName().equals(departmentDto.getName()));
    }

    private void cleanIsbn(BookDto dto) {
        dto.setIsbn(RegExUtils.replaceAll(dto.getIsbn(), "-", StringUtils.EMPTY).trim());
    }

    private Department getDepartmentByName(DepartmentDto departmentDto){
        return departmentMapper.toEntity(departmentService.getDepartmentByName(departmentDto.getName()));
    }

    private BookDetails createBookDetails(BookDto dto) {
        return bookDetailsMapper.toEntity(bookDetailsService.createBookDetails(dto.getIsbn()));
    }

    private Book incrementQuantity(Book existingBook) {
        existingBook.setQuantityAvailable(existingBook.getQuantityAvailable() + 1);
        return existingBook.setQuantityInLibrary(existingBook.getQuantityInLibrary() + 1);
    }

    private Book getBookbyIsbnFromDao(BookDto dto) {
        return bookDao.getByIsbn(dto.getIsbn());
    }

    private void sendEmailAboutNewBookInLibrary(Department existingdepartment, Book book) {
        try {
            emailSender.sendEmailsFromAdminAboutNewBook(book, existingdepartment);
        } catch (MessagingException e) {
            log.info("Mail not sent!");
        }
    }

}