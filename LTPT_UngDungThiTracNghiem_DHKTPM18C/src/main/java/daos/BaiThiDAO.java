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
                    "LEFT JOIN FETCH bt.danhSachCauHoi " +
                    "LEFT JOIN FETCH bt.danhSachLop " +
                    "WHERE bt.maBaiThi = :maBaiThi";

            BaiThi baiThi = em.createQuery(jpql, BaiThi.class)
                    .setParameter("maBaiThi", maBaiThi)
                    .getSingleResult();
            baiThi.getDanhSachPhienLamBaiCuaBaiThi().size();
            baiThi.getDanhSachLop().size();

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
                    "JOIN FETCH b.danhSachCauHoi " +
                    "JOIN b.danhSachLop l " +
                    "JOIN HocSinh hs ON hs.lop.maLop = l.maLop " +
                    "WHERE hs.maHocSinh = :maHocSinh";
            
            List<BaiThi> dsBaiThi = em.createQuery(jpql, BaiThi.class)
                    .setParameter("maHocSinh", maHocSinh)
                    .getResultList();

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
    public List<Lop> timLopTheoBaiThi(int maBaiThi) {
        try {
            String jpql = "SELECT l FROM BaiThi bt " +
                    "JOIN bt.danhSachLop l " +
                    "WHERE bt.maBaiThi = :maBaiThi";
            List<Lop> danhSachLop = em.createQuery(jpql, Lop.class)
                    .setParameter("maBaiThi", maBaiThi)
                    .getResultList();

            return danhSachLop;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public int demSoPhienLamBai(int maBaiThi) {
        try {
            String jpql = "SELECT COUNT(pl) FROM PhienLamBai pl " +
                    "WHERE pl.baiThi.maBaiThi = :maBaiThi";

            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("maBaiThi", maBaiThi)
                    .getSingleResult();

            return count.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu có lỗi
        }
    }
    // Phương thức mới: Xóa bài thi theo mã dùng JPQL
    public boolean xoaBaiThiKhongCoPhienLamBaiTheoMaJPQL(int maBaiThi) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            // JPQL để xóa bài thi nếu không có phiên làm bài
            String jpql = "DELETE FROM BaiThi bt " +
                    "WHERE bt.maBaiThi = :maBaiThi " +
                    "AND NOT EXISTS (SELECT pl FROM PhienLamBai pl WHERE pl.baiThi.maBaiThi = bt.maBaiThi)";

            int deletedCount = em.createQuery(jpql)
                    .setParameter("maBaiThi", maBaiThi)
                    .executeUpdate();

            transaction.commit();

            if (deletedCount > 0) {
                System.out.println("Đã xóa bài thi với mã: " + maBaiThi);
                return true; // Xóa thành công
            } else {
                System.out.println("Không thể xóa bài thi với mã " + maBaiThi + ": Bài thi không tồn tại hoặc đã có phiên làm bài.");
                return false; // Không xóa được
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false; // Có lỗi, không xóa được
        }

    }
    public static void main(String[] args) {
        // Khởi tạo EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb");
        EntityManager em = emf.createEntityManager();

        try {
            // Khởi tạo DAO
            BaiThiDAO baiThiDAO = new BaiThiDAO(em, BaiThi.class);
            boolean t = baiThiDAO.xoaBaiThiKhongCoPhienLamBaiTheoMaJPQL(3);
            System.out.println(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}