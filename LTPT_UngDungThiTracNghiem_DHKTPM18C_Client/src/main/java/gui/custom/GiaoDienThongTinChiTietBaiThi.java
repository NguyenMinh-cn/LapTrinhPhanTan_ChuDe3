package gui.custom;

import entities.BaiThi;
import entities.CauHoi;
import entities.Lop;
import entities.PhienLamBai;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;
import service.BaiThiService;
import service.CauHoiService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Giao diện hiển thị thông tin chi tiết của một bài thi
 */
public class GiaoDienThongTinChiTietBaiThi extends JPanel {
    private BaiThi baiThi;
    private CauHoiService cauHoiService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy");
    private BaiThiService baiThiService = (BaiThiService) Naming.lookup("rmi://localhost:8081/baiThiService");

    public GiaoDienThongTinChiTietBaiThi(BaiThi baiThi) throws MalformedURLException, NotBoundException, RemoteException {
        // Kiểm tra nếu baiThi là null
        if (baiThi == null) {
            JOptionPane.showMessageDialog(this,
                "Không thể hiển thị thông tin chi tiết bài thi vì bài thi không tồn tại.",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            this.baiThi = null;
            this.cauHoiService = null;
            return;
        }

        try {
            BaiThi baiThi1 = baiThiService.layThongTinBaiThiVaCauHoi(baiThi.getMaBaiThi());
            List<Lop> danhSachLop = baiThiService.timLopTheoBaiThi(baiThi.getMaBaiThi());

            System.out.println("Số lớp liên quan tới bài thi " + baiThi.getMaBaiThi() + ": " + danhSachLop.size());
            for (Lop lop : danhSachLop) {
                System.out.println("Lớp: " + lop.getTenLop()); // In tên lớp (giả sử Lop có phương thức getTenLop)
            }
            baiThi1.setDanhSachLop(danhSachLop);
            this.baiThi = baiThi1;
            if (this.baiThi == null) {
                JOptionPane.showMessageDialog(this,
                    "Không thể lấy thông tin chi tiết bài thi từ server. Sử dụng thông tin cơ bản.",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                this.baiThi = baiThi;
            }
        } catch (Exception e) {
            // Nếu không lấy được thông tin đầy đủ, sử dụng bài thi được truyền vào
            this.baiThi = baiThi;
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi lấy thông tin chi tiết bài thi: " + e.getMessage() +
                "\nSử dụng thông tin cơ bản.",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }

        try {
            this.cauHoiService = (CauHoiService) Naming.lookup("rmi://localhost:8081/cauHoiService");
            initUI();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi kết nối đến dịch vụ câu hỏi: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initUI() throws MalformedURLException, NotBoundException, RemoteException {
        // Kiểm tra nếu baiThi hoặc cauHoiService là null
        if (baiThi == null || cauHoiService == null) {
            setLayout(new BorderLayout());
            JLabel lblError = new JLabel("Không thể hiển thị thông tin chi tiết bài thi.", JLabel.CENTER);
            lblError.setFont(new Font("Arial", Font.BOLD, 16));
            lblError.setForeground(Color.RED);
            add(lblError, BorderLayout.CENTER);
            return;
        }

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 22));

        try {
            // Tab 1: Thông tin bài thi + Danh sách câu hỏi
            JPanel thongTinPanel = createThongTinVaCauHoiPanel();
            tabbedPane.addTab("Thông Tin & Câu Hỏi", new JScrollPane(thongTinPanel));

            // Tab 2: Lượt làm bài thi
            JPanel luotLamBaiPanel = createLuotLamBaiPanel();
            tabbedPane.addTab("Lượt Làm Bài Thi", new JScrollPane(luotLamBaiPanel));

            add(tabbedPane, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
            JLabel lblError = new JLabel("Lỗi khi tạo giao diện: " + e.getMessage(), JLabel.CENTER);
            lblError.setFont(new Font("Arial", Font.BOLD, 16));
            lblError.setForeground(Color.RED);
            add(lblError, BorderLayout.CENTER);
        }
    }

    private JPanel createThongTinVaCauHoiPanel() throws RemoteException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Kiểm tra nếu baiThi là null
        if (baiThi == null) {
            JLabel lblError = new JLabel("Không thể hiển thị thông tin chi tiết bài thi.", JLabel.CENTER);
            lblError.setFont(new Font("Arial", Font.BOLD, 16));
            lblError.setForeground(Color.RED);
            panel.add(lblError);
            return panel;
        }

        // Thông tin bài thi
        JPanel thongTinPanel = new JPanel();
        thongTinPanel.setLayout(new BoxLayout(thongTinPanel, BoxLayout.Y_AXIS));
        thongTinPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 0),
                "Thông tin bài thi",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                new Color(33, 150, 243)
        ));
        thongTinPanel.setBackground(Color.WHITE);

