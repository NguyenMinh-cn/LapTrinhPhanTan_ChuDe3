package service;

import entities.BaiThi;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BaiThiCallback extends Remote {
    void nhanThongBaoBaiThiMoi(BaiThi baiThi) throws RemoteException;
}