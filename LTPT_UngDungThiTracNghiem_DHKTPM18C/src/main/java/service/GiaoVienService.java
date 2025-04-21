package service;

import entities.GiaoVien;

import java.rmi.RemoteException;

public interface GiaoVienService extends GenericService<GiaoVien, Integer> {
    public GiaoVien timGiaoVienTheoEmail(String email) throws RemoteException;
}
