package gui;

import entities.GiaoVien;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Locale;

public class GiaoDienMenuGiaoVien {
    private GiaoVien giaoVienDangNhap;
    private JPanel panel1;
    private JPanel panelNoiDung;
    private JLabel lbTenGiaoVien;
    private JLabel lbNganHangCauHoi;
    private JLabel lbDSDeThi;
    private JLabel lbLopHocTap;
    private JLabel lbCaiDatTaiKhoan;
    private JLabel lbDangXuat;
    JLabel selectedLabel = null;

    public GiaoDienMenuGiaoVien(GiaoVien giaoVien) {
        this.giaoVienDangNhap = giaoVien;
        $$$setupUI$$$();
        lbTenGiaoVien.setText(giaoVien.getHoTen());
        lbCaiDatTaiKhoan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelNoiDung.removeAll();
                panelNoiDung.add(new GiaoDienCaiDatTaiKhoan().$$$getRootComponent$$$());
                panelNoiDung.revalidate();
                panelNoiDung.repaint();
            }
        });
        lbDangXuat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn đăng xuất?",
                        "Xác nhận đăng xuất",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Đóng cửa sổ hiện tại (GiaoDienChinh) và mở lại GiaoDienDangNhap
                    SwingUtilities.getWindowAncestor(panel1).dispose(); // Đóng cửa sổ hiện tại

                    JFrame frame = new JFrame("Đăng Nhập");
                    frame.setContentPane(new GiaoDienDangNhap().$$$getRootComponent$$$());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            }
        });

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
//        lbNganHangCauHoi.addMouseListener(listener);
//        lbDSDeThi.addMouseListener(listener);
//        lbLopHocTap.addMouseListener(listener);
//        lbCaiDatTaiKhoan.addMouseListener(listener);
        lbDangXuat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Component c = (Component) e.getSource();
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    label.setForeground(Color.RED);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Component c = (Component) e.getSource();
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    label.setForeground(Color.BLACK);
                }
            }


        });

        lbNganHangCauHoi.addMouseListener(listener);
        lbDSDeThi.addMouseListener(listener);
        lbLopHocTap.addMouseListener(listener);
        lbCaiDatTaiKhoan.addMouseListener(listener);
        lbDSDeThi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelNoiDung.removeAll();
                try {
                    panelNoiDung.add(new GiaoDienDanhSachBaiThi(giaoVienDangNhap).$$$getRootComponent$$$());
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
    }

