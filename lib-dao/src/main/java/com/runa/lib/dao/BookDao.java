package com.runa.lib.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
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

	@Override
	public List<Book> getAll() {
		try {
			CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Book> criteria = cBuilder.createQuery(getGenericClass());
			Root<Book> linkRoot = criteria.from(getGenericClass());
			criteria.select(linkRoot);
			TypedQuery<Book> query = entityManager.createQuery(criteria);
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	public List<Book> getBooksByDepartments(List<Department> departments) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> query = builder.createQuery(getGenericClass());
		Root<Book> rootBook = query.from(getGenericClass());
		ListJoin<Book, Department> joinBookDepartments = rootBook.join(Book_.departments);
		Expression<List<Department>> bookDepartments = rootBook.get(Book_.departments);
		Expression<Integer> countOfBookDepartments = builder.size(bookDepartments);
		Expression<Long> countOfBooksInGroup = builder.count(rootBook);
		Predicate predicateCountOfBookDepartmentsEqualsInputListSize = builder.equal(countOfBookDepartments,
				departments.size());
		Predicate predicateBookDepartmentsInInputList = joinBookDepartments.in(departments);

		query.where(
				builder.and(predicateCountOfBookDepartmentsEqualsInputListSize, predicateBookDepartmentsInInputList))
				.groupBy(rootBook).having(builder.equal(countOfBooksInGroup, departments.size()));

		return entityManager.createQuery(query).getResultList();
	}

	public List<Book> getBooksByDepartment(String department) {

		CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> criteria = cBuilder.createQuery(Book.class);
		Root<Book> linkRoot = criteria.from(Book.class);
		Join<Book, Department> departmentJoin = linkRoot.join(Book_.departments);
		criteria.select(linkRoot);
		criteria.where(cBuilder.equal(departmentJoin.get(Department_.name), department));
		TypedQuery<Book> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}
}
