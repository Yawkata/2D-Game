package entity;

import game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Zombie extends Entity {
    private final ArrayList<Image> textures = new ArrayList<>(Arrays.asList(
            // Movement textures
            new ImageIcon("./assets/zombie/animation/movement/moving_left/first_step.png").getImage(),
            new ImageIcon("./assets/zombie/animation/movement/moving_left/second_step.png").getImage(),
            new ImageIcon("./assets/zombie/animation/movement/moving_left/third_step.png").getImage(),
            new ImageIcon("./assets/zombie/animation/movement/moving_left/idle.png").getImage(),
            new ImageIcon("./assets/zombie/animation/movement/moving_right/first_step.png").getImage(),
            new ImageIcon("./assets/zombie/animation/movement/moving_right/second_step.png").getImage(),
            new ImageIcon("./assets/zombie/animation/movement/moving_right/third_step.png").getImage(),
            new ImageIcon("./assets/zombie/animation/movement/moving_right/idle.png").getImage(),
            // Attack textures
            new ImageIcon("./assets/zombie/animation/attack/left/first_part.png").getImage(),
            new ImageIcon("./assets/zombie/animation/attack/left/second_part.png").getImage(),
            new ImageIcon("./assets/zombie/animation/attack/left/third_part.png").getImage(),
            new ImageIcon("./assets/zombie/animation/attack/right/first_part.png").getImage(),
            new ImageIcon("./assets/zombie/animation/attack/right/second_part.png").getImage(),
            new ImageIcon("./assets/zombie/animation/attack/right/third_part.png").getImage()
    ));

    private String direction;
    private int currentImageIndex;
    private int frameCounter;
    private boolean isChasing;

    private final GamePanel gp;

    private boolean isNearPlayer = false;

    private int targetX;
    private int targetY;

    public Zombie(GamePanel gp) {
        super(1068, 1028, 3);
        this.gp = gp;
        currentImageIndex = 3; // Set idle state for initial image
        direction = "left";
        frameCounter = 0;
        isChasing = false;
        targetX = getRandomX();
        targetY = getRandomY();
        collisionArea = new Rectangle(9, 20, 30, 28);
    }

    public void update() {
        // Check visibility and determine whether to chase the player
        isChasing = checkPlayerVisibility();

        if (isChasing) {
            // Calculate player's position relative to the zombie
            int dx = gp.player.getX() - x;
            int dy = gp.player.getY() - y;

            int tolerance = 5;

            if (Math.abs(dx) <= tolerance && Math.abs(dy) <= tolerance) {
                isNearPlayer = true;
            } else if (Math.abs(dx) > tolerance) {
                isNearPlayer = false;

                if (dx < 0) {
                    moveLeft();
                    direction = "left";
                } else {
                    moveRight();
                    direction = "right";
                }

            } else if (Math.abs(dy) > tolerance) {
                isNearPlayer = false;

                if (dy < 0) {
                    moveUp();
                } else {
                    moveDown();
                }
            }
        } else {
            // Zombie is idle, set idle image and direction
            isNearPlayer = false;
            if (reachedTarget()) {
                // Generate new random target coordinates close to the zombie
                targetX = getRandomXInRange(x - 50, x + 50);
                targetY = getRandomYInRange(y - 50, y + 50);
            }
            moveTowardsTarget();
            currentImageIndex = getMovementOffset() + (frameCounter / 10) % 3; // Update animation frame for idle state
        }

        // Increment frame counter for animation timing
        frameCounter++;
    }

    private void wander() {
        if (!isChasing && frameCounter % 60 == 0) {
            if (x == targetX && y == targetY) {
                targetX = getRandomX();
                targetY = getRandomY();
            }

            // Calculate the direction to the target coordinates
            int dx = targetX - x;
            int dy = targetY - y;

            if (dx < 0) {
                moveLeft();
                direction = "left";
            } else if (dx > 0) {
                moveRight();
                direction = "right";
            }

            // Update the zombie's position based on the chosen direction
            int newX = x + dx;
            int newY = y + dy;

            // Check if the new position is within the game bounds and does not collide with obstacles
            if (newX >= 0 && newX < gp.SCREEN_WIDTH && newY >= 0 && newY < gp.SCREEN_HEIGHT && !collidesWithObstacle(newX, newY)) {
                x = newX;
                y = newY;
            }

            // Update the animation frame based on the current direction
            currentImageIndex = getMovementOffset() + (frameCounter / 10) % 3;
        }
    }

    private boolean checkPlayerVisibility() {
        int dx = Math.abs(gp.player.getX() - x);
        int dy = Math.abs(gp.player.getY() - y);

        int visibilityThreshold = 200;

        if (dx <= visibilityThreshold && dy <= visibilityThreshold) {
            int stepSize = 10;

            int numSteps = Math.max(dx / stepSize, dy / stepSize);

            float dxStep = dx / (float) numSteps;
            float dyStep = dy / (float) numSteps;

            float x = this.x;
            float y = this.y;
            for (int i = 0; i < numSteps; i++) {
                x += dxStep;
                y += dyStep;

                collisionOn = false;
                gp.collisionChecker.checkTile(this);
                if (collisionOn) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    private boolean collidesWithObstacle(int x, int y) {
        // Implement your collision detection logic here
        // Check if the position (x, y) collides with any obstacles in your game world
        // Return true if collision occurs, false otherwise

        // Placeholder implementation assuming no obstacles
        return false;
    }

    private int getRandomX() {
        return new Random().nextInt(gp.SCREEN_WIDTH);
    }

    private int getRandomY() {
        return new Random().nextInt(gp.SCREEN_HEIGHT);
    }

    private boolean reachedTarget() {
        // Check if the zombie has reached its target coordinates
        return x == targetX && y == targetY;
    }

    private void moveTowardsTarget() {
        // Move the zombie towards the target coordinates
        int dx = Integer.compare(targetX, x);
        int dy = Integer.compare(targetY, y);

        if (dx != 0 || dy != 0) {
            if (Math.abs(dx) >= Math.abs(dy)) {
                moveHorizontally(dx);
                moveVertically(dy);
            } else {
                moveVertically(dy);
                moveHorizontally(dx);
            }
        }
    }

    private void moveHorizontally(int dx) {
        if (dx > 0) {
            moveRight();
            direction = "right";
        } else if (dx < 0) {
            moveLeft();
            direction = "left";
        }
    }

    private void moveVertically(int dy) {
        if (dy > 0) {
            moveDown();
        } else if (dy < 0) {
            moveUp();
        }
    }

    public void draw(GamePanel gamePanel, Graphics2D g2d) {
        if (isNearPlayer) {
            currentImageIndex = getAttackOffset() + (frameCounter / 10) % 3;
        } else if (isChasing) {
            currentImageIndex = getMovementOffset() + (frameCounter / 10) % 3;
        }

        if (currentImageIndex >= textures.size()) {
            currentImageIndex = 0; // Reset to the first image if index is out of bounds
        }

        g2d.drawImage(textures.get(currentImageIndex), gamePanel.getCamera().translateX(x), gamePanel.getCamera().translateY(y), gp.OBJECT_SIZE, gp.OBJECT_SIZE, null);
    }

    private int getAttackOffset() {
        return (direction.equals("left")) ? 8 : 11;
    }

    private int getMovementOffset() {
        return (direction.equals("left")) ? 0 : 4;
    }

    private int getRandomXInRange(int minX, int maxX) {
        return new Random().nextInt(maxX - minX + 1) + minX;
    }

    private int getRandomYInRange(int minY, int maxY) {
        return new Random().nextInt(maxY - minY + 1) + minY;
    }
}
