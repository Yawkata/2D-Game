import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Game");

        GamePanel gameScreen = new GamePanel();
        window.add(gameScreen);

        window.pack();

        window.setLocationRelativeTo(null); // set the window in the center of the screen
        window.setVisible(true);

        gameScreen.startGameThread();
    }


}