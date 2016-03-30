package ystruct.com;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yang on 16-3-29.
 */
public class fourJpanel extends JPanel{
    String  act1[]={"1号放映厅","2号放映厅","3号放映厅","4号放映厅","5号放映厅"};
    String film1[]={"午夜凶铃","笔仙","叶问3"};
    JPanel fourJpanel;  //将要返回的卡片
    JPanel upJPanel;
    JLabel film=new JLabel("影片名");
    JLabel act=new JLabel("演出厅");
    JComboBox filmJcombox;
    JComboBox actcombox;
    JButton button=new JButton("查询");
    JButton nextpage=new JButton("下一页");
    JButton prepage=new JButton("上一页");
    JPanel downJPanel=new JPanel();
    JPanel gridPanel;
    public JPanel returnfouthPanel(){
        fourJpanel=new JPanel(new BorderLayout());
        upJPanel=new JPanel(new FlowLayout());
        fourJpanel.add(upJPanel,BorderLayout.NORTH);
        filmJcombox=new JComboBox(film1);
        actcombox=new JComboBox(act1);
        upJPanel.add(film);
        upJPanel.add(filmJcombox);
        upJPanel.add(act);
        upJPanel.add(actcombox);
        upJPanel.add(button);
        downJPanel.add(prepage);
        downJPanel.add(nextpage);
        fourJpanel.add(downJPanel,BorderLayout.SOUTH);
        gridPanel=new JPanel(new GridLayout(10,6,4,4));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel("影片名"));
        gridPanel.add(new JLabel("放映厅"));
        gridPanel.add(new JLabel("票价"));
        gridPanel.add(new JLabel("总票数"));
        gridPanel.add(new JLabel("总销售额"));
        for(int i=0;i<54;i++) {
            if (i % 6 == 0) {
                gridPanel.add(new JLabel(""));
            } else {
                gridPanel.add(new JLabel("" + i));
            }
        }
        fourJpanel.add(gridPanel,BorderLayout.CENTER);
        return  fourJpanel;
    }


}
