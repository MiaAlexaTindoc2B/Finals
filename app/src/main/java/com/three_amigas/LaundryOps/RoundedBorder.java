package com.three_amigas.LaundryOps;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Insets;
import javax.swing.border.Border;

public class RoundedBorder implements Border {
    private final int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(5, 10, 5, 10);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int adjustedX = x + 1;
        int adjustedY = y + 1;
        int adjustedWidth = width - 2;
        int adjustedHeight = height - 2;

        g2.drawRoundRect(adjustedX, adjustedY, adjustedWidth - 1, adjustedHeight - 1, radius, radius);

        g2.dispose();
    }
}