package test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import entities.CauHoi;

public class InsertCauHoi {
    public void themDSCauHoi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb");
        EntityManager em = emf.createEntityManager();

        CauHoi[] cauHois = {
                new CauHoi("Điểm cực Nam phần đất liền nước ta thuộc tỉnh/thành nào dưới đây?",
                        "Kiên Giang", "Cà Mau", "An Giang", "Bạc Liêu", "Cà Mau"),
                new CauHoi("Đường bờ biển của nước ta dài 3 260 km, chạy từ",
                        "Quảng Ninh đến Cà Mau", "Lạng Sơn đến Cà Mau", "Quảng Ninh đến Kiên Giang", "Lạng Sơn đến Kiên Giang", "Quảng Ninh đến Cà Mau"),
                new CauHoi("Nước ta có hơn 4 600km đường biên giới trên đất liền, giáp với các quốc gia nào sau đây?",
                        "Trung Quốc, Mianma, Lào", "Trung Quốc, Lào, Cam-pu-chia", "Trung Quốc, Lào, Thái Lan", "Lào, Cam-pu-chia, Thái Lan", "Trung Quốc, Lào, Cam-pu-chia"),
                new CauHoi("Vị trí địa lí của nước ta không có ý nghĩa nào sau đây?",
                        "Vị trí địa lí đặc biệt quan trọng ở vùng Đông Nam Á", "Tạo điều kiện thực hiện chính sách mở cửa, hội nhập",
                        "Chung sống hòa bình, hợp tác hữu nghị với các nước", "Tranh chấp Biển Đông và ranh giới với Trung Quốc", "Tranh chấp Biển Đông và ranh giới với Trung Quốc"),
                new CauHoi("Điểm cực Tây phần đất liền nước ta thuộc tỉnh/thành nào sau đây?",
                        "Điện Biên", "Cao Bằng", "Hà Giang", "Lạng Sơn", "Điện Biên"),
                new CauHoi("Điểm cực Bắc phần đất liền nước ta thuộc tỉnh/thành nào sau đây?",
                        "Lào Cai", "Cao Bằng", "Hà Giang", "Lạng Sơn", "Hà Giang"),
                new CauHoi("Trên đất liền, nước ta không có chung biên giới với quốc gia nào sau đây?",
                        "Lào", "Thái Lan", "Trung Quốc", "Cam-pu-chia", "Thái Lan"),
                new CauHoi("Nhờ có biển Đông mà nước ta có",
                        "thiên nhiên chịu ảnh hưởng sâu sắc của biển", "thiên nhiên nhiệt đới gió mùa, mùa đông lạnh",
                        "thiên nhiên phân hóa đa dạng theo bắc - nam", "khí hậu khô và nóng như các nước ở châu Phi", "thiên nhiên chịu ảnh hưởng sâu sắc của biển"),
                new CauHoi("Huyện đảo Hoàng Sa trực thuộc tỉnh/thành nào dưới đây?",
                        "Tỉnh Quảng Trị", "Thành phố Đà Nẵng", "Tỉnh Khánh Hòa", "Tỉnh Quảng Ngãi", "Thành phố Đà Nẵng"),
                new CauHoi("Vị trí địa lí của nước ta",
                        "nằm hoàn toàn ở khu vực ngoại chí tuyến", "gần trung tâm của khu vực Tây Nam Á", "rìa phía Đông của bán đảo Đông Dương", "giáp với Biển Đông và Đại Bình Dương", "rìa phía Đông của bán đảo Đông Dương"),
                new CauHoi("Lãnh thổ Việt Nam là một khối thống nhất toàn vẹn, bao gồm",
                        "vùng núi, đồng bằng, vùng biển", "vùng đất, hải đảo, thềm lục địa", "vùng núi cao, núi thấp, ven biển", "vùng đất, vùng trời, vùng biển", "vùng đất, hải đảo, thềm lục địa"),
                new CauHoi("Vị trí địa lí đã quy định đặc điểm cơ bản nào của thiên nhiên nước ta?",
                        "Chịu ảnh hưởng sâu sắc từ biển", "Thảm thực vật bốn màu xanh tốt", "Khí hậu thất thường, phân mùa", "Tính chất nhiệt đới ẩm gió mùa", "Tính chất nhiệt đới ẩm gió mùa"),
                new CauHoi("Điểm cực Nam phần đất liền nước ta thuộc tỉnh/thành nào sau đây?",
                        "Long An", "Kiên Giang", "Cà Mau", "An Giang", "Cà Mau"),
                new CauHoi("Nước ta giàu có về tài nguyên khoáng sản là do",
                        "tiếp giáp với đường hàng hải, hàng không quốc tế", "nằm trên vành đai sinh khoáng Thái Bình Dương",
                        "nằm trên vành đai lửa Thái Bình Dương, nhiều mỏ", "trên đường di cư và di lưu của nhiều động thực vật", "nằm trên vành đai sinh khoáng Thái Bình Dương"),
                new CauHoi("Các nước xếp theo thứ tự giảm dần về độ dài đường biên giới trên đất liền với nước ta là",
                        "Trung Quốc, Lào, Cam-pu-chia", "Trung Quốc, Cam-pu-chia, Lào", "Lào, Cam-pu-chia, Trung Quốc", "Lào, Trung Quốc, Cam-pu-chia", "Trung Quốc, Lào, Cam-pu-chia")
        };

        try {
            em.getTransaction().begin();

            for (CauHoi cauHoi : cauHois) {
                em.persist(cauHoi);
            }

            em.getTransaction().commit();
            System.out.println("Dữ liệu đã được ghi thành công!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
