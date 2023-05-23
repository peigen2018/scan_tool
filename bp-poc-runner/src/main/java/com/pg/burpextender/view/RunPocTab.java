package com.pg.burpextender.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RunPocTab extends JSplitPane {
    public static JSplitPane newInstance() {
        JSplitPane pane =new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        JPanel panel =new JPanel();
        panel.add(new JButton("执行POC"));
        panel.add(new TextField(""));
        pane.setTopComponent(panel);




        String[] columns = {  "URL", "结果" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);

        model.addRow(new String[]{"https://test.com","存在"});
        pane.setBottomComponent(new JScrollPane(table));
        return  pane;
    }
}
