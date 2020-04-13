package by.runa.lib.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.runa.lib.api.dao.IDepartmentDao;
import by.runa.lib.entities.Department;
import by.runa.lib.entities.Department_;

@Repository
public class DepartmentDao extends AGenericDao<Department> implements IDepartmentDao {

	public DepartmentDao() {
		super(Department.class);
	}

	@Override
	public Department getByName(String name) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Department> cq = cb.createQuery(getGenericClass());
			Root<Department> rootEntry = cq.from(Department.class);
			CriteriaQuery<Department> all = cq.select(rootEntry).where(cb.equal(rootEntry.get(Department_.name), name));
			TypedQuery<Department> result = entityManager.createQuery(all);
			return result.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}