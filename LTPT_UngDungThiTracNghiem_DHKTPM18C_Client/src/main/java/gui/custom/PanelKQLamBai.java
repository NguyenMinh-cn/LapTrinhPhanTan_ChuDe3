package gui.custom;

import entities.CauTraLoi;
import entities.PhienLamBai;
import service.PhienLamBaiService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.rmi.Naming;
public class PanelKQLamBai extends JPanel {
    private JLabel scoreLabel;
    private JLabel correctLabel;
    private JLabel wrongLabel;
    private JLabel emptyLabel;
    private JLabel startTimeLabel;
    private JLabel endTimeLabel;
    private JButton detailButton;
    private PhienLamBai phienLamBai;

    public PanelKQLamBai(PhienLamBai phienLamBai) {
        this.phienLamBai = phienLamBai;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // nền xám nhạt
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(15, 20, 15, 20)
        ));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        int soCauDung = 0, soCauSai = 0, soCauBoTrong = 0;

        if (phienLamBai != null && phienLamBai.getDanhSachCauTraLoi() != null) {
            for (CauTraLoi cauTraLoi : phienLamBai.getDanhSachCauTraLoi()) {
                if (cauTraLoi.getDapAnDaChon() == null) soCauBoTrong++;
                else if (cauTraLoi.isKetQua()) soCauDung++;
                else soCauSai++;
            }
        }

        double diem = (phienLamBai != null) ? phienLamBai.getDiem() : 0.0;
        Font font = new Font("Segoe UI", Font.PLAIN, 16);
        Font boldFont = new Font("Segoe UI", Font.BOLD, 16);

        // Bảng thông tin bên trái
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        resultPanel.setOpaque(false);

        scoreLabel = createLabel("Điểm: " + diem, boldFont, new Color(52, 152, 219));
        correctLabel = createLabel("Đúng: " + soCauDung, boldFont, new Color(39, 174, 96));
        wrongLabel = createLabel("Sai: " + soCauSai, boldFont, new Color(231, 76, 60));
        emptyLabel = createLabel("Bỏ trống: " + soCauBoTrong, boldFont, new Color(155, 89, 182));

        resultPanel.add(scoreLabel);
        resultPanel.add(correctLabel);
        resultPanel.add(wrongLabel);
        resultPanel.add(emptyLabel);

        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        timePanel.setOpaque(false);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

        String thoiGianBatDau = (phienLamBai != null && phienLamBai.getThoiGianBatDau() != null)
                ? phienLamBai.getThoiGianBatDau().format(formatter) : "N/A";
        String thoiGianKetThuc = (phienLamBai != null && phienLamBai.getThoiGianKetThuc() != null)
                ? phienLamBai.getThoiGianKetThuc().format(formatter) : "N/A";

        startTimeLabel = createLabel("Bắt đầu: " + thoiGianBatDau, font, Color.BLACK);
        endTimeLabel = createLabel("Kết thúc: " + thoiGianKetThuc, font, Color.BLACK);

        timePanel.add(startTimeLabel);
        timePanel.add(endTimeLabel);

        infoPanel.add(resultPanel);
        infoPanel.add(timePanel);

        // Bên phải: nút Xem chi tiết
        detailButton = new JButton("Xem chi tiết");
        styleButton(detailButton);
        detailButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "Tính năng xem chi tiết đang được phát triển!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(detailButton);

        contentPanel.add(infoPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.EAST);

        add(contentPanel, BorderLayout.CENTER);
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
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(140, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 1, true));
    }


    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    // Chạy thử
    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("Kết quả làm bài");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 300);

            PhienLamBaiService phienLamBaiService = (PhienLamBaiService) Naming.lookup("rmi://192.168.1.13:8081/phienLamBaiService");

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
