import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;

public class Snowman extends JPanel {

    private static Random rand = new Random();

    public static void main(String[] args){
        new Snowman();
    }

    private JFrame frame;

    private Snowman(){
        this.frame = new JFrame("Snowman");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.add(this);
        this.frame.setSize(600, 400);
        this.frame.setVisible(true);
    }

    public void paintComponent(Graphics g){

        int height = this.getHeight();
        int width = this.getWidth();

        // All dimensions found using graph paper

        // Draw body
        g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        g.drawOval(width / 2 - 3 * height / 16, height / 2, 3 * height / 8, 3 * height / 8);
        g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        g.drawOval(width / 2 - height / 8, height / 4, height / 4, height / 4);
        g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        g.drawOval(width / 2 - height / 16, height / 8, height / 8, height / 8);

        // Draw eyes
        g.setColor(new Color(0,0,0));
        g.fillOval(width/2 - height/32, 11*height/64, height/64, height/64);
        g.fillOval(width/2 + height/64 , 11*height/64, height/64, height/64);

        // Draw arms
        g.drawLine(width/2 - height/16, 5*height/16, width/2 - height/16 - height/6, 5*height/16 - height/9);
        g.drawLine(width/2 + height/16, 5*height/16, width/2 + height/16 + height/6, 5*height/16 - height/9);

    }

}
