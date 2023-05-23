package com.pg.burpextender.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class RadioRender implements TableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return (Component) value;
    }
}
