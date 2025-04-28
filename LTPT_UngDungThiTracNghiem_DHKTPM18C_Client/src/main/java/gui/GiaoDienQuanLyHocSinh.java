package gui;

import daos.HocSinhDAO;
import daos.LopDAO;
import daos.MonHocDAO;
import daos.TaiKhoanDAO;
import entities.HocSinh;
import entities.Lop;
import entities.MonHoc;
import entities.TaiKhoan;
import service.HocSinhService;
import service.LopService;
import service.MonHocService;
import service.TaiKhoanService;
import service.impl.HocSinhServiceImpl;
import service.impl.LopServiceImpl;
import service.impl.MonHocServiceImpl;
import service.impl.TaiKhoanServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class GiaoDienQuanLyHocSinh extends JPanel {
    private JPanel panel1;
    private JTextField txtHoTen, txtEmail, txtSoDienThoai, txtMatKhau;
    private JButton btnThem, btnChinhSua, btnXoa;
    private JTable tblHocSinh;
    private DefaultTableModel tableModel;
    private HocSinhService hocSinhService;
    private TaiKhoanService taiKhoanService;
    private JComboBox<String> cboLop; // ComboBox để chọn lớp

    public GiaoDienQuanLyHocSinh(HocSinhService hocSinhService, TaiKhoanService taiKhoanService, LopService lopService) {
        this.hocSinhService = hocSinhService;
        this.taiKhoanService = taiKhoanService;

        String[] columns = {"Mã học sinh", "Họ tên", "Email", "Số điện thoại", "Mã lớp"};
        tableModel = new DefaultTableModel(columns, 0);
        tblHocSinh = new JTable(tableModel);

        taoGiaoDien();
        loadAllHocSinh();
        loadLopToComboBox(lopService);  // Tải lớp vào ComboBox

//         Thêm học sinh
        btnThem.addActionListener(e -> {
            String hoTen = txtHoTen.getText().trim();
            String email = txtEmail.getText().trim();
            String soDienThoai = txtSoDienThoai.getText().trim();
            String matKhau = txtMatKhau.getText().trim();
            String selectedLop = (String) cboLop.getSelectedItem();

            if (hoTen.isEmpty() || email.isEmpty() || soDienThoai.isEmpty() || matKhau.isEmpty() || selectedLop == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Lấy mã lớp
                String maLop = selectedLop.split(" - ")[0].trim();

                // Tạo tài khoản
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setTenDangNhap(email);
                taiKhoan.setMatKhau(matKhau);
                taiKhoan.setLoaiTaiKhoan("hocSinh");

                // Lưu tài khoản
                if (!taiKhoanService.save(taiKhoan)) {
                    JOptionPane.showMessageDialog(this, "Không thể tạo tài khoản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Tạo học sinh
                HocSinh hocSinh = new HocSinh();
                hocSinh.setHoTen(hoTen);
                hocSinh.setEmail(email);
                hocSinh.setSoDienThoai(soDienThoai);

                // Gán đối tượng Lop
                Lop lop = new Lop();
                lop.setMaLop(Integer.parseInt(maLop));
                hocSinh.setLop(lop);

                // Lưu học sinh
                if (hocSinhService.save(hocSinh)) {
                    tableModel.addRow(new Object[]{
                            hocSinh.getMaHocSinh(),
                            hoTen,
                            email,
                            soDienThoai,
                            maLop
                    });
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Thêm học sinh thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể thêm học sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });



        // Chỉnh sửa học sinh
        // Chỉnh sửa học sinh
        btnChinhSua.addActionListener(e -> {
            int selectedRow = tblHocSinh.getSelectedRow();
            if (selectedRow != -1) {
                String maHocSinh = tableModel.getValueAt(selectedRow, 0).toString();
                String hoTen = tableModel.getValueAt(selectedRow, 1).toString();
                String email = tableModel.getValueAt(selectedRow, 2).toString();
                String soDienThoai = tableModel.getValueAt(selectedRow, 3).toString();
                String maLop = tableModel.getValueAt(selectedRow, 4).toString();
                showEditDialog(maHocSinh, hoTen, email, soDienThoai, maLop, selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Chọn một học sinh để chỉnh sửa.");
            }
        });

        // Xóa học sinh và tài khoản
        // Sự kiện nút Xóa học sinh và tài khoản
        btnXoa.addActionListener(e -> {
            int selectedRow = tblHocSinh.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một học sinh để xóa.");
                return;
            }

            String email = tableModel.getValueAt(selectedRow, 2).toString();
            String maHocSinhStr = tableModel.getValueAt(selectedRow, 0).toString();

            try {
                int maHocSinh = Integer.parseInt(maHocSinhStr);

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn xóa học sinh với email \"" + email + "\"?",
                        "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

                if (confirm != JOptionPane.YES_OPTION) return;

                // Xóa học sinh
                boolean isHocSinhDeleted = hocSinhService.delete(maHocSinh);

                if (!isHocSinhDeleted) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể xóa học sinh. Có thể học sinh đang có dữ liệu liên quan như bài thi hoặc chủ đề.",
                            "Lỗi xóa học sinh", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Xóa tài khoản nếu học sinh đã được xóa
                boolean isAccountDeleted = taiKhoanService.delete(email);

                if (!isAccountDeleted) {
                    JOptionPane.showMessageDialog(this,
                            "Học sinh đã được xóa, nhưng không thể xóa tài khoản.",
                            "Lỗi xóa tài khoản", JOptionPane.WARNING_MESSAGE);
                }

                // Xóa khỏi bảng
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Xóa học sinh và tài khoản thành công!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Mã học sinh không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace(); // Ghi log
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi xóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private void loadAllHocSinh() {
        try {
            List<HocSinh> hocSinhList = hocSinhService.getAll();
            for (HocSinh hocSinh : hocSinhList) {
                Lop lop = hocSinh.getLop(); // lấy đối tượng lớp
                String maLop = (lop != null) ? String.valueOf(lop.getMaLop()) : ""; // nếu có lớp thì lấy mã, không thì để trống

                tableModel.addRow(new Object[]{
                        hocSinh.getMaHocSinh(),
                        hocSinh.getHoTen(),
                        hocSinh.getEmail(),
                        hocSinh.getSoDienThoai(),
                        maLop
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu học sinh: " + e.getMessage());
        }
    }


    private void loadLopToComboBox(LopService lopService) {
        try {
            List<Lop> lopList = lopService.getAll();
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
            for (Lop lop : lopList) {
                comboBoxModel.addElement(lop.getMaLop() + " - " + lop.getTenLop()); // Mã lớp + Tên lớp
            }
            cboLop.setModel(comboBoxModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu lớp: " + e.getMessage());
        }
    }

    private void showEditDialog(String maHocSinh, String hoTen, String email, String soDienThoai, String maLop, int row) {
        JDialog editDialog = new JDialog((Frame) null, "Chỉnh sửa thông tin học sinh", true);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Mã học sinh - không cho phép chỉnh sửa
        JPanel panelMaHocSinh = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMaHocSinh.add(new JLabel("Mã học sinh:"));
        JTextField txtEditMaHocSinh = new JTextField(maHocSinh);
        txtEditMaHocSinh.setPreferredSize(new Dimension(200, 30));
        txtEditMaHocSinh.setEnabled(false);
        panelMaHocSinh.add(txtEditMaHocSinh);
        contentPanel.add(panelMaHocSinh);

        // Họ tên
        JPanel panelHoTen = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelHoTen.add(new JLabel("Họ tên:"));
        JTextField txtEditHoTen = new JTextField(hoTen);
        txtEditHoTen.setPreferredSize(new Dimension(200, 30));
        panelHoTen.add(txtEditHoTen);
        contentPanel.add(panelHoTen);

        // Email - không cho phép chỉnh sửa
        JPanel panelEmail = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEmail.add(new JLabel("Email:"));
        JTextField txtEditEmail = new JTextField(email);
        txtEditEmail.setPreferredSize(new Dimension(200, 30));
        txtEditEmail.setEnabled(false);
        panelEmail.add(txtEditEmail);
        contentPanel.add(panelEmail);

        // Số điện thoại
        JPanel panelSoDienThoai = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSoDienThoai.add(new JLabel("Số điện thoại:"));
        JTextField txtEditSoDienThoai = new JTextField(soDienThoai);
        txtEditSoDienThoai.setPreferredSize(new Dimension(200, 30));
        panelSoDienThoai.add(txtEditSoDienThoai);
        contentPanel.add(panelSoDienThoai);

        // Mã lớp - có thể chỉnh sửa
        JPanel panelMaLop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMaLop.add(new JLabel("Mã lớp:"));
        JTextField txtEditMaLop = new JTextField(maLop);
        txtEditMaLop.setPreferredSize(new Dimension(200, 30));
        panelMaLop.add(txtEditMaLop);
        contentPanel.add(panelMaLop);

        // Lưu button
        JButton btnSave = new JButton("Lưu");
        btnSave.setPreferredSize(new Dimension(80, 30));
        btnSave.addActionListener(e -> {
            String newHoTen = txtEditHoTen.getText().trim();
            String newSoDienThoai = txtEditSoDienThoai.getText().trim();
            String newMaLop = txtEditMaLop.getText().trim();

            if (!newHoTen.isEmpty() && !newSoDienThoai.isEmpty() && !newMaLop.isEmpty()) {
                try {
                    int maHocSinhInt = Integer.parseInt(maHocSinh);
                    HocSinh updatedHocSinh = new HocSinh();
                    updatedHocSinh.setMaHocSinh(maHocSinhInt);
                    updatedHocSinh.setHoTen(newHoTen);
                    updatedHocSinh.setEmail(email); // Giữ nguyên email
                    updatedHocSinh.setSoDienThoai(newSoDienThoai);

                    // Gán mã lớp
                    Lop lop = new Lop();
                    lop.setMaLop(Integer.parseInt(newMaLop));
                    updatedHocSinh.setLop(lop);

                    if (hocSinhService.update(updatedHocSinh)) {
                        tableModel.setValueAt(newHoTen, row, 1);
                        tableModel.setValueAt(newSoDienThoai, row, 3);
                        tableModel.setValueAt(newMaLop, row, 4); // Cập nhật mã lớp trong bảng
                        JOptionPane.showMessageDialog(editDialog, "Cập nhật thành công!");
                        editDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(editDialog, "Không thể cập nhật học sinh.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(editDialog, "Mã học sinh và mã lớp phải là số nguyên.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(editDialog, "Lỗi khi cập nhật: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(editDialog, "Vui lòng nhập đầy đủ thông tin");
            }
        });

        contentPanel.add(btnSave);
        editDialog.add(contentPanel);
        editDialog.setSize(350, 300);
        editDialog.setLocationRelativeTo(null);
        editDialog.setVisible(true);
    }

    private void clearForm() {
        txtHoTen.setText("");
        txtEmail.setText("");
        txtSoDienThoai.setText("");
        txtMatKhau.setText("");
    }

    private void taoGiaoDien() {
        setBackground(new Color(250, 250, 250)); // Nền tổng thể rất sáng
        setLayout(new BorderLayout(10, 10));

        panel1 = new JPanel();
        panel1.setBackground(new Color(255, 255, 255)); // Panel thông tin nền trắng
        panel1.setLayout(new GridBagLayout());
        panel1.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Thông tin học sinh",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(50, 50, 50)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Tạo khoảng cách rộng hơn
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        txtHoTen = new JTextField(20);
        txtEmail = new JTextField(20);
        txtSoDienThoai = new JTextField(20);
        txtMatKhau = new JTextField(20);
        cboLop = new JComboBox<>();

        txtHoTen.setFont(inputFont);
        txtEmail.setFont(inputFont);
        txtSoDienThoai.setFont(inputFont);
        txtMatKhau.setFont(inputFont);
        cboLop.setFont(inputFont);

        btnThem = new JButton("Thêm");
        btnChinhSua = new JButton("Chỉnh sửa");
        btnXoa = new JButton("Xóa");

        btnThem.setBackground(new Color(100, 181, 246)); // Xanh dương nhạt
        btnThem.setForeground(Color.WHITE);
        btnChinhSua.setBackground(new Color(255, 202, 40)); // Vàng nhạt
        btnChinhSua.setForeground(Color.BLACK);
        btnXoa.setBackground(new Color(239, 83, 80)); // Đỏ nhạt
        btnXoa.setForeground(Color.WHITE);

        btnThem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnChinhSua.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 14));

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row; panel1.add(new JLabel("Họ tên:", JLabel.RIGHT) {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1; gbc.gridy = row; panel1.add(txtHoTen, gbc);

        gbc.gridx = 2; gbc.gridy = row; panel1.add(new JLabel("Email:", JLabel.RIGHT) {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 3; gbc.gridy = row++; panel1.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = row; panel1.add(new JLabel("Số điện thoại:", JLabel.RIGHT) {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1; gbc.gridy = row; panel1.add(txtSoDienThoai, gbc);

        gbc.gridx = 2; gbc.gridy = row; panel1.add(new JLabel("Mật khẩu:", JLabel.RIGHT) {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 3; gbc.gridy = row++; panel1.add(txtMatKhau, gbc);

        gbc.gridx = 0; gbc.gridy = row; panel1.add(new JLabel("Lớp:", JLabel.RIGHT) {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 3; panel1.add(cboLop, gbc);
        gbc.gridwidth = 1;

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnThem);
        buttonPanel.add(btnChinhSua);
        buttonPanel.add(btnXoa);

        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 4;
        panel1.add(buttonPanel, gbc);

        // Table
        tblHocSinh = new JTable(tableModel);
        tblHocSinh.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblHocSinh.setRowHeight(25);
        tblHocSinh.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblHocSinh.getTableHeader().setBackground(new Color(230, 230, 230));
        tblHocSinh.setBackground(Color.WHITE);
        tblHocSinh.setGridColor(new Color(220, 220, 220));

        JScrollPane scrollPane = new JScrollPane(tblHocSinh);
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(panel1, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }


//    private void clearForm() {
//        txtHoTen.setText("");          // Xóa trường "Họ tên"
//        txtEmail.setText("");          // Xóa trường "Email"
//        txtSoDienThoai.setText("");    // Xóa trường "Số điện thoại"
//        txtMatKhau.setText("");     // Xóa trường "Mật khẩu"
//    }

    public JComponent layPanelChinh() {
        return panel1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HocSinhService hocSinhService ;
            HocSinhDAO hocSinhDAO=new HocSinhDAO(HocSinh.class);
            TaiKhoanService taiKhoanService ;
            TaiKhoanDAO taiKhoanDAO =new TaiKhoanDAO(TaiKhoan.class);
            LopService lopService ;
            LopDAO lopDAO =new LopDAO(Lop.class);
            try {
                lopService = new LopServiceImpl(lopDAO);
                hocSinhService = new HocSinhServiceImpl(hocSinhDAO);
                taiKhoanService = new TaiKhoanServiceImpl(taiKhoanDAO);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

            JFrame frame = new JFrame("Quản lý học sinh");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(950, 600);
            frame.setLocationRelativeTo(null);
            frame.add(new GiaoDienQuanLyHocSinh(hocSinhService, taiKhoanService,lopService));
            frame.setVisible(true);
        });
    }
}
