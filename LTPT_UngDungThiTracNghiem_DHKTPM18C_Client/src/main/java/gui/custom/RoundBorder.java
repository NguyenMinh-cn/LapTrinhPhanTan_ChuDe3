package gui.custom;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundBorder implements javax.swing.border.Border {
    private Color color;
    private int radius;

    public RoundBorder(Color color, int radius) {
        this.color = color;
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(5, 5, 5, 5); // Khoảng cách viền
    }

    @Override
    public boolean isBorderOpaque() {
        return false; // Viền không mờ
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Mượt viền
        g2d.setStroke(new BasicStroke(2)); // Độ dày của viền

        // Vẽ bo tròn
        g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
    }
}

