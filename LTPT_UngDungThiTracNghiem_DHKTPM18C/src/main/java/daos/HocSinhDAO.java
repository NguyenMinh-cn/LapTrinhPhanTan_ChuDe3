package daos;

import entities.HocSinh;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.query.Query;

import java.util.List;


public class HocSinhDAO extends GenericDAO<HocSinh, Long> {

    public HocSinhDAO(Class<HocSinh> clazz) {
        super(clazz);
    }

    public HocSinhDAO(EntityManager em, Class<HocSinh> clazz) {
        super(em, clazz);
    }

    public HocSinh timHocSinhTheoEmail(String email) {
        String jpql = "SELECT hs FROM HocSinh hs " +
                "LEFT JOIN FETCH hs.lop " +
                "LEFT JOIN FETCH hs.danhSachPhienLamBai " +
                "LEFT JOIN FETCH hs.taiKhoan " +
                "WHERE hs.email = :email";

        try {
            return em.createQuery(jpql, HocSinh.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<HocSinh> findHocSinhChuaCoLop() {
        String jpql = "SELECT hs FROM HocSinh hs " +
                "LEFT JOIN FETCH hs.taiKhoan " +
                "WHERE hs.lop IS NULL";

        try {
            TypedQuery<HocSinh> query = em.createQuery(jpql, HocSinh.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}