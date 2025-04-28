package gui.custom;

import javax.swing.*;
import java.awt.*;

public class DialogKetQua {

    public static void showResultDialog(JFrame parent, int soCauDung, int tongCauHoi, double diem) {
        JDialog dialog = new JDialog(parent, "Kết quả", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        // Panel chính
        JPanel panel = new JPanel();
        panel.setBackground(new Color(230, 240, 255)); // Màu xanh nhạt
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Label kết quả
        JLabel lblKetQua = new JLabel("Bạn đã trả lời đúng " + soCauDung + "/" + tongCauHoi + " câu.");
        lblKetQua.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblKetQua.setForeground(new Color(33, 37, 41)); // màu chữ tối
        lblKetQua.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Label điểm
        JLabel lblDiem = new JLabel("Điểm: " + String.format("%.2f", diem));
        lblDiem.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblDiem.setForeground(new Color(25, 135, 84)); // màu xanh lá đậm
        lblDiem.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nút đóng
        JButton btnDong = new JButton("Đóng");
        btnDong.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnDong.setBackground(new Color(13, 110, 253)); // xanh dương
        btnDong.setForeground(Color.WHITE);
        btnDong.setFocusPainted(false);
        btnDong.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDong.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDong.addActionListener(e -> dialog.dispose());

        // Canh giữa các thành phần
        panel.add(Box.createVerticalStrut(20));
        panel.add(lblKetQua);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblDiem);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnDong);
        panel.add(Box.createVerticalStrut(20));

        // Gắn panel vào dialog
        dialog.add(panel, BorderLayout.CENTER);

        dialog.setUndecorated(false); // Nếu muốn không có viền thì để true
        dialog.setVisible(true);
    }

    // Test thử
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setSize(300, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.dispose();  // Đóng cửa sổ cũ trước khi mở dialog mới
            showResultDialog(frame, 8, 10, 8.0);

        });
    }
}
