package by.runa.lib.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.runa.lib.api.dto.BookDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.dao.BookDao;
import by.runa.lib.dao.DepartmentDao;
import by.runa.lib.entities.Book;
import by.runa.lib.utils.mailsender.EmailSender;
import by.runa.lib.utils.mappers.BookDetailsMapper;
import by.runa.lib.utils.mappers.BookMapper;
import by.runa.lib.web.WebScraper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookDao bookDao;

    @Mock
    BookDetailsService bookDetailsService;

    @Mock
    DepartmentDao departmentDao;

    @Mock
    WebScraper webScraper;

    @Mock
    BookMapper bookMapper;

    @Mock
    EmailSender emailSender;

    @Mock
    BookDetailsMapper bookDetailsMapper;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(bookDao).isNotNull();
        assertThat(webScraper).isNotNull();
        assertThat(bookMapper).isNotNull();
    }

    @Test
    public void getAllBookTest() {
        List<Book> listBook = new ArrayList<>();
        listBook.add(createBook("name"));
        listBook.add(createBook("name2"));
        listBook.add(createBook("name3"));
        when(bookDao.getAll()).thenReturn(listBook);
        bookService.getAllBooks();
        verify(bookMapper, times(1)).toListDto(listBook);
    }

    @Test
    public void getBookByIdTest() throws EntityNotFoundException {
        Book book = createBook("name");
        when(bookDao.get(any(Long.class))).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(toDto(book));
        BookDto newbook = bookService.getBookById(1L);
        verify(bookMapper, times(1)).toDto(any(Book.class));
        assertThat(newbook.getIsbn() == book.getIsbn()).isTrue();
    }

    @Test
    public void updateBook() throws EntityNotFoundException {
        Book book = createBook("new");
        when(bookDao.get(any(Long.class))).thenReturn(book);
        MultipartFile fichier = new MockMultipartFile("fileThatDoesNotExists.txt", "fileThatDoesNotExists.txt",
                "text/plain", "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
        book.setIsbn("name");
        bookService.updateBook(toDto(book), fichier);
        verify(bookDao, times(1)).update(book);
    }

    private Book createBook(String name) {
        Book book = new Book();
        book.setId(1L);
        book.setIsbn(name);
        book.setQuantityInLibrary(1);
        return book;
    }

    private BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setQuantityInLibrary(book.getQuantityInLibrary());
        return dto;
    }
}