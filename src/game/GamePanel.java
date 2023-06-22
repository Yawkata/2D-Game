package game;

import entity.Player;
import world.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    public final int OBJECT_SIZE = 48; // 48x48 pixels
    public final int SCREEN_COL = 16;
    public final int SCREEN_ROW = 12;
    public final int SCREEN_WIDTH = SCREEN_COL * OBJECT_SIZE; // 768 pixels
    public final int SCREEN_HEIGHT = SCREEN_ROW * OBJECT_SIZE; // 576 pixels
    public final Camera camera;

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public final int worldWidth = OBJECT_SIZE * maxWorldCol;
    public final int worldHeight = OBJECT_SIZE * maxWorldRow;


    // FPS
    private static final int FPS = 60;
    private Thread gameThread;

    public CollisionChecker collisionChecker;
    public final Player player;
    public final KeyHandler keyHandler;
    public TileManager tileM;

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        collisionChecker = new CollisionChecker(this);
        player = new Player(this);
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        tileM = new TileManager(this);
        camera = new Camera();
    }

    public Camera getCamera() {
        return camera;
    }

    public void startGameThread() {
        gameThread = new Thread(this, "GameThread");
        gameThread.start();
    }

    @Override
    public void run() {
        double targetFrameTime = 1_000_000_000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            double elapsedTime = currentTime - lastTime;
            lastTime = currentTime;

            delta += elapsedTime / targetFrameTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    protected void update() {
        player.update();
        camera.setPosition(player.getX() - SCREEN_WIDTH / 2, player.getY() - SCREEN_HEIGHT / 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        tileM.draw(g2d);

        player.draw(this, g2d);


        g2d.dispose();
    }
}
