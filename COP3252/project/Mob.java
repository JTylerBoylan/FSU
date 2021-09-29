public class Mob extends GameObject implements GameObject.Dynamic {

    public final String imageFilePath = "mob.png";

    public final int movement_speed = 10;

    private int dx,dy;

    public Mob(int x, int y) {
        super(x,y,50,100,"images/mob.png");
        this.dx = 0;
        this.dy = 0;
    }

    public void collision(GameObject OBJ) {

        if (OBJ instanceof Player)
            ((Player) OBJ).kill();

        if (OBJ instanceof Bullet)
            ((Bullet) OBJ).delete = true;

    }

    public int getVelocityX() {
        return this.dx;
    }

    public int getVelocityY() {
        return this.dy;
    }

    public void setVelocityX(int DX) {
        this.dx = DX;
    }

    public void setVelocityY(int DY) {
        this.dy = DY;
    }

    public boolean obeysGravity() {
        return false;
    }

}
