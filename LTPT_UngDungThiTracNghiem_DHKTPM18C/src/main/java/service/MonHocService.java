package service;

import entities.MonHoc;

import java.rmi.RemoteException;

public interface MonHocService extends GenericService<MonHoc, Integer> {
    MonHoc findByTenMon(String tenMon) throws RemoteException;
}
