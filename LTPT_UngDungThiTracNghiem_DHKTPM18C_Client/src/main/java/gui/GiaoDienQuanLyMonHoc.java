package gui;

import daos.MonHocDAO;
import entities.MonHoc;
import service.MonHocService;
import service.impl.MonHocServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;

public class GiaoDienQuanLyMonHoc extends JPanel {
    private JPanel panel1;
    private JTextField txtMaMon, txtTenMon;
    private JButton btnThem, btnChinhSua, btnXoa;
    private JTable tblMonHoc;
    private DefaultTableModel tableModel;
    private MonHocService monHocService;

    public GiaoDienQuanLyMonHoc(MonHocService monHocService) {
        this.monHocService = monHocService;

        String[] columns = {"Mã môn", "Tên môn"};
        tableModel = new DefaultTableModel(columns, 0);
        tblMonHoc = new JTable(tableModel);

        taoGiaoDien();
        loadAllMonHoc();

        // Thêm môn học
        btnThem.addActionListener(e -> {
            String tenMon = txtTenMon.getText().trim();

            if (!tenMon.isEmpty()) {
                try {
                    // Chỉ cần đặt tên môn học
                    MonHoc monHoc = new MonHoc();
                    monHoc.setTenMon(tenMon); // Chỉ đặt tên môn học

                    if (monHocService.save(monHoc)) {
                        // Sau khi lưu thành công, mã môn học sẽ tự động sinh ra trong cơ sở dữ liệu
                        tableModel.addRow(new Object[]{monHoc.getMaMon(), tenMon});
                        txtTenMon.setText("");  // Xóa tên môn học sau khi thêm
                        JOptionPane.showMessageDialog(this, "Thêm môn học thành công!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể thêm môn học.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi thêm môn học: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên môn học!");
            }
        });


        // Chỉnh sửa môn học
        btnChinhSua.addActionListener(e -> {
            int selectedRow = tblMonHoc.getSelectedRow();
            if (selectedRow != -1) {
                String maMon = tableModel.getValueAt(selectedRow, 0).toString();
                String tenMon = tableModel.getValueAt(selectedRow, 1).toString();
                showEditDialog(maMon, tenMon, selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Chọn một môn học để chỉnh sửa.");
            }
        });

        // Xóa môn học
        btnXoa.addActionListener(e -> {
            int selectedRow = tblMonHoc.getSelectedRow();
            if (selectedRow != -1) {
                String maMonStr = tableModel.getValueAt(selectedRow, 0).toString();
                try {
                    int maMon = Integer.parseInt(maMonStr);

                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Bạn có chắc chắn muốn xóa môn học có mã " + maMon + "?",
                            "Xóa môn học", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            if (monHocService.delete(maMon)) {
                                tableModel.removeRow(selectedRow);
                                JOptionPane.showMessageDialog(this, "Xóa môn học thành công!");
                            } else {
                                JOptionPane.showMessageDialog(this, "Không thể xóa môn học.");
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Lỗi khi xóa môn học: " + ex.getMessage());
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Mã môn không hợp lệ.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chọn một môn học để xóa.");
            }
        });
    }

    private void loadAllMonHoc() {
        try {
            List<MonHoc> monHocList = monHocService.getAllMonHoc();
            for (MonHoc monHoc : monHocList) {
                tableModel.addRow(new Object[]{monHoc.getMaMon(), monHoc.getTenMon()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu môn học: " + e.getMessage());
        }
    }

    private void showEditDialog(String maMon, String tenMon, int row) {
        JDialog editDialog = new JDialog((Frame) null, "Chỉnh sửa môn học", true);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelMaMon = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMaMon.add(new JLabel("Mã môn:"));
        JTextField txtEditMaMon = new JTextField(maMon);
        txtEditMaMon.setPreferredSize(new Dimension(250, 40));  // Tăng kích thước
        panelMaMon.add(txtEditMaMon);
        contentPanel.add(panelMaMon);

        JPanel panelTenMon = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTenMon.add(new JLabel("Tên môn:"));
        JTextField txtEditTenMon = new JTextField(tenMon);
        txtEditTenMon.setPreferredSize(new Dimension(250, 40));  // Tăng kích thước
        panelTenMon.add(txtEditTenMon);
        contentPanel.add(panelTenMon);

        JButton btnSave = new JButton("Lưu");
        btnSave.setPreferredSize(new Dimension(120, 35));  // Tăng kích thước nút
        btnSave.addActionListener(e -> {
            String newMaMon = txtEditMaMon.getText().trim();
            String newTenMon = txtEditTenMon.getText().trim();

            if (!newMaMon.isEmpty() && !newTenMon.isEmpty()) {
                try {
                    int newMaMonInt = Integer.parseInt(newMaMon);
                    MonHoc updatedMonHoc = new MonHoc();
                    updatedMonHoc.setMaMon(newMaMonInt);
                    updatedMonHoc.setTenMon(newTenMon);

                    if (monHocService.update(updatedMonHoc)) {
                        tableModel.setValueAt(newMaMonInt, row, 0);
                        tableModel.setValueAt(newTenMon, row, 1);
                        JOptionPane.showMessageDialog(editDialog, "Cập nhật thành công!");
                        editDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(editDialog, "Không thể cập nhật môn học.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(editDialog, "Mã môn phải là số nguyên.");
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
        editDialog.setSize(350, 250);  // Tăng kích thước cho dialog
        editDialog.setLocationRelativeTo(null);
        editDialog.setVisible(true);
    }

    private void taoGiaoDien() {
        panel1 = new JPanel(new BorderLayout(10, 10));
        panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel1.setBackground(Color.WHITE);  // Set white background for the main panel

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));  // Điều chỉnh khoảng cách
        inputPanel.setBackground(Color.WHITE);  // Set white background for input panel

        JLabel lblMaMon = new JLabel("Mã môn:");
        lblMaMon.setFont(new Font("Arial", Font.BOLD, 16));
        txtMaMon = new JTextField(20);  // Tăng kích thước JTextField
        txtMaMon.setFont(new Font("Arial", Font.PLAIN, 14));
        txtMaMon.setPreferredSize(new Dimension(20, 30));
        txtMaMon.setBackground(Color.WHITE);  // White background for text fields

        JLabel lblTenMon = new JLabel("Tên môn:");
        lblTenMon.setFont(new Font("Arial", Font.BOLD, 16));
        txtTenMon = new JTextField(20);  // Tăng kích thước JTextField
        txtTenMon.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTenMon.setPreferredSize(new Dimension(20, 30));
        txtTenMon.setBackground(Color.WHITE);  // White background for text fields

        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.PLAIN, 16));
        btnThem.setPreferredSize(new Dimension(130, 45));  // Tăng kích thước nút
        btnThem.setBackground(new Color(0, 128, 0));  // Green
        btnThem.setForeground(Color.WHITE);

        btnChinhSua = new JButton("Chỉnh sửa");
        btnChinhSua.setFont(new Font("Arial", Font.PLAIN, 16));
        btnChinhSua.setPreferredSize(new Dimension(130, 45));  // Tăng kích thước nút
        btnChinhSua.setBackground(new Color(255, 165, 0));  // Orange
        btnChinhSua.setForeground(Color.WHITE);

        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.PLAIN, 16));
        btnXoa.setPreferredSize(new Dimension(130, 45));  // Tăng kích thước nút
        btnXoa.setBackground(new Color(255, 0, 0));  // Red
        btnXoa.setForeground(Color.WHITE);

        inputPanel.add(lblMaMon);
        inputPanel.add(txtMaMon);
        inputPanel.add(lblTenMon);
        inputPanel.add(txtTenMon);
        inputPanel.add(btnThem);
        inputPanel.add(btnChinhSua);
        inputPanel.add(btnXoa);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách môn học"));
        tablePanel.setBackground(Color.WHITE);  // White background for the table panel
        tblMonHoc.setRowHeight(30);
        tblMonHoc.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(tblMonHoc);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        panel1.add(inputPanel, BorderLayout.NORTH);
        panel1.add(tablePanel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1250, 750));
        inputPanel.setPreferredSize(new Dimension(1200, 180));
        tblMonHoc.setPreferredScrollableViewportSize(new Dimension(1000, 600));  // Điều chỉnh kích thước bảng

        add(panel1);
    }

    public JComponent layPanelChinh() {
        return panel1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MonHocDAO monHocDAO = new MonHocDAO(MonHoc.class);
            MonHocService monHocService;
            try {
                monHocService = new MonHocServiceImpl(monHocDAO);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

            JFrame frame = new JFrame("Quản lý môn học");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1250, 750);
            frame.setLocationRelativeTo(null);
            frame.add(new GiaoDienQuanLyMonHoc(monHocService));
            frame.setVisible(true);
        });
    }
}
