//package test;
//
//import entities.HocSinh;
//import entities.TaiKhoan;
//import entities.Lop;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//import net.datafaker.Faker;
//import java.util.List;
//
//public class InsertHocSinh {
//    public void themDSHocSinh() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb");
//        EntityManager em = emf.createEntityManager();
//        Faker faker = new Faker();
//
//        try {
//            List<Lop> lopList = em.createQuery("SELECT l FROM Lop l", Lop.class).getResultList();
////            System.out.println("Danh sách lớp:");
//
//            for (Lop lop : lopList) {
////                System.out.println("Mã lớp: " + lop.getMaLop() + ", Tên lớp: " + lop.getTenLop());
//                for (int i = 0; i < 20; i++) { // Chèn 20 học sinh cho mỗi lớp
//                    HocSinh hocSinh = new HocSinh();
//                    hocSinh.setMaHocSinh(faker.idNumber().valid());
//                    hocSinh.setHoTen(faker.name().fullName());
//                    hocSinh.setEmail(faker.internet().emailAddress());
//                    hocSinh.setSoDienThoai(faker.phoneNumber().subscriberNumber(10));
//
//                    hocSinh.setLop(lop);
//
//                    TaiKhoan taiKhoan = new TaiKhoan();
//                    taiKhoan.setTenDangNhap(hocSinh.getEmail());
//                    taiKhoan.setMatKhau(faker.internet().password());
//                    taiKhoan.setLoaiTaiKhoan("hocsinh");
//                    hocSinh.setTaiKhoan(taiKhoan);
//
//                    // Chèn học sinh vào cơ sở dữ liệu
//                    em.getTransaction().begin();
//                    em.persist(hocSinh);
//                    em.getTransaction().commit();
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//            if (emf != null) {
//                emf.close();
//            }
//        }
//    }
//
//}
