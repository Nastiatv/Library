package by.runa.lib.api.mappers;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import by.runa.lib.api.dto.OrderDto;
import by.runa.lib.entities.Book;
import by.runa.lib.entities.Order;
import by.runa.lib.entities.User;

@Component
public class OrderMapper extends AMapper<Order, OrderDto> {

	public OrderMapper(ModelMapper mapper) {
		super(Order.class, OrderDto.class);
		this.mapper = mapper;
	}

	@PostConstruct
	public void setupMapper() {
		mapper.createTypeMap(Order.class, OrderDto.class).addMappings(m -> {
			m.skip(OrderDto::setUser);
			m.skip(OrderDto::setBook);
		}).setPostConverter(toDtoConverter());
		mapper.createTypeMap(OrderDto.class, Order.class).addMappings(m -> {
			m.skip(Order::setUser);
			m.skip(Order::setBook);
		}).setPostConverter(toEntityConverter());
	}

	@Override
	void mapSpecificFields(Order source, OrderDto destination) {
		destination.setUser(getUser(source));
		destination.setBook(getBook(source));
	}

	private Book getBook(Order source) {
		return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getBook();
	}

	private User getUser(Order source) {
		return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getUser();
	}

	@Override
	void mapSpecificFields(OrderDto source, Order destination) {
		destination.setUser(source.getUser());
		destination.setBook(source.getBook());
	}

}
