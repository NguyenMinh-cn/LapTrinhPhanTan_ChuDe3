package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import entities.TaiKhoan;
import gui.custom.RoundBorder;
import service.TaiKhoanService;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Locale;

public class GiaoDienDangNhap {
    public static String ipAddress = "localhost";
    private JPanel panel1;
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;

    private TaiKhoanService taiKhoanService = (TaiKhoanService) Naming.lookup("rmi://" + ipAddress + ":8081/taiKhoanService");;

    public GiaoDienDangNhap() throws MalformedURLException, NotBoundException, RemoteException {
        try {
            $$$setupUI$$$();
            btnDangNhap.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    xuLyDangNhap();
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối tới server RMI", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
    }


    private void xuLyDangNhap() {
        String tenDangNhap = txtTenDangNhap.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword()).trim();

        try {
            Object ketQua = taiKhoanService.dangNhap(tenDangNhap, matKhau);
            TaiKhoan taiKhoanAdmin = taiKhoanService.finByID(tenDangNhap);
            System.out.println(taiKhoanAdmin);
            if (ketQua == null) {
                if(taiKhoanAdmin == null){
                    JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu", "Đăng nhập thất bại", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Đóng cửa sổ đăng nhập
            SwingUtilities.getWindowAncestor(panel1).dispose();

            // Mở giao diện chính, truyền tài khoản vào
            JFrame frame = new JFrame("Giao diện chính");
            if (ketQua == null) {
               frame.setContentPane(new GiaoDienAdmin(taiKhoanAdmin).$$$getRootComponent$$$());
            }
            frame.setContentPane(new GiaoDienChinh(ketQua).$$$getRootComponent$$$());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLocationRelativeTo(null); // căn giữa
            frame.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi trong quá trình đăng nhập", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        try {
        JFrame frame = new JFrame("Đăng Nhập");

        frame.setContentPane(new GiaoDienDangNhap().$$$getRootComponent$$$());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // căn giữa màn hình
        frame.setVisible(true);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối tới server RMI", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;  // Dừng ngay khi không thể kết nối tới server
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
        createUIComponents();
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setBackground(new Color(-1));
        Font panel1Font = this.$$$getFont$$$("JetBrains Mono", -1, 16, panel1.getFont());
        if (panel1Font != null) panel1.setFont(panel1Font);
        panel1.setPreferredSize(new Dimension(550, 300));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 8));
        panel2.setBackground(new Color(-1));
        panel2.setForeground(new Color(-1));
        panel2.setPreferredSize(new Dimension(162, 60));
        panel1.add(panel2, BorderLayout.SOUTH);
        btnDangNhap = new JButton();
        btnDangNhap.setBackground(new Color(-12020241));
        btnDangNhap.setEnabled(true);
        Font btnDangNhapFont = this.$$$getFont$$$("Consolas", Font.BOLD, 22, btnDangNhap.getFont());
        if (btnDangNhapFont != null) btnDangNhap.setFont(btnDangNhapFont);
        btnDangNhap.setForeground(new Color(-1));
        btnDangNhap.setHorizontalTextPosition(0);
        btnDangNhap.setText("Đăng nhập");
        btnDangNhap.setVerticalAlignment(0);
        panel2.add(btnDangNhap);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-1));
        panel3.setPreferredSize(new Dimension(400, 200));
        panel1.add(panel3, BorderLayout.CENTER);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Consolas", Font.BOLD, 22, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-41585));
        label1.setText("Tên đăng nhập");
        panel3.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Consolas", Font.BOLD, 22, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-41585));
        label2.setText("Mật khẩu");
        panel3.add(label2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTenDangNhap.setCaretColor(new Color(-16777216));
        Font txtTenDangNhapFont = this.$$$getFont$$$("Consolas", -1, 22, txtTenDangNhap.getFont());
        if (txtTenDangNhapFont != null) txtTenDangNhap.setFont(txtTenDangNhapFont);
        panel3.add(txtTenDangNhap, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, -1), null, 0, false));
        txtMatKhau.setCaretColor(new Color(-16777216));
        Font txtMatKhauFont = this.$$$getFont$$$("Consolas", -1, 22, txtMatKhau.getFont());
        if (txtMatKhauFont != null) txtMatKhau.setFont(txtMatKhauFont);
        panel3.add(txtMatKhau, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, -1), null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel3.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel3.add(spacer4, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel4.setBackground(new Color(-1));
        panel4.setPreferredSize(new Dimension(755, 100));
        panel1.add(panel4, BorderLayout.NORTH);
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Consolas", Font.BOLD, 30, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-12020241));
        label3.setHorizontalAlignment(0);
        label3.setHorizontalTextPosition(0);
        label3.setPreferredSize(new Dimension(500, 40));
        label3.setText("Đăng nhập hệ thống");
        panel4.add(label3);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Consolas", Font.BOLD, 30, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-12020241));
        label4.setHorizontalAlignment(0);
        label4.setHorizontalTextPosition(0);
        label4.setPreferredSize(new Dimension(500, 40));
        label4.setText("thi trắc nghiệm");
        panel4.add(label4);
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

    private void createUIComponents() {
        txtTenDangNhap = new JTextField();
        txtTenDangNhap.setPreferredSize(new Dimension(300, 50));  // Kích thước
        txtTenDangNhap.setBorder(new RoundBorder(new Color(255, 93, 143), 20));  // Viền bo tròn
        txtMatKhau = new JPasswordField();
        txtMatKhau.setPreferredSize(new Dimension(300, 50));  // Kích thước
        txtMatKhau.setBorder(new RoundBorder(new Color(255, 93, 143), 20));

    }
}
