package by.runa.lib.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.runa.lib.api.dto.BookDto;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.dao.BookDao;
import by.runa.lib.dao.DepartmentDao;
import by.runa.lib.entities.Book;
import by.runa.lib.utils.mappers.BookMapper;
import by.runa.lib.web.WebScraper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    private static final String TEST_ISBN = "1234567891234";

    @InjectMocks
    BookService bookService;

    @Mock
    BookDao bookDao;

    @Mock
    DepartmentDao departmentDao;

    @Mock
    WebScraper webScraper;

    @Mock
    BookMapper bookMapper;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(bookDao).isNotNull();
        assertThat(webScraper).isNotNull();
        assertThat(bookMapper).isNotNull();
    }

    @Test
    public void getAllBookTest() {
        List<Book> listBook = new ArrayList<>();
        Book book = new Book();
        book.setIsbn(TEST_ISBN);
        Book book2 = new Book();
        book.setIsbn("name2");
        Book book3 = new Book();
        book.setIsbn("name3");
        listBook.add(book);
        listBook.add(book2);
        listBook.add(book3);
        when(bookDao.getAll()).thenReturn(listBook);
        List<BookDto> dtoList = bookService.getAllBooks();
        verify(bookMapper, times(1)).toListDto(listBook);
        assertThat(listBook.size() == dtoList.size());
    }

//    @Test
//    public void createBookTest() {
//        Book book = createBook(TEST_ISBN);
//        when(bookDao.get(1L)).thenReturn(book);
//        DepartmentDto departmentDto = new DepartmentDto();
//        bookService.createBook(bookMapper.toDto(book), departmentDto);
//        verify(webScraper, times(1)).getBookDetailsFromWeb(TEST_ISBN);
//        assertThat(bookDao.get(1L).getIsbn() == TEST_ISBN);
//    }
//
//    @Test
//    public void getBookByIdTest() throws EntityNotFoundException {
//        Book book = createBook(TEST_ISBN);
//        when(bookDao.get(1L)).thenReturn(book);
//        bookService.getBookById(1L);
//        verify(bookMapper, times(1)).toDto(any(Book.class));
//        assertThat(bookDao.get(1L).getIsbn() == book.getIsbn()).isTrue();
//    }

    @Test
    public void deleteBookByIdTest() {
        Book book = createBook(TEST_ISBN);
        when(bookDao.get(1L)).thenReturn(book);
        DepartmentDto depDto = new DepartmentDto();
        bookService.deleteBookById(1L, depDto);
        verify(bookDao, times(1)).delete(book);
        assertThat(bookDao.get(1L) == null);
    }

//    @Test
//    public void updateBook() throws EntityNotFoundException {
//        Book book = createBook(TEST_ISBN);
//        when(bookDao.get(1L)).thenReturn(book);
//        String isbnToUpdate = TEST_ISBN + "new";
//        book.setIsbn(isbnToUpdate);
//        MultipartFile fichier = new MockMultipartFile("fileThatDoesNotExists.txt", "fileThatDoesNotExists.txt",
//                "text/plain", "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
//        bookService.updateBook(bookMapper.toDto(book), fichier);
//        verify(bookDao, times(1)).update(book);
//        assertThat((bookDao.get(1L)).getIsbn() == isbnToUpdate);
//    }

    private Book createBook(String name) {
        Book book = new Book();
        book.setId(1L);
        book.setIsbn(TEST_ISBN);
        book.setQuantityInLibrary(1);
        return book;
    }
}
