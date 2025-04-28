package gui;

import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Locale;

public class GiaoDienAdmin {
    private JPanel panel1;
    private JPanel pnNoiDung;
    private JLabel chucNangTK;
    private JLabel chucNangLopHoc;
    private JLabel chucNangMonHoc;
    private JLabel lbDangXuat;
    private JLabel selectedLabel;

    public GiaoDienAdmin() {
        $$$setupUI$$$();

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
        chucNangTK.addMouseListener(listener);
        chucNangLopHoc.addMouseListener(listener);
        chucNangMonHoc.addMouseListener(listener);
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

        chucNangTK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pnNoiDung.removeAll();
                try {
                    pnNoiDung.add(new GiaoDienQuanLyTaiKhoan(), BorderLayout.CENTER);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (NotBoundException ex) {
                    throw new RuntimeException(ex);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                pnNoiDung.repaint();
                pnNoiDung.revalidate();
            }
        });
    }
//
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
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel1.add(panel2, BorderLayout.WEST);
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-12020241));
        Font label1Font = this.$$$getFont$$$("Arial", Font.PLAIN, 20, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-1));
        label1.setHorizontalAlignment(0);
        label1.setOpaque(true);
        label1.setPreferredSize(new Dimension(96, 70));
        label1.setText("Tên giáo viên");
        panel2.add(label1, BorderLayout.NORTH);
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
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel3.setBackground(new Color(-3543049));
        panel3.setPreferredSize(new Dimension(200, 29));
        panel2.add(panel3, BorderLayout.CENTER);
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        chucNangTK = new JLabel();
        Font chucNangTKFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, chucNangTK.getFont());
        if (chucNangTKFont != null) chucNangTK.setFont(chucNangTKFont);
        chucNangTK.setPreferredSize(new Dimension(200, 19));
        chucNangTK.setText("   Tài khoản");
        panel3.add(chucNangTK);
        chucNangLopHoc = new JLabel();
        Font chucNangLopHocFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, chucNangLopHoc.getFont());
        if (chucNangLopHocFont != null) chucNangLopHoc.setFont(chucNangLopHocFont);
        chucNangLopHoc.setPreferredSize(new Dimension(200, 19));
        chucNangLopHoc.setText("   Lớp học");
        panel3.add(chucNangLopHoc);
        chucNangMonHoc = new JLabel();
        Font chucNangMonHocFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, chucNangMonHoc.getFont());
        if (chucNangMonHocFont != null) chucNangMonHoc.setFont(chucNangMonHocFont);
        chucNangMonHoc.setPreferredSize(new Dimension(200, 19));
        chucNangMonHoc.setText("   Môn học");
        panel3.add(chucNangMonHoc);
        pnNoiDung = new JPanel();
        pnNoiDung.setLayout(new BorderLayout(0, 0));
        pnNoiDung.setBackground(new Color(-1));
        panel1.add(pnNoiDung, BorderLayout.CENTER);
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
        try {
            JFrame frame = new JFrame("Đăng Nhập");

            frame.setContentPane(new GiaoDienAdmin().$$$getRootComponent$$$());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // căn giữa màn hình
            frame.setVisible(true);
            frame.setSize(1200, 750);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối tới server RMI", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;  // Dừng ngay khi không thể kết nối tới server
        }
    }
}
