package service;

import entities.CauHoi;

import java.rmi.RemoteException;
import java.util.List;

public interface CauHoiService extends GenericService<CauHoi, Integer>{
    public List<CauHoi> timCauHoiTheoMaBaiThi(int maBaiThi) throws RemoteException;
    public List<String> timDSDapAnTheoCauHoi(int maCH) throws RemoteException;
    CauHoi luuVaTraVeMa(CauHoi cauHoi) throws RemoteException;
    public List<CauHoi> luuNhieuVaTraVeMa(List<CauHoi> danhSachCauHoi) throws RemoteException;
    boolean inBaiThi(int maCauHoi) throws RemoteException;
    boolean inBaiThiDaDienRa(int maCauHoi) throws RemoteException;
    List<CauHoi> getCauHoiCoChuDe() throws RemoteException;
}
