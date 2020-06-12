package by.runa.lib.api.dao;

import java.util.List;

import by.runa.lib.entities.Feedback;

public interface IFeedbackDao extends IAGenericDao<Feedback> {

    List<Feedback> getAllFeedbacksByBookId(Long id);

    List<Feedback> getAllFeedbacksByUserId(Long id);
}
