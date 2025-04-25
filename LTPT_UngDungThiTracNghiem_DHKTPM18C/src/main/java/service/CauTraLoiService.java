package service;

import entities.CauTraLoi;

import java.rmi.RemoteException;

public interface CauTraLoiService extends GenericService<CauTraLoi, Integer>{
  List<CauTraLoi> getCauTraLoiByPhienLamBai(String maPhienLamBai) throws RemoteException;
}
