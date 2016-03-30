package ystruct.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by yang on 16-3-30.
 */

public class actdelete extends JFrame implements ActionListener {
    JPanel panel;
    JPanel jp1=new JPanel(new FlowLayout());
    JPanel Jp2=new JPanel(new FlowLayout());
    JLabel lab=new JLabel("请输入ID");
    JTextField text=new JTextField(10);
    JButton ok=new JButton("确定");
    JButton cancel=new JButton("取消");
    public actdelete(){
        panel=new JPanel();
        setSize(100,80);
        Container c=getContentPane();
        c.add(panel);
        setResizable(false);

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        jp1.add(lab);
        jp1.add(text);
        panel.add(jp1);
        Jp2.add(ok);
        Jp2.add(cancel);
        panel.add(Jp2);
        ok.addActionListener(this);
        cancel.addActionListener(this);
        pack();
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}