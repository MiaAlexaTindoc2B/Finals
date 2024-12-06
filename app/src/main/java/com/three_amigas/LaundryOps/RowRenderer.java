package com.three_amigas.LaundryOps;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RowRenderer extends DefaultTableCellRenderer {
    private int highlightedRow = -1; // No row highlighted initially

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setHorizontalAlignment(CENTER);

        // Reset background color
        c.setBackground(Color.WHITE);

        // Highlight the specified row
        if (row == highlightedRow) {
            c.setBackground(Color.YELLOW);
        }

        return c;
    }

    // Method to set the highlighted row
    public void setHighlightedRow(int row) {
        this.highlightedRow = row;
    }
}