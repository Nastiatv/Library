package by.runa.lib.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import by.runa.lib.api.dao.IOrderDao;
import by.runa.lib.entities.Order;

@DataJpaTest
@RunWith(SpringRunner.class)
public class OrderDaoTest {

	private static final LocalDateTime TEST_DATE =LocalDateTime.now();

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private IOrderDao orderDao;

	@Test
	public void injectedComponentsAreNotNull() {
		assertThat(orderDao).isNotNull();
		assertThat(entityManager).isNotNull();
	}

	@Test
	public void getById() {
		Long id = entityManager.persistAndGetId(createOrder(TEST_DATE), Long.class);
		Order orderInRep = orderDao.get(id);
		assertThat(orderInRep.getId().equals(id)).isTrue();
	}

	@Test
	public void getAll() {
		entityManager.persist(createOrder(TEST_DATE));
		entityManager.persist(createOrder(TEST_DATE));
		entityManager.persist(createOrder(TEST_DATE));
		List<Order> allOrdersInRep = orderDao.getAll();
		assertThat(allOrdersInRep.size() == 3).isTrue();
	}

	@Test
	public void update() {
		Long id = entityManager.persistAndGetId(createOrder(TEST_DATE), Long.class);
		Order orderInRep = orderDao.get(id);
		orderInRep.setProlonged(true);
		entityManager.merge(orderInRep);
		assertThat(orderInRep.isProlonged() == true).isTrue();
	}

	@Test
	public void delete() {
		Order order = entityManager.persist(createOrder(TEST_DATE));
		entityManager.remove(order);
		assertThat(orderDao.getAll().isEmpty()).isTrue();
	}

	private Order createOrder(LocalDateTime order_date) {
		Order order = new Order();
		order.setOrderDate(order_date);
		return order;
	}
}
