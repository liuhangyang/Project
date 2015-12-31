package ystruct.com;

import java.awt.*;

import javax.swing.*;

public class ProgressBar extends Thread{

public JProgressBar jPM=new JProgressBar(0,99);
    
    public String name;
	public JFrame jF=new JFrame();
    //定义一个构造器，传入名字
    public ProgressBar(String name){
    	this.name=name;
    }
    //定义一个显示窗体的方法
	public void showUI(){

		 //设置一个名字
		   jF.setTitle(name+"中……");
		    //设置一个大小
		   jF.setSize(300, 200);
		   //加一个布局管理器
		   FlowLayout f=new FlowLayout();
		   jF.setLayout(f);
		   jF.setResizable(false);
		   jF.setAlwaysOnTop(true);
		  // jF.setDefaultCloseOperation(3);
		   jF.getContentPane().setBackground(Color.WHITE);
		   jPM.setStringPainted(true);
		   //jPM.setSize(200,30);
            jPM.setPreferredSize(new Dimension(230,30));
		   jF.add(jPM);
		   jF.setVisible(true);

	}
	public void run(){
		showUI();
		while (true){
			try {
                 Thread.sleep(100);
        		//jPM.setValue(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jF.repaint();
		}
	}

}
