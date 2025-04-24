package gui;

import entities.CauHoi;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GiaoDienThemCauHoi extends JPanel {
    private JTextArea txtNoiDungCauHoi;
    private JTextField txtDapAnDung, txtDapAnSai1, txtDapAnSai2, txtDapAnSai3;
    private JButton btnLuu, btnHuy;

    public GiaoDienThemCauHoi() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Panel để chọn môn học và chủ đề từ JComboBox---
        JPanel panelLuaChon = new JPanel(new GridLayout(1, 2, 10, 10));
        String[] monHoc = {"Môn học 1", "Môn học 2", "Môn học 3"};
        String[] chuDe = {"Chủ đề 1", "Chủ đề 2", "Chủ đề 3"};
        JComboBox<String> cbMonHoc = new JComboBox<>(monHoc);
        cbMonHoc.setPreferredSize(new Dimension(350, 30)); // tăng kích thước JComboBox
        JComboBox<String> cbChuDe = new JComboBox<>(chuDe);
        cbChuDe.setPreferredSize(new Dimension(350, 30)); // tăng kích thước JComboBox
        JLabel lblMonHoc = new JLabel("Chọn môn học:");
        JLabel lblChuDe = new JLabel("Chọn chủ đề:");

        JPanel panelMonHoc = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMonHoc.add(lblMonHoc);
        panelMonHoc.add(cbMonHoc);

        JPanel panelChuDe = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelChuDe.add(lblChuDe);
        panelChuDe.add(cbChuDe);
        panelChuDe.setBackground(new Color(51, 184, 231));
        panelMonHoc.setBackground(new Color(51, 184, 231));

        panelLuaChon.add(panelMonHoc);
        panelLuaChon.add(panelChuDe);

        // --- Panel nội dung câu hỏi ---
        JPanel panelNoiDung = new JPanel(new BorderLayout());
        panelNoiDung.setBorder(BorderFactory.createTitledBorder("Nội dung câu hỏi"));
        TitledBorder border = (TitledBorder) panelNoiDung.getBorder();
        border.setTitleFont(new Font("Arial", Font.BOLD, 16));
        txtNoiDungCauHoi = new JTextArea(15, 50); // tăng số dòng và độ rộng
        JScrollPane scroll = new JScrollPane(txtNoiDungCauHoi);
        panelNoiDung.add(scroll, BorderLayout.CENTER);
        txtNoiDungCauHoi.setLineWrap(true); // tự động xuống dòng

        // --- Panel câu trả lời ---
        JPanel panelTraLoi = new JPanel(new GridBagLayout());
        panelTraLoi.setBorder(BorderFactory.createTitledBorder("Các câu trả lời"));
        TitledBorder borderTraLoi = (TitledBorder) panelTraLoi.getBorder();
        borderTraLoi.setTitleFont(new Font("Arial", Font.BOLD, 16));
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.anchor = GridBagConstraints.WEST;
        gbcLabel.insets = new Insets(8, 8, 8, 4);

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.fill = GridBagConstraints.HORIZONTAL;
        gbcField.weightx = 1.0;
        gbcField.insets = new Insets(8, 4, 8, 8);

        txtDapAnDung = new JTextField(40);
        txtDapAnSai1 = new JTextField(40);
        txtDapAnSai2 = new JTextField(40);
        txtDapAnSai3 = new JTextField(40);

        String[] labels = {"Đáp án đúng", "Đáp án sai", "Đáp án sai", "Đáp án sai"};
        JTextField[] fields = {txtDapAnDung, txtDapAnSai1, txtDapAnSai2, txtDapAnSai3};

        for (int i = 0; i < labels.length; i++) {
            gbcLabel.gridx = 0;
            gbcLabel.gridy = i;
            panelTraLoi.add(new JLabel(labels[i]), gbcLabel);

            gbcField.gridx = 1;
            gbcField.gridy = i;
            panelTraLoi.add(fields[i], gbcField);
        }

        // --- Panel nút ---
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLuu = new JButton("Lưu câu hỏi");
        btnLuu.setBackground(new Color(1, 218, 121));
        btnLuu.setForeground(Color.WHITE);

        btnHuy = new JButton("Hủy");
        btnHuy.setBackground(Color.RED);
        btnHuy.setForeground(Color.WHITE);

        panelButtons.add(btnLuu);
        panelButtons.add(btnHuy);

        // --- Thêm các panel vào panel chính ---
        JPanel panelMain = new JPanel(new BorderLayout());
        panelMain.add(panelNoiDung, BorderLayout.NORTH);
        panelMain.add(panelTraLoi, BorderLayout.CENTER);
        add(panelLuaChon, BorderLayout.NORTH);
        add(panelMain, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        // Thêm màu nền cho các panel
        panelLuaChon.setBackground(new Color(51, 184, 231));
        panelNoiDung.setBackground(Color.WHITE);
        panelTraLoi.setBackground(Color.WHITE);
        panelMain.setBackground(new Color(51, 184, 231));
        panelButtons.setBackground(new Color(51, 184, 231));
        setBackground(new Color(51, 184, 231));

        setPreferredSize(new Dimension(1200, 750)); // tăng kích thước tổng thể

        // --- Thêm ActionListener cho các nút ---
        btnLuu.addActionListener(this::actionPerformed);
        btnHuy.addActionListener(this::actionPerformed);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLuu) {
            String noiDung = txtNoiDungCauHoi.getText();
            String dapAnDung = txtDapAnDung.getText();
            String dapAnSai1 = txtDapAnSai1.getText();
            String dapAnSai2 = txtDapAnSai2.getText();
            String dapAnSai3 = txtDapAnSai3.getText();

            // Kiểm tra dữ liệu nhập vào
            if (noiDung.isEmpty() || dapAnDung.isEmpty() || dapAnSai1.isEmpty() || dapAnSai2.isEmpty() || dapAnSai3.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                // Xử lý lưu câu hỏi ở đây
                JOptionPane.showMessageDialog(this, "Câu hỏi đã được lưu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == btnHuy) {
            // Xử lý hủy bỏ
            txtNoiDungCauHoi.setText("");
            txtDapAnDung.setText("");
            txtDapAnSai1.setText("");
            txtDapAnSai2.setText("");
            txtDapAnSai3.setText("");
        }
    }

    // --- Main để chạy thử ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Nhập câu hỏi");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new GiaoDienThemCauHoi());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
