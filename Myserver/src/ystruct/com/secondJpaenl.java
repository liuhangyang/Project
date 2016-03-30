package ystruct.com;

import javax.jnlp.JNLPRandomAccessFile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by yang on 16-3-27.
 */
public class secondJpaenl extends JPanel {

    JPanel secondpanel;
    JPanel actsecond;
    JButton addactButton;
    JButton deletebutton;
    JButton modButton;
    JButton prepage;
    JButton nextpage;
    JPanel gridPanel;
    public JPanel returnactadd(){
        secondpanel=new JPanel(new BorderLayout());
        JLabel image2=new JLabel(new ImageIcon("/home/yang/IdeaProjects/Myserver/src/5.png"));
        secondpanel.add(image2,BorderLayout.NORTH);
        addactButton=new JButton("添加");
        deletebutton=new JButton("删除");
        modButton=new JButton("修改");
        prepage=new JButton("上一页");
        nextpage=new JButton("下一页");
        actsecond=new JPanel();
        actsecond.setLayout(new BoxLayout(actsecond,BoxLayout.X_AXIS));
        actsecond.add(Box.createHorizontalStrut(10));
        actsecond.add(addactButton);
        actsecond.add(Box.createHorizontalStrut(20));
        actsecond.add(deletebutton);
        actsecond.add(Box.createHorizontalStrut(20));
        actsecond.add(modButton);
        actsecond.add(Box.createHorizontalStrut(350));
        actsecond.add(prepage);
        actsecond.add(Box.createHorizontalStrut(10));
        actsecond.add(nextpage);
        secondpanel.add(actsecond, BorderLayout.SOUTH);

        gridPanel = new JPanel(new GridLayout(10, 6, 4, 4));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel("ID"));
        gridPanel.add(new JLabel("影片名称"));
        gridPanel.add(new JLabel("时长"));
        gridPanel.add(new JLabel("放映时间"));
        gridPanel.add(new JLabel("演出厅"));
        for (int i = 0; i < 54; i++) {
            if (i % 6 == 0) {
                gridPanel.add(new JLabel(" "));
            } else
                gridPanel.add(new JLabel("" + i));
        }
        secondpanel.add(gridPanel);

        addactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    new addact();
            }
        });
        deletebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    new actdelete();
            }
        });
        modButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new modact();
            }
        });
        return  secondpanel;
    }
}
