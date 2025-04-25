package daos;

import entities.CauTraLoi;
import entities.PhienLamBai;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PhienLamBaiDAO extends GenericDAO<PhienLamBai, String> {
    private CauTraLoiDAO cauTraLoiDAO;

    public PhienLamBaiDAO(Class<PhienLamBai> clazz) {
        super(clazz);
        this.cauTraLoiDAO = new CauTraLoiDAO(CauTraLoi.class);
    }

    public PhienLamBaiDAO(EntityManager em, Class<PhienLamBai> clazz) {
        super(em, clazz);
        this.cauTraLoiDAO = new CauTraLoiDAO(em, CauTraLoi.class);
    }

    // Tính điểm số dựa trên số câu đúng
    public double calculateScore(String maPhienLamBai) {
        try {
            long soCauDung = em.createQuery("SELECT COUNT(ct) FROM CauTraLoi ct " +
                            "WHERE ct.phienLamBai.maPhienLamBai = :maPhienLamBai AND ct.ketQua = true", Long.class)
                    .setParameter("maPhienLamBai", maPhienLamBai)
                    .getSingleResult();

            long tongSoCau = em.createQuery("SELECT COUNT(ct) FROM CauTraLoi ct " +
                            "WHERE ct.phienLamBai.maPhienLamBai = :maPhienLamBai", Long.class)
                    .setParameter("maPhienLamBai", maPhienLamBai)
                    .getSingleResult();

            if (tongSoCau == 0) return 0.0;
            return (soCauDung * 100.0) / tongSoCau; // Tính điểm trên thang 100
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    // Đếm số câu đúng
    public int countCorrectAnswers(String maPhienLamBai) {
        try {
            return em.createQuery("SELECT COUNT(ct) FROM CauTraLoi ct " +
                            "WHERE ct.phienLamBai.maPhienLamBai = :maPhienLamBai AND ct.ketQua = true", Long.class)
                    .setParameter("maPhienLamBai", maPhienLamBai)
                    .getSingleResult()
                    .intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Đếm số câu sai
    public int countIncorrectAnswers(String maPhienLamBai) {
        try {
            return em.createQuery("SELECT COUNT(ct) FROM CauTraLoi ct " +
                            "WHERE ct.phienLamBai.maPhienLamBai = :maPhienLamBai AND ct.ketQua = false", Long.class)
                    .setParameter("maPhienLamBai", maPhienLamBai)
                    .getSingleResult()
                    .intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Lấy danh sách câu trả lời theo phiên làm bài
    public List<CauTraLoi> getCauTraLoiByPhienLamBai(String maPhienLamBai) {
        try {
            return cauTraLoiDAO.findByPhienLamBai(maPhienLamBai);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}