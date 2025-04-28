package service;

import entities.Lop;

import java.rmi.RemoteException;
import java.util.List;

public interface LopService extends GenericService<Lop, Integer>{
    List<Lop> getAllLop() throws RemoteException;

}