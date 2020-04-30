package by.runa.lib.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import by.runa.lib.api.dao.IBookDao;
import by.runa.lib.entities.Book;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoTest {

	private static final String TEST_ISBN = "9780333630457";

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private IBookDao bookDao;

	@Test
	public void injectedComponentsAreNotNull() {
		assertThat(bookDao).isNotNull();
		assertThat(entityManager).isNotNull();
	}

	@Test
	public void getById() {
		Long id = entityManager.persistAndGetId(createBook(TEST_ISBN), Long.class);
		Book bookInRep = bookDao.get(id);
		assertThat(bookInRep.getId().equals(id)).isTrue();
	}

	@Test
	public void getAll() {
		entityManager.persist(createBook(TEST_ISBN));
		entityManager.persist(createBook(TEST_ISBN + 1));
		entityManager.persist(createBook(TEST_ISBN + 2));
		List<Book> allBooksInRep = bookDao.getAll();
		assertThat(allBooksInRep.size() == 3).isTrue();
	}

	@Test
	public void update() {
		Long id = entityManager.persistAndGetId(createBook(TEST_ISBN), Long.class);
		Book bookInRep = bookDao.get(id);
		bookInRep.setQuantity(999);
		entityManager.merge(bookInRep);
		assertThat(bookInRep.getQuantity() == 999).isTrue();
	}

	@Test
	public void delete() {
		Book book = entityManager.persist(createBook(TEST_ISBN));
		entityManager.remove(book);
		assertThat(bookDao.getAll().isEmpty()).isTrue();
	}

	@Test
	public void getByIsbn() {
		entityManager.persist(createBook(TEST_ISBN));
		Book bookInRep = bookDao.getByIsbn(TEST_ISBN);
		assertThat(bookInRep.getIsbn().equals(TEST_ISBN)).isTrue();
	}

	private Book createBook(String isbn) {
		Book book = new Book();
		book.setIsbn(isbn);
		return book;
	}
}
