package daos;

import entities.MonHoc;
import jakarta.persistence.EntityManager;

import java.rmi.RemoteException;

public class MonHocDAO extends GenericDAO<MonHoc, Integer> {
    public MonHocDAO(Class<MonHoc> clazz) {
        super(clazz);
    }

    public MonHocDAO(EntityManager em, Class<MonHoc> clazz) {
        super(em, clazz);
    }

    public MonHoc findByTenMon(String tenMon) throws RemoteException {
        String jpql = "SELECT mh FROM MonHoc mh WHERE mh.tenMon = :tenMon";
        return em.createQuery(jpql, MonHoc.class)
                .setParameter("tenMon", tenMon)
                .getSingleResult();
    }

}
