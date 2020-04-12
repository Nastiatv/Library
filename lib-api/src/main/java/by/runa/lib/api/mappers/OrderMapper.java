package by.runa.lib.api.mappers;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.runa.lib.api.dao.IBookDao;
import by.runa.lib.api.dao.IUserDao;
import by.runa.lib.api.dto.OrderDto;
import by.runa.lib.entities.Order;

@Component
public class OrderMapper extends AMapper<Order, OrderDto> {

	public OrderMapper(IUserDao userDao, ModelMapper mapper, IBookDao bookDao) {
		super(Order.class, OrderDto.class);
		this.userDao = userDao;
		this.bookDao = bookDao;
		this.mapper = mapper;
	}

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IBookDao bookDao;

	@PostConstruct
	public void setupMapper() {
		mapper.createTypeMap(Order.class, OrderDto.class).addMappings(m -> {
			m.skip(OrderDto::setUserId);
			m.skip(OrderDto::setBookId);
		}).setPostConverter(toDtoConverter());
		mapper.createTypeMap(OrderDto.class, Order.class).addMappings(m -> {
			m.skip(Order::setUser);
			m.skip(Order::setBook);
		}).setPostConverter(toEntityConverter());
	}

	@Override
	void mapSpecificFields(Order source, OrderDto destination) {
		destination.setUserId(getUserId(source));
		destination.setBookId(getBookId(source));
	}

	private Long getBookId(Order source) {
		return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getBook().getId();
	}

	private Long getUserId(Order source) {
		return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getUser().getId();
	}

	@Override
	void mapSpecificFields(OrderDto source, Order destination) {
		destination.setUser(userDao.get(source.getUserId()));
		destination.setBook(bookDao.get(source.getBookId()));
	}

}
