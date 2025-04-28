package service;

import entities.CauHoi;

import java.rmi.RemoteException;
import java.util.List;

public interface CauHoiService extends GenericService<CauHoi, Integer> {
    boolean inBaiThi(int maCauHoi) throws RemoteException;
    boolean inBaiThiDaDienRa(int maCauHoi) throws RemoteException;

    List<CauHoi> getCauHoiCoChuDe() throws RemoteException;
}
