package gui;

import entities.HocSinh;
import service.PhienLamBaiService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.rmi.Naming;
import java.util.List;

public class GiaoDienHocSinhXemKetQua extends JPanel {
    private String ipAddress = "localhost";
    private JPanel panel1;
    private JTable tblChiTiet;
    private JScrollPane scrollPane;
    private JLabel lblTieuDe;
    private JLabel lblDiemSo;
    private JLabel lblSoCauDung;
    private JLabel lblSoCauSai;
    private JButton btnQuayLai;
    private String maPhienLamBai; // Đã đổi thành String theo lựa chọn Cách 2
    private HocSinh hocSinh;

    public GiaoDienHocSinhXemKetQua(String maPhienLamBai, HocSinh hocSinh) {
        this.maPhienLamBai = maPhienLamBai;
        this.hocSinh = hocSinh;
        initComponents();
        loadKetQua();
    }

    private void initComponents() {
        panel1 = new JPanel();
        tblChiTiet = new JTable();
        scrollPane = new JScrollPane();
        lblTieuDe = new JLabel("KẾT QUẢ BÀI THI");
        lblDiemSo = new JLabel();
        lblSoCauDung = new JLabel();
        lblSoCauSai = new JLabel();
        btnQuayLai = new JButton("Quay lại");

        // Thiết lập layout chính
        setLayout(new BorderLayout());
        setBackground(new Color(216, 227, 231)); // Màu nền xanh nhạt giống GiaoDienXemDanhSachBaiThi

        // Tiêu đề
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));
        lblTieuDe.setForeground(new Color(0, 102, 204)); // Màu xanh đậm cho tiêu đề
        lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTieuDe, BorderLayout.NORTH);

        // Panel chứa thông tin điểm số
        JPanel panelThongTin = new JPanel();
        panelThongTin.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelThongTin.setBackground(new Color(173, 216, 230)); // Màu xanh nhạt hơn cho panel điểm số
        panelThongTin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblDiemSo.setText("Điểm: ");
        lblDiemSo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDiemSo.setForeground(Color.BLACK);

        lblSoCauDung.setText("Số câu đúng: ");
        lblSoCauDung.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSoCauDung.setForeground(Color.BLACK);

        lblSoCauSai.setText("Số câu sai: ");
        lblSoCauSai.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSoCauSai.setForeground(Color.BLACK);

        // Tùy chỉnh nút Quay lại
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 14));
        btnQuayLai.setBackground(new Color(0, 153, 255)); // Màu xanh dương
        btnQuayLai.setForeground(Color.WHITE);
        btnQuayLai.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnQuayLai.setFocusPainted(false);

        panelThongTin.add(lblDiemSo);
        panelThongTin.add(lblSoCauDung);
        panelThongTin.add(lblSoCauSai);
        panelThongTin.add(btnQuayLai);

        // Thiết lập bảng chi tiết kết quả
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Câu hỏi", "Đáp án chọn", "Đáp án đúng", "Kết quả"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bảng
            }
        };
        tblChiTiet.setModel(model);
        tblChiTiet.setRowHeight(35); // Tăng chiều cao hàng
        tblChiTiet.setFont(new Font("Arial", Font.PLAIN, 14)); // Font chữ trong bảng
        tblChiTiet.setBackground(Color.WHITE);
        tblChiTiet.setForeground(Color.BLACK);
        tblChiTiet.setGridColor(new Color(173, 216, 230)); // Màu lưới xanh nhạt

        // Tùy chỉnh tiêu đề bảng
        JTableHeader tableHeader = tblChiTiet.getTableHeader();
        tableHeader.setBackground(new Color(0, 102, 204)); // Màu xanh đậm cho tiêu đề
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));

        // Tùy chỉnh màu cho cột "Kết quả"
        tblChiTiet.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null && value.equals("Đúng")) {
                    c.setBackground(new Color(152, 251, 152)); // Màu xanh lá nhạt hơn
                    c.setForeground(Color.BLACK);
                } else if (value != null && value.equals("Sai")) {
                    c.setBackground(new Color(255, 204, 203)); // Màu hồng nhạt hơn
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
                }
                c.setFont(new Font("Arial", Font.PLAIN, 14));
                return c;
            }
        });

        // Tùy chỉnh JScrollPane
        scrollPane.setViewportView(tblChiTiet);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1)); // Viền xanh đậm
        scrollPane.setBackground(new Color(216, 227, 231));

        // Thêm các thành phần vào panel chính
        panel1.setLayout(new BorderLayout());
        panel1.setBackground(new Color(216, 227, 231));
        panel1.add(panelThongTin, BorderLayout.NORTH);
        panel1.add(scrollPane, BorderLayout.CENTER);
        panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Thêm khoảng cách viền

        add(panel1, BorderLayout.CENTER);

        // Xử lý sự kiện cho nút Quay lại
        btnQuayLai.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose(); // Đóng JFrame hiện tại
            }
        });
    }

    private void loadKetQua() {
        try {
            PhienLamBaiService phienLamBaiService = (PhienLamBaiService) Naming.lookup("rmi://" + ipAddress + ":8081/phienLamBaiService");

            // Lấy chi tiết kết quả
            List<Object[]> chiTietKetQua = phienLamBaiService.layKetQuaChiTietPhienLamBai(maPhienLamBai);
            DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();
            model.setRowCount(0);
            for (Object[] ketQua : chiTietKetQua) {
                model.addRow(ketQua);
            }

            // Lấy điểm số và số câu
            Object[] thongTinDiem = phienLamBaiService.tinhDiemVaSoCau(maPhienLamBai);
            lblDiemSo.setText("Điểm: " + thongTinDiem[0] + "/100");
            lblSoCauDung.setText("Số câu đúng: " + thongTinDiem[1]);
            lblSoCauSai.setText("Số câu sai: " + thongTinDiem[2]);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải kết quả: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JComponent $$$getRootComponent$$$() {
        return this;
    }
}