        try {
            addLabel(thongTinPanel, "Tên bài thi: " + baiThi.getTenBaiThi());
            addLabel(thongTinPanel, "Môn học: " + (baiThi.getMonHoc() != null ? baiThi.getMonHoc().getTenMon() : "Không có"));
            addLabel(thongTinPanel, "Thời gian bắt đầu: " + (baiThi.getThoiGianBatDau() != null ? baiThi.getThoiGianBatDau().format(formatter) : "Không có"));
            addLabel(thongTinPanel, "Thời gian kết thúc: " + (baiThi.getThoiGianKetThuc() != null ? baiThi.getThoiGianKetThuc().format(formatter) : "Không có"));
            addLabel(thongTinPanel, "Thời lượng: " + baiThi.getThoiLuong() + " phút");
            addLabel(thongTinPanel, "Giáo viên: " + (baiThi.getGiaoVien() != null ? baiThi.getGiaoVien().getHoTen() : "Không có"));
            addLabel(thongTinPanel, "Mật khẩu bài thi: " + (baiThi.getMatKhau() != null ? baiThi.getMatKhau() : "Không có"));
        } catch (Exception e) {
            e.printStackTrace();
            addLabel(thongTinPanel, "Lỗi khi hiển thị thông tin bài thi: " + e.getMessage());
        }
        StringBuilder lopApDungBuilder = new StringBuilder();

