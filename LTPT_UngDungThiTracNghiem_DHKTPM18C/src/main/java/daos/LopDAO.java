package daos;

import entities.Lop;
import entities.MonHoc;
import jakarta.persistence.EntityManager;

import java.util.List;

public class LopDAO extends GenericDAO<Lop, Integer> {
    public LopDAO(Class<Lop> clazz) {
        super(clazz);
    }

    public LopDAO(EntityManager em, Class<Lop> clazz) {
        super(em, clazz);
    }

    public static void main(String[] args) {
        LopDAO lopDAO = new LopDAO(Lop.class);
        List<Lop> dsLop =lopDAO.getAll();
        System.out.println(dsLop);
    }
}
