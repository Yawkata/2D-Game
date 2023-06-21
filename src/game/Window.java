package game;

import javax.swing.JFrame;

public class Window extends JFrame {
    public Window() {
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gameScreen = new GamePanel();
        add(gameScreen);
        pack();
        setLocationRelativeTo(null); // set the window in the center of the screen
        setVisible(true);
        setResizable(false);
        gameScreen.startGameThread();
    }
}
