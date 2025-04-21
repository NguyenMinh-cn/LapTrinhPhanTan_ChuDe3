package service.impl;

import daos.GiaoVienDAO;
import entities.GiaoVien;
import service.GiaoVienService;

import java.rmi.RemoteException;

public class GiaoVienServiceImpl extends GenericServiceImpl<GiaoVien, Integer> implements GiaoVienService {
    private GiaoVienDAO giaoVienDAO;

    public GiaoVienServiceImpl(GiaoVienDAO giaoVienDAO) throws RemoteException {
        super(giaoVienDAO);
        this.giaoVienDAO = giaoVienDAO;
    }

    @Override
    public GiaoVien timGiaoVienTheoEmail(String email) throws RemoteException {
        return giaoVienDAO.timGiaoVienTheoEmail(email);
    }
}
