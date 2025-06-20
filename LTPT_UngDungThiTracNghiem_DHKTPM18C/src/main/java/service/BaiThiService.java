    package service;

    import entities.BaiThi;
    import entities.Lop;

    import java.rmi.Remote;
    import java.rmi.RemoteException;
    import java.util.List;

public interface BaiThiService extends GenericService<BaiThi, Integer>{
    List<BaiThi> timDSBaiTHiTheoMaGiaoVien(int maGiaoVien) throws RemoteException;
    BaiThi layThongTinChiTietBaiThi(int maBaiThi) throws RemoteException;
    boolean delete(int maBaiThi) throws RemoteException;
    List<BaiThi> getAllBaiThiForHocSinh(Long maHocSinh) throws RemoteException;
    BaiThi layThongTinBaiThiVaCauHoi(int maBaiThi) throws RemoteException;
    List<Lop> timLopTheoBaiThi(int maBaiThi) throws RemoteException;
    boolean xoaBaiThiKhongCoPhienLamBaiTheoMaJPQL(int maBaiThi) throws RemoteException;
    void thongBaoBaiThiMoi(BaiThi baiThi) throws RemoteException;
    void dangKyClientNhanThongBao(BaiThiCallback callback) throws RemoteException;
    void huyDangKyClientNhanThongBao(BaiThiCallback callback) throws RemoteException;
}
