package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Locale;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import entities.*;
import gui.custom.*;
import lombok.SneakyThrows;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;
import service.BaiThiService;
import service.CauHoiService;
import service.LopService;
import service.MonHocService;
import service.GiaoVienService;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class GiaoDienDanhSachBaiThi extends JPanel {
    private BaiThiService baiThiService = (BaiThiService) Naming.lookup("rmi://localhost:8081/baiThiService");
    private PanelThoiGianThi panelThoiGianThi;
    private JPanel panel1;
    private JButton btnTaoDeThi;
    private JPanel pnHienThiCacBaiThi;
    private JPanel pnNoiDung;
    private JPanel pnCard;
    private JPanel pnDSBaiThi;
    private JPanel pnTaoDeThi;
    private JButton btnQuayLai;
    private JButton btnTaoCauHoi;
    private JPanel pnChucNang1;
    private JPanel pnChucNang2;
    private JTextField txtTenDeThi;
    private JComboBox cbBoxMonHoc;
    private JComboBox cbBoxThoiLuong;
    private JPanel pnThoiGian;
    private JPanel pnDSLop;
    private JPanel pnMatKhau;
    private JCheckBox ckBSuDungMK;
    private JPanel pnChucNangMK;
    private JTextField txtNhapMatKhau;
    private JButton btnHuongDan;
    private JTextArea txtANhapCauHoi;
    private JPanel pnSoanCauHoi;
    private JButton btnQuayLaiCard2;
    private JButton btnLuuBaiThi;
    private JButton btnThemCauHoi;
    private JPanel pnDSSoCauHoi;
    private JTextArea txtANoiDungCauHoi;

    private JPanel pnNoiDungCauHoiVaDapAn;
    private JPanel pnChiTietNoiDungCauHoi;
    private JButton btnThemCHTuNganHangCH;
    private JPanel pnThongTinBaiThi;
    private JButton btnQuayLai3;
    private JPanel pnTTCT;
    private JButton btnXoaCauHoi;
    private JComboBox cbBoxSoLanLamKiemTra;
    private final CardLayout cardLayout;
    private CauHoiService cauHoiService = (CauHoiService) Naming.lookup("rmi://localhost:8081/cauHoiService");
    private final List<Lop> lopDaChon = new ArrayList<>();
    private final GiaoVien giaoVienDangNhap;
    private List<MonHoc> monHocList;
    private JButton btnCauHoiDangChon = null;

    //    private List<BaiThi> dsBaiThi;
    public static String chuyenDinhDangNgayGio(LocalDateTime localDateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy");
            return localDateTime.format(formatter);
        } catch (Exception e) {
            e.printStackTrace();
            return "Định dạng không hợp lệ";
        }
    }

    public GiaoDienDanhSachBaiThi(GiaoVien giaoVien) throws MalformedURLException, NotBoundException, RemoteException {
        this.giaoVienDangNhap = giaoVien;
        $$$setupUI$$$();
        cardLayout = (CardLayout) pnCard.getLayout();

        taoDSBaiThi();

        btnTaoDeThi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(pnCard, "Card2");
                try {
                    txtTenDeThi.setText("");
                    pnThoiGian.removeAll();
                    panelThoiGianThi = new PanelThoiGianThi();
                    pnThoiGian.add(panelThoiGianThi.getPanel());
                    txtNhapMatKhau.setText("");
                    taoJCheckBoxLop();
                    taoJComboBoxMonHoc();
                    cbBoxThoiLuong.setSelectedItem("");
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (NotBoundException ex) {
                    throw new RuntimeException(ex);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnQuayLai.setIcon(FontIcon.of(MaterialDesign.MDI_ARROW_LEFT_BOLD, 16, Color.WHITE));
        btnQuayLai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(pnCard, "Card1");
            }
        });

        ckBSuDungMK.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (ckBSuDungMK.isSelected()) {

                    pnMatKhau.add(pnChucNangMK, BorderLayout.CENTER);
                } else {
                    pnMatKhau.remove(pnChucNangMK);
                    txtNhapMatKhau.setText("");
                }

                // Cập nhật lại giao diện sau khi thay đổi thành phần
                pnMatKhau.revalidate();
                pnMatKhau.repaint();
            }
        });

        btnHuongDan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(GiaoDienDanhSachBaiThi.this);
                showHuongDanDialog(parentFrame);
            }
        });
        btnThemCauHoi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                taoPhanThemCauHoi();

            }
        });
        btnQuayLaiCard2.setIcon(FontIcon.of(MaterialDesign.MDI_ARROW_LEFT_BOLD, 16, Color.WHITE));
        btnQuayLaiCard2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(pnCard, "Card2");
            }
        });
        btnTaoCauHoi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Kiểm tra Tên đề thi
                String tenDeThi = txtTenDeThi.getText().trim();
                if (tenDeThi.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên đề thi.");
                    return;
                }
                // Kiểm tra Môn học
                if (cbBoxMonHoc.getSelectedItem() == "") {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn môn học.");
                    return;
                }
                // Kiểm tra Thời lượng
                if (cbBoxThoiLuong.getSelectedItem() == null || cbBoxThoiLuong.getSelectedItem().toString().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn thời lượng làm bài.");
                    return;
                }
                // Kiểm tra Thời gian bắt đầu và kết thúc
                if (panelThoiGianThi.getStartDateTime() == null || panelThoiGianThi.getEndDateTime() == null) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn đầy đủ thời gian bắt đầu và kết thúc.");
                    return;
                }
                if (panelThoiGianThi.getEndDateTime().isBefore(panelThoiGianThi.getStartDateTime()) || panelThoiGianThi.getEndDateTime().equals(panelThoiGianThi.getStartDateTime())) {
                    JOptionPane.showMessageDialog(null, "Thời gian kết thúc phải sau thời gian bắt đầu.");
                    return;
                }
                // Lấy thời lượng được chọn (phút)
                int thoiLuongPhut;
                try {
                    String thoiLuongStr = cbBoxThoiLuong.getSelectedItem().toString();
                    // Tách phần số từ chuỗi (loại bỏ " phút")
                    thoiLuongStr = thoiLuongStr.replace(" phút", "");
                    thoiLuongPhut = Integer.parseInt(thoiLuongStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Thời lượng không hợp lệ.");
                    return;
                }
                // Tính thời gian chênh lệch
                long durationMinutes = Duration.between(panelThoiGianThi.getStartDateTime(), panelThoiGianThi.getEndDateTime()).toMinutes();
                System.out.println(durationMinutes);
                if (durationMinutes < thoiLuongPhut) {
                    JOptionPane.showMessageDialog(null, "Thời lượng giữa thời gian bắt đầu và kết thúc phải đúng bằng " + thoiLuongPhut + " phút.");
                    return;
                }
                // Kiểm tra ít nhất 1 checkbox lớp được chọn
                boolean coLopDuocChon = false;
                for (Component comp : pnDSLop.getComponents()) {
                    if (comp instanceof JCheckBox cb) {
                        if (cb.isSelected()) {
                            coLopDuocChon = true;
                            break;
                        }
                    }
                }
                if (!coLopDuocChon) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn ít nhất một lớp.");
                    return;
                }
                if (ckBSuDungMK.isSelected()) {
                    String matKhau = txtNhapMatKhau.getText().trim();
                    if (matKhau.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập mật khẩu!");
                        return;
                    }
                }

                cardLayout.show(pnCard, "Card3");
                pnDSSoCauHoi.removeAll();
                pnDSSoCauHoi.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));
            }
        });
        btnLuuBaiThi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {


                    Component[] components = pnDSSoCauHoi.getComponents();
                    List<CauHoi> danhSachCauHoi1 = new ArrayList<>();

                    // Duyệt qua từng component
                    for (Component component : components) {
                        if (component instanceof NutCauHoi) {
                            NutCauHoi nutCauHoi = (NutCauHoi) component;
                            danhSachCauHoi1.add(nutCauHoi.getCauHoi());
                        }
                    }
                    if (danhSachCauHoi1 == null || danhSachCauHoi1.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Không có câu hỏi để lưu.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Hiển thị thông báo đang xử lý
                    JOptionPane optionPane = new JOptionPane("Đang lưu bài thi...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                    JDialog dialog = optionPane.createDialog("Thông báo");
                    dialog.setModal(false);
                    dialog.setVisible(true);

                    // Bước 1: Lấy môn học được chọn
                    String monHocTen = (String) cbBoxMonHoc.getSelectedItem();
                    MonHoc monHocDuocChon = null;

                    // Tìm môn học trong danh sách monHocList
                    for (MonHoc monHoc : monHocList) {
                        if (monHoc.getTenMon().equals(monHocTen)) {
                            monHocDuocChon = monHoc;
                            break;
                        }
                    }

                    if (monHocDuocChon == null) {
                        dialog.dispose();
                        JOptionPane.showMessageDialog(null, "Không tìm thấy môn học đã chọn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Bước 2: Lưu các câu hỏi
                    List<CauHoi> cauHoiDaLuuThanhCong = cauHoiService.luuNhieuVaTraVeMa(danhSachCauHoi1);

                    if (cauHoiDaLuuThanhCong == null || cauHoiDaLuuThanhCong.isEmpty()) {
                        dialog.dispose();
                        JOptionPane.showMessageDialog(null, "Không thể lưu câu hỏi. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Bước 3: Tạo bài thi
                    BaiThi baiThi = new BaiThi();
                    baiThi.setTenBaiThi(txtTenDeThi.getText().trim());
                    baiThi.setMonHoc(monHocDuocChon);
                    baiThi.setThoiGianBatDau(panelThoiGianThi.getStartDateTime());
                    baiThi.setThoiGianKetThuc(panelThoiGianThi.getEndDateTime());
                    baiThi.setThoiLuong(Integer.parseInt(cbBoxThoiLuong.getSelectedItem().toString().replace(" phút", "")));
                    baiThi.setMatKhau(ckBSuDungMK.isSelected() ? txtNhapMatKhau.getText().trim() : null);
                    baiThi.setDanhSachCauHoi(cauHoiDaLuuThanhCong);
                    baiThi.setDanhSachLop(lopDaChon);
                    baiThi.setGiaoVien(giaoVienDangNhap);
                    Object selectedItem = cbBoxSoLanLamKiemTra.getSelectedItem();
                    if (selectedItem != null && selectedItem.toString().equalsIgnoreCase("Không giới hạn")) {
                        baiThi.setSoLanDuocPhepLamBai(0);
                    } else {
                        baiThi.setSoLanDuocPhepLamBai(Integer.parseInt(selectedItem.toString()));
                    }
                    // Bước 4: Lưu bài thi
                    boolean baiThiDaLuu = baiThiService.save(baiThi);

                    // Đóng dialog thông báo
                    dialog.dispose();

                    if (baiThiDaLuu) {
                        // Hiển thị thông báo thành công
                        JOptionPane.showMessageDialog(null, "Lưu bài thi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        // Reset dữ liệu
                        txtTenDeThi.setText("");
                        cbBoxThoiLuong.setSelectedItem("");
                        lopDaChon.clear();

                        // Xóa nội dung panel câu hỏi
                        pnDSSoCauHoi.removeAll();
                        pnDSSoCauHoi.revalidate();
                        pnDSSoCauHoi.repaint();

                        // Quay lại card1 và load lại danh sách bài thi
                        cardLayout.show(pnCard, "Card1");

                        // Load lại danh sách bài thi
                        try {
                            taoDSBaiThi();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null,
                                    "Đã lưu bài thi thành công nhưng không thể tải lại danh sách: " + ex.getMessage(),
                                    "Cảnh báo",
                                    JOptionPane.WARNING_MESSAGE);
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Lưu bài thi thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi không xác định: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        btnQuayLai3.setIcon(FontIcon.of(MaterialDesign.MDI_ARROW_LEFT_BOLD, 16, Color.WHITE));
        btnQuayLai3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(pnCard, "Card1");
            }
        });
        btnXoaCauHoi.setIcon(FontIcon.of(MaterialDesign.MDI_DELETE, 16, new Color(193, 18, 31)));
        btnXoaCauHoi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Bạn có chắc muốn xóa câu hỏi này không?",
                        "Xóa",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    if (btnCauHoiDangChon != null) {
                        pnDSSoCauHoi.remove(btnCauHoiDangChon);
                        pnChiTietNoiDungCauHoi.removeAll();
                        pnChiTietNoiDungCauHoi.repaint();
                        pnChiTietNoiDungCauHoi.revalidate();
                        btnCauHoiDangChon = null;
                        Component[] components = pnDSSoCauHoi.getComponents();
                        int index = 1;
                        for (Component component : components) {
                            if (component instanceof NutCauHoi) {
                                NutCauHoi nut = (NutCauHoi) component;
                                nut.setSoThuTu(index);
                                nut.setText(String.valueOf(index));
                                index++;
                            }
                        }
                        pnDSSoCauHoi.repaint();
                        pnDSSoCauHoi.revalidate();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });
    }

    private void taoPhanThemCauHoi() {
        // Tạo và cấu hình JPanel pnNoiDungCauHoi khi nhấn nút "Thêm câu hỏi"
        btnThemCauHoi.setEnabled(false);
        JPanel pnNoiDungCauHoi = new JPanel();
        pnNoiDungCauHoi.setBackground(Color.WHITE);
        pnNoiDungCauHoi.setLayout(new BoxLayout(pnNoiDungCauHoi, BoxLayout.Y_AXIS));
        JLabel nd1 = new JLabel("Nhập nội dung câu hỏi");
        nd1.setFont(new Font("Arial", Font.PLAIN, 20));
        pnNoiDungCauHoi.add(nd1);

        // Tạo JTextArea cho nội dung câu hỏi
        txtANoiDungCauHoi = new JTextArea();
        txtANoiDungCauHoi.setFont(new Font("Arial", Font.PLAIN, 16));
        txtANoiDungCauHoi.setLineWrap(true);  // Tự động xuống dòng
        txtANoiDungCauHoi.setWrapStyleWord(true); // Xuống dòng theo từ
        txtANoiDungCauHoi.setMargin(new Insets(10, 10, 10, 10)); // Thêm margin
        txtANoiDungCauHoi.setMinimumSize(new Dimension(300, 100)); // Chiều rộng và chiều cao tối thiểu

        JScrollPane scrollPaneCauHoi = new JScrollPane(txtANoiDungCauHoi);
        scrollPaneCauHoi.setPreferredSize(new Dimension(500, 100)); // Cố định chiều rộng và chiều cao

        pnNoiDungCauHoi.add(scrollPaneCauHoi);

        // Thêm pnNoiDungCauHoi vào panel cha (pnNoiDungCauHoiVaDapAn)
        pnNoiDungCauHoiVaDapAn.add(pnNoiDungCauHoi, BorderLayout.NORTH);

        // Khởi tạo pnThemDapAn
        JPanel pnThemDapAn = new JPanel(new BorderLayout());

        // Khởi tạo danh sách đáp án
        List<GiaoDienDapAn> danhSachDapAn = new ArrayList<>();
        ButtonGroup buttonGroup = new ButtonGroup();

        // Khởi tạo panel chứa các đáp án
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        pnThemDapAn.add(scrollPane, BorderLayout.CENTER);

        // Tạo panel dưới cùng để chứa nút "Thêm đáp án" và "Lưu đáp án", "Xóa câu hỏi"
        JPanel bottomPanel = new JPanel();
        JButton btnXoaCauHoi = new JButton("Hủy thêm câu hỏi");
        btnXoaCauHoi.setFont(new Font("Arial", Font.PLAIN, 18));
        btnXoaCauHoi.setBackground(new Color(135, 206, 235)); // Light blue
        btnXoaCauHoi.setForeground(Color.BLACK);
        btnXoaCauHoi.addActionListener(evt -> {
            // Xác nhận trước khi xóa
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Bạn có chắc muốn hủy nhập câu hỏi này không?",
                    "Xác nhận hủy",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                pnNoiDungCauHoiVaDapAn.removeAll();
                pnNoiDungCauHoiVaDapAn.repaint();
                pnNoiDungCauHoiVaDapAn.revalidate();
                btnThemCauHoi.setEnabled(true);

            }
        });

        bottomPanel.add(btnXoaCauHoi);
        JButton btnThemDapAn = new JButton("Thêm đáp án");
        btnThemDapAn.setFont(new Font("Arial", Font.PLAIN, 18));
        btnThemDapAn.setBackground(new Color(255, 223, 186)); // Light orange
        btnThemDapAn.setForeground(Color.BLACK);

        JButton btnLuuDapAn = new JButton("Lưu câu hỏi");
        btnLuuDapAn.setFont(new Font("Arial", Font.PLAIN, 18));
        btnLuuDapAn.setBackground(new Color(144, 238, 144)); // Light green
        btnLuuDapAn.setForeground(Color.BLACK);
        btnLuuDapAn.addActionListener(evt -> {
            boolean hopLe = true;
            boolean coDapAnDuocChon = false;
            if (txtANoiDungCauHoi.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nội dung câu hỏi không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (danhSachDapAn.size() < 2) {
                JOptionPane.showMessageDialog(null, "Câu hỏi phải có ít nhất 2 đáp án.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (GiaoDienDapAn dapAn : danhSachDapAn) {
                if (dapAn.getNoiDungDapAn().isEmpty()) {
                    hopLe = false;
                    break;
                }
                if (dapAn.isSelected()) {
                    coDapAnDuocChon = true;
                }
            }

            if (!hopLe) {
                JOptionPane.showMessageDialog(null, "Tất cả đáp án phải có nội dung.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!coDapAnDuocChon) {
                JOptionPane.showMessageDialog(null, "Phải chọn một đáp án đúng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            CauHoi cauHoi1 = new CauHoi();
            cauHoi1.setNoiDung(txtANoiDungCauHoi.getText().trim());
            List<String> danhSach = new ArrayList<>();
            String dapAnDung = "";
            for (GiaoDienDapAn dapAn : danhSachDapAn) {
                String nd = dapAn.getNoiDungDapAn();
                danhSach.add(nd);
                if (dapAn.isSelected()) {
                    dapAnDung = nd;
                }
            }
            cauHoi1.setDanhSachDapAn(danhSach);
            cauHoi1.setDapAnDung(dapAnDung);

            // Nếu hợp lệ -> thêm số câu hỏi vào panel danh sách câu hỏi
            int soCau = pnDSSoCauHoi.getComponentCount() + 1;
            NutCauHoi btnCauHoi = new NutCauHoi(soCau, cauHoi1);
            // Xử lý khi nhấn vào nút câu hỏi (xem lại)
            btnCauHoi.addActionListener(viewEvt -> {
//                JOptionPane.showMessageDialog(null, cauHoi1.toString(), "Chi tiết câu hỏi", JOptionPane.INFORMATION_MESSAGE);
//                cauHoiDangChon = cauHoi1;
                btnCauHoiDangChon = btnCauHoi;
                //phần hiện câu hỏi
                pnChiTietNoiDungCauHoi.removeAll();
                pnChiTietNoiDungCauHoi.setLayout(new BoxLayout(pnChiTietNoiDungCauHoi, BoxLayout.Y_AXIS));

                Font font = new Font("Arial", Font.PLAIN, 18);

                String noiDung = "<html><div>" + btnCauHoi.getCauHoi().getNoiDung().replace("\n", "<br>") + "</div></html>";
                System.out.println(noiDung);
                JLabel lblNoiDung = new JLabel(noiDung);
                lblNoiDung.setFont(font);
                pnChiTietNoiDungCauHoi.add(lblNoiDung);
                pnChiTietNoiDungCauHoi.add(Box.createVerticalStrut(10));

                List<String> danhSachDapAnCuaCauHoiHienTai = btnCauHoi.getCauHoi().getDanhSachDapAn();
                String dapAnDungCuaCauHoiHienTai = btnCauHoi.getCauHoi().getDapAnDung();

                for (int i = 0; i < danhSachDapAnCuaCauHoiHienTai.size(); i++) {
                    String dapAn = danhSachDapAnCuaCauHoiHienTai.get(i);
                    JLabel cb = new JLabel("<html><div>" + dapAn.replace("\n", "<br>") + "</div></html>");
                    cb.setOpaque(false);
                    cb.setFont(font);
                    cb.setIcon(dapAn.equals(dapAnDungCuaCauHoiHienTai) ? FontIcon.of(MaterialDesign.MDI_CHECK_CIRCLE, 16, new Color(173, 193, 120)) : FontIcon.of(MaterialDesign.MDI_CHECKBOX_BLANK_CIRCLE_OUTLINE, 16, Color.BLACK));
                    pnChiTietNoiDungCauHoi.add(cb);
                }
                pnChiTietNoiDungCauHoi.revalidate();
                pnChiTietNoiDungCauHoi.repaint();
                //phần chỉnh sửa nội dung

            });
            pnDSSoCauHoi.add(btnCauHoi);
            pnDSSoCauHoi.revalidate();
            pnDSSoCauHoi.repaint();
            pnNoiDungCauHoiVaDapAn.removeAll();
            pnNoiDungCauHoiVaDapAn.repaint();
            pnNoiDungCauHoiVaDapAn.revalidate();
            btnThemCauHoi.setEnabled(true);

            JOptionPane.showMessageDialog(null, "Lưu đáp án thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        });

        bottomPanel.add(btnThemDapAn);
        bottomPanel.add(btnLuuDapAn);
        pnThemDapAn.add(bottomPanel, BorderLayout.SOUTH);
        pnNoiDungCauHoiVaDapAn.add(pnThemDapAn, BorderLayout.CENTER);
        // Hàm cập nhật
        Runnable capNhatSoThuTu = () -> {
            for (int i = 0; i < danhSachDapAn.size(); i++) {
                danhSachDapAn.get(i).setTenDapAn("Đáp án " + (i + 1));
            }
            mainPanel.revalidate();
            mainPanel.repaint();
        };

        // Hàm thêm đáp án
        Runnable themDapAn = () -> {
            GiaoDienDapAn dapAn = new GiaoDienDapAn("");

            dapAn.setOnXoa(() -> {
                danhSachDapAn.remove(dapAn);
                buttonGroup.remove(dapAn.getRadioButton());
                mainPanel.remove(dapAn);
                capNhatSoThuTu.run();
            });

            danhSachDapAn.add(dapAn);
            buttonGroup.add(dapAn.getRadioButton());
            mainPanel.add(dapAn);
            capNhatSoThuTu.run();
        };

        for (int i = 0; i < 4; i++) {
            themDapAn.run();
        }

        btnThemDapAn.addActionListener(evt -> themDapAn.run());

        pnThemDapAn.revalidate();
        pnThemDapAn.repaint();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        pnCard = new JPanel();
        pnCard.setLayout(new CardLayout(0, 0));
        pnCard.setBackground(new Color(-1));
        panel1.add(pnCard, BorderLayout.CENTER);
        pnDSBaiThi = new JPanel();
        pnDSBaiThi.setLayout(new BorderLayout(0, 0));
        pnCard.add(pnDSBaiThi, "Card1");
        pnNoiDung = new JPanel();
        pnNoiDung.setLayout(new BorderLayout(0, 0));
        pnNoiDung.setBackground(new Color(-1));
        pnDSBaiThi.add(pnNoiDung, BorderLayout.CENTER);
        pnChucNang2 = new JPanel();
        pnChucNang2.setLayout(new GridLayoutManager(1, 3, new Insets(10, 10, 10, 10), -1, -1));
        pnChucNang2.setBackground(new Color(-2954497));
        pnChucNang2.setPreferredSize(new Dimension(345, 70));
        pnDSBaiThi.add(pnChucNang2, BorderLayout.NORTH);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Arial", Font.BOLD, 20, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-16777216));
        label1.setText("Danh sách đề thi");
        pnChucNang2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnTaoDeThi = new JButton();
        btnTaoDeThi.setAutoscrolls(true);
        btnTaoDeThi.setBackground(new Color(-41585));
        btnTaoDeThi.setEnabled(true);
        btnTaoDeThi.setFocusPainted(false);
        btnTaoDeThi.setFocusable(false);
        Font btnTaoDeThiFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, btnTaoDeThi.getFont());
        if (btnTaoDeThiFont != null) btnTaoDeThi.setFont(btnTaoDeThiFont);
        btnTaoDeThi.setForeground(new Color(-1));
        btnTaoDeThi.setMargin(new Insets(10, 10, 10, 10));
        btnTaoDeThi.setRequestFocusEnabled(true);
        btnTaoDeThi.setRolloverEnabled(true);
        btnTaoDeThi.setSelected(false);
        btnTaoDeThi.setText("Tạo đề thi");
        btnTaoDeThi.setVerifyInputWhenFocusTarget(true);
        btnTaoDeThi.setVisible(true);
        pnChucNang2.add(btnTaoDeThi, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        pnChucNang2.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        pnTaoDeThi = new JPanel();
        pnTaoDeThi.setLayout(new BorderLayout(0, 0));
        pnCard.add(pnTaoDeThi, "Card2");
        pnChucNang1 = new JPanel();
        pnChucNang1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 20, 0, 20), -1, -1));
        pnChucNang1.setBackground(new Color(-2954497));
        pnChucNang1.setPreferredSize(new Dimension(307, 70));
        pnTaoDeThi.add(pnChucNang1, BorderLayout.NORTH);
        btnQuayLai = new JButton();
        btnQuayLai.setBackground(new Color(-16630134));
        btnQuayLai.setFocusCycleRoot(false);
        btnQuayLai.setFocusPainted(false);
        btnQuayLai.setFocusable(false);
        Font btnQuayLaiFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, btnQuayLai.getFont());
        if (btnQuayLaiFont != null) btnQuayLai.setFont(btnQuayLaiFont);
        btnQuayLai.setForeground(new Color(-394759));
        btnQuayLai.setMargin(new Insets(10, 10, 10, 10));
        btnQuayLai.setText("Quay lại");
        pnChucNang1.add(btnQuayLai, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        pnChucNang1.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        btnTaoCauHoi = new JButton();
        btnTaoCauHoi.setBackground(new Color(-299118));
        btnTaoCauHoi.setFocusPainted(false);
        btnTaoCauHoi.setFocusable(false);
        Font btnTaoCauHoiFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, btnTaoCauHoi.getFont());
        if (btnTaoCauHoiFont != null) btnTaoCauHoi.setFont(btnTaoCauHoiFont);
        btnTaoCauHoi.setForeground(new Color(-1));
        btnTaoCauHoi.setMargin(new Insets(10, 10, 10, 10));
        btnTaoCauHoi.setText("Soạn câu hỏi");
        pnChucNang1.add(btnTaoCauHoi, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 2, new Insets(20, 0, 20, 0), -1, -1));
        panel2.setBackground(new Color(-1));
        pnTaoDeThi.add(panel2, BorderLayout.CENTER);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(10, 1, new Insets(10, 20, 10, 20), 10, 10));
        panel3.setBackground(new Color(-1));
        panel2.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Arial", Font.PLAIN, 20, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Tên đề thi");
        panel3.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTenDeThi = new JTextField();
        Font txtTenDeThiFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, txtTenDeThi.getFont());
        if (txtTenDeThiFont != null) txtTenDeThi.setFont(txtTenDeThiFont);
        panel3.add(txtTenDeThi, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Arial", Font.PLAIN, 20, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Môn học");
        panel3.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbBoxMonHoc = new JComboBox();
        Font cbBoxMonHocFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, cbBoxMonHoc.getFont());
        if (cbBoxMonHocFont != null) cbBoxMonHoc.setFont(cbBoxMonHocFont);
        panel3.add(cbBoxMonHoc, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Arial", Font.PLAIN, 20, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Thời lượng kiểm tra");
        panel3.add(label4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbBoxThoiLuong = new JComboBox();
        Font cbBoxThoiLuongFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, cbBoxThoiLuong.getFont());
        if (cbBoxThoiLuongFont != null) cbBoxThoiLuong.setFont(cbBoxThoiLuongFont);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("");
        defaultComboBoxModel1.addElement("5 phút");
        defaultComboBoxModel1.addElement("10 phút");
        defaultComboBoxModel1.addElement("15 phút");
        defaultComboBoxModel1.addElement("20 phút");
        defaultComboBoxModel1.addElement("25 phút");
        defaultComboBoxModel1.addElement("30 phút");
        defaultComboBoxModel1.addElement("45 phút");
        defaultComboBoxModel1.addElement("60 phút");
        defaultComboBoxModel1.addElement("90 phút");
        defaultComboBoxModel1.addElement("120 phút");
        cbBoxThoiLuong.setModel(defaultComboBoxModel1);
        cbBoxThoiLuong.setOpaque(false);
        panel3.add(cbBoxThoiLuong, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pnThoiGian = new JPanel();
        pnThoiGian.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pnThoiGian.setBackground(new Color(-1));
        panel3.add(pnThoiGian, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Arial", Font.PLAIN, 20, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("Chọn số lần được phép làm bài kiểm tra");
        panel3.add(label5, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbBoxSoLanLamKiemTra = new JComboBox();
        Font cbBoxSoLanLamKiemTraFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, cbBoxSoLanLamKiemTra.getFont());
        if (cbBoxSoLanLamKiemTraFont != null) cbBoxSoLanLamKiemTra.setFont(cbBoxSoLanLamKiemTraFont);
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("không giới hạn");
        defaultComboBoxModel2.addElement("1");
        defaultComboBoxModel2.addElement("2");
        defaultComboBoxModel2.addElement("3");
        defaultComboBoxModel2.addElement("4");
        defaultComboBoxModel2.addElement("5");
        cbBoxSoLanLamKiemTra.setModel(defaultComboBoxModel2);
        cbBoxSoLanLamKiemTra.setOpaque(false);
        panel3.add(cbBoxSoLanLamKiemTra, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 2, new Insets(10, 20, 10, 20), -1, -1));
        panel4.setBackground(new Color(-1));
        panel2.add(panel4, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(500, -1), null, 0, false));
        pnMatKhau = new JPanel();
        pnMatKhau.setLayout(new BorderLayout(0, 0));
        pnMatKhau.setBackground(new Color(-1));
        Font pnMatKhauFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, pnMatKhau.getFont());
        if (pnMatKhauFont != null) pnMatKhau.setFont(pnMatKhauFont);
        panel4.add(pnMatKhau, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ckBSuDungMK = new JCheckBox();
        ckBSuDungMK.setBackground(new Color(-1));
        Font ckBSuDungMKFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, ckBSuDungMK.getFont());
        if (ckBSuDungMKFont != null) ckBSuDungMK.setFont(ckBSuDungMKFont);
        ckBSuDungMK.setSelected(true);
        ckBSuDungMK.setText("Sử dụng mật khẩu");
        pnMatKhau.add(ckBSuDungMK, BorderLayout.NORTH);
        pnChucNangMK = new JPanel();
        pnChucNangMK.setLayout(new GridLayoutManager(3, 1, new Insets(10, 0, 0, 0), -1, -1));
        pnChucNangMK.setBackground(new Color(-1));
        pnMatKhau.add(pnChucNangMK, BorderLayout.CENTER);
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("Arial", Font.PLAIN, 20, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setText("Mật khẩu");
        pnChucNangMK.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtNhapMatKhau = new JTextField();
        Font txtNhapMatKhauFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, txtNhapMatKhau.getFont());
        if (txtNhapMatKhauFont != null) txtNhapMatKhau.setFont(txtNhapMatKhauFont);
        pnChucNangMK.add(txtNhapMatKhau, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer4 = new Spacer();
        pnChucNangMK.add(spacer4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(10, 10));
        panel5.setBackground(new Color(-1));
        panel4.add(panel5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pnDSLop = new JPanel();
        pnDSLop.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        pnDSLop.setBackground(new Color(-1));
        Font pnDSLopFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, pnDSLop.getFont());
        if (pnDSLopFont != null) pnDSLop.setFont(pnDSLopFont);
        panel5.add(pnDSLop, BorderLayout.CENTER);
        pnDSLop.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-12156236)), "Lớp", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Arial", Font.PLAIN, 20, pnDSLop.getFont()), new Color(-16777216)));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$("Arial", Font.PLAIN, 25, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setText("Thông tin cơ bản");
        panel2.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pnSoanCauHoi = new JPanel();
        pnSoanCauHoi.setLayout(new BorderLayout(0, 0));
        pnCard.add(pnSoanCauHoi, "Card3");
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 3, new Insets(0, 20, 0, 20), -1, -1));
        panel6.setBackground(new Color(-2954497));
        panel6.setPreferredSize(new Dimension(64, 70));
        pnSoanCauHoi.add(panel6, BorderLayout.NORTH);
        btnQuayLaiCard2 = new JButton();
        btnQuayLaiCard2.setBackground(new Color(-16630134));
        btnQuayLaiCard2.setFocusCycleRoot(false);
        btnQuayLaiCard2.setFocusPainted(false);
        btnQuayLaiCard2.setFocusable(false);
        Font btnQuayLaiCard2Font = this.$$$getFont$$$("Arial", Font.PLAIN, 20, btnQuayLaiCard2.getFont());
        if (btnQuayLaiCard2Font != null) btnQuayLaiCard2.setFont(btnQuayLaiCard2Font);
        btnQuayLaiCard2.setForeground(new Color(-394759));
        btnQuayLaiCard2.setMargin(new Insets(10, 10, 10, 10));
        btnQuayLaiCard2.setText("Quay lại");
        panel6.add(btnQuayLaiCard2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnLuuBaiThi = new JButton();
        btnLuuBaiThi.setBackground(new Color(-5781161));
        btnLuuBaiThi.setFocusCycleRoot(false);
        btnLuuBaiThi.setFocusPainted(false);
        btnLuuBaiThi.setFocusable(false);
        Font btnLuuBaiThiFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, btnLuuBaiThi.getFont());
        if (btnLuuBaiThiFont != null) btnLuuBaiThi.setFont(btnLuuBaiThiFont);
        btnLuuBaiThi.setMargin(new Insets(10, 10, 10, 10));
        btnLuuBaiThi.setText("Lưu");
        panel6.add(btnLuuBaiThi, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel6.add(spacer5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setBackground(new Color(-1));
        pnSoanCauHoi.add(panel7, BorderLayout.CENTER);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(3, 1, new Insets(20, 20, 20, 10), -1, -1));
        panel8.setBackground(new Color(-1));
        panel7.add(panel8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$("Arial", Font.PLAIN, 18, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setText("Danh mục câu hỏi");
        panel8.add(label8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new BorderLayout(0, 0));
        panel8.add(panel9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(20, -1), null, 0, false));
        pnDSSoCauHoi = new JPanel();
        pnDSSoCauHoi.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        pnDSSoCauHoi.setBackground(new Color(-1181959));
        panel9.add(pnDSSoCauHoi, BorderLayout.CENTER);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
        panel10.setBackground(new Color(-1));
        panel9.add(panel10, BorderLayout.NORTH);
        btnThemCauHoi = new JButton();
        btnThemCauHoi.setBackground(new Color(-16611119));
        btnThemCauHoi.setEnabled(true);
        btnThemCauHoi.setFocusPainted(false);
        btnThemCauHoi.setFocusable(false);
        Font btnThemCauHoiFont = this.$$$getFont$$$("Arial", Font.PLAIN, 18, btnThemCauHoi.getFont());
        if (btnThemCauHoiFont != null) btnThemCauHoi.setFont(btnThemCauHoiFont);
        btnThemCauHoi.setForeground(new Color(-1));
        btnThemCauHoi.setHorizontalTextPosition(0);
        btnThemCauHoi.setMargin(new Insets(5, 5, 5, 5));
        btnThemCauHoi.setText("+ Thêm câu hỏi");
        panel10.add(btnThemCauHoi);
        final Spacer spacer6 = new Spacer();
        panel10.add(spacer6);
        btnThemCHTuNganHangCH = new JButton();
        btnThemCHTuNganHangCH.setBackground(new Color(-16611119));
        btnThemCHTuNganHangCH.setFocusPainted(false);
        btnThemCHTuNganHangCH.setFocusable(false);
        Font btnThemCHTuNganHangCHFont = this.$$$getFont$$$("Arial", Font.PLAIN, 18, btnThemCHTuNganHangCH.getFont());
        if (btnThemCHTuNganHangCHFont != null) btnThemCHTuNganHangCH.setFont(btnThemCHTuNganHangCHFont);
        btnThemCHTuNganHangCH.setForeground(new Color(-1));
        btnThemCHTuNganHangCH.setMargin(new Insets(5, 5, 5, 5));
        btnThemCHTuNganHangCH.setText("Thêm câu hỏi từ ngân hàng đề thi");
        panel10.add(btnThemCHTuNganHangCH);
        final Spacer spacer7 = new Spacer();
        panel10.add(spacer7);
        btnXoaCauHoi = new JButton();
        btnXoaCauHoi.setBackground(new Color(-727322));
        Font btnXoaCauHoiFont = this.$$$getFont$$$("Arial", Font.PLAIN, 18, btnXoaCauHoi.getFont());
        if (btnXoaCauHoiFont != null) btnXoaCauHoi.setFont(btnXoaCauHoiFont);
        btnXoaCauHoi.setForeground(new Color(-4124129));
        btnXoaCauHoi.setText("Xóa câu hỏi");
        panel10.add(btnXoaCauHoi);
        pnChiTietNoiDungCauHoi = new JPanel();
        pnChiTietNoiDungCauHoi.setLayout(new BorderLayout(0, 0));
        pnChiTietNoiDungCauHoi.setBackground(new Color(-1));
        panel8.add(pnChiTietNoiDungCauHoi, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pnChiTietNoiDungCauHoi.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(2, 3, new Insets(20, 10, 20, 20), -1, -1));
        panel11.setBackground(new Color(-1));
        panel7.add(panel11, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(500, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        Font label9Font = this.$$$getFont$$$("Arial", Font.PLAIN, 18, label9.getFont());
        if (label9Font != null) label9.setFont(label9Font);
        label9.setText("Thêm câu hỏi mới");
        panel11.add(label9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pnNoiDungCauHoiVaDapAn = new JPanel();
        pnNoiDungCauHoiVaDapAn.setLayout(new BorderLayout(0, 0));
        pnNoiDungCauHoiVaDapAn.setBackground(new Color(-1));
        panel11.add(pnNoiDungCauHoiVaDapAn, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnHuongDan = new JButton();
        btnHuongDan.setBackground(new Color(-6241025));
        btnHuongDan.setFocusPainted(false);
        btnHuongDan.setFocusable(false);
        Font btnHuongDanFont = this.$$$getFont$$$("Arial", Font.PLAIN, 18, btnHuongDan.getFont());
        if (btnHuongDanFont != null) btnHuongDan.setFont(btnHuongDanFont);
        btnHuongDan.setMargin(new Insets(5, 5, 5, 5));
        btnHuongDan.setText("Hướng dẫn");
        panel11.add(btnHuongDan, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        panel11.add(spacer8, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        pnThongTinBaiThi = new JPanel();
        pnThongTinBaiThi.setLayout(new BorderLayout(0, 0));
        pnCard.add(pnThongTinBaiThi, "Card4");
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridLayoutManager(1, 2, new Insets(0, 20, 0, 20), -1, -1));
        panel12.setBackground(new Color(-2954497));
        panel12.setPreferredSize(new Dimension(64, 70));
        pnThongTinBaiThi.add(panel12, BorderLayout.NORTH);
        btnQuayLai3 = new JButton();
        btnQuayLai3.setBackground(new Color(-16630134));
        btnQuayLai3.setFocusPainted(false);
        btnQuayLai3.setFocusable(false);
        btnQuayLai3.setForeground(new Color(-1));
        btnQuayLai3.setMargin(new Insets(10, 10, 10, 10));
        btnQuayLai3.setText("Quay lại");
        panel12.add(btnQuayLai3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        panel12.add(spacer9, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        pnTTCT = new JPanel();
        pnTTCT.setLayout(new BorderLayout(0, 0));
        pnThongTinBaiThi.add(pnTTCT, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

    public void taoDSBaiThi() throws MalformedURLException, NotBoundException, RemoteException {
        try {
            pnHienThiCacBaiThi = new JPanel();
            pnHienThiCacBaiThi.setBackground(Color.WHITE);
            // Dùng WrapLayout để tự động xuống dòng và hỗ trợ cuộn
            pnHienThiCacBaiThi.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));

            // Thêm các phần tử vào pnHienThiCacBaiThi
            List<BaiThi> dsBaiThi = baiThiService.timDSBaiTHiTheoMaGiaoVien(giaoVienDangNhap.getMaGiaoVien());

            if (dsBaiThi.isEmpty()) {
                // Hiển thị thông báo nếu không có bài thi
                JLabel lblThongBao = new JLabel("Không có bài thi nào. Hãy tạo bài thi mới!");
                lblThongBao.setFont(new Font("Arial", Font.BOLD, 18));
                lblThongBao.setForeground(new Color(100, 100, 100));
                pnHienThiCacBaiThi.add(lblThongBao);
            } else {
                // Hiển thị danh sách bài thi
                for (BaiThi baiThi : dsBaiThi) {
                    System.out.println("Tên bài thi: " + baiThi.getTenBaiThi());
                    System.out.println("Môn học: " + (baiThi.getMonHoc() != null ? baiThi.getMonHoc().getTenMon() : "Không có"));
                    System.out.println("Thời lượng: " + baiThi.getThoiLuong() + " phút");
                    System.out.println("Số câu hỏi: " + baiThi.getDanhSachCauHoi().size());
                    System.out.println("Số lần được phép làm: " + baiThi.getSoLanDuocPhepLamBai());

//                    for (Lop lop : baiThi.getDanhSachLop()) {
//                        System.out.println("Lớp: " + lop.getTenLop()); // In tên lớp (giả sử Lop có phương thức getTenLop)
//                    }
                    pnHienThiCacBaiThi.add(thanhPhanBaiThi(baiThi));
                }
            }

            // Đưa pnHienThiCacBaiThi vào JScrollPane để cuộn
            JScrollPane scrollPane = new JScrollPane(pnHienThiCacBaiThi,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Bỏ viền

            // Thêm JScrollPane vào panel chứa nội dung
            pnNoiDung.removeAll();
            pnNoiDung.add(scrollPane, BorderLayout.CENTER);
            pnNoiDung.revalidate();
            pnNoiDung.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải danh sách bài thi: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    public JPanel thanhPhanBaiThi(BaiThi baiThi) {
        // Panel chính
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(350, 350)); // Giảm kích thước để gọn hơn
        panel.setMaximumSize(new Dimension(400, 400));
        panel.setMinimumSize(new Dimension(350, 300));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(10, 15, 10, 15) // Giảm padding
        ));

        // Panel chứa thông tin
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);

        // Font chữ hiện đại
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);
        Font boldFont = new Font("Segoe UI", Font.BOLD, 20);

        // Các nhãn thông tin
        JLabel lblTenBaiThi = new JLabel("<html>Tên bài thi: " + baiThi.getTenBaiThi() + "</html>");
        lblTenBaiThi.setFont(boldFont);
        lblTenBaiThi.setForeground(new Color(33, 33, 33));

        JLabel lblMonHoc = new JLabel("<html><b>Môn học: </b> " + baiThi.getMonHoc().getTenMon() + "</html>");
        lblMonHoc.setFont(labelFont);
        lblMonHoc.setForeground(new Color(66, 66, 66));

        JLabel lblBatDau = new JLabel("<html><b>Bắt đầu:</b> " + chuyenDinhDangNgayGio(baiThi.getThoiGianBatDau()) + "</html>");
        lblBatDau.setFont(labelFont);
        lblBatDau.setForeground(new Color(66, 66, 66));

        JLabel lblKetThuc = new JLabel("<html><b>Kết thúc:</b> " + chuyenDinhDangNgayGio(baiThi.getThoiGianKetThuc()) + "</html>");
        lblKetThuc.setFont(labelFont);
        lblKetThuc.setForeground(new Color(66, 66, 66));

        JLabel lblThoiGian = new JLabel("<html><b>Thời gian:</b> " + baiThi.getThoiLuong() + "</html>");
        lblThoiGian.setFont(labelFont);
        lblThoiGian.setForeground(new Color(66, 66, 66));

        JLabel lblSoCauHoi = new JLabel("<html><b>Số câu hỏi:</b> " + baiThi.getDanhSachCauHoi().size() + "</html>");
        lblSoCauHoi.setFont(labelFont);
        lblSoCauHoi.setForeground(new Color(66, 66, 66));

        JLabel lblGiaoVien = new JLabel("<html><b>Giáo viên:</b> " + baiThi.getGiaoVien().getHoTen() + "</html>");
        lblGiaoVien.setFont(labelFont);
        lblGiaoVien.setForeground(new Color(66, 66, 66));

        JLabel lblSoLanLamBai = new JLabel("<html><b>Số lần cho phép làm bài:</b> " + baiThi.getSoLanDuocPhepLamBai() + "</html>");
        lblSoLanLamBai.setFont(labelFont);
        lblSoLanLamBai.setForeground(new Color(66, 66, 66));
        // Thêm nhãn vào panel thông tin
        info.add(lblTenBaiThi);
        info.add(lblMonHoc);
        info.add(lblBatDau);
        info.add(lblKetThuc);
        info.add(lblThoiGian);
        info.add(lblSoCauHoi);
        info.add(lblGiaoVien);
        info.add(lblSoLanLamBai);

        // Nút "Xem trước" với hiệu ứng hover
        JButton btnXemTruocBaiThi = new JButton("Xem trước");
        btnXemTruocBaiThi.setPreferredSize(new Dimension(120, 35));
        btnXemTruocBaiThi.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnXemTruocBaiThi.setFocusPainted(false);
        btnXemTruocBaiThi.setBackground(new Color(33, 150, 243)); // Màu xanh hiện đại
        btnXemTruocBaiThi.setForeground(Color.WHITE);
        btnXemTruocBaiThi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnXemTruocBaiThi.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Hiệu ứng hover cho nút
        btnXemTruocBaiThi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btnXemTruocBaiThi.setBackground(new Color(25, 118, 210)); // Màu đậm hơn khi hover
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                btnXemTruocBaiThi.setBackground(new Color(33, 150, 243)); // Trở lại màu gốc
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(pnCard, "Card4");
                pnTTCT.removeAll();

                if (baiThi == null) {
                    JOptionPane.showMessageDialog(GiaoDienDanhSachBaiThi.this,
                        "Không thể hiển thị thông tin chi tiết bài thi vì bài thi không tồn tại.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    if (baiThiService != null) {
                        System.out.println("Kết nối thành công đến BaiThiService");
                    }

                    BaiThi bt = baiThiService.layThongTinChiTietBaiThi(baiThi.getMaBaiThi());

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(GiaoDienDanhSachBaiThi.this,
                            "Lỗi kết nối đến server: " + ex.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    System.out.println("Mã bài thi: " + baiThi.getMaBaiThi());
                    System.out.println("Tên bài thi: " + baiThi.getTenBaiThi());
                    System.out.println("Môn học: " + (baiThi.getMonHoc() != null ? baiThi.getMonHoc().getTenMon() : "Không có"));
                    System.out.println("Thời lượng: " + baiThi.getThoiLuong() + " phút");
                    System.out.println("Số câu hỏi: " + baiThi.getDanhSachCauHoi().size());
                    System.out.println("Số lần được phép làm: " + baiThi.getSoLanDuocPhepLamBai());
                    List<Lop> danhSachLop = baiThiService.timLopTheoBaiThi(baiThi.getMaBaiThi());
                    System.out.println("Số lớp liên quan tới bài thi " + baiThi.getMaBaiThi() + ": " + danhSachLop.size());
                    for (Lop lop : danhSachLop) {
                        System.out.println("Lớp: " + lop.getTenLop()); // In tên lớp (giả sử Lop có phương thức getTenLop)
                    }
                    baiThi.setDanhSachLop(danhSachLop);
                    System.out.println("Số lớp: " + baiThi.getDanhSachLop().size());

                    // Kiểm tra nếu bt là null trước khi sử dụng
                    if (baiThi == null) {
                        JOptionPane.showMessageDialog(GiaoDienDanhSachBaiThi.this,
                            "Không thể lấy thông tin chi tiết bài thi từ server11111.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Hiển thị thông tin chi tiết bài thi
                    pnTTCT.add(new GiaoDienThongTinChiTietBaiThi(baiThi));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(GiaoDienDanhSachBaiThi.this,
                        "Lỗi khi lấy thông tin chi tiết bài thi: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

// Nút "Chỉnh sửa bài thi"
        JButton btnChinhSua = new JButton("Chỉnh sửa");
        btnChinhSua.setPreferredSize(new Dimension(120, 35));
        btnChinhSua.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnChinhSua.setFocusPainted(false);
        btnChinhSua.setBackground(new Color(76, 175, 80)); // Màu xanh lá
        btnChinhSua.setForeground(Color.WHITE);
        btnChinhSua.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnChinhSua.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

// Hiệu ứng hover cho nút chỉnh sửa
        btnChinhSua.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btnChinhSua.setBackground(new Color(56, 142, 60)); // Xanh đậm hơn khi hover
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                btnChinhSua.setBackground(new Color(76, 175, 80)); // Màu gốc
            }

            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(pnCard, "Card2");
                txtTenDeThi.setText(baiThi.getTenBaiThi());
                cbBoxThoiLuong.setSelectedItem(baiThi.getThoiLuong() + " phút");
                taoJComboBoxMonHoc();
                cbBoxMonHoc.setSelectedItem(baiThi.getMonHoc().getTenMon());
                pnThoiGian.removeAll();
                panelThoiGianThi = new PanelThoiGianThi();
                pnThoiGian.add(panelThoiGianThi.getPanel());
                panelThoiGianThi.setStartDateTime(baiThi.getThoiGianBatDau());
                panelThoiGianThi.setEndDateTime(baiThi.getThoiGianKetThuc());
                if (baiThi.getMatKhau() != null) {
                    ckBSuDungMK.setSelected(true);
                    txtNhapMatKhau.setText(baiThi.getMatKhau());
                } else {
                    ckBSuDungMK.setSelected(false);
                    txtNhapMatKhau.setText("");
                }
                List<Lop> danhSachLop = baiThiService.timLopTheoBaiThi(baiThi.getMaBaiThi());
                int sttLop = 1;
                for (Lop lop : danhSachLop) {
                    System.out.println("Lớp " + sttLop++ + ": " + lop.getTenLop()); // Giả sử Lop có phương thức getTenLop()
                }
                baiThi.setDanhSachLop(danhSachLop);
                List<Lop> lopDaChon = baiThi.getDanhSachLop();
                taoJCheckBoxLop();
                for (Lop lop : lopDaChon) {
                    for (Component comp : pnDSLop.getComponents()) {
                        if (comp instanceof JCheckBox cb) {
                            if (cb.getText().equals(lop.getTenLop())) {
                                cb.setSelected(true);
                                break;
                            }
                        }
                    }
                }
                pnDSSoCauHoi.removeAll();
                pnDSSoCauHoi.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));
                List<CauHoi> cauHoiList = new ArrayList<>();
                try {
                    cauHoiList = baiThi.getDanhSachCauHoi();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Lỗi khi tải danh sách câu hỏi: " + exception.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (cauHoiList != null && !cauHoiList.isEmpty()) {
                    for (CauHoi cauHoi : cauHoiList) {
                        NutCauHoi nutCauHoi = new NutCauHoi(cauHoiList.indexOf(cauHoi) + 1, cauHoi);
                        System.out.println("Câu: " + cauHoi.getNoiDung());
                        System.out.println("Đáp án: " + cauHoi.getDapAnDung());
                        System.out.println("Danh sách đáp án: " + cauHoi.getDanhSachDapAn());
                        pnDSSoCauHoi.add(nutCauHoi);
                    }
                    pnDSSoCauHoi.revalidate();
                    pnDSSoCauHoi.repaint();
                }

            }
        });
        JLabel nutXoaBaiThi = new JLabel();
        nutXoaBaiThi.setIcon(FontIcon.of(MaterialDesign.MDI_DELETE, 24, new Color(240, 35, 60)));
        nutXoaBaiThi.setPreferredSize(new Dimension(50, 35));
        nutXoaBaiThi.setBackground(Color.WHITE);
        nutXoaBaiThi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nutXoaBaiThi.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        nutXoaBaiThi.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent evt) {
                nutXoaBaiThi.setIcon(FontIcon.of(MaterialDesign.MDI_DELETE, 24, new Color(217, 4, 41)));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Hiệu ứng khi nhấn nút (màu đậm hơn)
                nutXoaBaiThi.setIcon(FontIcon.of(MaterialDesign.MDI_DELETE, 24, new Color(217, 4, 41)));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                nutXoaBaiThi.setIcon(FontIcon.of(MaterialDesign.MDI_DELETE, 24, new Color(240, 35, 60)));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Hiển thị hộp thoại xác nhận xóa bài thi
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Bạn chắc chắn muốn xóa bài thi này?",
                        "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        // Xóa bài thi
                        boolean kq = baiThiService.delete(baiThi.getMaBaiThi());
                        System.out.println("Bài thi đã bị xóa!" + baiThi.getTenBaiThi());

                        if (kq) {
                            List<BaiThi> dsBaiThi = baiThiService.timDSBaiTHiTheoMaGiaoVien(giaoVienDangNhap.getMaGiaoVien());
                            // Xóa tất cả các thành phần hiện tại trong panel
                            pnHienThiCacBaiThi.removeAll();
                            if (dsBaiThi.isEmpty()) {
                                // Hiển thị thông báo nếu không có bài thi
                                JLabel lblThongBao = new JLabel("Không có bài thi nào. Hãy tạo bài thi mới!");
                                lblThongBao.setFont(new Font("Arial", Font.BOLD, 18));
                                lblThongBao.setForeground(new Color(100, 100, 100));
                                pnHienThiCacBaiThi.add(lblThongBao);
                            } else {
                                // Hiển thị danh sách bài thi
                                for (BaiThi baiThi : dsBaiThi) {
                                    pnHienThiCacBaiThi.add(thanhPhanBaiThi(baiThi));
                                }
                            }
                            // Cập nhật giao diện sau khi thay đổi
                            pnHienThiCacBaiThi.revalidate();
                            pnHienThiCacBaiThi.repaint();
                        } else {
                            JOptionPane.showMessageDialog(null, "Xóa bài thi thất bại!");
                        }
                    } catch (RemoteException ex) {
                        // Xử lý lỗi RemoteException
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa bài thi. Vui lòng thử lại.");
                    }
                }

            }
        });

        // Panel chứa nút
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.setOpaque(false);
        btnPanel.add(btnXemTruocBaiThi);
        btnPanel.add(Box.createHorizontalGlue());
        btnPanel.add(btnChinhSua); // Thêm trước hoặc sau tùy bạn muốn vị trí
        btnPanel.add(Box.createHorizontalGlue());
        btnPanel.add(nutXoaBaiThi);
        // Thêm vào panel chính
        panel.add(info, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void taoJComboBoxMonHoc() throws MalformedURLException, NotBoundException, RemoteException {
        MonHocService monHocService = (MonHocService) Naming.lookup("rmi://localhost:8081/monHocService");
        monHocList = monHocService.getAll();
        cbBoxMonHoc.removeAllItems();
        cbBoxMonHoc.addItem("");
        for (MonHoc monHoc : monHocList) {
            cbBoxMonHoc.addItem(monHoc.getTenMon());
        }
    }

    public void taoJCheckBoxLop() throws MalformedURLException, NotBoundException, RemoteException {
        LopService lopService = (LopService) Naming.lookup("rmi://localhost:8081/lopService");
        List<Lop> dsLop = lopService.getAll();
        pnDSLop.removeAll();
        pnDSLop.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));

        Font checkBoxFont = new Font("Arial", Font.PLAIN, 20);
        for (Lop lop : dsLop) {
            JCheckBox checkBox = new JCheckBox(lop.getTenLop());
            checkBox.setFont(checkBoxFont);
            checkBox.setOpaque(false);
            checkBox.setFocusPainted(false);
            checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            // Thêm ActionListener để theo dõi sự thay đổi của JCheckBox
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (checkBox.isSelected()) {
                        // Nếu chọn lớp, thêm vào danh sách đã chọn
                        lopDaChon.add(lop);
                    } else {
                        // Nếu bỏ chọn lớp, loại bỏ khỏi danh sách
                        lopDaChon.remove(lop);
                    }
                }
            });
            pnDSLop.add(checkBox);
            pnDSLop.add(Box.createVerticalStrut(5)); // Khoảng cách giữa các checkbox
        }

        pnDSLop.revalidate();
        pnDSLop.repaint();
    }


    public static void showHuongDanDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Cấu trúc soạn thảo câu hỏi bằng văn bản", true);
        dialog.setSize(600, 720);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());

        Font fontContent = new Font("Arial", Font.PLAIN, 15);
        Font fontTitle = new Font("Arial", Font.BOLD, 20);

        // ======= Tiêu đề =======
        JLabel title = new JLabel("Cấu trúc soạn thảo câu hỏi bằng văn bản", SwingConstants.CENTER);
        title.setFont(fontTitle);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // ======= Quy tắc soạn câu hỏi (dùng JLabel) =======
        String huongDanHTML = "<html><div style='font-size:14px; padding:0 20px;'>"
                + "<b>Quy tắc soạn câu hỏi</b><br/>"
                + "- Để tạo phần thi mới, viết dấu nháy [ ] ở đầu dòng<br/>"
                + "- Mỗi câu hỏi cách nhau 1 dòng hoặc nhiều dòng<br/>"
                + "- Đáp án đúng là đáp án có dấu * đằng trước<br/>"
                + "- Nếu muốn xuống dòng trong câu hỏi hoặc đáp án thì bạn cần bổ sung thẻ xuống dòng &lt;br /&gt; tại điểm muốn xuống dòng<br/>"
                + "- Nếu câu hỏi sai cấu trúc trên, hệ thống sẽ báo lỗi và câu hỏi không được hiển thị"
                + "</div></html>";
        JLabel rulesLabel = new JLabel(huongDanHTML);
        rulesLabel.setFont(fontContent);

        // ======= TextArea chứa ví dụ câu hỏi =======
        JTextArea textArea = new JTextArea(
                "When we went back to the bookstore, the bookseller _ the book we wanted.\n" +
                        "A. sold\n" +
                        "*B. had sold\n" +
                        "C. sells\n" +
                        "D. has sold\n\n" +
                        "By the end of last summer, the farmers _ all the crop.\n" +
                        "A. harvested\n" +
                        "B. are harvested\n" +
                        "C. harvest\n" +
                        "*D. had harvested"
        );
        textArea.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        scrollPane.setPreferredSize(new Dimension(540, 350));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // ======= Bottom panel (Copy button + ghi chú) =======
        JButton btnCopy = new JButton("Copy");
        btnCopy.setFont(new Font("Arial", Font.ITALIC, 20));
        btnCopy.addActionListener(ae -> {
            String copiedText = textArea.getText();
            textArea.selectAll();
            textArea.copy();
            JOptionPane.showMessageDialog(dialog, "Đã sao chép nội dung:\n\n" + copiedText);
        });

        JLabel lblNote = new JLabel("Sao chép văn bản trên và dán vào phần soạn thảo để xem trước câu hỏi nhé!");
        lblNote.setFont(new Font("Arial", Font.ITALIC, 15));
        lblNote.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        bottomPanel.add(btnCopy);
        bottomPanel.add(lblNote);

        // ======= Panel chứa tiêu đề + hướng dẫn =======
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(title);
        topPanel.add(rulesLabel);

        // ======= Thêm vào dialog =======
        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        // Tạo một JFrame để chứa giao diện

        JFrame frame = new JFrame("Danh Sách Bài Thi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        GiaoVienService giaoVienService = (GiaoVienService) Naming.lookup("rmi://localhost:8081/giaoVienService");
        GiaoVien giaoVien = giaoVienService.finByID(8);

        frame.setContentPane(new GiaoDienDanhSachBaiThi(giaoVien).$$$getRootComponent$$$());
        frame.setLocationRelativeTo(null); // căn giữa
        frame.setVisible(true);

    }

    private void createUIComponents() {
        btnTaoDeThi.setBackground(new Color(205, 180, 219));
    }
}
