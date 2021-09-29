import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JFrame;

import javax.imageio.ImageIO;

public class DungeonCrawlerGame extends JPanel implements Runnable {
    
    // frame properties
	public static final int WIDTH = 1024, HEIGHT = WIDTH / 16 * 9;
    public static final String title = "Dungeon Crawler";

    // game instance
    public static DungeonCrawlerGame game;

    public static void main(String[] args){

        game = new DungeonCrawlerGame();
        System.out.println("Starting " + title + "...");
        game.start();

    }

    private Thread thread;
    public Scene currentScene;
    private JFrame frame;
    public boolean win = false;

    public DungeonCrawlerGame() {

        this.frame = new JFrame(title);
        this.frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.frame.setMaximumSize(new Dimension(WIDTH,HEIGHT));
        this.frame.setMinimumSize(new Dimension(WIDTH,HEIGHT));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.add(this);
        this.frame.setVisible(true);

    }

    public synchronized void start() {
        this.currentScene = new Scene("level1");
        this.thread = new Thread(this);
        win = false;
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();  
            win = true;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double tps = 60.0; // ticks per second
		double ns = 1000000000 / tps;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (!win) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				currentScene.tick();
				delta--;
			}
            repaint();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

    @Override
    public void paint(Graphics g){
        super.paint(g);

        if (win){
            Image winImage = toImage("images/win.png");
            g.drawImage(winImage, 0, 0, WIDTH, HEIGHT, null);
        } else if (this.currentScene != null)
            this.currentScene.paint(g);
    }


    public Image toImage(String imageFilePath) {
        try {
            return ImageIO.read(this.getClass().getResourceAsStream(imageFilePath));
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}