package gui.custom;

import daos.PhienLamBaiDAO;
import entities.*;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;
import service.BaiThiService;
import service.CauHoiService;
import service.PhienLamBaiService;

import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Giao diện hiển thị thông tin chi tiết của một bài thi
 */
public class GiaoDienThongTinChiTietBaiThi extends JPanel {
    private String ipAddress = "localhost";
    private BaiThi baiThi;
    private CauHoiService cauHoiService = (CauHoiService) Naming.lookup("rmi://" + ipAddress + ":8081/cauHoiService");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy");
    private BaiThiService baiThiService = (BaiThiService) Naming.lookup("rmi://" + ipAddress + ":8081/baiThiService");
    private PhienLamBaiService phienLamBaiService = (PhienLamBaiService) Naming.lookup("rmi://" + ipAddress + ":8081/phienLamBaiService");

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
        // Tạo panel chính để chứa bảng, sử dụng BorderLayout để căn chỉnh dễ dàng
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Thêm khoảng cách viền 20px
        panel.setBackground(Color.WHITE); // Nền trắng cho panel

        // Kiểm tra nếu bài thi (baiThi) bị null
        if (baiThi == null) {
            // Hiển thị thông báo lỗi nếu không có bài thi
            JLabel lblError = new JLabel("Không thể hiển thị thông tin lượt làm bài.", JLabel.CENTER);
            lblError.setFont(new Font("Arial", Font.BOLD, 16)); // Font chữ Arial, đậm, cỡ 16
            lblError.setForeground(Color.RED); // Chữ màu đỏ
            panel.add(lblError, BorderLayout.CENTER); // Đặt thông báo ở giữa panel
            return panel;
        }

        try {
            // Lấy danh sách lượt làm bài của bài thi qua PhienLamBaiService
            List<PhienLamBai> phienLamBaiList = phienLamBaiService.layDanhSachPhienLamBaiVaCauTraLoiTheoBaiThi(baiThi.getMaBaiThi());

            // Kiểm tra nếu danh sách lượt làm bài rỗng hoặc null
            if (phienLamBaiList == null || phienLamBaiList.isEmpty()) {
                // Hiển thị thông báo nếu chưa có lượt làm bài nào
                JLabel lblNoData = new JLabel("Chưa có lượt làm bài nào.", JLabel.CENTER);
                lblNoData.setFont(new Font("Arial", Font.ITALIC, 20)); // Font Arial, nghiêng, cỡ 20
                lblNoData.setForeground(new Color(108, 117, 125)); // Màu xám nhạt
                panel.add(lblNoData, BorderLayout.CENTER); // Đặt thông báo ở giữa
                return panel;
            }

            // Nhóm các lượt làm bài theo mã học sinh để tính số lần làm bài
            Map<Long, List<PhienLamBai>> attemptsByStudent = new HashMap<>();
            for (PhienLamBai plb : phienLamBaiList) {
                if (plb.getHocSinh() != null) {
                    // Lấy mã học sinh từ đối tượng PhienLamBai
                    long maHocSinh = plb.getHocSinh().getMaHocSinh();
                    // Thêm lượt làm bài vào danh sách của học sinh, nếu chưa có thì tạo mới
                    attemptsByStudent.computeIfAbsent(maHocSinh, k -> new ArrayList<>()).add(plb);
                }
            }

            // Chuẩn bị dữ liệu cho bảng
            List<Object[]> tableData = new ArrayList<>();
            for (Map.Entry<Long, List<PhienLamBai>> entry : attemptsByStudent.entrySet()) {
                // Lấy danh sách lượt làm bài của một học sinh
                List<PhienLamBai> studentAttempts = entry.getValue();
                // Lấy thông tin học sinh từ lượt làm bài đầu tiên
                HocSinh hocSinh = studentAttempts.get(0).getHocSinh();
                // Lấy tên học sinh, nếu null thì hiển thị "Không xác định"
                String studentName = hocSinh.getHoTen() != null ? hocSinh.getHoTen() : "Không xác định";

                // Tính số lần làm bài của học sinh
                int attemptCount = studentAttempts.size();

                // Thêm từng lượt làm bài vào dữ liệu bảng
                for (int i = 0; i < studentAttempts.size(); i++) {
                    PhienLamBai plb = studentAttempts.get(i);
                    // Lấy điểm từ PhienLamBai
                    double score = plb.getDiem();

                    // Thêm một hàng dữ liệu vào bảng
                    tableData.add(new Object[]{
                            studentName, // Tên học sinh
                            (i + 1), // Số thứ tự lượt làm (1, 2, ...)
                            String.format("%.2f", score), // Điểm, định dạng 2 chữ số thập phân
                            plb.getThoiGianBatDau() != null ? plb.getThoiGianBatDau().format(formatter) : "Không có", // Thời gian bắt đầu
                            plb.getThoiGianKetThuc() != null ? plb.getThoiGianKetThuc().format(formatter) : "Không có", // Thời gian kết thúc
                            attemptCount // Số lần làm bài
                    });
                }
            }

            // Định nghĩa các cột của bảng
            String[] columnNames = {
                    "Học Sinh",
                    "Lượt Làm",
                    "Điểm",
                    "Thời Gian Bắt Đầu",
                    "Thời Gian Kết Thúc",
                    "Số Lần Làm"
            };

            // Tạo mô hình bảng với dữ liệu và tiêu đề cột
            DefaultTableModel tableModel = new DefaultTableModel(tableData.toArray(new Object[0][]), columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Không cho phép chỉnh sửa bảng
                }
            };

