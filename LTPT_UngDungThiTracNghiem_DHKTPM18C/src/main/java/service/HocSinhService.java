package service;

import entities.HocSinh;

import java.rmi.RemoteException;

public interface HocSinhService extends GenericService<HocSinh, Long> {
    public HocSinh timHocSinhTheoEmail(String email) throws RemoteException;
}
