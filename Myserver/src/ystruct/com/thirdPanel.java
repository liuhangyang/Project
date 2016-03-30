package ystruct.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by yang on 16-3-29.
 */
public class thirdPanel  extends JPanel{
    JPanel thirdPanel;  //将要返回的卡片;
    JComboBox selectact;
    JPanel gridPanel;
    JPanel upJpanel;
    JLabel seat;
    JLabel lable;
    JLabel[] seatmange=new JLabel[100];
    public JPanel returnthirdPanel(){
         int i;
            lable=new JLabel("请选择放映厅:");
        String  act[]={"1号放映厅","2号放映厅","3号放映厅","4号放映厅","5号放映厅"};
        thirdPanel=new JPanel(new BorderLayout());
        upJpanel=new JPanel(new FlowLayout());
        selectact=new JComboBox(act);
        selectact.setSelectedIndex(0);
        upJpanel.add(lable);
        upJpanel.add(selectact);
        thirdPanel.add(upJpanel,BorderLayout.NORTH);


        gridPanel=new JPanel(new GridLayout(10,10));
        for(i=0;i<100;i++){
             seatmange[i]=new JLabel(new ImageIcon("/home/yang/IdeaProjects/Myserver/src/6.png"));
            gridPanel.add(seatmange[i]);
        }

        for( i=0;i<100;i++) {
            seatmange[i].addMouseListener(new seat(i));

        }
        thirdPanel.add(gridPanel);
        return  thirdPanel;
    }
    class seat extends MouseAdapter{
        int i;
        public seat(int i){
            this.i=i;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if(seatmange[i].isVisible()){
                seatmange[i].setVisible(false);
            }else{
                System.out.println("cnd");
                seatmange[i]=new JLabel(new ImageIcon("/home/yang/IdeaProjects/Myserver/src/6.png"));
                gridPanel.add(seatmange[i]);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
        }
    }

}
