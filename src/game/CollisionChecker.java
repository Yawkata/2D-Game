package game;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getX() - 25 + entity.collisionArea.x;
        int entityRightWorldX = entity.getX() - 25 + entity.collisionArea.x + entity.collisionArea.width;
        int entityTopWorldY = entity.getY() - 25 + entity.collisionArea.y;
        int entityBottomWorldY = entity.getY() - 25 + entity.collisionArea.y + entity.collisionArea.height;

        int entityLeftCol = entityLeftWorldX/gp.OBJECT_SIZE;
        int entityRightCol = entityRightWorldX/gp.OBJECT_SIZE;
        int entityTopRow = entityTopWorldY/gp.OBJECT_SIZE;
        int entityBottomRow = entityBottomWorldY/gp.OBJECT_SIZE;

        int tileLeft, tileRight;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.OBJECT_SIZE;
                tileLeft = gp.tileM.worldGenerator.getTileValue(entityTopRow, entityLeftCol);
                tileRight = gp.tileM.worldGenerator.getTileValue(entityTopRow, entityRightCol);
                if (gp.tileM.tile.get(tileLeft).collision || gp.tileM.tile.get(tileRight).collision) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.OBJECT_SIZE;
                tileLeft = gp.tileM.worldGenerator.getTileValue(entityBottomRow, entityLeftCol);
                tileRight = gp.tileM.worldGenerator.getTileValue(entityBottomRow, entityRightCol);
                if (gp.tileM.tile.get(tileLeft).collision || gp.tileM.tile.get(tileRight).collision) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.OBJECT_SIZE;
                tileLeft = gp.tileM.worldGenerator.getTileValue(entityTopRow, entityLeftCol);
                tileRight = gp.tileM.worldGenerator.getTileValue(entityBottomRow, entityLeftCol);
                if (gp.tileM.tile.get(tileLeft).collision || gp.tileM.tile.get(tileRight).collision) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.OBJECT_SIZE;
                tileLeft = gp.tileM.worldGenerator.getTileValue(entityTopRow, entityRightCol);
                tileRight = gp.tileM.worldGenerator.getTileValue(entityBottomRow, entityRightCol);
                if (gp.tileM.tile.get(tileLeft).collision || gp.tileM.tile.get(tileRight).collision) {
                    entity.collisionOn = true;
                }
            }
        }

    }

}
