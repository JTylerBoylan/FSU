

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Player extends GameObject implements GameObject.Dynamic, KeyListener {

    public final int movement_speed = 10;
    public final int jump_strength = 25;

    private int dx,dy;
    public Boundary ground;

    public boolean facingRight = true;
    public boolean left = false, right = false, jump = false;
    public boolean dead = false, attack = false, treasure = false;

    public Player(int x, int y) {
        super(x,y,50,100,"images/player.png");
        this.dx = 0;
        this.dy = 0;
        this.ground = null;
    }

    public boolean onGround() {
        if (this.ground != null) {
            if (this.bounds.intersects(this.ground.bounds))
                return true;
            else
                this.ground = null;
        }
        return false;
    }

    public void collision(GameObject OBJ) {

        if (OBJ instanceof Treasure)
            this.treasure = true;

    }

    public int getVelocityX() {
        if (this.left)
            this.dx = -this.movement_speed;
        else if (this.right)
            this.dx = this.movement_speed;
        else
            this.dx = 0;
        return this.dx;
    }

    public int getVelocityY() {
        if (this.jump && this.onGround())
            this.dy -= this.jump_strength;
        return this.dy;
    }

    public boolean obeysGravity() {
        return true;
    }

    public void setVelocityX(int DX) {
        this.dx = DX;
    }

    public void setVelocityY(int DY) {
        this.dy = DY;
    }

    public void keyTyped(KeyEvent e) {
        int key = (int) e.getKeyChar();
        switch (key){
            case 65:
            case 97:
                this.facingRight = false;
                this.left = true;
                this.right = false;
                break;
            case 68:
            case 100:
                this.facingRight = true;
                this.left = false;
                this.right = true;
                break;
            case 87:
            case 119:
                this.jump = true;
                break;
            case 32:
                this.attack = true;
                break;
        }
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();
        switch (key){
            case 'a':
            case 'A':
                this.left = false;
                break;
            case 'd':
            case 'D':
                this.right = false;
                break;
            case 'w':
            case 'W':
                this.jump = false;
                break;
        }
    }
    
    public void kill(){
        this.dead = true;
    }

    public void attack(){
        this.attack = true;
    }

}
