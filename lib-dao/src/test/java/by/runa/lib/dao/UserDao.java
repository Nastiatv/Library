package by.runa.lib.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.runa.lib.api.dao.IUserDao;
import by.runa.lib.entities.User;
import by.runa.lib.entities.User_;

@Repository
public class UserDao extends AGenericDao<User> implements IUserDao {

	public UserDao() {
		super(User.class);
	}

	@Override
	public User getByEmail(String email) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(getGenericClass());
			Root<User> rootEntry = cq.from(User.class);
			CriteriaQuery<User> all = cq.select(rootEntry).where(cb.equal(rootEntry.get(User_.email), email));
			TypedQuery<User> result = entityManager.createQuery(all);
			return result.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}