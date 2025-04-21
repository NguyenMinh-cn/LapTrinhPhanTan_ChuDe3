package service;

import entities.GiaoVien;
import entities.HocSinh;
import entities.TaiKhoan;

import java.rmi.RemoteException;

public interface TaiKhoanService extends GenericService<TaiKhoan, String> {
    public Object dangNhap(String tenDangNhap, String matKhau) throws RemoteException;

}
