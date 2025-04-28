package daos;

import entities.Lop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

import static util.JPAUtil.getEntityManager;

public class LopDAO extends GenericDAO<Lop, Integer> {

    public LopDAO(Class<Lop> clazz) {
        super(clazz);
    }

    public LopDAO(EntityManager em, Class<Lop> clazz) {
        super(em, clazz);
    }

    public List<Lop> getAllLop() {
        List<Lop> lopList = null;
        try {
            // Truy vấn bằng JPQL (Java Persistence Query Language)
            String jpql = "SELECT l FROM Lop l";
            TypedQuery<Lop> query = em.createQuery(jpql, Lop.class);
            lopList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return lopList;
    }
}