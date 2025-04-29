package gui;

import entities.BaiThi;
import entities.HocSinh;
import entities.PhienLamBai;
import service.BaiThiService;
import service.HocSinhService;
import service.PhienLamBaiService;
import service.TaiKhoanService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GiaoDienMenuHocSinh extends JPanel {
    private String ipAddress = "localhost";
    private HocSinh hocSinh;
    private JPanel panel1;
    private JPanel panelCaiDatTaiKhoan;
    private JPanel panelDanhSachBaiThi;
    private JPanel panelNoiDung;
    private JLabel lbTenHocSinh;
    private JLabel lbDangXuat;
    private JLabel lbTaiKhoan;
    private JLabel lbDSBaiThi;
    private JLabel lbDanhSachBaiThi;
    private JPanel pnDangXuat;
    private TaiKhoanService taiKhoanService;
    private JLabel selectedLabel = null;

    public GiaoDienMenuHocSinh(HocSinh hocSinh) throws MalformedURLException, NotBoundException, RemoteException {
        this.hocSinh = hocSinh;
        this.taiKhoanService = (TaiKhoanService) Naming.lookup("rmi://" + ipAddress + ":8081/taiKhoanService");
        $$$setupUI$$$();
        lbTenHocSinh.setText(hocSinh.getHoTen());

        // MouseAdapter để xử lý hiệu ứng hover và chọn label
        MouseAdapter listener = new MouseAdapter() {
            Color hoverTextColor = new Color(67, 97, 238);

            @Override
            public void mouseEntered(MouseEvent e) {
                Component c = (Component) e.getSource();
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    if (label != selectedLabel) {
                        label.setForeground(hoverTextColor);  // Màu chữ khi hover
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Component c = (Component) e.getSource();
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    if (label != selectedLabel) {
                        label.setForeground(Color.BLACK);   // Màu chữ khi không hover
                    }
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                JLabel label = (JLabel) e.getSource();
                // Nếu đã có một nút được chọn, đổi lại màu cho nó
                if (selectedLabel != null) {
                    selectedLabel.setForeground(Color.BLACK);   // Màu chữ khi không chọn
                }
                selectedLabel = label;
                label.setForeground(new Color(63, 55, 201));   // Màu chữ khi chọn
            }
        };

        // Xử lý sự kiện đăng xuất
        lbDangXuat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn đăng xuất?",
                        "Xác nhận đăng xuất",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    SwingUtilities.getWindowAncestor(panel1).dispose();
                    JFrame frame = new JFrame("Đăng Nhập");
                    try {
                        frame.setContentPane(new GiaoDienDangNhap().$$$getRootComponent$$$());
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    } catch (NotBoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lbDangXuat.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lbDangXuat.setForeground(Color.BLACK);
            }
        });

        // Xử lý sự kiện cho label "Tài khoản"
        lbTaiKhoan.addMouseListener(listener);
        lbTaiKhoan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelNoiDung.removeAll();
                try {
                    panelNoiDung.add(new GiaoDienCaiDatTaiKhoan(taiKhoanService.finByID(hocSinh.getEmail())).$$$getRootComponent$$$());
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (NotBoundException ex) {
                    throw new RuntimeException(ex);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                panelNoiDung.revalidate();
                panelNoiDung.repaint();
            }
        });

        // Xử lý sự kiện cho label "Danh sách bài thi"
        lbDSBaiThi.addMouseListener(listener);
        lbDSBaiThi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // Hiển thị danh sách bài thi
                    hienThiDanhSachBaiThi();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(GiaoDienMenuHocSinh.this,
                            "Lỗi khi hiển thị danh sách bài thi: " + ex.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Getter để truy cập panelNoiDung từ bên ngoài (nếu cần)
    public JPanel getPanelNoiDung() {
        return panelNoiDung;
    }

    // Getter để truy cập lbDSBaiThi từ bên ngoài (nếu cần)
    public JLabel getLbDSBaiThi() {
        return lbDSBaiThi;
    }

    // Hàm hiển thị danh sách bài thi
    private void hienThiDanhSachBaiThi() {
        panelNoiDung.removeAll();
        try {
            // Khởi tạo giao diện GiaoDienXemDanhSachBaiThi
            GiaoDienXemDanhSachBaiThi giaoDienXemDanhSach = new GiaoDienXemDanhSachBaiThi(hocSinh);
            panelNoiDung.add(giaoDienXemDanhSach.$$$getRootComponent$$$(), BorderLayout.CENTER);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(GiaoDienMenuHocSinh.this,
                    "Lỗi khi hiển thị danh sách bài thi: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        panelNoiDung.revalidate();
        panelNoiDung.repaint();
    }
    // Hàm tạo giao diện cho mỗi bài thi
    private JPanel taoThanhPhanBaiThi(BaiThi baiThi, HocSinh hocSinh, PhienLamBaiService phienLamBaiService)
            throws RemoteException {
        // Panel chính cho bài thi
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(350, 300));
        panel.setMaximumSize(new Dimension(400, 400));
        panel.setMinimumSize(new Dimension(350, 300));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));

        // Panel chứa thông tin
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);

        // Font chữ
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);
        Font boldFont = new Font("Segoe UI", Font.BOLD, 20);

        // Các nhãn thông tin
        JLabel lblTenBaiThi = new JLabel("<html>Tên bài thi: " + baiThi.getTenBaiThi() + "</html>");
        lblTenBaiThi.setFont(boldFont);
        lblTenBaiThi.setForeground(new Color(33, 33, 33));

        JLabel lblMonHoc = new JLabel("<html><b>Môn học: </b>" + baiThi.getMonHoc().getTenMon() + "</html>");
        lblMonHoc.setFont(labelFont);
        lblMonHoc.setForeground(new Color(66, 66, 66));

        JLabel lblBatDau = new JLabel("<html><b>Bắt đầu: </b>" + chuyenDinhDangNgayGio(baiThi.getThoiGianBatDau()) + "</html>");
        lblBatDau.setFont(labelFont);
        lblBatDau.setForeground(new Color(66, 66, 66));

        JLabel lblKetThuc = new JLabel("<html><b>Kết thúc: </b>" + chuyenDinhDangNgayGio(baiThi.getThoiGianKetThuc()) + "</html>");
        lblKetThuc.setFont(labelFont);
        lblKetThuc.setForeground(new Color(66, 66, 66));

        JLabel lblThoiGian = new JLabel("<html><b>Thời gian: </b>" + baiThi.getThoiLuong() + " phút</html>");
        lblThoiGian.setFont(labelFont);
        lblThoiGian.setForeground(new Color(66, 66, 66));

        JLabel lblSoCauHoi = new JLabel("<html><b>Số câu hỏi: </b>" + baiThi.getDanhSachCauHoi().size() + "</html>");
        lblSoCauHoi.setFont(labelFont);
        lblSoCauHoi.setForeground(new Color(66, 66, 66));

        // Kiểm tra trạng thái bài thi (đã thi hay chưa) và điểm
        List<PhienLamBai> dsPhienLamBaiHocSinh = phienLamBaiService.findByMaHocSinh(hocSinh.getMaHocSinh());
        List<PhienLamBai> dsPhienLamBai = new ArrayList<>();
        for (PhienLamBai plb : dsPhienLamBaiHocSinh) {
            if (plb.getBaiThi().getMaBaiThi() == baiThi.getMaBaiThi()) {
                dsPhienLamBai.add(plb);
            }
        }

        JLabel lblTrangThai;
        JLabel lblDiem = new JLabel();
        if (dsPhienLamBai.isEmpty()) {
            lblTrangThai = new JLabel("<html><b>Trạng thái: </b>Chưa thi</html>");
            lblTrangThai.setFont(labelFont);
            lblTrangThai.setForeground(new Color(255, 69, 0)); // Màu đỏ cho trạng thái chưa thi
        } else {
            lblTrangThai = new JLabel("<html><b>Trạng thái: </b>Đã thi</html>");
            lblTrangThai.setFont(labelFont);
            lblTrangThai.setForeground(new Color(34, 139, 34)); // Màu xanh cho trạng thái đã thi

            // Lấy phiên làm bài mới nhất để hiển thị điểm
            PhienLamBai phienMoiNhat = dsPhienLamBai.get(dsPhienLamBai.size() - 1); // Lấy phiên mới nhất
            Object[] thongTinDiem = phienLamBaiService.tinhDiemVaSoCau(phienMoiNhat.getMaPhien());
            lblDiem.setText("<html><b>Điểm: </b>" + thongTinDiem[0] + "/100</html>");
            lblDiem.setFont(labelFont);
            lblDiem.setForeground(new Color(66, 66, 66));
        }

        // Thêm các nhãn vào panel thông tin
        info.add(lblTenBaiThi);
        info.add(lblMonHoc);
        info.add(lblBatDau);
        info.add(lblKetThuc);
        info.add(lblThoiGian);
        info.add(lblSoCauHoi);
        info.add(lblTrangThai);
        if (!dsPhienLamBai.isEmpty()) {
            info.add(lblDiem);
        }

        // Thêm sự kiện nhấp đúp để xem chi tiết
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Nhấp đúp
                    if (dsPhienLamBai.isEmpty()) {
                        JOptionPane.showMessageDialog(GiaoDienMenuHocSinh.this,
                                "Bạn chưa thi bài này!",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        try {
                            // Lấy phiên làm bài mới nhất
                            PhienLamBai phienMoiNhat = dsPhienLamBai.get(dsPhienLamBai.size() - 1);

                            // Tạo JFrame mới để hiển thị chi tiết kết quả
                            JFrame ketQuaFrame = new JFrame("Kết Quả Bài Thi: " + baiThi.getTenBaiThi());
                            ketQuaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            ketQuaFrame.setSize(800, 600);
                            ketQuaFrame.setLocationRelativeTo(null);

                            // Tạo giao diện xem kết quả
                            GiaoDienHocSinhXemKetQua giaoDienKetQua = new GiaoDienHocSinhXemKetQua(phienMoiNhat.getMaPhien(), hocSinh);
                            ketQuaFrame.setContentPane(giaoDienKetQua.$$$getRootComponent$$$());
                            ketQuaFrame.setVisible(true);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(GiaoDienMenuHocSinh.this,
                                    "Lỗi khi hiển thị kết quả: " + ex.getMessage(),
                                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        panel.add(info, BorderLayout.CENTER);
        return panel;
    }

    // Hàm định dạng ngày giờ
    private String chuyenDinhDangNgayGio(LocalDateTime localDateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy");
            return localDateTime.format(formatter);
        } catch (Exception e) {
            e.printStackTrace();
            return "Định dạng không hợp lệ";
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setBackground(new Color(-6106369));
        panel2.setPreferredSize(new Dimension(200, 20));
        panel1.add(panel2, BorderLayout.WEST);
        lbTenHocSinh = new JLabel();
        lbTenHocSinh.setBackground(new Color(-12020241));
        Font lbTenHocSinhFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, lbTenHocSinh.getFont());
        if (lbTenHocSinhFont != null) lbTenHocSinh.setFont(lbTenHocSinhFont);
        lbTenHocSinh.setForeground(new Color(-1));
        lbTenHocSinh.setHorizontalAlignment(0);
        lbTenHocSinh.setHorizontalTextPosition(0);
        lbTenHocSinh.setOpaque(true);
        lbTenHocSinh.setPreferredSize(new Dimension(200, 70));
        lbTenHocSinh.setText("Label");
        panel2.add(lbTenHocSinh, BorderLayout.NORTH);
        lbDangXuat = new JLabel();
        lbDangXuat.setBackground(new Color(-3543049));
        Font lbDangXuatFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, lbDangXuat.getFont());
        if (lbDangXuatFont != null) lbDangXuat.setFont(lbDangXuatFont);
        lbDangXuat.setHorizontalAlignment(0);
        lbDangXuat.setOpaque(true);
        lbDangXuat.setPreferredSize(new Dimension(71, 50));
        lbDangXuat.setText("Đăng xuất");
        panel2.add(lbDangXuat, BorderLayout.SOUTH);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel3.setAlignmentX(0.2f);
        panel3.setBackground(new Color(-3543049));
        panel3.setPreferredSize(new Dimension(850, 50));
        panel2.add(panel3, BorderLayout.CENTER);
        lbDSBaiThi = new JLabel();
        lbDSBaiThi.setAlignmentX(0.2f);
        lbDSBaiThi.setBackground(new Color(-4333314));
        Font lbDSBaiThiFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, lbDSBaiThi.getFont());
        if (lbDSBaiThiFont != null) lbDSBaiThi.setFont(lbDSBaiThiFont);
        lbDSBaiThi.setHorizontalAlignment(10);
        lbDSBaiThi.setOpaque(false);
        lbDSBaiThi.setPreferredSize(new Dimension(200, 30));
        lbDSBaiThi.setText("    Danh sách bài thi");
        panel3.add(lbDSBaiThi);
        lbTaiKhoan = new JLabel();
        lbTaiKhoan.setAlignmentX(0.2f);
        lbTaiKhoan.setBackground(new Color(-4333314));
        Font lbTaiKhoanFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, lbTaiKhoan.getFont());
        if (lbTaiKhoanFont != null) lbTaiKhoan.setFont(lbTaiKhoanFont);
        lbTaiKhoan.setHorizontalAlignment(10);
        lbTaiKhoan.setOpaque(false);
        lbTaiKhoan.setPreferredSize(new Dimension(200, 30));
        lbTaiKhoan.setText("    Tài khoản");
        panel3.add(lbTaiKhoan);
        panelNoiDung = new JPanel();
        panelNoiDung.setLayout(new BorderLayout(0, 0));
        panel1.add(panelNoiDung, BorderLayout.CENTER);
    }

    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    // Hàm main để chạy thử
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                HocSinhService hocSinhService = (HocSinhService) Naming.lookup("rmi://localhost:8081/hocSinhService");
                HocSinh hocSinh = hocSinhService.timHocSinhTheoEmail("hocsinh1@example.com"); // Ví dụ email

                if (hocSinh == null) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy học sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Tạo JFrame chính cho giao diện học sinh
                JFrame frame = new JFrame("Menu Học Sinh");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 600);
                frame.setLocationRelativeTo(null);

                // Khởi tạo giao diện menu học sinh
                GiaoDienMenuHocSinh menuHocSinh = new GiaoDienMenuHocSinh(hocSinh);
                frame.setContentPane(menuHocSinh.$$$getRootComponent$$$());
                frame.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khởi tạo: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
