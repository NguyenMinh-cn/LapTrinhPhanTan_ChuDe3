package service;

import entities.CauTraLoi;
import entities.PhienLamBai;

import java.rmi.RemoteException;
import java.util.List;

public interface PhienLamBaiService extends GenericService<PhienLamBai, String> {
    double calculateScore(String maPhienLamBai) throws RemoteException;
    int countCorrectAnswers(String maPhienLamBai) throws RemoteException;
    int countIncorrectAnswers(String maPhienLamBai) throws RemoteException;
    List<CauTraLoi> getCauTraLoiByPhienLamBai(String maPhienLamBai) throws RemoteException;
}