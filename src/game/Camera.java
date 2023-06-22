package game;

public class Camera {
    private int x;
    private int y;

    public Camera() {
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
