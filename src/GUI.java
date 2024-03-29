/**
 *
 * GUI
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.Random;
import java.io.*;
import javax.imageio.*;
public class GUI extends JFrame {
    private JPanel panel1,titlePanel,colorGrid,mainGrid, buttonSouth;
    private JPanel white, yellow, green, orange, blue, red;
    private DicePaint box1,box2,box3;
    private JButton rollButton, quitButton;
    private Timer time;
    private JLabel timeLable, title;
    private int t;
    private JTextField tfw,tfy,tfg,tfo,tfb,tfr;
    private TableScore ts;
    private Player thisPlayer = new Player();
    private Bot bot1;
    private Bot bot2;
    private Bot bot3;
    private Bot bot4;
    private Bot bot5;
    private Database db;
    private TableScore tb;
    
    public GUI(String playerName, Database db){
        
        this.db = db;
        rollButton= new JButton("Roll");
            rollButton.addActionListener(new rollAction());
        quitButton = new JButton("Quit");
            quitButton.addActionListener(new quitAction());
        panel1 = new JPanel(new GridLayout(1,3));
        titlePanel= new JPanel();
        colorGrid = new JPanel(new GridLayout(2,3));
        mainGrid=new JPanel(new GridLayout(2,1));
        buttonSouth = new JPanel();
        box1 = new DicePaint();
        box2=new DicePaint();
        box3= new DicePaint();
        time= new Timer(100,new TimerAction());
        timeLable = new JLabel("Seconds");
        title = new JLabel("Game Color");
            title.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        white = new JPanel();
            white.setBackground(Color.WHITE);
            white.setBorder(BorderFactory.createLineBorder(Color.black));
        yellow = new JPanel();
            yellow.setBackground(Color.YELLOW);
        green = new JPanel();
            green.setBackground(Color.GREEN);
        orange = new JPanel();
            orange.setBackground(Color.ORANGE);
        blue = new JPanel();
            blue.setBackground(Color.BLUE);
        red = new JPanel();
            red.setBackground(Color.RED);
        tfw = new JTextField("", 20);
        tfy = new JTextField("",20);
        tfg = new JTextField("",20);
        tfo = new JTextField("",20);
        tfb = new JTextField("",20);
        tfr = new JTextField("",20); 

        add(titlePanel, "North");
            titlePanel.add(title);
        add(mainGrid, "Center");
            mainGrid.add(panel1, "Center");
                panel1.add(box1);
                panel1.add(box2);
                panel1.add(box3);
            mainGrid.add(colorGrid);
                colorGrid.add(white);
                    white.add(tfw, "Center");
                colorGrid.add(yellow);
                    yellow.add(tfy, "Center");
                colorGrid.add(green);
                    green.add(tfg, "Center");
                colorGrid.add(orange);
                    orange.add(tfo, "Center");
                colorGrid.add(blue);
                    blue.add(tfb, "Center");
                colorGrid.add(red);
                    red.add(tfr, "Center");
        add(buttonSouth, "South");
            buttonSouth.add(rollButton);
            buttonSouth.add(timeLable);
            buttonSouth.add(quitButton);
        setSize(1024,768);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        thisPlayer.setPlayerName(playerName);

        bot1 = new Bot();
        bot2 = new Bot();
        bot3 = new Bot();
        bot4 = new Bot();
        bot5 = new Bot();
        
        tb = new TableScore(thisPlayer,bot1,bot2,bot3,bot4,bot5);
        thisPlayer.addObserver(tb);
        bot1.addObserver(tb);
        bot2.addObserver(tb);
        bot3.addObserver(tb);
        bot4.addObserver(tb);
        bot5.addObserver(tb);
    }

    private class TimerAction implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            box1.repaint();
            box2.repaint();
            box3.repaint();
            timeLable.setText(Integer.toString(t/10));
            labeler();

            if(t==0){
                rollButton.setText("Roll");
                thisPlayer.setColorsBet(tfw.getText(), tfy.getText(), tfg.getText(), tfo.getText(), tfb.getText(), tfr.getText());
                thisPlayer.wonGamble(box1.getBoxColor(), box2.getBoxColor(), box3.getBoxColor());
                botAuto();
            }
        }
    }
    private void labeler(){
        if(t==0){
            time.stop();
            rollButton.setText("Roll");
        }
        t-=1;
    }

    //Changes button label and resets time
    private class rollAction implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            if(time.isRunning()){
                time.stop();
                rollButton.setText("Roll");
                thisPlayer.setColorsBet(tfw.getText(), tfy.getText(), tfg.getText(), tfo.getText(), tfb.getText(), tfr.getText());
                thisPlayer.wonGamble(box1.getBoxColor(), box2.getBoxColor(), box3.getBoxColor());
                botAuto();
            }else{
                t=100;
                time.start();
                rollButton.setText("Stop");    
            }
            
        }
    }

    public void botAuto(){
        bot1.botBet();
        bot2.botBet();
        bot3.botBet();
        bot4.botBet();
        bot5.botBet();
        bot1.wonGamble(box1.getBoxColor(), box2.getBoxColor(), box3.getBoxColor());
        bot2.wonGamble(box1.getBoxColor(), box2.getBoxColor(), box3.getBoxColor());
        bot3.wonGamble(box1.getBoxColor(), box2.getBoxColor(), box3.getBoxColor());
        bot4.wonGamble(box1.getBoxColor(), box2.getBoxColor(), box3.getBoxColor());
        bot5.wonGamble(box1.getBoxColor(), box2.getBoxColor(), box3.getBoxColor());
    }

    private class quitAction implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            db.executeCommands(thisPlayer);
            setVisible(false);
            dispose();
            tb.setVisible(false);
            tb.dispose();
            MainMenu f = new MainMenu();
        }
    }

    
    
    public static void main(String[] args) {
        MainMenu t = new MainMenu();
    }

}