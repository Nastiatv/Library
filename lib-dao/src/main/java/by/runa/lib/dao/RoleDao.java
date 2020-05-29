package by.runa.lib.dao;

import by.runa.lib.api.dao.IRoleDao;
import by.runa.lib.entities.Role;
import by.runa.lib.entities.Role_;

import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class RoleDao extends AGenericDao<Role> implements IRoleDao {

    public RoleDao() {
        super(Role.class);
    }

    @Override
    public Role getByName(String name) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Role> cq = cb.createQuery(getGenericClass());
            Root<Role> rootEntry = cq.from(Role.class);
            CriteriaQuery<Role> all = cq.select(rootEntry).where(cb.equal(rootEntry.get(Role_.name), name));
            TypedQuery<Role> result = entityManager.createQuery(all);
            return result.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}