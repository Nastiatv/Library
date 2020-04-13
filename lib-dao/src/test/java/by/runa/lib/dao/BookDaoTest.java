package by.runa.lib.dao;

import org.springframework.beans.factory.annotation.Autowired;

import by.runa.lib.api.dao.IBookDao;

@DataJpaTest
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
	public void whenSave_thenGetById{
		long testBookId
		
	}
	
}
