package gui;

import entities.CauHoi;
import entities.ChuDe;
import entities.MonHoc;
import service.CauHoiService;
import service.ChuDeService;
import service.MonHocService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GiaoDienThemCauHoi extends JPanel {

    private JTextArea txtNoiDungCauHoi;
    private JTextField txtDapAnDung, txtDapAnSai1, txtDapAnSai2, txtDapAnSai3;
    private JButton btnLuu, btnHuy;
    private JComboBox<String> cbMonHoc, cbChuDe;
    private ChuDeService chuDeService = (ChuDeService) Naming.lookup("rmi://localhost:9090/chuDeService");
    private CauHoiService cauHoiService = (CauHoiService) Naming.lookup("rmi://localhost:9090/cauHoiService");
    private MonHocService monHocService = (MonHocService) Naming.lookup("rmi://localhost:9090/monHocService");
    private JPanel mainPanel;
    private CauHoi cauHoi;
    public GiaoDienThemCauHoi(JPanel mainPanel, CauHoi cauHoi) throws MalformedURLException, NotBoundException, RemoteException {
        this.mainPanel = mainPanel;
        this.cauHoi = cauHoi;

        if (this.cauHoi == null) {
            this.cauHoi = new CauHoi(); // Tạo mới nếu không truyền vào
        }

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Panel để chọn môn học và chủ đề từ JComboBox---
        JPanel panelLuaChon = new JPanel(new GridLayout(1, 2, 10, 10));
        List<MonHoc> dsMonHoc = monHocService.getAll();
        String[] monHoc = new String[dsMonHoc.size()];
        for (int i = 0; i < dsMonHoc.size(); i++) {
            MonHoc mh = dsMonHoc.get(i);
            monHoc[i] = mh.getTenMon();
        }
        cbMonHoc = new JComboBox<>(monHoc);
        cbMonHoc.setPreferredSize(new Dimension(350, 30)); // tăng kích thước JComboBox
        cbChuDe = new JComboBox<>();
        cbChuDe.setPreferredSize(new Dimension(350, 30)); // tăng kích thước JComboBox
        loadChuDe();
        JLabel lblMonHoc = new JLabel("Chọn môn học:");
        JLabel lblChuDe = new JLabel("Chọn chủ đề:");

        JPanel panelMonHoc = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMonHoc.add(lblMonHoc);
        panelMonHoc.add(cbMonHoc);

        JPanel panelChuDe = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelChuDe.add(lblChuDe);
        panelChuDe.add(cbChuDe);
        panelChuDe.setBackground(new Color(210, 234, 255));
        panelMonHoc.setBackground(new Color(210, 234, 255));

        panelLuaChon.add(panelMonHoc);
        panelLuaChon.add(panelChuDe);

        // --- Panel nội dung câu hỏi ---
        JPanel panelNoiDung = new JPanel(new BorderLayout());
        panelNoiDung.setBorder(BorderFactory.createTitledBorder("Nội dung câu hỏi"));
        TitledBorder border = (TitledBorder) panelNoiDung.getBorder();
        border.setTitleFont(new Font("Arial", Font.BOLD, 16));
        txtNoiDungCauHoi = new JTextArea(15, 50); // tăng số dòng và độ rộng
        JScrollPane scroll = new JScrollPane(txtNoiDungCauHoi);
        panelNoiDung.add(scroll, BorderLayout.CENTER);
        txtNoiDungCauHoi.setLineWrap(true); // tự động xuống dòng

        // --- Panel câu trả lời ---
        JPanel panelTraLoi = new JPanel(new GridBagLayout());
        panelTraLoi.setBorder(BorderFactory.createTitledBorder("Các câu trả lời"));
        TitledBorder borderTraLoi = (TitledBorder) panelTraLoi.getBorder();
        borderTraLoi.setTitleFont(new Font("Arial", Font.BOLD, 16));
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

        if(cauHoi != null) {
            txtNoiDungCauHoi.setText(cauHoi.getNoiDung());
            txtDapAnDung.setText(cauHoi.getDapAnDung());
            //Lấy danh sách câu trả lời sai
            List<String> dsDapAnSai = new ArrayList<>();
            for (String dapAn : cauHoi.getDanhSachDapAn()){
                if (!cauHoi.getDapAnDung().equals(dapAn)) {
                    dsDapAnSai.add(dapAn);
                }
            }
            txtDapAnSai1.setText(dsDapAnSai.get(0));
            txtDapAnSai2.setText(dsDapAnSai.get(1));
            txtDapAnSai3.setText(dsDapAnSai.get(2));
//            if(cauHoi.getChuDe() != null) {
                cbMonHoc.setSelectedItem(cauHoi.getChuDe().getMonHoc().getTenMon());
                loadChuDe();
                cbChuDe.setSelectedItem(cauHoi.getChuDe().getTenChuDe());
//            } else {
//                cbMonHoc.setSelectedItem(null);
//                cbChuDe.setSelectedItem(null);
//            }
        }

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
        btnLuu.setBackground(new Color(1, 218, 121));
        btnLuu.setForeground(Color.WHITE);

        btnHuy = new JButton("Hủy");
        btnHuy.setBackground(Color.RED);
        btnHuy.setForeground(Color.WHITE);

        panelButtons.add(btnLuu);
        panelButtons.add(btnHuy);

        // --- Thêm các panel vào panel chính ---
        JPanel panelMain = new JPanel(new BorderLayout());
        panelMain.add(panelNoiDung, BorderLayout.NORTH);
        panelMain.add(panelTraLoi, BorderLayout.CENTER);
        add(panelLuaChon, BorderLayout.NORTH);
        add(panelMain, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        // Thêm màu nền cho các panel
        panelLuaChon.setBackground(new Color(210, 234, 255));
        panelNoiDung.setBackground(Color.WHITE);
        panelTraLoi.setBackground(Color.WHITE);
        panelMain.setBackground(new Color(210, 234, 255));
        panelButtons.setBackground(new Color(210, 234, 255));
        setBackground(new Color(210, 234, 255));

        setPreferredSize(new Dimension(1200, 750)); // tăng kích thước tổng thể

        // --- Thêm ActionListener cho các nút ---
        btnLuu.addActionListener(e -> {
            try {
                actionPerformed(e);
                chuyenGiaoDien();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        btnHuy.addActionListener(e -> {
            chuyenGiaoDien();
        });
        cbMonHoc.addActionListener(e -> {
            try {
                actionPerformed(e);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void actionPerformed(ActionEvent e) throws RemoteException {
        if (e.getSource() == btnLuu) {
            String noiDung = txtNoiDungCauHoi.getText();
            String dapAnDung = txtDapAnDung.getText();
            String dapAnSai1 = txtDapAnSai1.getText();
            String dapAnSai2 = txtDapAnSai2.getText();
            String dapAnSai3 = txtDapAnSai3.getText();

            // Kiểm tra dữ liệu nhập vào
            if (noiDung.isEmpty() || dapAnDung.isEmpty() || dapAnSai1.isEmpty() || dapAnSai2.isEmpty() || dapAnSai3.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                String selectedChuDe = cbChuDe.getSelectedItem().toString();
                String selectedMonHoc = cbMonHoc.getSelectedItem().toString();
                ChuDe chuDe = chuDeService.findByTenMonHocAndTenChuDe(selectedMonHoc, selectedChuDe);

                List<String> dsDapAn = new ArrayList<>();
                dsDapAn.add(dapAnDung);
                dsDapAn.add(dapAnSai1);
                dsDapAn.add(dapAnSai2);
                dsDapAn.add(dapAnSai3);

                cauHoi.setChuDe(chuDe);
                cauHoi.setNoiDung(noiDung);
                cauHoi.setDapAnDung(dapAnDung);
                cauHoi.setDanhSachDapAn(dsDapAn);

                if(cauHoi.getMaCauHoi() >= 1){
                    cauHoiService.update(cauHoi); // Cập nhật câu hỏi nếu đã tồn tại
                }else {
                    cauHoiService.save(cauHoi);
                }
                JOptionPane.showMessageDialog(this, "Câu hỏi đã được lưu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        //Sự kiện cho JComboBox môn học
        else if (e.getSource() == cbMonHoc) {
            //Lấy danh sách chủ đề theo môn học
            loadChuDe();
        }
    }

    private void loadChuDe() throws RemoteException {
        String tenMonHoc = (String) cbMonHoc.getSelectedItem();
        List<ChuDe> dsChuDe = chuDeService.findByTenMonHoc(tenMonHoc);
        if (dsChuDe != null) {
            //Cập nhật danh sách chủ đề vào JComboBox
            String[] chuDe = new String[dsChuDe.size()];
            for (int i = 0; i < dsChuDe.size(); i++) {
                ChuDe cd = dsChuDe.get(i);
                chuDe[i] = cd.getTenChuDe();
            }
            cbChuDe.setModel(new DefaultComboBoxModel<>(chuDe));
            cbChuDe.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy chủ đề cho môn học này!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chuyenGiaoDien(){
        mainPanel.removeAll();
        try {
            mainPanel.add(new GiaoDienNganHangCauHoi(mainPanel)); // Chuyển sang giao diện ThemCauHoi
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        } catch (NotBoundException ex) {
            throw new RuntimeException(ex);
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // --- Main để chạy thử ---
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Nhập câu hỏi");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            try {
//                frame.add(new GiaoDienThemCauHoi(new JPanel()));
//            } catch (MalformedURLException | NotBoundException | RemoteException e) {
//                e.printStackTrace();
//            }
//            frame.pack();
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });
//    }
}
