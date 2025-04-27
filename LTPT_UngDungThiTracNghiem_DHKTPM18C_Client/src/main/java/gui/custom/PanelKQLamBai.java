package gui.custom;

import entities.CauTraLoi;
import entities.PhienLamBai;
import service.PhienLamBaiService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.rmi.Naming;

public class PanelKQLamBai extends JPanel {
    private JLabel scoreLabel;
    private JLabel correctLabel;
    private JLabel wrongLabel;
    private JLabel emptyLabel;
    private JButton detailButton;
    private PhienLamBai phienLamBai;

    public PanelKQLamBai(PhienLamBai phienLamBai) {
        this.phienLamBai = phienLamBai;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setOpaque(false);

        int soCauDung = 0, soCauSai = 0, soCauBoTrong = 0;

        if (phienLamBai != null && phienLamBai.getDanhSachCauTraLoi() != null) {
            try {
                for (CauTraLoi cauTraLoi : phienLamBai.getDanhSachCauTraLoi()) {
                    if (cauTraLoi.getDapAnDaChon() == null) soCauBoTrong++;
                    else if (cauTraLoi.isKetQua()) soCauDung++;
                    else soCauSai++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                showError("Lỗi khi truy cập danh sách câu trả lời: " + e.getMessage());
            }
        } else {
            showError("Không tìm thấy thông tin phiên làm bài hoặc danh sách câu trả lời.");
        }

        double diem = (phienLamBai != null) ? phienLamBai.getDiem() : 0.0;
        Font font = new Font("Arial", Font.BOLD, 18);

        scoreLabel = createLabel("Điểm: " + diem, font, new Color(52, 152, 219));
        correctLabel = createLabel("Đúng: " + soCauDung, font, new Color(82, 183, 136));
        wrongLabel = createLabel("Sai: " + soCauSai, font, new Color(231, 76, 60));
        emptyLabel = createLabel("Bỏ trống: " + soCauBoTrong, font, new Color(114, 9, 183));

        // Thêm label và khoảng cách
        mainPanel.add(scoreLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        mainPanel.add(correctLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        mainPanel.add(wrongLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        mainPanel.add(emptyLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        mainPanel.add(Box.createHorizontalGlue());

        // Nút Chi tiết
        detailButton = new JButton("Xem chi tiết");
        detailButton.setFocusPainted(false);
        detailButton.setContentAreaFilled(false);
        detailButton.setOpaque(true);
        styleButton(detailButton);
        detailButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "Tính năng xem chi tiết đang được phát triển!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });
        mainPanel.add(detailButton);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(150, 40));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    // Chạy thử
    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("Kết quả làm bài");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 200);

            PhienLamBaiService phienLamBaiService = (PhienLamBaiService) Naming.lookup("rmi://localhost:8081/phienLamBaiService");

            String maPhien = "2025042801031689";
            PhienLamBai phienLamBai1 = phienLamBaiService.layThongTinPhienLamBaiVaCauTraLoi(maPhien);

            if (phienLamBai1 != null) {
                PanelKQLamBai panel = new PanelKQLamBai(phienLamBai1);
                frame.add(panel);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy phiên làm bài.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
