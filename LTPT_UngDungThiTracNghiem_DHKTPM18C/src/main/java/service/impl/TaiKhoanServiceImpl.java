package service.impl;

import daos.TaiKhoanDAO;
import entities.GiaoVien;
import entities.HocSinh;
import entities.TaiKhoan;
import jakarta.persistence.TypedQuery;
import service.TaiKhoanService;

import java.rmi.RemoteException;

public class TaiKhoanServiceImpl extends GenericServiceImpl<TaiKhoan, String> implements TaiKhoanService {

    private TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanServiceImpl(TaiKhoanDAO taiKhoanDAO) throws RemoteException {
        super(taiKhoanDAO);
        this.taiKhoanDAO = taiKhoanDAO;
    }
    @Override
    public Object dangNhap(String tenDangNhap, String matKhau) throws RemoteException {
        // Kiểm tra đăng nhập
        Object taiKhoan = taiKhoanDAO.kiemTraDangNhap(tenDangNhap, matKhau);

        // Kiểm tra nếu đăng nhập thành công và phân biệt theo vai trò
        if (taiKhoan != null) {
            if (taiKhoan instanceof GiaoVien) {
                return (GiaoVien) taiKhoan;
            } else if (taiKhoan instanceof HocSinh) {
                return (HocSinh) taiKhoan;
            }
        }
        return null; // Trả về null nếu đăng nhập không thành công
    }


    @Override
    public boolean delete(String email) throws RemoteException {
        return taiKhoanDAO.delete(email); // Gọi phương thức từ DAO
    }


    public boolean update(TaiKhoan taiKhoan) throws RemoteException {
        return taiKhoanDAO.update(taiKhoan); // Gọi phương thức từ DAO
    }

}