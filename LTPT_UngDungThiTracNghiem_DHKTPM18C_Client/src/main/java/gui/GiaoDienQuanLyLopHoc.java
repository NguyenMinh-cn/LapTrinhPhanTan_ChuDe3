package gui;

import entities.HocSinh;
import entities.Lop;
import service.HocSinhService;
import service.LopService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class GiaoDienQuanLyLopHoc extends JPanel {
    private String ipAddress = "localhost";
    private JTextField txtTenLop;
    private JButton btnThemLop;
    private JComboBox<String> cmbLopGanHocSinh;
    private JTable tableHocSinhChuaCoLop;
    private DefaultTableModel tableModel;
    private JButton btnGanHocSinh;
    private LopService lopService;
    private HocSinhService hocSinhService;

    public GiaoDienQuanLyLopHoc() throws MalformedURLException, NotBoundException, RemoteException {
        lopService = (LopService) Naming.lookup("rmi://" + ipAddress + ":8081/lopService");
        hocSinhService = (HocSinhService) Naming.lookup("rmi://" + ipAddress + ":8081/hocSinhService");

        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        setBackground(Color.WHITE);
        Font fontChinh = new Font("Arial", Font.PLAIN, 20);
        Color primaryColor = new Color(33, 150, 243);
        Color comboBoxBackground = new Color(200, 220, 255); // Màu nền sáng cho JComboBox
        Color comboBoxBorderColor = new Color(100, 150, 255); // Màu viền cho JComboBox

        // --- Phần chính: Gộp Thêm lớp và Gán học sinh ---
        JPanel panelGanHocSinh = new JPanel(new BorderLayout(10, 10));
        panelGanHocSinh.setBackground(Color.WHITE);
        panelGanHocSinh.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel chứa các thành phần nhập liệu và nút (dàn ngang)
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelTop.setBackground(Color.WHITE);

        // Thêm lớp: Tên lớp, nút Thêm lớp
        JLabel lblTenLop = new JLabel("Tên lớp:");
        lblTenLop.setFont(fontChinh);
        txtTenLop = new JTextField(10);
        txtTenLop.setFont(fontChinh);
        txtTenLop.setToolTipText("Nhập tên lớp (ví dụ: 10A1)");

        btnThemLop = new JButton("Thêm lớp");
        btnThemLop.setBackground(primaryColor);
        btnThemLop.setForeground(Color.WHITE);
        btnThemLop.setFont(fontChinh);

        // Gán học sinh: Chọn lớp, nút Gán học sinh
        JLabel lblLopGan = new JLabel("Chọn lớp:");
        lblLopGan.setFont(fontChinh);

        cmbLopGanHocSinh = new JComboBox<>();
        cmbLopGanHocSinh.setFont(fontChinh);
        cmbLopGanHocSinh.setPreferredSize(new Dimension(250, 35)); // Thu nhỏ lại từ 300x40 xuống 250x35
        cmbLopGanHocSinh.setBackground(comboBoxBackground); // Màu nền sáng
        cmbLopGanHocSinh.setBorder(BorderFactory.createLineBorder(comboBoxBorderColor, 1)); // Viền sáng

        // Tùy chỉnh renderer để danh sách thả xuống cũng có màu nền sáng
        cmbLopGanHocSinh.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(fontChinh);
                if (!isSelected) {
                    label.setBackground(comboBoxBackground); // Màu nền sáng cho các mục thả xuống
                }
                return label;
            }
        });

        btnGanHocSinh = new JButton("Gán học sinh");
        btnGanHocSinh.setBackground(primaryColor);
        btnGanHocSinh.setForeground(Color.WHITE);
        btnGanHocSinh.setFont(fontChinh);

        // Thêm tất cả thành phần vào panelTop (dàn ngang, căn trái)
        panelTop.add(lblTenLop);
        panelTop.add(txtTenLop);
        panelTop.add(btnThemLop);
        panelTop.add(Box.createHorizontalStrut(20));
        panelTop.add(lblLopGan);
        panelTop.add(cmbLopGanHocSinh);
        panelTop.add(btnGanHocSinh);

        panelGanHocSinh.add(panelTop, BorderLayout.NORTH);

        // Bảng học sinh chưa có lớp
        initTableHocSinhChuaCoLop();
        JScrollPane scrollPane = new JScrollPane(tableHocSinhChuaCoLop);
        scrollPane.setBackground(Color.WHITE);
        panelGanHocSinh.add(scrollPane, BorderLayout.CENTER);

        // Thêm panel chính vào giao diện
        add(panelGanHocSinh, BorderLayout.CENTER);

        setupEvents();
        loadInitialData();
    }

    private void initTableHocSinhChuaCoLop() {
        String[] columnNames = {"Họ Tên", "Email", "Số Điện Thoại"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableHocSinhChuaCoLop = new JTable(tableModel);
        tableHocSinhChuaCoLop.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableHocSinhChuaCoLop.setFont(new Font("Arial", Font.PLAIN, 20));
        tableHocSinhChuaCoLop.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        tableHocSinhChuaCoLop.setRowHeight(30);
        tableHocSinhChuaCoLop.getTableHeader().setReorderingAllowed(false);
        tableHocSinhChuaCoLop.getTableHeader().setResizingAllowed(true);
        tableHocSinhChuaCoLop.setBackground(Color.WHITE);

        TableColumnModel columnModel = tableHocSinhChuaCoLop.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(250);
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(2).setPreferredWidth(200);

        tableHocSinhChuaCoLop.getTableHeader().setBackground(new Color(4, 117, 196));
        tableHocSinhChuaCoLop.getTableHeader().setForeground(Color.WHITE);
    }

    private void setupEvents() {
        btnThemLop.addActionListener(e -> themLop());
        btnGanHocSinh.addActionListener(e -> ganHocSinh());
    }

    private void loadInitialData() {
        try {
            List<Lop> danhSachLop = lopService.getAll();
            for (Lop lop : danhSachLop) {
                cmbLopGanHocSinh.addItem(lop.getTenLop());
            }

            loadHocSinhChuaCoLop();
        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void themLop() {
        try {
            String tenLop = txtTenLop.getText().trim();
            if (tenLop.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên lớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Lop lop = new Lop();
            lop.setTenLop(tenLop);

            boolean saveResult = lopService.save(lop);
            if (saveResult) {
                JOptionPane.showMessageDialog(this, "Thêm lớp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                txtTenLop.setText("");
                cmbLopGanHocSinh.addItem(tenLop);
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm lớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ganHocSinh() {
        try {
            int selectedRow = tableHocSinhChuaCoLop.getSelectedRow();
            String tenLop = (String) cmbLopGanHocSinh.getSelectedItem();
            if (selectedRow == -1 || tenLop == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn học sinh và lớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String email = (String) tableModel.getValueAt(selectedRow, 1);
            HocSinh hocSinh = hocSinhService.timHocSinhTheoEmail(email);
            Lop lop = lopService.findByTenLop(tenLop);

            if (hocSinh == null || lop == null) {
                JOptionPane.showMessageDialog(this, "Học sinh hoặc lớp không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            hocSinh.setLop(lop);
            boolean updateResult = hocSinhService.update(hocSinh);
            if (updateResult) {
                JOptionPane.showMessageDialog(this, "Gán học sinh vào lớp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi gán học sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadHocSinhChuaCoLop() {
        try {
            List<HocSinh> danhSachHS = hocSinhService.findHocSinhChuaCoLop();
            tableModel.setRowCount(0);
            for (HocSinh hs : danhSachHS) {
                Object[] row = {
                        hs.getHoTen(),
                        hs.getEmail(),
                        hs.getSoDienThoai()
                };
                tableModel.addRow(row);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách học sinh: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            try {
//                JFrame frame = new JFrame("Quản Lý Lớp Học");
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setSize(1000, 600);
//                frame.setLocationRelativeTo(null);
//                frame.add(new GiaoDienQuanLyLopHoc());
//                frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Lỗi khi khởi động ứng dụng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//    }
}