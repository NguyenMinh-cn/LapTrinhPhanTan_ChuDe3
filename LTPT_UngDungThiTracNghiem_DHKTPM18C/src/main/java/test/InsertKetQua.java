//package test;
//
//import entities.*;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//import java.math.BigDecimal;
//import java.time.Duration;
//import java.util.List;
//
//public class InsertKetQua {
//    public void themDSKetQua() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb");
//        EntityManager em = emf.createEntityManager();
//
//        try {
//
//            List<PhienLamBai> phienLamBaiList = em.createQuery("SELECT p FROM PhienLamBai p", PhienLamBai.class).getResultList();
//
//            for (PhienLamBai phienLamBai : phienLamBaiList) {
//                BaiThi baiThi = phienLamBai.getBaiThi();
//                // Lấy danh sách câu hỏi của bài thi
//                List<CauHoi> cauHoiList = em.createQuery("SELECT c FROM CauHoi c JOIN c.danhSachBaiThi b WHERE b = :baiThi", CauHoi.class)
//                        .setParameter("baiThi", baiThi)
//                        .getResultList();
//
//                // Tính số câu hỏi trong bài thi
//                int soCau = cauHoiList.size();
//
//                double diemMoiCau = 10.0 / soCau;
//
//                double tongDiem = 0;
//                int soCauDung = 0;
//
//                List<CauTraLoi> cauTraLoiList = em.createQuery(
//                                "SELECT c FROM CauTraLoi c WHERE c.phienLamBai = :phienLamBai", CauTraLoi.class)
//                        .setParameter("phienLamBai", phienLamBai)
//                        .getResultList();
//
//                for (CauTraLoi cauTraLoi : cauTraLoiList) {
//                    // So sánh đáp án đã chọn với đáp án đúng
//                    if (cauTraLoi.getDapAnDaChon().equals(cauTraLoi.getCauHoi().getDapAnDung())) {
//                        soCauDung++;
//                    }
//                }
//
//                // Tính tổng điểm
//                tongDiem = soCauDung * diemMoiCau;
////
////                // Tạo kết quả
//                KetQua ketQua = new KetQua();
//                ketQua.setMaPhienLamBai(phienLamBai.getMaPhien());
//                ketQua.setPhienLamBai(phienLamBai);
//                String diemString = String.format("%.2f", tongDiem);
//                double diemLamTron = Double.parseDouble(diemString);
//                ketQua.setDiem(diemLamTron);
//                ketQua.setSoCauDung(soCauDung);
//                ketQua.setSoCauSai(soCau - soCauDung);
//
//                // Tính thời gian làm bài
//                Duration duration = Duration.between(phienLamBai.getThoiGianBatDau(), phienLamBai.getThoiGianKetThuc());
//                int tongThoiGianLamBai = (int) duration.toMinutes();
//                ketQua.setTongThoiGianLamBai(tongThoiGianLamBai); // Thời gian làm bài tính bằng phút
//
//                // In kết quả ra console
//                System.out.println("Kết quả là : " );
//                System.out.println("Điểm bài thi: " + tongDiem);
//                System.out.println("Số câu đúng: " + soCauDung);
//                System.out.println("Số câu sai: " + (soCau - soCauDung));
//                System.out.println("Thời gian làm bài: " + tongThoiGianLamBai + " phút");
//                System.out.println("------------------------------------------------------");
//                em.getTransaction().begin();
//                em.persist(ketQua);
//                em.getTransaction().commit();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // Đóng EntityManager
//            em.close();
//            emf.close();
//        }
//    }
//}
