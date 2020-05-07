package by.runa.lib.api.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import by.runa.lib.api.dto.ADto;
import by.runa.lib.api.utils.IMapper;
import by.runa.lib.entities.AEntity;

public abstract class AMapper<E extends AEntity, D extends ADto> implements IMapper<E, D> {

	@Autowired
	ModelMapper mapper;

	private Class<E> entityClass;
	private Class<D> dtoClass;

	public AMapper(Class<E> entityClass, Class<D> dtoClass) {
		this.entityClass = entityClass;
		this.dtoClass = dtoClass;
	}

	@Override
	public E toEntity(D dto) {
		return Objects.isNull(dto) ? null : mapper.map(dto, entityClass);
	}

	@Override
	public E toLiteEntity(D dto) {
		return Objects.isNull(dto) ? null : mapper.map(dto, entityClass);
	}

	@Override
	public D toDto(E entity) {
		return Objects.isNull(entity) ? null : mapper.map(entity, dtoClass);
	}

	@Override
	public D toLiteDto(E entity) {
		return Objects.isNull(entity) ? null : mapper.map(entity, dtoClass);
	}

	@Override
	public List<D> toListDto(List<E> entities) {
		List<D> listDto = new ArrayList<>();
		for (E entity : entities) {
			listDto.add(toDto(entity));
		}
		return listDto;
	}
	
	public Converter<D, E> toEntityConverter() {
		return context -> {
			D source = context.getSource();
			E destination = context.getDestination();
			mapSpecificFields(source, destination);
			return context.getDestination();
		};
	}

	public Converter<E, D> toDtoConverter() {
		return context -> {
			E source = context.getSource();
			D destination = context.getDestination();
			mapSpecificFields(source, destination);
			return context.getDestination();
		};
	}

	void mapSpecificFields(E source, D destination) {
	}

	void mapSpecificFields(D source, E destination) {
	}
}