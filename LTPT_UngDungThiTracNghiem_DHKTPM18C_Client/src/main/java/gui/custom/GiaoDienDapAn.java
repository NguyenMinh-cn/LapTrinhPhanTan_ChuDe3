package gui.custom;

import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GiaoDienDapAn extends JPanel {
    private JRadioButton radioButton;
    private JButton btnXoa;
    private JTextPane textPane;
    private JLabel lblDapAn;
    private Runnable onXoa;
    public GiaoDienDapAn(String tenDapAn) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        Font fontChinh = new Font("Arial", Font.PLAIN, 20);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        radioButton = new JRadioButton();
        radioButton.setFont(fontChinh);
        radioButton.setOpaque(true);  // Đảm bảo bật chế độ opaque
        radioButton.setBackground(Color.WHITE);

        lblDapAn = new JLabel(tenDapAn);
        lblDapAn.setFont(fontChinh);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(radioButton);
        leftPanel.add(lblDapAn);
        leftPanel.setBackground(Color.WHITE);
        btnXoa = new JButton("Xoá đáp án");
        btnXoa.setFont(fontChinh);
        btnXoa.setForeground(new Color(193, 18, 31));
        btnXoa.setOpaque(false);
        btnXoa.setContentAreaFilled(false);
        btnXoa.setBorderPainted(false);
        FontIcon trashIcon = FontIcon.of(MaterialDesign.MDI_DELETE, 20, new Color(193, 18, 31));
        btnXoa.setIcon(trashIcon);
        btnXoa.setHorizontalTextPosition(SwingConstants.RIGHT); // Text bên phải icon
        btnXoa.setIconTextGap(10); // Khoảng cách giữa icon và text
        btnXoa.setFocusPainted(false);
        btnXoa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnXoa.setOpaque(true);
                btnXoa.setBackground(new Color(255, 200, 200)); // nền đỏ nhạt
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnXoa.setOpaque(false);
                btnXoa.setBackground(null);
            }
        });
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(btnXoa, BorderLayout.EAST);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textPane = new JTextPane();
        textPane.setFont(fontChinh);

        JScrollPane scrollPane = new JScrollPane(textPane);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Nút xóa
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onXoa != null) {
                    onXoa.run();
                }
            }
        });
    }
    public void setOnXoa(Runnable onXoa) {
        this.onXoa = onXoa;
    }
    public JRadioButton getRadioButton() {
        return radioButton;
    }

    public String getNoiDungDapAn() {
        return textPane.getText().trim();
    }

    public boolean isSelected() {
        return radioButton.isSelected();
    }

    public void setTenDapAn(String tenMoi) {
        lblDapAn.setText(tenMoi);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test GiaoDienDapAn");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);
            frame.setLocationRelativeTo(null); // Căn giữa màn hình

            // Tạo một GiaoDienDapAn và thêm vào frame
            GiaoDienDapAn dapAn1 = new GiaoDienDapAn("Đáp án A");
            dapAn1.setOnXoa(() -> {
                JOptionPane.showMessageDialog(frame, "Bạn đã xoá đáp án!");
            });

            frame.add(dapAn1);
            frame.setVisible(true);
        });
    }
}
