package daos;

import entities.GiaoVien;
import entities.HocSinh;
import entities.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class TaiKhoanDAO extends GenericDAO<TaiKhoan, String> {
    private GiaoVienDAO giaoVienDAO;
    private HocSinhDAO hocSinhDAO;

    public TaiKhoanDAO(Class<TaiKhoan> clazz) {
        super(clazz);
        giaoVienDAO = new GiaoVienDAO(GiaoVien.class);
        hocSinhDAO = new HocSinhDAO(HocSinh.class);
    }

    public TaiKhoanDAO(EntityManager em, Class<TaiKhoan> clazz) {
        super(em, clazz);
        giaoVienDAO = new GiaoVienDAO(em, GiaoVien.class);
        hocSinhDAO = new HocSinhDAO(em, HocSinh.class);
    }

    // Kiểm tra đăng nhập và trả về đối tượng tương ứng
    public Object kiemTraDangNhap(String tenDangNhap, String matKhau) {
        try {
            TaiKhoan tk = findByID(tenDangNhap);

            if (tk != null && tk.getMatKhau().equals(matKhau)) {
                String vaiTro = tk.getLoaiTaiKhoan();

                if (vaiTro.equalsIgnoreCase("HocSinh")) {
                    // Lấy thông tin học sinh từ email
                    return hocSinhDAO.timHocSinhTheoEmail(tenDangNhap);
                } else if (vaiTro.equalsIgnoreCase("GiaoVien")) {
                    // Lấy thông tin giáo viên từ email
                    return giaoVienDAO.timGiaoVienTheoEmail(tenDangNhap);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null; // Trả về null nếu đăng nhập không thành công
    }


}
