package entity;

import game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Zombie extends Entity {
    private static final int VISIBILITY_THRESHOLD = 200;
    private static final int TOLERANCE = 5;
    private static int MOVEMENT_FRAME_DELAY = 10;
    private static final int ATTACK_FRAME_DELAY = 10;

    private final List<Image> movementTextures;
    private final List<Image> attackTextures;
    private int movementAnimationFrame = 0;
    private int attackAnimationFrame = 0;
    private boolean isAttacking;
    private final GamePanel gp;
    private int targetX;
    private int targetY;

    public Zombie(GamePanel gamePanel) {
        super(1068, 1028, 3);
        this.gp = gamePanel;
        this.isAttacking = false;

        movementTextures = loadMovementTextures();
        attackTextures = loadAttackTextures();

        setRandomTarget();

        collisionArea = new Rectangle(9, 20, 30, 28);
    }

    public void update() {
        if (isPlayerVisible(gp.player)) {
            isAttacking = isNearPlayer(gp.player);
            speed = 3;
            MOVEMENT_FRAME_DELAY = 10;
            moveTowards(gp.player.getX(), gp.player.getY());
        } else {
            isAttacking = false;
            speed = 1;
            MOVEMENT_FRAME_DELAY = 30;
            wander();
        }
    }

    private boolean isPlayerVisible(Player player) {
        int dx = Math.abs(player.getX() - getX());
        int dy = Math.abs(player.getY() - getY());
        return dx <= VISIBILITY_THRESHOLD && dy <= VISIBILITY_THRESHOLD;
    }

    private boolean isNearPlayer(Player player) {
        int dx = Math.abs(player.getX() - x);
        int dy = Math.abs(player.getY() - y);
        return dx <= gp.OBJECT_SIZE / 2 && dy <= gp.OBJECT_SIZE / 2;
    }

    private void moveTowards(int targetX, int targetY) {
        int dx = Integer.compare(targetX, getX());
        int dy = Integer.compare(targetY, getY());

        int distanceX = Math.abs(targetX - getX());
        int distanceY = Math.abs(targetY - getY());

        int prevX = x;
        int prevY = y;

        if (distanceX > TOLERANCE && dx != 0) {
            moveHorizontally(dx);
        } else if (distanceY > TOLERANCE && dy != 0) {
            moveVertically(dy);
        }

        collisionOn = false;
        gp.collisionChecker.checkTile(this);

        if (collisionOn) {
            x = prevX;
            y = prevY;
        }
    }

    private void wander() {
        if (reachedTarget()) {
            setRandomTarget();
        }

        moveTowards(targetX, targetY);
    }

    private void moveHorizontally(int dx) {
        if (dx < 0) {
            moveLeft();
            direction = "left";
        } else if (dx > 0) {
            moveRight();
            direction = "right";
        }
    }

    private void moveVertically(int dy) {
        if (dy < 0) {
            moveUp();
        } else {
            moveDown();
        }
    }

    private boolean reachedTarget() {
        return Math.abs(getX() - targetX) <= TOLERANCE && Math.abs(getY() - targetY) <= TOLERANCE;
    }

    private void setRandomTarget() {
        targetX = getRandomInRange(getX() - 100, getX() + 100);
        targetY = getRandomInRange(getY() - 100, getY() + 100);
    }

    private int getRandomInRange(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    private List<Image> loadMovementTextures() {
        return new ArrayList<>(Arrays.asList(
                new ImageIcon(".\\assets\\zombie\\animation\\movement\\moving_left\\step_1.png").getImage(),
                new ImageIcon(".\\assets\\zombie\\animation\\movement\\moving_left\\step_2.png").getImage(),
                new ImageIcon(".\\assets\\zombie\\animation\\movement\\moving_left\\step_3.png").getImage(),
                new ImageIcon(".\\assets\\zombie\\animation\\movement\\moving_left\\idle.png").getImage(),
                new ImageIcon(".\\assets\\zombie\\animation\\movement\\moving_right\\step_1.png").getImage(),
                new ImageIcon(".\\assets\\zombie\\animation\\movement\\moving_right\\step_2.png").getImage(),
                new ImageIcon(".\\assets\\zombie\\animation\\movement\\moving_right\\step_3.png").getImage(),
                new ImageIcon(".\\assets\\zombie\\animation\\movement\\moving_right\\idle.png").getImage()
        ));
    }

    private List<Image> loadAttackTextures() {
        return new ArrayList<>(Arrays.asList(
                new ImageIcon(".\\assets\\zombie\\animation\\attack\\left\\part_1.png").getImage(),
                new ImageIcon(".\\assets\\zombie\\animation\\attack\\left\\part_2.png").getImage(),
                new ImageIcon(".\\assets\\zombie\\animation\\attack\\left\\part_3.png").getImage(),
                new ImageIcon(".\\assets\\zombie\\animation\\attack\\right\\part_1.png").getImage(),
                new ImageIcon(".\\assets\\zombie\\animation\\attack\\right\\part_2.png").getImage(),
                new ImageIcon(".\\assets\\zombie\\animation\\attack\\right\\part_3.png").getImage()
        ));
    }

    public void draw(Graphics2D g2d) {
        Image currentTexture;

        if (isAttacking) {
            if (direction.equals("left")) {
                currentTexture = attackTextures.get((attackAnimationFrame / ATTACK_FRAME_DELAY) % 3);
            } else {
                currentTexture = attackTextures.get(3 + ((attackAnimationFrame / ATTACK_FRAME_DELAY) % 3));
            }
            attackAnimationFrame = (attackAnimationFrame + 1) % (ATTACK_FRAME_DELAY * 6);
        } else {
            List<Image> currentMovementTextures = getMovementTexturesForDirection(direction);
            currentTexture = currentMovementTextures.get((movementAnimationFrame / MOVEMENT_FRAME_DELAY) % currentMovementTextures.size());
            movementAnimationFrame = (movementAnimationFrame + 1) % (MOVEMENT_FRAME_DELAY * currentMovementTextures.size());
        }

        g2d.drawImage(currentTexture, gp.getCamera().translateX(getX()) - gp.OBJECT_SIZE / 2,
                gp.getCamera().translateY(getY()) - gp.OBJECT_SIZE / 2, gp.OBJECT_SIZE, gp.OBJECT_SIZE, null);
    }

    private List<Image> getMovementTexturesForDirection(String direction) {
        if (direction.equals("left")) {
            return movementTextures.subList(0, 4);
        } else if (direction.equals("right")) {
            return movementTextures.subList(4, 8);
        }

        return movementTextures;
    }
}
