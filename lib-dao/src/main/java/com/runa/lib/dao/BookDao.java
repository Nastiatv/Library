package com.runa.lib.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.runa.lib.api.dao.IBookDao;
import com.runa.lib.entities.Book;
import com.runa.lib.entities.Book_;

@Repository
public class BookDao extends AGenericDao<Book> implements IBookDao {

	public BookDao() {
		super(Book.class);
	}

	@Override
	public Book getById(Long id) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Book> cq = cb.createQuery(getGenericClass());
			Root<Book> rootEntry = cq.from(Book.class);
			CriteriaQuery<Book> all = cq.select(rootEntry).where(cb.equal(rootEntry.get(Book_.id), id));
			TypedQuery<Book> result = entityManager.createQuery(all);
			return result.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}