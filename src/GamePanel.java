import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    private final int objectSize = 48; // 48X48 pixels
    private final int screenCol = 16;
    private final int screenRow = 12;
    private final int screenWidth = screenCol * objectSize; // 768 pixels
    private final int screenHeight = screenRow * objectSize; // 576 pixels

    final int FPS = 60;
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(100, 100);

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

    @Override
    public void run() {
        double drawInterval = (float)1000000000/FPS;
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
        player.update(keyHandler.isUpPressed(), keyHandler.isDownPressed(), keyHandler.isLeftPressed(), keyHandler.isRightPressed());
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(player.getTextures().get(player.getCurrentImageIndex()), player.getX(), player.getY(), null);

        g2.dispose();
    }
}
