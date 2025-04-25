package daos;

import entities.GiaoVien;
import entities.MonHoc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class GiaoVienDAO extends GenericDAO<GiaoVien, Integer> {
    public GiaoVienDAO(Class<GiaoVien> clazz) {
        super(clazz);
    }

    public GiaoVienDAO(EntityManager em, Class<GiaoVien> clazz) {
        super(em, clazz);
    }
    public GiaoVien timGiaoVienTheoEmail(String email) {
        String jpql = "SELECT DISTINCT gv FROM GiaoVien gv " +
                "LEFT JOIN FETCH gv.danhSachBaiThi " +
                "WHERE gv.email = :email";
        try {
            return em.createQuery(jpql, GiaoVien.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
