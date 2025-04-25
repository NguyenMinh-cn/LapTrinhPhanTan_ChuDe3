
import daos.GiaoVienDAO;
import daos.HocSinhDAO;
import daos.MonHocDAO;
import entities.GiaoVien;
import entities.HocSinh;
import entities.MonHoc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb"); // đổi nếu bạn dùng tên khác
        EntityManager em = emf.createEntityManager();
        try {
            HocSinhDAO hocSinhDAO = new HocSinhDAO(HocSinh.class);
            GiaoVienDAO giaoVienDAO = new GiaoVienDAO(GiaoVien.class);

            HocSinh hocSinh = hocSinhDAO.timHocSinhTheoEmail("anglythi@example.com");
            GiaoVien gv = giaoVienDAO.timGiaoVienTheoEmail("trantoocuong@gmail.com");
            System.out.println(hocSinh);
            System.out.println(gv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
