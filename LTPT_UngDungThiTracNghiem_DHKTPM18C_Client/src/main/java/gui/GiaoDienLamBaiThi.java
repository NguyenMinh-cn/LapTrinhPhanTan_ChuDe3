package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import entities.*;
import gui.custom.GiaoDienThi;
import gui.custom.PanelKQLamBai;
import gui.custom.WrapLayout;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;
import service.BaiThiService;
import service.HocSinhService;
import service.PhienLamBaiService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class GiaoDienLamBaiThi extends JPanel {
    private final CardLayout cardLayout;
    private JPanel panel1;
    private JPanel pnNoiDung;
    private JPanel pnDSBaiThi;
    private JPanel pnTTCTBaiThi;
    private JLabel txtTenBaiThi;
    private JLabel txtMo;
    private JLabel txtDong;
    private JLabel txtThoiLuong;
    private JLabel txtSoCH;
    private JPanel pnKetQua;
    private JPanel pnKetQuaLamBai;
    private JButton btnLamBaiThi;
    private JButton button1;
    private JLabel txtSoLanDuocPhepLamBai;
    private HocSinh hocSinh;
    private BaiThiService baiThiService = (BaiThiService) Naming.lookup("rmi://localhost:8081/baiThiService");
    private BaiThi baiThiDangChon;
    private PhienLamBaiService phienLamBaiService = (PhienLamBaiService) Naming.lookup("rmi://localhost:8081/phienLamBaiService");
    public static String chuyenDinhDangNgayGio(LocalDateTime localDateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy");
            return localDateTime.format(formatter);
        } catch (Exception e) {
            e.printStackTrace();
            return "Định dạng không hợp lệ";
        }
    }

    public GiaoDienLamBaiThi(HocSinh hocSinh) throws MalformedURLException, NotBoundException, RemoteException {
        this.hocSinh = hocSinh;
        $$$setupUI$$$();
        cardLayout = (CardLayout) panel1.getLayout();

        taoDSBaiThi();
        button1.setIcon(FontIcon.of(MaterialDesign.MDI_ARROW_LEFT_BOLD, 20, Color.WHITE));

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panel1, "Card1");
            }
        });
        btnLamBaiThi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<PhienLamBai> phienLamBaiList = null;
                try {
                    phienLamBaiList = phienLamBaiService.layDanhSachPhienLamBaiVaCauTraLoiTheoBaiThi(baiThiDangChon.getMaBaiThi());
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

                long soLanLamBai = phienLamBaiList.stream()
                        .filter(plb -> plb.getHocSinh().getMaHocSinh() == hocSinh.getMaHocSinh())
                        .count();

                System.out.println(soLanLamBai);
                System.out.println(baiThiDangChon.getSoLanDuocPhepLamBai());
                if (soLanLamBai >= baiThiDangChon.getSoLanDuocPhepLamBai()) {
                    JOptionPane.showMessageDialog(null, "Đã hết lượt làm bài thi.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (baiThiDangChon.getThoiGianBatDau().isAfter(LocalDateTime.now())) {
                    JOptionPane.showMessageDialog(null, "Bài thi chưa được mở. Vui lòng thử lại sau.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (baiThiDangChon.getThoiGianKetThuc().isBefore(LocalDateTime.now())) {
                    JOptionPane.showMessageDialog(null, "Bài thi đã kết thúc.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;

                }else if (baiThiDangChon.getMatKhau() != null) {
                    String matKhau = JOptionPane.showInputDialog(null, "Nhập mật khẩu bài thi:", "Mật khẩu", JOptionPane.PLAIN_MESSAGE);
                    if (matKhau == null || !matKhau.equals(baiThiDangChon.getMatKhau())) {
                        JOptionPane.showMessageDialog(null, "Mật khẩu không chính xác. Vui lòng thử lại.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                GiaoDienThi giaoDienThi = null;
                try {
                    giaoDienThi = new GiaoDienThi(baiThiDangChon, hocSinh);

                    // Thêm WindowListener để phát hiện khi cửa sổ đóng
                    final GiaoDienThi finalGiaoDienThi = giaoDienThi;
                    finalGiaoDienThi.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent windowEvent) {
                            // Cập nhật lại dữ liệu trong pnKetQuaLamBai
                            loadKetQuaLamBai(baiThiDangChon);
                        }
                    });

                    // Hiển thị giao diện thi
                    finalGiaoDienThi.taoGiaoDienThi();

                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (NotBoundException ex) {
                    throw new RuntimeException(ex);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void taoDSBaiThi() throws MalformedURLException, NotBoundException, RemoteException {
        try {
            JPanel contentPanel = new JPanel();
            contentPanel.setBackground(Color.WHITE);
            contentPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));

            List<BaiThi> dsBaiThi = baiThiService.getAllBaiThiForHocSinh(hocSinh.getMaHocSinh());

            if (dsBaiThi.isEmpty()) {
                JLabel lblThongBao = new JLabel("Hiện tại không có bài thi nào");
                lblThongBao.setFont(new Font("Arial", Font.BOLD, 18));
                lblThongBao.setForeground(new Color(100, 100, 100));
                contentPanel.add(lblThongBao);
            } else {
                for (BaiThi baiThi : dsBaiThi) {
                    contentPanel.add(thanhPhanBaiThi(baiThi));
                }
            }

            JScrollPane scrollPane = new JScrollPane(contentPanel,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            scrollPane.setBorder(BorderFactory.createEmptyBorder());

            // Update the main panel
            pnNoiDung.removeAll();
            pnNoiDung.add(scrollPane, BorderLayout.CENTER);
            pnNoiDung.revalidate();
            pnNoiDung.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi tải danh sách bài thi: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new CardLayout(0, 0));
        pnDSBaiThi = new JPanel();
        pnDSBaiThi.setLayout(new BorderLayout(0, 0));
        panel1.add(pnDSBaiThi, "Card1");
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(10, 10, 10, 10), -1, -1));
        panel2.setBackground(new Color(-2954497));
        panel2.setPreferredSize(new Dimension(203, 70));
        pnDSBaiThi.add(panel2, BorderLayout.NORTH);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Arial", Font.BOLD, 20, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-16777216));
        label1.setText("Danh sách đề thi");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pnNoiDung = new JPanel();
        pnNoiDung.setLayout(new BorderLayout(0, 0));
        pnDSBaiThi.add(pnNoiDung, BorderLayout.CENTER);
        pnTTCTBaiThi = new JPanel();
        pnTTCTBaiThi.setLayout(new BorderLayout(0, 0));
        panel1.add(pnTTCTBaiThi, "Card2");
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(8, 2, new Insets(20, 30, 10, 10), -1, -1));
        panel3.setBackground(new Color(-1247233));
        panel3.setPreferredSize(new Dimension(568, 250));
        pnTTCTBaiThi.add(panel3, BorderLayout.NORTH);
        txtTenBaiThi = new JLabel();
        txtTenBaiThi.setBackground(new Color(-2954497));
        Font txtTenBaiThiFont = this.$$$getFont$$$("Arial", Font.BOLD, 22, txtTenBaiThi.getFont());
        if (txtTenBaiThiFont != null) txtTenBaiThi.setFont(txtTenBaiThiFont);
        txtTenBaiThi.setForeground(new Color(-16636082));
        txtTenBaiThi.setHorizontalAlignment(2);
        txtTenBaiThi.setHorizontalTextPosition(2);
        txtTenBaiThi.setText("Thường kỳ Neo4j - Cypher - DHKTPM18C - Nhóm 2");
        panel3.add(txtTenBaiThi, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(524, 35), null, 0, false));
        txtMo = new JLabel();
        txtMo.setBackground(new Color(-2954497));
        Font txtMoFont = this.$$$getFont$$$("Arial", Font.PLAIN, 15, txtMo.getFont());
        if (txtMoFont != null) txtMo.setFont(txtMoFont);
        txtMo.setText("Opened: Tuesday, 11 March 2025, 5:20 PM");
        panel3.add(txtMo, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDong = new JLabel();
        txtDong.setBackground(new Color(-2954497));
        Font txtDongFont = this.$$$getFont$$$("Arial", Font.PLAIN, 15, txtDong.getFont());
        if (txtDongFont != null) txtDong.setFont(txtDongFont);
        txtDong.setText("Closed: Tuesday, 11 March 2025, 6:00 PM");
        panel3.add(txtDong, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtThoiLuong = new JLabel();
        txtThoiLuong.setBackground(new Color(-2954497));
        Font txtThoiLuongFont = this.$$$getFont$$$("Arial", Font.PLAIN, 15, txtThoiLuong.getFont());
        if (txtThoiLuongFont != null) txtThoiLuong.setFont(txtThoiLuongFont);
        txtThoiLuong.setText("Thời gian làm bài: 40 phút");
        panel3.add(txtThoiLuong, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtSoCH = new JLabel();
        txtSoCH.setBackground(new Color(-2954497));
        Font txtSoCHFont = this.$$$getFont$$$("Arial", Font.PLAIN, 15, txtSoCH.getFont());
        if (txtSoCHFont != null) txtSoCH.setFont(txtSoCHFont);
        txtSoCH.setText("Số câu hỏi");
        panel3.add(txtSoCH, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-1247233));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        button1 = new JButton();
        button1.setBackground(new Color(-14057287));
        button1.setContentAreaFilled(false);
        button1.setFocusPainted(false);
        button1.setFocusable(false);
        button1.setOpaque(true);
        button1.setText("");
        panel4.add(button1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel4.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        txtSoLanDuocPhepLamBai = new JLabel();
        txtSoLanDuocPhepLamBai.setBackground(new Color(-2954497));
        Font txtSoLanDuocPhepLamBaiFont = this.$$$getFont$$$("Arial", Font.PLAIN, 15, txtSoLanDuocPhepLamBai.getFont());
        if (txtSoLanDuocPhepLamBaiFont != null) txtSoLanDuocPhepLamBai.setFont(txtSoLanDuocPhepLamBaiFont);
        txtSoLanDuocPhepLamBai.setText("Số lần được phép làm bài:");
        panel3.add(txtSoLanDuocPhepLamBai, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        pnKetQua = new JPanel();
        pnKetQua.setLayout(new BorderLayout(0, 0));
        pnKetQua.setBackground(new Color(-1));
        pnKetQua.setForeground(new Color(-16766891));
        pnTTCTBaiThi.add(pnKetQua, BorderLayout.CENTER);
        pnKetQua.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.BELOW_TOP, this.$$$getFont$$$("Arial", Font.PLAIN, 16, pnKetQua.getFont()), null));
        pnKetQuaLamBai = new JPanel();
        pnKetQuaLamBai.setLayout(new GridLayoutManager(1, 1, new Insets(20, 0, 0, 0), -1, -1));
        pnKetQuaLamBai.setBackground(new Color(-1));
        pnKetQua.add(pnKetQuaLamBai, BorderLayout.CENTER);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 20, 0), -1, -1));
        panel5.setBackground(new Color(-1));
        pnKetQua.add(panel5, BorderLayout.NORTH);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Arial", Font.PLAIN, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Số lần làm bài");
        panel5.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(98, 41), null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel5.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(14, 41), null, 0, false));
        btnLamBaiThi = new JButton();
        btnLamBaiThi.setBackground(new Color(-16745473));
        btnLamBaiThi.setContentAreaFilled(false);
        btnLamBaiThi.setDefaultCapable(true);
        btnLamBaiThi.setFocusPainted(false);
        btnLamBaiThi.setFocusTraversalPolicyProvider(false);
        btnLamBaiThi.setFocusable(false);
        Font btnLamBaiThiFont = this.$$$getFont$$$("Arial", Font.BOLD, 18, btnLamBaiThi.getFont());
        if (btnLamBaiThiFont != null) btnLamBaiThi.setFont(btnLamBaiThiFont);
        btnLamBaiThi.setForeground(new Color(-1));
        btnLamBaiThi.setOpaque(true);
        btnLamBaiThi.setRolloverEnabled(true);
        btnLamBaiThi.setText("Làm bài thi");
        panel5.add(btnLamBaiThi, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(140, 41), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
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

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

    /**
     * Phương thức để tải kết quả làm bài của học sinh cho một bài thi cụ thể
     *
     * @param baiThi Bài thi cần tải kết quả
     */
    public void loadKetQuaLamBai(BaiThi baiThi) {
        try {


            // Lấy danh sách phiên làm bài của bài thi
            List<PhienLamBai> phienLamBaiList = phienLamBaiService.layDanhSachPhienLamBaiVaCauTraLoiTheoBaiThi(baiThi.getMaBaiThi());

            // Xóa tất cả các thành phần cũ trong panel
            pnKetQuaLamBai.removeAll();
            pnKetQuaLamBai.setLayout(new BoxLayout(pnKetQuaLamBai, BoxLayout.Y_AXIS));

            // Lọc và hiển thị các phiên làm bài của học sinh hiện tại
            boolean coPhienLamBai = false;
            for (PhienLamBai phienLamBai : phienLamBaiList) {
                if (phienLamBai.getHocSinh().getMaHocSinh() == hocSinh.getMaHocSinh()) {
                    System.out.println("Tìm thấy phiên làm bài: " + phienLamBai.getMaPhien());
                    pnKetQuaLamBai.add(new PanelKQLamBai(phienLamBai));
                    coPhienLamBai = true;
                }
            }

            // Nếu không có phiên làm bài nào, hiển thị thông báo
            if (!coPhienLamBai) {
                JLabel lblThongBao = new JLabel("Chưa có lần làm bài nào");
                lblThongBao.setFont(new Font("Arial", Font.ITALIC, 16));
                lblThongBao.setForeground(new Color(108, 117, 125));
                lblThongBao.setAlignmentX(Component.CENTER_ALIGNMENT);
                pnKetQuaLamBai.add(lblThongBao);
            }

            // Cập nhật giao diện
            pnKetQuaLamBai.revalidate();
            pnKetQuaLamBai.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi tải kết quả làm bài: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel thanhPhanBaiThi(BaiThi baiThi) {
        int soCauHoi = baiThi.getDanhSachCauHoi() != null ? baiThi.getDanhSachCauHoi().size() : 0;

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2d.setStroke(new BasicStroke(2f)); // độ dày viền 2px
                g2d.setColor(new Color(100, 100, 100, 180)); // màu viền đậm hơn
                g2d.drawRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(320, 420));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setOpaque(false);

        JLabel lblTenBaiThi = new JLabel(baiThi.getTenBaiThi(), SwingConstants.CENTER);
        lblTenBaiThi.setFont(new Font("Arial", Font.BOLD, 22));
        lblTenBaiThi.setForeground(new Color(33, 37, 41));
        lblTenBaiThi.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        panel.add(lblTenBaiThi, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        String[][] infoData = {
                {"Môn học", baiThi.getMonHoc().getTenMon()},
                {"Thời gian", baiThi.getThoiLuong() + " phút"},
                {"Số câu hỏi", String.valueOf(soCauHoi) + " câu"},
                {"Thời gian bắt đầu", chuyenDinhDangNgayGio(baiThi.getThoiGianBatDau())},
                {"Thời gian kết thúc", chuyenDinhDangNgayGio(baiThi.getThoiGianKetThuc())},
                {"Giáo viên", baiThi.getGiaoVien().getHoTen()}
        };

        for (String[] info : infoData) {
            JPanel item = new JPanel(new BorderLayout());
            item.setOpaque(false);
            item.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));

            JLabel label = new JLabel(info[0] + ": ");
            label.setFont(new Font("Arial", Font.PLAIN, 16)); // font nhỏ vừa
            label.setForeground(new Color(108, 117, 125));

            JLabel value = new JLabel(info[1]);
            value.setFont(new Font("Arial", Font.BOLD, 16));
            value.setForeground(new Color(52, 58, 64));

            item.add(label, BorderLayout.WEST);
            item.add(value, BorderLayout.CENTER);

            contentPanel.add(item);

            if (!info[0].equals("Giáo viên")) {
                JSeparator separator = new JSeparator();
                separator.setForeground(new Color(233, 236, 239));
                contentPanel.add(separator);
            }
        }

        panel.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton btnVaoThi = new JButton("Vào thi");
        btnVaoThi.setFont(new Font("Arial", Font.BOLD, 18));
        btnVaoThi.setForeground(Color.WHITE);
        btnVaoThi.setPreferredSize(new Dimension(180, 45)); // nhỏ gọn lại
        btnVaoThi.setFocusPainted(false);
        btnVaoThi.setBorderPainted(false);
        btnVaoThi.setContentAreaFilled(false);
        btnVaoThi.setOpaque(true);
        btnVaoThi.setBackground(new Color(0, 123, 255));
        btnVaoThi.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVaoThi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btnVaoThi.setBackground(new Color(0, 105, 217));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                btnVaoThi.setBackground(new Color(0, 123, 255));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                baiThiDangChon = baiThi;
                cardLayout.show(panel1, "Card2");
                txtTenBaiThi.setText(baiThi.getTenBaiThi());
                txtMo.setText("Thời gian mở đề: " + chuyenDinhDangNgayGio(baiThi.getThoiGianBatDau()));
                txtDong.setText("Thời gian đóng đề: " + chuyenDinhDangNgayGio(baiThi.getThoiGianKetThuc()));
                txtThoiLuong.setText("Thời gian làm bài: " + baiThi.getThoiLuong() + " phút");
                txtSoCH.setText("Số câu hỏi: " + soCauHoi + " câu");
                txtSoLanDuocPhepLamBai.setText("Số lần được phép làm: " + baiThi.getSoLanDuocPhepLamBai() + " lần");
                // Tải kết quả làm bài của học sinh
                loadKetQuaLamBai(baiThi);
            }
        });

        buttonPanel.add(btnVaoThi);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Thiết lập look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // Tạo frame
                JFrame frame = new JFrame("Làm Bài Thi");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1200, 800);
                frame.setLocationRelativeTo(null);
                HocSinhService hocSinhService = (HocSinhService) Naming.lookup("rmi://localhost:8081/hocSinhService");
                // Tạo instance của GiaoDienLamBaiThi
                GiaoDienLamBaiThi giaoDienLamBaiThi = new GiaoDienLamBaiThi(hocSinhService.finByID(84L));

                // Thêm vào frame
                frame.add(giaoDienLamBaiThi.$$$getRootComponent$$$());

                // Hiển thị frame
                frame.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Lỗi khởi tạo giao diện: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
