package ystruct.com;

import sun.plugin2.message.JavaObjectOpMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

/**
 * 完成！ 需要加细节
 * @author jq
 *
 */

public class UserFrame extends JFrame {
    Graphics g;
    Graphics p;
    JPanel ss = new JPanel();
    public static String Depath;
    public String Upath;
    public static  JFrame frame=new JFrame();
    public static final CardLayout c =new CardLayout();
	private JPanel buttonJPanel= new JPanel();
	private JButton buttonYa=new  JButton("压缩文件");
	private JButton buttonJa=new JButton("解压文件");
    private JButton buttonja=new JButton("详细信息");
    JFileChooser fileChooser=new JFileChooser();
    File f;
	public void showUI(){

        JPanel card1=new JPanel();
        JPanel card2 = new JPanel();
        ss.setLayout(c);

        card1.setSize(600, 180);
        card2.setSize(600, 180);
        //ss.add(card1);
        ss.add(card2);

        System.out.println("ncjd");
        frame.add(ss);
        frame.setTitle("哈夫曼压缩/解压~ystruct");
		frame.setSize(600, 300);
        JLabel photo = new JLabel(new ImageIcon("/home/yang/IdeaProjects/yang_Ystruct/1.jpg"));
        photo.setBounds(0, 0,600,350);
        frame.add(photo, BorderLayout.CENTER);

        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);
        buttonJPanel.add(buttonYa);
        buttonJPanel.add(buttonJa);
        buttonJPanel.add(buttonja);
        frame.add(buttonJPanel, BorderLayout.SOUTH);
        g=frame.getGraphics();
        g.setFont(new Font("微软雅黑", 14, 48));
        g.setColor(Color.green);
        g.drawString("欢迎使用",frame.getWidth()/2-100, frame.getHeight() / 2);
        buttonYa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.setColor(Color.green);
                g.drawString("欢迎使用",frame.getWidth()/2-100, frame.getHeight() / 2);
                fileChooser.showOpenDialog(null);
                f = fileChooser.getSelectedFile();
                try {
                    String s = f.getAbsolutePath();
                    String fileName = f.getName();
                    Depath = fileName;
                    System.out.println(fileName);
                    UtilHuffman uH = new UtilHuffman(s, fileName);
                    uH.start();
                }catch (Exception m) {
                    return;
                }
            }
        });
        buttonJa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //.clearRect(0,0,600,300);
                g.setColor(Color.green);
                g.drawString("欢迎使用", frame.getWidth() / 2 - 100, frame.getHeight() / 2);
                fileChooser.showOpenDialog(null);
                f = fileChooser.getSelectedFile();

                try {
                    String s = f.getAbsolutePath();
                    Upath=s;
                    String fileName = f.getName();
                    // System.out.println(s);
                    DecodeHuffman dH = new DecodeHuffman(s);
                    dH.start();
                } catch (Exception m) {
                    return;
                }
            }
        });
        buttonja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String str=null;
                c.last(ss);
                card2.getGraphics();
                g.setFont(new Font("微软雅黑", 14, 48));
                g.setColor(Color.white);
                g.drawString("欢迎使用", frame.getWidth() / 2 - 100, frame.getHeight() / 2);
                if(DecodeHuffman.md5==""){
                    JOptionPane.showMessageDialog(null,"没有详细信息");
                    return;
                }else{
                    System.out.println(DecodeHuffman.md5);
                    try {
                        File f =new  File(Depath);
                        System.out.println(Depath);
                        str= IsSameFile.getFileMD5(f);
                        System.out.println(str);
                    }catch (Exception m){
                        JOptionPane.showMessageDialog(null,"没有压缩文件");
                        return;
                    }
                    if(str.equals(DecodeHuffman.md5)){
                       // JLabel row = new JLabel("原文件名:"+Depath);
                        g.setFont(new Font("微软雅黑", 13, 30));
                        g.setColor(Color.green);
                        g.drawString("与原文件完全匹配", frame.getWidth() / 2 - 150, frame.getHeight() / 2 - 110);
                        g.setFont(new Font("宋体", 10, 20));
                        g.setColor(Color.BLACK);
                        g.drawString("原文件名:" + Depath, frame.getWidth() / 2 - 200, frame.getHeight() / 2 - 80);
                        g.drawString("原文件的大小:" + DecodeHuffman.rowfileLen,frame.getWidth()/2-200,frame.getHeight()/2-50);
                        g.drawString("压缩文件名:"+Upath,frame.getWidth()/2-200,frame.getHeight()/2-20);
                        g.drawString("压缩后的大小:"+DecodeHuffman.fileLen,frame.getWidth()/2-200,frame.getHeight()/2+10);
                        g.drawString("纯压缩大小:"+DecodeHuffman.chufileLen,frame.getWidth()/2-200,frame.getHeight()/2+40);
                        g.drawString("压缩率:"+((float)DecodeHuffman.fileLen/(float)DecodeHuffman.rowfileLen)*100+"%",frame.getWidth()/2-200,frame.getHeight()/2+70);
                        g.drawString("纯压缩率:"+((float)DecodeHuffman.chufileLen/(float)DecodeHuffman.rowfileLen)*100+"%",frame.getWidth()/2-200,frame.getHeight()/2+100);
                    }else {
                            int t=0;
                        try{
                            FileInputStream in = new FileInputStream(Depath);
                            t=in.available();
                        }catch (Exception h){
                            JOptionPane.showMessageDialog(null,"解压失败");
                           return;
                        }
                        g.setFont(new Font("微软雅黑", 13, 30));
                        g.setColor(Color.green);
                        g.drawString("与原文件不相同", frame.getWidth() / 2 - 150, frame.getHeight() / 2-110);
                        g.setFont(new Font("宋体", 10, 20));
                        g.setColor(Color.BLACK);
                        g.drawString("原文件名:" + Depath, frame.getWidth() / 2 - 200, frame.getHeight() / 2 - 80);
                        g.drawString("原文件的大小:" + DecodeHuffman.rowfileLen,frame.getWidth()/2-200,frame.getHeight()/2-50);
                        g.drawString("解压文件的大小:"+t,frame.getWidth()/2-200,frame.getHeight()/2-20);

                    }
                }

            }

        });
        }

    public static void main(String[] args) {
        new UserFrame().showUI();
    }
}
