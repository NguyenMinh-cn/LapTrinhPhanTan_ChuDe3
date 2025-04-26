package service;

import entities.ChuDe;

import java.rmi.RemoteException;
import java.util.List;

public interface ChuDeService extends GenericService<ChuDe, Integer> {
    ChuDe findByTenMonHocAndTenChuDe(String tenMon, String tenChuDe) throws RemoteException;
    List<ChuDe> findByTenMonHoc(String tenMon) throws RemoteException;
    boolean hasCauHoi(int maChuDe) throws RemoteException;
    boolean isDuplicate(String tenChuDe, String tenMon) throws RemoteException;
}
