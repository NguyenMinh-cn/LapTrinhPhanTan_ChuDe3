package gui;

import entities.HocSinh;
import service.TaiKhoanService;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Locale;


public class GiaoDienMenuHocSinh extends JPanel {
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
    private TaiKhoanService taiKhoanService = (TaiKhoanService) Naming.lookup("rmi://192.168.1.13:8081/taiKhoanService");
    private JLabel selectedLabel = null;

    public GiaoDienMenuHocSinh(HocSinh hocSinh) throws MalformedURLException, NotBoundException, RemoteException {
        this.hocSinh = hocSinh;
        $$$setupUI$$$();
        lbTenHocSinh.setText(hocSinh.getHoTen());
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
        });
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
        lbTaiKhoan.addMouseListener(listener);
        // Xử lý sự kiện tài khoản
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
        lbDSBaiThi.addMouseListener(listener);
        lbDSBaiThi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelNoiDung.removeAll();
                try {
                    panelNoiDung.add(new GiaoDienLamBaiThi(hocSinh).$$$getRootComponent$$$());
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
        // TODO: place custom component creation code here
    }
}
