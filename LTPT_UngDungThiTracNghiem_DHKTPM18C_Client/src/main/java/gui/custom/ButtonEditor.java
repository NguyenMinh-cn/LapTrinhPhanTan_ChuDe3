package gui.custom;

import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntConsumer;

public class ButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private IntConsumer action;
    private int row;

    public ButtonEditor(FontIcon icon, IntConsumer action) {
        super(new JCheckBox());
        this.action = action;
        this.button = new JButton(icon);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        button.addActionListener(e -> {
            if (action != null) {
                action.accept(row);
            }
            fireEditingStopped(); // Quan trọng!
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.row = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
