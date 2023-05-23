package com.pg.burpextender.component;

import burp.*;
import com.pg.burpextender.view.MainView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PocRunner implements IContextMenuFactory ,ITab{



    private static final String EXTENDER_NAME = "POC Runner";




    public PocRunner() {



    }


    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        List<JMenuItem> menus = new ArrayList<>();
        menus.add(new JMenuItem("POC_Runner"));
        return menus;
    }

    @Override
    public String getTabCaption() {
        return EXTENDER_NAME;
    }



    @Override
    public Component getUiComponent() {
        MainView mainView = new MainView();
        return mainView.getComponent();
    }
}
