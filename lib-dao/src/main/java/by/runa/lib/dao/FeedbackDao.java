package by.runa.lib.dao;

import org.springframework.stereotype.Repository;

import by.runa.lib.api.dao.IFeedbackDao;
import by.runa.lib.entities.Feedback;

@Repository
public class FeedbackDao extends AGenericDao<Feedback> implements IFeedbackDao {

	public FeedbackDao() {
		super(Feedback.class);
	}
}