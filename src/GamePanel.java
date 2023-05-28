import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    final int objectSize = 48; // 48X48 pixels
    final int screenCol = 16;
    final int screenRow = 12;
    final int screenWidth = screenCol * objectSize; // 768 pixels
    final int screenHeight = screenRow * objectSize; // 576 pixels

    final int FPS = 60;
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        //this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this, "GameThread");
        gameThread.start();
    }
    /*
    //@Override
    public void run2() {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {
            update();

            repaint();

            double remainingTime = nextDrawTime - System.nanoTime();
            try {
                Thread.sleep((long) remainingTime / 1000000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            nextDrawTime += drawInterval;

        }
    }
    */

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1){
                update();

                repaint();

                delta--;
            }
        }
    }

    public void update(){
        if(keyHandler.upPressed){
            playerY -= playerSpeed;
        } else if (keyHandler.downPressed) {
            playerY += playerSpeed;
        } else if (keyHandler.leftPressed) {
            playerX -= playerSpeed;
        } else if (keyHandler.rightPressed) {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, objectSize, objectSize);
        g2.dispose();
    }
}
