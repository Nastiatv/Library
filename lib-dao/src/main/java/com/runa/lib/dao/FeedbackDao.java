package com.runa.lib.dao;

import com.runa.lib.entities.Feedback;

public class FeedbackDao extends AGenericDao<Feedback> {

	public FeedbackDao(Class<Feedback> clazz) {
		super(Feedback.class);
	}
}