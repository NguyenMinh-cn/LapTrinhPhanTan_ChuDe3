package daos;

import entities.TaiKhoan;
import jakarta.persistence.EntityManager;

public class TaiKhoanDAO extends GenericDAO<TaiKhoan, String> {
    public TaiKhoanDAO(Class<TaiKhoan> clazz) {
        super(clazz);
    }

    public TaiKhoanDAO(EntityManager em, Class<TaiKhoan> clazz) {
        super(em, clazz);
    }
}
