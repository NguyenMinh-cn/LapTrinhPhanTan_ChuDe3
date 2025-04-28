package service;

import entities.MonHoc;

import java.rmi.RemoteException;

public interface MonHocService extends GenericService<MonHoc, Integer> {
    public boolean update(MonHoc monHoc) throws RemoteException;
    public boolean delete(int maMon) throws RemoteException;
    MonHoc findByTenMon(String tenMon) throws RemoteException;
}

