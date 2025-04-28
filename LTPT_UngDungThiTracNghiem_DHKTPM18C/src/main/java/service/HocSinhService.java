package service;

import entities.HocSinh;
import entities.MonHoc;

import java.rmi.RemoteException;
import java.util.List;

public interface HocSinhService extends GenericService<HocSinh, Integer> {
    public HocSinh timHocSinhTheoEmail(String email) throws RemoteException;
    List<HocSinh> getAll() throws RemoteException;
    public boolean update(HocSinh hocSinh) throws RemoteException;
    public boolean delete(int maHocSinh) throws RemoteException;
}
