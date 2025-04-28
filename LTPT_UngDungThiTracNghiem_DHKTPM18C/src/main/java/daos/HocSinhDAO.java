package daos;

import entities.HocSinh;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.query.Query;


public class HocSinhDAO extends GenericDAO<HocSinh, Integer> {

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
    public boolean disableAccount(String email) {
        // Tạo câu truy vấn JPQL để tìm học sinh theo email
        String jpql = "UPDATE HocSinh hs SET hs.taiKhoan.active = false WHERE hs.email = :email";

        try {
            // Thực hiện câu truy vấn cập nhật, thay đổi trạng thái tài khoản
            int updatedCount = em.createQuery(jpql)
                    .setParameter("email", email)
                    .executeUpdate();

            // Kiểm tra số dòng bị ảnh hưởng (nếu có học sinh được cập nhật)
            return updatedCount > 0; // Trả về true nếu có dòng được cập nhật
        } catch (Exception e) {
            // In ra lỗi nếu có ngoại lệ
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }



}