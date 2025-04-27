package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;

public class PasswordFieldWithToggle {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Mật khẩu với nút con mắt");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 150);
            frame.setLayout(new FlowLayout());

            // Tạo password field
            JPasswordField passwordField = new JPasswordField(20);

            // Tạo nút con mắt
            JButton toggleButton = new JButton(); // Biểu tượng con mắt Unicode
            toggleButton.setIcon(FontIcon.of(MaterialDesign.MDI_EYE, 20, Color.BLACK));
            toggleButton.setPreferredSize(new Dimension(50, 30));

            // Ban đầu, mật khẩu sẽ bị ẩn
            passwordField.setEchoChar('*');

            // Bắt sự kiện click nút con mắt
            toggleButton.addActionListener(new ActionListener() {
                private boolean showing = false;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (showing) {
                        passwordField.setEchoChar('*'); // Ẩn mật khẩu
                        toggleButton.setIcon(FontIcon.of(MaterialDesign.MDI_EYE_OFF, 20, Color.BLACK));
                    } else {
                        passwordField.setEchoChar((char) 0); // Hiện mật khẩu
                        toggleButton.setIcon(FontIcon.of(MaterialDesign.MDI_EYE, 20, Color.BLACK));
                    }
                    showing = !showing;
                }
            });

            // Thêm vào frame
            frame.add(passwordField);
            frame.add(toggleButton);

            frame.setLocationRelativeTo(null); // Canh giữa màn hình
            frame.setVisible(true);
        });
    }
}
