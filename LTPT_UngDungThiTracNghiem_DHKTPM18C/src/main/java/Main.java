
import daos.MonHocDAO;
import entities.MonHoc;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Tạo DAO cho MonHoc
        MonHocDAO monHocDAO = new MonHocDAO(MonHoc.class);

        // Thêm một môn học mới
        MonHoc monMoi = new MonHoc();
        monMoi.setTenMon("Lập trình phân tán");
//        boolean saved = monHocDAO.save(monMoi);
//        System.out.println("Lưu môn học mới: " + (saved ? "Thành công" : "Thất bại"));

        // Lấy danh sách tất cả các môn học
        List<MonHoc> danhSach = monHocDAO.getAll();
        System.out.println("Danh sách môn học:");
        for (MonHoc mh : danhSach) {
            System.out.println("Mã môn: " + mh.getMaMon() + ", Tên môn: " + mh.getTenMon());
        }

        // Tìm môn học theo ID

        MonHoc monTim = monHocDAO.findById(11);
        if (monTim != null) {
            System.out.println("Tìm thấy môn học: " + monTim.getTenMon());
        } else {
            System.out.println("Không tìm thấy môn học có mã: " + 11);
        }

        // Xóa môn học theo ID
        boolean xoa = monHocDAO.delete(11);
        System.out.println("Xóa môn học mã " + 11 + ": " + (xoa ? "Thành công" : "Thất bại"));
    }
}
