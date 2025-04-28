package daos;

import entities.Lop;
import entities.MonHoc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class LopDAO extends GenericDAO<Lop, Integer> {
    public LopDAO(Class<Lop> clazz) {
        super(clazz);
    }

    public LopDAO(EntityManager em, Class<Lop> clazz) {
        super(em, clazz);
    }
    public Lop findByTenLop(String tenLop) {
        String jpql = "SELECT l FROM Lop l WHERE l.tenLop = :tenLop";
        try {
            TypedQuery<Lop> query = em.createQuery(jpql, Lop.class);
            query.setParameter("tenLop", tenLop);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//    public static void main(String[] args) {
//        LopDAO lopDAO = new LopDAO(Lop.class);
//        System.out.println(lopDAO.findByTenLop("10A"));
//    }
}
