package gui;

import entities.HocSinh;
import entities.PhienLamBai;
import service.PhienLamBaiService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.rmi.Naming;
import java.text.SimpleDateFormat;
import java.util.List;

public class GiaoDienXemDanhSachBaiThi extends JPanel {
    private JPanel panel1;
    private JTable tblPhienLamBai;
    private JScrollPane scrollPane;
    private HocSinh hocSinh;

    public GiaoDienXemDanhSachBaiThi(HocSinh hocSinh) {
        this.hocSinh = hocSinh;
        initComponents();
        loadDanhSachPhienLamBai();
    }

    private void initComponents() {
        panel1 = new JPanel();
        tblPhienLamBai = new JTable();
        scrollPane = new JScrollPane();

        // Thiết lập layout
        setLayout(new BorderLayout());

        // Bảng danh sách phiên làm bài
        tblPhienLamBai.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã phiên", "Mã bài thi", "Thời gian bắt đầu", "Thời gian kết thúc", "Điểm"}
        ));
        scrollPane.setViewportView(tblPhienLamBai);

        // Thêm bảng vào panel
        panel1.setLayout(new BorderLayout());
        panel1.add(scrollPane, BorderLayout.CENTER);

        // Thêm panel vào giao diện chính
        add(panel1, BorderLayout.CENTER);

        // Xử lý nhấp đúp để xem chi tiết
        tblPhienLamBai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Nhấp đúp
                    int row = tblPhienLamBai.getSelectedRow();
                    if (row != -1) {
                        String maPhien = (String) tblPhienLamBai.getValueAt(row, 0);
                        // Mở GiaoDienHocSinhXemKetQua và truyền cả HocSinh
                        JPanel parentPanel = (JPanel) getParent();
                        parentPanel.removeAll();
                        parentPanel.add(new GiaoDienHocSinhXemKetQua(maPhien, hocSinh).$$$getRootComponent$$$());
                        parentPanel.revalidate();
                        parentPanel.repaint();
                    }
                }
            }
        });
    }

    private void loadDanhSachPhienLamBai() {
        try {
            PhienLamBaiService phienLamBaiService = (PhienLamBaiService) Naming.lookup("rmi://localhost:8081/phienLamBaiService");
            List<PhienLamBai> danhSachPhien = phienLamBaiService.findByMaHocSinh(hocSinh.getMaHocSinh());

            DefaultTableModel model = (DefaultTableModel) tblPhienLamBai.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            for (PhienLamBai phien : danhSachPhien) {
                Object[] thongTinDiem = phienLamBaiService.tinhDiemVaSoCau(phien.getMaPhien());
                model.addRow(new Object[]{
                        phien.getMaPhien(),
                        phien.getBaiThi() != null ? phien.getBaiThi().getMaBaiThi() : "",
                        phien.getThoiGianBatDau() != null ? sdf.format(phien.getThoiGianBatDau()) : "",
                        phien.getThoiGianKetThuc() != null ? sdf.format(phien.getThoiGianKetThuc()) : "",
                        thongTinDiem[0] // Điểm số
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách phiên làm bài: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JComponent $$$getRootComponent$$$() {
        return this;
    }
}