package gui;

import javax.swing.*;
import java.awt.*;

public class GiaoDienChinhNganHang extends JFrame {
    private JPanel mainPanel;

    public GiaoDienChinhNganHang() {
        setTitle("Hệ thống quản lý Ngân hàng câu hỏi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        try {
            mainPanel.add(new GiaoDienNganHangCauHoi(mainPanel)); // Load giao diện Ngân hàng câu hỏi
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GiaoDienChinhNganHang().setVisible(true);
        });
    }
}
