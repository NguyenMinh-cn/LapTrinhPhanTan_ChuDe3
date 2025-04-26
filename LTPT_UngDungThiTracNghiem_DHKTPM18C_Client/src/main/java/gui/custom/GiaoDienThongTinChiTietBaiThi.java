package gui.custom;

import entities.BaiThi;
import entities.CauHoi;
import entities.PhienLamBai;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;
import service.BaiThiService;
import service.CauHoiService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
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

    /**
     * Khởi tạo giao diện chi tiết bài thi
     * @param baiThi Bài thi cần hiển thị thông tin
     */
    public GiaoDienThongTinChiTietBaiThi(BaiThi baiThi) throws MalformedURLException, NotBoundException, RemoteException {
        this.baiThi = baiThi;
        this.cauHoiService = (CauHoiService) Naming.lookup("rmi://localhost:9090/cauHoiService");
        initUI();
    }

    /**
     * Khởi tạo giao diện người dùng
     */
    private void initUI() throws MalformedURLException, NotBoundException, RemoteException {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 22));

        // Tab 1: Thông tin bài thi + Danh sách câu hỏi
        JPanel thongTinPanel = createThongTinVaCauHoiPanel();
        tabbedPane.addTab("Thông Tin & Câu Hỏi", new JScrollPane(thongTinPanel));

        // Tab 2: Lượt làm bài thi
        JPanel luotLamBaiPanel = createLuotLamBaiPanel();
        tabbedPane.addTab("Lượt Làm Bài Thi", new JScrollPane(luotLamBaiPanel));

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Tạo panel hiển thị thông tin bài thi và danh sách câu hỏi
     */
    private JPanel createThongTinVaCauHoiPanel() throws RemoteException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Panel thông tin bài thi
        JPanel thongTinPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        thongTinPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                "Thông tin bài thi",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(33, 150, 243)
        ));
        thongTinPanel.setBackground(Color.WHITE);

        addLabelPair(thongTinPanel, "Tên bài thi:", baiThi.getTenBaiThi());
        addLabelPair(thongTinPanel, "Môn học:", baiThi.getMonHoc() != null ? baiThi.getMonHoc().getTenMon() : "Không có");
        addLabelPair(thongTinPanel, "Thời gian bắt đầu:", baiThi.getThoiGianBatDau().format(formatter));
        addLabelPair(thongTinPanel, "Thời gian kết thúc:", baiThi.getThoiGianKetThuc().format(formatter));
        addLabelPair(thongTinPanel, "Thời lượng:", baiThi.getThoiLuong() + " phút");
        addLabelPair(thongTinPanel, "Giáo viên:", baiThi.getGiaoVien() != null ? baiThi.getGiaoVien().getHoTen() : "Không có");
        addLabelPair(thongTinPanel, "Mật khẩu bài thi:", baiThi.getMatKhau() != null ? baiThi.getMatKhau() : "Không có");

        panel.add(thongTinPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel danh sách câu hỏi
        JPanel cauHoiPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        cauHoiPanel.setBackground(Color.WHITE);


        cauHoiPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                "Danh sách câu hỏi",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 20),
                new Color(33, 150, 243)
        ));
        cauHoiPanel.setBackground(Color.WHITE);
        List<CauHoi> cauHoiList = new ArrayList<>();
        try {
            cauHoiList = baiThi.getDanhSachCauHoi();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải danh sách câu hỏi: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (cauHoiList != null && !cauHoiList.isEmpty()) {
            int stt = 1;
            for (CauHoi cauHoi : cauHoiList) {
                JPanel cauHoiItemPanel = new JPanel();
                cauHoiItemPanel.setLayout(new BoxLayout(cauHoiItemPanel, BoxLayout.Y_AXIS));
                cauHoiItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Quan trọng
                cauHoiItemPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                        new EmptyBorder(10, 10, 10, 10)
                ));
                cauHoiItemPanel.setBackground(Color.WHITE);

                // Label câu hỏi
                JLabel lblCauHoi = new JLabel("Câu " + stt + ": " + cauHoi.getNoiDung());
                lblCauHoi.setFont(new Font("Arial", Font.BOLD, 20));
                lblCauHoi.setAlignmentX(Component.LEFT_ALIGNMENT); // Quan trọng
                cauHoiItemPanel.add(lblCauHoi);
                cauHoiItemPanel.add(Box.createRigidArea(new Dimension(0, 5)));

                List<String> danhSachDapAn = cauHoi.getDanhSachDapAn();
                String dapAnDung = cauHoi.getDapAnDung();
                if (danhSachDapAn != null) {
                    Font font = new Font("Arial", Font.PLAIN, 18);
                    for (String dapAn : danhSachDapAn) {
                        JLabel lblDapAn = new JLabel("<html><div>" + dapAn.replace("\n", "<br>") + "</div></html>");
                        lblDapAn.setFont(font);
                        lblDapAn.setOpaque(false);
                        lblDapAn.setAlignmentX(Component.LEFT_ALIGNMENT); // Quan trọng
                        lblDapAn.setIcon(
                                dapAn.equals(dapAnDung)
                                        ? FontIcon.of(MaterialDesign.MDI_CHECK_CIRCLE, 16, new Color(76, 175, 80))
                                        : FontIcon.of(MaterialDesign.MDI_CHECKBOX_BLANK_CIRCLE_OUTLINE, 16, Color.GRAY)
                        );
                        cauHoiItemPanel.add(lblDapAn);
                    }
                }

                cauHoiItemPanel.add(Box.createRigidArea(new Dimension(0, 5)));

                cauHoiPanel.add(cauHoiItemPanel);
                stt++;
            }
        } else {
            JLabel lblNoData = new JLabel("Chưa có câu hỏi nào.");
            lblNoData.setFont(new Font("Arial", Font.ITALIC, 18));
            lblNoData.setAlignmentX(Component.CENTER_ALIGNMENT);
            cauHoiPanel.add(lblNoData);
        }

        panel.add(cauHoiPanel);
        return panel;
    }


    /**
     * Thêm cặp label vào panel
     */
    private void addLabelPair(JPanel panel, String labelText, String valueText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel value = new JLabel(valueText);
        value.setFont(new Font("Arial", Font.PLAIN, 20));

        panel.add(label);
        panel.add(value);
    }

    /**
     * Tạo panel hiển thị danh sách lượt làm bài
     */
    private JPanel createLuotLamBaiPanel() {
        JPanel panel = new JPanel(new BorderLayout());
//        panel.setBackground(Color.WHITE);
//        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
//
//        // Tạo model cho bảng
//        String[] columnNames = {"Mã Phiên", "Tên Thí Sinh", "Điểm", "Thời Gian Bắt Đầu", "Thời Gian Kết Thúc"};
//        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false; // Không cho phép chỉnh sửa
//            }
//        };
//
//        // Thêm dữ liệu vào bảng
//        if (baiThi.getDanhSachPhienLamBaiCuaBaiThi() != null) {
//            for (PhienLamBai phien : baiThi.getDanhSachPhienLamBaiCuaBaiThi()) {
//                Object[] row = {
//                        phien.getMaPhien(),
//                        (phien.getHocSinh() != null ? phien.getHocSinh().getHoTen() : "Không xác định"),
//                        phien.getDiem(),
//                        phien.getThoiGianBatDau().format(formatter),
//                        phien.getThoiGianKetThuc().format(formatter)
//                };
//                tableModel.addRow(row);
//            }
//        }
//
//        // Tạo bảng và scroll pane
//        JTable table = new JTable(tableModel);
//        table.setRowHeight(25);
//        table.setFont(new Font("Arial", Font.PLAIN, 20));
//        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
//
//        JScrollPane scrollPane = new JScrollPane(table);
//        scrollPane.setBorder(BorderFactory.createEmptyBorder());
//
//        // Thêm tiêu đề
//        JLabel lblTitle = new JLabel("Danh sách lượt làm bài");
//        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
//        lblTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
//
//        // Thêm thống kê
//        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        statsPanel.setBackground(Color.WHITE);
//        statsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
//
//        int totalAttempts = baiThi.getDanhSachPhienLamBaiCuaBaiThi() != null ?
//                baiThi.getDanhSachPhienLamBaiCuaBaiThi().size() : 0;
//
//        JLabel lblStats = new JLabel("Tổng số lượt làm bài: " + totalAttempts);
//        lblStats.setFont(new Font("Arial", Font.BOLD, 20));
//        statsPanel.add(lblStats);
//
//        // Thêm các thành phần vào panel chính
//        panel.add(lblTitle, BorderLayout.NORTH);
//        panel.add(scrollPane, BorderLayout.CENTER);
//        panel.add(statsPanel, BorderLayout.SOUTH);
//
        return panel;
    }

    /**
     * Phương thức main để test giao diện
     */
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        try {
            // Thiết lập look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Hiển thị giao diện
        BaiThiService baiThiService = (BaiThiService) Naming.lookup("rmi://localhost:9090/baiThiService");
        BaiThi baiThi1 = baiThiService.layThongTinChiTietBaiThi(3);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chi tiết bài thi");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            try {
                frame.setContentPane(new GiaoDienThongTinChiTietBaiThi(baiThi1));
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "Lỗi khi tải giao diện: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            frame.setVisible(true);
        });
    }
}
