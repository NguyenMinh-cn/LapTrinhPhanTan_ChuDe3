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
    private String ipAddress = "localhost";
    private JTable table;
    private DefaultTableModel model;
    private int maMon;
    private MonHocService monHocService = (MonHocService) Naming.lookup("rmi://" + ipAddress + ":8081/monHocService");
    private ChuDeService chuDeService = (ChuDeService) Naming.lookup("rmi://" + ipAddress + ":8081/chuDeService");
    private MonHoc monHoc;
    private JComboBox<String> cbMonHoc;

    public GiaoDienQuanLyChuDe() throws RemoteException, MalformedURLException, NotBoundException {
        //Lấy môn học từ database (bổ sung sau)

        setLayout(new BorderLayout());

        //Tạo panel cho tiêu đề
        JPanel panelMonHoc = new JPanel();
        panelMonHoc.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        panelMonHoc.setBackground(new Color(210, 234, 255));

        JLabel lblMonHoc = new JLabel("Chọn môn học:");
        List<MonHoc> dsMonHoc = monHocService.getAll();
        String[] dsmonHoc = new String[dsMonHoc.size()];
        for (int i = 0; i < dsMonHoc.size(); i++) {
            MonHoc mh = dsMonHoc.get(i);
            dsmonHoc[i] = mh.getTenMon();
        }
        cbMonHoc = new JComboBox<>(dsmonHoc);
        cbMonHoc.setPreferredSize(new Dimension(350, 30));
        panelMonHoc.add(lblMonHoc);
        panelMonHoc.add(cbMonHoc);



        add(panelMonHoc, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"STT", "Tên chủ đề", "Sửa", "Xoá"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 2; // chỉ cột "Sửa" và "Xoá" được click
            }
        };

        table = new JTable(model);
        table.setRowHeight(36);

        table.getColumnModel().getColumn(0).setPreferredWidth(10); // Cột "Mã chủ đề"
        table.getColumnModel().getColumn(1).setPreferredWidth(450); // Cột "Tên chủ đề"
        table.getColumnModel().getColumn(2).setPreferredWidth(10);  // Cột "Sửa"
        table.getColumnModel().getColumn(3).setPreferredWidth(10);  // Cột "Xoá"


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
        scrollPane.setBackground(new Color(210, 234, 255));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        add(scrollPane, BorderLayout.CENTER);

        //Lấy dữ liệu danh sách chủ đề
        loadChuDe();

        // Tạo panel cho nút thêm
        JPanel panelButton = new JPanel();
        JButton btnAdd = new JButton("Thêm chủ đề");
        btnAdd.setIcon(FontIcon.of(BootstrapIcons.PLUS_CIRCLE, 18));
        btnAdd.addActionListener(e -> {
            String tenChuDe = JOptionPane.showInputDialog(this, "Nhập tên chủ đề:");
            if(tenChuDe!=null){
                if (validateTenChuDe(tenChuDe)) {
                    try {
                        if(chuDeService.isDuplicate(tenChuDe.trim(), monHoc.getTenMon())) {
                            JOptionPane.showMessageDialog(this, "Chủ đề này đã tồn tại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        } else {
                            ChuDe newChuDe = new ChuDe();
                            newChuDe.setTenChuDe(tenChuDe.trim());
                            newChuDe.setMonHoc(monHoc);
                            chuDeService.save(newChuDe);
                            model.addRow(new Object[]{table.getRowCount()+1, newChuDe.getTenChuDe(), "", ""});
                        }
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Tên chủ đề không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        cbMonHoc.addActionListener(e -> {
            try {
                loadChuDe();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });

        btnAdd.setBackground(new Color(51, 231, 166));

        panelButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelButton.add(btnAdd);
        panelButton.setBackground(new Color(210, 234, 255));
        panelButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        add(panelButton, BorderLayout.SOUTH);
    }

    private void loadChuDe() throws RemoteException {
        monHoc = monHocService.findByTenMon(cbMonHoc.getSelectedItem().toString());
        List<ChuDe> dsChuDe = chuDeService.findByTenMonHoc(monHoc.getTenMon());
        model.setRowCount(0); // Xóa dữ liệu cũ
        int stt = 1;
        for (ChuDe chuDe : dsChuDe) {
            model.addRow(new Object[]{stt, chuDe.getTenChuDe(), "", ""});
            stt++;
        }
    }

    private void suaChuDe(int row) throws RemoteException {
        String current = (String) model.getValueAt(row, 1);
        ChuDe chuDe = chuDeService.findByTenMonHocAndTenChuDe(monHoc.getTenMon(), current);
        String updated = JOptionPane.showInputDialog(this, "Sửa tên chủ đề:", current);
        if (updated != null) {
            if (validateTenChuDe(updated) && !updated.trim().isEmpty()) {
                if( chuDeService.isDuplicate(updated.trim(), monHoc.getTenMon())) {
                    JOptionPane.showMessageDialog(this, "Chủ đề này đã tồn tại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                } else{
                    chuDe.setTenChuDe(updated.trim());
                    chuDeService.update(chuDe);
                    model.setValueAt(updated.trim(), row, 1);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Tên chủ đề không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
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
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                loadChuDe();
            }
        }
    }

    public boolean validateTenChuDe(String tenChuDe) {
        if (tenChuDe == null || tenChuDe.trim().isEmpty()) {
            return false;
        }
        return tenChuDe.matches("^[\\p{L}\\d\\s,()_-]+$");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý chủ đề");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 800);
        try {
            frame.add(new GiaoDienQuanLyChuDe());
        }catch (Exception e) {
            e.printStackTrace();
        }
        frame.setVisible(true);
    }
}
