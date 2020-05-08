package by.runa.lib.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.runa.lib.api.dao.IDepartmentDao;
import by.runa.lib.api.dao.IUserDao;
import by.runa.lib.entities.User;
import by.runa.lib.entities.User_;

@Repository
public class UserDao extends AGenericDao<User> implements IUserDao {

	@Autowired
	private IDepartmentDao departmentDao;

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

	@Override
	public List<User> getByDepartment(String department) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(getGenericClass());
			Root<User> rootEntry = cq.from(User.class);
			CriteriaQuery<User> all = cq.select(rootEntry)
					.where(cb.equal(rootEntry.get(User_.department), departmentDao.getByName(department)));
			TypedQuery<User> result = entityManager.createQuery(all);
			return result.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User getByName(String name) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(getGenericClass());
			Root<User> rootEntry = cq.from(User.class);
			CriteriaQuery<User> all = cq.select(rootEntry).where(cb.equal(rootEntry.get(User_.username), name));
			TypedQuery<User> result = entityManager.createQuery(all);
			return result.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}