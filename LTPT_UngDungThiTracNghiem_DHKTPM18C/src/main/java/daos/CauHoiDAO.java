package daos;

import entities.CauHoi;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class CauHoiDAO extends GenericDAO<CauHoi, Integer>{
    public CauHoiDAO(Class<CauHoi> clazz) {
        super(clazz);
    }
    public CauHoiDAO(EntityManager em, Class<CauHoi> clazz) {
        super(em, clazz);
    }

//    public List<CauHoi> timCauHoiTheoMaBaiThi(int maBaiThi) {
//        try {
//            String jpql = """
//        SELECT DISTINCT ch FROM BaiThi bt
//        JOIN bt.danhSachCauHoi ch
//        LEFT JOIN FETCH ch.chuDe
//        LEFT JOIN FETCH ch.danhSachDapAn
//        WHERE bt.maBaiThi = :maBaiThi
//        """;
//
//            return em.createQuery(jpql, CauHoi.class)
//                    .setParameter("maBaiThi", maBaiThi)
//                    .getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>(); // Trả về danh sách rỗng thay vì null khi có lỗi
//        }
//    }
public List<CauHoi> timCauHoiTheoMaBaiThi(int maBaiThi) {
    try {
        List<CauHoi> list = em.createQuery("""
            SELECT DISTINCT ch
            FROM CauHoi ch
            JOIN ch.danhSachBaiThi bt
            WHERE bt.maBaiThi = :maBaiThi
            """, CauHoi.class)
                .setParameter("maBaiThi", maBaiThi)
                .getResultList();

        // Buộc Hibernate khởi tạo danhSachDapAn
        for (CauHoi ch : list) {
            ch.getDanhSachDapAn().size(); // gọi size() để Hibernate load danh sách
        }

        return list;
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>();
    }
}
    public List<String> timDSDapAnTheoCauHoi(int maCH) {
        try {
            String jpql = "SELECT da.danhSachDapAn FROM CauHoiDanSachDapAn da WHERE da.cauHoi.maCauHoi = :maCH";

            return em.createQuery("SELECT ch.danhSachDapAn FROM CauHoi ch WHERE ch.maCauHoi = :maCH", String.class)
                    .setParameter("maCH", maCH)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    // Trả về đối tượng đã lưu (có id tự sinh)
    public CauHoi luuVaTraVeMa(CauHoi cauHoi) {
        try {
            em.getTransaction().begin();
            em.persist(cauHoi);
            em.getTransaction().commit();
            return cauHoi; // Trả về đối tượng đã lưu (có id tự sinh)
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
    public List<CauHoi> luuNhieuVaTraVeMa(List<CauHoi> danhSachCauHoi) {
        List<CauHoi> danhSachCauHoiDaLuuThanhCong = new ArrayList<>();
        try {
            em.getTransaction().begin();
            for (CauHoi ch : danhSachCauHoi) {
                em.persist(ch);
                danhSachCauHoiDaLuuThanhCong.add(ch);
            }
            em.getTransaction().commit();
            return danhSachCauHoiDaLuuThanhCong;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
    //Kiểm tra câu hỏi có trong bài thi (BaiThi) hay không
    public boolean inBaiThi(int maCauHoi) {
        String jpql = "SELECT COUNT(bt) FROM BaiThi bt JOIN bt.danhSachCauHoi ch WHERE ch.maCauHoi = :maCauHoi";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("maCauHoi", maCauHoi)
                .getSingleResult();
        return count > 0;
    }
//    public static void main(String[] args) {
//        CauHoiDAO chDAO = new CauHoiDAO(CauHoi.class);
//        List<String> list = chDAO.timDSDapAnTheoCauHoi(1);
//        for (String ch : list) {
//            System.out.println(ch + "/n");
//
//        }


    //Câu hỏi trong bài thi đã diễn ra (đã qua thoiGianBatDau)
    public boolean inBaiThiDaDienRa(int maCauHoi) {
        String jpql = "SELECT COUNT(bt) FROM BaiThi bt JOIN bt.danhSachCauHoi ch WHERE ch.id = :maCauHoi AND bt.thoiGianBatDau < CURRENT_TIMESTAMP";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("maCauHoi", maCauHoi)
                .getSingleResult();
        return count > 0;
    }

    //Lấy danh sách câu hỏi có chủ đề chủ đề (chủ đề không null)
    public List<CauHoi> getCauHoiCoChuDe() {
        String jpql = "SELECT ch FROM CauHoi ch WHERE ch.chuDe IS NOT NULL";
        return em.createQuery(jpql, CauHoi.class).getResultList();
    }

    public List<CauHoi> findByMon(String tenMon) {
        String jpql = "SELECT ch FROM CauHoi ch WHERE ch.chuDe.monHoc.tenMon = :tenMon";
        return em.createQuery(jpql, CauHoi.class)
                .setParameter("tenMon", tenMon)
                .getResultList();
    }
}
