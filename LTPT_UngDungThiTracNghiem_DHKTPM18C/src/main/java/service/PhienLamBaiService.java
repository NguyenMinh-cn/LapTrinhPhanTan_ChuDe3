package service;

import entities.PhienLamBai;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PhienLamBaiService extends GenericService<PhienLamBai, String> {
    List<Object[]> layKetQuaChiTietPhienLamBai(String maPhienLamBai) throws RemoteException;
    Object[] tinhDiemVaSoCau(String maPhienLamBai) throws RemoteException;
    List<PhienLamBai> findByMaHocSinh(long maHocSinh) throws RemoteException;
    PhienLamBai layThongTinChiTietPhienLamBai(String maPhienLamBai) throws RemoteException;
    PhienLamBai layThongTinPhienLamBaiVaCauTraLoi(String maPhienLamBai) throws RemoteException;
    List<PhienLamBai> layDanhSachPhienLamBaiVaCauTraLoiTheoBaiThi(int maBaiThi) throws RemoteException;
}
