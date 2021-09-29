import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Scene {

    private final int gravity = 1; // pixels/tick^2

    private String levelName;

    private DataFile dataFile;

    private int levelNum;
    private String nextScene;
    private Image background;
    private Rectangle bounds;
    private ArrayList<GameObject> gameObjects;
    private Player player;
    private Camera camera;

    public Scene(String level){
        this.levelName = level;
        this.dataFile = new DataFile("scenes/" + level + ".txt");
        this.levelNum = toInteger(this.dataFile.getStringData("level"));
        this.nextScene = this.dataFile.getStringData("next");
        this.background = DungeonCrawlerGame.game.toImage(this.dataFile.getStringData("background"));
        String[] boundData = this.dataFile.getStringData("bounds").split(" ");
        this.bounds = new Rectangle(
            toInteger(boundData[0]),
            toInteger(boundData[1]),
            toInteger(boundData[2]),
            toInteger(boundData[3]));
        this.gameObjects = new ArrayList<GameObject>();
        this.player = new Player(0,0);
        this.camera = new Camera(player);

        load();
    }

    public void addGameObject(GameObject obj) {
        this.gameObjects.add(obj);

        if (obj instanceof KeyListener)
            DungeonCrawlerGame.game.addKeyListener((KeyListener) obj);

    }

    public void removeGameObject(GameObject obj) {
        this.gameObjects.remove(obj);
    }

    private void spawnPlayer(){
        String[] playerSpawnData = this.dataFile.getStringData("player-spawn").split(" ");
        int playerSpawnX = toInteger(playerSpawnData[0]);
        int playerSpawnY = toInteger(playerSpawnData[1]);
        this.player.add(playerSpawnX, playerSpawnY);
        this.addGameObject(player);
    }

    private void spawnBoundaries() {
        List<String> bounds = this.dataFile.getStringListData("boundaries");
        for (String bound : bounds) {
            String[] boundData = bound.split(" ");
            int boundX = toInteger(boundData[0]);
            int boundY = toInteger(boundData[1]);
            int boundW = toInteger(boundData[2]);
            int boundH = toInteger(boundData[3]);
            this.addGameObject(new Boundary(boundX, boundY, boundW, boundH));
        }
    }

    private void spawnMobs() {
        List<String> mobs = this.dataFile.getStringListData("mobs");
        for (String mob : mobs) {
            String[] mobData = mob.split(" ");
            int mobX = toInteger(mobData[0]);
            int mobY = toInteger(mobData[1]);
            this.addGameObject(new Mob(mobX,mobY));
        }
    }

    private void spawnTreasure() {
        String[] treasureData = this.dataFile.getStringData("treasure").split(" ");
        int treasureX = toInteger(treasureData[0]);
        int treasureY = toInteger(treasureData[1]);
        this.addGameObject(new Treasure(treasureX, treasureY));
    }

    // Runs on first load up
    private void load() {
        this.spawnBoundaries();
        this.spawnPlayer();
        this.spawnMobs();
        this.spawnTreasure();
    }

    // Runs every frame refresh
    public void tick() {

        // Check completed
        if (this.player.treasure){
            if (!this.nextScene.equals("none"))
                DungeonCrawlerGame.game.currentScene = new Scene(this.nextScene);
            else
                DungeonCrawlerGame.game.win = true;
            return;
        }

        ArrayList<GameObject> delete = new ArrayList<GameObject>();

        for (GameObject gameObject : gameObjects) {

            // Update positions
            if (gameObject instanceof GameObject.Dynamic) {
                GameObject.Dynamic dynamicObject = (GameObject.Dynamic) gameObject;
                gameObject.add(dynamicObject.getVelocityX(), dynamicObject.getVelocityY());
                if (dynamicObject.obeysGravity())
                    dynamicObject.setVelocityY(dynamicObject.getVelocityY() + gravity);
            }

            // Collisions
            for (GameObject gameObject2 : gameObjects)
                if (gameObject != gameObject2)
                    if (gameObject.getBounds().intersects(gameObject2.getBounds()))
                        gameObject.collision(gameObject2);

            // Deletions
            if (gameObject.delete == true)
                delete.add(gameObject);

        }

        // Remove deleted game objects
        for (GameObject del : delete)
            this.removeGameObject(del);

        // Move Camera
        if (this.camera.player != null){
            this.camera.x = this.camera.player.getX() + (this.camera.player.width - DungeonCrawlerGame.WIDTH)/2;
            this.camera.y = this.camera.player.getY() + 3*(this.camera.player.height - DungeonCrawlerGame.HEIGHT)/5;
        }

        // Reset
        if (!this.player.getBounds().intersects(this.bounds) || this.player.dead ){
            DungeonCrawlerGame.game.currentScene = new Scene(levelName);
        }

        // Attack
        if (this.player.attack){
            this.addGameObject(new Bullet(
                this.player.getX() + this.player.width/2,
                this.player.getY() + this.player.height/2,
                this.player.facingRight ? 20 : -20,
                0));
            this.player.attack = false;
        }

    }

    public void paint(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.BLACK);
        g2D.fillRect(0, 0, 1000, 1000);

        // Draw background
        g2D.drawImage(background, 0, 0, null);

        // Draw all game objects
        int length = this.gameObjects.size();
        for (int i = 0; i < length; i++){
            GameObject gameObject;
            try {
                gameObject = this.gameObjects.get(i);
            } catch (IndexOutOfBoundsException e){
                continue;
            }
            if (gameObject.getImage() != null)
                g2D.drawImage(gameObject.getImage(), gameObject.getX() - camera.x, gameObject.getY() - camera.y, gameObject.width, gameObject.height, null);
        }

        g2D.drawRect(this.bounds.x - this.camera.x, this.bounds.y - this.camera.y, this.bounds.width, this.bounds.height);
        
        g2D.drawString("Level " + this.levelNum, 30, 30);
        g2D.drawString("Jump: [W] \t Left: [A] \t Right: [D] \t Shoot: [SPACE]", 1024-330, 30);

        // Dispose of graphics
        g2D.dispose();
    }

    // Camera class
    public class Camera {

        public Player player;
        public int x,y;

        public Camera(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Camera(Player player) {
            this.player = player;
        }

    }

    public static int toInteger(String str){
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e){
            return 0;
        }
    }

}
