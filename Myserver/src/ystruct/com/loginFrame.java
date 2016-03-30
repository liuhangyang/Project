package ystruct.com;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.*;
import org.jvnet.substance.theme.SubstanceSteelBlueTheme;
import org.jvnet.substance.title.Glass3DTitlePainter;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Created by yang on 16-3-17.
 */
public class loginFrame extends JFrame {
    private  String username="root";
    private  String  passwd="123456";
    private   String confim="A25S";
    private final JLabel name=new JLabel("欢迎时光影海管理系统",SwingConstants.CENTER);
    private Font font=new Font("微软雅黑", Font.BOLD,20);
    private JPanel contentPane;
    private BorderLayout boder=new BorderLayout();
    private Box horizontal=Box.createHorizontalBox();
    private JTextField Ufield;
    private JPasswordField Pfield;
    private JTextField Cfield;
    private JTextField Jfield;
    private JPanel UPjanel;
    public loginFrame(){
        name.setFont(new Font("微软雅黑",Font.BOLD,35));
        name.setForeground(Color.YELLOW);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(boder);
        contentPane.add(name,BorderLayout.NORTH);
       horizontal.add(Box.createVerticalStrut(5));
        UPjanel=new JPanel();
        UPjanel.setLayout(new BoxLayout(UPjanel,BoxLayout.PAGE_AXIS));
        //用户名框部分
        UPjanel.add(horizontal);
        JPanel Upanel=new JPanel();
        UPjanel.add(Upanel);
      //  contentPane.add(Upanel,BorderLayout.CENTER);
        JLabel Ulable=new JLabel("账 号");
        Ulable.setForeground(Color.GRAY);
        Ulable.setFont(font);
        Upanel.add(Ulable);
        Ufield=new JTextField();
        Ufield.setFont(font);
        Upanel.add(Ufield);
        Ufield.setColumns(10);
      //   UPjanel.add(horizontal);
        //密码框部分
        JPanel Ppasswd=new JPanel();
        Upanel.add(Ppasswd);
      //  contentPane.add(Ppasswd,BorderLayout.CENTER);
        JLabel Plable=new JLabel("密 码");
        Plable.setForeground(Color.gray);
        Plable.setFont(font);
        Ppasswd.add(Plable);
        Pfield=new JPasswordField();
        Ppasswd.setFont(font);
        Ppasswd.add(Pfield);
        Pfield.setColumns(11);
        //验证码
        JPanel confirm=new JPanel();
        UPjanel.add(confirm);
        JLabel Clable=new JLabel("验 证");
        Clable.setForeground(Color.gray);
        Clable.setFont(font);
        confirm.add(Clable);
        Cfield=new JTextField();
        confirm.setFont(font);
        confirm.add(Cfield);
        Cfield.setColumns(11);
        contentPane.add(UPjanel);
        //登录退出按钮
        JPanel buttonPanel=new JPanel();
        contentPane.add(buttonPanel,BorderLayout.SOUTH);
        JButton submitButton=new JButton("登录");
        JButton canelButton=new JButton("取消");
       // JButton registerButton=new JButton("注册");
        buttonPanel.add(submitButton);
        buttonPanel.add(canelButton);
        //buttonPanel.add(registerButton);
        submitButton.setFont(font);
        canelButton.setFont(font);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*System.out.print("cdcd");
                String gname=Ufield.getText();

                System.out.println("g:"+gname.length());
                String gpaswd=new String(Pfield.getPassword());
                String  gconfirm=Cfield.getText();
                System.out.println(gname+" "+gpaswd+" "+gconfirm);
                if((username.equals(gname) && passwd.equals(gpaswd))){
                    if(gconfirm.equals(confim)){
                            System.out.print("登录成功!");
                            dispose();
                             probar.start();
                            for(int i=0;i<=100;i++){
                                probar.jPM.setValue(i);
                            }
                            UserFrame user=new UserFrame();
                            user.setVisible(true);
                    }else{
                        System.out.println("验证码错误！");

                    }
                }else{
                    System.out.println("用户名或密码错误！");
                }*/
                dispose();
                UserFrame user=new UserFrame();
                user.setVisible(true);
            }
        });
        canelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
       /* registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });*/
        this.setSize(500,310);
        this.setResizable(false);

    }

    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes=new FontUIResource(font);
        for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();){
            Object key=keys.nextElement();
            Object value=UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
    public static void main(String[] args) {
        Font font=new Font("微软雅黑",Font.BOLD,18);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
               try{
                   UIManager.setLookAndFeel(new SubstanceLookAndFeel());
                   JFrame.setDefaultLookAndFeelDecorated(true);
                   JDialog.setDefaultLookAndFeelDecorated(true);
                   SubstanceLookAndFeel.setCurrentTheme(new SubstanceSteelBlueTheme());
                   SubstanceLookAndFeel.setSkin(new MangoSkin());
                   SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());
                   SubstanceLookAndFeel.setCurrentWatermark(new SubstanceBubblesWatermark());
                   SubstanceLookAndFeel.setCurrentBorderPainter(new StandardBorderPainter());
                   SubstanceLookAndFeel.setCurrentGradientPainter(new StandardGradientPainter());
                   SubstanceLookAndFeel.setCurrentTitlePainter(new Glass3DTitlePainter());
                   SubstanceLookAndFeel.setCurrentButtonShaper(new StandardButtonShaper());

               }catch (Exception e){
                   e.printStackTrace();
               }
                InitGlobalFont(font);
                loginFrame login=new loginFrame();
                login.setVisible(true);
            }
        });
    }
}
