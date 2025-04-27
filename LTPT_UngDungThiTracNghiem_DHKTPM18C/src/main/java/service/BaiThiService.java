package service;

import entities.BaiThi;

import java.rmi.RemoteException;
import java.util.List;

public interface BaiThiService extends GenericService<BaiThi, Integer>{
    List<BaiThi> timDSBaiTHiTheoMaGiaoVien(int maGiaoVien) throws RemoteException;
    BaiThi layThongTinChiTietBaiThi(int maBaiThi) throws RemoteException;
    boolean delete(int maBaiThi) throws RemoteException;
    List<BaiThi> getAllBaiThiForHocSinh(Long maHocSinh) throws RemoteException;
}
