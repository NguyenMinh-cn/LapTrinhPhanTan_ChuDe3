package service;

import entities.HocSinh;

import java.rmi.RemoteException;

public interface HocSinhService extends GenericService<HocSinh, Integer> {
    public HocSinh timHocSinhTheoEmail(String email) throws RemoteException;
}
