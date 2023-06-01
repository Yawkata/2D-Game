import javax.swing.JFrame;

public class Window extends JFrame{
    public Window(){
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gameScreen = new GamePanel();
        add(gameScreen);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        gameScreen.startGameThread();
    }
}
