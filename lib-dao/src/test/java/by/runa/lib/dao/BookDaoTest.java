package by.runa.lib.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import by.runa.lib.api.dao.IBookDao;

@DataJpaTest
@RunWith(SpringRunner.class)
public class BookDaoTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private IBookDao bookDao;

	@Test
	public void injectedBooksAreNotNull() {
		assertThat(bookDao).isNotNull();
		assertThat(entityManager).isNotNull();
	}

	@Test
	public void whenSave_thenGetById() {

	}
}
