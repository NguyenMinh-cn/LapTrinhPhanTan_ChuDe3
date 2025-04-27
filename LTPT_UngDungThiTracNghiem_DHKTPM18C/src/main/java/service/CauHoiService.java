package service;

import entities.CauHoi;

import java.rmi.RemoteException;

public interface CauHoiService extends GenericService<CauHoi, Integer> {
    boolean inBaiThi(int maCauHoi) throws RemoteException;
    boolean inBaiThiDaDienRa(int maCauHoi) throws RemoteException;
}
