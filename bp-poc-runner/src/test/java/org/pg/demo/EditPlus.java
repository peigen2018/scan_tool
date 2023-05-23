package org.pg.demo;

import com.pg.burpextender.processor.ScanHolder;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;


public class EditPlus extends JFrame {

    public EditPlus() {
        super("模板");

        JSplitPane splitPane2 =new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//        textComp = createTextCompoent();
        this.setSize(800, 600);
        JPanel panel =new JPanel();

        JButton startButton = new JButton("开始检测");
        JButton stopButton = new JButton("停止检测");
        panel.add(startButton);
        panel.add(stopButton);
        splitPane2.setTopComponent(panel);

        String[] columns = { "name", "age", "email" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);


        splitPane2.setBottomComponent(new JScrollPane(table));

        AtomicInteger i = new AtomicInteger(0);
        startButton.addActionListener(action -> {
            int i1 = i.incrementAndGet();
            model.addRow(new String[]{"name"+i1,"age"+i1,"email"+i1});

        });


        JSplitPane splitPane1 =new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        JPanel tabpanel =new JPanel();
        tabpanel.add(new JButton("获取POC"));
        splitPane1.setTopComponent(tabpanel);


        JTabbedPane  tab = new JTabbedPane();

        tab.addTab("选择",splitPane1);
        tab.addTab("选择2",splitPane2);

        this.setContentPane(tab);

    }

    protected JTextComponent createTextCompoent() {
        JTextArea a = new JTextArea();
        a.setLineWrap(true);
        return a;
    }





    public class OpenAction extends AbstractAction {
        public OpenAction() {
            super("打开", new ImageIcon("icons/open.gif"));
        }
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(EditPlus.this) != JFileChooser.APPROVE_OPTION)
                return;
            File f = fc.getSelectedFile();
            if (f == null)
                return;
            FileReader fr = null;
            try {
                fr = new FileReader(f);
//                textComp.read(fr, null);
            } catch (Exception ee) {
                showWarnDialog("读文件异常!",ee.toString());
            } finally {
                if (fr != null) {
                    try {
                        fr.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    public class SaveAction extends AbstractAction {
        public SaveAction() {
            super("保存", new ImageIcon("icons/save.gif"));
        }

        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(EditPlus.this) != JFileChooser.APPROVE_OPTION)
                return;
            File f = fc.getSelectedFile();
            if (f == null)
                return;

            FileWriter fw = null;
            try {
                fw = new FileWriter(f);
//                textComp.write(fw);
            } catch (Exception ee) {
                showWarnDialog("保存文件异常!",ee.toString());
            } finally {
                try {
                    if (fw != null)
                        fw.close();
                } catch (IOException eee) {
                    eee.printStackTrace();
                }
            }
        }
    }

    public class CloseAction extends AbstractAction {
        public CloseAction() {
            super("关闭");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public void showWarnDialog(String msg, String warn) {
        JPanel p = new JPanel();
        JLabel label = new JLabel(msg);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        JTextArea area = new JTextArea(warn);
        area.setOpaque(false);
        area.setLineWrap(true);
        area.setPreferredSize(new Dimension(280, 100));
        p.setLayout(new BorderLayout());
        p.add(new JLabel(" "), BorderLayout.CENTER);
        p.add(label, BorderLayout.NORTH);
        p.add(new JScrollPane(area), BorderLayout.SOUTH);
        JOptionPane.showMessageDialog(EditPlus.this, p, "异常信息",
                JOptionPane.WARNING_MESSAGE);
    }

}