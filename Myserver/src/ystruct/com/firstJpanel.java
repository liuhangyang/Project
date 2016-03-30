package ystruct.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by yang on 16-3-26.
 */
public class firstJpanel extends ScrollPane {
    JPanel firstJpanel;  //将要返回德的卡片
    JButton firstButton; //添加按钮
    JButton sencondButton;  //删除影片
    JButton thirdButton;    //修改影片
    JPanel firstupimagetell;
    JPanel gridPanel;
    JButton nextpage;
    JButton prepage;

    public JPanel returnfilm() {
        firstJpanel = new JPanel(new BorderLayout());
        firstButton = new JButton("添加影片");
        sencondButton = new JButton("删除影片");
        thirdButton = new JButton("修改影片");
        nextpage = new JButton("下一页");
        prepage = new JButton("上一页");
        //上面那一块
        JLabel image = new JLabel(new ImageIcon("/home/yang/IdeaProjects/Myserver/src/5.png"));
        firstJpanel.add(image, BorderLayout.NORTH);
        firstupimagetell = new JPanel();
        BoxLayout layout = new BoxLayout(firstupimagetell, BoxLayout.X_AXIS);
        firstupimagetell.setLayout(layout);
        firstupimagetell.add(Box.createHorizontalStrut(10));
        firstupimagetell.add(firstButton);
        firstupimagetell.add(Box.createHorizontalStrut(20));
        firstupimagetell.add(sencondButton);
        firstupimagetell.add(Box.createHorizontalStrut(20));
        firstupimagetell.add(thirdButton);
        firstupimagetell.add(Box.createHorizontalStrut(350));
        firstupimagetell.add(prepage);
        firstupimagetell.add(Box.createHorizontalStrut(10));
        firstupimagetell.add(nextpage);
        firstJpanel.add(firstupimagetell, BorderLayout.SOUTH);


        //grid
        gridPanel = new JPanel(new GridLayout(10, 6, 4, 4));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel("ID"));
        gridPanel.add(new JLabel("影片名称"));
        gridPanel.add(new JLabel("时长"));
        gridPanel.add(new JLabel("添加时间"));
        gridPanel.add(new JLabel("状态"));
        for (int i = 0; i < 54; i++) {
            if (i % 6 == 0) {
                gridPanel.add(new JLabel(" "));
            } else
                gridPanel.add(new JLabel("" + i));
        }
        firstJpanel.add(gridPanel);

        firstButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("杨龙飞");
                MyDialog dlg=new MyDialog();
            }
        });
        sencondButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new deletefilm();
            }
        });
        thirdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MyDialog();
            }
        });
        prepage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        nextpage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        return firstJpanel;
    }


}