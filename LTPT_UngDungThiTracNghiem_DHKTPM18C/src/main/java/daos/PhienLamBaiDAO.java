package daos;

import entities.CauHoi;
import entities.CauTraLoi;
import entities.PhienLamBai;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class PhienLamBaiDAO extends GenericDAO<PhienLamBai, String> {
    public PhienLamBaiDAO(Class<PhienLamBai> clazz) {
        super(clazz);
    }

    public PhienLamBaiDAO(EntityManager em, Class<PhienLamBai> clazz) {
        super(em, clazz);
    }
    public PhienLamBai layThongTinPhienLamBaiVaCauTraLoi(String maPhienLamBai) {
        try {
            String jpql = "SELECT plb FROM PhienLamBai plb " +
                    "LEFT JOIN FETCH plb.baiThi bt " +
                    "LEFT JOIN FETCH plb.danhSachCauTraLoi ctl " +
                    "WHERE plb.maPhien = :maPhienLamBai";

            TypedQuery<PhienLamBai> query = em.createQuery(jpql, PhienLamBai.class);
            query.setParameter("maPhienLamBai", maPhienLamBai);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<PhienLamBai> layDanhSachPhienLamBaiVaCauTraLoiTheoBaiThi(int maBaiThi) {
        try {
            String jpql = "SELECT DISTINCT plb FROM PhienLamBai plb " +
                    "LEFT JOIN FETCH plb.baiThi bt " +
                    "LEFT JOIN FETCH plb.danhSachCauTraLoi ctl " +
                    "WHERE bt.maBaiThi = :maBaiThi";

            TypedQuery<PhienLamBai> query = em.createQuery(jpql, PhienLamBai.class);
            query.setParameter("maBaiThi", maBaiThi);

            List<PhienLamBai> danhSach = query.getResultList();

            for (PhienLamBai plb : danhSach) {
                plb.getDanhSachCauTraLoi().size();
            }

            return danhSach;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public PhienLamBai layThongTinChiTietPhienLamBai(String maPhienLamBai) {
        try {
            String jpql = "SELECT DISTINCT plb FROM PhienLamBai plb " +
                    "LEFT JOIN FETCH plb.baiThi bt " +
                    "LEFT JOIN FETCH bt.danhSachCauHoi ch " +
                    "LEFT JOIN FETCH plb.danhSachCauTraLoi ctl " +
                    "WHERE plb.maPhien = :maPhienLamBai";

            PhienLamBai phienLamBai = em.createQuery(jpql, PhienLamBai.class)
                    .setParameter("maPhienLamBai", maPhienLamBai)
                    .getSingleResult();

            for (CauHoi ch : phienLamBai.getBaiThi().getDanhSachCauHoi()) {
                ch.getDanhSachDapAn().size();
            }

            return phienLamBai;
        } catch (NoResultException e) {
            System.out.println("Không tìm thấy phiên làm bài với mã " + maPhienLamBai);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object[]> layKetQuaChiTietPhienLamBai(String maPhienLamBai) {
        List<Object[]> ketQua = new ArrayList<>();
        try {
            PhienLamBai phienLamBai = layThongTinChiTietPhienLamBai(maPhienLamBai);
            if (phienLamBai == null) {
                return ketQua;
            }

            List<CauTraLoi> danhSachCauTraLoi = phienLamBai.getDanhSachCauTraLoi();
            List<CauHoi> danhSachCauHoi = phienLamBai.getBaiThi().getDanhSachCauHoi();

            for (CauTraLoi cauTraLoi : danhSachCauTraLoi) {
                String noiDungCauHoi = cauTraLoi.getNoiDungCauHoi();
                String dapAnChon = cauTraLoi.getDapAnDaChon() != null ? cauTraLoi.getDapAnDaChon() : "";
                String ketQuaCauHoi = cauTraLoi.isKetQua() ? "Đúng" : "Sai";

                String dapAnDung = "";
                for (CauHoi cauHoi : danhSachCauHoi) {
                    // Kiểm tra xem danhSachDapAn của CauTraLoi có khớp với các đáp án của CauHoi không
                    List<String> dapAnList = cauHoi.getDanhSachDapAn();
                    if (dapAnList != null && dapAnList.contains(cauHoi.getDapAnDung())) {
                        dapAnDung = cauHoi.getDapAnDung();
                        break;
                    }
                }

                ketQua.add(new Object[]{noiDungCauHoi, dapAnChon, dapAnDung, ketQuaCauHoi});
            }

            return ketQua;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Object[] tinhDiemVaSoCau(String maPhienLamBai) {
        try {
            PhienLamBai phienLamBai = layThongTinChiTietPhienLamBai(maPhienLamBai);
            if (phienLamBai == null) {
                return new Object[]{0, 0, 0};
            }

            int soCauDung = 0;
            int soCauSai = 0;
            List<CauTraLoi> danhSachCauTraLoi = phienLamBai.getDanhSachCauTraLoi();

            for (CauTraLoi cauTraLoi : danhSachCauTraLoi) {
                if (cauTraLoi.isKetQua()) {
                    soCauDung++;
                } else {
                    soCauSai++;
                }
            }

            int tongSoCau = soCauDung + soCauSai;
            double diemSo = (tongSoCau > 0) ? ((double) soCauDung / tongSoCau) * 100 : 0;
            return new Object[]{Math.round(diemSo), soCauDung, soCauSai};
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[]{0, 0, 0};
        }
    }

   public List<PhienLamBai> findByMaHocSinh(long maHocSinh) {
    try {
        String jpql = "SELECT plb FROM PhienLamBai plb WHERE plb.hocSinh.maHocSinh = :maHocSinh";
        return em.createQuery(jpql, PhienLamBai.class)
                .setParameter("maHocSinh", maHocSinh)
                .getResultList();
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>();
    }
}
}
