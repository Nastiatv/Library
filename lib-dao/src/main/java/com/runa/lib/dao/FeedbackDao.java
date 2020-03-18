package com.runa.lib.dao;

import org.springframework.stereotype.Repository;

import com.runa.lib.api.dao.IFeedbackDao;
import com.runa.lib.entities.Feedback;

@Repository
public class FeedbackDao extends AGenericDao<Feedback> implements IFeedbackDao {

	public FeedbackDao() {
		super(Feedback.class);
	}
}