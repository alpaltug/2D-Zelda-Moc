package org.alpaltug.game;

import org.alpaltug.entity.Player;
import org.alpaltug.object.SuperObject;
import org.alpaltug.tile.Tile;
import org.alpaltug.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile (standard for many retro 2D games)
    final int scale = 3; // scale up the characters (standard scale for retro games)

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();

    // SOUND
    Sound music = new Sound();
    Sound se = new Sound();

    // Collision Checker
    public CollisionChecker collisionChecker = new CollisionChecker(this);

    // Asset Setter
    public AssetSetter aSetter = new AssetSetter(this);

    // GAME UI (TEXTS)
    public UI ui = new UI(this);

    // GAME THREAD
    Thread gameThread;

    // Entity
    public Player player = new Player(this, keyH);

    // Objects
    public SuperObject obj[] = new SuperObject[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // Places objects using ObjectSetter+
    public void setupGame() {
        aSetter.setObject();

        playMusic(0);
    }

    // Starts up async game thread
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS; // nanoseconds per frame
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // Define variables to track FPS
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                drawCount++;
                delta--;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        // X increases as we go right
        // Y increases as we go down
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // TILE
        tileM.draw(g2);

        // OBJECT
        for (int i = 0; i < obj.length; i++) {
            if(obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        // PLAYER
        player.draw(g2);

        // UI
        ui.draw(g2);

        g2.dispose(); // free up memory after done using g2
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {

        music.stop();
    }
    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }
}
