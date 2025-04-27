package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import entities.GiaoVien;
import entities.HocSinh;
import entities.TaiKhoan;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;
import service.GiaoVienService;
import service.HocSinhService;
import service.TaiKhoanService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Locale;

public class GiaoDienCaiDatTaiKhoan extends JPanel {
    private final CardLayout cardLayout;
    private JPanel panel1;
    private JButton btnTTTK;
    private JButton btnDMK;
    private JPanel panelNoiDungCaiDat;
    private JPanel panelChucNang;
    private JPanel panelTTTK;
    private JPanel panelDMK;
    private JTextField txtSDT;
    private JTextField txtHoTen;
    private JTextField txtEmail;
    private JButton lưuButton;
    private JTextField txtVaiTro;
    private JLabel lbLop;
    private JTextField txtLop;
    private JPanel pnMKHienTai;
    private JPanel pnMKMoi;
    private JPanel pnXacNhanMK;
    private JPanel pnChucNang;
    private JButton btnThayDoiMatKhau;
    private JPasswordField txtMKHT;
    private JPasswordField txtMKM;
    private JPasswordField txtXNMK;
    private JButton btnM3;
    private JButton btnM2;
    private JButton btnM1;
    private GiaoVienService giaoVienService = (GiaoVienService) Naming.lookup("rmi://localhost:8081/giaoVienService");
    private HocSinhService hocSinhService = (HocSinhService) Naming.lookup("rmi://localhost:8081/hocSinhService");
    private TaiKhoanService taiKhoanService = (TaiKhoanService) Naming.lookup("rmi://localhost:8081/taiKhoanService");
    public GiaoDienCaiDatTaiKhoan(TaiKhoan taiKhoan) throws MalformedURLException, NotBoundException, RemoteException {

        $$$setupUI$$$();
        cardLayout = (CardLayout) panelNoiDungCaiDat.getLayout();
        txtVaiTro.setText("GiaoVien".equals(taiKhoan.getLoaiTaiKhoan()) ? "Giáo viên" : "Học sinh");

        if (taiKhoan.getLoaiTaiKhoan().equalsIgnoreCase("GiaoVien")) {
            GiaoVien giaoVien = giaoVienService.timGiaoVienTheoEmail(taiKhoan.getTenDangNhap());
            txtHoTen.setText(giaoVien.getHoTen());
            txtEmail.setText(giaoVien.getEmail());
            txtSDT.setText(giaoVien.getSoDienThoai());
            panelTTTK.remove(lbLop);
            panelTTTK.remove(txtLop);
        }
        if (taiKhoan.getLoaiTaiKhoan().equalsIgnoreCase("HocSinh")) {
            HocSinh hocSinh = hocSinhService.timHocSinhTheoEmail(taiKhoan.getTenDangNhap());
            txtHoTen.setText(hocSinh.getHoTen());
            txtEmail.setText(hocSinh.getEmail());
            txtSDT.setText(hocSinh.getSoDienThoai());
            txtLop.setText(hocSinh.getLop().getTenLop());
        }
        btnTTTK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelNoiDungCaiDat.removeAll();
                panelNoiDungCaiDat.add(panelTTTK);
                panelNoiDungCaiDat.repaint();
                panelNoiDungCaiDat.revalidate();
            }
        });

        btnDMK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelNoiDungCaiDat.removeAll();
                panelNoiDungCaiDat.add(panelDMK);
                panelNoiDungCaiDat.repaint();
                panelNoiDungCaiDat.revalidate();
            }
        });
        btnM1.setIcon(FontIcon.of(MaterialDesign.MDI_EYE_OFF, 20, Color.BLACK));
        btnM1.addActionListener(new ActionListener() {
            private boolean showing = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (showing) {
                    txtMKHT.setEchoChar('\u25CF'); // Ẩn mật khẩu
                    btnM1.setIcon(FontIcon.of(MaterialDesign.MDI_EYE_OFF, 20, Color.BLACK));
                } else {
                    txtMKHT.setEchoChar((char) 0); // Hiện mật khẩu
                    btnM1.setIcon(FontIcon.of(MaterialDesign.MDI_EYE, 20, Color.BLACK));
                }
                showing = !showing;
            }
        });
        btnM2.setIcon(FontIcon.of(MaterialDesign.MDI_EYE_OFF, 20, Color.BLACK));
        btnM2.addActionListener(new ActionListener() {
            private boolean showing = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (showing) {
                    txtMKM.setEchoChar('\u25CF'); // Ẩn mật khẩu
                    btnM2.setIcon(FontIcon.of(MaterialDesign.MDI_EYE_OFF, 20, Color.BLACK));
                } else {
                    txtMKM.setEchoChar((char) 0); // Hiện mật khẩu
                    btnM2.setIcon(FontIcon.of(MaterialDesign.MDI_EYE, 20, Color.BLACK));
                }
                showing = !showing;
            }
        });
        btnM3.setIcon(FontIcon.of(MaterialDesign.MDI_EYE_OFF, 20, Color.BLACK));
        btnM3.addActionListener(new ActionListener() {
            private boolean showing = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (showing) {
                    txtXNMK.setEchoChar('\u25CF'); // Ẩn mật khẩu
                    btnM3.setIcon(FontIcon.of(MaterialDesign.MDI_EYE_OFF, 20, Color.BLACK));
                } else {
                    txtXNMK.setEchoChar((char) 0); // Hiện mật khẩu
                    btnM3.setIcon(FontIcon.of(MaterialDesign.MDI_EYE, 20, Color.BLACK));
                }
                showing = !showing;
            }
        });
        btnDMK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelNoiDungCaiDat, "Card2");
            }
        });
        btnTTTK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelNoiDungCaiDat, "Card1");
            }
        });
        btnThayDoiMatKhau.addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Lấy mật khẩu từ các trường nhập
                        String matKhauHienTai = new String(txtMKHT.getPassword());
                        String matKhauMoi = new String(txtMKM.getPassword());
                        String xacNhanMatKhau = new String(txtXNMK.getPassword());

                        // Kiểm tra các trường mật khẩu
                        if (matKhauHienTai.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Vui lòng nhập mật khẩu hiện tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            txtMKHT.requestFocus();
                            return;
                        }

                        if (matKhauMoi.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Vui lòng nhập mật khẩu mới", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            txtMKM.requestFocus();
                            return;
                        }

                        if (xacNhanMatKhau.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Vui lòng xác nhận mật khẩu mới", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            txtXNMK.requestFocus();
                            return;
                        }

                        // Kiểm tra độ dài mật khẩu mới (ít nhất 6 ký tự)
                        if (matKhauMoi.length() < 6) {
                            JOptionPane.showMessageDialog(null, "Mật khẩu mới phải có ít nhất 6 ký tự", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            txtMKM.requestFocus();
                            return;
                        }

                        // Kiểm tra mật khẩu mới và xác nhận mật khẩu có khớp nhau không
                        if (!matKhauMoi.equals(xacNhanMatKhau)) {
                            JOptionPane.showMessageDialog(null, "Mật khẩu mới và xác nhận mật khẩu không khớp", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            txtXNMK.requestFocus();
                            return;
                        }

                        // Kiểm tra mật khẩu hiện tại có đúng không
                        if (taiKhoan == null) {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin tài khoản", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!taiKhoan.getMatKhau().equals(matKhauHienTai)) {
                            JOptionPane.showMessageDialog(null, "Mật khẩu hiện tại không đúng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            txtMKHT.requestFocus();
                            return;
                        }

                        // Cập nhật mật khẩu mới
                        taiKhoan.setMatKhau(matKhauMoi);
                        boolean updated = taiKhoanService.update(taiKhoan);

                        if (updated) {
                            JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            // Xóa các trường mật khẩu
                            txtMKHT.setText("");
                            txtMKM.setText("");
                            txtXNMK.setText("");
                            // Quay lại tab thông tin tài khoản
                            cardLayout.show(panelNoiDungCaiDat, "Card1");
                        } else {
                            JOptionPane.showMessageDialog(null, "Đổi mật khẩu thất bại. Vui lòng thử lại sau.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
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
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setBackground(new Color(-1));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        panelChucNang = new JPanel();
        panelChucNang.setLayout(new BorderLayout(0, 0));
        panelChucNang.setBackground(new Color(-1));
        panelChucNang.setMaximumSize(new Dimension(500, 200));
        panelChucNang.setMinimumSize(new Dimension(450, 77));
        panelChucNang.setPreferredSize(new Dimension(200, 103));
        panel1.add(panelChucNang, BorderLayout.WEST);
        panelChucNang.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label1 = new JLabel();
        label1.setAlignmentX(0.5f);
        Font label1Font = this.$$$getFont$$$("Arial", Font.PLAIN, 20, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setHorizontalAlignment(0);
        label1.setPreferredSize(new Dimension(250, 50));
        label1.setText("Cài đặt tài khoản");
        panelChucNang.add(label1, BorderLayout.NORTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel2.setBackground(new Color(-1));
        panel2.setFocusable(false);
        panel2.setMaximumSize(new Dimension(250, 32767));
        panel2.setMinimumSize(new Dimension(200, 53));
        panel2.setPreferredSize(new Dimension(230, 53));
        panelChucNang.add(panel2, BorderLayout.CENTER);
        btnTTTK = new JButton();
        btnTTTK.setAutoscrolls(false);
        btnTTTK.setBackground(new Color(-1315861));
        btnTTTK.setFocusPainted(false);
        Font btnTTTKFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, btnTTTK.getFont());
        if (btnTTTKFont != null) btnTTTK.setFont(btnTTTKFont);
        btnTTTK.setMargin(new Insets(10, 10, 10, 10));
        btnTTTK.setPreferredSize(new Dimension(200, 43));
        btnTTTK.setText("Thông tin tài khoản");
        panel2.add(btnTTTK);
        btnDMK = new JButton();
        btnDMK.setAutoscrolls(false);
        btnDMK.setBackground(new Color(-1315861));
        btnDMK.setFocusPainted(false);
        Font btnDMKFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, btnDMK.getFont());
        if (btnDMKFont != null) btnDMK.setFont(btnDMKFont);
        btnDMK.setMargin(new Insets(10, 10, 10, 10));
        btnDMK.setMaximumSize(new Dimension(250, 43));
        btnDMK.setMinimumSize(new Dimension(200, 43));
        btnDMK.setPreferredSize(new Dimension(200, 43));
        btnDMK.setText("Đổi mật khẩu");
        panel2.add(btnDMK);
        panelNoiDungCaiDat = new JPanel();
        panelNoiDungCaiDat.setLayout(new CardLayout(0, 0));
        panelNoiDungCaiDat.setBackground(new Color(-1));
        panel1.add(panelNoiDungCaiDat, BorderLayout.CENTER);
        panelTTTK = new JPanel();
        panelTTTK.setLayout(new GridLayoutManager(7, 3, new Insets(10, 30, 10, 10), 20, 10));
        panelTTTK.setBackground(new Color(-1));
        Font panelTTTKFont = this.$$$getFont$$$("Arial", Font.BOLD, 20, panelTTTK.getFont());
        if (panelTTTKFont != null) panelTTTK.setFont(panelTTTKFont);
        panelNoiDungCaiDat.add(panelTTTK, "Card1");
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-368076));
        Font label2Font = this.$$$getFont$$$("Arial", Font.BOLD, 20, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Họ tên");
        panelTTTK.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, 10), null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelTTTK.add(spacer1, new GridConstraints(2, 2, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Arial", Font.BOLD, 20, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Email");
        panelTTTK.add(label3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Arial", Font.BOLD, 20, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Số điện thoại");
        panelTTTK.add(label4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtSDT = new JTextField();
        txtSDT.setDisabledTextColor(new Color(-16777216));
        txtSDT.setEditable(false);
        txtSDT.setEnabled(false);
        Font txtSDTFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, txtSDT.getFont());
        if (txtSDTFont != null) txtSDT.setFont(txtSDTFont);
        txtSDT.setMargin(new Insets(3, 11, 3, 8));
        txtSDT.setSelectionColor(new Color(-16777216));
        txtSDT.setText("0123456789");
        panelTTTK.add(txtSDT, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 50), null, 0, false));
        txtEmail = new JTextField();
        txtEmail.setDisabledTextColor(new Color(-16777216));
        txtEmail.setEditable(false);
        txtEmail.setEnabled(false);
        Font txtEmailFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, txtEmail.getFont());
        if (txtEmailFont != null) txtEmail.setFont(txtEmailFont);
        txtEmail.setMargin(new Insets(3, 11, 3, 8));
        txtEmail.setSelectionColor(new Color(-16777216));
        txtEmail.setText("a@gmail.com");
        panelTTTK.add(txtEmail, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 50), null, 0, false));
        txtHoTen = new JTextField();
        txtHoTen.setDisabledTextColor(new Color(-16777216));
        txtHoTen.setEditable(false);
        txtHoTen.setEnabled(false);
        Font txtHoTenFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, txtHoTen.getFont());
        if (txtHoTenFont != null) txtHoTen.setFont(txtHoTenFont);
        txtHoTen.setMargin(new Insets(3, 11, 3, 8));
        txtHoTen.setSelectionColor(new Color(-16777216));
        txtHoTen.setText("Nguyễn Văn A");
        panelTTTK.add(txtHoTen, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 50), null, 0, false));
        lưuButton = new JButton();
        lưuButton.setBackground(new Color(-16611119));
        lưuButton.setFocusPainted(false);
        lưuButton.setFocusable(false);
        Font lưuButtonFont = this.$$$getFont$$$("Arial", Font.BOLD, 20, lưuButton.getFont());
        if (lưuButtonFont != null) lưuButton.setFont(lưuButtonFont);
        lưuButton.setForeground(new Color(-1));
        lưuButton.setMargin(new Insets(10, 0, 10, 0));
        lưuButton.setText("Lưu");
        panelTTTK.add(lưuButton, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setBackground(new Color(-368076));
        Font label5Font = this.$$$getFont$$$("Arial", Font.BOLD, 20, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("Vai trò");
        panelTTTK.add(label5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, 10), null, 0, false));
        txtVaiTro = new JTextField();
        txtVaiTro.setDisabledTextColor(new Color(-16777216));
        txtVaiTro.setEditable(false);
        txtVaiTro.setEnabled(false);
        Font txtVaiTroFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, txtVaiTro.getFont());
        if (txtVaiTroFont != null) txtVaiTro.setFont(txtVaiTroFont);
        txtVaiTro.setMargin(new Insets(3, 11, 3, 8));
        txtVaiTro.setSelectionColor(new Color(-16777216));
        txtVaiTro.setText("Giáo viên");
        panelTTTK.add(txtVaiTro, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 50), null, 0, false));
        lbLop = new JLabel();
        Font lbLopFont = this.$$$getFont$$$("Arial", Font.BOLD, 20, lbLop.getFont());
        if (lbLopFont != null) lbLop.setFont(lbLopFont);
        lbLop.setText("Lớp");
        panelTTTK.add(lbLop, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtLop = new JTextField();
        txtLop.setDisabledTextColor(new Color(-16777216));
        txtLop.setEditable(false);
        txtLop.setEnabled(false);
        Font txtLopFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, txtLop.getFont());
        if (txtLopFont != null) txtLop.setFont(txtLopFont);
        txtLop.setMargin(new Insets(3, 11, 3, 8));
        txtLop.setSelectionColor(new Color(-16777216));
        txtLop.setText("10A2");
        panelTTTK.add(txtLop, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 50), null, 0, false));
        panelDMK = new JPanel();
        panelDMK.setLayout(new GridLayoutManager(4, 1, new Insets(10, 30, 10, 10), -1, -1));
        panelDMK.setBackground(new Color(-1));
        panelNoiDungCaiDat.add(panelDMK, "Card2");
        pnMKHienTai = new JPanel();
        pnMKHienTai.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        pnMKHienTai.setBackground(new Color(-1));
        panelDMK.add(pnMKHienTai, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pnMKHienTai.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("Arial", Font.BOLD, 20, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setOpaque(false);
        label6.setText("Mật khẩu hiện tại");
        pnMKHienTai.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMKHT = new JPasswordField();
        Font txtMKHTFont = this.$$$getFont$$$("Arial", Font.BOLD, 20, txtMKHT.getFont());
        if (txtMKHTFont != null) txtMKHT.setFont(txtMKHTFont);
        pnMKHienTai.add(txtMKHT, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 50), null, 0, false));
        btnM1 = new JButton();
        btnM1.setFocusPainted(false);
        btnM1.setFocusable(false);
        Font btnM1Font = this.$$$getFont$$$("Arial", Font.BOLD, 20, btnM1.getFont());
        if (btnM1Font != null) btnM1.setFont(btnM1Font);
        btnM1.setText("");
        pnMKHienTai.add(btnM1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pnChucNang = new JPanel();
        pnChucNang.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pnChucNang.setBackground(new Color(-1));
        panelDMK.add(pnChucNang, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pnChucNang.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        btnThayDoiMatKhau = new JButton();
        btnThayDoiMatKhau.setBackground(new Color(-16611119));
        btnThayDoiMatKhau.setFocusPainted(false);
        btnThayDoiMatKhau.setFocusable(false);
        Font btnThayDoiMatKhauFont = this.$$$getFont$$$("Arial", Font.BOLD, 20, btnThayDoiMatKhau.getFont());
        if (btnThayDoiMatKhauFont != null) btnThayDoiMatKhau.setFont(btnThayDoiMatKhauFont);
        btnThayDoiMatKhau.setForeground(new Color(-1));
        btnThayDoiMatKhau.setMargin(new Insets(10, 10, 10, 10));
        btnThayDoiMatKhau.setText("Thay đổi mật khẩu");
        pnChucNang.add(btnThayDoiMatKhau);
        final Spacer spacer2 = new Spacer();
        pnChucNang.add(spacer2);
        pnMKMoi = new JPanel();
        pnMKMoi.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        pnMKMoi.setBackground(new Color(-1));
        panelDMK.add(pnMKMoi, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pnMKMoi.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$("Arial", Font.BOLD, 20, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setOpaque(false);
        label7.setText("Mật khẩu mới");
        pnMKMoi.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtMKM = new JPasswordField();
        Font txtMKMFont = this.$$$getFont$$$("Arial", Font.BOLD, 20, txtMKM.getFont());
        if (txtMKMFont != null) txtMKM.setFont(txtMKMFont);
        pnMKMoi.add(txtMKM, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 50), null, 0, false));
        btnM2 = new JButton();
        btnM2.setFocusPainted(false);
        btnM2.setFocusable(false);
        Font btnM2Font = this.$$$getFont$$$("Arial", Font.BOLD, 20, btnM2.getFont());
        if (btnM2Font != null) btnM2.setFont(btnM2Font);
        btnM2.setText("");
        pnMKMoi.add(btnM2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pnXacNhanMK = new JPanel();
        pnXacNhanMK.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        pnXacNhanMK.setBackground(new Color(-1));
        panelDMK.add(pnXacNhanMK, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pnXacNhanMK.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$("Arial", Font.BOLD, 20, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setOpaque(false);
        label8.setText("Xác nhận mật khẩu mới");
        pnXacNhanMK.add(label8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtXNMK = new JPasswordField();
        Font txtXNMKFont = this.$$$getFont$$$("Arial", Font.BOLD, 20, txtXNMK.getFont());
        if (txtXNMKFont != null) txtXNMK.setFont(txtXNMKFont);
        txtXNMK.setText("");
        pnXacNhanMK.add(txtXNMK, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 50), null, 0, false));
        btnM3 = new JButton();
        btnM3.setFocusPainted(false);
        btnM3.setFocusable(false);
        Font btnM3Font = this.$$$getFont$$$("Arial", Font.BOLD, 20, btnM3.getFont());
        if (btnM3Font != null) btnM3.setFont(btnM3Font);
        btnM3.setText("");
        pnXacNhanMK.add(btnM3, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        // Khởi tạo giao diện
        JFrame frame = new JFrame("Giao diện cài đặt");
        TaiKhoanService taiKhoanService = (TaiKhoanService) Naming.lookup("rmi://localhost:8081/taiKhoanService");
        frame.setContentPane(new GiaoDienCaiDatTaiKhoan(taiKhoanService.finByID("buidungmai@gmail.com")).$$$getRootComponent$$$());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
