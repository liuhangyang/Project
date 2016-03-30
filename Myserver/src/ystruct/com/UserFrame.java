package ystruct.com;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.skin.*;
import org.jvnet.substance.theme.SubstanceBottleGreenTheme;
import org.jvnet.substance.theme.SubstanceDesertSandTheme;
import org.jvnet.substance.watermark.SubstanceWoodWatermark;

import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;


/**
 * Created by yang on 16-3-17.
 */
public class UserFrame extends JFrame {

    //菜单栏
    JPanel JmenuBar;
    JLabel filmadd;
    JLabel actadd;
    JLabel seatmage;
    JLabel help;
    JLabel searchcount;
    JLabel peopleregister;
    JLabel myself;
    //所有的卡片
    JPanel cardfilmadd;
    JPanel cardactadd;
    JPanel cardseatmage;
    JPanel cardhelp;
    JPanel cardsearchcount;
    JPanel cardpeopleregister;
    JPanel cardmyself;
    JButton Enter;
    JButton ScreenCapture;
    JTextField TextField;
    JPanel ButtonPanel;
    JPanel pane;  //最大的panel;
    JPanel cardPanel;   //容纳panel的卡片
    public  JLabel clickmenu;
    CardLayout card;
    public UserFrame(){
        setSize(1000,700);
        Container c=getContentPane();
        pane=new JPanel(new BorderLayout());
        c.add(pane);
        JmenuBar=returnmenubar(); //菜单栏
        pane.add(JmenuBar,BorderLayout.NORTH);
        filmadd.addMouseListener(new mymouseadapter());
        actadd.addMouseListener(new mymouseadapter());
        seatmage.addMouseListener(new mymouseadapter());
        help.addMouseListener(new mymouseadapter());
        searchcount.addMouseListener(new mymouseadapter());
        peopleregister.addMouseListener(new mymouseadapter());
        myself.addMouseListener(new mymouseadapter());
        cardPanel=new JPanel();
        card=new CardLayout();
        cardPanel.setLayout(card);
        pane.add(cardPanel,BorderLayout.CENTER);
        //第一张卡片

        cardfilmadd=new firstJpanel().returnfilm();
        cardPanel.add(cardfilmadd);
        //第二张卡片
        cardactadd=new secondJpaenl().returnactadd();
        cardPanel.add(cardactadd);

        //第三张卡片
        cardseatmage=new thirdPanel().returnthirdPanel();
        cardPanel.add(cardseatmage);

        //第四张卡片
        cardsearchcount=new fourJpanel().returnfouthPanel();
        cardPanel.add(cardsearchcount);
        //低五张卡片
        cardpeopleregister=new secondJpaenl().returnactadd();
        cardPanel.add(cardpeopleregister);
        //第六张卡片
        cardmyself=new secondJpaenl().returnactadd();
        cardPanel.add(cardmyself);
        //第七张卡片
        cardhelp=new secondJpaenl().returnactadd();
        cardPanel.add(cardhelp);

        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                SubstanceLookAndFeel.setSkin(new FieldOfWheatSkin());
                SubstanceLookAndFeel.setCurrentWatermark(new SubstanceWoodWatermark());
                SubstanceLookAndFeel.setCurrentTheme(new SubstanceBottleGreenTheme());
                SubstanceLookAndFeel.setCurrentButtonShaper(new StandardButtonShaper());
            }
        });
        /******************************界面设计模块****************************/

        /******************************事件点击模块****************************/


    }
    public JPanel returnmenubar(){
        JmenuBar = new JPanel();
        JmenuBar.setLayout(new FlowLayout(FlowLayout.LEFT,30,5));
        JmenuBar.setBackground(Color.GRAY);
        filmadd=new JLabel("影片管理");
        actadd=new JLabel("演出管理");
        seatmage=new JLabel("座位管理");
        help=new JLabel("帮助");
        searchcount=new JLabel("查询统计");
        peopleregister=new JLabel("会员信息");
        myself=new JLabel("个人中心");
        JmenuBar.add(filmadd);
        JmenuBar.add(actadd);
        JmenuBar.add(seatmage);
        JmenuBar.add(searchcount);
        JmenuBar.add(peopleregister);
        JmenuBar.add(myself);
        JmenuBar.add(help);
        return  JmenuBar;
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


    //处理JLable菜单栏的点击事件;
    class mymouseadapter extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            clickmenu = (JLabel) e.getSource();
            if (clickmenu == filmadd) {
                filmadd.setForeground(Color.BLUE);
                card.first(cardPanel);
                for (int i = 0; i < 0; i++) {
                    card.next(cardPanel);
                }

            } else if (clickmenu == actadd) {
                System.out.print("vm,fk");
                actadd.setForeground(Color.BLUE);
                card.first(cardPanel);
                for (int i = 0; i < 1; i++) {
                    card.next(cardPanel);
                }
            }else if(clickmenu==seatmage){
                seatmage.setForeground(Color.BLUE);
                card.first(cardPanel);
                for (int i = 0; i < 2; i++) {
                    card.next(cardPanel);
                }
            }else if (clickmenu == searchcount) {
                searchcount.setForeground(Color.BLUE);
                card.first(cardPanel);
                for (int i = 0; i < 3; i++) {
                    card.next(cardPanel);
                }
            } else if (clickmenu == peopleregister) {
                peopleregister.setForeground(Color.BLUE);
                card.first(cardPanel);
                for (int i = 0; i < 4; i++) {
                    card.next(cardPanel);
                }
            } else if (clickmenu == myself) {
                myself.setForeground(Color.BLUE);
                card.first(cardPanel);
                for (int i = 0; i < 5; i++) {
                    card.next(cardPanel);
                }
            } else if (clickmenu == help) {
                help.setForeground(Color.BLUE);
                card.first(cardPanel);
                for (int i = 0; i < 6; i++) {
                    card.next(cardPanel);
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            filmadd.setForeground(Color.BLACK);
            actadd.setForeground(Color.BLACK);
            searchcount.setForeground(Color.BLACK);
            help.setForeground(Color.BLACK);
            peopleregister.setForeground(Color.BLACK);
            myself.setForeground(Color.BLACK);
            seatmage.setForeground(Color.BLACK);

        }
    }
}
class mylisten implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
