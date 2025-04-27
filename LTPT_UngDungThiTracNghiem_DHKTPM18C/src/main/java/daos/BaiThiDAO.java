package daos;

import entities.BaiThi;
import entities.CauHoi;
import entities.GiaoVien;
import entities.Lop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import service.BaiThiService;
import service.LopService;

import java.rmi.Naming;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

public class BaiThiDAO extends GenericDAO<BaiThi, Integer>{
    public BaiThiDAO(Class<BaiThi> clazz) {
        super(clazz);
    }
    public BaiThiDAO(EntityManager em, Class<BaiThi> clazz) {
        super(em, clazz);
    }

    public List<BaiThi> timDSBaiTHiTheoMaGiaoVien(int maGiaoVien) {
        String jpql = "SELECT DISTINCT bt FROM BaiThi bt " +
                "JOIN FETCH bt.monHoc mh " +
                "JOIN FETCH bt.danhSachCauHoi ch " +
                "WHERE bt.giaoVien.maGiaoVien = :maGiaoVien";

        return em.createQuery(jpql, BaiThi.class)
                .setParameter("maGiaoVien", maGiaoVien)
                .getResultList();
    }
    public BaiThi layThongTinChiTietBaiThi(int maBaiThi) {
        try {
            String jpql = "SELECT DISTINCT bt FROM BaiThi bt " +
                    "LEFT JOIN FETCH bt.monHoc " +
                    "LEFT JOIN FETCH bt.giaoVien " +
                    "LEFT JOIN FETCH bt.danhSachCauHoi " +   // chỉ fetch danhSachCauHoi
                    "LEFT JOIN FETCH bt.danhSachLop " +
                    "WHERE bt.maBaiThi = :maBaiThi";

            BaiThi baiThi = em.createQuery(jpql, BaiThi.class)
                    .setParameter("maBaiThi", maBaiThi)
                    .getSingleResult();

            // Sau đó ép Hibernate tự động load các collection còn lại
            baiThi.getDanhSachPhienLamBaiCuaBaiThi().size(); // ép load
            baiThi.getDanhSachLop().size();                  // ép load nốt nếu cần

            return baiThi;
        } catch (NoResultException e) {
            System.out.println("Không tìm thấy bài thi với mã " + maBaiThi);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<BaiThi> getAllBaiThiForHocSinh(Long maHocSinh) {
        try {
            String jpql = "SELECT DISTINCT b FROM BaiThi b JOIN b.danhSachLop l JOIN HocSinh hs ON hs.lop.maLop = l.maLop " +
                    "WHERE hs.maHocSinh = :maHocSinh";
            return em.createQuery(jpql, BaiThi.class)
                    .setParameter("maHocSinh", maHocSinh)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public static void main(String[] args) {
        // Khởi tạo EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb");
        EntityManager em = emf.createEntityManager();

        try {
            // Tạo instance của BaiThiDAO (giả sử BaiThiDAO nhận EntityManager)
            BaiThiDAO baiThiDAO = new BaiThiDAO(BaiThi.class);

            // Gọi phương thức getAllBaiThiForHocSinh với maHocSinh (ví dụ: 1L)
            Long maHocSinh = 1L; // Thay đổi maHocSinh tùy theo dữ liệu của bạn
            List<BaiThi> list = baiThiDAO.getAllBaiThiForHocSinh(83L);

            // In thông tin các bài thi
            if (list.isEmpty()) {
                System.out.println("Không tìm thấy bài thi nào cho học sinh có mã: " + maHocSinh);
            } else {
                for (BaiThi baiThi : list) {
                    System.out.println("Tên bài thi: " + baiThi.getTenBaiThi());
                    System.out.println("Môn học: " + (baiThi.getMonHoc() != null ? baiThi.getMonHoc().getTenMon() : "Không có"));
                    System.out.println("Thời gian bắt đầu: " + baiThi.getThoiGianBatDau());
                    System.out.println("Thời gian kết thúc: " + baiThi.getThoiGianKetThuc());
                    System.out.println("Thời lượng làm bài: " + baiThi.getThoiLuong() + " phút");
                    System.out.println("Số câu hỏi: " + (baiThi.getDanhSachCauHoi() != null ? baiThi.getDanhSachCauHoi().size() : 0));
                    System.out.println("Giáo viên tạo bài thi: " + (baiThi.getGiaoVien() != null ? baiThi.getGiaoVien().getHoTen() : "Không có"));
                    System.out.println("-----");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng EntityManager và EntityManagerFactory
            em.close();
            emf.close();
        }
    }
}
