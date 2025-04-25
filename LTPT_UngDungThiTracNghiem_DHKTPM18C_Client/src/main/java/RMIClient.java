import entities.GiaoVien;
import entities.HocSinh;
import entities.MonHoc;
import entities.TaiKhoan;
import service.MonHocService;
import service.HocSinhService;
import service.GiaoVienService;
import service.HocSinhService;
import service.TaiKhoanService;

import java.rmi.Naming;

public class RMIClient {

    public static void main(String[] args) throws Exception {

        // Kết nối tới các dịch vụ qua RMI
        TaiKhoanService taiKhoanService = (TaiKhoanService) Naming.lookup("rmi://localhost:8081/taiKhoanService");
        HocSinhService hocSinhService = (HocSinhService) Naming.lookup("rmi://localhost:8081/hocSinhService");
        GiaoVienService giaoVienService = (GiaoVienService) Naming.lookup("rmi://localhost:8081/giaoVienService");
        try {
            MonHocService monHocService = (MonHocService) Naming.lookup("rmi://localhost:8081/monHocService");
            System.out.println("Kết nối thành công tới monHocService");
            // Tiến hành gọi các phương thức trên monHocService
        } catch (Exception e) {
            e.printStackTrace(); // In chi tiết lỗi nếu kết nối không thành công
            System.out.println("Không thể kết nối đến monHocService: " + e.getMessage());
        }

        // Thử tìm học sinh theo email
        String emailHS = "anglythi@example.com";
        HocSinh hocSinh = hocSinhService.timHocSinhTheoEmail(emailHS);
        if (hocSinh != null) {
            System.out.println("Tìm thấy học sinh: " + hocSinh);
        } else {
            System.out.println("Không tìm thấy học sinh với email: " + emailHS);
        }

        // Thử tìm giáo viên theo email
        String emailGV = "trantoocuong@gmail.com";
        GiaoVien giaoVien = giaoVienService.timGiaoVienTheoEmail(emailGV);
        if (giaoVien != null) {
            System.out.println("Tìm thấy giáo viên: " + giaoVien);
        } else {
            System.out.println("Không tìm thấy giáo viên với email: " + emailGV);
        }

        // Test đăng nhập học sinh
        Object ketQuaHS = taiKhoanService.dangNhap(emailHS, "111");
        if (ketQuaHS instanceof HocSinh) {
            HocSinh hs = (HocSinh) ketQuaHS;
            System.out.println("Đăng nhập thành công với vai trò Học Sinh: " + hs);
        } else {
            System.out.println("Đăng nhập thất bại hoặc sai vai trò cho email: " + emailHS);
        }

        // Test đăng nhập giáo viên
        Object ketQuaGV = taiKhoanService.dangNhap(emailGV, "111");
        if (ketQuaGV instanceof GiaoVien) {
            GiaoVien gv = (GiaoVien) ketQuaGV;
            System.out.println("Đăng nhập thành công với vai trò Giáo Viên: " + gv);
        } else {
            System.out.println("Đăng nhập thất bại hoặc sai vai trò cho email: " + emailGV);
        }
    }
}
