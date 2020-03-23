package com.runa.lib.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.runa.lib.api.dao.IBookDetailsDao;
import com.runa.lib.entities.BookDetails;

@Repository
public class BookDetailsDao implements IBookDetailsDao {

	public BookDetailsDao() {
		super();
	}

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public BookDetails create(BookDetails entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public BookDetails get(Long id) {
		return entityManager.find(getGenericClass(), id);
	}

	@Override
	public void update(BookDetails entity) {
		entityManager.merge(entity);
		entityManager.flush();
	}

	@Override
	public void delete(BookDetails entity) {
		entityManager.remove(entity);
	}

	@Override
	public List<BookDetails> getAll() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<BookDetails> cq = cb.createQuery(getGenericClass());
			Root<BookDetails> rootEntry = cq.from(getGenericClass());
			cq.select(rootEntry);
			TypedQuery<BookDetails> result = entityManager.createQuery(cq);
			return result.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Class<BookDetails> getGenericClass() {
		// TODO Auto-generated method stub
		return null;
	}
}
