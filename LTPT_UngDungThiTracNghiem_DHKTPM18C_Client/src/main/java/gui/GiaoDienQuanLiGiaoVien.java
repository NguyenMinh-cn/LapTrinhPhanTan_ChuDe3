package gui;

import daos.GiaoVienDAO;
import entities.GiaoVien;
import entities.TaiKhoan;
import service.GiaoVienService;
import service.TaiKhoanService;
import service.impl.GiaoVienServiceImpl;
import service.impl.TaiKhoanServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class GiaoDienQuanLyGiaoVien extends JPanel {
    private JPanel panel1;
    private JTextField txtHoTen, txtEmail, txtSoDienThoai, txtMatKhau;
    private JButton btnThem, btnChinhSua, btnXoa;
    private JTable tblGiaoVien;
    private DefaultTableModel tableModel;
    private GiaoVienService giaoVienService;
    private TaiKhoanService taiKhoanService;

    public GiaoDienQuanLyGiaoVien(GiaoVienService giaoVienService, TaiKhoanService taiKhoanService) {
        this.giaoVienService = giaoVienService;
        this.taiKhoanService = taiKhoanService;

        String[] columns = {"Mã giáo viên", "Họ tên", "Email", "Số điện thoại"};
        tableModel = new DefaultTableModel(columns, 0);
        tblGiaoVien = new JTable(tableModel);

        taoGiaoDien();
        loadAllGiaoVien();

        // Thêm giáo viên
        btnThem.addActionListener(e -> {
            String hoTen = txtHoTen.getText().trim();
            String email = txtEmail.getText().trim();
            String soDienThoai = txtSoDienThoai.getText().trim();
            String matKhau = txtMatKhau.getText().trim();

            if (!hoTen.isEmpty() && !email.isEmpty() && !soDienThoai.isEmpty() && !matKhau.isEmpty()) {
                try {
                    // Tạo tài khoản
                    TaiKhoan taiKhoan = new TaiKhoan(email, "GiaoVien", matKhau);
                    if (taiKhoanService.save(taiKhoan)) {
                        // Tạo giáo viên
                        GiaoVien giaoVien = new GiaoVien();
                        giaoVien.setHoTen(hoTen);
                        giaoVien.setEmail(email);
                        giaoVien.setSoDienThoai(soDienThoai);
                        giaoVien.setTaiKhoan(taiKhoan);

                        if (giaoVienService.save(giaoVien)) {
                            tableModel.addRow(new Object[]{
                                    giaoVien.getMaGiaoVien(),
                                    hoTen,
                                    email,
                                    soDienThoai
                            });
                            txtHoTen.setText("");
                            txtEmail.setText("");
                            txtSoDienThoai.setText("");
                            txtMatKhau.setText("");
                            JOptionPane.showMessageDialog(this, "Thêm giáo viên thành công!");
                        } else {
                            taiKhoanService.delete(email); // Rollback nếu thêm giáo viên thất bại
                            JOptionPane.showMessageDialog(this, "Không thể thêm giáo viên.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể tạo tài khoản.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi thêm giáo viên: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            }
        });

        // Chỉnh sửa giáo viên
        btnChinhSua.addActionListener(e -> {
            int selectedRow = tblGiaoVien.getSelectedRow();
            if (selectedRow != -1) {
                String maGiaoVien = tableModel.getValueAt(selectedRow, 0).toString();
                String hoTen = tableModel.getValueAt(selectedRow, 1).toString();
                String email = tableModel.getValueAt(selectedRow, 2).toString();
                String soDienThoai = tableModel.getValueAt(selectedRow, 3).toString();
                showEditDialog(maGiaoVien, hoTen, email, soDienThoai, selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Chọn một giáo viên để chỉnh sửa.");
            }
        });

        // Xóa giáo viên và tài khoản
        btnXoa.addActionListener(e -> {
            int selectedRow = tblGiaoVien.getSelectedRow();
            if (selectedRow != -1) {
                String email = tableModel.getValueAt(selectedRow, 2).toString();
                String maGiaoVien = tableModel.getValueAt(selectedRow, 0).toString();
                try {
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Bạn có chắc chắn muốn xóa giáo viên với email " + email + "? Các bài thi liên quan cũng sẽ bị xóa.",
                            "Xóa giáo viên", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        // Xóa giáo viên (tự động xóa các BaiThi liên quan do cascade)
                        int maGiaoVienInt = Integer.parseInt(maGiaoVien);
                        if (giaoVienService.delete(maGiaoVienInt)) {
                            // Sau đó xóa tài khoản
                            if (taiKhoanService.delete(email)) {
                                tableModel.removeRow(selectedRow);
                                JOptionPane.showMessageDialog(this, "Xóa giáo viên và tài khoản thành công!");
                            } else {
                                JOptionPane.showMessageDialog(this, "Không thể xóa tài khoản.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Không thể xóa giáo viên.");
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa giáo viên: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chọn một giáo viên để xóa.");
            }
        });
    }

    private void loadAllGiaoVien() {
        try {
            List<GiaoVien> giaoVienList = giaoVienService.getAll();
            for (GiaoVien giaoVien : giaoVienList) {
                tableModel.addRow(new Object[]{
                        giaoVien.getMaGiaoVien(),
                        giaoVien.getHoTen(),
                        giaoVien.getEmail(),
                        giaoVien.getSoDienThoai()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu giáo viên: " + e.getMessage());
        }
    }

    private void showEditDialog(String maGiaoVien, String hoTen, String email, String soDienThoai, int row) {
        JDialog editDialog = new JDialog((Frame) null, "Chỉnh sửa thông tin giáo viên", true);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelMaGiaoVien = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMaGiaoVien.add(new JLabel("Mã giáo viên:"));
        JTextField txtEditMaGiaoVien = new JTextField(maGiaoVien);
        txtEditMaGiaoVien.setPreferredSize(new Dimension(200, 30));
        txtEditMaGiaoVien.setEnabled(false);
        panelMaGiaoVien.add(txtEditMaGiaoVien);
        contentPanel.add(panelMaGiaoVien);

        JPanel panelHoTen = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelHoTen.add(new JLabel("Họ tên:"));
        JTextField txtEditHoTen = new JTextField(hoTen);
        txtEditHoTen.setPreferredSize(new Dimension(200, 30));
        panelHoTen.add(txtEditHoTen);
        contentPanel.add(panelHoTen);

        JPanel panelEmail = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEmail.add(new JLabel("Email:"));
        JTextField txtEditEmail = new JTextField(email);
        txtEditEmail.setPreferredSize(new Dimension(200, 30));
        txtEditEmail.setEnabled(false);
        panelEmail.add(txtEditEmail);
        contentPanel.add(panelEmail);

        JPanel panelSoDienThoai = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSoDienThoai.add(new JLabel("Số điện thoại:"));
        JTextField txtEditSoDienThoai = new JTextField(soDienThoai);
        txtEditSoDienThoai.setPreferredSize(new Dimension(200, 30));
        panelSoDienThoai.add(txtEditSoDienThoai);
        contentPanel.add(panelSoDienThoai);

        JButton btnSave = new JButton("Lưu");
        btnSave.setPreferredSize(new Dimension(80, 30));
        btnSave.addActionListener(e -> {
            String newHoTen = txtEditHoTen.getText().trim();
            String newSoDienThoai = txtEditSoDienThoai.getText().trim();

            if (!newHoTen.isEmpty() && !newSoDienThoai.isEmpty()) {
                try {
                    int maGiaoVienInt = Integer.parseInt(maGiaoVien);
                    GiaoVien updatedGiaoVien = new GiaoVien();
                    updatedGiaoVien.setMaGiaoVien(maGiaoVienInt);
                    updatedGiaoVien.setHoTen(newHoTen);
                    updatedGiaoVien.setEmail(email);
                    updatedGiaoVien.setSoDienThoai(newSoDienThoai);

                    if (giaoVienService.update(updatedGiaoVien)) {
                        tableModel.setValueAt(newHoTen, row, 1);
                        tableModel.setValueAt(newSoDienThoai, row, 3);
                        JOptionPane.showMessageDialog(editDialog, "Cập nhật thành công!");
                        editDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(editDialog, "Không thể cập nhật giáo viên.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(editDialog, "Mã giáo viên phải là số nguyên.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(editDialog, "Lỗi khi cập nhật: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(editDialog, "Vui lòng nhập đầy đủ thông tin");
            }
        });

        contentPanel.add(btnSave);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        editDialog.add(contentPanel);
        editDialog.setSize(350, 250);
        editDialog.setLocationRelativeTo(null);
        editDialog.setVisible(true);
    }

    private void taoGiaoDien() {
        panel1 = new JPanel(new BorderLayout(10, 10));
        panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JLabel lblHoTen = new JLabel("Họ tên:");
        txtHoTen = new JTextField(15);
        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(15);
        JLabel lblSoDienThoai = new JLabel("Số ĐT:");
        txtSoDienThoai = new JTextField(10);
        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        txtMatKhau = new JTextField(10);

        btnThem = new JButton("Thêm");
        btnChinhSua = new JButton("Chỉnh sửa");
        btnXoa = new JButton("Xóa");

        inputPanel.add(lblHoTen);
        inputPanel.add(txtHoTen);
        inputPanel.add(lblEmail);
        inputPanel.add(txtEmail);
        inputPanel.add(lblSoDienThoai);
        inputPanel.add(txtSoDienThoai);
        inputPanel.add(lblMatKhau);
        inputPanel.add(txtMatKhau);
        inputPanel.add(btnThem);
        inputPanel.add(btnChinhSua);
        inputPanel.add(btnXoa);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách giáo viên"));
        tblGiaoVien.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tblGiaoVien);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        panel1.add(inputPanel, BorderLayout.NORTH);
        panel1.add(tablePanel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(900, 500));
        add(panel1);
    }

    public JComponent layPanelChinh() {
        return panel1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GiaoVienService giaoVienService = null;
            TaiKhoanService taiKhoanService = null;
            try {
                giaoVienService = (GiaoVienService) Naming.lookup("rmi://localhost:8081/giaoVienService");
                taiKhoanService = (TaiKhoanService) Naming.lookup("rmi://localhost:8081/taiKhoanService");
            } catch (NotBoundException | MalformedURLException | RemoteException e) {
                throw new RuntimeException(e);
            }

            JFrame frame = new JFrame("Quản lý giáo viên");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(950, 600);
            frame.setLocationRelativeTo(null);
            frame.add(new GiaoDienQuanLyGiaoVien(giaoVienService, taiKhoanService));
            frame.setVisible(true);
        });
    }
}