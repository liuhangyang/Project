package ystruct.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by yang on 16-3-29.
 */
public class addact extends JFrame implements ActionListener {
  JPanel panel;
    String  act[]={"1号放映厅","2号放映厅","3号放映厅","4号放映厅","5号放映厅"};
    String film[]={"午夜凶铃","笔仙","叶问3"};
    String year[]={"2016","2017","2018"};
    String month[]={"1","2","3","4","5","6","7","8","9"};
    String day[]={"1","2","3","4","5","6","7","8","9","10"};
    JComboBox combo;
    JComboBox combo1;
    JPanel jp;
    JPanel jp2;
    JLabel  data=new JLabel("放映时间");
    JButton ok=new JButton("确定");
    JButton cancel=new JButton("取消");
    //JTextField dataetxt=new JTextField(10);
    public addact(){
        panel=new JPanel();
        setSize(300,200);
        Container c=getContentPane();
        c.add(panel);
        setResizable(false);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        combo=new JComboBox(act);
        combo.setSelectedIndex(0);
        combo1=new JComboBox(film);
        panel.add(combo1);
        panel.add(combo);
        jp=new JPanel();
        jp.setLayout(new BoxLayout(jp,BoxLayout.X_AXIS));
        jp.add(data);
        JComboBox comboBox1=new JComboBox(year);
        comboBox1.setSelectedIndex(0);
        JComboBox comboBox2=new JComboBox(month);
        comboBox2.setSelectedIndex(0);
        JComboBox comboBox3=new JComboBox(day);
        comboBox3.setSelectedIndex(0);
        jp.add(comboBox1);
        jp.add(comboBox2);
        jp.add(comboBox3);
        panel.add(jp);
        jp2=new JPanel(new FlowLayout());
        jp2.add(ok);
        jp2.add(cancel);
        panel.add(jp2);
       // pack();
        ok.addActionListener(this);
        cancel.addActionListener(this);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

            dispose();
    }
}
class modact extends JFrame implements ActionListener{
    String  act[]={"1号放映厅","2号放映厅","3号放映厅","4号放映厅","5号放映厅"};
    String year[]={"2016","2017","2018"};
    String month[]={"1","2","3","4","5","6","7","8","9"};
    String day[]={"1","2","3","4","5","6","7","8","9","10"};
    JLabel lab1=new JLabel("请选择的ID");
    JLabel label=new JLabel("时长");
    JLabel lab3=new JLabel("放映时间");
    JTextField text=new JTextField(10);
    JPanel jp1=new JPanel(new FlowLayout());
    JPanel jp2=new JPanel(new FlowLayout());
    JPanel jp3=new JPanel(new FlowLayout());
    JPanel jp4=new JPanel(new FlowLayout());
    JButton ok=new JButton("确定");
    JButton cancel=new JButton("取消");
    String SID[]={"1","2","3","4","5","6","7","8","9"};
    JComboBox combo;
    JComboBox combo1;
    JPanel panel;
    public  modact(){
        panel=new JPanel();
        setSize(400,300);
        Container c=getContentPane();
        c.add(panel);
        setResizable(false);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        combo=new JComboBox(SID);
        combo.setSelectedIndex(0);
        jp1.add(lab1);
        jp1.add(combo);
        panel.add(jp1);
        jp2.add(label);
        jp2.add(text);
        panel.add(jp2);
        JComboBox comboBox1=new JComboBox(year);
        comboBox1.setSelectedIndex(0);
        JComboBox comboBox2=new JComboBox(month);
        comboBox2.setSelectedIndex(0);
        JComboBox comboBox3=new JComboBox(day);
        comboBox3.setSelectedIndex(0);
        jp3.add(lab3);
        jp3.add(comboBox1);
        jp3.add(comboBox2);
        jp3.add(comboBox3);
        panel.add(jp3);
        JComboBox comact=new JComboBox(act);
        panel.add(comact);
        ok.addActionListener(this);
        cancel.addActionListener(this);
        jp4.add(ok);
        jp4.add(cancel);
        panel.add(jp4);
         ok.addActionListener(this);
        cancel.addActionListener(this);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
            dispose();
    }
}
