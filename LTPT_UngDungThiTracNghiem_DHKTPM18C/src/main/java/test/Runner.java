package test;

import daos.*;
import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;
import util.JPAUtil;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.*;

public class Runner {
    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.taoTKAdmin();
        // Các phương thức hiện có
//        runner.taoDSGVNgauNhien();
//        runner.taoDSLopVaHSNgauNhien();
//        runner.taoDSMonHocVaChuDe();
//        runner.themDSCauHoi();
//        runner.themCauHoiToanHoc();
//        runner.themCauHoiVietHoc();
//        runner.themCauHoiVatLy();
//        runner.themCauHoiHoaHoc();
//        runner.themCauHoiSinhHoc();
//        runner.themCauHoiLichSu();
//        runner.themCauHoiDiaLy();
//        runner.themCauHoiNgoaiNgu();
//        runner.themCauHoiGDCN();
//        runner.themCauHoiGDTC();
//        runner.taoBaiThiToanVaNgoaiNgu()
        runner.taoCacBaiThiMauVaPhienLamBai();

    }

    private String toSlug(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String withoutDiacritics = normalized.replaceAll("\\p{M}", "");
        return withoutDiacritics.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }

    public void taoTKAdmin() {
        EntityManager em = JPAUtil.getEntityManager();
        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(em, TaiKhoan.class);

        // Kiểm tra xem tài khoản đã tồn tại chưa
        try {
            TaiKhoan existingTaiKhoan = em.createQuery("SELECT tk FROM TaiKhoan tk WHERE tk.tenDangNhap = :tenDangNhap", TaiKhoan.class)
                    .setParameter("tenDangNhap", "admin@gmail.com")
                    .getSingleResult();

            // Nếu tài khoản đã tồn tại, in thông báo và bỏ qua
            System.out.println("Tài khoản admin@gmail.com đã tồn tại, bỏ qua bước tạo tài khoản admin.");
        } catch (jakarta.persistence.NoResultException e) {
            // Nếu không tìm thấy, tạo tài khoản mới
            TaiKhoan taiKhoan = new TaiKhoan("admin@gmail.com", "Admin", "111");
            taiKhoanDAO.save(taiKhoan);
            System.out.println("Đã tạo tài khoản admin thành công.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void taoDSGVNgauNhien() {
        EntityManager em = JPAUtil.getEntityManager();
        GiaoVienDAO giaoVienDAO = new GiaoVienDAO(em, GiaoVien.class);

        Faker faker = new Faker(new Locale("vi"));
        for (int i = 0; i < 10; i++) {
            String ten = faker.name().fullName();
            String emailTen = toSlug(ten);
            String email = emailTen + "@gmail.com";
            String sdt = "0" + faker.number().digits(9);

            TaiKhoan taiKhoan = new TaiKhoan(email, "GiaoVien", "111");
            GiaoVien giaoVien = new GiaoVien(0, ten, email, sdt, null, taiKhoan);
            giaoVienDAO.save(giaoVien);
        }
        em.close();
    }

    public void taoDSLopVaHSNgauNhien() {
        EntityManager em = JPAUtil.getEntityManager();
        LopDAO lopDAO = new LopDAO(em, Lop.class);
        HocSinhDAO hocSinhDAO = new HocSinhDAO(em, HocSinh.class);
        Faker faker = new Faker(new Locale("vi"));

        Set<String> tenLopSet = new LinkedHashSet<>(Arrays.asList(
                "10A", "10B", "10C",
                "11A", "11B", "11C",
                "12A", "12B", "12C"
        ));
        for (String tenLop : tenLopSet) {
            Lop lop = new Lop();
            lop.setTenLop(tenLop);
            lopDAO.save(lop);
            for (int i = 0; i < 10; i++) {
                String ten = faker.name().fullName();
                String emailTen = toSlug(ten);
                String email = emailTen + "@example.com";
                String sdt = "0" + faker.number().digits(9);

                TaiKhoan taiKhoan = new TaiKhoan(email, "HocSinh", "111");
                HocSinh hocSinh = new HocSinh(0, ten, email, sdt, lop, null, taiKhoan);
                hocSinhDAO.save(hocSinh);
            }
        }
        em.close();
    }

    public void taoDSMonHocVaChuDe() {
        EntityManager em = JPAUtil.getEntityManager();
        Map<String, List<String>> monHocVaChuDe = new LinkedHashMap<>();
        monHocVaChuDe.put("Toán học", Arrays.asList("Số học", "Hàm số", "Hình học"));
        monHocVaChuDe.put("Văn học", Arrays.asList("Văn học trung đại", "Văn học hiện đại"));
        monHocVaChuDe.put("Vật lý", Arrays.asList("Cơ học", "Quang học"));
        monHocVaChuDe.put("Hóa học", Arrays.asList("Nguyên tử", "Phản ứng hóa học"));
        monHocVaChuDe.put("Sinh học", Arrays.asList("Tế bào", "Di truyền học"));
        monHocVaChuDe.put("Lịch sử", Arrays.asList("Lịch sử Việt Nam", "Lịch sử Thế giới"));
        monHocVaChuDe.put("Địa lý", Arrays.asList("Tự nhiên", "Kinh tế"));
        monHocVaChuDe.put("Ngoại ngữ", Arrays.asList("Ngữ pháp", "Giao tiếp"));
        monHocVaChuDe.put("Giáo dục công dân", Arrays.asList("Pháp luật", "Kỹ năng sống"));
        monHocVaChuDe.put("Giáo dục thể chất", Arrays.asList("Bóng đá", "Điền kinh"));

        try {
            em.getTransaction().begin();
            for (Map.Entry<String, List<String>> entry : monHocVaChuDe.entrySet()) {
                String tenMon = entry.getKey();
                List<String> chuDes = entry.getValue();
                MonHoc monHoc = new MonHoc(0, tenMon, null, null);
                em.persist(monHoc);
                for (String tenChuDe : chuDes) {
                    ChuDe chuDe = new ChuDe();
                    chuDe.setTenChuDe(tenChuDe);
                    chuDe.setMonHoc(monHoc);
                    em.persist(chuDe);
                }
            }
            em.getTransaction().commit();
            System.out.println("✅ Đã thêm danh sách môn học và các chủ đề thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void themDSCauHoi() {
        EntityManager em = JPAUtil.getEntityManager();
        List<CauHoi> cauHoiList = new ArrayList<>();
        CauHoi[] cauHois = {
                new CauHoi(0, "Điểm cực Nam phần đất liền nước ta thuộc tỉnh nào?",
                        List.of("Kiên Giang", "Cà Mau", "An Giang", "Bạc Liêu"), "Cà Mau", null, null),
                new CauHoi(0, "Đường bờ biển của nước ta dài bao nhiêu km?",
                        List.of("3 260 km", "2 500 km", "4 000 km", "3 500 km"), "3 260 km", null, null),
                new CauHoi(0, "Nước ta có bao nhiêu đường biên giới trên đất liền?",
                        List.of("4 600 km", "5 000 km", "4 000 km", "3 800 km"), "4 600 km", null, null),
                new CauHoi(0, "Điểm cực Bắc phần đất liền nước ta thuộc tỉnh nào?",
                        List.of("Lào Cai", "Cao Bằng", "Hà Giang", "Lạng Sơn"), "Hà Giang", null, null),
                new CauHoi(0, "Huyện đảo Hoàng Sa trực thuộc tỉnh nào?",
                        List.of("Thành phố Đà Nẵng", "Tỉnh Quảng Ngãi", "Tỉnh Khánh Hòa", "Tỉnh Quảng Trị"), "Thành phố Đà Nẵng", null, null),
                new CauHoi(0, "Điểm cực Tây phần đất liền nước ta thuộc tỉnh nào?",
                        List.of("Điện Biên", "Cao Bằng", "Hà Giang", "Lạng Sơn"), "Điện Biên", null, null),
                new CauHoi(0, "Vị trí địa lí của nước ta giáp với những quốc gia nào?",
                        List.of("Trung Quốc, Lào, Cam-pu-chia", "Trung Quốc, Lào, Thái Lan", "Trung Quốc, Mianma, Lào", "Trung Quốc, Lào, Campuchia"), "Trung Quốc, Lào, Cam-pu-chia", null, null),
                new CauHoi(0, "Vị trí địa lý của nước ta không có ý nghĩa nào sau đây?",
                        List.of("Tạo điều kiện thực hiện chính sách mở cửa, hội nhập", "Tranh chấp Biển Đông và ranh giới với Trung Quốc", "Chung sống hòa bình với các nước", "Gây ảnh hưởng xấu đến môi trường biển"), "Tranh chấp Biển Đông và ranh giới với Trung Quốc", null, null),
                new CauHoi(0, "Lãnh thổ Việt Nam bao gồm những gì?",
                        List.of("Vùng đất, hải đảo, thềm lục địa", "Vùng đất, biển, vùng trời", "Vùng đất, đảo, biển", "Vùng đất, biển và khí hậu đặc trưng"), "Vùng đất, hải đảo, thềm lục địa", null, null),
                new CauHoi(0, "Biển Đông ảnh hưởng thế nào đến thiên nhiên nước ta?",
                        List.of("Thiên nhiên chịu ảnh hưởng sâu sắc của biển", "Khí hậu gió mùa nhiệt đới", "Khí hậu khô và nóng", "Thiên nhiên phân hóa theo mùa"), "Thiên nhiên chịu ảnh hưởng sâu sắc của biển", null, null),
                new CauHoi(0, "Điểm cực Đông phần đất liền nước ta thuộc tỉnh nào?",
                        List.of("Phú Yên", "Bình Thuận", "Quảng Ngãi", "Quảng Bình"), "Phú Yên", null, null),
        };
        for (CauHoi cauHoi : cauHois) {
            cauHoiList.add(cauHoi);
        }
        em.getTransaction().begin();
        try {
            for (CauHoi cauHoi : cauHoiList) {
                em.persist(cauHoi);
            }
            em.getTransaction().commit();
            System.out.println("Dữ liệu câu hỏi đã được lưu thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void themCauHoiToanHoc() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ChuDe chuDe = em.createQuery(
                            "SELECT c FROM ChuDe c WHERE c.tenChuDe = :tenChuDe AND c.monHoc.tenMon = :tenMon", ChuDe.class)
                    .setParameter("tenChuDe", "Số học")
                    .setParameter("tenMon", "Toán học")
                    .getSingleResult();
            List<CauHoi> ds = List.of(
                    taoCauHoi("Số nguyên tố là gì?", List.of("Số chỉ chia hết cho 1 và chính nó", "Số chia hết cho 2", "Số chẵn", "Số lẻ"), "Số chỉ chia hết cho 1 và chính nó", chuDe),
                    taoCauHoi("7 + 5 bằng bao nhiêu?", List.of("10", "12", "14", "15"), "12", chuDe),
                    taoCauHoi("Số chẵn đầu tiên lớn hơn 10 là?", List.of("11", "12", "13", "14"), "12", chuDe),
                    taoCauHoi("Tổng các số từ 1 đến 5 là?", List.of("10", "12", "15", "20"), "15", chuDe),
                    taoCauHoi("Số lớn nhất trong 3, 9, 2, 5?", List.of("2", "3", "5", "9"), "9", chuDe)
            );
            ds.forEach(em::persist);
            em.getTransaction().commit();
            System.out.println("✅ Đã thêm câu hỏi Toán học thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void themCauHoiVietHoc() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ChuDe chuDe = em.createQuery(
                            "SELECT c FROM ChuDe c WHERE c.tenChuDe = :tenChuDe AND c.monHoc.tenMon = :tenMon", ChuDe.class)
                    .setParameter("tenChuDe", "Văn học trung đại")
                    .setParameter("tenMon", "Văn học")
                    .getSingleResult();
            List<CauHoi> ds = List.of(
                    taoCauHoi("Tác phẩm nào là của Nguyễn Du?", List.of("Truyện Kiều", "Chí Phèo", "Tắt đèn", "Lão Hạc"), "Truyện Kiều", chuDe),
                    taoCauHoi("Văn học hiện đại Việt Nam bắt đầu từ khi nào?", List.of("Sau năm 1945", "Trước năm 1945", "Năm 1900", "Năm 1954"), "Sau năm 1945", chuDe)
            );
            ds.forEach(em::persist);
            em.getTransaction().commit();
            System.out.println("✅ Đã thêm câu hỏi Văn học thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void themCauHoiVatLy() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ChuDe chuDe = em.createQuery(
                            "SELECT c FROM ChuDe c WHERE c.tenChuDe = :tenChuDe AND c.monHoc.tenMon = :tenMon", ChuDe.class)
                    .setParameter("tenChuDe", "Cơ học")
                    .setParameter("tenMon", "Vật lý")
                    .getSingleResult();
            List<CauHoi> ds = List.of(
                    taoCauHoi("Lực hấp dẫn có giá trị bằng bao nhiêu?", List.of("9.8 m/s^2", "10 m/s^2", "9 m/s^2", "8 m/s^2"), "9.8 m/s^2", chuDe),
                    taoCauHoi("Áp suất được tính như thế nào?", List.of("P = F/S", "P = S/F", "P = F * S", "P = S * F"), "P = F/S", chuDe)
            );
            ds.forEach(em::persist);
            em.getTransaction().commit();
            System.out.println("✅ Đã thêm câu hỏi Vật lý thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void themCauHoiHoaHoc() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ChuDe chuDe = em.createQuery(
                            "SELECT c FROM ChuDe c WHERE c.tenChuDe = :tenChuDe AND c.monHoc.tenMon = :tenMon", ChuDe.class)
                    .setParameter("tenChuDe", "Nguyên tử")
                    .setParameter("tenMon", "Hóa học")
                    .getSingleResult();
            List<CauHoi> ds = List.of(
                    taoCauHoi("Nguyên tử gồm những hạt nào?", List.of("Electron, proton, neutron", "Electron, neutron", "Proton, neutron", "Electron, proton"), "Electron, proton, neutron", chuDe),
                    taoCauHoi("Hóa trị của oxi trong H2O là bao nhiêu?", List.of("1", "2", "3", "4"), "2", chuDe)
            );
            ds.forEach(em::persist);
            em.getTransaction().commit();
            System.out.println("✅ Đã thêm câu hỏi Hóa học thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void themCauHoiSinhHoc() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ChuDe chuDe = em.createQuery(
                            "SELECT c FROM ChuDe c WHERE c.tenChuDe = :tenChuDe AND c.monHoc.tenMon = :tenMon", ChuDe.class)
                    .setParameter("tenChuDe", "Tế bào")
                    .setParameter("tenMon", "Sinh học")
                    .getSingleResult();
            List<CauHoi> ds = List.of(
                    taoCauHoi("Tế bào gồm những phần chính nào?", List.of("Màng tế bào, nhân, tế bào chất", "Màng tế bào, nhân", "Tế bào chất, nhân", "Màng tế bào, chất nền"), "Màng tế bào, nhân, tế bào chất", chuDe),
                    taoCauHoi("DNA là gì?", List.of("Đơn vị di truyền", "Mô hình di truyền", "Một loại axit amin", "Chất lỏng trong tế bào"), "Đơn vị di truyền", chuDe)
            );
            ds.forEach(em::persist);
            em.getTransaction().commit();
            System.out.println("✅ Đã thêm câu hỏi Sinh học thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void themCauHoiLichSu() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ChuDe chuDe = em.createQuery(
                            "SELECT c FROM ChuDe c WHERE c.tenChuDe = :tenChuDe AND c.monHoc.tenMon = :tenMon", ChuDe.class)
                    .setParameter("tenChuDe", "Lịch sử Việt Nam")
                    .setParameter("tenMon", "Lịch sử")
                    .getSingleResult();
            List<CauHoi> ds = List.of(
                    taoCauHoi("Ai là vị vua đầu tiên của triều đại Lý?", List.of("Lý Thái Tổ", "Lý Thái Tông", "Lý Anh Tông", "Lý Công Uẩn"), "Lý Thái Tổ", chuDe),
                    taoCauHoi("Chiến tranh Việt Nam bắt đầu vào năm nào?", List.of("1954", "1965", "1975", "1945"), "1954", chuDe)
            );
            ds.forEach(em::persist);
            em.getTransaction().commit();
            System.out.println("✅ Đã thêm câu hỏi Lịch sử thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void themCauHoiDiaLy() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ChuDe chuDe = em.createQuery(
                            "SELECT c FROM ChuDe c WHERE c.tenChuDe = :tenChuDe AND c.monHoc.tenMon = :tenMon", ChuDe.class)
                    .setParameter("tenChuDe", "Tự nhiên")
                    .setParameter("tenMon", "Địa lý")
                    .getSingleResult();
            List<CauHoi> ds = List.of(
                    taoCauHoi("Vùng nhiệt đới có đặc điểm gì?", List.of("Khí hậu nóng, mưa nhiều", "Khí hậu lạnh, ít mưa", "Khí hậu ôn hòa", "Khí hậu khô hạn"), "Khí hậu nóng, mưa nhiều", chuDe),
                    taoCauHoi("Địa lý Việt Nam có bao nhiêu tỉnh?", List.of("63", "64", "66", "60"), "63", chuDe)
            );
            ds.forEach(em::persist);
            em.getTransaction().commit();
            System.out.println("✅ Đã thêm câu hỏi Địa lý thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void themCauHoiNgoaiNgu() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ChuDe chuDe = em.createQuery(
                            "SELECT c FROM ChuDe c WHERE c.tenChuDe = :tenChuDe AND c.monHoc.tenMon = :tenMon", ChuDe.class)
                    .setParameter("tenChuDe", "Ngữ pháp")
                    .setParameter("tenMon", "Ngoại ngữ")
                    .getSingleResult();
            List<CauHoi> ds = List.of(
                    taoCauHoi("Thì hiện tại đơn trong tiếng Anh được sử dụng để diễn tả điều gì?", List.of("Sự thật hiển nhiên", "Sự việc trong quá khứ", "Sự việc trong tương lai", "Sự việc xảy ra mỗi ngày"), "Sự thật hiển nhiên", chuDe),
                    taoCauHoi("Từ nào sau đây là động từ bất quy tắc?", List.of("Go", "Walk", "Talk", "Play"), "Go", chuDe)
            );
            ds.forEach(em::persist);
            em.getTransaction().commit();
            System.out.println("✅ Đã thêm câu hỏi Ngoại ngữ thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void themCauHoiGDCN() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ChuDe chuDe = em.createQuery(
                            "SELECT c FROM ChuDe c WHERE c.tenChuDe = :tenChuDe AND c.monHoc.tenMon = :tenMon", ChuDe.class)
                    .setParameter("tenChuDe", "Pháp luật")
                    .setParameter("tenMon", "Giáo dục công dân")
                    .getSingleResult();
            List<CauHoi> ds = List.of(
                    taoCauHoi("Luật pháp có vai trò gì trong xã hội?", List.of("Bảo vệ quyền lợi con người", "Làm tăng sự giàu có", "Giảm thiểu mọi hình thức tội phạm", "Tạo ra sự phân biệt xã hội"), "Bảo vệ quyền lợi con người", chuDe),
                    taoCauHoi("Một người bị kết án tù thì có quyền gì?", List.of("Kháng cáo", "Chạy trốn", "Làm chứng", "Từ bỏ quyền lợi"), "Kháng cáo", chuDe)
            );
            ds.forEach(em::persist);
            em.getTransaction().commit();
            System.out.println("✅ Đã thêm câu hỏi Giáo dục công dân thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void themCauHoiGDTC() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ChuDe chuDe = em.createQuery(
                            "SELECT c FROM ChuDe c WHERE c.tenChuDe = :tenChuDe AND c.monHoc.tenMon = :tenMon", ChuDe.class)
                    .setParameter("tenChuDe", "Bóng đá")
                    .setParameter("tenMon", "Giáo dục thể chất")
                    .getSingleResult();
            List<CauHoi> ds = List.of(
                    taoCauHoi("Sân bóng đá có kích thước chuẩn là bao nhiêu?", List.of("100m x 60m", "110m x 70m", "120m x 75m", "90m x 45m"), "110m x 70m", chuDe),
                    taoCauHoi("Số người chơi tối đa trong mỗi đội bóng?", List.of("11", "10", "9", "12"), "11", chuDe)
            );
            ds.forEach(em::persist);
            em.getTransaction().commit();
            System.out.println("✅ Đã thêm câu hỏi Giáo dục thể chất thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private CauHoi taoCauHoi(String noiDung, List<String> dapAn, String dapAnDung, ChuDe chuDe) {
        CauHoi ch = new CauHoi();
        ch.setNoiDung(noiDung);
        ch.setDanhSachDapAn(new ArrayList<>(dapAn));
        ch.setDapAnDung(dapAnDung);
        ch.setChuDe(chuDe);
        return ch;
    }

    public void taoBaiThiToanVaNgoaiNgu() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            LopDAO lopDAO = new LopDAO(em, Lop.class);
            Lop lop1 = lopDAO.findByID(1);
            Lop lop2 = lopDAO.findByID(2);

            MonHoc toan = em.createQuery("SELECT m FROM MonHoc m WHERE m.tenMon = :tenMon", MonHoc.class)
                    .setParameter("tenMon", "Toán học")
                    .getSingleResult();
            MonHoc ngoaiNgu = em.createQuery("SELECT m FROM MonHoc m WHERE m.tenMon = :tenMon", MonHoc.class)
                    .setParameter("tenMon", "Ngoại ngữ")
                    .getSingleResult();
            GiaoVien giaoVien = em.createQuery("SELECT g FROM GiaoVien g WHERE g.id = 1", GiaoVien.class)
                    .getSingleResult();

            List<CauHoi> cauHoiToan = em.createQuery(
                            "SELECT c FROM CauHoi c WHERE c.chuDe.tenChuDe = :tenChuDe AND c.chuDe.monHoc.tenMon = :tenMon", CauHoi.class)
                    .setParameter("tenChuDe", "Số học")
                    .setParameter("tenMon", "Toán học")
                    .getResultList();
            List<CauHoi> cauHoiNgoaiNgu = em.createQuery(
                            "SELECT c FROM CauHoi c WHERE c.chuDe.tenChuDe = :tenChuDe AND c.chuDe.monHoc.tenMon = :tenMon", CauHoi.class)
                    .setParameter("tenChuDe", "Ngữ pháp")
                    .setParameter("tenMon", "Ngoại ngữ")
                    .getResultList();

            LocalDateTime batDau = LocalDateTime.of(2025, 5, 20, 8, 0);
            LocalDateTime ketThuc = LocalDateTime.of(2025, 5, 20, 9, 0);

            BaiThi baiThi10Toan = new BaiThi();
            baiThi10Toan.setTenBaiThi("Bài thi Toán học lớp 10A");
            baiThi10Toan.setThoiGianBatDau(batDau);
            baiThi10Toan.setThoiGianKetThuc(ketThuc);
            baiThi10Toan.setThoiLuong(60);
            baiThi10Toan.setMonHoc(toan);
            baiThi10Toan.setGiaoVien(giaoVien);
            baiThi10Toan.getDanhSachLop().add(lop1);
            baiThi10Toan.setDanhSachCauHoi(cauHoiToan);
            em.persist(baiThi10Toan);

            BaiThi baiThi11NgoaiNgu = new BaiThi();
            baiThi11NgoaiNgu.setTenBaiThi("Bài thi Ngoại ngữ lớp 11A");
            baiThi11NgoaiNgu.setThoiGianBatDau(batDau);
            baiThi11NgoaiNgu.setThoiGianKetThuc(ketThuc.plusMinutes(30));
            baiThi11NgoaiNgu.setThoiLuong(90);
            baiThi11NgoaiNgu.setMonHoc(ngoaiNgu);
            baiThi11NgoaiNgu.setGiaoVien(giaoVien);
            baiThi11NgoaiNgu.getDanhSachLop().add(lop2);
            baiThi11NgoaiNgu.setDanhSachCauHoi(cauHoiNgoaiNgu);
            em.persist(baiThi11NgoaiNgu);

            em.getTransaction().commit();
            System.out.println("✅ Đã tạo thành công 2 bài thi (Toán & Ngoại ngữ) cho lớp 10A và 11A");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void taoCacBaiThiMauVaPhienLamBai() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // Xóa dữ liệu cũ
            em.createNativeQuery("DELETE FROM CauTraLoi").executeUpdate();
            em.createNativeQuery("DELETE FROM PhienLamBai").executeUpdate();
            em.createNativeQuery("DELETE FROM BaiThi_CauHoi").executeUpdate();
            em.createNativeQuery("DELETE FROM BaiThi_Lop").executeUpdate();
            em.createNativeQuery("DELETE FROM BaiThi").executeUpdate();
            em.createNativeQuery("DELETE FROM GiaoVien").executeUpdate();
            em.createNativeQuery("DELETE FROM CauHoi_danhSachDapAn").executeUpdate();
            em.createNativeQuery("DELETE FROM CauHoi").executeUpdate();
            em.createNativeQuery("DELETE FROM ChuDe").executeUpdate();
            em.createNativeQuery("DELETE FROM MonHoc").executeUpdate();
            em.createNativeQuery("DELETE FROM HocSinh").executeUpdate();
            em.createNativeQuery("DELETE FROM Lop").executeUpdate();
            em.createNativeQuery("DELETE FROM TaiKhoan WHERE TenDangNhap != 'admin@gmail.com'").executeUpdate();

            // Tạo lớp học mẫu
            LopDAO lopDAO = new LopDAO(em, Lop.class);
            Lop lop = new Lop();
            lop.setTenLop("10A");
            lopDAO.save(lop);

            // Tạo học sinh mẫu
            HocSinhDAO hocSinhDAO = new HocSinhDAO(em, HocSinh.class);
            TaiKhoan taiKhoanHS = new TaiKhoan("hocsinh1@example.com", "HocSinh", "111");
            HocSinh hocSinh = new HocSinh(0, "Nguyễn Văn A", "hocsinh1@example.com", "0901234567", lop, null, taiKhoanHS);
            hocSinhDAO.save(hocSinh);

            // Tạo giáo viên mẫu
            GiaoVienDAO giaoVienDAO = new GiaoVienDAO(em, GiaoVien.class);
            TaiKhoan taiKhoanGV = new TaiKhoan("giaovien1@gmail.com", "GiaoVien", "111");
            GiaoVien giaoVien = new GiaoVien(0, "Trần Thị B", "giaovien1@gmail.com", "0912345678", null, taiKhoanGV);
            giaoVienDAO.save(giaoVien);

            // Tạo môn học và chủ đề
            MonHoc monToan = new MonHoc(0, "Toán học", null, null);
            em.persist(monToan);
            ChuDe chuDeToan = new ChuDe();
            chuDeToan.setTenChuDe("Số học");
            chuDeToan.setMonHoc(monToan);
            em.persist(chuDeToan);

            MonHoc monVan = new MonHoc(0, "Văn học", null, null);
            em.persist(monVan);
            ChuDe chuDeVan = new ChuDe();
            chuDeVan.setTenChuDe("Văn học trung đại");
            chuDeVan.setMonHoc(monVan);
            em.persist(chuDeVan);

            MonHoc monVatLy = new MonHoc(0, "Vật lý", null, null);
            em.persist(monVatLy);
            ChuDe chuDeVatLy = new ChuDe();
            chuDeVatLy.setTenChuDe("Cơ học");
            chuDeVatLy.setMonHoc(monVatLy);
            em.persist(chuDeVatLy);

            // Tạo câu hỏi mẫu
            List<CauHoi> cauHoiToan = List.of(
                    taoCauHoi("7 + 5 bằng bao nhiêu?", List.of("10", "12", "14", "15"), "12", chuDeToan),
                    taoCauHoi("Số chẵn đầu tiên lớn hơn 10 là?", List.of("11", "12", "13", "14"), "12", chuDeToan),
                    taoCauHoi("Tổng các số từ 1 đến 5 là?", List.of("10", "12", "15", "20"), "15", chuDeToan)
            );
            cauHoiToan.forEach(em::persist);

            List<CauHoi> cauHoiVan = List.of(
                    taoCauHoi("Tác phẩm nào là của Nguyễn Du?", List.of("Truyện Kiều", "Chí Phèo", "Tắt đèn", "Lão Hạc"), "Truyện Kiều", chuDeVan),
                    taoCauHoi("Văn học hiện đại Việt Nam bắt đầu từ khi nào?", List.of("Sau năm 1945", "Trước năm 1945", "Năm 1900", "Năm 1954"), "Sau năm 1945", chuDeVan)
            );
            cauHoiVan.forEach(em::persist);

            List<CauHoi> cauHoiVatLy = List.of(
                    taoCauHoi("Lực hấp dẫn có giá trị bằng bao nhiêu?", List.of("9.8 m/s^2", "10 m/s^2", "9 m/s^2", "8 m/s^2"), "9.8 m/s^2", chuDeVatLy),
                    taoCauHoi("Áp suất được tính như thế nào?", List.of("P = F/S", "P = S/F", "P = F * S", "P = S * F"), "P = F/S", chuDeVatLy)
            );
            cauHoiVatLy.forEach(em::persist);

            // Tạo bài thi mẫu
            LocalDateTime batDau = LocalDateTime.of(2025, 5, 20, 8, 0);
            LocalDateTime ketThuc = LocalDateTime.of(2025, 5, 20, 9, 0);

            // Bài thi Toán
            BaiThi baiThiToan = new BaiThi();
            baiThiToan.setTenBaiThi("Bài thi Toán học mẫu");
            baiThiToan.setThoiGianBatDau(batDau);
            baiThiToan.setThoiGianKetThuc(ketThuc);
            baiThiToan.setThoiLuong(60);
            baiThiToan.setMonHoc(monToan);
            baiThiToan.setGiaoVien(giaoVien);
            baiThiToan.getDanhSachLop().add(lop);
            baiThiToan.setDanhSachCauHoi(cauHoiToan);
            em.persist(baiThiToan);

            // Bài thi Văn
            BaiThi baiThiVan = new BaiThi();
            baiThiVan.setTenBaiThi("Bài thi Văn học mẫu");
            baiThiVan.setThoiGianBatDau(batDau);
            baiThiVan.setThoiGianKetThuc(ketThuc.plusMinutes(30));
            baiThiVan.setThoiLuong(90);
            baiThiVan.setMonHoc(monVan);
            baiThiVan.setGiaoVien(giaoVien);
            baiThiVan.getDanhSachLop().add(lop);
            baiThiVan.setDanhSachCauHoi(cauHoiVan);
            em.persist(baiThiVan);

            // Bài thi Vật lý
            BaiThi baiThiVatLy = new BaiThi();
            baiThiVatLy.setTenBaiThi("Bài thi Vật lý mẫu");
            baiThiVatLy.setThoiGianBatDau(batDau);
            baiThiVatLy.setThoiGianKetThuc(ketThuc.plusMinutes(15));
            baiThiVatLy.setThoiLuong(75);
            baiThiVatLy.setMonHoc(monVatLy);
            baiThiVatLy.setGiaoVien(giaoVien);
            baiThiVatLy.getDanhSachLop().add(lop);
            baiThiVatLy.setDanhSachCauHoi(cauHoiVatLy);
            em.persist(baiThiVatLy);

            // Tạo mã phiên làm bài
            long count = (long) em.createQuery("SELECT COUNT(p) FROM PhienLamBai p").getSingleResult();
            String maPhienToan = String.format("PLB%03d", count + 1);
            String maPhienVan = String.format("PLB%03d", count + 2);
            String maPhienVatLy = String.format("PLB%03d", count + 3);

            // Phiên làm bài Toán
            PhienLamBai phienToan = new PhienLamBai();
            phienToan.setMaPhien(maPhienToan);
            phienToan.setHocSinh(hocSinh);
            phienToan.setBaiThi(baiThiToan);
            phienToan.setThoiGianBatDau(batDau);
            phienToan.setThoiGianKetThuc(ketThuc.minusMinutes(10));

            List<CauTraLoi> danhSachCauTraLoiToan = new ArrayList<>();
            CauTraLoi cauTraLoiToan1 = new CauTraLoi();
            cauTraLoiToan1.setPhienLamBai(phienToan);
            cauTraLoiToan1.setNoiDungCauHoi(cauHoiToan.get(0).getNoiDung());
            cauTraLoiToan1.setDapAnDaChon("12");
            cauTraLoiToan1.setKetQua("12".equals(cauHoiToan.get(0).getDapAnDung()));
            danhSachCauTraLoiToan.add(cauTraLoiToan1);

            CauTraLoi cauTraLoiToan2 = new CauTraLoi();
            cauTraLoiToan2.setPhienLamBai(phienToan);
            cauTraLoiToan2.setNoiDungCauHoi(cauHoiToan.get(1).getNoiDung());
            cauTraLoiToan2.setDapAnDaChon("14");
            cauTraLoiToan2.setKetQua("14".equals(cauHoiToan.get(1).getDapAnDung()));
            danhSachCauTraLoiToan.add(cauTraLoiToan2);

            CauTraLoi cauTraLoiToan3 = new CauTraLoi();
            cauTraLoiToan3.setPhienLamBai(phienToan);
            cauTraLoiToan3.setNoiDungCauHoi(cauHoiToan.get(2).getNoiDung());
            cauTraLoiToan3.setDapAnDaChon("15");
            cauTraLoiToan3.setKetQua("15".equals(cauHoiToan.get(2).getDapAnDung()));
            danhSachCauTraLoiToan.add(cauTraLoiToan3);

            phienToan.setDanhSachCauTraLoi(danhSachCauTraLoiToan);
            int soCauDungToan = (int) danhSachCauTraLoiToan.stream().filter(CauTraLoi::isKetQua).count();
            int tongSoCauToan = danhSachCauTraLoiToan.size();
            double diemToan = (tongSoCauToan > 0) ? ((double) soCauDungToan / tongSoCauToan) * 100 : 0;
            phienToan.setDiem(diemToan);
            em.persist(phienToan);

            // Phiên làm bài Văn
            PhienLamBai phienVan = new PhienLamBai();
            phienVan.setMaPhien(maPhienVan);
            phienVan.setHocSinh(hocSinh);
            phienVan.setBaiThi(baiThiVan);
            phienVan.setThoiGianBatDau(batDau);
            phienVan.setThoiGianKetThuc(ketThuc.plusMinutes(20));

            List<CauTraLoi> danhSachCauTraLoiVan = new ArrayList<>();
            CauTraLoi cauTraLoiVan1 = new CauTraLoi();
            cauTraLoiVan1.setPhienLamBai(phienVan);
            cauTraLoiVan1.setNoiDungCauHoi(cauHoiVan.get(0).getNoiDung());
            cauTraLoiVan1.setDapAnDaChon("Truyện Kiều");
            cauTraLoiVan1.setKetQua("Truyện Kiều".equals(cauHoiVan.get(0).getDapAnDung()));
            danhSachCauTraLoiVan.add(cauTraLoiVan1);

            CauTraLoi cauTraLoiVan2 = new CauTraLoi();
            cauTraLoiVan2.setPhienLamBai(phienVan);
            cauTraLoiVan2.setNoiDungCauHoi(cauHoiVan.get(1).getNoiDung());
            cauTraLoiVan2.setDapAnDaChon("Năm 1900");
            cauTraLoiVan2.setKetQua("Năm 1900".equals(cauHoiVan.get(1).getDapAnDung()));
            danhSachCauTraLoiVan.add(cauTraLoiVan2);

            phienVan.setDanhSachCauTraLoi(danhSachCauTraLoiVan);
            int soCauDungVan = (int) danhSachCauTraLoiVan.stream().filter(CauTraLoi::isKetQua).count();
            int tongSoCauVan = danhSachCauTraLoiVan.size();
            double diemVan = (tongSoCauVan > 0) ? ((double) soCauDungVan / tongSoCauVan) * 100 : 0;
            phienVan.setDiem(diemVan);
            em.persist(phienVan);

            // Phiên làm bài Vật lý
            PhienLamBai phienVatLy = new PhienLamBai();
            phienVatLy.setMaPhien(maPhienVatLy);
            phienVatLy.setHocSinh(hocSinh);
            phienVatLy.setBaiThi(baiThiVatLy);
            phienVatLy.setThoiGianBatDau(batDau);
            phienVatLy.setThoiGianKetThuc(ketThuc.plusMinutes(5));

            List<CauTraLoi> danhSachCauTraLoiVatLy = new ArrayList<>();
            CauTraLoi cauTraLoiVatLy1 = new CauTraLoi();
            cauTraLoiVatLy1.setPhienLamBai(phienVatLy);
            cauTraLoiVatLy1.setNoiDungCauHoi(cauHoiVatLy.get(0).getNoiDung());
            cauTraLoiVatLy1.setDapAnDaChon("9.8 m/s^2");
            cauTraLoiVatLy1.setKetQua("9.8 m/s^2".equals(cauHoiVatLy.get(0).getDapAnDung()));
            danhSachCauTraLoiVatLy.add(cauTraLoiVatLy1);

            CauTraLoi cauTraLoiVatLy2 = new CauTraLoi();
            cauTraLoiVatLy2.setPhienLamBai(phienVatLy);
            cauTraLoiVatLy2.setNoiDungCauHoi(cauHoiVatLy.get(1).getNoiDung());
            cauTraLoiVatLy2.setDapAnDaChon("P = S/F");
            cauTraLoiVatLy2.setKetQua("P = S/F".equals(cauHoiVatLy.get(1).getDapAnDung()));
            danhSachCauTraLoiVatLy.add(cauTraLoiVatLy2);

            phienVatLy.setDanhSachCauTraLoi(danhSachCauTraLoiVatLy);
            int soCauDungVatLy = (int) danhSachCauTraLoiVatLy.stream().filter(CauTraLoi::isKetQua).count();
            int tongSoCauVatLy = danhSachCauTraLoiVatLy.size();
            double diemVatLy = (tongSoCauVatLy > 0) ? ((double) soCauDungVatLy / tongSoCauVatLy) * 100 : 0;
            phienVatLy.setDiem(diemVatLy);
            em.persist(phienVatLy);

            em.getTransaction().commit();
            System.out.println("✅ Đã tạo thành công học sinh, bài thi mẫu và phiên làm bài!");
            System.out.println("Danh sách MaPhien vừa tạo: " + maPhienToan + ", " + maPhienVan + ", " + maPhienVatLy);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("❌ Lỗi khi tạo dữ liệu: " + e.getMessage());
        } finally {
            em.close();
        }
    }

}
