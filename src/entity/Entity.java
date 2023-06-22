package entity;

import java.awt.*;

public class Entity {
    protected int x;
    protected int y;
    public int speed;

    public String direction;

    public Rectangle collisionArea;
    public boolean collisionOn = false;
    public Entity(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        direction = "down";

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
