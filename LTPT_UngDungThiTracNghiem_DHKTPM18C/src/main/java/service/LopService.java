package service;

import entities.Lop;

import java.rmi.RemoteException;

public interface LopService extends GenericService<Lop, Integer>{
    Lop findByTenLop(String tenLop) throws RemoteException;
}