        try {
            if (baiThi != null && baiThi.getDanhSachLop() != null) {
                List<Lop> danhSachLop = baiThi.getDanhSachLop();
                for (int i = 0; i < danhSachLop.size(); i++) {
                    if (i > 0) {
                        lopApDungBuilder.append(", ");
                    }
                    lopApDungBuilder.append(danhSachLop.get(i).getTenLop());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lopApDungBuilder.append("Không thể hiển thị danh sách lớp");
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải danh sách lớp: " + e.getMessage() +
                "\nVui lòng đảm bảo rằng bạn đã sử dụng phương thức layThongTinChiTietBaiThi để lấy bài thi.",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        String lopApDung = lopApDungBuilder.length() > 0 ? lopApDungBuilder.toString() : "Không có";
        addLabel(thongTinPanel, "Lớp áp dụng kiểm tra: " + lopApDung);
        addLabel(thongTinPanel, "Số lần làm bài kiểm tra: " + (baiThi.getSoLanDuocPhepLamBai() > 0 ? baiThi.getSoLanDuocPhepLamBai() : "Không giới hạn"));
        panel.add(thongTinPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Danh sách câu hỏi
        JPanel cauHoiPanel = new JPanel();
        cauHoiPanel.setLayout(new BoxLayout(cauHoiPanel, BoxLayout.Y_AXIS));
        cauHoiPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                "Danh sách câu hỏi",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                new Color(33, 150, 243)
        ));
        cauHoiPanel.setBackground(Color.WHITE);

        List<CauHoi> cauHoiList = new ArrayList<>();
        try {
            cauHoiList = baiThi.getDanhSachCauHoi();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách câu hỏi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        if (cauHoiList != null && !cauHoiList.isEmpty()) {
            int stt = 1;
            for (CauHoi cauHoi : cauHoiList) {
                JPanel cauHoiItemPanel = new JPanel();
                cauHoiItemPanel.setLayout(new BoxLayout(cauHoiItemPanel, BoxLayout.Y_AXIS));
                cauHoiItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                cauHoiItemPanel.setBackground(Color.WHITE);
                cauHoiItemPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

                JTextArea lblCauHoi = new JTextArea("Câu " + stt + ": " + cauHoi.getNoiDung());
                lblCauHoi.setFont(new Font("Arial", Font.BOLD, 18));
                lblCauHoi.setWrapStyleWord(true);
                lblCauHoi.setLineWrap(true);
                lblCauHoi.setEditable(false);
                lblCauHoi.setFocusable(false);
                lblCauHoi.setOpaque(false);
                lblCauHoi.setAlignmentX(Component.LEFT_ALIGNMENT);
                cauHoiItemPanel.add(lblCauHoi);
                cauHoiItemPanel.add(Box.createRigidArea(new Dimension(0, 5)));

                List<String> danhSachDapAn = cauHoi.getDanhSachDapAn();
                String dapAnDung = cauHoi.getDapAnDung();
                if (danhSachDapAn != null) {
                    for (String dapAn : danhSachDapAn) {
                        JPanel dapAnPanel = new JPanel();
                        dapAnPanel.setLayout(new BoxLayout(dapAnPanel, BoxLayout.X_AXIS));
                        dapAnPanel.setOpaque(false);
                        dapAnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                        JLabel iconLabel = new JLabel();
                        iconLabel.setIcon(
                                dapAn.equals(dapAnDung)
                                        ? FontIcon.of(MaterialDesign.MDI_CHECK_CIRCLE, 16, new Color(76, 175, 80))
                                        : FontIcon.of(MaterialDesign.MDI_CHECKBOX_BLANK_CIRCLE_OUTLINE, 16, Color.GRAY)
                        );

                        JTextArea lblDapAn = new JTextArea(dapAn);
                        lblDapAn.setFont(new Font("Arial", Font.PLAIN, 16));
                        lblDapAn.setWrapStyleWord(true);
                        lblDapAn.setLineWrap(true);
                        lblDapAn.setEditable(false);
                        lblDapAn.setFocusable(false);
                        lblDapAn.setOpaque(false);
                        lblDapAn.setAlignmentX(Component.LEFT_ALIGNMENT);

                        dapAnPanel.add(iconLabel);
                        dapAnPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                        dapAnPanel.add(lblDapAn);

                        cauHoiItemPanel.add(dapAnPanel);
                    }
                }

                cauHoiPanel.add(cauHoiItemPanel);
                cauHoiPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                stt++;
            }
        } else {
            JLabel lblNoData = new JLabel("Chưa có câu hỏi nào.");
            lblNoData.setFont(new Font("Arial", Font.ITALIC, 16));
            lblNoData.setAlignmentX(Component.CENTER_ALIGNMENT);
            cauHoiPanel.add(lblNoData);
        }

        panel.add(cauHoiPanel);

        return panel;
    }

    private void addLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private JPanel createLuotLamBaiPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Kiểm tra nếu baiThi là null
        if (baiThi == null) {
            JLabel lblError = new JLabel("Không thể hiển thị thông tin lượt làm bài.", JLabel.CENTER);
            lblError.setFont(new Font("Arial", Font.BOLD, 16));
            lblError.setForeground(Color.RED);
            panel.add(lblError);
            return panel;
        }

        try {
            // Hiển thị thông tin lượt làm bài (có thể thêm code ở đây sau này)
            JLabel lblInfo = new JLabel("Chức năng này đang được phát triển.", JLabel.CENTER);
            lblInfo.setFont(new Font("Arial", Font.ITALIC, 16));
            lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lblInfo);
        } catch (Exception e) {
            e.printStackTrace();
            JLabel lblError = new JLabel("Lỗi khi hiển thị thông tin lượt làm bài: " + e.getMessage(), JLabel.CENTER);
            lblError.setFont(new Font("Arial", Font.BOLD, 16));
            lblError.setForeground(Color.RED);
            panel.add(lblError);
        }

        return panel;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BaiThiService baiThiService = (BaiThiService) Naming.lookup("rmi://localhost:8081/baiThiService");
            BaiThi baiThi1 = baiThiService.layThongTinChiTietBaiThi(10);

            if (baiThi1 == null) {
                JOptionPane.showMessageDialog(null,
                    "Không tìm thấy bài thi với mã 9.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            final BaiThi finalBaiThi = baiThi1; // Create final copy for lambda

            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Chi tiết bài thi");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 700);
                frame.setLocationRelativeTo(null);

                try {
                    frame.setContentPane(new GiaoDienThongTinChiTietBaiThi(finalBaiThi));
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(frame,
                        "Lỗi khi tạo giao diện: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

                frame.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Lỗi khi kết nối đến server hoặc lấy thông tin bài thi: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
