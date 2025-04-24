package gui;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer(FontIcon icon) {
        setIcon(icon);
        setOpaque(true);
        setBorderPainted(false);
        setContentAreaFilled(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        return this;
    }
}
