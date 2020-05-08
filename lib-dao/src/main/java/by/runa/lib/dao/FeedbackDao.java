package by.runa.lib.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.runa.lib.api.dao.IFeedbackDao;
import by.runa.lib.entities.Book;
import by.runa.lib.entities.Book_;
import by.runa.lib.entities.Feedback;
import by.runa.lib.entities.Feedback_;
import by.runa.lib.entities.User;
import by.runa.lib.entities.User_;

@Repository
public class FeedbackDao extends AGenericDao<Feedback> implements IFeedbackDao {

	public FeedbackDao() {
		super(Feedback.class);
	}

	public List<Feedback> getAllFeedbacksByBookId(Long id) {
		CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Feedback> criteria = cBuilder.createQuery(Feedback.class);
		Root<Feedback> linkRoot = criteria.from(Feedback.class);
		Join<Feedback, Book> bookJoin = linkRoot.join(Feedback_.book);
		criteria.select(linkRoot);
		criteria.where(cBuilder.equal(bookJoin.get(Book_.id), id));
		TypedQuery<Feedback> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}

	public List<Feedback> getAllFeedbacksByUserId(Long id) {
		CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Feedback> criteria = cBuilder.createQuery(Feedback.class);
		Root<Feedback> linkRoot = criteria.from(Feedback.class);
		Join<Feedback, User> userJoin = linkRoot.join(Feedback_.user);
		criteria.select(linkRoot);
		criteria.where(cBuilder.equal(userJoin.get(User_.id), id));
		TypedQuery<Feedback> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}
}
