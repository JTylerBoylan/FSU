public class Boundary extends GameObject {

    public Boundary(int x, int y, int width, int height){
        super(x,y,width,height,"images/boundary.png");
    }

    @Override
    public void collision(GameObject OBJ){
        if (OBJ instanceof Bullet)
            OBJ.delete = true;
        if (OBJ instanceof Player) {
            Player player = (Player) OBJ;
            int colX = OBJ.bounds.intersection(this.bounds).width;
            int colY = OBJ.bounds.intersection(this.bounds).height;
            if (colX < colY) {

                if (player.facingRight){
                    OBJ.add(-colX-1, 0);
                    if (OBJ instanceof Player)
                        ((Player) OBJ).right = false;
                } else {
                    OBJ.add(colX+1, 0);
                    if (OBJ instanceof Player)
                        ((Player) OBJ).left = false;
                }

                player.setVelocityX(0);

            } else if (colX > colY) {

                if (player.getVelocityY() > 0){
                    OBJ.add(0, -colY+1);
                    if (OBJ instanceof Player)
                        ((Player) OBJ).ground = this;
                } else {
                    OBJ.add(0, colY+2);
                }

                player.setVelocityY(0);

            }
        }
    }

}
