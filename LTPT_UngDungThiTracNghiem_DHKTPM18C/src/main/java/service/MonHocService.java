package service;

import entities.MonHoc;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MonHocService extends Remote {
    boolean save(MonHoc monHoc) throws RemoteException;

    boolean update(MonHoc monHoc) throws RemoteException;

    boolean delete(int maMon) throws RemoteException;

    List<MonHoc> getAllMonHoc() throws RemoteException;

    MonHoc findById(int maMon) throws RemoteException;
}

