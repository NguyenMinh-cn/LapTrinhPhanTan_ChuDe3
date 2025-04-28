package service;

import entities.HocSinh;

import java.rmi.RemoteException;
import java.util.List;

public interface HocSinhService extends GenericService<HocSinh, Long> {
    public HocSinh timHocSinhTheoEmail(String email) throws RemoteException;
    List<HocSinh> findHocSinhChuaCoLop() throws RemoteException;
}
