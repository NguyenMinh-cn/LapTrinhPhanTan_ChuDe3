package daos;

import entities.MonHoc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

import java.rmi.RemoteException;

public class MonHocDAO extends GenericDAO<MonHoc, Integer> {

    public MonHocDAO(Class<MonHoc> clazz) {
        super(clazz);
    }

    public MonHocDAO(EntityManager em, Class<MonHoc> clazz) {
        super(em, clazz);
    }


//    public boolean save(MonHoc monHoc) {
//        EntityTransaction transaction = em.getTransaction();
//        try {
//            transaction.begin();
//            em.persist(monHoc);
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            if (transaction.isActive()) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//            return false;
//        }
//    }


    public boolean update(MonHoc monHoc) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(monHoc);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int maMon) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            MonHoc monHoc = em.find(MonHoc.class, maMon);
            if (monHoc != null) {
                em.remove(monHoc);
                transaction.commit();
                return true;
            }
            transaction.rollback();
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    public List<MonHoc> getAllMonHoc() {
        try {
            return em.createQuery("SELECT m FROM MonHoc m", MonHoc.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MonHoc findByTenMon(String tenMon) throws RemoteException {
        String jpql = "SELECT mh FROM MonHoc mh WHERE mh.tenMon = :tenMon";
        return em.createQuery(jpql, MonHoc.class)
                .setParameter("tenMon", tenMon)
                .getSingleResult();
    }

}