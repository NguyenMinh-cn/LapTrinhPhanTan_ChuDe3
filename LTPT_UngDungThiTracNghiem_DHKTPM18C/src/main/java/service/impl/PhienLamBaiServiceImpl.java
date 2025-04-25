package service.impl;

import daos.PhienLamBaiDAO;
import entities.CauTraLoi;
import entities.PhienLamBai;
import service.PhienLamBaiService;

import java.rmi.RemoteException;
import java.util.List;

public class PhienLamBaiServiceImpl extends GenericServiceImpl<PhienLamBai, String> implements PhienLamBaiService {
    private PhienLamBaiDAO phienLamBaiDAO;

    public PhienLamBaiServiceImpl(PhienLamBaiDAO phienLamBaiDAO) throws RemoteException {
        super(phienLamBaiDAO);
        this.phienLamBaiDAO = phienLamBaiDAO;
    }

    @Override
    public double calculateScore(String maPhienLamBai) throws RemoteException {
        try {
            return phienLamBaiDAO.calculateScore(maPhienLamBai);
        } catch (Exception e) {
            throw new RemoteException("Lỗi khi tính điểm: " + e.getMessage(), e);
        }
    }

    @Override
    public int countCorrectAnswers(String maPhienLamBai) throws RemoteException {
        try {
            return phienLamBaiDAO.countCorrectAnswers(maPhienLamBai);
        } catch (Exception e) {
            throw new RemoteException("Lỗi khi đếm số câu đúng: " + e.getMessage(), e);
        }
    }

    @Override
    public int countIncorrectAnswers(String maPhienLamBai) throws RemoteException {
        try {
            return phienLamBaiDAO.countIncorrectAnswers(maPhienLamBai);
        } catch (Exception e) {
            throw new RemoteException("Lỗi khi đếm số câu sai: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CauTraLoi> getCauTraLoiByPhienLamBai(String maPhienLamBai) throws RemoteException {
        try {
            return phienLamBaiDAO.getCauTraLoiByPhienLamBai(maPhienLamBai);
        } catch (Exception e) {
            throw new RemoteException("Lỗi khi lấy danh sách câu trả lời: " + e.getMessage(), e);
        }
    }
}