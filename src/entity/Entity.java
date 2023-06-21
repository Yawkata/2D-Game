package entity;

public class Entity {
    protected int x;
    protected int y;
    protected int speed;
    public Entity(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected void moveUp() {
        y -= speed;
    }

    protected void moveDown() {
        y += speed;
    }

    protected void moveLeft() {
        x -= speed;
    }

    protected void moveRight() {
        x += speed;
    }
}
