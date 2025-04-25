package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GiaoDienThemCauHoi extends JPanel {
    private JTextArea txtNoiDungCauHoi;
    private JTextField txtDapAnDung, txtDapAnSai1, txtDapAnSai2, txtDapAnSai3;
    private JButton btnLuu, btnHuy;

    public GiaoDienThemCauHoi() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Panel nội dung câu hỏi ---
        JPanel panelNoiDung = new JPanel(new BorderLayout());
        panelNoiDung.setBorder(BorderFactory.createTitledBorder("Nội dung câu hỏi"));
        txtNoiDungCauHoi = new JTextArea(6, 50); // tăng số dòng và độ rộng
        JScrollPane scroll = new JScrollPane(txtNoiDungCauHoi);
        panelNoiDung.add(scroll, BorderLayout.CENTER);

        // --- Panel câu trả lời ---
        JPanel panelTraLoi = new JPanel(new GridBagLayout());
        panelTraLoi.setBorder(BorderFactory.createTitledBorder("Các câu trả lời"));
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
        btnLuu.setBackground(Color.GREEN);
        btnLuu.setForeground(Color.WHITE);

        btnHuy = new JButton("Hủy");
        btnHuy.setBackground(Color.RED);
        btnHuy.setForeground(Color.WHITE);

        panelButtons.add(btnLuu);
        panelButtons.add(btnHuy);

        // --- Thêm các panel vào panel chính ---
        add(panelNoiDung, BorderLayout.NORTH);
        add(panelTraLoi, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(700, 450)); // tăng kích thước tổng thể
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