            // Tạo bảng JTable
            JTable table = new JTable(tableModel);
            table.setBackground(Color.WHITE);
            table.setFont(new Font("Arial", Font.PLAIN, 20)); // Font Arial, thường, cỡ 20
            table.setRowHeight(30); // Chiều cao hàng 30px cho dễ đọc

            table.setGridColor(new Color(200, 200, 200)); // Đường viền bảng màu xám nhạt
            table.setShowGrid(true); // Hiển thị đường viền bảng
            table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
                {
                    setOpaque(true);
                    setBackground(new Color(33, 150, 243));
                    setForeground(Color.WHITE);
                    setFont(new Font("Arial", Font.BOLD, 20));
                    setHorizontalAlignment(CENTER);
                }
            });
            // Căn giữa tất cả các cột
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // Điều chỉnh chiều rộng cột cho phù hợp
            table.getColumnModel().getColumn(0).setPreferredWidth(200); // Cột Tên Học Sinh
            table.getColumnModel().getColumn(1).setPreferredWidth(100); // Cột Lượt Làm
            table.getColumnModel().getColumn(2).setPreferredWidth(80);  // Cột Điểm
            table.getColumnModel().getColumn(3).setPreferredWidth(200); // Cột Thời Gian Bắt Đầu
            table.getColumnModel().getColumn(4).setPreferredWidth(200); // Cột Thời Gian Kết Thúc
            table.getColumnModel().getColumn(5).setPreferredWidth(100); // Cột Số Lần Làm

            // Đặt bảng vào thanh cuộn để hỗ trợ nhiều dữ liệu
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    "Danh Sách Lượt Làm Bài", // Tiêu đề của bảng
                    TitledBorder.LEFT, // Căn trái
                    TitledBorder.TOP, // Đặt ở trên
                    new Font("Arial", Font.BOLD, 18), // Font tiêu đề Arial, đậm, cỡ 18
                    new Color(33, 150, 243) // Màu xanh cho tiêu đề
            ));
            scrollPane.setBackground(Color.WHITE);
            // Thêm thanh cuộn vào panel chính
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.setBackground(Color.WHITE);
        } catch (Exception e) {
            // Xử lý lỗi nếu có, ví dụ: lỗi RMI hoặc kết nối
            e.printStackTrace();
            JLabel lblError = new JLabel("Lỗi khi hiển thị thông tin lượt làm bài: " + e.getMessage(), JLabel.CENTER);
            lblError.setFont(new Font("Arial", Font.BOLD, 20)); // Font Arial, đậm, cỡ 20
            lblError.setForeground(Color.RED); // Chữ màu đỏ
            panel.add(lblError, BorderLayout.CENTER);
        }
        panel.setBackground(Color.WHITE);
        return panel;
    }
//    private JPanel createLuotLamBaiPanel() {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
//        panel.setBackground(Color.WHITE);
//
//        // Kiểm tra nếu baiThi là null
//        if (baiThi == null) {
//            JLabel lblError = new JLabel("Không thể hiển thị thông tin lượt làm bài.", JLabel.CENTER);
//            lblError.setFont(new Font("Arial", Font.BOLD, 16));
//            lblError.setForeground(Color.RED);
//            panel.add(lblError);
//            return panel;
//        }
//
//        try {
//            // Hiển thị thông tin lượt làm bài (có thể thêm code ở đây sau này)
//            JLabel lblInfo = new JLabel("Chức năng này đang được phát triển.", JLabel.CENTER);
//            lblInfo.setFont(new Font("Arial", Font.ITALIC, 16));
//            lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
//            panel.add(lblInfo);
//        } catch (Exception e) {
//            e.printStackTrace();
//            JLabel lblError = new JLabel("Lỗi khi hiển thị thông tin lượt làm bài: " + e.getMessage(), JLabel.CENTER);
//            lblError.setFont(new Font("Arial", Font.BOLD, 16));
//            lblError.setForeground(Color.RED);
//            panel.add(lblError);
//        }
//
//        return panel;
//    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BaiThiService baiThiService = (BaiThiService) Naming.lookup("rmi://localhost:8081/baiThiService");
            BaiThi baiThi1 = baiThiService.layThongTinBaiThiVaCauHoi(3);

            if (baiThi1 == null) {
                JOptionPane.showMessageDialog(null,
                    "Không tìm thấy bài thi với mã 3.",
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
