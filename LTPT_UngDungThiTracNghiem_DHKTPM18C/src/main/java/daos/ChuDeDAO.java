package daos;

import entities.ChuDe;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ChuDeDAO extends GenericDAO<ChuDe, Integer> {
    public ChuDeDAO(Class<ChuDe> clazz) {
        super(clazz);
    }

    public ChuDeDAO(EntityManager em, Class<ChuDe> clazz) {
        super(em, clazz);
    }

    public List<ChuDe> findByMaMonHoc(int maMon) {
        String jpql = "SELECT cd FROM ChuDe cd WHERE cd.monHoc.maMon = :maMon";
        return em.createQuery(jpql, ChuDe.class)
                .setParameter("maMon", maMon)
                .getResultList();
    }
}
