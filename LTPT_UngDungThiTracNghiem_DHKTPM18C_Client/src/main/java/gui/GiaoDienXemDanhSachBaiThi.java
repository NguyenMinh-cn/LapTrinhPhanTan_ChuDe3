package gui;

import entities.BaiThi;
import entities.HocSinh;
import entities.PhienLamBai;
import service.BaiThiService;
import service.PhienLamBaiService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.rmi.Naming;
import java.util.List;

public class GiaoDienXemDanhSachBaiThi extends JPanel {
    private String ipAddress = "localhost";
    private JPanel panel1;
    private JTable tblBaiThi;
    private JScrollPane scrollPane;
    private HocSinh hocSinh;
    private BaiThiService baiThiService;
    private PhienLamBaiService phienLamBaiService;

    public GiaoDienXemDanhSachBaiThi(HocSinh hocSinh) {
        this.hocSinh = hocSinh;
        try {
            this.baiThiService = (BaiThiService) Naming.lookup("rmi://" + ipAddress + ":8081/baiThiService");
            this.phienLamBaiService = (PhienLamBaiService) Naming.lookup("rmi://" + ipAddress + ":8081/phienLamBaiService");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối server: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        initComponents();
        loadDanhSachBaiThi();
    }

    private void initComponents() {
        panel1 = new JPanel();
        tblBaiThi = new JTable();
        scrollPane = new JScrollPane();

        setLayout(new BorderLayout());

        tblBaiThi.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã bài thi", "Tên bài thi", "Trạng thái", "Điểm"}
        ));
        scrollPane.setViewportView(tblBaiThi);

        panel1.setLayout(new BorderLayout());
        panel1.add(scrollPane, BorderLayout.CENTER);

        add(panel1, BorderLayout.CENTER);

        // Xử lý nhấp chuột để xem chi tiết
        tblBaiThi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Nhấp đúp
                    int row = tblBaiThi.getSelectedRow();
                    if (row != -1) {
                        Integer maBaiThi = Integer.parseInt((String) tblBaiThi.getValueAt(row, 0));
                        String trangThai = (String) tblBaiThi.getValueAt(row, 2);
                        if (trangThai.equals("Đã thi")) {
                            try {
                                // Lấy phiên làm bài mới nhất
                                List<PhienLamBai> danhSachPhien = phienLamBaiService.findByMaHocSinh(hocSinh.getMaHocSinh());
                                PhienLamBai phienMoiNhat = danhSachPhien.stream()
                                        .filter(p -> p.getBaiThi().getMaBaiThi() == maBaiThi)
                                        .max((p1, p2) -> p1.getThoiGianKetThuc().compareTo(p2.getThoiGianKetThuc()))
                                        .orElse(null);
                                if (phienMoiNhat != null) {
                                    // Mở GiaoDienHocSinhXemKetQua trong JFrame riêng
                                    JFrame frame = new JFrame("Kết quả bài thi: " + maBaiThi);
                                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                    frame.add(new GiaoDienHocSinhXemKetQua(phienMoiNhat.getMaPhien(), hocSinh).$$$getRootComponent$$$());
                                    frame.setSize(800, 600);
                                    frame.setLocationRelativeTo(null);
                                    frame.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(GiaoDienXemDanhSachBaiThi.this, "Không tìm thấy phiên làm bài.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(GiaoDienXemDanhSachBaiThi.this, "Lỗi khi tải kết quả: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(GiaoDienXemDanhSachBaiThi.this, "Bạn chưa thi bài này.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    private void loadDanhSachBaiThi() {
        try {
            // Chuyển đổi kiểu dữ liệu từ int sang Long
            Long maHocSinh = Long.valueOf(hocSinh.getMaHocSinh());
            List<BaiThi> danhSachBaiThi = baiThiService.getAllBaiThiForHocSinh(maHocSinh);
            List<PhienLamBai> danhSachPhien = phienLamBaiService.findByMaHocSinh(hocSinh.getMaHocSinh());
            DefaultTableModel model = (DefaultTableModel) tblBaiThi.getModel();
            model.setRowCount(0);

            for (BaiThi baiThi : danhSachBaiThi) {
                boolean daThi = danhSachPhien.stream()
                        .anyMatch(p -> p.getBaiThi().getMaBaiThi() == baiThi.getMaBaiThi());
                String trangThai = daThi ? "Đã thi" : "Chưa thi";
                Object diem = null;
                if (daThi) {
                    PhienLamBai phienMoiNhat = danhSachPhien.stream()
                            .filter(p -> p.getBaiThi().getMaBaiThi() == baiThi.getMaBaiThi())
                            .max((p1, p2) -> p1.getThoiGianKetThuc().compareTo(p2.getThoiGianKetThuc()))
                            .orElse(null);
                    if (phienMoiNhat != null) {
                        Object[] thongTinDiem = phienLamBaiService.tinhDiemVaSoCau(phienMoiNhat.getMaPhien());
                        diem = thongTinDiem[0];
                    }
                }
                model.addRow(new Object[]{
                        String.valueOf(baiThi.getMaBaiThi()),
                        baiThi.getTenBaiThi() != null ? baiThi.getTenBaiThi() : "",
                        trangThai,
                        diem != null ? diem : ""
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách bài thi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JComponent $$$getRootComponent$$$() {
        return this;
    }
}
