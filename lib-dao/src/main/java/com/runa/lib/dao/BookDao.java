package com.runa.lib.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.runa.lib.api.dao.IBookDao;
import com.runa.lib.entities.Book;
import com.runa.lib.entities.Book_;
import com.runa.lib.entities.Department;
import com.runa.lib.entities.Department_;

@Repository
public class BookDao extends AGenericDao<Book> implements IBookDao {

	public BookDao() {
		super(Book.class);
	}

	@Override
	public Book getByIsbn(String isbn) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Book> cq = cb.createQuery(getGenericClass());
			Root<Book> rootEntry = cq.from(Book.class);
			CriteriaQuery<Book> all = cq.select(rootEntry).where(cb.equal(rootEntry.get(Book_.isbn), isbn));
			TypedQuery<Book> result = entityManager.createQuery(all);
			return result.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Book> getAll(Book book) {
		try {
			CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Book> query = cBuilder.createQuery(getGenericClass());
			Root<Book> root = query.from(getGenericClass());
			Join<Book, Department> depJoin = root.join(Book_.DEPARTMENTS);
			query.select(root);
			query.where(cBuilder.equal(depJoin.get(Department_.BOOKS), book));
			TypedQuery<Book> result = entityManager.createQuery(query);
			return result.getResultList();
			} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
}
