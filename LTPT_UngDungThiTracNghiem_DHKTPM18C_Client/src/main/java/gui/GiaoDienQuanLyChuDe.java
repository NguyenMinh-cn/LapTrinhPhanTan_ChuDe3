package gui;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GiaoDienQuanLyChuDe extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private int maMon;

    public GiaoDienQuanLyChuDe(int maMon) {
        this.maMon = maMon;
        //Lấy môn học từ database (bổ sung sau)

        setLayout(new BorderLayout());

        //Tạo panel cho tiêu đề
        JPanel panelTitle = new JPanel();
        panelTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JLabel lblTitle = new JLabel("Môn học: " + maMon);
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
        table.getColumn("Sửa").setCellEditor(new ButtonEditor(iconEdit, this::suaChuDe));

        table.getColumn("Xoá").setCellRenderer(new ButtonRenderer(iconDelete));
        table.getColumn("Xoá").setCellEditor(new ButtonEditor(iconDelete, this::xoaChuDe));

        //Hàng tiêu đề màu xanh
        table.getTableHeader().setBackground(new Color(4, 117, 196));
        table.getTableHeader().setForeground(Color.WHITE);

        //Tạo scroll cho bảng
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(new Color(51, 184, 231));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        add(scrollPane, BorderLayout.CENTER);

        // Dữ liệu mẫu
        model.addRow(new Object[]{"CD001", "Cấu trúc dữ liệu", "", ""});
        model.addRow(new Object[]{"CD002", "Giải thuật", "", ""});

        // Tạo panel cho nút thêm
        JPanel panelButton = new JPanel();
        JButton btnAdd = new JButton("Thêm chủ đề");
        btnAdd.setIcon(FontIcon.of(BootstrapIcons.PLUS_CIRCLE, 18));
        btnAdd.addActionListener(e -> {
            String maChuDe = JOptionPane.showInputDialog(this, "Nhập mã chủ đề:");
            String tenChuDe = JOptionPane.showInputDialog(this, "Nhập tên chủ đề:");
            if (maChuDe != null && tenChuDe != null) {
                model.addRow(new Object[]{maChuDe.trim(), tenChuDe.trim(), "", ""});
            }
        });
        btnAdd.setBackground(new Color(51, 231, 166));

        panelButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelButton.add(btnAdd);
        panelButton.setBackground(new Color(51, 184, 231));
        panelButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        add(panelButton, BorderLayout.SOUTH);
    }

    private void suaChuDe(int row) {
        String current = (String) model.getValueAt(row, 1);
        String updated = JOptionPane.showInputDialog(this, "Sửa tên chủ đề:", current);
        if (updated != null && !updated.trim().isEmpty()) {
            model.setValueAt(updated.trim(), row, 1);
        }
    }

    private void xoaChuDe(int row) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xoá chủ đề này?", "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            model.removeRow(row);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý chủ đề");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 800);
        frame.add(new GiaoDienQuanLyChuDe(1));
        frame.setVisible(true);
    }
}
