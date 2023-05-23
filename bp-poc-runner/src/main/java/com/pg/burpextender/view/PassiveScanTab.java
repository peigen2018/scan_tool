package com.pg.burpextender.view;

import javax.swing.*;

public class PassiveScanTab extends JSplitPane {
    public static JSplitPane newInstance() {
        JSplitPane pane =new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        pane.add(new JButton("获取POC"));
        return  pane;
    }
}
