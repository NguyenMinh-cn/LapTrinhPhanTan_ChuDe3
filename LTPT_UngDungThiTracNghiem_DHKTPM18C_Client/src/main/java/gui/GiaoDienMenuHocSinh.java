package gui;

import entities.HocSinh;
import service.TaiKhoanService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class GiaoDienMenuHocSinh extends JPanel {
    private HocSinh hocSinh;
    private JPanel panel1;
    private JPanel panelCaiDatTaiKhoan;
    private JPanel panelDanhSachBaiThi;
    private JPanel panelNoiDung;
    private JLabel lbTenHocSinh;
    private JLabel lbDangXuat;
    private JLabel lbTaiKhoan;
    private JLabel lbDanhSachBaiThi;
    private JPanel pnDangXuat;
    private TaiKhoanService taiKhoanService = (TaiKhoanService) Naming.lookup("rmi://localhost:8081/taiKhoanService");
    public GiaoDienMenuHocSinh(HocSinh hocSinh) throws MalformedURLException, NotBoundException, RemoteException {
        this.hocSinh = hocSinh;
        $$$setupUI$$$();
        lbTenHocSinh.setText(hocSinh.getHoTen());

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
                    frame.setContentPane(new GiaoDienDangNhap().$$$getRootComponent$$$());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            }
        });

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

        // Xử lý sự kiện danh sách bài thi
        lbDanhSachBaiThi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelNoiDung.removeAll();
                panelNoiDung.add(new GiaoDienXemDanhSachBaiThi(hocSinh).$$$getRootComponent$$$());
                panelNoiDung.revalidate();
                panelNoiDung.repaint();
            }
        });
    }

    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setBackground(new Color(-6106369));
        panel2.setPreferredSize(new Dimension(200, 20));
        panel1.add(panel2, BorderLayout.WEST);
        lbTenHocSinh = new JLabel();
        lbTenHocSinh.setBackground(new Color(-6106369));
        lbTenHocSinh.setHorizontalAlignment(0);
        lbTenHocSinh.setHorizontalTextPosition(0);
        lbTenHocSinh.setPreferredSize(new Dimension(200, 50));
        lbTenHocSinh.setText("Label");
        panel2.add(lbTenHocSinh, BorderLayout.NORTH);
        lbDangXuat = new JLabel();
        lbDangXuat.setHorizontalAlignment(0);
        lbDangXuat.setPreferredSize(new Dimension(71, 30));
        lbDangXuat.setText("Đăng xuất");
        panel2.add(lbDangXuat, BorderLayout.SOUTH);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel3.setBackground(new Color(-6106369));
        panel2.add(panel3, BorderLayout.CENTER);
        lbDanhSachBaiThi = new JLabel();
        lbDanhSachBaiThi.setBackground(new Color(-4333314));
        lbDanhSachBaiThi.setHorizontalAlignment(0);
        lbDanhSachBaiThi.setOpaque(true);
        lbDanhSachBaiThi.setPreferredSize(new Dimension(200, 30));
        lbDanhSachBaiThi.setText("Danh sách bài thi");
        panel3.add(lbDanhSachBaiThi);
        lbTaiKhoan = new JLabel();
        lbTaiKhoan.setBackground(new Color(-4333314));
        lbTaiKhoan.setHorizontalAlignment(0);
        lbTaiKhoan.setOpaque(true);
        lbTaiKhoan.setPreferredSize(new Dimension(200, 30));
        lbTaiKhoan.setText("Tài khoản");
        panel3.add(lbTaiKhoan);
        panelNoiDung = new JPanel();
        panelNoiDung.setLayout(new BorderLayout(0, 0));
        panel1.add(panelNoiDung, BorderLayout.CENTER);
    }

    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
