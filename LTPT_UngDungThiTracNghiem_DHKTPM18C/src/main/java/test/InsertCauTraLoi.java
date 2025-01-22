package test;

import entities.BaiThi;
import entities.CauHoi;
import entities.CauTraLoi;
import entities.PhienLamBai;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InsertCauTraLoi {
    public void themDSCauTraLoi()  {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            // Lấy danh sách các phiên làm bài
            List<PhienLamBai> phienLamBaiList = em.createQuery("SELECT p FROM PhienLamBai p", PhienLamBai.class).getResultList();
            for (PhienLamBai phienLamBai : phienLamBaiList) {
                BaiThi baiThi = phienLamBai.getBaiThi();
                // Lấy danh sách câu hỏi của bài thi
                List<CauHoi> cauHoiList = em.createQuery("SELECT c FROM CauHoi c JOIN c.danhSachBaiThi b WHERE b = :baiThi", CauHoi.class)
                        .setParameter("baiThi", baiThi)
                        .getResultList();

                System.out.println("Bài thi: " + baiThi.getTenBaiThi());

                for (CauHoi cauHoi : cauHoiList) {
                    CauTraLoi cauTraLoi = new CauTraLoi();
                    cauTraLoi.setCauHoi(cauHoi);
                    cauTraLoi.setPhienLamBai(phienLamBai);
                    cauTraLoi.setDapAnA(cauHoi.getDapAnA());
                    cauTraLoi.setDapAnB(cauHoi.getDapAnB());
                    cauTraLoi.setDapAnC(cauHoi.getDapAnC());
                    cauTraLoi.setDapAnD(cauHoi.getDapAnD());

                    List<String> dapAns = new ArrayList<>();
                    dapAns.add(cauTraLoi.getDapAnA());
                    dapAns.add(cauTraLoi.getDapAnB());
                    dapAns.add(cauTraLoi.getDapAnC());
                    dapAns.add(cauTraLoi.getDapAnD());
                    Random random = new Random();
                    String dapAnDaChon = dapAns.get(random.nextInt(dapAns.size()));
                    cauTraLoi.setDapAnDaChon(dapAnDaChon);
                    em.persist(cauTraLoi);
                    System.out.println("Đã thêm câu trả lời cho câu hỏi: " + cauHoi.getNoiDung());
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
