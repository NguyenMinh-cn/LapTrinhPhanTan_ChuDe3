package daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JPAUtil;

import java.util.List;

public abstract class GenericDAO<T, ID> {

    protected EntityManager em;
    protected Class<T> clazz;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
        this.em = JPAUtil.getEntityManager();
    }

    public GenericDAO(EntityManager em, Class<T> clazz) {
        this.em = em;
        this.clazz = clazz;
    }

    public T findByID(ID id) {
        return em.find(clazz, id);
    }

    public List<T> getAll() {
        return em.createQuery("from " + clazz.getSimpleName(), clazz)
                .getResultList();
    }

    public boolean save(T t) {
        EntityTransaction tr = em.getTransaction();
        boolean beganTransaction = false;
        try {
            
            if (!tr.isActive()) {
                tr.begin();
                beganTransaction = true;
            }
            em.persist(t);
          
            if (beganTransaction && tr.isActive() && !tr.getRollbackOnly()) {
                tr.commit();
            }
            return true;
        } catch (Exception ex) {
            if (beganTransaction && tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public boolean update(T t) {
        EntityTransaction tr = em.getTransaction();
        boolean beganTransaction = false;
        try {
            if (!tr.isActive()) {
                tr.begin();
                beganTransaction = true;
            }
            em.merge(t);
            if (beganTransaction && tr.isActive() && !tr.getRollbackOnly()) {
                tr.commit();
            }
            return true;
        } catch (Exception ex) {
            if (beganTransaction && tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public boolean delete(ID id) {
        EntityTransaction tr = em.getTransaction();
        boolean beganTransaction = false;
        try {
            if (!tr.isActive()) {
                tr.begin();
                beganTransaction = true;
            }
            T t = em.find(clazz, id);
            if (t != null) {
                em.remove(t);
                if (beganTransaction && tr.isActive() && !tr.getRollbackOnly()) {
                    tr.commit();
                }
                return true;
            }
            // Nếu không tìm thấy entity, không commit mà vẫn trả về false
            return false;
        } catch (Exception ex) {
            if (beganTransaction && tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
