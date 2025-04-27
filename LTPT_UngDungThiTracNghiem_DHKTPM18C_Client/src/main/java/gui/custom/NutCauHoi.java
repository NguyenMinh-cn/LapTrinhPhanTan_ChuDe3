package gui.custom;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import entities.CauHoi;
import java.util.Arrays;

public class NutCauHoi extends JButton {
    private static NutCauHoi selectedButton = null; // Biến tĩnh để lưu nút đã chọn
    private boolean isSelected = false; // Để theo dõi trạng thái của nút
    private int soThuTu;
    private CauHoi cauHoi; // Đối tượng CauHoi tương ứng

    // Constructor nhận vào số thứ tự và đối tượng CauHoi
    public NutCauHoi(int soThuTu, CauHoi cauHoi) {
        this.soThuTu = soThuTu;
        this.cauHoi = cauHoi;
        initButton();
    }

    private void initButton() {
        // Thiết lập màu sắc nền và border
        this.setBackground(new Color(255, 194, 209)); // Màu hồng nhạt
        this.setFont(new Font("Arial", Font.BOLD, 18));
        this.setText(String.valueOf(soThuTu));
        this.setFocusPainted(false);
        this.setBorder(BorderFactory.createLineBorder(new Color(255, 194, 209), 2)); // Viền xung quanh
        this.setOpaque(true); // Đảm bảo nền màu không bị trong suốt
        this.setPreferredSize(new Dimension(50, 40)); // Kích thước của nút
        this.setMargin(new Insets(5, 10, 5, 10)); // Định kích thước lề

        // Bo tròn nút
        this.setBorder(BorderFactory.createLineBorder(new Color(255, 194, 209), 2, true));

        // Thêm hiệu ứng khi nhấn nút
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(new Color(255, 194, 209)); // Màu hồng đậm khi nhấn
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isSelected) {
                    setBackground(new Color(255, 143, 171)); // Giữ màu hồng đậm khi chọn
                } else {
                    setBackground(new Color(255, 194, 209)); // Quay lại màu hồng nhạt
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hiển thị con trỏ tay khi di chuột vào
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Trở lại con trỏ mặc định
            }
        });

        // Thêm sự kiện khi nút được click
        this.addActionListener(e -> {
            // Nếu có nút đã được chọn trước đó, quay lại màu ban đầu
            if (selectedButton != null) {
                selectedButton.setBackground(new Color(255, 194, 209)); // Quay lại màu hồng nhạt
                selectedButton.isSelected = false; // Đặt trạng thái của nút đã chọn về false
            }

            // Cập nhật màu cho nút hiện tại
            isSelected = true;
            setBackground(new Color(255, 143, 171));

            selectedButton = this;

            NutCauHoi selectedNutt = this;

        });
    }

    // Phương thức getter để lấy đối tượng CauHoi
    public CauHoi getCauHoi() {
        return cauHoi;
    }

    public void setSoThuTu(int soThuTu) {
        this.soThuTu = soThuTu;
    }

    // Phương thức tĩnh để lấy đối tượng nutCauHoi đã được chọn
    public static NutCauHoi getSelectedButton() {
        return selectedButton;
    }

    public static void main(String[] args) {
        // Tạo 3 câu hỏi
        CauHoi cauHoi1 = new CauHoi(1, "Câu hỏi 1: Java là gì?", Arrays.asList("Ngôn ngữ lập trình", "Hệ điều hành", "Trình biên dịch"), "Ngôn ngữ lập trình", null, null);
        CauHoi cauHoi2 = new CauHoi(2, "Câu hỏi 2: Java được phát triển bởi ai?", Arrays.asList("Oracle", "Microsoft", "Apple"), "Oracle", null, null);
        CauHoi cauHoi3 = new CauHoi(3, "Câu hỏi 3: Câu lệnh nào để in ra màn hình trong Java?", Arrays.asList("print()", "println()", "echo()"), "println()", null, null);

        // Tạo cửa sổ JFrame
        JFrame frame = new JFrame("Danh sách câu hỏi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        // Tạo nút câu hỏi và thêm vào frame
        NutCauHoi nut1 = new NutCauHoi(1, cauHoi1);
        NutCauHoi nut2 = new NutCauHoi(2, cauHoi2);
        NutCauHoi nut3 = new NutCauHoi(3, cauHoi3);

        // Thêm các nút vào frame
        frame.add(nut1);
        frame.add(nut2);
        frame.add(nut3);

        // Hiển thị cửa sổ
        frame.setVisible(true);

        // Đợi sự kiện nhấn nút và lấy đối tượng đã được chọn
        // Sau khi nút được nhấn, ta có thể truy xuất đối tượng được chọn
        // Lấy đối tượng đã được chọn và in ra thông tin câu hỏi
        javax.swing.Timer timer = new javax.swing.Timer(500, e -> {
            NutCauHoi selectedButton = NutCauHoi.getSelectedButton();
            if (selectedButton != null) {
                System.out.println("Câu hỏi được chọn: " + selectedButton.getCauHoi());
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
