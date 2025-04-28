package gui.custom;

import entities.*;
import service.BaiThiService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import service.HocSinhService;
import service.PhienLamBaiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiaoDienThi extends JFrame {
    private JLabel lblTime;
    private Timer timer;
    private int thoiGianConLai;
    private BaiThi baiThi;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy");
    private PhienLamBaiService phienLamBaiService = (PhienLamBaiService) Naming.lookup("rmi://192.168.1.13:8081/phienLamBaiService");

    private PhienLamBai phienLamBai;
    private HocSinh hocSinh;
    private Map<Integer, String> cauTraLoi = new HashMap<>(); // Map<soThuTu, dapAnDaChon>
    private List<NutCauHoi> danhSachNutCauHoi = new ArrayList<>();

    public GiaoDienThi(BaiThi baiThi,HocSinh hocSinh ) throws MalformedURLException, NotBoundException, RemoteException {
        this.baiThi = baiThi;
        this.hocSinh = hocSinh;
    }

    public void taoGiaoDienThi() {
        try {
            // Tạo mã phiên: yyyyMMddHHmmss + maHocSinh
            String maPhien = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + hocSinh.getMaHocSinh();

            phienLamBai = new PhienLamBai();
            phienLamBai.setMaPhien(maPhien);
            phienLamBai.setThoiGianBatDau(LocalDateTime.now());
            phienLamBai.setHocSinh(hocSinh);
            phienLamBai.setBaiThi(baiThi);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo phiên làm bài!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        setTitle("Làm bài thi");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Không cho đóng bằng nút X
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true); // Bỏ thanh tiêu đề để không thu nhỏ/phóng to được
        setResizable(false); // Không cho phép thay đổi kích thước

        // Thêm WindowListener để chặn các hành động thoát
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Không làm gì cả để chặn đóng cửa sổ
            }
        });

        // Thêm KeyListener để chặn Alt+F4
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F4 && e.isAltDown()) {
                    e.consume(); // Chặn Alt+F4
                }
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 246, 248));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // === Panel Thông tin bên trái ===
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(300, 0));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblTitle = new JLabel(baiThi.getTenBaiThi());
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTime = new JLabel(String.format("%02d:00", baiThi.getThoiLuong()));
        lblTime.setFont(new Font("Arial", Font.BOLD, 24));
        lblTime.setForeground(new Color(0, 105, 217));
        lblTime.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nút nộp bài
        JButton btnNopBai = new JButton("Nộp bài");

        btnNopBai.setBackground(new Color(40, 167, 69)); // Màu đỏ
        btnNopBai.setForeground(Color.WHITE);
        btnNopBai.setFocusPainted(false);
        btnNopBai.setFont(new Font("Arial", Font.BOLD, 18));
        btnNopBai.setPreferredSize(new Dimension(200, 40));
        btnNopBai.setForeground(Color.WHITE);
        btnNopBai.setFocusPainted(false);
        btnNopBai.setContentAreaFilled(false);
        btnNopBai.setOpaque(true);

        btnNopBai.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn nộp bài thi?",
                    "Xác nhận nộp bài",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (option == JOptionPane.YES_OPTION) {
                nopBai();
            }
        });

        // Nút trở về
        JButton btnTroVe = new JButton("Trở về");
        btnTroVe.setBackground(new Color(220, 53, 69)); // Màu đỏ
        btnTroVe.setForeground(Color.WHITE);
        btnTroVe.setFocusPainted(false);
        btnTroVe.setFont(new Font("Arial", Font.BOLD, 18));
        btnTroVe.setPreferredSize(new Dimension(200, 40));
        btnTroVe.setForeground(Color.WHITE);
        btnTroVe.setFocusPainted(false);
        btnTroVe.setContentAreaFilled(false);
        btnTroVe.setOpaque(true);

        btnTroVe.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn thoát khỏi bài thi?\nCác câu trả lời sẽ không được lưu.",
                "Xác nhận thoát",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (option == JOptionPane.YES_OPTION) {
                dispose(); // Đóng cửa sổ hiện tại
            }
        });

        infoPanel.add(lblTitle);
        infoPanel.add(Box.createVerticalStrut(30));
        infoPanel.add(lblTime);
        infoPanel.add(Box.createVerticalStrut(10));

        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(btnTroVe);

        // === Panel Nội dung câu hỏi chính giữa ===
        JPanel questionPanel = new JPanel(new BorderLayout(10, 10));
        questionPanel.setBackground(Color.WHITE);
        questionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Hiển thị câu hỏi đầu tiên (nếu có)
        String cauHoiText = "Chưa có câu hỏi";
        if (!baiThi.getDanhSachCauHoi().isEmpty()) {
            CauHoi cauHoiDauTien = baiThi.getDanhSachCauHoi().get(0);
            cauHoiText = "";
        }

        JTextArea lblCauHoi = new JTextArea(cauHoiText);
        lblCauHoi.setFont(new Font("Arial", Font.BOLD, 20));
        lblCauHoi.setWrapStyleWord(true);
        lblCauHoi.setLineWrap(true);
        lblCauHoi.setEditable(false);
        lblCauHoi.setFocusable(false);
        lblCauHoi.setBackground(null);
        lblCauHoi.setBorder(null);
        lblCauHoi.setMargin(new Insets(10, 10, 10, 10));

        JPanel answersPanel = new JPanel();
        answersPanel.setLayout(new BoxLayout(answersPanel, BoxLayout.Y_AXIS));
        answersPanel.setBackground(Color.WHITE);

        ButtonGroup answerGroup = new ButtonGroup();

        answersPanel.removeAll();
        answerGroup.clearSelection();
        questionPanel.add(lblCauHoi, BorderLayout.NORTH);
        questionPanel.add(answersPanel, BorderLayout.CENTER);

        // === Panel Mục lục câu hỏi bên phải ===
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setPreferredSize(new Dimension(250, 0));
        navigationPanel.setBackground(Color.WHITE);
        navigationPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblMucLuc = new JLabel("Mục lục câu hỏi");
        lblMucLuc.setFont(new Font("Arial", Font.BOLD, 20));
        lblMucLuc.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel btnListPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnListPanel.setBackground(Color.WHITE);

        // Tạo NutCauHoi cho mỗi câu hỏi trong bài thi
        List<CauHoi> danhSachCauHoi = layDanhSachCauHoiAnToan();
        danhSachNutCauHoi.clear();

        for (int i = 0; i < danhSachCauHoi.size(); i++) {
            CauHoi cauHoi = danhSachCauHoi.get(i);
            NutCauHoi nutCauHoi = new NutCauHoi(i + 1, cauHoi);
            nutCauHoi.setFocusPainted(false);
            nutCauHoi.setContentAreaFilled(false);
            nutCauHoi.setOpaque(true);
            danhSachNutCauHoi.add(nutCauHoi);

            // Thêm sự kiện click cho nút
            nutCauHoi.addActionListener(e -> {
                // Cập nhật nội dung câu hỏi khi click vào nút
                lblCauHoi.setText("Câu " + nutCauHoi.getSoThuTu() + ": " + cauHoi.getNoiDung());

                // Cập nhật các đáp án
                answerGroup.clearSelection();
                answersPanel.removeAll();

                // Thêm các đáp án mới
                List<String> danhSachDapAn = cauHoi.getDanhSachDapAn();
                final int cauHoiIndex = nutCauHoi.getSoThuTu();

                for (int j = 0; j < danhSachDapAn.size(); j++) {
                    final String dapAn = danhSachDapAn.get(j);
                    final String dapAnLabel = dapAn;

                    JRadioButton radio = new JRadioButton(dapAnLabel);
                    radio.setFont(new Font("Arial", Font.PLAIN, 18));
                    radio.setBackground(Color.WHITE);

                    // Kiểm tra xem câu hỏi này đã được trả lời chưa
                    if (cauTraLoi.containsKey(cauHoiIndex) &&
                        cauTraLoi.get(cauHoiIndex).equals(dapAn)) {
                        radio.setSelected(true);
                    }

                    radio.addActionListener(actionEvent -> {
                        cauTraLoi.put(cauHoiIndex, dapAn);

                        nutCauHoi.setAnswered(true);
                    });

                    answerGroup.add(radio);
                    answersPanel.add(radio);
                    answersPanel.add(Box.createVerticalStrut(10));
                }

                answersPanel.revalidate();
                answersPanel.repaint();
            });

            btnListPanel.add(nutCauHoi);
        }

        navigationPanel.add(lblMucLuc);
        navigationPanel.add(Box.createVerticalStrut(10));
        navigationPanel.add(btnListPanel);
        navigationPanel.add(Box.createVerticalGlue());
        navigationPanel.add(btnNopBai);
        // Add tất cả vào mainPanel
        mainPanel.add(infoPanel, BorderLayout.WEST);
        mainPanel.add(questionPanel, BorderLayout.CENTER);
        mainPanel.add(navigationPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
        setVisible(true);

        // Bắt đầu đồng hồ đếm ngược theo thời lượng của bài thi
        khoiDongDemNguoc(baiThi.getThoiLuong());
    }

    private List<CauHoi> layDanhSachCauHoiAnToan() {
        try {
            if (baiThi != null) {
                List<CauHoi> danhSachCauHoi = baiThi.getDanhSachCauHoi();
                if (danhSachCauHoi != null) {
                    return danhSachCauHoi;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi truy cập danh sách câu hỏi: ",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return new ArrayList<>(); // Trả về danh sách rỗng nếu có lỗi
    }

    private void khoiDongDemNguoc(int soPhut) {
        thoiGianConLai = soPhut * 60;

        timer = new Timer(1000, e -> {
            if (thoiGianConLai > 0) {
                thoiGianConLai--;
                int phut = thoiGianConLai / 60;
                int giay = thoiGianConLai % 60;
                lblTime.setText(String.format("%02d:%02d", phut, giay));

                if (thoiGianConLai <= 30) {
                    lblTime.setForeground(Color.RED);
                } else if (thoiGianConLai <= 120) {
                    lblTime.setForeground(new Color(255, 140, 0));
                } else {
                    lblTime.setForeground(new Color(0, 105, 217));
                }
            } else {
                timer.stop();
                nopBai();
                dispose();
            }
        });
        timer.start();
    }
    private void nopBai() {
        if (timer != null) timer.stop(); // Dừng timer nếu còn chạy

        try {
            int soCauDung = 0;
            int tongCauHoi = baiThi.getDanhSachCauHoi().size();

            phienLamBai.setThoiGianKetThuc(java.time.LocalDateTime.now());

            List<CauTraLoi> danhSachCauTraLoi = new ArrayList<>();

            for (int i = 0; i < tongCauHoi; i++) {
                CauHoi cauHoi = baiThi.getDanhSachCauHoi().get(i);
                String dapAnDaChon = cauTraLoi.get(i + 1);

                CauTraLoi cauTraLoiEntity = new CauTraLoi();
                cauTraLoiEntity.setNoiDungCauHoi(cauHoi.getNoiDung());
                cauTraLoiEntity.setDanhSachDapAn(new ArrayList<>(cauHoi.getDanhSachDapAn()));
                cauTraLoiEntity.setPhienLamBai(phienLamBai);

                if (dapAnDaChon != null) {
                    cauTraLoiEntity.setDapAnDaChon(dapAnDaChon);
                    boolean isCorrect = dapAnDaChon.charAt(0) == cauHoi.getDapAnDung().charAt(0);
                    cauTraLoiEntity.setKetQua(isCorrect);

                    if (isCorrect) soCauDung++;
                } else {
                    cauTraLoiEntity.setDapAnDaChon("");
                    cauTraLoiEntity.setKetQua(false);
                }

                danhSachCauTraLoi.add(cauTraLoiEntity);
            }

            double diem = (double) soCauDung / tongCauHoi * 10.0;
            phienLamBai.setDiem(diem);
            phienLamBai.setDanhSachCauTraLoi(danhSachCauTraLoi);

            phienLamBaiService.save(phienLamBai);

            DialogKetQua.showResultDialog(this, soCauDung, tongCauHoi, diem);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi lưu phiên làm bài: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            dispose(); // đóng giao diện
        }
    }




    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        try {
            BaiThiService baiThiService = (BaiThiService) Naming.lookup("rmi://192.168.1.13:8081/baiThiService");
            // Sử dụng layThongTinBaiThiVaCauHoi thay vì layThongTinChiTietBaiThi để đảm bảo các collection được load
            BaiThi baiThi = baiThiService.layThongTinBaiThiVaCauHoi(9);
            if (baiThi != null) {
                System.out.println("Tên bài thi: " + baiThi.getTenBaiThi());
                System.out.println("Số câu hỏi: " + (baiThi.getDanhSachCauHoi() != null ? baiThi.getDanhSachCauHoi().size() : 0));

                // Tạo và hiển thị giao diện thi trong EDT
                SwingUtilities.invokeLater(() -> {
                    GiaoDienThi giaoDienThi = null;
                    try {
                        HocSinhService hocSinhService = (HocSinhService) Naming.lookup("rmi://localhost:8081/hocSinhService");
                        giaoDienThi = new GiaoDienThi(baiThi, hocSinhService.finByID(89L));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (NotBoundException e) {
                        throw new RuntimeException(e);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    giaoDienThi.taoGiaoDienThi();
                });
            } else {
                System.out.println("Không tìm thấy bài thi với mã 9");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối đến server: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
