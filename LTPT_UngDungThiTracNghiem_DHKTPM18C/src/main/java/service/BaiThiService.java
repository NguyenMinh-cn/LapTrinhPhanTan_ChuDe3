package service;

import entities.BaiThi;

import java.rmi.RemoteException;
import java.util.List;

public interface BaiThiService extends GenericService<BaiThi, Integer>{
    public List<BaiThi> timDSBaiTHiTheoMaGiaoVien(int maGiaoVien) throws RemoteException;
}
