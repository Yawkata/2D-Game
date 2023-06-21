package game;

public class Camera {
    private int x;
    private int y;

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int translateX(int x) {
        return x - this.x;
    }

    public int translateY(int y) {
        return y - this.y;
    }
}
