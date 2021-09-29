import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.event.*;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ScreenSaver extends JPanel implements ActionListener {
    
    private static int circles = 50;
    public static void main(String[] args){
       new ScreenSaver();
    }

    private int delay = 1000;
    private JFrame frame;
    private Timer timer;

    private ScreenSaver(){
        this.frame = new JFrame("ScreenSaver");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.add(this);
        this.frame.setSize(800, 600);
        this.frame.setVisible(true);
        this.timer = new Timer(delay,this);
        this.timer.start();
    }

    public void actionPerformed(ActionEvent e){
        this.repaint();
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;

        Random rand = new Random();

        int height = this.getHeight();
        int width = this.getWidth();

        g2D.clearRect(0, 0, width, height);

        for (int i = 0; i < circles; i++) {

            Color col = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            int thic = rand.nextInt(10)+1;
            int tall = rand.nextInt(height);
            int fat = rand.nextInt(width);

            int boundX = width - fat;
            int boundY = height - tall;

            int posX = rand.nextInt(boundX);
            int posY = rand.nextInt(boundY);

            g2D.setColor(col);
            g2D.setStroke(new BasicStroke(thic));
            g2D.drawOval(posX, posY, fat, tall);


        }

    }


}
