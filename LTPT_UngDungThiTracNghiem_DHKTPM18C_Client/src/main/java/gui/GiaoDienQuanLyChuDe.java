package gui;
import entities.ChuDe;
import entities.MonHoc;
import gui.custom.ButtonEditor;
import gui.custom.ButtonRenderer;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;
import service.ChuDeService;
import service.MonHocService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class GiaoDienQuanLyChuDe extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private int maMon;
    private MonHocService monHocService = (MonHocService) Naming.lookup("rmi://localhost:8081/monHocService");
    private ChuDeService chuDeService = (ChuDeService) Naming.lookup("rmi://localhost:8081/chuDeService");
    private MonHoc monHoc;

    public GiaoDienQuanLyChuDe(int maMon) throws RemoteException, MalformedURLException, NotBoundException {
        this.maMon = maMon;
        //Lấy môn học từ database (bổ sung sau)

        setLayout(new BorderLayout());

        //Tạo panel cho tiêu đề
        JPanel panelTitle = new JPanel();
        panelTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        monHoc = monHocService.finByID(maMon);
        JLabel lblTitle = new JLabel("Môn học: " + monHoc.getTenMon());
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        //màu nền
        panelTitle.setBackground(new Color(51, 184, 231));
        panelTitle.add(lblTitle);
        add(panelTitle, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Mã chủ đề", "Tên chủ đề", "Sửa", "Xoá"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 2; // chỉ cột "Sửa" và "Xoá" được click
            }
        };

        table = new JTable(model);
        table.setRowHeight(36);

        FontIcon iconEdit = FontIcon.of(BootstrapIcons.PENCIL, 18, Color.BLUE);
        FontIcon iconDelete = FontIcon.of(BootstrapIcons.TRASH, 18, Color.RED);

        // Cài renderer & editor cho từng nút
        table.getColumn("Sửa").setCellRenderer(new ButtonRenderer(iconEdit));
        table.getColumn("Sửa").setCellEditor(new ButtonEditor(iconEdit, row -> {
            try {
                suaChuDe(row);
            } catch (RemoteException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi sửa chủ đề!");
            }
        }));
        table.getColumn("Xoá").setCellRenderer(new ButtonRenderer(iconDelete));
        table.getColumn("Xoá").setCellEditor(new ButtonEditor(iconDelete, row -> {
            try {
                xoaChuDe(row);
            } catch (RemoteException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi xoá chủ đề!");
            }
        }));
        //Hàng tiêu đề màu xanh
        table.getTableHeader().setBackground(new Color(4, 117, 196));
        table.getTableHeader().setForeground(Color.WHITE);

        //Tạo scroll cho bảng
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(new Color(51, 184, 231));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        add(scrollPane, BorderLayout.CENTER);

        //Lấy dữ liệu danh sách chủ đề
        List<ChuDe> dsChuDe = chuDeService.findByTenMonHoc(monHoc.getTenMon());
        for (ChuDe chuDe : dsChuDe) {
            model.addRow(new Object[]{chuDe.getMaChuDe(), chuDe.getTenChuDe(), "", ""});
        }

        // Tạo panel cho nút thêm
        JPanel panelButton = new JPanel();
        JButton btnAdd = new JButton("Thêm chủ đề");
        btnAdd.setIcon(FontIcon.of(BootstrapIcons.PLUS_CIRCLE, 18));
        btnAdd.addActionListener(e -> {
            String tenChuDe = JOptionPane.showInputDialog(this, "Nhập tên chủ đề:");
            if (tenChuDe != null) {
                ChuDe chuDe = new ChuDe();
                chuDe.setTenChuDe(tenChuDe);
                chuDe.setMonHoc(monHoc);
                try {
                    chuDeService.save(chuDe);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    chuDe = chuDeService.findByTenMonHocAndTenChuDe(monHoc.getTenMon(), tenChuDe);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                model.addRow(new Object[]{chuDe.getMaChuDe(), chuDe.getTenChuDe(), "", ""});
            }
        });
        btnAdd.setBackground(new Color(51, 231, 166));

        panelButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelButton.add(btnAdd);
        panelButton.setBackground(new Color(51, 184, 231));
        panelButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        add(panelButton, BorderLayout.SOUTH);
    }

    private void suaChuDe(int row) throws RemoteException {
        String current = (String) model.getValueAt(row, 1);
        ChuDe chuDe = chuDeService.findByTenMonHocAndTenChuDe(monHoc.getTenMon(), current);
        String updated = JOptionPane.showInputDialog(this, "Sửa tên chủ đề:", current);
        if (updated != null && !updated.trim().isEmpty()) {
            if( chuDeService.isDuplicate(updated.trim(), monHoc.getTenMon())) {
                JOptionPane.showMessageDialog(this, "Chủ đề này đã tồn tại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else{
                chuDe.setTenChuDe(updated.trim());
                chuDeService.update(chuDe);
                model.setValueAt(updated.trim(), row, 1);
            }
        }
    }

    private void xoaChuDe(int row) throws RemoteException {
        String current = (String) model.getValueAt(row, 1);
        ChuDe chuDe = chuDeService.findByTenMonHocAndTenChuDe(monHoc.getTenMon(), current);
        boolean hasCauHoi = chuDeService.hasCauHoi(chuDe.getMaChuDe());
        if (hasCauHoi) {
            JOptionPane.showMessageDialog(this, "Chủ đề này không thể xoá vì đã có câu hỏi liên quan!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
        else{
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xoá chủ đề này?", "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                chuDeService.delete(chuDe.getMaChuDe());
                model.removeRow(row);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý chủ đề");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 800);
        try {
            frame.add(new GiaoDienQuanLyChuDe(1));
        }catch (Exception e) {
            e.printStackTrace();
        }
        frame.setVisible(true);
    }
}
