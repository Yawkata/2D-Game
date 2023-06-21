// GamePanel class
package game;

import entity.Player;
import world.WorldGenerator;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final int OBJECT_SIZE = 48; // 48x48 pixels
    private final int SCREEN_COL = 16;
    private final int SCREEN_ROW = 12;
    private final int SCREEN_WIDTH = SCREEN_COL * OBJECT_SIZE; // 768 pixels
    private final int SCREEN_HEIGHT = SCREEN_ROW * OBJECT_SIZE; // 576 pixels

    private final int FPS = 60;

    private Thread gameThread;
    private final Player player;
    private final KeyHandler keyHandler;
    private final WorldGenerator worldGenerator;
    private final Camera camera;

    private Image grassTexture;
    private Image sandTexture;
    private Image treeTexture;

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        player = new Player(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);

        try {
            grassTexture = new ImageIcon(".\\assets\\world\\nature\\ground\\grass.png").getImage();
            sandTexture = new ImageIcon(".\\assets\\world\\nature\\ground\\sand.png").getImage();
            treeTexture = new ImageIcon(".\\assets\\world\\nature\\tree_types\\wide_leaf_tree.png").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        worldGenerator = new WorldGenerator();
        worldGenerator.loadWorldFromFile(".\\assets\\world\\world_textures_placement.txt");

        camera = new Camera(0, 0);
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
        player.update(keyHandler.isUpPressed(), keyHandler.isDownPressed(),
                keyHandler.isLeftPressed(), keyHandler.isRightPressed());
        camera.setPosition(player.getX() - SCREEN_WIDTH / 2, player.getY() - SCREEN_HEIGHT / 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        for (int row = 0; row < SCREEN_ROW; row++) {
            for (int col = 0; col < SCREEN_COL; col++) {
                int tileValue = worldGenerator.getTileValue(row, col);

                // Draw textures based on the tile value
                while (tileValue > 0) {
                    int lastDigit = tileValue % 10;

                    switch (lastDigit) {
                        case 1 ->
                            // Tree texture
                                g2d.drawImage(treeTexture, camera.translateX(col * OBJECT_SIZE), camera.translateY(row * OBJECT_SIZE), null);
                        case 2 ->
                            // Grass texture
                                g2d.drawImage(grassTexture, camera.translateX(col * OBJECT_SIZE), camera.translateY(row * OBJECT_SIZE), null);
                        case 3 ->
                            // Sand texture
                                g2d.drawImage(sandTexture, camera.translateX(col * OBJECT_SIZE), camera.translateY(row * OBJECT_SIZE), null);
                    }

                    tileValue /= 10;
                }
            }
        }

        player.draw(this, g2d);

        g2d.dispose();
    }
}
