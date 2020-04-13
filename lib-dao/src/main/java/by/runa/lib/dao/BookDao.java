package by.runa.lib.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.runa.lib.entities.Book_;

import by.runa.lib.api.dao.IBookDao;
import by.runa.lib.entities.Book;

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

//	public List<Book> getBooksByDepartments(List<Department> departments) {
//		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//		CriteriaQuery<Book> query = builder.createQuery(getGenericClass());
//		Root<Book> rootBook = query.from(getGenericClass());
//		ListJoin<Book, String> joinBookDepartments = rootBook.join(Book_.departmentName);
//		Expression<List<String>> bookDepartments = rootBook.get(Book_.departmentName);
//		Expression<Integer> countOfBookDepartments = builder.size(bookDepartments);
//		Expression<Long> countOfBooksInGroup = builder.count(rootBook);
//		Predicate predicateCountOfBookDepartmentsEqualsInputListSize = builder.equal(countOfBookDepartments,
//				departments.size());
//		Predicate predicateBookDepartmentsInInputList = joinBookDepartments.in(departments);
//
//		query.where(
//				builder.and(predicateCountOfBookDepartmentsEqualsInputListSize, predicateBookDepartmentsInInputList))
//				.groupBy(rootBook).having(builder.equal(countOfBooksInGroup, departments.size()));
//
//		return entityManager.createQuery(query).getResultList();
//	}
//
//	public List<Book> getBooksByDepartment(String department) {
//
//		CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
//		CriteriaQuery<Book> criteria = cBuilder.createQuery(Book.class);
//		Root<Book> linkRoot = criteria.from(Book.class);
//		Join<Book, String> departmentJoin = linkRoot.join(Book_.departmentName);
//		criteria.select(linkRoot);
//		criteria.where(cBuilder.equal(departmentJoin.get(Department_.name), department));
//		TypedQuery<Book> query = entityManager.createQuery(criteria);
//		return query.getResultList();
//	}
}
