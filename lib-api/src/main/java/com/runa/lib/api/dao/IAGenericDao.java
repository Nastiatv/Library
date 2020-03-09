package com.runa.lib.api.dao;

import java.util.List;
import java.util.Optional;

import com.runa.lib.entities.AEntity;

public interface IAGenericDao<T extends AEntity> {

	Class<T> getGenericClass();

	T create(T entity);

	Optional<T> get(Long id);

	void update(T entity);

	void delete(T entity);

	List<T> getAll();

}
