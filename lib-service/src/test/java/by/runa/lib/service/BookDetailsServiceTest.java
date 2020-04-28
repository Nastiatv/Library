package by.runa.lib.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import by.runa.lib.api.dto.BookDetailsDto;
import by.runa.lib.api.mappers.BookDetailsMapper;
import by.runa.lib.dao.BookDetailsDao;
import by.runa.lib.entities.BookDetails;
import by.runa.lib.web.WebScraper;

@RunWith(MockitoJUnitRunner.class)
public class BookDetailsServiceTest {

	private static final String TEST_NAME = "name";

	@InjectMocks
	BookDetailsService bookDetailsService;

	@Mock
	BookDetailsDao bookDetailsDao;

	@Mock
	WebScraper webScraper;

	@Mock
	BookDetailsMapper bookDetailsMapper;

	@Test
	public void injectedComponentsAreNotNull() {
		assertThat(bookDetailsDao).isNotNull();
		assertThat(webScraper).isNotNull();
		assertThat(bookDetailsMapper).isNotNull();
	}

	@Test
	public void getAllBookDetailsTest() {
		List<BookDetails> listBookDetails = new ArrayList<>();
		BookDetails bookDetails = new BookDetails();
		bookDetails.setName(TEST_NAME);
		BookDetails bookDetails2 = new BookDetails();
		bookDetails.setName("name2");
		BookDetails bookDetails3 = new BookDetails();
		bookDetails.setName("name3");
		listBookDetails.add(bookDetails);
		listBookDetails.add(bookDetails2);
		listBookDetails.add(bookDetails3);
		when(bookDetailsDao.getAll()).thenReturn(listBookDetails);
		List<BookDetailsDto> dtoList = bookDetailsService.getAllBookDetails();
		verify(bookDetailsMapper, times(1)).toListEntities(listBookDetails);
		assertThat(listBookDetails.size() == dtoList.size());
	}

	@Test
	public void createBookDetailsTest() {
		BookDetails bookDetails = createBookDetails(TEST_NAME);
		when(webScraper.getBookDetailsFromWeb(TEST_NAME)).thenReturn(bookDetails);
		when(bookDetailsDao.get(1L)).thenReturn(bookDetails);
		bookDetailsService.createBookDetails(TEST_NAME);
		verify(webScraper, times(1)).getBookDetailsFromWeb(TEST_NAME);
		assertThat(bookDetailsDao.get(1L).getName() == TEST_NAME);
	}

	@Test
	public void getBookDetailsByIdTest() {
		BookDetails bookDetails = createBookDetails(TEST_NAME);
		when(bookDetailsDao.get(1L)).thenReturn(bookDetails);
		bookDetailsService.getBookDetailsById(1L);
		verify(bookDetailsMapper, times(1)).toDto(any(BookDetails.class));
		assertThat(bookDetailsDao.get(1L).getName() == bookDetails.getName()).isTrue();
	}

	@Test
	public void deleteBookDetailsByIdTest() {
		BookDetails bookDetails = createBookDetails(TEST_NAME);
		when(bookDetailsDao.get(1L)).thenReturn(bookDetails);
		bookDetailsService.deleteBookDetailsById(1L);
		verify(bookDetailsDao, times(1)).delete(bookDetails);
		assertThat(bookDetailsDao.get(1L) == null);
	}

	@Test
	public void updateBookDetails() {
		BookDetails bookDetails = createBookDetails(TEST_NAME);
		when(bookDetailsDao.get(1L)).thenReturn(bookDetails);
		String nameToUpdate = TEST_NAME + "new";
		BookDetailsDto bookDetailsDto = new BookDetailsDto();
		bookDetailsDto.setName(nameToUpdate);
		bookDetailsService.updateBookDetails(bookDetailsDto);
		verify(bookDetailsDao, times(1)).update(bookDetails);
		assertThat(bookDetailsDao.get(1L).getName() == nameToUpdate);
	}

	private BookDetails createBookDetails(String name) {
		BookDetails bookDetails = new BookDetails();
		bookDetails.setId(1L);
		bookDetails.setName(name);
		return bookDetails;

	}

}
