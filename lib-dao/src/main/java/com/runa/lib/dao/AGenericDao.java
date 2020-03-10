package com.runa.lib.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.runa.lib.api.dao.IAGenericDao;
import com.runa.lib.entities.AEntity;

public abstract class AGenericDao<T extends AEntity> implements IAGenericDao<T> {

	private Class<T> clazz;

	public AGenericDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Class<T> getGenericClass() {
		return this.clazz;
	}

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public T create(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public Optional<T> get(Long id) {
		return Optional.ofNullable(entityManager.find(getGenericClass(), id));
	}

	@Override
	public void update(T entity) {
		entityManager.merge(entity);
		entityManager.flush();
	}

	@Override
	public void delete(T entity) {
		entityManager.remove(entity);
	}

	@Override
	public List<T> getAll() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(getGenericClass());
			Root<T> rootEntry = cq.from(getGenericClass());
			cq.select(rootEntry);
			TypedQuery<T> result = entityManager.createQuery(cq);
			return result.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
}