public class Bullet extends GameObject implements GameObject.Dynamic {
    
    private int dx,dy;

    public boolean delete = false;

    public Bullet(int x, int y, int velX, int velY){
        super(x,y,10,10,"images/bullet.png");
        this.dx = velX;
        this.dy = velY;
    }

    @Override
    public void collision(GameObject OBJ){
        if (OBJ instanceof Mob)
            OBJ.delete = true;
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