//    {
//// GUI initializer generated by IntelliJ IDEA GUI Designer
//// >>> IMPORTANT!! <<<
//// DO NOT EDIT OR ADD ANY CODE HERE!
//        $$$setupUI$$$();
//    }

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
        panel1.setBackground(new Color(-3608842));
        panel1.setPreferredSize(new Dimension(1000, 800));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(10, 0));
        panel2.setBackground(new Color(-3543049));
        panel2.setPreferredSize(new Dimension(250, 10));
        panel1.add(panel2, BorderLayout.WEST);
        lbTenGiaoVien = new JLabel();
        lbTenGiaoVien.setBackground(new Color(-12020241));
        Font lbTenGiaoVienFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, lbTenGiaoVien.getFont());
        if (lbTenGiaoVienFont != null) lbTenGiaoVien.setFont(lbTenGiaoVienFont);
        lbTenGiaoVien.setForeground(new Color(-394759));
        lbTenGiaoVien.setHorizontalAlignment(0);
        lbTenGiaoVien.setHorizontalTextPosition(0);
        lbTenGiaoVien.setOpaque(true);
        lbTenGiaoVien.setPreferredSize(new Dimension(200, 70));
        lbTenGiaoVien.setText("Tên giáo viên");
        panel2.add(lbTenGiaoVien, BorderLayout.NORTH);
        lbDangXuat = new JLabel();
        lbDangXuat.setAlignmentX(0.5f);
        Font lbDangXuatFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, lbDangXuat.getFont());
        if (lbDangXuatFont != null) lbDangXuat.setFont(lbDangXuatFont);
        lbDangXuat.setForeground(new Color(-16777216));
        lbDangXuat.setHorizontalAlignment(0);
        lbDangXuat.setHorizontalTextPosition(0);
        lbDangXuat.setPreferredSize(new Dimension(71, 50));
        lbDangXuat.setText("Đăng xuất");
        panel2.add(lbDangXuat, BorderLayout.SOUTH);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel3.setBackground(new Color(-3543049));
        panel2.add(panel3, BorderLayout.CENTER);
        lbNganHangCauHoi = new JLabel();
        lbNganHangCauHoi.setAlignmentX(0.2f);
        lbNganHangCauHoi.setBackground(new Color(-3543049));
        Font lbNganHangCauHoiFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, lbNganHangCauHoi.getFont());
        if (lbNganHangCauHoiFont != null) lbNganHangCauHoi.setFont(lbNganHangCauHoiFont);
        lbNganHangCauHoi.setForeground(new Color(-16777216));
        lbNganHangCauHoi.setHorizontalAlignment(2);
        lbNganHangCauHoi.setHorizontalTextPosition(0);
        lbNganHangCauHoi.setOpaque(true);
        lbNganHangCauHoi.setPreferredSize(new Dimension(200, 30));
        lbNganHangCauHoi.setText("Ngân hàng câu hỏi");
        panel3.add(lbNganHangCauHoi);
        lbDSDeThi = new JLabel();
        lbDSDeThi.setAlignmentX(0.2f);
        lbDSDeThi.setBackground(new Color(-3543049));
        Font lbDSDeThiFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, lbDSDeThi.getFont());
        if (lbDSDeThiFont != null) lbDSDeThi.setFont(lbDSDeThiFont);
        lbDSDeThi.setForeground(new Color(-16777216));
        lbDSDeThi.setHorizontalAlignment(2);
        lbDSDeThi.setHorizontalTextPosition(0);
        lbDSDeThi.setOpaque(true);
        lbDSDeThi.setPreferredSize(new Dimension(200, 30));
        lbDSDeThi.setText("Danh sách đề thi");
        panel3.add(lbDSDeThi);
        lbLopHocTap = new JLabel();
        lbLopHocTap.setAlignmentX(0.2f);
        lbLopHocTap.setBackground(new Color(-3543049));
        Font lbLopHocTapFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, lbLopHocTap.getFont());
        if (lbLopHocTapFont != null) lbLopHocTap.setFont(lbLopHocTapFont);
        lbLopHocTap.setForeground(new Color(-16777216));
        lbLopHocTap.setHorizontalAlignment(2);
        lbLopHocTap.setHorizontalTextPosition(0);
        lbLopHocTap.setOpaque(true);
        lbLopHocTap.setPreferredSize(new Dimension(200, 30));
        lbLopHocTap.setText("Lớp học tập");
        panel3.add(lbLopHocTap);
        lbCaiDatTaiKhoan = new JLabel();
        lbCaiDatTaiKhoan.setAlignmentX(0.2f);
        lbCaiDatTaiKhoan.setBackground(new Color(-3543049));
        Font lbCaiDatTaiKhoanFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, lbCaiDatTaiKhoan.getFont());
        if (lbCaiDatTaiKhoanFont != null) lbCaiDatTaiKhoan.setFont(lbCaiDatTaiKhoanFont);
        lbCaiDatTaiKhoan.setForeground(new Color(-16777216));
        lbCaiDatTaiKhoan.setHorizontalAlignment(2);
        lbCaiDatTaiKhoan.setHorizontalTextPosition(0);
        lbCaiDatTaiKhoan.setOpaque(true);
        lbCaiDatTaiKhoan.setPreferredSize(new Dimension(200, 30));
        lbCaiDatTaiKhoan.setText("Tài khoản");
        panel3.add(lbCaiDatTaiKhoan);
        panelNoiDung = new JPanel();
        panelNoiDung.setLayout(new BorderLayout(0, 0));
        panel1.add(panelNoiDung, BorderLayout.CENTER);
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

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new GiaoDienMenuGiaoVien(new GiaoVien(0, "Minh", "abc@gmail,com", "0971770425", null, null)).$$$getRootComponent$$$());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // căn giữa màn hình
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
