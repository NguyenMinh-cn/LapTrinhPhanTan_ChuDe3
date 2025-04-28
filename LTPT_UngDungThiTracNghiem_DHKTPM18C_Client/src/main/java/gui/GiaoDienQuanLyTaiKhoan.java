package gui;

import daos.GiaoVienDAO;
import entities.GiaoVien;
import entities.HocSinh;
import entities.TaiKhoan;
import service.GiaoVienService;
import service.HocSinhService;
import service.TaiKhoanService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class GiaoDienQuanLyTaiKhoan extends JPanel {
    private String ipAddress = "localhost";
    private JTextField txtThemHoTen, txtThemEmail, txtThemSDT, txtThemMatKhau;
    private JComboBox<String> cmbThemLoaiTaiKhoan;
    private JButton btnThemTaiKhoan;

    private JComboBox<String> cmbLoaiTaiKhoanSua;
    private JTable tableTaiKhoan;
    private DefaultTableModel tableModel;
    private JTextField txtSuaHoTen, txtSuaEmail, txtSuaSDT, txtSuaMatKhau;
    private JButton btnCapNhatTaiKhoan;
    private TaiKhoanService taiKhoanService;
    private GiaoVienService giaoVienService;
    private HocSinhService hocSinhService;

    public GiaoDienQuanLyTaiKhoan() throws MalformedURLException, NotBoundException, RemoteException {
        taiKhoanService = (TaiKhoanService) Naming.lookup("rmi://" + ipAddress + ":8081/taiKhoanService");
        giaoVienService = (GiaoVienService) Naming.lookup("rmi://" + ipAddress + ":8081/giaoVienService");
        hocSinhService = (HocSinhService) Naming.lookup("rmi://" + ipAddress + ":8081/hocSinhService");
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        setBackground(Color.WHITE);
        Font fontChinh = new Font("Segoe UI", Font.PLAIN, 20);
        Font fontTieuDe = new Font("Segoe UI", Font.BOLD, 20);
        Color primaryColor = new Color(33, 150, 243);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(fontChinh);

        // --- Tab Thêm tài khoản ---
        JPanel panelThem = new JPanel(new GridBagLayout());
        panelThem.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblThemLoai = new JLabel("Loại tài khoản:");
        JLabel lblThemHoTen = new JLabel("Họ tên:");
        JLabel lblThemEmail = new JLabel("Email:");
        JLabel lblThemSDT = new JLabel("Số điện thoại:");
        JLabel lblThemMatKhau = new JLabel("Mật khẩu:");

        lblThemLoai.setFont(fontChinh);
        lblThemHoTen.setFont(fontChinh);
        lblThemEmail.setFont(fontChinh);
        lblThemSDT.setFont(fontChinh);
        lblThemMatKhau.setFont(fontChinh);

        cmbThemLoaiTaiKhoan = new JComboBox<>(new String[]{"Giáo Viên", "Học Sinh"});
        cmbThemLoaiTaiKhoan.setFont(fontChinh);

        txtThemHoTen = new JTextField(20);
        txtThemHoTen.setFont(fontChinh);

        txtThemEmail = new JTextField(20);
        txtThemEmail.setFont(fontChinh);


        txtThemSDT = new JTextField(20);
        txtThemSDT.setFont(fontChinh);

        txtThemMatKhau = new JTextField(20);
        txtThemMatKhau.setFont(fontChinh);
        txtThemMatKhau.setText("111");

        btnThemTaiKhoan = new JButton("Thêm tài khoản");
        btnThemTaiKhoan.setBackground(primaryColor);
        btnThemTaiKhoan.setForeground(Color.WHITE);
        btnThemTaiKhoan.setFont(fontChinh);

        gbc.gridx = 0; gbc.gridy = 0;
        panelThem.add(lblThemLoai, gbc);
        gbc.gridx = 1;
        panelThem.add(cmbThemLoaiTaiKhoan, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelThem.add(lblThemHoTen, gbc);
        gbc.gridx = 1;
        panelThem.add(txtThemHoTen, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelThem.add(lblThemEmail, gbc);
        gbc.gridx = 1;
        panelThem.add(txtThemEmail, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelThem.add(lblThemSDT, gbc);
        gbc.gridx = 1;
        panelThem.add(txtThemSDT, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelThem.add(lblThemMatKhau, gbc);
        gbc.gridx = 1;
        panelThem.add(txtThemMatKhau, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelThem.add(btnThemTaiKhoan, gbc);

        tabbedPane.addTab("Thêm Tài Khoản", panelThem);

        // --- Tab Chỉnh sửa tài khoản ---
        JPanel panelSua = new JPanel(new BorderLayout(10, 10));
        panelSua.setBackground(Color.WHITE);
        panelSua.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setBackground(Color.WHITE);

        JLabel lblLoaiTaiKhoanSua = new JLabel("Loại tài khoản:");
        lblLoaiTaiKhoanSua.setFont(fontChinh);

        cmbLoaiTaiKhoanSua = new JComboBox<>(new String[]{"Giáo Viên", "Học Sinh"});
        cmbLoaiTaiKhoanSua.setFont(fontChinh);

        panelTop.add(lblLoaiTaiKhoanSua);
        panelTop.add(cmbLoaiTaiKhoanSua);

        panelSua.add(panelTop, BorderLayout.NORTH);

        initTable();
        JScrollPane scrollPane = new JScrollPane(tableTaiKhoan);

        panelSua.add(scrollPane, BorderLayout.CENTER);

        JPanel panelFormSua = new JPanel(new GridBagLayout());
        panelFormSua.setBackground(Color.WHITE);

        JLabel lblSuaHoTen = new JLabel("Họ tên:");
        JLabel lblSuaEmail = new JLabel("Email:");
        JLabel lblSuaSDT = new JLabel("Số điện thoại:");
        JLabel lblSuaMatKhau = new JLabel("Mật khẩu:");

        lblSuaHoTen.setFont(fontChinh);
        lblSuaEmail.setFont(fontChinh);
        lblSuaSDT.setFont(fontChinh);
        lblSuaMatKhau.setFont(fontChinh);

        txtSuaHoTen = new JTextField(20);
        txtSuaHoTen.setFont(fontChinh);

        txtSuaEmail = new JTextField(20);
        txtSuaEmail.setFont(fontChinh);
        txtSuaEmail.setEditable(false);

        txtSuaSDT = new JTextField(20);
        txtSuaSDT.setFont(fontChinh);

        txtSuaMatKhau = new JTextField(20);
        txtSuaMatKhau.setFont(fontChinh);

        btnCapNhatTaiKhoan = new JButton("Cập nhật tài khoản");
        btnCapNhatTaiKhoan.setBackground(new Color(76, 175, 80));
        btnCapNhatTaiKhoan.setForeground(Color.WHITE);
        btnCapNhatTaiKhoan.setFont(fontChinh);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelFormSua.add(lblSuaHoTen, gbc);
        gbc.gridx = 1;
        panelFormSua.add(txtSuaHoTen, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormSua.add(lblSuaEmail, gbc);
        gbc.gridx = 1;
        panelFormSua.add(txtSuaEmail, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormSua.add(lblSuaSDT, gbc);
        gbc.gridx = 1;
        panelFormSua.add(txtSuaSDT, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormSua.add(lblSuaMatKhau, gbc);
        gbc.gridx = 1;
        panelFormSua.add(txtSuaMatKhau, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelFormSua.add(btnCapNhatTaiKhoan, gbc);

        panelSua.add(panelFormSua, BorderLayout.EAST);

        tabbedPane.addTab("Chỉnh Sửa Tài Khoản", panelSua);

        add(tabbedPane);

        setupEvents();
    }

    private void initTable() {
        // Khởi tạo model cho table
        String[] columnNames = {"Họ Tên", "Email", "Số Điện Thoại", "Lớp"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép edit trực tiếp trên table
            }
        };
        
        tableTaiKhoan = new JTable(tableModel);
        tableTaiKhoan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Set font size cho table
        Font tableFont = new Font("Arial", Font.PLAIN, 16); // Giảm font size xuống 16
        tableTaiKhoan.setFont(tableFont);
        tableTaiKhoan.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        
        // Set row height để phù hợp với font size
        tableTaiKhoan.setRowHeight(30); // Giảm row height xuống 30
        
        // Không cho phép di chuyển các cột
        tableTaiKhoan.getTableHeader().setReorderingAllowed(false);
        
        // Cho phép resize columns
        tableTaiKhoan.getTableHeader().setResizingAllowed(true);
        
        // Set kích thước ban đầu cho các cột
        TableColumnModel columnModel = tableTaiKhoan.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200); // Họ Tên
        columnModel.getColumn(1).setPreferredWidth(200); // Email
        columnModel.getColumn(2).setPreferredWidth(150); // Số Điện Thoại
        columnModel.getColumn(3).setPreferredWidth(100); // Lớp
        
        // Set màu cho header
        tableTaiKhoan.getTableHeader().setBackground(new Color(4, 117, 196));
        tableTaiKhoan.getTableHeader().setForeground(Color.WHITE);
        
        // Thêm listener cho việc chọn dòng
        tableTaiKhoan.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableTaiKhoan.getSelectedRow();
                if (selectedRow != -1) {
                    updateFormFromSelection(selectedRow);
                }
            }
        });

        // Tạo JScrollPane cho table
        JScrollPane scrollPane = new JScrollPane(tableTaiKhoan);
        scrollPane.setBackground(new Color(210, 234, 255));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Thêm scrollPane vào panel chứa table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);
    }

    private void setupEvents() {
        btnThemTaiKhoan.addActionListener(e -> {
            String loaiTK = (String) cmbThemLoaiTaiKhoan.getSelectedItem();
            if ("Giáo Viên".equals(loaiTK)) {
                themGiaoVien();
            } else {
                // Xử lý thêm học sinh
                themHocSinh();
            }
        });

        btnCapNhatTaiKhoan.addActionListener(e -> {
            try {
                capNhatTaiKhoan();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            // Reset form sau khi cập nhật
            resetFormCapNhat();
        });
        
        cmbLoaiTaiKhoanSua.addActionListener(e -> {
            loadDanhSachTaiKhoan();
            // Reset form khi chuyển loại tài khoản
            resetFormCapNhat();
        });
    }

    private void themGiaoVien() {
        try {
            // 1. Validate dữ liệu đầu vào
            String hoTen = txtThemHoTen.getText().trim();
            String email = txtThemEmail.getText().trim();
            String sdt = txtThemSDT.getText().trim();
            String matKhau = txtThemMatKhau.getText().trim();

            // Kiểm tra dữ liệu trống
            if (hoTen.isEmpty() || email.isEmpty() || sdt.isEmpty() || matKhau.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
//            Validate Họ tên: Chỉ cho phép chữ cái và khoảng trắng
            if (!hoTen.matches("[\\p{L} ]+")) {
                JOptionPane.showMessageDialog(this,
                        "Họ tên chỉ được chứa chữ cái và khoảng trắng!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
// Validate Email: Phải có dạng đúng chứa '@'
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(this,
                        "Email không hợp lệ!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Validate Số điện thoại: 10 chữ số, bắt đầu bằng 0
            if (!sdt.matches("^0\\d{9}$")) {
                JOptionPane.showMessageDialog(this,
                        "Số điện thoại phải gồm 10 số và bắt đầu bằng 0!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Kiểm tra giáo viên tồn tại
            TaiKhoan tkDaCo = taiKhoanService.finByID(email);
            if (tkDaCo != null) {
                JOptionPane.showMessageDialog(this,
                    "Email đã được sử dụng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo tài khoản mới
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setTenDangNhap(email);  // Email là khóa chính của TaiKhoan
            taiKhoan.setMatKhau(matKhau);
            taiKhoan.setLoaiTaiKhoan("GiaoVien");

            // Tạo giáo viên mới
            GiaoVien giaoVien = new GiaoVien();
            giaoVien.setHoTen(hoTen);
            giaoVien.setEmail(email);
            giaoVien.setSoDienThoai(sdt);
            giaoVien.setTaiKhoan(taiKhoan);  // Thiết lập quan hệ với tài khoản

            // Lưu giáo viên
            boolean saveGVResult = giaoVienService.save(giaoVien);
            if (!saveGVResult) {
                JOptionPane.showMessageDialog(this,
                        "Có lỗi khi tạo tài khoản cho giáo viên!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Thành công
            JOptionPane.showMessageDialog(this,
                "Thêm giáo viên thành công!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Reset form và refresh danh sách
            resetForm();
            loadDanhSachGiaoVien();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                e,
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void themHocSinh() {
        try {
            // 1. Validate dữ liệu đầu vào
            String hoTen = txtThemHoTen.getText().trim();
            String email = txtThemEmail.getText().trim();
            String sdt = txtThemSDT.getText().trim();
            String matKhau = txtThemMatKhau.getText().trim();

            // Kiểm tra dữ liệu trống
            if (hoTen.isEmpty() || email.isEmpty() || sdt.isEmpty() || matKhau.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate Họ tên: Chỉ cho phép chữ cái và khoảng trắng
            if (!hoTen.matches("[\\p{L} ]+")) {
                JOptionPane.showMessageDialog(this,
                        "Họ tên chỉ được chứa chữ cái và khoảng trắng!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate Email: Phải có dạng đúng chứa '@'
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(this,
                        "Email không hợp lệ!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate Số điện thoại: 10 chữ số, bắt đầu bằng 0
            if (!sdt.matches("^0\\d{9}$")) {
                JOptionPane.showMessageDialog(this,
                        "Số điện thoại phải gồm 10 số và bắt đầu bằng 0!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra tài khoản tồn tại
            TaiKhoan tkDaCo = taiKhoanService.finByID(email);
            if (tkDaCo != null) {
                JOptionPane.showMessageDialog(this,
                    "Email đã được sử dụng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo tài khoản mới
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setTenDangNhap(email);  // Email là khóa chính của TaiKhoan
            taiKhoan.setMatKhau(matKhau);
            taiKhoan.setLoaiTaiKhoan("HocSinh");

            // Tạo học sinh mới
            HocSinh hocSinh = new HocSinh();
            hocSinh.setHoTen(hoTen);
            hocSinh.setEmail(email);
            hocSinh.setSoDienThoai(sdt);
            hocSinh.setTaiKhoan(taiKhoan);  // Thiết lập quan hệ với tài khoản

            // Lưu học sinh
            boolean saveHSResult = hocSinhService.save(hocSinh);
            if (!saveHSResult) {
                JOptionPane.showMessageDialog(this,
                        "Có lỗi khi tạo tài khoản cho học sinh!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Thành công
            JOptionPane.showMessageDialog(this,
                "Thêm học sinh thành công!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Reset form và refresh danh sách
            resetForm();
            loadDanhSachHocSinh();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                e,
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        // Reset text fields in "Add Account" section
        txtThemHoTen.setText("");
        txtThemEmail.setText("");
        txtThemSDT.setText("");
        txtThemMatKhau.setText("");
        
        // Reset text fields in "Edit Account" section
        txtSuaHoTen.setText("");
        txtSuaEmail.setText("");
        txtSuaSDT.setText("");
        txtSuaMatKhau.setText("");
        
        // Reset combo boxes to first item
        cmbThemLoaiTaiKhoan.setSelectedIndex(0);
        cmbLoaiTaiKhoanSua.setSelectedIndex(0);
    }

    private void loadDanhSachGiaoVien() {
        try {
            List<GiaoVien> danhSachGV = giaoVienService.getAll();
            tableModel.setRowCount(0); // Xóa dữ liệu cũ
            
            for (GiaoVien gv : danhSachGV) {
                Object[] row = {
                    gv.getHoTen(),
                    gv.getEmail(),
                    gv.getSoDienThoai(),
                    "N/A" // Giáo viên không có lớp
                };
                tableModel.addRow(row);
            }
            
        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải danh sách giáo viên: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDanhSachHocSinh() {
        try {
            List<HocSinh> danhSachHS = hocSinhService.getAll();
            tableModel.setRowCount(0); // Xóa dữ liệu cũ
            
            for (HocSinh hs : danhSachHS) {
                Object[] row = {
                    hs.getHoTen(),
                    hs.getEmail(),
                    hs.getSoDienThoai(),
                    hs.getLop() != null ? hs.getLop().getTenLop() : "Chưa có lớp"
                };
                tableModel.addRow(row);
            }
            
        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải danh sách học sinh: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateFormFromSelection(int selectedRow) {
        txtSuaHoTen.setText((String) tableModel.getValueAt(selectedRow, 0));
        txtSuaEmail.setText((String) tableModel.getValueAt(selectedRow, 1));
        txtSuaSDT.setText((String) tableModel.getValueAt(selectedRow, 2));
        txtSuaMatKhau.setText(""); // Không hiển thị mật khẩu cũ
    }

    private void capNhatTaiKhoan() throws RemoteException {

            int selectedRow = tableTaiKhoan.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn tài khoản cần cập nhật!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 1. Lấy thông tin từ form
            String hoTen = txtSuaHoTen.getText().trim();
            String email = txtSuaEmail.getText().trim();
            String sdt = txtSuaSDT.getText().trim();
            String matKhau = txtSuaMatKhau.getText().trim();

            boolean isValid = true; // Cờ kiểm tra lỗi đầu vào

            // 2. Validate dữ liệu
            if (hoTen.isEmpty() || email.isEmpty() || sdt.isEmpty()||matKhau.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập đầy đủ thông tin!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                isValid = false;
            }

            // 3. Validate Họ tên
            if (!hoTen.matches("[\\p{L} ]+")) {
                JOptionPane.showMessageDialog(this,
                        "Họ tên chỉ được chứa chữ cái và khoảng trắng!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                isValid = false;
            }

            // 4. Validate Email
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(this,
                        "Email không hợp lệ!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                isValid = false;
            }

            // 5. Validate Số điện thoại
            if (!sdt.matches("^0\\d{9}$")) {
                JOptionPane.showMessageDialog(this,
                        "Số điện thoại phải gồm 10 số và bắt đầu bằng 0!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                isValid = false;
            }

            if (isValid) {
                String loaiTK = (String) cmbLoaiTaiKhoanSua.getSelectedItem();
                boolean updateSuccess = false;
                String currentEmail = (String) tableModel.getValueAt(selectedRow, 1);
                if (!email.equals(currentEmail)) {
                    TaiKhoan emailTaiKhoan = taiKhoanService.finByID(email);
                    if (emailTaiKhoan != null) {
                        JOptionPane.showMessageDialog(this,
                                "Email đã được sử dụng!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                // Cập nhật thông tin cho Giáo Viên hoặc Học Sinh
                if ("Giáo Viên".equals(loaiTK)) {
                    GiaoVien giaoVien = giaoVienService.timGiaoVienTheoEmail((String) tableModel.getValueAt(selectedRow, 1));
                    if (giaoVien != null) {
                        giaoVien.setHoTen(hoTen);
                        giaoVien.setSoDienThoai(sdt);
                        if (!matKhau.isEmpty()) {
                            giaoVien.getTaiKhoan().setMatKhau(matKhau);
                        }
                        updateSuccess = giaoVienService.update(giaoVien);
                        if (updateSuccess) {
                            JOptionPane.showMessageDialog(this,
                                    "Cập nhật thông tin giáo viên thành công!",
                                    "Thông báo",
                                    JOptionPane.INFORMATION_MESSAGE);
                            loadDanhSachGiaoVien();
                        }
                    }else{
                        JOptionPane.showMessageDialog(this,
                                "Không tìm thấy giáo viên!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    HocSinh hocSinh = hocSinhService.timHocSinhTheoEmail((String) tableModel.getValueAt(selectedRow, 1));
                    if (hocSinh != null) {
                        hocSinh.setHoTen(hoTen);
                        hocSinh.setSoDienThoai(sdt);
                        if (!matKhau.isEmpty()) {
                            hocSinh.getTaiKhoan().setMatKhau(matKhau);
                        }
                        updateSuccess = hocSinhService.update(hocSinh);
                        if (updateSuccess) {
                            JOptionPane.showMessageDialog(this,
                                    "Cập nhật thông tin học sinh thành công!",
                                    "Thông báo",
                                    JOptionPane.INFORMATION_MESSAGE);
                            loadDanhSachHocSinh();
                        }
                    }else{
                        JOptionPane.showMessageDialog(this,
                                "Không tìm thấy giáo viên!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                // 6. Nếu cập nhật thành công, reset form
                if (updateSuccess) {
                    resetFormCapNhat();
                }
            }

    }

    private void loadDanhSachTaiKhoan() {
        String loaiTK = (String) cmbLoaiTaiKhoanSua.getSelectedItem();
        if ("Giáo Viên".equals(loaiTK)) {
            loadDanhSachGiaoVien();
        } else {
            loadDanhSachHocSinh();
        }
    }

    // Thêm method mới để reset form cập nhật
    private void resetFormCapNhat() {
        txtSuaHoTen.setText("");
        txtSuaEmail.setText("");
        txtSuaSDT.setText("");
        txtSuaMatKhau.setText("");
        tableTaiKhoan.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Tạo JFrame để chứa JPanel
                JFrame frame = new JFrame("Quản Lý Tài Khoản");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 600);
                frame.setLocationRelativeTo(null);

                // Tạo GiaoDienQuanLyTaiKhoan và thêm vào JFrame
                GiaoDienQuanLyTaiKhoan panel = new GiaoDienQuanLyTaiKhoan();
                frame.add(panel);

                // Hiển thị JFrame
                frame.setVisible(true);
            } catch (MalformedURLException | NotBoundException | RemoteException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi khởi động ứng dụng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}