import entities.GiaoVien;
import entities.HocSinh;
import entities.TaiKhoan;
import service.GiaoVienService;
import service.HocSinhService;
import service.TaiKhoanService;

import java.rmi.Naming;
import java.util.Objects;


public class RMIClient {

    public static void main(String[] args) throws Exception {

        TaiKhoanService taiKhoanService = (TaiKhoanService) Naming.lookup("rmi://localhost:9090/taiKhoanService");
        HocSinhService hocSinhService = (HocSinhService) Naming.lookup("rmi://localhost:9090/hocSinhService");
        GiaoVienService giaoVienService = (GiaoVienService) Naming.lookup("rmi://localhost:9090/giaoVienService");

        String emailHS = "anglythi@example.com";
//        HocSinh hocSinh = hocSinhService.timHocSinhTheoEmail(emailHS);
//        if (hocSinh != null) {
//            System.out.println("Tìm thấy học sinh: " + hocSinh);
//        } else {
//            System.out.println("Không tìm thấy học sinh với email: " + emailHS);
//        }

        String emailGV = "trantoocuong@gmail.com";
//        GiaoVien giaoVien = giaoVienService.timGiaoVienTheoEmail(emailGV);
//        if (giaoVien != null) {
//            System.out.println("Tìm thấy giáo viên: " + giaoVien);
//        } else {
//            System.out.println("Không tìm thấy giáo viên với email: " + emailGV);
//        }

        Object ketQuaHS = taiKhoanService.dangNhap(emailHS, "111");
        if (ketQuaHS instanceof HocSinh hocSinh) {
            System.out.println("Đăng nhập thành công với vai trò Học Sinh: " + hocSinh);
        } else {
            System.out.println("Đăng nhập thất bại hoặc sai vai trò cho email: " + emailHS);
        }

        // Test đăng nhập giáo viên

        Object ketQuaGV = taiKhoanService.dangNhap(emailGV, "111");
        if (ketQuaGV instanceof GiaoVien giaoVien) {
            System.out.println("Đăng nhập thành công với vai trò Giáo Viên: " + giaoVien);
        } else {
            System.out.println("Đăng nhập thất bại hoặc sai vai trò cho email: " + emailGV);
        }
    }
}
