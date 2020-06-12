package by.runa.lib.api.dao;

import java.util.List;

import by.runa.lib.entities.AEntity;

public interface IAGenericDao<T extends AEntity> {

    Class<T> getGenericClass();

    T create(T entity);

    T get(Long id);

    void update(T entity);

    List<T> getAll();

    void delete(T entity);
}
