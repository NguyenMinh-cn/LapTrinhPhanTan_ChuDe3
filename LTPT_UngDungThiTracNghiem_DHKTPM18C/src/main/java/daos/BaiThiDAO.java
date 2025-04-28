package daos;

import entities.BaiThi;
import entities.CauHoi;
import entities.GiaoVien;
import entities.Lop;
import jakarta.persistence.*;
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
    public BaiThi layThongTinBaiThiVaCauHoi(int maBaiThi) {
        try {
            String jpql = "SELECT DISTINCT bt FROM BaiThi bt " +
                    "LEFT JOIN FETCH bt.danhSachCauHoi " +
                    "WHERE bt.maBaiThi = :maBaiThi";
            TypedQuery<BaiThi> query = em.createQuery(jpql, BaiThi.class);
            query.setParameter("maBaiThi", maBaiThi);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Không tìm thấy bài thi với mã: " + maBaiThi);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<BaiThi> getAllBaiThiForHocSinh(Long maHocSinh) {
        try {
            String jpql = "SELECT DISTINCT b FROM BaiThi b " +
                    "JOIN FETCH b.danhSachCauHoi " +  // Thêm JOIN FETCH
                    "JOIN b.danhSachLop l " +
                    "JOIN HocSinh hs ON hs.lop.maLop = l.maLop " +
                    "WHERE hs.maHocSinh = :maHocSinh";
            
            List<BaiThi> dsBaiThi = em.createQuery(jpql, BaiThi.class)
                    .setParameter("maHocSinh", maHocSinh)
                    .getResultList();

            // Force initialize các collection cần thiết
            for (BaiThi baiThi : dsBaiThi) {
                baiThi.getDanhSachCauHoi().size();
                baiThi.getMonHoc().getTenMon();
                baiThi.getGiaoVien().getHoTen();
            }

            return dsBaiThi;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public static void main(String[] args) {
        // Khởi tạo EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb");
        EntityManager em = emf.createEntityManager();

        try {
            // Khởi tạo DAO
            BaiThiDAO baiThiDAO = new BaiThiDAO(em, BaiThi.class);

            // Gọi DAO lấy bài thi theo mã
            int maBaiThi = 9; // <== sửa mã bài thi tùy database bạn đang có
            BaiThi baiThi = baiThiDAO.layThongTinBaiThiVaCauHoi(maBaiThi);

            if (baiThi != null) {
                System.out.println("=== Thông tin bài thi ===");
                System.out.println("Tên bài thi: " + baiThi.getTenBaiThi());
                System.out.println("Môn học: " + (baiThi.getMonHoc() != null ? baiThi.getMonHoc().getTenMon() : "Không có"));
                System.out.println("Thời lượng: " + baiThi.getThoiLuong() + " phút");
                System.out.println("Số câu hỏi: " + baiThi.getDanhSachCauHoi().size());
                System.out.println("Số lần được phép làm: " + baiThi.getSoLanDuocPhepLamBai());
                System.out.println("\n=== Danh sách câu hỏi ===");
                List<CauHoi> dsCauHoi = baiThi.getDanhSachCauHoi();
                int stt = 1;
                for (CauHoi ch : dsCauHoi) {
                    System.out.println("Câu " + (stt++) + ": " + ch.getNoiDung());
                    System.out.println("Đáp án:");
                    List<String> dsDapAn = ch.getDanhSachDapAn();
                    for (int i = 0; i < dsDapAn.size(); i++) {
                        System.out.println((char)('A' + i) + ". " + dsDapAn.get(i));
                    }
                    System.out.println("Đáp án đúng: " + ch.getDapAnDung());
                    System.out.println("---");
                }
            } else {
                System.out.println("Không tìm thấy bài thi có mã " + maBaiThi);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}