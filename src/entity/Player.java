package entity;

import game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Player extends Entity {
    private final ArrayList<Image> movementTextures = new ArrayList<>(Arrays.asList(
            new ImageIcon(".\\assets\\player\\animation\\movement\\moving_up\\first_step.png").getImage(),
            new ImageIcon(".\\assets\\player\\animation\\movement\\moving_up\\idle.png").getImage(),
            new ImageIcon(".\\assets\\player\\animation\\movement\\moving_up\\second_step.png").getImage(),
            new ImageIcon(".\\assets\\player\\animation\\movement\\moving_down\\first_step.png").getImage(),
            new ImageIcon(".\\assets\\player\\animation\\movement\\moving_down\\idle.png").getImage(),
            new ImageIcon(".\\assets\\player\\animation\\movement\\moving_down\\second_step.png").getImage(),
            new ImageIcon(".\\assets\\player\\animation\\movement\\moving_left\\first_step.png").getImage(),
            new ImageIcon(".\\assets\\player\\animation\\movement\\moving_left\\idle.png").getImage(),
            new ImageIcon(".\\assets\\player\\animation\\movement\\moving_left\\second_step.png").getImage(),
            new ImageIcon(".\\assets\\player\\animation\\movement\\moving_right\\first_step.png").getImage(),
            new ImageIcon(".\\assets\\player\\animation\\movement\\moving_right\\idle.png").getImage(),
            new ImageIcon(".\\assets\\player\\animation\\movement\\moving_right\\second_step.png").getImage()
    ));

    private String direction;
    private int currentImageIndex;
    private int frameCounter;

    public Player(int x, int y) {
        super(x, y);
        speed = 4;
        currentImageIndex = 4;
        direction = "down";
        frameCounter = 0;
    }

    public void update(boolean isUpPressed, boolean isDownPressed, boolean isLeftPressed, boolean isRightPressed) {
        int newCount;
        String previousDirection = direction;

        if (isUpPressed) {
            moveUp();
            direction = "up";
        } else if (isDownPressed) {
            moveDown();
            direction = "down";
        } else if (isLeftPressed) {
            moveLeft();
            direction = "left";
        } else if (isRightPressed) {
            moveRight();
            direction = "right";
        } else {
            currentImageIndex = getDirectionOffset();
            frameCounter = 0;
        }

        if (!previousDirection.equals(direction)) {
            currentImageIndex = getDirectionOffset();
            frameCounter = 0;
        }

        if (frameCounter % 10 == 0) {
            newCount = (currentImageIndex % 3 == 2) ? getDirectionOffset() : (currentImageIndex + 1);
            currentImageIndex = newCount;
        }

        frameCounter++;

        if (!isUpPressed && !isDownPressed && !isLeftPressed && !isRightPressed) {
            currentImageIndex = getDirectionOffset() + 1;
        }
    }

    private int getDirectionOffset() {
        return switch (direction) {
            case "down" -> 3;
            case "left" -> 6;
            case "right" -> 9;
            default -> 0;
        };
    }

    public void draw(GamePanel gamePanel, Graphics2D g2d) {
        g2d.drawImage(movementTextures.get(currentImageIndex), gamePanel.getCamera().translateX(x),
                gamePanel.getCamera().translateY(y), null);
    }
}
