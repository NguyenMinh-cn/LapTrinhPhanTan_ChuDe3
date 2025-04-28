package gui;
import entities.CauHoi;
import entities.MonHoc;
import gui.custom.ButtonEditor;
import gui.custom.ButtonRenderer;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;
import service.CauHoiService;
import service.MonHocService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class GiaoDienNganHangCauHoi extends JPanel {
    private JPanel mainPanel; // Panel cha chứa các màn hình con
    private CauHoiService cauHoiService = (CauHoiService) Naming.lookup("rmi://192.168.1.13:8081/cauHoiService");
    private MonHocService monHocService = (MonHocService) Naming.lookup("rmi://192.168.1.13:8081/monHocService");
    private JTable table;
    private DefaultTableModel model;
    private List<CauHoi> listCauHoi;
    private JComboBox<String> comboBoxMonHoc;

    public GiaoDienNganHangCauHoi(JPanel mainPanel) throws MalformedURLException, NotBoundException, RemoteException {
        this.mainPanel = mainPanel;
        initUI();
    }

    private void initUI() throws RemoteException {
        setLayout(new BorderLayout());

        // Toolbar
        // Toolbar
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(new Color(210, 234, 255));
        toolbar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel trái: ComboBox chọn môn học
        JPanel panelTrai = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTrai.setBackground(new Color(210, 234, 255));

        comboBoxMonHoc = new JComboBox<>();
        comboBoxMonHoc.setPreferredSize(new Dimension(350, 30));
        comboBoxMonHoc.addItem("Tất cả"); // option mặc định

        List<MonHoc> danhSachMonHoc = monHocService.getAll();
        for (MonHoc mh : danhSachMonHoc) {
            comboBoxMonHoc.addItem(mh.getTenMon());
        }

        panelTrai.add(new JLabel("Môn học: "));
        panelTrai.add(comboBoxMonHoc);

        // Panel phải: Nút Thêm câu hỏi
        JPanel panelPhai = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelPhai.setBackground(new Color(210, 234, 255));

        JButton btnThemCauHoi = new JButton("Thêm câu hỏi");
        btnThemCauHoi.setBackground(new Color(4, 117, 196));
        btnThemCauHoi.setForeground(Color.WHITE);
        panelPhai.add(btnThemCauHoi);

        // Add panel trái và phải vào toolbar
        toolbar.add(panelTrai, BorderLayout.WEST);
        toolbar.add(panelPhai, BorderLayout.EAST);

        add(toolbar, BorderLayout.NORTH);


        model = new DefaultTableModel(new Object[]{"STT", "Mã Câu Hỏi", "Nội dung", "Đáp án đúng", "Môn học", "Chủ đề", "Sửa", "Xoá"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 6; // Sửa, Xoá mới được click
            }
        };

        table = new JTable(model);
        table.setRowHeight(36);

        table.getColumnModel().getColumn(0).setPreferredWidth(2); //Cột STT
        // Ẩn cột Mã Câu Hỏi
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);
        table.getColumnModel().getColumn(1).setWidth(0);
        table.getColumnModel().getColumn(2).setPreferredWidth(400); // Cột "Nội dung"
        table.getColumnModel().getColumn(3).setPreferredWidth(150); // Cột "Đáp án đúng"
        table.getColumnModel().getColumn(4).setPreferredWidth(50); // Cột "Môn học"
        table.getColumnModel().getColumn(5).setPreferredWidth(50); // Cột "Chủ đề"
        table.getColumnModel().getColumn(6).setPreferredWidth(5); // Cột "Sửa"
        table.getColumnModel().getColumn(7).setPreferredWidth(5); // Cột "Xoá"

        // Áp dụng render cho phép xuống dòng trong ô
        table.getColumnModel().getColumn(2).setCellRenderer(new MultiLineTableCellRenderer());
        table.getColumnModel().getColumn(3).setCellRenderer(new MultiLineTableCellRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new MultiLineTableCellRenderer());
        table.getColumnModel().getColumn(5).setCellRenderer(new MultiLineTableCellRenderer());

        FontIcon iconEdit = FontIcon.of(BootstrapIcons.PENCIL, 18, Color.BLUE);
        FontIcon iconDelete = FontIcon.of(BootstrapIcons.TRASH, 18, Color.RED);

        // Cài renderer & editor cho từng nút
        table.getColumn("Sửa").setCellRenderer(new ButtonRenderer(iconEdit));
        table.getColumn("Sửa").setCellEditor(new ButtonEditor(iconEdit, row -> {
            try {
                suaCauHoi(row);
            } catch (RemoteException | MalformedURLException | NotBoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi sửa chủ đề!");
            }
        }));
        table.getColumn("Xoá").setCellRenderer(new ButtonRenderer(iconDelete));
        table.getColumn("Xoá").setCellEditor(new ButtonEditor(iconDelete, row -> {
            try {
                xoaCauHoi(row);
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
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 20, 15));
        add(scrollPane, BorderLayout.CENTER);

        // Load dữ liệu vào bảng
        loadCauHoi();
        adjustRowHeights(table);

        // Sự kiện nút Thêm câu hỏi
        btnThemCauHoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                try {
                    mainPanel.add(new GiaoDienThemCauHoi(mainPanel, null)); // Chuyển sang giao diện ThemCauHoi
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
        });

        comboBoxMonHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String monHocChon = (String) comboBoxMonHoc.getSelectedItem();
                    if (monHocChon.equals("Tất cả")) {
                        loadCauHoi();
                    } else {
                        loadCauHoiTheoMon(monHocChon);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    private void suaCauHoi(int row) throws RemoteException, MalformedURLException, NotBoundException {
        long maCauHoi = (long) model.getValueAt(row, 1); // lấy từ cột 1: Mã Câu Hỏi
        int maCauHoiInt = (int) maCauHoi;
        CauHoi cauHoi = cauHoiService.finByID(maCauHoiInt);
        mainPanel.removeAll();
        mainPanel.add(new GiaoDienThemCauHoi(mainPanel, cauHoi));
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void xoaCauHoi(int row) throws RemoteException {
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }
        long maCauHoi = (long) model.getValueAt(row, 1); // lấy từ cột 1: Mã Câu Hỏi
        int maCauHoiInt = (int) maCauHoi;
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xoá câu hỏi này không?", "Xoá câu hỏi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if(cauHoiService.inBaiThi(maCauHoiInt)){
                JOptionPane.showMessageDialog(this, "Câu hỏi này đã được sử dụng trong bài thi, không thể xoá!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }else {
                cauHoiService.delete(maCauHoiInt);
                loadCauHoi();
                JOptionPane.showMessageDialog(this, "Xoá câu hỏi thành công!");
            }
        }
    }

    private void loadCauHoi() throws RemoteException {
        listCauHoi = cauHoiService.getCauHoiCoChuDe();
        model.setRowCount(0); // Xóa dữ liệu cũ
        int stt = 1;
        for (CauHoi ch : listCauHoi) {
            model.addRow(new Object[]{stt, ch.getMaCauHoi(), ch.getNoiDung(), ch.getDapAnDung(), ch.getChuDe().getMonHoc().getTenMon(), ch.getChuDe().getTenChuDe()});
            stt++;
        }
    }

    private void loadCauHoiTheoMon(String tenMonHoc) throws RemoteException {
        listCauHoi = cauHoiService.findByMon(tenMonHoc);
        model.setRowCount(0); // Xoá bảng cũ

        int stt = 1;
        for (CauHoi ch : listCauHoi) {
            model.addRow(new Object[]{stt, ch.getMaCauHoi(), ch.getNoiDung(), ch.getDapAnDung(), ch.getChuDe().getMonHoc().getTenMon(), ch.getChuDe().getTenChuDe()});
            stt++;
        }
    }

    private void adjustRowHeights(JTable table) {
        for (int row = 0; row < table.getRowCount(); row++) {
            int maxHeight = table.getRowHeight(row);

            for (int column = 0; column < table.getColumnCount(); column++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(cellRenderer, row, column);
                int height = comp.getPreferredSize().height + table.getRowMargin();
                maxHeight = Math.max(height, maxHeight);
            }

            if (table.getRowHeight(row) != maxHeight) {
                table.setRowHeight(row, maxHeight);
            }
        }
    }

}
