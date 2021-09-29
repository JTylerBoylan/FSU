import java.awt.Image;
import java.awt.Rectangle;

public class GameObject {

    public int width, height;
    public Rectangle bounds;
    public Image image;
    public boolean delete;

    public GameObject(int x, int y, int width, int height, String imageFilePath) {
        this.bounds = new Rectangle(x,y,width,height);
        this.width = width;
        this.height = height;
        this.image = DungeonCrawlerGame.game.toImage(imageFilePath);
        this.delete = false;
    }

    public Image getImage() {
        return this.image;
    } 
    public void collision(GameObject OBJ) {
        
    }

    public int getX() {
        return this.bounds.x;
    }

    public int getY() {
        return this.bounds.y;
    }

    public void add(int X, int Y) {
        this.bounds.setLocation(this.getX() + X, this.getY() + Y);
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public void setBounds(Rectangle BODY) {
        this.bounds = BODY;
    }

    public interface Dynamic {

        public int getVelocityX();

        public int getVelocityY();

        public void setVelocityX(int DX);

        public void setVelocityY(int DY);

        public boolean obeysGravity();

    }

}
