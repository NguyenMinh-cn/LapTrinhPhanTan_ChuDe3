package gui;

import entities.HocSinh;
import service.PhienLamBaiService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.rmi.Naming;
import java.util.List;

public class GiaoDienHocSinhXemKetQua extends JPanel {
    private String ipAddress = "localhost";
    private JPanel panel1;
    private JTable tblChiTiet;
    private JScrollPane scrollPane;
    private JLabel lblDiemSo;
    private JLabel lblSoCauDung;
    private JLabel lblSoCauSai;
    private JButton btnQuayLai; // Nút Quay lại
    private String maPhienLamBai;
    private HocSinh hocSinh; // Thêm thông tin HocSinh


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
        lblDiemSo = new JLabel();
        lblSoCauDung = new JLabel();
        lblSoCauSai = new JLabel();
        btnQuayLai = new JButton("Quay lại"); // Khởi tạo nút Quay lại

        // Thiết lập layout
        setLayout(new BorderLayout());

        // Panel chứa các nhãn điểm số
        JPanel panelThongTin = new JPanel();
        panelThongTin.setLayout(new FlowLayout());
        lblDiemSo.setText("Điểm: ");
        lblSoCauDung.setText("Số câu đúng: ");
        lblSoCauSai.setText("Số câu sai: ");
        panelThongTin.add(lblDiemSo);
        panelThongTin.add(lblSoCauDung);
        panelThongTin.add(lblSoCauSai);
        panelThongTin.add(btnQuayLai); // Thêm nút vào panel

        // Bảng chi tiết kết quả
        tblChiTiet.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Câu hỏi", "Đáp án chọn", "Đáp án đúng", "Kết quả"}
        ));
        scrollPane.setViewportView(tblChiTiet);

        // Tùy chỉnh màu cho cột "Kết quả"
        tblChiTiet.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null && value.equals("Đúng")) {
                    c.setBackground(new Color(144, 238, 144)); // Màu xanh cho "Đúng"
                } else if (value != null && value.equals("Sai")) {
                    c.setBackground(new Color(255, 182, 193)); // Màu hồng cho "Sai"
                } else {
                    c.setBackground(table.getBackground());
                }
                return c;
            }
        });

        // Thêm các thành phần vào panel chính
        panel1.setLayout(new BorderLayout());
        panel1.add(panelThongTin, BorderLayout.NORTH);
        panel1.add(scrollPane, BorderLayout.CENTER);

        // Thêm panel vào giao diện chính
        add(panel1, BorderLayout.CENTER);

        // Xử lý sự kiện cho nút Quay lại
        btnQuayLai.addActionListener(e -> {
            JPanel parentPanel = (JPanel) getParent();
            parentPanel.removeAll();
            parentPanel.add(new GiaoDienXemDanhSachBaiThi(hocSinh).$$$getRootComponent$$$());
            parentPanel.revalidate();
            parentPanel.repaint();
        });
    }

    private void loadKetQua() {
        try {
            PhienLamBaiService phienLamBaiService = (PhienLamBaiService) Naming.lookup("rmi://"+ipAddress+":8081/phienLamBaiService");

            // Lấy chi tiết kết quả
            List<Object[]> chiTietKetQua = phienLamBaiService.layKetQuaChiTietPhienLamBai(maPhienLamBai);
            DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ
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
