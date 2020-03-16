package com.runa.lib.dao;

import org.springframework.stereotype.Repository;

import com.runa.lib.entities.Feedback;

@Repository
public class FeedbackDao extends AGenericDao<Feedback> {

	public FeedbackDao(Class<Feedback> clazz) {
		super(Feedback.class);
	}
}