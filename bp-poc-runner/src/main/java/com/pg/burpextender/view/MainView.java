package com.pg.burpextender.view;

import javax.swing.*;
import java.awt.*;

public class MainView {

    private JTabbedPane  tab;



    public MainView() {
        tab = new JTabbedPane();
        tab.add("加载POC",SelectPocTab.newInstance());
        tab.add("执行POC",RunPocTab.newInstance());
        tab.add("被动扫描",PassiveScanTab.newInstance());
    }


    public void writeMsg(String msg){
        System.out.println(msg);
    }


    public Component  getComponent(){
        return tab;
    }
}
