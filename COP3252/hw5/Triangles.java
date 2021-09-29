import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.Random;

public class Triangles extends JPanel {

    // STATIC
    private static final int triangles = 5;

    public static void main(String[] args) {
        new Triangles();
    }

    // MEMBER

    private JFrame frame;
    private Random rand;

    private Triangles() {
        this.frame = new JFrame("Triangles");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(500, 500);
        this.frame.add(this);
        this.frame.setVisible(true);
        this.rand = new Random();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        for (int i = 0; i < triangles; i++) {
            
            GeneralPath triangle = new GeneralPath();

            triangle.moveTo(rand.nextInt(this.getWidth()),rand.nextInt(this.getHeight()));
            triangle.lineTo(rand.nextInt(this.getWidth()),rand.nextInt(this.getHeight()));
            triangle.lineTo(rand.nextInt(this.getWidth()),rand.nextInt(this.getHeight()));
            triangle.closePath();

            g2D.setColor(new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)));

            g2D.fill(triangle);

        }

    }
    

}