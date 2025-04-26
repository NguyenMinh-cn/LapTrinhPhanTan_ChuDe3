package daos;

import entities.BaiThi;
import entities.CauHoi;
import entities.GiaoVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.List;


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
            // Chỉ fetch 1 collection thôi để tránh lỗi
            String jpql = "SELECT DISTINCT bt FROM BaiThi bt " +
                    "LEFT JOIN FETCH bt.monHoc " +
                    "LEFT JOIN FETCH bt.giaoVien " +
                    "LEFT JOIN FETCH bt.danhSachCauHoi " +   // chỉ fetch danhSachCauHoi
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


    //    public static void main(String[] args) {
//        BaiThiDAO baiThiDAO = new BaiThiDAO(BaiThi.class);
//        List<BaiThi> list = baiThiDAO.timDSBaiTHiTheoMaGiaoVien(1);
//        for (BaiThi baiThi : list) {
//            System.out.println("Tên bài thi: " + baiThi.getTenBaiThi());
//            System.out.println("Môn học: " + baiThi.getMonHoc().getTenMon());
//            System.out.println("Thời gian bắt đầu: " + baiThi.getThoiGianBatDau());
//            System.out.println("Thời gian kết thúc: " + baiThi.getThoiGianKetThuc());
//            System.out.println("Thời lượng làm bài: " + baiThi.getThoiLuong() + " phút");
//            System.out.println("Số câu hỏi: " + baiThi.getDanhSachCauHoi().size());
//            System.out.println("Giáo viên tạo bài thi: " + baiThi.getGiaoVien().getHoTen());
//            System.out.println("-----");
//        }
//    }
public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb");
    EntityManager em = emf.createEntityManager();
    BaiThiDAO baiThiDAO = new BaiThiDAO(em, BaiThi.class);

    try {
    BaiThi baiThi = baiThiDAO.layThongTinChiTietBaiThi(1);
    if (baiThi != null) {
        System.out.println("=== THÔNG TIN CHI TIẾT BÀI THI ===");
        System.out.println("Mã bài thi: " + baiThi.getMaBaiThi());
        System.out.println("Tên bài thi: " + baiThi.getTenBaiThi());
        System.out.println("Môn học: " + (baiThi.getMonHoc() != null ? baiThi.getMonHoc().getTenMon() : "Không có"));
        System.out.println("Thời gian bắt đầu: " + baiThi.getThoiGianBatDau());
        System.out.println("Thời gian kết thúc: " + baiThi.getThoiGianKetThuc());
        System.out.println("Thời lượng: " + baiThi.getThoiLuong() + " phút");
        System.out.println("Mật khẩu: " + (baiThi.getMatKhau() != null ? baiThi.getMatKhau() : "Không có"));
        System.out.println("Giáo viên: " + (baiThi.getGiaoVien() != null ? baiThi.getGiaoVien().getHoTen() : "Không có"));
        System.out.println("Số câu hỏi: " + (baiThi.getDanhSachCauHoi() != null ? baiThi.getDanhSachCauHoi().size() : 0));
        System.out.println("Số lớp tham gia: " + (baiThi.getDanhSachLop() != null ? baiThi.getDanhSachLop().size() : 0));
        System.out.println("Số phiên làm bài: " + (baiThi.getDanhSachPhienLamBaiCuaBaiThi() != null ? baiThi.getDanhSachPhienLamBaiCuaBaiThi().size() : 0));

        // In danh sách lớp
        if (baiThi.getDanhSachLop() != null && !baiThi.getDanhSachLop().isEmpty()) {
            System.out.println("\nDanh sách lớp tham gia:");
            baiThi.getDanhSachLop().forEach(lop ->
                    System.out.println("- " + lop.getTenLop())
            );
        }

        // In danh sách câu hỏi
        if (baiThi.getDanhSachCauHoi() != null && !baiThi.getDanhSachCauHoi().isEmpty()) {
            System.out.println("\nDanh sách câu hỏi:");
            int i = 1;
            for (CauHoi cauHoi : baiThi.getDanhSachCauHoi()) {
                System.out.println("Câu " + i + ": " + cauHoi.getNoiDung());
                i++;
            }
        }
    } else {
        System.out.println("Không tìm thấy bài thi với mã 1");
    }
    } finally {
        em.close();
        emf.close();
    }
}
}
