package ystruct.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by yang on 16-3-29.
 */
public class MyDialog extends JFrame implements ActionListener {
        JPanel panel=new JPanel();
        JPanel jp1=new JPanel(new FlowLayout());
        JPanel jp2=new JPanel(new FlowLayout());
        JPanel jp3=new JPanel(new FlowLayout());
        JPanel jp4=new JPanel(new FlowLayout());
        JLabel label=new JLabel("请输入影片 ID");
        JLabel label1=new JLabel("请输入影片名称");
        JLabel label3=new JLabel("请输入影片时长");
        JTextField film1=new JTextField(10);
        JTextField film2=new JTextField(10);
        JTextField film3=new JTextField(10);
        JButton ok=new JButton("确定");
        JButton cancel=new JButton("取消");
        MyDialog() {
            setSize(100,80);
            Container c=getContentPane();
            c.add(panel);
            setResizable(false);
            panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
            jp1.add(label);
            jp1.add(film1);
            panel.add(jp1);
            jp2.add(label1);
            jp2.add(film2);
            panel.add(jp2);
            jp3.add(label3);
            jp3.add(film3);
            panel.add(jp3);
            jp4.add(ok);
            jp4.add(cancel);
            panel.add(jp4);
            ok.addActionListener(this);
            cancel.addActionListener(this);
            pack();
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==ok){
                dispose();
            }else{
                dispose();
            }

        }
    }
