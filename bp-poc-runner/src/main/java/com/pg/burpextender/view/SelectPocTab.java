package com.pg.burpextender.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class SelectPocTab extends JSplitPane {
    public static JSplitPane newInstance() {
        JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        JPanel panel = new JPanel();
        panel.add(new JButton("选择POC"));
        JTextField field = new JTextField(60);
        field.setMaximumSize(new Dimension(80, 20));
        panel.add(field);
        panel.add(new JButton("查询"));
        pane.setTopComponent(panel);


        String[] columns = {"产线", "产品","版本", "日期"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);


        JTable table = new JTable(model);
        TableColumn column = table.getColumnModel().getColumn(0);
        model.addRow(new Object[]{ "零信任","vpn","5.0", "2022/11/12"});
        model.addRow(new Object[]{ "终端安全","天擎","1.0", "2022/11/12"});
        pane.setBottomComponent(new JScrollPane(table));
        return pane;
    }
}
