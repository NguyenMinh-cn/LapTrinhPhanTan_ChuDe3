package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GiaoDienQuanLyMonHoc extends JPanel {
    private JPanel panel1;
    private JTextField txtMaMon, txtTenMon;
    private JButton btnThem, btnChinhSua;
    private JTable tblMonHoc;
    private DefaultTableModel tableModel;

    {
        taoGiaoDien();
    }

    public GiaoDienQuanLyMonHoc() {
        String[] columns = {"Mã môn", "Tên môn"};
        tableModel = new DefaultTableModel(columns, 0);
        tblMonHoc.setModel(tableModel);

        btnChinhSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblMonHoc.getSelectedRow();
                if (selectedRow != -1) {
                    String maMon = (String) tableModel.getValueAt(selectedRow, 0);
                    String tenMon = (String) tableModel.getValueAt(selectedRow, 1);
                    showEditDialog(maMon, tenMon, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Chọn một môn học để chỉnh sửa.");
                }
            }
        });

        btnThem.addActionListener(e -> {
        });
    }

    private void showEditDialog(String maMon, String tenMon, int row) {
        JDialog editDialog = new JDialog((Frame) null, "Chỉnh sửa môn học", true);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelMaMon = new JPanel();
        panelMaMon.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelMaMon.add(new JLabel("Mã môn:"));
        JTextField txtEditMaMon = new JTextField(maMon);
        txtEditMaMon.setPreferredSize(new Dimension(200, 30));
        panelMaMon.add(txtEditMaMon);
        contentPanel.add(panelMaMon);

        JPanel panelTenMon = new JPanel();
        panelTenMon.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTenMon.add(new JLabel("Tên môn:"));
        JTextField txtEditTenMon = new JTextField(tenMon);
        txtEditTenMon.setPreferredSize(new Dimension(200, 30));
        panelTenMon.add(txtEditTenMon);
        contentPanel.add(panelTenMon);

        JButton btnSave = new JButton("Lưu");
        btnSave.setPreferredSize(new Dimension(80, 30));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newMaMon = txtEditMaMon.getText().trim();
                String newTenMon = txtEditTenMon.getText().trim();
                if (!newMaMon.isEmpty() && !newTenMon.isEmpty()) {
                    tableModel.setValueAt(newMaMon, row, 0);
                    tableModel.setValueAt(newTenMon, row, 1);
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật thành công!");
                    editDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Vui lòng nhập đầy đủ thông tin");
                }
            }
        });

        contentPanel.add(btnSave);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        editDialog.add(contentPanel);
        editDialog.setSize(350, 200);
        editDialog.setLocationRelativeTo(null);
        editDialog.setVisible(true);
    }

    private void taoGiaoDien() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(10, 10));
        panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JLabel lblMaMon = new JLabel("Mã môn:");
        lblMaMon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtMaMon = new JTextField(10);
        txtMaMon.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JLabel lblTenMon = new JLabel("Tên môn:");
        lblTenMon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtTenMon = new JTextField(15);
        txtTenMon.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        btnChinhSua = new JButton("Chỉnh sửa");
        btnChinhSua.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        inputPanel.add(lblMaMon);
        inputPanel.add(txtMaMon);
        inputPanel.add(lblTenMon);
        inputPanel.add(txtTenMon);
        inputPanel.add(btnThem);
        inputPanel.add(btnChinhSua);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách môn học"));

        tblMonHoc = new JTable();
        tblMonHoc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblMonHoc.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tblMonHoc);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        panel1.add(inputPanel, BorderLayout.NORTH);
        panel1.add(tablePanel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(750, 500));
    }

    public JComponent layPanelChinh() {
        return panel1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý môn học");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new GiaoDienQuanLyMonHoc().layPanelChinh());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}